package business.usr.mypage.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 북마크정보 모델 클래스
 *
 * @class   : BookmarkVO
 * @author  : LSH
 * @since   : 2023.04.29
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
public class BookmarkVO extends BaseModel {

    // 일련번호
    private Long sn;
    // 업체번호
    private String bzentyNo;
    // 대상업체번호
    private String trgtBzentyNo;
    // 문서번호
    private String docNo;
    // 북마크구분코드
    private String bkmkSeCd;
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
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
}
