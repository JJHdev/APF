package business.adm.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리 - 모태펀드등록 Service Interface
 * 
 * @class   : FundService
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface FundService {

    /**
     * 모태펀드 페이징목록 조회
     */
    public PaginatedArrayList listFund(FundVO fundVO, int currPage, int pageSize) throws Exception;

    /**
     * 모태펀드 목록조회
     */
    public List listFund(FundVO fundVO) throws Exception;

    /**
     * 모태펀드 상세조회
     */
    public FundVO viewFund(FundVO fundVO) throws Exception;

    /**
     * 모태펀드 등록,수정,삭제
     */
    public String saveFund(FundVO fundVO) throws Exception;
    
    
    
    /**
     * 전체 조합원 페이징목록조회
     */
    public PaginatedArrayList listInvt(FundVO fundVo, int currPage, int pageSize) throws Exception;
    
    /**
     * 전체 조합원 목록조회
     */
    public List listInvt(FundVO fundVo) throws Exception;
    
    
    
    /**
     * 특정 조합원 페이징목록조회
     */
    public PaginatedArrayList listFundInvstr(FundVO fundVo, int currPage, int pageSize) throws Exception;
    
    /**
     * 특정 조합원 목록조회
     */
    public List listFundInvstr(FundVO fundVo) throws Exception;
    
    /**
     * 조합원 등록,삭제
     */
	public String saveFundInvstr(FundVO fundVo) throws Exception;
}