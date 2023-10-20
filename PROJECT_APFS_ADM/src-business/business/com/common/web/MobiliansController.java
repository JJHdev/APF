package business.com.common.web;

import java.security.MessageDigest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.common.service.MobiliansVO;
import common.base.BaseController;
import common.util.CommUtils;
import mup.mcash.module.common.McashCipher.McashCipher;

/**
 *  [컨트롤러클래스] - KG 모빌리언스 본인인증 Controller
 *
 * @class   : MobiliansController
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
public class MobiliansController extends BaseController {

    @Resource(name="CommService")
    protected CommService commService;
    
    /**
	 *==========================================================
	 * 파일명 : ci_web.jsp
	 * 작성일 : 2020.06
	 * 용  도 : 휴대폰본인확인 Weblink 방식 결제 연동 페이지
	 * 버	전 : 0008

	 * 가맹점의 소스 임의변경에 따른 책임은 모빌리언스에서 책임을 지지 않습니다.
	 * 요청 파라미터 및 결제 후  가맹점측  Okurl로 Return 되는 파라미터와 가맹점 서비스처리 방법은
	 * 연동 매뉴얼을 반드시 참조하세요.
	 * 실서버 전환시 꼭 모빌리언스 기술지원팀으로 연락바랍니다.
	 *==========================================================*/
    @RequestMapping("/com/common/popupMobilians.do")
    public String open(HttpServletRequest request
    		, @ModelAttribute MobiliansVO mobiliansVO
    		, ModelMap model) throws Exception {
    	
    	String context = CommBizUtils.getDomain(request)+request.getContextPath();
    	String tradeid = CommUtils.getCurrTimeStampString();
    	
    	mobiliansVO.setPAY_MODE    (CommConst.MOBILIANS_PAYMODE);		//[필수] 연동시 테스트,실결제구분 ( 00 : 테스트결제, 10 : 실거래결제 )
    	mobiliansVO.setSiteurl     (CommConst.MOBILIANS_SITEURL);		//[필수] 가맹점도메인
    	mobiliansVO.setCI_SVCID    (CommConst.MOBILIANS_SVCID  );		//[필수] 서비스아이디
    	mobiliansVO.setCI_Mode     (CommConst.MOBILIANS_CIMODE );		//[필수] 61:SMS발송  67:SMS미발송 (기타 모드는 연동매뉴얼 참조)
    	mobiliansVO.setOkurl       (context+CommConst.MOBILIANS_OKURL  );	    //[필수] 성공URL : 완료통보페이지
    	mobiliansVO.setNotiurl     (context+CommConst.MOBILIANS_NOTIURL);		//[필수] 성공URL : 완료통보페이지
    	mobiliansVO.setCryptyn     (CommConst.MOBILIANS_CRYPTYN);		//[필수] 암호화 사용 여부 (default : Y)
    	mobiliansVO.setKeygb       (CommConst.MOBILIANS_KEYGB  );		//[필수] 암호화 Key 구분 (1 or 2 : 가맹점 관리자 등록 후 사용)
    	mobiliansVO.setTradeid     (tradeid); //[필수] 가맹점거래번호 (유니크한 값 세팅)
    	mobiliansVO.setCASH_GB     ("CI"   ); //[결제수단 캐시 구분] 대표결제수단
    	mobiliansVO.setCallback    (""     ); //[선택] SMS,LMS발신번호
    	mobiliansVO.setSmstext     (""     ); //[선택] SMS문구
    	mobiliansVO.setLmstitle    (""     ); //[선택] LMS제목
    	mobiliansVO.setLmstext     (""     ); //[선택] LMS문구
    	mobiliansVO.setSUB_CPID    (""     ); //[선택] SUB대행사 식별코드
    	mobiliansVO.setDI_CODE     (""     ); //[선택] DI웹사이트코드 (12byte)
    	mobiliansVO.setCI_FIXCOMMID(""     ); //[선택] 이동통신사 고정 시 사용
    	mobiliansVO.setCloseurl    (""     ); //[선택] 취소버튼 클릭 시 이동할 페이지 (예:http://www.mcash.co.kr/failurl.jsp)
    	mobiliansVO.setMSTR        (""     ); //[선택] 가맹점콜백변수
    	mobiliansVO.setLOGO_YN     ("N"    ); //[디자인] 가맹점 로고 사용여부
    	mobiliansVO.setCALL_TYPE   ("SELF" ); //[디자인] 결제창 호출방식( SELF 페이지전환 , P 팝업 )

    	// 오픈화면 모드
    	model.addAttribute("mode", CommConst.OPEN);
    	model.addAttribute("form", mobiliansVO);
        return "com/common/popupMobilians";
    }
    
    /**
	 *==========================================================
	 * 파일명 : ci_notiurl.jsp
     * 가맹점에서 구현해야 하는 notiurl 페이지 이며
     * 모빌리언스에서 인증요청에 대한 결과전송을 위해 호출하는 페이지
     * 가맹점 구현부 처리(DB 처리 등)에 대한 결과를 리턴
     * 처리결과 : 성공 시 'SUCCESS', 실패 시 'FAIL' 출력
    	String Svcid		= request.getParameter("Svcid");		// 서비스아이디
    	String Mobilid		= request.getParameter("Mobilid");		// 모빌리언스 거래번호
    	String Signdate		= request.getParameter("Signdate");		// 거래일자
    	String Tradeid		= request.getParameter("Tradeid");		// 상점거래번호
    	String Resultcd		= request.getParameter("Resultcd");		// 결과코드
	 *==========================================================*/
    @RequestMapping("/com/common/notiurlMobilians.do")
    @ResponseBody
    public String notiurl(HttpServletRequest request
    		, @ModelAttribute MobiliansVO mobiliansVO
    		, ModelMap model) throws Exception {

    	// 가맹점 구현 부분
    	// 처리 결과에 따라서 returnMsg에 반드시 "SUCCESS" 또는 "FAIL" 출력
    	String code = mobiliansVO.getResultcd();
    	String text = "FAIL";
    	// 성공처리인 경우
    	if ("0000".equals(code)) {
    		text = "SUCCESS";
    	}
    	return text;
    }
    
    /**
	 *==========================================================
	 * 파일명 : ci_okurl.jsp
	 * 인증 성공시 웹 페이지 전환으로 호출되는 페이지이며 가맹점에서 구현해야하는 화면입니다.
	 * 암호화 사용시  필수 클래스  
	 * mup.mcash.module.common.McashCipher.class 
	 * mup.mcash.module.common.McashSeed.class
    	String Svcid		= request.getParameter("Svcid");		//서비스아이디
    	String Mobilid 		= request.getParameter("Mobilid");		//모빌리언스 거래번호
    	String Signdate		= request.getParameter("Signdate");		//결제일자
    	String Tradeid		= request.getParameter("Tradeid");		//가맹점 거래번호
    	String Name			= request.getParameter("Name");			//이름
    	String No			= request.getParameter("No");			//휴대폰번호
    	String Commid		= request.getParameter("Commid");		//이동통신사
    	String Resultcd		= request.getParameter("Resultcd");		//결과코드
    	String Resultmsg	= request.getParameter("Resultmsg");	//결과메시지
    	String Cryptyn		= request.getParameter("Cryptyn");		//암호화 사용 여부 (default : Y)
    	String Keygb		= request.getParameter("Keygb");		//암호화 Key 구분 (1 or 2 : 가맹점 관리자 등록 후 사용)
    	String Socialno		= request.getParameter("Socialno");		//생년월일
    	String Sex			= request.getParameter("Sex");			//성별 (남성:M, 여성:F)
    	String Foreigner	= request.getParameter("Foreigner");	//외국인여부 (외국인 : Y)
    	String Ci			= request.getParameter("Ci");			//CI
    	String Di			= request.getParameter("Di");			//DI
    	String CI_Mode		= request.getParameter("CI_Mode");		//CI_Mode 41:LMS문구설정, 51:SMS문구설정, 61:SMS발송
    	String DI_Code		= request.getParameter("DI_Code");		//웹사이트코드
    	String Mac			= request.getParameter("Mac");			//검증키
    	String MSTR			= request.getParameter("MSTR");			//가맹점 확장 변수
	 *==========================================================*/
    @RequestMapping("/com/common/okurlMobilians.do")
    public String okurl(HttpServletRequest request
    		, HttpSession session
    		, @ModelAttribute MobiliansVO mobiliansVO
    		, ModelMap model) throws Exception {

    	request.setCharacterEncoding("EUC-KR");
    	
    	String Name			= request.getParameter("Name");			//이름
    	String No			= request.getParameter("No");			//휴대폰번호
    	String Commid		= request.getParameter("Commid");		//이동통신사
    	String Resultcd		= request.getParameter("Resultcd");		//결과코드
    	String Resultmsg	= request.getParameter("Resultmsg");	//결과메시지
    	String Cryptyn		= request.getParameter("Cryptyn");		//암호화 사용 여부 (default : Y)
    	String Keygb		= request.getParameter("Keygb");		//암호화 Key 구분 (1 or 2 : 가맹점 관리자 등록 후 사용)
    	String Socialno		= request.getParameter("Socialno");		//생년월일
    	String Sex			= request.getParameter("Sex");			//성별 (남성:M, 여성:F)
    	String Foreigner	= request.getParameter("Foreigner");	//외국인여부 (외국인 : Y)
    	String Ci			= request.getParameter("Ci");			//CI
    	String Di			= request.getParameter("Di");			//DI
    	
    	/*********************************************************************************
    	' Okurl 암호화 사용 시 사용자정보 암호문 전달
    	' 주) 고유KEY 설정 시 비밀키 : "가맹점 고유 KEY + CI_SVCID 앞 8자리" (16byte)    	// Keygb 1 or 2
    	'	  "userKey" 항목에 key 세팅
    	*********************************************************************************/
    	String userKey   = CommConst.MOBILIANS_PASSWORD + CommConst.MOBILIANS_SVCID.substring(0,8);
    	
        Name      = McashCipher.decodeString( mobiliansVO.getName(),      userKey );
        No        = McashCipher.decodeString( mobiliansVO.getNo(),        userKey );
        Commid    = McashCipher.decodeString( mobiliansVO.getCommid(),    userKey );
        Socialno  = McashCipher.decodeString( mobiliansVO.getSocialno(),  userKey );
        Sex       = McashCipher.decodeString( mobiliansVO.getSex(),       userKey );
        Foreigner = McashCipher.decodeString( mobiliansVO.getForeigner(), userKey );
        Ci        = McashCipher.decodeString( mobiliansVO.getCi(),        userKey );
        Di        = McashCipher.decodeString( mobiliansVO.getDi(),        userKey );

        mobiliansVO.setResultcd  (Resultcd  );
        mobiliansVO.setResultmsg (Resultmsg );
        mobiliansVO.setName      (Name      );
        mobiliansVO.setNo        (No        );
        mobiliansVO.setCommid    (Commid    );
        mobiliansVO.setSocialno  (Socialno  );
        mobiliansVO.setSex       (Sex       );
        mobiliansVO.setForeigner (Foreigner );
        mobiliansVO.setCi        (Ci        );
        mobiliansVO.setDi        (Di        );
    	String Svcid     = mobiliansVO.getSvcid();    //서비스아이디
    	String Mobilid   = mobiliansVO.getMobilid();  //모빌리언스 거래번호
    	String Signdate  = mobiliansVO.getSigndate(); //결제일자
    	String Tradeid   = mobiliansVO.getTradeid();  //가맹점 거래번호
    	String Mac       = mobiliansVO.getMac();      //검증키
        
    	/*********************************************************************************
    	* Mac값 위변조 여부 확인 SHA256(Signdate+Di+Ci+Mobilid+Svcid.substring(0, 8)+Svcid.substring(0, 8))
    	**********************************************************************************/
    	String key = Signdate+Di+Ci+Mobilid+Svcid.substring(0, 8)+Svcid.substring(0, 8);
    	MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
    	sha256.update(key.getBytes());
    	byte[] byteData = sha256.digest();
    	String sha = Hex.encodeHexString(byteData);

    	//String result = "정상 데이터";
    	//if( !Mac.equals(sha)){
    	//	result = "데이터가 위·변조되었습니다.";
    	//}
    	/*********************************************************************************
    	* 아래는 결과를 단순히 출력하는 샘플입니다.
    	* 가맹점에서는 부모창 전환등 스크립트 처리등을 하시면 됩니다.
    	**********************************************************************************/
    	if (Mac.equals(sha)) {
    		mobiliansVO.setResult("SUCCESS");
    	}
    	else {
    		mobiliansVO.setResult("FAILURE");
    	}
    	
    	// 검증을 위한 세션 생성
    	//session.setAttribute(CommConst.SESS_MOBILIANS_NM, mobiliansVO.getName());
    	//session.setAttribute(CommConst.SESS_MOBILIANS_NO, mobiliansVO.getNo  ());
        
    	// 결과화면 모드
    	model.addAttribute("mode", CommConst.RESULT);
    	model.addAttribute("form", mobiliansVO);
        return "com/common/popupMobilians";
    }
}
