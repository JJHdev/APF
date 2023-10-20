package business.sys.log.service;

import java.util.List;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 다운로드이력 Service Interface
 *
 * @class   : DownLogService
 * @author  : LSH
 * @since   : 2021.11.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface DownLogService {

    /**
     * 다운로드이력 페이징목록 조회
     */
    public PaginatedArrayList listDownLog(LogVO logVO, int currPage, int pageSize) throws Exception;

    /**
     * 다운로드이력 목록조회
     */
    public List listDownLog(LogVO logVO) throws Exception;

    /**
     * 다운로드이력 등록
     */
    public String regiDownLog(LogVO logVO) throws Exception;

    /**
     * 다운로드이력 다중삭제
     */
    public String deltDownLog(LogVO logVO) throws Exception;
}