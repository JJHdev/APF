package business.usr.inform.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
import business.usr.inform.service.SurveyService;
import business.usr.inform.service.SurveyVO;
import business.usr.invest.service.FundService;
import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;
import common.util.properties.ApplicationProperty;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.codehaus.jettison.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * [서비스클래스] - Gis Service 구현 클래스
 * 
 * @class   : GiserviceImpl
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.13      LHB              First Coding.
 */

@Service("SurveyService")
@SuppressWarnings({"all"})
public class SurveyServiceImpl extends BaseService implements SurveyService {
	
	@Resource(name = "SurveyDAO")
	private SurveyDAO surveyDAO;
	
	@Override
	public List<EgovMap> selectSurveyList(EgovMap params) {
		List<EgovMap> surveyList = this.surveyDAO.selectSurveyList(params);
		List<EgovMap> particiList = this.surveyDAO.selectParticiSurveyList(params);
		List<EgovMap> list = new ArrayList<EgovMap>();
		
		if(particiList.size() == 0) {
			for(int i=0; i<surveyList.size(); i++) {
				EgovMap param = surveyList.get(i);
				param.put("particiPation", "미참여");
				list.add(param);
			}
		}
		else {
			for(int i=0; i<surveyList.size(); i++) {
				for(int j=0; j<particiList.size(); j++) {
					String srvyNo = particiList.get(j).get("srvyNo").toString();
					if(srvyNo.equals(surveyList.get(i).get("srvyNo"))) {
						EgovMap param = surveyList.get(i);
						param.put("particiPation", "참여완료");
						list.add(param);
						surveyList.remove(i);
					}
				}
			}
			for(int i=0; i<surveyList.size(); i++) {
				EgovMap param = surveyList.get(i);
				param.put("particiPation", "미참여");
				list.add(param);
			}
		}
		return list;
	}
	
	@Override
	public PaginatedArrayList selectSurveyListPagnation(SurveyVO surveyVO, int page, int size) throws Exception {
		return surveyDAO.selectSurveyListPagnation(surveyVO,page,size);
	}
	@Override
	public List selectSurveyListPagnation(SurveyVO surveyVO) throws Exception {
		return surveyDAO.selectSurveyListPagnation(surveyVO);
	}
	
	/*
	@Override
	public List<EgovMap> selectSurveyImgList(List<EgovMap> surveyList) {
		List<EgovMap> surveyImgList = new ArrayList<EgovMap>();
		for(int i=0; i<surveyList.size(); i++) {
			EgovMap params = (EgovMap) surveyList.get(i).get("srvyNo");
			EgovMap SurveyImg = this.surveyDAO.selectSurveyImgList(params);
			surveyImgList.add(i, SurveyImg);
		}
	}*/
	
	@Override
	public List<EgovMap> selectSurveyInfoList(EgovMap params) {
		return this.surveyDAO.selectSurveyInfoList(params);
	}
	
	@Override
	public List<EgovMap> selectSurveyInfoItemList(EgovMap params) {
		return this.surveyDAO.selectSurveyInfoItemList(params);
	}
	
	@Override
	public List<Integer> insertSurveyData(EgovMap params) {
		HttpServletRequest req = (HttpServletRequest) params.get("req");
		String[] radioSrvyArr = req.getParameterValues("radioSrvyArr");
		String[] radioQitmArr = req.getParameterValues("radioQitmArr");
		String[] radioArtclArr = req.getParameterValues("radioArtclArr");
		String[] checkSrvyArr = req.getParameterValues("checkSrvyArr");
		String[] checkQitmArr = req.getParameterValues("checkQitmArr");
		String[] checkArtclArr = req.getParameterValues("checkArtclArr");
		String[] textSrvyArr = req.getParameterValues("textSrvyArr");
		String[] textQitmArr = req.getParameterValues("textQitmArr");
		String[] textArtclArr = req.getParameterValues("textArtclArr");
		String[] textDetailArr = req.getParameterValues("textDetailArr");
		
		List<Integer> ckArrList = new ArrayList<Integer>();
		List<Integer> numericValues = new ArrayList<>();
		
		if(radioSrvyArr != null) {
			for(int i=0; i<radioSrvyArr.length; i++) {
				EgovMap SurvyMap = new EgovMap();
				SurvyMap.put("srvyNo", radioSrvyArr[i]);
				// 23.09.14 JH 시큐어코딩 mapper입력시 타입 일치화 작업
				int numericValue1 = Integer.parseInt(radioQitmArr[i]);
				int numericValue2 = Integer.parseInt(radioArtclArr[i]);
				SurvyMap.put("qitemNo", numericValue1);
				SurvyMap.put("artclNo", numericValue2);
				//-------------------------------------------------------------------------
				SurvyMap.put("userNo", params.get("userNo"));
				int insertCk_S = this.surveyDAO.insertSurveyData(SurvyMap);
				int insertCk_D = this.surveyDAO.insertSurveyDetail(SurvyMap);
				ckArrList.add(insertCk_S);
				ckArrList.add(insertCk_D);
	     	}
		}
		
		if(checkSrvyArr != null) {
			for(int i=0; i<checkSrvyArr.length; i++) {
				EgovMap SurvyMap = new EgovMap();
				SurvyMap.put("srvyNo", checkSrvyArr[i]);
				// 23.09.14 JH 시큐어코딩 mapper입력시 타입 일치화 작업
				int numericValue1 = Integer.parseInt(checkQitmArr[i]);
				int numericValue2 = Integer.parseInt(checkArtclArr[i]);
				SurvyMap.put("qitemNo", numericValue1);
				SurvyMap.put("artclNo", numericValue2);
				//-------------------------------------------------------------------------
				SurvyMap.put("userNo", params.get("userNo"));
				int insertCk_S = this.surveyDAO.insertSurveyData(SurvyMap);
				int insertCk_D = this.surveyDAO.insertSurveyDetail(SurvyMap);
				ckArrList.add(insertCk_S);
				ckArrList.add(insertCk_D);
	     	}
		}
		
		if(textSrvyArr != null) {
			for(int i=0; i<textSrvyArr.length; i++) {
				EgovMap SurvyMap = new EgovMap();
				SurvyMap.put("srvyNo", textSrvyArr[i]);
				// 23.09.14 JH 시큐어코딩 mapper입력시 타입 일치화 작업
				int numericValue1 = Integer.parseInt(textQitmArr[i]);
				int numericValue2 = Integer.parseInt(textArtclArr[i]);
				SurvyMap.put("qitemNo", numericValue1);
				SurvyMap.put("artclNo", numericValue2);
				//-------------------------------------------------------------------------
				SurvyMap.put("rspnsCn", textDetailArr[i]);
				SurvyMap.put("userNo", params.get("userNo"));
				int insertCk_S = this.surveyDAO.insertSurveyData(SurvyMap);
				int insertCk_D = this.surveyDAO.insertSurveyDetailText(SurvyMap);
				ckArrList.add(insertCk_S);
				ckArrList.add(insertCk_D);
	     	}
		}
		return ckArrList;
	}
	
	@Override
	public EgovMap selectSurveyAttendCnt(EgovMap params) {
		return this.surveyDAO.selectSurveyAttendCnt(params);
	}
	
	@Override
	public EgovMap selectSurveyPopHeadInfo(EgovMap params) {
		return this.surveyDAO.selectSurveyPopHeadInfo(params);
	}

}