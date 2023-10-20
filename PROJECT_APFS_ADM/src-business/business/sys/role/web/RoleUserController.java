package business.sys.role.web;

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

import business.sys.role.service.RoleUserService;
import business.sys.role.service.RoleVO;
import common.base.BaseController;

/**
 * [컨트롤러클래스] - 역할별사용자관리 관리 Controller
 * 
 * @class   : RoleUserController
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
public class RoleUserController extends BaseController {

    @Resource(name="RoleUserService")
    protected RoleUserService roleUserService;
    
    /**
     * 역할별사용자관리 관리 화면
     */
    @RequestMapping("/sys/role/listRoleUser.do")
    public String listRoleUser(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute RoleVO roleVO) throws Exception {
				
        setGlobalSession(request, roleVO);
        // -------------------- Default Setting End -----------------------//

        model.addAttribute("model", roleVO);
    	
        return "adm/sys/role/listRoleUser";
    }

    /**
     * 역할별사용자관리 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/role/getListRoleUser.do")
    @ResponseBody
    public Map getListRoleUser(HttpServletRequest request, @RequestParam Map<String,String> reqMap
            , @ModelAttribute RoleVO roleVO, ModelMap model) throws Exception {

        setGlobalSession(request, roleVO);
        // -------------------- Default Setting End -----------------------//

        List list = roleUserService.listRoleUser(roleVO);

        // Easy UI GRID용 결과값 반환
        return getEasyUIResult(list);
    }

    /**
     * 역할별사용자관리 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     * Object와 Array 복합된 JSON 데이터 받기
     * (@RequestBody를 Model 객체에 사용하며, 
     *  Model 객체에 List를 Property로 가지고 있는다.)
     */
    @RequestMapping("/sys/role/saveRoleUser.do")
    @ResponseBody
    public Map saveRoleUser(
    		HttpServletRequest request, 
    		@RequestBody RoleVO roleVO
    	) throws Exception {
    	
        setGlobalSession(request, roleVO);
        
        if (roleVO.getUserInfo(request) != null)
        	roleVO.setUserNo(roleVO.getUserInfo(request).getUserNo());
        // 역할별사용자관리를 저장한다.
    	String result = roleUserService.saveRoleUser(roleVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
}
