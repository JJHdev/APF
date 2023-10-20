package business.com.common.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import business.com.CommConst;
import business.com.common.service.SampleVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.util.FileUtils;
import common.view.PDFModel;
import common.view.PDFView;
import fr.opensagres.xdocreport.document.images.FileImageProvider;

/**
 * [컨트롤러클래스] - 메인 컨트롤러
 *
 * @class   : MainController
 * @author  : ntarget
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
public class SampleController extends BaseController {

    /**
     * 2023.03.13 LSH
     * PDF 샘플파일 다운로드 예제
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/usr/sample/downSamplePDF.do")
    public String downSamplePDF(HttpServletRequest request
            , ModelMap model) throws Exception {
    	
    	Map<String,Object> paramMap = getParameterMap(request, true);
        // 생성일자
        paramMap.put("downDate", CommUtils.getCurrDateTime());
        
        // 이미지 물리적 경로
        String imagePath = CommConst.getWebRootDir(request)+File.separator+"images"+File.separator+"sample";

        PDFModel dataModel = PDFModel.builder().imagePath(imagePath).build();

        List<SampleVO> list = new ArrayList<SampleVO> ();
        for (int i = 0; i < 30; i++) {
        	list.add(SampleVO.builder()
    			.userId("test"        +i)
    			.userNo(String.valueOf(i))
    			.userNm("테스터"      +i)
    			.photo(new FileImageProvider(new File(FileUtils.mergePath(imagePath, "test.png"))))
    			.build()
        	);
        }

        dataModel.setData (paramMap);
        dataModel.putList ("dataList", list);
        dataModel.putImage("logo1", "logo.png");

		model.addAttribute(PDFView.MODEL_KEY    , dataModel);
		model.addAttribute(PDFView.DOWNLOAD_KEY , (String)paramMap.get("downloadName"));
		model.addAttribute(PDFView.TEMPLATE_KEY , (String)paramMap.get("templateName"));

		return "PDFView";
    }
    
}
