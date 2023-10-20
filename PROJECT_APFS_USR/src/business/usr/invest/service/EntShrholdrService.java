package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 주주현황 Service Interface
 * 
 * @class   : EntShrholdrService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntShrholdrService {

    /**
     * 주주현황 목록조회
     */
    public List listEntShrholdr(EntVO entVO) throws Exception;

    /**
     * 주주현황 상세조회
     */
    public EntVO viewEntShrholdr(EntVO entVO) throws Exception;

    /**
     * 주주현황 다중저장처리
     */
    public String saveEntShrholdr(EntVO entVO, List<EntVO> list) throws Exception;
}