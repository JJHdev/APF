package business.sys.log.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.log.service.InfoLogService;
import business.sys.log.service.LogVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 정보조회이력 Service 구현 클래스
 * 
 * @class   : InfoLogServiceImpl
 * @author  : LSH
 * @since   : 2021.11.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("InfoLogService")
@SuppressWarnings({"all"})
public class InfoLogServiceImpl extends BaseService implements InfoLogService {

    @Resource(name = "InfoLogDAO")
    private InfoLogDAO infoLogDAO;
	
    /**
     * 정보조회이력 페이징목록조회
     */
    @Override
    public PaginatedArrayList listInfoLog(LogVO logVO, int currPage, int pageSize) throws Exception {
    	return infoLogDAO.listInfoLog(logVO, currPage, pageSize);
    }

    /**
     * 정보조회이력 목록조회
     */
    @Override
    public List listInfoLog(LogVO logVO) throws Exception {
    	return infoLogDAO.listInfoLog(logVO);
    }

    /**
     * 정보조회이력 다중삭제
     */
    public String deltInfoLog(LogVO logVO) throws Exception {

		if (logVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		List<LogVO> rows = logVO.getLogList();
		
		if (rows == null || rows.size() == 0)
			throw processException("error.comm.notTarget");
		
		for (LogVO row : rows) {
			// 정보조회이력 삭제
			ret += infoLogDAO.deltInfoLog(row);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
    }
}