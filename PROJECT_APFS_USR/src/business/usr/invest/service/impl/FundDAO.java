 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 펀드정보(모태펀드)을 관리하는 DAO 클래스
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
 * @class   : FundDAO
 * @author  : LSH
 * @since   : 2023.04.21
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("FundDAO")
@SuppressWarnings({"all"})
public class FundDAO extends BaseDAO {

    /**
     * 펀드정보(모태펀드) 페이징목록 조회
     */
    public PaginatedArrayList listFund(FundVO fundVO, int currPage, int pageSize) {
        return pageList("Fund.listFund", fundVO, currPage, pageSize);
    }

    /**
     * 펀드정보(모태펀드) 목록 조회
     */
    public List listFund(FundVO fundVO) {
        return list("Fund.listFund", fundVO);
    }

    /**
     * 펀드정보(모태펀드) 상세 조회
     */
    public FundVO viewFund(String fundNo) {
        return (FundVO)view("Fund.viewFund", fundNo);
    }

    /**
     * 펀드정보(모태펀드) 등록
     */
    public int regiFund(FundVO fundVO) {
        return insert("Fund.regiFund", fundVO);
    }

    /**
     * 펀드정보(모태펀드) 수정
     */
    public int updtFund(FundVO fundVO) {
        return update("Fund.updtFund", fundVO);
    }

    /**
     * 펀드정보(모태펀드) 삭제
     */
    public int deltFund(FundVO fundVO) {
        return delete("Fund.deltFund", fundVO);
    }

    /**
     * 펀드조합 업체번호 기준 펀드및조합정보 목록조회
     */
    public List listFundByBzenty(String bzentyNo) {
        return list("Fund.listFundByBzenty", bzentyNo);
    }

    /**
     * 2023.09.11 LSH
     * 펀드번호기준 펀드조합업체목록 조회
     */
    public List listFundEnt(String fundNo) {
        return list("Fund.listFundEnt", fundNo);
    }
}