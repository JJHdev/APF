 package business.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 역할관리을 관리하는 DAO 클래스
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
 * @class   : RoleDAO
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("RoleDAO")
@SuppressWarnings({"all"})
public class RoleDAO extends BaseDAO {

    /**
     * 역할관리 목록 조회
     */
    public List listRole(RoleVO roleVO) {
        return list("Role.listRole", roleVO);
    }

    /**
     * 역할관리 상세 조회
     */
    public RoleVO viewRole(RoleVO roleVO) {
        return (RoleVO)view("Role.viewRole", roleVO);
    }

    /**
     * 역할관리 등록
     */
    public int regiRole(RoleVO roleVO) {
        return insert("Role.regiRole", roleVO);
    }

    /**
     * 역할관리 수정
     */
    public int updtRole(RoleVO roleVO) {
        return update("Role.updtRole", roleVO);
    }

    /**
     * 역할관리 삭제
     */
    public int deltRole(RoleVO roleVO) {
        return delete("Role.deltRole", roleVO);
    }

    /**
     * 하위역할 건수조회(중복체크)
     */
    public int confLowerRole(String roleId) {
    	return (Integer)view("Role.confLowerRole", roleId);
    }
    /**
     * 코드페이징리스트
     */
	public PaginatedArrayList listRole(RoleVO roleVO, int page, int size) {
		return pageList("Role.listRole", roleVO, page, size);
	}

}