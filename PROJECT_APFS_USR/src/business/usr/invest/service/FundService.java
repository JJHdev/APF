package business.usr.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 펀드정보(모태펀드) Service Interface
 * 
 * @class   : FundService
 * @author  : LSH
 * @since   : 2023.04.21
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface FundService {

    /**
     * 펀드정보(모태펀드) 페이징목록 조회
     */
    public PaginatedArrayList listFund(FundVO fundVO, int currPage, int pageSize) throws Exception;

    /**
     * 펀드정보(모태펀드) 목록조회
     */
    public List listFund(FundVO fundVO) throws Exception;

    /**
     * 펀드정보(모태펀드) 상세조회
     */
    public FundVO viewFund(String fundNo) throws Exception;

    /**
     * 펀드정보(모태펀드) 등록,수정,삭제
     */
    public String saveFund(FundVO fundVO) throws Exception;

    /**
     * 투자자기준 경영체의 펀드지원정보(IR지원) 페이징목록 조회
     */
    public PaginatedArrayList listFundSprt(FundVO fundVO, int currPage, int pageSize) throws Exception;

    /**
     * 투자자기준 경영체의 펀드지원정보(IR지원) 목록조회
     */
    public List listFundSprt(FundVO fundVO) throws Exception;

    /**
     * 펀드지원정보(IR지원) 상세조회
     */
    public FundVO viewFundSprt(FundVO fundVO) throws Exception;

    /**
     * 경영체 펀드지원하기 등록처리
     */
    public String registFundSprt(FundVO fundVO, List<FileInfo> files) throws Exception;

    /**
     * 마이페이지(투자자) - 경영체지원현황 - 상태변경처리 (다중처리)
     */
    public String updateFundSprt(FundVO fundVO) throws Exception;

    /**
     * 펀드투자자(조합원) 페이징목록 조회
     */
    public PaginatedArrayList listFundInvstr(FundVO fundVO, int currPage, int pageSize) throws Exception;

    /**
     * 펀드투자자(조합원) 목록조회
     */
    public List listFundInvstr(FundVO fundVO) throws Exception;

    /**
     * 펀드투자자(조합원) 상세조회
     */
    public FundVO viewFundInvstr(FundVO fundVO) throws Exception;

    /**
     * 펀드투자자(조합원) 등록,수정,삭제
     */
    public String saveFundInvstr(FundVO fundVO) throws Exception;

    /**
     * 펀드조합 업체번호 기준 펀드및조합정보 목록조회
     */
    public List listFundByBzenty(String bzentyNo) throws Exception;
}