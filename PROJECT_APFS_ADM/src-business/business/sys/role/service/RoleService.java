package business.sys.role.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 역할관리을 관리하는 Service Interface
 * 
 * @class   : RoleService
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface RoleService {

    /**
     * 역할관리 목록조회
     */
    public List listRole(RoleVO roleVO) throws Exception;

    /**
     * 역할관리 상세조회
     */
    public RoleVO viewRole(RoleVO roleVO) throws Exception;

    /**
     * 역할관리 등록,수정,삭제
     */
    public String saveRole(RoleVO roleVO) throws Exception;
 
    /**
     * 권한 페이징리스트 조회
     */
	public List listRole(RoleVO roleVO, int page, int size) throws Exception;
}