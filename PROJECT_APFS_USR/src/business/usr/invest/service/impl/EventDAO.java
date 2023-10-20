package business.usr.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EventVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자정보관리 - 투자설명회등록을 관리하는 DAO 클래스
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
 * @class   : EventDAO
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EventDAO")
@SuppressWarnings({"all"})
public class EventDAO extends BaseDAO {
	
    /**
     * 투자설명회 페이징목록 조회
     */
    public PaginatedArrayList listEvent(EventVO eventVO, int currPage, int pageSize) {
        return pageList("Event.listEvent", eventVO, currPage, pageSize);
    }

    /**
     * 투자설명회 목록 조회
     */
    public List listEvent(EventVO eventVO) {
        return list("Event.listEvent", eventVO);
    }

    /**
     * 투자설명회 상세 조회
     */
    public EventVO viewEvent(EventVO eventVO) {
        return (EventVO) view("Event.viewEvent", eventVO);
    }

    
    /**
     * 참여기업(경영체) 페이징목록 조회
     */
    public PaginatedArrayList listEventEnt(EventVO eventVO, int currPage, int pageSize) {
        return pageList("Event.listEventEnt", eventVO, currPage, pageSize);
    }

    /**
     * 참여기업(경영체) 목록 조회
     */
    public List listEventEnt(EventVO eventVO) {
        return list("Event.listEventEnt", eventVO);
    }
    
}