package common.base;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.user.UserInfo;
import common.util.CommUtils;
import egovframework.com.cmm.EgovMessageSource;

/**
 * Program Name 	: BaseController
 * Description 		:
 * Programmer Name 	: ntarget
 * Creation Date 	: 2021-02-08
 * Used Table 		:
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *  2021.07.16 LSH         EasyUI 페이징 관련 메서드 추가
 *
 */

@Aspect
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name="message")
    //protected Message message;
    protected EgovMessageSource message;

    private HttpServletRequest request = null;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    protected int baseCurrPage = 1;
    protected int basePageSize = 10;
    
    protected UserInfo getUserInfo(HttpServletRequest req) {
    	UserInfo userInfo = null;
    	if (req.getSession() != null)
    		userInfo = (UserInfo)req.getSession().getAttribute(CommConst.SESS_USER_INFO);
    	if (userInfo == null)
    		userInfo = new UserInfo();
    	return userInfo;
    }
    
    // 사용자정보 세션생성
    protected void setUserInfo(HttpServletRequest req, UserInfo userInfo) {
    	userInfo.setIpAddr(req.getRemoteAddr());
    	req.getSession().setAttribute(CommConst.SESS_USER_INFO, userInfo);
    }

    protected HashMap getParameterMap(HttpServletRequest req, boolean sessionFlag) {
        setRequest(req);

        HashMap map = new HashMap();

        map = getParameterMap(req);

        if (sessionFlag)
            setGlobalSession(req, map);

        return map;
    }

    protected HashMap getParameterMap(HttpServletRequest req) {
        setRequest(req);

        HashMap map = new HashMap();

        Enumeration enm = req.getParameterNames();

        String name = null;
        String value = null;
        String[] arr = null;

        while (enm.hasMoreElements()) {
            name = (String) enm.nextElement();
            arr = req.getParameterValues(name);

            // 배열로 받기 -> arr로 시작하는 변수명 ex) arrSysCode 는 배열로 받음.
            if (name.startsWith("arr")) {
                map.put(name, arr);
            } else {
                if (arr != null && arr.length > 0) {
                    value = arr[0];
                } else {
                    value = req.getParameter(name);
                }
                map.put(name, value);
            }
        }

        return map;
    }

    // Get Method Name
    protected String getMethodName(Throwable trb) {
        StackTraceElement[] stacks = trb.getStackTrace();
        StackTraceElement currentStack = stacks[0];
        return currentStack.getMethodName();
    }

    /**
     * 글로벌세션으로 만들기(UserInfo -> MAP)
     * ex) userId   -> gsUserId
     */
    protected void setGlobalSession(HttpServletRequest req, Map map) {
        setGlobalSession(req, map, null);
    }

    /**
     * 글로벌세션으로 만들기(UserInfo를 BaseModel에 추가)
     */
    protected void setGlobalSession(HttpServletRequest req, BaseModel baseModel) {
        setGlobalSession(req, null, baseModel);
    }

    /**
     * 글로벌세션으로 만들기
     *  - UserInfo -> MAP
     *  - UserInfo를 BaseModel에 추가
     *
     * @param map
     * @param baseModel
     */
    protected void setGlobalSession(HttpServletRequest req, Map map, BaseModel baseModel) {
        try {
        	UserInfo userInfo = getUserInfo(req);
        	
            if( map != null ) {
                Map userMap = new HashMap();
                userMap = PropertyUtils.describe(userInfo);
                // Serializable Object excluded.
                userMap.remove("advisors");
                userMap.remove("class");
                userMap.remove("callbacks");
                userMap.remove("exposeProxy");
                userMap.remove("frozen");
                userMap.remove("preFiltered");
                userMap.remove("proxiedInterfaces");
                userMap.remove("proxyTargetClass");
                userMap.remove("targetClass");
                userMap.remove("targetObject");
                userMap.remove("targetSource");

                Iterator k = userMap.keySet().iterator();
                String key = "";
                while (k.hasNext()) {
                    key = (String) k.next();
                    map.put("gs"+CommUtils.toUpper(CommUtils.substring(key, 0, 1))+CommUtils.substring(key, 1)
                            , userMap.get(key));
                }
            }

            // userInfo 추가
            if(baseModel != null) {
                baseModel.setUserInfo(userInfo == null ? new UserInfo() : userInfo);
            }
        } catch (IllegalAccessException iae) {
        	logger.debug(iae+"");
        } catch (Exception e) {
        	logger.debug(e+"");	
        }
    }

    public void resultFlag(String msg) {
        request.getSession().setAttribute("PROCFLAG", msg);
    }

    /**
     * 2021.07.16 LSH EasyUI GRID에서 받을 수 있는 결과데이터를 반환한다.
     */
    public Map getEasyUIResult(List list) {
        Map result = new HashMap();
    	if (list instanceof PaginatedArrayList) {
    		PaginatedArrayList p = (PaginatedArrayList)list;
    		result.put("total", p.getTotalSize());
    	}
    	result.put("rows", list);
    	return result;
   	}

    /**
     * 2021.10.29 LSH
     * 페이징정보가 담긴 맵을 반환한다.
     * 화면에서 JSON으로 페이징목록을 처리할때 사용한다.
     */
    public Map getPaginatedResult(List list) {
        Map result = new HashMap();
    	if (list instanceof PaginatedArrayList) {
    		PaginatedArrayList p = (PaginatedArrayList)list;
    		result.put("total", p.getTotalSize());
    		result.put("page",  p.getCurrPage());
    		result.put("pages", p.getTotalPage());
    	}
    	result.put("rows", list);
    	return result;
   	}
    
    /**
     * 2021.08.20 LSH 성공결과맵 반환
     */
    public Map getSuccess() {
        Map result = new HashMap();
        result.put("Code", 0);
        return result;
    }
    
    /**
     * 2021.08.20 LSH 성공결과맵 반환
     */
    public Map getSuccess(Object data) {
    	Map result = getSuccess();
        result.put("Data", data);
        return result;
    }
    
    /**
     * 2021.08.20 LSH 성공결과맵 반환
     */
    public Map getSuccess(String key, Object data) {
    	Map result = getSuccess();
        result.put(key, data);
        return result;
    }
    
    /**
     * 2021.10.12 LSH 오류결과맵 반환
     */
    public Map getFailure(BindingResult bindingResult) {
    	
   		List<String> messages = new ArrayList<String> ();
    	if (bindingResult != null &&
    		!CommUtils.isEmptyList(bindingResult.getAllErrors())) {
    		for (ObjectError error : bindingResult.getAllErrors()) {
    			String msg = null;
    			if (error.getDefaultMessage() != null)
    				msg = error.getDefaultMessage();
    			else if (error.getCode() != null) {
    				if (error.getArguments() == null)
        				msg = message.getMessage(error.getCode());
        			else
        				msg = message.getMessageArgs(error.getCode(), error.getArguments());
    			}
    			if (msg != null) {
    				messages.add(msg);
	   				logger.error("BindingResult Error Message : "+msg);
    			}
    		}
    	}
        Map result = new HashMap();
    	result.put("Code", -3);
   		// 오류의 첫번째 메세지를 가져온다.
    	if (messages.size() > 0)
	    	result.put("Message", messages.get(0));
    	else
	    	result.put("Message", message.getMessage("exception.proc.err"));
    	if (messages.size() > 1)
    		result.put("Messages", messages);
        return result;
    }
    
    /**
     * 2021.10.14 LSH 오류결과맵 반환
     */
    public Map getFailure() {
        Map result = new HashMap();
    	result.put("Code",    -1);
    	result.put("Message", message.getMessage("exception.proc.err"));
        return result;
    }
    
    /**
     * 2021.11.08 LSH 오류결과맵 반환
     */
    public Map getFailure(String message) {
        Map result = new HashMap();
    	result.put("Code",    -1);
    	result.put("Message", message);
        return result;
    }
}
