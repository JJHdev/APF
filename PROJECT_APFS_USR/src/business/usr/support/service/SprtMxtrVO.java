package business.usr.support.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 투자지원신청 투자조합정보 모델 클래스
 *
 * @class   : SprtMxtrVO
 * @author  : LSH
 * @since   : 2023.06.07
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
public class SprtMxtrVO extends BaseModel {
	
    // 지원신청번호
    private String sprtAplyNo;
    // 일련번호
    private Long sn;
    // 투자조합명
    private String invtMxtrNm;
    // 투자일자
    private String invtYmd;
    // 투자자명
    private String invstrNm;
    // 투자금액
    private Long invtAmt;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
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
    // 세션업체회원여부
    private String gsBzentyYn;
    
    // 기본조건
    //---------------------------------
}
