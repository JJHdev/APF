package business.usr.inform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [서비스인터페이스] - survey Service Interface
 * 
 * @class   : GisService
 * @author  : SUK
 * @since   : 2023.07.18
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.13      LHB               First Coding.
 */
@SuppressWarnings("all")
public interface SurveyService {
	
	List<EgovMap> selectSurveyList(EgovMap params);
	
	//List<EgovMap> selectSurveyImgList(List<EgovMap> surveyList);
	
	List<EgovMap> selectSurveyInfoList(EgovMap params);
	
	List<EgovMap> selectSurveyInfoItemList(EgovMap params);
	
	List<Integer> insertSurveyData(EgovMap params);
	
	EgovMap selectSurveyAttendCnt(EgovMap params);
	
	EgovMap selectSurveyPopHeadInfo(EgovMap params);
	
    // 23.09.14 JH 페이지네이션 추가작업 
	public PaginatedArrayList selectSurveyListPagnation(SurveyVO surveyVO, int page, int size) throws Exception;
	public List selectSurveyListPagnation(SurveyVO surveyVO) throws Exception;
	
}