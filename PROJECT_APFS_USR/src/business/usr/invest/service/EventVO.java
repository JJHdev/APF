package business.usr.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 투자정보관리 - 투자설명회 모델 클래스
 *
 * @class   : EventVO
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
public class EventVO extends BaseModel {

	// 행사번호
	private String evntNo;
	// 행사명
	private String evntNm;
	// 행사내용
	private String evntCn;
	// 행사시작일자
	private String evntBgngYmd;
	// 행사종료일자
	private String evntEndYmd;
	// 행사연도
	private String evntYr;
	// 사용여부
	private String useYn;
	// 삭제여부
	private String delYn;
	
	// 투자자 업체번호
	private String invtBzentyNo;
	// 투자자 업체명
	private String invtBzentyNm;
	// 투자자 등록자
	private String invtRgtrNo;
	private String invtRgtrNm;
	
	// 경영체 업체번호
	private String bzentyNo;
	// 행사참여번호
	private String evntPartcptnNo;
	private String[] evntPartcptnNoArr;
	// 경영체 사업자등록번호
	private String brno;
	// 경영체 업체명
	private String bzentyNm;
	// 경영체 대표자명
	private String rprsvNm;
	// 경영체 전화번호
	private String telno;
	// 경영체 이메일주소
	private String emlAddr;
	// 경영체 주요사업내용
	private String mainBizCn;
	
	// 타입
	private String evntType;	// invt - 투자자, ent - 경영체

	// 투자자(운용사)명
	private String bzentyInvtNm;
	// 사업내용
	private String bizCn;
	private String bizCnArr;
	// 제품내용
	private String prdctCn;
	private String prdctCnArr;
	// 회사내용
	private String coCn;
	private String coCnArr;
	// 종합의견
	private String gnrlzOpnn;
	private String gnrlzOpnnArr;
	// 투자관심정도코드
	private String invtItrstDgreeCd;
	private String[] invtItrstDgreeCdArr;
	// 후속회의의향여부
	private String fllwMtgIntenYn;
	private String[] fllwMtgIntenYnArr;
	// 진행상태
	private String prgrsSttsCd;
	
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
    
    private String srchText;
    private String srchYr;
    
    // IR검토의견 등록 수정여부
    private String irRgstYn;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
