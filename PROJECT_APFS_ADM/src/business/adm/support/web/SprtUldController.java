package business.adm.support.web;

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

import business.adm.support.service.SprtUldService;
import business.adm.support.service.SprtBizVO;
import business.com.CommConst;
import business.com.CommFormFile;
import business.com.exception.AjaxBusinessException;
import business.com.exception.ModalBusinessException;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;

/**
 * [컨트롤러클래스] - 운영관리-경영체데이터업로드 Controller
 * 
 * @class   : SprtBizController
 * @author  : LHB
 * @since   : 2023.07.06
 * @version : 1.0
 *
 *   수정일       수정자             수정내용
 *  --------   --------    ---------------------------
 * 23.07.06      LHB            First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class SprtUldController extends BaseController {
	
	@Resource(name="SprtUldService")
    protected SprtUldService sprtUldService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	@Autowired
    protected SprtUldValidator sprtUldValidator;

	/**
     * 운영관리-경영체데이터업로드 데이터 업로드 화면 오픈
     */
    @RequestMapping("/adm/support/biz/openSprtUld.do")
    public String openSprtUld(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute SprtBizVO sprtBizVO) throws Exception {
    	setGlobalSessionVO(request, sprtBizVO);
        
        return "adm/support/biz/openSprtUld";
    }
    
    /**
     * 운영관리-경영체데이터업로드 파일업로드 화면 오픈
     */
    @RequestMapping("/adm/support/biz/modalSprtUld.do")
    public String modalSprtUld(ModelMap model, HttpServletRequest request, @RequestBody SprtBizVO sprtBizVO) throws Exception {
        setGlobalSessionVO(request, sprtBizVO);
        
        return "adm/support/biz/modalSprtUld";
    }
    
    /**
     * 운영관리-경영체데이터업로드 상세보기 화면 오픈
     */
    @RequestMapping("/adm/support/biz/modalSprtUldForm.do")
    public String modalSprtUldForm(ModelMap model, HttpServletRequest request, @RequestParam HashMap params, @RequestBody SprtBizVO sprtBizVO) throws Exception {
        setGlobalSessionVO(request, sprtBizVO);
        
        SprtBizVO data = sprtUldService.viewSprtUld(sprtBizVO);
        if (data == null) {
        	throw new ModalBusinessException(message.getMessage("exception.notKey"));
        }
        
        model.addAttribute("model", data);
        
        return "adm/support/biz/modalSprtUldForm";
    }
    
    /**
     * 운영관리-경영체데이터업로드 저장처리
     */
    @RequestMapping("/adm/support/biz/saveSprtUld.do")
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
	 * 운영관리-경영체데이터업로드 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/support/biz/getListSprtUld.do")
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
	 * 운영관리-경영체데이터업로드-경영체 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/support/biz/getListSprtBiz.do")
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
     * 운영관리-경영체데이터업로드-지원사업이력 삭제 처리
     */
    @RequestMapping("/adm/support/biz/saveSprtBiz.do")
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
    @RequestMapping("/adm/support/biz/downloadSprtUldFile.do")
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
     * 운영관리-경영체데이터업로드 양식 다운로드
     */
    @RequestMapping("/adm/support/biz/downloadSprtUld.do")
    public void downloadSprtUld(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        // 다운로드할 양식 파일 정보
    	CommFormFile cf = CommFormFile.SPRT_ULD;
        
        FileInfo fileInfo = FileInfo.builder()
							.saveName(cf.getSaveName())
							.fileName(cf.getFileName())
							.fullPath(cf.getFullPath())
							.build();
        // 실제 파일 다운로드 처리
        fileManager.procFileDownload(request, response, fileInfo);
    }
    
    /**
     * 운영관리-경영체데이터업로드 업로드실패 화면 오픈
     */
    @RequestMapping("/adm/support/biz/modalSprtUldResult.do")
    public String modalSprtUldResult(ModelMap model, @RequestBody HashMap paramMap) throws Exception {
    	model.addAttribute("model", paramMap);
        
        return "adm/support/biz/modalSprtUldResult";
    }
	
	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SprtBizVO sprtBizVO) {
    	
        setGlobalSession(request, sprtBizVO);

        if (sprtBizVO.getUserInfo(request) != null) {
        	sprtBizVO.setGsUserNo(sprtBizVO.getUserInfo(request).getUserNo());
        	sprtBizVO.setGsRoleId(sprtBizVO.getUserInfo(request).getRoleId());
        }
    }

}