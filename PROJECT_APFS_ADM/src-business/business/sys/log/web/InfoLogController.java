package business.sys.log.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.sys.log.service.InfoLogService;
import business.sys.log.service.LogVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러클래스] - 정보조회이력 관리 Controller
 * 
 * @class   : InfoLogController
 * @author  : LSH
 * @since   : 2021.11.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
@RequestMapping("/adm")
public class InfoLogController extends BaseController {

    @Resource(name="InfoLogService")
    protected InfoLogService infoLogService;
    
    /**
     * 정보조회이력 관리 화면
     */
    @RequestMapping("/sys/log/listInfoLog.do")
    public String listInfoLog(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute LogVO logVO) throws Exception {
				
        setGlobalSession(request, logVO);
        // -------------------- Default Setting End -----------------------//

        model.addAttribute("model", logVO);
    	
        return "adm/sys/log/listInfoLog";
    }

    /**
     * 정보조회이력 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/log/getListInfoLog.do")
    @ResponseBody
    public Map getListInfoLog(HttpServletRequest request, @RequestParam Map<String,String> reqMap
            , @ModelAttribute LogVO logVO, ModelMap model) throws Exception {

        setGlobalSession(request, logVO);
        // -------------------- Default Setting End -----------------------//

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = infoLogService.listInfoLog(logVO, page, size);
        }
        else {
        	list = infoLogService.listInfoLog(logVO);
        }
        // Easy UI GRID용 결과값 반환
        return getEasyUIResult(list);
    }

    /**
     * 정보조회이력 저장처리 (다중삭제)
     */
    @RequestMapping("/sys/log/saveInfoLog.do")
    @ResponseBody
    public Map saveInfoLog(
    		HttpServletRequest request, 
    		@RequestBody LogVO logVO
    	) throws Exception {
        // 정보조회이력를 다중삭제한다.
    	String result = infoLogService.deltInfoLog(logVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
    
	 /**
     * 접속이력 엑셀 다운로드
     */
    @RequestMapping("/sys/log/downInfoLogExcel.do")
    public String downInfoLogExcel(HttpServletRequest request
            , @ModelAttribute LogVO logVO
            , ModelMap model) throws Exception {
    	
    	Map paramMap = getParameterMap(request, true);

        setGlobalSession(request, logVO);
        logVO.setGsUserNo(logVO.getUserInfo(request).getUserNo());
        logVO.setGsUserNm(logVO.getUserInfo(request).getUserNm());
        // -------------------- Default Setting End -----------------------//

        List list = infoLogService.listInfoLog(logVO);
        // 생성일자
        paramMap.put("downDate", CommUtils.getCurrDateTime());
		
		model.addAttribute(ExcelTempView.DATA_KEY    , list);
		model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
		model.addAttribute(ExcelTempView.TEMPLATE_KEY, "InfoLog");
		model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "개인정보접속이력_현황");

		return "excelTempView";
    }
}
