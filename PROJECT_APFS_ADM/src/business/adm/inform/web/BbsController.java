package business.adm.inform.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import business.adm.file.service.BbsFileVO;
import business.adm.inform.service.BbsService;
import business.adm.inform.service.BbsVO;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * [컨트롤러클래스] - 게시판 공통 Controller
 * 
 * 게시판의 AJAX 목록검색, 저장처리, 다운로드 등을 공통으로 처리함.
 * 
 * @class   : BbsController
 * @author  : LSH
 * @since   : 2023.04.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({ "all" })
public class BbsController extends BaseController {

	@Resource(name = "BbsService")
	protected BbsService bbsService;

	@Resource(name = "fileManager")
	protected FileManager fileManager;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private BbsValidator bbsValidator;

	/**
	 * 게시판 공통목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/inform/bbs/getListBbs.do")
	@ResponseBody
	public Map getListBbs(ModelMap model, 
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
	 * 게시판 공통저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/adm/inform/bbs/saveBbs.do")
	@ResponseBody
	public Map saveBbs(@ModelAttribute BbsVO bbsVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {
		// 세션정보 정의
		setGlobalSessionVO(request, bbsVO);
		// 세션사용자번호 정의
		if (bbsVO.getUserInfo(request) != null)
			bbsVO.setGsUserNo(bbsVO.getUserInfo(request).getUserNo());
		
        // 저장할 입력값 검증
		bbsValidator.validate(bbsVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Bbs Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 다중파일을 임시경로에 업로드한다.
        List<FileInfo> files = null;
        try {
    		files = fileManager.multiFileUploadByName(request, null);
        } catch (NullPointerException e) {
        	logger.error("NullPointerException :: ", e);
        } catch (Exception e) {
        	logger.error("Exception :: ", e);
        }
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

		if (bbsVO.getUserInfo(request) != null) {
        	bbsVO.setGsUserNo  (bbsVO.getUserInfo(request).getUserNo  ());
        	bbsVO.setGsUserNm  (bbsVO.getUserInfo(request).getUserNm  ());
        	bbsVO.setGsRoleId  (bbsVO.getUserInfo(request).getRoleId  ());
        	bbsVO.setGsBzentyNo(bbsVO.getUserInfo(request).getBzentyNo());
        }
		// Context
		bbsVO.setGsContext(request.getContextPath());
    }
}
