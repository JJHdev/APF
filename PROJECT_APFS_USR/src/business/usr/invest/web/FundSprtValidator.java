package business.usr.invest.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.usr.invest.service.FundVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 펀드지원하기 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : FundSprtValidator
 * @author  : LSH
 * @since   : 2023.06.22
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
public class FundSprtValidator implements Validator {
	
    @Resource(name="CommService")
    protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return FundVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

    	FundVO data = (FundVO) o;
    	
    	String mode     = data.getMode    (); // 처리모드(등록/수정)
    	String gsUserNo = data.getGsUserNo(); // 로그인사용자
    	
    	// [기본검증]
    	if (CommUtils.isEmpty(gsUserNo))
            errors.reject("gsUserNo", "인증된 사용자를 확인할 수 없습니다.");

    	// 지원하기 등록 검증
    	if (CommConst.INSERT.equals(mode)) {

    		if (CommUtils.isEmpty(data.getFundNo()))
                errors.reject("fundNo", "펀드번호를 확인할 수 없습니다.");
        	if (CommUtils.isEmpty(data.getBzentyNo()))
                errors.reject("bzentyNo", "지원업체를 확인할 수 없습니다.");
    		
    	}
    	// 상태변경(다중항목) 검증
    	else if (CommConst.UPDATE.equals(mode)) {
    		// 변경할 상태값
    		String status = data.getSprtSttsCd();
    		// 저장할 다중항목
    		List<FundVO> list = data.getSprtList();
    		
        	if (CommUtils.isEmpty(status))
                errors.reject("sprtSttsCd", "변경할 상태를 확인할 수 없습니다.");
        	if (list == null || list.size() == 0)
                errors.reject("sprtList", "변경할 지원정보가 존재하지 않습니다.");
        	
        	int seq = 1;
        	
        	if (list != null) {
        		for (FundVO item : list) {
            		if (CommUtils.isEmpty(item.getFundNo()))
            			errors.reject("fundNo", "["+seq+"]번 항목 - 펀드번호를 확인할 수 없습니다.");
            		if (CommUtils.isEmpty(item.getBzentyNo()))
            			errors.reject("bzentyNo", "["+seq+"]번 항목 - 지원업체를 확인할 수 없습니다.");
            		if (CommUtils.isEqual(status, item.getSprtSttsCd()))
            			errors.reject("sprtList", "["+seq+"]번 항목 - 변경할 상태와 동일한 상태값입니다.");
            		seq++;
            	}
        	}
    	}
    }
}
