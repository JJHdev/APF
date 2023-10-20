package business.com.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import business.com.CommBizUtils;
import business.com.CommConst;
import business.usr.CodeConst;
import common.base.BaseModel;
import common.util.CommUtils;
import egovframework.com.utl.sim.service.EgovFileScrty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 회원가입 모델 클래스
 *
 * @class   : UserVO
 * @author  : LSH
 * @since   : 2023.03.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class UserVO extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
    // 사용자번호
    private String userNo;
    // 업체번호
    private String bzentyNo;
    // 사용자ID
    private String userId;
    // 사용자명
    private String userNm;
    // 비밀번호
    private String pswd;
    // 이메일주소
    private String emlAddr;
    // 전화번호
    private String telno;
    // 휴대전화번호
    private String mblTelno;
    // 생년월일
    private String brdt;
    // 우편번호
    private String zip;
    // 주소
    private String addr;
    // 상세주소
    private String daddr;
    // 성별
    private String sexdstn;
    // 부서코드
    private String deptCd;
    // 부서명
    private String deptNm;
    // 사원번호
    private String emplNo;
    // 식별ID
    private String idntfId;
    // 로그인구분코드
    private String lgnSeCd;
    // 가입일자
    private String joinYmd;
    // 탈퇴일자
    private String whdwlYmd;
    // 접속가능IP주소
    private String cntnPsbltyIpAddr;
    // 비밀번호잠금일자
    private String pswdLockYmd;
    // 비밀번호오류수
    private Long pswdErrCnt;
    // 비밀번호변경일자
    private String pswdChgYmd;
    // 비밀번호다음일자
    private String pswdNextYmd;
    // 마지막로그인일시
    private String lastLgnDt;
    // 휴대전화수신동의여부
    private String moblphonRcptnAgreYn;
    // 개인정보수집동의여부
    private String prvcClctAgreYn;
    // 제3자제공동의여부
    private String thptyPvsnAgreYn;
    // 이용약관동의여부 (칼럼없음)
    private String srvcTrmsAgreYn;
    // 사용상태코드
    private String useSttsCd;
    // 시험사용여부
    private String testUseYn;
    // 등록자번호
    private String rgtrNo;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    // 컨텍스트
    private String gsContext;
    
    // 가입유형
    private String joinCd;
    // 진행단계
    private String stepCd;
    // 사용자 ROLE
    private String roleId;
    private String roleNm;
    
    // 연동 ACCESS TOKEN
    private String token;
    
    // 본인인증여부
    private String certYn;
    // 네이버URL
    private String naverUrl;
    // 카카오URL
    private String kakaoUrl;
    
    // 모빌리언스인증용 세션정보
    private String ssUserNm;
    private String ssMblTelno;

    /**
     * 저장시 데이터를 항목에 맞게 REBUILD 한다.
     */
    public void rebuildProperties(HttpServletRequest request, HttpSession session) {
        
        String mode = getMode(); // 저장모드
        
		// 세션 사용자정보의 사용자번호를 모델객체에 맵핑
        if (getUserInfo(request) != null) {
    		setGsBzentyNo(getUserInfo(request).getBzentyNo());
    		setGsRoleId  (getUserInfo(request).getRoleId());
    		setGsUserNo  (getUserInfo(request).getUserNo());
    		setUserNo    (getUserInfo(request).getUserNo());
        }
        // 개인정보 변경인 경우
        if (CommConst.UPDATE.equals(mode)) {
        	
    		// 모빌리언스 휴대폰 본인인증 완료시 세션검증을 위한 정의
    		if (CommConst.YES.equals(getCertYn())) {
        		// 모빌리언스 인증 세션가져오기
    	        //setSsUserNm   ((String)session.getAttribute(CommConst.SESS_MOBILIANS_NM));
    	        //setSsMblTelno ((String)session.getAttribute(CommConst.SESS_MOBILIANS_NO));
    	    	// 휴대폰 번호 포맷 제거
    	        //setSsMblTelno(CommUtils.replace(getSsMblTelno(), "-", ""));
    		}
    		// 모빌리언스 휴대폰 본인인증 미완료시 이름/휴대폰번호 제외
    		else {
        		// 본인이름 / 휴대폰번호 제외
    			setUserNm  (null);
    			setMblTelno(null);
    		}
	    	// 휴대폰 번호 포맷 제거
	    	if (CommUtils.isNotEmpty(getMblTelno()))
				setMblTelno(CommBizUtils.toMobileNumber(getMblTelno()));
        }
        // 회원탈퇴인 경우
        else if (CommConst.DELETE.equals(mode)) {
        	// 탈퇴일자 정의
        	setWhdwlYmd(CommUtils.getCurDateString());
        	// 사용상태 정의 (미사용)
        	setUseSttsCd(CodeConst.USE_STATUS_UNUSED);
        }
        // 회원가입인 경우
        else if (CommConst.INSERT.equals(mode)) {
        	
        	// 가입유형이 없는 경우 세션에서 가져오기
        	if (CommUtils.isEmpty(getJoinCd()))
        		setJoinCd((String)session.getAttribute(CommConst.SESS_USER_JOINCD));
        	
    		// 모빌리언스 휴대폰 본인인증 완료시 세션검증을 위한 정의
    		if (CommConst.YES.equals(getCertYn())) {
        		// 모빌리언스 인증 세션가져오기
    	        //setSsUserNm   ((String)session.getAttribute(CommConst.SESS_MOBILIANS_NM));
    	        //setSsMblTelno ((String)session.getAttribute(CommConst.SESS_MOBILIANS_NO));
    	    	// 휴대폰 번호 포맷 제거
    	        //setSsMblTelno(CommUtils.replace(getSsMblTelno(), "-", ""));
    		}
	    	// 휴대폰 번호 포맷 제거
    		// 2023.09.22 국가번호 포함 포맷처리 추가
    		
	    	if (CommUtils.isNotEmpty(getMblTelno()))
				setMblTelno(CommBizUtils.toMobileNumber(getMblTelno()));
	    		//setMblTelno(CommUtils.replace(getMblTelno(), "-", ""));
        	
			// 이용약관 동의항목 가져오기
	    	//setSrvcTrmsAgreYn((String)session.getAttribute(CommConst.SESS_AGREE_SERVICE));
	    	//setPrvcClctAgreYn((String)session.getAttribute(CommConst.SESS_AGREE_PRIVACY));
	    	
	    	// 이메일을 사용자ID와 동일하게 맵핑
	    	setEmlAddr(getUserId());
	    	// 사용상태 정의 (활성상태)
        	setUseSttsCd(CodeConst.USE_STATUS_USABLE);
        	// 가입일자 정의
        	setJoinYmd(CommUtils.getCurDateString());
			// 일반회원인 경우
			if (CommConst.JOIN_USR.equals(getJoinCd())) {
				// 사용자의 기본권한을 ROLE_AUTH_USR(회원사용자)로 정의
				setRoleId(CommConst.USER_ROLE_USR);
			}
    	}
    }
    
    // 사용자 비밀번호 암호화 처리
    public void encryptPswd() throws Exception {
        // 사용자 비밀번호 암호화
    	if (CommUtils.isNotEmpty(getPswd()) &&
    		CommUtils.isNotEmpty(getUserId())) {
	    	setPswd(EgovFileScrty.encryptPassword(getPswd(), getUserId()));
    	}
    }
}
