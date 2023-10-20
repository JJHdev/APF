package business.com.common.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.common.service.MainService;
import business.com.common.service.MainVO;
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

    @RequestMapping("/adm/main/main.do")
    public String main(HttpServletRequest request, ModelMap model, @ModelAttribute MainVO mainVO) throws Exception {

        //Map paramMap = getParameterMap(request, true);
    	MainVO data = mainService.listToDoCount(mainVO);
    	MainVO cnt = mainService.listNumCount(mainVO);

    	model.addAttribute("model", data);
    	model.addAttribute("cnt", cnt);
    	
        return "adm/main/main";
    }
    
    /**
     * 대시보드 - 사용자 유형별 접속 통계
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/adm/main/getListAccess.do")
    @ResponseBody
    public Map getListAccess(ModelMap model, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute MainVO mainVO) throws Exception {
        
    	List list = mainService.listAcs(mainVO);
    	
		// 일반 GRID용 결과값 반환
		return getSuccess(list);
    }

    /**
     * 2023.07.06 LSH 차트 샘픔
     * 
     * Chartjs 활용 
     */
    @RequestMapping("/adm/main/sample.do")
    public String sample() throws Exception {
        return "adm/main/sample";
    }
}
