 package business.usr.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 업체IR정보을 관리하는 DAO 클래스
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
 * @class   : EntIrDAO
 * @author  : LSH
 * @since   : 2023.05.09
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntIrDAO")
@SuppressWarnings({"all"})
public class EntIrDAO extends BaseDAO {

    /**
     * 업체IR정보 목록 조회
     */
    public List listEntIr(EntVO entVO) {
        return list("EntIr.listEntIr", entVO);
    }

    /**
     * 업체IR정보 상세 조회
     */
    public EntVO viewEntIr(EntVO entVO) {
        return (EntVO)view("EntIr.viewEntIr", entVO);
    }

    /**
     * 업체IR정보 등록
     */
    public int regiEntIr(EntVO entVO) {
        return insert("EntIr.regiEntIr", entVO);
    }

    /**
     * 업체IR정보 수정
     */
    public int updtEntIr(EntVO entVO) {
        return update("EntIr.updtEntIr", entVO);
    }

    /**
     * 2023.07.28 LSH
     * 업체IR정보 조회수증가
     */
    public int updtEntIrInqCnt(String irNo) {
        return update("EntIr.updtEntIrInqCnt", irNo);
    }

    /**
     * 업체IR정보 삭제
     */
    public int deltEntIr(EntVO entVO) {
        return delete("EntIr.deltEntIr", entVO);
    }
    
    /**
     * 업체IR정보 작성여부 확인
     */
    public boolean existEntIr(EntVO entVO) {
        return (Boolean)view("EntIr.existEntIr", entVO);
    }

    /**
     * 2023.07.28 LSH
     * 업체번호 기준 최종IR번호 조회
     * 
     * @param entVO.bzentyNo    업체번호
     * @param entVO.prgrsSttsCd 진행상태 (선택)
     */
    public String getLastEntIrNo(EntVO entVO) {
        return (String)view("EntIr.getLastEntIrNo", entVO);
    }
}