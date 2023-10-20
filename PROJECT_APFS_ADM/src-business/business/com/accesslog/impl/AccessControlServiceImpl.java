package business.com.accesslog.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.com.accesslog.AccessControlService;
import business.com.common.service.EmailVO;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 접속로그 등록(페이지접속, 로그인접속 로그 저장)
 * 
 * SecurityInterceptor 에서 접근하는 공통 서비스
 *
 * @class   : AccessControlServiceImpl
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 */

@Service("AccessControlService")
@SuppressWarnings({"all"})
public class AccessControlServiceImpl extends BaseService implements AccessControlService {

    @Resource(name = "AccessControlDAO")
    private AccessControlDAO accessControlDAO;

    /**
     * 2021.11.04 LSH
     * 정보보안로그
     */
    public int regiInfoLog(Map paramMap) {
        return accessControlDAO.regiInfoLog(paramMap);
    }

    /**
     * 접속로그
     */
    public int regiAccessLog(Map paramMap) {
        return accessControlDAO.regiAccessLog(paramMap);
    }

    /**
     * 로그인로그
     */
    public void regiLoginLog(HttpServletRequest request, String userNo, String failFlag) {
    	
    	// 로그기록여부 TRUE일 경우에만 로그남김
    	if (!CommConst.IS_ACCESS_LOG)
    		return;

        Map logMap = new HashMap();
        logMap.put("userNo",    CommUtils.nvlTrim(userNo, "guest"));
        logMap.put("ipAddr",    request.getRemoteAddr());
        logMap.put("srvrNm",    CommConst.SERVER_NAME+"_"+request.getLocalName()+"_"+request.getLocale().toString());
        logMap.put("sysCd",     CommConst.SYSTEM_CODE);
        logMap.put("lgnStusCd", CommUtils.isEmpty(failFlag) ? "O" : failFlag);

        accessControlDAO.regiLoginLog(logMap);
    }

    /**
     * 2023.03.24 LSH
     * 메일발송로그
     */
    public void regiEmailLog(EmailVO emailVO) {
        accessControlDAO.regiEmailLog(emailVO);
    }
}
