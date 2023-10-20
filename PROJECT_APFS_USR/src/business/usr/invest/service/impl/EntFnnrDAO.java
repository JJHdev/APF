 package business.usr.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 재무정보을 관리하는 DAO 클래스
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
 * @class   : EntFnnrDAO
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntFnnrDAO")
@SuppressWarnings({"all"})
public class EntFnnrDAO extends BaseDAO {

    /**
     * 재무정보 목록 조회
     */
    public List listEntFnnr(EntVO entVO) {
        return list("EntFnnr.listEntFnnr", entVO);
    }

    /**
     * 재무정보 요약 목록 조회
     */
    public List listEntFnnrSummary(EntVO entVO) {
        return list("EntFnnr.listEntFnnrSummary", entVO);
    }

    /**
     * 재무정보 세부 목록 조회
     */
    public List listEntFnnrDetails(EntVO entVO) {
        return list("EntFnnr.listEntFnnrDetails", entVO);
    }

    /**
     * 재무정보 년도별 목록 조회
     */
    public List listEntFnnrYear(EntVO entVO) {
        return list("EntFnnr.listEntFnnrYear", entVO);
    }
    
    /**
     * 재무정보 기준년도 조회
     */
    public String getEntFnnrYear(EntVO entVO) {
        return (String)view("EntFnnr.getEntFnnrYear", entVO);
    }

    /**
     * 재무정보 상세 조회
     */
    public EntVO viewEntFnnr(EntVO entVO) {
        return (EntVO)view("EntFnnr.viewEntFnnr", entVO);
    }

    /**
     * 재무정보 등록
     */
    public int regiEntFnnr(EntVO entVO) {
        return insert("EntFnnr.regiEntFnnr", entVO);
    }

    /**
     * 재무정보 수정
     */
    public int updtEntFnnr(EntVO entVO) {
        return update("EntFnnr.updtEntFnnr", entVO);
    }

    /**
     * 재무정보 삭제
     */
    public int deltEntFnnr(EntVO entVO) {
        return delete("EntFnnr.deltEntFnnr", entVO);
    }

    /**
     * 2023.07.24 LSH
     * 재무정보 KODATA 등록여부 조회
     */
    public boolean existKodata(String bzentyNo) {
        return (Boolean)view("EntFnnr.existKodata", bzentyNo);
    }

    /**
     * 2023.07.24 LSH
     * 재무정보 수정 목록 조회
     * 
     * @param entVO.irNo     IR번호
     * @param entVO.fnnrSeCd 재무구분코드
     * @param entVO.dataSeCd 데이터구분코드
     * @param entVO.fnnrType 합계계정 공통코드
     * 
     * fnnrYr,fnnrAmt1,fnnrAmt2,fnnrAmt3
     */
    public List listEntFnnrUpdates(EntVO entVO) {
        return list("EntFnnr.listEntFnnrUpdates", entVO);
    }

    /**
     * 2023.07.28 LSH
     * IR번호 기준 수정대상 재무정보목록 삭제
     * 
     * @param entVO.irNo     IR번호
     * @param entVO.dataSeCd 데이터구분코드 (M)
     */
    public int deltEntFnnrForUpdates(EntVO entVO) {
    	return delete("EntFnnr.deltEntFnnrForUpdates", entVO);
    }
}