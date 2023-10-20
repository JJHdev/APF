package business.sys.role.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 역할별메뉴관리을 관리하는 Service Interface
 * 
 * @class   : RoleMenuService
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface RoleMenuService {

    /**
     * 역할별메뉴관리 목록조회
     */
    public List listRoleMenu(RoleVO roleVO) throws Exception;

    /**
     * 권한 미적용 역할별메뉴관리 목록조회
     */
    public List listNotRoleMenu(RoleVO roleVO) throws Exception;
    /**
     * 역할별메뉴관리 등록,수정,삭제
     */
    public String saveRoleMenu(RoleVO roleVO) throws Exception;

}