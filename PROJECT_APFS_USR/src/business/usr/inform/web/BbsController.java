package business.usr.inform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 공통 Controller
 * 
 * @class   : BbsController
 * @author  : LSH
 * @since   : 2023.05.18
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *  
 * TODO 사용자시스템은 게시판 구분별로 권한과 사용기능이 다를 수 있으므로
 * 공통부분에서 한번에 처리하기 보단 개별적으로 URL과 기능을 구현하는게 맞습니다.
 *
 */
@Controller
@SuppressWarnings({"all"})
public class BbsController extends BaseController {
	
	@Resource(name = "BbsService")
	private BbsService bbsService;
	
	/**
	 * 게시판 탭목록JSON 반환
	 */
	@RequestMapping("/usr/inform/bbs/getListBbsTab.do")
	@ResponseBody
	public Map getListBbsTab(ModelMap model, 
			HttpServletRequest request, 
			@ModelAttribute BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		List list = bbsService.listBbsTab(bbsVO);
		
		String bbsSeCd = CommUtils.nvlTrim(bbsVO.getBbsSeCd());
		
		if(CommUtils.isEqual(bbsSeCd, CodeConst.BBS_FAQ) || CommUtils.isEqual(bbsSeCd, CodeConst.BBS_COMMUNITY) || CommUtils.isEqual(bbsSeCd, CodeConst.BBS_DATA)) {
			Map<String, String> firstObject = new HashMap<>();
			firstObject.put("code", "00");
			firstObject.put("text", "전체");
			if (list != null) {
				list.add(0, firstObject);
			}
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	
	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, BbsVO bbsVO) {

    	setGlobalSession(request, bbsVO);

		if (bbsVO.getUserInfo(request) != null) {
        	bbsVO.setGsUserNo  (bbsVO.getUserInfo(request).getUserNo  ());
        	bbsVO.setGsUserNm  (bbsVO.getUserInfo(request).getUserNm  ());
        	bbsVO.setGsRoleId  (bbsVO.getUserInfo(request).getRoleId  ());
        	bbsVO.setGsBzentyNo(bbsVO.getUserInfo(request).getBzentyNo());
        }
		// Context
		bbsVO.setGsContext(request.getContextPath());
    }
    
}
