package business.sys.log.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 프로그램접속이력을 관리하는 Service Interface
 * 
 * @class   : AccessLogService
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface AccessLogService {

    /**
     * 프로그램접속이력 페이징목록 조회
     */
    public PaginatedArrayList listAccessLog(LogVO logVO, int currPage, int pageSize) throws Exception;

    /**
     * 프로그램접속이력 목록조회
     */
    public List listAccessLog(LogVO logVO) throws Exception;

    /**
     * 프로그램접속이력 다중삭제
     */
    public String deltAccessLog(LogVO logVO) throws Exception;
}