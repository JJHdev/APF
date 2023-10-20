package business.usr.file.web;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.CommFormFile;
import business.com.exception.AjaxBusinessException;
import business.usr.file.service.EntFileService;
import business.usr.file.service.EntFileVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 업체첨부파일 Controller
 * 
 * @class   : EntFileController
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class EntFileController extends BaseController {

    @Resource(name="EntFileService")
    protected EntFileService entFileService;
    
	@Resource(name = "fileManager")
	protected FileManager fileManager;

	/**
	 * 업체첨부파일 목록조회 JSON 반환
	 */
	@RequestMapping("/usr/file/getListEntFile.do")
	@ResponseBody
	public Map getListEntFile(@ModelAttribute EntFileVO entFileVO) throws Exception {
		List list = entFileService.listEntFile(entFileVO);
		return getSuccess("rows", list);
	}

    /**
     * 업체첨부파일 조회JSON 반환
     */
    @RequestMapping("/usr/file/getEntFile.do")
    @ResponseBody
    public Map getEntFile(@RequestParam String sn
    		, HttpServletRequest request) throws Exception {
        // 파일정보조회
        EntFileVO obj = _getEntFile(sn, request, false);
        return getSuccess(obj);
    }

	/**
	 * 업체첨부파일 다운로드
	 */
	@RequestMapping("/usr/file/downloadEntFile.do")
	public void downloadEntFile(@RequestParam String sn
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

    	String title = "";
		try {
			// 다운로드할 파일 정보 조회
			EntFileVO fileVO = _getEntFile(sn, request, false);
			// 파일정보객체 변환
			FileInfo fileInfo = fileVO.getFileInfo();
    		// 파일명
    		title = fileVO.getFileNm();
            // 실제 파일 다운로드 처리
            fileManager.procFileDownload(request, response, fileInfo);
    	}
    	catch(BusinessException be) {
    		logger.error("File Download Error : ", be);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("error :: ", e1);
			} catch (Exception e1) {
				logger.error("error :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
    	}
    	catch(Exception e) {
    		logger.error("File Download Error : ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("error :: ", e1);
			} catch (Exception e1) {
				logger.error("error :: ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
    	}
	}

	/**
	 * 업체첨부파일 단일파일 삭제처리
	 */
	@RequestMapping("/usr/file/deleteEntFile.do")
	@ResponseBody
	public Map deleteEntFile(@RequestParam String sn
			, HttpServletRequest request) throws Exception {

		// 삭제대상 파일 정보 조회
    	EntFileVO fileVO = _getEntFile(sn, request, true);

		// 첨부파일을 삭제한다.
		entFileService.deltEntFile(fileVO);

		return getSuccess();
	}

    /**
     * 업체첨부파일 저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/usr/file/saveEntFile.do")
    @ResponseBody
    public Map saveEntFile(@ModelAttribute EntFileVO entFileVO
    		, HttpServletRequest request) throws Exception {

		// 세션정보 정의
    	setGlobalSessionVO(request, entFileVO);

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
        // 업체첨부파일를 저장한다.
		entFileService.saveEntFile(entFileVO, entFileVO.getDocuCds(), files);
		// 성공결과 반환
		return getSuccess();
    }
    
    /**
     * 문서/미디어 URL링크보기
     */
    @RequestMapping(value="/usr/file/linkEntFile.do")
    @ResponseBody
    public HttpEntity<byte[]> linkFile(HttpServletRequest request,
    		HttpServletResponse response,
    		@RequestParam String sn) throws Exception {
		// 다운로드할 파일 정보 조회
    	EntFileVO data = _getEntFile(sn, request, false);
		// 파일정보객체 변환
		FileInfo fileInfo = data.getFileInfo();
        // 미디어 URL 링크용 HttpEntity 반환
    	return fileManager.linkFile(fileInfo , request, response);
    }

    /**
     * 2023.07.29 LSH
     * 업체관련 양식파일 다운로드
     * - 사업계획서
     * - 위임장
     */
    @RequestMapping("/usr/file/downloadEntFormFile.do")
    public void downloadEntFormFile(@RequestParam String fileType,
    		HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {

    	String title = "";
		try {
	        // 다운로드할 양식 파일 정보
	    	CommFormFile cf = CommFormFile.get("ENT_FILE_"+fileType);
	    	if (cf == null)
	    		throw new BusinessException("다운로드할 양식파일이 없습니다.");
    		// 파일명
    		title = cf.getFileName();
	        
	        FileInfo fileInfo = FileInfo.builder()
								.saveName(cf.getSaveName())
								.fileName(cf.getFileName())
								.fullPath(cf.getFullPath())
								.build();
            // 실제 파일 다운로드 처리
            fileManager.procFileDownload(request, response, fileInfo);
    	}
    	catch(BusinessException be) {
    		logger.error("Form File Download Error : ", be);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("error :: ", e1);
			} catch (Exception e1) {
				logger.error("error :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
    	}
    	catch(Exception e) {
    		logger.error("FormFile Download Error : ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("error :: ", e1);
			} catch (Exception e1) {
				logger.error("error :: ", e1);
			}
    	    throw new AjaxBusinessException("양식 파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
    	}
    }
    
    /**
     * 2023.08.02 JH
     * 농금원 양식파일 다운로드
     * - 운영기준
     */
    @RequestMapping("/usr/file/downloadFormFile.do")
    public void downloadFormFile(@RequestParam String fileType,
    		HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {
    	String title = "";
		try {
	        // 다운로드할 양식 파일 정보
	    	CommFormFile cf = CommFormFile.get("FORM_FILE_"+fileType);
	    	if (cf == null)
	    		throw new BusinessException("다운로드할 양식파일이 없습니다.");
    		// 파일명
    		title = cf.getFileName();
	        
	        FileInfo fileInfo = FileInfo.builder()
								.saveName(cf.getSaveName())
								.fileName(cf.getFileName())
								.fullPath(cf.getFullPath())
								.build();
            // 실제 파일 다운로드 처리
            fileManager.procFileDownload(request, response, fileInfo);
    	}
    	catch(BusinessException be) {
    		logger.error("Form File Download Error : ", be);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("error :: ", e1);
			} catch (Exception e1) {
				logger.error("error :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
    	}
    	catch(Exception e) {
    		logger.error("FormFile Download Error : ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("error :: ", e1);
			} catch (Exception e1) {
				logger.error("error :: ", e1);
			}
    	    throw new AjaxBusinessException("양식 파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
    	}
    }
    
    /**
     * 파일 상세조회
     */
    private EntFileVO _getEntFile(String snStr, HttpServletRequest request, boolean isWrite) throws Exception {
    	
    	Map params = getParameterMap(request, true);
    	String gsRoleId = (String)params.get("gsRoleId");
    	String gsUserNo = (String)params.get("gsUserNo");
    	
    	// BASE64 DECODE 
    	String dec = new String(Base64.getDecoder().decode(snStr));
    	// 파일번호
    	Long sn = CommUtils.getLong(dec, 0);
    	// 파일정보 상세조회
    	EntFileVO fileVO = entFileService.viewEntFile(sn);
    	if (fileVO == null)
    		throw new BusinessException(message.getMessage("exception.notExistAttachFile"));

    	// 세션사용자 정의
    	fileVO.setGsUserNo(gsUserNo);
    	
    	// 단순조회인 경우 OK
    	if (!isWrite)
    		return fileVO;

    	// 관리자인 경우 ACCESS OK
    	if (CommConst.isAdminRole(gsRoleId)) {
    		return fileVO;
    	}
    	// 관리자가 아닌 경우 본인인지 체크
    	else {
    		// 파일 생성자이면 ACCESS OK
    		if (CommUtils.isEqual(gsUserNo, fileVO.getRgtrNo()))
    			return fileVO;
    	}
    	// 그외엔 NOT ACCESS
		throw new BusinessException(message.getMessage("error.file.notAccess"));
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, EntFileVO entFileVO) {
    	
        setGlobalSession(request, entFileVO);

        if (entFileVO.getUserInfo(request) != null) {
        	entFileVO.setGsUserNo  (entFileVO.getUserInfo(request).getUserNo  ());
        	entFileVO.setGsRoleId  (entFileVO.getUserInfo(request).getRoleId  ());
        }
    }
}
