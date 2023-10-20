package business.usr.support.service;

import java.util.List;
import java.util.Map;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 투자지원신청 Service Interface
 * 
 * @class   : SprtService
 * @author  : LSH
 * @since   : 2023.05.22
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface SprtService {

    /**
     * 투자지원신청 페이징목록 조회
     */
    public PaginatedArrayList listSprt(SprtVO sprtVO, int currPage, int pageSize) throws Exception;

    /**
     * 투자지원신청 목록조회
     */
    public List listSprt(SprtVO sprtVO) throws Exception;

    /**
     * 투자지원신청 상세조회
     */
    public SprtVO viewSprt(String sprtAplyNo) throws Exception;

    /**
     * 투자지원신청 있는지 확인
     */
    public boolean existSprt(String sprtAplyNo);

    /**
     * 투자지원신청 등록,수정
     */
    public String saveSprt(SprtVO sprtVO) throws Exception;

    /**
     * 투자지원신청 제출서류 저장
     */
    public String saveFile(SprtVO sprtVO) throws Exception;

    /**
     * 투자지원신청 지원사업 목록조회
     */
    public List listPrgrm(SprtVO sprtVO) throws Exception;
    
    /**
     * 투자지원신청 지원사업 상세 조회
     */
    public Map viewPrgrm(SprtVO sprtVO) throws Exception;

    /**
     * 투자지원신청 프로그램 있는지 확인
     */
    public boolean existPrgrm(String prgrmNo);
    
    /**
     * 투자지원신청 지원사업 명칭 조회
     */
    public String getPrgrmName(String prgrmNo) throws Exception;
    
    /**
     * 투자지원신청 매출액 목록조회
     */
    public List listSprtFnnr(SprtVO sprtVO) throws Exception;
    
    /**
     * 투자지원신청 투자조합정보 목록조회
     */
    public List listSprtMxtr(SprtVO sprtVO) throws Exception;
    
    /**
     * 마이페이지 - 신청내역 - 지원사업진행현황 목록조회
     */
    public List listSprtPrgre(String sprtAplyNo) throws Exception;
}