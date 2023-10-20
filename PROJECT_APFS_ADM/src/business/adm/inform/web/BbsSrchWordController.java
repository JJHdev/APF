package business.adm.inform.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.adm.CodeConst;
import business.adm.inform.service.BbsService;
import business.adm.inform.service.BbsVO;
import business.adm.inform.service.SrchWordVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 운영관리 > 검색어관리 Controller
 * 
 * @class   : SrchWordController
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class BbsSrchWordController extends BaseController {

	@Resource(name = "BbsService")
	protected BbsService bbsService;
	
	/**
	 * 운영관리 - 우수투자사례 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/openSrchWord.do")
	public String openInvestCase(ModelMap model, HttpServletRequest request, @ModelAttribute SrchWordVO srchWordVO) throws Exception {
		setGlobalSession(request, srchWordVO);
		model.addAttribute("model", srchWordVO);
		return "adm/inform/bbs/openSrchWord";
	}
	
	/**
	 * 검색어관리 공통목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/inform/bbs/getListSrchWord.do")
	@ResponseBody
	public Map getListSrchWord(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute SrchWordVO srchWordVO) throws Exception {
		setGlobalSession(request, srchWordVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = bbsService.getListSrchWord(srchWordVO, page, size);
		} else {
			list = bbsService.getListSrchWord(srchWordVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	/**
	 * 검색어관리 공통목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/inform/bbs/downSrchWordExcel.do")
	@ResponseBody
	public Map downSrchWordExcel(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute SrchWordVO srchWordVO) throws Exception {
		setGlobalSession(request, srchWordVO);
		// -------------------- Default Setting End -----------------------//
		List list = bbsService.downSrchWordExcel(srchWordVO);
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	
	/**
	 * 게시판 공통저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/adm/inform/bbs/deleteSrchWord.do")
	@ResponseBody
	public Map deleteSrchWord (@ModelAttribute SrchWordVO srchWordVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {
		// 세션정보 정의
		setGlobalSession(request, srchWordVO);
		// 세션사용자번호 정의
		if (srchWordVO.getUserInfo(request) != null)
			srchWordVO.setGsUserNo(srchWordVO.getUserInfo(request).getUserNo());
    	
    	// 입력값 검증시 오류가 있을 경우
        if (CommUtils.isEmpty(srchWordVO.getSrchWrd())) {
        	logger.info("SrchWord Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
		// 게시판를 저장한다.
        int result = bbsService.deleteSrchWord(srchWordVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}

}
