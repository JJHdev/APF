package business.usr.inform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 통합검색 Service Interface
 * 
 * @class   : SearchService
 * @author  : LHB
 * @since   : 2023.06.29
 * @version : 1.0
 *
  *   수정일      수정자                 수정내용
 *  --------   --------    ---------------------------
 *  23.06.29     LHB              First Coding.
 */
@SuppressWarnings("all")
public interface SearchService {

	/**
     * 통합검색 목록 조회
     */
	public List<HashMap> listSearch(BbsVO bbsVO) throws Exception;
	
	/**
	 * 인기검색어 조회
	 */
	public List listSrchWrd(HashMap params) throws Exception;
	
	/**
	 * 검색어 저장
	 */
	public int regiSrchWrd(HashMap params) throws Exception;
	
	/**
     * 마이페이지 메뉴 목록 조회
     */
    public List listMypageMenu(Map params) throws Exception;
	
}