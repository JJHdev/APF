package business.adm;

import business.com.CommConst;
import common.util.CommUtils;

public class CodeConst {

    public static final String PROCESS_SUBMIT    = "01"; // 처리상태 - 제출
    public static final String PROCESS_NOTSUBMIT = "02"; // 처리상태 - 미제출
    
    // 게시판구분
    public static final String BBS_SE        = "CT.BBS_SE";
    public static final String BBS_NOTICE    = "B10"; // 게시판 - 공지사항
    public static final String BBS_QNA       = "B20"; // 게시판 - 1:1문의
    public static final String BBS_FAQ       = "B21"; // 게시판 - 자주묻는질문
    public static final String BBS_DATA      = "B30"; // 게시판 - 자료실
    public static final String BBS_INVTCASE  = "B40"; // 게시판 - 우수투자사례
    public static final String BBS_PROMOTION = "B50"; // 게시판 - 홍보영상
    public static final String BBS_COMMUNITY = "B60"; // 게시판 - 홍보영상

    // 게시판업무구분
    public static final String BBS_DTY_SE1	 = "BB1"; // 게시판업무구분 - 게시판
    public static final String BBS_DTY_SE2	 = "BB2"; // 게시판업무구분 - 문의게시판
    public static final String BBS_DTY_SE3	 = "BB3"; // 게시판업무구분 - 배너관리

    // 사용상태
    public static final String USE_STATUS_UNUSED = "0"; // 사용상태 - 미사용
    public static final String USE_STATUS_USABLE = "1"; // 사용상태 - 사용(승인)
    public static final String USE_STATUS_RESBMT = "8"; // 사용상태 - 재신청
    public static final String USE_STATUS_REJECT = "9"; // 사용상태 - 반려

    // 업체구분
    public static final String ENT_EIV = "10"; // 투자자업체
    public static final String ENT_EBZ = "20"; // 경영체업체
    public static final String ENT_EIS = "30"; // 유관기관업체
    
	// 유관기관구분
    public static final String CRDNS_SE		= "CT.CRDNS_SE";
    public static final String CRDNS_SE_01	= "01";	// 농업정책보험금융원
    public static final String CRDNS_SE_02	= "02";	// 농림식품기술기획평가원
    public static final String CRDNS_SE_03	= "03";	// 한국농업기술진흥원
    public static final String CRDNS_SE_04	= "04";	// 한국식품산업클러스터진흥원
    public static final String CRDNS_SE_99	= "99";	// 농업정책보험금융원
    
    // 승인상태
    public static final String APRV_STTS	= "CT.APRV_STTS";
    public static final String APRV_STTS_0	= "0";	// 승인대기
    public static final String APRV_STTS_1	= "1";	// 승인
    
    // 업무구분
    public static final String TASK_FUND = "TS001"; // 펀드정보
    public static final String TASK_BZIR = "TS002"; // 기업IR정보
    public static final String TASK_PBNC = "TS003"; // 사업공고
    public static final String TASK_SPRT = "TS004"; // 투자지원신청
    public static final String TASK_EVNT = "TS005"; // 행사참여경영체
    
    // 지원상태
    public static final String SPRT_SUBMIT  = "10"; // 미검토
    public static final String SPRT_HOLD    = "20"; // 보류
    public static final String SPRT_APPROVE = "90"; // 적합

    // 투자금액구분
    public static final String INVT_AMT_FUND  = "IAF"; // 농식품모태펀드 투자금액
    public static final String INVT_AMT_ETC   = "IAR"; // 기타 투자금액
    
    // 사용상태 공통코드
    public static final String USE_STUS		= "CT.USE_STUS";
	// 조합상태 공통코드
	public static final String ASSC_STUS	= "CT.ASSC_STUS";
	// 프로그램 구분코드 (투자유치전)
	public static final String PRGRM_SE		= "CT.PRGRM_SE";
	// 사업 구분코드
	public static final String BIZ_SE		= "CT.BIZ_SE";
	// 기업유형 구분코드
	public static final String BZENTY_TYPE	= "CT.BZENTY_TYPE";
	// 기업형태 구분코드
	public static final String BZENTY_STLE	= "CT.BZENTY_STLE";
	// 기업규모 구분코드
	public static final String BZENTY_SCALE	= "CT.BZENTY_SCALE";
	// 업종 구분코드
	public static final String INDUTY_SE	= "CT.INDUTY_SE";
	// 사업분야 구분코드
	public static final String BIZ_RLM    	= "CT.BIZ_RLM";
	// 진행상태코드1 (상담필요, 상담완료, ...)
	public static final String PRGRS_STTS1			= "CT.PRGRS_STTS1";
	// 진행세부상태코드 (배정, 생략, 중단, 완료)
	public static final String PRGRS_DETAIL_STTS	= "CT.PRGRS_DETAIL_STTS";
	// 차수
	public static final String PRCS_CYCL			= "CT.PRCS_CYCL";
	
