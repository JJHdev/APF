package business.sys.role.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 역할별사용자관리을 관리하는 Service Interface
 * 
 * @class   : RoleUserService
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface RoleUserService {

    /**
     * 역할별사용자관리 목록조회
     */
    public List listRoleUser(RoleVO roleVO) throws Exception;

    /**
     * 역할별사용자관리 등록,수정,삭제
     */
    public String saveRoleUser(RoleVO roleVO) throws Exception;

    /**
     * 역할별사용자관리 등록
     */
    public int regiRoleUser(RoleVO RoleVO) throws Exception;

    /**
     * 역할별사용자관리 삭제
     */
    public int deltRoleUser(RoleVO RoleVO) throws Exception;
    
    /**
     * 2021.09.17 LSH 추가
     * 역할별사용자 등록여부 확인
     */
    public boolean existRoleUser(RoleVO roleVO) throws Exception;
    
    /**
     * 2021.09.17 LSH 추가
     * 역할별사용자관리 사용자 기준 권한 삭제
     */
    public int deltRoleUserByUserNo(String userNo) throws Exception;
    
    /**
     * 2023.08.07 LHB 추가
     * 역할별사용자관리 사용자 아이디 기준 관리자 권한 삭제
     */
	public int deltAdmRoleUserByUserNo(String userNo) throws Exception;
	
	/**
     * 2023.08.07 LHB 추가
     * 역할별사용자관리 사용자 아이디 기준 사용자 권한 삭제
     */
	public int deltUsrRoleUserByUserNo(String userNo) throws Exception;
    
}