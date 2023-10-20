 package business.sys.role.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.sys.role.service.RoleVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 역할별메뉴관리을 관리하는 DAO 클래스
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
 * @class   : RoleMenuDAO
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("RoleMenuDAO")
@SuppressWarnings({"all"})
public class RoleMenuDAO extends BaseDAO {

    /**
     * 역할별메뉴관리 목록 조회
     */
    public List listRoleMenu(RoleVO roleVO) {
        return list("RoleMenu.listRoleMenu", roleVO);
    }
    public List listNotRoleMenu(RoleVO roleVO) {
    	return list("RoleMenu.listNotRoleMenu", roleVO);
    }

    /**
     * 역할별메뉴관리 등록
     */
    public int regiRoleMenu(RoleVO roleVO) {
        return insert("RoleMenu.regiRoleMenu", roleVO);
    }

    /**
     * 역할별메뉴관리 삭제
     */
    public int deltRoleMenu(RoleVO roleVO) {
        return delete("RoleMenu.deltRoleMenu", roleVO);
    }


}