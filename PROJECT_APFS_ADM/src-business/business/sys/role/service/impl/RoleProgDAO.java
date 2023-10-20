 package business.sys.role.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.sys.role.service.RoleVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 역할별프로그램관리을 관리하는 DAO 클래스
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
 * @class   : RoleProgDAO
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("RoleProgDAO")
@SuppressWarnings({"all"})
public class RoleProgDAO extends BaseDAO {

    /**
     * 역할별프로그램관리 목록 조회
     */
    public List listRoleProg(RoleVO roleVO) {
        return list("RoleProg.listRoleProg", roleVO);
    }

    /**
     * 미적용 역할별프로그램관리 목록 조회
     */
    public List listNotRoleProg(RoleVO roleVO) {
    	return list("RoleProg.listNotRoleProg", roleVO);
    }
    /**
     * 역할별프로그램관리 등록
     */
    public int regiRoleProg(RoleVO roleVO) {
        return insert("RoleProg.regiRoleProg", roleVO);
    }

    /**
     * 역할별프로그램관리 삭제
     */
    public int deltRoleProg(RoleVO roleVO) {
        return delete("RoleProg.deltRoleProg", roleVO);
    }


}