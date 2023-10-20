package business.adm.invest.web;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.CodeConst;
import business.adm.invest.service.EntVO;
import business.adm.invest.service.FundVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserVO;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 회원관리-업체관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : EntValidator
 * @author  : LHB
 * @since   : 2023.06.12
 * @version : 1.0
 *
 *   수정일       수정자              수정내용
 *  -------    --------    ---------------------------
 * 23.06.12      LHB            First Coding.
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
public class EntValidator implements Validator {
	
	@Autowired
	protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return EntVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
        EntVO data = (EntVO) o;
        
        String mode = data.getMode();
        
        if (CommUtils.nvlTrim(mode).isEmpty()) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getBzentyNo())) {
                errors.reject("bzentyNo", "업체번호를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }
        
        if (!CommUtils.nvlTrim(data.getMngrMemo()).isEmpty() && CommUtils.nvlTrim(data.getMngrMemo()).length() > 300) {
        	errors.reject("mngrMemo", "관리자 메모는 300자를 초과할 수 없습니다.");
        }
        
    }
}
