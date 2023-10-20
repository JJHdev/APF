 package business.sys.log.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.sys.log.service.LogVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 프로그램접속이력을 관리하는 DAO 클래스
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
 * @class   : AccessLogDAO
 * @author  : LSH
 * @since   : 2021.09.06
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("AccessLogDAO")
@SuppressWarnings({"all"})
public class AccessLogDAO extends BaseDAO {

    /**
     * 프로그램접속이력 페이징목록 조회
     */
    public PaginatedArrayList listAccessLog(LogVO logVO, int currPage, int pageSize) {
        return pageList("AccessLog.listAccessLog", logVO, currPage, pageSize);
    }

    /**
     * 프로그램접속이력 목록 조회
     */
    public List listAccessLog(LogVO logVO) {
        return list("AccessLog.listAccessLog", logVO);
    }

    /**
     * 프로그램접속이력 삭제
     */
    public int deltAccessLog(LogVO logVO) {
        return delete("AccessLog.deltAccessLog", logVO);
    }

}