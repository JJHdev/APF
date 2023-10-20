package business.usr.mypage.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import business.com.CommConst;
import business.com.user.service.GroupService;
import business.com.user.service.GroupVO;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.CodeConst;
import business.usr.ValidRule;
import business.usr.invest.service.EntService;
import common.user.UserInfo;
import common.util.CommUtils;

/**
 * [검증클래스] - 그룹관리 Server Side의 데이터 검증을 위한 Validator
 *
 * @class   : GroupValidator
 * @author  : LSH
 * @since   : 2023.06.27
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
public class GroupValidator implements Validator {
    
    @Resource(name = "EntService")
    private EntService entService;
    
    @Resource(name = "UserService")
    private UserService userService;
    
    @Resource(name = "GroupService")
    private GroupService groupService;

    @Override
    public boolean supports(Class<?> type) {
        return GroupVO.class.isAssignableFrom(type);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
    	
    	GroupVO data = (GroupVO) o;
    	
    	// 세션사용자 정보
    	UserInfo userInfo = data.getUserInfo();
    	
        if (CommUtils.nvlTrim(userInfo.getUserNo()).isEmpty()) {
            errors.reject("userNo", "사용자정보를 확인할 수 없습니다.");
        }
        if (CommUtils.nvlTrim(userInfo.getRoleId()).isEmpty()) {
            errors.reject("roleId", "사용자권한을 확인할 수 없습니다.");
        }
        if (CommUtils.nvlTrim(userInfo.getBzentyNo()).isEmpty()) {
            errors.reject("bzentyNo", "업체정보를 확인할 수 없습니다.");
        }
        if (!CommConst.YES.equals(data.getAccessYn())) {
            errors.reject("accessYn", "승인되지 않은 업체입니다.");
        }
        if (!CommConst.YES.equals(data.getRprsYn())) {
            errors.reject("rprsYn", "대표계정만 접근이 가능합니다.");
        }
    	if (!CommUtils.exist(new String[] {
    			CodeConst.MYPG_GROUP_CODE, 
    			CodeConst.MYPG_GROUP_MMBR, 
    			CodeConst.MYPG_GROUP_ROLE}, data.getPageCd())) {
            errors.reject("pageCd", "화면구분을 확인할 수 없습니다.");
            return;
        }

    	String mode = data.getMode(); // 처리모드
        String act  = data.getAct (); // 행위구분
        
        // 등록인 경우 (추후 회원가입시 구현예정)
        if (CommUtils.isEqual(CommConst.INSERT, mode)) {
        } 
    	// 삭제인 경우
        else if (CommUtils.isEqual(CommConst.DELETE, mode)) {
        	if (!errors.hasErrors()) {
	    		// 삭제시 검증처리
	    		_validateDelete(errors, data);
        	}
    	}
    	// 수정인 경우
        else if (CommUtils.isEqual(CommConst.UPDATE, mode)) {
        	if (!CommUtils.exist(new String[] {
        			CommConst.ACT_GROUP_CODE, 
        			CommConst.ACT_GROUP_ROLE, 
        			CommConst.ACT_GROUP_RPRS}, act)) {
                errors.reject("act", "잘못된 접근이거나 처리할 데이터에 오류가 있습니다.");
            }
        	if (!errors.hasErrors()) {
            	// 그룹코드저장인 경우
            	if (CommUtils.isEqual(CommConst.ACT_GROUP_CODE, act)) {
                	// 그룹코드 검증처리
            		_validateCode(errors, data);
            	}
            	// 대표계정변경인 경우
            	else if (CommUtils.isEqual(CommConst.ACT_GROUP_RPRS, act)) {
                	// 대표변경 검증처리
            		_validateRprs(errors, data);
            	}
            	// 그룹권한저장인 경우
            	else if (CommUtils.isEqual(CommConst.ACT_GROUP_ROLE, act)) {
                	// 권한저장 검증처리
            		_validateRole(errors, data);
            	}
        	}
    	}
    }
    // 그룹코드 저장검증
    private void _validateCode(Errors errors, GroupVO data) {
    	ValidRule.NEED.validate    (errors, data, "saveGroupCd", "그룹코드"); // 필수체크
    	ValidRule.GROUPCD.validate (errors, data, "saveGroupCd", "그룹코드"); // 형식체크
    	if (!errors.hasErrors()) {
    		// 그룹코드 중복인 경우
    		try {
				if (!groupService.uniqueGroupCd(data.getSaveGroupCd()))
					 errors.reject("saveGroupCd", "동일한 그룹코드가 존재합니다.");
			} catch (NullPointerException e) {
				errors.reject("saveGroupCd", "데이터 검증시 오류가 발생하였습니다.");
			} catch (Exception e) {
				errors.reject("saveGroupCd", "데이터 검증시 오류가 발생하였습니다.");
			}
    	}
    }
    
    // 사용자정보 검증
    private void _validateUser(Errors errors, GroupVO data, String key) {
		if (CommUtils.nvlTrim(data.getUserNo()).isEmpty()) {
            errors.reject(key, "회원번호를 확인할 수 없습니다.");
        }
		else {
			try {
				// 사용자정보조회
				UserVO user = userService.getUser(data.getUserNo());
				if (user == null)
					errors.reject(key, "유효하지 않은 회원번호입니다.");
				else {
    				// 같은 업체인지 확인
    				if (!CommUtils.isEqual(user.getBzentyNo(), data.getGsBzentyNo()))
        				errors.reject(key, "같은 업체의 회원이 아닙니다.");
    				// 같은 권한인지 확인
    				//if (!CommUtils.exist(user.getRoleId(), data.getGsRoleId()))
    				//	errors.reject(key, "해당 회원에 대한 처리 권한이 없습니다.");
				}
			} catch (NullPointerException e) {
				errors.reject(key, "데이터 검증시 오류가 발생하였습니다.");
			} catch (Exception e) {
				errors.reject(key, "데이터 검증시 오류가 발생하였습니다.");
			}
		}
    }
    
    // 권한저장시 검증처리
    private void _validateRole(Errors errors, GroupVO data) {
		
        List<GroupVO> saveList = data.getSaveList();
    	
    	if (CommUtils.isEmptyList(saveList))
            errors.reject("saveList", "권한설정 대상을 확인할 수 없습니다.");
    	else {
    		for (GroupVO item : saveList) {

    			// 사용자정보 검증
    			_validateUser(errors, item, "item.userNo");

    			if (CommUtils.nvlTrim(item.getMenuId()).isEmpty()) {
    	            errors.reject("item.menuId", "설정항목을 확인할 수 없습니다.");
    	        }
    	    	if (!CommUtils.exist(CodeConst.GROUP_AUTH_MENUS, item.getMenuId())) {
    	            errors.reject("item.menuId", "설정항목이 유효하지 않습니다.");
    	        }
    			if (CommUtils.nvlTrim(item.getGroupAuthrtCd()).isEmpty()) {
    	            errors.reject("item.groupAuthrtCd", "권한정보를 확인할 수 없습니다.");
    	        }
    	    	if (!CommUtils.exist(new String[] {
    	    			CodeConst.GROUP_AUTH_RESTRICT, 
    	    			CodeConst.GROUP_AUTH_VIEW    , 
    	    			CodeConst.GROUP_AUTH_MODIFY }, item.getGroupAuthrtCd())) {
    	            errors.reject("item.groupAuthrtCd", "권한정보값이 유효하지 않습니다.");
    	        }
    		}
    	}
    }
    
    // 대표변경시 검증처리
    private void _validateRprs(Errors errors, GroupVO data) {

		// 사용자정보 검증
		_validateUser(errors, data, "userNo");
		
		if (!errors.hasErrors()) {
			try {
    			// 대표계정인지 확인
				if (CommConst.YES.equals(groupService.getGrpRprsYn(data.getUserNo())))
					errors.reject("userNo", "이미 대표계정으로 지정된 회원입니다.");
			} catch (NullPointerException e) {
				errors.reject("userNo", "데이터 검증시 오류가 발생하였습니다.");
			} catch (Exception e) {
				errors.reject("userNo", "데이터 검증시 오류가 발생하였습니다.");
			}
		}
    }
    
    // 삭제시 검증처리
    private void _validateDelete(Errors errors, GroupVO data) {
    	
        List<GroupVO> saveList = data.getSaveList();
    	
    	if (CommUtils.isEmptyList(saveList))
            errors.reject("saveList", "삭제대상을 확인할 수 없습니다.");
    	else {
    		for (GroupVO item : saveList) {

    			// 사용자정보 검증
    			_validateUser(errors, item, "item.userNo");
    			
    			if (!errors.hasErrors()) {
    				try {
    	    			// 대표계정인지 확인
						if (CommConst.YES.equals(groupService.getGrpRprsYn(item.getUserNo())))
							errors.reject("item.userNo", "대표계정은 삭제하실 수 없습니다.");
    				} catch (NullPointerException e) {
    					errors.reject("item.userNo", "데이터 검증시 오류가 발생하였습니다.");
    				} catch (Exception e) {
    					errors.reject("item.userNo", "데이터 검증시 오류가 발생하였습니다.");
    				}
    			}
    		}
    	}
    }
}
