package business.adm.inform.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 게시판 모델 클래스
 *
 * @class   : SrchWordVO
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.2
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
public class SrchWordVO extends BaseModel {

    // 검색어 일련번호
    private Long sn;
    // 검색어
    private String srchWrd;
    // 검색순위
    private Long rank;
    // 검색 수
    private Long count;
    // ip주소
    private String ipAddr;
    
    // 등록자번호
    private String rgtrNo;
    private String rgtrNm;
    // 등록일시
    private String regYmd;
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
    
    // 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    
    // 조건정보
    //---------------------------------
    private String srchType;
    private String srchText;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
