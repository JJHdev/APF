package business.com.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import business.com.CommConst;
import business.com.accesslog.AccessControlService;
import business.com.authority.AuthorityService;
import common.user.UserInfo;
import common.util.CommUtils;

/**
 * SecurityInterceptor - 권한체크 및 페이지 접속로그등록.
 *
 * @class   : SecurityInterceptor
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일         수정자                수정내용
 *  -------        --------      ---------------------------
 *
 */

@SuppressWarnings("all")
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AuthorityService  authorityService;

    @Autowired
    private AccessControlService accessControlService;

    /**
     * 페이지 권한체크 처리
     * 페이지정보 세션 저장
     * 메뉴정보 세션저장
     * 접속로그 처리
     *
     * URL로 시스템접근정보
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	boolean isAjax	= true;
    	// Ajax 여부
        if (!"XMLHttpRequest".equals(request.getHeader("x-requested-with")) ) {
        	isAjax = false;
        }    	
    	
    	// 2021.11.06 현재 시스템코드 가져오기
        String sysCd = getSystemCode(request);

    	// 2021.08.27 LSH 화면 적용 변수 생성 (Template 변수)
        setPageAttribute(request, sysCd);
        
        // 2022.01.20 LSH 현재 URL
        String reqUrl = getRequestURL(request);

        // 2022.01.20 LSH 에러페이지 권한처리 SKIP
        if (reqUrl.startsWith(CommConst.ERROR_URL_PATTERN))
        	return true;

        // 권한체크 사용여부
        if (CommConst.isAuthCheck()) {
        	
        	UserInfo userInfo = getUserInfo(request);
        	
            Map map = new HashMap();
            map.put("url",          reqUrl );
            map.put("roleId",       CommUtils.nvlTrim(userInfo.getRoleId()) );
            map.put("userNo",       CommUtils.nvlTrim(userInfo.getUserNo()) );
            map.put("sysCd",        CommUtils.nvlTrim(sysCd) );

            // 사용자 ROLE이 없을 경우 제한된 사용자 ROLE로 맵핑 처리
            if (CommUtils.isEmpty((String)map.get("roleId"))) {
            	map.put("roleId", CommConst.ROLE_RESTRICTED);
            }

            Map authMap = null;
            // ajax 제외 (TODO. 저장시 AJAX를 사용하는 경우 SKIP되는 문제가 있음)
            //if (isAjax == false) {
            	authMap = authorityService.getAuthorityUser(map);
            //}
            // null -> 권한대상이 아님.
            if (authMap == null) {
            	logger.debug("Not subject to authorization :: " + reqUrl);
            	
            // 권한체크
            } else {
            	// TODO 2023.05.18 LSH. 권한접근 OK가 아니면서 세션정보가 없을경우
                if (!CommUtils.nvlTrim((String)authMap.get("auth")).equals("O") &&
                	 CommUtils.isEmpty(userInfo.getUserNo()) ) {
                    // 접근한 URL 정보를 세션에 담아 로그인시 구분조건으로 사용한다.
                    request.getSession().setAttribute(CommConst.SESS_ACCESS_URL, request.getRequestURI());
                    response.sendRedirect(request.getContextPath() + CommConst.LOGIN_URL);
                    return false;
                }
            	// 권한 없을경우 에러페이지
                if (CommUtils.nvlTrim((String)authMap.get("auth")).equals("X") ) {
                	// 2022.01.13 LSH 에러코드 403 으로 처리
                	// web.xml에 정의된 에러페이지로 Forwarding 됨
                	response.sendError(HttpStatus.FORBIDDEN.value());
                    //response.sendRedirect(request.getContextPath() + ACCESSDENIED_PAGE);
                    return false;
                }
            }
        }

        // Access Log
        accessLog(request, sysCd);

        // ajax 제외
        if (!"XMLHttpRequest".equals(request.getHeader("x-requested-with")) ) {

            // Page 정보 세션정의
            setPageInfo(request, sysCd);

            // Menu 정보 세션정의 (2021.11.26 현재사용안함)
            //setMenuInfo(request, sysCd);
        }
        return true;
    }

    // URL을 통해 시스템코드 가져오기
    private String getSystemCode(HttpServletRequest request) {
    	/*
    	HttpSession session = request.getSession();
    	String url = getRequestURL(request);

    	if (url.startsWith(CommConst.COMMON_URL_PATTERN)) 
    		return CommConst.COMMON_SYSTEM_CODE;
		*/
    	
    	return CommConst.SYSTEM_CODE;
    }

    // 화면 적용 변수 생성 (Template 변수)
    private void setPageAttribute(HttpServletRequest request, String sysCd) {

    	String contextPath = request.getContextPath();
    	String requestUri  = request.getRequestURI();
        String scriptPage  = requestUri.substring(requestUri.lastIndexOf("/"));
        if (!"".equals(scriptPage) &&
        	!"/".equals(scriptPage) )
        	scriptPage = scriptPage.substring(0, scriptPage.lastIndexOf("."));

        String scriptPath = requestUri.substring(0, requestUri.lastIndexOf("/"));
        scriptPath = scriptPath.replaceAll("/WEB-INF/jsp/", "");
        // Context Path는 제외한다.
        scriptPath = scriptPath.substring(contextPath.length());

        request.setAttribute("sysCd"      , sysCd);
        request.setAttribute("scriptPage" , scriptPath + scriptPage);
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("ver"        , CommUtils.getCurrDateTime2());

        //logger.info("SECURITY_INTERCEPTOR ***** ["+SYS_CD+":"+requestUri+"]");
    }
    
    // 세션 사용자정보 가져오기
    private UserInfo getUserInfo(HttpServletRequest request) {
    	UserInfo userInfo = null;
    	if (request.getSession() != null)
    		userInfo = (UserInfo)request.getSession().getAttribute(CommConst.SESS_USER_INFO);
    	if (userInfo == null)
    		userInfo = new UserInfo();
    	return userInfo;
    }

    // 로그 등록
    private void accessLog(HttpServletRequest request, String sysCd) {
    	
    	// 로그기록여부 TRUE일 경우에만 로그남김
    	if (!CommConst.IS_ACCESS_LOG)
    		return;
    	
    	UserInfo userInfo = getUserInfo(request);
    	
        String userNo  = "";
        if (userInfo != null)
            userNo = CommUtils.nvlTrim(userInfo.getUserNo());

        String url = getRequestURL(request);

        // Access Log Registration
        Map logMap = new HashMap();
        logMap.put("userNo",        CommUtils.nvlTrim(userNo, "guest") );
        logMap.put("ipAddr",        request.getRemoteAddr());
        logMap.put("progUrl",       url);
        logMap.put("srvrNm",        CommConst.SERVER_NAME+":"+request.getServerPort()+"__"+
                                    request.getLocalName()+"__"+
                                    request.getLocale().toString());
        logMap.put("sysCd",         CommUtils.nvlTrim(sysCd));

        accessControlService.regiAccessLog(logMap);
    }

    // 프로그램정보 세션정의
    private void setPageInfo(HttpServletRequest request, String sysCd) throws Exception {
    	
    	UserInfo userInfo = getUserInfo(request);
    	
        // Page Information
        Map pageMap = new HashMap();
        pageMap.put("url",          getRequestURL(request));
        pageMap.put("roleId",       CommUtils.nvlTrim(userInfo.getRoleId(), CommConst.ROLE_RESTRICTED));
        pageMap.put("sysCd",        CommUtils.nvlTrim(sysCd));

        if (getCurrPage(request).endsWith(CommConst.URL_POSTFIX)) { // ".do"
            String excludeProg = CommUtils.nvlTrim(CommConst.EXCLUDE_ACC_PROG);

            if (excludeProg.indexOf(getCurrPage(request)) < 0 ) {
                Map progMap = authorityService.getProgInfo(pageMap);

                if (progMap != null)
                    pageMap.put("pageInfo", progMap);
            }
        }

        if (userInfo != null)
            pageMap.put("userInfo", userInfo);

        request.getSession().setAttribute(CommConst.SESS_PAGE_INFO, pageMap);
    }

    // 메뉴정보 세션정의
    private void setMenuInfo(HttpServletRequest request, String sysCd) throws Exception {
    	
    	UserInfo userInfo = getUserInfo(request);

    	// 현재 화면 URL을 기준으로 해당 메뉴정보를 조회해 온다.


        // MAIN_URL 접속시 무조건 다시 메뉴조회 가능하게 메뉴정보를 삭제
        //if (getCurrPage(request).endsWith("index.do"))
        //    request.getSession().removeAttribute(SESS_MENU_INFO);

        // 세션에 등록된 메뉴정보 있으면 메뉴조회 쿼리 수행 안함.
        //if (request.getSession().getAttribute(SESS_MENU_INFO) == null) {
        Map menuMap = new HashMap();
        menuMap.put("roleId",   CommUtils.nvlTrim(userInfo.getRoleId(), CommConst.ROLE_RESTRICTED));
        menuMap.put("sysCd",    CommUtils.nvlTrim(sysCd));

        // 메뉴목록을 조회하여 세션에 담는다. (TODO 세션의 메뉴목록 사용여부 확인필요)
        List menuList = authorityService.getMenuList(menuMap);
        request.getSession().setAttribute(CommConst.SESS_MENU_LIST, menuList);
    }

    private String getRequestURL(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        requestURL      = requestURL.substring(request.getContextPath().length());
        String pathInfo = request.getPathInfo();

        if ("/".equals(requestURL) && pathInfo != null) {
            requestURL = pathInfo;
        }
        return requestURL;
    }

    private String getCurrPage(HttpServletRequest request) {
        String currPage     = request.getRequestURI();
        return currPage.substring(currPage.lastIndexOf("/")+1);
    }


}
