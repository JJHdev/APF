package business.adm.support.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 지원사업진행현황 모델 클래스
 *
 * @class   : SupportProgressVO
 * @author  : LHB
 * @since   : 2023.04.20
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
public class SupportProgressVO extends BaseModel {
    
	// 일련번호
	private Long sn;
	// 지원신청번호
	private String sprtAplyNo;
	// 프로그램구분코드
	private String prgrmSeCd;
	// 상담센터코드
	private String dscsnCntrCd;
	// 처리차수
	private String prcsCycl;
	// 대면여부
	private String ftfYn;
	// 진행내용
	private String prgrsCn;
	// 담당자명
	private String picNm;
	// 담당자전화번호
	private String picTelno;
	// 행사번호
	private String evntNo;
	// 진행상태코드
	private String prgrsSttsCd;
	// 진행세부상태코드
	private String prgrsDetailSttsCd;
	// 처리일자
	private String prcsYmd;
	
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
    
    @SuppressWarnings("rawtypes")
	private List files;
}
