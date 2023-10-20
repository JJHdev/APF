package business.com.user.web;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.kodata.service.KodataBizVO;
import business.com.user.oauth.KakaoLoginBO;
import business.com.user.oauth.NaverLoginBO;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.CodeConst;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import business.usr.invest.web.EntValidator;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.user.UserInfo;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 회원컨트롤러
 *
 * @class   : UserController
 * @author  : LSH
 * @since   : 2023.03.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("rawtypes")
@Controller
public class UserController extends BaseController {

	/* KakaoLogin */
	@Autowired
	private KakaoLoginBO kakaoLoginBO;
	
	/* NaverLogin */
	@Autowired
	private NaverLoginBO naverLoginBO;

	@Autowired
	private UserService userService;

	@Autowired 
    private UserValidator userValidator;

	@Resource(name = "fileManager")
	protected FileManager fileManager;

    // 기업정보 저장 검증용 VALIDATOR
    @Autowired 
    private EntValidator entValidator;
    
	@Resource(name="EntService")
    protected EntService entService;

	/**
	 * 회원가입 화면 오픈
	 */
	@RequestMapping("/com/user/openJoin.do")
	public String openJoin(@ModelAttribute UserVO userVO, 
			HttpSession session,
			ModelMap model) throws Exception {
		
		// 진행단계 정의
		if (userVO.getStepCd() == null)
			userVO.setStepCd(CodeConst.JOIN_CHOOSE);
		
		// 가입유형선택인 경우
		if (CodeConst.JOIN_CHOOSE.equals(userVO.getStepCd())) {
		}
		else {
			userVO.setJoinCd((String)session.getAttribute(CommConst.SESS_USER_JOINCD));
			// 개인정보입력인 경우
			if (CodeConst.JOIN_USRFORM.equals(userVO.getStepCd())) {
				// 네이버로그인 URL
				userVO.setNaverUrl(naverLoginBO.getAuthorizationUrl(session));
		    	// 카카오로그인 URL
				userVO.setKakaoUrl(kakaoLoginBO.getAuthorizationUrl(session));
			}
			// 가입완료인 경우
			else if (CodeConst.JOIN_DONE.equals(userVO.getStepCd())) {
				// 가입완료 후 임시로 사용한 세션 제거
				clearSessionInformation(session);
			}
		}
		model.addAttribute("model", userVO);
		
		return "com/user/openJoin";
	}

    /**
     * 가입완료 후 임시로 사용한 세션 제거
     */
    private void clearSessionInformation(HttpSession session) {
    	session.removeAttribute(CommConst.SESS_USER_ACTION  ); // 처리구분
        session.removeAttribute(CommConst.SESS_USER_JOINCD  ); // 가입유형
        session.removeAttribute(CommConst.SESS_USER_VO      ); // 회원정보
		session.removeAttribute(CommConst.SESS_AGREE_SERVICE); // 약관동의항목
		session.removeAttribute(CommConst.SESS_AGREE_PRIVACY); // 약관동의항목
    	session.removeAttribute(CommConst.SESS_MOBILIANS_NM ); // 모빌리언스검증항목
    	session.removeAttribute(CommConst.SESS_MOBILIANS_NO ); // 모빌리언스검증항목
    	session.removeAttribute(CommConst.SESS_KODATA_INFO  ); // KODATA결과정보
    }

	/**
	 * 회원가입 - 이메일(아이디) 중복체크
	 */
	@RequestMapping("/com/user/unique.do")
	@ResponseBody
	public boolean unique(@RequestParam("userId") String userId) throws Exception {
		return !userService.existUser(userId);
	}
    
