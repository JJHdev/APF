 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자금액을 관리하는 DAO 클래스
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
 * @class   : EntInvtAmtDAO
 * @author  : LSH
 * @since   : 2023.05.09
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntInvtAmtDAO")
@SuppressWarnings({"all"})
public class EntInvtAmtDAO extends BaseDAO {

    /**
     * 투자금액 목록 조회
     */
    public List listEntInvtAmt(EntVO entVO) {
        return list("EntInvtAmt.listEntInvtAmt", entVO);
    }

    /**
     * 투자금액 상세 조회
     */
    public EntVO viewEntInvtAmt(EntVO entVO) {
        return (EntVO)view("EntInvtAmt.viewEntInvtAmt", entVO);
    }

    /**
     * 투자금액 등록
     */
    public int regiEntInvtAmt(EntVO entVO) {
        return insert("EntInvtAmt.regiEntInvtAmt", entVO);
    }

    /**
     * 투자금액 수정
     */
    public int updtEntInvtAmt(EntVO entVO) {
        return update("EntInvtAmt.updtEntInvtAmt", entVO);
    }

    /**
     * 투자금액 삭제
     */
    public int deltEntInvtAmt(EntVO entVO) {
        return delete("EntInvtAmt.deltEntInvtAmt", entVO);
    }

}