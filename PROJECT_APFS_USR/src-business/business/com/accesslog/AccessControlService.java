package business.com.accesslog;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import business.com.common.service.EmailVO;

/**
 * [인터페이스클래스] - 접속로그 등록(페이지접속, 로그인접속 로그 저장)
 * 
 * SecurityInterceptor 에서 접근하는 공통 서비스
 *
 * @class   : AccessControlService
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 */

@SuppressWarnings({"all"})
public interface AccessControlService {

    /* 2021.11.04 LSH 정보보안 로그 저장 */
    public int regiInfoLog(Map map);

    /* 페이지접속 로그 저장 */
    public int regiAccessLog(Map map);

    /* 로그인 로그 저장 */
    public void regiLoginLog(HttpServletRequest request, String userNo, String failFlag);
    
    /* 2023.03.24 LSH 메일발송로그 저장 */
    public void regiEmailLog(EmailVO emailVO);
}
