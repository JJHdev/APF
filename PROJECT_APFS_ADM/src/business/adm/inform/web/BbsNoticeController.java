package business.adm.inform.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import business.adm.CodeConst;
import business.adm.inform.service.BbsService;
import business.adm.inform.service.BbsVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 > 공지사항 Controller
 * 
 * @class   : BbsNoticeController
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
public class BbsNoticeController extends BaseController {

	@Resource(name = "BbsService")
	protected BbsService bbsService;
	
	// 공지사항 구분코드
	private static final String BBS_CODE = CodeConst.BBS_NOTICE;

	/**
	 * 운영관리 - 공지사항 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/openNotice.do")
	public String openNotice(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		bbsVO.setBbsSeCd(BBS_CODE);
		model.addAttribute("model", bbsVO);
		
		return "adm/inform/bbs/openNotice";
	}

	/**
	 * 운영관리 - 공지사항 등록/수정 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/modalNoticeForm.do")
	public String modalNoticeForm(ModelMap model, HttpServletRequest request, @RequestBody BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		
		if (CommUtils.isNotEmpty(bbsVO.getPstNo())) {
			BbsVO data = bbsService.viewBbs(bbsVO);
			
			if (data == null)
				throw new ModalBusinessException(message.getMessage("exception.notKey"));
			
			// 수정모드
			data.setMode(CommConst.UPDATE);
			model.addAttribute("model", data);
		} 
		else {
			bbsVO.setBbsSeCd(BBS_CODE);
			// 등록모드
			bbsVO.setMode(CommConst.INSERT);
			model.addAttribute("model", bbsVO);
		}
		return "adm/inform/bbs/modalNoticeForm";
	}

	/**
	 * 운영관리 - 공지사항 상세조회 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/modalNoticeView.do")
	public String modalNoticeView(ModelMap model, HttpServletRequest request, @RequestBody BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		
		String movePage = bbsVO.getMovePage();
		
		BbsVO data = new BbsVO();
		// 이전, 다음 버튼, 일반 진입 클릭시 진입
		if(CommUtils.isEqual(movePage, "before")){
			data = bbsService.viewBbs(bbsService.beforeBbs(bbsVO));
			if (data == null) {
				throw new ModalBusinessException(message.getMessage("이전 글이 없습니다."));
			}
		}else if(CommUtils.isEqual(movePage, "next")){
			data = bbsService.viewBbs(bbsService.nextBbs(bbsVO));
			if (data == null) {
				throw new ModalBusinessException(message.getMessage("다음 글이 없습니다."));
			}
		}else {
			data = bbsService.viewBbs(bbsVO);
		}
		
		if (data == null)
			throw new ModalBusinessException(message.getMessage("exception.notKey"));
		
		data.setPage(bbsVO.getPage());
		model.addAttribute("model", data);

		return "adm/inform/bbs/modalNoticeView";
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
