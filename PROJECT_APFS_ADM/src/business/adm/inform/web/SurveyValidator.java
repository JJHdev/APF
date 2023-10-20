package business.adm.inform.web;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.inform.service.SurveyVO;
import business.com.CommConst;
import common.util.CommUtils;

/**
 * [검증클래스] - 설문관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : SurveyValidator
 * @author  : KYW
 * @since   : 2023.07.20
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 2023.07.20     KYW          First Coding.
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
public class SurveyValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return SurveyVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SurveyVO data = (SurveyVO) o;
        
        String mode = data.getMode();

        if (CommUtils.isEmpty(CommUtils.nvlTrim(mode))) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getSrvyNo())) {
                errors.reject("srvyNo", "설문번호를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }
        
        if (CommUtils.isEmpty(data.getSrvyCn())) {
        	errors.reject("srvyCn", "설문제목은 필수 입력사항입니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getSrvyBgngYmd()))) {
            errors.reject("srvyBgngYmd", "설문시작일자는 필수 입력사항입니다.");
        } else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getSrvyBgngYmd()), "-"))) {
        	errors.reject("srvyBgngYmd", "설문시작일자의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getSrvyEndYmd()))) {
            errors.reject("srvyEndYmd", "설문종료일자는 필수 입력사항입니다.");
        } else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getSrvyEndYmd()), "-"))) {
        	errors.reject("srvyEndYmd", "설문종료일자의 형식이 올바르지 않습니다.");
        }
        
    }
    
}
