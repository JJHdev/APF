 package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.support.service.DscsnVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 상담신청을 관리하는 DAO 클래스
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
 * @class   : DscsnDAO
 * @author  : LSH
 * @since   : 2023.05.25
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("DscsnDAO")
@SuppressWarnings({"all"})
public class DscsnDAO extends BaseDAO {

    /**
     * 상담신청 페이징목록 조회
     */
    public PaginatedArrayList listDscsn(DscsnVO dscsnVO, int currPage, int pageSize) {
        return pageList("Dscsn.listDscsn", dscsnVO, currPage, pageSize);
    }

    /**
     * 상담신청 목록 조회
     */
    public List listDscsn(DscsnVO dscsnVO) {
        return list("Dscsn.listDscsn", dscsnVO);
    }

    /**
     * 상담신청 상세 조회
     */
    public DscsnVO viewDscsn(DscsnVO dscsnVO) {
        return (DscsnVO)view("Dscsn.viewDscsn", dscsnVO);
    }

    /**
     * 상담신청 있는지 확인
     */
    public boolean existDscsn(String dscsnAplyNo) {
        return (Boolean)view("Dscsn.existDscsn", dscsnAplyNo);
    }

    /**
     * 상담신청 등록
     */
    public int regiDscsn(DscsnVO dscsnVO) {
        return insert("Dscsn.regiDscsn", dscsnVO);
    }

    /**
     * 상담신청 수정
     */
    public int updtDscsn(DscsnVO dscsnVO) {
        return update("Dscsn.updtDscsn", dscsnVO);
    }

    /**
     * 상담신청 삭제
     */
    public int deltDscsn(DscsnVO dscsnVO) {
        return delete("Dscsn.deltDscsn", dscsnVO);
    }

}