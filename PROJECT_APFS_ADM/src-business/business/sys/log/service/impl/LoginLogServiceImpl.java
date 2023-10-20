package business.sys.log.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.log.service.LoginLogService;
import business.sys.log.service.LogVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 로그인이력을 관리하는 Service 구현 클래스
 * 
 * @class   : LoginLogServiceImpl
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("LoginLogService")
@SuppressWarnings({"all"})
public class LoginLogServiceImpl extends BaseService implements LoginLogService {

    @Resource(name = "LoginLogDAO")
    private LoginLogDAO loginLogDAO;
	
    /**
     * 로그인이력 페이징목록조회
     */
    @Override
    public PaginatedArrayList listLoginLog(LogVO logVO, int currPage, int pageSize) throws Exception {
    	return loginLogDAO.listLoginLog(logVO, currPage, pageSize);
    }

    /**
     * 로그인이력 목록조회
     */
    @Override
    public List listLoginLog(LogVO logVO) throws Exception {
    	return loginLogDAO.listLoginLog(logVO);
    }

    /**
     * 로그인이력 다중삭제
     */
	@Override
	public String deltLoginLog(LogVO logVO) throws Exception {
		
		if (logVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		List<LogVO> rows = logVO.getLogList();
		
		if (rows == null || rows.size() == 0)
			throw processException("error.comm.notTarget");
		
		for (LogVO row : rows) {
			// 로그인이력 삭제
			ret += loginLogDAO.deltLoginLog(row);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}