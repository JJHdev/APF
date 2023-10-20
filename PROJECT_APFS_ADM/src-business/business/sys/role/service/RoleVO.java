package business.sys.role.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 역할관리 모델 클래스
 *
 * @class   : RoleVO
 * @author  : LSH
 * @since   : 2021.09.06
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
public class RoleVO extends BaseModel {

    // 역할ID
    private String roleId;
    // 상위역할ID
    private String upRoleId;
    // 역할명
    private String roleNm;
    // 시스템구분 
    private String sysCd;
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

    // 메뉴ID
    private String menuId;
    // 메뉴명
    private String menuNm;
    // 프로그램ID
    private String prgrmId;
    // 프로그램명
    private String prgrmNm;
    // 사용자번호
    private String userNo;
    // 사용자명
    private String userNm;

	// 검색조건
    private String srchText;
    private String srchUpId;
    private String srchNotId;
    private String srchRoleId;
    private String srchSysCd;
    private String srchMenuId;
    private String srchPrgrmId;
    private String srchUserNo;
    private String srchSysSe;
      
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    
    // 저장처리용 데이터
    private List<RoleVO> roleList;
}
