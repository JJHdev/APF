package business.usr.invest.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserService;
import business.usr.CodeConst;
import business.usr.ValidRule;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [검증클래스] - 업체정보 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : EntValidator
 * @author  : LSH
 * @since   : 2023.07.07
 * @version : 1.0
 *
 * 1) [mode:I / act:BI] 회원가입 - 기업정보 등록
 * 2) [mode:U / act:BI] 마이페이지 - 기본정보 - 기업기본정보 수정
 * 3) [mode:U / act:BF] 마이페이지 - 기본정보 - 기업상세정보 수정
 * 4) [mode:U / act:II] 마이페이지 - IR작성하기 - 대시보드 수정
 * 5) [mode:U / act:IF] 마이페이지 - IR작성하기 - 상세정보 수정
 * 6) [mode:U / act:IS] 마이페이지 - IR작성하기 - 기타지원이력 등록
 * 
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
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 */
@Service
public class EntValidator implements Validator {
    
    @Resource(name = "EntService")
    private EntService entService;
    
    @Resource(name = "UserService")
    private UserService userService;
    
    @Resource(name = "CommService")
    private CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return EntVO.class.isAssignableFrom(type);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
    	
    	EntVO data  = (EntVO) o;
    	String act  = data.getAct();
    	String mode = data.getMode();
    	
