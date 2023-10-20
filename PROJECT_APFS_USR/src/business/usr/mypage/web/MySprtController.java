package business.usr.mypage.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import business.com.CommFormFile;
import business.com.common.service.CommService;
import business.com.exception.ModalBusinessException;
import business.com.user.service.GroupService;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
import business.usr.invest.service.FundService;
import business.usr.invest.service.FundVO;
import business.usr.invest.web.FundSprtValidator;
import business.usr.mypage.service.MyPageService;
import business.usr.mypage.service.MyPageVO;
import business.usr.support.service.SprtService;
import business.usr.support.service.SprtVO;
import business.usr.support.web.SprtValidator;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 마이페이지 - 신청내역 Controller
 * 
 * @class   : MySprtController
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
public class MySprtController extends BaseController {

	@Resource(name="MyPageService")
    protected MyPageService myPageService;

    @Resource(name="SprtService")
    protected SprtService sprtService;

    @Resource(name="FundService")
    protected FundService fundService;

    @Resource(name="CommService")
    protected CommService commService;

	@Resource(name = "fileManager")
	protected FileManager fileManager;

    @Resource(name="BizFileService")
    protected BizFileService bizFileService;

    @Resource(name="GroupService")
    protected GroupService groupService;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private SprtValidator sprtValidator;
    
    // 데이터 검증용 VALIDATOR
    @Autowired 
    private FundSprtValidator fundSprtValidator;

