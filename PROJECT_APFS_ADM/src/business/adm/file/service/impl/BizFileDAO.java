 package business.adm.file.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.adm.file.service.BizFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 업무첨부파일을 관리하는 DAO 클래스
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
 * @class   : BizFileDAO
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("BizFileDAO")
@SuppressWarnings({"all"})
public class BizFileDAO extends BaseDAO {

    /**
     * 업무첨부파일 목록 조회
     */
    public List listBizFile(BizFileVO bizFileVO) {
        return list("BizFile.listBizFile", bizFileVO);
    }

    /**
     * 업무첨부파일 상세 조회
     */
    public BizFileVO viewBizFile(Long sn) {
        return (BizFileVO)view("BizFile.viewBizFile", sn);
    }

    /**
     * 업무첨부파일 등록
     */
    public int regiBizFile(BizFileVO bizFileVO) {
        return insert("BizFile.regiBizFile", bizFileVO);
    }

    /**
     * 업무첨부파일 수정
     */
    public int updtBizFile(BizFileVO bizFileVO) {
        return update("BizFile.updtBizFile", bizFileVO);
    }

    /**
     * 업무첨부파일 삭제
     */
    public int deltBizFile(BizFileVO bizFileVO) {
        return update("BizFile.deltBizFile", bizFileVO);
    }

    /**
     * 업무첨부파일 실제삭제
     */
    public int deltBizFileActually(Long sn) {
        return delete("BizFile.deltBizFileActually", sn);
    }

    /**
     * 서류양식 목록 조회
     */
    public List listPape(BizFileVO bizFileVO) {
        return list("BizFile.listPape", bizFileVO);
    }
    
    /**
     * 서류양식그룹 목록 조회
     */
    public List listPapeGroup(BizFileVO bizFileVO) {
        return list("BizFile.listPapeGroup", bizFileVO);
    }

    /**
     * 서류양식 상세 조회
     */
    public BizFileVO viewPape(String dcmntCd) {
        return (BizFileVO)view("BizFile.viewPape", dcmntCd);
    }

}