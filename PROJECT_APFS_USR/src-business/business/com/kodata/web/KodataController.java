package business.com.kodata.web;

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
import business.com.kodata.service.KodataBizVO;
import business.com.kodata.service.KodataService;
import commf.exception.BusinessException;
import common.base.BaseController;

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
    private KodataService kodataService;
    
    /**
     * KODATA OPEN API 실행 (JSON반환)
     */
    @RequestMapping("/com/common/getKodata.do")
    @ResponseBody
    public Map getKodata(@ModelAttribute KodataBizVO kodataBizVO
    		, HttpSession session
    		, ModelMap model) throws Exception {
    	
    	try {
	    	String mode = kodataBizVO.getMode();
	    	String brno = kodataBizVO.getBzno();
        	KodataBizVO result = kodataService.getKodata(brno);
            
    		if (result == null)
    			return getFailure();
    		else {
    			if (CommConst.VIEW.equals(mode)) {
		        	// 검색된 결과를 가입시 사용하기위해 세션에 저장한다.
		        	session.setAttribute(CommConst.SESS_KODATA_INFO, result);
    			}
    			return getSuccess(result);
    		}
    	}
    	catch (BusinessException be) {
    		return getFailure(be.getMessage());
    	}
    }

    @RequestMapping(value="/com/common/apikodata.do", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getEnp(@RequestParam String bzno) {
    	Map<String,Object> result = kodataService.getEnp(bzno);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