    /**
     * 마이페이지 - 신청내역 화면 오픈
     */
    @RequestMapping("/usr/mypage/support/openSprt.do")
    public String openSprt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MyPageVO myPageVO) throws Exception {
		
        setGlobalSessionVO(request, myPageVO, false);
    	
        // 신청구분이 없을 경우
        if (CommUtils.isEmpty(myPageVO.getAplyCd())) {
        	// 경영체 타입인 경우
        	if (CommConst.ALIAS_ENT.equals(myPageVO.getAplyType()))
        		myPageVO.setAplyCd(CodeConst.SPRT_APLY_SE_BEFORE);
        	// 투자자 타입인 경우
        	else
        		myPageVO.setAplyCd(CodeConst.MYPG_APLY_BM);
        }
        // 제목 정의
        myPageVO.setAplyNm(
       		commService.getCodeName(CodeConst.MYPG_APLY_SE, myPageVO.getAplyCd())
        );
        // 프로그램번호가 없는 경우
        if (CodeConst.isCrowdCode(myPageVO.getAplyCd()) &&
        	CommUtils.isEmpty(myPageVO.getPrgrmNo()))
        	myPageVO.setPrgrmNo(CodeConst.PRGRM_COATING);

        model.addAttribute("model", myPageVO);

        return "usr/mypage/support/openSprt";
    }

    /**
     * 마이페이지 - 신청내역 종류버튼(그룹) 목록 조회
     */
    @RequestMapping("/usr/mypage/support/getListAplyGroup.do")
    @ResponseBody
    public List getListAplyGroup(HttpServletRequest request, @ModelAttribute MyPageVO myPageVO) throws Exception {
		
        setGlobalSessionVO(request, myPageVO, false);
        
        return myPageService.listAplyGroup(myPageVO);
    }

    /**
     * 신청정보조회 모달팝업 오픈
     * 1) 투자유치전지원
     *    - 진행현황목록
     *    - 경영체담당자정보 (업체)
     * 2) 투자유치후지원
     *    - 결과확인목록
     *    - 경영체담당자정보 (업체)
     *    - 제출서류목록 (업체)
     * 3) 농식품크라우드펀딩
     *    - 경영체담당자정보 (업체)
     *    - 펀딩시도확인여부 (컨설팅지원)
     *    - 제출서류목록
     *    - 코멘트
     */
    @RequestMapping("/usr/mypage/support/modalSprtView.do")
    public String modalSprtView(ModelMap model
    		, HttpServletRequest request
    		, @RequestBody MyPageVO myPageVO) throws Exception {
    	
    	// 권한이 없는 경우 Exception이 발생함
		try {
	        setGlobalSessionVO(request, myPageVO, false);
		} catch(BusinessException be) {
			// 모달스타일의 오류페이지 연결을 위한 처리
			throw new ModalBusinessException(be.getMessage());
		}

        SprtVO obj = sprtService.viewSprt(myPageVO.getAplyNo());
        
        // 조회된 정보가 없는 경우
        if (obj == null)
    		throw new ModalBusinessException("신청정보를 조회할 수 없습니다.");
        
        myPageVO.setUpdateYn(CommConst.NO);
        
        // 보완요청상태인 경우 수정버튼 표시
        // 투자전지원은 수정제외
        if (CodeConst.STATUS_SPLMNT.equals(obj.getStatusCd()) &&
        	!CodeConst.isBeforeCode(obj.getSprtSeCd()))
        	myPageVO.setUpdateYn(CommConst.YES);
    	
    	model.addAttribute("model", myPageVO);
    	
        return "usr/mypage/support/modalSprtView";
    }

    /**
     * 신청정보수정 모달팝업 오픈
     */
    @RequestMapping("/usr/mypage/support/modalSprtForm.do")
    public String modalSprtForm(ModelMap model
    		, HttpServletRequest request
    		, @RequestBody MyPageVO myPageVO) throws Exception {
    	
        setGlobalSessionVO(request, myPageVO, true);

        SprtVO obj = sprtService.viewSprt(myPageVO.getAplyNo());
        
        // 조회된 정보가 없는 경우
        if (obj == null)
    		throw new ModalBusinessException("신청정보를 조회할 수 없습니다.");
        
        myPageVO.setUpdateYn(CommConst.NO);

        // 보완요청상태인 경우 수정버튼 표시
        // 투자전지원은 수정제외
        if (CodeConst.STATUS_SPLMNT.equals(obj.getStatusCd()) &&
           	!CodeConst.isBeforeCode(obj.getSprtSeCd()))
        	myPageVO.setUpdateYn(CommConst.YES);
    	
    	model.addAttribute("model", myPageVO);
    	
        return "usr/mypage/support/modalSprtForm";
    }

    /**
     * 투자지원신청정보 조회JSON 반환
     */
    @RequestMapping("/usr/mypage/support/getSprt.do")
    @ResponseBody
    public Map getSprt(HttpServletRequest request, @ModelAttribute MyPageVO myPageVO) throws Exception {

        setGlobalSessionVO(request, myPageVO, false);

        SprtVO obj = sprtService.viewSprt(myPageVO.getAplyNo());
        
        // 조회된 정보가 없는 경우
        if (obj == null)
        	return getFailure(message.getMessage("error.comm.notTarget"));

        return getSuccess(obj);
    }

    /**
     * 투자유치후 지원사업 안내문 다운로드
     */
    @RequestMapping("/usr/mypage/support/downloadNotice.do")
    public void downloadNotice(
    		HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {
    	
        // 다운로드할 양식 파일 정보
    	CommFormFile cf = CommFormFile.SPRT_NOTICE;
        
        FileInfo fileInfo = FileInfo.builder()
							.saveName(cf.getSaveName())
							.fileName(cf.getFileName())
							.fullPath(cf.getFullPath())
							.build();
        // 실제 파일 다운로드 처리
        fileManager.procFileDownload(request, response, fileInfo);
    }

    /**
     * 마이페이지(투자자) - 경영체지원현황 - 상태변경처리 (다중처리)
     */
    @RequestMapping("/usr/mypage/support/updtSprt.do")
    @ResponseBody
    public Map updtSprt(@RequestBody MyPageVO myPageVO
    		, HttpServletRequest request
    		, HttpSession session
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, myPageVO, true);
        
        FundVO fundVO = FundVO.builder()
        		.sprtSttsCd(myPageVO.getSprtSttsCd())
        		.gsUserNo  (myPageVO.getGsUserNo  ())
        		.sprtList  (myPageVO.getSprtList  ())
        		.build     ();
        fundVO.setMode(CommConst.UPDATE);

        // 저장할 입력값 검증
    	fundSprtValidator.validate(fundVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("FundSprt Update Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 펀드지원정보(IR지원)의 상태변경을 저장한다.
    	String result = fundService.updateFundSprt(fundVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
    
    /**
     * 보완요청상태의 지원신청을 수정제출처리
     * 제출서류가 없는 경우에도 처리 가능
     */
    @RequestMapping("/usr/mypage/support/saveFile.do")
    @ResponseBody
    public Map saveFile(@RequestBody MyPageVO myPageVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, myPageVO, true);

        // 검증을 위한 지원신청정보 조회
        SprtVO sprtVO = sprtService.viewSprt(myPageVO.getAplyNo());
        sprtVO.setUserInfo  (myPageVO.getUserInfo  ());
        sprtVO.setGsUserNo  (myPageVO.getGsUserNo  ());
        sprtVO.setGsRoleId  (myPageVO.getGsRoleId  ());
        sprtVO.setGsBzentyNo(myPageVO.getGsBzentyNo());
        sprtVO.setGsBzentyYn(myPageVO.getGsBzentyYn());

        // 저장데이터에 맞게 REBUILD
        sprtVO.rebuildSplmntProperties();
        // 입력된 정보맵핑
        sprtVO.setMode       (myPageVO.getMode       ());
        sprtVO.setSaveFiles  (myPageVO.getSaveFiles  ());
        sprtVO.setRemoveFiles(myPageVO.getRemoveFiles());
        
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
    	
        // 저장할 입력값 검증
    	sprtValidator.validate(sprtVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Sprt Splmnt File Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 투자지원신청 제출서류를 저장한다.
    	String result = sprtService.saveFile(sprtVO);
    	
    	// 성공결과 반환
        return getSuccess(result);
    }
    
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     * 신청구분타입 ENT (경영체) : 일반회원 / 경영체회원 / 관리자
     * 신청구분타입 INV (투자자) : 투자자회원
     * 그외 접근불가
     */
    private void setGlobalSessionVO(HttpServletRequest request, MyPageVO myPageVO, boolean saveMode) {
    	
        setGlobalSession(request, myPageVO);

        if (myPageVO.getUserInfo(request) != null) {
        	myPageVO.setUserInfo  (myPageVO.getUserInfo(request));
        	myPageVO.setGsUserNo  (myPageVO.getUserInfo(request).getUserNo  ());
        	myPageVO.setGsRoleId  (myPageVO.getUserInfo(request).getRoleId  ());
        	myPageVO.setGsBzentyNo(myPageVO.getUserInfo(request).getBzentyNo());
        	// 업체회원여부
        	myPageVO.setGsBzentyYn(
    			CommConst.isBizRole(myPageVO.getGsRoleId()) ?
    			CommConst.YES :
    			CommConst.NO
        	);
        }
        myPageVO.setAplyType(CommConst.ALIAS_NON);
        // 조회가능여부 설정
        myPageVO.setSelectYn(CommConst.NO);
        // 수정가능여부 설정
        myPageVO.setUpdateYn(CommConst.NO);
        
        if (myPageVO.getUserInfo(request) != null) {
	        // 투자자회원인 경우
        	// 2023.09.12 농금원 포함
	        if (myPageVO.getUserInfo(request).isInv() ||
	        	myPageVO.getUserInfo(request).isMng())
	        	myPageVO.setAplyType(CommConst.ALIAS_INV);
	        // 관리자/경영체/일발회원인 경우
	        if (myPageVO.getUserInfo(request).isAdmin() || 
	        	myPageVO.getUserInfo(request).isEnt  () || 
	        	myPageVO.getUserInfo(request).isUsr  ())
	        	myPageVO.setAplyType(CommConst.ALIAS_ENT);
        }
        
        // 그룹관리 권한설정에 따른 기업정보 접근권한코드
        myPageVO.setAuthCd(
	        groupService.getGrpAuthCd(
				myPageVO.getGsUserNo(), 
				CodeConst.GROUP_MENU_SPRT
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

        // 2023.07.05 LSH 
    	// 그룹관리 권한설정에 따른 접근권한 체크
    	// 권한이 없는 경우 Exception이 발생함
		try {
	    	groupService.access(
				myPageVO.getGsUserNo(), 
				myPageVO.getGsRoleId(), 
				CodeConst.GROUP_MENU_SPRT,
				saveMode);
		} catch(BusinessException be) {
	       	if (saveMode) {
				// 모달스타일의 오류페이지 연결을 위한 처리
				throw new ModalBusinessException(be.getMessage());
	    	}
	       	throw(be);
		}
    }
}
