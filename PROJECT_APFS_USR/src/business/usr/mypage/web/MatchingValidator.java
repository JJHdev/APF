package business.usr.mypage.web;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.usr.ValidRule;
import business.usr.mypage.service.MatchingVO;
import common.user.UserInfo;
import common.util.CommUtils;

/**
 * [검증클래스] - 매칭서비스 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : MatchingValidator
 * @author  : LSH
 * @since   : 2023.05.16
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
public class MatchingValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return MatchingVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

    	MatchingVO data = (MatchingVO) o;
    	
    	// 세션사용자 정보
    	UserInfo userInfo = data.getUserInfo();
    	
        if (CommUtils.nvlTrim(userInfo.getUserNo()).isEmpty()) {
            errors.reject("userNo", "사용자정보를 확인할 수 없습니다.");
        }
        if (CommUtils.nvlTrim(userInfo.getRoleId()).isEmpty()) {
            errors.reject("roleId", "사용자권한을 확인할 수 없습니다.");
        }
        
		// 일반회원인 경우
        if (userInfo.isUsr  ()) {
            // 필수체크
        	ValidRule.NEED.validate (errors, data, "bizFldList"       , "사업분야");
        	ValidRule.NEED.validate (errors, data, "bizTrgtList"      , "지원대상");
        	ValidRule.NEED.validate (errors, data, "bizTrgtAgeList"   , "지원연령");
        	ValidRule.NEED.validate (errors, data, "bizTrgtFntnPdList", "창업기간");
        }
		// 관리자/투자자/유관기관인 경우
		else if (userInfo.isAdmin() || 
				 userInfo.isInv() || 
	        	 userInfo.isRis()) {
            // 필수체크
        	ValidRule.NEED.validate (errors, data, "invtFldList"      , "투자분야");
        	ValidRule.NEED.validate (errors, data, "bizFldList"       , "사업분야");
        	ValidRule.NEED.validate (errors, data, "invtHopeList"     , "투자희망금액");
		}
		// 경영체인 경우
		else if (userInfo.isEnt()) {
            // 필수체크
        	ValidRule.NEED.validate (errors, data, "invtFldList"      , "투자분야");
        	ValidRule.NEED.validate (errors, data, "fundScaleList"    , "펀드규모");
        	ValidRule.NEED.validate (errors, data, "bizFldList"       , "사업분야");
        	ValidRule.NEED.validate (errors, data, "bizTrgtList"      , "지원대상");
        	ValidRule.NEED.validate (errors, data, "bizTrgtAgeList"   , "지원연령");
        	ValidRule.NEED.validate (errors, data, "bizTrgtFntnPdList", "창업기간");
		}
        // 권한확인이 안되는 경우
		else {
            errors.reject("roleId", "사용자권한을 확인할 수 없습니다.");
		}
    }
}
