package business.usr.invest.web;

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

import business.com.CommBizUtils;
import business.com.exception.ModalBusinessException;
import business.usr.invest.service.FundService;
import business.usr.invest.service.FundVO;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 펀드정보(모태펀드) Controller
 * 
 * @class   : FundController
 * @author  : LSH
 * @since   : 2023.04.19
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class FundController extends BaseController {

    @Resource(name="FundService")
    protected FundService fundService;

	@Resource(name = "fileManager")
	protected FileManager fileManager;
    
    // 데이터 검증용 VALIDATOR
    @Autowired 
    private FundSprtValidator fundSprtValidator;

    /**
     * 투자서비스-투자자검색-스마트검색 화면 오픈
     */
    @RequestMapping("/usr/invest/fund/listFund.do")
    public String listFund(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute HashMap params) throws Exception {
		
        setGlobalSession(request, params);
        
        model.addAttribute("model", params);
    	
        return "usr/invest/fund/listFund";
    }

    /**
     * 투자분야선택 모달팝업 오픈
     */
    @RequestMapping("/usr/invest/fund/modalInvestField.do")
    public String modalInvestField(ModelMap model) throws Exception {
        return "/usr/invest/fund/modalInvestField";
    }

    /**
     * 펀드 지원하기 모달팝업 오픈
     */
    @RequestMapping("/usr/invest/fund/modalApplyFund.do")
    public String modalApplyFund(ModelMap model
    		, HttpServletRequest request
    		, @RequestBody FundVO fundVO) throws Exception {
    	
        setGlobalSessionVO(request, fundVO);

        FundVO infoObj = fundService.viewFund(fundVO.getFundNo());
    	
    	if (infoObj == null)
    		throw new ModalBusinessException("펀드정보를 조회할 수 없습니다.");
    	
    	// 지원업체 : 로그인사용자의 업체정보
    	infoObj.setBzentyNo(fundVO.getUserInfo(request).getBzentyNo());
    	infoObj.setBzentyNm(fundVO.getUserInfo(request).getBzentyNm());
    	
        // 펀드지원정보(IR지원) 조회
    	FundVO sprtObj = fundService.viewFundSprt(infoObj);
    	if (sprtObj != null)
    		throw new ModalBusinessException("이미 지원한 펀드입니다.");
    	
    	// 첨부파일타입
    	infoObj.setFileType(FileDirectory.BIZ.getCode());
    	
    	model.addAttribute("model", infoObj);
    	
        return "/usr/invest/fund/modalApplyFund";
    }

    /**
     * 펀드정보(모태펀드) 목록JSON 반환 (GRID)
     */
    @RequestMapping("/usr/invest/fund/getListFund.do")
    @ResponseBody
    public Map getListFund(@ModelAttribute FundVO fundVO
    		, HttpServletRequest request) throws Exception {

    	setGlobalSessionVO(request, fundVO);

        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = fundService.listFund(fundVO, page, size);
        }
        else {
        	list = fundService.listFund(fundVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 투자자기준 경영체의 펀드지원정보 목록JSON 반환 (GRID)
     * 마이페이지에서 사용됨 (투자자)
     */
    @RequestMapping("/usr/invest/fund/getListFundSprt.do")
    @ResponseBody
    public Map getListFundSprt(ModelMap model
    		, HttpServletRequest request
            , @RequestParam Map<String,String> reqMap
            , @ModelAttribute FundVO fundVO) throws Exception {

    	setGlobalSessionVO(request, fundVO);
        // -------------------- Default Setting End -----------------------//

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = fundService.listFundSprt(fundVO, page, size);
        }
        else {
        	list = fundService.listFundSprt(fundVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 펀드지원하기 저장처리
     */
    @RequestMapping("/usr/invest/fund/saveFundSprt.do")
    @ResponseBody
    public Map saveFundSprt(@ModelAttribute FundVO fundVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {
    	
    	setGlobalSessionVO(request, fundVO);
        // 현재 Context Path 변수 바인딩 (메일발송을 위한 부분)
    	fundVO.setGsContext(CommBizUtils.getDomainContext(request));

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
    	
        // 저장할 입력값 검증
    	fundSprtValidator.validate(fundVO, bindingResult);

    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("FundSprt Apply Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

        // 펀드지원정보(IR지원)를 등록한다.
    	String result = fundService.registFundSprt(fundVO, files);
    	
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, FundVO fundVO) {
    	
        setGlobalSession(request, fundVO);

        if (fundVO.getUserInfo(request) != null) {
        	fundVO.setGsUserNo  (fundVO.getUserInfo(request).getUserNo  ());
        	fundVO.setGsRoleId  (fundVO.getUserInfo(request).getRoleId  ());
        	fundVO.setGsBzentyNo(fundVO.getUserInfo(request).getBzentyNo());
        }
    }
}
