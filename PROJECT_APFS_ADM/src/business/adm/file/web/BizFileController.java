package business.adm.file.web;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.com.CommConst;
import business.com.exception.AjaxBusinessException;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;

/**
 * [컨트롤러클래스] - 업무첨부파일 Controller
 * 
 * 
 * /adm/file/saveBizFile.do      : 업무첨부파일 저장처리
 * /adm/file/deleteBizFile.do    : 업무첨부파일 단일파일 삭제처리
 * /adm/file/downloadBizFile.do  : 업무첨부파일 다운로드
 * /adm/file/getBizFile.do       : 업무첨부파일 조회JSON 반환
 * /adm/file/getListBizFile.do   : 업무첨부파일 목록조회 JSON 반환
 * /adm/file/getListPape.do      : 서류양식 목록조회 JSON 반환
 * /adm/file/getListPapeGroup.do : 서류그룹 목록조회 JSON 반환
 * /adm/file/uploadBizFile.do    : 서류코드 맵핑이 필요한 업무첨부파일 임시 업로드 (업로드팝업)
 * /adm/file/downloadPapeFile.do : 서류양식파일 다운로드
 * 
 * 
 * @class   : BizFileController
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
public class BizFileController extends BaseController {

    @Resource(name="BizFileService")
    protected BizFileService bizFileService;
    
	@Resource(name = "fileManager")
	protected FileManager fileManager;

	/**
	 * 업무첨부파일 목록조회 JSON 반환
	 */
	@RequestMapping("/adm/file/getListBizFile.do")
	@ResponseBody
	public Map getListBizFile(@ModelAttribute BizFileVO bizFileVO) throws Exception {
		List list = bizFileService.listBizFile(bizFileVO);
		return getSuccess("rows", list);
	}

    /**
     * 업무첨부파일 조회JSON 반환
     */
    @RequestMapping("/adm/file/getBizFile.do")
    @ResponseBody
    public Map getBizFile(@RequestParam String sn
    		, HttpServletRequest request) throws Exception {
    	// 파일 정보 조회
		BizFileVO obj = _getBizFile(sn, request);
        return getSuccess(obj);
    }

	/**
	 * 업무첨부파일 다운로드
	 */
	@RequestMapping("/adm/file/downloadBizFile.do")
	public void downloadBizFile(@RequestParam String sn
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

    	// 다운로드할 파일 정보 조회
		BizFileVO fileVO = _getBizFile(sn, request);
		// 파일정보객체 변환
		FileInfo fileInfo = fileVO.getFileInfo();
        // 실제 파일 다운로드 처리
        _download(fileInfo, request, response, fileVO.getFileNm());
	}

	/**
	 * 업무첨부파일 단일파일 삭제처리
	 */
	@RequestMapping("/adm/file/deleteBizFile.do")
	@ResponseBody
	public Map deleteBizFile(@RequestParam String sn
			, HttpServletRequest request) throws Exception {

		// 삭제대상 파일정보조회
		BizFileVO fileVO = _getBizFile(sn, request);

		// 첨부파일을 삭제한다.
		bizFileService.deltBizFile(fileVO);

		return getSuccess();
	}

    /**
     * 업무첨부파일 저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/file/saveBizFile.do")
    @ResponseBody
    public Map saveBizFile(@ModelAttribute BizFileVO bizFileVO
    		, HttpServletRequest request) throws Exception {

		// 세션정보 정의
    	setGlobalSessionVO(request, bizFileVO);

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
        // 업무첨부파일를 저장한다.
		bizFileService.saveBizFile(bizFileVO, files);
		// 성공결과 반환
		return getSuccess();
    }

    /**
     * 서류양식 목록조회 JSON 반환
     */
    @RequestMapping("/adm/file/getListPape.do")
    @ResponseBody
	public Map getListPape(@ModelAttribute BizFileVO bizFileVO) throws Exception {
		List list = bizFileService.listPape(bizFileVO);
		return getSuccess("rows", list);
	}
    
    /**
     * 서류그룹 목록조회 JSON 반환
     */
    @RequestMapping("/adm/file/getListPapeGroup.do")
    @ResponseBody
	public Map getListPapeGroup(@ModelAttribute BizFileVO bizFileVO) throws Exception {
		List list = bizFileService.listPapeGroup(bizFileVO);
		return getSuccess("rows", list);
	}

    /**
     * 서류코드 맵핑이 필요한 업무첨부파일 임시 업로드 
     * (업로드 팝업에서 호출)
     */
    @RequestMapping("/adm/file/uploadBizFile.do")
    @ResponseBody
    public Map uploadFile(HttpServletRequest request) throws Exception {

    	Map paramMap = getParameterMap(request, true);

	    // 파일을 임시 경로에 업로드 한다.
	    List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
	    BizFileVO obj = null;

	    // 단일 파일로 처리
	    if (files != null &&
	    	files.size() == 1) {

	    	FileInfo fileInfo = files.get(0);
	    	fileInfo.setGsUserNo((String)paramMap.get("gsUserNo"));

		    // 업로드한 파일정보를 저장한다.
		    obj = bizFileService.saveTempFile(files.get(0));
	    }
	    return getSuccess("File", obj);
    }


    /**
     * 서류양식파일 다운로드
     */
    @RequestMapping("/adm/file/downloadPapeFile.do")
    public void downloadPapeFile(@RequestParam String dcmntCd
    		, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {

    	// BASE64 DECODE 
    	String decCd = new String(Base64.getDecoder().decode(dcmntCd));

        // 다운로드할 파일 정보 조회
    	BizFileVO pape = bizFileService.viewPape(decCd);

        // 파일문서타입
        FileDirectory d = FileDirectory.FORMFILE;

        FileInfo fileInfo = FileInfo.builder()
							.saveName(pape.getFileNm())
							.fileName(pape.getFileNm())
							.filePath(pape.getFilePath())
							.fullPath(d.getRealPath(pape.getFilePath()))
							.build();
        
        // 실제 파일 다운로드 처리
        _download(fileInfo, request, response, pape.getFileNm());
    }
    
    /**
     * 2023.09.21 JH 서류양식파일 다운로드(수정)
     */
    @RequestMapping("/adm/file/downloadPapeForm.do")
    public void downloadPapeForm(@RequestParam String dcmntCd
    		, HttpServletRequest request
    		, HttpServletResponse response) throws Exception {
    	
    	// BASE64 DECODE 
    	String decCd = new String(Base64.getDecoder().decode(dcmntCd));
    	
    	// 다운로드할 파일 정보 조회
    	BizFileVO pape = bizFileService.viewPape(decCd);
    	
    	// 파일문서타입
    	FileDirectory d = FileDirectory.FORMFILE;
        // 양식파일 확장자 읽기
        String fileExt  = FileUtils.getFileExt(pape.getFileNm());
        // 양식파일 저장명칭 생성
        String saveName = pape.getDcmntCd()+"."+fileExt;
    	
        FileInfo fileInfo = FileInfo.builder()
							.saveName(saveName)
							.fileName(pape.getFileNm())
							.fullPath(d.getRealPath())
							.build();
    	
    	// 실제 파일 다운로드 처리
    	_download(fileInfo, request, response, pape.getFileNm());
    }
    
    private void _download(FileInfo fileInfo
    		, HttpServletRequest request
    		, HttpServletResponse response
    		, String title) throws IOException {
		try {
            // 실제 파일 다운로드 처리
            fileManager.procFileDownload(request, response, fileInfo);
    	}
    	catch(BusinessException be) {
    		logger.error("File Download Error : ", be);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			}
    	    throw new AjaxBusinessException(be.getMessage());
    	}
    	catch(Exception e) {
    		logger.error("File Download Error : ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("IOException :: ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
    	}
    }
    
    /**
     * 파일 상세조회
     */
    private BizFileVO _getBizFile(String snStr, HttpServletRequest request) throws Exception {
    	
    	Map params = getParameterMap(request, true);
    	String gsRoleId = (String)params.get("gsRoleId");
    	String gsUserNo = (String)params.get("gsUserNo");
    	
    	// BASE64 DECODE 
    	String dec = new String(Base64.getDecoder().decode(snStr));
    	// 파일번호
    	Long sn = CommUtils.getLong(dec, 0);
    	// 파일정보 상세조회
    	BizFileVO fileVO = bizFileService.viewBizFile(sn);
    	if (fileVO == null)
    		throw new BusinessException(message.getMessage("exception.notExistAttachFile"));

    	// 세션사용자 정의
    	fileVO.setGsUserNo(gsUserNo);

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
    private void setGlobalSessionVO(HttpServletRequest request, BizFileVO bizFileVO) {
    	
        setGlobalSession(request, bizFileVO);

        if (bizFileVO.getUserInfo(request) != null) {
        	bizFileVO.setGsUserNo  (bizFileVO.getUserInfo(request).getUserNo  ());
        	bizFileVO.setGsRoleId  (bizFileVO.getUserInfo(request).getRoleId  ());
        }
    }
}
