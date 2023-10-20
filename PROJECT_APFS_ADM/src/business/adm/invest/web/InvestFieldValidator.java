package business.adm.invest.web;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.CodeConst;
import business.adm.invest.service.FundVO;
import business.adm.invest.service.InvestFieldVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserVO;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 운영관리-투자분야관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : InvestFieldValidator
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 23.06.09      LHB            First Coding.
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
public class InvestFieldValidator implements Validator {
	
	@Autowired
	protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return InvestFieldVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
        InvestFieldVO data = (InvestFieldVO) o;
        
        String mode = data.getMode();
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(mode))) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getInvtFldCd())) {
                errors.reject("invtFldCd", "투자분야코드를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getInvtFldNm()))) {
        	errors.reject("invtFldCd", "투자분야명은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getInvtFldNm()).length() > 100) {
        	errors.reject("invtFldCd", "투자분야명은 100자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getInvtFldCn()))) {
        	errors.reject("invtFldCd", "투자분야내용은 필수 입력사항입니다.");
        } else if (CommUtils.nvlTrim(data.getInvtFldCn()).length() > 1000) {
        	errors.reject("invtFldCd", "투자분야내용은 1000자를 초과할 수 없습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getUseBgngYmd()))) {
            errors.reject("useBgngYmd", "사용시작일자는 필수 입력사항입니다.");
        } else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getUseBgngYmd()), "-"))) {
        	errors.reject("useBgngYmd", "사용시작일자의 형식이 올바르지 않습니다.");
        }
        
        if (!CommUtils.isEmpty(CommUtils.nvlTrim(data.getUseEndYmd())) && !CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getUseEndYmd()), "-"))) {
        	errors.reject("useEndYmd", "사용종료일자의 형식이 올바르지 않습니다.");
        }
        
    }
}
