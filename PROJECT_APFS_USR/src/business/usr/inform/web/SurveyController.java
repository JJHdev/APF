package business.usr.inform.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import business.usr.inform.service.SurveyService;
import business.usr.inform.service.SurveyVO;
import common.base.BaseController;
import common.util.CommUtils;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [컨트롤러클래스] - 설문정보 Controller
 * 
 * @class   : SurveyController
 * @author  : LSH
 * @since   : 2023.04.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SurveyController extends BaseController {
	

	@Resource(name = "SurveyService")
	private SurveyService surveyService;

    /**
     * 설문정보 화면 오픈
     */
    @RequestMapping("/usr/inform/survey/listSurvey.do")
    public ModelAndView listSurvey(ModelAndView mv, ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params, SurveyVO surveyVO) throws Exception {
    	mv.setViewName("usr/inform/survey/listSurvey");
    	
    	setGlobalSessionVO(request, surveyVO);
        mv.addObject("model", params);
    	
        return mv;
    }
    
    @RequestMapping("/usr/inform/survey/getListSurvey.do")
	@ResponseBody
	public Map getListNotice(ModelMap model, HttpServletRequest request, @RequestParam Map<String, String> reqMap, SurveyVO surveyVO) throws Exception {
		
		setGlobalSessionVO(request, surveyVO);
		// -------------------- Default Setting End -----------------------//
		EgovMap params = new EgovMap();
		String trgtCode = this.setSurveyTrgtCode(surveyVO);
		params.put("trgtCode", trgtCode);
		params.put("gsUserNo", surveyVO.getGsUserNo());
		List<EgovMap> list = new ArrayList<EgovMap>();
		if (reqMap.containsKey("page")) {
			params.put("page", CommUtils.getInt(reqMap, "page"));
			params.put("size", CommUtils.getInt(reqMap, "rows"));
			list = surveyService.selectSurveyList(params);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
    
    
    // 23.09.14 JH 페이지네이션 추가작업 
    @RequestMapping("/usr/inform/survey/getListSurveyPagenation.do")
	@ResponseBody
	public Map getListSurvey(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap,
			@ModelAttribute  SurveyVO surveyVO) throws Exception {
		
		setGlobalSessionVO(request, surveyVO);
		// -------------------- Default Setting End -----------------------//
		surveyVO.setTrgtCode(this.setSurveyTrgtCode(surveyVO));
		surveyVO.setGsUserNo(surveyVO.getGsUserNo());
		
		List<EgovMap> list = new ArrayList<EgovMap>();
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = surveyService.selectSurveyListPagnation(surveyVO, page, size);
		}else {
			list = surveyService.selectSurveyListPagnation(surveyVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
    
    @RequestMapping(value = "/usr/inform/survey/ajax/getSurveyInfo.do", method = RequestMethod.GET)
	public ModelAndView selectChartData(HttpServletRequest req, ModelAndView mv) throws Exception {
		mv.setViewName("jsonView");
		
		EgovMap params = this.paramMap(req);
		List<EgovMap> surveyInfoList = new ArrayList<EgovMap>();
		List<EgovMap> surveyInfoItemList = new ArrayList<EgovMap>();
		
		try {
			surveyInfoList = this.surveyService.selectSurveyInfoList(params);		
			for(int i=0; i<surveyInfoList.size(); i++) {
				//if(!surveyInfoList.get(i).get("qitemType").equals("QT3")) {//인풋박스가 아닐때
					List<EgovMap> items = this.surveyService.selectSurveyInfoItemList(surveyInfoList.get(i));
					for(int j=0; j<items.size(); j++) {
						surveyInfoItemList.add(items.get(j));
					//}
				}
			}
			EgovMap attendCnt = this.surveyService.selectSurveyAttendCnt(params);
			EgovMap popHeadInfo = this.surveyService.selectSurveyPopHeadInfo(params);
			mv.addObject("attendCnt", attendCnt);
			mv.addObject("popHeadInfo", popHeadInfo);
			mv.addObject("surveyInfoList", surveyInfoList);
			mv.addObject("surveyInfoItemList", surveyInfoItemList);
		} catch (NullPointerException e) {
			logger.error("selectChartData Error :: ", e);
		} catch(Exception e) {
			logger.error("selectChartData Error :: ", e);
		}
		
		return mv;
	}

    @RequestMapping(value = "/usr/inform/survey/ajax/saveSurvey.do", method = RequestMethod.GET)
    @ResponseBody
	public String insertSurveyData(HttpServletRequest req, ModelAndView mv, @ModelAttribute SurveyVO surveyVO) throws Exception {
		
		setGlobalSessionVO(req, surveyVO);
		EgovMap params = new EgovMap();
		String msg = "";
		params.put("req", req);
		params.put("userNo", surveyVO.getUserInfo(req).getUserNo());
		int plag = 0;
		try {
			List<Integer> ckArrList = this.surveyService.insertSurveyData(params);
			
			for(int i=0; i<ckArrList.size(); i++) {
				if(ckArrList.get(i).equals(0)) {
					plag = 1;
				}
			}
		} catch (NullPointerException e) {
			logger.error("insertSurveyData Error :: ", e);
		} catch(Exception e) {
			logger.error("insertSurveyData Error :: ", e);
		}		
		
		if(plag==1) {
			msg = "false";
		}
		else {
			msg = "true";
		}
		
		return msg;
	}
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SurveyVO surveyVO) {
        setGlobalSession(request, surveyVO);

        if (surveyVO.getUserInfo(request) != null) {
        	surveyVO.setGsUserNo(surveyVO.getUserInfo(request).getUserNo());
        	surveyVO.setGsRoleId(surveyVO.getUserInfo(request).getRoleId());
        }
    }
    
    private String setSurveyTrgtCode(SurveyVO surveyVO) {
    	String code = "";
    	if(surveyVO.getGsRoleId().equals("ROLE_USR_EIV")) {
    		code = "QT1";
		}
    	else if(surveyVO.getGsRoleId().equals("ROLE_USR_EBZ")) {
    		code = "QT2";
    	}
    	else if(surveyVO.getGsRoleId().equals("ROLE_USR_USR")) {
    		code = "QT3";
    	}
    	
    	return code;
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
