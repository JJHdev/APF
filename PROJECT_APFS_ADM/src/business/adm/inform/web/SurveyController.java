package business.adm.inform.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import business.adm.inform.service.SurveyService;
import business.adm.inform.service.SurveyVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.file.FileManager;
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
public class SurveyController extends BaseController {
	
	@Resource(name = "SurveyService")
	protected SurveyService surveyService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	// 데이터 검증용 VALIDATOR	
	@Autowired
	private SurveyValidator srvyValidator;
	
    /**
     * 설문관리 - 설문관리 화면 오픈
     */
    @RequestMapping("/adm/inform/survey/openSurvey.do")
    public String openSurvey(ModelMap model, HttpServletRequest request, @ModelAttribute SurveyVO surveyVO) throws Exception {
        setGlobalSessionVO(request, surveyVO);
        model.addAttribute("model", surveyVO);
        return "adm/inform/survey/openSurvey";
    }

    /**
     * 설문관리 - 설문문항관리 화면 오픈
     */
    @RequestMapping("/adm/inform/survey/openQuestion.do")
    public String openQuestion(ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/inform/survey/openQuestion";
    }

    /**
     * 설문관리 - 설문항목관리 화면 오픈
     */
    @RequestMapping("/adm/inform/survey/openItem.do")
    public String openItem(ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/inform/survey/openItem";
    }

    /**
     * 설문관리 - 설문결과관리 화면 오픈
     */
    @RequestMapping("/adm/inform/survey/openAnswer.do")
    public String openAnswer(ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/inform/survey/openAnswer";
    }
    
