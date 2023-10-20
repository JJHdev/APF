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
 * @class   : DscsnVO
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.1
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
public class SubmitFileVO extends BaseModel {
	
    // 상위 서류 구분코드
    private String dcmntTaskSeCd;
    private String chckDcmntTaskSeCd;
    private String dcmntTaskSeNm;
    // 프로그램명구분 코드
    private String dtlSeCd;
    private String chckDtlSeCd;
    // 신청구분 코드
    private String aplySeCd;
    private String chckAplySeCd;
    private String aplySeNm;
    // 서류구분 코드
    private String dcmntCd;
    private String chckDcmntCd;
    private String dcmntNm;
    // 제출서류 필수 여부    
    private String esntlYn;
    // 제출서류 사용 구분 코드
    private String useYn;
    // 등록자 번호
    private String rgtrNo;
    private String rgtrNm;
    // 등록일 
    private String regYmd;
    // 수정 회원번호
    private String mdfrNo;
    private String mdfrNm;
    // 수정일자
    private String mdfcnYmd;
    
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
    
    // 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    
    private String srchType;
    private String srchText;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
