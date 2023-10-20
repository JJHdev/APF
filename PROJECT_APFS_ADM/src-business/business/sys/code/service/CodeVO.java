package business.sys.code.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO 클래스] - 코드관리
 *
 * @class   : CodeVO
 * @author  : ntarget
 * @since   : 2021.02.08
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
public class CodeVO extends BaseModel {
	
    // 코드ID
    private String cdId;
    // 코드명
    private String cdNm;
    // 코드내용
    private String cdCn;
    // 상위코드ID
    private String upCdId;
    private String orgUpCdId;
    // 상위코드명
    private String upCdNm;
    // 유효시작일자
    private String vldBgngYmd;
    // 유효종료일자
    private String vldEndYmd;
    // 코드순서
    private Long cdOrdr;
    // 사용여부
    private String useYn;
    // 등록자번호
    private String rgtrNo;
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
    
    private String srchType;
    private String srchText;
    private String srchUseYn;
    
    // 세션사용자번호
    private String gsUserNo;
    
    
}