	/**
	 * 2023.07.17 LSH
	 * 회원가입 - 사업자번호 기준 업체번호/업체유형/대표여부를 조회한다.
	 * 
	 * @param  brno           사업자번호
	 * @return map.bzentyNo   업체번호
	 * @return map.bzentySeCd 업체유형
	 * @return map.bzentyNm   회사명
	 * @return map.rprsvNm    대표자
	 * @return map.fndnYmd    설립일
	 * @return map.rprsTelno  대표번호
	 * @return map.rprsYn     대표여부
	 */
    @RequestMapping("/com/user/getEnt.do")
    @ResponseBody
    public Map getEnt(@ModelAttribute EntVO entVO) throws Exception {
        Map result = entService.viewEntByBrno(entVO.getBrno());
        return getSuccess(result);
    }

    /**
     * 2023.09.05
     * 반려업체 정보수정 - 업체정보 조회
     */
    @RequestMapping("/com/user/getEntByNo.do")
    @ResponseBody
    public Map getEntByNo(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
        EntVO obj = entService.viewEnt(entVO);
        return getSuccess(obj);
    }
    
    /**
     * 회원가입 - 가입유형선택처리
     */
	@RequestMapping("/com/user/saveChoose.do")
    @ResponseBody
    public Map saveChoose(@RequestBody UserVO userVO
    		, HttpSession session) throws Exception {
		// 선택한 가입유형을 세션에 담는다. (OAUTH에서 사용됨)
		session.setAttribute(CommConst.SESS_USER_JOINCD, userVO.getJoinCd());
		session.setAttribute(CommConst.SESS_USER_ACTION, CommConst.ACT_JOIN);
		
		return getSuccess();
    }

    /**
     * 회원가입 - 약관동의처리
     */
	@RequestMapping("/com/user/saveAgree.do")
    @ResponseBody
    public Map saveAgree(@RequestBody UserVO userVO
    		, HttpSession session) throws Exception {
		// 이용약관 동의항목을 세션에 담는다
		session.setAttribute(CommConst.SESS_AGREE_SERVICE, userVO.getSrvcTrmsAgreYn());
		session.setAttribute(CommConst.SESS_AGREE_PRIVACY, userVO.getPrvcClctAgreYn());
		
		return getSuccess();
    }

