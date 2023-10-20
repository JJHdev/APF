package business.adm.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.invest.service.EventVO;
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
 * @class   : IrOpnnDAO
 * @author  : LHB
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 *  23.06.07     LHB            First Coding.
 */

@Repository("IrOpnnDAO")
@SuppressWarnings({"all"})
public class IrOpnnDAO extends BaseDAO {
	
	/**
     * IR검토의견서관리 투자자 페이징목록 조회
     */
    public PaginatedArrayList listIrOpnnInvt(EventVO eventVO, int currPage, int pageSize) {
        return pageList("IrOpnn.listIrOpnnInvt", eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 투자자 목록 조회
     */
    public List listIrOpnnInvt(EventVO eventVO) {
        return list("IrOpnn.listIrOpnnInvt", eventVO);
    }
    
    /**
     * IR검토의견서관리 경영체 페이징목록 조회
     */
    public PaginatedArrayList listIrOpnnEnt(EventVO eventVO, int currPage, int pageSize) {
        return pageList("IrOpnn.listIrOpnnEnt", eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 경영체 목록 조회
     */
    public List listIrOpnnEnt(EventVO eventVO) {
        return list("IrOpnn.listIrOpnnEnt", eventVO);
    }
    
    
	
    /**
     * IR검토의견서관리 의견서 페이징목록 조회
     */
    public PaginatedArrayList listIrOpnn(EventVO eventVO, int currPage, int pageSize) {
        return pageList("IrOpnn.listIrOpnn", eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 의견서 목록 조회
     */
    public List listIrOpnn(EventVO eventVO) {
        return list("IrOpnn.listIrOpnn", eventVO);
    }

    /**
     * IR검토의견서관리 의견서 상세 조회
     */
    public EventVO viewIrOpnn(EventVO eventVO) {
        return (EventVO) view("IrOpnn.viewIrOpnn", eventVO);
    }

}