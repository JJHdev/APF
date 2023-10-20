package business.com.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.com.user.service.GroupService;
import business.com.user.service.GroupVO;
import business.com.user.service.UserService;
import business.usr.CodeConst;
import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 그룹관리 Service 구현 클래스
 * 
 * @class   : GroupServiceImpl
 * @author  : LSH
 * @since   : 2023.06.14
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("GroupService")
@SuppressWarnings({"all"})
public class GroupServiceImpl extends BaseService implements GroupService {

    @Resource(name = "GroupDAO")
    private GroupDAO groupDAO;
    
    @Resource(name = "UserService")
    private UserService userService;

    /**
     * 마이페이지 메뉴 목록 조회
     * @param params.gsRoleId 세션사용자 권한ID
     * @param params.gsUserNo 세션사용자 번호
     * @param params.gsRprsYn 세션사용자 대표여부
     */
	@Override
    public List listMypageMenu(Map params) throws Exception {
    	return groupDAO.listMypageMenu(params);
    }

    /**
     * 마이페이지 메뉴별 접근가능여부
     * @param params.userNo   사용자번호
     * @param params.roleId   권한ID
     * @param params.menuId   메뉴ID
     * @param params.saveMode 수정모드여부
     * 
	 * 그룹권한적용사항
	 * 1) 일반회원/관리자/유관기관 : SKIP
	 * 2) 투자자/경영체
	 *    - 메뉴권한정보 조회
	 *    - 권한여부 확인
     */
	@Override
    public boolean access(
    		String userNo, 
    		String roleId, 
    		String menuId, 
    		boolean saveMode) throws BusinessException {
		if (!CommConst.isEntRole(roleId) &&
			!CommConst.isInvRole(roleId))
			return true;
    	
		String authCd = getGrpAuthCd(userNo, menuId);

    	if (authCd == null)
    		return true;
    	
    	boolean ret = false;
    	if (saveMode) {
        	// 수정권한 여부
        	ret = CodeConst.isGroupEditable(authCd);
    	}
    	else {
        	// 조회권한 여부
        	ret = CodeConst.isGroupViewable(authCd);
    	}
    	if (!ret)
    		throw new BusinessException(message.getMessage("error.comm.noAuthTask"));
    	
    	return ret;
    }
	

    /**
     * 마이페이지 메뉴별 접근권한코드
     * @param params.userNo   사용자번호
     * @param params.menuId   메뉴ID
     */
	@Override
    public String getGrpAuthCd(
    		String userNo, 
    		String menuId) throws BusinessException {
    	
		return groupDAO.getGrpAuthCd(
    			GroupVO.builder()
	    			.userNo(userNo)
	    			.menuId(menuId)
	    			.build()
    			);
    }
	
    /**
     * 멤버관리/권한관리 페이징목록 조회
     */
	@Override
    public PaginatedArrayList listGroup(GroupVO groupVO, int currPage, int pageSize) throws Exception {
    	return groupDAO.listGroup(groupVO, currPage, pageSize);
    }

    /**
     * 멤버관리/권한관리 목록 조회
     */
	@Override
    public List listGroup(GroupVO groupVO) throws Exception {
    	return groupDAO.listGroup(groupVO);
    }

    /**
     * 세션사용자의 메뉴ID 기준 접근권한 확인
     * @param groupVO.userNo 세션사용자 번호
     * @param groupVO.menuId 접근화면의 메뉴ID
     */
	@Override
    public boolean checkAccess(GroupVO groupVO) throws Exception {
    	return groupDAO.checkAccess(groupVO);
    }

    /**
     * 그룹코드 중복 확인 (중복된 값이 없는 경우 true 반환)
     */
	@Override
    public boolean uniqueGroupCd(String groupCd) throws Exception {
    	return groupDAO.uniqueGroupCd(groupCd);
    }

    /**
     * 대표계정여부 확인
     * 
     * @param userNo 세션사용자 번호
     */
	@Override
    public String getGrpRprsYn(String userNo) throws Exception {
    	return groupDAO.getGrpRprsYn(userNo);
    }

