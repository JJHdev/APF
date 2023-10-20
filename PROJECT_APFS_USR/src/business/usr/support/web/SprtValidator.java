package business.usr.support.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.usr.CodeConst;
import business.usr.ValidRule;
import business.usr.file.service.BizFileVO;
import business.usr.support.service.SprtFnnrVO;
import business.usr.support.service.SprtMxtrVO;
import business.usr.support.service.SprtService;
import business.usr.support.service.SprtVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 투자지원신청 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : SprtValidator
 * @author  : LSH
 * @since   : 2023.05.30
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 * Errors 에 대한 사용가능 메서드 
 *     
 *     reject(String errorCode) 
 *     : 전 객체에 대한 글로벌 에러 코드를 추가
 *     
 *     reject(String errorCode, String defaultMessage) 
 *     : 전 객체에 대한 글로벌 에러코드를 추가하고, 
 *       에러코드에 대한 메세지가 존재하지 않을 경우 defaultMessage를 사용
 *     
 *     reject(String errorCode, Object[] errorArgs, String defaultMessage) 
 *     : 전 객체에 대한 글로벌 에러코드를 추가, 
 *       메세지 인자로 errorArgs를 전달, 
 *       에러코드에 대한 메세지가 존재하지 않을 경우 defaultMessage를 사용
 *     
 *     rejectValue(String field, String errorCode) 
 *     : 필드에 대한 에러코드를 추가
 *     
 *     rejectValue(String field, String errorCode, String defaultMessage) 
 *     : 필드에 대한 에러코드를 추가 
 *       에러코드에 대한 메세지가 존재하지 않을 경우 defaultMessage를 사용
 *     
 *     rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) 
 *     : 필드에 대한 에러코드를 추가, 
 *       메세지 인자로 errorArgs를 전달, 
 *       에러코드에 대한 메세지가 존재하지 않을 경우 defaultMessage사용
 */
@Service
public class SprtValidator implements Validator {
	
    @Resource(name="CommService")
    protected CommService commService;
	
    @Resource(name="SprtService")
    protected SprtService sprtService;

