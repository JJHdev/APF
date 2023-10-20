package business.sys.menu.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 메뉴관리 모델 클래스
 *
 * @class   : MenuVO
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
public class MenuVO extends BaseModel {

    // 메뉴ID
    private String menuId;
    // 상위메뉴ID
    private String upMenuId;
    // 메뉴명
    private String menuNm;
    // 메뉴레벨
    private Long menuLvl;
    // 메뉴순서
    private Long menuOrdr;
    // 타겟URL
    private String trgtUrl;
    // 팝업여부
    private String popupYn;
    // 시스템구분
    private String sysCd;
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
    private String srchUpId;
    private String underMenuId;
    private String underNotMenuId;
    private String srchText;
    private String srchNotId;
    private String srchRoleId;
    
    // 세션사용자번호
    private String gsUserNo;
}

