package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntFnnrService;
import business.usr.invest.service.EntVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 재무정보 Service 구현 클래스
 * 
 * @class   : EntFnnrServiceImpl
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntFnnrService")
@SuppressWarnings({"all"})
public class EntFnnrServiceImpl extends BaseService implements EntFnnrService {

    @Resource(name = "EntFnnrDAO")
    private EntFnnrDAO entFnnrDAO;

    /**
     * 재무정보 목록조회
     */
    @Override
    public List listEntFnnr(EntVO entVO) throws Exception {
    	return entFnnrDAO.listEntFnnr(entVO);
    }

    /**
     * 재무정보 요약 목록 조회
     */
	@Override
    public List listEntFnnrSummary(EntVO entVO) throws Exception {
    	return entFnnrDAO.listEntFnnrSummary(entVO);
    }

    /**
     * 재무정보 세부 목록 조회
     */
	@Override
    public List listEntFnnrDetails(EntVO entVO) throws Exception {
    	return entFnnrDAO.listEntFnnrDetails(entVO);
    }

    /**
     * 재무정보 년도별 목록 조회
     */
	@Override
    public List listEntFnnrYear(EntVO entVO) throws Exception {
    	return entFnnrDAO.listEntFnnrYear(entVO);
    }
    
    /**
     * 재무정보 기준년도 조회
     */
	@Override
    public String getEntFnnrYear(EntVO entVO) throws Exception {
    	return entFnnrDAO.getEntFnnrYear(entVO);
    }
    
    /**
     * 재무정보 상세조회
     */
	@Override
	public EntVO viewEntFnnr(EntVO entVO) throws Exception {
		return entFnnrDAO.viewEntFnnr(entVO);
	}
	
	/**
	 * 삭제대상에서 제외처리
	 */
	private void _removeItem(List<EntVO> originals, EntVO item) {
		for (int i = 0 ; i < originals.size(); i++) {
			EntVO target = originals.get(i);
			if (target.getSn().equals(item.getSn())) {
				originals.remove(i);
				return;
			}
		}
	}

    /**
     * 2023.07.27 LSH
     * 재무정보 다중저장처리
     * - 수기입력으로 작성된 추가재무정보의 데이터를 삭제후 재등록한다.
     */
	@Override
	public String saveEntFnnr(EntVO entVO, List<EntVO> list) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		if (list == null)
			return null;
		
		int ret = 0;
		
		// 수정대상 추가재무정보를 삭제한다.
		entFnnrDAO.deltEntFnnrForUpdates(entVO);
		
		for (EntVO item : list) {
			
			item.setGsUserNo(entVO.getGsUserNo());
			item.setBzentyNo(entVO.getBzentyNo());
			item.setIrNo    (entVO.getIrNo    ());
			
			// 재무정보 등록
			ret += entFnnrDAO.regiEntFnnr(item);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		return null;
	}

    /**
     * 2023.07.24 LSH
     * 재무정보 KODATA 등록여부 조회
     */
	@Override
    public boolean existKodata(String bzentyNo) throws Exception {
        return entFnnrDAO.existKodata(bzentyNo);
    }

    /**
     * 2023.07.24 LSH
     * 재무정보 수정 목록 조회
     * 
     * @param entVO.irNo     IR번호
     * @param entVO.fnnrSeCd 재무구분코드
     * @param entVO.dataSeCd 데이터구분코드
     * @param entVO.fnnrType 합계계정 공통코드
     * 
     * fnnrYr,fnnrAmt1,fnnrAmt2,fnnrAmt3
     */
	@Override
    public List listEntFnnrUpdates(EntVO entVO) throws Exception {
        return entFnnrDAO.listEntFnnrUpdates(entVO);
    }
}