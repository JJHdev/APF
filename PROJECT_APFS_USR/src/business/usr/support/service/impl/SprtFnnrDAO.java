 package business.usr.support.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.support.service.SprtFnnrVO;
import business.usr.support.service.SprtVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 매출정보을 관리하는 DAO 클래스
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
 * @class   : SprtFnnrDAO
 * @author  : LSH
 * @since   : 2023.05.28
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("SprtFnnrDAO")
@SuppressWarnings({"all"})
public class SprtFnnrDAO extends BaseDAO {

    /**
     * 매출정보 목록 조회
     */
    public List listSprtFnnr(SprtVO sprtVO) {
        return list("SprtFnnr.listSprtFnnr", sprtVO);
    }

    /**
     * 매출정보 등록
     */
    public int regiSprtFnnr(SprtFnnrVO sprtFnnrVO) {
        return insert("SprtFnnr.regiSprtFnnr", sprtFnnrVO);
    }

    /**
     * 매출정보 삭제
     */
    public int deltSprtFnnr(SprtFnnrVO sprtFnnrVO) {
        return delete("SprtFnnr.deltSprtFnnr", sprtFnnrVO);
    }

}