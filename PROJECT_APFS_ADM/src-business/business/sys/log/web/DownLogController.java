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

import business.com.CommBizUtils;
import business.sys.log.service.DownLogService;
import business.sys.log.service.LogVO;
import common.base.BaseController;
import common.util.CommUtils;
import common.view.ExcelTempView;

/**
 * [컨트롤러클래스] - 다운로드이력 Controller
 *
 * @class   : DownLogController
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
public class DownLogController extends BaseController {

    @Resource(name="DownLogService")
    protected DownLogService downLogService;

    /**
     * 다운로드이력 관리 화면
     */
    @RequestMapping("/sys/log/listDownLog.do")
    public String listDownLog(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute LogVO logVO) throws Exception {

        setGlobalSession(request, logVO);
        // -------------------- Default Setting End -----------------------//

        model.addAttribute("model", logVO);

        return "adm/sys/log/listDownLog";
    }

    /**
     * 다운로드이력 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/log/getListDownLog.do")
    @ResponseBody
    public Map getListDownLog(HttpServletRequest request, @RequestParam Map<String,String> reqMap
            , @ModelAttribute LogVO logVO, ModelMap model) throws Exception {

        setGlobalSession(request, logVO);
        // -------------------- Default Setting End -----------------------//

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = downLogService.listDownLog(logVO, page, size);
        }
        else {
        	list = downLogService.listDownLog(logVO);
        }
        // Easy UI GRID용 결과값 반환
        return getEasyUIResult(list);
    }

    /**
     * 다운로드 이력 엑셀 다운로드
     */
    @RequestMapping("/sys/log/downDownLogExcel.do")
    public String downDownLogExcel(HttpServletRequest request
            , @ModelAttribute LogVO logVO, ModelMap model) throws Exception {

        Map paramMap = getParameterMap(request, true);

        setGlobalSession(request, logVO);
        logVO.setGsUserNo(logVO.getUserInfo(request).getUserNo());
        logVO.setGsUserNm(logVO.getUserInfo(request).getUserNm());
        // -------------------- Default Setting End -----------------------//

        // 취약계층 신청접수목록
        List list = downLogService.listDownLog(logVO);
        // 생성일자
        paramMap.put("downDate", CommUtils.getCurrDateTime());

        model.addAttribute(ExcelTempView.DATA_KEY    , list);
        model.addAttribute(ExcelTempView.PARAM_KEY   , paramMap);
        model.addAttribute(ExcelTempView.TEMPLATE_KEY, "DownLog");
        model.addAttribute(ExcelTempView.DOWNLOAD_KEY, "다운로드이력_현황");

        return "excelTempView";
    }

    /**
     * 다운로드이력 저장처리
     * 다중건 다운로드이력 등록 추가
     * 문서번호 입력시 파일정보 검색후 다운로드이력 등록 추가
     */
    @RequestMapping("/sys/log/saveDownLog.do")
    @ResponseBody
    public Map saveDownLog(
    		HttpServletRequest request,
    		@RequestBody LogVO logVO
    	) throws Exception {

        setGlobalSession(request, logVO);
        // -------------------- Default Setting End -----------------------//

        logVO.setUserNo     (CommUtils.nvlTrim(logVO.getUserInfo(request).getUserNo(),"guest"));
        logVO.setSrvrNm     (CommBizUtils.getServerName(request));
        logVO.setSysCd      ((String)request.getAttribute("sysCd"));
        logVO.setIpAddr     (request.getRemoteAddr());
        logVO.setProgUrl    (logVO.getProgUrl()); // CommBizUtils.getRequestURL(request)
        logVO.setDownResn   (logVO.getDownResn());

		String result = downLogService.regiDownLog(logVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }

    /**
     * 다운로드이력 다중삭제처리
     */
    @RequestMapping("/sys/log/deltDownLog.do")
    @ResponseBody
    public Map deltDownLog(
    		HttpServletRequest request,
    		@RequestBody LogVO logVO
    	) throws Exception {
        // 다운로드이력를 다중삭제한다.
    	String result = downLogService.deltDownLog(logVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
}
