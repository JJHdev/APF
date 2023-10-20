package business.com.common.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.common.service.MainService;
import business.com.common.service.MainVO;
import business.usr.inform.service.BbsVO;
import common.base.BaseController;

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
public class MainController extends BaseController {

	@Autowired
    protected MainService mainService;

    @RequestMapping("/usr/main/main.do")
    public String main(HttpServletRequest request, ModelMap model, @ModelAttribute MainVO mainVO) throws Exception {

        //Map paramMap = getParameterMap(request, true);
        
        return "usr/main/main";
    }
    
    /**
     * 메인화면 배너 표출
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/usr/main/listBanner.do")
    @ResponseBody
    public List listBanner(MainVO mainVO, ModelMap model) throws Exception {
    	List list = mainService.listBanner(mainVO);
    	return list;
    }
    
    /**
	 *  메인화면 공지사항 팝업창(layer) 
	 */
	 @SuppressWarnings("rawtypes")
	 @RequestMapping("/usr/main/popupNotice.do") 
	 public String popupNotice(BbsVO bbsVO, ModelMap model) throws Exception {
	  	 List popupData = mainService.listPopupNotice(bbsVO);
		 model.addAttribute("lstPopup", popupData); 
		 return "usr/main/popupNotice"; 
	 }

    /**
     * 2023.08.07 LSH 메일보내기 샘플
     */
    @RequestMapping("/usr/main/sample.do")
    public String sample() throws Exception {
        return "usr/main/sample";
    }
}
