package business.com.kodata.service;

import java.net.URI;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import business.com.CommConst;
import commf.exception.BusinessException;

/**
 *  [컨트롤러클래스] - 한국평가데이터 KODATA 연계 API 모듈
 *
 * @class   : KodataAPI
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 * 
 * -----------------------------------------------------------------------------
 * 서비스별 요청 예제
 * #enpinfo 서비스 (테스트서버)
 * https://testkedex.cretop.com:포트/enpinfo?userid={기관ID}&process=S&bzno=1111111111&pidagryn
 * =Y&jmno={기관서비스전문상세코드}&format=XML
 * 
 * 서비스 응답 전문 (JSON 구성도)
 * {"KED":{"HEADER":{... 응답 헤더 구성 ... },
 *         "CONTENTS":{... 전문 내용 ... }
 *        }
 * }
 * 
 * 요청 파라메터
 * 파라미터명 파라미터 한글명 필수구분 설명 형식
 * userid 아이디 필수 요청자 ID VARCHAR(20)
 * process 처리구분 필수 조회(S) VARCHAR(1)
 * bzno 요청사업자번호 필수(택1) bzno/cono/pid 중 반드시 선택1 VARCHAR(10)
 * cono 요청법인번호 (택1) VARCHAR(13)
 * pid 요청주민번호 (택1) (기업정보 조회시 사용안함) VARCHAR(13)
 * pidagryn 개인정보조회동의여부 필수 Y or N VARCHAR(1)
 * jmno 전문상세코드 필수 요청 서비스 전문상세코드 VARCHAR(20)
 * format 데이터 제공방식 필수 XML or JSON VARCHAR(10)
 * 
 * 응답 헤더(HEADER) 구성
 * # enpinfo 서비스
 * 파라미터명 파라미터 한글명 필수구분 설명 형식
 * userid 아이디 요청자 ID VARCHAR(20)
 * process 처리구분 조회(S) VARCHAR(1)
 * bzno 요청사업자번호 bzno/cono/pid 중 반드시 선택1 VARCHAR(10)
 * cono 요청법인번호 VARCHAR(13)
 * pid 요청주민번호 VARCHAR(13)
 * pidagryn 개인정보조회동의여부 Y or N VARCHAR(1)
 * jmno 전문상세코드 요청 서비스 전문상세코드 VARCHAR(20)
 * format 데이터 제공방식 XML or JSON VARCHAR(10)
 * resptm 응답시간 YYYYMMDDHH24MISS VARCHAR(14)
 * errcd 에러코드 에러공통코드 VARCHAR(2)
 * errctt 에러내용 에러공통코드 VARCHAR(100
 * 
 * 통신결과 에러코드
 * 구분 코드 코드내용 비고
 * 정상 00 OK 정상처리
 * 에러 01 Parameters are required 필수항목 값이 누락된 경우, 요청사업자/법인/주민번호 모두 없는 경우
 * 에러 02 No results found 조회 결과 없음 / KED코드가 없는 경우
 * 에러 03 Invalid credentials 권한을 부여받지 않은 전문상세코드를 요청한 경우
 * 에러 04 Invalid Agreement 주민번호 요청은 했으나 동의 여부값이 'N'인 경우
 * 에러 05 Duplication 요청한 번호에 대해 매칭 기관이 2개 이상인 경우
 * 에러 99 Failure 기타오류
 */
public class KodataAPI {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static String title = "KODATA API";
	
	private KodataEntity entity;
	
	public KodataAPI(KodataEntity entity) {

		this.entity = entity;
		// 개발일 경우 no check certificate 처리 (운영일땐 필요없음)
		if (CommConst.isDev()) {
			HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		} 
	}

	/**
     * KODATA 기본 API 실행
     */
    private JSONObject _openAPI() throws Exception {

		URI uri = UriComponentsBuilder
				.fromUriString(this.entity.getUrl ())
				.path         (this.entity.getPath())
		        .queryParam   ("userid"   , this.entity.getUserid   ())
		        .queryParam   ("process"  , this.entity.getProcess  ())
		        .queryParam   ("bzno"     , this.entity.getBzno     ())
		        //.queryParam   ("cono"     , this.entity.getCono     ())
		        //.queryParam   ("pid"      , this.entity.getPid      ())
		        .queryParam   ("pidagryn" , this.entity.getPidagryn ())
		        .queryParam   ("jmno"     , this.entity.getJmno     ())
		        .queryParam   ("format"   , this.entity.getFormat   ())
				.build()
				.toUri();

		logger.debug(title+" URI : "+ uri.toString());

	    RestTemplate rt = new RestTemplate();
        
	    logger.debug(title+" CONNECT ....");
	    // API 호출
		ResponseEntity<String> res = rt.getForEntity(uri, String.class);
	    
	    logger.debug(title+" RESPONSE : "+res.toString());

	    // API 응답결과 받기
		if (res.getStatusCode() == HttpStatus.OK) {
			JSONParser parser = new JSONParser();
	        return (JSONObject) parser.parse(res.getBody());
		}
		return null;
    }
    
    public void execute(KodataVO kodataVO) {
    	try {
    		JSONObject result = _openAPI();
        	
	        if (result != null) {
		        JSONObject jsonKED = (JSONObject)result.get("KED");
		        if (jsonKED != null) {
		        	
		        	JSONObject jsonHEAD = (JSONObject)jsonKED.get("HEADER");
		        	this.entity.setErrcd ((String)jsonHEAD.get("errcd" ));
		        	this.entity.setErrtt ((String)jsonHEAD.get("errtt" ));
		        	this.entity.setResptm((String)jsonHEAD.get("resptm"));
		        	// 성공인 경우
		        	if ("00".equals(this.entity.getErrcd())) {
			        	JSONObject jsonCONT = (JSONObject)jsonKED.get("CONTENTS");
			        	// Object 변환
			        	kodataVO.fromJSON(jsonCONT);
		        	}
		        	else
		        		throw new BusinessException(title+" ["+this.entity.getErrcd()+"] "+this.entity.getErrtt());
		        }
	        }
    	}
    	catch(BusinessException be) {
    		throw new BusinessException(be);
    	}
    	catch(Exception e) {
    		throw new BusinessException(title+"KODATA 통신오류가 발생하였습니다.", e);
    	}
    }
}
