package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 경영진정보 Service Interface
 * 
 * @class   : EntMgmtService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntMgmtService {

    /**
     * 경영진정보 목록조회
     */
    public List listEntMgmt(EntVO entVO) throws Exception;

    /**
     * 경영진정보 상세조회
     */
    public EntVO viewEntMgmt(EntVO entVO) throws Exception;

    /**
     * 경영진정보 다중저장처리
     */
    public String saveEntMgmt(EntVO entVO, List<EntVO> list) throws Exception;
}