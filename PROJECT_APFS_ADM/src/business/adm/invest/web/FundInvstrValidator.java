package business.adm.invest.web;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.CodeConst;
import business.adm.invest.service.FundVO;
import business.adm.invest.service.impl.FundDAO;
import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserVO;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 투자정보관리 - 모태펀드등록 조합원 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : FundValidator
 * @author  : LHB
 * @since   : 2023.04.27
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
public class FundInvstrValidator implements Validator {
	
	@Autowired
	protected CommService commService;
	
	@Resource(name = "FundDAO")
    private FundDAO fundDAO;

    @Override
    public boolean supports(Class<?> type) {
        return FundVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
        FundVO data = (FundVO) o;
        
        String mode = data.getMode();
        String[] bzentyNoArr = data.getBzentyNoArr();
        
        if (!mode.equals(CommConst.INSERT) && !mode.equals(CommConst.DELETE)) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        
        if (mode.equals(CommConst.DELETE)) {
        	return;
        }
        
        if (CommUtils.nvlTrim(data.getFundNo()).isEmpty()) {
            errors.reject("fundNo", "펀드번호는 필수 선택사항입니다.");
        }
        
        if(bzentyNoArr == null || bzentyNoArr.length < 1) {
        	// 비회원 조합원 추가
        	if (CommUtils.nvlTrim(data.getInvtBzentyNm()).isEmpty()) {
        		errors.reject("invtBzentyNm", "조합원명은 필수 입력사항입니다.");
        	} else if (CommUtils.nvlTrim(data.getInvtBzentyNm()).length() > 100) {
        		errors.reject("invtBzentyNm", "조합원명은 100자를 초과할 수 없습니다.");
        	}
        	
        	if (CommUtils.nvlTrim(data.getBrno()).isEmpty()) {
        		errors.reject("brno", "사업자 등록번호는 필수 입력사항입니다.");
        	} else if (CommUtils.nvlTrim(data.getBrno()).length() != 10) {
        		errors.reject("brno", "사업자 등록번호의 형식이 올바르지 않습니다.");
        	}
        } else {
        	// 회원 조합원 추가
        	for(int i=0 ; i<bzentyNoArr.length ; i++) {
            	if (CommUtils.nvlTrim(bzentyNoArr[i]).isEmpty()) {
            		errors.reject("bzentyNo", "잘못된 업체번호입니다.");
            		break;
            	} else {
            		data.setBzentyNo(bzentyNoArr[i]);
    				int ret = fundDAO.viewFundInvstr(data);
    				if (mode.equals(CommConst.INSERT)) {
    					if (ret > 0)
    						errors.reject("bzentyNo", "이미 등록된 업체가 존재합니다.");
    				} else if (mode.equals(CommConst.DELETE)) {
    					if (ret < 1)
    						errors.reject("bzentyNo", "등록되지 않은 업체입니다.");
    				}
    				
            	}
            }
        }
    }
}
