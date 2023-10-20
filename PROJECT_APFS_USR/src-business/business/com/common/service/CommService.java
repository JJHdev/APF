package business.com.common.service;

import java.util.List;
import java.util.Map;

/**
 * [서비스인터페이스] - 공통 Service IF
 *
 * @class   : CommService
 * @author  :
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일            수정자                             수정내용
 *  -------    --------    ---------------------------
 *
 */
public interface CommService {

    /**
     * 특정 상위코드에 해당하는 코드 리스트 조회 
     */
    public List<Map<String,Object>> listCode(Map<String,Object> paramMap) throws Exception;

    /**
     * 특정 상위코드에 해당하는 코드 리스트 조회 
     */
    public List<Map<String,Object>> listCode(String upCdId) throws Exception;

    /**
     * 특정 상위코드에 해당하는 코드 리스트 구분자로 합쳐서 조회 
     */
    public String listCodeSep(String upCdId, String sep) throws Exception;

    /**
     * 코드상세조회
     */
    public Map<String,Object> getCode(String upCdId, String cdId) throws Exception;

    /**
     * 코드명칭조회
     */
    public String getCodeName(String upCdId, String cdId) throws Exception;
    
    /**
     * 투자분야코드상세조회
     */
    public Map<String,Object> getInvtRlmCode(String cdId) throws Exception;
    
    /**
     * 코드목록을 Name KEY 맵으로 변환
     */
    public Map<String,Object> getMapCodeByName(String upCdId) throws Exception;
    
    /**
     * 코드목록을 Code KEY 맵으로 변환
     */
    public Map<String,Object> getMapCode(String upCdId) throws Exception;
    
    /**
     * LSH. 코드값에 해당하는 코드가 있는지 확인
     */
    public boolean existCode(String upCdId, String cdId);
    
    /**
     * LSH. 코드값에 입력받은 코드배열이 모두 포함되는지 확인
     */
    public boolean existCodes(String upCdId, List<String> cdIds);
    
    /**
     * LHB. 투자분야 코드값에 해당하는 코드가 있는지 확인
     */
    public boolean existInvtRlmCode(String invtFldCd);
    
    /**
     * LSH. 투자분야 코드값에 입력받은 코드배열이 모두 포함되는지 확인
     */
    public boolean existInvtRlmCodes(List<String> invtFldCds);

	// 서류양식 상위코드 콤보 조회
    public List<Map<String,Object>> listUpPape(Map<String,Object> paramMap) throws Exception;
	// 서류양식 하위코드 콤보 조회
    public List<Map<String,Object>> listPape(Map<String,Object> paramMap) throws Exception;
    
    // 샘플용 KODATA 반환
    public Map<String,Object> viewSampleKodata(String bzno) throws Exception;

    // 투자분야코드 콤보 조회
    public List<Map<String,Object>> listInvtRlmCode(Map<String,Object> paramMap) throws Exception;
    
	// 유관기관 콤보 조회
    public List<Map<String,Object>> listCrdnsEntCode(Map<String,Object> paramMap) throws Exception;

    /**
     * 2023.07.04 LSH
     * 트리형태 코드리스트 조회
     * 
     * @param paramMap.upCdId   상위코드
     * @param paramMap.srchCode 노드선택검색시 상위노드값
     * @param paramMap.srchText 텍스트검색시 검색텍스트
     */
    public List<Map<String,Object>> listTreeCode(Map<String,Object> paramMap) throws Exception;

    // 제출서류관리 지원사업명 검색
	@SuppressWarnings("rawtypes")
	public List listUpDcmnt(Map paramMap);

	// 제출서류관리 프로그램명 검색
	@SuppressWarnings("rawtypes")
	public List listDcmnt(Map paramMap);
	
	/**
	 * 2023.08.17 LSH
     * 사업분야코드 상세조회
     */
    public Map<String,Object> viewBizRlmCode(Map<String,Object> paramMap) throws Exception;

	/**
	 * 2023.08.17 LSH
     * 사업분야코드 리스트 조회 
     */
    public List<Map<String,Object>> listBizRlmCode(Map<String,Object> paramMap) throws Exception;
}
