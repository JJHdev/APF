package business.usr.mypage.service;

import java.util.List;

/**
 * [서비스인터페이스] - 마이페이지 Service Interface
 * 
 * @class   : MyPageService
 * @author  : LSH
 * @since   : 2023.06.19
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface MyPageService {

    /**
     * 마이페이지 신청내역 건수목록조회
     */
    public List listAplyGroup(MyPageVO myPageVO) throws Exception;
}