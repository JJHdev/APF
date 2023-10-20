package business.com;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import common.util.CommUtils;
import common.util.properties.ApplicationProperty;

/**
 * [상수클래스] - 공통 상수
 *
 * @class   : CommConst
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
public class CommConst {

	public static final String INSERT  = "I";        // 등록
    public static final String UPDATE  = "U";        // 수정
    public static final String DELETE  = "D";        // 삭제
    public static final String SAVE    = "S";        // 저장
    public static final String LOAD    = "L";        // 로드
    public static final String LIST    = "LIST";     // 목록
    public static final String VIEW    = "VIEW";     // 상세조회
    public static final String OPEN    = "OPEN";     // 오픈
    public static final String SUBMIT  = "SUBMIT";   // 제출하기
    public static final String TMPSAVE = "TMPSAVE";  // 임시저장
    public static final String RESULT  = "RESULT";   // 결과
    public static final String SEARCH  = "SEARCH";   // 검색
    public static final String SELECT  = "SELECT";   // 조회
    public static final String GIS     = "GIS";      // GIS 화면구분을 위한 변수
    
    public static final String YES = "Y";
    public static final String NO  = "N";
    public static final String ALL = "ALL";
    
    public static final String ACT_LOGIN = "LOGIN"; // 로그인 ACTION
    public static final String ACT_JOIN  = "JOIN" ; // 회원가입 ACTION
    public static final String ACT_MAIN  = "MAIN" ; // 메인 ACTION
    public static final String ACT_DONE  = "DONE" ; // 완료 ACTION
    public static final String ACT_ERROR = "ERROR"; // 오류 ACTION

    public static final String ACT_GROUP_CODE = "CODE"; // 그룹관리 - 그룹코드저장 ACTION
    public static final String ACT_GROUP_ROLE = "ROLE"; // 그룹관리 - 그룹권한저장 ACTION
    public static final String ACT_GROUP_RPRS = "RPRS"; // 그룹관리 - 대표계정변경 ACTION
    
    public static final String ACT_BZ_INFO    = "BI"; // 회원가입/기업정보 - 기본정보 (등록/수정)
    public static final String ACT_BZ_FILE    = "BF"; // 회원가입/기업정보 - 상세정보 (등록/수정)
    public static final String ACT_IR_INFO    = "II"; // IR작성하기 - 대시보드 (등록/수정)
    public static final String ACT_IR_FILE    = "IF"; // IR작성하기 - 상세정보 (등록/수정)
    public static final String ACT_IR_SPTHST  = "IS"; // IR작성하기 - 기타지원이력 등록
    public static final String ACT_CRDNS_FILE = "CF"; // 정보서비스-경영체 데이터 업로드 - 파일 업로드 (등록)
    
    public static final String SYSTEM_FLAG               = ApplicationProperty.get("system.real");               // 시스템사용구분 (real:운영, dev:개발)
    public static final String SYSTEM_CODE               = ApplicationProperty.get("system.code");               // 시스템코드
    public static final String SYSTEM_MAIN               = ApplicationProperty.get("system.main");               // 메인페이지 (.do 제외)
    public static final String IS_AUTH_CHECK             = ApplicationProperty.get("auth.check");                // 권한체크 사용여부
    public static final String EXCLUDE_ACC_PROG          = ApplicationProperty.get("exclude.acc.prog");          // 페이지세션에 저장할 페이지 제외 프로그램.
    public static final String SESS_PAGE_INFO            = ApplicationProperty.get("SESS.PAGEINFO");             // 페이지세션명
    public static final String SESS_MENU_INFO            = ApplicationProperty.get("SESS.MENUINFO");             // 메뉴세션명
    public static final String SESS_ACCESS_URL           = ApplicationProperty.get("SESS.ACCESSURL");            // 접근URL세션명
    public static final String SESS_SYSTEM_CODE          = ApplicationProperty.get("SESS.SYSTEMCODE");           // 시스템코드세션명
    public static final String SESS_MENU_LIST            = ApplicationProperty.get("SESS.MENULIST");             // 메뉴목록세션명

    public static final String SERVER_NAME               = ApplicationProperty.get("system.servername");         // 서버명
    public static final String ROLE_RESTRICTED           = ApplicationProperty.get("system.role.restricted");    // 제한된사용자 롤

    public static final String REMOVE_PATH               = ApplicationProperty.get("upload.remove.dir");         // 삭제된 파일의 ROOT 디렉토리

    public static final boolean EMAIL_SEND_ENABLE        = ApplicationProperty.getBoolean("spring.mail.send.enable"   ); // 메일 발송 사용여부 (false일 경우 로그도 남겨지지 않음)
    public static final boolean EMAIL_EXEC_ENABLE        = ApplicationProperty.getBoolean("spring.mail.execute.enable"); // 메일 발송 실행여부 (false로 설정해도 로그는 남겨짐)
    public static final String EMAIL_TEMPLATE_FILE       = ApplicationProperty.get       ("spring.mail.template"      ); // 메일 발송 템플릿 파일명
    
    public static final String COOKIE_LAST_LOGIN         = "APFS_COOK_LAST"; // 최종 로그인 유형 쿠키명칭
    
    public static final String SYSTEM_CHARSET            = "UTF-8";
    public static final String EMAIL_BASE64_ENCODE       = "B";
    public static final int    EMAIL_TYPE_CONTENT        = 1; // 이메일 내용타입 : HTML 컨텐츠
    public static final int    EMAIL_TYPE_TEMPLATE       = 2; // 이메일 내용타입 : Thymeleaf를 이용한 템플릿 컨텐츠
    
    public static final String EMAIL_SEND_SUCCESS        = "S00"; // 메일전송상태 - 성공
    public static final String EMAIL_SEND_FAILURE        = "E01"; // 메일전송상태 - 실패
    public static final String EMAIL_SEND_TEST           = "S09"; // 메일전송상태 - 테스트
   
    /*
     * SecurityInterceptor 에서 사용하는 상수
     **/
    public static final String COMMON_URL_PATTERN        = "/com/";
    public static final String COMMON_SYSTEM_CODE        = "COM";
    
    public static final String ERROR_URL_PATTERN         = "/com/error/";
    public static final String URL_POSTFIX               = ".do";

    public static final String LOGIN_PAGE                = "com/common/login";
    public static final String LOGIN_CHECK_PAGE          = "com/common/loginCheck";
    public static final String LOGIN_SUCCESS_PAGE        = "com/common/loginSucc";
    public static final String LOGOUT_PAGE               = "com/common/logout";
    public static final String LOGIN_TITLE               = "로그인";
    
    public static final String LOGIN_URL                 = "/" + LOGIN_PAGE         + URL_POSTFIX;
    public static final String LOGIN_CHECK_URL           = "/" + LOGIN_CHECK_PAGE   + URL_POSTFIX;
    public static final String LOGIN_SUCCESS_URL         = "/" + LOGIN_SUCCESS_PAGE + URL_POSTFIX;
    public static final String LOGOUT_URL                = "/" + LOGOUT_PAGE        + URL_POSTFIX;
    public static final String INDEX_URL                 = SYSTEM_MAIN + URL_POSTFIX;
    
    // ACCESS LOG 기록여부 (로그인, 프로그램접속)
    public static final boolean IS_ACCESS_LOG = true;
    
    /*
     * 네이버 로그인 설정값
     **/
    public static final String OAUTH_NAVER               = ApplicationProperty.get("OAUTH.NAVER");
    public static final String OAUTH_NAVER_CLIENT_ID     = ApplicationProperty.get("OAUTH.NAVER.CLIENT_ID");
    public static final String OAUTH_NAVER_CLIENT_SECRET = ApplicationProperty.get("OAUTH.NAVER.CLIENT_SECRET");
    public static final String OAUTH_NAVER_SESSION_STATE = ApplicationProperty.get("OAUTH.NAVER.SESSION_STATE");
    public static final String OAUTH_NAVER_CALLBACK_URL  = ApplicationProperty.get("OAUTH.NAVER.CALLBACK_URL");
    public static final String OAUTH_NAVER_AUTHORIZE_URL = ApplicationProperty.get("OAUTH.NAVER.AUTHORIZE_URL");
    public static final String OAUTH_NAVER_TOKEN_URL     = ApplicationProperty.get("OAUTH.NAVER.TOKEN_URL");
    public static final String OAUTH_NAVER_PROFILE_URL   = ApplicationProperty.get("OAUTH.NAVER.PROFILE_URL");
    public static final String OAUTH_NAVER_UNLINK_URL    = ApplicationProperty.get("OAUTH.NAVER.UNLINK_URL");

    /*
     * 카카오 로그인 설정값
     **/
    public static final String OAUTH_KAKAO               = ApplicationProperty.get("OAUTH.KAKAO");
    public static final String OAUTH_KAKAO_CLIENT_ID     = ApplicationProperty.get("OAUTH.KAKAO.CLIENT_ID");
    public static final String OAUTH_KAKAO_CLIENT_SECRET = ApplicationProperty.get("OAUTH.KAKAO.CLIENT_SECRET");
    public static final String OAUTH_KAKAO_SESSION_STATE = ApplicationProperty.get("OAUTH.KAKAO.SESSION_STATE");
    public static final String OAUTH_KAKAO_CALLBACK_URL  = ApplicationProperty.get("OAUTH.KAKAO.CALLBACK_URL");
    public static final String OAUTH_KAKAO_AUTHORIZE_URL = ApplicationProperty.get("OAUTH.KAKAO.AUTHORIZE_URL");
    public static final String OAUTH_KAKAO_TOKEN_URL     = ApplicationProperty.get("OAUTH.KAKAO.TOKEN_URL");
    public static final String OAUTH_KAKAO_PROFILE_URL   = ApplicationProperty.get("OAUTH.KAKAO.PROFILE_URL");
    public static final String OAUTH_KAKAO_UNLINK_URL    = ApplicationProperty.get("OAUTH.KAKAO.UNLINK_URL");

    // 개인회원
    public static final String JOIN_USR = "U";
    // 기업회원
    public static final String JOIN_BIZ = "B";
    // 회원가입/로그인시 OAUTH에서 사용되는 임시세션 - 가입유형
    public static final String SESS_USER_JOINCD = "SS_USER_JOINCD";
    // 회원가입/로그인시 OAUTH에서 사용되는 임시세션 - 회원가입/로그인
    public static final String SESS_USER_ACTION = "SS_USER_ACTION";
    // 회원가입/로그인시 OAUTH에서 사용되는 임시세션 - 회원정보
    public static final String SESS_USER_VO     = "SS_USER_VO";
    // 2023.08.30 LSH 사용자세션명 (나중에 globals.properties로 빼야함)
    public static final String SESS_USER_INFO   = "USERINFO";

    // 회원가입시 이용약관 동의세션
    public static final String SESS_AGREE_SERVICE = "SS_AGRE_SERVICE";
    // 회원가입시 개인정보 동의세션
    public static final String SESS_AGREE_PRIVACY = "SS_AGRE_PRIVACY";
    
    // 모빌리언스 본인인증시 검증을 위한 임시세션
    public static final String SESS_MOBILIANS_NM = "SS_MBLNS_NM";
    public static final String SESS_MOBILIANS_NO = "SS_MBLNS_NO";
    
    // 일반회원 사용자 ROLE
    public static final String USER_ROLE_USR = "ROLE_USR_USR";
    // 기업회원 - 투자자 사용자 ROLE
    public static final String USER_ROLE_INV = "ROLE_USR_EIV";
    // 기업회원 - 경영체 사용자 ROLE
    public static final String USER_ROLE_ENT = "ROLE_USR_EBZ";
    // 기업회원 - 유관기관 사용자 ROLE
    public static final String USER_ROLE_RIS = "ROLE_USR_EIS";

    // 관리자 ALIAS
    public static final String ALIAS_ADM = "ADM";
    // 일반회원 ALIAS
    public static final String ALIAS_USR = "USR";
    // 투자자회원 ALIAS
    public static final String ALIAS_INV = "INV";
    // 경영체회원 ALIAS
    public static final String ALIAS_ENT = "ENT";
    // 유관기관회원 ALIAS
    public static final String ALIAS_RIS = "RIS";
    // 비회원 ALIAS
    public static final String ALIAS_NON = "NON";

    /*
     * KG 모빌리언스 휴대폰 본인인증 관련 설정값
     */
    public static final boolean IS_MOBILIANS      = ApplicationProperty.getBoolean("MOBILIANS.ENABLE"); // 휴대폰 본인인증 사용여부 (내부설정용)
    public static final String MOBILIANS_PAYMODE  = ApplicationProperty.get("MOBILIANS.PAYMODE"      ); // 휴대폰 본인인증 - 처리구분 ( 00 : 테스트결제, 10 : 실거래결제 ) - "10"
    public static final String MOBILIANS_CIMODE   = ApplicationProperty.get("MOBILIANS.CIMODE"       ); // 휴대폰 본인인증 - SMS발송 (61:SMS발송  67:SMS미발송) - "61"
    public static final String MOBILIANS_SVCID    = ApplicationProperty.get("MOBILIANS.SVCID"        ); // 휴대폰 본인인증 - 서비스 아이디
    public static final String MOBILIANS_SITEURL  = ApplicationProperty.get("MOBILIANS.SITEURL"      ); // 휴대폰 본인인증 - 가맹점 도메인
    public static final String MOBILIANS_OKURL    = ApplicationProperty.get("MOBILIANS.OKURL"        ); // 휴대폰 본인인증 - OK URL (처리결과수신 페이지)
    public static final String MOBILIANS_NOTIURL  = ApplicationProperty.get("MOBILIANS.NOTIURL"      ); // 휴대폰 본인인증 - NOTI URL (가맹점결과전송 페이지)
    public static final String MOBILIANS_CRYPTYN  = ApplicationProperty.get("MOBILIANS.CRYPTYN"      ); // 휴대폰 본인인증 - 암호화 사용 여부 - "Y"
    public static final String MOBILIANS_KEYGB    = ApplicationProperty.get("MOBILIANS.CRYPTGB"      ); // 휴대폰 본인인증 - 암호화 Key 구분 (1 or 2 : 가맹점 관리자 등록 후 사용) - "1"
    public static final String MOBILIANS_PASSWORD = ApplicationProperty.get("MOBILIANS.PASSOWRD"     ); // 휴대폰 본인인증 - 암호화 Key
    
    /*
     * KODATA API 설정값
     */
    public static final String KODATA_API_URL      = ApplicationProperty.get("KODATA.API.URL"          ); // KODATA API - 연결 URL
    public static final String KODATA_API_PATH     = ApplicationProperty.get("KODATA.API.PATH"         ); // KODATA API - 서비스명
    public static final String KODATA_API_USERID   = ApplicationProperty.get("KODATA.API.USERID"       ); // KODATA API - 기관ID
    public static final String KODATA_API_PROCESS  = ApplicationProperty.get("KODATA.API.PROCESS"      ); // KODATA API - 처리구분
    public static final String KODATA_API_PIDAGRYN = ApplicationProperty.get("KODATA.API.PIDAGRYN"     ); // KODATA API - 개인정보조회동의여부
    public static final String KODATA_API_FORMAT   = ApplicationProperty.get("KODATA.API.FORMAT"       ); // KODATA API - 데이터 제공방식
    public static final String KODATA_API_JMNO     = ApplicationProperty.get("KODATA.API.JMNO"         ); // KODATA API - 전문상세코드
    public static final boolean IS_KODATA_SAMPLE   = ApplicationProperty.getBoolean("KODATA.API.SAMPLE"); // KODATA API - 연동샘플 사용여부 (연결가능시엔 false로 설정할것)

    // 관리자 URL 패턴
    public static final String ADMIN_URL_PATTERN = "/adm/";
    // 비회원 임시 ID
    public static final String USER_GUEST_ID = "guest";
    // 최상위 CODE
    public static final String ROOT_CODE = "NONE";
    // 최상위 ROLE
    public static final String ROOT_ROLE = "NONE";
    // 최상위 메뉴
    public static final String ROOT_MENU = "NONE";
    // 관리자 ROLE 배열
    public static final String[] ADMIN_ROLES = {"ROLE_ADM_MNG", "ROLE_ADM_SYS"};
    // 업체회원 ROLE 배열
    public static final String[] BIZ_ROLES = {USER_ROLE_INV, USER_ROLE_ENT, USER_ROLE_RIS};
    // 투자자대상회원 ROLE 배열 (관리자/유관기관/투자자)
    public static final String[] INVT_ROLES = {"ROLE_ADM_MNG", "ROLE_ADM_SYS", USER_ROLE_INV, USER_ROLE_RIS};
    
    // 관리자 기본명칭
    public static final String ADMIN_NAME = "관리자";
    
    // 개발환경인지 확인하는 함수
    public static boolean isDev() {
    	if ("dev".equalsIgnoreCase(SYSTEM_FLAG))
        	return true;
        return false;
    }

    // 로그인 여부를 확인하는 함수
    public static boolean isLogin(String userId) {
    	if (CommUtils.isNotEmpty(userId) && 
        	CommConst.USER_GUEST_ID.equals(userId) == false)
        	return true;
        return false;
    }
    // 관리자인지 확인하는 함수
    public static boolean isAdminRole(String roleId) {
    	return Arrays.asList(ADMIN_ROLES).contains(roleId);
    }
    // 일반회원인지 확인하는 함수
    public static boolean isUsrRole(String roleId) {
    	return USER_ROLE_USR.equals(roleId);
    }
    // 기업회원인지 확인하는 함수
    public static boolean isBizRole(String roleId) {
    	return Arrays.asList(BIZ_ROLES).contains(roleId);
    }
    // 투자자인지 확인하는 함수
    public static boolean isInvRole(String roleId) {
    	return USER_ROLE_INV.equals(roleId);
    }
    // 경영체인지 확인하는 함수
    public static boolean isEntRole(String roleId) {
    	return USER_ROLE_ENT.equals(roleId);
    }
    // 유관기관인지 확인하는 함수
    public static boolean isRisRole(String roleId) {
    	return USER_ROLE_RIS.equals(roleId);
    }
    // 투자자대상(관리자/유관기관/투자자)인지 확인하는 함수
    public static boolean isInvtTargetRole(String roleId) {
    	return Arrays.asList(INVT_ROLES).contains(roleId);
    }
    // 기업회원인지 확인하는 함수 (","로 묶여진 다중ROLE 체크)
    public static boolean isBizRoles(String roleId) {
    	if (roleId == null)
    		return false;
    	for (String bizRoleId : BIZ_ROLES) {
    		if (roleId.indexOf(bizRoleId) >= 0)
    			return true;
    	}
    	return false;
    }
    public static boolean isAuthCheck() {
    	return IS_AUTH_CHECK.equalsIgnoreCase("true");
    }
    // WEB ROOT 경로
    public static String getWebRootDir(HttpServletRequest request) {
    	return request.getServletContext().getRealPath("");
    }
    
    // 처리상태 - 제출
    public static final String PROCESS_SUBMIT    = "01";
    // 처리상태 - 미제출
    public static final String PROCESS_NOTSUBMIT = "02";

    // 화면모드 - 비회원
    public static String SHOW_MODE_LOGIN  = "X";
    // 화면모드 - 매칭미설정
    public static String SHOW_MODE_CONFIG = "N";
    // 화면모드 - 경영체대상
    public static String SHOW_MODE_ENT    = "B";
    // 화면모드 - 투자자/유관기관대상
    public static String SHOW_MODE_INV    = "I";
    // 화면모드 - 일반회원대상
    public static String SHOW_MODE_USR    = "U";

    // 검색모드 - 일반검색
    public static String SRCH_MODE_SEARCH = "S";
    // 검색모드 - 매칭설정검색
    public static String SRCH_MODE_MATCH  = "M";
    
	// 참여기업 타입 - 투자자
    public static String EVNT_INVT	= "INVT";
	// 참여기업 타입 - 경영체
    public static String EVNT_ENT	= "ENT";
    
    // 매칭모드 - 투자서비스
    public static String MTCH_MODE_INVT   = "I";
    // 매칭모드 - 지원서비스
    public static String MTCH_MODE_SPRT   = "S";
    
    // 개인정보 동의세션 - 지원구분
    public static final String SESS_PRVC_SE = "SS_PRVCAGRE_SE";
    // 개인정보 동의세션 - 프로그램번호
    public static final String SESS_PRVC_PG = "SS_PRVCAGRE_PG";
    
    // 통합시스템 동의세션 - 지원구분
    public static final String SESS_MSYS_SE = "SS_MSYSAGRE_SE";
    // 통합시스템 동의세션 - 프로그램번호
    public static final String SESS_MSYS_PG = "SS_MSYSAGRE_PG";

    // KODATA 조회시 업체정보 저장 세션
    public static final String SESS_KODATA_INFO = "SS_KODATA_INFO";
    
    // 썸네일 이미지파일 최대 갯수
    public static final int THUMB_MAX_CNT = 3;
    
    // 지원신청 동의항목 사용여부
    public static final boolean SPRT_AGREE = false;

    // CKEDITOR IMAGE URL CONTEXT 명칭
    public static final String CKEDITOR_IMAGE_CONTEXT = "{gsContext}";
    // CKEDITOR IMAGE URL
    public static final String CKEDITOR_IMAGE_URL = "/com/common/imageSrc.do";
}
