package business.usr.invest.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import business.com.CommConst;
import business.usr.mypage.service.MatchingService;
import commf.exception.BusinessException;
import common.base.BaseController;

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
public class MatchingInvtController extends BaseController {
    
    @Resource(name="MatchingService")
    protected MatchingService matchingService;

    /**
     * 투자서비스-매칭서비스-매칭설정 화면 오픈
     * 투자서비스 내의 URL로 변경함
     * 
     * 화면 표시 조건
     * 1) showMode = 'X' : 비로그인시
     * 2) showMode = 'N' : 로그인 - 매칭미설정시
     * 2) showMode = 'B' : 로그인 - 경영체인 경우
     * 3) showMode = 'I' : 로그인 - 투자자/유관기관인 경우
     */
    @RequestMapping("/usr/invest/matching/openMatchingInvt.do")
    public String openMatchingInvt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute HashMap params) throws Exception {
		
        setGlobalSession(request, params);
        
        String gsUserNo   = (String)params.get("gsUserNo");
        String gsRoleId   = (String)params.get("gsRoleId");
        String showMode   = CommConst.SHOW_MODE_LOGIN;
        // 로그인시
        if (CommConst.isLogin(gsUserNo)) {
        	// 매칭 미설정시
        	if (!matchingService.existMatching(gsUserNo)) {
        		showMode = CommConst.SHOW_MODE_CONFIG;
        	}
        	// 일반회원인 경우
        	else if (CommConst.isUsrRole(gsRoleId)) {
        		showMode = CommConst.SHOW_MODE_USR;
        	}
        	// 경영체인 경우
        	else if (CommConst.isEntRole(gsRoleId)) {
        		showMode = CommConst.SHOW_MODE_ENT;
        	}
        	// 투자자/유관기관/농금원/관리자인 경우
        	else if (CommConst.isAdminRole(gsRoleId) ||
        			 CommConst.isInvRole(gsRoleId) || 
        			 CommConst.isRisRole(gsRoleId) ||
        			 CommConst.isMngRole(gsRoleId)) {
        		showMode = CommConst.SHOW_MODE_INV;
        	}
        	else {
        		throw new BusinessException(message.getMessage("error.denied.message"));
        	}
        }
        
        model.addAttribute("showMode", showMode);
        model.addAttribute("srchMode", CommConst.SRCH_MODE_MATCH);
        model.addAttribute("mtchMode", CommConst.MTCH_MODE_INVT );

        return "usr/invest/matching/openMatchingInvt";
    }
}
