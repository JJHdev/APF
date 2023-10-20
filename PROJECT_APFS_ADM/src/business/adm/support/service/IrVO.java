package business.adm.support.service;

import java.util.List;

import business.adm.invest.service.FundVO;
import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 운영관리-IR지원현황 모델 클래스
 *
 * @class   : IrVO
 * @author  : LHB
 * @since   : 2023.06.08
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 23.06.08      LHB           First Coding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class IrVO extends BaseModel {
    
	// 펀드번호
	private String fundNo;
	// 펀드명
	private String fundNm;
	// 업체번호
	private String bzentyNo;
	// IR공개여부
	private String irRlsYn;
	// 지원상태코드
	private String sprtSttsCd;
	private String sprtSttsCdNm;
	// 지원일자
	private String sprtYmd;
	// 검토일자
	private String rvwYmd;
	
	// 타입
	private String type; // EVNT_INVT (투자자), EVNT_ENT (경영체)
	
	/* 경영체 VO가 개발시점기준 없는 관계로 경영체 정보는 추후에 VO로 관리하도록 수정.*/
	// 경영체 정보
	// 휴대폰 번호
	private String mblTelno;
	// 사업자번호
	private String brno;
	// 소속
	private String bzentyNm;
	// 대표자
	private String rprsvNm;
	// 설립일
	private String fndnYmd;
	// 대표번호
	private String rprsTelno;
	
	// 투자자 정보
	private FundVO fundVO;
	
    // 등록자번호
    private String rgtrNo;
    private String rgtrNm;
    private String rgtrId;
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
    
    @SuppressWarnings("rawtypes")
	private List files;
}
