 package business.usr.inform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.inform.service.SurveyVO;
import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [DAO클래스] - GIS를 관리하는 DAO 클래스
 * 
 * 사용 가능한  DAO Statement Method
 * 1. list          : 리스트 조회시 사용함.
 * 2. pageListOra   : 페이징처리용 리스트조회시 사용함. for Oracle, Tibero
 * 3. view          : 단건조회, 상세조회시 사용함.
 * 4. save          : INSERT, UPDATE, DELETE 모두 사용가능. (Return Type : Integer)
 * 5. insert        : INSERT (Return String : Key 채번 사용함.)
 * 6. update        : UPDATE (Return Type : Integer)
 * 7. delete        : DELETE (Return Type : Integer)
 * 
 *
 * @class   : GisDAO
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  -------    --------    ---------------------------
 * 23.06.13      LHB              First Coding.
 */

@Repository("SurveyDAO")
@SuppressWarnings({"all"})
public class SurveyDAO extends BaseDAO {
	
	public List<EgovMap> selectSurveyList(EgovMap params) {
		return selectList("Survey.selectSurveyList", params);	
	}
	
	public List<EgovMap> selectParticiSurveyList(EgovMap params) {
		return selectList("Survey.selectParticiSurveyList", params);	
	}
	
	/*
	public EgovMap selectSurveyImgList(EgovMap params) {
		return selectOne("Survey.selectSurveyImgList");	
	}*/
	
	public List<EgovMap> selectSurveyInfoList(EgovMap params) {
		return selectList("Survey.selectSurveyInfoList", params);	
	}
	
	public List<EgovMap> selectSurveyInfoItemList(EgovMap params) {
		return selectList("Survey.selectSurveyInfoItemList", params);	
	}
	
	public int insertSurveyData(EgovMap params) {
		return insert("Survey.insertSurveyData", params);	
	}
	
	public int insertSurveyDetail(EgovMap params) {
		return insert("Survey.insertSurveyDetail", params);	
	}
	
	public int insertSurveyDetailText(EgovMap params) {
		return insert("Survey.insertSurveyDetailText", params);	
	}
	
	public EgovMap selectSurveyAttendCnt(EgovMap params) {
		return selectOne("Survey.selectSurveyAttendCnt", params);	
	}
	
	public EgovMap selectSurveyPopHeadInfo(EgovMap params) {
		return selectOne("Survey.selectSurveyPopHeadInfo", params);	
	}
    // 23.09.14 JH 페이지네이션 추가작업 
	public PaginatedArrayList selectSurveyListPagnation(SurveyVO surveyVO, int page, int size) {
		return pageList("Survey.selectSurveyListPagnation", surveyVO, page, size);
	}
    // 23.09.14 JH 페이지네이션 추가작업 
	public List selectSurveyListPagnation(SurveyVO surveyVO) {
		return list("Survey.selectSurveyListPagnation", surveyVO);
	}

}