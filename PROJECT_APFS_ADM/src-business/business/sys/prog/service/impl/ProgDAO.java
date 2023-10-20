 package business.sys.prog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.sys.prog.service.ProgVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 프로그램관리을 관리하는 DAO 클래스
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
 * @class   : ProgDAO
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("ProgDAO")
@SuppressWarnings({"all"})
public class ProgDAO extends BaseDAO {

    /**
     * 프로그램관리 페이징목록 조회
     */
    public PaginatedArrayList listProg(ProgVO progVO, int currPage, int pageSize) {
        return pageList("Prog.listProg", progVO, currPage, pageSize);
    }

    /**
     * 프로그램관리 목록 조회
     */
    public List listProg(ProgVO progVO) {
        return list("Prog.listProg", progVO);
    }

    /**
     * 프로그램관리 상세 조회
     */
    public ProgVO viewProg(ProgVO progVO) {
        return (ProgVO)view("Prog.viewProg", progVO);
    }

    /**
     * 프로그램관리 등록
     */
    public int regiProg(ProgVO progVO) {
        return insert("Prog.regiProg", progVO);
    }

    /**
     * 프로그램관리 수정
     */
    public int updtProg(ProgVO progVO) {
        return update("Prog.updtProg", progVO);
    }

    /**
     * 프로그램관리 삭제
     */
    public int deltProg(ProgVO progVO) {
        return delete("Prog.deltProg", progVO);
    }

}