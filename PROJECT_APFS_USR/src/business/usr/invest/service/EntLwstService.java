package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 소송현황 Service Interface
 * 
 * @class   : EntLwstService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntLwstService {

    /**
     * 소송현황 목록조회
     */
    public List listEntLwst(EntVO entVO) throws Exception;

    /**
     * 소송현황 상세조회
     */
    public EntVO viewEntLwst(EntVO entVO) throws Exception;

    /**
     * 소송현황 다중저장처리
     */
    public String saveEntLwst(EntVO entVO, List<EntVO> list) throws Exception;
}