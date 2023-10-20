package business.usr.inform.service;

import java.util.HashMap;
import java.util.List;

import business.usr.support.service.SprtBizVO;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 정보서비스-경영체 데이터 업로드 Service Interface
 * 
 * @class   : SprtUldService
 * @author  : LHB
 * @since   : 2023.07.13
 * @version : 1.0
 *
  *   수정일      수정자                 수정내용
 *  --------   --------    ---------------------------
 *  23.07.13     LHB               First Coding.
 */
@SuppressWarnings("all")
public interface SprtUldService {

	/**
     * 정보서비스-경영체 데이터 업로드 페이징목록 조회
     */
    public PaginatedArrayList listSprtUld(SprtBizVO sprtBizVO, int currPage, int pageSize) throws Exception;

    /**
     * 정보서비스-경영체 데이터 업로드 목록조회
     */
    public List listSprtUld(SprtBizVO sprtBizVO) throws Exception;
    
    /**
     * 정보서비스-경영체 데이터 업로드 상세조회
     */
    public SprtBizVO viewSprtUld(SprtBizVO sprtBizVO) throws Exception;
    
    /**
     * 정보서비스-경영체 데이터 업로드 등록
     */
	public HashMap saveSprtUld(SprtBizVO sprtBizVO, List<FileInfo> files) throws Exception;
	
	
	
	/**
     * 정보서비스-경영체 데이터 업로드 - 정부지원사업이력 페이징목록 조회
     */
    public PaginatedArrayList listSprtBiz(SprtBizVO sprtBizVO, int currPage, int pageSize) throws Exception;

    /**
     * 정보서비스-경영체 데이터 업로드 - 정부지원사업이력 목록조회
     */
    public List listSprtBiz(SprtBizVO sprtBizVO) throws Exception;
    
    /**
     * 정보서비스-경영체 데이터 업로드 - 정부지원사업이력 삭제
     */
    public String saveSprtBiz(SprtBizVO sprtBizVO, long[] arrSn) throws Exception;
	
}