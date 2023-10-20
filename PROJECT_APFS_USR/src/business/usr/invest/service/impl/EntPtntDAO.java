 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 특허상표권현황을 관리하는 DAO 클래스
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
 * @class   : EntPtntDAO
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntPtntDAO")
@SuppressWarnings({"all"})
public class EntPtntDAO extends BaseDAO {

    /**
     * 특허상표권현황 페이징목록 조회
     */
    public PaginatedArrayList listEntPtnt(EntVO entVO, int currPage, int pageSize) {
        return pageList("EntPtnt.listEntPtnt", entVO, currPage, pageSize);
    }

    /**
     * 특허상표권현황 목록 조회
     */
    public List listEntPtnt(EntVO entVO) {
        return list("EntPtnt.listEntPtnt", entVO);
    }

    /**
     * 특허상표권현황 구분별 합계건수 목록조회
     */
    public List listEntPtntSummary(EntVO entVO) {
        return list("EntPtnt.listEntPtntSummary", entVO);
    }

    /**
     * 특허상표권현황 상세 조회
     */
    public EntVO viewEntPtnt(EntVO entVO) {
        return (EntVO)view("EntPtnt.viewEntPtnt", entVO);
    }

    /**
     * 특허상표권현황 등록
     */
    public int regiEntPtnt(EntVO entVO) {
        return insert("EntPtnt.regiEntPtnt", entVO);
    }

    /**
     * 특허상표권현황 수정
     */
    public int updtEntPtnt(EntVO entVO) {
        return update("EntPtnt.updtEntPtnt", entVO);
    }

    /**
     * 특허상표권현황 삭제
     */
    public int deltEntPtnt(EntVO entVO) {
        return delete("EntPtnt.deltEntPtnt", entVO);
    }

    /**
     * 2023.07.24 LSH
     * 특허상표권현황 KODATA 등록여부 조회
     */
    public boolean existKodata(String bzentyNo) {
        return (Boolean)view("EntPtnt.existKodata", bzentyNo);
    }
}