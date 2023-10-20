package business.usr.inform.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommFormFile;
import business.com.exception.AjaxBusinessException;
import business.usr.inform.service.SprtUldService;
import business.usr.inform.web.validator.SprtUldValidator;
import business.usr.support.service.SprtBizVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;

/**
 * [컨트롤러클래스] - 정보서비스 > 경영체 데이터 업로드 Controller
 * 
 * @class   : SprtUldController
 * @author  : LHB
 * @since   : 2023.07.13
 * @version : 1.1
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class SprtUldController extends BaseController {

	@Resource(name = "fileManager")
	private FileManager fileManager;
	
	@Resource(name="SprtUldService")
    protected SprtUldService sprtUldService;
	
	@Autowired
    protected SprtUldValidator sprtUldValidator;

	/**
	 * 정보서비스 - 경영체 데이터 업로드 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listSprtUld.do")
	public String listSprtUld(ModelMap model, HttpServletRequest request, @ModelAttribute SprtBizVO sprtBizVO) throws Exception {
		setGlobalSessionVO(request, sprtBizVO);

		model.addAttribute("model", sprtBizVO);
		
		return "usr/inform/bbs/listSprtUld";
	}
	
	/**
     * 운영관리-경영체데이터업로드 파일업로드 화면 오픈
     */
    @RequestMapping("/usr/inform/bbs/modalSprtUld.do")
    public String modalSprtUld(ModelMap model, HttpServletRequest request, @RequestBody SprtBizVO sprtBizVO) throws Exception {
        setGlobalSessionVO(request, sprtBizVO);
        
        return "usr/inform/bbs/modalSprtUld";
    }
	
	/**
	 * 정보서비스 - 경영체 데이터 업로드 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListSprtUld.do")
	@ResponseBody
	public Map getListSprtUld(ModelMap model, 
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute SprtBizVO sprtBizVO) throws Exception {
		setGlobalSessionVO(request, sprtBizVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = sprtUldService.listSprtUld(sprtBizVO, page, size);
		} else {
			list = sprtUldService.listSprtUld(sprtBizVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 운영관리-경영체데이터업로드 저장처리
     */
    @RequestMapping("/usr/inform/bbs/saveSprtUld.do")
    @ResponseBody
    public Map saveSprtUld(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute SprtBizVO sprtBizVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, sprtBizVO);
        
        // 입력값 검증
    	sprtUldValidator.validate(sprtBizVO, bindingResult);
        
        // LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save SprtUld Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
        // 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
        
        HashMap result = sprtUldService.saveSprtUld(sprtBizVO, files);
        
        return result;
    }
	
	/**
     * 정보서비스 - 경영체 데이터 업로드 양식 다운로드
     */
    @RequestMapping("/usr/inform/bbs/downloadSprtUld.do")
    public void downloadSprtUld(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        // 다운로드할 양식 파일 정보
    	CommFormFile cf = CommFormFile.SPRT_ULD;
        
    	String title = "";
    	try {
	        FileInfo fileInfo = FileInfo.builder()
								.saveName(cf.getSaveName())
								.fileName(cf.getFileName())
								.fullPath(cf.getFullPath())
								.build();
	        
	        title = fileInfo.getFileName();
	        // 실제 파일 다운로드 처리
	        fileManager.procFileDownload(request, response, fileInfo);
    	} catch (BusinessException be) {
			logger.error("경영체 데이터 양식 다운로드 에러 :: ", be);
			try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			} catch (Exception e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
		} catch (Exception e) {
			logger.error("경영체 데이터 양식 파일 다운로드 에러 :: ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			} catch (Exception e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
		}
    }
    
    
    
    /**
	 * 정보서비스 - 경영체 데이터 업로드 정부지원사업이력 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListSprtBiz.do")
	@ResponseBody
	public Map getListSprtBiz(ModelMap model,
			HttpServletRequest request,
			@RequestParam HashMap params, 
			@ModelAttribute SprtBizVO sprtBizVO) throws Exception {
		setGlobalSessionVO(request, sprtBizVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = sprtUldService.listSprtBiz(sprtBizVO, page, size);
		} else {
			list = sprtUldService.listSprtBiz(sprtBizVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 운영관리-경영체데이터업로드-이력 삭제 처리
     */
    @RequestMapping("/usr/inform/bbs/saveSprtBiz.do")
    @ResponseBody
    public Map saveSprtBiz(ModelMap model,
    		HttpServletRequest request,
    		@ModelAttribute HashMap params,
    		@ModelAttribute SprtBizVO sprtBizVO,
    		BindingResult bindingResult) throws Exception {
    	setGlobalSessionVO(request, sprtBizVO);
    	
    	long[] arrSn = sprtBizVO.getArrSn();
        
        String result = sprtUldService.saveSprtBiz(sprtBizVO, arrSn);
        
        return getSuccess(result);
    }
	
	/**
     * 정보서비스 - 경영체 데이터 업로드 - 업로드 파일 다운로드
     */
    @RequestMapping("/usr/inform/bbs/downloadSprtUldFile.do")
    public void downloadSprtUldFile(HttpServletRequest request, HttpServletResponse response,
    		@ModelAttribute SprtBizVO sprtBizVO) throws Exception {
    	setGlobalSessionVO(request, sprtBizVO);
    	
    	// 다운로드할 파일 정보 조회
		SprtBizVO data = sprtUldService.viewSprtUld(sprtBizVO);
		
		if (data == null) {
			try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			} catch (Exception e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			}
    	    throw new AjaxBusinessException("파일 정보를 조회할 수 없습니다.");
		}
		
		String title = "";
		try {
			// 파일정보객체 변환
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSaveName(data.getStrgFileNm());
			fileInfo.setFileName(data.getFileNm());
			fileInfo.setFilePath(data.getFilePath());
			fileInfo.setFullPath(FileUtils.mergePath(FileDirectory.ENT.getRealDir(), data.getFilePath()));
			
			title = data.getFileNm();
	    	
	        // 실제 파일 다운로드 처리
	        fileManager.procFileDownload(request, response, fileInfo);
		} catch (BusinessException be) {
			logger.error("경영체 데이터 엑셀 파일 다운로드 에러 :: ", be);
			try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			} catch (Exception e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
		} catch (Exception e) {
			logger.error("경영체 데이터 엑셀 파일 다운로드 에러 :: ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			} catch (Exception e1) {
				logger.error("파일 다운로드 중 오류 발생 ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
		}
    }
    
	/**
     * 정보서비스-경영체데이터업로드 업로드실패 화면 오픈
     */
    @RequestMapping("/usr/inform/bbs/modalSprtUldResult.do")
    public String modalSprtUldResult(ModelMap model, @RequestBody HashMap paramMap) throws Exception {
    	model.addAttribute("model", paramMap);
        
        return "usr/inform/bbs/modalSprtUldResult";
    }
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SprtBizVO sprtBizVO) {
    	setGlobalSession(request, sprtBizVO);

		if (sprtBizVO.getUserInfo(request) != null) {
        	sprtBizVO.setGsUserNo  (sprtBizVO.getUserInfo(request).getUserNo  ());
        	sprtBizVO.setGsRoleId  (sprtBizVO.getUserInfo(request).getRoleId  ());
        	sprtBizVO.setGsBzentyNo(sprtBizVO.getUserInfo(request).getBzentyNo());
        }
    }
}
