package business.sys.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.invest.service.EntService;
import business.com.CommConst;
import business.com.common.service.EmailService;
import business.com.user.service.GroupService;
import business.com.user.service.GroupVO;
import business.sys.role.service.RoleUserService;
import business.sys.role.service.RoleVO;
import business.sys.user.service.UserInfoService;
import business.sys.user.service.UserInfoVO;
import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.user.UserInfo;
import common.util.CommUtils;
import egovframework.com.utl.sim.service.EgovFileScrty;

/**
 * [서비스 클래스] - 사용자정보 조회
 *
 * @class   : UserInfoServiceImpl
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@Service("UserInfoService")
@SuppressWarnings({"all"})
public class UserInfoServiceImpl extends BaseService implements UserInfoService {

    @Resource(name = "UserInfoDAO")
    private UserInfoDAO userInfoDAO;

    @Resource(name = "RoleUserService")
    private RoleUserService roleUserService;
    
    @Autowired
    private EntService entService;
    
    @Resource(name="GroupService")
    protected GroupService groupService;
    
    // 이메일
    @Resource(name = "EmailService")
    private EmailService emailService;

    /**
     * 2021.09.09 LSH 추가
     * 사용자정보 페이징목록조회
     */
    @Override
    public PaginatedArrayList listUserInfo(Map userMap, int currPage, int pageSize) throws Exception {
    	return userInfoDAO.listUserInfo(userMap, currPage, pageSize);
    }

    /**
     * 2021.09.09 LSH 추가
     * 사용자정보 목록조회
     */
    @Override
    public List listUserInfo(Map userMap) throws Exception {
    	return userInfoDAO.listUserInfo(userMap);
    }

    /**
     * 사용자정보 상세조회
     */
	@Override
	public UserInfoVO viewUserInfo(String userNo) throws Exception {
		UserInfoVO userInfoVO = userInfoDAO.viewUserInfo(userNo);
		
		setRoleIdSep(userInfoVO);
		
		return userInfoVO;
	}

    /**
     * 사용자정보 등록
     */
    private int regiUserInfo(UserInfoVO userInfoVO) throws Exception {
        return userInfoDAO.regiUserInfo(userInfoVO);
    }

    /**
     * - 사용자정보 수정
     * - 비밀번호 변경 내용 추가
     */
    private int updtUserInfo(UserInfoVO userInfoVO) throws Exception {

        int res = userInfoDAO.updtUserInfo(userInfoVO);

        // 2022.01.04 CSLEE 수정 (정보 변경과 비밀번호 변경 내용 분리)
        // 수정이 처리됐고 비밀번호 입력 내용이 존재하면 비밀번호 변경 로직 수행
        if( res > 0 && CommUtils.isNotEmpty(userInfoVO.getPswd())) {
            // 비밀번호 변경 처리
            userInfoDAO.updtUserPswd(userInfoVO);
        }
        return res;
    }

    /**
     * 사용자정보 삭제 (비활성 처리)
     */
    private int deltUserInfo(String userNo) throws Exception {
        return userInfoDAO.deltUserInfo(userNo);
    }

    /**
     * 사용자정보 등록,수정,삭제
     * 2023.08.11 LSH 그룹권한 처리로직 추가
     */
	@Override
	public String saveUserInfo(UserInfoVO userInfoVO) throws Exception {

		if (userInfoVO == null)
			throw processException("error.comm.notTarget");

		int ret = 0;
		String mode = userInfoVO.getMode();

		if (CommConst.INSERT.equals(mode)) {
			
			// 사용자정보 등록
			ret = regiUserInfo(userInfoVO);
			// 2023.08.12 LSH 사용중일때만 권한처리
			if (ret > 0 && 
				CodeConst.USE_STATUS_USABLE.equals(userInfoVO.getUseSttsCd())) {
				// 권한 설정
				if (CommUtils.isNotEmpty(CommUtils.nvlTrim(userInfoVO.getAdmRoleId()))) {
					// 관리자 권한 설정
					userInfoVO.setRoleId(CommUtils.nvlTrim(userInfoVO.getAdmRoleId()));
					saveRoleUser(userInfoVO);
				}
				if (CommUtils.isNotEmpty(CommUtils.nvlTrim(userInfoVO.getUsrRoleId()))) {
					// 사용자 권한 설정
					userInfoVO.setRoleId(CommUtils.nvlTrim(userInfoVO.getUsrRoleId()));
					saveRoleUser(userInfoVO);
					// 2023.08.10 LSH 그룹권한등록
					groupService.regiGrpUsr(
						GroupVO.builder()
						.userNo  (userInfoVO.getUserNo  ())
						.gsUserNo(userInfoVO.getUserNo  ())
						.roleId  (userInfoVO.getRoleId  ())
						.bzentyNo(userInfoVO.getBzentyNo())
						.rprsYn  (userInfoVO.getRprsYn  ())
						.build()
					);
				}
			}
		} else if (CommConst.UPDATE.equals(mode)) {

			UserInfo org = userInfoDAO.viewUserInfoById(userInfoVO.getUserId());
			setRoleIdSep(org);

			if (org == null)
				throw processException("error.comm.notTarget");

			// 사용자정보 수정
			ret = updtUserInfo(userInfoVO);

			// 2023.08.12 LSH 사용중일때만 권한처리
			if (ret > 0 && 
				CodeConst.USE_STATUS_USABLE.equals(userInfoVO.getUseSttsCd())) {

				// 관리자 권한이 변경되었다면
				if (!CommUtils.isEqual(
						CommUtils.nvlTrim(org.getAdmRoleId()), 
						CommUtils.nvlTrim(userInfoVO.getAdmRoleId()))) {
					// 해당 사용자의 권한 삭제
					roleUserService.deltAdmRoleUserByUserNo(userInfoVO.getUserNo());
					if (CommUtils.isNotEmpty(userInfoVO.getAdmRoleId())) {
						// 관리자권한 설정
						userInfoVO.setRoleId(userInfoVO.getAdmRoleId());
						saveRoleUser(userInfoVO);
					}
				}
				
				// 그룹권한 처리를 위한 기본 모델
				GroupVO groupVO = GroupVO.builder()
					.userNo  (userInfoVO.getUserNo   ())
					.gsUserNo(userInfoVO.getGsUserNo ())
					.roleId  (userInfoVO.getUsrRoleId())
					.bzentyNo(userInfoVO.getBzentyNo ())
					.rprsYn  (userInfoVO.getRprsYn   ())
					.build   ();
				
				// 사용자 권한이 변경되었다면
				// 2023.08.21 LSH 수정 - NULL처리
				if (!CommUtils.isEqual(
							CommUtils.nvlTrim(org.getUsrRoleId()), 
							CommUtils.nvlTrim(userInfoVO.getUsrRoleId()))) {
					// 1) 기존 그룹권한 삭제
					//    기존 사용자권한 삭제
					if (groupService.resetGrpUsr(userInfoVO.getUserNo()) > 0 &&
						roleUserService.deltUsrRoleUserByUserNo(userInfoVO.getUserNo()) > 0) {
						// 2) 변경 그룹권한 등록
						//    변경 사용자권한 등록 
						if (CommUtils.isNotEmpty(userInfoVO.getUsrRoleId())) {
							// 사용자권한 설정
							userInfoVO.setRoleId(userInfoVO.getUsrRoleId());
							// 사용자권한 등록
							saveRoleUser(userInfoVO);
							// 그룹권한등록
							groupService.regiGrpUsr(groupVO);
						}
					}
				}
				// 사용자 권한이 동일하고 업쳬가 있는 경우
				else if (CommUtils.isNotEmpty(userInfoVO.getUsrRoleId()) &&
						 CommUtils.isNotEmpty(userInfoVO.getBzentyNo())) {
					// 업체가 변경되었을 경우
					if (!CommUtils.isEqual(org.getBzentyNo(), userInfoVO.getBzentyNo())) {
						// 1) 기존 그룹권한 삭제
						groupService.resetGrpUsr(userInfoVO.getUserNo());
						// 2) 현재 그룹권한 등록
						groupService.regiGrpUsr(groupVO);
					}
					// 업체가 동일하고 대표계정이 변경된 경우
					else if (!CommUtils.isEqual(org.getRprsYn(), userInfoVO.getRprsYn())) {
						// 1) 대표계정이면
						if (CommConst.YES.equals(userInfoVO.getRprsYn())) {
							// 2023.09.06 LSH 수정요청에 의해 기본권한을 뷰어(V)에서 수정(M)으로 변경함
							// 해당 수정요청에 의해 그룹권한 변경이 불필요해짐
							// 그룹권한 V(뷰어)로 설정
							//groupVO.setGroupAuthrtCd(CodeConst.GROUP_AUTH_VIEW);
							// 업체의 대표계정의 그룹권한 V(뷰어)로 일괄수정
							//groupService.updtGrpAuthRprs(groupVO);
							// 업체의 대표계정을 멤버계정으로 변경
							groupService.updtGrpUsrReset(groupVO);
							// 현재 계정을 대표로 전환처리
							groupService.updtGrpRprs(groupVO, CommConst.YES);
						}
						// 2) 멤버계정이면
						// TODO 대표계정을 멤버계정으로 전환시 
						//      대표계정이 업체에 하나도 없을 경우가 발생함
						//      따라서, 화면에서 멤버계정 전환은 막아야함.
						else {
							// 현재 계정을 멤버로 전환처리
							groupService.updtGrpRprs(groupVO, CommConst.NO);
						}
					}
				}
			}

		} else if (CommConst.DELETE.equals(mode)) {
			// 사용자정보 삭제 (비활성처리)
			ret = deltUserInfo(userInfoVO.getUserNo());
			if (ret > 0) {
				// 해당 사용자의 권한 삭제
				roleUserService.deltRoleUserByUserNo(userInfoVO.getUserNo());
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

	// 사용자 권한 설정
	private int saveRoleUser(UserInfoVO userInfoVO) throws Exception {
		// 사용자권한 설정
		RoleVO roleVO = RoleVO.builder()
				.userNo(userInfoVO.getUserNo())
				.roleId(userInfoVO.getRoleId())
				.gsUserNo(userInfoVO.getGsUserNo())
				.build();

		// 이미 등록되어 있으면
		if (roleUserService.existRoleUser(roleVO))
			return 0;

		return roleUserService.regiRoleUser(roleVO);
	}

    /**
     * 사용자아이디 중복체크
     */
	@Override
    public boolean existUserId(String userId) throws Exception {
		return userInfoDAO.existUserId(userId);
	}


	/**
	 * 2023.08.12 LSH
	 * 사용자 비밀번호 재설정 및 메일발송 처리
	 * @param userInfoVO.userNo   사용자 번호
	 * @param userInfoVO.gsUserNo 세션사용자 번호
	 */
	@Override
	public String savePswdReset(UserInfoVO userInfoVO) throws Exception {
		
		if (CommUtils.isEmpty(userInfoVO.getUserNo()))
			throw processException("error.comm.notTarget");
		
		// 사용자정보 조회
		UserInfoVO obj = viewUserInfo(userInfoVO.getUserNo());
		if (obj == null)
			throw processException("error.comm.notTarget");
		// 사용자 비활성상태인 경우
		if (!CodeConst.USE_STATUS_USABLE.equals(obj.getUseSttsCd()))
			throw new BusinessException("중지상태의 사용자는 비밀번호를 재설정하실 수 없습니다.");

		// 사용자이름
		String userNm = obj.getUserNm();
		// 사용자ID
		String userId = obj.getUserId();
		// 사용자이메일
		String userEml = obj.getEmlAddr();
		// 비밀번호 임시생성 (8자리)
		String pswd   = CommUtils.getRamdomPassword(8);
		// 비밀번호 암호화처리
		userInfoVO.setPswd( EgovFileScrty.encryptPassword(pswd, userId));
		// 비밀번호 초기화값 DB Update
		if (userInfoDAO.updtUserPswd(userInfoVO) > 0) {

			Map<String,String> params = new HashMap<String,String> ();
			params.put("context"  , userInfoVO.getGsContext());
			params.put("userId"   , userId);
			params.put("userNm"   , userNm);
			params.put("pswd"     , pswd);
			params.put("toName"   , userNm);
			params.put("toAddress", userEml);
			//안녕하세요.<br>[{platform}]입니다.<br><br>
			//{userNm}님의 임시비밀번호는 <b>{pswd}</b>입니다.<br><br>
			//로그인하신 후 반드시 비밀번호를 변경하시기 바랍니다.<br><br>
			//감사합니다.<br>
			//<a href="{homeUrl}" target="_blank">[{platform}으로 이동]</a>

			// 메일발송
			emailService.sendBizEmail(params, CodeConst.BIZMAIL_PASSWD);
	        // 저장결과를 반환한다.
	        return message.getMessage("prompt.success");
		}
		throw processException("error.comm.notProcess");
	}

    /**
     * roleId 값으로 사용자, 관리자 페이지 권한 각각 세팅하는 함수
     * @param UserInfoVO 사용자 정보
     * @return
     */
    public void setRoleIdSep(UserInfoVO userInfoVO) throws Exception {
		String[] roleId = userInfoVO.getRoleId().split(",");
		
		// 관리자, 사용자 페이지 권한 따로 세팅
		for(int i=0 ; i<roleId.length ; i++) {
			String role = roleId[i];
			if (role.indexOf("ROLE_ADM") > -1) {
				userInfoVO.setAdmRoleId(role);
			} else if (role.indexOf("ROLE_USR") > -1) {
				userInfoVO.setUsrRoleId(role);
			}
		}
		
		return;
    }
    
    /**
     * roleId 값으로 사용자, 관리자 페이지 권한 각각 세팅하는 함수
     * @param UserInfoVO 사용자 정보
     * @return
     */
    public void setRoleIdSep(UserInfo userInfo) throws Exception {
		String[] roleId = userInfo.getRoleId().split(",");
		
		// 관리자, 사용자 페이지 권한 따로 세팅
		for(int i=0 ; i<roleId.length ; i++) {
			String role = roleId[i];
			if (role.indexOf("ROLE_ADM") > -1) {
				userInfo.setAdmRoleId(role);
			} else if (role.indexOf("ROLE_USR") > -1) {
				userInfo.setUsrRoleId(role);
			}
		}
		
		return;
    }
}