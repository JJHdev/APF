 package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.support.service.SprtVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자지원신청을 관리하는 DAO 클래스
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
 * @class   : SprtDAO
 * @author  : LSH
 * @since   : 2023.05.22
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("SprtDAO")
@SuppressWarnings({"all"})
public class SprtDAO extends BaseDAO {

    /**
     * 투자지원신청 페이징목록 조회
     */
    public PaginatedArrayList listSprt(SprtVO sprtVO, int currPage, int pageSize) {
        return pageList("Sprt.listSprt", sprtVO, currPage, pageSize);
    }

    /**
     * 투자지원신청 목록 조회
     */
    public List listSprt(SprtVO sprtVO) {
        return list("Sprt.listSprt", sprtVO);
    }

    /**
     * 투자지원신청 상세 조회
     */
    public SprtVO viewSprt(String sprtAplyNo) {
        return (SprtVO)view("Sprt.viewSprt", sprtAplyNo);
    }

    /**
     * 투자지원신청 있는지 확인
     */
    public boolean existSprt(String sprtAplyNo) {
        return (Boolean)view("Sprt.existSprt", sprtAplyNo);
    }

    /**
     * 투자지원신청 등록
     */
    public int regiSprt(SprtVO sprtVO) {
        return insert("Sprt.regiSprt", sprtVO);
    }

    /**
     * 투자지원신청 수정
     */
    public int updtSprt(SprtVO sprtVO) {
        return update("Sprt.updtSprt", sprtVO);
    }

    /**
     * 투자지원신청 삭제
     */
    public int deltSprt(SprtVO sprtVO) {
        return delete("Sprt.deltSprt", sprtVO);
    }

    /**
     * 투자지원신청 지원사업 목록 조회
     */
    public List listPrgrm(SprtVO sprtVO) {
        return list("Sprt.listPrgrm", sprtVO);
    }

    /**
     * 투자지원신청 지원사업 상세 조회
     */
    public Map viewPrgrm(SprtVO sprtVO) {
        return (Map)view("Sprt.viewPrgrm", sprtVO);
    }

    /**
     * 투자지원신청 지원사업 있는지 확인
     */
    public boolean existPrgrm(String prgrmNo) {
        return (Boolean)view("Sprt.existPrgrm", prgrmNo);
    }

    /**
     * 마이페이지 - 신청내역 - 지원사업진행현황 목록조회
     */
    public List listSprtPrgre(String sprtAplyNo) {
        return list("Sprt.listSprtPrgre", sprtAplyNo);
    }
}