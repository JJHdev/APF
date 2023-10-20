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
import org.springframework.web.bind.annotation.RequestMapping;
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
 * [컨트롤러클래스] - 게시판 > 1:1문의 Controller
 * 
 * @class   : BbsQNAController
 * @author  : JH
 * @since   : 2023.06.28
 * @version : 1.3
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
public class BbsQNAController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;

	@Resource(name = "fileManager")
	private FileManager fileManager;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private BbsQNAValidator bbsValidator;
	
	// 1:1문의 구분코드
	private static final String BBS_CODE = CodeConst.BBS_QNA;

    /**
     * 고객센터-1:1문의-문의글작성 화면 오픈
     */
    @RequestMapping("/usr/inform/bbs/formQNA.do")
    public String formQNA(@ModelAttribute BbsVO bbsVO, HttpServletRequest request, ModelMap model) throws Exception {
    	
    	setGlobalSessionVO(request, bbsVO);

    	if(CommUtils.isEqual(bbsVO.getIsNext(), "comple")) {
    		bbsVO.setIsNext("comple");
    		model.addAttribute("model", bbsVO);
    		return "usr/inform/bbs/openQNA";
    	}else {
    		model.addAttribute("model", bbsVO);
    		return "usr/inform/bbs/formQNA";
    	}
    }
    
	/**
	 * 고객센터-1:1문의 저장처리 (등록,수정,삭제)
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/usr/inform/bbs/saveQNA.do")
	@ResponseBody
	public Map saveQNA(@ModelAttribute BbsVO bbsVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {
		// 세션정보 정의
		setGlobalSessionVO(request, bbsVO);
		// 등록모드
		bbsVO.setMode(CommConst.INSERT);
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
		// Context
		bbsVO.setGsContext(request.getContextPath());
    }
    /**
	 * 고객센터 - 1:1문의내역 문의유형 코드조회
	 */
	@RequestMapping("/usr/inform/bbs/getComboSysCode.do")
    @ResponseBody
	public List getComboData(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		setGlobalSession(request, bbsVO);
		if (bbsVO == null)
			throw new BusinessException(message.getMessage("exception.notKey"));
		return bbsService.getSysCodeData(bbsVO);
	}
	
}
