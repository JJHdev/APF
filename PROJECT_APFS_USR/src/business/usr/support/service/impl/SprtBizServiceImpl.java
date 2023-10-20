package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.support.service.SprtBizService;
import business.usr.support.service.SprtBizVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 지원사업이력 Service 구현 클래스
 * 
 * @class   : SprtBizServiceImpl
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("SprtBizService")
@SuppressWarnings({"all"})
public class SprtBizServiceImpl extends BaseService implements SprtBizService {

    @Resource(name = "SprtBizDAO")
    private SprtBizDAO sprtBizDAO;
	
    /**
     * 지원사업이력 페이징목록조회
     */
    @Override
    public PaginatedArrayList listSprtBiz(SprtBizVO sprtBizVO, int currPage, int pageSize) throws Exception {
    	return sprtBizDAO.listSprtBiz(sprtBizVO, currPage, pageSize);
    }

    /**
     * 지원사업이력 목록조회
     */
    @Override
    public List listSprtBiz(SprtBizVO sprtBizVO) throws Exception {
    	return sprtBizDAO.listSprtBiz(sprtBizVO);
    }

    /**
     * 지원사업이력 상세조회
     */
	@Override
	public SprtBizVO viewSprtBiz(SprtBizVO sprtBizVO) throws Exception {
		return sprtBizDAO.viewSprtBiz(sprtBizVO);
	}

    /**
     * 지원사업이력 등록,수정,삭제
     */
	@Override
	public String saveSprtBiz(SprtBizVO sprtBizVO) throws Exception {
		
		if (sprtBizVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = sprtBizVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 지원사업이력 수정
			ret = sprtBizDAO.updtSprtBiz(sprtBizVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 지원사업이력 등록
			ret = sprtBizDAO.regiSprtBiz(sprtBizVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 지원사업이력 삭제
			ret = sprtBizDAO.deltSprtBiz(sprtBizVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}