    	// IR작성하기인 경우
    	if (CommUtils.exist(new String[] {
    			CommConst.ACT_IR_INFO, 
    			CommConst.ACT_IR_FILE,
    			CommConst.ACT_IR_SPTHST}, act)) {

        	if (!CommUtils.exist(new String[] {
        			CommConst.DELETE, 
        			CommConst.INSERT, 
        			CommConst.UPDATE}, mode))
        		errors.reject("mode", "잘못된 접근입니다.");
        	else {
            	if (CommUtils.exist(new String[] {
            			CommConst.INSERT, 
            			CommConst.UPDATE}, mode))
            		_validateIR(data, errors); // IR작성하기 검증
        	}
    	}
    	// 회원가입/기업정보수정
    	else if (CommUtils.exist(new String[] {
    			CommConst.ACT_BZ_INFO, 
    			CommConst.ACT_BZ_FILE}, act)) {

    		// 회원가입인 경우
        	if (CommConst.INSERT.equals(mode))
        		_validateRegist(data, errors);
        	
        	// 기업정보수정인 경우
        	else if (CommConst.UPDATE.equals(mode))
        		_validateUpdate(data, errors);
        	
        	else
        		errors.reject("mode", "잘못된 접근입니다.");
    	}
    	else
    		errors.reject("act", "잘못된 접근입니다.");
    }
    
    // 기업정보수정 검증
    private void _validateUpdate(EntVO data, Errors errors) {

    	String act = data.getAct();

		// 세션정보와 다른 경우
        if (!CommUtils.isEqual(data.getUserNo(), data.getGsUserNo())) {
        	errors.reject("error.comm.notValid");
        	return;
        } 
        if (!CommUtils.isEqual(data.getBzentyNo(), data.getGsBzentyNo())) {
        	errors.reject("error.comm.notValid");
        	return;
        } 
    	// 기업기본정보인 경우
    	if (CommConst.ACT_BZ_INFO.equals(act)) {

    		// KODATA가 아닌 경우
    		if (!CommConst.YES.equals(data.getKodataYn())) {
	        	ValidRule.NEED.validate (errors, data, "bzentyStleCd", "기업형태");
		    	if (CommUtils.isNotEmpty(data.getBzentyStleCd())) {
		    		if (!commService.existCode(CodeConst.BZENTY_STLE, data.getBzentyStleCd()))
		    			errors.reject("bzentyStleCd", "기업형태코드가 유효하지 않습니다.");
		    	}
	        	ValidRule.NEED.validate (errors, data, "bzentyTypeCd", "기업유형");
		    	if (CommUtils.isNotEmpty(data.getBzentyTypeCd())) {
		    		if (!commService.existCode(CodeConst.BZENTY_TYPE, data.getBzentyTypeCd()))
		    			errors.reject("bzentyTypeCd", "기업유형코드가 유효하지 않습니다.");
		    	}
	        	ValidRule.NEED.validate (errors, data, "bzentyScaleCd", "기업규모");
		    	if (CommUtils.isNotEmpty(data.getBzentyScaleCd())) {
		    		if (!commService.existCode(CodeConst.BZENTY_SCALE, data.getBzentyScaleCd()))
		    			errors.reject("bzentyScaleCd", "기업규모코드가 유효하지 않습니다.");
		    	}
	        	ValidRule.NEED.validate (errors, data, "tpbizCd", "업종");
		    	if (CommUtils.isNotEmpty(data.getTpbizCd())) {
		    		if (!commService.existCode(CodeConst.INDUTY_SE, data.getTpbizCd()))
		    			errors.reject("tpbizCd", "업종코드가 유효하지 않습니다.");
		    	}
    		}
	    	//ValidRule.NEED.validate (errors, data, "hmpgAddr"    , "홈페이지");
	    	ValidRule.URL.validate  (errors, data, "hmpgAddr"    , "홈페이지");
	    	ValidRule.NEED.validate (errors, data, "emlAddr"     , "이메일");
	    	ValidRule.EMAIL.validate(errors, data, "emlAddr"     , "이메일");
        	ValidRule.NEED.validate (errors, data, "rprsTelno"   , "TEL");
	    	//ValidRule.PHONE.validate(errors, data, "rprsTelno"   , "TEL");
	    	//ValidRule.PHONE.validate(errors, data, "fxno"        , "FAX");
	    	ValidRule.NEED.validate (errors, data, "lctnAddr"    , "소재지");
        	ValidRule.MAXL.validate (errors, data, "lctnAddr"    , "소재지", 200);
    	}
    	// 기업상세정보인 경우
    	else if (CommConst.ACT_BZ_FILE.equals(act)) {
    		
	    	ValidRule.NEED.validate (errors, data, "bizFldList"  , "사업분야");
	    	//if (CommUtils.isNotEmpty(data.getBizFldList())) {
	    	//	if (!commService.existCodes(CodeConst.BIZ_RLM, data.getBizFldList()))
	    	//		errors.reject("bizFldList", "사업분야코드가 유효하지 않습니다.");
	    	//}
	    	ValidRule.NEED.validate (errors, data, "invtFldList"  , "투자분야");
	    	//if (CommUtils.isNotEmpty(data.getInvtFldList())) {
	    	//	if (!commService.existInvtRlmCodes(data.getInvtFldList()))
	    	//		errors.reject("invtFldList", "투자분야코드가 유효하지 않습니다.");
	    	//}
	    	//Long 타입이므로 별도 형식검증 없음
	    	ValidRule.NEED.validate (errors, data, "invtHopeAmt" , "투자희망금액");

            // 저장파일목록 (최대3개)
            List<FileInfo> saveFiles = data.getSaveFiles();
        	
            if (saveFiles == null || 
            	saveFiles.size() == 0) {
            	errors.reject("saveFiles", "썸네일 파일은 필수등록사항입니다.");
            }
            // 최대 갯수는 3개까지
            else if (saveFiles.size() > CommConst.THUMB_MAX_CNT) {
            	errors.reject("saveFiles", "썸네일 파일은 최대 "+CommConst.THUMB_MAX_CNT+"개 까지만 등록가능합니다.");
            }
    	}
    }

    // 회원가입 검증
    private void _validateRegist(EntVO data, Errors errors) {
    	
    	// 기업기본정보 검증 ==================================================>
    	ValidRule.NEED.validate (errors, data, "brno"       , "사업자번호");
    	ValidRule.NEED.validate (errors, data, "bzentyNm"   , "회사명");
    	ValidRule.NEED.validate (errors, data, "rprsvNm"    , "대표자");
    	ValidRule.NEED.validate (errors, data, "fndnYmd"    , "설립일");
    	ValidRule.NEED.validate (errors, data, "rprsTelno"  , "대표번호");
    	//ValidRule.PHONE.validate(errors, data, "rprsTelno"  , "대표번호");
    	ValidRule.NEED.validate (errors, data, "bzentySeCd" , "기업유형");
    	
    	// 대표계정인 경우
    	if (CommConst.YES.equals(data.getRprsYn())) {
    		
    		// 기업상세정보 검증 ==============================================>
    		// 경영체인 경우
    		if (CodeConst.ENT_EBZ.equals(data.getBzentySeCd())) {
    	    	ValidRule.NEED.validate (errors, data, "bizFldList", "사업분야");
    	    	//if (CommUtils.isNotEmpty(data.getBizFldList())) {
    	    	//	if (!commService.existCodes(CodeConst.BIZ_RLM, data.getBizFldList()))
    	    	//		errors.reject("bizFldList", "사업분야코드가 유효하지 않습니다.");
    	    	//}
    	    	//ValidRule.NEED.validate (errors, data, "invtFldList", "투자분야");
    	    	//if (CommUtils.isNotEmpty(data.getInvtFldList())) {
    	    	//	if (!commService.existInvtRlmCodes(data.getInvtFldList()))
    	    	//		errors.reject("invtFldList", "투자분야코드가 유효하지 않습니다.");
    	    	//}
    	    	//Long 타입이므로 별도 형식검증 없음
    	    	ValidRule.NEED.validate (errors, data, "invtHopeAmt", "투자희망금액");
            }
    		// 파일정보 검증 SKIP
			//_entfiles['dlgtFile']
    		//_entfiles['bregFile']
    		//_entfiles['thmbFile']
    	}
    }
    
    // IR작성하기 검증
    private void _validateIR(EntVO data, Errors errors) {

    	String act = data.getAct();

        if (!CommConst.YES.equals(data.getUpdateYn())) {
            errors.reject("accessYn", "IR정보 작성권한이 없습니다.");
        	return;
        }
        if (CommUtils.isEmpty(data.getBzentyNo())) {
        	errors.reject("bzentyNo", "업체정보를 확인할 수 없습니다.");
        	return;
        }
        // IR번호는 화면오프시 생성되므로 반드시 있어야함
        if (CommUtils.isEmpty(data.getIrNo())) {
        	errors.reject("irNo", "업체정보를 확인할 수 없습니다.");
        	return;
        }
        
    	// IR작성하기 - 대시보드인 경우
    	if (CommConst.ACT_IR_INFO.equals(act)) {

    		ValidRule.NEED.validate (errors, data, "mainBizCn", "주요사업"         );
			ValidRule.MAXL.validate (errors, data, "mainBizCn", "주요사업"    , 100);
			ValidRule.NEED.validate (errors, data, "coreItmCn", "핵심아이템"       );
			ValidRule.MAXL.validate (errors, data, "coreItmCn", "핵심아이템"  , 100);
			ValidRule.NEED.validate (errors, data, "bizCn"    , "투자요소"         );
			ValidRule.MAXL.validate (errors, data, "bizCn"    , "투자요소"    , 100);
			ValidRule.NEED.validate (errors, data, "picNm"    , "담당자"           );
			ValidRule.MAXL.validate (errors, data, "picNm"    , "담당자"      ,  10);
			ValidRule.NEED.validate (errors, data, "picTelno" , "담당자연락처"     );
			//ValidRule.PHONE.validate(errors, data, "picTelno" , "담당자연락처"     );
			
			// IR정보 세부항목 검증
			_validateIRList(data, errors);
    	}
    	// IR작성하기 - 상세정보인 경우
    	else if (CommConst.ACT_IR_FILE.equals(act)) {
    		
    		ValidRule.URL.validate (errors, data, "prVidoUrl" , "홍보영상");
    		ValidRule.MAXL.validate(errors, data, "prVidoUrl" , "홍보영상", 100);
            // 저장파일목록: 필수항목 아님
    	}
    	// IR작성하기 - 기타지원이력인 경우
    	else if (CommConst.ACT_IR_SPTHST.equals(act)) {
            if (CommUtils.isEmpty(data.getIrNo())) {
            	errors.reject("irNo", "IR정보가 먼저 등록되어야 합니다.");
            	return;
            }
    		ValidRule.NEED.validate (errors, data, "fldCn" , "분야"        );
    		ValidRule.MAXL.validate (errors, data, "fldCn" , "분야"    , 50);
    		ValidRule.NEED.validate (errors, data, "instNm", "지원기관"    );
    		ValidRule.MAXL.validate (errors, data, "instNm", "지원기관", 50);
    		ValidRule.NEED.validate (errors, data, "bizNm" , "사업명"      );
    		ValidRule.MAXL.validate (errors, data, "bizNm" , "사업명"  , 50);
    		ValidRule.NEED.validate (errors, data, "dtlCn" , "세부내용"    );
    		ValidRule.MAXL.validate (errors, data, "dtlCn" , "세부내용", 50);
    	}
    }
    
    // IR정보 세부항목 검증
    private void _validateIRList(EntVO data, Errors errors) {

	    // 대표자이력 검증 (필수)
		if (CommUtils.isEmptyList(data.getRprsHstList()))
			errors.reject("rprsHstList", "대표자이력은 필수등록사항입니다.");
    	
		String nm = "";
	    // 투자금액 검증
		if (!CommUtils.isEmptyList(data.getInvtList())) {
			nm = "투자금액";
			for (EntVO item : data.getInvtList()) {
				ValidRule.NEED.validate (errors, item, "invtYr"  , "["+nm+"] 년도");
				ValidRule.NEED.validate (errors, item, "invtSeCd", "["+nm+"] 단계");
				ValidRule.NEED.validate (errors, item, "invtAmt" , "["+nm+"] 투자금액");
			}
		}
	    // 재무정보 검증 (KODATA없는 업체)
		if (!CommUtils.isEmptyList(data.getFnnrList())) {
			nm = "재무정보";
			for (EntVO item : data.getFnnrList()) {
				ValidRule.NEED.validate (errors, item, "fnnrYr"     , "["+nm+"] 년도");
				ValidRule.NEED.validate (errors, item, "fnnrSeCd"   , "["+nm+"] 재무구분");
				ValidRule.NEED.validate (errors, item, "dataSeCd"   , "["+nm+"] 자료구분");
				ValidRule.NEED.validate (errors, item, "fnnrAmt"    , "["+nm+"] 금액");
				ValidRule.NEED.validate (errors, item, "fnnrAcntCd" , "["+nm+"] 계정코드");
			}
		}
	    // 회사연혁 검증
		if (!CommUtils.isEmptyList(data.getCoHstList())) {
			nm = "회사연혁";
			for (EntVO item : data.getCoHstList()) {
				ValidRule.NEED.validate (errors, item, "bgngCoYmd", "["+nm+"] 시기");
				ValidRule.NEED.validate (errors, item, "cn"       , "["+nm+"] 내용");
			}
		}
	    // 특허상표권현황 검증 (KODATA없는 업체)
		if (!CommUtils.isEmptyList(data.getPtntList())) {
			nm = "특허및상표권보유현황";
			for (EntVO item : data.getPtntList()) {
				ValidRule.NEED.validate (errors, item, "applnm"      , "["+nm+"] 출원인"  );
				//ValidRule.NEED.validate (errors, item, "patntrtMan"  , "["+nm+"] 특허권자");
				//ValidRule.NEED.validate (errors, item, "nm"          , "["+nm+"] 명칭"    );
				//ValidRule.NEED.validate (errors, item, "illtRegNo"   , "["+nm+"] 등록번호");
				//ValidRule.NEED.validate (errors, item, "patentRegYmd", "["+nm+"] 등록일자");
			}
		}
	    // 대표자이력 검증 (필수)
		if (!CommUtils.isEmptyList(data.getRprsHstList())) {
			nm = "대표자이력";
			for (EntVO item : data.getRprsHstList()) {
				ValidRule.NEED.validate (errors, item, "bgngYmd" , "["+nm+"] 시기"    );
				ValidRule.MAXL.validate (errors, item, "hstryCn" , "["+nm+"] 주요경력");
			}
		}
	    // 경영진정보 검증
		if (!CommUtils.isEmptyList(data.getMgmtList())) {
			nm = "경영진및기술진";
			for (EntVO item : data.getMgmtList()) {
				ValidRule.NEED.validate (errors, item, "jbpsNm"  , "["+nm+"] 직위"    );
				ValidRule.NEED.validate (errors, item, "mgtFlnm" , "["+nm+"] 성명"    );
				ValidRule.NEED.validate (errors, item, "age"     , "["+nm+"] 연령"    );
				ValidRule.MAXL.validate (errors, item, "careerCn", "["+nm+"] 주요경력");
			}
		}
	    // 주주현황 검증
		if (!CommUtils.isEmptyList(data.getShroldrList())) {
			nm = "주주현황";
			for (EntVO item : data.getShroldrList()) {
				ValidRule.NEED.validate (errors, item, "flnm"    , "["+nm+"] 성명"  );
				ValidRule.NEED.validate (errors, item, "invtAmt" , "["+nm+"] 투자액");
				ValidRule.NEED.validate (errors, item, "qotaRt"  , "["+nm+"] 지분율");
				ValidRule.MAXL.validate (errors, item, "relCn"   , "["+nm+"] 관계"  );
			}
		}
	    // 소송현황 검증
		if (!CommUtils.isEmptyList(data.getLwstList())) {
			nm = "소송중인내용";
			for (EntVO item : data.getLwstList()) {
				ValidRule.NEED.validate (errors, item, "acusrNm" , "["+nm+"] 원고"    );
				ValidRule.NEED.validate (errors, item, "dfdtNm"  , "["+nm+"] 피고"    );
				ValidRule.NEED.validate (errors, item, "lwstCn"  , "["+nm+"] 소송내용");
				ValidRule.MAXL.validate (errors, item, "lwstAmt" , "["+nm+"] 소송금액");
			}
		}
    }
}
