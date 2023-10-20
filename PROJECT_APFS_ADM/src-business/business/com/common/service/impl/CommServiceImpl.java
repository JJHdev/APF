package business.com.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.common.service.CommService;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 공통 ServiceImpl
 *
 * @class   : CommServiceImpl
 * @author  :
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일            수정자                             수정내용
 *  -------    --------    ---------------------------
 *
 */
@Service("CommService")
@SuppressWarnings("rawtypes")
public class CommServiceImpl extends BaseService implements CommService {

    @Resource(name = "CommDAO")
    private CommDAO commDAO;
    
	@Override
	public List<Map<String, Object>> listCode(Map<String, Object> paramMap) throws Exception {
		return commDAO.listCode(paramMap);
	}

	@Override
	public List<Map<String, Object>> listCode(String upCdId) throws Exception {
		return _getCodeList(upCdId);
	}
	
	/**
     * 특정 상위코드에 해당하는 코드 리스트 구분자로 합쳐서 조회 
     */
    public String listCodeSep(String upCdId, String sep) throws Exception {
    	List<Map<String,Object>> list = _getCodeList(upCdId);
    	String result = "";
    	
    	for(int i=0 ; i<list.size() ; i++) {
    		String param = (String) list.get(i).get("code");
    		result += param;
    		if(i != list.size()-1)
    			result += sep;
    	}
    	
    	return result;
    }

