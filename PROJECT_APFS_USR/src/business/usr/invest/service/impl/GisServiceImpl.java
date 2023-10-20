package business.usr.invest.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
import business.usr.invest.service.FundService;
import business.usr.invest.service.FundVO;
import business.usr.invest.service.GisService;
import business.usr.invest.service.GisVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;
import common.util.properties.ApplicationProperty;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * [서비스클래스] - Gis Service 구현 클래스
 * 
 * @class   : GiserviceImpl
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.13      LHB              First Coding.
 */

@Service("GisService")
@SuppressWarnings({"all"})
public class GisServiceImpl extends BaseService implements GisService {
	
	@Resource(name = "GisDAO")
	private GisDAO gisDAO;
	
	private String geoserverProtocol	= ApplicationProperty.get("geoserver.protocol" );
	private String geoserverHost		= ApplicationProperty.get("geoserver.host"     );
	private String geoserverUsername	= ApplicationProperty.get("geoserver.username" );
	private String geoserverPassword	= ApplicationProperty.get("geoserver.password" );
	private String geoserverWorkspace	= ApplicationProperty.get("geoserver.workspace");
	
	@Override
	public JSONObject getLayer(GisVO gisVO) throws Exception {
		
		BufferedReader br	= null;
    	StringBuffer   sb	= null;
    	JSONObject result	= null;
    	
    	try {
    		
    		String apiUrl = _getGeoserverUrl(gisVO);
    		
    		URL url = new URL(apiUrl);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		
    		// Header에 사용자 정보 세팅
    		String xCredentialsValue = "private-user=" + geoserverUsername + "&private-pw=" + geoserverPassword;
    		//conn.setRequestProperty("X-Credentials", xCredentialsValue);
    		conn.setRequestProperty("X-Credentials", URLEncoder.encode(xCredentialsValue));
    		
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "application/json");
    		conn.setRequestProperty("User-Agent", "Mozilla/4.0");
    		conn.setDoOutput(true);
    	
        	sb = new StringBuffer();
        	
        	if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        		br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        		
        		String line = null;
            	while((line = br.readLine()) != null) {
            		sb.append(line).append("\n");
            	}
            	
            	JSONParser parser = new JSONParser();
            	result = (JSONObject) parser.parse(sb.toString()); 
            	
            	br.close();
        	}
        	
    	} catch (NullPointerException e) {
    		logger.error("GEOSERVER API ERROR : ", e);
    	} catch(Exception e) {
    		logger.error("GEOSERVER API ERROR : ", e);
    	} finally {
    		try {
        		if (br != null) {
        			br.close();
        		}
    		} catch (Exception ex) {
    			logger.error("GEOSERVER API ERROR : ", ex);
    		}
    	}
    	
    	return result;
	}
	
	@Override
	public List<EgovMap> selectMgmtChartDataList(EgovMap params) {
		return this.gisDAO.selectMgmtChartDataList(params);
	}
	
	@Override
	public List<EgovMap> selectScaleChartDataList(EgovMap params) {
		return this.gisDAO.selectScaleChartDataList(params);
	}
	
	private String _getGeoserverUrl(GisVO gisVO) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(geoserverProtocol);
		sb.append(geoserverHost);
		sb.append("/geoserver/");
		sb.append(geoserverWorkspace);
		sb.append("/ows?service=");
		sb.append(gisVO.getService());
		sb.append("&version=");
		sb.append(gisVO.getVersion());
		sb.append("&request=");
		sb.append(gisVO.getReqType());
		sb.append("&typeName=");
		sb.append(geoserverWorkspace);
		sb.append(":");
		sb.append(gisVO.getLayerNm());
		sb.append("&maxFeatures=");
		sb.append(gisVO.getMaxFeatures());
		sb.append("&outputFormat=");
		sb.append(gisVO.getOutputFormat());
		
		return sb.toString();
	}

}