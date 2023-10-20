package business.usr.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 펀드정보(모태펀드) 모델 클래스
 *
 * @class   : FundInfoVO
 * @author  : LSH
 * @since   : 2023.04.21
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
public class FundVO extends BaseModel {

    // 펀드번호
    private String fundNo;
    // 펀드명
    private String fundNm;
    // 펀드내용
    private String fundCn;
    // 투자분야코드
    private String invtFldCd;
    private String invtFldNm;
    // 펀드운영규모
    private Long fundOperScale;
    // 조성연도
    private String makeYr;
    // 결성일자
    private String orgzYmd;
    // 투자시작일자
    private String invtBgngYmd;
    // 투자종료일자
    private String invtEndYmd;
    // 조합상태코드
    private String mxtrSttsCd;
    private String mxtrSttsNm;
    // 조합원1
    private String mmbs1;
    // 조합원2
    private String mmbs2;
    // 조합원3
    private String mmbs3;
    // 조합원4
    private String mmbs4;
    // 조합원5
    private String mmbs5;
    // 대표전화번호
    private String rprsTelno;
    // 대표홈페이지주소
    private String rprsHmpgAddr;
    // 사용여부
    private String useYn;
    // 삭제여부
    private String delYn;
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
    
    // 펀드투자자정보
    //---------------------------------
    // 업체번호
    private String invtBzentyNo;
    // 투자업체명
    private String invtBzentyNm;
    // 사업자등록번호
    private String invtBrno;
    // 농금원업체여부
    private String apfsYn;

    // 펀드지원정보(IR지원)
    //---------------------------------
    // 지원업체번호
    private String bzentyNo;
    // 지원업체명
    private String bzentyNm;
    // IR공개여부
    private String irRlsYn;
    // 지원상태코드
    private String sprtSttsCd;
    // 지원일자
    private String sprtYmd;
    // 검토일자
    private String rvwYmd;
    
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
    // 검색모드 (M:매칭서비스 / S:기본검색 / U: IR지원목록)
    private String srchMode;
    // 정렬칼럼
    private String ordrField;
    // 정렬방식
    private String ordrType;
    // 검색텍스트
    private String srchText;
    
    // 검색항목
    //---------------------------------
    // 모집진행상태
    private String prgrsSttsCd;
    // 투자분야 다중선택값
    private List<String> invtFldList;
    // 첨부파일타입
    private String fileType;
    // 모집진행상태 (아이콘값)
    private String prgrsIconCd;
    // 펀드규모 (슬라이드바)
    private String fundScaleCd;
    // 투자자 대표홈페이지
    private String invtHmpgAddr;

    // 펀드지원정보 다중입력값 (저장시사용)
    private List<FundVO> sprtList;
}
