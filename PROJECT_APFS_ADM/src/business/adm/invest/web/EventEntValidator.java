package business.adm.invest.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.invest.service.EventVO;
import business.adm.invest.service.impl.EventDAO;
import business.adm.invest.service.impl.FundDAO;
import business.com.CommConst;
import business.com.user.service.UserVO;
import common.util.CommUtils;

/**
 * [검증클래스] - 투자정보관리-참여기업등록 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : EventEntValidator
 * @author  : LHB
 * @since   : 2023.06.05
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
public class EventEntValidator implements Validator {
	
	@Resource(name = "EventDAO")
    private EventDAO eventDAO;

    @Override
    public boolean supports(Class<?> type) {
        return EventVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
    	EventVO data = (EventVO) o;
    	
    	String mode = data.getMode();
        
        if (CommUtils.nvlTrim(mode).isEmpty()) {
        	errors.reject("mode", "실행 모드를 확인할 수 없습니다.");
        }
        if (CommConst.UPDATE.equals(mode) ||
            CommConst.DELETE.equals(mode) ) {
            if (CommUtils.isEmpty(data.getEvntNo())) {
                errors.reject("evntNo", "행사번호를 확인할 수 없습니다.");
            }
        }
        if (CommConst.DELETE.equals(mode)) {
        	return;
        }
        
        String evntType = data.getEvntType();
        
        if (CommUtils.nvlTrim(evntType).equals(CommConst.EVNT_INVT)) {			// 투자자 등록
        	String[] bzentyNoArr = data.getBzentyNoArr();
        	
        	if(bzentyNoArr.length < 1) {
            	errors.reject("bzentyNo", "업체는 필수 선택사항입니다.");
            } else {
            	for(int i=0 ; i<bzentyNoArr.length ; i++) {
                	if (CommUtils.nvlTrim(bzentyNoArr[i]).isEmpty()) {
                		errors.reject("bzentyNo", "잘못된 업체번호입니다.");
                		break;
                	} else {
                		data.setBzentyNo(bzentyNoArr[i]);
        				int ret = eventDAO.viewEventInvt(data);
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
        } else if (CommUtils.nvlTrim(evntType).equals(CommConst.EVNT_ENT)) {	// 참여 경영체 사업계획서
        	if (CommUtils.nvlTrim(data.getBzentyNm()).isEmpty()) {
                errors.reject("bzentyNm", "기업명은 필수 입력사항입니다.");
            } else if (CommUtils.nvlTrim(data.getBzentyNm()).length() > 100) {
            	errors.reject("bzentyNm", "기업명은 100자를 초과할 수 없습니다.");
            }
        	
        	if (!CommUtils.nvlTrim(data.getBrno()).isEmpty() && CommUtils.nvlTrim(data.getBrno()).length() != 10) {
        		errors.reject("brno", "사업자번호의 형식이 올바르지 않습니다.");
        	}
            
            if (CommUtils.nvlTrim(data.getRprsvNm()).isEmpty()) {
                errors.reject("rprsvNm", "대표자는 필수 입력사항입니다.");
            } else if (CommUtils.nvlTrim(data.getRprsvNm()).length() > 20) {
            	errors.reject("rprsvNm", "대표자는 20자를 초과할 수 없습니다.");
            }
            
            if (CommUtils.nvlTrim(data.getTelno()).isEmpty()) {
                errors.reject("telno", "연락처는 필수 입력사항입니다.");
            } else if (!CommUtils.isValidTelno(CommUtils.nvlTrim(data.getTelno()), "")) {
            	errors.reject("telno", "연락처의 형식이 올바르지 않습니다.");
            }
            
            if (CommUtils.nvlTrim(data.getEmlAddr()).isEmpty()) {
                errors.reject("emlAddr", "이메일주소는 필수 입력사항입니다.");
            } else if (CommUtils.nvlTrim(data.getEmlAddr()).length() > 100) {
            	errors.reject("emlAddr", "이메일주소는 100자리를 초과할 수 없습니다.");
            } else if (!CommUtils.isValidEmail(CommUtils.nvlTrim(data.getEmlAddr()))) {
            	errors.reject("emlAddr", "이메일주소의 형식이 올바르지 않습니다.");
            }
            
            if (CommUtils.nvlTrim(data.getMainBizCn()).isEmpty()) {
                errors.reject("mainBizCn", "주요사업내용은 필수 입력사항입니다.");
            } else if (CommUtils.nvlTrim(data.getMainBizCn()).length() > 2000) {
            	errors.reject("mainBizCn", "주요사업내용은 2000자를 초과할 수 없습니다.");
            }
        } else {
        	errors.reject("evntType", "잘못된 정보입니다.");
        }
        
        // 첨부파일, 썸네일 조건처리
        
    }
    
}
