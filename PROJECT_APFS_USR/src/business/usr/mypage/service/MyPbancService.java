package business.usr.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 사업공고관리 Service Interface
 * 
 * @class   : PbancService
 * @author  : LSH
 * @since   : 2023.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface MyPbancService {

    /**
     * 마이페이지 사업공고관리 페이징목록 조회
     */
    public PaginatedArrayList listPbanc(MyPbancVO myPbancVO, int currPage, int pageSize) throws Exception;

    /**
     * 마이페이지 사업공고관리 목록조회
     */
    public List listPbanc(MyPbancVO myPbancVO) throws Exception;

    /**
     * 마이페이지 사업공고관리 상세조회
     */
    public MyPbancVO viewPbanc(MyPbancVO myPbancVO) throws Exception;

    /**
     * 마이페이지 사업공고관리 등록,수정,삭제
     */
    public String savePbanc(MyPbancVO myPbancVO, List<FileInfo> files) throws Exception;

    /**
     * 마이페이지 사업공고관리 북마크 구분 탭 조회
     */
	public List listPbancTab(MyPbancVO myPbancVO);

    /**
     * 마이페이지 사업공고관리 세션 등록기관명 조회
     */
	public MyPbancVO viewCrdnsBzentyNm(MyPbancVO myPbancVO);
}