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
 * [컨트롤러클래스] - 투자정보관리-참여기업등록 Controller
 * 
 * @class   : EventEntController
 * @author  : LHB
 * @since   : 2023.06.05
 * @version : 1.0
 *
 *   수정일      수정자                  수정내용
 *  --------   --------    -------------------------------------
 *  
 */
@Controller
@SuppressWarnings({"all"})
public class EventEntController extends BaseController {
	
	@Autowired
    protected EventService eventService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	@Autowired
    protected EventEntValidator eventEntValidator;

    /**
     * 투자정보관리-참여기업등록 화면 오픈
     */
    @RequestMapping("/adm/invest/event/openEventEnt.do")
    public String openEventEnt(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        model.addAttribute("model", params);
        
        return "adm/invest/event/openEventEnt";
    }
    
    /**
     * 투자정보관리-참여기업등록 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/event/modalEventEntForm.do")
    public String modalEventForm(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@RequestBody EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        if (CommUtils.isNotEmpty(eventVO.getEvntNo())) {
        	EventVO data = eventService.viewEvent(eventVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 투자자, 경영체 구분
        	if (eventVO.getEvntType() == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	List list = null;
            if (params.containsKey("page")) {
    			int page = CommUtils.getInt(params, "page");
    			int size = CommUtils.getInt(params, "rows");
    			if (eventVO.getEvntType().equals(CommConst.EVNT_INVT)) {
    				// 투자자 조회
    				list = eventService.listEventInvt(eventVO, page, size);	
    			} else {
    				// 참여 경영체 조회
    				list = eventService.listEventEnt(eventVO, page, size);
    			}
    		} else {
    			if (eventVO.getEvntType().equals(CommConst.EVNT_INVT)) {
    				// 투자자 조회
    				list = eventService.listEventInvt(eventVO);
    			} else {
    				// 참여 경영체 조회
    				list = eventService.listEventEnt(eventVO);
    			}
    		}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);	// 행사정보
        	model.addAttribute("list" , list);	// 투자자 or 참여경영체 정보
        } else {
        	// 등록모드
        	eventVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", eventVO);
        }
        
        return "adm/invest/event/modalEventEntForm";
    }
    
    /**
	 * 투자정보관리-참여기업등록 참여기업(참여 투자자 / 참여 경영체 사업계획서), IR검토의견서관리 상세조회 투자자별/경영체별 목록 JSON 반환 (EasyUI GRID)
	 */
	@RequestMapping("/adm/invest/fund/getListEventEnt.do")
	@ResponseBody
	public Map getListInvt(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute EventVO eventVO) throws Exception {
		setGlobalSessionVO(request, eventVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			if (eventVO.getEvntType().equals(CommConst.EVNT_INVT)) {
				// 투자자 조회
				list = eventService.listEventInvt(eventVO, page, size);	
			} else {
				// 참여 경영체 조회
				list = eventService.listEventEnt(eventVO, page, size);
			}
		} else {
			if (eventVO.getEvntType().equals(CommConst.EVNT_INVT)) {
				// 투자자 조회
				list = eventService.listEventInvt(eventVO);
			} else {
				// 참여 경영체 조회
				list = eventService.listEventEnt(eventVO);
			}
		}
        
		// Easy UI GRID용 결과값 반환
		return getEasyUIResult(list);
	}
    
    /**
     * 참여기업(투자자) 공통저장처리 (등록,삭제)
     */
    @RequestMapping("/adm/invest/event/saveEventInvt.do")
    @ResponseBody
    public Map saveEventInvt(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute EventVO eventVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, eventVO);
        
        // 입력값 검증
        eventEntValidator.validate(eventVO, bindingResult);
        
        // LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save EventEnt Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
        String result = eventService.saveEventInvt(eventVO);
        
        return getSuccess("Message", result);
    }
    
    /**
     * 참여기업(경영체) 공통저장처리 (등록,삭제)
     */
    @RequestMapping("/adm/invest/event/saveEventEnt.do")
    @ResponseBody
    public Map saveEventEnt(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute EventVO eventVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, eventVO);
        
        // 입력값 검증
        eventEntValidator.validate(eventVO, bindingResult);
        
        // LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save EventEnt Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
        // 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
        
        String result = eventService.saveEventEnt(eventVO, files);
        
        return getSuccess("Message", result);
    }
    
    /**
     * 참여기업(경영체) 추가 가능 여부 확인
     */
    @RequestMapping("/adm/invest/event/getEventEntExist.do")
    @ResponseBody
    public Map getEventEntExist(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute EventVO eventVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, eventVO);
        
        boolean result = eventService.getEventEntExist(eventVO);
        
        return getSuccess("Message", result);
    }
    
    /******************************************************************
     *******************************************************************
     ******************************************************************/
    
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
