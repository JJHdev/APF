package business.usr.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 북마크정보 Service Interface
 * 
 * @class   : BookmarkService
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface BookmarkService {

    /**
     * 북마크정보 상세조회
     */
    public BookmarkVO viewBookmark(BookmarkVO bookmarkVO) throws Exception;

    /**
     * 북마크정보 등록,수정,삭제
     */
    public String saveBookmark(BookmarkVO bookmarkVO) throws Exception;
}