package business.adm.invest.web;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import business.adm.invest.service.EventService;
import business.adm.invest.service.EventVO;
import business.adm.invest.service.FundVO;
import business.adm.invest.service.IrOpnnService;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.com.exception.PopupBusinessException;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 투자정보관리-IR검토의견서관리 Controller
 * 
 * @class   : IrOpnnController
 * @author  : LHB
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일      수정자                  수정내용
 *  --------   --------    -------------------------------------
 *  23.06.07   LHB                First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class IrOpnnController extends BaseController {
	
	@Autowired
    protected EventService eventService;
	
	@Autowired
    protected IrOpnnService irOpnnService;
	
	@Autowired 
	private EventValidator eventValidator;
	
	@Autowired
	private IrOpnnValidator irOpnnValidator;
	
	/**
     * 투자정보관리-IR검토의견서관리 화면 오픈
     */
    @RequestMapping("/adm/invest/event/openIrOpnn.do")
    public String openEvent(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        model.addAttribute("model", params);
        
        return "adm/invest/event/openIrOpnn";
    }
    
    /**
     * 투자정보관리-IR검토의견서관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/event/modalIrOpnnForm.do")
    public String modalIrOpnnForm(ModelMap model,
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
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);	// 행사정보
        } else {
        	// 등록모드
        	eventVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", eventVO);
        }
        
        return "adm/invest/event/modalIrOpnnForm";
    }
    
    /**
     * 투자정보관리-IR검토의견서관리 상세조회 JSON
     */
    @RequestMapping("/adm/invest/event/viewIrOpnn.do")
    @ResponseBody
    public Map viewIrOpnn(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        EventVO data = eventService.viewEvent(eventVO);
        if (CommUtils.isEmpty(data.getEvntNo())) {
        	throw new ModalBusinessException(message.getMessage("exception.notKey"));
        }
        
        List list = null;
        int page = 1;
        if (params.containsKey("page")) {
			page = CommUtils.getInt(params, "page");
        }
        
        list = irOpnnService.listIrOpnn(eventVO, page, 1);
        HashMap result = new HashMap();
        if(list.size() > 0) {
        	result = (HashMap) list.get(0);
        	result.put("page", page);
        } else {
        	throw new ModalBusinessException("IR 검토의견서가 존재하지 않습니다.");
        }
        
        Map ret = getSuccess("Data", result);
        if (list instanceof PaginatedArrayList) {
    		PaginatedArrayList p = (PaginatedArrayList)list;
    		ret.put("total", p.getTotalSize());
    		ret.put("page", p.getCurrPage());
    	}
        
        return ret;
    }
    
    /**
	 * 투자정보관리-IR검토의견서관리-상세조회-투자자별/경영체별 목록 JSON 반환 (경영체별)
	 */
	@RequestMapping("/adm/invest/event/getListIrOpnn.do")
	@ResponseBody
	public Map getListIrOpnn(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute EventVO eventVO) throws Exception {
		setGlobalSessionVO(request, eventVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
		String evntType = CommUtils.nvlTrim(eventVO.getEvntType());
		
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			if (evntType.equals(CommConst.EVNT_INVT)) {
				list = irOpnnService.listIrOpnnInvt(eventVO, page, size);
			} else if (evntType.equals(CommConst.EVNT_ENT)) {
				list = irOpnnService.listIrOpnnEnt(eventVO, page, size);
			}
			
		} else {
			if (evntType.equals(CommConst.EVNT_INVT)) {
				list = irOpnnService.listIrOpnnInvt(eventVO);
			} else if (evntType.equals(CommConst.EVNT_ENT)) {
				list = irOpnnService.listIrOpnnEnt(eventVO);
			}
		}
        
		// 일반 GRID용 결과값 반환
        return getEasyUIResult(list);
	}
	
	/**
     * 투자정보관리-IR검토의견서관리-IR검토의견서 상세보기(경영체별)
     */
    @RequestMapping("/adm/invest/event/modalIrOpnnEnt.do")
    public String modalIrOpnnEnt(ModelMap model,
    		HttpServletRequest request,
    		@RequestBody EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        List list = null;
        if (eventVO.getEvntNo()!=null && eventVO.getEvntPartcptnNo()!=null) {
			list = irOpnnService.listIrOpnn(eventVO);
		} else {
			throw new ModalBusinessException("존재하지 않는 정보입니다.");
		}
        
        if(list.size() < 1) {
        	throw new ModalBusinessException("IR 검토의견서가 존재하지 않습니다.");
        }
        
        model.addAttribute("model", list);
        
        return "adm/invest/event/modalIrOpnnEnt";
    }
	
	/**
     * 투자정보관리-IR검토의견서관리 IR검토의견서 상세보기(투자자별)
     */
    @RequestMapping("/adm/invest/event/modalIrOpnn.do")
    public String modalIrOpnn(ModelMap model,
    		HttpServletRequest request,
    		@RequestBody EventVO eventVO) throws Exception {
        setGlobalSessionVO(request, eventVO);
        
        EventVO data = eventService.viewEvent(eventVO);
        if (CommUtils.isEmpty(data.getEvntNo())) {
        	throw new PopupBusinessException(message.getMessage("exception.notKey"));
        }
        
        List list = null;
        int page = 1;
        if (eventVO.getPage() != null) {
			page = Integer.parseInt(eventVO.getPage());
        }
        
        list = irOpnnService.listIrOpnn(eventVO, page, 1);
        HashMap result = new HashMap();
        if(list.size() > 0) {
        	ObjectMapper om = new ObjectMapper();
        	//data = om.convertValue(list.get(0), EventVO.class);
        	CommUtils.mapToBean((Map) list.get(0), data);
        	result.put("page", page);
        	model.addAttribute("page", page);
        } else {
        	//throw new ModalBusinessException("IR 검토의견서가 존재하지 않습니다.");
        	throw new PopupBusinessException("작성된 IR 검토의견서가 없습니다.");
        }
        
        if (list instanceof PaginatedArrayList) {
    		PaginatedArrayList p = (PaginatedArrayList)list;
    		model.addAttribute("total", p.getTotalSize());
    		model.addAttribute("page" , p.getCurrPage());
    	}
        
        data.setUserNo(eventVO.getUserNo());
        
        model.addAttribute("model", data);
        
        return "adm/invest/event/modalIrOpnn";
    }
    
    /**
	 * 투자정보관리-IR검토의견서관리 이전, 다음 이동시 게시글 번호 조회
	 */
    /*
	public EventVO moveIrOpnn(EventVO eventVO, String buttonType) throws Exception {
		EventVO data = new EventVO();
		// 이전, 다음 버튼, 일반 진입 클릭시 진입
		if (CommUtils.isEqual(buttonType, "before")) {
			data = irOpnnService.viewIrOpnn(irOpnnService.beforeIrOpnn(eventVO));
			if (data == null) {
				throw new BusinessException(message.getMessage("이전 글이 없습니다."));
			}
			// 이전버튼 클릭시 조회할 번호
			if (CommUtils.isNotEmpty(irOpnnService.viewIrOpnn(irOpnnService.beforeIrOpnn(irOpnnService.beforeIrOpnn(eventVO))))) {
				data.setIsBefore(CommConst.YES);
			} else {
				data.setIsBefore(CommConst.NO);
			}
			if (CommUtils.isNotEmpty(irOpnnService.viewIrOpnn(irOpnnService.nextIrOpnn(irOpnnService.beforeIrOpnn(eventVO))))) {
				data.setIsNext(CommConst.YES);
			} else {
				data.setIsNext(CommConst.NO);
			}
		} else if (CommUtils.isEqual(buttonType, "next")) {
			data = irOpnnService.viewIrOpnn(irOpnnService.nextIrOpnn(eventVO));
			if (data == null) {
				throw new BusinessException(message.getMessage("다음 글이 없습니다."));
			}
			// 다음버튼 클릭시 조회할 번호
			if (CommUtils.isNotEmpty(irOpnnService.viewIrOpnn(irOpnnService.beforeIrOpnn(irOpnnService.nextIrOpnn(eventVO))))) {
				data.setIsBefore(CommConst.YES);
			} else {
				data.setIsBefore(CommConst.NO);
			}
			if (CommUtils.isNotEmpty(irOpnnService.viewIrOpnn(irOpnnService.nextIrOpnn(irOpnnService.nextIrOpnn(eventVO))))) {
				data.setIsNext(CommConst.YES);
			} else {
				data.setIsNext(CommConst.NO);
			}
		} else {
			data = irOpnnService.viewIrOpnn(eventVO);
			// List 클릭시 조회할 번호
			if (CommUtils.isNotEmpty(irOpnnService.viewIrOpnn(irOpnnService.beforeIrOpnn(eventVO)))) {
				data.setIsBefore(CommConst.YES);
			} else {
				data.setIsBefore(CommConst.NO);
			}
			if (CommUtils.isNotEmpty(irOpnnService.viewIrOpnn(irOpnnService.nextIrOpnn(eventVO)))) {
				data.setIsNext(CommConst.YES);
			} else {
				data.setIsNext(CommConst.NO);
			}
		}
		return data;
	}
	*/
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, EventVO eventVO) {
    	
        setGlobalSession(request, eventVO);

        if (eventVO.getUserInfo(request) != null) {
        	eventVO.setGsUserNo(eventVO.getUserInfo(request).getUserNo  ());
        	eventVO.setGsRoleId(eventVO.getUserInfo(request).getRoleId  ());
        }
    }
}