	/**
	 * [AJAX] 회원가입 - 기본정보 저장처리
	 */
	@RequestMapping("/com/user/saveJoin.do")
	@ResponseBody
	public Map saveJoin(@ModelAttribute UserVO userVO,
			HttpServletRequest request, 
			HttpSession session,
			BindingResult bindingResult) throws Exception {

		// 세션사용자정보를 정의
		setGlobalSession(request, userVO);
		// 저장모드 정의
		userVO.setMode(CommConst.INSERT);
		// 저장에 맞게 입력값 REBUILD
		userVO.rebuildProperties(request, session);
        // 입력값 검증
    	userValidator.validate(userVO, bindingResult);
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("User Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
		// 사용자 비밀번호 암호화
		userVO.encryptPswd();

		// 일반회원인 경우 회원가입 처리
		if (CommConst.JOIN_USR.equals(userVO.getJoinCd())) {
			// 회원가입처리
			if (userService.saveJoin(userVO) > 0)
				return getSuccess("userId", userVO.getUserId());
			// 실패결과 반환
			return getFailure();
		}
		// 기업회원인 경우 입력정보를 세션에 저장처리
		else {
			// 회원정보를 임시 세션에 담는다.
			session.setAttribute(CommConst.SESS_USER_VO, userVO);
			return getSuccess();
		}
	}
    
	/**
	 * 2023.07.28 LSH
	 * [AJAX] 회원가입 - 세션에 저장된 회원정보와 KODATA 정보의 일치여부를 확인한다.
	 */
    @RequestMapping("/com/user/matchKodata.do")
    @ResponseBody
    public boolean matchKodata(HttpSession session) throws Exception {
    	
    	UserVO      user   = (UserVO)session.getAttribute(CommConst.SESS_USER_VO);
    	KodataBizVO kodata = (KodataBizVO)session.getAttribute(CommConst.SESS_KODATA_INFO);
    	if (user == null)
    		return false;
    	if (kodata == null)
    		return false;
    	if (CommUtils.isEmpty(user.getUserNm()))
    		return false;
    	if (CommUtils.isEmpty(kodata.getReperName()))
    		return false;
    	// 회원정보의 이름과 KODATA정보의 대표자명이 일치하는지 여부를 반환한다.
    	return CommUtils.isEqual(user.getUserNm(), kodata.getReperName());
    }

	/**
	 * [AJAX] 회원가입 - 기업정보 저장처리 (파일업로드 포함)
	 * 1) 회원정보   - 세션에서 가져옴
	 * 2) KODATA정보 - 세션에서 가져옴
	 * 3) 회원의 ROLE 정의 / 그룹정보 정의
	 * 4) 업체파일 업로드 처리 (썸네일/위임장/사업자등록증)
	 * 
	 */
	@RequestMapping("/com/user/saveJoinEnt.do")
	@ResponseBody
	public Map saveJoinEnt(@ModelAttribute EntVO entVO
			, HttpServletRequest request
			, HttpSession session
			, BindingResult bindingResult) throws Exception {

		// 회원정보
		UserVO userVO = (UserVO)session.getAttribute(CommConst.SESS_USER_VO);
		// KODATA정보
		KodataBizVO kodataVO = (KodataBizVO)session.getAttribute(CommConst.SESS_KODATA_INFO);
		
		// 회원정보가 없는 경우 오류
		if (userVO == null)
			return getFailure(message.getMessage("error.comm.notValid"));
		
		// KODATA 정보이면서 KED_CD가 다른 경우 오류
		if (CommConst.YES.equals(entVO.getKodataYn()) &&
			!CommUtils.isEqual(entVO.getKdCd(), kodataVO.getKedcd()))
			return getFailure(message.getMessage("error.comm.notValid"));
		
		// 사업자번호 포맷제거
		Pattern p = Pattern.compile("[^0-9]");
		entVO.setBrno(p.matcher(entVO.getBrno()).replaceAll(""));
		
		// 사업자번호 기준 업체정보 조회
		Map ent = entService.viewEntByBrno(entVO.getBrno());
		if (ent != null) {
			entVO.setUseSttsCd ((String)ent.get("useSttsCd"));
			entVO.setBzentyNo  ((String)ent.get("bzentyNo"));
			entVO.setRprsYn    ((String)ent.get("rprsYn"));
			entVO.setExistYn   (CommConst.YES);
			// 일반계정인 경우 그룹코드 검증
			if (!CommConst.YES.equals(entVO.getRprsYn())) {
				// 멤버회원여부 설정
				entVO.setMemberYn(CommConst.YES);
				EntVO obj = entService.getEnt(entVO.getBzentyNo());
				// 그룹코드가 일치하지 않는 경우
				if (!CommUtils.isEqual(obj.getGroupCd(), entVO.getGroupCd()))
					return getFailure(message.getMessage("error.user.notEqualGroupCd"));
			} 
		}
		// 사용자의 ROLE 정의
		userVO.setRoleId(CodeConst.getBzentyRole(entVO.getBzentySeCd()));
		// 기업정보 처리모드 설정
		entVO.setMode(CommConst.INSERT);
		entVO.setAct (CommConst.ACT_BZ_INFO);
		
		// 사용상태 정의
		if (CommUtils.isEmpty(entVO.getUseSttsCd())) {
			// 2023.07.28 LSH
			// 경영체인 경우에도 관리자 승인후 메뉴이용 가능하므로
			// 가입시엔 미승인 처리되어야 함
			//if (CommConst.isEntRole(userVO.getRoleId()))
			//	entVO.setUseSttsCd(CodeConst.USE_STATUS_USABLE);
			// 사용상태 (미사용 - 승인전)
			entVO.setUseSttsCd(CodeConst.APRV_STTS_WAITING);
		}
		
		// 대표계정인 경우
		if (CommConst.YES.equals(entVO.getRprsYn())) {
			// 다중파일을 임시경로에 업로드한다.
			List<FileInfo> files = fileManager.multiFileUploadAdditional(request, null);
			entVO.setSaveFiles(files);
		}

    	// 저장데이터에 맞게 REBUILD
    	entVO.rebuildProperties(kodataVO);
    	
        // 저장할 입력값 검증
    	entValidator.validate(entVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Ent Regist Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 현재 Context Path 변수 바인딩 (메일발송을 위한 부분)
        userVO.setGsContext(CommBizUtils.getDomainContext(request));
        
		// 회원가입처리
		if (userService.saveJoinEnt(userVO, entVO) > 0)
			return getSuccess("userId", userVO.getUserId());
		// 실패결과 반환
		return getFailure();
	}
		    
	/**
	 * [AJAX] 마이페이지 - 개인정보 변경처리
	 */
	@RequestMapping("/com/user/updtUser.do")
	@ResponseBody
	public Map updtUser(@ModelAttribute UserVO userVO,
			HttpServletRequest request, 
			HttpSession session,
			BindingResult bindingResult) throws Exception {
		
		// 세션사용자정보를 정의
		setGlobalSession(request, userVO);
		// 저장모드 정의
		userVO.setMode(CommConst.UPDATE);
		// 저장에 맞게 입력값 REBUILD
		userVO.rebuildProperties(request, session);
        // 입력값 검증
    	userValidator.validate(userVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("User Update Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
		// 사용자 비밀번호 암호화
		userVO.encryptPswd();

		// 개인정보 변경처리
		if (userService.updtUser(userVO) > 0)
			return getSuccess();

		return getFailure();
	}

	/**
	 * [AJAX] 마이페이지 - 회원탈퇴처리
	 * 
	 * - 대표계정 회원탈퇴 불가처리함
     * - TODO 개인단위의 미팅신청 정보의 처리여부 확인필요
     * - TODO 개인단위의 매칭설정 정보의 처리여부 확인필요
     * - 현재는 회원정보만 비활성처리하는 것으로 협의함
	 */
	@RequestMapping("/com/user/deltUser.do")
	@ResponseBody
	public Map deltUser(@ModelAttribute UserVO userVO, 
			HttpServletRequest request, 
			HttpSession session,
			BindingResult bindingResult) throws Exception {
		
		// 세션사용자정보를 정의
		setGlobalSession(request, userVO);
		// 저장모드 정의
		userVO.setMode(CommConst.DELETE);
		// 저장에 맞게 입력값 REBUILD
		userVO.rebuildProperties(request, session);
        // 입력값 검증
    	userValidator.validate(userVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("User Delete Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
		// 회원탈퇴처리
		if (userService.deltUser(userVO) > 0) {
			return getSuccess();
		}
		return getFailure();
	}

	/**
	 * 2023.09.04 LSH 반려 업체 수정 처리
	 * 
	 * LoginController의 modalEntCmpl에서 호출됨
	 */
	@RequestMapping("/com/user/saveEntCmpl.do")
	@ResponseBody
	public Map saveEntCmpl(@ModelAttribute EntVO entVO
			, HttpServletRequest request) throws Exception {

    	UserInfo user = (UserInfo)request.getSession().getAttribute(CommConst.SESS_REJECT_USER);
    	if (user == null)
    		throw new BusinessException(message.getMessage("error.comm.noAuthTask"));
    	
    	entVO.setGsUserNo  (user.getUserNo());
    	entVO.setGsBzentyNo(user.getBzentyNo());
    	entVO.setMode      (CommConst.UPDATE);
    	// 2023.09.07 재신청상태로 변경
    	entVO.setUseSttsCd (CodeConst.APRV_STTS_REAPPLY);
		// 사용자의 ROLE 정의
    	user.setRoleId(CodeConst.getBzentyRole(entVO.getBzentySeCd()));
    	// 수정전 업체유형 정의
    	entVO.setBzentySeCdOrg(user.getBzentySeCd());
    	// 분야정보 REBUILD
    	entVO.rebuildProperties();
		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadAdditional(request, null);
		entVO.setSaveFiles(files);
		userService.saveEntCmpl(user, entVO);
		
		return getSuccess();
	}

}
