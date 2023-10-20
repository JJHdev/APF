package business.sys.role.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 역할별프로그램관리을 관리하는 Service Interface
 * 
 * @class   : RoleProgService
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface RoleProgService {

    /**
     * 역할별프로그램관리 목록조회
     */
    public List listRoleProg(RoleVO roleVO) throws Exception;

    /**
     * 미적용 역할별프로그램관리 목록조회
     */
    public List listNotRoleProg(RoleVO roleVO) throws Exception;
    /**
     * 역할별프로그램관리 등록,수정,삭제
     */
    public String saveRoleProg(RoleVO roleVO) throws Exception;

}