	/**
	 * 그룹관리 저장처리
     * 
     * 마이페이지 - 그룹코드 - 그룹코드 저장처리
     * @param groupVO.mode     : 수정모드(U)
     * @param groupVO.act      : 행위구분(그룹코드:CODE)
     * @param groupVO.saveGroupCd: 그룹코드
	 * 
     * 마이페이지 - 멤버관리 - 삭제처리
     * @param groupVO.mode     : 삭제모드(D)
     * @param groupVO.saveList : 삭제목록(userNo)
     * 
     * 마이페이지 - 멤버관리/권한관리 - 대표계정 변경처리
     * @param groupVO.mode     : 수정모드(U)
     * @param groupVO.act      : 행위구분(대표변경:RPRS)
     * @param groupVO.userNo   : 대표사용자번호
     * 
     * 마이페이지 - 권한관리 - 권한변경처리
     * @param groupVO.mode     : 수정모드(U)
     * @param groupVO.act      : 행위구분(권한변경:ROLE)
     * @param groupVO.saveList : 권한목록
	 */
	@Override
	public String saveGroup(GroupVO groupVO) throws Exception {
		
		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = groupVO.getMode();
		String act  = groupVO.getAct ();
		List<GroupVO> saveList = groupVO.getSaveList();
		
		// 수정처리
		if (CommConst.UPDATE.equals(mode)) {
        	// 그룹코드저장인 경우
        	if (CommConst.ACT_GROUP_CODE.equals(act)) {
        		// 그룹코드 저장처리
				if (saveGrpCode(groupVO) <= 0)
					throw processException("error.group.code.updateError");
        	}
        	// 대표계정변경인 경우
        	else if (CommConst.ACT_GROUP_RPRS.equals(act)) {
        		// 대표계정 변경처리
				if (saveGrpRprs(groupVO) <= 0)
					throw processException("error.group.rprs.updateError");
        	}
        	// 그룹권한저장인 경우
        	else if (CommConst.ACT_GROUP_ROLE.equals(act)) {
    			for (GroupVO data : saveList) {
    				// 그룹권한 저장처리
    				if (saveGrpAuth(data) <= 0)
    					throw processException("error.group.role.updateError");
    			}
        	}
		}
		// 삭제처리
		else if (CommConst.DELETE.equals(mode)) {
			
			for (GroupVO data : saveList) {
				// 그룹사용자 삭제처리
				if (deltGrpUsr(data) <= 0)
					throw processException("error.group.deleteError");
			}
		}
		else {
			throw processException("exception.proc.err");
		}
        // 저장결과를 반환한다.
        return message.getMessage("prompt.success");
	}

    /**
     * 마이페이지 - 권한관리 - 권한변경처리
     * 
     * @param groupVO.gsUserNo      세션사용자번호
     * @param groupVO.userNo        사용자번호
     * @param groupVO.menuId        메뉴ID
     * @param groupVO.groupAuthrtCd 그룹권한
     */
	@Override
	public int saveGrpAuth(GroupVO groupVO) throws Exception {

		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		// 권한정보가 있는경우 수정처리
		if (groupDAO.existGrpAuth(groupVO)) {
			return groupDAO.updtGrpAuth(groupVO);
		}
		// 권한정보가 없으면 등록처리
		else {
			return groupDAO.regiGrpAuth(groupVO);
		}
	}

