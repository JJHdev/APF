package business.adm.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리-IR검토의견서관리 Service Interface
 * 
 * @class   : IrOpnnService
 * @author  : LHB
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일        수정자             수정내용
 *  --------   --------    --------------------------
 *   23.06.07    LHB            First Coding.
 */
@SuppressWarnings("all")
public interface IrOpnnService {
	
	/**
     * IR검토의견서관리 투자자 페이징목록 조회
     */
    public PaginatedArrayList listIrOpnnInvt(EventVO eventVO, int currPage, int pageSize) throws Exception;

    /**
     * IR검토의견서관리 투자자 목록조회
     */
    public List listIrOpnnInvt(EventVO eventVO) throws Exception;
    
    /**
     * IR검토의견서관리 경영체 페이징목록 조회
     */
    public PaginatedArrayList listIrOpnnEnt(EventVO eventVO, int currPage, int pageSize) throws Exception;

    /**
     * IR검토의견서관리 경영체 목록조회
     */
    public List listIrOpnnEnt(EventVO eventVO) throws Exception;
    
    

    /**
     * IR검토의견서관리 의견서 페이징목록 조회
     */
    public PaginatedArrayList listIrOpnn(EventVO eventVO, int currPage, int pageSize) throws Exception;

    /**
     * IR검토의견서관리 의견서 목록조회
     */
    public List listIrOpnn(EventVO eventVO) throws Exception;

    /**
     * IR검토의견서관리 의견서 상세조회
     */
    public EventVO viewIrOpnn(EventVO eventVO) throws Exception;
    
}