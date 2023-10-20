 package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.mypage.service.BookmarkVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 북마크정보을 관리하는 DAO 클래스
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
 * @class   : BookmarkDAO
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("BookmarkDAO")
@SuppressWarnings({"all"})
public class BookmarkDAO extends BaseDAO {

    /**
     * 북마크정보 상세 조회
     */
    public BookmarkVO viewBookmark(BookmarkVO bookmarkVO) {
        return (BookmarkVO)view("Bookmark.viewBookmark", bookmarkVO);
    }

    /**
     * 북마크정보 등록
     */
    public int regiBookmark(BookmarkVO bookmarkVO) {
        return insert("Bookmark.regiBookmark", bookmarkVO);
    }

    /**
     * 북마크정보 삭제
     */
    public int deltBookmark(BookmarkVO bookmarkVO) {
        return delete("Bookmark.deltBookmark", bookmarkVO);
    }

}