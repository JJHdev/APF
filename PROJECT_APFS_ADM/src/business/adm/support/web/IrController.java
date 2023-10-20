package business.adm.support.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.adm.invest.service.FundService;
import business.adm.invest.service.FundVO;
import business.adm.support.service.IrService;
import business.adm.support.service.IrVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 운영관리-IR지원현황 Controller
 * 
 * @class   : IrController
 * @author  : LHB
 * @since   : 2023.06.08
 * @version : 1.0
 *
 *   수정일       수정자             수정내용
 *  --------   --------    ---------------------------
 * 23.06.08      LHB            First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class IrController extends BaseController {
	
	@Resource(name = "IrService")
	protected IrService irService;
	
	@Resource(name = "FundService")
	protected FundService fundService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	// Validator

	/**
     * 운영관리-IR지원현황 화면 오픈
     */
    @RequestMapping("/adm/support/ir/openIr.do")
    public String openIr(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute IrVO irVO) throws Exception {
    	setGlobalSessionVO(request, irVO);
        
        return "adm/support/ir/openIr";
    }
    
    /**
     * 운영관리-IR지원현황 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/support/ir/modalIrForm.do")
    public String modalIrForm(ModelMap model, HttpServletRequest request, @RequestBody IrVO irVO) throws Exception {
        setGlobalSessionVO(request, irVO);
        
        if (irVO.getType() == null) {
    		throw new ModalBusinessException("등록 정보가 잘못되었습니다.");
    	}
        
        if (CommUtils.isNotEmpty(irVO.getFundNo())) {
        	IrVO data = irService.viewIr(irVO);
        	data.setType(irVO.getType());
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	if (data.getType().equals(CommConst.EVNT_INVT)) {
        		// 투자자
        		String fundNo = data.getFundNo();
        		
        		if (data.getFundNo() == null) {
        			throw new ModalBusinessException(message.getMessage("exception.notKey"));
        		}
        		
        		FundVO fundVO = new FundVO();
        		fundVO.setFundNo(fundNo);
        		
        		data.setFundVO(fundService.viewFund(fundVO));
        	} else if (data.getType().equals(CommConst.EVNT_ENT)) {
        		// 경영체
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);
        } else {
        	// 등록모드
        	irVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", irVO);
        }
        
        return "adm/support/ir/modalIrForm";
    }
    
    /**
	 * 운영관리-IR지원현황 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/support/ir/getListIr.do")
	@ResponseBody
	public Map getListIr(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute IrVO irVO) throws Exception {
		setGlobalSessionVO(request, irVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = irService.listIr(irVO, page, size);
		} else {
			list = irService.listIr(irVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, IrVO irVO) {
    	
        setGlobalSession(request, irVO);

        if (irVO.getUserInfo(request) != null) {
        	irVO.setGsUserNo(irVO.getUserInfo(request).getUserNo());
        	irVO.setGsRoleId(irVO.getUserInfo(request).getRoleId());
        }
    }

}