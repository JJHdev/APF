package business.usr.mypage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.usr.mypage.service.MyPageService;
import business.usr.mypage.service.MyPageVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 마이페이지 Service 구현 클래스
 * 
 * @class   : MyPageServiceImpl
 * @author  : LSH
 * @since   : 2023.06.19
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("MyPageService")
@SuppressWarnings({"all"})
public class MyPageServiceImpl extends BaseService implements MyPageService {

    @Resource(name = "MyPageDAO")
    private MyPageDAO myPageDAO;

    /**
     * 마이페이지 신청내역 건수조회
     */
	@Override
    public List listAplyGroup(MyPageVO myPageVO) throws Exception {
    	return myPageDAO.listAplyGroup(myPageVO);
    }
}