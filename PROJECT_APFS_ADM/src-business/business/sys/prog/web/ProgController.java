package business.sys.prog.web;

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

import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.menu.service.MenuVO;
import business.sys.prog.service.ProgService;
import business.sys.prog.service.ProgVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러클래스] - 프로그램관리 관리 Controller
 * 
 * @class   : ProgController
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
@RequestMapping("/adm")
public class ProgController extends BaseController {

    @Resource(name="ProgService")
    protected ProgService progService;
    
    /**
     * 프로그램관리 관리 화면
     */
    @RequestMapping("/sys/prog/listProg.do")
    public String listProg(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute ProgVO progVO) throws Exception {
				
        setGlobalSession(request, progVO);
        // -------------------- Default Setting End -----------------------//

        model.addAttribute("model", progVO);
    	
        return "adm/sys/prog/listProg";
    }

    /**
     * 프로그램관리 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/prog/getListProg.do")
    @ResponseBody
    public Map getListProg(HttpServletRequest request, @RequestParam Map<String,String> reqMap
            , @ModelAttribute ProgVO progVO, ModelMap model) throws Exception {

        setGlobalSession(request, progVO);
        // -------------------- Default Setting End -----------------------//

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = progService.listProg(progVO, page, size);
        }
        else {
        	list = progService.listProg(progVO);
        }
        // Easy UI GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 시스템 코드 관리-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/sys/prog/modalProgForm.do")
    public String modalProgForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody ProgVO progVO) throws Exception {
    	setGlobalSession(request, progVO);
        
        if (CommUtils.isNotEmpty(progVO.getPrgrmId())) {
        	ProgVO obj = progService.viewProg(progVO);
        	if (obj == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	// 수정모드
        	obj.setMode(CommConst.UPDATE);
        	model.addAttribute("model",	obj);
        } else {
        	// 등록모드
        	progVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", progVO);
        }
        
        return "adm/sys/prog/modalProgForm";
    }

    /**
     * 프로그램관리 조회JSON 반환
     */
    @RequestMapping("/sys/prog/getProg.do")
    @ResponseBody
    public Map getProg(HttpServletRequest request
            , @ModelAttribute ProgVO progVO
			, ModelMap model) throws Exception {

        ProgVO obj = progService.viewProg(progVO);
        return getSuccess(obj);
    }

    /**
     * 프로그램관리 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/sys/prog/saveProg.do")
    @ResponseBody
    public Map saveProg(
    		HttpServletRequest request, 
    		@ModelAttribute ProgVO progVO
    	) throws Exception {
    	
        setGlobalSession(request, progVO);
        
        if (progVO.getUserInfo(request) != null)
        	progVO.setGsUserNo(progVO.getUserInfo(request).getUserNo());
        // 프로그램관리를 저장한다.
    	String result = progService.saveProg(progVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }

    /**
     * 프로그램관리 엑셀 다운로드
    @RequestMapping("/sys/prog/downProgExcel.do")
    public String downProgExcel(
			HttpServletRequest request, 
            @ModelAttribute ProgVO progVO,
			ModelMap model
		) throws Exception {
    	
        Map paramMap = getParameterMap(request, true);
        // 목록 조회
        List<ProgVO> list = progService.listProg(progVO);
		
		model.addAttribute(ExcelTempView.DATA_KEY    , list);
		model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
		model.addAttribute(ExcelTempView.TEMPLATE_KEY, "Prog");
		model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "프로그램관리");
		return "excelTempView";
    }
     */
}
