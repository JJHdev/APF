package business.adm.inform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import business.adm.inform.service.SurveyService;
import business.adm.inform.service.SurveyVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [서비스클래스] - 공통 ServiceImpl
 *
 * @class   : SurveyServiceImpl
 * @author  :
 * @since   : 2023.07.12
 * @version : 1.0
 *
 *   수정일       수정자            수정내용
 *  -------    --------    ---------------------------
 *	2023.07.12	KYW			First Coding.
 */
@Service("SurveyService")
@SuppressWarnings("rawtypes")
public class SurveyServiceImpl extends BaseService implements SurveyService {

	@Resource(name = "SurveyDAO")
    private SurveyDAO surveyDAO;
    
	/**
	 * 설문관리 페이징목록조회
	 */
	@Override
	public PaginatedArrayList listSurvey(SurveyVO surveyVO, int currPage, int pageSize) throws Exception {
		return surveyDAO.listSurvey(surveyVO, currPage, pageSize);
	}
	
	/**
	 * 설문관리 목록조회
	 */
	@Override
	public List listSurvey(SurveyVO surveyVO) throws Exception {
		return surveyDAO.listSurvey(surveyVO);
	}
	
	/**
	 * 설문관리 상세조회
	 */
	@Override
	public SurveyVO viewSurvey(SurveyVO surveyVO) throws Exception {
		return surveyDAO.viewSurvey(surveyVO);
	}
	
	/**
	 * 설문관리 상세조회(문제)
	 */
	@Override
	public List listSurveyQstnn(SurveyVO surveyVO) throws Exception {
		return surveyDAO.listSurveyQstnn(surveyVO);
	}
	
	/**
	 * 설문관리 상세조회(항목)
	 */
	@Override
	public List listSurveyQitem(SurveyVO surveyVO) throws Exception {
		return surveyDAO.listSurveyQitem(surveyVO);
		
	}
	/**
     * 설문관리 등록,수정,삭제
     */
	@Override
	public String saveSurvey(SurveyVO surveyVO) throws Exception {
		
		if (surveyVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = surveyVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 해당 설문의 하위 items 지우기
			ret = surveyDAO.deltSurveyItems(surveyVO);
			
			//설문 UPDATE
			if(ret > 0) {
				ret = 0;
				ret = surveyDAO.updtSurvey(surveyVO);
			}
			// 하위 items 재등록
			if(ret > 0)
				ret = regiSurveyItems(surveyVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 설문 등록
			Long srvyNo = surveyDAO.getSrvyNo(surveyVO);
			surveyVO.setSrvyNo(srvyNo);
			
			ret = surveyDAO.regiSurvey(surveyVO);
			
			if(ret > 0) 
				ret = regiSurveyItems(surveyVO);
			
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 설문 삭제
			ret = surveyDAO.deltSurvey(surveyVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	/**
	 * 설문 문제, 항목 등록 (tb_qstnn_qesitm, tb_qesitm_item)
	 */
	private int regiSurveyItems(SurveyVO surveyVO) throws Exception{
		int ret = 0;
		
		// JSON 문자열을 JSONObject로 변환
		JSONParser parser = new JSONParser();
		// 배열 풀어서 insert
		JSONObject qitemCnArr = (JSONObject)parser.parse(surveyVO.getQitemCnArr());
		for(int i=0; i<qitemCnArr.size(); i++) {
			surveyVO.setQitemCn((String)qitemCnArr.get("qitemCn"+i));
			surveyVO.setQitemType(surveyVO.getQitemTypeArr()[i]);
			surveyVO.setMarkNo(Integer.toString(i+1));
			// 설문문항관리 insert
			ret = surveyDAO.regiQesitm(surveyVO);
		}
		
		if(ret > 0) {
			List<Long> qitemNoArr = surveyDAO.getQitemNo(surveyVO);
			String artclCnData = surveyVO.getArtclCnArr();
			
			
			JSONObject artclCnArr = (JSONObject)parser.parse(surveyVO.getArtclCnArr());
			
			// 설문번호로 문항번호 가져오기
			for(int i=0; i<qitemNoArr.size(); i++) { // 문항당 항목개수
				surveyVO.setQitemNo(qitemNoArr.get(i));
				
				// artclCn+i의 값을 배열로 얻어오기
				JSONArray artclCnArray = (JSONArray)artclCnArr.get("artclCn"+i);
				
				// artclCn1의 배열 값 출력
				for (int j = 0; j < artclCnArray.size(); j++) {
					String artclCn = (String)artclCnArray.get(j);
					surveyVO.setArtclCn(artclCn);
					surveyVO.setMarkNo(Integer.toString(j+1));
					// 설문문항항목 insert
					ret = surveyDAO.regiItem(surveyVO);
				}
				
			}
		}
		return ret;
	}
	
	/**
     * 설문 응답 조회
     */
	@Override
	public List<EgovMap> selectSurveyAnswer(SurveyVO surveyVO) {
		List<EgovMap> surveyAnswerList = this.surveyDAO.selectSurveyAnswer(surveyVO);
		return surveyAnswerList;
	}
	
	/**
     * 설문 참여인원 조회
     */
	@Override
	public EgovMap selectSurveyAnswerCnt(SurveyVO surveyVO) {
		EgovMap cnt = this.surveyDAO.selectSurveyAnswerCnt(surveyVO);
		return cnt;
	}
}
