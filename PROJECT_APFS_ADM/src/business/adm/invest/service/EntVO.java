package business.adm.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 업체정보 모델 클래스
 *
 * @class   : EntVO
 * @author  : LSH
 * @since   : 2023.04.27
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
public class EntVO extends BaseModel {

    // 업체번호
    private String bzentyNo;
    // 상위업체번호
    private String upBzentyNo;
    // 업체구분코드
    private String bzentySeCd;
    private String bzentySeNm;
    // 사업자등록번호
    private String brno;
    private String brnoForm;
    // 법인등록번호
    private String crno;
    // 업체명
    private String bzentyNm;
    // 대표자명
    private String rprsvNm;
    // 설립일자
    private String fndnYmd;
    // 대표전화번호
    private String rprsTelno;
    // 팩스번호
    private String fxno;
    // 업종코드
    private String tpbizCd;
    // 업종명
    private String tpbizNm;
    // 업체유형코드
    private String bzentyTypeCd;
    private String bzentyTypeNm;
    // 업체형태코드
    private String bzentyStleCd;
    private String bzentyStleNm;
    // 투자희망금액
    private Long invtHopeAmt;
    // 이메일주소
    private String emlAddr;
    // 홈페이지주소
    private String hmpgAddr;
    // 우편번호
    private String zip;
    // 소재지주소
    private String lctnAddr;
    // 소재지상세주소
    private String lctnDaddr;
    // 직원수
    private Long empCnt;
    // 관리자메모
    private String mngrMemo;
    // 승인일자
    private String aprvYmd;
    // 반려일자
    private String rjctYmd;
    // 사용상태코드
    private String useSttsCd;
    private String useSttsNm;
    // 위치정보
    private String lcinfo;

    // 대표자생년월일
    private String rprsvBrdt;
    // 대표자성별
    private String rprsvSexdstn;
    private String rprsvSexdstnNm;
    // 그룹코드
    private String groupCd;
    // KODATA코드
    private String kdCd;
    // 직접입력여부 (2023.08.08 추가 - 경영체데이터업로드시 사용)
    private String directInptYn;
    // KODATA에서 입력된 대표자명 (2023.09.06 추가)
    private String rmrk4;
    
    // 일련번호
    private Long sn;

    // 업체규모
    private String bzentyScaleCd;
    private String bzentyScaleNm;

    // IR정보 - IR번호
    private String irNo;
    // IR정보 - 주요사업내용
    private String mainBizCn;
    // IR정보 - 핵심아이템내용
    private String coreItmCn;
    // IR정보 - 사업내용
    private String bizCn;
    // IR정보 - 담당자명
    private String picNm;
    // IR정보 - 담당자전화번호
    private String picTelno;
    // IR정보 - 홍보영상URL
    private String prVidoUrl;
    // IR정보 - 공개여부
    private String rlsYn;
    // IR정보 - 조회수
    private Long inqCnt;
    // IR정보 - 진행상태코드
    private String prgrsSttsCd;
    private String prgrsSttsNm;
    
    // 주요재무정보 - 데이터구분코드
    // 특허상표권현황 - 데이터구분코드
    private String dataSeCd;
    private String dataSeNm;

    // 주요재무정보 - 재무구분코드
    private String fnnrSeCd;
    private String fnnrSeNm;
    // 주요재무정보 - 재무계정코드
    private String fnnrAcntCd;
    private String fnnrAcntNm;
    // 주요재무정보 - 재무일자
    private String fnnrYmd;
    // 주요재무정보 - 재무금액
    private Long fnnrAmt;

    // 특허상표권현황 - 특허구분코드
    private String patentSeCd;
    private String patentSeNm;
    // 특허상표권현황 - 출원인
    private String applnm;
    // 특허상표권현황 - 특허권자
    private String patntrtMan;
    // 특허상표권현황 - 명칭
    private String nm;
    // 특허상표권현황 - 지적재산권등록번호
    private String illtRegNo;
    // 특허상표권현황 - 특허등록일자
    private String patentRegYmd;

    // 정부기타지원이력 - 사업명
    private String bizNm;
    // 정부기타지원이력 - 기관명
    private String instNm;
    // 정부기타지원이력 - 분야내용
    private String fldCn;
    // 정부기타지원이력 - 세부내용
    private String dtlCn;

    // 회사연혁   - 시작일자
    // 대표자이력 - 시작일자
    private String bgngYmd;
    // 회사연혁   - 종료일자
    // 대표자이력 - 종료일자
    private String endYmd;
    // 대표자이력 - 이력내용
    private String hstryCn;
    // 회사연혁 - 내용
    private String cn;
    // 회사연혁 - 비고
    private String rmrk;

    // 경영진정보 - 직위명
    private String jbpsNm;
    // 경영진정보 - 성명
    // 주주현황 - 성명
    private String flnm;
    // 경영진정보 - 연령
    private Long age;
    // 경영진정보 - 경력내용
    private String careerCn;

    // 주주현황 - 투자금액
    // 기타투자금액 - 투자금액
    private Long invtAmt;
    // 주주현황   - 지분율
    private Double qotaRt;
    // 주주현황   - 관계내용
    private String relCn;
    
    // 기타투자금액 - 투자구분코드
    private String invtSeCd;
    private String invtSeNm;
    // 기타투자금액 - 투자연도
    private String invtYr;
    // 기타투자금액 - 투자단계코드
    private String invtStepCd;
    private String invtStepNm;
    private String invtStepCn;
	
    // 소송현황 - 원고명
    private String acusrNm;
    // 소송현황 - 피고명
    private String dfdtNm;
    // 소송현황 - 소송내용
    private String lwstCn;
    // 소송현황 - 소송금액
    private Long lwstAmt;
    
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
    
    // 사업분야 텍스트
    private String bizFldCode;
    private String bizFldText;
    // 투자분야 텍스트
    private String invtFldCode;
    private String invtFldText;
    // 대표이미지번호
    private Long rprsFileSn;
    
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
    // 컨텍스트
    private String gsContext;
    
    // 기본조건
    //---------------------------------
    // 검색모드 (M:매칭서비스 / S:기본검색)
    private String srchMode;
    // 정렬칼럼
    private String ordrField;
    // 정렬방식
    private String ordrType;
    // 검색 텍스트
    private String srchText;
    // 검색 기업유형
    private String srchBzentySeCd;
    // 검색 상태
    private String srchAprvSttsCd;
    // 사용자번호
    private String userNo;
    // 멤버회원여부
    private String memberYn;

    // 검색항목
    //---------------------------------
    // 투자분야 다중선택값
    private List<String> invtFldList;
    // 사업분야 다중선택값
    private List<String> bizFldList;
    // 정부지원사업 이력포함여부
    private String entSptHstYn;
    // 투자희망금액 (슬라이드바)
    private String invtHopeCd;
    // 손익계산서(PLOS)/재무상태표(FNTL) 구분타입
    private String fnnrType;
    // 기준년도 최대값
    private int fnnrMaxYr;
    // 정부지원이력 구분
    private String crdnsCd;
    
}
