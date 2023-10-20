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
 * @class   : BbsVO
 * @author  : LSH
 * @since   : 2023.04.11
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
public class BbsVO extends BaseModel {

    // 게시물번호
    private Long pstNo;
    // 상위게시물번호
    private Long upPstNo;
    // 게시판구분코드
    private String bbsSeCd;
    private String bbsSeNm;
    
    // 게시물분류코드
    private String pstClsfCd;
    private String pstClsfNm;
    // 문의유형코드
    private String inqryTypeCd;
    private String inqryTypeNm;
    // 처리상태코드
    private String prcsSttsCd;
    private String prcsSttsNm;
    // 파일저장 코드
    private String taskSeCd;
    // 처리상태코드
    private String isNext;
    // 처리상태코드
    private String isBefore;
    // 익명
    private String annymty;
    // 게시물제목
    private String pstTtl;
    // 게시물내용
    private String pstCn;
    // 게시시작일자
    private String pstgBgngYmd;
    // 게시종료일자
    private String pstgEndYmd;
    // 게시물연계URL
    private String pstLinkUrl;
    // 고정시작일자 2023.06.01 추가
    private String fixingBgngYmd;
    // 고정종료일자 2023.06.01 추가
    private String fixingEndYmd;
    
    // 이메일주소
    private String emlAddr;
    // 태그내용
    private String tagCn;
    // 조회수
    private Long inqCnt;
    // 추천수
    private Long rcmdtnCnt;
    // 게시물비밀번호
    private String pstPswd;
    // 공지여부
    private String ntcYn;
    // 고정여부
    private String fixingYn;
    // 공개여부
    private String rlsYn;
    // 삭제여부
    private String delYn;
    // 팝업여부
    private String popupYn;
    // 팝업높이
    private Long popupHg;
    // 팝업너비
    private Long popupAr;
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
    private String gsUserNm;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    // 컨텍스트
    private String gsContext;
    
    // 조건정보
    //---------------------------------
    private String srchType;
    private String srchText;
    // 정렬칼럼
    private String ordrField;
    // 정렬방식
    private String ordrType;
    // 페이지 이전, 다음 이동구분
    private String movePage;
    
    @SuppressWarnings("rawtypes")
	private List files;
}
