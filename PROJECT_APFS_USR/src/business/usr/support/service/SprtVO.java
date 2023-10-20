package business.usr.support.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.file.service.BizFileVO;
import common.base.BaseModel;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 투자지원신청 모델 클래스
 *
 * @class   : SprtVO
 * @author  : LSH
 * @since   : 2023.05.22
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
public class SprtVO extends BaseModel {
	
    // 지원신청번호
    private String sprtAplyNo;
    // 지원신청구분코드
    private String sprtSeCd;
    private String sprtSeNm;
    // 신청업체번호
    private String bzentyNo;
    // 신청업체명
    private String bzentyNm;
    // 신청사용자번호
    private String userNo;
    // 신청사용자명
    private String userNm;

    // 개인정보수집동의여부
    private String prvcClctAgreYn;
    // 통합관리시스템동의여부
    private String unityMngSysAgreYn;
    
    // 사업자등록번호
    private String brno;
    // 설립일자
    private String fndnYmd;
    // 대표자명
    private String rprsvNm;

    // 직원수
    private Long empCnt;
    // 법인등록번호
    private String crno;
    // 생년월일
    private String brdt;
    // 성별
    private String sexdstn;
    // 소재지주소
    private String lctnAddr;
    // 소재지상세주소
    private String lctnDaddr;
    // 대표전화번호
    private String rprsTelno;
    // 팩스번호
    private String fxno;
    // 업체유형코드
    private String bzentyTypeCd;
    // 사업분야
    private String bizFld;
    // 홈페이지주소
    private String hmpgAddr;
    // 투자희망금액
    private Long invtHopeAmt;
    // 주요사업내용
    private String mainBizCn;
    // 핵심아이템내용
    private String coreItmCn;
    // 사업내용
    private String bizCn;
    // 담당자명
    private String picNm;
    // 담당자부서명
    private String picDeptNm;
    // 담당자전화번호
    private String picTelno;
    // 담당자이메일주소
    private String picEmlAddr;

    // 펀드링크URL
    private String fundLinkUrl;
    // 접수일자
    private String rcptYmd;
    // 처리일자
    private String prcsYmd;
    // 비고
    private String rmrk;
    // 진행상태코드
    private String prgrsSttsCd;
    private String prgrsSttsNm;
    // 처리결과내용
    private String prcsRsltCn;
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
    
    // 프로그램번호
    // 프로그램명
    private String prgrmNo;
    private String prgrmNm;
    
    // 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    // 세션업체회원여부
    private String gsBzentyYn;
    
    // 기본조건
    //---------------------------------
	// 화면단계
    private String stepNo;
    private String stepCd;
    private String stepNm;

    // 저장항목
    //---------------------------------
    // 매출액 목록
    private List<SprtFnnrVO> fnnrList;
    // 투자조합 목록
    private List<SprtMxtrVO> mxtrList;
	// 사업분야(다중선택)
    private List<String> bizFldCode;

    // 검증용 서류코드 목록
    private List<BizFileVO> papeList;
    // 저장대상 신청파일목록
    private List<BizFileVO> saveFiles;
    // 삭제대상 신청파일목록
    private List<BizFileVO> removeFiles;

    // 추가항목
    //---------------------------------
    // 접수상태
    private String rcptText;
    // 처리결과
    private String statusNm;
    private String statusCd;
    // 신청자명 (이름/아이디)
    private String aplcntNm;

    // 신청진행현황
    //---------------------------------
    // 일련번호
    private Long sn;
    // 프로그램구분코드
    private String prgrmSeCd;
    private String prgrmSeNm;
    // 상담센터코드
    private String dscsnCntrCd;
    private String dscsnCntrNm;
    // 처리차수
    private Integer prcsCycl;
    // 대면여부
    private String ftfYn;
    // 진행내용
    private String prgrsCn;
    // 행사번호
    private String evntNo;
    // 진행세부상태코드
    private String prgrsDetailSttsCd;
    private String prgrsDetailSttsNm;

