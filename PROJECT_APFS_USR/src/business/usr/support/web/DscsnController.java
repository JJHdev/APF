package business.usr.support.web;

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
import business.com.common.service.CommService;
import business.usr.CodeConst;
import business.usr.support.service.DscsnService;
import business.usr.support.service.DscsnVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 상담신청 Controller
 * 
 * @class   : DscsnController
 * @author  : LSH
 * @since   : 2023.05.25
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class DscsnController extends BaseController {

    @Resource(name="DscsnService")
    protected DscsnService dscsnService;

    @Resource(name="CommService")
    protected CommService commService;

    // 데이터 검증용 VALIDATOR
    @Autowired 
    private DscsnValidator validator;
    
    /**
     * 투자지원신청 - 투자유치 후 지원 상담신청폼 화면 오픈
     */
    @RequestMapping("/usr/support/support/formDscsn.do")
    public String formDscsn(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute DscsnVO dscsnVO) throws Exception {
				
        setGlobalSessionVO(request, dscsnVO);
        
        dscsnVO.setPrgrmNo(CodeConst.DSCSN_CODE);
        dscsnVO.setPrgrmNm(CodeConst.DSCSN_NAME);

        // 지원신청구분이 없을 경우
        if (CommUtils.isEmpty(dscsnVO.getSprtSeCd()))
        	dscsnVO.setSprtSeCd(CodeConst.SPRT_APLY_SE_AFTER);
        
        dscsnVO.setSprtSeNm(
   			commService.getCodeName(CodeConst.SPRT_APLY_SE, dscsnVO.getSprtSeCd())
    	);
    	
    	// 개인회원 신청불가
    	if (!"Y".equals(dscsnVO.getGsBzentyYn()))
    		throw new BusinessException("개인회원은 "+dscsnVO.getSprtSeNm()+"은 하실 수 없습니다.");
        
        // 신청단계가 없을 경우
        if (CommUtils.isEmpty(dscsnVO.getStepNo()))
        	dscsnVO.setStepNo("1");
        
        // 처리모드 설정
        if (CommUtils.isNotEmpty(dscsnVO.getDscsnAplyNo()) &&
        	dscsnService.existDscsn(dscsnVO.getDscsnAplyNo()))
        	dscsnVO.setMode(CommConst.UPDATE);
        else
        	dscsnVO.setMode(CommConst.INSERT);

        model.addAttribute("model", dscsnVO);
    	
        return "usr/support/support/formDscsn";
    }

    /**
     * 상담신청 조회JSON 반환
     */
    @RequestMapping("/usr/support/support/getDscsn.do")
    @ResponseBody
    public Map getDscsn(HttpServletRequest request, @ModelAttribute DscsnVO dscsnVO) throws Exception {

        setGlobalSessionVO(request, dscsnVO);

        DscsnVO obj = dscsnService.viewDscsn(dscsnVO);
        
        // 조회된 정보가 없는 경우
        if (obj == null)
        	return getFailure(message.getMessage("error.comm.notTarget"));

        // 관리자가 아니면서 작성자가 아닌 경우
        if (!CommConst.isAdminRole(dscsnVO.getGsRoleId()) &&
        	!CommUtils.isEqual(dscsnVO.getGsUserNo(), obj.getRgtrNo()))
        	return getFailure(message.getMessage("error.comm.notAccess"));
        
        return getSuccess(obj);
    }

    /**
     * 상담신청 저장처리 (등록,수정)
     */
    @RequestMapping("/usr/support/support/saveDscsn.do")
    @ResponseBody
    public Map saveDscsn(@RequestBody DscsnVO dscsnVO
    		, HttpServletRequest request
    		, HttpSession session
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, dscsnVO);

    	// 저장데이터에 맞게 REBUILD
        dscsnVO.rebuildProperties();
    	
        // 저장할 입력값 검증
    	validator.validate(dscsnVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        if (bindingResult.hasErrors()) {
        	logger.info("Dscsn Apply Validate Error...", bindingResult.getAllErrors());
        	return getFailure(bindingResult);
        }
        // 상담신청를 저장한다.
    	String aplyNo = dscsnService.saveDscsn(dscsnVO);
    	
    	if (CommUtils.isEmpty(aplyNo))
    		return getFailure();
    	
    	// 성공결과 반환
        return getSuccess(aplyNo);
    }
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, DscsnVO dscsnVO) {
    	
        setGlobalSession(request, dscsnVO);

        if (dscsnVO.getUserInfo(request) != null) {
        	dscsnVO.setGsUserNo  (dscsnVO.getUserInfo(request).getUserNo  ());
        	dscsnVO.setGsRoleId  (dscsnVO.getUserInfo(request).getRoleId  ());
        	dscsnVO.setGsBzentyNo(dscsnVO.getUserInfo(request).getBzentyNo());
        	// 업체회원여부
        	dscsnVO.setGsBzentyYn(
    			CommConst.isBizRole(dscsnVO.getGsRoleId()) ?
    			CommConst.YES :
    			CommConst.NO
        	);
        }
    }
}
