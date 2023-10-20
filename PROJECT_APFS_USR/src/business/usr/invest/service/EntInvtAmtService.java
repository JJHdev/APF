package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 투자금액 Service Interface
 * 
 * @class   : EntInvtAmtService
 * @author  : LSH
 * @since   : 2023.05.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntInvtAmtService {

    /**
     * 투자금액 목록조회
     */
    public List listEntInvtAmt(EntVO entVO) throws Exception;

    /**
     * 투자금액 상세조회
     */
    public EntVO viewEntInvtAmt(EntVO entVO) throws Exception;

    /**
     * 투자금액 다중저장처리
     */
    public String saveEntInvtAmt(EntVO entVO, List<EntVO> list) throws Exception;
}