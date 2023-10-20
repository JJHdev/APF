 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 대표자이력을 관리하는 DAO 클래스
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
 * @class   : EntRprsvHstDAO
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntRprsvHstDAO")
@SuppressWarnings({"all"})
public class EntRprsvHstDAO extends BaseDAO {

    /**
     * 대표자이력 목록 조회
     */
    public List listEntRprsvHst(EntVO entVO) {
        return list("EntRprsvHst.listEntRprsvHst", entVO);
    }

    /**
     * 대표자이력 상세 조회
     */
    public EntVO viewEntRprsvHst(EntVO entVO) {
        return (EntVO)view("EntRprsvHst.viewEntRprsvHst", entVO);
    }

    /**
     * 대표자이력 등록
     */
    public int regiEntRprsvHst(EntVO entVO) {
        return insert("EntRprsvHst.regiEntRprsvHst", entVO);
    }

    /**
     * 대표자이력 수정
     */
    public int updtEntRprsvHst(EntVO entVO) {
        return update("EntRprsvHst.updtEntRprsvHst", entVO);
    }

    /**
     * 대표자이력 삭제
     */
    public int deltEntRprsvHst(EntVO entVO) {
        return delete("EntRprsvHst.deltEntRprsvHst", entVO);
    }

}