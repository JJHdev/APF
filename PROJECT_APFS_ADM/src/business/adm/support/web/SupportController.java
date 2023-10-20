package business.adm.support.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.support.service.SupportService;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.prog.service.ProgVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러클래스] - 지원사업관리-신청현황관리 Controller
 * 
 * @class   : SupportController
 * @author  : LHB
 * @since   : 2023.04.27
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class SupportController extends BaseController {
	
	@Resource(name = "SupportService")
	protected SupportService supportService;
	
	@Autowired 
    private InvtSprtValidator invtSprtValidator;
	
	@Autowired 
    private SprtBizPrgreValidator sprtBizPrgreValidator;
	
	@Resource(name="BizFileService")
    protected BizFileService bizFileService;
    
    /**
     * 지원사업관리-신청현황관리 화면 오픈
     */
    @RequestMapping("/adm/support/support/openInvtSprtAply.do")
    public String openInvtSprtAply(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/support/support/openInvtSprtAply";
    }
    
    /**
     * 지원사업관리-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/support/support/modalInvtSprtAplyForm.do")
    public String modalInvtSprtForm(ModelMap model, HttpServletRequest request,@RequestParam HashMap params, @RequestBody SupportVO SupportVO) throws Exception {
        setGlobalSessionVO(request, SupportVO);
        
        if (CommUtils.isNotEmpty(SupportVO.getSprtAplyNo())) {
        	SupportVO data = supportService.viewInvtSprtAply(SupportVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 매출액
        	List listSls	= supportService.listInvtSprtSls(data);
        	// 농식품 투자조합 정보
        	List listMxtr	= supportService.listInvtMxtr(data);
        	// 제출서류
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model",		data);
        	model.addAttribute("listSls",	listSls);
        	model.addAttribute("listMxtr",	listMxtr);
        } else {
        	// 등록모드
        	SupportVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", SupportVO);
        }
        
        return "adm/support/support/modalInvtSprtAplyForm";
    }
    
    /**
     * 지원사업관리-신청현황관리 JSON 목록 반환
     */
    @RequestMapping("/adm/support/support/getListInvtSprtAply.do")
    @ResponseBody
    public Map getListInvtSprtAply(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO) throws Exception {
        setGlobalSession(request, supportVO);
        
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = supportService.listInvtSprtAply(supportVO, page, size);
		} else {
			list = supportService.listInvtSprtAply(supportVO);
		}
        
        // 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 지원사업관리-신청현황관리 상세조회 JSON 반환
     */
    @RequestMapping("/adm/support/support/getInvtSprtAply.do")
    @ResponseBody
    public Map getInvtSprtAply(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO) throws Exception {
        setGlobalSession(request, supportVO);
        
        SupportVO result = supportService.viewInvtSprtAply(supportVO);
        
        return getSuccess(result);
    }
    
    //2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 데이터조회
    @RequestMapping("/adm/support/support/getInvtSprtAplyFileInfo.do")
    @ResponseBody
    public Map getInvtSprtAplyFileInfo(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO) throws Exception {
        setGlobalSession(request, supportVO);
        
        SupportVO result = supportService.viewInvtSprtAplyFileInfo(supportVO);
        
        return getSuccess(result);
    }
    //2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 메소드
    @RequestMapping("/adm/support/support/saveFile.do")
    @ResponseBody
    public Map saveFile(@RequestBody SupportVO sprtVO
    		, HttpServletRequest request
    		, HttpSession session
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, sprtVO);
        
        // 검증을 위한 서류코드 목록조회
        sprtVO.setPapeList(
                bizFileService.listPape(
                		BizFileVO.builder()
                		.upDcmntCd(CodeConst.getPapeGroup(sprtVO.getSprtSeCd()))
                		.aplySeCd (CodeConst.getPapeType(sprtVO.getGsBzentyYn()))
                		.dtlSeCd  (sprtVO.getPrgrmNo())
                		.build    ()
                )
        );
    	// 저장데이터에 맞게 REBUILD
        sprtVO.rebuildProperties(session);
    	
        // 투자지원신청 제출서류를 저장한다.
    	String result = supportService.saveFile(sprtVO);
    	logger.info("sprtVOsprtVOsprtVO"+sprtVO);
    	logger.info("resultresultresult"+result);
    	// 성공결과 반환
        return getSuccess(result);
    }
    
    /**
     * 지원사업관리-신청현황관리 삭제처리
     */
    @RequestMapping("/adm/support/support/saveInvtSprtAply.do")
    @ResponseBody
    public Map saveInvtSprtAply(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, supportVO);

        // 입력값 검증
        sprtBizPrgreValidator.validate(supportVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save SprtBizPrgre Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        String result = supportService.saveInvtSprtAply(supportVO, null);
        
        return getSuccess("Message", result);
    }
    
    /**
     * 지원사업관리-신청현황관리 매출액 JSON 목록 반환
     */
    @RequestMapping("/adm/support/support/getListInvtSprtSls.do")
    @ResponseBody
    public Map getListInvtSprtSls(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO) throws Exception {
        setGlobalSession(request, supportVO);
        
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = supportService.listInvtSprtSls(supportVO, page, size);
		} else {
			list = supportService.listInvtSprtSls(supportVO);
		}
        
        return getSuccess(list);
    }
    
    /**
     * 지원사업관리-신청현황관리 지원사업진행현황 JSON 목록 반환
     */
    @RequestMapping("/adm/support/support/getListSprtBizPrgre.do")
    @ResponseBody
    public Map getListSprtBizPrgre(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO) throws Exception {
        setGlobalSessionVO(request, supportVO);
        
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = supportService.listSprtBizPrgre(supportVO, page, size);
		} else {
			list = supportService.listSprtBizPrgre(supportVO);
		}
        
		// Easy UI GRID용 결과값 반환
		return getEasyUIResult(list);
    }
    
    /**
     * 지원사업관리-신청현황관리 지원사업진행현황 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/support/support/saveSprtBizPrgre.do")
    @ResponseBody
    public Map saveSprtBizPrgre(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SupportVO supportVO,
    		BindingResult bindingResult) throws Exception {
        setGlobalSessionVO(request, supportVO);

        // 입력값 검증
        sprtBizPrgreValidator.validate(supportVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save SprtBizPrgre Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        String result = supportService.saveSprtBizPrgre(supportVO);
        
        Map resultMap = getSuccess("Message", result);
        resultMap.put("prgrsSttsCd", supportVO.getPrgrsSttsCd());
        
        return resultMap;
    }
    
    /**
     * 지원사업관리-신청현황관리 엑셀 다운로드
     */
    @RequestMapping("/adm/support/support/downInvtSprtAply.do")
    public String downInvtSprtAply(HttpServletRequest request, @ModelAttribute SupportVO supportVO, ModelMap model) throws Exception {
        Map paramMap = getParameterMap(request, true);
        
        // 목록 조회
        List<SupportVO> list = supportService.listInvtSprtAplyExcl(supportVO);
		
		model.addAttribute(ExcelTempView.DATA_KEY    , list);
		model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
		model.addAttribute(ExcelTempView.TEMPLATE_KEY, "InvtSprtAply");
		model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "신청현황관리");
		
		return "excelTempView";
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
