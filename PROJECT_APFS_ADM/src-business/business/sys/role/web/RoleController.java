package business.sys.role.web;

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
import business.sys.code.service.CodeVO;
import business.sys.role.service.RoleService;
import business.sys.role.service.RoleVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러클래스] - 역할관리 관리 Controller
 * 
 * @class   : RoleController
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
@RequestMapping("/adm")
public class RoleController extends BaseController {

    @Resource(name="RoleService")
    protected RoleService roleService;
    
    /**
     * 역할관리 관리 화면
     */
    @RequestMapping("/sys/role/listRole.do")
    public String listRole(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute RoleVO roleVO) throws Exception {
				
        setGlobalSession(request, roleVO);
        // -------------------- Default Setting End -----------------------//
        model.addAttribute("model", roleVO);
    	
        return "adm/sys/role/listRole";
    }

    /**
     * 역할관리 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/role/getListRole.do")
    @ResponseBody
    public Map getListRole(HttpServletRequest request
			    		, @RequestParam Map<String,String> reqMap
			            , @ModelAttribute RoleVO roleVO
			            , ModelMap model) throws Exception {

        setGlobalSession(request, roleVO);
        roleVO.setGsRoleId(roleVO.getUserInfo(request).getRoleId());
        // -------------------- Default Setting End -----------------------//
        // 2022.01.09 LSH 세션사용자 역할ID 맵핑

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = roleService.listRole(roleVO, page, size);
        }
        else {
        	list = roleService.listRole(roleVO);
        }
        

        // Easy UI GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 역할관리 조회JSON 반환
     */
    @RequestMapping("/sys/role/getRole.do")
    @ResponseBody
    public Map getRole(HttpServletRequest request
            , @ModelAttribute RoleVO roleVO
			, ModelMap model) throws Exception {

        RoleVO obj = roleService.viewRole(roleVO);
        return getSuccess(obj);
    }

    /**
     * 역할관리 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/sys/role/saveRole.do")
    @ResponseBody
    public Map saveRole(
    		HttpServletRequest request, 
    		@ModelAttribute RoleVO roleVO
    	) throws Exception {
    	
        setGlobalSession(request, roleVO);
        
        if (roleVO.getUserInfo(request) != null) {
        	roleVO.setUserNo(roleVO.getUserInfo(request).getUserNo());
	        roleVO.setGsRoleId(roleVO.getUserInfo(request).getRoleId());
        }
    	
        // 역할관리를 저장한다.
    	String result = roleService.saveRole(roleVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
    
    /**
     * 시스템 코드 관리-신청현황관리 등록/수정 화면 오픈
     */
    @RequestMapping("/sys/role/modalRoleForm.do")
    public String modalRoleForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody RoleVO roleVO) throws Exception {
    	setGlobalSession(request, roleVO);
        
        if (CommUtils.isNotEmpty(roleVO.getRoleId())) {
        	RoleVO obj = roleService.viewRole(roleVO);
        	if (obj == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	// 수정모드
        	obj.setMode(CommConst.UPDATE);
        	model.addAttribute("model",	obj);
        } else {
        	// 등록모드
        	roleVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", roleVO);
        }
        return "adm/sys/role/modalRoleForm";
    }

    /**
     * 역할관리 엑셀 다운로드
    @RequestMapping("/sys/role/downRoleExcel.do")
    public String downRoleExcel(
			HttpServletRequest request, 
            @ModelAttribute RoleVO roleVO,
			ModelMap model
		) throws Exception {
    	
        Map paramMap = getParameterMap(request, true);

        // 2022.01.09 LSH 세션사용자 역할ID 맵핑
        roleVO.setGsRoleId((String)paramMap.get("gsRoleId"));
        
        // 목록 조회
        List<RoleVO> list = roleService.listRole(roleVO);
		
		model.addAttribute(ExcelTempView.DATA_KEY    , list);
		model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
		model.addAttribute(ExcelTempView.TEMPLATE_KEY, "Role");
		model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "역할관리");
		return "excelTempView";
    }
    */
}
