 package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.mypage.service.MatchingVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 매칭설정관리을 관리하는 DAO 클래스
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
 * @class   : MatchingDAO
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("MatchingDAO")
@SuppressWarnings({"all"})
public class MatchingDAO extends BaseDAO {

    /**
     * 매칭설정관리 목록 조회
     */
    public List listMatching(MatchingVO matchingVO) {
        return list("Matching.listMatching", matchingVO);
    }

    /**
     * 매칭설정관리 등록
     */
    public int regiMatching(MatchingVO matchingVO) {
        return insert("Matching.regiMatching", matchingVO);
    }

    /**
     * 매칭설정 사용자기준 삭제
     */
    public int deltMatchingAll(String userNo) {
        return delete("Matching.deltMatchingAll", userNo);
    }

    /**
     * 매칭설정여부 확인
     */
    public boolean existMatching(String userNo) {
        return (Boolean)view("Matching.existMatching", userNo);
    }
    

}