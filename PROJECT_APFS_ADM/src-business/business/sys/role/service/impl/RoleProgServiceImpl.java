package business.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.role.service.RoleProgService;
import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 역할별프로그램관리을 관리하는 Service 구현 클래스
 * 
 * @class   : RoleProgServiceImpl
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("RoleProgService")
@SuppressWarnings({"all"})
public class RoleProgServiceImpl extends BaseService implements RoleProgService {

    @Resource(name = "RoleProgDAO")
    private RoleProgDAO roleProgDAO;

    /**
     * 역할별프로그램관리 목록조회
     */
    @Override
    public List listRoleProg(RoleVO roleVO) throws Exception {
    	return roleProgDAO.listRoleProg(roleVO);
    }

    /**
     * 미적용 역할별프로그램관리 목록조회
     */
	@Override
	public List listNotRoleProg(RoleVO roleVO) throws Exception {
		return roleProgDAO.listNotRoleProg(roleVO);
	}

    /**
     * 역할별프로그램관리 등록
     */
    private int regiRoleProg(RoleVO roleVO) throws Exception {
        return roleProgDAO.regiRoleProg(roleVO);
    }

    /**
     * 역할별프로그램관리 삭제
     */
    private int deltRoleProg(RoleVO roleVO) throws Exception {
        return roleProgDAO.deltRoleProg(roleVO);
    }

    /**
     * 역할별프로그램관리 등록,삭제 (다중목록을 처리한다.)
     */
	@Override
	public String saveRoleProg(RoleVO roleVO) throws Exception {
		
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
				// 역할별프로그램관리 등록
				ret += regiRoleProg(row);
			}
		}
		else if (CommConst.DELETE.equals(mode)) {
			for (RoleVO row : rows) {
				// 역할별프로그램관리 삭제
				ret += deltRoleProg(row);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

}