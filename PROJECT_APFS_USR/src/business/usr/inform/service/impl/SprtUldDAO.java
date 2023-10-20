 package business.usr.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import business.usr.inform.service.BbsVO;
import business.usr.support.service.SprtBizVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 정보서비스-경영체 데이터 업로드를 관리하는 DAO 클래스
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
 * @class   : SprtUldDAO
 * @author  : LHB
 * @since   : 2023.07.13
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 * 23.07.13      LHB            First Coding.
 */

@Repository("SprtUldDAO")
@SuppressWarnings({"all"})
public class SprtUldDAO extends BaseDAO {

	/**
     * 운영관리-경영체 데이터 업로드 페이징목록 조회
     */
    public PaginatedArrayList listSprtUld(SprtBizVO sprtBizVO, int currPage, int pageSize) {
        return pageList("SprtBiz.listSprtUld", sprtBizVO, currPage, pageSize);
    }

    /**
     * 운영관리-경영체 데이터 업로드 목록 조회
     */
    public List listSprtUld(SprtBizVO sprtBizVO) {
        return list("SprtBiz.listSprtUld", sprtBizVO);
    }
    
    /**
     * 운영관리-경영체 데이터 업로드 상세 조회
     */
    public SprtBizVO viewSprtUld(SprtBizVO sprtBizVO) {
        return (SprtBizVO) view("SprtBiz.viewSprtUld", sprtBizVO);
    }
    
    /**
     * 운영관리-경영체 데이터 업로드 이력 등록
     */
    public int regiSprtUld(SprtBizVO sprtBizVO) {
        return insert("SprtBiz.regiSprtUld", sprtBizVO);
    }
    
    /**
     * 운영관리-경영체 데이터 업로드 이력 수정
     */
    public int updtSprtUld(SprtBizVO sprtBizVO) {
        return update("SprtBiz.updtSprtUld", sprtBizVO);
    }

    
    
    /**
     * 운영관리-경영체 데이터 업로드-지원사업이력 등록
     */
    public int regiSprtBiz(SprtBizVO sprtBizVO) {
        return insert("SprtBiz.regiSprtBiz", sprtBizVO);
    }
    
    /**
     * 운영관리-경영체 데이터 업로드-지원사업이력 삭제
     */
    public int updtSprtBiz(SprtBizVO sprtBizVO) {
        return update("SprtBiz.updtSprtBiz", sprtBizVO);
    }
    
    
    
    /**
     * 운영관리-경영체 데이터 업로드 페이징목록 조회
     */
    public PaginatedArrayList listSprtBiz(SprtBizVO sprtBizVO, int currPage, int pageSize) {
        return pageList("SprtBiz.listSprtBiz", sprtBizVO, currPage, pageSize);
    }

    /**
     * 운영관리-경영체 데이터 업로드 목록 조회
     */
    public List listSprtBiz(SprtBizVO sprtBizVO) {
        return list("SprtBiz.listSprtBiz", sprtBizVO);
    }
    
    
    
    /**
     * 운영관리-경영체 데이터 업로드 IR번호 조회
     */
    public String viewIrNo(SprtBizVO sprtBizVO) {
    	return (String) view("SprtBiz.viewIrNo", sprtBizVO);
    }
    
    /**
     * 운영관리-경영체 데이터 업로드 IR 등록
     */
    public int regiEntIr(SprtBizVO sprtBizVO) {
    	return insert("SprtBiz.regiEntIr", sprtBizVO);
    }
    
    /**
     * 운영관리-경영체 데이터 업로드 IR 투자금액 등록
     */
    public int regiEntInvtAmt(SprtBizVO sprtBizVO) {
    	return insert("SprtBiz.regiEntInvtAmt", sprtBizVO);
    }
}