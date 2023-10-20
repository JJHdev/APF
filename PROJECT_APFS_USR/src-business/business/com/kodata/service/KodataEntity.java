package business.com.kodata.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  [컨트롤러클래스] - 한국평가데이터 KODATA 연계 요청 파라메터 모델
 *
 * @class   : KodataEntity
 * @author  : LSH
 * @since   : 2023.03.08
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
 public class KodataEntity {
	
	private String url;
	private String path;
	private String userid;  // 아이디 필수 요청자 ID VARCHAR(20)
	private String process; // 처리구분 필수 조회(S) VARCHAR(1)
	private String bzno;    // 요청사업자번호 필수(택1) bzno/cono/pid 중 반드시 선택1 VARCHAR(10)
	private String cono;    // [사용안함] 요청법인번호 (택1) VARCHAR(13)
	private String pid;     // [사용안함] 요청주민번호 (택1) (기업정보 조회시 사용안함) VARCHAR(13)
	private String pidagryn;// 개인정보조회동의여부 필수 Y or N VARCHAR(1)
	private String jmno;    // 전문상세코드 필수 요청 서비스 전문상세코드 VARCHAR(20)
	private String format;  // 데이터 제공방식 필수 XML or JSON VARCHAR(10)
	
	private String errcd;   // [응답] 에러코드
	private String errtt;   // [응답] 에러내용
	private String resptm;  // [응답] 응답시간 (YYYYMMDDHH24MIS)
}
