package business.usr.mypage.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 사업공고관리 모델 클래스
 *
 * @class   : PbancVO
 * @author  : LSH
 * @since   : 2023.04.30
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
public class MyPbancVO extends BaseModel {

    // 사업공고번호
    private String bizPbancNo;
    // 사업공고명
    private String bizPbancNm;
    // 유관기관업체번호
    private String crdnsBzentyNo;
    // 유관기관업체명
    private String crdnsBzentyNm;
    // 사업자등록번호
    private String brno;
    // 담당부서명
    private String tkcgDeptNm;
    // 담당자명
    private String picNm;
    // 담당자전화번호
    private String picTelno;
    private String picTelnoForm;
    // 사업안내URL
    private String bizGuidanceUrl;
    // 접수구분코드
    private String rcptSeCd;
    private String rcptSeCdNm;
    // 접수시작일시
    private String rcptBgngDt;
    private String rcptBgngYmd;
    private String rcptBgngTm;
    // 접수종료일시
    private String rcptEndDt;
    private String rcptEndYmd;
    private String rcptEndTm;
    // 지원분야코드
    private String sprtFldCd;
    private String sprtFldNm;
    // 사업분야
    private String bizFld;
    private String bizFldNm;
    // 사업대상
    private String bizTrgt;
    private String bizTrgtNm;
    // 사업대상연령
    private String bizTrgtAge;
    private String bizTrgtAgeNm;
    // 사업대상창업기간
    private String bizTrgtFntnPd;
    private String bizTrgtFntnPdNm;
    // 접수방법코드
    private String rcptMthdCd;
    private String rcptMthdNm;
    // 접수방법내용
    private String rcptMthdCn;
    // 신청자격내용
    private String aplyQlfcCn;
    // 신청제외대상내용
    private String aplyExclTrgtCn;
    // 제출서류내용
    private String sbmsnDcmntCn;
    // 지원내용
    private String sprtCn;
    // 공고상태코드
    private String pbancSttsCd;
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
    
    // 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    // 로그인업체의 북마크여부
    private String gsBkmkYn;
    
    // 기본조건
    //---------------------------------
    // 목록유형
    private String sn;
    private String showMode;
    // 검색모드 (M:매칭서비스 / S:기본검색)
    private String divisionBkmk;
    // 검색모드 (00:전체 항목보기/ BM2:북마크내역보기)
    private String srchMode;
    // 정렬칼럼
    private String ordrField;
    // 정렬방식
    private String ordrType;
    // 검색텍스트
    private String srchText;

    // 검색항목
    //---------------------------------
    // 모집상태코드
    private String prgrsSttsCd;    
    // 모집마감 제외여부
    private String exPrgrsCd;
}
