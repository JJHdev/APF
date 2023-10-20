package business.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.role.service.RoleMenuService;
import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 역할별메뉴관리을 관리하는 Service 구현 클래스
 * 
 * @class   : RoleMenuServiceImpl
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("RoleMenuService")
@SuppressWarnings({"all"})
public class RoleMenuServiceImpl extends BaseService implements RoleMenuService {

    @Resource(name = "RoleMenuDAO")
    private RoleMenuDAO roleMenuDAO;

    /**
     * 역할별메뉴관리 목록조회
     */
    @Override
    public List listRoleMenu(RoleVO roleVO) throws Exception {
    	return roleMenuDAO.listRoleMenu(roleVO);
    }

    /**
     * 권한 미적용 역할별메뉴관리 목록조회
     */
	@Override
	public List listNotRoleMenu(RoleVO roleVO) throws Exception {
		return roleMenuDAO.listNotRoleMenu(roleVO);
	}

    /**
     * 역할별메뉴관리 등록
     */
    private int regiRoleMenu(RoleVO roleVO) throws Exception {
        return roleMenuDAO.regiRoleMenu(roleVO);
    }

    /**
     * 역할별메뉴관리 삭제
     */
    private int deltRoleMenu(RoleVO roleVO) throws Exception {
        return roleMenuDAO.deltRoleMenu(roleVO);
    }

    /**
     * 역할별메뉴관리 등록,삭제 (다중목록을 처리한다.)
     */
	@Override
	public String saveRoleMenu(RoleVO roleVO) throws Exception {
		
		if (roleVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = roleVO.getMode();
		List<RoleVO> rows = roleVO.getRoleList();
		
		if (rows == null || rows.size() == 0)
			throw processException("error.comm.notTarget");
		
		if (CommConst.INSERT.equals(mode)) {
			for (RoleVO row : rows) {
				row.setRoleId(roleVO.getRoleId());
				row.setGsUserNo(roleVO.getGsUserNo());
				// 역할별메뉴관리 등록
				ret += regiRoleMenu(row);
			}
		}
		else if (CommConst.DELETE.equals(mode)) {
			for (RoleVO row : rows) {
				// 역할별메뉴관리 삭제
				ret += deltRoleMenu(row);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

}