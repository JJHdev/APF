 package business.usr.inform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import common.base.BaseDAO;

/**
 * [DAO클래스] - 검색어를 관리하는 DAO 클래스
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
 * @class   : SearchDAO
 * @author  : KYW
 * @since   : 2023.08.01
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  -------    --------    ---------------------------
 * 
 */

@Repository("SearchDAO")
@SuppressWarnings({"all"})
public class SearchDAO extends BaseDAO {
	
	/**
	 * 인기검색어 조회
	 */
	public List listSrchWrd(HashMap params) {
		return list("Search.listSrchWrd", params);	
	}
	
	/**
	 * 검색 시 저장
	 */
	public int regiSrchWrd(HashMap params) {
		return save("Search.regiSrchWrd", params);
	}
	
	/**
     * 마이페이지 메뉴 목록 조회
     */
    public List listMypageMenu(Map params) {
        return list("Search.listSearchMenu", params);
    }
	
	
}