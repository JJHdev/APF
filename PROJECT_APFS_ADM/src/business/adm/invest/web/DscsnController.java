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

import business.adm.invest.service.DscsnService;
import business.adm.invest.service.DscsnVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.adm.CodeConst;
import business.adm.inform.service.BbsVO;
import common.base.BaseController;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 상담일지-신청현황관리 Controller
 * 
 * @class   : DscsnController
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class DscsnController extends BaseController {
	
	@Resource(name="DscsnService")
	protected DscsnService dscsnService;
	
    /**
     * 상담일지-신청현황관리 화면 오픈
     */
    @RequestMapping("/adm/invest/dscsn/openDscsn.do")
    public String openDscsn(ModelMap model,
    		HttpServletRequest request, 
    		@ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/invest/dscsn/openDscsn";
    }
    
    
    /**
     * 상담일지-신청현황관리 JSON 목록 반환
     */
    @RequestMapping("/adm/invest/dscsn/getListDscsn.do")
    @ResponseBody
    public Map getListDscsn(ModelMap model,
    		HttpServletRequest request, 
    		@RequestParam HashMap params,
    		@ModelAttribute DscsnVO dscsnVO) throws Exception {
    	setGlobalSessionVO(request, dscsnVO);
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = dscsnService.listDscsn(dscsnVO, page, size);
		} else {
			list = dscsnService.listDscsn(dscsnVO);
		}
        
        // 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 상담일지-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/dscsn/modalDscsnForm.do")
    public String modalDscsnForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody DscsnVO dscsnVO) throws Exception {
    	setGlobalSessionVO(request, dscsnVO);
        
        if (CommUtils.isNotEmpty(dscsnVO.getSn())) {
        	DscsnVO data = dscsnService.viewDscsn(dscsnVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model",	data);
        } else {
        	// 등록모드
        	dscsnVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", dscsnVO);
        }
        return "adm/invest/dscsn/modalDscsnForm";
    }
    
    /**
	 * 상담일지 공통저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/adm/invest/dscsn/saveDscsn.do")
	@ResponseBody
	public Map saveDscsn(@ModelAttribute DscsnVO dscsnVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {
		
		// 세션정보 정의
		setGlobalSession(request, dscsnVO);
		// 세션사용자번호 정의
		if (dscsnVO.getUserInfo(request) != null)
			dscsnVO.setGsUserNo(dscsnVO.getUserInfo(request).getUserNo());
		String mode = dscsnVO.getMode();
		
		// 게시판를 저장한다.
		String result = dscsnService.saveDscsn(dscsnVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, DscsnVO dscsnVO) {
    	
        setGlobalSession(request, dscsnVO);

        if (dscsnVO.getUserInfo(request) != null) {
        	dscsnVO.setGsUserNo(dscsnVO.getUserInfo(request).getUserNo());
        	dscsnVO.setGsRoleId(dscsnVO.getUserInfo(request).getRoleId());
        }
    }
    
}
