
package business.com.user.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;

import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.accesslog.AccessControlService;
import business.com.kodata.service.KodataBizVO;
import business.com.kodata.service.KodataService;
import business.com.user.UserError;
import business.com.user.oauth.KakaoLoginBO;
import business.com.user.oauth.NaverLoginBO;
import business.com.user.oauth.OAuthLoginBO;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.CodeConst;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.user.UserInfo;
import common.util.CommUtils;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.com.utl.slm.EgovHttpSessionBindingListener;

/**
 * [컨트롤러클래스] - 로그인(관리자)
 *      1. 로그인/로그아웃
 *      2. 중복로그인 방지
 *      3. 5회 비밀번호 실패시 잠김
 *      4. 분기별로 비밀번호 변경안내 및 변경
 *      5. 로그인 이력 남기기
 *
 * @class   : LoginController
 * @author  : ntarget
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"unchecked","rawtypes"})
public class LoginController extends BaseController {

    protected static final String FORM_USERNAME         = "username";
    protected static final String FORM_PASSWORD         = "password";

    @Autowired
    private AccessControlService accessControlService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntService entService;

    @Autowired
    private KodataService kodataService;

	@Autowired
	private KakaoLoginBO kakaoLoginBO;
	
	@Autowired
	private NaverLoginBO naverLoginBO;

    /**
     * 로그인화면 이동
     */
    @RequestMapping(CommConst.LOGIN_URL)
    public String login(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
    	
		// 처리타입을 세션에 담는다. (OAUTH에서 사용됨)
		session.setAttribute(CommConst.SESS_USER_ACTION, CommConst.ACT_LOGIN);
    	
    	model.addAttribute("mode", CommConst.SYSTEM_CODE);
    	model.addAttribute("act" , CommConst.ACT_LOGIN);
		// 네이버로그인 URL
    	model.addAttribute("naverAuthUrl", naverLoginBO.getAuthorizationUrl(session));
    	// 카카오로그인 URL
    	model.addAttribute("kakaoAuthUrl", kakaoLoginBO.getAuthorizationUrl(session));
    	
        // 로그인화면 SecurityInterceptor에서 제외되어 있으므로 
        // 다음의 항목을 Attribute로 정의해야 화면에서 사용이 가능하다.
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("sysCd"      , CommConst.SYSTEM_CODE);
        request.setAttribute("ver"        , CommUtils.getCurrDateTime2());
        // 화면의 제목표시를 위한 설정
        request.setAttribute("titleNm"    , CommConst.LOGIN_TITLE);

        logger.info("로그인 화면 이동");
        
        return CommConst.LOGIN_PAGE;
    }

    /**
     * 로그인 성공시 이동화면
     */
    @RequestMapping(CommConst.LOGIN_SUCCESS_URL)
    public String loginSucc(HttpServletRequest request, ModelMap model) throws Exception {

        logger.info("로그인 성공화면 이동");

        return "redirect:"+CommConst.INDEX_URL;
    }

    /**
     * 로그인 인증 처리
     */
    @RequestMapping(CommConst.LOGIN_CHECK_URL)
    @ResponseBody
    public Map loginCheck(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        String userId       = CommUtils.nvlTrim(request.getParameter(FORM_USERNAME));
        String password     = CommUtils.nvlTrim(request.getParameter(FORM_PASSWORD));
        UserInfo user       = (UserInfo)userService.getUserInfo(userId);

        UserError result = UserError.SUCCESS;
        
        // 로그인 검증.
        if (user == null) {
        	// 사용자정보가 없음.
            result = UserError.NOTFOUND;
        } else {
            // 사용자 비밀번호 암호화
        	password = EgovFileScrty.encryptPassword(password, userId);
        	
        	boolean equalPassword = userService.existUserPswd(userId, password);

            if (!CommUtils.isEmpty(user.getUseIp()) &&
                 CommUtils.nvlTrim(user.getUseIp()).indexOf(request.getRemoteAddr()) < 0 ) {
            	// 접속불가능 IP
            	result = UserError.REJECT;
            }
            else if (Integer.valueOf(CommUtils.nvlTrim(user.getPswdErrCnt(), "0")) >= 5 ) {
            	// 로그인 5회이상 실패
            	result = UserError.EXPIRED;
            }
            else if (!equalPassword) {
            	// 패스워드 틀림 (일반)
                result = UserError.NOTPASSWD;
                // 패스워드 오류 카운터
                userService.updtPswdErrCnt(
               		user.getUserId(), 
               		CommUtils.strToInt(user.getPswdErrCnt(), 0) + 1
                );
            }
	        // 로그인시 업체회원인 경우 업체승인여부 체크
            else if (CommUtils.isNotEmpty(user.getBzentyNo())) {
    			result = _checkApprove(request, user);
            }
        }
        // 로그인처리 수행
        _loginProcess(request, user, result);

        Map returnMap = new HashMap();
        returnMap.put("failFlag",   result.getFlag());
        returnMap.put("message" ,   result.getMessage());
        
        // 로그인성공시에만 사용자정보가 담기도록
        if (result.isSuccess() && user != null) {
        	Map userRet = new HashMap();
        	userRet.put("roleId"      , user.getRoleId());
        	userRet.put("diffDays"    , user.getDiffDays());
        	userRet.put("diffNextDays", user.getDiffNextDays());
            returnMap.put("user",  userRet);
        }
        return returnMap;
    }
    
    // 로그인시 업체회원인 경우 업체승인여부 체크
    private UserError _checkApprove(HttpServletRequest request, UserInfo user) throws Exception {
		// 업체회원인 경우
		if (CommUtils.isNotEmpty(user.getBzentyNo())) {
			// 업체정보 조회
			EntVO entVO = entService.getEnt(user.getBzentyNo());
			
			if (entVO == null)
				return UserError.UNKNOWN;

			// 2023.07.31 LSH 업체승인이 완료되지 않은 경우
			if (!CodeConst.APRV_STTS_APPROVE.equals(entVO.getUseSttsCd())) {
				
				// 2023.09.07 업체반려상태 또는 재신청 상태인 경우
				if (CodeConst.APRV_STTS_REJECT.equals(entVO.getUseSttsCd()) ||
					CodeConst.APRV_STTS_REAPPLY.equals(entVO.getUseSttsCd())) {
				
					// 2023.09.04 업체대표계정 조회
					UserVO rprsVO = userService.getUserRprs(user.getBzentyNo());
				
					// 2023.09.04 업체대표계정인 경우
					if (rprsVO != null && CommUtils.isEqual(rprsVO.getUserNo(), user.getUserNo())) {
						
						user.setBzentyNm  (entVO.getBzentyNm());
						user.setBzentySeCd(entVO.getBzentySeCd());
				    	user.setBrno      (entVO.getBrno());
				    	user.setKdCd      (entVO.getKdCd());
				    	user.setKodataYn  (CommConst.NO);
				    	user.setMatchYn   (CommConst.NO);
				    	user.setExistYn   (CommConst.YES);
				    	user.setRprsYn    (CommConst.YES);
				    	
				    	if (CommUtils.isNotEmpty(entVO.getKdCd())) {
				    		// KODATA 조회
				        	KodataBizVO kodata = kodataService.getKodata(entVO.getBrno());
				        	if (kodata != null) {
					    		user.setKodataYn (CommConst.YES);
						    	if (CommUtils.isNotEmpty(kodata.getReperName()) &&
					    			CommUtils.isNotEmpty(user.getUserNm()) &&
						    		CommUtils.isEqual(kodata.getReperName(), user.getUserNm()))
						    		user.setMatchYn(CommConst.YES);
				        	}
				    	}
						request.getSession().setAttribute(CommConst.SESS_REJECT_USER, user);
						return UserError.REJECT_ENT;
					}
				}
				return UserError.NOTAPPROVE;
			}
		}
		return UserError.SUCCESS;
    }
    
    // 로그인 처리수행
    private void _loginProcess(HttpServletRequest request, UserInfo user, UserError result) throws Exception {
    	
    	String userNo = null;
    	if (user != null)
    		userNo = user.getUserNo();
    	else
    		result = UserError.NOTFOUND;
    	
        // 로그인처리 이력 저장
        accessControlService.regiLoginLog(request, userNo, result.getFlag());

        // 로그인 성공
        if (result.isSuccess() && user != null) {
            // 중복로그인처리(기존로그인사용자 해제)
            loginExpired(request, user.getUserId());
            // 이전세션 제거
            clearSessionInformation(request);
            
            // 회원정보 세션생성
            setUserInfo(request, user);

            // 로그인 시간 저장, ERR CNT 0
            userService.updtLoginTime(user.getUserId());
        }
    }

    /**
     * 로그아웃 처리.
     */
    @RequestMapping(CommConst.LOGOUT_URL)
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
        clearSessionInformation(request);
        request.getSession().invalidate();

        logger.info("로그아웃 화면 이동");
        
        // 관리자시스템인 경우 로그인페이지로 이동
        return "redirect:"+CommConst.LOGIN_URL;
    }
    
	/**
	 * 2022.10.04 ntarget 세션타임아웃 시간을 연장한다. (Egov) 
	 * Cookie에 COOK.LATEST.TIME, COOK.EXPIRE.TIME 기록하도록 한다.
	 * @return result - String
	 * @exception Exception
	 */
    @RequestMapping("/com/common/refreshSessionTimeout.do")
    @ResponseBody
    public Map refreshSessionTimeout() throws Exception {
    	logger.info("세션타임아웃 시간 연장");
        return getSuccess();
    }

	// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping("/com/common/naverLoginCheck.do")
	public String naverLoginCheck(ModelMap model, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session) throws Exception {

		String code  = request.getParameter("code"); 
		String state = request.getParameter("state");
		String error = request.getParameter("error");
		String ermsg = request.getParameter("error_description");

		// 에러로 넘어왔을 경우 처리로직
		if (CommUtils.isNotEmpty(error)) {
			logger.error("NAVER LOGIN ERROR : [error="+error+", description="+ermsg+"]");
			// 오류 반환
			return _getOauthFailure(model, CommConst.ACT_ERROR, UserError.ERROR_NAVER);
		}
		logger.debug("NAVER 로그인 성공");

		// 접근토큰 발급
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		
		if (oauthToken == null) {
			// 오류 반환
			return _getOauthFailure(model, CommConst.ACT_ERROR, UserError.ERROR_NAVER);
		}

		// 로그인 사용자 정보를 읽어온다.
		String apiResult = naverLoginBO.getUserProfile(oauthToken);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj    = (JSONObject) jsonParser.parse(apiResult);
		JSONObject respObj    = (JSONObject) jsonObj.get("response");

		return _loginOauthCheck(request, response, session, model, naverLoginBO, oauthToken, 
				UserVO.builder()
				.lgnSeCd (CommConst.OAUTH_NAVER.substring(0,1))
				.idntfId ((String)respObj.get("id"    )) // // 고유식별정보
				.userNm  ((String)respObj.get("name"  ))
				.emlAddr ((String)respObj.get("email" ))
				.mblTelno((String)respObj.get("mobile"))
				.token   (oauthToken.getAccessToken())
				.build   ()
		);
	}

	// 카카오 로그인 성공시 callback
	@RequestMapping("/com/common/kakaoLoginCheck.do")
	public String kakaoLoginCheck(ModelMap model, 
			@RequestParam String code, 
			@RequestParam String state, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session) throws Exception {

		logger.debug("KAKAO 로그인 성공");

		// 접근토큰 발급
		OAuth2AccessToken oauthToken = kakaoLoginBO.getAccessToken(session, code, state);

		// 로그인 사용자 정보를 읽어온다.
		String apiResult = kakaoLoginBO.getUserProfile(oauthToken);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj    = (JSONObject) jsonParser.parse(apiResult);
		JSONObject respObj    = (JSONObject) jsonObj.get("kakao_account");
		return _loginOauthCheck(request, response, session, model, kakaoLoginBO, oauthToken,
				UserVO.builder()
			.lgnSeCd (CommConst.OAUTH_KAKAO.substring(0,1))
			.idntfId (String.valueOf((Long)jsonObj.get("id"))) // 고유식별정보
			.userNm  ((String)respObj.get("name"            )) // 이름 (TODO 검수신청완료 후 사용가능)
			.emlAddr ((String)respObj.get("email"           )) // 이메일
			.mblTelno((String)respObj.get("phone_number"    )) // 연락처
			.token   (oauthToken.getAccessToken())
			.build     ()
		);
	}
	
	private String _loginOauthCheck(
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session,
			ModelMap model,
			OAuthLoginBO oauthBO, 
			OAuth2AccessToken oauthToken, 
			UserVO userVO) throws Exception {
		
		logger.debug(userVO.getLgnSeCd() + " API PROFILE "
				+ "[ID     = "+userVO.getIdntfId()
				+ ",name   = "+userVO.getUserNm()
				+ ",email  = "+userVO.getEmlAddr()
				+ ",mobile = "+userVO.getMblTelno()+"]");

		String act  = (String)session.getAttribute(CommConst.SESS_USER_ACTION); // 처리타입
		String code = (String)session.getAttribute(CommConst.SESS_USER_JOINCD); // 가입유형
		
		// 해당하는 사용자정보 조회
		UserInfo oauthUser = userService.getUserInfoByOauth(userVO);

		
		// 1. 로그인인 경우
		// ---------------------------------------------------------------------
		if (CommConst.ACT_LOGIN.equals(act)) {
		    
		    // 사용자정보가 없는 경우
		    if (oauthUser == null) {
		    	// 연동해제 처리
				oauthBO.unlink(oauthToken);
				// 오류 반환 (사용자 정보가 없습니다)
				return _getOauthFailure(model, act, UserError.NOTFOUND);
		    }
	        // 로그인시 업체회원인 경우 업체승인여부 체크
            else if (CommUtils.isNotEmpty(oauthUser.getBzentyNo())) {
            	UserError result = _checkApprove(request, oauthUser);
            	if (!UserError.SUCCESS.equals(result)) {
    				// 오류 반환 (업체승인이 완료되지 않았습니다.)
    				return _getOauthFailure(model, act, result);
            	}
            }
			// OAUTH 인증 사용자 로그인 처리
		    _loginProcess(request, oauthUser, UserError.SUCCESS);

		    // 2023.07.11 LSH. 마지막로그인 유형 쿠키설정
            Cookie cookie = new Cookie(CommConst.COOKIE_LAST_LOGIN, userVO.getLgnSeCd());
            cookie.setPath("/");
            response.addCookie(cookie);
		    
			// 메인페이지로 이동
		    return _getOauthSuccess(model, CommConst.ACT_MAIN);
		}
		// 2. 회원가입인 경우
		// ---------------------------------------------------------------------
		else if (CommConst.ACT_JOIN.equals(act)) {

			// 이미 가입된 회원인 경우
			if (oauthUser != null) {
				// 오류 메세지 반환 (이미 가입된 회원입니다. 로그인하세요.)
				return _getOauthFailure(model, CommConst.ACT_LOGIN, UserError.EXIST_USER);
			}
			// 이미 해당 이메일로 가입된 경우
			if (userService.existUser(userVO.getEmlAddr())) {
		    	// 현재 연결된 OAUTH 연동해제 처리
				oauthBO.unlink(oauthToken);
				// 오류 메세지 반환 (이미 해당 이메일로 가입된 회원이 있습니다. 로그인하세요.)
				return _getOauthFailure(model, CommConst.ACT_LOGIN, UserError.EXIST_EMAIL);
			}
			// 저장모드 정의
			userVO.setMode(CommConst.INSERT);
			// 가입유형 정의
			userVO.setJoinCd(code);
			// 이메일을 회원ID로 지정
			userVO.setUserId(userVO.getEmlAddr());
			// 저장에 맞게 REBUILD
			userVO.rebuildProperties(request, session);

			// 가입유형이 개인회원인 경우
			if (CommConst.JOIN_USR.equals(code)) {
				// 회원정보 저장 처리
				if (userService.saveJoin(userVO) > 0) {
					// 가입완료 페이지로 이동
					return _getOauthSuccess(model, CommConst.ACT_DONE);
				}
			}
			// 가입유형이 기업회원인 경우
			else {
				// OAUTH 정보를 임시 세션에 담는다.
				session.setAttribute(CommConst.SESS_USER_VO, userVO);
				
				// 기업정보입력 페이지로 이동
			    return _getOauthSuccess(model, CommConst.ACT_JOIN);
			}
		}
		// ---------------------------------------------------------------------
		// 팝업페이지이므로 하위 페이지를 이동시켜야 함.
	    return _getOauthFailure(model, CommConst.ACT_ERROR, UserError.UNKNOWN);
	}
	
	private String _getOauthFailure(ModelMap model, String act, UserError err) {
		model.addAttribute("act"     , act);
		model.addAttribute("success" , CommConst.NO);
		// 오류 메세지 반환 (이미 개인회원으로 가입된 회원입니다. 로그인하세요.)
		model.addAttribute("message" , err.getMessage());
		model.addAttribute("failFlag", err.getFlag());
		return "com/common/loginCheck";
	}
	
	private String _getOauthSuccess(ModelMap model, String act) {
		model.addAttribute("act"     , act);
		model.addAttribute("success" , CommConst.YES);
		return "com/common/loginCheck";
	}
	
    /**
     * 2023.10.11 LSH OAUTH 로그인 결과 테스트용 코드
     * (테스트시에만 사용)
	@RequestMapping("/com/common/popupLoginSample.do")
    public String popupLogin(ModelMap model, HttpServletRequest request) throws Exception {
    	
		String act = CommConst.ACT_LOGIN;
		// 해당하는 사용자정보 조회
    	UserVO userVO = UserVO.builder().lgnSeCd("K").idntfId("1").build();
		UserInfo oauthUser = userService.getUserInfoByOauth(userVO);
		    
	    // 사용자정보가 없는 경우
	    if (oauthUser == null) {
			// 오류 반환 (사용자 정보가 없습니다)
			return _getOauthFailure(model, act, UserError.NOTFOUND);
	    }
        // 로그인시 업체회원인 경우 업체승인여부 체크
        else if (CommUtils.isNotEmpty(oauthUser.getBzentyNo())) {
        	UserError result = _checkApprove(request, oauthUser);
        	if (!UserError.SUCCESS.equals(result)) {
				// 오류 반환 (업체승인이 완료되지 않았습니다.)
				return _getOauthFailure(model, act, result);
        	}
        }
	    return "com/common/loginCheck";
    }
     */
	
    /**
     * 중복로그인 처리
     */
    private void loginExpired(HttpServletRequest request, String userId) throws Exception {
        EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener();
        request.getSession().setAttribute(userId, listener);

        // 세션중복로그인 설정.
        //request.getSession().setAttribute("SESS.EXPIRED",   "1");
    }
    
    
    /**
     * 아이디 찾기 팝업 화면
     */
    @RequestMapping("/com/common/modalFindId.do")
    public String modalFindId(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
        
        return "/com/common/modalFindId";
    }
    
    /**
     * 비밀번호 찾기 팝업 화면
     */
    @RequestMapping("/com/common/modalFindPswd.do")
    public String modalFindPswd(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
        
        return "/com/common/modalFindPswd";
    }
    
    /**
     * 사용자 정보 찾기
     */
    @RequestMapping("/com/common/getUserByIdPswd.do")
    @ResponseBody
    public Map getUserByIdPw(HttpServletRequest request, HttpServletResponse response, ModelMap model,
    		@ModelAttribute UserVO userVO)
            throws Exception {
    	
    	String mode = userVO.getMode();
    	
    	UserVO userInfo = userService.getUserInfoByIdPw(userVO);
    	
    	if (CommUtils.isEmpty(userInfo)) {
    		return getFailure(message.getMessage("error.user.notExist"));
    	}
    	
    	if (mode.equals(CodeConst.FIND_PSWD)) {
    		// 비밀번호 찾기인 경우
    		if (userInfo.getLgnSeCd().equals(CommConst.LGN_SE_NORMAL)) {
    			// 일반 회원가입
    			
    			// 현재 Context Path 변수 바인딩 (메일발송을 위한 부분)
        		userInfo.setGsContext(CommBizUtils.getDomainContext(request));
        		
        		// 2023.09.06 LSH 임시생성 비밀번호 로직 변경 처리
        		// 비밀번호 임시생성 (8자리)
        		String tempPswd   = CommUtils.getRamdomPassword(8);
        		//String tempPswd	= generateRandomString();
        		userInfo.setPswd(tempPswd);
        		userInfo.encryptPswd();
        		int ret = userService.updtUser(userInfo);
        		
        		if (ret <= 0) {
        			Map result = getFailure(message.getMessage("error.comm.fail"));
        			result.put("Code", -2);
        			
        			return result;
        		}
        		
        		userService.sendMail(userInfo, tempPswd);
    		} else {
    			// 네이버, 카카오 회원가입
    			Map result = getSuccess(userInfo);
    			result.put("Code", 2);
    			result.put("Message", userInfo.getLgnSeCd());
    			
    			return result;
    		}
    	}
    	
    	return getSuccess(userInfo);
    }
    
    /**
     * 2023.09.04 LSH 반려 업체 보완제출 팝업 화면
     * 
     * - 저장처리는 UserController에서 처리함
     */
    @RequestMapping("/com/common/modalEntCmpl.do")
    public String modalEntCmpl(HttpServletRequest request, 
    		ModelMap model) throws Exception {
        
    	UserInfo user = (UserInfo)request.getSession().getAttribute(CommConst.SESS_REJECT_USER);
    	if (user == null)
    		throw new BusinessException(message.getMessage("error.comm.noAuthTask"));
    	
    	model.addAttribute("model", user);
    	return "/com/common/modalEntCmpl";
    }

    // Session Remove
    private void clearSessionInformation(HttpServletRequest request) {
        request.getSession().removeAttribute(CommConst.SESS_PAGE_INFO);
        request.getSession().removeAttribute(CommConst.SESS_MENU_INFO);
        request.getSession().removeAttribute(CommConst.SESS_USER_ACTION);
        request.getSession().removeAttribute(CommConst.SESS_USER_VO);
        request.getSession().removeAttribute(CommConst.SESS_USER_JOINCD);
    }
    
    // 세션 유효성 검증을 위한 난수 생성기
 	protected String generateRandomString() {
 		return UUID.randomUUID().toString();
 	}
}
