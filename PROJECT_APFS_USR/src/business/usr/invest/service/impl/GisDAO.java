 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.FundVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [DAO클래스] - GIS를 관리하는 DAO 클래스
 * 
 * 사용 가능한  DAO Statement Method
 * 1. list          : 리스트 조회시 사용함.
 * 2. pageListOra   : 페이징처리용 리스트조회시 사용함. for Oracle, Tibero
 * 3. view          : 단건조회, 상세조회시 사용함.
 * 4. save          : INSERT, UPDATE, DELETE 모두 사용가능. (Return Type : Integer)
 * 5. insert        : INSERT (Return String : Key 채번 사용함.)
 * 6. update        : UPDATE (Return Type : Integer)
 * 7. delete        : DELETE (Return Type : Integer)
 * 
 *
 * @class   : GisDAO
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  -------    --------    ---------------------------
 * 23.06.13      LHB              First Coding.
 */

@Repository("GisDAO")
@SuppressWarnings({"all"})
public class GisDAO extends EgovAbstractMapper {
	
	public List<EgovMap> selectMgmtChartDataList(EgovMap params) {
		return selectList("Gis.selectMgmtChartDataList", params);	
	}
	
	public List<EgovMap> selectScaleChartDataList(EgovMap params) {
		return selectList("Gis.selectScaleChartDataList", params);	
	}
}