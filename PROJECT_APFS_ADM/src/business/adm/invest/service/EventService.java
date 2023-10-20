package business.adm.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리 - 투자설명회등록 Service Interface
 * 
 * @class   : EventService
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일        수정자             수정내용
 *  --------   --------    --------------------------
 *   23.04.17    LHB             First Coding.
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
     * 투자설명회 등록,수정,삭제
     */
    public String saveEvent(EventVO eventVO) throws Exception;
    
    
    
    /**
     * 참여기업(투자자) 페이징목록조회
     */
    public PaginatedArrayList listEventInvt(EventVO eventVO, int currPage, int pageSize) throws Exception;
    
    /**
     * 참여기업(투자자) 목록조회
     */
    public List listEventInvt(EventVO eventVO) throws Exception;
    
    /**
     * 참여기업(투자자) 등록,수정,삭제
     */
    public String saveEventInvt(EventVO eventVO) throws Exception;
    
    
    /**
     * 참여기업(경영체) 페이징목록조회
     */
    public PaginatedArrayList listEventEnt(EventVO eventVO, int currPage, int pageSize) throws Exception;
    
    /**
     * 참여기업(경영체) 목록조회
     */
    public List listEventEnt(EventVO eventVO) throws Exception;
    
    /**
     * 참여기업(경영체) 등록,수정,삭제
     */
    public String saveEventEnt(EventVO eventVO, List<FileInfo> files) throws Exception;
    
    
    
    /**
     * 참여기업(경영체) 추가 가능 여부 확인
     */
    public boolean getEventEntExist(EventVO eventVO) throws Exception;
}