 package business.sys.role.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.sys.role.service.RoleVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 역할별사용자관리을 관리하는 DAO 클래스
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
 * @class   : RoleUserDAO
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("RoleUserDAO")
@SuppressWarnings({"all"})
public class RoleUserDAO extends BaseDAO {

    /**
     * 역할별사용자관리 목록 조회
     */
    public List listRoleUser(RoleVO roleVO) {
        return list("RoleUser.listRoleUser", roleVO);
    }

    /**
     * 역할별사용자관리 등록
     */
    public int regiRoleUser(RoleVO roleVO) {
        return insert("RoleUser.regiRoleUser", roleVO);
    }

    /**
     * 역할별사용자관리 삭제
     */
    public int deltRoleUser(RoleVO roleVO) {
        return delete("RoleUser.deltRoleUser", roleVO);
    }
    
    /**
     * 2021.09.17 LSH 추가
     * 역할별사용자 등록여부 확인
     */
    public boolean existRoleUser(RoleVO roleVO) {
        return (Integer)view("RoleUser.existRoleUser", roleVO) > 0;
    }
    
    /**
     * 2021.09.17 LSH 추가
     * 역할별사용자관리 사용자 기준 권한 삭제
     */
    public int deltRoleUserByUserNo(String userNo) {
        return delete("RoleUser.deltRoleUserByUserNo", userNo);
    }
    
    /**
     * 2023.08.07 LHB 추가
     * 역할별사용자관리 사용자 아이디 기준 관리자 권한 삭제
     */
    public int deltAdmRoleUserByUserNo(String userNo) {
        return delete("RoleUser.deltAdmRoleUserByUserNo", userNo);
    }
    
    /**
     * 2023.08.07 LHB 추가
     * 역할별사용자관리 사용자 아이디 기준 사용자 권한 삭제
     */
    public int deltUsrRoleUserByUserNo(String userNo) {
        return delete("RoleUser.deltUsrRoleUserByUserNo", userNo);
    }
}