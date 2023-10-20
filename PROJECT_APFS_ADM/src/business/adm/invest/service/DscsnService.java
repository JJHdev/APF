package business.adm.invest.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 투자정보관리 - 모태펀드등록 Service Interface
 * 
 * @class   : DscsnService
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface DscsnService {

    /**
     * 상담일지 페이징목록 조회
     */
	public PaginatedArrayList listDscsn(DscsnVO dscsnVO, int page, int size) throws Exception;

    /**
     * 상담일지 조회
     */
	public List listDscsn(DscsnVO dscsnVO) throws Exception ;

    /**
     * 상담일지 상세조회
     */
	public DscsnVO viewDscsn(DscsnVO dscsnVO) throws Exception ;

    /**
     * 상담일지 등록,수정,삭제
     */
	public String saveDscsn(DscsnVO dscsnVO) throws Exception;
    
}