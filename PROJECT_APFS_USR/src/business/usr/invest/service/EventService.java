package business.usr.invest.service;

import java.util.List;

import business.usr.invest.service.EventVO;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리 - 투자설명회 Service Interface
 * 
 * @class   : EventService
 * @author  : KYW
 * @since   : 2023.06.23
 * @version : 1.0
 *
 *   수정일        수정자             수정내용
 *  --------   --------    --------------------------
 *   23.06.23    KYW             First Coding.
 */
@SuppressWarnings("all")
public interface EventService {

    /**
     * 투자설명회 페이징목록 조회
     */
    public PaginatedArrayList listEvent(EventVO eventVO, int currPage, int pageSize) throws Exception;

    /**
     * 투자설명회 목록조회
     */
    public List listEvent(EventVO eventVO) throws Exception;

    /**
     * 투자설명회 상세조회
     */
    public EventVO viewEvent(EventVO eventVO) throws Exception;

    
    /**
     * 참여기업(경영체) 페이징목록조회
     */
    public PaginatedArrayList listEventEnt(EventVO eventVO, int currPage, int pageSize) throws Exception;
    
    /**
     * 참여기업(경영체) 목록조회
     */
    public List listEventEnt(EventVO eventVO) throws Exception;
   
}