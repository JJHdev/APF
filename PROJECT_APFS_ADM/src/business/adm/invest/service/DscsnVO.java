package business.adm.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 투자지원신청 모델 클래스
 *
 * @class   : SupportVO
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
public class DscsnVO extends BaseModel {
	
	// 상담신청 번호
    private Long sn;
    // 업체명
    private String dscsnAplyYmd;
    // 상담자 이름
    private String cnslrNm;
    // 업체명
    private String bzentyNm;
    // 사업자 등록번호
    private String brno;
    // 대표자 이름
    private String rprsvNm;
    // 생년월일
    private String brdt;
    // 성별
    private String sexdstn;
    // 사업자등록번호
    private String picNm;
    // 대표자명
    private String picDeptNm;
    // 담당자 전화번호
    private String picTelno;
    // 상담내용
    private String dscsnCn;
    // 성별
    private String prcsRsltCn;
    // 상담센터코드
    private String dscsnCntrCd;
    private String dscsnCntrNm;
    // 상담일자
    private String dscsnYmd;
    private String dscsnDttm;
    private String dscsnDate;
    // 상담방법코드
    private String dscsnMthdCd;
    private String dscsnMthdNm;
    // 담당자이메일주소
    private String picEmlAddr;
    // 설립일자
    private String fndnYmd;
    // 직원수
    private Long empCnt;
    private String empCntForm;
    // 업체유형코드
    private String bzentyTypeCd;
    private String bzentyTypeCdNm;
    // 사업분야
    private String bizFld;
    private String bizFldNm;
    // 투자분야명
    private String invtFldNm;
    // 업종구분명
    private String tpbizSeNm;
    // 산업구분명
    private String industSeNm;
    // 전년도 매출금액
    private Long slsAmt;
    private String slsAmtForm;
    // 홈페이지주소
    private String hmpgAddr;
    // 소재지주소1
    private String lctnAddr1;
    // 소재지주소2
    private String lctnAddr2;
    // 사업내용
    private String bizCn;
    // 진행상태코드
    private String prgrsSttsCd;
    // 등록자번호
    private String rgtrNo;
    private String rgtrNm;
    // 등록일자
    private String regYmd;
    private String regDttm;
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    private String mdfrNm;
    // 수정일자
    private String mdfcnYmd;
    private String mdfDttm;
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
