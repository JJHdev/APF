package business.usr.mypage.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 미팅신청 모델 클래스
 *
 * @class   : MeetingVO
 * @author  : LSH
 * @since   : 2023.05.16
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
public class MeetingVO extends BaseModel {

    // 일련번호
    private Long sn;
    // 신청자번호
    private String aplcntNo;
    private String aplcntNm;
    // 대상업체번호
    private String trgtBzentyNo;
    private String trgtBzentyNm;
    // 신청내용
    private String aplyCn;
    // 신청일자
    private String aplyYmd;
    // 응답내용
    private String rspnsCn;
    // 응답일자
    private String rspnsYmd;
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
    
    
    // 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    // Context Path
    private String gsContext;
    
    // 기본조건
    //---------------------------------
    private int meetingCount;
    // 검색항목
    //---------------------------------
    // 정렬방식 (A: 최신순,B: 오래된순)
    private String srchType;
    private String ordrField;
    private String ordrType;
    // 검색기준 (DEFAULT: 경영체기준)
    // - I: 투자자 기준 (마이페이지 - 신청내역 - 미팅신청내역)
    // - E: 경영체 기준 (마이페이지 - 문의내역 - 투자자의 미팅요청내역)
    private String srchMode;
}