    /**
	 * 설문관리 - 등록/수정 모달팝업화면 오픈
	 */
	@RequestMapping("adm/inform/survey/modalSurveyForm.do")
	public String modalSurveyForm(ModelMap model,
			HttpServletRequest request, 
			@RequestBody SurveyVO surveyVO) throws Exception {
		setGlobalSessionVO(request, surveyVO);
		if (CommUtils.isNotEmpty(surveyVO.getSrvyNo())) {
			SurveyVO data = surveyService.viewSurvey(surveyVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model",    data);
        } else {
        	// 등록모드
        	surveyVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", surveyVO);
        }
		
		return "adm/inform/survey/modalSurveyForm";
	}
	
	/**
	 * 설문관리 - 상세 모달팝업화면 오픈
	 */
	@RequestMapping("adm/inform/survey/modalSurveyView.do")
	public String modalSurveyView(ModelMap model, HttpServletRequest request, @RequestBody SurveyVO surveyVO) throws Exception {
		setGlobalSessionVO(request, surveyVO);
		
		SurveyVO data = surveyService.viewSurvey(surveyVO);
		List listQstnn = surveyService.listSurveyQstnn(surveyVO);
		List listQitem = surveyService.listSurveyQitem(surveyVO);
		
		model.addAttribute("model", data);
		model.addAttribute("listQstnn", listQstnn);
		model.addAttribute("listQitem", listQitem);
		
		return "adm/inform/survey/modalSurveyView";
	}
	
	/**
	 * 설문결과관리 - 상세 모달팝업화면 오픈
	 */
	@RequestMapping("adm/inform/survey/modalSurveyAnswer.do")
	public ModelAndView modalSurveyAnswer(ModelMap model, HttpServletRequest request, @RequestBody SurveyVO surveyVO, ModelAndView mv) throws Exception {
		mv.setViewName("adm/inform/survey/modalSurveyAnswer");
		setGlobalSessionVO(request, surveyVO);
		
		SurveyVO data = surveyService.viewSurvey(surveyVO);
		List listQstnn = surveyService.listSurveyQstnn(surveyVO);
		List listQitem = surveyService.listSurveyQitem(surveyVO);
		List<EgovMap> surveyAnswerList = surveyService.selectSurveyAnswer(surveyVO);
		EgovMap answerCnt = surveyService.selectSurveyAnswerCnt(surveyVO);
		
		List<EgovMap> surveyAnswerListText = new ArrayList<EgovMap>();
		List<EgovMap> surveyAnswerListNomal = new ArrayList<EgovMap>();
		
		int k=0;
		for(int i=0; i<surveyAnswerList.size(); i++) {
			if("QT3".equals(surveyAnswerList.get(i).get("qitemType"))) {
				surveyAnswerListText.add(surveyAnswerList.get(i));
			}
			else {
				surveyAnswerListNomal.add(surveyAnswerList.get(i));
				k++;
			}
		}
		String[] qitemArrayCopy = new String[k]; 
		for(int i=0; i<surveyAnswerListNomal.size(); i++) {
			qitemArrayCopy[i] = String.valueOf(surveyAnswerListNomal.get(i).get("qitemNo"));
		}
		for (int i = 0; i < surveyAnswerListNomal.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (qitemArrayCopy[i].equals(String.valueOf(surveyAnswerListNomal.get(j).get("qitemNo")))) {  // 중복 검사
                	surveyAnswerListNomal.remove(j);
                }
            }
        }
	
		JSONArray jsonArray = new JSONArray();
		for(int i=0; i<surveyAnswerList.size(); i++){
		    JSONObject json = new JSONObject();
		    json.put("artclCn", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("artclCn"))));
		    json.put("userNo", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("userNo"))));
		    json.put("srvyNo", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("srvyNo"))));
		    json.put("qitemNo", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("qitemNo"))));
		    json.put("qitemType", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("qitemType"))));
		    json.put("artclNo", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("artclNo"))));
		    json.put("count", changeNullToZero(String.valueOf(surveyAnswerList.get(i).get("count"))));
		    jsonArray.add(i, json);
		}
		
		mv.addObject("model", data);
		mv.addObject("listQstnn", listQstnn);
		mv.addObject("surveyAnswerList", surveyAnswerList);
		mv.addObject("answerCnt", answerCnt);
		mv.addObject("jsonAnswerList", jsonArray);
		mv.addObject("surveyAnswerListNomal", surveyAnswerListNomal);
		mv.addObject("surveyAnswerListText", surveyAnswerListText);
		
		return mv;
	}
	
	/**
	 * 게시판 공통저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/adm/inform/survey/saveSurvey.do")
	@ResponseBody
	public Map saveSurvey(@ModelAttribute SurveyVO surveyVO
			, HttpServletRequest request
			, BindingResult bindingResult) throws Exception {
		// 세션정보 정의
		setGlobalSessionVO(request, surveyVO);
		
        // 저장할 입력값 검증
		srvyValidator.validate(surveyVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("survey Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
		// 게시판를 저장한다.
		String result = surveyService.saveSurvey(surveyVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}
    
    /**
	 * 설문관리 공통목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/inform/survey/getListSurvey.do")
	@ResponseBody
	public Map getListSurvey(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute SurveyVO surveyVO) throws Exception {

		setGlobalSession(request, surveyVO);
		// -------------------- Default Setting End -----------------------//
		
		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = (List)surveyService.listSurvey(surveyVO, page, size);
		} else {
			list = surveyService.listSurvey(surveyVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	
	/**
	 * 설문관리 상세정보 반환 
	 */
	@RequestMapping("/adm/inform/survey/getListSurveyInfo.do")
	@ResponseBody
	public Map getListSurveyInfo(ModelMap model,
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap,
			@ModelAttribute SurveyVO surveyVO) throws Exception {
		
		setGlobalSessionVO(request, surveyVO);
		// --------------------- Default Setting End ----------------------//
		
		List listQitem = surveyService.listSurveyQitem(surveyVO);
		
		return getSuccess(listQitem); 
	}
    
	/**
     * 세션정보를 모델의 변수에 바인딩한다 
     * 설문관리 - SurveyVO
     * 
     */
    private void setGlobalSessionVO(HttpServletRequest request, SurveyVO surveyVO) {
    	
    	setGlobalSession(request, surveyVO);
    	if (surveyVO.getUserInfo(request) != null) {
			surveyVO.setGsUserNo(surveyVO.getUserInfo(request).getUserNo());
			surveyVO.setGsUserNm(surveyVO.getUserInfo(request).getUserNm());
			surveyVO.setGsRoleId(surveyVO.getUserInfo(request).getRoleId());
		}
    }
    
    /** req 변환을 위한 메소드
     * 
     * @param request
     * @return
     */
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
    
    private String changeNullToZero(String str) {
    	if(str == null || str.equals("")) {
    		str = "0";
    	}
    	return str;
    }
}
