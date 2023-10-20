package business.usr.inform.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import business.usr.inform.service.SearchService;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 통합검색 Controller
 * 
 * @class   : SearchController
 * @author  : LHB
 * @since   : 2023.06.29
 * @version : 1.0
 *
 *   수정일       수정자                수정내용
 *  --------   --------    ---------------------------
 * 23.06.29      LHB             First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class SearchController extends BaseController {
	
	@Resource(name = "SearchService")
	private SearchService searchService;
	
	@Resource(name = "BbsService")
	private BbsService bbsService;
	
	// 통합검색 게시판 기본값
	private static final String[] EPRSS_BBS = new String[] { CodeConst.BBS_NOTICE, CodeConst.BBS_DATA, CodeConst.BBS_INVTCASE };

    /**
     * 통합검색 - 전체 화면 오픈
     */
    @RequestMapping("/usr/inform/search/openSearch.do")
    public String openSearch(HttpServletRequest request, ModelMap model, @RequestParam HashMap params) throws Exception {
        setGlobalSession(request, params);
        
        BbsVO data = _getBbsVO(params, null);
        
        model.addAttribute("model", data);
    	
        return "usr/inform/search/openSearch";
    }
    
    /**
     * 통합검색 - 공지사항 화면 오픈
     */
    @RequestMapping("/usr/inform/search/openSearchNotice.do")
    public String openSearchNotice(HttpServletRequest request, ModelMap model, @RequestParam HashMap params) throws Exception {
        setGlobalSession(request, params);
        
        BbsVO data = _getBbsVO(params, CodeConst.BBS_NOTICE);
        
        model.addAttribute("model", data);
    	
        return "usr/inform/search/openSearchNotice";
    }
    
    /**
     * 통합검색 - 자료실 화면 오픈
     */
    @RequestMapping("/usr/inform/search/openSearchData.do")
    public String openSearchData(HttpServletRequest request, ModelMap model, @RequestParam HashMap params) throws Exception {
        setGlobalSession(request, params);
        
        BbsVO data = _getBbsVO(params, CodeConst.BBS_DATA);
        
        model.addAttribute("model", data);
    	
        return "usr/inform/search/openSearchData";
    }
    
    /**
     * 통합검색 - 우수투자사례 화면 오픈
     */
    @RequestMapping("/usr/inform/search/openSearchInvestCase.do")
    public String openSearchInvestCase(HttpServletRequest request, ModelMap model, @RequestParam HashMap params) throws Exception {
        setGlobalSession(request, params);
        
        BbsVO data = _getBbsVO(params, CodeConst.BBS_INVTCASE);
        
        model.addAttribute("model", data);
    	
        return "usr/inform/search/openSearchInvestCase";
    }
    
    /**
     * 2023.08.14 LHB
     * 통합검색 - 메뉴 목록 조회 (JSON)
     */
    @RequestMapping("/usr/inform/search/listSearchMenu.do")
    @ResponseBody
    public List listSearchMenu(HttpServletRequest request,  ModelMap model) throws Exception {

        Map paramMap = getParameterMap(request, true);
        
        paramMap.put("gsUserNo", CommUtils.nvlTrim(getUserInfo(request).getUserNo(), CommConst.USER_GUEST_ID));
        paramMap.put("gsRoleId", CommUtils.nvlTrim(getUserInfo(request).getRoleId(), CommConst.ROLE_RESTRICTED));

        // -------------------- Default Setting End -----------------------//
        List<Map> menuList = searchService.listMypageMenu(paramMap);
		return menuList; 
    }
    
    /**
     * 통합검색 목록 JSON 반환
     */
    @RequestMapping("/usr/inform/search/getListSearch.do")
    @ResponseBody
    public Map getListSearch(ModelMap model, @RequestParam HashMap params, @ModelAttribute BbsVO bbsVO, HttpServletRequest request) throws Exception {
        setGlobalSession(request, params);
        
        bbsVO.setBbsSeCdArr(Arrays.asList(EPRSS_BBS));
        
        List list = null;
        
        if(CommUtils.isNotEmpty(CommUtils.nvlTrim((String) params.get("srchText")))) {
        	params.put("ipAddr",    request.getRemoteAddr());
        	// 공백아닐때 검색어 db 추가
        	searchService.regiSrchWrd(params);
        	
        	if (params.containsKey("page")) {
    			int page = CommUtils.getInt(params, "page");
    			int size = CommUtils.getInt(params, "rows");
    			list = bbsService.listBbs(bbsVO, page, size);
    		} else {
    			list = bbsService.listBbs(bbsVO);
    		}
        } else {
        	list = new ArrayList();
        }
    	
        return getPaginatedResult(list);
    }
    
    /**
     * 인기검색어 JSON 반환
     */
    @RequestMapping("/usr/inform/search/getListSrchWrd.do")
    @ResponseBody
    public List getListSrchWrd(ModelMap model, @RequestParam HashMap params, @ModelAttribute BbsVO bbsVO) throws Exception {
    	List list = searchService.listSrchWrd(params);
    	
    	return list;
    }
    
    private BbsVO _getBbsVO(HashMap params, String bbsSeCd) {
    	int page = CommUtils.getInt(params, "page");
		int size = CommUtils.getInt(params, "rows");
		String srchText = CommUtils.getStr(params, "srchText");
		
		BbsVO bbsVO = BbsVO.builder()
						.bbsSeCd(bbsSeCd)
						.srchText(srchText)
						.build();
		
		bbsVO.setPage    (String.valueOf(page));
		bbsVO.setPageSize(String.valueOf(size));
		
		return bbsVO;
    }
    
}
