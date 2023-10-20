package business.sys.menu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 메뉴관리을 관리하는 Service Interface
 * 
 * @class   : MenuService
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface MenuService {

    /**
     * 메뉴관리 페이징목록 조회
     */
    public PaginatedArrayList listMenu(MenuVO menuVO, int currPage, int pageSize) throws Exception;

    /**
     * 메뉴관리 목록조회
     */
    public List listMenu(MenuVO menuVO) throws Exception;

    /**
     * 메뉴관리 상세조회
     */
    public MenuVO viewMenu(MenuVO menuVO) throws Exception;

    /**
     * 메뉴관리 등록,수정,삭제
     */
    public String saveMenu(MenuVO menuVO) throws Exception;
}