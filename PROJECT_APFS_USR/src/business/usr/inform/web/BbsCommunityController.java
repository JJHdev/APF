package business.usr.inform.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import business.usr.inform.web.validator.BbsDataValidator;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 > 유관기관커뮤니티 Controller
 * 
 * @class   : BbsCommunityController
 * @author  : JH
 * @since   : 2023.06.28
 * @version : 1.3
 */
@Controller
@SuppressWarnings({"all"})
public class BbsCommunityController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;

	@Resource(name = "fileManager")
	private FileManager fileManager;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private BbsDataValidator bbsDataValidator;
	
	// 유관기관커뮤니티 구분코드
	private static final String BBS_CODE = CodeConst.BBS_COMMUNITY;

	/**
	 * 정보서비스 - 유관기관커뮤니티 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listCommunity.do")
	public String listCommunity(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);

		model.addAttribute("model", bbsVO);
		
		return "usr/inform/bbs/listCommunity";
	}
	
	/**
	 * 정보서비스 - 유관기관커뮤니티 등록/수정 모달팝업화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/modalCommunityForm.do")
	public String modalCommunity(ModelMap model, HttpServletRequest request, @RequestBody BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		if (CommUtils.isNotEmpty(bbsVO.getPstNo())) {
			BbsVO data = bbsService.viewBbs(bbsVO);
			
			if (data == null)
				throw new BusinessException(message.getMessage("exception.notKey"));
			// 수정모드
			data.setMode(CommConst.UPDATE);
			model.addAttribute("model", data);
		} 
		else {
			bbsVO.setBbsSeCd(BBS_CODE);
			// 등록모드
			bbsVO.setMode(CommConst.INSERT);
			model.addAttribute("model", bbsVO);
		}
		
		return "usr/inform/bbs/modalCommunityForm";
	}

	/**
	 * 정보서비스 - 유관기관커뮤니티 상세조회 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/viewCommunity.do")
	public String viewCommunity(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
 
		BbsVO data = bbsService.viewBbs(bbsVO);

		setGlobalSessionVO(request, data);
		
		bbsService.updtBbsInqCnt(bbsVO);
		
		if (data == null)
			throw new BusinessException(message.getMessage("exception.notKey"));
		
		model.addAttribute("model" , data);
		model.addAttribute("search", bbsVO);

		return "usr/inform/bbs/viewCommunity";
	}

    /**
     * 정보서비스 - 유관기관커뮤니티 조회JSON 반환
     */
    @RequestMapping("/usr/inform/bbs/getCommunity.do")
    @ResponseBody
    public Map getCommunity(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute BbsVO bbsVO) throws Exception {
    	
    	setGlobalSessionVO(request, bbsVO);
        
    	BbsVO data = bbsService.viewBbs(bbsVO);

    	return getSuccess(data);
    }

	/**
	 * 정보서비스 - 유관기관커뮤니티 목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListCommunity.do")
	@ResponseBody
	public Map getListCommunity(ModelMap model, 
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
	 * 정보서비스 - 유관기관커뮤니티 저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/usr/inform/bbs/saveCommunity.do")
	@ResponseBody
	public Map saveCommunity(@ModelAttribute BbsVO bbsVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {

		// 세션정보 정의
		setGlobalSessionVO(request, bbsVO);

        // 저장할 입력값 검증
		bbsDataValidator.validate(bbsVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Bbs Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        // 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
		// 게시판를 저장한다.
		String result = bbsService.saveBbs(bbsVO, files);
		// 성공결과 반환
		return getSuccess("Message", result);
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
    }
}
