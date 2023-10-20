package business.sys.user.service;

import business.adm.invest.service.EntVO;
import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 사용자정보 모델 클래스
 *
 * @class : UserInfoVO
 * @author : 한금주
 * @since : 2021.10.07
 * @version : 1.0
 *
 *          수정일 수정자 수정내용 ------- -------- ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends BaseModel {

	// 시스템 공통정보
	private String roleId; // 권한롤
    private String roleNm; // 권한롤명칭
	private String ipAddr; // IP주소
    private String joinCd; // 가입유형
	private String useYn;  // 사용여부
	private String enabled;

	// 세션사용자번호
	private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 컨텍스트
    private String gsContext;
    
    // 관리자 페이지 권한
    private String admRoleId;
    private String admRoleNm;
    // 사용자 페이지 권한
    private String usrRoleId;
    private String usrRoleNm;

    // 사용자번호
    private String userNo;
    // 업체번호
    private String bzentyNo;
    // 업체정보
    private EntVO entVO;
    // 업체명
    private String bzentyNm;
    // 업체구분
    private String bzentySeCd;
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
    private int pswdErrCnt;
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
    
    // 대표여부
    private String rprsYn;
    // 대표여부 (이전정보)
    private String rprsYnOrg;
    
	// 검색조건
	private String srchText;

}
