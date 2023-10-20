 package business.usr.mypage.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.mypage.service.MyPageVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 마이페이지 관리하는 DAO 클래스
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
 * @class   : MyPageDAO
 * @author  : LSH
 * @since   : 2023.06.19
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("MyPageDAO")
@SuppressWarnings({"all"})
public class MyPageDAO extends BaseDAO {

    /**
     * 마이페이지 신청내역 건수조회
     */
    public List listAplyGroup(MyPageVO myPageVO) {
        return list("MyPage.listAplyGroup", myPageVO);
    }
}