package business.com.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.com.common.service.EmailService;
import business.com.user.service.GroupService;
import business.com.user.service.GroupVO;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.adm.CodeConst;
import business.adm.invest.service.EntService;
import business.adm.invest.service.EntVO;
import commf.exception.BusinessException;
import common.base.BaseService;
import common.user.UserInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 회원관리 Service 구현 클래스
 * 
 * @class   : UserServiceImpl
 * @author  : LSH
 * @since   : 2023.03.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("UserService")
@SuppressWarnings({"all"})
public class UserServiceImpl extends BaseService implements UserService {

    @Resource(name = "UserDAO")
    private UserDAO userDAO;

    @Resource(name = "GroupService")
    private GroupService groupService;

    @Resource(name="EntService")
    protected EntService entService;

    @Resource(name = "EmailService")
    private EmailService emailService;

	/**
	 * 개인회원 회원가입 저장처리
	 */
	@Override
	public int saveJoin(UserVO userVO) throws Exception {
		// 사용자정보 저장처리
		if (userDAO.regiUser(userVO) > 0) {
			// 일반회원 권한 저장처리
			userDAO.regiRole(userVO);
			// 일반회원 그룹관리 권한등록
			groupService.regiGrpUsr(
				GroupVO.builder()
				.userNo  (userVO.getUserNo  ())
				.gsUserNo(userVO.getUserNo  ())
				.roleId  (userVO.getRoleId  ())
				.build()
			);
			return 1;
		}
		return 0;
	}

	/**
	 * 기업회원 회원가입 저장처리
	 */
	@Override
	public int saveJoinEnt(UserVO userVO, EntVO entVO) throws Exception {

		// 사용자정보 저장처리
		if (userDAO.regiUser(userVO) > 0) {

			// 회원정보 - 세션사용자번호 맵핑
			userVO.setGsUserNo(userVO.getUserNo());
			// 기업정보 - 세션사용자번호 맵핑
			entVO.setGsUserNo(userVO.getUserNo());
			// 시업정보 사용자번호 맵핑
			entVO.setUserNo(userVO.getUserNo());

			// 권한 저장처리
			if (userDAO.regiRole(userVO) == 0)
				throw processException("exception.proc.err");
			
			// 기업정보 등록처리
			entService.saveEnt(entVO);

			// 업체번호 맵핑
			userVO.setBzentyNo(entVO.getBzentyNo());
			// 사용자의 업체번호 업데이트
			if (updtUserBzenty(userVO) == 0)
				throw processException("exception.proc.err");
			
			// 그룹관리 권한등록
			groupService.regiGrpUsr(
				GroupVO.builder()
				.userNo  (userVO.getUserNo  ())
				.gsUserNo(userVO.getUserNo  ())
				.roleId  (userVO.getRoleId  ())
				.bzentyNo(userVO.getBzentyNo())
				.build()
			);
			
			// 기업의 멤버회원인 경우 메일발송처리
			if (CommConst.YES.equals(entVO.getMemberYn()))
				_sendMail(userVO, entVO);
			
			return 1;
		}
		return 0;
	}
	
	/**
	 * 기업 멤버회원 가입시 메일발송 처리
	 */
	private void _sendMail(UserVO userVO, EntVO entVO) throws Exception {

		// 대표계정 회원조회
		UserVO trgt = getUserRprs(entVO.getBzentyNo());
		// 이메일정보가 없을 경우
		if (trgt == null || CommUtils.isEmpty(trgt.getEmlAddr()))
			return;
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put("context"     , userVO.getGsContext());
		params.put("toName"      , trgt.getUserNm());
		params.put("toAddress"   , trgt.getEmlAddr());
		params.put("userNm"      , userVO.getUserNm());
		params.put("bzentyNm"    , entVO.getBzentyNm());
		// TODO 마이페이지 - 그룹관리 - 권한관리 이동 처리는 불가 (로그인후 사용가능)

		// 메일발송
		emailService.sendBizEmail(params, CodeConst.BIZMAIL_MEMBER);
	}
	
    /**
     * 2023.07.05 LSH 회원정보 변경처리
     * 마이페이지 - 기본정보 - 개인정보에서 사용됨
     *
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.userNm     사용자이름 (선택항목)
     * @param userVO.mblTelno   휴대폰번호 (선택항목)
     * @param userVO.pswd       비밀번호   (선택항목)
     */
	@Override
	public int updtUser(UserVO userVO) throws Exception {
		return userDAO.updtUser(userVO);
	}
	
