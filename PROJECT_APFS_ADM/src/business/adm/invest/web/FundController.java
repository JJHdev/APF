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

import business.adm.inform.service.BbsVO;
import business.adm.invest.service.FundService;
import business.adm.invest.service.FundVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 투자정보관리-모태펀드등록 Controller
 * 
 * @class   : FundController
 * @author  : LSH
 * @since   : 2023.04.04
 * @version : 1.0
 *
 *   수정일      수정자                  수정내용
 *  --------   --------    -------------------------------------
 *  23.04.17   LHB         InvestController -> FundController 변경
 */
@Controller
@SuppressWarnings({"all"})
public class FundController extends BaseController {
	
	@Autowired
    protected FundService fundService;
	
	@Autowired 
    private FundValidator fundValidator;
	
	@Autowired 
    private FundInvstrValidator fundInvstrValidator;

	/**
     * 투자정보관리 - 모태펀드등록 화면 오픈
     */
    @RequestMapping("/adm/invest/fund/openFund.do")
    public String openFund(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute FundVO fundVO) throws Exception {
        setGlobalSessionVO(request, fundVO);
        
        model.addAttribute("model", params);
        
        return "adm/invest/fund/openFund";
    }
    
    /**
     * 투자정보관리 - 모태펀드등록 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/fund/modalFundForm.do")
    public String modalFundForm(ModelMap model, HttpServletRequest request, @RequestBody FundVO fundVO) throws Exception {
        setGlobalSessionVO(request, fundVO);
        
        if (CommUtils.isNotEmpty(fundVO.getFundNo())) {
        	FundVO data = fundService.viewFund(fundVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);
        	
        	List invstrList = fundService.listFundInvstr(fundVO);
        	model.addAttribute("invstr", invstrList);
        } else {
        	// 등록모드
        	fundVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", fundVO);
        }
        
        return "adm/invest/fund/modalFundForm";
    }
    
    /**
	 * 모태펀드 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/invest/fund/getListFund.do")
	@ResponseBody
	public Map getListFund(ModelMap model,
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute FundVO fundVO) throws Exception {
		setGlobalSessionVO(request, fundVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = fundService.listFund(fundVO, page, size);
		} else {
			list = fundService.listFund(fundVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 모태펀드등록 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/invest/fund/saveFund.do")
    @ResponseBody
    public Map saveFund(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute FundVO fundVO,
    		BindingResult bindingResult) throws Exception {
    	// 세션사용자정보를 정의
    	setGlobalSessionVO(request, fundVO);
        
        // 입력값 검증
        fundValidator.validate(fundVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save Fund Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        String result = fundService.saveFund(fundVO);
        
        return getSuccess("Message", result);
    }
	
	/**
	 * 전체 조합원 목록 JSON 반환 (EasyUI GRID)
	 * (투자정보관리 > 참여기업등록 > 참여 투자자 등록)
	 */
	@RequestMapping("/adm/invest/fund/getListInvt.do")
	@ResponseBody
	public Map getListInvt(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute FundVO fundVO) throws Exception {
		setGlobalSessionVO(request, fundVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = fundService.listInvt(fundVO, page, size);
		} else {
			list = fundService.listInvt(fundVO);
		}
		// Easy UI GRID용 결과값 반환
		return getEasyUIResult(list);
	}
	
	/**
	 * 특정 펀드 조합원 목록 JSON 반환 (EasyUI GRID)
	 */
	@RequestMapping("/adm/invest/fund/getListFundInvstr.do")
	@ResponseBody
	public Map getListFundInvstr(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute FundVO fundVO) throws Exception {
		setGlobalSessionVO(request, fundVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = fundService.listFundInvstr(fundVO, page, size);
		} else {
			list = fundService.listFundInvstr(fundVO);
		}
        
        return getSuccess(list);
	}
	
	/**
     * 조합원 공통저장처리 (등록,삭제)
     */
    @RequestMapping("/adm/invest/fund/saveFundInvstr.do")
    @ResponseBody
    public Map saveFundInvstr(ModelMap model,
    		@RequestParam HashMap params,
    		@ModelAttribute FundVO fundVO,
    		HttpServletRequest request,
    		BindingResult bindingResult) throws Exception {
    	// 세션사용자정보를 정의
    	setGlobalSessionVO(request, fundVO);
        
        // 입력값 검증
        fundInvstrValidator.validate(fundVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save FundInvstr Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        String result = fundService.saveFundInvstr(fundVO);
        
        return getSuccess("Message", result);
    }
    
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, FundVO fundVO) {
    	
        setGlobalSession(request, fundVO);

        if (fundVO.getUserInfo(request) != null) {
        	fundVO.setGsUserNo  (fundVO.getUserInfo(request).getUserNo  ());
        	fundVO.setGsRoleId  (fundVO.getUserInfo(request).getRoleId  ());
        }
    }
}
