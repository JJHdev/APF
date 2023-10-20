package business.adm.invest.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import business.adm.inform.service.BannerService;
import business.adm.invest.service.InvestFieldVO;
import business.adm.inform.web.BannerValidator;
import business.adm.invest.service.InvestFieldService;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 운영관리-투자분야관리 Controller
 * 
 * @class   : InvestFieldController
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class InvestFieldController extends BaseController {
	
	@Autowired
    protected InvestFieldService investFieldService;
	
	@Autowired 
    private InvestFieldValidator investFieldValidator;

    /**
     * 운영관리-투자분야관리 화면 오픈
     */
    @RequestMapping("/adm/invest/investfield/openInvestField.do")
    public String openInvestField(ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params) throws Exception {

        setGlobalSession(request, params);
        model.addAttribute("model", params);
    	
        return "adm/invest/investfield/openInvestField";
    }
    
    /**
     * 운영관리-투자분야관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/investfield/modalInvestFieldForm.do")
    public String modalInvestFieldForm(ModelMap model, HttpServletRequest request, @RequestBody InvestFieldVO investFieldVO) throws Exception {
        setGlobalSessionVO(request, investFieldVO);
        
        if (CommUtils.isNotEmpty(investFieldVO.getInvtFldCd())) {
        	InvestFieldVO data = investFieldService.viewInvestField(investFieldVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);
        } else {
        	// 등록모드
        	investFieldVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", investFieldVO);
        }
        
        return "adm/invest/investfield/modalInvestFieldForm";
    }
    
    /**
	 * 운영관리-투자분야관리 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/invest/investfield/getListInvestField.do")
	@ResponseBody
	public Map getListBanner(ModelMap model,
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute InvestFieldVO investFieldVO) throws Exception {
		setGlobalSessionVO(request, investFieldVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = investFieldService.listInvestField(investFieldVO, page, size);
		} else {
			list = investFieldService.listInvestField(investFieldVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 운영관리-투자분야관리 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/invest/investfield/saveInvestField.do")
    @ResponseBody
    public Map saveBanner(ModelMap model,
    		@ModelAttribute InvestFieldVO investFieldVO,
    		BindingResult bindingResult,
    		HttpServletRequest request) throws Exception {
    	// 세션사용자정보를 정의
    	setGlobalSessionVO(request, investFieldVO);
        
        // 입력값 검증
        investFieldValidator.validate(investFieldVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save InvestField Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        String result = investFieldService.saveInvestField(investFieldVO);
        
        return getSuccess("Message", result);
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, InvestFieldVO investFieldVO) {
    	
        setGlobalSession(request, investFieldVO);

        if (investFieldVO.getUserInfo(request) != null) {
        	investFieldVO.setGsUserNo(investFieldVO.getUserInfo(request).getUserNo());
        	investFieldVO.setGsRoleId(investFieldVO.getUserInfo(request).getRoleId());
        }
    }
}
