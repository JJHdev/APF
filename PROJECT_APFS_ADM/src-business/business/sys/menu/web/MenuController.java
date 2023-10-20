package business.sys.menu.web;

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

import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.code.service.CodeVO;
import business.sys.menu.service.MenuService;
import business.sys.menu.service.MenuVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러클래스] - 메뉴관리 관리 Controller
 * 
 * @class   : MenuController
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
public class MenuController extends BaseController {

    @Resource(name="MenuService")
    protected MenuService menuService;
    
    /**
     * 메뉴관리 관리 화면
     */
    @RequestMapping("/sys/menu/listMenu.do")
    public String listMenu(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute MenuVO menuVO) throws Exception {
				
        setGlobalSession(request, menuVO);
        // -------------------- Default Setting End -----------------------//
        model.addAttribute("model", menuVO);
    	
        return "adm/sys/menu/listMenu";
    }

    /**
     * 메뉴관리 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/menu/getListMenu.do")
    @ResponseBody
    public Map getListMenu(HttpServletRequest request
    		, @RequestParam Map<String,String> reqMap
            , @ModelAttribute MenuVO menuVO
            , ModelMap model) throws Exception {

        setGlobalSession(request, menuVO);
        // -------------------- Default Setting End -----------------------//
        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = menuService.listMenu(menuVO, page, size);
        }
        else {
        	list = menuService.listMenu(menuVO);
        }
            return getPaginatedResult(list);
    }
    
    /**
     * 메뉴관리 조회JSON 반환
     */
    @RequestMapping("/sys/menu/getMenu.do")
    @ResponseBody
    public Map getMenu(HttpServletRequest request
            , @ModelAttribute MenuVO menuVO
			, ModelMap model) throws Exception {

        MenuVO obj = menuService.viewMenu(menuVO);
        return getSuccess(obj);
    }
    
    /**
     * 시스템 코드 관리-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/sys/menu/modalMenuForm.do")
    public String modalMenuForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody MenuVO menuVO) throws Exception {
    	setGlobalSession(request, menuVO);
        
        if (CommUtils.isNotEmpty(menuVO.getMenuId())) {
        	MenuVO obj = menuService.viewMenu(menuVO);
        	if (obj == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	// 수정모드
        	obj.setMode(CommConst.UPDATE);
        	model.addAttribute("model",	obj);
        } else {
        	// 등록모드
        	menuVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", menuVO);
        }
        
        return "adm/sys/menu/modalMenuForm";
    }

    /**
     * 메뉴관리 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/sys/menu/saveMenu.do")
    @ResponseBody
    public Map saveMenu(
    		HttpServletRequest request, 
    		@ModelAttribute MenuVO menuVO
    	) throws Exception {
    	
        setGlobalSession(request, menuVO);
        
        if (menuVO.getUserInfo(request) != null)
        	menuVO.setGsUserNo(menuVO.getUserInfo(request).getUserNo());
        // 메뉴관리를 저장한다.
    	String result = menuService.saveMenu(menuVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }

    /**
     * 메뉴관리 엑셀 다운로드
     
    @RequestMapping("/sys/menu/downMenuExcel.do")
    public String downMenuExcel(
			HttpServletRequest request, 
            @ModelAttribute MenuVO menuVO,
			ModelMap model
		) throws Exception {
    	
        Map paramMap = getParameterMap(request, true);
        // 목록 조회
        List<MenuVO> list = menuService.listMenu(menuVO);
		
		model.addAttribute(ExcelTempView.DATA_KEY    , list);
		model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
		model.addAttribute(ExcelTempView.TEMPLATE_KEY, "Menu");
		model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "메뉴관리");
		return "excelTempView";
    }
    */
}
