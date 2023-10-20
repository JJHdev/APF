package business.adm.inform.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 운영관리-배너관리 모델 클래스
 *
 * @class   : BannerVO
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 23.06.09      LHB           First Coding.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class BannerVO extends BaseModel {

    // 배너번호
	private Long bannerNo;
	// 배너명
	private String bannerNm;
	// 배너URL
	private String bannerUrl;
	// 배너설명
	private String bannerExpln;
	// 게시시작일자
	private String pstgBgngYmd;
	// 게시종료일자
	private String pstgEndYmd;
	// 순서
	private String ordr;
	// 사용 여부
	private String useYn;
	
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
    // 페이지 이전, 다음 이동구분
    private String movePage;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
