package business.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.code.service.CodeVO;
import business.sys.role.service.RoleService;
import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 역할관리을 관리하는 Service 구현 클래스
 * 
 * @class   : RoleServiceImpl
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("RoleService")
@SuppressWarnings({"all"})
public class RoleServiceImpl extends BaseService implements RoleService {

    @Resource(name = "RoleDAO")
    private RoleDAO roleDAO;

    /**
     * 역할관리 목록조회
     */
    @Override
    public List listRole(RoleVO roleVO) throws Exception {
    	return roleDAO.listRole(roleVO);
    }

    /**
     * 역할관리 상세조회
     */
	@Override
	public RoleVO viewRole(RoleVO roleVO) throws Exception {
		return roleDAO.viewRole(roleVO);
	}

    /**
     * 역할관리 등록
     */
    private int regiRole(RoleVO roleVO) throws Exception {
        return roleDAO.regiRole(roleVO);
    }

    /**
     * 역할관리 수정
     */
    private int updtRole(RoleVO roleVO) throws Exception {
        return roleDAO.updtRole(roleVO);
    }

    /**
     * 역할관리 삭제
     */
    private int deltRole(RoleVO roleVO) throws Exception {
        return roleDAO.deltRole(roleVO);
    }

    /**
     * 역할관리 등록,수정,삭제
     */
	@Override
	public String saveRole(RoleVO roleVO) throws Exception {
		
		if (roleVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode   = roleVO.getMode();
		String roleId = roleVO.getRoleId();
		String[] args = { roleId };
		
		if (CommConst.UPDATE.equals(mode)) {
			// 역할관리 수정
			ret = updtRole(roleVO);
		}
		else if (CommConst.INSERT.equals(mode)) {

			// 역할ID 중복체크
			if (viewRole(roleVO) != null) {
				// [{0}] 역할은 이미 등록되어 있습니다.
				throw processException("exception.adm.duplRole", args);
			}
			// 역할관리 등록
			ret = regiRole(roleVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			
			// 하위역할 존재여부 확인
			if (roleDAO.confLowerRole(roleId) > 0) {
				// [{0}] 역할은 하위 역할이 존재하여 삭제할 수 없습니다.
				throw processException("exception.adm.hasLowerRole", args);
			}
			// 역할관리 삭제
			ret = deltRole(roleVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
    /**
     * 코드페이징리스트
     */
	@Override
	public PaginatedArrayList listRole(RoleVO roleVO, int page, int size) throws Exception {
		return roleDAO.listRole(roleVO, page, size);
	}
}