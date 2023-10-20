package business.sys.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.adm.CodeConst;
import business.adm.invest.service.EntService;
import business.adm.invest.web.FundValidator;
import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.sys.user.service.UserInfoService;
import business.sys.user.service.UserInfoVO;
import common.base.BaseController;
import common.util.CommUtils;
import egovframework.com.utl.sim.service.EgovFileScrty;

/**
 * [컨트롤러클래스] - 회원관리-사용자관리 Controller
 *
 * @class : UserInfoController
 * @author : LSH
 * @since : 2021.09.09
 * @version : 1.1
 *
 *   수정일       수정자                  수정내용
 *  --------   --------    -------------------------------------
 *  21.09.09     LSH               First Coding.
 *  23.06.12     LHB                 모달 변경.
 */
@Controller
@SuppressWarnings({ "all" })
@RequestMapping("/adm")
public class UserInfoController extends BaseController {

	@Resource(name = "UserInfoService")
	protected UserInfoService userInfoService;
	
	@Autowired 
    private UserInfoValidator userInfoValidator;
	
	@Autowired
	protected EntService entService;

	/**
	 * 회원관리-사용자관리 화면
	 */
	@RequestMapping("/sys/user/openUserInfo.do")
	public String openUserInfo(ModelMap model, 
			HttpServletRequest request,
			@ModelAttribute UserInfoVO userInfoVO) throws Exception {
		setGlobalSessionVO(request, userInfoVO);

		model.addAttribute("model", userInfoVO);

		return "adm/sys/user/openUserInfo";
	}
	
	/**
     * 회원관리-사용자관리 등록/수정 화면 오픈
     */
    @RequestMapping("/sys/user/modalUserInfoForm.do")
    public String modalUserInfoForm(ModelMap model, HttpServletRequest request, @RequestBody UserInfoVO userInfoVO) throws Exception {
    	setGlobalSessionVO(request, userInfoVO);
        
        if (CommUtils.isNotEmpty(userInfoVO.getUserNo())) {
        	UserInfoVO data = userInfoService.viewUserInfo(userInfoVO.getUserNo());
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model",    data);
        } else {
        	// 사용상태 기본값 정의 (사용)
        	userInfoVO.setUseSttsCd(CodeConst.USE_STATUS_USABLE);
        	// 회원구분 기본값 정의 (개인회원)
        	userInfoVO.setJoinCd(CommConst.JOIN_USR);
        	// 등록모드
        	userInfoVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", userInfoVO);
        }
        return "adm/sys/user/modalUserInfoForm";
    }

	/**
	 * 회원관리-사용자관리 목록JSON 반환 (EasyUI GRID)
	 */
	@RequestMapping("/sys/user/getListUserInfo.do")
	@ResponseBody
	public Map getListUserInfo(ModelMap model, 
			HttpServletRequest request,
			@RequestParam Map<String, String> reqMap) throws Exception {

		setGlobalSession(request, reqMap);
		// -------------------- Default Setting End -----------------------//

		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = userInfoService.listUserInfo(reqMap, page, size);
		} else {
			list = userInfoService.listUserInfo(reqMap);
		}
		
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}

	/**
	 * 회원관리-사용자관리 저장처리 (등록,수정,삭제) mode 값에 따라 분기
	 */
	@RequestMapping("/sys/user/saveUserInfo.do")
	@ResponseBody
	public Map saveUserInfo(@ModelAttribute UserInfoVO userInfoVO,
			HttpServletRequest request,
			BindingResult bindingResult) throws Exception {

		setGlobalSessionVO(request, userInfoVO);

		// 비밀번호 암호화처리
		if (CommUtils.isNotEmpty(userInfoVO.getUserId()) &&
			CommUtils.isNotEmpty(userInfoVO.getPswd())) {
			userInfoVO.setPswd(
					EgovFileScrty.encryptPassword(
							userInfoVO.getPswd(), 
							userInfoVO.getUserId()
					)
			);
		}
		// 사용상태 기본값처리
		if (CommUtils.isEmpty(userInfoVO.getUseSttsCd()))
			userInfoVO.setUseSttsCd(CodeConst.USE_STATUS_USABLE);
		
		// 2023.09.01 LSH 계정구분이 없을 경우 이전계정구분을 맵핑
		if (CommUtils.isEmpty(userInfoVO.getRprsYn()))
			userInfoVO.setRprsYn(userInfoVO.getRprsYnOrg());
		// 계정구분 기본값처리
		if (CommUtils.isEmpty(userInfoVO.getRprsYn()))
			userInfoVO.setRprsYn(CommConst.NO);
		
		// 입력값 검증
		userInfoValidator.validate(userInfoVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save UserInfo Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
		// 사용자정보를 저장한다.
		String result = userInfoService.saveUserInfo(userInfoVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}

	/**
	 * 회원관리-사용자관리 중복체크
	 */
	@RequestMapping("/sys/user/checkDuplicate.do")
	@ResponseBody
	public boolean checkDuplicate(@RequestParam("userId") String userId) throws Exception {
		return (userInfoService.existUserId(userId) == false);
	}

	/**
	 * 2023.08.12 LSH
	 * 비밀번호 초기화 및 회원에게 메일 발송 처리
	 */
	@RequestMapping("/sys/user/savePswdReset.do")
	@ResponseBody
	public Map savePswdReset(@RequestBody UserInfoVO userInfoVO,
			HttpServletRequest request) throws Exception {
		
		setGlobalSessionVO(request, userInfoVO);
        // 현재 Context Path 변수 바인딩 (메일발송을 위한 부분)
		//userInfoVO.setGsContext(CommBizUtils.getDomainContext(request));
        // 2023.08.29 LSH 수정 
        // 사용자쪽 이미지를 사용하기 위해 컨텍스트를 제외한 도메인을 설정한다.
		userInfoVO.setGsContext(CommBizUtils.getDomain(request));
		// 비밀번호 초기화 및 메일발송
		String result = userInfoService.savePswdReset(userInfoVO);
		// 성공결과 반환
		return getSuccess("Message", result);
	}

	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, UserInfoVO userInfoVO) {
    	
        setGlobalSession(request, userInfoVO);

        if (userInfoVO.getUserInfo(request) != null) {
        	userInfoVO.setGsUserNo(userInfoVO.getUserInfo(request).getUserNo());
        	userInfoVO.setGsRoleId(userInfoVO.getUserInfo(request).getRoleId());
        }
    }

}
