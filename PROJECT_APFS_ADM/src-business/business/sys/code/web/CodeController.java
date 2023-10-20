package business.sys.code.web;

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

import business.adm.invest.service.SubmitFileVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러 클래스] - 코드관리
 *
 * @class   : CodeController
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일         수정자                수정내용
 *  -------        --------      ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
@RequestMapping("/adm")
public class CodeController extends BaseController {

    @Resource(name="CodeService")
    protected CodeService codeService;

    /**
     * 코드리스트 페이지
     */
    @RequestMapping("/sys/code/listCode.do")
    public String listCode(HttpServletRequest request, @RequestParam Map paramMap
            , @ModelAttribute CodeVO codeVO, ModelMap model) throws Exception {
        setGlobalSession(request, codeVO);
        // -------------------- Default Setting End -----------------------//
        model.addAttribute("model",     codeVO);
        return "adm/sys/code/listCode";
    }
    /**
     * 코드리스트 조회
     */
    @RequestMapping("/sys/code/getListCode.do")
    @ResponseBody
    public Map getlistCode(HttpServletRequest request
    		, @RequestParam Map<String,String> reqMap
            , @ModelAttribute CodeVO codeVO
            , ModelMap model) throws Exception {
        setGlobalSession(request, codeVO);
        // -------------------- Default Setting End -----------------------//
        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = codeService.listCode(codeVO, page, size);
        }
        else {
        	list = codeService.listCode(codeVO);
        }
        return getPaginatedResult(list);
    }

    /**
     * 코드상세조회JSON 반환
     */
    @RequestMapping("/sys/code/getCode.do")
    @ResponseBody
    public Map getCode(HttpServletRequest request
            , @ModelAttribute CodeVO codeVO
            , ModelMap model) throws Exception {
        CodeVO obj = codeService.viewCode(codeVO);
        return getSuccess(obj);
    }
    /**
     * 코드저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/sys/code/saveCode.do")
    @ResponseBody
    public Map saveCode(HttpServletRequest request
    		, @ModelAttribute CodeVO codeVO) throws Exception {
    	
        setGlobalSession(request, codeVO);
        
        if (codeVO.getUserInfo(request) != null)
        	codeVO.setGsUserNo(codeVO.getUserInfo(request).getUserNo());
    	
        // 코드관리를 저장한다.
    	String result = codeService.saveCode(codeVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
    /**
     * 시스템 코드 관리-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/sys/code/modalCodeForm.do")
    public String modalCodeForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody CodeVO codeVO) throws Exception {
    	setGlobalSession(request, codeVO);
        
        if (CommUtils.isNotEmpty(codeVO.getCdNm())) {
        	 CodeVO obj = codeService.viewCode(codeVO);
        	if (obj == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	// 수정모드
        	obj.setMode(CommConst.UPDATE);
        	model.addAttribute("model",	obj);
        } else {
        	// 등록모드
        	codeVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", codeVO);
        }
        
        return "adm/sys/code/modalCodeForm";
    }
    /**
     * 코드엑셀 다운로드
     
    @RequestMapping("/sys/code/downCodeExcel.do")
    public String downCodeExcel(HttpServletRequest request
    		, @ModelAttribute CodeVO codeVO
    		, ModelMap model) throws Exception {
    	
    	Map paramMap = getParameterMap(request, true);
    	// 목록 조회
    	List<CodeVO> list = codeService.listCode(codeVO);
    	
    	model.addAttribute(ExcelTempView.DATA_KEY    , list);
    	model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
    	model.addAttribute(ExcelTempView.TEMPLATE_KEY, "Code");
    	model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "코드관리");
    	return "excelTempView";
    }    
    */
}
