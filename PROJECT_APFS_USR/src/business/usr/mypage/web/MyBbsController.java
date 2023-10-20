package business.usr.mypage.web;

import java.util.ArrayList;
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
import business.usr.inform.web.validator.BbsQNAValidator;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 마이페이지 - 문의내역 Controller
 * 
 * @class   : MyBbsController
 * @author  : LSH
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class MyBbsController extends BaseController {
    
    @Resource(name = "BbsService")
	private BbsService bbsService;
    
    @Resource(name = "fileManager")
	private FileManager fileManager;
    
    // 데이터 검증용 VALIDATOR
    @Autowired 
    private BbsQNAValidator bbsValidator;
    
    // 공지사항 구분코드
 	private static final String BBS_CODE = CodeConst.BBS_QNA;
    
    /**
     * TODO 마이페이지 - 문의내역 화면 오픈
     */
    @RequestMapping("/usr/mypage/bbs/openBbs.do")
    public String openBbs(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute BbsVO bbsVO) throws Exception {
		
    	setGlobalSessionVO(request, bbsVO);
        model.addAttribute("model", bbsVO);
    	
        return "usr/mypage/bbs/openBbs";
    }
    
    /**
     * 문의내역 - 문의 목록 반환 (GRID)
     */
    @RequestMapping("/usr/mypage/bbs/getListBbs.do")
    @ResponseBody
    public Map getListBbs(ModelMap model, 
    		HttpServletRequest request,
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute BbsVO bbsVO) throws Exception {
    	setGlobalSessionVO(request, bbsVO);
        
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
	 * 문의내역 - 상세조회 정보
	 */
	@RequestMapping("/usr/mypage/bbs/viewBbs.do")
	@ResponseBody
	public List<BbsVO> viewNotice(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);

		BbsVO data = bbsService.viewBbs(bbsVO);
		BbsVO dataAdm = bbsService.viewQnaBbs(bbsVO);
		if (data == null)
			throw new BusinessException(message.getMessage("exception.notKey"));

		List<BbsVO> list = new ArrayList<BbsVO>();
		list.add(data);
		list.add(dataAdm);
		
		return list;
	}
	
	/**
	 * 문의내역 - 1:1문의 등록/수정 모달팝업화면 오픈
	 */
	@RequestMapping("/usr/mypage/bbs/modalBbs.do")
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
		
		return "usr/mypage/bbs/modalBbs";
	}
	
	/**
	 * 마이페이지-1:1문의 저장처리 (등록,수정,삭제)
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/usr/mypage/bbs/saveQNA.do")
	@ResponseBody
	public Map saveQNA(@ModelAttribute BbsVO bbsVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {

		// 세션정보 정의
		setGlobalSessionVO(request, bbsVO);
		
        // 저장할 입력값 검증
		bbsValidator.validate(bbsVO, bindingResult);
    	
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
        	bbsVO.setGsRoleId  (bbsVO.getUserInfo(request).getRoleId  ());
        	bbsVO.setGsBzentyNo(bbsVO.getUserInfo(request).getBzentyNo());
        }
    }
}
