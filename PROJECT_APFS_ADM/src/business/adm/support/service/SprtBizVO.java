package business.adm.support.service;

import java.util.Arrays;
import java.util.List;

import business.adm.invest.service.FundVO;
import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 운영관리-경영체 데이터 업로드 모델 클래스
 *
 * @class   : SprtBizVO
 * @author  : LHB
 * @since   : 2023.07.06
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 23.07.06      LHB           First Coding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SprtBizVO extends BaseModel {
    
	// 지원사업이력
	// 일련번호
	private Long sn;
	private String arrSnStr;
	// 유관기관업체번호
	private String crdnsBzentyNoPrev; // 유관기관 코드값 01, 02, 03, ...
	private String crdnsBzentyNo;
	// 유관기관업체명
	private String crdnsBzentyNm;
	// 업체번호
	private String bzentyNo;
	// 업체명
	private String bzentyNm;
	// 사업연도
	private String bizYr;
	// 사업명
	private String bizNm;
	// 항목명1
	private String artclNm1;
	// 항목내용1
	private String artclCn1;
	// 항목명2
	private String artclNm2;
	// 항목내용2
	private String artclCn2;
	// 항목명3
	private String artclNm3;
	// 항목내용3
	private String artclCn3;
	// 항목명4
	private String artclNm4;
	// 항목내용4
	private String artclCn4;
	// 항목명5
	private String artclNm5;
	// 항목내용5
	private String artclCn5;
	// 공개여부
	private String rlsYn;
	// 삭제여부
	private String delYn;
	// 업로드번호
	private long uldNo;
	
	// 사업자등록번호
	private String brno;
	// 파일명
	private String fileNm;
	// 파일경로
	private String filePath;
	// 저장파일명
	private String strgFileNm;
	// 비고1
	private String rmrk1;
	// 비고2
	private String rmrk2;
	// 비고3
	private String rmrk3;
	// 비고4
	private String rmrk4;
	// 비고5
	private String rmrk5;
	
	// 강제 업로드 여부
	private String directInptYn;
	
	// LHB 23.09.19 IR 투자금액 정보
    //---------------------------------
 	// 투자구분코드 (IAF : 농식품모태펀드, IAR : 기타투자금액)
    private String invtSeCd;
    // 투자연도
    private String invtYr;
    // 투자금액
    private String invtAmt;
	
    // 등록자번호
    private String rgtrNo;
    private String rgtrNm;
    private String rgtrId;
    private String rgtrNmNo;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    private String mdfrNm;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    
    private String srchType;
    private String srchText;
    private String srchBgngYmd;
    private String srchEndYmd;
    
    // IR번호
    private String irNo;
    
    @SuppressWarnings("rawtypes")
	private List files;
    
    public long[] getArrSn() {
    	return Arrays.stream(arrSnStr.split(","))
    				.mapToLong(Long::parseLong)
    				.toArray();
    }
}
