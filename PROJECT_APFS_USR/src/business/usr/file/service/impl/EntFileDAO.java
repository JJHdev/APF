 package business.usr.file.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.file.service.EntFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 업체첨부파일을 관리하는 DAO 클래스
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
 * @class   : EntFileDAO
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntFileDAO")
@SuppressWarnings({"all"})
public class EntFileDAO extends BaseDAO {

    /**
     * 업체첨부파일 목록 조회
     */
    public List listEntFile(EntFileVO entFileVO) {
        return list("EntFile.listEntFile", entFileVO);
    }

    /**
     * 업체첨부파일 상세 조회
     */
    public EntFileVO viewEntFile(Long sn) {
        return (EntFileVO)view("EntFile.viewEntFile", sn);
    }

    /**
     * 업체첨부파일 등록
     */
    public int regiEntFile(EntFileVO entFileVO) {
        return insert("EntFile.regiEntFile", entFileVO);
    }

    /**
     * 업체첨부파일 수정
     */
    public int updtEntFile(EntFileVO entFileVO) {
        return update("EntFile.updtEntFile", entFileVO);
    }

    /**
     * 업체첨부파일 삭제
     */
    public int deltEntFile(EntFileVO entFileVO) {
        return update("EntFile.deltEntFile", entFileVO);
    }

}