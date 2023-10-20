package business.usr.invest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.file.FileInfo;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * [서비스인터페이스] - Gis Service Interface
 * 
 * @class   : GisService
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.13      LHB               First Coding.
 */
@SuppressWarnings("all")
public interface GisService {

	public JSONObject getLayer(GisVO gisVO) throws Exception; 
	
	List<EgovMap> selectMgmtChartDataList(EgovMap params);
	
	List<EgovMap> selectScaleChartDataList(EgovMap params);
	
}