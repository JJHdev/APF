package business.com.user.web;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.com.user.service.GroupService;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.ValidRule;
import common.util.CommUtils;

/**
 * [검증클래스] - 회원가입 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : UserValidator
 * @author  : LSH
 * @since   : 2023.03.20
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
public class UserValidator implements Validator {
	
    @Resource(name="UserService")
    protected UserService userService;

	@Autowired
	private GroupService groupService;

    @Override
    public boolean supports(Class<?> type) {
        return UserVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
        UserVO data = (UserVO) o;
        
        String mode = data.getMode(); // 저장모드

        // 개인정보 변경인 경우
        if (CommConst.UPDATE.equals(mode)) {
        	// 비로그인시
        	if (!data.getUserInfo().isLogin()) {
        		errors.reject("fail.common.nologin");
        		return;
        	}
        	// 변경대상 정보확인
	        if (CommUtils.nvlTrim(data.getMblTelno()).isEmpty() &&
	        	CommUtils.nvlTrim(data.getPswd()).isEmpty()) {
	            errors.reject("pswd", "변경한 정보가 없습니다.");
	            return;
	        }
	        // 비밀번호변경 필수정보 확인
	        if (!CommUtils.nvlTrim(data.getPswd()).isEmpty() &&
	        	CommUtils.nvlTrim(data.getUserId()).isEmpty()) {
	        	errors.reject("pswd", "사용자 정보를 확인할 수 없습니다.");
	            return;
	        }
	        ValidRule.PHONE.validate(errors, data, "mblTelno", "휴대폰 번호");
	        ValidRule.PSWD.validate (errors, data, "pswd"    , "비밀번호");
			try {
				// 개인정보 조회
				UserVO user = userService.getUser(data.getUserNo());
				// 사용자 일치여부 확인
				if (!CommUtils.isEqual(data.getUserId(), user.getUserId())) {
		            errors.reject("error.comm.notValid");
		            return;
				}
			} catch (NullPointerException e) {
				errors.reject("error.comm.selectFail");
				return;
			} catch (Exception e) {
				errors.reject("error.comm.selectFail");
				return;
			}
			// 모빌리언스 휴대폰 본인인증 완료시 세션검증
			if (CommConst.YES.equals(data.getCertYn())) {
		        //// 모빌리언스 인증이 완료되었는지 확인
		        //if (!CommUtils.isEqual(data.getSsUserNm()  , data.getUserNm  ())||
		        //	!CommUtils.isEqual(data.getSsMblTelno(), data.getMblTelno()))
		        //	errors.reject("certYn", "본인인증 정보가 일치하지 않습니다.");
			}
			// 비밀번호 변경여부 확인
			else if (CommUtils.isEmpty(data.getPswd())) {
				errors.reject("pswd", "변경하신 정보가 없습니다.");
			}
        }
        // 회원탈퇴인 경우
        else if (CommConst.DELETE.equals(mode)) {
        	// 비로그인시
        	if (!data.getUserInfo().isLogin()) {
        		errors.reject("fail.common.nologin");
        		return;
        	}
	        if (CommUtils.nvlTrim(data.getUserNo()).isEmpty()) {
	            errors.reject("userNo", "사용자를 확인할 수 없습니다.");
	        }
			try {
				// 개인정보 조회
				UserVO user = userService.getUser(data.getUserNo());
				// 사용자 일치여부 확인
				if (!CommUtils.isEqual(data.getUserId(), user.getUserId())) {
		            errors.reject("error.comm.notValid");
		            return;
				}
			} catch (NullPointerException e) {
				errors.reject("error.comm.selectFail");
				return;
			} catch (Exception e) {
				errors.reject("error.comm.selectFail");
				return;
			}
			try {
				// 대표계정 탈퇴불가
				if (CommConst.YES.equals(groupService.getGrpRprsYn(data.getUserNo()))) {
		            errors.reject("userNo", "대표계정은 탈퇴하실 수 없습니다. 담당자에게 문의하세요.");
		            return;
				}
			} catch (NullPointerException e) {
				errors.reject("error.comm.selectFail");
				return;
			} catch (Exception e) {
				errors.reject("error.comm.selectFail");
				return;
			}
        }
        // 회원가입인 경우
        else if (CommConst.INSERT.equals(mode)) {
        	
	        ValidRule.NEED.validate (errors, data, "userNm"  , "성명");
	        ValidRule.NEED.validate (errors, data, "mblTelno", "휴대폰 번호");
	        ValidRule.PHONE.validate(errors, data, "mblTelno", "휴대폰 번호");
	        ValidRule.NEED.validate (errors, data, "userId"  , "아이디");
	        ValidRule.EMAIL.validate(errors, data, "userId"  , "아이디");
	        ValidRule.NEED.validate (errors, data, "pswd"    , "비밀번호");
	        ValidRule.PSWD.validate (errors, data, "pswd"    , "비밀번호");
	        
			// 이용약관 동의여부 확인
	        //if (!CommConst.YES.equals(data.getSrvcTrmsAgreYn())) {
	        //    errors.reject("srvcTrmsAgreYn", "이용약관 동의가 완료되지 않았습니다.");
	        //    return;
	        //}
	        //if (!CommConst.YES.equals(data.getPrvcClctAgreYn())) {
	        //    errors.reject("prvcClctAgreYn", "개인정보 수집 및 이용 동의가 완료되지 않았습니다.");
	        //    return;
	        //}
	        // 모빌리언스 휴대폰 본인인증 사용시
	        if (CommConst.IS_MOBILIANS) {
		        if (!CommConst.YES.equals(data.getCertYn())) {
		            errors.reject("certYn", "본인인증이 완료되지 않았습니다.");
		            return;
		        }
				//// 모빌리언스 휴대폰 본인인증 완료시 세션검증
		        //if (!CommUtils.isEqual(data.getSsUserNm()  , data.getUserNm  ())||
		        //	!CommUtils.isEqual(data.getSsMblTelno(), data.getMblTelno()))
		        //	errors.reject("certYn", "본인인증 정보가 일치하지 않습니다.");
	        }
			try {
				// 이미 가입한 회원인지 확인
				if (userService.existUser(data.getUserId())) {
					errors.reject("userId", "이미 가입한 회원입니다.");
				}
			} catch (NullPointerException e) {
				errors.reject("error.comm.selectFail");
			} catch (Exception e) {
				errors.reject("error.comm.selectFail");
			}
    	}
        else {
        	errors.reject("mode", "잘못된 접근입니다.");
        }
    }
    
}
