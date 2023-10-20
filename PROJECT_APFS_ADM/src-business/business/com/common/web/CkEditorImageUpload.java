package business.com.common.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import business.com.CommConst;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;
import common.util.properties.AppProperties;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;

/**
 *  [컨트롤러클래스] - CkEditorImageUpload Controller
 *
 * @class   : CkEditorImageUpload (upload, view)
 * @author  :
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
public class CkEditorImageUpload extends BaseController {
	
	/** 암호화서비스 */
	@Resource(name = "egovEnvCryptoService")
	EgovEnvCryptoService cryptoService;	
	
    @Resource(name="fileManager")
    protected FileManager fileManager;

	private static final String CKIMAGE_REAL_DIR 		= "upload.real.dir";
	private static final String CKIMAGE_SUB_DIR 		= FileDirectory.CKIMAGE.getPath();
	private static final String CKIMAGE_TYPE_ALLOW 		= "file.img.allow.exts";
	
	private static final String SEPERATOR 				= File.separator;
	private static final int 	BUFFER_SIZE 			= 8192;
	
    /**
     * CkImage Upload.
     *
     * CkImageSaver (egov CKIMAGE 저장처리 참조)
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/com/common/ckupload.do")
    public String ckupload(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	    	
    	String ckimageRealDir 		= (String)AppProperties.get(CKIMAGE_REAL_DIR);
    	String ckimageSubDir 		= CKIMAGE_SUB_DIR;
    	String ckimageTypeAllow 	= (String)AppProperties.get(CKIMAGE_TYPE_ALLOW);

    	String sudDir		= ckimageSubDir + "/" + CommUtils.getCurDateString() +"/";
    	String saveDir		= ckimageRealDir + sudDir;
    	
    	PrintWriter out = null;
    	
		try {
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	        Map<String, MultipartFile> files = multipartRequest.getFileMap();
	        
	        if (files.size() > 0) {
	        	//디렉토리 생성
	        	FileUtils.makeDirectories(saveDir);
	        }
	        
	        MultipartFile inFile 	= files.get("upload");
	
	        // 실제 파일명칭
	        String realName = FileUtils.convertInvalidFileName(CommUtils.nvlTrim(inFile.getOriginalFilename()));
	        
	    	// 저장할 파일명칭
	        String saveName 	= fileManager.getFileName(saveDir, realName);
	        String fileExt		= FileUtils.getFileExt(saveName);
	        String contentType	= inFile.getContentType();
	        
	    	if (CommUtils.isEmpty(saveName))
	    		return null;
	    	
            // 파일을 폴더에 저장함
            FileUtils.copyFile(inFile.getInputStream(), new FileOutputStream(saveDir + saveName));
            
	    	String[] arrCkimageTypeAllow = null;
	
	    	if (CommUtils.isNotEmpty(ckimageTypeAllow)) {
				arrCkimageTypeAllow = StringUtils.split(ckimageTypeAllow, ",");
			}
			
	    	if (request.getContentType() == null || request.getContentType().indexOf("multipart") == -1) {
				logger.info("ckimage ContentType is not multipart...");
			} else {
				String errorMessage = null;
				String relUrl = null;
				
				if (isAllowFileType(arrCkimageTypeAllow, realName)) {
					relUrl = request.getContextPath()
							+ CommConst.CKEDITOR_IMAGE_URL + "?"		
						    + "path=" + this.encrypt(sudDir)
						    + "&physical=" + this.encrypt(saveName)
						    + "&contentType=" + this.encrypt(contentType);
					
				} else {
					errorMessage = "Restricted Image Format";
				}
	
				String funcNo 	= EgovWebUtil.clearXSSMaximum(request.getParameter("CKEditorFuncNum"));
				StringBuffer sb = new StringBuffer();
				
				sb.append("<script type=\"text/javascript\">\n");
				sb.append("window.parent.CKEDITOR.tools.callFunction(").append(funcNo).append(", '");
				sb.append(relUrl);
	
				if (errorMessage != null) {
					sb.append("', '").append(errorMessage);
				}
				sb.append("');\n </script>");
					
				response.setContentType("text/html");
				response.setHeader("Cache-Control", "no-cache");
				
				out = response.getWriter();
				out.print(sb.toString());
				out.flush();
				out.close();
	        }
		} catch (IOException e) {
			logger.error("IOException :: ", e);
		} catch (Exception e) {
			logger.error("" + e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		
    	return null;
    }
	
    /**
     * CkImage View.
     *
     *	EgovFormBasedFileUtil.viewFile (egov 파일미리보기 참조)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value=CommConst.CKEDITOR_IMAGE_URL, method=RequestMethod.GET)
    public void ckImageSrc(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String uploadDir 		= (String)AppProperties.get(CKIMAGE_REAL_DIR);
    	String extWhiteList 	= (String)AppProperties.get(CKIMAGE_TYPE_ALLOW);
    	
    	String subPath 		= this.decrypt(CommUtils.nvlTrim(request.getParameter("path")));
    	String physical 	= this.decrypt(CommUtils.nvlTrim(request.getParameter("physical")));
    	String mimeType 	= this.decrypt(CommUtils.nvlTrim(request.getParameter("contentType")));

		if (subPath.indexOf("..") >= 0 ) 
			throw new EgovBizException("### Security Exception - illegal url called.");
		if (physical.indexOf("..") >= 0 ) 
			throw new EgovBizException("### Security Exception - illegal url called.");
		
		String ext = "";
		if ( physical.lastIndexOf(".") > 0 )
			ext = physical.substring(physical.lastIndexOf(".") + 1, physical.length()).toLowerCase();
		
		if ( ext == null ) 
			throw new FileNotFoundException();
		
		if ( extWhiteList.indexOf(ext) >= 0 )
			viewFile(response, uploadDir, subPath, physical, mimeType);
		else
			throw new FileNotFoundException();
    }
    
	private void viewFile(HttpServletResponse response
			, String where, String serverSubPath, String physicalName, String mimeTypeParam) throws Exception {
		
		String mimeType 	= mimeTypeParam;
		//String downFileName = where + SEPERATOR + serverSubPath + SEPERATOR + physicalName;
		String downFileName = where + serverSubPath + physicalName;

		File file = new File(EgovWebUtil.filePathBlackList(downFileName));

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFFER_SIZE];

		if (mimeType == null) {
			mimeType = "application/octet-stream;";
		}

		response.setContentType(EgovWebUtil.removeCRLF(mimeType));
		response.setHeader("Content-Disposition", "filename=image;");

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin 	= new BufferedInputStream(new FileInputStream(file));
			outs 	= new BufferedOutputStream(response.getOutputStream());

			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} finally {
			EgovResourceCloseHelper.close(outs, fin);
		}
	}
	
    /**
     * 암호화
     *
     * @param encrypt
     */
    private String encrypt(String encrypt) {
    	try {
			return cryptoService.encrypt(encrypt); // Handles URLEncoding.
			//return cryptoService.encryptNone(encrypt); // Does not handle URLEncoding.
        } catch(IllegalArgumentException e) {
    		logger.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
        } catch (Exception e) {
        	logger.error("[" + e.getClass() +"] :" + e.getMessage());
        }
		return encrypt;
    }
    
    /**
     * 복호화
     *
     * @param decrypt
     */
    private String decrypt(String decrypt){
    	try {
    		//return cryptoService.decrypt(decrypt); // Handles URLDecoding.
    		return cryptoService.decryptNone(decrypt); // Does not handle URLDecoding.
        } catch(IllegalArgumentException e) {
    		logger.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
        } catch (Exception e) {
        	logger.error("[" + e.getClass() +"] :" + e.getMessage());
        }
		return decrypt;
    }	
	
	private boolean isAllowFileType(String[] allowFileTypeArr, String fileName) {
		boolean isAllow = false;
		if (allowFileTypeArr != null && allowFileTypeArr.length > 0) {
			for (String allowFileType : allowFileTypeArr) {
				if (StringUtils.equalsIgnoreCase(allowFileType, StringUtils.substringAfterLast(fileName, "."))) {
					isAllow = true;
					break;
				}
			}
		} else {
			isAllow = true;
		}

		return isAllow;
	}
}