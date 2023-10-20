package business.adm.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 모태펀드 모델 클래스
 *
 * @class   : FundVO
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class FundVO extends BaseModel {
    
	// 펀드번호
	private String fundNo;
	// 펀드명
	private String fundNm;
	// 펀드내용
	private String fundCn;
	// 투쟈분야코드
	private String invtFldCd;
	private String invtFldCdNm;
	// 펀드운영규모
	private Long fundOperScale;
	// 조성연도
	private String makeYr;
	// 결성일자
	private String orgzYmd;
	// 투자시작일자
	private String invtBgngYmd;
	// 투자종료일자
	private String invtEndYmd;
	// 조합상태코드
	private String mxtrSttsCd;
	// 조합원1
	private String mmbs1;
	// 조합원2
	private String mmbs2;
	// 조합원3
	private String mmbs3;
	// 조합원4
	private String mmbs4;
	// 조합원5
	private String mmbs5;
	// 대표전화번호
	private String rprsTelno;
	// 대표홈페이지주소
	private String rprsHmpgAddr;
	// 사용여부
	private String useYn;
	
	// 업체번호
	private String bzentyNo;
	// 업체구분
	private String bzentySeCd;
	
	// IR번호
	private String irNo;
	// IR공개여부
	private String isRlsYn;
	// 지원상태코드
	private String sprtSttsCd;
	// 지원일자
	private String sprtYmd;
	// 검토일자
	private String rvwYmd;
	
	// (조합원) 일련번호 배열
	private long[] snArr;
	// (조합원) 일련번호
	private long sn;
	// (조합원) 업체번호 배열
	private String[] bzentyNoArr;
	// (조합원) 투자업체명
	private String invtBzentyNm;
	// (조합원) 사업자 등록번호
	private String brno;
	
	
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
    
    private String srchFundNm;
    private String srchInvtFldCd;
    private String srchMakeYr;
    private String srchTextInvt;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
