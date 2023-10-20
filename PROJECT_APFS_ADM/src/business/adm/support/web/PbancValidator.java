package business.adm.support.web;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.CodeConst;
import business.adm.invest.service.FundVO;
import business.adm.support.service.PbancVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserVO;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 운영관리 - 사업공고관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : PbancValidator
 * @author  : LHB
 * @since   : 2023.05.03
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
public class PbancValidator implements Validator {
	
	@Autowired
	protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return PbancVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
    	PbancVO data	= (PbancVO) o;
    	
    	String mode		= data.getMode();
    	
    	if (CommUtils.nvlTrim(mode).isEmpty()) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getBizPbancNo())) {
                errors.reject("bizPbancNo", "사업공고번호를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }

    	/* 주관기관 정보 */
        if (CommUtils.nvlTrim(data.getCrdnsBzentyNo()).isEmpty()) {
        	errors.reject("crdnsBzentyNo", "등록기관명은 필수 선택사항입니다.");
        }
        
        if (CommUtils.nvlTrim(data.getTkcgDeptNm()).isEmpty()) {
        	errors.reject("tkcgDeptNm", "사업담당부서는 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getTkcgDeptNm()).length() > 20) {
        	errors.reject("tkcgDeptNm", "사업담당부서는 20자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getPicNm()).isEmpty()) {
        	errors.reject("picNm", "사업담당자명은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getPicNm()).length() > 20) {
        	errors.reject("picNm", "사업담당자명은 20자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getPicTelno()).isEmpty()) {
            errors.reject("picTelno", "담당자연락처는 필수 입력사항입니다.");
        } else if (!CommUtils.isValidTelno(CommUtils.nvlTrim(data.getPicTelno()), "")) {
        	errors.reject("picTelno", "담당자연락처의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getBizPbancNm()).isEmpty()) {
        	errors.reject("bizPbancNm", "공고명은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getPicNm()).length() > 100) {
        	errors.reject("bizPbancNm", "공고명은 100자를 초과할 수 없습니다.");
        }
        
        if (!CommUtils.nvlTrim(data.getBizGuidanceUrl()).isEmpty() && CommUtils.nvlTrim(data.getBizGuidanceUrl()).length() > 500) {
        	errors.reject("bizGuidanceUrl", "사업안내URL은 500자를 초과할 수 없습니다.");
        }
        
        // 접수기간 체크
        if (CommUtils.nvlTrim(data.getRcptSeCd()).isEmpty()) {
        	errors.reject("rcptSeCd", "접수기간은 필수 선택사항입니다.");
        } else if (!commService.existCode(CodeConst.RCPT_WHL_SE, CommUtils.nvlTrim(data.getRcptSeCd()))) {
        	errors.reject("rcptSeCd", "접수기간의 형식이 올바르지 않습니다.");
        } else if (CommUtils.nvlTrim(data.getRcptSeCd()).equals(CodeConst.RCPT_WHL_SE3) || CommUtils.nvlTrim(data.getRcptSeCd()).equals(CodeConst.RCPT_WHL_SE1)) {	// 접수기간이 기간 혹은 예정인 경우
        	
        	if (CommUtils.nvlTrim(data.getRcptBgngDt()).isEmpty()) {
                errors.reject("rcptBgngDt", "접수시작일자는 필수 입력사항입니다.");
            }
        	
        	if (CommUtils.nvlTrim(data.getRcptEndDt()).isEmpty()) {
                errors.reject("rcptEndDt", "접수종료일시는 필수 입력사항입니다.");
            }
        	
        	if (CommUtils.nvlTrim(data.getRcptSeCd()).equals(CodeConst.RCPT_WHL_SE1)) {
        		try {
            		int bgngTime = Integer.parseInt(data.getRcptBgngTm());
            		if(bgngTime < 0 || bgngTime > 23) {
            			errors.reject("rcptBgngDt", "접수시작시간의 형식이 올바르지 않습니다.");
            		}
            	} catch (NumberFormatException e) {
            		errors.reject("rcptBgngDt", "접수시작시간의 형식이 올바르지 않습니다.");
            	} catch (Exception e) {
            		errors.reject("rcptBgngDt", "접수시작시간의 형식이 올바르지 않습니다.");
            	}
            	
            	try {
            		int endTime = Integer.parseInt(data.getRcptEndTm());
            		if(endTime < 0 || endTime > 23) {
            			errors.reject("rcptEndDt", "접수종료시간의 형식이 올바르지 않습니다.");
            		}
            	} catch (NumberFormatException e) {
            		errors.reject("rcptBgngDt", "접수시작시간의 형식이 올바르지 않습니다.");
            	} catch (Exception e) {
            		errors.reject("rcptEndDt", "접수종료시간의 형식이 올바르지 않습니다.");
            	}
        	}
        }
        
        if (CommUtils.nvlTrim(data.getBizFld()).isEmpty()) {
        	errors.reject("bizFld", "사업분야는 필수 선택사항입니다.");
        } else {
        	String[] bizFldArr = CommUtils.nvlTrim(data.getBizFld()).split(",");
        	for (int i=0 ; i<bizFldArr.length ; i++) {
        		if(!commService.existCode(CodeConst.BIZ_FLD, CommUtils.nvlTrim(bizFldArr[i]))) {
		        	errors.reject("bizFld", "사업분야의 형식이 올바르지 않습니다.");
		        	break;
		        }
        	}
        }
        
        if (CommUtils.nvlTrim(data.getBizTrgt()).isEmpty()) {
        	errors.reject("bizTrgt", "사업대상은 필수 선택사항입니다.");
        } else {
        	String[] bizTrgtArr = CommUtils.nvlTrim(data.getBizTrgt()).split(",");
        	for (int i=0 ; i<bizTrgtArr.length ; i++) {
        		if(!commService.existCode(CodeConst.BIZ_TRGT, CommUtils.nvlTrim(bizTrgtArr[i]))) {
		        	errors.reject("bizTrgt", "사업대상의 형식이 올바르지 않습니다.");
		        	break;
		        }
        	}
        }

        if (CommUtils.nvlTrim(data.getBizTrgtAge()).isEmpty()) {
        	errors.reject("bizTrgtAge", "사업대상연령은 필수 선택사항입니다.");
        } else if (!commService.existCode(CodeConst.BIZ_TRGT_AGE, CommUtils.nvlTrim(data.getBizTrgtAge()))) {
        	errors.reject("bizTrgtAge", "사업대상연령의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getBizTrgtFntnPd()).isEmpty()) {
        	errors.reject("bizTrgtFntnPd", "사업대상업력은 필수 선택사항입니다.");
        } else if (!commService.existCode(CodeConst.BIZ_TRGT_FNTN_PD, CommUtils.nvlTrim(data.getBizTrgtFntnPd()))) {
        	errors.reject("bizTrgtFntnPd", "사업대상업력의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getRcptMthdCn()).isEmpty()) {
        	errors.reject("rcptMthdCn", "공고접수방법은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getRcptMthdCn()).length() > 2000) {
        	errors.reject("rcptMthdCn", "공고접수방법은 2000자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getAplyQlfcCn()).isEmpty()) {
        	errors.reject("aplyQlfcCn", "신청자격은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getAplyQlfcCn()).length() > 2000) {
        	errors.reject("aplyQlfcCn", "신청자격은 2000자를 초과할 수 없습니다.");
        }
        
        if (!CommUtils.nvlTrim(data.getAplyExclTrgtCn()).isEmpty() && CommUtils.nvlTrim(data.getAplyExclTrgtCn()).length() > 2000) {
        	errors.reject("aplyExclTrgtCn", "신청제외대상은 2000자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getSbmsnDcmntCn()).isEmpty()) {
        	errors.reject("sbmsnDcmntCn", "제출서류는 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getSbmsnDcmntCn()).length() > 2000) {
        	errors.reject("sbmsnDcmntCn", "제출서류는 2000자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getSprtCn()).isEmpty()) {
        	errors.reject("sprtCn", "지원내용은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getSprtCn()).length() > 2000) {
        	errors.reject("sprtCn", "지원내용은 2000자를 초과할 수 없습니다.");
        }
        
    }
}
