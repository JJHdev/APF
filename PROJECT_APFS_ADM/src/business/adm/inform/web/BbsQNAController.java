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
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 > 질의응답 Controller
 * 
 * @class   : BbsQNAController
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
public class BbsQNAController extends BaseController {

	@Resource(name = "BbsService")
	protected BbsService bbsService;
	
	// 질의응답 구분코드
	private static final String BBS_CODE_QNA = CodeConst.BBS_QNA;
	private static final String BBS_CODE_FAQ = CodeConst.BBS_FAQ;

	/**
	 * 운영관리 - 질의응답 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/openQNA.do")
	public String openQNA(ModelMap model, HttpServletRequest request, @ModelAttribute BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		
		if(CommUtils.isEqual(bbsVO.getBbsSeCd(), BBS_CODE_FAQ)) {
			bbsVO.setBbsSeCd(BBS_CODE_FAQ);
			model.addAttribute("model", bbsVO);
			return "adm/inform/bbs/openFAQ";
		}else {
			bbsVO.setBbsSeCd(BBS_CODE_QNA);
			model.addAttribute("model", bbsVO);
			return "adm/inform/bbs/openQNA";
		}
	}
	
	/**
	 * 운영관리 - 질의응답 등록/수정 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/modalQNAForm.do")
	public String modalQNAForm(ModelMap modelUsr, HttpServletRequest request, ModelMap modelAdm, @RequestBody BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		/* 이전, 다음버튼 이동 */
		BbsVO data = moveQNA(bbsVO);
		
		if (CommUtils.isNotEmpty(bbsService.viewQnaBbs(data))) {
			BbsVO dataAmd = bbsService.viewQnaBbs(data);
			if (data == null)
				throw new ModalBusinessException(message.getMessage("exception.notKey"));
			
			// 수정모드
			dataAmd.setMode(CommConst.UPDATE);
			modelUsr.addAttribute("modelUsr", data);
			modelAdm.addAttribute("modelAdm", dataAmd);
		}else {
			// 세션사용자이름 정의 (등록시 이름 고정)
			if (bbsVO.getUserInfo(request) != null)
				bbsVO.setRgtrNm(bbsVO.getUserInfo(request).getUserNm());
			bbsVO.setBbsSeCd(BBS_CODE_QNA);
			// 등록모드
			bbsVO.setMode(CommConst.INSERT);
			modelUsr.addAttribute("modelUsr", data);
			modelAdm.addAttribute("modelAdm", bbsVO);
		}
		return "adm/inform/bbs/modalQNAForm";
	}
	
	/**
	 * 운영관리 - 질의응답 상세조회 화면 오픈
	 */
	@RequestMapping("/adm/inform/bbs/modalQNAView.do")
	public String modalQNAView(ModelMap modelUsr, HttpServletRequest request, ModelMap modelAdm, @RequestBody BbsVO bbsVO) throws Exception {
		setGlobalSessionVO(request, bbsVO);
		
		/* 이전, 다음버튼 이동 */
		BbsVO data = moveQNA(bbsVO);
		
		// 관리자 답글 "답변 완료"
		BbsVO admData = bbsService.viewQnaBbs(data);
		if (data == null)
			throw new ModalBusinessException(message.getMessage("exception.notKey"));
		
		data.setPage(bbsVO.getPage());
		modelUsr.addAttribute("modelUsr", data);
		modelAdm.addAttribute("modelAdm", admData);

		return "adm/inform/bbs/modalQNAView";
	}
	
	/**
	 * 운영관리 - 질의응답 이전, 다음, 목록이동시 게시글 번호 조회
	 */
	public BbsVO moveQNA (BbsVO bbsVO) throws Exception {
		BbsVO data = new BbsVO();
		// 이전, 다음 버튼, 일반 진입 클릭시 진입
		if(CommUtils.isEqual(bbsVO.getMovePage(), "before")){
			data = bbsService.viewBbs(bbsService.beforeBbs(bbsVO));
			if (data == null) {
				throw new ModalBusinessException(message.getMessage("이전 글이 없습니다."));
			}
			/* 이전버튼 클릭시 조회할 번호 */
			if(CommUtils.isNotEmpty(bbsService.viewQnaBbs(bbsService.beforeBbs(bbsService.beforeBbs(bbsVO))))) {
				data.setIsBefore(CommConst.YES);
			}else {
				data.setIsBefore(CommConst.NO);
			}
			if(CommUtils.isNotEmpty(bbsService.viewQnaBbs(bbsService.nextBbs(bbsService.beforeBbs(bbsVO))))) {
				data.setIsNext(CommConst.YES);
			}else {
				data.setIsNext(CommConst.NO);
			}
		}else if(CommUtils.isEqual(bbsVO.getMovePage(), "next")){
			data = bbsService.viewBbs(bbsService.nextBbs(bbsVO));
			if (data == null) {
				throw new ModalBusinessException(message.getMessage("다음 글이 없습니다."));
			}
			/* 다음버튼 클릭시 조회할 번호 */
			if(CommUtils.isNotEmpty(bbsService.viewQnaBbs(bbsService.beforeBbs(bbsService.nextBbs(bbsVO))))) {
				data.setIsBefore(CommConst.YES);
			}else {
				data.setIsBefore(CommConst.NO);
			}
			if(CommUtils.isNotEmpty(bbsService.viewQnaBbs(bbsService.nextBbs(bbsService.nextBbs(bbsVO))))) {
				data.setIsNext(CommConst.YES);
			}else {
				data.setIsNext(CommConst.NO);
			}
		}else {
			data = bbsService.viewBbs(bbsVO);
			/* 그외 버튼 클릭시 조회할 번호 */
			if(CommUtils.isNotEmpty(bbsService.viewQnaBbs(bbsService.beforeBbs(bbsVO)))) {
				data.setIsBefore(CommConst.YES);
			}else {
				data.setIsBefore(CommConst.NO);
			}
			if(CommUtils.isNotEmpty(bbsService.viewQnaBbs(bbsService.nextBbs(bbsVO)))) {
				data.setIsNext(CommConst.YES);
			}else {
				data.setIsNext(CommConst.NO);
			}
		}
		return data;
	}
	
	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, BbsVO bbsVO) {
    	
    	setGlobalSession(request, bbsVO);
        
        // 게시판구분 정의
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
