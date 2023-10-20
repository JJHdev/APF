package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 회사연혁 Service Interface
 * 
 * @class   : EntCoHstService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntCoHstService {

    /**
     * 회사연혁 목록조회
     */
    public List listEntCoHst(EntVO entVO) throws Exception;

    /**
     * 회사연혁 상세조회
     */
    public EntVO viewEntCoHst(EntVO entVO) throws Exception;

    /**
     * 회사연혁 다중저장처리
     */
    public String saveEntCoHst(EntVO entVO, List<EntVO> list) throws Exception;
}