    /**
     * [그룹관리] 대표계정 변경처리
     * 
     * 1) 이전 대표계정에 대한 처리
     * - 권한있는 메뉴의 그룹권한을 V(뷰어)로 일괄수정
     * - 2023.09.06 LSH 수정요청에 의해 기본권한을 뷰어(V)에서 수정(M)으로 변경함
     * - 해당 수정요청에 따라 그룹권한 변경이 불필요해짐
     * 
     * 2) 신규 대표계정에 대한 처리
     * - 권한있는 메뉴의 그룹권한을 M(수정)로 일괄수정
     * 
     * @param groupVO.gsUserNo   세션사용자번호 (기존대표계정)
     * @param groupVO.gsBzentyNo 세션업체번호
     * @param groupVO.userNo     대표사용자번호 (신규대표계정)
     */
	@Override
	public int saveGrpRprs(GroupVO groupVO) throws Exception {

		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		GroupVO org = GroupVO.builder()
				.gsBzentyNo   (groupVO.getGsBzentyNo())
				.gsUserNo     (groupVO.getGsUserNo())
				.groupAuthrtCd(CodeConst.GROUP_AUTH_MODIFY)
				.build        ();

		// 이전 대표계정의 그룹권한 V(뷰어)로 일괄수정
		// 이전 대표계정을 멤버계정으로 번환처리
		// 2023.09.06 LSH 수정요청에 따라 그룹권한 변경이 불필요해짐
		//if (groupDAO.updtGrpAuthRprs(org) > 0 &&
		//	groupDAO.updtGrpUsrReset(org) > 0) {
		if (groupDAO.updtGrpUsrReset(org) > 0) {
			// 대표계정 설정처리
			return updtGrpRprs(groupVO, CommConst.YES);
		}
		throw processException("error.group.rprs.updateError");
	}

    /**
     * 2023.08.11 LSH
     * [그룹관리] 해당 업체의 모든 사용자의 계정을 멤버계정으로 초기화 처리
     * 
     * @param groupVO.userNo     대상사용자번호     
     * @param groupVO.gsUserNo   세션사용자번호
     * @param groupVO.gsBzentyNo 대상업체번호
     */
	@Override
	public int updtGrpUsrReset(GroupVO groupVO) throws Exception {
		return groupDAO.updtGrpUsrReset(groupVO);
	}

    /**
     * 2023.08.12 LSH
     * 업체회원의 대표계정 권한 일괄 수정
     * 
     * @param groupVO.gsUserNo      세션사용자번호
     * @param groupVO.gsBzentyNo    세션업체번호
     * @param groupVO.groupAuthrtCd 그룹권한
     */
    public int updtGrpAuthRprs(GroupVO groupVO) throws Exception {
		return groupDAO.updtGrpAuthRprs(groupVO);
	}

    /**
     * 2023.08.11 LSH
     * [그룹관리] 일반계정/대표계정으로 전환처리
     * 
     * 1) 해당 사용자를 일반계정으로 전환
     * 2) 해당 사용자의 그룹권한을 V(뷰어)로 수정
     * 2023.09.06 LSH 수정요청에 의해 기본권한을 뷰어(V)에서 수정(M)으로 변경함
     * 
     * @param groupVO.userNo     대상사용자번호     
     * @param groupVO.gsUserNo   세션사용자번호
     * @param rprsYn             대표계정여부 (Y/N)
     */
	@Override
	public int updtGrpRprs(GroupVO groupVO, String rprsYn) throws Exception {

		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		// 2023.09.06 LSH 수정요청에 의해 기본권한을 뷰어(V)에서 수정(M)으로 변경함
		String authCd = CodeConst.GROUP_AUTH_MODIFY;
		//String authCd = CodeConst.GROUP_AUTH_VIEW;
		//if (CommConst.YES.equals(rprsYn))
		//	authCd = CodeConst.GROUP_AUTH_MODIFY;
		
		GroupVO obj = GroupVO.builder()
			.gsUserNo     (groupVO.getGsUserNo())
			.userNo       (groupVO.getUserNo())
			.rprsYn       (rprsYn)
			.groupAuthrtCd(authCd)
			.build        ();
		
		// 일반계정/대표계정으로 전환
		// 그룹권한 일괄수정
		if (groupDAO.updtGrpUsr(obj) > 0 &&
			groupDAO.updtGrpAuthByUser(obj) > 0) {
			return 1;
		}
		throw processException("error.group.rprs.updateError");
	}

    /**
     * 2023.08.11 LSH
     * [그룹관리] 그룹사용자 초기화처리
     * : 해당 사용자의 권한있는 메뉴 삭제
     * : 해당 사용자의 그룹사용자 삭제
     * 
     * @param userNo  삭제대상 사용자번호
     */
	@Override
	public int resetGrpUsr(String userNo) throws Exception {

		if (userNo == null)
			throw processException("error.comm.notTarget");
		
		GroupVO groupVO = GroupVO.builder().userNo(userNo).build();

		if (groupDAO.deltGrpAuthByUser(groupVO) > 0 &&
			groupDAO.deltGrpUsr(groupVO) > 0) {
			return 1;
		}
		throw processException("error.group.deleteError");
	}

