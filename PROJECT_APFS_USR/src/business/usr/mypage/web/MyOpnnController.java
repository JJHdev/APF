package business.usr.mypage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.com.user.service.GroupService;
import business.usr.CodeConst;
import business.usr.invest.service.EventService;
import business.usr.invest.service.EventVO;
import business.usr.invest.service.OpnnIrService;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 마이페이지 - IR검토의견등록 Controller
 * 
 * @class   : MyOpnnIrController
 * @author  : LSH
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *	23.06.22	KYW			First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class MyOpnnController extends BaseController {
	
	@Resource(name = "EventService")
	private EventService eventService;
	
	@Resource(name = "OpnnIrService")
    protected OpnnIrService opnnIrService;

	@Resource(name="GroupService")
    protected GroupService groupService;
	
    /**
     * 마이페이지 - IR검토의견등록 화면 오픈
     */
    @RequestMapping("/usr/mypage/opnn/openOpnnIr.do")
    public String openIrOpnn(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EventVO eventVO) throws Exception {
				
    	setGlobalSessionVO(request, eventVO, false);
        model.addAttribute("model", eventVO);
    	
        return "usr/mypage/opnn/openOpnnIr";
    }
    
    /**
     * 마이페이지 - IR검토의견등록 투자설명회 사업계획서 화면 오픈
     */
    @RequestMapping("/usr/mypage/opnn/openOpnnIrEnt.do")
    public String openOpnnIrEnt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EventVO eventVO) throws Exception {
				
    	setGlobalSessionVO(request, eventVO, false);
        model.addAttribute("model", eventVO);
    	
        return "usr/mypage/opnn/openOpnnIrEnt";
    }
    
    /**
     * IR검토의견등록 - 투자설명회 목록 반환 (GRID)
     */
    @RequestMapping("/usr/mypage/opnn/getListEvent.do")
    @ResponseBody
    public Map getListEvent(ModelMap model, 
    		HttpServletRequest request,
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute EventVO eventVO) throws Exception {
    	setGlobalSessionVO(request, eventVO, false);
        
    	List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = eventService.listEvent(eventVO, page, size);
		} else {
			list = eventService.listEvent(eventVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
    }
    
    /**
	 * IR검토의견등록 - 작성가이드 모달팝업화면 오픈
	 */
	@RequestMapping("/usr/mypage/opnn/modalGuide.do")
	public String modalGuide(ModelMap model) throws Exception {
		
		return "usr/mypage/opnn/modalGuide";
	}
	
	/**
	 * IR검토의견등록 - 검토의견 모달팝업화면 오픈
	 */
	@RequestMapping("/usr/mypage/opnn/modalOpnnIr.do")
	public String modalData(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params,
			@RequestBody EventVO eventVO) throws Exception {
		
		setGlobalSessionVO(request, eventVO, false);
		model.addAttribute("model", eventVO);
		
		return "usr/mypage/opnn/modalOpnnIr";
	}
	
	/**
	 * IR검토의견등록 - 검토의견 모달 오픈시 
	 */
	@RequestMapping("/usr/mypage/opnn/viewOpnnIr.do")
	@ResponseBody
	public Map viewOpnnIr(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute EventVO eventVO) throws Exception {
		
		setGlobalSessionVO(request, eventVO, false);
		List list = null;
		
		if(params.get("mode").equals(CommConst.INSERT)) {
			// 등록하기 I
			list = eventService.listEventEnt(eventVO);
		}else {
			// 등록완료 - 조회 / 수정
			list = opnnIrService.listOpnnIr(eventVO);
		}
		
		return getSuccess(list);
		
	}
	
	/**
     * IR검토의견등록 - 투자설명회 경영체 반환 (GRID-CARD)
     */
    @RequestMapping("/usr/mypage/opnn/getListEventEnt.do")
    @ResponseBody
    public Map getListEventEnt(ModelMap model, 
    		HttpServletRequest request,
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute EventVO eventVO) throws Exception {
    	setGlobalSessionVO(request, eventVO, false);
        
    	List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = eventService.listEventEnt(eventVO, page, size);
		} else {
			list = eventService.listEventEnt(eventVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
    }
    
    /**
     * IR검토의견등록 - 검토의견서 저장처리 (등록)
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/usr/mypage/opnn/saveOpnnIr.do")
	@ResponseBody
	public Map saveOpnnIr(@ModelAttribute EventVO eventVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {

		// 세션정보 정의
		setGlobalSessionVO(request, eventVO, true);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Opnn Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

		// 검토의견을 저장한다.
		String result = opnnIrService.saveOpnnIr(eventVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다 
     * 투자설명회 - EventVO
     * 
     * 2023.07.05 LSH 그룹관리 권한설정에 따른 접근권한 체크로직 추가
     * 
     * @param saveMode 저장모드여부
     */
    private void setGlobalSessionVO(HttpServletRequest request, EventVO eventVO, boolean saveMode) {
    	
    	setGlobalSession(request, eventVO);

    	if (eventVO.getUserInfo(request) != null) {
			eventVO.setGsUserNo(eventVO.getUserInfo(request).getUserNo());
			eventVO.setGsRoleId(eventVO.getUserInfo(request).getRoleId());
			eventVO.setBzentyNo(eventVO.getUserInfo(request).getBzentyNo());
		}
        // 2023.07.05 LSH 
    	// 그룹관리 권한설정에 따른 접근권한 체크
    	// 권한이 없는 경우 Exception이 발생함
		try {
	    	groupService.access(
	       			eventVO.getGsUserNo(), 
	       			eventVO.getGsRoleId(), 
	    			CodeConst.GROUP_MENU_IRREVIEW,
	    			saveMode);
		} catch(BusinessException be) {
	       	if (saveMode) {
				// 모달스타일의 오류페이지 연결을 위한 처리
				throw new ModalBusinessException(be.getMessage());
	    	}
	       	throw(be);
		}
    }
}
