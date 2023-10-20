package business.sys.log.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO 클래스] - 로그관리
 *
 * @class   : LogVO
 * @author  : Jh
 * @since   : 2023.07.27
 * @version : 1.1
 *  private String progUrl; >> 이전 버전 테이블 컬럼명으로 신규 컬럼명으로 추가
 *  private String cntnDd;  >> 이전 버전 테이블 컬럼명으로 신규 컬럼명으로 추가
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LogVO extends BaseModel {

    // 일련번호
    private Long sn;
    // 사용자번호
    private String userNo;
    // IP주소
    private String ipAddr;
    // 서버명
    private String srvrNm;
    // 시스템구분
    private String sysCd;

    /* ========== 로그인이력 ========== */
    // 로그인일시
    private String lgnDt;
    // 로그인연도
    private String lgnYr;
    // 로그인월
    private String lgnMm;
    // 로그인일
    private String lgnDd;
    // 로그인상태
    private String lgnStusCd;

    /* ========== 접속이력 ========== */
    // 프로그램URL
    private String progUrl;
    // 접속일
    private String cntnDd;
    
    // 프로그램URL
    private String prgrmUrl;
    // 프로그램URL접속명
    private String prgrmNm;
    // 접속구분코드
    private String cntnSeCd;
    // 접속일시
    private String cntnDt;
    // 접속연도
    private String cntnYr;
    // 접속월
    private String cntnMm;
    // 접속일
    private String cntnDay;

    /* ========== 파일다운로드이력 ========== */
    // 첨부파일일련번호
    private Long atchFileSn;
    // 다중 첨부파일일련번호
    private List<Long> atchFileSns;
    // 다운로드사유
    private String downResn;

    /* ========== 정보보안이력 ========== */
    // 접속구분명
    private String cntnSeNm;
    // KEY1
    private String key1;
    // KEY2
    private String key2;
    // KEY3
    private String key3;
    // KEY4
    private String key4;

	// 검색조건
    private String srchStDt;
    private String srchEnDt;
    private String srchText;

    // 세션사용자번호
    private String gsUserNo;

    // 저장처리용 데이터
    private List<LogVO> logList;

    // 2022.01.07 LSH 문서번호
    private String dcmtNo;
    // 2022.01.07 LSH 세부문서번호
    private String dtlDcmtNo;
    // 2022.01.07 LSH 문서구분
    private String dtySeCd;
    // 2022.02.15 CSLEE 추가
    private String userNm;
    private String fileNm;
    private String dtySeNm;
    // 세션사용자이름
    private String gsUserNm;
}