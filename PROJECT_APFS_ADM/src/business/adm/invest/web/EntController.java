package business.adm.invest.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import business.adm.invest.service.EntService;
import business.adm.invest.service.EntVO;
import business.adm.invest.service.FundVO;
import business.com.CommBizUtils;
import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.com.user.service.UserVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 회원관리-업체관리 Controller
 * 
 * @class   : EntController
 * @author  : LHB
 * @since   : 2023.06.12
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.12      LHB               First Coding.
 */
@Controller
@SuppressWarnings({"all"})
public class EntController extends BaseController {
	
	@Autowired
	protected EntService entService;
	
	@Autowired
	protected EntValidator entValidator;

    /**
     * 회원관리-업체관리 화면 오픈
     */
    @RequestMapping("/adm/invest/ent/openEnt.do")
    public String listCompany(ModelMap model, HttpServletRequest request, @ModelAttribute HashMap params, @ModelAttribute EntVO entVO) throws Exception {
    	setGlobalSessionVO(request, entVO);
    	
        return "adm/invest/ent/openEnt";
    }
    
    /**
     * 회원관리-업체관리 등록/수정 화면 오픈
     */
    @RequestMapping("/adm/invest/ent/modalEntForm.do")
    public String modalEntForm(ModelMap model, HttpServletRequest request, @RequestBody EntVO entVO) throws Exception {
    	setGlobalSessionVO(request, entVO);
        
    	String bzentyNo = entVO.getBzentyNo();
    	
        if (CommUtils.isNotEmpty(bzentyNo)) {
        	EntVO data = entService.viewEnt(entVO);
        	
        	if (data == null) {
        		throw new ModalBusinessException(message.getMessage("exception.notKey"));
        	}
        	
        	UserVO rprsInfo = entService.getRprsInfo(bzentyNo);
        	
        	// 수정모드
        	data.setMode(CommConst.UPDATE);
        	model.addAttribute("model",		data);
        	model.addAttribute("rprsInfo",	rprsInfo);
        } else {
        	// 등록모드
        	entVO.setMode(CommConst.INSERT);
        	model.addAttribute("model", entVO);
        }
        
        return "adm/invest/ent/modalEntForm";
    }
    
    /**
	 * 회원관리-업체관리 목록 JSON 반환 (일반 GRID)
	 */
	@RequestMapping("/adm/invest/ent/getListEnt.do")
	@ResponseBody
	public Map getListFund(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam HashMap params, 
			@ModelAttribute EntVO entVO) throws Exception {
		setGlobalSessionVO(request, entVO);
		// -------------------- Default Setting End -----------------------//

		List list = null;
        if (params.containsKey("page")) {
			int page = CommUtils.getInt(params, "page");
			int size = CommUtils.getInt(params, "rows");
			list = entService.listEnt(entVO, page, size);
		} else {
			list = entService.listEnt(entVO);
		}
        
		// 일반 GRID용 결과값 반환
        return getPaginatedResult(list);
	}
	
	/**
     * 회원관리-업체관리 공통저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/adm/invest/ent/saveEnt.do")
    @ResponseBody
    public Map saveEnt(ModelMap model,
    		@ModelAttribute EntVO entVO,
    		HttpServletRequest request,
    		BindingResult bindingResult) throws Exception {
    	// 세션사용자정보를 정의
    	setGlobalSessionVO(request, entVO);
        
        // 입력값 검증
    	entValidator.validate(entVO, bindingResult);
        
    	// LSH. 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Save Ent Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 현재 Context Path 변수 바인딩 (메일발송을 위한 부분)
        // 2023.08.29 LSH 수정 
        // 사용자쪽 이미지를 사용하기 위해 컨텍스트를 제외한 도메인을 설정한다.
        entVO.setGsContext(CommBizUtils.getDomain(request));
        logger.debug("MAIL CONTEXT : "+CommBizUtils.getDomain(request));

        String result = entService.saveEnt(entVO);
        
        return getSuccess("Message", result);
    }
    
    
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, EntVO entVO) {
        setGlobalSession(request, entVO);

        if (entVO.getUserInfo(request) != null) {
        	entVO.setGsUserNo(entVO.getUserInfo(request).getUserNo());
        	entVO.setGsRoleId(entVO.getUserInfo(request).getRoleId());
        }
    }
}
