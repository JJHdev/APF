package business.adm.support.web;

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
import business.adm.invest.web.FundValidator;
import business.adm.support.service.PbancService;
import business.adm.support.service.PbancVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 운영관리-사업공고관리 Controller
 * 
 * @class   : PbancController
 * @author  : LHB
 * @since   : 2023.04.24
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class PbancController extends BaseController {
	
	@Resource(name = "PbancService")
	protected PbancService pbancService;
	
	@Resource(name = "fileManager")
	protected FileManager fileManager;
	
	@Autowired 
    private PbancValidator pbancValidator;

	/**
     * 운영관리-사업공고관리 화면 오픈
     */
    @RequestMapping("/adm/support/pbanc/openPbanc.do")
    public String openPbanc(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute PbancVO pbancVO) throws Exception {
    	setGlobalSessionVO(request, pbancVO);
        
        model.addAttribute("model", params);
        return "adm/support/pbanc/openPbanc";
    }
    
    /**
     * 운영관리-사업공고관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/support/pbanc/modalPbancForm.do")
    public String modalPbancForm(ModelMap model, HttpServletRequest request, @RequestBody PbancVO pbancVO) throws Exception {
        setGlobalSessionVO(request, pbancVO);
        
        if (CommUtils.isNotEmpty(pbancVO.getBizPbancNo())) {
        	PbancVO data = pbancService.viewPbanc(pbancVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	// 수정모드
        	if (pbancVO.getMode() != null && pbancVO.getMode().equals(CommConst.VIEW)) {
        		data.setMode(CommConst.VIEW);
        	} else {
        		data.setMode(CommConst.UPDATE);
        	}
        	model.addAttribute("model", data);
        } else {
        	// 등록모드
        	if (pbancVO.getMode() != null && pbancVO.getMode().equals(CommConst.VIEW)) {
        		pbancVO.setMode(CommConst.VIEW);
        	} else {
        		pbancVO.setMode(CommConst.INSERT);
        	}
        	
        	model.addAttribute("model", pbancVO);
        }
        
        return "adm/support/pbanc/modalPbancForm";
    }
    
    /**
     * 운영관리-사업공고관리 JSON 목록 반환
     */
    @RequestMapping("/adm/support/pbanc/getListPbanc.do")
    @ResponseBody
    public Map getListInvtSprt(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute PbancVO pbancVO) throws Exception {
    	setGlobalSessionVO(request, pbancVO);
        
        List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = pbancService.listPbanc(pbancVO, page, size);
		} else {
			list = pbancService.listPbanc(pbancVO);
		}
        
        // 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 운영관리-사업공고관리 상세조회 JSON 반환
     */
    @RequestMapping("/adm/support/pbanc/getPbanc.do")
    @ResponseBody
    public Map viewPbanc(ModelMap model,
    		HttpServletRequest request,
    		@RequestParam HashMap params,
    		@ModelAttribute PbancVO pbancVO) throws Exception {
    	setGlobalSessionVO(request, pbancVO);
        
        PbancVO result = pbancService.viewPbanc(pbancVO);
        
        return getSuccess(result);
    }
    
    /**
	 * 운영관리-사업공고관리 공통저장처리 (등록,수정,삭제)
	 */
	@RequestMapping("/adm/support/pbanc/savePbanc.do")
	@ResponseBody
	public Map savePbanc(ModelMap model,
			@RequestParam HashMap params,
			@ModelAttribute PbancVO pbancVO,
			BindingResult bindingResult,
			HttpServletRequest request) throws Exception {

		// 세션정보 정의
		setGlobalSessionVO(request, pbancVO);
		
		// 입력값 검증
		pbancValidator.validate(pbancVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save Pbanc Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadByName(request, null);
		
		// 게시판를 저장한다.
		String result = pbancService.savePbanc(pbancVO, files);
		
		// 성공결과 반환
		return getSuccess("Message", result);
	}
	
	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, PbancVO pbancVO) {
    	
        setGlobalSession(request, pbancVO);

        if (pbancVO.getUserInfo(request) != null) {
        	pbancVO.setGsUserNo(pbancVO.getUserInfo(request).getUserNo());
        	pbancVO.setGsRoleId(pbancVO.getUserInfo(request).getRoleId());
        }
    }

}