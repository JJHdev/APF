package business.adm.support.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.support.service.SupportService;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 투자정보관리 - 모태펀드등록 Service 구현 클래스
 * 
 * @class   : SupportServiceImpl
 * @author  : LHB
 * @since   : 2023.04.20
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("SupportService")
@SuppressWarnings({"all"})
public class SupportServiceImpl extends BaseService implements SupportService {

    @Resource(name = "SupportDAO")
    private SupportDAO supportDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * 세부지원사업관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listInvtSprt(SupportVO supportVO, int currPage, int pageSize) throws Exception {
    	return supportDAO.listInvtSprt(supportVO, currPage, pageSize);
    }

    /**
     * 세부지원사업관리 목록조회
     */
    @Override
    public List listInvtSprt(SupportVO supportVO) throws Exception {
    	return supportDAO.listInvtSprt(supportVO);
    }

    /**
     * 세부지원사업관리 상세조회
     */
	@Override
	public SupportVO viewInvtSprt(SupportVO supportVO) throws Exception {
		return supportDAO.viewInvtSprt(supportVO);
	}

    /**
     * 세부지원사업관리 등록,수정,삭제
     */
	@Override
	public String saveInvtSprt(SupportVO supportVO) throws Exception {
		
		if (supportVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = supportVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 세부지원사업관리 수정
			ret = supportDAO.updtInvtSprt(supportVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 세부지원사업관리 등록
			ret = supportDAO.regiInvtSprt(supportVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 세부지원사업관리 삭제
			ret = supportDAO.deltInvtSprt(supportVO);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	/**
     * 신청현황관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listInvtSprtAply(SupportVO supportVO, int currPage, int pageSize) throws Exception {
    	return supportDAO.listInvtSprtAply(supportVO, currPage, pageSize);
    }

    /**
     * 신청현황관리 목록조회
     */
    @Override
    public List listInvtSprtAply(SupportVO supportVO) throws Exception {
    	return supportDAO.listInvtSprtAply(supportVO);
    }

    /**
     * 신청현황관리 상세조회
     */
	@Override
	public SupportVO viewInvtSprtAply(SupportVO supportVO) throws Exception {
		return supportDAO.viewInvtSprtAply(supportVO);
	}
	
	/**
     * 신청현황관리 수정, 삭제
     */
    public String saveInvtSprtAply(SupportVO supportVO, List<FileInfo> files) throws Exception {
    	
    	if (supportVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = supportVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 신청현황 수정 
			ret = supportDAO.updtInvtSprtAply(supportVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 신청현황 삭제
			ret = supportDAO.deltInvtSprtAply(supportVO);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
    }
    
    /**
     * 신청현황관리 엑셀 목록조회
     */
    @Override
    public List listInvtSprtAplyExcl(SupportVO supportVO) throws Exception {
    	return supportDAO.listInvtSprtAplyExcl(supportVO);
    }
    
    
    
    /**
     * 신청현황 매출액 페이징목록조회
     */
    @Override
    public PaginatedArrayList listInvtSprtSls(SupportVO supportVO, int currPage, int pageSize) throws Exception {
    	return supportDAO.listInvtSprtSls(supportVO, currPage, pageSize);
    }

    /**
     * 신청현황 매출액 목록조회
     */
    @Override
    public List listInvtSprtSls(SupportVO supportVO) throws Exception {
    	return supportDAO.listInvtSprtSls(supportVO);
    }
    
    
    
    /**
     * 농식품 투자조합 페이징목록조회
     */
    @Override
    public PaginatedArrayList listInvtMxtr(SupportVO supportVO, int currPage, int pageSize) throws Exception {
    	return supportDAO.listInvtMxtr(supportVO, currPage, pageSize);
    }

    /**
     * 농식품 투자조합 목록조회
     */
    @Override
    public List listInvtMxtr(SupportVO supportVO) throws Exception {
    	return supportDAO.listInvtMxtr(supportVO);
    }
    
    
	
    /**
     * 지원사업 진행현황 페이징목록 조회
     */
    @Override
    public PaginatedArrayList listSprtBizPrgre(SupportVO supportVO, int currPage, int pageSize) throws Exception {
    	return supportDAO.listSprtBizPrgre(supportVO, currPage, pageSize);
    }

    /**
     * 지원사업 진행현황 목록조회
     */
    @Override
    public List listSprtBizPrgre(SupportVO supportVO) throws Exception {
    	return supportDAO.listSprtBizPrgre(supportVO);
    }

    /**
     * 지원사업 진행현황 등록,수정,삭제
     */
	@Override
	public String saveSprtBizPrgre(SupportVO supportVO) throws Exception {
		
		if (supportVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		
		String sprtAplySeCd = supportVO.getSprtAplySeCd();
		String prgrsSttsCd	= supportVO.getPrgrsSttsCd();
		String prcsCycl		= supportVO.getPrcsCycl();
		String preSprtAplySeCd;
		
		if (sprtAplySeCd.equals(CodeConst.SPRT_APLY_SE_BEFORE)) {		// 투자유치 전
			preSprtAplySeCd = "B";
		} else if (sprtAplySeCd.equals(CodeConst.SPRT_APLY_SE_AFTER)) {	// 투자유치 후
			preSprtAplySeCd = "A";
		} else if (sprtAplySeCd.equals(CodeConst.SPRT_APLY_SE_CROWD)) {	// 크라우드 펀딩
			preSprtAplySeCd = "C";
		} else {
			throw processException("error.comm.notProcess");
		}
		
		// 관련 없는 데이터들 NULL 값 처리
		if(prcsCycl != null && prcsCycl.isEmpty()) supportVO.setPrcsCycl(null);
		
		supportVO.setPrgrsSttsCd(preSprtAplySeCd + prgrsSttsCd); // 진행상태코드 세팅
		
		ret = supportDAO.regiSprtBizPrgre(supportVO);
		
		if (ret > 0) {
			// 신청현황 상태값 변경
			ret = supportDAO.updtInvtSprtAply(supportVO);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

	
	
	//2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 데이터조회
	@Override
	public SupportVO viewInvtSprtAplyFileInfo(SupportVO supportVO) throws Exception {
		return supportDAO.viewInvtSprtAplyFileInfo(supportVO);
	}
	//2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 메소드
	@Override
	public String saveFile(SupportVO supportVO) throws Exception {
		if (supportVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = supportDAO.updtSprt(supportVO);

		// 투자지원신청 수정 (제출처리)
		if (ret > 0 && supportVO.existFile()) {
			// 제출서류 업데이트
			// 1) 문서번호 업데이트
			// 2) 임시경로에서 실제경로로 파일이동처리
			bizFileService.savePapeFile(
					supportVO,
					supportVO.getSaveFiles(),
					supportVO.getRemoveFiles()
			);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}