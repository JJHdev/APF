package business.sys.log.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 로그인이력을 관리하는 Service Interface
 * 
 * @class   : LoginLogService
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface LoginLogService {

    /**
     * 로그인이력 페이징목록 조회
     */
    public PaginatedArrayList listLoginLog(LogVO logVO, int currPage, int pageSize) throws Exception;

    /**
     * 로그인이력 목록조회
     */
    public List listLoginLog(LogVO logVO) throws Exception;

    /**
     * 로그인이력 다중삭제
     */
    public String deltLoginLog(LogVO logVO) throws Exception;
}