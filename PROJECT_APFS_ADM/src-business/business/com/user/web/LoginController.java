
package business.com.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.accesslog.AccessControlService;
import business.com.user.UserError;
import business.com.user.service.UserService;
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

    /**
     * 로그인화면 이동
     */
    @RequestMapping(CommConst.LOGIN_URL)
    public String login(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
    	
    	model.addAttribute("mode", CommConst.SYSTEM_CODE);
    	model.addAttribute("act" , CommConst.ACT_LOGIN);
    	
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
	
    /**
     * 중복로그인 처리
     */
    private void loginExpired(HttpServletRequest request, String userId) throws Exception {
        EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener();
        request.getSession().setAttribute(userId, listener);

        // 세션중복로그인 설정.
        //request.getSession().setAttribute("SESS.EXPIRED",   "1");
    }

    // Session Remove
    private void clearSessionInformation(HttpServletRequest request) {
        request.getSession().removeAttribute(CommConst.SESS_PAGE_INFO);
        request.getSession().removeAttribute(CommConst.SESS_MENU_INFO);
        request.getSession().removeAttribute(CommConst.SESS_USER_ACTION);
        request.getSession().removeAttribute(CommConst.SESS_USER_VO);
        request.getSession().removeAttribute(CommConst.SESS_USER_JOINCD);
    }
}
