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

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 > 자주묻는질문 Controller
 * 
 * @class   : BbsFAQController
 * @author  : JH
 * @since   : 2023.06.28
 * @version : 1.3
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class BbsFAQController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;
	
	// 자주묻는질문 구분코드
	private static final String BBS_CODE = CodeConst.BBS_FAQ;

	/**
	 * 고객센터 - 자주묻는질문 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listFAQ.do")
	public String listFAQ(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		
		model.addAttribute("model", bbsVO);
		return "usr/inform/bbs/listFAQ";
	}
	
	/**
	 * 게시판 공통목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListBbs.do")
	@ResponseBody
	public Map getListBbs(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute BbsVO bbsVO) throws Exception {

		setGlobalSessionVO(request, bbsVO);
		// -------------------- Default Setting End -----------------------//

		
		logger.info("getCkImageContext = ", bbsVO.getCkImageContext());
		logger.info("getGsContext = ", bbsVO.getGsContext());
		
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
	 * 게시판 공통목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListFAQ.do")
	@ResponseBody
	public Map getListFAQ(ModelMap model, 
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
        	bbsVO.setGsUserNm  (bbsVO.getUserInfo(request).getUserNm  ());
        	bbsVO.setGsRoleId  (bbsVO.getUserInfo(request).getRoleId  ());
        	bbsVO.setGsBzentyNo(bbsVO.getUserInfo(request).getBzentyNo());
        }
		// Context
		bbsVO.setGsContext(request.getContextPath());

		bbsVO.setCkImageContext(CommConst.CKEDITOR_IMAGE_CONTEXT);
    }
}