    /**
     * 코드상세조회
     */
	@Override
    public Map<String,Object> getCode(String upCdId, String cdId) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("upCdId", upCdId);
    	params.put("cdId"  , cdId);
    	return commDAO.viewCode(params);
    }

    /**
     * 코드명칭조회
     */
	@Override
    public String getCodeName(String upCdId, String cdId) throws Exception {
    	Map<String,Object> code = getCode(upCdId, cdId);
    	if (code == null)
    		return null;
    	return (String)code.get("cdNm");
    }
	
	/**
     * 투자분야코드상세조회
     */
	@Override
    public Map<String,Object> getInvtRlmCode(String invtFldCd) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("invtFldCd"  , invtFldCd);
    	return commDAO.viewInvtRlmCode(params);
    }
    
    /**
     * LSH. 코드값에 해당하는 코드가 있는지 확인
     */
	@Override
    public boolean existCode(String upCdId, String cdId) {
		if (CommUtils.isEmpty(upCdId))
			return false;
		if (CommUtils.isEmpty(cdId))
			return false;
		try {
			Map<String,Object> code = getCode(upCdId, cdId);
			if (code != null)
				return true;
		} catch(Exception e) {
			logger.error("existCode Error!", e);
		}
		return false;
    }
    
    /**
     * LSH. 코드값에 입력받은 코드배열이 모두 포함되는지 확인
     */
	@Override
    public boolean existCodes(String upCdId, List<String> cdIds) {
		if (CommUtils.isEmpty(upCdId))
			return false;
		if (cdIds == null)
			return false;
		if (cdIds.size() == 0)
			return false;
		try {
			List<Map<String,Object>> codes = _getCodeList(upCdId);
			if (codes == null)
				return false;
			
			for (String cdId : cdIds) {
				Map code = codes.stream().filter(x -> x.get("code").equals(cdId)).findAny().get();
				if (code == null)
					return false;
			}
			return true;
		} catch(Exception e) {
			logger.error("existCode Error!", e);
		}
		return false;
    }

	/**
     * LHB. 투자분야 코드값에 해당하는 코드가 있는지 확인
     */
	@Override
    public boolean existInvtRlmCode(String invtFldCd) {
		try {
			Map<String,Object> code = getInvtRlmCode(invtFldCd);
			if (code != null)
				return true;
		} catch(Exception e) {
			logger.error("existCode Error!", e);
		}
		return false;
    }
    
    /**
     * LSH. 투자분야 코드값에 입력받은 코드배열이 모두 포함되는지 확인
     */
	@Override
    public boolean existInvtRlmCodes(List<String> invtFldCds) {
		if (invtFldCds == null)
			return false;
		if (invtFldCds.size() == 0)
			return false;
		try {
			List<Map<String,Object>> codes = listInvtRlmCode(new HashMap<String,Object> ());
			if (codes == null)
				return false;
			
			for (String cdId : invtFldCds) {
				Map code = codes.stream().filter(x -> x.get("code").equals(cdId)).findAny().get();
				if (code == null)
					return false;
			}
			return true;
		} catch(Exception e) {
			logger.error("existInvtRlmCodes Error!", e);
		}
		return false;
    }
    
    /**
     * 상위코드ID에 해당하는 코드목록 반환
     */
    @SuppressWarnings("unchecked")
	private List<Map<String, Object>> _getCodeList(String upCdId) throws Exception {
		Map params = new HashMap();
		params.put("upCdId", upCdId);
		return listCode(params);
    }
    
    /**
     * 코드리스트를 맵으로 변환
     * @param list      코드리스트 (List<Map>)
     * @param keyField  맵의 KEY에 해당하는 필드명
     * @param valueField 맵의 VALUE에 해당하는 필드명
     */
    private Map<String,Object> _toMap(List<Map<String, Object>> list, String keyField, String valueField) {
    	Map<String,Object> ret = new HashMap<String,Object>();
    	if (list == null)
    		return ret;
    	for (Map item : list) {
    		ret.put((String)item.get(keyField), item.get(valueField));
    	}
    	return ret;
    }
    
    /**
     * 코드목록을 Name KEY 맵으로 변환
     */
	@Override
	public Map<String,Object> getMapCodeByName(String upCdId) throws Exception{
    	List<Map<String, Object>> list = _getCodeList(upCdId);
    	return _toMap(list, "text", "code");
    }
    
    /**
     * 코드목록을 CODE KEY 맵으로 변환
     */
	@Override
	public Map<String,Object> getMapCode(String upCdId) throws Exception{
    	List<Map<String, Object>> list = _getCodeList(upCdId);
    	return _toMap(list, "code", "text");
    }

	// 서류양식 상위코드 콤보 조회
	@Override
    public List<Map<String,Object>> listUpPape(Map<String,Object> paramMap) throws Exception{
		return commDAO.listUpPape(paramMap);
    }
	// 서류양식 하위코드 콤보 조회
	@Override
    public List<Map<String,Object>> listPape(Map<String,Object> paramMap) throws Exception{
		return commDAO.listPape(paramMap);
    }
    // 샘플용 KODATA 반환
	@Override
	public Map<String,Object> viewSampleKodata(String bzno) throws Exception{
		return commDAO.viewSampleKodata(bzno);
    }
	
	/**
     * 특정 상위코드에 해당하는 코드 리스트 조회 
     */
	@Override
    public List<Map<String,Object>> listInvtRlmCode(Map<String,Object> paramMap) throws Exception{
    	return commDAO.listInvtRlmCode(paramMap);
    }
	
	/**
     * 유관기관 코드 리스트 조회 
     */
	@Override
    public List<Map<String,Object>> listCrdnsEntCode(Map<String,Object> paramMap) throws Exception{
    	return commDAO.listCrdnsEntCode(paramMap);
    }
    
    /**
     * 2023.07.04 LSH
     * 트리형태 코드리스트 조회
     * 
     * @param paramMap.upCdId   상위코드
     * @param paramMap.srchCode 노드선택검색시 상위노드값
     * @param paramMap.srchText 텍스트검색시 검색텍스트
     */
	@Override
    public List<Map<String,Object>> listTreeCode(Map paramMap) throws Exception {
    	return commDAO.listTreeCode(paramMap);
    }

	// 제출서류관리 지원사업명 검색
	@Override
	public List listUpDcmnt(Map paramMap) {
		return commDAO.listUpDcmnt(paramMap);
	}

	// 제출서류관리 프로그램명 검색
	@Override
	public List listDcmnt(Map paramMap) {
		return commDAO.listDcmnt(paramMap);
	}
	
    //  제출서류관리 지원사원명 검색 (프로그램명 없을 경우 표출 X)
	@Override
	public List getComboUpDcmntTask(Map paramMap) throws Exception {
		return commDAO.getComboUpDcmntTask(paramMap);
	}
}
