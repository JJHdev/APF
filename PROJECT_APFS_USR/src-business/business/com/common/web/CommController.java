package business.com.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.authority.AuthorityService;
import business.com.common.service.CommService;
import business.com.user.service.GroupService;
import common.base.BaseController;
import common.util.CommUtils;

/**
 *  [컨트롤러클래스] - 공통 Controller
 *
 * @class   : CommController
 * @author  :
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
public class CommController extends BaseController {

    @Resource(name="CommService")
    protected CommService commService;

    @Resource(name="AuthorityService")
    protected AuthorityService authorityService;

    @Resource(name="GroupService")
    protected GroupService groupService;

    /**
     * Combo Code 조회
     */
    @RequestMapping("/com/common/getComboCode.do")
    @ResponseBody
    public List getComboCode(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        return commService.listCode(paramMap);
    }

    /**
     * 특정 코드 조회
     */
    @RequestMapping("/com/common/getCode.do")
    @ResponseBody
    public Map getCode(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        Map codeMap = commService.getCode(
        		(String)paramMap.get("upCdId"), 
        		(String)paramMap.get("cdId")
        			);
    	return getSuccess(codeMap);
    }

    /**
     * 2021.08.26 LSH
     * 해당 시스템 메뉴 목록 조회 (JSON)
     * 
     * 입력조건에 따라 메뉴가 2가지로 조회되어야 한다.
     * - 관리자시스템 (sysCd = ADM)
     * - 사용자시스템 (sysCd = USR)
     * TREE 형태의 계층구조 목록으로 반환한다.
     */
    @RequestMapping("/com/common/listMenu.do")
    @ResponseBody
    public List listMenu(HttpServletRequest request, 
    		ModelMap model) throws Exception {

        Map paramMap = getParameterMap(request, true);
        
        // -------------------- Default Setting End -----------------------//
        Map menuMap = new HashMap();
        menuMap.put("roleId",   CommUtils.nvlTrim(getUserInfo(request).getRoleId(), CommConst.ROLE_RESTRICTED));
        menuMap.put("sysCd",    paramMap.get("sysCd"));
        menuMap.put("notId",    paramMap.get("notId"));
        menuMap.put("menuLvl",  paramMap.get("menuLvl"));
        menuMap.put("upMenuId", paramMap.get("upMenuId"));
        if (paramMap.containsKey("useYn"))
        	menuMap.put("useYn", paramMap.get("useYn"));
        List<Map> menuList = authorityService.getMenuList(menuMap);

        // TREE 형태의 계층구조로 변환
		return _buildMenuTree(paramMap, menuList); 
    }

    /**
     * 2023.06.14 LSH
     * 마이페이지 메뉴 목록 조회 (JSON)
     */
    @RequestMapping("/com/common/listMypageMenu.do")
    @ResponseBody
    public List listMypageMenu(HttpServletRequest request, 
    		ModelMap model) throws Exception {

        Map paramMap = getParameterMap(request, true);
        
        paramMap.put("gsUserNo", CommUtils.nvlTrim(getUserInfo(request).getUserNo(), CommConst.USER_GUEST_ID));
        paramMap.put("gsRoleId", CommUtils.nvlTrim(getUserInfo(request).getRoleId(), CommConst.ROLE_RESTRICTED));
        
        // 대표계정이 아닌 경우에만 조건설정
        String rprsYn = CommConst.NO;
        if (CommUtils.isNotEmpty(getUserInfo(request).getUserNo()))
        	rprsYn = groupService.getGrpRprsYn(getUserInfo(request).getUserNo());
        
        if (!CommConst.YES.equals(rprsYn))
            paramMap.put("gsRprsYn", CommConst.NO);

        // -------------------- Default Setting End -----------------------//
        List<Map> menuList = groupService.listMypageMenu(paramMap);
		return menuList; 
    }
    
    /**
     * 메뉴목록을 TREE 형태의 계층구조로 변환하여 반환한다.
     */
    private List _buildMenuTree(Map paramMap, List menuList) {

        // 최상위 항목
        String rootId   = (String)paramMap.get("rootId");
        String rootText = (String)paramMap.get("rootText");
    	
        // 2021.08.26 메뉴를 계층구조로 변경
		// 2021.09.09 계층구조 생성유틸로 변경
        // 2023.01.11 menuLvl 용도변경에 따라 level로 계층레벨 사용변경
		Map keys = new HashMap();
		keys.put("level" , "level");
		keys.put("itemId", "menuId");
		keys.put("itemNm", "menuNm");
		keys.put("parentId", "upMenuId");
		
		List<Map> list = CommBizUtils.buildTree(menuList, keys);
		
        // 최상위 항목이 존재하는 경우
		if (CommUtils.isNotEmpty(rootText)) {
			Map root = new HashMap();
			root.put("id", rootId);
			root.put("text", rootText);
			root.put("children", list);
			List<Map> result = new ArrayList<Map>();
			result.add(root);
			return result;
		}
		return list; 
    }

    /**
     * 서류양식 상위코드 콤보 조회
     */
    @RequestMapping("/com/common/getComboUpPape.do")
    @ResponseBody
    public List getComboUpPape(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        return commService.listUpPape(paramMap);
    }
    
    /**
     * 서류양식 하위코드 콤보 조회
     */
    @RequestMapping("/com/common/getComboPape.do")
    @ResponseBody
    public List getComboPape(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        return commService.listPape(paramMap);
    }
    
    /**
     * 투자 분야 Combo Code 조회
     */
    @RequestMapping("/com/common/getInvtRlmComboCode.do")
    @ResponseBody
    public List getInvtRlmComboCode(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        return commService.listInvtRlmCode(paramMap);
    }
    
    /**
     * 유관기관 Combo Code 조회
     */
    @RequestMapping("/com/common/getCrdnsEntComboCode.do")
    @ResponseBody
    public List getCrdnsEntComboCode(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        return commService.listCrdnsEntCode(paramMap);
    }

    /**
     * 2023.07.03 LSH
     * TREE형 콤보값 목록 조회
     * REGEXP 형태의 조건으로 하위노드를 검색함
     * 업종(CT.INDUTY_SE) 트리구조 조회시 사용됨
     * 
     * 
     * @param paramMap.upCdId   상위코드
     * @param paramMap.srchMode SELECT: 노드선택검색 / SEARCH: 텍스트검색
     * @param paramMap.srchCode 노드선택검색시 상위노드값
     * @param paramMap.srchText 텍스트검색시 검색텍스트
     */
    @RequestMapping("/com/common/getListTreeCode.do")
    @ResponseBody
    public List getListTreeCode(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        
        if (CommUtils.isEmpty(paramMap.get("upCdId")))
        	return null;
        
        if (CommUtils.isEmpty(paramMap.get("srchMode")))
        	return null;

        String mode = CommUtils.getStr(paramMap, "srchMode");
        String code = CommUtils.getStr(paramMap, "srchCode");
        String text = CommUtils.getStr(paramMap, "srchText");
        
        // 노드선택검색인 경우
        if (CommConst.SELECT.equals(mode)) {
	        // 1단계 (1자리) / 2단계 (3자리) / 그 이후단계는 1자리씩 증가됨
	        if (CommUtils.isEmpty(code)) code  = "_"; 
	        else if (code.length() == 1) code += "_";
	        paramMap.put("srchCode", code);
	        
	        return commService.listTreeCode(paramMap);
        }
        // 텍스트검색인 경우
        else {
        	List list = commService.listTreeCode(paramMap);
        	
        	if (list == null)
        		return null;
        	
        	Map<String,String> keys = new HashMap<String,String>();
        	keys.put("level"   , "level");
        	keys.put("itemId"  , "code");
        	keys.put("itemNm"  , "text");
        	keys.put("parentId", "parent");
        	// 목록을 구조화된 TREE 형태로 반환한다.
        	return CommBizUtils.buildTree(list, keys);
        }
    }

    /**
     * 업종트리 선택팝업 오픈
     */
    @RequestMapping("/com/common/modalTpbizTree.do")
    public String modalTpbizTree(ModelMap model
    		, @RequestBody HashMap paramMap) throws Exception {

    	model.addAttribute("model", paramMap);
    	
        return "com/common/modalTpbizTree";
    }

    /**
     * 사업자등록번호 조회팝업 오픈
     */
    @RequestMapping("/com/common/modalBizNo.do")
    public String modalBizNo(ModelMap model
    		, @RequestBody HashMap paramMap) throws Exception {

    	model.addAttribute("model", paramMap);
    	
        return "com/common/modalBizNo";
    }
    
    /**
     * 2023.08.17 LSH
     * 사업 분야 Combo Code 조회
     */
    @RequestMapping("/com/common/getBizRlmComboCode.do")
    @ResponseBody
    public List getBizRlmComboCode(HttpServletRequest request) throws Exception {
        Map paramMap = getParameterMap(request, true);
        return commService.listBizRlmCode(paramMap);
    }
    
}