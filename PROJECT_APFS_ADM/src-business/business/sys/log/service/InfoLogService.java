package business.sys.log.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 정보조회이력 Service Interface
 * 
 * @class   : InfoLogService
 * @author  : LSH
 * @since   : 2021.11.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface InfoLogService {

    /**
     * 정보조회이력 페이징목록 조회
     */
    public PaginatedArrayList listInfoLog(LogVO logVO, int currPage, int pageSize) throws Exception;

    /**
     * 정보조회이력 목록조회
     */
    public List listInfoLog(LogVO logVO) throws Exception;

    /**
     * 정보조회이력 다중삭제
     */
    public String deltInfoLog(LogVO logVO) throws Exception;
}