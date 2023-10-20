package business.adm.inform.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 게시판 Service Interface
 * 
 * @class   : BbsService
 * @author  : JH
 * @since   : 2023.07.11
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface BbsService {

    /**
     * 게시판 페이징목록 조회
     */
    public PaginatedArrayList listBbs(BbsVO bbsVO, int currPage, int pageSize) throws Exception;

    /**
     * 게시판 목록조회
     */
    public List listBbs(BbsVO bbsVO) throws Exception;

    /**
     * 게시판 상세조회
     */
    public BbsVO viewBbs(BbsVO bbsVO) throws Exception;

    /**
     * 게시판 등록,수정,삭제
     */
    public String saveBbs(BbsVO bbsVO, List<FileInfo> files) throws Exception;

    /**
     * 1:1문의 관리자 답변 조회
     */
	public BbsVO viewQnaBbs(BbsVO data) throws Exception;
    
	/**
	 * 게시판 다음 글번호 조회
	 */
	public BbsVO nextBbs(BbsVO bbsVO) throws Exception;
	
	/**
     * 게시판 이전 글번호 조회
     */
	public BbsVO beforeBbs(BbsVO bbsVO) throws Exception;
	
	/**
     * 게시판 ComboBox코드 조회
     */
	public List getSysCodeData(BbsVO bbsVO) throws Exception;

	/**
     * 검색어 관리 페이징목록 조회
     */
	public PaginatedArrayList getListSrchWord(SrchWordVO srchWordVO, int page, int size) throws Exception;
	
    /**
     * 검색어 관리  조회
     */
    public List getListSrchWord(SrchWordVO srchWordVO) throws Exception;

    /**
     * 검색어 관리  삭제
     */
	public int deleteSrchWord(SrchWordVO srchWordVO) throws Exception;

    /**
     * 검색어 관리  엑셀다운로드
     */
	public List downSrchWordExcel(SrchWordVO srchWordVO) throws Exception;
}