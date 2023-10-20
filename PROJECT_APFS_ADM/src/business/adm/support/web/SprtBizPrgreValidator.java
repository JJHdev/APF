package business.adm.support.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.adm.CodeConst;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import common.util.CommUtils;

/**
 * [검증클래스] - 지원사업관리 - 신청현황관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : SprtBizPrgreValidator
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
public class SprtBizPrgreValidator implements Validator {
	
	@Autowired
	protected CommService commService;

    @Override
    public boolean supports(Class<?> type) {
        return SupportVO.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	
    	SupportVO data = (SupportVO) o;
    	
    	String mode = data.getMode();
    	
    	String prgrsSttsCd  		= CommUtils.nvlTrim(data.getPrgrsSttsCd());
    	String sprtAplySeCd 		= CommUtils.nvlTrim(data.getSprtAplySeCd());
    	
    	String prgrmSeCd			= CommUtils.nvlTrim(data.getPrgrmSeCd());			// 투자유치 전 지원사업 프로그램명
    	String prgrsDetailSttsCd	= CommUtils.nvlTrim(data.getPrgrsDetailSttsCd());	// 투자유치 전 지원사업 진행상태
    	
    	/* 투자유치 전 */
    	// 상담 - 상담센터, 상담담당자, 상담일, 상담내용
    	
    	// 현장코칭 - 생략,중단: 코멘트 / 배정: 담당자,담당자연락처,배정일자,코멘트 / 완료 : 담당자,대면여부,담당자연락처,완료일자,코멘트 /
    	
    	// 맞춤형컨설팅 - 생략,중단: 코멘트 / 배정: 차수,담당자,담당자연락처,배정일자,코멘트 / 완료 : 차수,담당자,대면여부,담당자연락처,완료일자,코멘트
    	
    	/* 투자유치 후 */
    	// 반려,보완요청: 코멘트, 심사완료: 담당자,담당자연락처, 사업종료: 지원사업 완료일자
    	
    	/* 크라우드 펀딩 */
    	// 반려,보완요청: 코멘트, 심사완료: 담당자,담당자연락처
    	
    	
    	if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getSprtAplyNo()))) {
        	errors.reject("sprtAplyNo", "지원신청번호는 필수 입력사항입니다.");
        }
    	
    	if (CommConst.DELETE.equals(mode)) {
        	return;
        }
    	
    	if (CommUtils.isEmpty(prgrsSttsCd)) {
        	errors.reject("prgrsSttsCd", "진행상태코드는 필수 입력사항입니다.");
        }
    	
    	if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelno())) && !CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelnoModal2())))
    		data.setPicTelno(CommUtils.nvlTrim(data.getPicTelnoModal2()));
    	else if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelno())) && !CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelnoModal5())))
    		data.setPicTelno(CommUtils.nvlTrim(data.getPicTelnoModal5()));
    	// 담당자 연락처 세팅
    	
    	if (CommUtils.isEmpty(sprtAplySeCd)) {
    		errors.reject("sprtAplySeCd", "지원 신청 정보가 올바르지 않습니다.");
    	} else if (sprtAplySeCd.equals(CodeConst.SPRT_APLY_SE_BEFORE)) {	// 투자유치 전
    		if (prgrsSttsCd.equals("20")) {			// 상담완료
    			if (!CommUtils.isEmpty(CommUtils.nvlTrim(data.getDscsnCntrCd()))) {	// 상담센터 값이 있을 경우, 상담완료로 간주함
    				if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicNm()))) {
    					errors.reject("picNm", "상담담당자는 필수 입력사항입니다.");
    				} else if (CommUtils.nvlTrim(data.getPicNm()).length() > 20) {
    					errors.reject("picNm", "상담담당자는 20자를 초과할 수 없습니다.");
    				}
    				
    				if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrcsYmd()))) {
    					errors.reject("prcsYmd", "상담일은 필수 입력사항입니다.");
    				} else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getPrcsYmd()), "-"))) {
    					errors.reject("prcsYmd", "상담일의 형식이 올바르지 않습니다.");
    				}
    				
    				if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrgrsCn()))) {
    					errors.reject("prgrsCn", "상담내용은 필수 입력사항입니다.");
    				} else if (CommUtils.nvlTrim(data.getPrgrsCn()).length() > 300) {
    					errors.reject("prgrsCn", "상담내용은 300자를 초과할 수 없습니다.");
    				}
    			} else {													// 진행현황등록
    				if (CommUtils.isEmpty(prgrmSeCd)) {
    					errors.reject("prgrmSeCd", "프로그램명은 필수 입력사항입니다.");
    				} else if (!commService.existCode(CodeConst.PRGRM_SE, prgrmSeCd)) {
    					errors.reject("prgrmSeCd", "프로그램명 정보를 찾을 수 없습니다.");
    				}
    				
    				if (CommUtils.isEmpty(prgrsDetailSttsCd)) {
    					errors.reject("prgrsDetailSttsCd", "진행상태는 필수 입력사항입니다.");
    				} else if (!commService.existCode(CodeConst.PRGRS_DETAIL_STTS, prgrsDetailSttsCd)) {
    					errors.reject("prgrsDetailSttsCd", "진행상태의 형식이 올바르지 않습니다.");
    				}
    				
    				// 중단, 생략은 필수값이 '코멘트' 밖에 없음
    				if (prgrsDetailSttsCd.equals("20") || prgrsDetailSttsCd.equals("40")) {	// 배정, 완료
    					if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicNm()))) {
        					errors.reject("picNm", "담당자는 필수 입력사항입니다.");
        				} else if (CommUtils.nvlTrim(data.getPicNm()).length() > 20) {
        					errors.reject("picNm", "담당자는 20자를 초과할 수 없습니다.");
        				}
    					
    					if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelno()))) {
    	    				errors.reject("picTelno", "담당자 연락처는 필수 입력사항입니다.");
    	    			}/* else if (!CommUtils.isValidTelno(CommUtils.nvlTrim(data.getPicTelno()), "")) {
    	    	        	errors.reject("picTelno", "담당자 연락처의 형식이 올바르지 않습니다.");
    	    	        }*/
    					
    					if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrcsYmd()))) {
        					errors.reject("prcsYmd", "일자는 필수 입력사항입니다.");
        				} else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getPrcsYmd()), "-"))) {
        					errors.reject("prcsYmd", "일자의 형식이 올바르지 않습니다.");
        				}
    					
    					if(prgrmSeCd.equals("SB04")) { // IR 설명회는 배정 상태일 때 투자설명회가 필수
    						if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getEvntNo()))) {
            					errors.reject("evntNo", "투자설명회는 필수 입력사항입니다.");
            				}
    					}
    				}
    				
    				if(prgrmSeCd.equals("SB03")) { // 맞춤형 컨설팅은 차수가 필수
						if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrcsCycl()))) {
	    					errors.reject("prcsCycl", "차수는 필수 입력사항입니다.");
	    				} else if (!commService.existCode(CodeConst.PRCS_CYCL, CommUtils.nvlTrim(data.getPrcsCycl()))) {
	    					errors.reject("prcsCycl", "차수의 형식이 올바르지 않습니다.");
	    				}
						
						if(prgrsDetailSttsCd.equals("40")) { // 맞춤형 컨설팅 완료 단계에서는 대면여부가 필수
							if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getFtfYn()))) {
		    					errors.reject("ftfYn", "대면여부는 필수 입력사항입니다.");
		    				} else if (!CommUtils.nvlTrim(data.getFtfYn()).equals(CommConst.YES) && !CommUtils.nvlTrim(data.getFtfYn()).equals(CommConst.NO)) {
		    					errors.reject("ftfYn", "대면여부의 형식이 올바르지 않습니다.");
		    				}
    					}
					}
    				
    				if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrgrsCn()))) {
    					errors.reject("prgrsCn", "코멘트는 필수 입력사항입니다.");
    				} else if (CommUtils.nvlTrim(data.getPrgrsCn()).length() > 300) {
    					errors.reject("prgrsCn", "코멘트는 300자를 초과할 수 없습니다.");
    				}
    				
    			}
    		} else if (prgrsSttsCd.equals("90")) {	// 사업종료
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrcsYmd()))) {
					errors.reject("prcsYmd", "지원사업 완료일자는 필수 입력사항입니다.");
				} else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getPrcsYmd()), "-"))) {
					errors.reject("prcsYmd", "지원사업 완료일자의 형식이 올바르지 않습니다.");
				}
    		} else {
    			errors.reject("prgrsSttsCd", "등록 정보가 올바르지 않습니다.");
    		}
    	} else if (sprtAplySeCd.equals(CodeConst.SPRT_APLY_SE_AFTER)) {		// 투자유치 후
    		if (prgrsSttsCd.equals("20")) {			// 반려
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrgrsCn()))) {
					errors.reject("prgrsCn", "코멘트는 필수 입력사항입니다.");
				} else if (CommUtils.nvlTrim(data.getPrgrsCn()).length() > 300) {
					errors.reject("prgrsCn", "코멘트는 300자를 초과할 수 없습니다.");
				}
    		} else if (prgrsSttsCd.equals("30")) {	// 보완요청
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrgrsCn()))) {
					errors.reject("prgrsCn", "코멘트는 필수 입력사항입니다.");
				} else if (CommUtils.nvlTrim(data.getPrgrsCn()).length() > 300) {
					errors.reject("prgrsCn", "코멘트는 300자를 초과할 수 없습니다.");
				}
    		} else if (prgrsSttsCd.equals("40")) {	// 심사완료
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicNm()))) {
					errors.reject("picNm", "상담담당자는 필수 입력사항입니다.");
				} else if (CommUtils.nvlTrim(data.getPicNm()).length() > 20) {
					errors.reject("picNm", "상담담당자는 20자를 초과할 수 없습니다.");
				}
    			
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelno()))) {
    				errors.reject("picTelno", "담당자 연락처는 필수 입력사항입니다.");
    			}/* else if (!CommUtils.isValidTelno(CommUtils.nvlTrim(data.getPicTelno()), "")) {
    	        	errors.reject("picTelno", "담당자 연락처의 형식이 올바르지 않습니다.");
    	        }*/
    		} else if (prgrsSttsCd.equals("90")) {	// 사업종료
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrcsYmd()))) {
					errors.reject("prcsYmd", "지원사업 완료일자는 필수 입력사항입니다.");
				} else if (!CommUtils.isValidDate(CommUtils.toShortDate(CommUtils.nvlTrim(data.getPrcsYmd()), "-"))) {
					errors.reject("prcsYmd", "지원사업 완료일자의 형식이 올바르지 않습니다.");
				}
    		} else {
    			errors.reject("prgrsSttsCd", "등록 정보가 올바르지 않습니다.");
    		}
    	} else if (sprtAplySeCd.equals(CodeConst.SPRT_APLY_SE_CROWD)) {		// 크라우드 펀딩
    		if (prgrsSttsCd.equals("20")) {			// 반려
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrgrsCn()))) {
					errors.reject("prgrsCn", "코멘트는 필수 입력사항입니다.");
				} else if (CommUtils.nvlTrim(data.getPrgrsCn()).length() > 300) {
					errors.reject("prgrsCn", "코멘트는 300자를 초과할 수 없습니다.");
				}
    		} else if (prgrsSttsCd.equals("30")) {	// 보완요청
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPrgrsCn()))) {
					errors.reject("prgrsCn", "코멘트는 필수 입력사항입니다.");
				} else if (CommUtils.nvlTrim(data.getPrgrsCn()).length() > 300) {
					errors.reject("prgrsCn", "코멘트는 300자를 초과할 수 없습니다.");
				}
    		} else if (prgrsSttsCd.equals("40")) {	// 심사완료
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicNm()))) {
					errors.reject("picNm", "상담담당자는 필수 입력사항입니다.");
				} else if (CommUtils.nvlTrim(data.getPicNm()).length() > 20) {
					errors.reject("picNm", "상담담당자는 20자를 초과할 수 없습니다.");
				}
    			
    			if (CommUtils.isEmpty(CommUtils.nvlTrim(data.getPicTelno()))) {
    				errors.reject("picTelno", "담당자 연락처는 필수 입력사항입니다.");
    			}/* else if (!CommUtils.isValidTelno(CommUtils.nvlTrim(data.getPicTelno()), "")) {
    	        	errors.reject("picTelno", "담당자 연락처의 형식이 올바르지 않습니다.");
    	        }*/
    		} else {
    			errors.reject("prgrsSttsCd", "등록 정보가 올바르지 않습니다.");
    		}
    	} else {
    		errors.reject("sprtAplySeCd", "지원 신청 정보가 올바르지 않습니다.");
    	}
    }
}
