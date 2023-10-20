package business.usr.mypage.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.usr.mypage.service.BookmarkService;
import business.usr.mypage.service.BookmarkVO;
import common.base.BaseController;

/**
 * [컨트롤러클래스] - 북마크정보 Controller
 * 
 * @class   : BookmarkController
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class BookmarkController extends BaseController {

    @Resource(name="BookmarkService")
    protected BookmarkService bookmarkService;

    /**
     * 북마크정보 조회JSON 반환
     */
    @RequestMapping("/usr/mypage/bookmark/getBookmark.do")
    @ResponseBody
    public Map getBookmark(ModelMap model
            , @ModelAttribute BookmarkVO bookmarkVO) throws Exception {

        BookmarkVO obj = bookmarkService.viewBookmark(bookmarkVO);
        return getSuccess(obj);
    }

    /**
     * 북마크정보 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/usr/mypage/bookmark/saveBookmark.do")
    @ResponseBody
    public Map saveBookmark(HttpServletRequest request, @ModelAttribute BookmarkVO bookmarkVO) throws Exception {
    	
    	setGlobalSessionVO(request, bookmarkVO);

        // 북마크정보를 저장한다.
    	String result = bookmarkService.saveBookmark(bookmarkVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, BookmarkVO bookmarkVO) {
    	
        setGlobalSession(request, bookmarkVO);

        if (bookmarkVO.getUserInfo(request) != null) {
        	bookmarkVO.setGsUserNo  (bookmarkVO.getUserInfo(request).getUserNo  ());
        	bookmarkVO.setGsRoleId  (bookmarkVO.getUserInfo(request).getRoleId  ());
        	bookmarkVO.setGsBzentyNo(bookmarkVO.getUserInfo(request).getBzentyNo());
        }
    }
    
}
