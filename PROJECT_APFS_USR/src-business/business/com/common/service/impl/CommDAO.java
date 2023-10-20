package business.com.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import common.base.BaseDAO;

/**
 * [DAO클래스] - 공통 DAO
 *
 * @class   : CommDAO
 * @author  :
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */
@Repository("CommDAO")
@SuppressWarnings({"all"})
public class CommDAO extends BaseDAO {
	
	// 코드리스트 조회
    public List<Map<String,Object>> listCode(Map paramMap) throws Exception {
        return list("Comm.listCode", paramMap);
    }

    // 코드상세조회
    public Map<String,Object> viewCode(Map paramMap) {
        return (Map)view("Comm.viewCode", paramMap);
    }
    
	// 서류양식 상위코드 콤보 조회
    public List<Map<String,Object>> listUpPape(Map paramMap) throws Exception {
        return list("Comm.listUpPape", paramMap);
    }
	// 서류양식 하위코드 콤보 조회
    public List<Map<String,Object>> listPape(Map paramMap) throws Exception {
        return list("Comm.listPape", paramMap);
    }
    
    // 샘플용 KODATA 반환
    public Map<String,Object> viewSampleKodata(String bzno) {
        return (Map)view("Comm.viewSampleKodata", bzno);
    }
    
    // 투자분야 코드리스트 조회
    public List<Map<String,Object>> listInvtRlmCode(Map paramMap) throws Exception {
        return list("Comm.listInvtRlmCode", paramMap);
    }
    
    // 투자분야 코드 상세조회
    public Map<String,Object> viewInvtRlmCode(Map paramMap) {
        return (Map)view("Comm.viewInvtRlmCode", paramMap);
    }
    
	// 유관기관 코드리스트 조회
    public List<Map<String,Object>> listCrdnsEntCode(Map paramMap) throws Exception {
        return list("Comm.listCrdnsEntCode", paramMap);
    }
    
    /**
     * 2023.07.04 LSH
     * 트리형태 코드리스트 조회
     * 
     * @param paramMap.upCdId   상위코드
     * @param paramMap.srchCode 노드선택검색시 상위노드값
     * @param paramMap.srchText 텍스트검색시 검색텍스트
     */
    public List<Map<String,Object>> listTreeCode(Map paramMap) throws Exception {
        return list("Comm.listTreeCode", paramMap);
    }

    // 제출서류관리 프로그램명 검색
	public List listUpDcmnt(Map paramMap) {
		return list("Comm.listUpDcmnt", paramMap);
	}
	// 제출서류관리 프로그램명 검색
	public List listDcmnt(Map paramMap) {
		return list("Comm.listDcmnt", paramMap);
	}
	
	/**
	 * 2023.08.17 LSH
     * 사업분야코드 상세조회
     */
    public Map<String,Object> viewBizRlmCode(Map paramMap) throws Exception {
        return (Map)view("Comm.viewBizRlmCode", paramMap);
    }

	/**
	 * 2023.08.17 LSH
     * 사업분야코드 리스트 조회 
     */
    public List<Map<String,Object>> listBizRlmCode(Map paramMap) throws Exception {
        return list("Comm.listBizRlmCode", paramMap);
    }
	
}
