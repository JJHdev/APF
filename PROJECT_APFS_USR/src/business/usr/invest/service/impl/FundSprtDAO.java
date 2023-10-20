 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 펀드지원정보(IR지원)을 관리하는 DAO 클래스
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
 * @class   : FundSprtDAO
 * @author  : LSH
 * @since   : 2023.04.21
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("FundSprtDAO")
@SuppressWarnings({"all"})
public class FundSprtDAO extends BaseDAO {

    /**
     * 펀드지원정보(IR지원) 페이징목록 조회
     */
    public PaginatedArrayList listFundSprt(FundVO fundVO, int currPage, int pageSize) {
        return pageList("FundSprt.listFundSprt", fundVO, currPage, pageSize);
    }

    /**
     * 펀드지원정보(IR지원) 목록 조회
     */
    public List listFundSprt(FundVO fundVO) {
        return list("FundSprt.listFundSprt", fundVO);
    }

    /**
     * 펀드지원정보(IR지원) 상세 조회
     */
    public FundVO viewFundSprt(FundVO fundVO) {
        return (FundVO)view("FundSprt.viewFundSprt", fundVO);
    }

    /**
     * 펀드지원정보(IR지원) 등록
     */
    public int regiFundSprt(FundVO fundVO) {
        return insert("FundSprt.regiFundSprt", fundVO);
    }

    /**
     * 펀드지원정보(IR지원) 수정
     */
    public int updtFundSprt(FundVO fundVO) {
        return update("FundSprt.updtFundSprt", fundVO);
    }

    /**
     * 펀드지원정보(IR지원) 삭제
     */
    public int deltFundSprt(FundVO fundVO) {
        return delete("FundSprt.deltFundSprt", fundVO);
    }

}