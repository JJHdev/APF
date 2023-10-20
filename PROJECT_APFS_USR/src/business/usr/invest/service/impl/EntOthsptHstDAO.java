 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 정부기타지원이력을 관리하는 DAO 클래스
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
 * @class   : EntOthsptHstDAO
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntOthsptHstDAO")
@SuppressWarnings({"all"})
public class EntOthsptHstDAO extends BaseDAO {

    /**
     * 정부기타지원이력 목록 조회
     */
    public List listEntOthsptHst(EntVO entVO) {
        return list("EntOthsptHst.listEntOthsptHst", entVO);
    }

    /**
     * 정부지원이력 기관별 합계건수 목록조회
     */
    public List listEntOthsptHstSummary(EntVO entVO) {
        return list("EntOthsptHst.listEntOthsptHstSummary", entVO);
    }

    /**
     * 정부지원이력 기관별 년도별 건수 목록조회
     */
    public List listEntOthsptHstYears(EntVO entVO) {
        return list("EntOthsptHst.listEntOthsptHstYears", entVO);
    }

    /**
     * 정부기타지원이력 상세 조회
     */
    public EntVO viewEntOthsptHst(EntVO entVO) {
        return (EntVO)view("EntOthsptHst.viewEntOthsptHst", entVO);
    }

    /**
     * 정부기타지원이력 등록
     */
    public int regiEntOthsptHst(EntVO entVO) {
        return insert("EntOthsptHst.regiEntOthsptHst", entVO);
    }

    /**
     * 정부기타지원이력 수정
     */
    public int updtEntOthsptHst(EntVO entVO) {
        return update("EntOthsptHst.updtEntOthsptHst", entVO);
    }

    /**
     * 정부기타지원이력 삭제
     */
    public int deltEntOthsptHst(EntVO entVO) {
        return delete("EntOthsptHst.deltEntOthsptHst", entVO);
    }

}