	// 업무메일구분
	public static final String BIZMAIL_SE	   = "CT.BIZMAIL_SE";
    public static final String BIZMAIL_MEETING = "BM01001"; // [사용자] 미팅신청 메일
    public static final String BIZMAIL_MEMBER  = "BM01002"; // [사용자] 기업회원 가입메일
    public static final String BIZMAIL_FINDPW  = "BM01003"; // [사용자] 임시비밀번호 메일
    public static final String BIZMAIL_APPROVE = "BM02001"; // [관리자] 업체승인 메일
    public static final String BIZMAIL_REJECT  = "BM02002"; // [관리자] 업체반려 메일
    public static final String BIZMAIL_PASSWD  = "BM02003"; // [관리자] 비밀번호초기화 메일
	
	// 업체파일구분
    public static final String FILE_RPRS = "00";  // 파일구분: 대표이미지파일
    public static final String FILE_GNRL = "01";  // 파일구분: 일반파일
    public static final String FILE_BREG = "02";  // 파일구분: 사업자등록증
    public static final String FILE_DLGT = "03";  // 파일구분: 위임장
    public static final String FILE_OFFC = "04";  // 파일구분: 재직증명
    public static final String FILE_PLAN = "05";  // 파일구분: 사업계획서
    public static final String FILE_ADOC = "06";  // 파일구분: IR첨부서류
	
    // 파일구분
    public static final String FILE_REP = "00";  // 파일구분: 대표파일
    public static final String FILE_GEN = "01";  // 파일구분: 일반파일

	// 재무상태표 합계코드
	public static final String FNNR_FNTL_SE		= "CT.FNNR_FNTL_SE";
	// 손익계산서 합계코드
	public static final String FNNR_PLOS_SE		= "CT.FNNR_PLOS_SE";
	// 재무상태표 구분코드
	public static final String FNNR_FNTL_CD		= "1";
	// 손익계산서 구분코드
	public static final String FNNR_PLOS_CD		= "2";
	// 재무데이터 구분코드
	public static final String FNNR_KODATA		= "K"; // KODATA (주요재무정보)
	// 재무데이터 구분코드
	public static final String FNNR_MANUAL		= "M"; // MANUAL (추가재무정보)
	
	// 접수기간
	public static final String RCPT_WHL_SE			= "CT.RCPT_WHL_SE";
	public static final String RCPT_WHL_SE1			= "10"; // 예정
	public static final String RCPT_WHL_SE2			= "20"; // 상시모집
	public static final String RCPT_WHL_SE3			= "30"; // 기간
	
	// 사업분야
	public static final String BIZ_FLD				= "CT.BIZ_RLM";
	public static final String BIZ_FLD0				= "ALL";		// 전체
	
	// 사업대상
	public static final String BIZ_TRGT				= "CT.SPRT_TRGT";
	public static final String BIZ_TRGT0			= "ALL";		// 전체
	
	// 사업대상연령
	public static final String BIZ_TRGT_AGE			= "CT.SPRT_AGE";
	
	// 사업대상업력 사업대상창업기간
	public static final String BIZ_TRGT_FNTN_PD		= "CT.FNTN_WHL";

	// 지원신청구분코드
	public static final String SPRT_APLY_SE			= "CT.SPRT_APLY_SE";
	public static final String SPRT_APLY_SE_BEFORE	= "SB"; // 투자유치 전
	public static final String SPRT_APLY_SE_AFTER	= "SA"; // 투자유치 후
	public static final String SPRT_APLY_SE_CROWD	= "SC"; // 크라우드펀딩
	
	// KODATA DB 연계 관련
	// 기관ID값
	public static final String INST_KODATA		= "KODATA";
	public static final String INST_APFS		= "APFS";
		
	// 송수신 구분
	public static final String TRSMRCV_SE		= "CT.TRSMRCV_SE";
	public static final String TRSMRCV_TRSM		= "TRSM";
	public static final String TRSMRCV_RCV		= "RCV";
	