    /**
     * 저장시 데이터를 항목에 맞게 REBUILD 한다.
     */
    public void rebuildProperties(HttpSession session) {
    	
    	// 업체번호를 세션정보로 정의
        setBzentyNo(getGsBzentyNo());
    	// 사용자번호를 세션정보로 정의
        setUserNo(getGsUserNo());
        
    	String step = getStepCd(); // 단계코드(신청서작성/제출서류)
    	String act  = getAct   (); // 저장모드(임시저장/제출)
    	
    	// 신청서작성인 경우
    	if (CommUtils.isEqual(CodeConst.SPRT_FORM, step)) {

    		// 동의처리 세션가져오기
	        String ssPsprtSeCd = (String)session.getAttribute(CommConst.SESS_PRVC_SE);
	        String ssPprgrmNo  = (String)session.getAttribute(CommConst.SESS_PRVC_PG);
	        String ssMsprtSeCd = (String)session.getAttribute(CommConst.SESS_MSYS_SE);
	        String ssMprgrmNo  = (String)session.getAttribute(CommConst.SESS_MSYS_PG);
	        
	        // 투자전 지원인 경우
	        if (CodeConst.isBeforeCode(sprtSeCd)) {
	        	if (CommUtils.isEqual(sprtSeCd, ssPsprtSeCd))
	        		setPrvcClctAgreYn(CommConst.YES);
	        }
	        else {
	        	if (CommUtils.isEqual(sprtSeCd, ssPsprtSeCd) &&
	        		CommUtils.isEqual(prgrmNo , ssPprgrmNo ))
	        		setPrvcClctAgreYn(CommConst.YES);
	        	if (CommUtils.isEqual(sprtSeCd, ssMsprtSeCd) &&
	        		CommUtils.isEqual(prgrmNo , ssMprgrmNo ))
	        		setUnityMngSysAgreYn(CommConst.YES);
	        }

			Pattern p = Pattern.compile("[^0-9]");
	        // 설립일 포맷 제거
	    	if (CommUtils.isNotEmpty(getFndnYmd()))
	    		setFndnYmd(p.matcher(getFndnYmd()).replaceAll(""));
			// 전화번호 포맷제거
			if (CommUtils.isNotEmpty(getRprsTelno()))
				setRprsTelno(p.matcher(getRprsTelno()).replaceAll(""));
			if (CommUtils.isNotEmpty(getPicTelno()))
				setPicTelno(p.matcher(getPicTelno()).replaceAll(""));

	    	// 사업분야 (다중선택항목)를 ","로 묶여진 문자열로 변환
			setBizFld(CommUtils.mergeString(getBizFldCode(), ","));
    	}
    	// 제출서류인 경우
    	else if (CommUtils.isEqual(CodeConst.SPRT_FILE, step)) {
    	}
    	
    	// 접수일자 정의 (제출시에만)
    	if (CommUtils.isEqual(CommConst.SUBMIT, act)) {
    		setRcptYmd(CommUtils.getCurDateString());
    	}
    	// 진행상태 정의
   		setPrgrsSttsCd(CodeConst.getSprtPrgrsCode(sprtSeCd, act));
    }
    
    // 저장할 제출서류 있는지 확인
    public boolean existFile() {
    	return 	!CommUtils.isEmptyList(saveFiles  ) ||
    			!CommUtils.isEmptyList(removeFiles) ;
    }

    /**
     * 보완요청 제출시 데이터를 항목에 맞게 REBUILD 한다.
     * - 보완제출시엔 진행상태를 다시 제출상태로 변경한다.
     */
    public void rebuildSplmntProperties() {
    	// 업체번호를 세션정보로 정의
        setBzentyNo(getGsBzentyNo());
    	// 사용자번호를 세션정보로 정의
        setUserNo(getGsUserNo());
        // 단계코드를 제출서류로 정의
        setStepCd(CodeConst.SPRT_FILE);
        // 저장모드 (제출)
        setAct(CommConst.SUBMIT);
    	// 진행상태 정의
   		setPrgrsSttsCd(CodeConst.getSprtPrgrsCode(sprtSeCd, getAct()));
    }

}
