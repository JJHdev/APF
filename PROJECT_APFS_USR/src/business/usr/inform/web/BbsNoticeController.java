package business.usr.inform.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 고객센터 > 공지사항 Controller
 * 
 * @class   : BbsNoticeController
 * @author  : JH
 * @since   : 2023.06.28
 * @version : 1.3
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *  
 *  TODO 2023.05.18 LSH
 *  [작업사항]
 *  1. 목록화면 / 상세화면 기본 조회 및 디자인 적용
 *  2. 분류탭별 조회 기능 구현
 *  
 *  [담당자 작업해야할 내용]
 *  1. 접근 권한 처리 확인 및 테스트 필요.
 *  2. 공지여부 / 게시기간에 따른 목록 조회 조건 적용해야함.
 *  3. 목록 및 상세화면의 세부항목이 설계서와 일치하는지 확인 후 보충 작업해야함.
 *  4. 그외 버그가 있을 경우 수정 필요.
 */
@Controller
@SuppressWarnings({"all"})
public class BbsNoticeController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;
	
	// 공지사항 구분코드
	private static final String BBS_CODE = CodeConst.BBS_NOTICE;

	/**
	 * 고객센터 - 공지사항 목록 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listNotice.do")
	public String listNotice(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		model.addAttribute("model", bbsVO);
		return "usr/inform/bbs/listNotice";
	}

	/**
	 * 고객센터 - 공지사항 상세조회 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/viewNotice.do")
	public String viewNotice(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);

		BbsVO data = bbsService.viewBbs(bbsVO);
		bbsService.updtBbsInqCnt(bbsVO);
		if (data == null)
			throw new BusinessException(message.getMessage("exception.notKey"));
		
		model.addAttribute("model" , data);
		model.addAttribute("search", bbsVO);

		return "usr/inform/bbs/viewNotice";
	}

	/**
	 * 고객센터 - 공지사항 목록JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/usr/inform/bbs/getListNotice.do")
	@ResponseBody
	public Map getListNotice(ModelMap model, 
			HttpServletRequest request,
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		// -------------------- Default Setting End -----------------------//
		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = bbsService.listBbs(bbsVO, page, size);
		} else {
			list = bbsService.listBbs(bbsVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, BbsVO bbsVO) {
    	
    	setGlobalSession(request, bbsVO);
        
        // 게시판구분 정의
		bbsVO.setBbsSeCd(BBS_CODE);

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
