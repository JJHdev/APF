package business.usr.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.inform.service.BbsVO;
import business.usr.invest.service.EventVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자정보관리-IR검토의견서관리를 관리하는 DAO 클래스
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
 * @class   : OpnnIrDAO
 * @author  : LHB
 * @since   : 2023.06.28
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 *  23.06.28     KYW            First Coding.
 */

@Repository("OpnnIrDAO")
@SuppressWarnings({"all"})
public class OpnnIrDAO extends BaseDAO {
	
    /**
     * IR검토의견서관리 페이징목록 조회
     */
    public PaginatedArrayList listOpnnIr(EventVO eventVO, int currPage, int pageSize) {
        return pageList("OpnnIr.listOpnnIr", eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 목록 조회
     */
    public List listOpnnIr(EventVO eventVO) {
        return list("OpnnIr.listOpnnIr", eventVO);
    }

    /**
     * IR검토의견서관리 상세 조회
     */
    public EventVO viewOpnnIr(EventVO eventVO) {
        return (EventVO) view("OpnnIr.viewOpnnIr", eventVO);
    }
    
    /**
     * IR 검토의견 등록
     */
    public int regiOpnnIr(EventVO eventVO) {
        return insert("OpnnIr.regiOpnnIr", eventVO);
    }
    
    /**
     * IR 검토의견 수정
     */
    public int updtOpnnIr(EventVO eventVO) {
    	return update("OpnnIr.updtOpnnIr", eventVO);
    }

}