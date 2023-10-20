package common.user;

import java.io.Serializable;

import business.com.CommConst;

/**
 * Program Name 	: UserInfo
 * Description 		:
 * Programmer Name 	: ntarget
 * Creation Date 	: 2021-02-08
 * Used Table 		:
 */

@SuppressWarnings({"serial"})
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
    // 시스템 공통정보
    private String userId      = null; // 사용자아이디
    private String userNm      = null; // 사용자명
    private String joinCd      = null; // 사용자가입유형
    private String pswd        = null; // 비밀번호
    private String roleId      = null; // 권한롤
    private String admRoleId   = null; // 어드민 권한
    private String usrRoleId   = null; // 사용자 권한
    private String roleNm      = null; // 권한롤명칭
    private String ipAddr      = null; // IP주소

    private int    diffDays    = 0;    // 비밀번호변경일수
    private int    diffNextDays= 0;    // 비밀번호 다음에변경 일수

    /* 사용자정보 */
    private String userNo      = null; // 사용자번호
    private String bzentyNo    = null; // 업체번호
    private String bzentyNm    = null; // 업체명칭
    private String useIp       = null; // 사용IP
    private String emlAddr     = null; // 이메일
    private String telno       = null; // 전화번호
    private String mblTelno    = null; // 휴대전화번호
    private String brdt        = null; // 생년월일
    private String sexdstn     = null; // 성별
    private String deptCd      = null; // 부서코드
    private String deptNm      = null; // 부서명
    private String zip         = null; // 우편번호
    private String addr        = null; // 주소1
    private String daddr       = null; // 주소2
    private String joinYmd     = null; // 가입일자
    private String pswdLockYmd = null; // 비밀번호잠금일자
    private String pswdErrCnt  = null; // 비밀번호오류횟수
    private String pswdChgYmd  = null; // 비밀번호변경일자
    private String pswdNextYmd = null; // 비밀번호다음일자
    private String lastLgnDt   = null; // 마지막로그인일시
    private String useSttsCd   = null; // 사용상태
    private String testUseYn   = null; // 테스트사용여부
    private String rgtrNo      = null; // 등록자번호
    private String regDttm     = null; // 등록일시
    private String regDate     = null; // 등록일자
    private String mdfrNo      = null; // 수정자번호
    private String mdfDttm     = null; // 수정일시
    private String mdfDate     = null; // 수정일자
    private String cntnPsbltyIpAddr    = null; // 접속가능IP주소
    private String moblphonRcptnAgreYn = null; // 휴대전화수신동의여부
    private String indvInfoClctAgrdYn  = null; // 개인정보수집동의여부
    private String thptyPvsnAgreYn     = null; // 제3자제공동의여부
    
    private String gsUserNo    = null; // 로그인사용자번호
    private String mode        = null; // 처리모드
    
    private String lgnSeCd     = null; // 로그인구분타입
    private String idntfId     = null; // 식별ID
    
    private String rprsYn		= null;
    private String bzentySeCd   = null; // 업체구분
    private String brno         = null; // 사업자번호
    private String kdCd         = null;
    private String kodataYn     = null;
    private String existYn      = null;
    private String matchYn      = null;
    
    // 2021.08.27 LSH 사용자 로그인 여부
    public boolean isLogin() {
    	return CommConst.isLogin(userNo);
    }
    // 2021.08.27 LSH 관리자인지 여부
    public boolean isAdmin() {
    	return CommConst.isAdminRole(roleId);
    }
    // 2023.05.01 LSH 업체회원인지 여부
    public boolean isBiz() {
    	return isLogin() && CommConst.isBizRole(roleId);
    }
    // 2023.05.01 LSH 투자자인지 여부
    public boolean isInv() {
    	return isLogin() && CommConst.isInvRole(roleId);
    }
    // 2023.05.01 LSH 경영체인지 여부
    public boolean isEnt() {
    	return isLogin() && CommConst.isEntRole(roleId);
    }
    // 2023.05.01 LSH 유관기관인지 여부
    public boolean isRis() {
    	return isLogin() && CommConst.isRisRole(roleId);
    }
    // 2023.06.15 LSH 일반회원인지 여부
    public boolean isUsr() {
    	return isLogin() && CommConst.isUsrRole(roleId);
    }
    // 2023.09.11 LSH 농금원관리자 여부
    public boolean isMng() {
    	return isLogin() && CommConst.isMngRole(roleId);
    }
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getJoinCd() {
		return joinCd;
	}
	public void setJoinCd(String joinCd) {
		this.joinCd = joinCd;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAdmRoleId() {
		return admRoleId;
	}
	public void setAdmRoleId(String admRoleId) {
		this.admRoleId = admRoleId;
	}
	public String getUsrRoleId() {
		return usrRoleId;
	}
	public void setUsrRoleId(String usrRoleId) {
		this.usrRoleId = usrRoleId;
	}
	public String getRoleNm() {
		return roleNm;
	}
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getDiffDays() {
		return diffDays;
	}
	public void setDiffDays(int diffDays) {
		this.diffDays = diffDays;
	}
	public int getDiffNextDays() {
		return diffNextDays;
	}
	public void setDiffNextDays(int diffNextDays) {
		this.diffNextDays = diffNextDays;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUseIp() {
		return useIp;
	}
	public void setUseIp(String useIp) {
		this.useIp = useIp;
	}
	public String getEmlAddr() {
		return emlAddr;
	}
	public void setEmlAddr(String emlAddr) {
		this.emlAddr = emlAddr;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getMblTelno() {
		return mblTelno;
	}
	public void setMblTelno(String mblTelno) {
		this.mblTelno = mblTelno;
	}
	public String getBrdt() {
		return brdt;
	}
	public void setBrdt(String brdt) {
		this.brdt = brdt;
	}
	public String getSexdstn() {
		return sexdstn;
	}
	public void setSexdstn(String sexdstn) {
		this.sexdstn = sexdstn;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDaddr() {
		return daddr;
	}
	public void setDaddr(String daddr) {
		this.daddr = daddr;
	}
	public String getJoinYmd() {
		return joinYmd;
	}
	public void setJoinYmd(String joinYmd) {
		this.joinYmd = joinYmd;
	}
	public String getPswdLockYmd() {
		return pswdLockYmd;
	}
	public void setPswdLockYmd(String pswdLockYmd) {
		this.pswdLockYmd = pswdLockYmd;
	}
	public String getPswdErrCnt() {
		return pswdErrCnt;
	}
	public void setPswdErrCnt(String pswdErrCnt) {
		this.pswdErrCnt = pswdErrCnt;
	}
	public String getPswdChgYmd() {
		return pswdChgYmd;
	}
	public void setPswdChgYmd(String pswdChgYmd) {
		this.pswdChgYmd = pswdChgYmd;
	}
	public String getPswdNextYmd() {
		return pswdNextYmd;
	}
	public void setPswdNextYmd(String pswdNextYmd) {
		this.pswdNextYmd = pswdNextYmd;
	}
	public String getTestUseYn() {
		return testUseYn;
	}
	public void setTestUseYn(String testUseYn) {
		this.testUseYn = testUseYn;
	}
	public String getRgtrNo() {
		return rgtrNo;
	}
	public void setRgtrNo(String rgtrNo) {
		this.rgtrNo = rgtrNo;
	}
	public String getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getMdfrNo() {
		return mdfrNo;
	}
	public void setMdfrNo(String mdfrNo) {
		this.mdfrNo = mdfrNo;
	}
	public String getMdfDttm() {
		return mdfDttm;
	}
	public void setMdfDttm(String mdfDttm) {
		this.mdfDttm = mdfDttm;
	}
	public String getMdfDate() {
		return mdfDate;
	}
	public void setMdfDate(String mdfDate) {
		this.mdfDate = mdfDate;
	}
	public String getIndvInfoClctAgrdYn() {
		return indvInfoClctAgrdYn;
	}
	public void setIndvInfoClctAgrdYn(String indvInfoClctAgrdYn) {
		this.indvInfoClctAgrdYn = indvInfoClctAgrdYn;
	}
	public String getThptyPvsnAgreYn() {
		return thptyPvsnAgreYn;
	}
	public void setThptyPvsnAgreYn(String thptyPvsnAgreYn) {
		this.thptyPvsnAgreYn = thptyPvsnAgreYn;
	}
	public String getGsUserNo() {
		return gsUserNo;
	}
	public void setGsUserNo(String gsUserNo) {
		this.gsUserNo = gsUserNo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getLgnSeCd() {
		return lgnSeCd;
	}
	public void setLgnSeCd(String lgnSeCd) {
		this.lgnSeCd = lgnSeCd;
	}
	public String getIdntfId() {
		return idntfId;
	}
	public void setIdntfId(String idntfId) {
		this.idntfId = idntfId;
	}
	public String getRprsYn() {
		return rprsYn;
	}
	public void setRprsYn(String rprsYn) {
		this.rprsYn = rprsYn;
	}
	public String getBzentyNo() {
		return bzentyNo;
	}
	public void setBzentyNo(String bzentyNo) {
		this.bzentyNo = bzentyNo;
	}
	public String getLastLgnDt() {
		return lastLgnDt;
	}
	public void setLastLgnDt(String lastLgnDt) {
		this.lastLgnDt = lastLgnDt;
	}
	public String getUseSttsCd() {
		return useSttsCd;
	}
	public void setUseSttsCd(String useSttsCd) {
		this.useSttsCd = useSttsCd;
	}
	public String getCntnPsbltyIpAddr() {
		return cntnPsbltyIpAddr;
	}
	public void setCntnPsbltyIpAddr(String cntnPsbltyIpAddr) {
		this.cntnPsbltyIpAddr = cntnPsbltyIpAddr;
	}
	public String getMoblphonRcptnAgreYn() {
		return moblphonRcptnAgreYn;
	}
	public void setMoblphonRcptnAgreYn(String moblphonRcptnAgreYn) {
		this.moblphonRcptnAgreYn = moblphonRcptnAgreYn;
	}
	public String getBzentyNm() {
		return bzentyNm;
	}
	public void setBzentyNm(String bzentyNm) {
		this.bzentyNm = bzentyNm;
	}
	public String getBzentySeCd() {
		return bzentySeCd;
	}
	public void setBzentySeCd(String bzentySeCd) {
		this.bzentySeCd = bzentySeCd;
	}
	public String getBrno() {
		return brno;
	}
	public void setBrno(String brno) {
		this.brno = brno;
	}
	public String getKdCd() {
		return kdCd;
	}
	public void setKdCd(String kdCd) {
		this.kdCd = kdCd;
	}
	public String getKodataYn() {
		return kodataYn;
	}
	public void setKodataYn(String kodataYn) {
		this.kodataYn = kodataYn;
	}
	public String getExistYn() {
		return existYn;
	}
	public void setExistYn(String existYn) {
		this.existYn = existYn;
	}
	public String getMatchYn() {
		return matchYn;
	}
	public void setMatchYn(String matchYn) {
		this.matchYn = matchYn;
	}
}

