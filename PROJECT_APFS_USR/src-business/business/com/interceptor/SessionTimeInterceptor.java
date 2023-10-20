package business.com.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.util.properties.ApplicationProperty;

/**
 * SessionInterceptor - 세션시간 관리. ( EgovFramework 참조 )
 *
 * @class   : SessionInterceptor
 * @author  : ntarget
 * @since   : 2022.10.04
 * @version : 1.0
 *
 *   수정일         수정자                수정내용
 *  -------        --------      ---------------------------
 *
 */

@SuppressWarnings("all")
public class SessionTimeInterceptor extends HandlerInterceptorAdapter {

    protected final Log logger = LogFactory.getLog(getClass());

    protected static final String SERVER_TIME		= ApplicationProperty.get("COOK.LATEST.TIME");
    protected static final String EXPIRE_TIME       = ApplicationProperty.get("COOK.EXPIRE.TIME");

    /**
     * 세션시간 setCookie
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	HttpServletResponse httpResponse 	= (HttpServletResponse) response;
        HttpServletRequest httpRequest 		= (HttpServletRequest) request;
        
        long serverTime 		= System.currentTimeMillis();
        long sessionExpireTime 	= serverTime + httpRequest.getSession().getMaxInactiveInterval() * 1000;
        
        Cookie cookie = new Cookie(this.SERVER_TIME, "" + serverTime);
        cookie.setPath("/");
        
        httpResponse.addCookie(cookie);
        cookie = new Cookie(this.EXPIRE_TIME, "" + sessionExpireTime);
        cookie.setPath("/");
        
        Date dateServer 		= new Date(serverTime);
        Date dateExpiry 		= new Date(sessionExpireTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String serverYMD = format.format(dateServer);
        String expiryYMD = format.format(dateExpiry);
        
        //logger.info("serverYMD : " + serverYMD);
        //logger.info("expiryYMD : " + expiryYMD);
        
        httpResponse.addCookie(cookie);

        return true;
    }

}