	// 연계 코드
	public static final String LINK_SE		= "CT.LINK_SE";
	public static final String LINK_50D1	= "50D1";
	public static final String LINK_50D2	= "50D2";
	public static final String LINK_5038	= "5038";
	public static final String LINK_5039	= "5039";
	public static final String LINK_5041	= "5041";
	public static final String LINK_5056	= "5056";
	public static final String LINK_5057	= "5057";
	public static final String LINK_5058	= "5058";
	public static final String LINK_5062	= "5062";
	public static final String LINK_5063	= "5063";
	public static final String LINK_50TA	= "50TA";
	public static final String LINK_50TB	= "50TB";
	public static final String LINK_50TF	= "50TF";
	public static final String LINK_50TG	= "50TG";
	public static final String LINK_50TH	= "50TH";
	public static final String LINK_50TI	= "50TI";
	public static final String LINK_50TK	= "50TK";
	public static final String LINK_50TL	= "50TL";
	public static final String LINK_50PA	= "50PA";
	
	// 처리결과
	public static final String PRCS_RSLT		= "CT.PRCS_RSLT";
	public static final String PRCS_RSLT_E01	= "E01";	// 다운로드 - 연결 실패 
	public static final String PRCS_RSLT_E02	= "E02";	// 다운로드 - 폴더없음
	public static final String PRCS_RSLT_E03	= "E03";	// 다운로드 - 파일없음
	public static final String PRCS_RSLT_E04	= "E04";	// 다운로드 - 다운로드 실패
	public static final String PRCS_RSLT_S00	= "S00";	// 다운로드 - 다운로드 성공
	
	public static final String PRCS_RSLT_E11	= "E11";	// DB업로드 - 파일없음
	public static final String PRCS_RSLT_E12	= "E12";	// DB업로드 - 압축실패
	public static final String PRCS_RSLT_E13	= "E13";	// DB업로드 - 
	public static final String PRCS_RSLT_E14	= "E14";	// DB업로드 - SQL오류
	public static final String PRCS_RSLT_E15	= "E15";	// DB업로드 - 삭제 실패
	public static final String PRCS_RSLT_E16	= "E16";	// DB업로드 - 삭제 실패
	public static final String PRCS_RSLT_S10	= "S10";	// DB업로드 - DB업로드 성공
	
	public static final String PRCS_RSLT_E21	= "E21";	// 업로드 - 파일입출력오류
	public static final String PRCS_RSLT_E22	= "E22";	// 업로드 - 연결 실패
	public static final String PRCS_RSLT_E23	= "E23";	// 업로드 - 폴더없음
	public static final String PRCS_RSLT_E24	= "E24";	// 업로드 - 폴더없음
	public static final String PRCS_RSLT_E25	= "E25";	// 업로드 - 업로드 실패
	public static final String PRCS_RSLT_E26	= "E26";	// 업로드 - 삭제 실패
	public static final String PRCS_RSLT_S20	= "S20";	// 업로드 - 업로드 성공
	
	// 마이페이지 - 신청내역
	public static final String MYPG_APLY_IR		= "IR"; // IR지원현황
	public static final String MYPG_APLY_BM		= "BM"; // 북마크
	public static final String MYPG_APLY_MT		= "MT"; // 미팅신청내역
	public static final String MYPG_APLY_FD		= "FD"; // 경영체지원현황
	// 마이페이지 신청구분
	public static final String MYPG_APLY_SE     = "CT.MYPG_APLY_SE";
	// 마이페이지 그룹관리 구분
	public static final String MYPG_GROUP_SE    = "CT.MYPG_GROUP_SE";
	
	// 마이페이지 - 그룹관리
	public static final String MYPG_GROUP_CODE	= "GC"; // 그룹코드
	public static final String MYPG_GROUP_MMBR	= "GM"; // 멤버관리
	public static final String MYPG_GROUP_ROLE	= "GR"; // 권한관리

	// 마이페이지 - IR작성하기
	public static final String MYPG_ENTIR_DASH	= "ED"; // 대시보드
	public static final String MYPG_ENTIR_INFO	= "EI"; // 상세정보
	
