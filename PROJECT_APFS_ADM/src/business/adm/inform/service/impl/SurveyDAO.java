package business.adm.inform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.SurveyVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [DAO클래스] - 공통 DAO
 *
 * @class   : SurveyDAO
 * @author  :
 * @since   : 2023.07.13
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *	23.07.13	KYW			First Coding.
 */
@Repository("SurveyDAO")
@SuppressWarnings({"all"})
public class SurveyDAO extends BaseDAO {
	/**
	 * 설문관리 페이징목록조회
	 */
	 public PaginatedArrayList listSurvey(SurveyVO surveyVO, int currPage, int pageSize) {
		 return pageList("Survey.listSurvey", surveyVO, currPage, pageSize);
	 }
	 
	 /**
	  * 설문관리 목록조회
	  */
	 public List listSurvey(SurveyVO surveyVO) {
		 return list("Survey.listSurvey", surveyVO);
	 }
	 
	 /**
	  * 설문관리 상세조회(설문)
	  */
	 public SurveyVO viewSurvey(SurveyVO surveyVO) {
		 return (SurveyVO)view("Survey.viewSurvey", surveyVO);
	 }
	 
	 /**
	  * 설문관리 상세조회(문제)
	  */
	 public List listSurveyQstnn(SurveyVO surveyVO) {
		 return list("Survey.listSurveyQstnn", surveyVO);
	 }
	 
	 /**
	  * 설문관리 상세조회(항목)
	  */
	public List listSurveyQitem(SurveyVO surveyVO) {
		return list("Survey.listSurveyQitem", surveyVO);
	}
	 
	/**
	 * 설문번호조회
	 */
	public Long getSrvyNo(SurveyVO surveyVO) {
		return (Long)view("Survey.getSrvyNo", surveyVO);
	}
	
	/**
	 * 문항번호 조회
	 */
	public List getQitemNo(SurveyVO surveyVO) {
		List list = list("Survey.getQitemNo", surveyVO);
		return list;
	}
	
	/**
     * 설문 등록
     */
    public int regiSurvey(SurveyVO surveyVO) {
    	return insert("Survey.regiSurvey", surveyVO);
    }
    
    /**
     * 문항관리 등록
     */
    public int regiQesitm(SurveyVO surveyVO) {
    	return insert("Survey.regiQesitm", surveyVO);
    }
    
    /**
     * 문항항목 등록
     */
    public int regiItem(SurveyVO surveyVO) {
    	return insert("Survey.regiItem", surveyVO);
    }
    
    /**
     * 설문 수정
     */
    public int updtSurvey(SurveyVO surveyVO) {
        return update("Survey.updtSurvey", surveyVO);
    }

    /**
     * 설문 삭제
     */
    public int deltSurvey(SurveyVO surveyVO) {
        return delete("Survey.deltSurvey", surveyVO);
    }
    
    /**
     * 설문 삭제(문제, 항목)
     */
    public int deltSurveyItems(SurveyVO surveyVO) {
    	return delete("Survey.deltSurveyItems", surveyVO);
    }
    
    /**
     * 설문 응답 조회
     */
    public List<EgovMap> selectSurveyAnswer(SurveyVO surveyVO) {
		return selectList("Survey.selectSurveyAnswer", surveyVO);	
	}
    
    /**
     * 설문 참여인원 조회
     */
    public EgovMap selectSurveyAnswerCnt(SurveyVO surveyVO) {
		return selectOne("Survey.selectSurveyAnswerCnt", surveyVO);	
	}
}
