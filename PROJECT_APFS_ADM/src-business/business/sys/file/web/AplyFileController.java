package business.sys.file.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.sys.file.service.AplyFileService;
import business.sys.file.service.AplyFileVO;
import business.sys.file.service.PapeCodeService;
import common.base.BaseController;
import common.file.FileManager;
import common.util.CommUtils;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * [컨트롤러클래스] - 신청첨부파일 Controller
 *
 * @class   : AplyFileController
 * @author  : LSH
 * @since   : 2021.10.07
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
@RequestMapping("/adm")
public class AplyFileController extends BaseController {

    @Resource(name="AplyFileService")
    protected AplyFileService aplyFileService;

    @Resource(name="PapeCodeService")
    protected PapeCodeService papeCodeService;

    @Resource(name="fileManager")
    FileManager fileManager;

    /**
     * 신청첨부파일 화면 오픈
     */
    @RequestMapping("/sys/file/listAplyFile.do")
    public String listAplyFile(HttpServletRequest request
	        , ModelMap model
            , @ModelAttribute AplyFileVO aplyFileVO) throws Exception {

        setGlobalSession(request, aplyFileVO);
        // -------------------- Default Setting End -----------------------//

        model.addAttribute("model", aplyFileVO);

        return "adm/sys/file/listAplyFile";
    }

    /**
     * 신청첨부파일 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/sys/file/getListAplyFile.do")
    @ResponseBody
    public Map getListAplyFile(HttpServletRequest request
            , @RequestParam Map<String,String> reqMap
            , @ModelAttribute AplyFileVO aplyFileVO
            , ModelMap model) throws Exception {

        setGlobalSession(request, aplyFileVO);
        // -------------------- Default Setting End -----------------------//


        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = aplyFileService.listAplyFile(aplyFileVO, page, size);
        }
        else {
        	list = aplyFileService.listAplyFile(aplyFileVO);
        }
        // Easy UI GRID용 결과값 반환
        return getEasyUIResult(list);
    }

    /**
     * 신청첨부파일 조회JSON 반환
     */
    @RequestMapping("/sys/file/getAplyFile.do")
    @ResponseBody
    public Map getAplyFile(HttpServletRequest request
            , @ModelAttribute AplyFileVO aplyFileVO
			, ModelMap model) throws Exception {

        Map paramMap = getParameterMap(request, true);

        String sn = aplyFileVO.getSn();
        // 다운로드할 파일 정보 조회
    	AplyFileVO fileVO = _getAplyFile(sn, paramMap);

        return getSuccess(fileVO);
    }

    /**
     * 신청첨부파일 저장처리 (등록,수정,삭제)
     * mode 값에 따라 분기
     */
    @RequestMapping("/sys/file/saveAplyFile.do")
    @ResponseBody
    public Map saveAplyFile(HttpServletRequest request
			, @ModelAttribute AplyFileVO aplyFileVO) throws Exception {

        setGlobalSession(request, aplyFileVO);

        if (aplyFileVO.getUserInfo(request) != null)
        	aplyFileVO.setGsUserNo(aplyFileVO.getUserInfo(request).getUserNo());

        // 신청첨부파일를 저장한다.
    	String result = aplyFileService.saveAplyFile(aplyFileVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }

    /**
     * 파일 상세조회 및 권한 체크
     */
    private AplyFileVO _getAplyFile(String sn, Map paramMap) throws Exception {

    	// 파일정보조회
    	AplyFileVO fileVO = aplyFileService.viewAplyFile(sn);
    	if (fileVO == null)
    		throw new EgovBizException(message.getMessage("error.file.notExist"));

    	String gsRoleId = (String)paramMap.get("gsRoleId");
    	String gsUserNo = (String)paramMap.get("gsUserNo");
    	String gsUserId = (String)paramMap.get("gsUserId");
    	// 관리자인 경우 ACCESS OK
    	if (CommConst.isAdminRole(gsRoleId)) {
    		return fileVO;
    	}
    	// 회원사용자인 경우 본인인지 체크 파일 생성자이면 ACCESS OK
    	else if (CommUtils.isEqual(gsUserNo, fileVO.getRgtrNo())) {
   			return fileVO;
    	}
    	// 그외엔 NOT ACCESS
		throw new EgovBizException(message.getMessage("error.file.notAccess"));
    }
}
