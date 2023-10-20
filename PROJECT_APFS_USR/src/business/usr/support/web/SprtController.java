package business.usr.support.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
import business.usr.support.service.SprtService;
import business.usr.support.service.SprtVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 투자지원신청 Controller
 * 
 * @class   : SprtController
 * @author  : LSH
 * @since   : 2023.05.22
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class SprtController extends BaseController {

    @Resource(name="SprtService")
    protected SprtService sprtService;

    @Resource(name="CommService")
    protected CommService commService;
    
    @Resource(name="UserService")
    protected UserService userService;

    @Resource(name="BizFileService")
    protected BizFileService bizFileService;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private SprtValidator validator;

    /**
     * 투자지원신청 - 투자유치 후 지원 화면 오픈
     * 투자지원신청 - 투자유치 전 지원 화면 오픈
     * 투자지원신청 - 농식품 크라우드 펀딩 지원 화면 오픈
     */
    @RequestMapping("/usr/support/support/openSprt.do")
    public String openSprt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute SprtVO sprtVO) throws Exception {
		
        setGlobalSessionVO(request, sprtVO);
        
        // 지원신청구분이 없을 경우
        if (CommUtils.isEmpty(sprtVO.getSprtSeCd()))
        	sprtVO.setSprtSeCd(CodeConst.SPRT_APLY_SE_BEFORE);

        model.addAttribute("model", sprtVO);
    	
        return "usr/support/support/openSprt";
    }
    
    /**
     * 투자지원신청 - 투자유치 후 지원 신청폼 화면 오픈
     * 투자지원신청 - 투자유치 전 지원 신청폼 화면 오픈
     * 투자지원신청 - 농식품 크라우드 펀딩 지원 신청폼 화면 오픈
     * 
     * TODO 수정이면서 최종단계가 아닌 경우 신청서가 임시저장 상태인지 확인 필요
     */
    @RequestMapping("/usr/support/support/formSprt.do")
    public String formSprt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute SprtVO sprtVO) throws Exception {
		
        setGlobalSessionVO(request, sprtVO);
        
        // 지원신청구분이 없을 경우
        if (CommUtils.isEmpty(sprtVO.getSprtSeCd()))
        	sprtVO.setSprtSeCd(CodeConst.SPRT_APLY_SE_BEFORE);
        
    	sprtVO.setSprtSeNm(
   			commService.getCodeName(CodeConst.SPRT_APLY_SE, sprtVO.getSprtSeCd())
    	);
    	
    	// 개인회원 투자후지원 신청불가
    	if (CodeConst.SPRT_APLY_SE_AFTER.equals(sprtVO.getSprtSeCd()) &&
    		!"Y".equals(sprtVO.getGsBzentyYn()))
    		throw new BusinessException("개인회원은 "+sprtVO.getSprtSeNm()+"은 하실 수 없습니다.");
        
        // 신청단계번호가 없을 경우
        if (CommUtils.isEmpty(sprtVO.getStepNo())) {
        	// 신청단계코드가 있을 경우
        	if (CommUtils.isNotEmpty(sprtVO.getStepCd())) {
	        	sprtVO.setStepNo(CodeConst.getSprtStepNo(sprtVO.getSprtSeCd(), sprtVO.getStepCd()));
        	}
        	else {
	        	sprtVO.setStepNo("1");
        	}
        }
        
        // 지원신청종류가 있을 경우
        if (CommUtils.isNotEmpty(sprtVO.getPrgrmNo())) {
        	sprtVO.setPrgrmNm(
       			sprtService.getPrgrmName(sprtVO.getPrgrmNo())
        	);
        }
        // 처리모드 설정
        if (CommUtils.isNotEmpty(sprtVO.getSprtAplyNo()) &&
        	sprtService.existSprt(sprtVO.getSprtAplyNo()))
        	sprtVO.setMode(CommConst.UPDATE);
        else
        	sprtVO.setMode(CommConst.INSERT);

        model.addAttribute("model", sprtVO);
    	
        return "usr/support/support/formSprt";
    }

    /**
     * 투자지원신청 조회JSON 반환
     */
    @RequestMapping("/usr/support/support/getSprt.do")
    @ResponseBody
    public Map getSprt(HttpServletRequest request, @ModelAttribute SprtVO sprtVO) throws Exception {

        setGlobalSessionVO(request, sprtVO);

        SprtVO obj = sprtService.viewSprt(sprtVO.getSprtAplyNo());
        
        // 조회된 정보가 없는 경우
        if (obj == null)
        	return getFailure(message.getMessage("error.comm.notTarget"));

        // 관리자가 아니면서 작성자가 아닌 경우
        if (!CommConst.isAdminRole(sprtVO.getGsRoleId()) &&
        	!CommUtils.isEqual(sprtVO.getGsUserNo(), obj.getRgtrNo()))
        	return getFailure(message.getMessage("error.comm.notAccess"));
        
        Map ret = getSuccess(obj);
        
        if (obj != null) {
        	// 매출액 목록 조회
        	ret.put("fnnrList", sprtService.listSprtFnnr(obj));
        	// 투자조합정보 목록 조회
        	ret.put("mxtrList", sprtService.listSprtMxtr(obj));
        }
        return ret;
    }

    /**
     * 투자지원신청 일반회원의 정보조회JSON 반환
     */
    @RequestMapping("/usr/support/support/getUser.do")
    @ResponseBody
    public Map getUser(HttpServletRequest request, @ModelAttribute SprtVO sprtVO) throws Exception {

        setGlobalSessionVO(request, sprtVO);
        
        // 사용자정보 조회
        UserVO user = userService.getUser(sprtVO.getGsUserNo());

        SprtVO obj = SprtVO.builder()
        		.gsUserNo    (user.getUserNo())
        		.userNo      (user.getUserNo())
        		.rprsvNm     (user.getUserNm())
        		.brdt        (user.getBrdt())
        		.sexdstn     (user.getSexdstn())
        		.lctnAddr    (user.getAddr())
        		.lctnDaddr   (user.getDaddr())
        		.rprsTelno   (user.getTelno())
        		.bzentyTypeCd(CodeConst.BZENTY_TYPE_USR)
        		.build       ();
        
        return getSuccess(obj);
    }

    /**
     * 투자지원신청 신청목록 조회 (GRID)
     * 마이페이지 - 신청내역 - 투자전지원/투자후지원/크라우드펀딩에서 사용됨
     */
    @RequestMapping("/usr/support/support/getListSprt.do")
    @ResponseBody
    public Map getListSprt(HttpServletRequest request
            , @ModelAttribute SprtVO sprtVO) throws Exception {

        setGlobalSessionVO(request, sprtVO);

        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = sprtService.listSprt(sprtVO, page, size);
        }
        else {
        	list = sprtService.listSprt(sprtVO);
        }
        return getPaginatedResult(list);
    }

    /**
     * 투자지원신청 동의처리
     */
    @RequestMapping("/usr/support/support/saveSprtAgree.do")
    @ResponseBody
    public Map saveSprtAgree(@RequestBody SprtVO sprtVO
    		, HttpServletRequest request
    		, HttpSession session) throws Exception {

        setGlobalSessionVO(request, sprtVO);
        
        String prvcYn   = sprtVO.getPrvcClctAgreYn();
        String msysYn   = sprtVO.getUnityMngSysAgreYn();
        String sprtSeCd = sprtVO.getSprtSeCd();
        String prgrmNo  = sprtVO.getPrgrmNo();
        
        // 동의처리 세션저장
        if (CommUtils.isEqual(CommConst.YES, prvcYn)) {
            session.setAttribute(CommConst.SESS_PRVC_SE, sprtSeCd);
            if (CommUtils.isNotEmpty(prgrmNo))
            	session.setAttribute(CommConst.SESS_PRVC_PG, prgrmNo);
        }
        if (CommUtils.isEqual(CommConst.YES, msysYn)) {
            session.setAttribute(CommConst.SESS_MSYS_SE, sprtSeCd);
            if (CommUtils.isNotEmpty(prgrmNo))
	            session.setAttribute(CommConst.SESS_MSYS_PG, prgrmNo);
        }
        return getSuccess();
    }

    /**
     * 투자지원신청 AJAX 저장처리 (등록,수정,삭제)
     * 
     */
    @RequestMapping("/usr/support/support/saveSprt.do")
    @ResponseBody
    public Map saveSprt(@RequestBody SprtVO sprtVO
    		, HttpServletRequest request
    		, HttpSession session
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, sprtVO);

    	// 저장데이터에 맞게 REBUILD
        sprtVO.rebuildProperties(session);
    	
        // 저장할 입력값 검증
    	validator.validate(sprtVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Sprt Apply Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 투자지원신청를 저장한다.
    	String aplyNo = sprtService.saveSprt(sprtVO);
    	
    	if (CommUtils.isEmpty(aplyNo))
    		return getFailure();
    	
        // 동의처리 세션삭제
        session.removeAttribute(CommConst.SESS_PRVC_SE);
        session.removeAttribute(CommConst.SESS_PRVC_PG);
        session.removeAttribute(CommConst.SESS_MSYS_SE);
        session.removeAttribute(CommConst.SESS_MSYS_PG);
    	
    	// 성공결과 반환
        return getSuccess(aplyNo);
    }

    /**
     * 투자지원신청 제출서류 저장처리 (등록,수정,삭제)
     * 제출서류가 없는 경우에도 처리 가능
     */
    @RequestMapping("/usr/support/support/saveFile.do")
    @ResponseBody
    public Map saveFile(@RequestBody SprtVO sprtVO
    		, HttpServletRequest request
    		, HttpSession session
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, sprtVO);
        
        // 검증을 위한 서류코드 목록조회
        sprtVO.setPapeList(
                bizFileService.listPape(
                		BizFileVO.builder()
                		.upDcmntCd(CodeConst.getPapeGroup(sprtVO.getSprtSeCd()))
                		.aplySeCd (CodeConst.getPapeType(sprtVO.getGsBzentyYn()))
                		.dtlSeCd  (sprtVO.getPrgrmNo())
                		.build    ()
                )
        );
    	// 저장데이터에 맞게 REBUILD
        sprtVO.rebuildProperties(session);
    	
        // 저장할 입력값 검증
    	validator.validate(sprtVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Sprt Submit File Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 투자지원신청 제출서류를 저장한다.
    	String result = sprtService.saveFile(sprtVO);
    	
    	// 성공결과 반환
        return getSuccess(result);
    }

    /**
     * 투자지원신청 지원사업 목록JSON 반환
     */
    @RequestMapping("/usr/support/support/getListPrgrm.do")
    @ResponseBody
    public List getListPrgrm(HttpServletRequest request, @ModelAttribute SprtVO sprtVO) throws Exception {

        setGlobalSessionVO(request, sprtVO);
        
        List list = sprtService.listPrgrm(sprtVO);

        // 투자후지원인 경우 상담신청 항목 추가
    	if (CodeConst.SPRT_APLY_SE_AFTER.equals(sprtVO.getSprtSeCd())) {
    		Map code = new HashMap();
    		code.put("code"  , CodeConst.DSCSN_CODE);
    		code.put("text"  , CodeConst.DSCSN_NAME);
    		code.put("group" , CodeConst.DSCSN_GROUP);
    		code.put("active", 1);

    		if (list == null)
    			list = new ArrayList();
    		list.add(0, code);
    	}
    	return list;
    }

    /**
     * 마이페이지 - 신청내역 - 지원사업진행현황 목록조회
     */
    @RequestMapping("/usr/support/support/getListSprtPrgre.do")
    @ResponseBody
    public List getListSprtPrgre(HttpServletRequest request, @ModelAttribute SprtVO sprtVO) throws Exception {

        setGlobalSessionVO(request, sprtVO);
        return sprtService.listSprtPrgre(sprtVO.getSprtAplyNo());
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, SprtVO sprtVO) {
    	
        setGlobalSession(request, sprtVO);

        if (sprtVO.getUserInfo(request) != null) {
        	sprtVO.setUserInfo  (sprtVO.getUserInfo(request));
        	sprtVO.setGsUserNo  (sprtVO.getUserInfo(request).getUserNo  ());
        	sprtVO.setGsRoleId  (sprtVO.getUserInfo(request).getRoleId  ());
        	sprtVO.setGsBzentyNo(sprtVO.getUserInfo(request).getBzentyNo());
        	// 업체회원여부
        	sprtVO.setGsBzentyYn(
    			CommConst.isBizRole(sprtVO.getGsRoleId()) ?
    			CommConst.YES :
    			CommConst.NO
        	);
        }
    }
}
