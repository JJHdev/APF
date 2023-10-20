package business.sys.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.sys.user.service.UserInfoVO;
import business.adm.CodeConst;
import common.util.CommUtils;

/**
 * [검증클래스] - 회원관리-사용자관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : UserInfoValidator
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  -------    --------    ---------------------------
 * 23.06.13      LHB              First Coding.
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
public class UserInfoValidator implements Validator {
	
	@Autowired
	protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return UserInfoVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
    	UserInfoVO data = (UserInfoVO) o;
        
        String mode = data.getMode();
        
        if (CommUtils.nvlTrim(mode).isEmpty()) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getUserNo())) {
                errors.reject("userNo", "사용자번호를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }
        
        if (CommUtils.nvlTrim(data.getMblTelno()).isEmpty()) {
            errors.reject("mblTelno", "휴대전화는 필수 입력사항입니다.");
        } else if (!CommUtils.isValidTelno(CommUtils.nvlTrim(data.getMblTelno()), "")) {
        	errors.reject("mblTelno", "휴대전화의 형식이 올바르지 않습니다.");
        }
        
        if (CommUtils.nvlTrim(data.getUseSttsCd()).isEmpty()) {
            errors.reject("useSttsCd", "사용상태는 필수 선택사항입니다.");
        } else if (!commService.existCode(CodeConst.USE_STUS, data.getUseSttsCd())) {
        	errors.reject("useSttsCd", "사용상태의 형식이 올바르지 않습니다.");
        }
        // 2023.08.21 LSH 사용자권한이 있는 경우에만 체크하도록 수정
        if (CommUtils.isNotEmpty(data.getUsrRoleId())) {
	        if (CommUtils.nvlTrim(data.getBzentyNo()).isEmpty() && !CommUtils.nvlTrim(data.getUsrRoleId()).equals(CommConst.USER_ROLE_USR)) {
	        	// 개인회원인데 권한이 개인회원이 아닐 경우
	        	errors.reject("roleId", "개인회원이 얻을 수 없는 권한입니다.");
	        } else if (!CommUtils.nvlTrim(data.getBzentyNo()).isEmpty()) {
	        	// 기업회원인 경우
	        	if (CommUtils.nvlTrim(data.getUsrRoleId()).equals(CommConst.USER_ROLE_USR) || CommUtils.nvlTrim(data.getUsrRoleId()).equals(CommConst.ROLE_RESTRICTED)) {
	        		errors.reject("roleId", "기업회원이 얻을 수 없는 권한입니다.");
	        	}
	        	if (CommUtils.nvlTrim(data.getRprsYn()).isEmpty()) {
	        		errors.reject("roleId", "대표계정 유무는 필수 선택사항입니다.");
	        	}
	        }
        }
    }
}
