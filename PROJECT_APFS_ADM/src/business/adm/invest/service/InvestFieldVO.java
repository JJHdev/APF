package business.adm.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 운영관리-투자분야관리 모델 클래스
 *
 * @class   : InvestFieldVO
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 23.06.09      LHB            First Coding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class InvestFieldVO extends BaseModel {
    
	// 투자분야코드
	private String invtFldCd;
	// 투자분야명
	private String invtFldNm;
	// 투자분야내용
	private String invtFldCn;
	// 아이템키워드
	private String itmKwrd;
	// 사용시작일자
	private String useBgngYmd;
	// 사용종료일자
	private String useEndYmd;
	// 코드순서
	private String cdOrdr;
	// 사용여부
	private String useYn;
	
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
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    
    private String srchType;
    private String srchText;
    private String srchBgngYmd;
    private String srchEndYmd;
    private String srchUseYn;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
