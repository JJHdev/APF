package business.usr.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.usr.file.service.BizFileService;
import business.usr.invest.service.EventService;
import business.usr.invest.service.EventVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;

/**
 * [서비스클래스] - 투자정보관리 - 투자설명회 Service 구현 클래스
 * 
 * @class   : EventServiceImpl
 * @author  : KYW
 * @since   : 2023.06.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *	23.06.23    KYW             First Coding.
 */

@Service("EventService")
@SuppressWarnings({"all"})
public class EventServiceImpl extends BaseService implements EventService {

    @Resource(name = "EventDAO")
    private EventDAO eventDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * 투자설명회 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEvent(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return eventDAO.listEvent(eventVO, currPage, pageSize);
    }

    /**
     * 투자설명회 목록조회
     */
    @Override
    public List listEvent(EventVO eventVO) throws Exception {
    	return eventDAO.listEvent(eventVO);
    }

    /**
     * 투자설명회 상세조회
     */
	@Override
	public EventVO viewEvent(EventVO eventVO) throws Exception {
		return eventDAO.viewEvent(eventVO);
	}

	/**
     * 참여기업(경영체) 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEventEnt(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return eventDAO.listEventEnt(eventVO, currPage, pageSize);
    }
    
    /**
     * 참여기업(경영체) 목록조회
     */
    @Override
    public List listEventEnt(EventVO eventVO) throws Exception {
    	return eventDAO.listEventEnt(eventVO);
    }
	
}