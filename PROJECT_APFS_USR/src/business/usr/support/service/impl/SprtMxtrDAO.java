 package business.usr.support.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.support.service.SprtMxtrVO;
import business.usr.support.service.SprtVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자조합정보를 관리하는 DAO 클래스
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
 * @class   : SprtMxtrDAO
 * @author  : LSH
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("SprtMxtrDAO")
@SuppressWarnings({"all"})
public class SprtMxtrDAO extends BaseDAO {

    /**
     * 투자조합정보 목록 조회
     */
    public List listSprtMxtr(SprtVO sprtVO) {
        return list("SprtMxtr.listSprtMxtr", sprtVO);
    }

    /**
     * 투자조합정보 등록
     */
    public int regiSprtMxtr(SprtMxtrVO sprtMxtrVO) {
        return insert("SprtMxtr.regiSprtMxtr", sprtMxtrVO);
    }

    /**
     * 투자조합정보 삭제
     */
    public int deltSprtMxtr(SprtMxtrVO sprtMxtrVO) {
        return delete("SprtMxtr.deltSprtMxtr", sprtMxtrVO);
    }

}