package business.adm.invest.web;

import java.util.Enumeration;
import java.util.HashMap;
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

import business.adm.invest.service.EventService;
import business.adm.invest.service.EventVO;
import business.adm.invest.service.FundVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 투자정보관리 - 투자설명회등록, 참여기업등록 Controller
 * 
 * @class   : EventController
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일      수정자                  수정내용
 *  --------   --------    -------------------------------------
 *  
 */
@Controller
@SuppressWarnings({"all"})
public class EventController extends BaseController {
	
	@Autowired
    protected EventService eventService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	@Autowired
    protected EventValidator eventValidator;

    /**
     * 투자정보관리 - 투자설명회등록 화면 오픈
     */
    @RequestMapping("/adm/invest/event/openEvent.do")
    public String openEvent(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute EventVO eventVO) throws Exception {
    	setGlobalSessionVO(request, eventVO);
        
        model.addAttribute("model", params);
        model.addAttribute("gsUserNm", eventVO.getUserInfo(request).getUserNm());
        
        return "adm/invest/event/openEvent";
    }
    
    /**
     * 투자정보관리 - 모태펀드등록 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/event/modalEventForm.do")
    public String modalEventForm(ModelMap model, HttpServletRequest request, @RequestBody EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        if (CommUtils.isNotEmpty(eventVO.getEvntNo())) {
        	EventVO data = eventService.viewEvent(eventVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);
        } else {
        	// 등록모드
        	eventVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", eventVO);
        }
        
        return "adm/invest/event/modalEventForm";
    }
    
    /**
	 * 투자정보관리 - 투자설명회등록(openEvent), 참여기업등록(openEventEnt), IR검토의견서관리(openIrOpnn) 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/invest/event/getListEvent.do")
	@ResponseBody
	public Map getListEvent(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute EventVO eventVO) throws Exception {
		setGlobalSessionVO(request, eventVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = eventService.listEvent(eventVO, page, size);
		} else {
			list = eventService.listEvent(eventVO, 1, 10);
		}
        
        // 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
	 * 투자설명회 상세조회 JSON 반환
	 */
	@RequestMapping("/adm/invest/event/getEvent.do")
	@ResponseBody
	public Map getFund(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute EventVO eventVO) throws Exception {
		setGlobalSessionVO(request, eventVO);
		// -------------------- Default Setting End -----------------------//

		EventVO result = eventService.viewEvent(eventVO);

        return getSuccess(result);
	}
    
    /**
     * 투자설명회등록 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/invest/event/saveEvent.do")
    @ResponseBody
    public Map saveEvent(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute EventVO eventVO,
    		BindingResult bindingResult) throws Exception {
    	// 세션사용자정보를 정의
    	setGlobalSessionVO(request, eventVO);
        
        eventVO.setGsUserNo(eventVO.getUserInfo(request).getUserNo());
        
        // 입력값 검증
        eventValidator.validate(eventVO, bindingResult);
        
        // LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save Event Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
        String result = eventService.saveEvent(eventVO);
        
        
        return getSuccess("Message", result);
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, EventVO eventVO) {
    	
        setGlobalSession(request, eventVO);

        if (eventVO.getUserInfo(request) != null) {
        	eventVO.setGsUserNo(eventVO.getUserInfo(request).getUserNo());
        	eventVO.setGsRoleId(eventVO.getUserInfo(request).getRoleId());
        }
    }
    
}
