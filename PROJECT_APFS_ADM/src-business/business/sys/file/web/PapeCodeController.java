package business.sys.file.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.code.service.CodeVO;
import business.sys.file.service.PapeCodeService;
import business.sys.file.service.PapeCodeVO;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.properties.ApplicationProperty;
import egovframework.com.utl.sim.service.EgovFileScrty;

/**
 * [컨트롤러클래스] - 서류코드관리 Controller
 *
 * @class : PapeCodeController
 * @author : JH
 * @since : 2023.08.04
 * @version : 1.2
 *
 *          수정일 수정자 수정내용 -------- -------- ---------------------------
 *
 */
@Controller
@SuppressWarnings({ "all" })
@RequestMapping("/adm")
public class PapeCodeController extends BaseController {

	@Resource(name = "PapeCodeService")
	protected PapeCodeService papeCodeService;

	@Resource(name = "fileManager")
	FileManager fileManager;
	
    @Resource(name="BizFileService")
    protected BizFileService bizFileService;

	/**
	 * 서류코드관리 화면 오픈
	 */
	@RequestMapping("/sys/file/listPapeCode.do")
	public String listPapeCode(HttpServletRequest request, ModelMap model, @ModelAttribute PapeCodeVO papeCodeVO)
			throws Exception {

		setGlobalSession(request, papeCodeVO);
		// -------------------- Default Setting End -----------------------//
		model.addAttribute("model", papeCodeVO);
		return "adm/sys/file/listPapeCode";
	}

	/**
	 * 서류코드관리 계층형 목록JSON 반환 (EasyUI GRID)
	 */
	@RequestMapping("/sys/file/getListPapeCode.do")
	@ResponseBody
	public Map getListPapeCode(HttpServletRequest request, @RequestParam Map<String, String> reqMap,
			@ModelAttribute PapeCodeVO papeCodeVO, ModelMap model) throws Exception {

		setGlobalSession(request, papeCodeVO);
		// -------------------- Default Setting End -----------------------//
		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = papeCodeService.listPapeCode(papeCodeVO,page,size);
		} else {
			list = papeCodeService.listPapeCode(papeCodeVO);
		}
		// Easy UI GRID용 결과값 반환
		return getPaginatedResult(list);
	}

	/**
	 * 서류코드관리 조회JSON 반환
	 */
	@RequestMapping("/sys/file/modalPapeCodeForm.do")
	public String modalPapeCodeForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody PapeCodeVO papeCodeVO) throws Exception {
		setGlobalSession(request, papeCodeVO);
		
		if (CommUtils.isNotEmpty(papeCodeVO.getDcmntCd())) {
			PapeCodeVO obj = papeCodeService.viewPapeCode(papeCodeVO);
       	if (obj == null) {
       		throw new ModalBusinessException(message.getMessage("exception.notKey"));
       	}
       	// 수정모드
       	obj.setMode(CommConst.UPDATE);
       	model.addAttribute("model",	obj);
       } else {
       	// 등록모드
	    papeCodeVO.setMode(CommConst.INSERT);
       	model.addAttribute("model", papeCodeVO);
       }
		return "adm/sys/file/modalPapeCodeForm";
	}

	/**
	 * 서류코드관리 저장처리 (등록,수정,삭제) mode 값에 따라 분기
	 */
	@RequestMapping("/sys/file/savePapeCode.do")
	@ResponseBody
	public Map savePapeCode(HttpServletRequest request, @ModelAttribute PapeCodeVO papeCodeVO) throws Exception {

		setGlobalSession(request, papeCodeVO);
		String mode = papeCodeVO.getMode();
		if (papeCodeVO.getUserInfo(request) != null)
			papeCodeVO.setGsUserNo(papeCodeVO.getUserInfo(request).getUserNo());
		
        // 다중파일을 임시경로에 업로드한다.
        List<FileInfo> files = null;
        try {
    		files = fileManager.multiFileUploadByPapeName(request, null);
        } catch (NullPointerException e) {
        	logger.error("NullPointerException e :: ", e);
        } catch (Exception e) {
        	logger.error("Exception e :: ", e);
        }
        
		// 서류코드관리를 저장한다.
		String result = papeCodeService.savePapeCode(papeCodeVO,files);
		// 성공결과 반환
		return getSuccess("Message", result);
	}
	
	private void deletePapeForm () {
	
	};

}