    /**
     * [그룹관리] 그룹사용자 삭제처리 (일반회원으로 변경)
     * : 해당 사용자의 권한있는 메뉴 삭제
     * : 해당 사용자의 업체번호 빈값 업데이트
     * : 해당 사용자의 권한을 일반회원으로 권한변경처리
     * : 해당 사용자의 일반회원 기준 권한 등록
     * : 실제 삭제되지 않으며 권한만 변경된다.
     * 
     * @param groupVO.gsUserNo   세션사용자 번호
     * @param groupVO.gsRoleId   세션사용자 권한
     * @param groupVO.gsBzentyNo 세션사용자 업체번호
     * @param groupVO.userNo     삭제대상 사용자번호
     */
	@Override
	public int deltGrpUsr(GroupVO groupVO) throws Exception {

		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		// 해당 사용자의 권한있는 메뉴 일괄삭제
	    // 해당 사용자의 업체번호 빈값 업데이트
		Map<String,String> params = new HashMap<String,String>();
		params.put("gsUserNo", groupVO.getGsUserNo());
		params.put("userNo"  , groupVO.getUserNo());
		
		// 해당 사용자의 권한있는 메뉴 일괄삭제
	    // 해당 사용자의 업체번호 빈값 업데이트
		if (groupDAO.deltGrpAuthByUser(groupVO) > 0 &&
			userService.updtUserBzentyReset(params) > 0) {

			params.put("roleId"   , groupVO.getGsRoleId());
			params.put("newRoleId", CommConst.USER_ROLE_USR); // 일반사용자권한
			// 해당 사용자의 권한을 일반회원으로 권한변경처리
			if (userService.updtUserBzentyRole(params) > 0) {
				// 일반회원 권한ID 정의
				groupVO.setRoleId(CommConst.USER_ROLE_USR);
				// 2023.08.03 LSH 일반회원의 그룹권한은 M(수정)으로 설정해야 한다.
				// 일반회원 그룹권한 정의: M(수정) 
				groupVO.setGroupAuthrtCd(CodeConst.GROUP_AUTH_MODIFY);
				// 해당 사용자의 일반회원 기준 권한 등록
				groupDAO.regiGrpAuthByRole(groupVO);
				return 1;
			}
		}
		throw processException("error.group.deleteError");
	}

