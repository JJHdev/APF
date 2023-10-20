package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntOthsptHstService;
import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 정부기타지원이력 Service 구현 클래스
 * 
 * @class   : EntOthsptHstServiceImpl
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntOthsptHstService")
@SuppressWarnings({"all"})
public class EntOthsptHstServiceImpl extends BaseService implements EntOthsptHstService {

    @Resource(name = "EntOthsptHstDAO")
    private EntOthsptHstDAO entOthsptHstDAO;

    /**
     * 정부기타지원이력 목록조회
     */
    @Override
    public List listEntOthsptHst(EntVO entVO) throws Exception {
    	return entOthsptHstDAO.listEntOthsptHst(entVO);
    }

    /**
     * 정부지원이력 기관별 합계건수 목록조회
     */
    @Override
    public List listEntOthsptHstSummary(EntVO entVO) throws Exception {
    	return entOthsptHstDAO.listEntOthsptHstSummary(entVO);
    }

    /**
     * 정부지원이력 기관별 년도별 건수 목록조회
     */
    public List listEntOthsptHstYears(EntVO entVO) throws Exception {
    	return entOthsptHstDAO.listEntOthsptHstYears(entVO);
    }

    /**
     * 정부기타지원이력 상세조회
     */
	@Override
	public EntVO viewEntOthsptHst(EntVO entVO) throws Exception {
		return entOthsptHstDAO.viewEntOthsptHst(entVO);
	}

    /**
     * 정부기타지원이력 등록,수정,삭제
     */
	@Override
	public String saveEntOthsptHst(EntVO entVO) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = entVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 정부기타지원이력 수정
			ret = entOthsptHstDAO.updtEntOthsptHst(entVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 정부기타지원이력 등록
			ret = entOthsptHstDAO.regiEntOthsptHst(entVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 정부기타지원이력 삭제
			ret = entOthsptHstDAO.deltEntOthsptHst(entVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}