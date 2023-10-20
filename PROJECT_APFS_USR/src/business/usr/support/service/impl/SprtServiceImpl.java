package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.file.service.BizFileService;
import business.usr.support.service.SprtFnnrVO;
import business.usr.support.service.SprtMxtrVO;
import business.usr.support.service.SprtService;
import business.usr.support.service.SprtVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;

/**
 * [서비스클래스] - 투자지원신청 Service 구현 클래스
 * 
 * @class   : SprtServiceImpl
 * @author  : LSH
 * @since   : 2023.05.22
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("SprtService")
@SuppressWarnings({"all"})
public class SprtServiceImpl extends BaseService implements SprtService {

    @Resource(name = "SprtDAO")
    private SprtDAO sprtDAO;

    @Resource(name = "SprtFnnrDAO")
    private SprtFnnrDAO sprtFnnrDAO;

    @Resource(name = "SprtMxtrDAO")
    private SprtMxtrDAO sprtMxtrDAO;

    @Resource(name="BizFileService")
    protected BizFileService bizFileService;
    
    /**
     * 투자지원신청 페이징목록조회
     */
    @Override
    public PaginatedArrayList listSprt(SprtVO sprtVO, int currPage, int pageSize) throws Exception {
    	return sprtDAO.listSprt(sprtVO, currPage, pageSize);
    }

    /**
     * 투자지원신청 목록조회
     */
    @Override
    public List listSprt(SprtVO sprtVO) throws Exception {
    	return sprtDAO.listSprt(sprtVO);
    }

    /**
     * 투자지원신청 상세조회
     */
	@Override
	public SprtVO viewSprt(String sprtAplyNo) throws Exception {
		return sprtDAO.viewSprt(sprtAplyNo);
	}

    /**
     * 투자지원신청 있는지 확인
     */
    @Override
    public boolean existSprt(String sprtAplyNo) {
    	return sprtDAO.existSprt(sprtAplyNo);
    }

    /**
     * 투자지원신청 지원사업 목록조회
     */
	@Override
    public List listPrgrm(SprtVO sprtVO) throws Exception {
    	return sprtDAO.listPrgrm(sprtVO);
    }

    /**
     * 투자지원신청 지원사업 상세조회
     */
	@Override
    public Map viewPrgrm(SprtVO sprtVO) throws Exception {
    	return sprtDAO.viewPrgrm(sprtVO);
    }

    /**
     * 투자지원신청 지원사업 유효한지 확인
     */
	@Override
    public boolean existPrgrm(String prgrmNo) {
    	return sprtDAO.existPrgrm(prgrmNo);
    }

    /**
     * 투자지원신청 지원사업 명칭
     */
	@Override
    public String getPrgrmName(String prgrmNo) throws Exception {
    	Map obj = viewPrgrm(SprtVO.builder().prgrmNo(prgrmNo).build());
    	if (obj == null)
    		return null;
    	return (String)obj.get("text");
    }

    /**
     * 투자지원신청 매출액 목록조회
     */
	@Override
    public List listSprtFnnr(SprtVO sprtVO) throws Exception {
    	return sprtFnnrDAO.listSprtFnnr(sprtVO);
    }

    /**
     * 투자지원신청 투자조합 목록조회
     */
	@Override
    public List listSprtMxtr(SprtVO sprtVO) throws Exception {
    	return sprtMxtrDAO.listSprtMxtr(sprtVO);
    }
    
    /**
     * 투자지원신청 등록,수정
     */
	@Override
	public String saveSprt(SprtVO sprtVO) throws Exception {
		
		if (sprtVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = sprtVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 투자지원신청 수정
			ret = sprtDAO.updtSprt(sprtVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 투자지원신청 등록
			ret = sprtDAO.regiSprt(sprtVO);
		}
		if (ret > 0) {
			// 투자조합정보 저장처리
			_saveSprtMxtr(sprtVO);
			// 매출액 저장처리
			_saveSprtFnnr(sprtVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return sprtVO.getSprtAplyNo();
		else
			throw processException("error.comm.notProcess");
	}
    
    /**
     * 투자지원신청 제출서류 저장
     * 제출서류가 없을 경우 제출상태만 저장한다.
     */
	@Override
	public String saveFile(SprtVO sprtVO) throws Exception {
		
		if (sprtVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = sprtDAO.updtSprt(sprtVO);

		// 투자지원신청 수정 (제출처리)
		if (ret > 0 && sprtVO.existFile()) {
			// 제출서류 업데이트
			// 1) 문서번호 업데이트
			// 2) 임시경로에서 실제경로로 파일이동처리
			bizFileService.savePapeFile(
					sprtVO,
					sprtVO.getSaveFiles(),
					sprtVO.getRemoveFiles()
			);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
    
    /**
     * 투자지원신청 매출액 다중저장처리
     * 기존의 데이터를 삭제후 저장한다.
     */
	private void _saveSprtFnnr(SprtVO sprtVO) throws Exception {
		
		// 신청번호기준 매출액 삭제처리
		sprtFnnrDAO.deltSprtFnnr(
			SprtFnnrVO.builder().sprtAplyNo(sprtVO.getSprtAplyNo()).build()
		);

		// 신청정보 삭제인 경우
		if (CommConst.DELETE.equals(sprtVO.getMode())) 
			return;
		if (sprtVO.getFnnrList() == null)
			return;
		
		for (SprtFnnrVO item : sprtVO.getFnnrList()) {
			
			item.setSprtAplyNo(sprtVO.getSprtAplyNo());
			item.setGsUserNo  (sprtVO.getGsUserNo());
			// 매출액 등록처리
			sprtFnnrDAO.regiSprtFnnr(item);
		}
	}
    
    /**
     * 투자지원신청 투자조합정보 다중저장처리
     * 기존의 데이터를 삭제후 저장한다.
     */
	private void _saveSprtMxtr(SprtVO sprtVO) throws Exception {
		
		// 신청번호기준 투자조합정보 삭제처리
		sprtMxtrDAO.deltSprtMxtr(
			SprtMxtrVO.builder().sprtAplyNo(sprtVO.getSprtAplyNo()).build()
		);

		// 신청정보 삭제인 경우
		if (CommConst.DELETE.equals(sprtVO.getMode())) 
			return;
		if (sprtVO.getMxtrList() == null)
			return;
		
		for (SprtMxtrVO item : sprtVO.getMxtrList()) {
			
			item.setSprtAplyNo(sprtVO.getSprtAplyNo());
			item.setGsUserNo  (sprtVO.getGsUserNo());
			// 투자조합정보 등록처리
			sprtMxtrDAO.regiSprtMxtr(item);
		}
	}

    /**
     * 마이페이지 - 신청내역 - 지원사업진행현황 목록조회
     */
	@Override
    public List listSprtPrgre(String sprtAplyNo) throws Exception {
        return sprtDAO.listSprtPrgre(sprtAplyNo);
    }
}