    /**
     * 2023.07.05 LSH 회원정보 탈퇴처리
     * 마이페이지 - 기본정보 - 개인정보에서 사용됨
     * 
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.whdwlYmd   탈퇴일자
     * @param userVO.useSttsCd  사용상태
     * 
     * 회원탈퇴시 처리절차
     * 1) 대표계정인 경우 탈퇴처리 안되도록 처리
     * 2) 회원의 탈퇴처리를 수행한다.
     */
	@Override
	public int deltUser(UserVO userVO) throws Exception {

		// 대표계정 탈퇴불가
		if (CommConst.YES.equals(groupService.getGrpRprsYn(userVO.getUserNo())))
			throw new BusinessException("대표계정은 탈퇴하실 수 없습니다. 담당자에게 문의하세요.");
		// 회원 탈퇴처리
		return userDAO.updtUserFinish(userVO);
	}

    // 이미 가입된 회원인지 확인
	@Override
	public boolean existUser(String userId) throws Exception {
		return userDAO.existUser(userId);
	}
	// 사용자정보 조회
	@Override
    public UserVO getUser(String userNo) throws Exception {
		return userDAO.getUser(userNo);
	}

	// 로그인 사용자정보 조회
	@Override
	public UserInfo getUserInfo(String userId) throws Exception {
        return userDAO.getUserInfo(userId);
	}

    // OAUTH 로그인시 사용자정보 조회
	@Override
    public UserInfo getUserInfoByOauth(UserVO userVO) throws Exception {
        return userDAO.getUserInfoByOauth(userVO);
	}

	// 패스워드 실패시 카운터 업데이트 및 잠금시간 등록
	@Override
	public int updtPswdErrCnt(String userId, int pswdErrCnt) throws Exception {
		Map<String, Object> params = new HashMap<String, Object> ();
		params.put("userId"    , userId    );
		params.put("pswdErrCnt", pswdErrCnt);
        return userDAO.updtPswdErrCnt(params);
	}

    // 로그인시간 등록
	@Override
	public int updtLoginTime(String userId) throws Exception {
        return userDAO.updtLoginTime(userId);
	}

    // 사용자아이디,비밀번호 일치여부 확인
	@Override
	public boolean existUserPswd(String userId, String password) throws Exception {
		return userDAO.existUserPswd(userId, password);
	}

    /**
     * 2023.07.31 LSH 로그인시 업체 승인 여부 확인
     */
	@Override
	public boolean approveBzenty(String bzentyNo) throws Exception {
		return userDAO.approveBzenty(bzentyNo);
	}
	
    /**
     * 2023.06.28 LSH 업체회원의 업체번호 초기화 처리 (NULL업데이트)
     * 마이페이지 - 그룹관리 - 멤버관리에서 사용됨
     * 
     * @param params.gsUserNo 세션사용자번호
     * @param params.userNo   대상사용자번호
     */
	@Override
    public int updtUserBzentyReset(Map<String,String> params) throws Exception {
        return userDAO.updtUserBzentyReset(params);
    }

    /**
     * 2023.06.28 LSH 업체회원의 권한변경처리
     * 마이페이지 - 그룹관리 - 멤버관리에서 사용됨
     * 
     * @param params.gsUserNo  세션사용자번호
     * @param params.userNo    대상사용자번호
     * @param params.roleId    대상사용자권한
     * @param params.newRoleId 신규사용자권한
     */
	@Override
    public int updtUserBzentyRole(Map<String,String> params) throws Exception {
        return userDAO.updtUserBzentyRole(params);
    }

    /**
     * 2023.06.28 LSH 회원의 권한조회
     */
	@Override
	public String getUserRole(String userNo) throws Exception {
        return userDAO.getUserRole(userNo);
    }
	
    /**
     * 2023.07.18 LSH 회원의 업체번호 업데이트
     * 회원가입 - 기업회원에서 사용됨
     * 
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.bzentyNo   업체번호
     */
	@Override
	public int updtUserBzenty(UserVO userVO) throws Exception {
        return userDAO.updtUserBzenty(userVO);
	}
	
    /**
     *  2023.07.31 LSH 업체의 대표계정 회원정보 조회
     */
	@Override
    public UserVO getUserRprs(String bzentyNo) throws Exception {
		return userDAO.getUserRprs(bzentyNo);
    }
	
	/**
     *  2023.08.01 LHB
     *  ID/PW 찾기 사용자정보 조회
     */
	@Override
    public UserVO getUserInfoByIdPw(UserVO userVO) throws Exception {
		return (UserVO) userDAO.getUserInfoByIdPw(userVO);
    }
	
	/**
     *  2023.08.01 LHB
     *  임시비밀번호 메일 발송
     */
	public void sendMail(UserVO userVO, String tempPswd) throws Exception {

		if (CommUtils.isEmpty(userVO.getEmlAddr())) {
			// 사용자 이메일 정보가 없을 경우
			return;
		}
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put("userId"  , userVO.getUserId());
		params.put("tempPswd", tempPswd);
		
		params.put("context"  , userVO.getGsContext());
		params.put("toName"   , userVO.getUserNm());
		params.put("toAddress", userVO.getEmlAddr());
		params.put("userNm"   , userVO.getUserNm());

		// 메일발송
		emailService.sendBizEmail(params, CodeConst.BIZMAIL_FINDPW);
	}
}
