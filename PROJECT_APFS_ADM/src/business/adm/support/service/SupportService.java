package business.adm.support.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리 - 모태펀드등록 Service Interface
 * 
 * @class   : SupportService
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface SupportService {

    /**
     * 세부지원사업관리 페이징목록 조회
     */
    public PaginatedArrayList listInvtSprt(SupportVO supportVO, int currPage, int pageSize) throws Exception;

    /**
     * 세부지원사업관리 목록조회
     */
    public List listInvtSprt(SupportVO supportVO) throws Exception;

    /**
     * 세부지원사업관리 상세조회
     */
    public SupportVO viewInvtSprt(SupportVO supportVO) throws Exception;

    /**
     * 세부지원사업관리 등록,수정,삭제
     */
    public String saveInvtSprt(SupportVO supportVO) throws Exception;
    
    
    
    /**
     * 신청현황 페이징목록 조회
     */
    public PaginatedArrayList listInvtSprtAply(SupportVO supportVO, int currPage, int pageSize) throws Exception;

    /**
     * 신청현황 목록조회
     */
    public List listInvtSprtAply(SupportVO supportVO) throws Exception;
    
    /**
     * 신청현황 상세조회
     */
    public SupportVO viewInvtSprtAply(SupportVO supportVO) throws Exception;
    
    /**
     * 신청현황 삭제
     */
    public String saveInvtSprtAply(SupportVO supportVO, List<FileInfo> files) throws Exception;

    /**
     * 신청현황 엑셀 목록조회
     */
    public List listInvtSprtAplyExcl(SupportVO supportVO) throws Exception;
    
    
    
    /**
     * 신청현황 매출액 페이징목록 조회
     */
    public PaginatedArrayList listInvtSprtSls(SupportVO supportVO, int currPage, int pageSize) throws Exception;

    /**
     * 신청현황 매출액 목록조회
     */
    public List listInvtSprtSls(SupportVO supportVO) throws Exception;
    
    
    
    /**
     * 농식품 투자조합 페이징목록 조회
     */
    public PaginatedArrayList listInvtMxtr(SupportVO supportVO, int currPage, int pageSize) throws Exception;

    /**
     * 농식품 투자조합 매출액 목록조회
     */
    public List listInvtMxtr(SupportVO supportVO) throws Exception;
    
    
    
    /**
     * 지원사업 진행현황 페이징목록 조회
     */
    public PaginatedArrayList listSprtBizPrgre(SupportVO supportVO, int currPage, int pageSize) throws Exception;

    /**
     * 지원사업 진행현황 목록조회
     */
    public List listSprtBizPrgre(SupportVO supportVO) throws Exception;
    
    /**
     * 지원사업 진행현황 등록,수정,삭제
     */
    public String saveSprtBizPrgre(SupportVO supportVO) throws Exception;

    //2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 데이터조회
	public SupportVO viewInvtSprtAplyFileInfo(SupportVO supportVO) throws Exception;
    
    //2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 메소드
	public String saveFile(SupportVO supportVO) throws Exception;
    
}