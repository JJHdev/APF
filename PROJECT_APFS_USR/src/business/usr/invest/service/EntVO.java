package business.usr.invest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.kodata.service.KodataBizVO;
import business.usr.CodeConst;
import common.base.BaseModel;
import common.file.FileInfo;
import common.util.CommUtils;
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
    private String bzentySeCdOrg;
    
    // 사업자등록번호
    private String brno;
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
    // 소재지 - 시도 공통코드
    private String lctnCd;
    // 소재지 - 시도 공통명칭
    private String lctnNm;
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
    private String lng;
    private String lat;

    // 대표자생년월일
    private String rprsvBrdt;
    // 대표자성별
    private String rprsvSexdstn;
    // 그룹코드 (2023.06.14 추가)
    private String groupCd;
    // KODATA코드 (2023.06.29 추가)
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
    private String fnnrYr;
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
    private String bgngCoYmd;
    // 회사연혁   - 종료일자
    private String endCoYmd;
    // 회사연혁 - 내용
    private String cn;
    // 회사연혁 - 비고
    private String rmrk;

    // 대표자이력 - 시작일자
    private String bgngYmd;
    // 대표자이력 - 종료일자
    private String endYmd;
    // 대표자이력 - 이력내용
    private String hstryCn;
    
    // 경영진정보 - 직위명
    private String jbpsNm;
    // 경영진정보 - 성명
    private String mgtFlnm;
    // 경영진정보 - 연령
    private Long age;
    // 경영진정보 - 경력내용
    private String careerCn;

    // 주주현황 - 투자금액
    // 주주현황 - 성명
    private String flnm;
    // 주주현황   - 지분율
    private Double qotaRt;
    // 주주현황   - 관계내용
    private String relCn;
    
    // 기타투자금액 - 투자금액
    private Long invtAmt;
    private Long summAmt;
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
    
    // 업체분야정보
    //---------------------------------
    private String fldSeCd;
    private String fldCd;

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
    // 검색모드 (M:매칭서비스 / S:기본검색)
    private String srchMode;
    // 정렬칼럼
    private String ordrField;
    // 정렬방식
    private String ordrType;
    // 검색텍스트
    private String srchText;
    // 사용자번호
    private String userNo;
    // KODATA여부
    private String kodataYn;
    // 대표계정여부
    private String rprsYn;
    // 신규등록여부
    private String existYn;
    // 화면구분코드
    private String pageCd;
    // 수정권한여부
    private String updateYn;
    // 조회권한여부
    private String selectYn;
    // 수정대상조회여부
    private String manualYn;
    // 멤버회원여부
    private String memberYn;
    // 추가재무정보 데이터여부
    private String fnnrManualYn;
    // 데이터여부
    private String dataYn;
    // 북마크구분코드
    private String bkmkSeCd;
    
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
    private Integer fnnrMaxYr;
    // 정부지원이력 구분
    private String crdnsCd;
    // 기준년도개수 조건
    private Integer fnnrYrCnt;
    // 재무정보 KODATA 등록여부
    private String fnnrKodataYn;
    // 특허상표권현황 KODATA 등록여부
    private String ptntKodataYn;
    
    // 저장대상 썸네일파일목록
    private List<FileInfo> saveFiles;
    
    // 저장대상 업체분야목록
    private List<EntVO> entRlms;
    
    // 경영체 데이터 업로드 관련 구분자
    //---------------------------------
    private String crdnsAct; // 
    
    // 하위목록
    //---------------------------------
    private List<EntVO> lwstList;      // 소송현황 목록
    private List<EntVO> mgmtList;      // 경영진 목록
    private List<EntVO> rprsHstList;   // 대표자이력 목록
    private List<EntVO> shroldrList;   // 주주현황 목록
    private List<EntVO> coHstList;     // 회사연혁 목록
    private List<EntVO> ptntList;      // 특허상표권현황 목록

    private List<EntVO> othspSumList;  // 정부지원이력 기관별 합계건수 목록
    private List<EntVO> ptntSumList;   // 특허상표권현황 구분별 합계건수 목록
    
    private List<EntVO> invtFndList;   // 농식품모태펀드 투자금액 목록
    private List<EntVO> invtEtcList;   // 기타 투자금액 목록
    
    private List<EntVO>  invtList;     // 투자금액정보 목록
    private List<EntVO>  fnnrList;     // 재무정보 목록
    private List<String> yearList;     // 년도 목록

    private List<EntVO>  fnnrFntlList; // 추가재무정보 재무상태표 수정목록
    private List<EntVO>  fnnrPlosList; // 추가재무정보 손익계산서 수정목록
    
    private EntVO fnnrKoFntl; // 주요재무정보 - 재무상태표
    private EntVO fnnrKoPlos; // 주요재무정보 - 손익계산서
    private EntVO fnnrMnFntl; // 추가재무정보 - 재무상태표
    private EntVO fnnrMnPlos; // 추가재무정보 - 손익계산서
    
    // 재무정보 목록저장용 변수
    private String fnnrAcntCd1;
    private String fnnrAcntCd2;
    private String fnnrAcntCd3;
    private Long   fnnrAmt1;
    private Long   fnnrAmt2;
    private Long   fnnrAmt3;
    
    // 관리자여부
    private boolean admin;
    
    // 저장데이터에 맞게 REBUILD
	public void rebuildProperties() {
		
		Pattern p = Pattern.compile("[^0-9]");
		// 전화번호 포맷제거
		if (CommUtils.isNotEmpty(getRprsTelno()))
			setRprsTelno(p.matcher(getRprsTelno()).replaceAll(""));
		// 팩스번호 포맷제거
		if (CommUtils.isNotEmpty(getFxno()))
			setFxno(p.matcher(getFxno()).replaceAll(""));
		
		// 분야정보를 목록으로 변환한다.
		List<EntVO> list = new ArrayList<EntVO> ();
		
		if (invtFldList != null) {
			for (String code : invtFldList) {
				list.add(EntVO.builder()
						.gsUserNo(getGsUserNo())
						.bzentyNo(getBzentyNo())
						.fldSeCd (CodeConst.ENT_RLM_INVT)
						.fldCd   (code)
						.build   ()
				);
			}
		}
		if (bizFldList != null) {
			for (String code : bizFldList) {
				list.add(EntVO.builder()
						.gsUserNo(getGsUserNo())
						.bzentyNo(getBzentyNo())
						.fldSeCd (CodeConst.ENT_RLM_BIZ)
						.fldCd   (code)
						.build   ()
				);
			}
		}
		if (list.size() > 0)
			setEntRlms(list);
	}
    
    // 저장데이터에 맞게 REBUILD (KODATA 포함)
	public void rebuildProperties(KodataBizVO kodataVO) {
		
		// 분야정보 REBUILD
		rebuildProperties();
		
		if (CommConst.YES.equals(getKodataYn())) {
			setBrno          (kodataVO.getBzno      ()); // 사업자등록번호
			setCrno          (kodataVO.getConoPid   ()); // 법인등록번호
			setFxno          (kodataVO.getFaxNo     ()); // 팩스번호
			setTpbizCd       (kodataVO.getBzcCd     ()); // 업종코드
			setTpbizNm       (kodataVO.getBzcNm     ()); // 업종명
			setBzentyTypeCd  (kodataVO.getEnpTyp    ()); // 업체유형코드
			setBzentyStleCd  (kodataVO.getEnpFcd    ()); // 업체형태코드
			setBzentyScaleCd (kodataVO.getEnpSze    ()); // 업체규모
			setEmlAddr       (kodataVO.getEmail     ()); // 이메일주소
			setHmpgAddr      (kodataVO.getHpageUrl  ()); // 홈페이지주소
			setZip           (kodataVO.getLocZip    ()); // 우편번호
			setLctnAddr      (kodataVO.getLocAddra  ()); // 소재지주소
			setLctnDaddr     (kodataVO.getLocAddrb  ()); // 소재지상세주소
			setEmpCnt        (kodataVO.getLaborerSum()); // 직원수
			
			if (CommUtils.isNotEmpty(kodataVO.getEnpNm    ())) setBzentyNm (kodataVO.getEnpNm    ()); // 업체명
			if (CommUtils.isNotEmpty(kodataVO.getReperName())) setRprsvNm  (kodataVO.getReperName()); // 대표자명
			if (CommUtils.isNotEmpty(kodataVO.getEstbDt   ())) setFndnYmd  (kodataVO.getEstbDt   ()); // 설립일자
			if (CommUtils.isNotEmpty(kodataVO.getTelNo    ())) setRprsTelno(kodataVO.getTelNo    ()); // 대표전화번호
		}
		Pattern p = Pattern.compile("[^0-9]");
		// 사업자번호 포맷제거
		setBrno(p.matcher(getBrno()).replaceAll(""));
		// 전화번호 포맷제거
		setRprsTelno(p.matcher(getRprsTelno()).replaceAll(""));
		// 팩스번호 포맷제거
		if (CommUtils.isNotEmpty(getFxno()))
			setFxno(p.matcher(getFxno()).replaceAll(""));
		// 설립일자 포맷제거
		if (CommUtils.isNotEmpty(getFndnYmd()))
			setFndnYmd(p.matcher(getFndnYmd()).replaceAll(""));
	}
    
    // IR저장데이터에 맞게 REBUILD
	public void rebuildIrProperties(List<Map<String,Object>> fnnrCodes) {
		
		// IR작성 - 대시보드 저장인 경우
		if (CommConst.ACT_IR_INFO.equals(getAct())) {
			
			Pattern p = Pattern.compile("[^0-9]");

			// 담당자연락처 포맷제거
			if (CommUtils.isNotEmpty(picTelno))
				setPicTelno(p.matcher(picTelno).replaceAll(""));

			// 추가재무정보를 금액별 저장단위 List로 변환
			List<EntVO> fnlst = new ArrayList<EntVO> ();
			// 재무상태표 / 손익계산서
			if (!CommUtils.isEmptyList(fnnrList)) {
				for (EntVO fnnr : fnnrList) {
					fnlst.add(_getFnnrItem(fnnrCodes, fnnr, 1));
					fnlst.add(_getFnnrItem(fnnrCodes, fnnr, 2));
					fnlst.add(_getFnnrItem(fnnrCodes, fnnr, 3));
				}
			}
			setFnnrList(fnlst);
		}
		// IR작성 - 상세정보 저장인 경우
		else if (CommConst.ACT_IR_FILE.equals(getAct())) {
			
			// IR공개여부 설정
			setRlsYn(CommConst.YES);
			// 진행상태 설정 (작성완료)
			setPrgrsSttsCd(CodeConst.PRGRS_COMPLETE);
		}
	}
	
	// 재무정보 항목생성 반환
	private EntVO _getFnnrItem(List<Map<String,Object>> codes, EntVO fnnr, int seq) {
		
		String upCdId = CodeConst.FNNR_FNTL_SE;
		if (CodeConst.FNNR_PLOS_CD.equals(fnnr.getFnnrSeCd()))
			upCdId = CodeConst.FNNR_PLOS_SE;
		// 재무정보 계정코드
		Map<String,Object> code = CommBizUtils.getCode(codes, upCdId, "cdOrdr", Long.valueOf(seq));
		if (code == null)
			return null;
		
		EntVO obj = EntVO.builder()
				.dataSeCd   (fnnr.getDataSeCd())
				.fnnrSeCd   (fnnr.getFnnrSeCd())
				.fnnrYr     (fnnr.getFnnrYr())
				.fnnrAmt    ((Long)CommUtils.getMethodValue(fnnr, "fnnrAmt"+seq))
				.fnnrAcntCd ((String)code.get("code"))
				.build      ();
		obj.setMode(fnnr.getMode());
		return obj;
	}
	
	/**
	 * KODATA 연계파일 생성을 위한 데이터 BUILD
	 * 
	 * String 배열 설명:
	 *      사업자등록번호, 사업자등록번호, 법인번호, 업체명, 사업자/법인구분, 거래처삭제여부
	 *      회원가입쪽에서는 새로운 업체 등록이라서 맨 뒤에 두 개 값은 1, N 고정하면 될 것 같습니다!
	 */
	public List<String[]> buildForWriteKodata() {
		
		List<String[]> list = new ArrayList<String[]>();
		
		list.add(new String[] {
			getBrno(), 
			getBrno(), 
			CommUtils.nvl(getCrno()), 
			CommUtils.nvl(getBzentyNm()),
			"1",
			"N" 
		});
		return list;
	}
}
