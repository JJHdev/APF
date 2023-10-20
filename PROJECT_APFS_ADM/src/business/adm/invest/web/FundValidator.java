package business.adm.invest.web;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.CodeConst;
import business.adm.invest.service.FundVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserVO;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 투자정보관리 - 모태펀드등록 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : FundValidator
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일       수정자                수정내용
 *  -------    --------    ---------------------------
 * 23.04.17      LHB             First Coding.
 * 23.06.16      LHB        펀드정보에 조합원1, 조합원2 삭제
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
public class FundValidator implements Validator {
	
	@Autowired
	protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return FundVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
        FundVO data = (FundVO) o;
        
        String mode = data.getMode();
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(mode))) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getFundNo())) {
                errors.reject("fundNo", "펀드번호를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getFundNm()))) {
            errors.reject("fundNm", "펀드명은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getFundNm()).length() > 100) {
        	errors.reject("fundNm", "펀드명은 100자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getInvtFldCd()))) {
            errors.reject("invtRlmCd", "투자분야는 필수 선택사항입니다.");
        } else if (!commService.existInvtRlmCode(CommUtils.nvlTrim(data.getInvtFldCd()))) {
        	errors.reject("invtRlmCd", "투자분야의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getOrgzYmd()))) {
            errors.reject("orgzYmd", "결성일은 필수 입력사항입니다.");
        } else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getOrgzYmd()), "-"))) {
        	errors.reject("orgzYmd", "결성일의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getInvtBgngYmd()))) {
            errors.reject("invtBgngYmd", "투자시작일자는 필수 입력사항입니다.");
        } else if (CommUtils.toShortDate(CommUtils.nvlTrim(data.getInvtBgngYmd()), "-").length() != 6) {
        	errors.reject("invtBgngYmd", "투자시작일자의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getInvtEndYmd()))) {
            errors.reject("invtEndYmd", "투자종료일자는 필수 입력사항입니다.");
        } else if (CommUtils.toShortDate(CommUtils.nvlTrim(data.getInvtEndYmd()), "-").length() != 6) {
        	errors.reject("invtEndYmd", "투자종료일자의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(String.valueOf(data.getFundOperScale())))) {
            errors.reject("fundOperScale", "펀드운영규모는 필수 입력사항입니다.");
        } else if (!Pattern.matches("^[0-9]*$", CommUtils.nvlTrim(String.valueOf(data.getFundOperScale())))) {
        	errors.reject("fundOperScale", "펀드운영규모의 형식이 올바르지 않습니다..");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getRprsTelno()))) {
            errors.reject("rprsTelno", "대표전화번호는 필수 입력사항입니다.");
        }
    }
}
