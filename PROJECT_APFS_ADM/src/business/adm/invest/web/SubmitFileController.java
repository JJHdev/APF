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

import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.inform.service.BbsVO;
import business.adm.invest.service.DscsnVO;
import business.adm.invest.service.SubmitFileService;
import business.adm.invest.service.SubmitFileVO;
import business.adm.support.web.InvtSprtValidator;
import business.adm.support.web.SprtBizPrgreValidator;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import commf.exception.BusinessException;
import business.adm.CodeConst;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 운영관리-제출서류관리 Controller
 * 
 * @class   : DscsnController
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class SubmitFileController extends BaseController {
	
	@Resource(name="SubmitFileService")
	protected SubmitFileService submitFileService;
	
    /**
     * 지원사업관리-신청현황관리 화면 오픈
     */
    @RequestMapping("/adm/invest/submitfile/openSubmitFile.do")
    public String openSubmitFile(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/invest/submitfile/openSubmitFile";
    }
    
    
    /**
     * 지원사업관리-신청현황관리 JSON 목록 반환
     */
    @RequestMapping("/adm/invest/submitfile/getListSubmitFile.do")
    @ResponseBody
    public Map getListSubmitFile(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SubmitFileVO submitFileVO) throws Exception {
        setGlobalSession(request, submitFileVO);
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = submitFileService.listSubmitFile(submitFileVO, page, size);
		} else {
			list = submitFileService.listSubmitFile(submitFileVO);
		}
        // 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 상담일지-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/submitfile/modalSubmitFileForm.do")
    public String modalSubmitFileForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody SubmitFileVO submitFileVO) throws Exception {
    	setGlobalSessionVO(request, submitFileVO);
        
        if (CommUtils.isNotEmpty(submitFileVO.getDcmntCd())) {
        	SubmitFileVO data = submitFileService.viewSubmitFile(submitFileVO);
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model",	data);
        } else {
        	// 등록모드
        	submitFileVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", submitFileVO);
        }
        return "adm/invest/submitfile/modalSubmitFileForm";
    }
    
    /**
	 * 상담일지 공통저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/adm/invest/submitfile/saveSubmitFile.do")
	@ResponseBody
	public Map saveSubmitFile(@ModelAttribute SubmitFileVO submitFileVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {
		// 세션정보 정의
		setGlobalSession(request, submitFileVO);
		// 세션사용자번호 정의
		if (submitFileVO.getUserInfo(request) != null)
			submitFileVO.setGsUserNo(submitFileVO.getUserInfo(request).getUserNo());
		
		String mode = submitFileVO.getMode();
		SubmitFileVO checkVO = submitFileService.viewSubmitFile(submitFileVO);
	    
		  // 입력값 검증시 오류가 있을 경우 
		if (isSubmitFileVOInsertEmpty(checkVO) && !CommConst.DELETE.equals(mode) && CommConst.INSERT.equals(mode)) {
			throw new BusinessException(message.getMessage("프로그램,서류,신청구분이 이미 중복된 신청서가 있습니다."));
		}
		// 게시판를 저장한다.
		String result = submitFileService.saveSubmitFile(submitFileVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SubmitFileVO submitFileVO) {
    	
        setGlobalSession(request, submitFileVO);

        if (submitFileVO.getUserInfo(request) != null) {
        	submitFileVO.setGsUserNo(submitFileVO.getUserInfo(request).getUserNo());
        	submitFileVO.setGsRoleId(submitFileVO.getUserInfo(request).getRoleId());
        }
    }
    
    public boolean isSubmitFileVOInsertEmpty(SubmitFileVO submitFileVO) {
        if (submitFileVO == null) {
            return false;
        }
        if (submitFileVO.getAplySeCd() == null || submitFileVO.getDcmntTaskSeCd() == null) {
            return false;
        }
        return true;
    }
    
}
