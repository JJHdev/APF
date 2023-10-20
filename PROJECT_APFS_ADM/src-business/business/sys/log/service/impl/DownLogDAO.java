 package business.sys.log.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.sys.log.service.LogVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 다운로드이력을 관리하는 DAO 클래스
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
 * @class   : DownLogDAO
 * @author  : LSH
 * @since   : 2021.11.04
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("DownLogDAO")
@SuppressWarnings({"all"})
public class DownLogDAO extends BaseDAO {

    /**
     * 다운로드이력 페이징목록 조회
     */
    public PaginatedArrayList listDownLog(LogVO logVO, int currPage, int pageSize) {
        return pageList("DownLog.listDownLog", logVO, currPage, pageSize);
    }

    /**
     * 다운로드이력 목록 조회
     */
    public List listDownLog(LogVO logVO) {
        return list("DownLog.listDownLog", logVO);
    }

    /**
     * 다운로드이력 등록
     */
    public int regiDownLog(LogVO logVO) {
        return insert("DownLog.regiDownLog", logVO);
    }

    /**
     * 다운로드이력 삭제
     */
    public int deltDownLog(LogVO logVO) {
        return delete("DownLog.deltDownLog", logVO);
    }
    
    /**
     * 대상파일 키 목록조회
     * logVO.dtySeCd   : 업무구분코드
     * logVO.dcmtNo    : 업무번호
     * logVO.dtlDcmtNo : 업무세부번호
     */
    public List listDownFile(LogVO logVO) {
        return list("DownLog.listDownFile", logVO);
    }
}