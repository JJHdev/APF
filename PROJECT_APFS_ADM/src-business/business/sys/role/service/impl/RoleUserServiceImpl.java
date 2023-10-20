package business.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.role.service.RoleUserService;
import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 역할별사용자관리을 관리하는 Service 구현 클래스
 * 
 * @class   : RoleUserServiceImpl
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("RoleUserService")
@SuppressWarnings({"all"})
public class RoleUserServiceImpl extends BaseService implements RoleUserService {

    @Resource(name = "RoleUserDAO")
    private RoleUserDAO roleUserDAO;

    /**
     * 역할별사용자관리 목록조회
     */
    @Override
    public List listRoleUser(RoleVO RoleVO) throws Exception {
    	return roleUserDAO.listRoleUser(RoleVO);
    }

    /**
     * 역할별사용자관리 등록
     */
	@Override
    public int regiRoleUser(RoleVO RoleVO) throws Exception {
        return roleUserDAO.regiRoleUser(RoleVO);
    }

    /**
     * 역할별사용자관리 삭제
     */
	@Override
    public int deltRoleUser(RoleVO RoleVO) throws Exception {
        return roleUserDAO.deltRoleUser(RoleVO);
    }

    /**
     * 역할별사용자관리 등록,삭제 (다중목록을 처리한다.)
     */
	@Override
	public String saveRoleUser(RoleVO roleVO) throws Exception {
		
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
				// 역할별사용자관리 등록
				ret += regiRoleUser(row);
			}
		}
		else if (CommConst.DELETE.equals(mode)) {
			for (RoleVO row : rows) {
				// 역할별사용자관리 삭제
				ret += deltRoleUser(row);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
    
    /**
     * 2021.09.17 LSH 추가
     * 역할별사용자 등록여부 확인
     */
	@Override
    public boolean existRoleUser(RoleVO roleVO) throws Exception {
    	return roleUserDAO.existRoleUser(roleVO);
    }

    /**
     * 2021.09.17 LSH 추가
     * 역할별사용자관리 사용자 기준 권한 삭제
     */
	@Override
	public int deltRoleUserByUserNo(String userNo) throws Exception {
    	return roleUserDAO.deltRoleUserByUserNo(userNo);
	}
	
	/**
     * 2023.08.07 LHB 추가
     * 역할별사용자관리 사용자 아이디 기준 관리자 권한 삭제
     */
	@Override
	public int deltAdmRoleUserByUserNo(String userNo) throws Exception {
    	return roleUserDAO.deltAdmRoleUserByUserNo(userNo);
	}
	
	/**
     * 2023.08.07 LHB 추가
     * 역할별사용자관리 사용자 아이디 기준 사용자 권한 삭제
     */
	@Override
	public int deltUsrRoleUserByUserNo(String userNo) throws Exception {
    	return roleUserDAO.deltUsrRoleUserByUserNo(userNo);
	}
	
}