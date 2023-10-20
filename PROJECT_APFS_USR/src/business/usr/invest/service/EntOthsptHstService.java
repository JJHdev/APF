package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 정부기타지원이력 Service Interface
 * 
 * @class   : EntOthsptHstService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntOthsptHstService {

    /**
     * 정부기타지원이력 목록조회
     */
    public List listEntOthsptHst(EntVO entVO) throws Exception;

    /**
     * 정부지원이력 기관별 합계건수 목록조회
     */
    public List listEntOthsptHstSummary(EntVO entVO) throws Exception;

    /**
     * 정부지원이력 기관별 년도별 건수 목록조회
     */
    public List listEntOthsptHstYears(EntVO entVO) throws Exception;

    /**
     * 정부기타지원이력 상세조회
     */
    public EntVO viewEntOthsptHst(EntVO entVO) throws Exception;

    /**
     * 정부기타지원이력 등록,수정,삭제
     */
    public String saveEntOthsptHst(EntVO entVO) throws Exception;
}