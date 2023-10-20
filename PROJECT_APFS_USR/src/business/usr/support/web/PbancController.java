package business.usr.support.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.usr.support.service.PbancService;
import business.usr.support.service.PbancVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 정부지원사업 - 사업공고관리 Controller
 * 
 * @class   : PbancController
 * @author  : LSH
 * @since   : 2023.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class PbancController extends BaseController {

    @Resource(name="PbancService")
    protected PbancService pbancService;
    
    /**
     * 지원서비스 - 지원사업 통합검색 화면 오픈
     */
    @RequestMapping("/usr/support/pbanc/listPbanc.do")
    public String listPbanc(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute PbancVO pbancVO) throws Exception {
		
    	setGlobalSessionVO(request, pbancVO);
        
        // 검색조건용 모델 정의
        model.addAttribute("search", getSearchVO(pbancVO));
        
        return "usr/support/pbanc/listPbanc";
    }

    /**
     * 지원서비스 - 지원사업 통합검색 상세조회 오픈
     */
    @RequestMapping("/usr/support/pbanc/viewPbanc.do")
    public String viewPbanc(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute PbancVO pbancVO) throws Exception {
		
    	setGlobalSessionVO(request, pbancVO);
        
        PbancVO obj = pbancService.viewPbanc(pbancVO);
        
        if (obj == null)
        	throw new BusinessException("해당하는 지원사업의 사업공고가 존재하지 않습니다.");
        
        // 상세조회용 모델 바인딩
        model.addAttribute("model", obj);
        
        // 검색조건용 모델 정의
        model.addAttribute("search", getSearchVO(pbancVO));

        return "usr/support/pbanc/viewPbanc";
    }

    /**
     * 사업공고관리 목록JSON 반환 (GRID)
     */
    @RequestMapping("/usr/support/pbanc/getListPbanc.do")
    @ResponseBody
    public Map getListPbanc(HttpServletRequest request
            , @ModelAttribute PbancVO pbancVO) throws Exception {

    	setGlobalSessionVO(request, pbancVO);

        if (pbancVO.getUserInfo(request) != null) {
        	pbancVO.setGsUserNo  (pbancVO.getUserInfo(request).getUserNo  ());
        	pbancVO.setGsRoleId  (pbancVO.getUserInfo(request).getRoleId  ());
        	pbancVO.setGsBzentyNo(pbancVO.getUserInfo(request).getBzentyNo());
        }
        
        // TODO 지원사업 - 매칭설정 조건처리 필요 (SQL)

        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = pbancService.listPbanc(pbancVO, page, size);
        }
        else {
        	list = pbancService.listPbanc(pbancVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, PbancVO pbancVO) {
    	
        setGlobalSession(request, pbancVO);

        if (pbancVO.getUserInfo(request) != null) {
        	pbancVO.setGsUserNo  (pbancVO.getUserInfo(request).getUserNo  ());
        	pbancVO.setGsRoleId  (pbancVO.getUserInfo(request).getRoleId  ());
        	pbancVO.setGsBzentyNo(pbancVO.getUserInfo(request).getBzentyNo());
        }
    }
    
    /**
     * 검색조건 값만 재정의하여 반환한다.
     */
    private PbancVO getSearchVO(PbancVO pbancVO) {
    	
    	PbancVO search = PbancVO.builder()
    	.showMode       (pbancVO.getShowMode      ())
        .ordrField      (pbancVO.getOrdrField     ())
        .ordrField      (pbancVO.getOrdrField     ())
        .srchText       (pbancVO.getSrchText      ())
        .exPrgrsCd      (pbancVO.getExPrgrsCd     ())
        .prgrsSttsCd    (pbancVO.getPrgrsSttsCd   ())
        .sprtFldCd      (pbancVO.getSprtFldCd     ())
        .bizFld         (pbancVO.getBizFld        ())
        .bizTrgt        (pbancVO.getBizTrgt       ())
        .bizTrgtAge     (pbancVO.getBizTrgtAge    ())
        .bizTrgtFntnPd  (pbancVO.getBizTrgtFntnPd ())
        .rcptSeCd       (pbancVO.getRcptSeCd      ())
        .rcptMthdCd     (pbancVO.getRcptMthdCd    ())
        .pbancSttsCd    (pbancVO.getPbancSttsCd   ())
        .build();
    	
        search.setPage(pbancVO.getPage());
        
        return search;
    }
    
}
