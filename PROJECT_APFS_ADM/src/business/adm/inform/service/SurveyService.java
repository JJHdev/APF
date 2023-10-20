package business.adm.inform.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [서비스인터페이스] - 개발탬플릿(개발자 표준 가이드용)
 *
 * @class   : SurveyService
 * @author  : ntarget
 * @since   : 2023.07.12
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@SuppressWarnings("all")
public interface SurveyService {
	/**
	 * 설문관리 페이징목록 조회
	 */
	public PaginatedArrayList listSurvey(SurveyVO surveyVO, int currPage, int pageSize) throws Exception;
	
	/**
	 * 설문관리 목록조회
	 */
	public List listSurvey(SurveyVO surveyVO) throws Exception;
	
	/**
	 * 설문관리 상세조회
	 */
	public SurveyVO viewSurvey(SurveyVO surveyVO) throws Exception;
	 
	/**
	 * 설문관리 상세조회(문제)
	 */
	public List listSurveyQstnn(SurveyVO surveyVO) throws Exception;
	
	/**
	 * 설문관리 상세조회(항목)
	 */
	public List listSurveyQitem(SurveyVO surveyVO) throws Exception;
	
	/**
	 * 설문관리 등록, 수정, 삭제
	 */
	public String saveSurvey(SurveyVO surveyVO) throws Exception;

	/**
	 * 설문응답 조회
	 */
	public List<EgovMap> selectSurveyAnswer(SurveyVO surveyVO);
	
	/**
	 * 설문 참여인원 조회
	 */
	public EgovMap selectSurveyAnswerCnt(SurveyVO surveyVO);
}