 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 펀드투자자(조합원)을 관리하는 DAO 클래스
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
 * @class   : FundInvstrDAO
 * @author  : LSH
 * @since   : 2023.04.21
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("FundInvstrDAO")
@SuppressWarnings({"all"})
public class FundInvstrDAO extends BaseDAO {

    /**
     * 펀드투자자(조합원) 페이징목록 조회
     */
    public PaginatedArrayList listFundInvstr(FundVO fundVO, int currPage, int pageSize) {
        return pageList("FundInvstr.listFundInvstr", fundVO, currPage, pageSize);
    }

    /**
     * 펀드투자자(조합원) 목록 조회
     */
    public List listFundInvstr(FundVO fundVO) {
        return list("FundInvstr.listFundInvstr", fundVO);
    }

    /**
     * 펀드투자자(조합원) 상세 조회
     */
    public FundVO viewFundInvstr(FundVO fundVO) {
        return (FundVO)view("FundInvstr.viewFundInvstr", fundVO);
    }

    /**
     * 펀드투자자(조합원) 등록
     */
    public int regiFundInvstr(FundVO fundVO) {
        return insert("FundInvstr.regiFundInvstr", fundVO);
    }

    /**
     * 펀드투자자(조합원) 수정
     */
    public int updtFundInvstr(FundVO fundVO) {
        return update("FundInvstr.updtFundInvstr", fundVO);
    }

    /**
     * 펀드투자자(조합원) 삭제
     */
    public int deltFundInvstr(FundVO fundVO) {
        return delete("FundInvstr.deltFundInvstr", fundVO);
    }

}