	// 지원신청종류
	public static final String PRGRM_DISCUSS   = "SA000"; // 상담신청		
	public static final String PRGRM_PURCHASE  = "SA001"; // 구매상담회		
	public static final String PRGRM_MENTORING = "SA002"; // 수출초보기업 멘토링데이		
	public static final String PRGRM_POPSTORE  = "SA003"; // 팝업스토어		
	public static final String PRGRM_DOMEXPO   = "SA004"; // 국내 대규모 박람회 참가		
	public static final String PRGRM_OVSEXPO   = "SA005"; // 해외 박람회 참가		
	public static final String PRGRM_USRMKT    = "SA006"; // 수요자 제안형 마케팅		
	public static final String PRGRM_INFMKT    = "SA007"; // 인플루언서 마케팅		
	public static final String PRGRM_PRCMKT    = "SA008"; // PR·콘텐츠 마케팅		
	public static final String PRGRM_ONLINE    = "SA019"; // 온라인교육		
	public static final String PRGRM_SCALEUP   = "SA011"; // 스케일업 프로그램		
	public static final String PRGRM_AFTER     = "SA012"; // 후속투자 유치지원		
	public static final String PRGRM_CFRNC     = "SA013"; // 농식품 산업 투자생태계 활성화 컨퍼런스		
	public static final String PRGRM_COATING   = "SC014"; // 현장코칭 지원		
	public static final String PRGRM_CONSULT   = "SC015"; // 컨설팅 비용지원		
	public static final String PRGRM_FEE       = "SC016"; // 수수료 비용지원
	
	// 지원신청 - 투자전지원인지 확인하는 함수
	public static boolean isBeforeCode( String code ) {
		return CommUtils.isEqual(CodeConst.SPRT_APLY_SE_BEFORE, code);
	};
	// 지원신청 - 투자후지원인지 확인하는 함수
	public static boolean isAfterCode( String code ) {
		return CommUtils.isEqual(CodeConst.SPRT_APLY_SE_AFTER, code);
	};
	// 지원신청 - 크라우드펀딩인지 확인하는 함수
	public static boolean isCrowdCode( String code ) {
		return CommUtils.isEqual(CodeConst.SPRT_APLY_SE_CROWD, code);
	};
	// 지원신청종류 - 상담신청인지 확인하는 함수
	public static boolean isDscsnCode( String code ) {
		return CommUtils.isEqual(CodeConst.PRGRM_DISCUSS, code);
	};
	// 지원신청종류 - 현장코칭인지 확인하는 함수
	public static boolean isCoatingCode( String code ) {
		return CommUtils.isEqual(CodeConst.PRGRM_COATING, code);
	};
	// 지원신청종류 - 수수료지원인지 확인하는 함수
	public static boolean isFeeCode( String code ) {
		return CommUtils.isEqual(CodeConst.PRGRM_FEE, code);
	};
	// 지원신청종류 - 컨설팅지원인지 확인하는 함수
	public static boolean isConsultCode( String code ) {
		return CommUtils.isEqual(CodeConst.PRGRM_CONSULT, code);
	};
	// 지원신청 진행상태 코드 반환 함수
	public static String getSprtPrgrsCode(String sprtSeCd, String act) {
		String prgrsCd = null;
		if      (isBeforeCode(sprtSeCd)) prgrsCd = "B";
		else if (isAfterCode (sprtSeCd)) prgrsCd = "A";
		else if (isCrowdCode (sprtSeCd)) prgrsCd = "C";
		
		if (prgrsCd == null)
			return null;
		
    	// 제출인 경우
    	if (CommUtils.isEqual(CommConst.SUBMIT, act))
    		return prgrsCd + "10";
    	// 임시저장인 경우
    	else
    		return prgrsCd + "00";
	}
	// 회원가입단계 (공통코드에 없음)
	public static final String JOIN_CHOOSE   = "CHOS"; // 가입유형선택   
	public static final String JOIN_AGREE    = "AGRE"; // 약관동의     
	public static final String JOIN_USRFORM  = "FUSR"; // 개인정보입력  
	public static final String JOIN_BIZFORM  = "FBIZ"; // 기업정보입력     
	public static final String JOIN_DONE     = "DONE"; // 가입완료
	
	// 230801 LHB. ID/PW 찾기 구분
	public static final String FIND_ID		= "FID";	// 아이디 찾기
	public static final String FIND_PSWD	= "FPSWD";	// 비밀번호 찾기
	
	// 지원신청단계 (공통코드에 없음)
	public static final String SPRT_AGREE  = "AGRE"; // 약관동의     
	public static final String SPRT_CHOOSE = "CHOS"; // 지원사업선택   
	public static final String SPRT_FORM   = "FORM"; // 신청서작성    
	public static final String SPRT_FILE   = "FILE"; // 제출서류     
	public static final String SPRT_APPLY  = "APLY"; // 접수완료     
	
	// 상담신청 관련
	public static final String DSCSN_CODE  = "SA000";
	public static final String DSCSN_NAME  = "상담신청";
	public static final String DSCSN_GROUP = "PT0";
	
	// IR작성상태
	public static final String PRGRS_WRITING  = "00"; // 작성중
	public static final String PRGRS_COMPLETE = "10"; // 작성완료

