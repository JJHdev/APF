package business.com.kodata.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.kodata.service.KodataAPI;
import business.com.kodata.service.KodataBizVO;
import business.com.kodata.service.KodataEntity;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 *  [컨트롤러클래스] - 한국평가데이터 KODATA 연계 Controller
 *
 * @class   : KodataController
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
public class KodataController extends BaseController {

	@Autowired
    private CommService commService;
    
    /**
     * KODATA OPEN API 실행 (JSON반환)
     */
    @RequestMapping("/com/common/getKodata.do")
    @ResponseBody
    public Map getKodata(@ModelAttribute KodataBizVO kodataBizVO
    		, HttpSession session
    		, ModelMap model) throws Exception {
    	
    	KodataEntity entity = KodataEntity.builder()
    			.url      (CommConst.KODATA_API_URL)
    			.path     (CommConst.KODATA_API_PATH)
    			.format   (CommConst.KODATA_API_FORMAT)
    			.process  (CommConst.KODATA_API_PROCESS)
    			.pidagryn (CommConst.KODATA_API_PIDAGRYN)
    			.userid   (CommConst.KODATA_API_USERID)
    			.jmno     (CommConst.KODATA_API_JMNO)
    			.bzno     (kodataBizVO.getBzno())
    			.build();
    	
    	// 샘플로 사용할 경우
    	if (CommConst.isDev() && 
    		CommConst.IS_KODATA_SAMPLE) {
    		KodataBizVO result = loadKodataSample(kodataBizVO);

    		if (result == null)
    			return getFailure();
    		else
    			return getSuccess(result);
    	}
    	else {
	    	try {
	        	KodataAPI api = new KodataAPI(entity);
	        	api.execute(kodataBizVO);
	        	
	        	// 검색된 결과를 가입시 사용하기위해 세션에 저장한다.
	        	session.setAttribute(CommConst.SESS_KODATA_INFO, kodataBizVO);
	        	
	            return getSuccess(kodataBizVO);
	    	}
	    	catch (BusinessException be) {
	    		return getFailure(be.getMessage());
	    	}
    	}
    }
    
    // KODATA API 연동문제로 테스트가 필요한 경우 샘플을 반환한다.
    // 개발시에만 사용되며 운영 중엔 사용되지 않는다.
    private KodataBizVO loadKodataSample(KodataBizVO kodataBizVO) throws Exception {
    	Map<String,Object> data = commService.viewSampleKodata(kodataBizVO.getBzno());
		if (data != null) {
    		// 맵데이터를 KodataBizVO에 담는다.
    		CommUtils.mapToBean(data, kodataBizVO);
    		return kodataBizVO;
		}
		return null;
    }

    @RequestMapping(value="/com/common/apikodata.do", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getEnp(@RequestParam String bzno) {
    	
    	Map<String,Object> contents = new HashMap<String,Object> ();
    	
    	try {
    		Map<String,Object> data = commService.viewSampleKodata(bzno);
    		contents.put("ENPlntr0000", data);
		} catch (IOException e) {
			logger.error("apikodata error : ", e);
		} catch (Exception e) {
			// 230621 LHB
			logger.error("apikodata error : ", e);
		}
    	Map<String,Object> header = new HashMap<String,Object> ();
    	header.put("errcd", "00");
    	header.put("errtt", "00");
    	header.put("resptm", CommUtils.getCurrDateTime2());
    	
    	Map<String,Object> ked = new HashMap<String,Object> ();
    	ked.put("HEADER", header);
    	ked.put("CONTENTS", contents);
    	
    	Map<String,Object> result = new HashMap<String,Object> ();
    	result.put("KED", ked);
    	
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