    @Override
    public boolean supports(Class<?> type) {
        return SprtVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

    	SprtVO data = (SprtVO) o;
    	
    	String act        = data.getAct       (); // 저장모드(임시저장/제출)
    	String mode       = data.getMode      (); // 처리모드(등록/수정)
    	String stepCd     = data.getStepCd    (); // 단계코드
    	String sprtSeCd   = data.getSprtSeCd  (); // 신청구분코드
    	String prgrmNo    = data.getPrgrmNo   (); // 프로그램번호
    	String gsBzentyYn = data.getGsBzentyYn(); // 업체회원여부
    		
    	// [기본검증]
    	//------------------------------------------------------------------
    	if (CommUtils.isEmpty(sprtSeCd))
            errors.reject("sprtSeCd", "지원신청구분을 확인할 수 없습니다.");
    	if (CommUtils.isEmpty(stepCd))
            errors.reject("stepCd", "현재단계를 확인할 수 없습니다.");
    	if (!CodeConst.isBeforeCode(sprtSeCd) && 
    		CommUtils.isEmpty(prgrmNo))
            errors.reject("prgrmNo", "프로그램번호를 확인할 수 없습니다.");
    	// 1) 저장모드가 정상적인지 확인
    	if (!CommUtils.isEqual(CommConst.SUBMIT , act) &&
    		!CommUtils.isEqual(CommConst.TMPSAVE, act))
			errors.reject("error.sprt.act.notValid");
    	// 1) 처리모드가 정상적인지 확인
    	if (!CommUtils.isEqual(CommConst.INSERT, mode) &&
    		!CommUtils.isEqual(CommConst.UPDATE, mode))
			errors.reject("error.sprt.mode.notValid");
    	// 2) 단계코드가 정상적인지 확인
    	if (!CommUtils.exist(new String[] {
    			CodeConst.SPRT_FORM, 
    			CodeConst.SPRT_FILE}, stepCd))
			errors.reject("error.sprt.step.notValid");
    	// 3) 구분코드가 유효한지 확인
    	if (!commService.existCode(CodeConst.SPRT_APLY_SE, sprtSeCd))
			errors.reject("error.sprt.aplySe.notValid");
    	// 4) 프로그램번호가 유효한지 확인 (상담신청,투자전지원은 제외)
    	if (!CodeConst.isDscsnCode(prgrmNo) &&
    		!CodeConst.isBeforeCode(sprtSeCd) &&
    		!sprtService.existPrgrm(prgrmNo))
			errors.reject("error.sprt.prgrmNo.notValid");
    	// 5) 업체회원만 투자후지원에 접근가능
    	if (CodeConst.isAfterCode(sprtSeCd) && 
    		!CommUtils.isEqual(CommConst.YES, gsBzentyYn))
    		errors.reject("error.sprt.user.notAccess");
    	if (CommUtils.isEqual(CommConst.YES, gsBzentyYn) &&
    		CommUtils.isEmpty(data.getBzentyNo()))
            errors.reject("bzentyNo", "신청업체를 확인할 수 없습니다.");
    	if (CommUtils.isEmpty(data.getUserNo()))
            errors.reject("userNo", "신청사용자를 확인할 수 없습니다.");
    	
    	// 2023.08.08 약관동의 사용일 경우에만 체크
    	if (CommConst.SPRT_AGREE) {
	        // 등록인 경우에만 약관동의 세션을 확인한다
	        if (CommUtils.isEqual(CommConst.INSERT, mode)) {
		   		// 약관동의 : 상담신청제외
		    	if (!CodeConst.isDscsnCode(prgrmNo)) {
		    		if (!CommUtils.isEqual(CommConst.YES, data.getPrvcClctAgreYn()))
		    			errors.reject("prvcClctAgreYn", "개인정보 수집·이용·및 제공동의가 완료되지 않았습니다.");
		    		if ((CodeConst.isFeeCode(prgrmNo) || CodeConst.isConsultCode(prgrmNo)) &&
		    			!CommUtils.isEqual(CommConst.YES, data.getUnityMngSysAgreYn()))
		    			errors.reject("unityMngSysAgreYn", "중소기업 지원사업 통합관리시스템 정보활용 동의가 완료되지 않았습니다.");
		    	}
	        } 
    	}
    	// 수정인 경우
    	if (CommUtils.isEqual(CommConst.UPDATE, mode)) {
        	if (CommUtils.isEmpty(data.getSprtAplyNo()))
                errors.reject("sprtAplyNo", "지원신청번호를 확인할 수 없습니다.");
    	}
    	
    	// 신청서작성인 경우
    	if (CommUtils.isEqual(CodeConst.SPRT_FORM, stepCd)) {
    		// 신청서작성 검증
    		_validateForm(data, errors);
    	}
    	// 제출서류인 경우
    	else if (CommUtils.isEqual(CodeConst.SPRT_FILE, stepCd)) {
    		// 제출서류 검증
    		_validateFile(data, errors);
    	}
    }
    
