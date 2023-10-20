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

import business.adm.file.service.BbsFileService;
import business.adm.file.service.BbsFileVO;
import business.com.CommConst;
import business.com.exception.AjaxBusinessException;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판첨부파일 Controller
 * 
 * @class   : BbsFileController
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
public class BbsFileController extends BaseController {

    @Resource(name="BbsFileService")
    protected BbsFileService bbsFileService;
    
	@Resource(name = "fileManager")
	protected FileManager fileManager;

	/**
	 * 게시글 공통첨부파일 목록조회 JSON 반환
	 */
	@RequestMapping("/adm/file/getListBbsFile.do")
	@ResponseBody
	public Map getListBbsFile(@ModelAttribute BbsFileVO bbsFileVO) throws Exception {
		List list = bbsFileService.listBbsFile(bbsFileVO);
		return getSuccess("rows", list);
	}

    /**
     * 게시판첨부파일 조회JSON 반환
     */
    @RequestMapping("/adm/file/getBbsFile.do")
    @ResponseBody
    public Map getBbsFile(@RequestParam String sn
    		, HttpServletRequest request) throws Exception {
        // 파일정보조회
    	BbsFileVO obj = _getBbsFile(sn, request, false);
        return getSuccess(obj);
    }

	/**
	 * 게시판첨부파일 다운로드
	 */
	@RequestMapping("/adm/file/downloadBbsFile.do")
	public void downloadBbsFile(@RequestParam String sn
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

    	String title = "";
		try {
    		// 다운로드할 파일 정보 조회
    		BbsFileVO fileVO = _getBbsFile(sn, request, false);
    		// 파일정보객체 변환
    		FileInfo fileInfo = fileVO.getFileInfo();
    		// 파일명
    		title = fileVO.getFileNm();
            // 실제 파일 다운로드 처리
            fileManager.procFileDownload(request, response, fileInfo);
    	} catch(BusinessException be) {
    		logger.error("File Download Error : ", be);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e) {
				logger.error("downloadBbsFile Error :: ", e);
			} catch (Exception e) {
				logger.error("downloadBbsFile Error :: ", e);
			}
    	    throw new AjaxBusinessException(be.getMessage());
    	} catch(Exception e) {
    		logger.error("File Download Error : ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e1) {
				logger.error("downloadBbsFile Error :: ", e1);
			} catch (Exception e1) {
				logger.error("downloadBbsFile Error :: ", e1);
			}
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
    	}
	}

	/**
	 * 게시판첨부파일 단일파일 삭제처리
	 */
	@RequestMapping("/adm/file/deleteBbsFile.do")
	@ResponseBody
	public Map deleteBbsFile(@RequestParam String sn
			, HttpServletRequest request) throws Exception {

    	// 삭제대상 파일 정보 조회
		BbsFileVO fileVO = _getBbsFile(sn, request, true);

		// 첨부파일을 삭제한다.
		bbsFileService.deltBbsFile(fileVO);

		return getSuccess();
	}

    /**
     * 게시판첨부파일 저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/file/saveBbsFile.do")
    @ResponseBody
    public Map saveBbsFile(@ModelAttribute BbsFileVO bbsFileVO
    		, HttpServletRequest request) throws Exception {

		// 세션정보 정의
    	setGlobalSessionVO(request, bbsFileVO);

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
        // 게시판첨부파일를 저장한다.
		bbsFileService.saveBbsFile(bbsFileVO, files);
		// 성공결과 반환
		return getSuccess();
    }
    
    /**
     * 파일 상세조회
     */
    private BbsFileVO _getBbsFile(String snStr, HttpServletRequest request, boolean isWrite) throws Exception {
    	
    	Map params = getParameterMap(request, true);
    	String gsRoleId = (String)params.get("gsRoleId");
    	String gsUserNo = (String)params.get("gsUserNo");
    	
    	// BASE64 DECODE 
    	String dec = new String(Base64.getDecoder().decode(snStr));
    	// 파일번호
    	Long sn = CommUtils.getLong(dec, 0);
    	// 파일정보 상세조회
    	BbsFileVO fileVO = bbsFileService.viewBbsFile(sn);
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
    private void setGlobalSessionVO(HttpServletRequest request, BbsFileVO bbsFileVO) {
    	
        setGlobalSession(request, bbsFileVO);

        if (bbsFileVO.getUserInfo(request) != null) {
        	bbsFileVO.setGsUserNo  (bbsFileVO.getUserInfo(request).getUserNo  ());
        	bbsFileVO.setGsRoleId  (bbsFileVO.getUserInfo(request).getRoleId  ());
        }
    }
}
