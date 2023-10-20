package business.sys.log.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.log.service.AccessLogService;
import business.sys.log.service.LogVO;
import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 프로그램접속이력을 관리하는 Service 구현 클래스
 * 
 * @class   : AccessLogServiceImpl
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("AccessLogService")
@SuppressWarnings({"all"})
public class AccessLogServiceImpl extends BaseService implements AccessLogService {

    @Resource(name = "AccessLogDAO")
    private AccessLogDAO accessLogDAO;
	
    /**
     * 프로그램접속이력 페이징목록조회
     */
    @Override
    public PaginatedArrayList listAccessLog(LogVO logVO, int currPage, int pageSize) throws Exception {
    	return accessLogDAO.listAccessLog(logVO, currPage, pageSize);
    }

    /**
     * 프로그램접속이력 목록조회
     */
    @Override
    public List listAccessLog(LogVO logVO) throws Exception {
    	return accessLogDAO.listAccessLog(logVO);
    }

    /**
     * 프로그램접속이력 다중삭제
     */
	@Override
	public String deltAccessLog(LogVO logVO) throws Exception {
		
		if (logVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		List<LogVO> rows = logVO.getLogList();
		
		if (rows == null || rows.size() == 0)
			throw processException("error.comm.notTarget");
		
		for (LogVO row : rows) {
			// 프로그램접속이력 삭제
			ret += accessLogDAO.deltAccessLog(row);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}