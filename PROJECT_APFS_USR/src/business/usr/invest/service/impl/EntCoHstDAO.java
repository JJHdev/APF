 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 회사연혁을 관리하는 DAO 클래스
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
 * @class   : EntCoHstDAO
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntCoHstDAO")
@SuppressWarnings({"all"})
public class EntCoHstDAO extends BaseDAO {

    /**
     * 회사연혁 목록 조회
     */
    public List listEntCoHst(EntVO entVO) {
        return list("EntCoHst.listEntCoHst", entVO);
    }

    /**
     * 회사연혁 상세 조회
     */
    public EntVO viewEntCoHst(EntVO entVO) {
        return (EntVO)view("EntCoHst.viewEntCoHst", entVO);
    }

    /**
     * 회사연혁 등록
     */
    public int regiEntCoHst(EntVO entVO) {
        return insert("EntCoHst.regiEntCoHst", entVO);
    }

    /**
     * 회사연혁 수정
     */
    public int updtEntCoHst(EntVO entVO) {
        return update("EntCoHst.updtEntCoHst", entVO);
    }

    /**
     * 회사연혁 삭제
     */
    public int deltEntCoHst(EntVO entVO) {
        return delete("EntCoHst.deltEntCoHst", entVO);
    }

}