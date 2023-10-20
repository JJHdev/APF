 package business.adm.support.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.support.service.PbancVO;
import business.adm.support.service.SupportProgressVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 운영관리 - 사업공고를 관리하는 DAO 클래스
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
 * @author  : LHB
 * @since   : 2023.04.24
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
     * 세부지원사업관리 페이징목록 조회
     */
    public PaginatedArrayList listPbanc(PbancVO pbancVO, int currPage, int pageSize) {
        return pageList("Support.listPbanc", pbancVO, currPage, pageSize);
    }

    /**
     * 세부지원사업관리 목록 조회
     */
    public List listPbanc(PbancVO pbancVO) {
        return list("Support.listPbanc", pbancVO);
    }

    /**
     * 세부지원사업관리 상세 조회
     */
    public PbancVO viewPbanc(PbancVO pbancVO) {
        return (PbancVO) view("Support.viewPbanc", pbancVO);
    }

    /**
     * 세부지원사업관리 등록
     */
    public int regiPbanc(PbancVO pbancVO) {
        return insert("Support.regiPbanc", pbancVO);
    }

    /**
     * 세부지원사업관리 수정
     */
    public int updtPbanc(PbancVO pbancVO) {
        return update("Support.updtPbanc", pbancVO);
    }

    /**
     * 세부지원사업관리 삭제
     */
    public int deltPbanc(PbancVO pbancVO) {
        return delete("Support.deltPbanc", pbancVO);
    }
    
}