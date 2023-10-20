package business.usr.support.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.usr.CodeConst;
import business.usr.ValidRule;
import business.usr.support.service.DscsnVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 상담신청 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : DscsnValidator
 * @author  : LSH
 * @since   : 2023.06.10
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
public class DscsnValidator implements Validator {
	
    @Resource(name="CommService")
    protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return DscsnVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

    	DscsnVO data = (DscsnVO) o;
    	
    	String mode       = data.getMode      (); // 처리모드(등록/수정)
    	String stepCd     = data.getStepCd    (); // 단계코드
    	String sprtSeCd   = data.getSprtSeCd  (); // 신청구분코드
    	String gsBzentyYn = data.getGsBzentyYn(); // 업체회원여부
    		
    	// [기본검증]
    	//------------------------------------------------------------------
    	if (CommUtils.isEmpty(sprtSeCd))
            errors.reject("sprtSeCd", "지원신청구분을 확인할 수 없습니다.");
    	if (CommUtils.isEmpty(stepCd))
            errors.reject("stepCd", "현재단계를 확인할 수 없습니다.");
    	// 1) 처리모드가 정상적인지 확인
    	if (!CommUtils.isEqual(CommConst.INSERT, mode) &&
    		!CommUtils.isEqual(CommConst.UPDATE, mode))
			errors.reject("error.sprt.mode.notValid");
    	// 2) 단계코드가 정상적인지 확인
    	if (!CommUtils.exist(new String[] {
    			CodeConst.SPRT_FORM, 
    			CodeConst.SPRT_FILE}, stepCd))
			errors.reject("error.sprt.step.notValid");
    	// 3) 구분코드가 유효한지 확인
    	if (!commService.existCode(CodeConst.SPRT_APLY_SE, sprtSeCd))
			errors.reject("error.sprt.aplySe.notValid");
    	// 5) 업체회원만 접근가능
    	if (!CommUtils.isEqual(CommConst.YES, gsBzentyYn))
    		errors.reject("error.sprt.user.notAccess");
    	if (CommUtils.isEmpty(data.getBzentyNo()))
            errors.reject("bzentyNo", "신청업체를 확인할 수 없습니다.");
    	
    	// 수정인 경우
    	if (CommUtils.isEqual(CommConst.UPDATE, mode)) {
        	if (CommUtils.isEmpty(data.getDscsnAplyNo()))
                errors.reject("dscsnAplyNo", "신청번호를 확인할 수 없습니다.");
    	}
    	
    	ValidRule.NEED.validate (errors, data, "picNm"       , "담당자 이름");
    	ValidRule.NEED.validate (errors, data, "picTelno"    , "담당자 직통번호"); // 필수
    	//ValidRule.PHONE.validate(errors, data, "picTelno"    , "담당자 직통번호"); // phone
    	ValidRule.NEED.validate (errors, data, "picEmlAddr"  , "담당자 이메일"); // 필수
    	ValidRule.EMAIL.validate(errors, data, "picEmlAddr"  , "담당자 이메일"); // email
    }
}
