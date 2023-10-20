package common.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import business.com.CommConst;
import common.user.UserInfo;
import common.util.properties.ApplicationProperty;

public class UserUtils {

	// Autowired 사용시 JSP에서 사용하는 함수
	public static UserInfo getUserInfo(ServletContext servletContext) {
		// UserInfo 세션
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return (UserInfo) wac.getBean("userInfo");
	}

	public static UserInfo getUserInfo(HttpServletRequest request) {
    	UserInfo userInfo = null;
    	if (request.getSession() != null)
    		userInfo = (UserInfo)request.getSession().getAttribute(CommConst.SESS_USER_INFO);
    	if (userInfo == null)
    		userInfo = new UserInfo();
    	return userInfo;
	}
	
	public static String getCookieLatestTime() {
		return ApplicationProperty.get("COOK.LATEST.TIME");
	}
	public static String getCookieExpireTime() {
		return ApplicationProperty.get("COOK.EXPIRE.TIME");
	}
}
