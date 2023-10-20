package business.usr.inform.web;

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

import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 > 우수투자사례 Controller
 * 
 * @class   : BbsInvtCaseController
 * @author  : JH
 * @since   : 2023.06.28
 * @version : 1.3
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
public class BbsInvestCaseController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;
	
	// 우수투자사례 구분코드
	private static final String BBS_CODE = CodeConst.BBS_INVTCASE;
	
    /**
     * 정보서비스-투자유치가이드 화면 오픈 (HTML만 있음)
     */
    @RequestMapping("/usr/inform/bbs/openInvestGuide.do")
    public String openInvestGuide(HttpServletRequest request, @ModelAttribute BbsVO bbsVO, ModelMap model) throws Exception {

    	setGlobalSessionVO(request, bbsVO);
		model.addAttribute("model", bbsVO);
		
		return "usr/inform/bbs/openInvestGuide";
    }

	/**
	 * 정보서비스 - 우수투자사례 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listInvestCase.do")
	public String listInvestCase(HttpServletRequest request, ModelMap model, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		bbsVO.setPage(bbsVO.getPage());
		bbsVO.setSrchType(bbsVO.getSrchType());
		
		model.addAttribute("model", bbsVO);
		return "usr/inform/bbs/listInvestCase";
	}

	/**
	 * 정보서비스 - 우수투자사례 상세조회 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/viewInvestCase.do")
	public String viewInvestCase(HttpServletRequest request, ModelMap model, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		BbsVO data = bbsService.viewBbs(bbsVO);
		
		bbsService.updtBbsInqCnt(bbsVO);
		if (data == null)
			throw new BusinessException(message.getMessage("exception.notKey"));
		
		data.setFiles(bbsService.getBbsFileSn(bbsVO).getFiles());
		data.setSrchType(bbsVO.getSrchType());
		data.setPage(bbsVO.getPage());
		data.setSrchText(bbsVO.getSrchText());
		
		model.addAttribute("model", data);

		return "usr/inform/bbs/viewInvestCase";
	}
	
	/**
	 * 정보서비스 - 우수투자사례 목록JSON 반환 (일반 GRID)
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/usr/inform/bbs/getListInvestCase.do")
	@ResponseBody
	public Map getListInvestCase(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		// -------------------- Default Setting End -----------------------//
		
		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = bbsService.listBbs(bbsVO, page, size);
		} else {
			list = bbsService.listBbs(bbsVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, BbsVO bbsVO) {
    	
    	setGlobalSession(request, bbsVO);
        
        // 게시판구분 정의
		bbsVO.setBbsSeCd(BBS_CODE);

		if (bbsVO.getUserInfo(request) != null) {
        	bbsVO.setGsUserNo  (bbsVO.getUserInfo(request).getUserNo  ());
        	bbsVO.setGsRoleId  (bbsVO.getUserInfo(request).getRoleId  ());
        	bbsVO.setGsBzentyNo(bbsVO.getUserInfo(request).getBzentyNo());
        }
		// Context
		bbsVO.setGsContext(request.getContextPath());
    }
}
