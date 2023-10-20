package business.usr.inform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.com.common.service.CommService;
import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import business.usr.inform.service.SearchService;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 게시판 Service 구현 클래스
 * 
 * @class   : SearchServiceImpl
 * @author  : LHB
 * @since   : 2023.06.29
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 *  23.06.29     LHB              First Coding.
 */

@Service("SearchService")
@SuppressWarnings({"all"})
public class SearchServiceImpl extends BaseService implements SearchService {

	@Resource(name = "BbsService")
	private BbsService bbsService;
	
	@Resource(name = "SearchDAO")
	private SearchDAO searchDAO;
	
	@Autowired
    private CommService commService;
	
	/**
     * 통합검색 목록 조회
     */
    @Override
    public List<HashMap> listSearch(BbsVO bbsVO) throws Exception {
    	return null;
    }
    
    /**
	 * 인기검색어 조회
	 */
    @Override
	public List<HashMap> listSrchWrd(HashMap params) throws Exception {
    	return searchDAO.listSrchWrd(params);
    }
	
	/**
	 * 검색어 저장
	 */
    @Override
	public int regiSrchWrd(HashMap params) throws Exception {
		return searchDAO.regiSrchWrd(params);
	}
    
    /**
     * 마이페이지 메뉴 목록 조회
     */
	@Override
    public List listMypageMenu(Map params) throws Exception {
    	return searchDAO.listMypageMenu(params);
    }

}