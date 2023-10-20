package business.usr.mypage.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.GroupService;
import business.com.user.service.GroupVO;
import business.usr.CodeConst;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 그룹관리 Controller
 * 
 * @class   : GroupController
 * @author  : LSH
 * @since   : 2023.04.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class GroupController extends BaseController {

    @Resource(name="GroupService")
    protected GroupService groupService;

    @Resource(name="CommService")
    protected CommService commService;

    @Resource(name="EntService")
    protected EntService entService;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private GroupValidator validator;

    /**
     * 마이페이지 - 그룹관리 화면 오픈
     */
    @RequestMapping("/usr/mypage/group/openGroup.do")
    public String openGroup(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute GroupVO groupVO) throws Exception {
		
    	setGlobalSessionVO(request, groupVO, true);
        // 제목 정의
        groupVO.setPageNm(
       		commService.getCodeName(CodeConst.MYPG_GROUP_SE, groupVO.getPageCd())
        );
        model.addAttribute("model", groupVO);
    	
        return "usr/mypage/group/openGroup";
    }

    /**
     * 마이페이지 - 멤버관리/권한관리 업체번호 기준 목록JSON 반환 (GRID)
     */
    @RequestMapping("/usr/mypage/group/getListGroup.do")
    @ResponseBody
    public Map getListGroup(ModelMap model
    		, HttpServletRequest request
            , @RequestParam Map<String,String> reqMap
            , @ModelAttribute GroupVO groupVO) throws Exception {

    	setGlobalSessionVO(request, groupVO, false);
        // -------------------- Default Setting End -----------------------//

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = groupService.listGroup(groupVO, page, size);
        }
        else {
        	list = groupService.listGroup(groupVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 마이페이지 - 그룹코드 - 그룹코드 중복확인
     */
    @RequestMapping("/usr/mypage/group/unique.do")
    @ResponseBody
    public String unique(HttpServletRequest request, @ModelAttribute GroupVO groupVO) throws Exception {
    	
        setGlobalSessionVO(request, groupVO, false);
        // 그룹코드 중복확인
        boolean unique = groupService.uniqueGroupCd(groupVO.getGroupCd());
        return (unique ? CommConst.YES : CommConst.NO);
    }

    /**
     * 마이페이지 - 멤버관리 - 삭제처리
     * 
     * @param groupVO.mode     : 삭제모드(D)
     * @param groupVO.pageCd   : 화면구분
     * @param groupVO.bzentyNo : 업체번호
     * @param groupVO.saveList : 삭제목록(userNo)
     */
    @RequestMapping("/usr/mypage/group/deltGroupMember.do")
    @ResponseBody
    public Map deltGroupMember(@RequestBody GroupVO groupVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {
    	// 검증 및 저장처리
    	return saveGroup(request, groupVO, bindingResult, "GROUP MEMBER DELETE");
    }

    /**
     * 마이페이지 - 권한관리 - 권한변경처리
     * 
     * @param groupVO.mode     : 수정모드(U)
     * @param groupVO.act      : 행위구분(권한변경:ROLE)
     * @param groupVO.pageCd   : 화면구분
     * @param groupVO.bzentyNo : 업체번호
     * @param groupVO.saveList : 권한목록(userNo,'menu00','menu01','menu02','menu03','menu04')
     */
    @RequestMapping("/usr/mypage/group/saveGroupRole.do")
    @ResponseBody
    public Map saveGroupRole(@RequestBody GroupVO groupVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {
    	// 검증 및 저장처리
    	return saveGroup(request, groupVO, bindingResult, "GROUP ROLE SAVE");
    }

    /**
     * 마이페이지 - 멤버관리/권한관리 - 대표계정 변경처리
     * 
     * @param groupVO.mode     : 수정모드(U)
     * @param groupVO.act      : 행위구분(대표변경:RPRS)
     * @param groupVO.pageCd   : 화면구분
     * @param groupVO.bzentyNo : 업체번호
     * @param groupVO.userNo   : 대표사용자번호
     */
    @RequestMapping("/usr/mypage/group/saveGroupRprs.do")
    @ResponseBody
    public Map saveGroupRprs(@RequestBody GroupVO groupVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {
    	// 검증 및 저장처리
    	return saveGroup(request, groupVO, bindingResult, "GROUP RPRS CHANGE");
    }

    /**
     * 마이페이지 - 그룹코드 - 그룹코드 저장처리
     * @param groupVO.mode     : 수정모드(U)
     * @param groupVO.act      : 행위구분(그룹코드:CODE)
     * @param groupVO.pageCd   : 화면구분
     * @param groupVO.bzentyNo : 업체번호
     * @param groupVO.groupCd  : 그룹코드
     */
    @RequestMapping("/usr/mypage/group/saveGroupCode.do")
    @ResponseBody
    public Map saveGroupCode(@RequestBody GroupVO groupVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {
    	// 검증 및 저장처리
    	return saveGroup(request, groupVO, bindingResult, "GROUP CODE SAVE");
    }
    
    /**
     * 그룹관리 공통 검증 및 저장처리
     */
    private Map saveGroup(HttpServletRequest request, GroupVO groupVO, BindingResult bindingResult, String title) throws Exception {
    	
        // 세션정보 정의
    	setGlobalSessionVO(request, groupVO, true);

        // 저장데이터에 맞게 REBUILD
        groupVO.rebuildProperties();
    	
        // 저장할 입력값 검증
    	validator.validate(groupVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("["+title+"] Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 항목을 저장한다.
    	String result = groupService.saveGroup(groupVO);
    	
    	// 성공결과 반환
        return getSuccess(result);
    }

    /**
     * 세션정보를 모델의 변수에 바인딩한다
     * 기본정보를 바인딩한다.
     * 대표계정이 아닌 경우 Exception을 반환한다.
     * 그룹관리는 대표계정만 접근이 가능함
     */
    private void setGlobalSessionVO(HttpServletRequest request, GroupVO groupVO, boolean checkEnt) throws Exception {
    	
        setGlobalSession(request, groupVO);

        if (groupVO.getUserInfo(request) != null) {
        	groupVO.setGsUserNo  (groupVO.getUserInfo(request).getUserNo  ());
        	groupVO.setGsRoleId  (groupVO.getUserInfo(request).getRoleId  ());
        	groupVO.setGsBzentyNo(groupVO.getUserInfo(request).getBzentyNo());
        }
        // 화면구분이 없을 경우 그룹코드를 기본화면으로 정의
        if (CommUtils.isEmpty(groupVO.getPageCd())) {
        	groupVO.setPageCd(CodeConst.MYPG_GROUP_CODE);
        }
        // 업체번호 정의
        groupVO.setBzentyNo(groupVO.getGsBzentyNo());
        // 대표계정여부 확인
        groupVO.setRprsYn(groupService.getGrpRprsYn(groupVO.getGsUserNo()));
        
        // 대표계정이 아닌 경우 Exception 반환
        if (!CommConst.YES.equals(groupVO.getRprsYn()))
        	throw new BusinessException(message.getMessage("error.group.rprs.notAccess"));
        
        // 업체정보 확인이 필요한 경우
        if (checkEnt) {
	        // 업체정보 조회
	        EntVO entVO = entService.getEnt(groupVO.getGsBzentyNo());
	        // 그룹코드 설정
	        groupVO.setGroupCd(entVO.getGroupCd());
	        // 승인여부 설정
	        groupVO.setAccessYn(CommConst.NO);
	        // 경영체의 경우 자동승인(?) 처리
	        if (groupVO.getUserInfo(request).isEnt())
	        	groupVO.setAccessYn(CommConst.YES);
	        // 유관기관/투자자의 경우 승인여부 확인
	        else if (CodeConst.USE_STATUS_USABLE.equals(entVO.getUseSttsCd())) {
	        	groupVO.setAccessYn(CommConst.YES);
	        }
        }
    }
}
