package business.com.common.service;

import common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * [VO클래스] - KG 모빌리언스 본인인증 모델클랙스
 *
 * @class   : MobiliansVO
 * @author  : LSH
 * @since   : 2021.11.16
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class MobiliansVO extends BaseModel {
	
	/*****************************************************************************************
	- 결제수단 캐시 구분
	 *****************************************************************************************/
	private String CASH_GB = "CI"; //대표결제수단
	/*****************************************************************************************
	- 필수 입력 항목 
	 *****************************************************************************************/
	private String PAY_MODE ;		//연동시 테스트,실결제구분 ( 00 : 테스트결제, 10 : 실거래결제 )
	private String Siteurl  ;		//가맹점도메인
	private String Tradeid  ;		//가맹점거래번호 (유니크한 값 세팅)
	private String CI_SVCID ;		//서비스아이디
	private String CI_Mode  ;		// 61:SMS발송  67:SMS미발송 (기타 모드는 연동매뉴얼 참조)
                                   // CI_Mode 41:LMS문구설정, 51:SMS문구설정, 61:SMS발송
	private String Okurl   ; //성공URL : 완료통보페이지
	private String Notiurl ; //성공URL : 완료통보페이지
	private String Cryptyn ; //암호화 사용 여부 (default : Y)
	private String Keygb   ; //암호화 Key 구분 (1 or 2 : 가맹점 관리자 등록 후 사용)

	/*****************************************************************************************
	- 선택 입력 항목 
	 *****************************************************************************************/
	private String Callback ;		//SMS,LMS발신번호
	private String Smstext  ;		//SMS문구
	private String Lmstitle ;		//LMS제목
	private String Lmstext  ;		//LMS문구
	private String SUB_CPID     ;	//SUB대행사 식별코드
	private String DI_CODE      ;	//DI웹사이트코드 (12byte) DI_Code로 들어오는지 확인할것
	private String CI_FIXCOMMID ;	//이동통신사 고정 시 사용
	private String Closeurl     ;	//취소버튼 클릭 시 이동할 페이지 (예:http://www.mcash.co.kr/failurl.jsp)
	private String Cryptokurl   ;	//취소버튼 클릭 시 이동할 페이지 (예:http://www.mcash.co.kr/failurl.jsp)
	private String MSTR         ;	//가맹점콜백변수
								//가맹점에서 추가적으로 파라미터가 필요한 경우 사용하며 &, % 는 사용불가 ( 예 : MSTR="a=1|b=2|c=3" )
	/*****************************************************************************************
	 - 디자인 관련 선택항목 ( 향후 변경될 수 있습니다 )
	 *****************************************************************************************/
	private String LOGO_YN   ;		//가맹점 로고 사용여부 ( 가맹점 로고 사용시 'Y'로 설정, 사전에 모빌리언스에 가맹점 로고 이미지가 있어야함 )
	private String CALL_TYPE ;		//결제창 호출방식( SELF 페이지전환 , P 팝업 )

	private String Svcid		; //서비스아이디
	private String Mobilid 		; //모빌리언스 거래번호
	private String Signdate		; //결제일자
	private String Resultcd		; //결과코드
	private String Resultmsg	; //결과메시지
	private String Name			; //이름
	private String No			; //휴대폰번호
	private String Commid		; //이동통신사
	private String Socialno		; //생년월일
	private String Sex			; //성별 (남성:M, 여성:F)
	private String Foreigner	; //외국인여부 (외국인 : Y)
	private String Ci			; //CI
	private String Di			; //DI
	private String Mac			; //검증키
	
	private String Result; // 최종결과
}