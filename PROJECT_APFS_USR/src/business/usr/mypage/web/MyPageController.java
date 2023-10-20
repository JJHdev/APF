package business.usr.mypage.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.user.service.GroupService;
import business.com.user.service.UserService;
import business.com.user.service.UserVO;
import business.usr.CodeConst;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import business.usr.invest.service.FundService;
import business.usr.invest.web.EntValidator;
import business.usr.mypage.service.MyPageVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 마이페이지 Controller
 * 
 * @class   : MyPageController
 * @author  : LSH
 * @since   : 2023.04.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class MyPageController extends BaseController {

    @Resource(name="GroupService")
    protected GroupService groupService;

    @Resource(name="UserService")
    protected UserService userService;

    @Resource(name="EntService")
    protected EntService entService;

    @Resource(name="FundService")
    protected FundService fundService;

	@Resource(name = "fileManager")
	protected FileManager fileManager;

    // 기업정보 저장 검증용 VALIDATOR
    @Autowired 
    private EntValidator entValidator;
	
    /**
     * 마이페이지 - 기본정보 화면 오픈
     * 
     * 기업정보 접근권한에 따른 조회/수정 기능 제한
     */
    @RequestMapping("/usr/mypage/mypage/openInfo.do")
    public String openInfo(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MyPageVO myPageVO) throws Exception {
				
    	setGlobalSessionVO(request, myPageVO);
    	
        model.addAttribute("model", myPageVO);
    	
        return "usr/mypage/mypage/openInfo";
    }

    /**
     * 마이페이지 - 회원정보 조회
     */
    @RequestMapping("/usr/mypage/mypage/getUsrInfo.do")
    @ResponseBody
    public Object getUsrInfo(HttpServletRequest request, @ModelAttribute MyPageVO myPageVO) throws Exception {
    	
        setGlobalSessionVO(request, myPageVO);
        
        // 회원정보 조회
        UserVO user = userService.getUser(myPageVO.getGsUserNo());
        
        if (user == null)
        	throw new BusinessException(message.getMessage("error.comm.noResult"));
        return user;
    }

    /**
     * 마이페이지 - 업체정보 조회
     * 기업회원인 경우
     */
    @RequestMapping("/usr/mypage/mypage/getEntInfo.do")
    @ResponseBody
    public Object getEntInfo(HttpServletRequest request, @ModelAttribute MyPageVO myPageVO) throws Exception {
    	
        setGlobalSessionVO(request, myPageVO);
        
        // 조회가능할 경우에만 처리
        if (CommConst.YES.equals(myPageVO.getSelectYn())) {
        	EntVO ent = entService.getEnt(myPageVO.getGsBzentyNo());
            if (ent == null)
            	throw new BusinessException(message.getMessage("error.comm.noResult"));
           	return ent;
        }
        return null;
    }

    /**
     * 마이페이지 - 투자조합목록 조회
     * 투자자인 경우
     */
    @RequestMapping("/usr/mypage/mypage/getListInvstr.do")
    @ResponseBody
    public List getListInvstr(HttpServletRequest request, @ModelAttribute MyPageVO myPageVO) throws Exception {
    	
        setGlobalSessionVO(request, myPageVO);
        
        // 투자자의 경우 펀드조합정보 조회
        if (myPageVO.getUserInfo(request).isInv()) {
        	return fundService.listFundByBzenty(myPageVO.getGsBzentyNo());
        }
        return null;
    }

    /**
     * [act:BI] 마이페이지 - 기본정보 - 기업기본정보 AJAX 저장처리
     */
    @RequestMapping("/usr/mypage/mypage/saveEnt.do")
    @ResponseBody
    public Map saveEnt(@RequestBody EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

    	// 세션정의 및 권한검증
    	validateSession(request, entVO);

    	// 저장데이터에 맞게 REBUILD
    	entVO.rebuildProperties();
    	
        // 저장할 입력값 검증
    	entValidator.validate(entVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Ent Update Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 기업정보를 저장한다.
    	String result = entService.saveEnt(entVO);
    	// 성공결과 반환
        return getSuccess(result);
    }

    /**
     * [act:BF] 마이페이지 - 기본정보 - 기업상세정보 저장처리 (파일업로드) 
     */
    @RequestMapping("/usr/mypage/mypage/saveEntFile.do")
    @ResponseBody
    public Map saveEntFile(@ModelAttribute EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

    	// 세션정의 및 권한검증
    	validateSession(request, entVO);

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadAdditional(request, null);
		entVO.setSaveFiles(files);

    	// 저장데이터에 맞게 REBUILD
    	entVO.rebuildProperties();
    	
        // 저장할 입력값 검증
    	entValidator.validate(entVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Ent Update Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 기업정보를 저장한다.
    	String result = entService.saveEnt(entVO);
    	// 성공결과 반환
        return getSuccess(result);
    }
    
    /**
     * 기업정보 저장 처리시 필요한 세션을 정의하고 
     * 권한에 대한 검증을 수행한다.
     */
    private void validateSession(HttpServletRequest request, EntVO entVO) throws Exception {

    	MyPageVO myPageVO = MyPageVO.builder().build();
    	// 세션정보 및 권한정의
        setGlobalSessionVO(request, myPageVO);
    	
        // 수정권한 체크
        if (!CommConst.YES.equals(myPageVO.getUpdateYn()))
        	throw new BusinessException("해당 기업정보에 대한 수정권한이 없습니다.");
        
        // 세션정보 정의
        entVO.setGsBzentyNo(myPageVO.getGsBzentyNo());
        entVO.setGsUserNo  (myPageVO.getGsUserNo  ());
        entVO.setGsRoleId  (myPageVO.getGsRoleId  ());
        entVO.setKodataYn  (myPageVO.getKodataYn  ());
    }

    /**
     * 세션정보를 모델의 변수에 바인딩한다
     * 기본정보를 바인딩한다.
     */
    private void setGlobalSessionVO(HttpServletRequest request, MyPageVO myPageVO) throws Exception {
    	
        setGlobalSession(request, myPageVO);

        if (myPageVO.getUserInfo(request) != null) {
        	myPageVO.setGsUserNo  (myPageVO.getUserInfo(request).getUserNo  ());
        	myPageVO.setGsRoleId  (myPageVO.getUserInfo(request).getRoleId  ());
        	myPageVO.setGsBzentyNo(myPageVO.getUserInfo(request).getBzentyNo());
        }
        // 대표계정여부 확인
        myPageVO.setRprsYn(groupService.getGrpRprsYn(myPageVO.getGsUserNo()));
        // 조회가능여부 설정
        myPageVO.setSelectYn(CommConst.NO);
        // 수정가능여부 설정
        myPageVO.setUpdateYn(CommConst.NO);
        
        // 업체회원인 경우
        if (myPageVO.getUserInfo(request).isBiz()) {
	        // 업체정보 조회
	        EntVO entVO = entService.getEnt(myPageVO.getGsBzentyNo());
	        // 승인여부 설정
	        myPageVO.setAccessYn(CommConst.NO);
	        // KODATA여부 설정
	        myPageVO.setKodataYn(CommConst.NO);
	        // 경영체의 경우
	        if (myPageVO.getUserInfo(request).isEnt()) {
	            // KODATA인 경우
	            if (CommUtils.isNotEmpty(entVO.getKdCd()))
	            	myPageVO.setKodataYn(CommConst.YES);
		        // 경영체의 경우 자동승인(?) 처리
	        	myPageVO.setAccessYn(CommConst.YES);
	        }
	        // 유관기관/투자자의 경우 승인여부 확인
	        else if (CodeConst.USE_STATUS_USABLE.equals(entVO.getUseSttsCd())) {
	        	myPageVO.setAccessYn(CommConst.YES);
	            // 조회가능여부 설정
	            myPageVO.setSelectYn(CommConst.YES);
	        }
        }
        // 그룹관리 권한설정에 따른 기업정보 접근권한코드
        myPageVO.setAuthCd(
	        groupService.getGrpAuthCd(
				myPageVO.getGsUserNo(), 
				CodeConst.GROUP_MENU_BZENTY
			)
        );
        // 수정권한이 있는 경우
        if (CodeConst.GROUP_AUTH_MODIFY.equals(myPageVO.getAuthCd())) {
        	myPageVO.setUpdateYn(CommConst.YES);
        	myPageVO.setSelectYn(CommConst.YES);
        }
        // 조회권한이 있는 경우
        else if (CodeConst.GROUP_AUTH_VIEW.equals(myPageVO.getAuthCd())) {
        	myPageVO.setSelectYn(CommConst.YES);
        }
    }
}