    // 신청서작성 검증처리
    private void _validateForm(SprtVO data, Errors errors) {
    	
    	String sprtSeCd   = data.getSprtSeCd  (); // 신청구분코드
    	String prgrmNo    = data.getPrgrmNo   (); // 프로그램번호
    	String gsBzentyYn = data.getGsBzentyYn(); // 업체회원여부
    	
    	boolean isPicg = false; // 담당자정보 사용여부
    	boolean isInfo =  true; // 신청자정보 사용여부
    	boolean isInvt = false; // 투자조합정보 사용여부
    	boolean isLink = false; // 펀딩시도항목 사용여부
    	boolean isFnnr =  true; // 매출액항목 사용여부
    	
    	// 크라우드펀딩인 경우
    	if (CodeConst.isCrowdCode(sprtSeCd))
    		isFnnr = false;
    	// 컨설팅지원인 경우
    	if (CodeConst.isConsultCode(prgrmNo))
    		isLink = true;
    	// 업체회원인 경우
    	if ("Y".equals(gsBzentyYn))
    		isPicg = true;
    	// 투자후지원인 경우
    	if (CodeConst.isAfterCode(sprtSeCd)) {
    		isInvt = true;
    		// 상담신청인 경우
    		if (CodeConst.isDscsnCode(prgrmNo))
    			isInfo = false;
    	}
    	
    	// 신청자정보가 유효한 경우 검증처리
    	if (isInfo) {
    		// 필수체크
    		// 업체회원인 경우 필수체크항목
    		if (CommConst.YES.equals(gsBzentyYn)) {
            	ValidRule.NEED.validate (errors, data, "bzentyNm"    , "경영체명"); // 필수
            	ValidRule.MAXL.validate (errors, data, "bzentyNm"    , "경영체명", 50); // 최대길이
            	ValidRule.NEED.validate (errors, data, "brno"        , "사업자번호"); // 필수
            	ValidRule.BZNO.validate (errors, data, "brno"        , "사업자번호"); // 형식
            	ValidRule.NEED.validate (errors, data, "fndnYmd"     , "설립일"); // 필수
            	ValidRule.DATE.validate (errors, data, "fndnYmd"     , "설립일"); // 형식
            	//2023.09.04 "8907271******" 식으로 저장된 정보가 있어 형식검증 SKIP 처리함
            	//ValidRule.CRNO.validate (errors, data, "crno"        , "법인등록번호");// 형식
    		}
        	ValidRule.NEED.validate (errors, data, "rprsvNm"     , "대표자명"); // 필수
        	ValidRule.MAXL.validate (errors, data, "rprsvNm"     , "대표자명", 20); // 최대길이
        	ValidRule.NEED.validate (errors, data, "brdt"        , "대표자 생년월일"); // 필수
        	ValidRule.DATE.validate (errors, data, "brdt"        , "대표자 생년월일"); // 형식
        	ValidRule.NEED.validate (errors, data, "sexdstn"     , "대표자 성별"); // 필수
        	ValidRule.SXDST.validate(errors, data, "sexdstn"     , "대표자 성별"); // 코드
        	ValidRule.NEED.validate (errors, data, "lctnAddr"    , "주소"); // 필수
        	ValidRule.MAXL.validate (errors, data, "lctnAddr"    , "주소", 200); // 최대길이
	    	ValidRule.NEED.validate (errors, data, "rprsTelno"   , "전화번호"); // 필수
	    	//ValidRule.PHONE.validate(errors, data, "rprsTelno"   , "전화번호"); // phone
	    	//ValidRule.PHONE.validate(errors, data, "fxno"        , "FAX"); // 형식
	    	ValidRule.NEED.validate (errors, data, "bzentyTypeCd", "기업유형"); // BZENTY_TYPE
	    	if (CommUtils.isNotEmpty(data.getBzentyTypeCd())) {
	    		if (!commService.existCode(CodeConst.BZENTY_TYPE, data.getBzentyTypeCd()))
	    			errors.reject("bzentyTypeCd", "기업유형코드가 유효하지 않습니다.");
	    	}
	    	ValidRule.NEED.validate (errors, data, "bizFldCode"  , "사업분야"); // BIZ_RLM
	    	if (CommUtils.isNotEmpty(data.getBizFldCode())) {
	    		if (!commService.existCodes(CodeConst.BIZ_RLM, data.getBizFldCode()))
	    			errors.reject("bizFldCode", "사업분야코드가 유효하지 않습니다.");
	    	}
	    	ValidRule.URL.validate  (errors, data, "hmpgAddr"    , "홈페이지"); // 형식
	    	//Long 타입이므로 별도 형식검증 없음
	    	ValidRule.NEED.validate (errors, data, "invtHopeAmt" , "투자희망금액");
	    	//Long 타입이므로 별도 형식검증 없음
	    	//ValidateRule.NUMBER.validate(errors, data, "empCnt", "임직원수");
	    	
	    	ValidRule.NEED.validate(errors, data, "mainBizCn"   , "주요사업");
        	ValidRule.MAXL.validate(errors, data, "mainBizCn"   , "주요사업", 1000); // 최대길이
	    	ValidRule.NEED.validate(errors, data, "coreItmCn"   , "핵심아이템");
        	ValidRule.MAXL.validate(errors, data, "coreItmCn"   , "핵심아이템", 1000); // 최대길이
	    	ValidRule.NEED.validate(errors, data, "bizCn"       , "사업내용");
        	ValidRule.MAXL.validate(errors, data, "bizCn"       , "사업내용", 1000); // 최대길이
    	}
    	// 매출액항목 사용시 검증처리
    	if (isFnnr) {
    		List<SprtFnnrVO> fnnrList = data.getFnnrList();
    		if (CommUtils.isEmptyList(fnnrList)) {
    			//2023.08.17 LSH 매출액 필수사항 아님
    			//errors.reject("fnnrList", "매출액은 필수 등록사항입니다.");
    		}
    		else {
    			int no = 1;
    			for (SprtFnnrVO fnnr : fnnrList) {
    				if (CommUtils.isEmpty(fnnr.getFnnrYmd()))
    					errors.reject("fnnrList.fnnrYmd", "["+no+"] 결산년도는 필수 등록사항입니다.");
    				if (CommUtils.isEmpty(fnnr.getFnnrAmt()))
    					errors.reject("fnnrList.fnnrAmt", "["+no+"] 매출액은 필수 등록사항입니다.");
    				no++;
    			}
    		}
    	}
    	// 펀딩시도항목 사용시 검증처리
    	if (isLink) {
	    	ValidRule.NEED.validate(errors, data, "fundLinkUrl", "펀딩시도 확인링크 URL"); // url
    	}
    	// 투자조합정보가 유효한 경우 검증처리
    	if (isInvt) {
    		List<SprtMxtrVO> mxtrList = data.getMxtrList();
    		if (CommUtils.isEmptyList(mxtrList)) {
    			//errors.reject("fnnrList", "투자조합정보는 필수 등록사항입니다.");
    		}
    		else {
    			int no = 1;
    			for (SprtMxtrVO fnnr : mxtrList) {
    				if (CommUtils.isEmpty(fnnr.getInvtMxtrNm()))
    					errors.reject("mxtrList.invtMxtrNm", "["+no+"] 투자조합명은 필수 등록사항입니다.");
    				if (CommUtils.isEmpty(fnnr.getInvstrNm()))
    					errors.reject("mxtrList.invstrNm", "["+no+"] 운용사명은 필수 등록사항입니다.");
    				if (CommUtils.isEmpty(fnnr.getInvtYmd()))
    					errors.reject("mxtrList.invtYmd", "["+no+"] 투자년월은 필수 등록사항입니다.");
    				if (CommUtils.isEmpty(fnnr.getInvtAmt()))
    					errors.reject("mxtrList.invtAmt", "["+no+"] 투자금액은 필수 등록사항입니다.");
    				no++;
    			}
    		}
    	}
    	// 담당자정보가 유효한 경우 검증처리
    	if (isPicg) {
        	ValidRule.NEED.validate (errors, data, "picNm"       , "담당자 이름");
        	ValidRule.NEED.validate (errors, data, "picTelno"    , "담당자 직통번호"); // 필수
        	//ValidRule.PHONE.validate(errors, data, "picTelno"    , "담당자 직통번호"); // phone
        	ValidRule.NEED.validate (errors, data, "picEmlAddr"  , "담당자 이메일"); // 필수
        	ValidRule.EMAIL.validate(errors, data, "picEmlAddr"  , "담당자 이메일"); // email
    	}
    }

    
    // 제출서류 검증처리
    private void _validateFile(SprtVO data, Errors errors) {
    	
    	// 저장모드 (임시저장/제출)
    	String act = data.getAct();
    	// 서류코드목록
    	List<BizFileVO> papes = data.getPapeList();
        // 저장파일목록
        List<BizFileVO> saveFiles = data.getSaveFiles();
    	
    	// 서류코드가 없는 경우 검증 SKIP
    	if (CommUtils.isEmptyList(papes))
    		return;
    	
        // 서류코드에 따른 저장파일 VALIDATION
    	for (BizFileVO pape : papes) {

    		pape.setFiles(new ArrayList<BizFileVO>());
        	
    		for (BizFileVO fileVO : saveFiles) {
    			if (CommUtils.isEqual(pape.getDcmntCd(), fileVO.getDcmntCd())) {
    				pape.addFiles(fileVO);
    			}
    		}
    		
    		String title = "["+pape.getDcmntNm()+"]";
    		// 업로드 파일목록
    		List<BizFileVO> files = pape.getFiles();
    		
    		// 임시저장의 경우 필수 체크 SKIP
    		if (CommConst.SUBMIT.equals(act)) {
            	// 필수인 경우
            	if ("Y".equals(pape.getEsntlYn()) && files.size() == 0) {
            		errors.reject("papeFile", title + " 파일은 필수등록사항입니다.");
            	}
    		}
        	// 파일갯수 초과인 경우
        	if (files.size() > pape.getLmtCnt()) {
        		errors.reject("papeFile", title + " 파일은 최대 "+pape.getLmtCnt()+"개 까지만 등록가능합니다.");
        	}
    	}
    }
}
