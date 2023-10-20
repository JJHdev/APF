package business.usr.inform.service;

import java.util.ArrayList;
import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 게시판 공통 Service Interface
 * 
 * @class   : BbsService
 * @author  : JH
 * @since   : 2023.08.07
 * @version : 1.1
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
     * 게시판 탭목록 조회
     */
    public List listBbsTab(BbsVO bbsVO) throws Exception;

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
     * 1:1문의 유형 코드 조회
     */
	public List getSysCodeData(BbsVO bbsVO) throws Exception;
    /**
     * 우수투자사례 상세조회 이미지 번호 조회
     */
	public BbsVO getBbsFileSn(BbsVO bbsVO) throws Exception;

    /**
     * 홍보영상 유튜브 목록 업데이트 유무 조회
     */
	public int listBbsPromotionCount(BbsVO bbsVO) throws Exception;

    /**
     * 조회수 증가
     */
	public int updtBbsInqCnt(BbsVO bbsVO) throws Exception;

    /**
     * 유튜브영상 최근 40개 삭제 되었는지 (유튜브 홈페이지에서 삭제시 DB에서 삭제용 List 조회)
     */
	public List<BbsVO> listPromotionBbs(BbsVO bbsVO) throws Exception;
	
	//테스트용 홍보영상 전부 지우고 넣기
	public void deleteTestBbs() throws Exception;

	/**
	 * 홍보영상, 제목, 조회수 최신화
	 */
	public String updtPromotion(BbsVO bbsVO, List<FileInfo> files) throws Exception;

	/**
	 * 홍보영상 같은게 있는지 체크하기.
	 */
	public List<BbsVO> findDuplicatesByName(BbsVO bbsDataVO) throws Exception;

	/**
	 * 홍보영상 같은 제목 있을시 임의로 삭제 요청
	 */
	public void deletePromotion(BbsVO bbsVO, List<FileInfo> files) throws Exception;
	
}