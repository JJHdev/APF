package business.usr.support.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.usr.support.service.SprtBizService;
import business.usr.support.service.SprtBizVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 지원사업이력 Controller
 * 
 * @class   : SprtBizController
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class SprtBizController extends BaseController {

    @Resource(name="SprtBizService")
    protected SprtBizService sprtBizService;

    /**
     * 지원사업이력 목록JSON 반환
     */
    @RequestMapping("/usr/support/sprt/getListSprtBiz.do")
    @ResponseBody
    public Map getListSprtBiz(HttpServletRequest request
            , @ModelAttribute SprtBizVO sprtBizVO) throws Exception {

    	setGlobalSessionVO(request, sprtBizVO);

        // -------------------- Default Setting End -----------------------//

        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = sprtBizService.listSprtBiz(sprtBizVO, page, size);
        }
        else {
        	list = sprtBizService.listSprtBiz(sprtBizVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SprtBizVO sprtBizVO) {
    	
        setGlobalSession(request, sprtBizVO);

        if (sprtBizVO.getUserInfo(request) != null) {
        	sprtBizVO.setGsUserNo  (sprtBizVO.getUserInfo(request).getUserNo  ());
        	sprtBizVO.setGsRoleId  (sprtBizVO.getUserInfo(request).getRoleId  ());
        	sprtBizVO.setGsBzentyNo(sprtBizVO.getUserInfo(request).getBzentyNo());
        }
    }
}
