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

import business.adm.file.service.PapeFileService;
import business.adm.file.service.PapeFileVO;
import business.com.CommConst;
import business.com.exception.AjaxBusinessException;
import business.sys.file.service.PapeCodeService;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;

/**
 * [컨트롤러클래스] - 업무첨부파일 Controller
 * 
 * 
 * /adm/file/getListPapeFile.do   : 업무첨부파일 목록조회 JSON 반환
 * /adm/file/downloadPapeFile.do  : 서류양식파일 다운로드
 * 
 * 
 * @class   : PapeFileController
 * @author 	: JH
 * @since 	: 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class PapeFileController extends BaseController {

    @Resource(name="PapeFileService")
    protected PapeFileService papeFileService;
    
	@Resource(name = "PapeCodeService")
	protected PapeCodeService papeCodeService;
    
	@Resource(name = "fileManager")
	protected FileManager fileManager;

	/**
	 * 업무첨부파일 목록조회 JSON 반환
	 */
	@RequestMapping("/adm/file/getListPapeFormFile.do")
	@ResponseBody
	public Map getListBizFile(@ModelAttribute PapeFileVO papeFileVO) throws Exception {
		List list = papeFileService.listPapeFile(papeFileVO);
		return getSuccess("rows", list);
	}

	/**
	 * 업무첨부파일 다운로드
	 */
	@RequestMapping("/adm/file/downloadPapeFormFile.do")
	public void downloadPapeFile(@RequestParam String sn
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

    	// 다운로드할 파일 정보 조회
		PapeFileVO fileVO = _getPapeFile(sn, request);
		// 파일정보객체 변환
		FileInfo fileInfo = fileVO.getFileInfo();
        // 실제 파일 다운로드 처리
        _download(fileInfo, request, response, fileVO.getFileNm());
	}

    private void _download(FileInfo fileInfo
    		, HttpServletRequest request
    		, HttpServletResponse response
    		, String title) throws IOException {
		try {
            // 실제 파일 다운로드 처리
			String saveName =fileInfo.getPapeCd()+'.'+FileUtils.getFileExt(fileInfo.getFileName());
			fileInfo.setSaveName(saveName);
            fileManager.procFileDownload(request, response, fileInfo);
    	}
    	catch(BusinessException be) {
    		logger.error("File Download Error : ", be);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (IOException e) {
				logger.error("IOException :: ", e);
			} catch (Exception e) {
				logger.error("Exception :: ", e);
			}
    	    throw new AjaxBusinessException(be.getMessage());
    	} catch(Exception e) {
    		logger.error("File Download Error : ", e);
    	    try {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}  catch (IOException e1) {
				logger.error("IOException :: ", e1);
			} catch (Exception e1) {
				logger.error("Exception :: ", e1);
			}
    	    
    	    throw new AjaxBusinessException("파일 다운로드 중 오류가 발생하였습니다. ("+title+")");
    	}
    }
    
    /**
     * 파일 상세조회
     */
    private PapeFileVO _getPapeFile(String snStr, HttpServletRequest request) throws Exception {
    	Map params = getParameterMap(request, true);
    	String gsRoleId = (String)params.get("gsRoleId");
    	String gsUserNo = (String)params.get("gsUserNo");
    	
    	// BASE64 DECODE 
    	String dec = new String(Base64.getDecoder().decode(snStr));
    	// 파일번호
    	String dcmntCd = dec;
    	// 파일정보 상세조회
    	PapeFileVO fileVO = papeFileService.viewPapeFile(dcmntCd);
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
    private void setGlobalSessionVO(HttpServletRequest request, PapeFileVO bizFileVO) {
    	
        setGlobalSession(request, bizFileVO);

        if (bizFileVO.getUserInfo(request) != null) {
        	bizFileVO.setGsUserNo  (bizFileVO.getUserInfo(request).getUserNo  ());
        	bizFileVO.setGsRoleId  (bizFileVO.getUserInfo(request).getRoleId  ());
        }
    }
}
