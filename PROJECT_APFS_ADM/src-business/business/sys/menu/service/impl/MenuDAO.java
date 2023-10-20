 package business.sys.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.sys.menu.service.MenuVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 메뉴관리을 관리하는 DAO 클래스
 * 
 * 사용 가능한  DAO Statement Method
 * 1. list          : 리스트 조회시 사용함.
 * 2. pageListOra   : 페이징처리용 리스트조회시 사용함. for Oracle, Tibero
 * 3. view          : 단건조회, 상세조회시 사용함.
 * 4. save          : INSERT, UPDATE, DELETE 모두 사용가능. (Return Type : Integer)
 * 5. insert        : INSERT (Return String : Key 채번 사용함.)
 * 6. update        : UPDATE (Return Type : Integer)
 * 7. delete        : DELETE (Return Type : Integer)
 * 
 *
 * @class   : MenuDAO
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("MenuDAO")
@SuppressWarnings({"all"})
public class MenuDAO extends BaseDAO {

    /**
     * 메뉴관리 페이징목록 조회
     */
    public PaginatedArrayList listMenu(MenuVO menuVO, int currPage, int pageSize) {
        return pageList("Menu.listMenu", menuVO, currPage, pageSize);
    }

    /**
     * 메뉴관리 목록 조회
     */
    public List listMenu(MenuVO menuVO) {
        return list("Menu.listMenu", menuVO);
    }

    /**
     * 메뉴관리 상세 조회
     */
    public MenuVO viewMenu(MenuVO menuVO) {
        return (MenuVO)view("Menu.viewMenu", menuVO);
    }

    /**
     * 메뉴관리 등록
     */
    public int regiMenu(MenuVO menuVO) {
        return insert("Menu.regiMenu", menuVO);
    }

    /**
     * 메뉴관리 수정
     */
    public int updtMenu(MenuVO menuVO) {
        return update("Menu.updtMenu", menuVO);
    }

    /**
     * 메뉴관리 삭제
     */
    public int deltMenu(MenuVO menuVO) {
        return delete("Menu.deltMenu", menuVO);
    }

    /**
     * 하위메뉴 건수조회(중복체크)
     */
    public int confLowerMenu(String menuId) {
    	return (Integer)view("Menu.confLowerMenu", menuId);
    }

    /**
     * 하위메뉴 건수조회(중복체크)
     */
	public List listUnderMenu(MenuVO underMenuVO) {
        return list("Menu.listUnderMenu", underMenuVO);
    }
}