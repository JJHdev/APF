package business.com.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import business.com.CommConst;
import business.com.exception.AjaxBusinessException;
import business.com.exception.ModalBusinessException;
import business.com.exception.PopupBusinessException;
import commf.exception.BusinessException;
import common.base.BaseController;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 *  [컨트롤러클래스] - 에러페이지 Controller
 *  
 *  동일한 JSP (/WebContent/WEB-INF/jsp/error/error.jsp) 로 맵핑함.
 *
 * @class   : CommController
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
public class ErrorController extends BaseController {
	
	private void _addModelAttribute(
			HttpServletRequest request, 
			ModelMap model, 
			String errorCode
		) {
		
		String sysCd = (String)request.getSession().getAttribute(CommConst.SESS_SYSTEM_CODE);
		if (sysCd == null)
			sysCd = CommConst.SYSTEM_CODE;
    	
    	model.addAttribute("sysCd"     , sysCd);
    	model.addAttribute("sysCdLower", sysCd.toLowerCase());
    	
    	Exception e = (Exception)request.getAttribute("exception");
    	if (e != null) {
    		
    		if (e instanceof EgovBizException) {
        		model.addAttribute("exceptionMessage", e.getMessage());
    		}
    		else if (e instanceof AjaxBusinessException) {
        		model.addAttribute("exceptionMessage", e.getMessage());
    		}
    		else if (e instanceof ModalBusinessException) {
        		model.addAttribute("exceptionMessage", e.getMessage());
    		}
    		else if (e instanceof PopupBusinessException) {
        		model.addAttribute("exceptionMessage", e.getMessage());
    		}
    		else if (e instanceof BusinessException) {
        		model.addAttribute("exceptionMessage", e.getMessage());
    		}
    	}
    	model.addAttribute("errorCode"   , errorCode);
    	model.addAttribute("errorTitle"  , message.getMessage("error."+errorCode+".title"));
    	model.addAttribute("errorMessage", message.getMessage("error."+errorCode+".message"));
	}
	
    /**
     * 500 에러페이지
     */
    @RequestMapping("/com/error/500.do")
    public String error500(HttpServletRequest request, ModelMap model) {
    	// 에러 관련 데이터 맵핑
    	_addModelAttribute(request, model, "500");
    	
    	Exception e = (Exception)request.getAttribute("exception");
    	if (e != null) {
    		if (e instanceof AjaxBusinessException) {
        		return "error/ajax";
    		}
        	else if (e instanceof ModalBusinessException) {
           		return "error/modal";
    		}
        	else if (e instanceof PopupBusinessException) {
           		return "error/popup";
    		}
    	}
        return "error/error";
    }

    /**
     * 404 에러페이지
     */
    @RequestMapping("/com/error/404.do")
    public String error404(HttpServletRequest request, ModelMap model) throws Exception {
    	// 에러 관련 데이터 맵핑
    	_addModelAttribute(request, model, "404");
        return "error/error";
    }

    /**
     * 403 접근제한 에러페이지
     */
    @RequestMapping("/com/error/denied.do")
    public String denied(HttpServletRequest request, ModelMap model) throws Exception {
    	// 에러 관련 데이터 맵핑
    	_addModelAttribute(request, model, "denied");
        return "error/error";
    }
	
    /**
     * 팝업 에러페이지
     */
    @RequestMapping("/com/error/popup.do")
    public String errorPopup(HttpServletRequest request, ModelMap model) {
    	// 에러 관련 데이터 맵핑
    	_addModelAttribute(request, model, "500");
        return "error/popup";
    }
	
    /**
     * 모달창 에러페이지
     */
    @RequestMapping("/com/error/modal.do")
    public String errorModal(HttpServletRequest request, ModelMap model) {
    	// 에러 관련 데이터 맵핑
    	_addModelAttribute(request, model, "500");
        return "error/modal";
    }
	
    /**
     * AJAX용 에러페이지
     */
    @RequestMapping("/com/error/ajax.do")
    public String errorAjax(HttpServletRequest request, ModelMap model) {
    	// 에러 관련 데이터 맵핑
    	_addModelAttribute(request, model, "500");
        return "error/ajax";
    }
}


