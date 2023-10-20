package business.adm.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.invest.service.InvestFieldVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자정보관리-투자분야관리를 관리하는 DAO 클래스
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
 * @class   : InvestFieldDAO
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 *  23.06.09     LHB            First Coding.
 */

@Repository("InvestFieldDAO")
@SuppressWarnings({"all"})
public class InvestFieldDAO extends BaseDAO {
	
    /**
     *  투자정보관리-투자분야관리 페이징목록 조회
     */
    public PaginatedArrayList listInvestField(InvestFieldVO investFieldVO, int currPage, int pageSize) {
        return pageList("InvestField.listInvestField", investFieldVO, currPage, pageSize);
    }

    /**
     * 투자정보관리-투자분야관리 목록 조회
     */
    public List listInvestField(InvestFieldVO investFieldVO) {
        return list("InvestField.listInvestField", investFieldVO);
    }

    /**
     * 투자정보관리-투자분야관리 상세 조회
     */
    public InvestFieldVO viewInvestField(InvestFieldVO investFieldVO) {
        return (InvestFieldVO) view("InvestField.viewInvestField", investFieldVO);
    }
    
    /**
     * 투자정보관리-투자분야관리 등록
     */
    public int regiInvestField(InvestFieldVO investFieldVO) {
        return insert("InvestField.regiInvestField", investFieldVO);
    }

    /**
     * 투자정보관리-투자분야관리 수정
     */
    public int updtInvestField(InvestFieldVO investFieldVO) {
        return update("InvestField.updtInvestField", investFieldVO);
    }

    /**
     * 투자정보관리-투자분야관리 삭제
     */
    public int deltInvestField(InvestFieldVO investFieldVO) {
        return update("InvestField.deltInvestField", investFieldVO);
    }

}