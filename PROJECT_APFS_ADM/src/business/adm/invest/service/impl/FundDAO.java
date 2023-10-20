 package business.adm.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.invest.service.FundVO;
import business.adm.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자정보관리 - 모태펀드등록을 관리하는 DAO 클래스
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
 * @author  : LHB
 * @since   : 2023.04.17
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
     * 모태펀드 페이징목록 조회
     */
    public PaginatedArrayList listFund(FundVO fundVO, int currPage, int pageSize) {
        return pageList("Fund.listFund", fundVO, currPage, pageSize);
    }

    /**
     * 모태펀드 목록 조회
     */
    public List listFund(FundVO fundVO) {
        return list("Fund.listFund", fundVO);
    }

    /**
     * 모태펀드 상세 조회
     */
    public FundVO viewFund(FundVO fundVO) {
        return (FundVO) view("Fund.viewFund", fundVO);
    }

    /**
     * 모태펀드 등록
     */
    public int regiFund(FundVO fundVO) {
        return insert("Fund.regiFund", fundVO);
    }

    /**
     * 모태펀드 수정
     */
    public int updtFund(FundVO fundVO) {
        return update("Fund.updtFund", fundVO);
    }

    /**
     * 모태펀드 삭제
     */
    public int deltFund(FundVO fundVO) {
        return delete("Fund.deltFund", fundVO);
    }
    
    
    
    /**
     * 전체 조합원 페이징목록 조회
     */
    public PaginatedArrayList listInvt(FundVO fundVO, int currPage, int pageSize) {
        return pageList("Fund.listInvt", fundVO, currPage, pageSize);
    }

    /**
     * 전체 조합원 목록 조회
     */
    public List listInvt(FundVO fundVO) {
        return list("Fund.listInvt", fundVO);
    }
    
    
    
    /**
     * 특정 조합원 페이징목록 조회
     */
    public PaginatedArrayList listFundInvstr(FundVO fundVO, int currPage, int pageSize) {
        return pageList("Fund.listFundInvstr", fundVO, currPage, pageSize);
    }

    /**
     * 특정 조합원 목록 조회
     */
    public List listFundInvstr(FundVO fundVO) {
        return list("Fund.listFundInvstr", fundVO);
    }
    
    /**
     * 특정 조합원 등록
     */
    public int regiFundInvstr(FundVO fundVO) {
        return insert("Fund.regiFundInvstr", fundVO);
    }
    
    /**
     * 특정 조합원 조회
     */
    public int viewFundInvstr(FundVO fundVO) {
        return (int) view("Fund.viewFundInvstr", fundVO);
    }
    
    /**
     * 특정 조합원 삭제
     */
    public int deltFundInvstr(FundVO fundVO) {
        return insert("Fund.deltFundInvstr", fundVO);
    }
    
    /**
     * 특정 조합원 전체 삭제
     */
    public int deltFundInvstrAll(FundVO fundVO) {
        return insert("Fund.deltFundInvstrAll", fundVO);
    }
    
}