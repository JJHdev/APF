package business.adm.inform.web;

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

import business.adm.inform.service.BannerService;
import business.adm.inform.service.BannerVO;
import business.adm.invest.service.FundService;
import business.adm.invest.service.FundVO;
import business.adm.invest.web.FundValidator;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 운영관리-배너관리 Controller
 * 
 * @class   : BannerController
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자                수정내용
 *  --------   --------    ---------------------------
 * 23.06.09      LHB             First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class BannerController extends BaseController {
	
	@Autowired
    protected BannerService bannerService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	@Autowired 
    private BannerValidator bannerValidator;
	
    /**
     * 운영관리 - 배너관리 화면 오픈
     */
    @RequestMapping("/adm/inform/banner/openBanner.do")
    public String openBanner(ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params) throws Exception {
        setGlobalSession(request, params);
        model.addAttribute("model", params);
        return "adm/inform/banner/openBanner";
    }
    
    /**
     * 운영관리 - 배너관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/inform/banner/modalBannerForm.do")
    public String modalBannerForm(ModelMap model, HttpServletRequest request, @RequestBody BannerVO bannerVO) throws Exception {
        setGlobalSessionVO(request, bannerVO);
        
        if (CommUtils.isNotEmpty(bannerVO.getBannerNo())) {
        	BannerVO data = bannerService.viewBanner(bannerVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model", data);
        } else {
        	// 등록모드
        	bannerVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", bannerVO);
        }
        
        return "adm/inform/banner/modalBannerForm";
    }
    
    /**
	 * 배너관리 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/inform/banner/getListBanner.do")
	@ResponseBody
	public Map getListBanner(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam HashMap params, 
			@ModelAttribute BannerVO bannerVO) throws Exception {
		setGlobalSessionVO(request, bannerVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = bannerService.listBanner(bannerVO, page, size);
		} else {
			list = bannerService.listBanner(bannerVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 배너관리 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/inform/banner/saveBanner.do")
    @ResponseBody
    public Map saveBanner(ModelMap model,
    		@ModelAttribute BannerVO bannerVO,
    		BindingResult bindingResult,
    		HttpServletRequest request) throws Exception {
    	// 세션사용자정보를 정의
    	setGlobalSessionVO(request, bannerVO);
        
        // 입력값 검증
        bannerValidator.validate(bannerVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save Banner Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        
        // 다중파일을 임시경로에 업로드한다.
     	List<FileInfo> files = fileManager.multiFileUploadByName(request, null);

        String result = bannerService.saveBanner(bannerVO, files);
        
        return getSuccess("Message", result);
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, BannerVO bannerVO) {
    	
        setGlobalSession(request, bannerVO);

        if (bannerVO.getUserInfo(request) != null) {
        	bannerVO.setGsUserNo(bannerVO.getUserInfo(request).getUserNo());
        	bannerVO.setGsRoleId(bannerVO.getUserInfo(request).getRoleId());
        }
    }

}
