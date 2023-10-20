package business.usr.inform.web;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import business.com.CommFormFile;
import business.com.exception.AjaxBusinessException;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;

/**
 * [컨트롤러클래스] - 정보서비스 > 플랫폼소개 Controller
 * 
 * @class   : IntroController
 * @author  : LSH
 * @since   : 2023.04.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class IntroController extends BaseController {
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
    
    /**
     * 플랫폼 소개 화면 오픈
     */
    @RequestMapping("/usr/inform/intro/openPlatform.do")
    public String openPlatform(HttpServletRequest request, @ModelAttribute HashMap params, ModelMap model) throws Exception {

        setGlobalSession(request, params);

        model.addAttribute("model", params);
    	
        return "usr/inform/intro/openPlatform";
    }
    
    /**
     * 농금원 안내 화면 오픈
     */
    @RequestMapping("/usr/inform/intro/openAPFS.do")
    public String openAPFS(HttpServletRequest request, @ModelAttribute HashMap params, ModelMap model) throws Exception {

        setGlobalSession(request, params);

        model.addAttribute("model", params);
    	
        return "usr/inform/intro/openAPFS";
    }
    
    /**
     * 유관기관 안내 화면 오픈
     */
    @RequestMapping("/usr/inform/intro/openAgency.do")
    public String openAgency(HttpServletRequest request, @ModelAttribute HashMap params, ModelMap model) throws Exception {

        setGlobalSession(request, params);

        model.addAttribute("model", params);
    	
        return "usr/inform/intro/openAgency";
    }
    
    /**
     * ASSIST BI AI 파일 다운로드
     */
    @RequestMapping("/usr/inform/intro/downloadBiAi.do")
    public void downloadBiAi(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 다운로드할 양식 파일 정보
    	CommFormFile cf = CommFormFile.BI_AI;
        
        String title = "";
		try {
			// 파일정보객체 변환
			FileInfo fileInfo = FileInfo.builder()
					.saveName(cf.getSaveName())
					.fileName(cf.getFileName())
					.fullPath(cf.getFullPath())
					.build();
			
			title = fileInfo.getFileName();
	    	
	        // 실제 파일 다운로드 처리
			fileManager.procFileDownload(request, response, fileInfo);
		} catch (BusinessException be) {
			logger.error("BI AI 파일 다운로드 에러 :: ", be);
			try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
		} catch (Exception e) {
			logger.error("BI AI 파일 다운로드 에러 :: ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
		}
    }
    
    /**
     * ASSIST BI PNG 파일 다운로드
     */
    @RequestMapping("/usr/inform/intro/downloadBiPng.do")
    public void downloadBiPng(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 다운로드할 양식 파일 정보
    	CommFormFile cf = CommFormFile.BI_PNG;
    	
    	String title = "";
		try {
			// 파일정보객체 변환
			FileInfo fileInfo = FileInfo.builder()
					.saveName(cf.getSaveName())
					.fileName(cf.getFileName())
					.fullPath(cf.getFullPath())
					.build();
			
			title = fileInfo.getFileName();
	    	
	        // 실제 파일 다운로드 처리
			fileManager.procFileDownload(request, response, fileInfo);
		} catch (BusinessException be) {
			logger.error("BI PNG 파일 다운로드 에러 :: ", be);
			try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
		} catch (Exception e) {
			logger.error("BI PNG 파일 다운로드 에러 :: ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
		}
    }
    
    /**
     * ASSIST BI JPG 파일 다운로드
     */
    @RequestMapping("/usr/inform/intro/downloadBiJpg.do")
    public void downloadBiJpg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 다운로드할 양식 파일 정보
    	CommFormFile cf = CommFormFile.BI_JPG;
    	
    	String title = "";
		try {
			// 파일정보객체 변환
			FileInfo fileInfo = FileInfo.builder()
					.saveName(cf.getSaveName())
					.fileName(cf.getFileName())
					.fullPath(cf.getFullPath())
					.build();
			
			title = fileInfo.getFileName();
	    	
	        // 실제 파일 다운로드 처리
			fileManager.procFileDownload(request, response, fileInfo);
		} catch (BusinessException be) {
			logger.error("BI JPG 파일 다운로드 에러 :: ", be);
			try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
		} catch (Exception e) {
			logger.error("BI JPG 파일 다운로드 에러 :: ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
		}
    }

}
