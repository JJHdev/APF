 package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.support.service.PbancVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 사업공고관리을 관리하는 DAO 클래스
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
 * @class   : PbancDAO
 * @author  : LSH
 * @since   : 2023.04.30
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("PbancDAO")
@SuppressWarnings({"all"})
public class PbancDAO extends BaseDAO {

    /**
     * 사업공고관리 페이징목록 조회
     */
    public PaginatedArrayList listPbanc(PbancVO pbancVO, int currPage, int pageSize) {
        return pageList("Pbanc.listPbanc", pbancVO, currPage, pageSize);
    }

    /**
     * 사업공고관리 목록 조회
     */
    public List listPbanc(PbancVO pbancVO) {
        return list("Pbanc.listPbanc", pbancVO);
    }

    /**
     * 사업공고관리 상세 조회
     */
    public PbancVO viewPbanc(PbancVO pbancVO) {
        return (PbancVO)view("Pbanc.viewPbanc", pbancVO);
    }

    /**
     * 사업공고관리 등록
     */
    public int regiPbanc(PbancVO pbancVO) {
        return insert("Pbanc.regiPbanc", pbancVO);
    }

    /**
     * 사업공고관리 수정
     */
    public int updtPbanc(PbancVO pbancVO) {
        return update("Pbanc.updtPbanc", pbancVO);
    }

    /**
     * 사업공고관리 삭제
     */
    public int deltPbanc(PbancVO pbancVO) {
        return delete("Pbanc.deltPbanc", pbancVO);
    }

}