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

import business.com.CommBizUtils;
import business.usr.mypage.service.MeetingService;
import business.usr.mypage.service.MeetingVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 미팅신청 Controller
 * 
 * @class   : MeetingController
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
public class MeetingController extends BaseController {

    @Resource(name="MeetingService")
    protected MeetingService meetingService;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private MeetingValidator meetingValidator;

    /**
     * 마이페이지 - 문의내역 - 투자자미팅요청내역 화면 오픈
     */
    @RequestMapping("/usr/mypage/meeting/openMeeting.do")
    public String openMeeting(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MeetingVO meetingVO) throws Exception {
		
    	setGlobalSessionVO(request, meetingVO);
        
    	// 경영체기준(E)으로 기본값 정의
        if (CommUtils.isEmpty(meetingVO.getSrchMode()))
        	meetingVO.setSrchMode("E");
    	//미팅 개수 조회
    	int meetingCount = meetingService.listMeetingCount(meetingVO);
    	meetingVO.setMeetingCount(meetingCount);
    	
        model.addAttribute("model", meetingVO);
    	
        return "usr/mypage/meeting/openMeeting";
    }
    
    /**
     * 미팅신청 모달팝업 오픈
     */
    @RequestMapping("/usr/mypage/meeting/modalMeeting.do")
    public String modalMeeting(ModelMap model
    		, HttpServletRequest request
            , @RequestBody MeetingVO meetingVO) throws Exception {
				
        setGlobalSessionVO(request, meetingVO);
        
        // -------------------- Default Setting End -----------------------//

        model.addAttribute("model", meetingVO);
    	
        return "usr/mypage/meeting/modalMeeting";
    }

    /**
     * 미팅신청 목록JSON 반환
     * 
     * @param meetingVO.srchMode = "E" : 마이페이지 - 문의내역 - 투자자의 미팅요청내역 (기본값)
     * @param meetingVO.srchMode = "I" : 마이페이지 - 신청내역 - 미팅신청내역
     * @param meetingVO.srchType = "A" : 최신순 정렬 (기본값)
     * @param meetingVO.srchType = "B" : 오래된순 정렬
     */
    @RequestMapping("/usr/mypage/meeting/getListMeeting.do")
    @ResponseBody
    public Map getListMeeting(HttpServletRequest request
            , @ModelAttribute MeetingVO meetingVO) throws Exception {

        setGlobalSessionVO(request, meetingVO);

        // -------------------- Default Setting End -----------------------//

        // 검색모드가 없는경우 경영체기준(E)으로 기본값 정의
        if (CommUtils.isEmpty(meetingVO.getSrchMode()))
        	meetingVO.setSrchMode("E");
        
        // 정렬방식이 없는경우 최신순정렬(A)로 기본값 정의
        if (CommUtils.isEmpty(meetingVO.getSrchType()))
        	meetingVO.setSrchType("A");

        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = meetingService.listMeeting(meetingVO, page, size);
        }
        else {
        	list = meetingService.listMeeting(meetingVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 미팅신청 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/usr/mypage/meeting/saveMeeting.do")
    @ResponseBody
    public Map saveMeeting(@RequestBody MeetingVO meetingVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, meetingVO);

        // -------------------- Default Setting End -----------------------//
    	// 로그인 사용자정보 재정의
        meetingVO.setAplcntNo(meetingVO.getGsUserNo());
        // 현재 Context Path 변수 바인딩 (메일발송을 위한 부분)
        meetingVO.setGsContext(CommBizUtils.getDomainContext(request));

        // 저장할 입력값 검증
		meetingValidator.validate(meetingVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Meeting Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 미팅신청를 저장한다.
    	String result = meetingService.saveMeeting(meetingVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, MeetingVO meetingVO) {
    	
        setGlobalSession(request, meetingVO);

        if (meetingVO.getUserInfo(request) != null) {
        	meetingVO.setGsUserNo  (meetingVO.getUserInfo(request).getUserNo  ());
        	meetingVO.setGsRoleId  (meetingVO.getUserInfo(request).getRoleId  ());
        	meetingVO.setGsBzentyNo(meetingVO.getUserInfo(request).getBzentyNo());
        }
    }
}
