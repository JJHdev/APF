package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.mypage.service.BookmarkService;
import business.usr.mypage.service.BookmarkVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 북마크정보 Service 구현 클래스
 * 
 * @class   : BookmarkServiceImpl
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("BookmarkService")
@SuppressWarnings({"all"})
public class BookmarkServiceImpl extends BaseService implements BookmarkService {

    @Resource(name = "BookmarkDAO")
    private BookmarkDAO bookmarkDAO;

    /**
     * 북마크정보 상세조회
     */
	@Override
	public BookmarkVO viewBookmark(BookmarkVO bookmarkVO) throws Exception {
		return bookmarkDAO.viewBookmark(bookmarkVO);
	}

    /**
     * 북마크정보 등록,수정,삭제
     */
	@Override
	public String saveBookmark(BookmarkVO bookmarkVO) throws Exception {
		
		if (bookmarkVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = bookmarkVO.getMode();
		
		if (CommConst.INSERT.equals(mode)) {
			// 북마크정보 등록
			ret = bookmarkDAO.regiBookmark(bookmarkVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 북마크정보 삭제
			ret = bookmarkDAO.deltBookmark(bookmarkVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}