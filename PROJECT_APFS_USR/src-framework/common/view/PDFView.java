package common.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import commf.exception.BusinessException;
import common.util.FileUtils;
import common.util.properties.ApplicationProperty;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

/**
 * Program Name 	: PDFView
 * Description 		: XDocReport 라이브러리 사용, Open Document Template을 참고하여 PDF를 생성하는 Viewer
 * Programmer Name 	: LSH
 * Creation Date 	: 2023.03.10
 */

@SuppressWarnings({"rawtypes"})
public class PDFView extends AbstractView {
    /** Logger for this class */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String PATH_PROP_KEY = "pdf.template.servletContextDir";
	public static final String TEMPLATE_KEY  = "templateName"; // 템플릿명칭 KEY
	public static final String DOWNLOAD_KEY  = "downloadName"; // 다운로드명칭 KEY
	public static final String MODEL_KEY     = "dataModel";    // 데이터모델 KEY

	@Override
    protected void renderMergedOutputModel(Map map, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
		
		if (map.get(MODEL_KEY) == null)
			throw new BusinessException("["+MODEL_KEY+"]이 정의되지 않았습니다.");
		if (!(map.get(MODEL_KEY) instanceof PDFModel))
			throw new BusinessException("["+MODEL_KEY+"]객체는 common.view.PDFModel로 정의되어야 합니다.");

    	// 템플릿 경로
        String dir = request.getServletContext().getRealPath("") + File.separator
        		   + ApplicationProperty.get(PATH_PROP_KEY) + File.separator;

        // 다운로드 파일명
        String downName	= (String)map.get(DOWNLOAD_KEY) + ".pdf";
        // 템플릿 파일명
        String tmplName = (String)map.get(TEMPLATE_KEY) + ".odt";
        
        PDFModel model = (PDFModel)map.get(MODEL_KEY);
        
        // 템플릿 파일명 (물리적 경로 포함)
        String template = dir + tmplName;
        
    	BufferedInputStream  is = null;
    	BufferedOutputStream os = null;
        
        try {
        	logger.info("response charset : " + response.getCharacterEncoding());

        	response.setContentType("application/x-msdownload");
            // 파일명 인코딩
            FileUtils.setDisposition(downName, request, response);

	        // 템플릿파일 읽기
	        is = new BufferedInputStream(new FileInputStream(template));
            // Prepare the IXDocReport instance based on the template, 
	        // using Freemarker template engine
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(is, TemplateEngineKind.Freemarker);

            // Add properties to the context
            IContext context = report.createContext();
            // instruct XDocReport to inspect data class entity as well
            // which is given as list and iterated in a table
            FieldsMetadata metadata = report.createFieldsMetadata();
            
            context.putMap(model.getData());
            
            if (model.getLists() != null) {
            	model.getLists().forEach(
            		(k, v) -> {
            			if (v.size() > 0) {
                			context.put((String)k, v);
                			try {
								metadata.load((String)k, v.get(0).getClass(), true);
							} 
                			catch (XDocReportException e) {
								throw new RuntimeException(e);
							}
            			}
            		}
            	);
            }
            if (model.getImages() != null) {
            	model.getImages().forEach(
            		(k, v) -> {
            			context.put((String)k, v);
            			metadata.addFieldAsImage((String)k);
            		}
            	);
            }

            // Define what we want to do (PDF file from ODF template)
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);
            // Write the PDF file to output stream
            report.convert(context, options, response.getOutputStream());
        } 
        catch (Exception ex) {
            logger.info("PDF DOWNLOAD ERROR : FILE NAME = "+downName+" ");
            throw new RuntimeException(ex);
        } 
        finally {
            try {
            	if (is!=null) 
                	is.close();
            } catch(Exception ex) {}
            try {
            	if (os!=null) 
                	os.close();
            } catch(Exception ex) {}
        }
    }
}
