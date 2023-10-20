package business.adm.support.web;

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
import business.adm.support.service.SupportService;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 지원사업관리-세부지원사업관리 Controller
 * 
 * @class   : SupportController
 * @author  : LHB
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일        수정자             수정내용
 *  --------   --------    -----------------------------------
 *  23.06.07     LHB         SupportController 분리해서 별도로 관리
 */
@Controller
@SuppressWarnings({"all"})
public class InvtSprtController extends BaseController {
	
	@Resource(name = "SupportService")
	protected SupportService supportService;
	
	@Autowired 
    private InvtSprtValidator invtSprtValidator;

	/**
     * 지원사업관리-세부지원사업관리 화면 오픈
     */
    @RequestMapping("/adm/support/support/openInvtSprt.do")
    public String openInvtSprt(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/support/support/openInvtSprt";
    }
    
    /**
     * 지원사업관리-세부지원사업관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/support/support/modalInvtSprtForm.do")
    public String modalInvtSprtForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody SupportVO SupportVO) throws Exception {
        setGlobalSessionVO(request, SupportVO);
        
        if (CommUtils.isNotEmpty(SupportVO.getPrgrmNo())) {
        	SupportVO data = supportService.viewInvtSprt(SupportVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);
        } else {
        	// 등록모드
        	SupportVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", SupportVO);
        }
        
        return "adm/support/support/modalInvtSprtForm";
    }
    
    /**
     * 지원사업관리-세부지원사업관리 목록 JSON 반환 (일반 GRID)
     */
    @RequestMapping("/adm/support/support/getListInvtSprt.do")
    @ResponseBody
    public Map getListInvtSprt(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO) throws Exception {
    	setGlobalSessionVO(request, supportVO);
        
        supportVO.setDelYn("N");
        
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = supportService.listInvtSprt(supportVO, page, size);
		} else {
			list = supportService.listInvtSprt(supportVO);
		}
        
		// Easy UI GRID용 결과값 반환
		return getPaginatedResult(list);
    }
    
    /**
	 * 지원사업관리-세부지원사업관리 상세조회 JSON 반환
	 */
	@RequestMapping("/adm/support/support/getInvtSprt.do")
	@ResponseBody
	public Map getInvtSprt(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute SupportVO supportVO) throws Exception {
		setGlobalSessionVO(request, supportVO);

		SupportVO result = supportService.viewInvtSprt(supportVO);

        return getSuccess(result);
	}
    
    /**
     * 지원사업관리-세부지원사업관리 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/support/support/saveInvtSprt.do")
    @ResponseBody
    public Map saveInvtSprt(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, supportVO);

        // 입력값 검증
        invtSprtValidator.validate(supportVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save InvtSprt Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        String result = supportService.saveInvtSprt(supportVO);
        
        return getSuccess("Message", result);
    }
    
    
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SupportVO supportVO) {
    	
        setGlobalSession(request, supportVO);

        if (supportVO.getUserInfo(request) != null) {
        	supportVO.setGsUserNo(supportVO.getUserInfo(request).getUserNo());
        	supportVO.setGsRoleId(supportVO.getUserInfo(request).getRoleId());
        }
    }
    
}
