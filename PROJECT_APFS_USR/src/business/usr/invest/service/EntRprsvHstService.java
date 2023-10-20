package business.usr.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 대표자이력 Service Interface
 * 
 * @class   : EntRprsvHstService
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface EntRprsvHstService {

    /**
     * 대표자이력 목록조회
     */
    public List listEntRprsvHst(EntVO entVO) throws Exception;

    /**
     * 대표자이력 상세조회
     */
    public EntVO viewEntRprsvHst(EntVO entVO) throws Exception;

    /**
     * 대표자이력 다중저장처리
     */
    public String saveEntRprsvHst(EntVO entVO, List<EntVO> list) throws Exception;
}