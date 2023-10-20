package business.batch.service;

import java.sql.Timestamp;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - SFTP-연계이력 모델 클래스
 *
 * @class   : IntrfcHstVO
 * @author  : LHB
 * @since   : 2023.06.19
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  -------    --------    ---------------------------
 * 23.06.19      LHB              First Coding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class IntrfcHstVO extends BaseModel {
	
	// 일련번호
	private long sn;
	// 연계코드
	private String linkCd;
	// 제공기관ID
	private String pvsnInstId;
	// 수신기관ID
	private String rcptnInstId;
	// 송수신구분코드
	private String trsmrcvSeCd;
	// 발생일시
	private Timestamp ocrnDt;
	// 처리결과코드
	private String prcsRsltCd;
	
	// 등록자번호
    private String rgtrNo;
    private String rgtrNm;
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
    
}
