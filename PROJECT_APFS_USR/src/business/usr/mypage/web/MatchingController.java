package business.usr.mypage.web;

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
import business.com.user.service.GroupService;
import business.usr.CodeConst;
import business.usr.mypage.service.MatchingService;
import business.usr.mypage.service.MatchingVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 매칭서비스 Controller
 * 
 * @class   : MatchingController
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
public class MatchingController extends BaseController {
    
    @Resource(name="MatchingService")
    protected MatchingService matchingService;

    @Resource(name="GroupService")
    protected GroupService groupService;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private MatchingValidator validator;
    
    /**
     * 마이페이지 - 매칭설정 화면 오픈
     */
    @RequestMapping("/usr/mypage/matching/openMatching.do")
    public String openMatching(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MatchingVO matchingVO) throws Exception {
				
        setGlobalSessionVO(request, matchingVO, false);
		
        model.addAttribute("model", matchingVO);
    	
        return "usr/mypage/matching/openMatching";
    }

    /**
     * 마이페이지 - 매칭설정 AJAX 조회
     */
    @RequestMapping("/usr/mypage/matching/getMatching.do")
    @ResponseBody
    public Map getMatching(HttpServletRequest request, @ModelAttribute MatchingVO matchingVO) throws Exception {

        setGlobalSessionVO(request, matchingVO, false);
        
        // 세션사용자번호 조건설정
        MatchingVO data = MatchingVO.builder()
        		.userNo(matchingVO.getGsUserNo())
        		.build();
        // 매칭설정목록 가져오기
        List list = matchingService.listMatching(data);
        
        if (CommUtils.isEmptyList(list))
        	return getFailure();
        // 설정목록을 화면에 맞는 데이터로 REBUILD
        data.rebuildResults(list);
    	// 결과 반환
        return getSuccess(data);
    }

    /**
     * 마이페이지 - 매칭설정 AJAX 저장처리 (등록,삭제)
     */
    @RequestMapping("/usr/mypage/matching/saveMatching.do")
    @ResponseBody
    public Map saveMatching(@RequestBody MatchingVO matchingVO
    		, HttpServletRequest request
    		, HttpSession session
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, matchingVO, true);

    	// 저장데이터에 맞게 REBUILD
        // (문자열배열을 객체배열로 변환처리)
        matchingVO.rebuildProperties();
    	
        // 저장할 입력값 검증
    	validator.validate(matchingVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Matching Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 매칭설정을 저장한다.
    	String result = matchingService.saveMatching(matchingVO);
    	// 성공결과 반환
        return getSuccess(result);
    }
    
    /**
     * 마이페이지 - 매칭결과 이동 처리
     * 투자서비스로 이동
     */
    @RequestMapping("/usr/mypage/matching/doneMatching.do")
    public String doneMatching(HttpServletRequest request, @ModelAttribute MatchingVO matchingVO) throws Exception {

        setGlobalSessionVO(request, matchingVO, false);
        
        String gsUrl = "/usr/invest/matching/openMatchingInvt.do";
    	
        return "redirect:"+gsUrl;
    }

    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, MatchingVO matchingVO, boolean saveMode) {
    	
        setGlobalSession(request, matchingVO);

        if (matchingVO.getUserInfo(request) != null) {
        	matchingVO.setGsUserNo  (matchingVO.getUserInfo(request).getUserNo  ());
        	matchingVO.setGsRoleId  (matchingVO.getUserInfo(request).getRoleId  ());
        	matchingVO.setGsBzentyNo(matchingVO.getUserInfo(request).getBzentyNo());
        }
        
        // 그룹관리 권한설정에 따른 접근권한 체크
    	groupService.access(
			matchingVO.getGsUserNo(), 
			matchingVO.getGsRoleId(), 
			CodeConst.GROUP_MENU_MATCHING,
			saveMode);
    	
        // 조회가능여부 설정
    	matchingVO.setSelectYn(CommConst.NO);
        // 수정가능여부 설정
    	matchingVO.setUpdateYn(CommConst.NO);
        
        // 그룹관리 권한설정에 따른 기업정보 접근권한코드
    	matchingVO.setAuthCd(
	        groupService.getGrpAuthCd(
	        	matchingVO.getGsUserNo(), 
				CodeConst.GROUP_MENU_MATCHING
			)
        );
        // 수정권한이 있는 경우
        if (CodeConst.GROUP_AUTH_MODIFY.equals(matchingVO.getAuthCd())) {
        	matchingVO.setUpdateYn(CommConst.YES);
        	matchingVO.setSelectYn(CommConst.YES);
        }
        // 조회권한이 있는 경우
        else if (CodeConst.GROUP_AUTH_VIEW.equals(matchingVO.getAuthCd())) {
        	matchingVO.setSelectYn(CommConst.YES);
        }
    }
}
