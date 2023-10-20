package business.sys.prog.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 프로그램관리 모델 클래스
 *
 * @class   : ProgVO
 * @author  : LSH
 * @since   : 2021.09.05
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
public class ProgVO extends BaseModel {

    // 프로그램ID
    private String prgrmId;
    // 프로그램명
    private String prgrmNm;
    // 프로그램패턴
    private String prgrmUrl;
    // 프로그램타입
    private String prgrmType;
    // 메뉴ID
    private String menuId;
    // 메뉴명
    private String menuNm;
    // 프로그램순서
    private Long prgrmOrdr;
    // 시스템구분
    private String sysCd;
    private String sysNm;
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
    
	// 검색조건
    private String srchSysCd;
    private String srchMenuId;
    private String srchRoleId;
    private String srchText;
    
    // 세션사용자번호
    private String gsUserNo;
}
