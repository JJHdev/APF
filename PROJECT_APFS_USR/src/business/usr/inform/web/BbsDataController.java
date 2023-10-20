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
 * [컨트롤러클래스] - 게시판 > 자료실 Controller
 * 
 * @class   : BbsDataController
 * @author  : JH
 * @since   : 2023.06.28
 * @version : 1.3
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 *  TODO 2023.05.18 LSH
 *  [작업사항]
 *  1. 목록화면 / 상세화면 기본 조회 및 디자인 적용
 *  2. 등록 모달팝업 구현 및 디자인 적용
 *  3. 첨부파일 처리 및 디자인 적용
 *  4. 분류탭별 조회 기능 구현
 *  5. 기본 저장 처리 확인
 *  
 *  [담당자 작업해야할 내용]
 *  1. 접근 권한 처리 확인 및 테스트 필요.
 *  2. 공지여부 / 게시기간에 따른 목록 조회 조건 적용해야함.
 *  3. 목록 및 상세화면의 세부항목이 설계서와 일치하는지 확인 후 보충 작업해야함.
 *  4. 등록만 구현되어 있으므로 수정 기능은 등록기능을 변형하여 구현할 것.
 *  5. 그외 삭제 등의 기능이 있는 경우 구현 필요.
 *  4. 그외 버그가 있을 경우 수정 필요.
 */
@Controller
@SuppressWarnings({"all"})
public class BbsDataController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;

	@Resource(name = "fileManager")
	private FileManager fileManager;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private BbsDataValidator bbsDataValidator;
	
	// 자료실 구분코드
	private static final String BBS_CODE = CodeConst.BBS_DATA;

	/**
	 * 정보서비스 - 자료실 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listData.do")
	public String listData(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		model.addAttribute("model", bbsVO);
		
		return "usr/inform/bbs/listData";
	}

	/**
	 * 정보서비스 - 자료실 등록/수정 모달팝업화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/modalDataForm.do")
	public String modalData(ModelMap model, HttpServletRequest request, @RequestBody BbsVO bbsVO) throws Exception {
		
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
		
		return "usr/inform/bbs/modalDataForm";
	}

	/**
	 * 정보서비스 - 자료실 상세조회 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/viewData.do")
	public String viewData(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {

		BbsVO data = bbsService.viewBbs(bbsVO);
		
		setGlobalSessionVO(request, data);
		bbsService.updtBbsInqCnt(bbsVO);
		
		if (data == null)
			throw new BusinessException(message.getMessage("exception.notKey"));
		
		model.addAttribute("model" , data);
		model.addAttribute("search", bbsVO);

		return "usr/inform/bbs/viewData";
	}

    /**
     * 정보서비스 - 자료실 조회JSON 반환
     */
    @RequestMapping("/usr/inform/bbs/getData.do")
    @ResponseBody
    public Map getData(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute BbsVO bbsVO) throws Exception {
    	
    	setGlobalSessionVO(request, bbsVO);
        
    	BbsVO data = bbsService.viewBbs(bbsVO);

    	return getSuccess(data);
    }

	/**
	 * 정보서비스 - 자료실 목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListData.do")
	@ResponseBody
	public Map getListData(ModelMap model, 
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
	 * 정보서비스 - 자료실 저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/usr/inform/bbs/saveData.do")
	@ResponseBody
	public Map saveData(@ModelAttribute BbsVO bbsVO
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
		// Context
		bbsVO.setGsContext(request.getContextPath());
    }
}
