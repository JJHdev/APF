package business.usr.invest.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.invest.service.EntCoHstService;
import business.usr.invest.service.EntFnnrService;
import business.usr.invest.service.EntInvtAmtService;
import business.usr.invest.service.EntIrService;
import business.usr.invest.service.EntLwstService;
import business.usr.invest.service.EntMgmtService;
import business.usr.invest.service.EntOthsptHstService;
import business.usr.invest.service.EntPtntService;
import business.usr.invest.service.EntRprsvHstService;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntShrholdrService;
import business.usr.invest.service.EntVO;
import common.base.BaseController;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 업체정보 Controller
 * 
 * @class   : EntController
 * @author  : LSH
 * @since   : 2023.04.28
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class EntController extends BaseController {

    // 경영체정보
	@Resource(name="EntService")
    protected EntService entService;
    // 업체IR정보
	@Resource(name="EntIrService")
    protected EntIrService entIrService;
    // 회사연혁
    @Resource(name="EntCoHstService")
    protected EntCoHstService entCoHstService;
    // 손익계산서/재무상태표
    @Resource(name="EntFnnrService")
    protected EntFnnrService entFnnrService;
    // 투자금액
    @Resource(name="EntInvtAmtService")
    protected EntInvtAmtService entEtcinvtAmtService;
    // 소송현황
    @Resource(name="EntLwstService")
    protected EntLwstService entLwstService;
    // 경영진정보
    @Resource(name="EntMgmtService")
    protected EntMgmtService entMgmtService;
    // 정부기타지원이력
    @Resource(name="EntOthsptHstService")
    protected EntOthsptHstService entOthsptHstService;
    // 특허상표권현황
    @Resource(name="EntPtntService")
    protected EntPtntService entPtntService;
    // 대표자이력
    @Resource(name="EntRprsvHstService")
    protected EntRprsvHstService entRprsvHstService;
    // 주주현황
    @Resource(name="EntShrholdrService")
    protected EntShrholdrService entShrholdrService;
    
    /**
     * 경영체정보 화면 오픈
     */
    @RequestMapping("/usr/invest/ent/listEnt.do")
    public String listEnt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
		
    	setGlobalSessionVO(request, entVO);
        
        model.addAttribute("model", entVO);
    	
        return "usr/invest/ent/listEnt";
    }

    /**
     * 경영체정보 상세보기 화면 오픈
     */
    @RequestMapping("/usr/invest/ent/viewEnt.do")
    public String viewEnt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
		
    	setGlobalSessionVO(request, entVO);
        
        model.addAttribute("model", entVO);
    	
        return "usr/invest/ent/viewEnt";
    }

    /**
     * 사업분야선택 모달팝업 오픈
     */
    @RequestMapping("/usr/invest/ent/modalBizField.do")
    public String modalBizField(ModelMap model) throws Exception {
        return "/usr/invest/ent/modalBizField";
    }

    /**
     * 경영체 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/usr/invest/ent/getListEntBiz.do")
    @ResponseBody
    public Map getListEntBiz(HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {

    	setGlobalSessionVO(request, entVO);
        
        // 경영체 구분코드 설정
        entVO.setBzentySeCd(CodeConst.ENT_EBZ);
        
        return _getListEnt(request, entVO);
    }

    /**
     * 북마크한 업체정보 목록JSON 반환 (EasyUI GRID)
     */
    @RequestMapping("/usr/invest/ent/getListEntBkmk.do")
    @ResponseBody
    public Map getListEntBkmk(HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {

    	setGlobalSessionVO(request, entVO);

        Map reqMap = getParameterMap(request, true);        

        int page = CommUtils.getInt(reqMap, "page");
        int size = CommUtils.getInt(reqMap, "rows");
    	List list = entService.listEntBkmk(entVO, page, size);

    	return getPaginatedResult(list);
    }
    
    // 경영체 목록JSON 반환
    private Map _getListEnt(HttpServletRequest request, EntVO entVO) throws Exception {
    	
        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = entService.listEnt(entVO, page, size);
        }
        else {
        	list = entService.listEnt(entVO);
        }
        // Easy UI GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 경영체 조회JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getEnt.do")
    @ResponseBody
    public Map getEnt(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
    	
    	setGlobalSessionVO(request, entVO);
        
        EntVO obj = entService.viewEnt(entVO);
        return getSuccess(obj);
    }
    
    /**
     * 경영체 상세조회(하위목록 포함) JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getEntAll.do")
    @ResponseBody
    public Map getEntAll(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
    	
    	setGlobalSessionVO(request, entVO);
        
    	String mode   = entVO.getMode();
    	String pageCd = entVO.getPageCd();
    	String irNo   = entVO.getIrNo();
        EntVO obj     = null;
        
		// IR번호가 있는 경우 IR작성용 업체정보 조회
        if (CommUtils.isNotEmpty(irNo)) {
        	// IR작성하기 or 미리보기
			obj = entService.viewEntForIr(entVO);
        }
		else {
			// 경영체정보 상세조회
			obj = entService.viewEnt(entVO);

			if (CommUtils.isNotEmpty(obj.getIrNo())) {
				// IR조회 카운트 증가
	    		entIrService.updtEntIrInqCnt(obj.getIrNo());
	    	}
	    	// 업체조회이력 남기기
			entService.regiEntInqHst(entVO);
		}
    	
        // IR작성하기 - 대시보드 인 경우 전체목록 조회
		if (obj != null && !CodeConst.MYPG_ENTIR_INFO.equals(pageCd)) {
			
		    // 재무정보 KODATA 등록여부
			obj.setFnnrKodataYn(entFnnrService.existKodata(obj.getBzentyNo()) ? CommConst.YES : CommConst.NO);
		    // 특허상표권현황 KODATA 등록여부
			obj.setPtntKodataYn(entPtntService.existKodata(obj.getBzentyNo()) ? CommConst.YES : CommConst.NO);

			obj.setLwstList    (entLwstService.listEntLwst                 (obj));   // 소송현황 목록
			obj.setMgmtList    (entMgmtService.listEntMgmt                 (obj));   // 경영진 목록
			obj.setRprsHstList (entRprsvHstService.listEntRprsvHst         (obj));   // 대표자이력 목록
			obj.setShroldrList (entShrholdrService.listEntShrholdr         (obj));   // 주주현황 목록
			obj.setCoHstList   (entCoHstService.listEntCoHst               (obj));   // 회사연혁 목록
			obj.setOthspSumList(entOthsptHstService.listEntOthsptHstSummary(obj));   // 정부지원이력 기관별 합계건수 목록
			obj.setPtntSumList (entPtntService.listEntPtntSummary          (obj));   // 특허상표권현황 구분별 합계건수 목록
			// 주요재무정보 - 재무상태표 조회
			obj.setFnnrKoFntl(_getFnnrInfo(obj.getIrNo(), CodeConst.FNNR_KODATA, CodeConst.FNNR_FNTL_CD));
			// 주요재무정보 - 손익계산서 조회
			obj.setFnnrKoPlos(_getFnnrInfo(obj.getIrNo(), CodeConst.FNNR_KODATA, CodeConst.FNNR_PLOS_CD));
			// 추가재무정보 - 재무상태표 조회
			obj.setFnnrMnFntl(_getFnnrInfo(obj.getIrNo(), CodeConst.FNNR_MANUAL, CodeConst.FNNR_FNTL_CD));
			// 추가재무정보 - 손익계산서 조회
			obj.setFnnrMnPlos(_getFnnrInfo(obj.getIrNo(), CodeConst.FNNR_MANUAL, CodeConst.FNNR_PLOS_CD));
			
			// 추가재무정보 데이터여부
			obj.setFnnrManualYn(
				CommConst.YES.equals(obj.getFnnrMnFntl().getDataYn()) ||
				CommConst.YES.equals(obj.getFnnrMnPlos().getDataYn()) ?
				CommConst.YES :
				CommConst.NO
			);

			// 수정인 경우
			if (CommConst.UPDATE.equals(mode)) {
				obj.setInvtSeCd(CodeConst.INVT_AMT_FUND);
				obj.setInvtFndList(entEtcinvtAmtService.listEntInvtAmt(obj));   // 농식품모태펀드 투자금액 목록
				obj.setInvtSeCd(CodeConst.INVT_AMT_ETC);
				obj.setInvtEtcList(entEtcinvtAmtService.listEntInvtAmt(obj));   // 기타 투자금액 목록
				// 수정대상목록만 조회
				obj.setManualYn(CommConst.YES);
				// 특허상표권현황 목록
				obj.setPtntList(entPtntService.listEntPtnt(obj));
				// 추가재무정보 - 재무상태표 수정목록 조회
				obj.setFnnrFntlList(_getEntFnnrUpdates(obj.getIrNo(), CodeConst.FNNR_MANUAL, CodeConst.FNNR_FNTL_CD));
				// 추가재무정보 - 손익계산서 수정목록 조회
				obj.setFnnrPlosList(_getEntFnnrUpdates(obj.getIrNo(), CodeConst.FNNR_MANUAL, CodeConst.FNNR_PLOS_CD));
			}
			// 조회인 경우
			else {
				obj.setInvtList(entEtcinvtAmtService.listEntInvtAmt(obj));   // 투자금액 목록
			}
		}
        return getSuccess(obj);
    }
    
    // 재무정보 수정목록 조회
    private List _getEntFnnrUpdates(String irNo, String dataSeCd, String fnnrSeCd) throws Exception {
    	
    	EntVO obj = EntVO.builder()
    			.dataSeCd(dataSeCd)
    			.fnnrSeCd(fnnrSeCd)
    			.irNo    (irNo    )
    			.build   ();

    	if (CodeConst.FNNR_FNTL_CD.equals(fnnrSeCd))
    		obj.setFnnrType(CodeConst.FNNR_FNTL_SE);
        else
        	obj.setFnnrType(CodeConst.FNNR_PLOS_SE);

		return entFnnrService.listEntFnnrUpdates(obj);
    }

    // 재무정보 조회
    private EntVO _getFnnrInfo(String irNo, String dataSeCd, String fnnrSeCd) throws Exception {
    	
    	EntVO obj = EntVO.builder()
    			.dataSeCd(dataSeCd)
    			.fnnrSeCd(fnnrSeCd)
    			.irNo    (irNo    )
    			.build   ();
    	
		_setEntFnnrSummary(obj);

		return obj;
    }
    
    // 재무정보 요약목록 조회
    private void _setEntFnnrSummary(EntVO entVO) throws Exception {

    	if (CodeConst.FNNR_FNTL_CD.equals(entVO.getFnnrSeCd()))
        	entVO.setFnnrType(CodeConst.FNNR_FNTL_SE);
        else
        	entVO.setFnnrType(CodeConst.FNNR_PLOS_SE);
        
        //재무정보 기준년도 조회
        String year = entFnnrService.getEntFnnrYear(entVO);
        entVO.setFnnrMaxYr(CommUtils.strToInt(year, CommUtils.getCurrYear()));

		//재무정보 요약목록
        entVO.setFnnrList(entFnnrService.listEntFnnrSummary(entVO));
        //재무정보 년도별목록
        entVO.setYearList(entFnnrService.listEntFnnrYear(entVO));
        
        if (entVO.getFnnrList() != null)
        	entVO.setDataYn(CommConst.YES);
    }

    /**
     * 회사연혁 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntCoHst.do")
    @ResponseBody
    public Map getListEntCoHst(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entCoHstService.listEntCoHst(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 손익계산서/재무상태표 모달팝업 오픈
     */
    @RequestMapping("/usr/invest/ent/modalEntFnnr.do")
    public String modalEntFnnr(ModelMap model) throws Exception {
        return "/usr/invest/ent/modalEntFnnr";
    }

    /**
     * 재무정보 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntFnnr.do")
    @ResponseBody
    public Map getListEntFnnr(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entFnnrService.listEntFnnr(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 재무정보 요약목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getEntFnnrSummary.do")
    @ResponseBody
    public Map getEntFnnrSummary(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);
		
        // 재무정보 요약목록 조회
		_setEntFnnrSummary(entVO);
		
        Map result = getSuccess();
        result.put("rows" , entVO.getFnnrList());
        result.put("years", entVO.getYearList());
        // 결과값 반환
        return result;
    }

    /**
     * 재무정보 상세목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getEntFnnrDetails.do")
    @ResponseBody
    public Map getEntFnnrDetails(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);
		
        //재무정보 기준년도 조회
        String year = entFnnrService.getEntFnnrYear(entVO);
        entVO.setFnnrMaxYr(CommUtils.strToInt(year, CommUtils.getCurrYear()));

		//재무정보 요약목록
        List list  = entFnnrService.listEntFnnrDetails(entVO);
        //재무정보 년도별목록
        List years = entFnnrService.listEntFnnrYear(entVO);

        
        Map result = getSuccess();
        result.put("rows" , list);
        result.put("years", years);
        // 결과값 반환
        return result;
    }

    /**
     * 투자금액 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntInvtAmt.do")
    @ResponseBody
    public Map getListEntInvtAmt(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entEtcinvtAmtService.listEntInvtAmt(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 소송현황 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntLwst.do")
    @ResponseBody
    public Map getListEntLwst(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entLwstService.listEntLwst(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 경영진정보 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntMgmt.do")
    @ResponseBody
    public Map getListEntMgmt(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entMgmtService.listEntMgmt(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 정부기타지원이력 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntOthsptHst.do")
    @ResponseBody
    public Map getListEntOthsptHst(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entOthsptHstService.listEntOthsptHst(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 정부지원이력 기관별 합계건수 목록조회
     */
    @RequestMapping("/usr/invest/ent/getListEntOthsptHstSummary.do")
    @ResponseBody
    public Map getListEntOthsptHstSummary(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entOthsptHstService.listEntOthsptHstSummary(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 정부지원이력 기관별 년도별 건수 목록조회
     */
    @RequestMapping("/usr/invest/ent/getListEntOthsptHstYears.do")
    @ResponseBody
    public Map listEntOthsptHstYears(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entOthsptHstService.listEntOthsptHstYears(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 특허상표권현황 모달팝업 오픈
     */
    @RequestMapping("/usr/invest/ent/modalEntPtnt.do")
    public String modalEntPtnt(ModelMap model) throws Exception {
        return "/usr/invest/ent/modalEntPtnt";
    }

    /**
     * 2023.07.24 LSH
     * 기타지원이력 작성하기 모달팝업 오픈
     * - 마이페이지 - IR작성하기 에서 사용됨
     * - 정보서비스 - 관리자IR작성 에서 사용됨
     * 2023.08.25 LSH 수정하기 추가
     */
    @RequestMapping("/usr/invest/ent/modalEntSptHst.do")
    public String modalEntSptHst(ModelMap model, @RequestBody EntVO entVO) throws Exception {
    	
    	EntVO obj = null;
    	if (entVO.getSn() != null) {
    		obj = entOthsptHstService.viewEntOthsptHst(entVO);
    	}
    	if (obj == null) {
    		obj = EntVO.builder().build();
    		obj.setMode(CommConst.INSERT);
    	}
    	else {
    		obj.setMode(CommConst.UPDATE);
    	}
    	model.addAttribute("model", obj);

    	return "/usr/invest/ent/modalEntSptHst";
    }

    /**
     * 2023.07.28 LSH
     * IR작성하기 - 미리보기 모달팝업 오픈
     * - 마이페이지 - IR작성하기 에서 사용됨
     * - 정보서비스 - 관리자IR작성 에서 사용됨
     */
    @RequestMapping("/usr/invest/ent/modalEntPreview.do")
    public String modalEntPreview(ModelMap model) throws Exception {
        return "/usr/invest/ent/modalEntPreview";
    }
    
    /**
     * 특허상표권현황 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntPtnt.do")
    @ResponseBody
    public Map getListEntPtnt(HttpServletRequest request, 
    		@ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = entPtntService.listEntPtnt(entVO, page, size);
        }
        else {
        	list = entPtntService.listEntPtnt(entVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 특허상표권현황 구분별 합계건수 목록조회
     */
    @RequestMapping("/usr/invest/ent/getListEntPtntSummary.do")
    @ResponseBody
    public Map getListEntPtntSummary(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entPtntService.listEntPtntSummary(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 대표자이력 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntRprsvHst.do")
    @ResponseBody
    public Map getListEntRprsvHst(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entRprsvHstService.listEntRprsvHst(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 주주현황 목록JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getListEntShrholdr.do")
    @ResponseBody
    public Map getListEntShrholdr(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

        // 세션정보 모델 바인딩
		setGlobalSessionVO(request, entVO);

        List list = entShrholdrService.listEntShrholdr(entVO);
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }

    /**
     * 로그인한 경영체의 IR작성여부 확인
     */
    @RequestMapping("/usr/invest/ent/existIr.do")
	@ResponseBody
	public boolean existIr(HttpServletRequest request, @ModelAttribute EntVO entVO) throws Exception {

    	setGlobalSessionVO(request, entVO);
        
        String bzentyNo = null;
        
        if (entVO.getUserInfo(request) != null) {
        	bzentyNo = entVO.getUserInfo(request).getBzentyNo();
        }
        if (bzentyNo == null)
        	return false;
    	
		return entIrService.existEntIr(bzentyNo);
	}
	
    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, EntVO entVO) {
    	
        setGlobalSession(request, entVO);

        if (entVO.getUserInfo(request) != null) {
        	entVO.setGsUserNo  (entVO.getUserInfo(request).getUserNo  ());
        	entVO.setGsRoleId  (entVO.getUserInfo(request).getRoleId  ());
        	entVO.setGsBzentyNo(entVO.getUserInfo(request).getBzentyNo());
        }
    }
}