	// 신청상태
	public static final String APLY_STUS_TMPSAVE  = "00"; // 임시저장
	public static final String APLY_STUS_WAITING  = "10"; // 접수대기
	public static final String APLY_STUS_SUBMIT   = "20"; // 접수완료
	public static final String APLY_STUS_REJECT   = "90"; // 반려
	
	// 진행상태
	public static final String STATUS_TMPSAVE = "00"; // 임시저장
	public static final String STATUS_WAITING = "10"; // 심사필요
	public static final String STATUS_REJECT  = "20"; // 반려
	public static final String STATUS_SPLMNT  = "30"; // 보완요청
	public static final String STATUS_REVIEW  = "40"; // 심사완료
	public static final String STATUS_FINISH  = "90"; // 사업종료
	
	public static final String PAPE_GROUP_BEFORE  = "D00"; // 지원신청서류: 투지유치 전 지원
	public static final String PAPE_GROUP_AFTER   = "D01"; // 지원신청서류: 투지유치 후 지원
	public static final String PAPE_GROUP_CROWD   = "D02"; // 지원신청서류: 농식품 크라우드펀딩 지원

	public static final String PAPE_TYPE_ENT      = "20"; // 서류신청구분: 경영체
	public static final String PAPE_TYPE_USR      = "40"; // 서류신청구분: 개인
	
	// 지원구분별 서류그룹 반환 함수
	public static String getPapeGroup(String sprtSeCd) {
		if      (isAfterCode (sprtSeCd)) return PAPE_GROUP_AFTER;
		else if (isCrowdCode (sprtSeCd)) return PAPE_GROUP_CROWD;
		else if (isBeforeCode(sprtSeCd)) return PAPE_GROUP_BEFORE;
		return null;
	}
	// 서류신청타입 반환 함수
	public static String getPapeType(String bizYn) {
		return (CommConst.YES.equals(bizYn) ? PAPE_TYPE_ENT : PAPE_TYPE_USR);
	}

    public static final String BZENTY_TYPE_COP = "1";  // 법인기업
    public static final String BZENTY_TYPE_IND = "2";  // 개인기업
    public static final String BZENTY_TYPE_USR = "3";  // 개인회원
    
    // 그룹관리 권한항목
    public static final String GROUP_AUTH_RESTRICT = "R";  // 제한
    public static final String GROUP_AUTH_VIEW     = "V";  // 뷰어
    public static final String GROUP_AUTH_MODIFY   = "M";  // 수정
    
    // 그룹관리 조회가능여부
    public static boolean isGroupViewable(String code) {
    	return GROUP_AUTH_VIEW.equals(code) ||
    		   GROUP_AUTH_MODIFY.equals(code);
    }
    // 그룹관리 수정가능여부
    public static boolean isGroupEditable(String code) {
    	return GROUP_AUTH_MODIFY.equals(code);
    }
    
    // 그룹관리 권한설정메뉴
    public static final String GROUP_MENU_BZENTY   = "MU_USR_MYP0001"; // 기업정보
    public static final String GROUP_MENU_IRREGIST = "MU_USR_MYP0101"; // IR작성하기
    public static final String GROUP_MENU_MATCHING = "MU_USR_MYP0201"; // 매칭설정
    public static final String GROUP_MENU_SPRT     = "MU_USR_MYP0301"; // 신청내역
    public static final String GROUP_MENU_IRREVIEW = "MU_USR_MYP0401"; // IR검토의견등록

    // 그룹관리 권한설정 메뉴목록 (KEY순서대로 정렬되어야함)
    public static final String[] GROUP_AUTH_MENUS = new String[] {
        GROUP_MENU_BZENTY   , // 기업정보
        GROUP_MENU_IRREGIST , // IR작성하기
        GROUP_MENU_MATCHING , // 매칭설정
        GROUP_MENU_SPRT     , // 신청내역
        GROUP_MENU_IRREVIEW   // IR검토의견등록
    };
    
    // 업체분야코드
    public static final String ENT_RLM_INVT = "I"; // 투자분야
    public static final String ENT_RLM_BIZ  = "B"; // 사업분야
    
    // 업체구분에 따른 권한ID 반환
    public static String getBzentyRole(String bzentySeCd) {
    	if      (ENT_EIV.equals(bzentySeCd))	return CommConst.USER_ROLE_INV; // 투자자
    	else if (ENT_EBZ.equals(bzentySeCd))	return CommConst.USER_ROLE_ENT; // 경영체
    	else if (ENT_EIS.equals(bzentySeCd))	return CommConst.USER_ROLE_RIS; // 유관기관
    	else                                    return CommConst.ROLE_RESTRICTED;
    }
}
