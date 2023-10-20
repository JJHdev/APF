 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 경영진정보을 관리하는 DAO 클래스
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
 * @class   : EntMgmtDAO
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntMgmtDAO")
@SuppressWarnings({"all"})
public class EntMgmtDAO extends BaseDAO {

    /**
     * 경영진정보 목록 조회
     */
    public List listEntMgmt(EntVO entVO) {
        return list("EntMgmt.listEntMgmt", entVO);
    }

    /**
     * 경영진정보 상세 조회
     */
    public EntVO viewEntMgmt(EntVO entVO) {
        return (EntVO)view("EntMgmt.viewEntMgmt", entVO);
    }

    /**
     * 경영진정보 등록
     */
    public int regiEntMgmt(EntVO entVO) {
        return insert("EntMgmt.regiEntMgmt", entVO);
    }

    /**
     * 경영진정보 수정
     */
    public int updtEntMgmt(EntVO entVO) {
        return update("EntMgmt.updtEntMgmt", entVO);
    }

    /**
     * 경영진정보 삭제
     */
    public int deltEntMgmt(EntVO entVO) {
        return delete("EntMgmt.deltEntMgmt", entVO);
    }

}