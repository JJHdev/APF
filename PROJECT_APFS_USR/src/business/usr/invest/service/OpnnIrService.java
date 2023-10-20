package business.usr.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 마이페이지-IR검토의견 관리 Service Interface
 * 
 * @class   : OpnnIrService
 * @author  : LHB
 * @since   : 2023.06.28
 * @version : 1.0
 *
 *   수정일        수정자             수정내용
 *  --------   --------    --------------------------
 *   23.06.28    KYW            First Coding.
 */
@SuppressWarnings("all")
public interface OpnnIrService {

    /**
     * IR검토의견서관리 페이징목록 조회
     */
    public PaginatedArrayList listOpnnIr(EventVO eventVO, int currPage, int pageSize) throws Exception;

    /**
     * IR검토의견서관리 목록조회
     */
    public List listOpnnIr(EventVO eventVO) throws Exception;

    /**
     * IR검토의견서관리 상세조회
     */
    public EventVO viewOpnnIr(EventVO eventVO) throws Exception;
    
    /**
     * IR검토의견서관리 저장
     */
    public String saveOpnnIr(EventVO eventVO) throws Exception;
    
}