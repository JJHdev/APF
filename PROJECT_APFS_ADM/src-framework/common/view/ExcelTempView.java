package common.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import business.com.CommConst;
import common.user.UserInfo;
import common.util.CommUtils;
import common.util.FileUtils;
import common.util.properties.ApplicationProperty;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * Program Name 	: ExcelTempView
 * Description 		:
 * Programmer Name 	: ntarget
 * Creation Date 	: 2021-02-08
 * Used Table 		:
 */

@SuppressWarnings({"rawtypes"})
public class ExcelTempView extends AbstractView {
    /** Logger for this class */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    // 20200812 변경 : 경로를 context기반으로 /WEB-INF/부터 명시하게 함.
    // 이전 경로 KEY : "excel.templete.dir"
	public static final String PATH_PROP_KEY = "excel.templete.servletContextDir";
	// 템플릿파일명칭 KEY
	public static final String TEMPLATE_KEY  = "templateName";
	// 다운로드파일명칭 KEY
	public static final String DOWNLOAD_KEY  = "downloadName";
	// 데이터셋 KEY
	public static final String DATA_KEY      = "excelList";
	// 파라메터 KEY
	public static final String PARAM_KEY     = "paramMap";
	// 템플릿파일명칭 뒷부분
	public static final String TEMPLATE_POST = "_Template";
	// 엑셀파일 확장자
	public static final String EXTENSION     = ".xlsx";
	
	private String _getRandomName(String dir, String name) {
        SecureRandom random = new SecureRandom();
        StringBuffer sb = null;
        sb = new StringBuffer();
        sb.append(String.valueOf(System.currentTimeMillis()));
        sb.append(String.valueOf(random.nextLong()));
        return dir + sb.toString() + "_" + name;
	}
    
    // 세션 사용자정보 가져오기
    private UserInfo getUserInfo(HttpServletRequest request) {
    	return (UserInfo)request.getSession().getAttribute(CommConst.SESS_USER_INFO);
    }

	@Override
    @SuppressWarnings("unchecked")
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    	// 템플릿 경로
        String dir = request.getServletContext().getRealPath("") + File.separator
        		   + ApplicationProperty.get(PATH_PROP_KEY) + File.separator;

        // 다운로드 파일명
        String downName	= (String)model.get(DOWNLOAD_KEY) + EXTENSION;
        // 템플릿 파일명
        String tempName = (String)model.get(TEMPLATE_KEY)+TEMPLATE_POST + EXTENSION;
        // 템플릿 파일명 (물리적 경로 포함)
        String template = dir + tempName;
        // 다운로드 파일명 (물리적 경로 포함)
        String download = _getRandomName(dir, downName);
        
        // 데이터 정의
        Map params = (Map)model.get(PARAM_KEY);
        if (params == null)
        	params = new HashMap<String,Object> ();
        params.put("downDate", CommUtils.getCurrDateTime());
        
        UserInfo userInfo = getUserInfo(request);
        
        if (userInfo != null) {
            params.put("userId"  , userInfo.getUserId());
            params.put("userNm"  , userInfo.getUserNm());
        }
        // 데이터
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(DATA_KEY , model.get(DATA_KEY));
        data.put(PARAM_KEY, params);

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(template, data, download);

        // File Download
        File f = new File(download);

        if (f.exists()) {

        	logger.info("response charset : " + response.getCharacterEncoding());

            response.setContentType("application/x-msdownload");
            // 파일명 인코딩 처리
            FileUtils.setDisposition(downName, request, response);
            
			//response.setHeader("Content-Type", "application/octet-stream");
            //response.setHeader("Content-Disposition", "attachment; filename=" + downName);

            byte[] buffer = new byte[1024];
            BufferedInputStream  ins = new BufferedInputStream(new FileInputStream(f));
            BufferedOutputStream ous = new BufferedOutputStream(response.getOutputStream());

            try {
                int read = 0;
                while (true) {
                    read = ins.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    ous.write(buffer, 0, read);
                }
                ous.close();
                ins.close();
            } catch (IOException e) {
                logger.info("EXCEL DOWNLOAD ERROR : FILE NAME = "+download+" ");
            } finally {
            	
                // Download Excel : File Remove
                FileUtils.deleteFile(download);
                
                if(ous!=null) ous.close();
                if(ins!=null) ins.close();
            }
        }
    }
}
