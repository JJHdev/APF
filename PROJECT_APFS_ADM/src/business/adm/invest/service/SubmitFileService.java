package business.adm.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리 - 모태펀드등록 Service Interface
 * 
 * @class   : SupportService
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface SubmitFileService {

    /**
     * 제출서류 관리 페이징목록 조회
     */
    public PaginatedArrayList listSubmitFile(SubmitFileVO submitFileVO, int currPage, int pageSize) throws Exception;

    /**
     * 제출서류 관리
     */
    public List listSubmitFile(SubmitFileVO submitFileVO) throws Exception;

    /**
     * 제출서류 조회
     */
	public SubmitFileVO viewSubmitFile(SubmitFileVO submitFileVO) throws Exception;

    /**
     * 제출서류 수정,등록,삭제
     */
	public String saveSubmitFile(SubmitFileVO submitFileVO) throws Exception;
    
}