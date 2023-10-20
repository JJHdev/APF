package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 특허상표권현황 Service Interface
 * 
 * @class   : EntPtntService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntPtntService {

    /**
     * 특허상표권현황 페이징목록 조회
     */
    public PaginatedArrayList listEntPtnt(EntVO entVO, int currPage, int pageSize) throws Exception;

    /**
     * 특허상표권현황 목록조회
     */
    public List listEntPtnt(EntVO entVO) throws Exception;

    /**
     * 특허상표권현황 구분별 합계건수 목록조회
     */
    public List listEntPtntSummary(EntVO entVO) throws Exception;

    /**
     * 특허상표권현황 상세조회
     */
    public EntVO viewEntPtnt(EntVO entVO) throws Exception;

    /**
     * 특허상표권현황 다중저장처리
     */
    public String saveEntPtnt(EntVO entVO, List<EntVO> list) throws Exception;

    /**
     * 2023.07.24 LSH
     * 특허상표권현황 KODATA 등록여부 조회
     */
    public boolean existKodata(String bzentyNo) throws Exception;
}