    /**
     * [회원가입] 그룹사용자 신규등록
     * : 해당 그룹에 사용자가 있는지 확인 후 대표여부를 설정한다.
     * : 해당 사용자의 그룹사용자 등록
     * : 해당 사용자의 그룹권한 등록
     * 
     * @param groupVO.bzentyNo   업체번호
     * @param groupVO.userNo     사용자번호
     * @param groupVO.roleId     권한ID
     * @param groupVO.rprsYn     대표계정여부 (있을경우에만 사용)
     * @param groupVO.gsUserNo   세션사용자 번호
     */
	@Override
	public int regiGrpUsr(GroupVO groupVO) throws Exception {
		
		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		// 대표설정 
		String rprsYn = CommConst.NO;
		// 그룹권한
		// 일반그룹권한: V(뷰어)
		// 2023.09.06 LSH 수정요청에 의해 기본권한을 뷰어(V)에서 수정(M)으로 변경함
		//String authCd = CodeConst.GROUP_AUTH_VIEW;  
		String authCd = CodeConst.GROUP_AUTH_MODIFY;

		// 2023.08.03 LSH 일반회원의 그룹권한은 M(수정)으로 설정해야 한다.
		if (CommConst.isUsrRole(groupVO.getRoleId()))
			authCd = CodeConst.GROUP_AUTH_MODIFY; 
		
		// 2023.08.10 LSH 대표계정여부가 있을경우 해당 설정을 사용한다.
		if (CommUtils.isNotEmpty(groupVO.getRprsYn())) {
			rprsYn = groupVO.getRprsYn();
			if (CommConst.isUsrRole(groupVO.getRoleId()))
				authCd = CodeConst.GROUP_AUTH_MODIFY;
			else if (CommConst.YES.equals(rprsYn))
				authCd = CodeConst.GROUP_AUTH_MODIFY;
		}
		else {
			if (CommUtils.isNotEmpty(groupVO.getBzentyNo()) &&
				!groupDAO.existGrpUsr(groupVO.getBzentyNo())) {
				// 그룹에 사용자가 없으면 대표
				rprsYn = CommConst.YES; 
				// 대표그룹권한: M(수정)
				authCd = CodeConst.GROUP_AUTH_MODIFY; 
			}
		}
		// 대표여부 설정
		groupVO.setRprsYn(rprsYn);
		// 그룹권한 설정
		groupVO.setGroupAuthrtCd(authCd);
		// 대표인 경우 이전 대표를 멤버로 변경처리
		if (CommConst.YES.equals(rprsYn)) {
			GroupVO org = GroupVO.builder()
					.gsBzentyNo   (groupVO.getBzentyNo())
					.gsUserNo     (groupVO.getGsUserNo())
					.groupAuthrtCd(CodeConst.GROUP_AUTH_VIEW)
					.build        ();
			// 업체의 대표계정의 그룹권한 V(뷰어)로 일괄수정
			groupDAO.updtGrpAuthRprs(org);
			// 업체의 대표계정을 비활성처리 한다.
			groupDAO.updtGrpUsrReset(org);
		}
		// 그룹 사용자 등록
		if (groupDAO.regiGrpUsr(groupVO) > 0) {
			// 해당 사용자의 권한 일괄 등록
			return groupDAO.regiGrpAuthByRole(groupVO);
		}
		throw processException("error.group.registError");
	}

    /**
     * 그룹코드 저장처리
     * 
     * @param groupVO.gsUserNo    세션사용자 번호
     * @param groupVO.gsBzentyNo  세션사용자 업체번호
     * @param groupVO.saveGroupCd 그룹코드
     */
	@Override
	public int saveGrpCode(GroupVO groupVO) throws Exception {

		if (groupVO == null)
			throw processException("error.comm.notTarget");
		
		return updtGroupCd(
				GroupVO.builder()
					.bzentyNo(groupVO.getGsBzentyNo ())
					.groupCd (groupVO.getSaveGroupCd())
					.gsUserNo(groupVO.getGsUserNo   ())
					.build   ()
		);
	}

    /**
     * 업체의 그룹코드 수정
     * 마이페이지 - 그룹관리 에서 사용됨
     * 2023.08.04 LSH EntService에서 이동함
     * 
     * @param entVO.bzentyNo  업체번호
     * @param entVO.groupCd   그룹코드
     * @param entVO.gsUserNo  세션사용자번호
     */
	@Override
	public int updtGroupCd(GroupVO groupVO) throws Exception {
		return groupDAO.updtGroupCd(groupVO);
	}
	

    /**
	 * 2023.09.05 LSH
     * 그룹사용자 리셋 및 재등록처리 (대표계정에 한함)
     * 
     * @param groupVO.gsUserNo   사용자번호
     * @param groupVO.userNo     사용자번호
     * @param groupVO.roleId     변경권한ID
     */
	@Override
	public int updtGrpRprs(String userNo, String roleId) throws Exception {

		GroupVO groupVO = GroupVO.builder()
			.gsUserNo(userNo)
			.userNo  (userNo)
			.roleId  (roleId)
			.build();		
		
		// 해당 사용자의 권한있는 메뉴 일괄삭제
		if (groupDAO.deltGrpAuthByUser(groupVO) > 0) {
			// 대표계정 권한정의 
			groupVO.setGroupAuthrtCd(CodeConst.GROUP_AUTH_MODIFY);
			// 권한ID 기준 권한 등록
			groupDAO.regiGrpAuthByRole(groupVO);
			return 1;
		}
		throw processException("error.group.deleteError");
	}
}