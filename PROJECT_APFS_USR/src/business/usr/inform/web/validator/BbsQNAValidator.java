package business.usr.inform.web.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.usr.inform.service.BbsVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 1:1문의 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : BbsQNAValidator
 * @author  : LSH
 * @since   : 2023.05.18
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
public class BbsQNAValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return BbsVO.class.isAssignableFrom(type);
    }

    /**
	 * QNA(1:1문의) 게시판 - 유효성검사.
	 */
    @Override
    public void validate(Object o, Errors errors) {

        BbsVO data = (BbsVO) o;
        
    	String mode    = data.getMode();
        String bbsSeCd = data.getBbsSeCd();
     
    	if (CommUtils.nvlTrim(mode).isEmpty()) {
    		errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
    	}
    	if (CommUtils.nvlTrim(bbsSeCd).isEmpty()) {
    		errors.reject("bbsSeCd", "게시판 종류를 확인할 수 없습니다.");
    	}
    	if (CommConst.UPDATE.equals(mode) ||
    			CommConst.DELETE.equals(mode) ) {
    		if (CommUtils.isEmpty(data.getPstNo())) {
    			errors.reject("pstNo", "게시물 번호를 확인할 수 없습니다.");
    		}
    	}
    	if (CommConst.DELETE.equals(mode)) {
    		return;
    	}
    	if (CommUtils.nvlTrim(data.getPstCn()).isEmpty()) {
    		errors.reject("pstCn", "내용은 필수입력사항입니다.");
    	}
    }
}
