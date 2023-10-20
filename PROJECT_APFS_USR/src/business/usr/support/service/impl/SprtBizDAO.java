 package business.usr.support.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.support.service.SprtBizVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 지원사업이력을 관리하는 DAO 클래스
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
 * @class   : SprtBizDAO
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("SprtBizDAO")
@SuppressWarnings({"all"})
public class SprtBizDAO extends BaseDAO {

    /**
     * 지원사업이력 페이징목록 조회
     */
    public PaginatedArrayList listSprtBiz(SprtBizVO sprtBizVO, int currPage, int pageSize) {
        return pageList("SprtBiz.listSprtBiz", sprtBizVO, currPage, pageSize);
    }

    /**
     * 지원사업이력 목록 조회
     */
    public List listSprtBiz(SprtBizVO sprtBizVO) {
        return list("SprtBiz.listSprtBiz", sprtBizVO);
    }

    /**
     * 지원사업이력 상세 조회
     */
    public SprtBizVO viewSprtBiz(SprtBizVO sprtBizVO) {
        return (SprtBizVO)view("SprtBiz.viewSprtBiz", sprtBizVO);
    }

    /**
     * 지원사업이력 등록
     */
    public int regiSprtBiz(SprtBizVO sprtBizVO) {
        return insert("SprtBiz.regiSprtBiz", sprtBizVO);
    }

    /**
     * 지원사업이력 수정
     */
    public int updtSprtBiz(SprtBizVO sprtBizVO) {
        return update("SprtBiz.updtSprtBiz", sprtBizVO);
    }

    /**
     * 지원사업이력 삭제
     */
    public int deltSprtBiz(SprtBizVO sprtBizVO) {
        return delete("SprtBiz.deltSprtBiz", sprtBizVO);
    }
    
    
    
    /**
     * 특정 업체번호의 강제로 업로드된 업체가 있는지
     */
    public boolean existEntByBzentyNo(String bzentyNo) {
    	return (boolean) view("SprtBiz.existEntByBzentyNo", bzentyNo);
    }
    
    /**
     * 특정 업체번호의 사용자가 있는지
     */
    public boolean existUserByBzentyNo(String bzentyNo) {
    	return (boolean) view("SprtBiz.existUserByBzentyNo", bzentyNo);
    }
    
    /**
     * 특정 업체번호의 정부지원사업이력이 있는지
     */
    public boolean existSprtBizByBzentyNo(String bzentyNo) {
    	return (boolean) view("SprtBiz.existSprtBizByBzentyNo", bzentyNo);
    }
    
    /**
     * 강제 업로드된 업체정보 삭제
     */
    public int deltEntDirectInptYn(String bzentyNo) {
        return delete("SprtBiz.deltEntDirectInptYn", bzentyNo);
    }
    
    
    
    /**
     * 유관기관 코드 조회
     */
    public int viewCrdnsCode(HashMap map) {
        return delete("SprtBiz.viewCrdnsCode", map);
    }

}