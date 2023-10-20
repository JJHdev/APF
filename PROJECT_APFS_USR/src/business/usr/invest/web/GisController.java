package business.usr.invest.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import business.com.CommConst;
import business.usr.invest.service.GisService;
import business.usr.invest.service.GisVO;
import common.base.BaseController;
import common.util.WebUtil;
import common.util.properties.ApplicationProperty;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [컨트롤러클래스] - GIS Controller
 * 
 * @class   : GisController
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.12      LHB               First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class GisController extends BaseController {
	
	@Autowired
	protected GisService gisService;
    
	private String geoserverProtocol	= ApplicationProperty.get("geoserver.protocol" );
	private String geoserverHost		= ApplicationProperty.get("geoserver.host"     );
	private String geoserverUsername	= ApplicationProperty.get("geoserver.username" );
	private String geoserverPassword	= ApplicationProperty.get("geoserver.password" );
	private String geoserverWorkspace	= ApplicationProperty.get("geoserver.workspace");
	
    /**
     * GIS 화면 오픈
     */
    @RequestMapping("/usr/gis/gis/openGIS.do")
    public String openGIS(@ModelAttribute HashMap params,
    		HttpServletRequest request,
    		@ModelAttribute GisVO gisVO,
    		ModelMap model) throws Exception {
    	setGlobalSessionVO(request, gisVO);
    	
    	gisVO.setService("WFS");
    	gisVO.setVersion("1.0.0");
    	gisVO.setReqType("GetFeature");
    	gisVO.setLayerNm("getListEnt");
    	gisVO.setMaxFeatures(50);
    	gisVO.setOutputFormat("application/json");
    	
    	// GIS 화면구분을 위한 변수
        model.addAttribute("gsScreen", CommConst.GIS);
        
        String OAUTH_KAKAO_CLIENT_JS_ID = ApplicationProperty.get("OAUTH.KAKAO.CLIENT_JS_ID");
        model.addAttribute("OAUTH_KAKAO_CLIENT_JS_ID", OAUTH_KAKAO_CLIENT_JS_ID);
        
        return "usr/gis/gis/openGIS";
    }
    
    @ResponseBody
	@RequestMapping(value="/usr/gis/gis/gisProxy.do")
    public void gisProxy(@RequestParam(value="_encode", required=false, defaultValue="UTF-8") String encode,
        HttpServletRequest request, HttpServletResponse response, @ModelAttribute HashMap params) throws Exception {
 
        String url = geoserverProtocol + geoserverHost + "/geoserver/APFS/wms";
        WebUtil.getProxy(url, encode, request, response);
    }
    
    
    /**
     * GIS 화면 오픈
     */
    /*
    @RequestMapping("/usr/gis/gis/getListLayerInfo.do")
    @ResponseBody
    public Map getListLayerInfo(@ModelAttribute HashMap params,
    		@ModelAttribute GisVO gisVO,
    		ModelMap model) throws Exception {
    	setGlobalSessionVO(gisVO);
    	
    	gisVO.setService("WFS");
    	gisVO.setVersion("1.0.0");
    	gisVO.setReqType("GetFeature");
    	gisVO.setLayerNm("getListEnt");
    	gisVO.setMaxFeatures(50);
    	gisVO.setOutputFormat("application/json");

    	JSONObject result = gisService.getLayer(gisVO);
    	if(result == null) {
    		throw new ModalBusinessException("존재하지 않는 정보입니다.");
    	}
    	
        return getSuccess("Data", result);
    }
    */
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, GisVO gisVO) {
        setGlobalSession(request, gisVO);

        if (gisVO.getUserInfo(request) != null) {
        	gisVO.setGsUserNo(gisVO.getUserInfo(request).getUserNo());
        	gisVO.setGsRoleId(gisVO.getUserInfo(request).getRoleId());
        }
    }
    
    @RequestMapping(value = "/usr/gis/gis/ajax/getChartData.do", method = RequestMethod.GET)
	public ModelAndView selectChartData(HttpServletRequest req, ModelAndView mv) throws Exception {
		mv.setViewName("jsonView");
		
		EgovMap params = this.paramMap(req);
		List<EgovMap> chartDataList = new ArrayList<EgovMap>();
		
		try {
			if(params.get("chartFlag").equals("mgmt")) {//경영체현황조회
				chartDataList = this.gisService.selectMgmtChartDataList(params);
			}
			else {//자본규모현황조회
				chartDataList = this.gisService.selectScaleChartDataList(params);
			}
			mv.addObject("chartDataList", chartDataList);
		} catch (NullPointerException e) {
			logger.error("selectChartData Error :: ", e);
		} catch (Exception e) {
			logger.error("selectChartData Error :: ", e);
		}
		
		return mv;
	}
    
    private EgovMap paramMap(HttpServletRequest request){
		EgovMap paramMap = new EgovMap();
		Set keySet = request.getParameterMap().keySet();
		Iterator<?> iter = keySet.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			paramMap.put(key, request.getParameter(key));
			logger.info("request.getParameter(\""+key+"\", \""+request.getParameter(key)+"\")");
		}
		return paramMap;
	}
}
