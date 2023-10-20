package business.adm.support.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 운영관리-IR지원현황 Service Interface
 * 
 * @class   : IrService
 * @author  : LHB
 * @since   : 2023.06.08
 * @version : 1.0
 *
 *   수정일       수정자             수정내용
 *  --------   --------    ---------------------------
 *  23.06.08     LHB         First Coding.
 */
@SuppressWarnings("all")
public interface IrService {

    /**
     * 운영관리-IR지원현황 페이징목록 조회
     */
    public PaginatedArrayList listIr(IrVO irVO, int currPage, int pageSize) throws Exception;

    /**
     * 운영관리-IR지원현황 목록조회
     */
    public List listIr(IrVO irVO) throws Exception;
    
    /**
     * 운영관리-IR지원현황 상세조회
     */
    public IrVO viewIr(IrVO irVO) throws Exception;
    
    /**
     * 운영관리-IR지원현황 등록,수정,삭제
     */
	public String saveIr(IrVO irVO, List<FileInfo> files) throws Exception;

    
}