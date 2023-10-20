package business.usr.mypage.web;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.usr.mypage.service.MeetingVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 미팅신청 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : MeetingValidator
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
public class MeetingValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return MeetingVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

        MeetingVO data = (MeetingVO) o;
        
        String mode = data.getMode();
        if (CommConst.UPDATE.equals(mode) ||
        	CommConst.DELETE.equals(mode)) {
            if (CommUtils.isEmpty(data.getSn())) {
                errors.reject("sn", "일련번호는 필수입력사항입니다.");
            }
        }
        if (CommConst.INSERT.equals(mode) ||
        	CommConst.UPDATE.equals(mode)) {
            if (CommUtils.nvlTrim(data.getAplcntNo()).isEmpty()) {
                errors.reject("aplcntNo", "신청자번호는 필수입력사항입니다.");
            }
            if (CommUtils.nvlTrim(data.getTrgtBzentyNo()).isEmpty()) {
                errors.reject("trgtBzentyNo", "대상업체번호는 필수입력사항입니다.");
            }
            if (CommUtils.nvlTrim(data.getAplyCn()).isEmpty()) {
                errors.reject("aplyCn", "신청내용는 필수입력사항입니다.");
            }
        }
    }
}
