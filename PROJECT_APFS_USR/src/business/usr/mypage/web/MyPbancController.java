package business.usr.mypage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.exception.ModalBusinessException;
import business.usr.mypage.service.MyPbancService;
import business.usr.mypage.service.MyPbancVO;
import commf.exception.BusinessException;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 마이페이지 - 사업공고등록 Controller
 * 
 * @class   : MyPbancController
 * @author  : LSH
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class MyPbancController extends BaseController {

    @Resource(name="MyPbancService")
    protected MyPbancService myPbancService;
    
	@Resource(name = "fileManager")
	protected FileManager fileManager;
    
    /**
     * TODO 마이페이지 - 사업공고등록 화면 오픈
     */
    @RequestMapping("/usr/mypage/pbanc/openPbanc.do")
    public String openPbanc(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MyPbancVO myPbancVO) throws Exception {
		
    	setGlobalSessionVO(request, myPbancVO);
        model.addAttribute("model", myPbancVO);
    	
        return "usr/mypage/pbanc/openPbanc";
    }
    
    /**
     * 사업공고관리 목록JSON 반환 (GRID)
     */
    @RequestMapping("/usr/mypage/pbanc/getListPbanc.do")
    @ResponseBody
    public Map getListPbanc(HttpServletRequest request
            , @ModelAttribute MyPbancVO myPbancVO) throws Exception {

    	setGlobalSessionVO(request, myPbancVO);
    	
        if (myPbancVO.getUserInfo(request) != null) {
        	myPbancVO.setGsUserNo  (myPbancVO.getUserInfo(request).getUserNo  ());
        	myPbancVO.setGsRoleId  (myPbancVO.getUserInfo(request).getRoleId  ());
        	myPbancVO.setGsBzentyNo(myPbancVO.getUserInfo(request).getBzentyNo());
        }
        Map reqMap = getParameterMap(request, true);        

        List list = null;
        if (reqMap.containsKey("page")) {
            int page = CommUtils.getInt(reqMap, "page");
            int size = CommUtils.getInt(reqMap, "rows");
        	list = myPbancService.listPbanc(myPbancVO, page, size);
        }
        else {
        	list = myPbancService.listPbanc(myPbancVO);
        }
        // GRID용 결과값 반환
        return getPaginatedResult(list);
    }
    
    /**
     * 지원서비스 - 지원사업 통합검색 상세조회 오픈
     */
    @RequestMapping("/usr/mypage/pbanc/viewPbanc.do")
    public String viewPbanc(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MyPbancVO myPbancVO) throws Exception {
		
    	setGlobalSessionVO(request, myPbancVO);
    	MyPbancVO obj = myPbancService.viewPbanc(myPbancVO);
    	if(! CommUtils.isEqual(obj.getRcptSeCd(),"20")) {
    		if(CommUtils.isEqual(obj.getRcptBgngDt().substring(8, 10), "00") && CommUtils.isEqual(obj.getRcptEndDt().substring(8, 10), "00")){
    			obj.setRcptBgngDt(obj.getRcptBgngDt().substring(0, 7));
    			obj.setRcptEndDt(obj.getRcptEndDt().substring(0, 7));
    		}
    	}
    	
    	obj.setDivisionBkmk(myPbancVO.getDivisionBkmk());
    	obj.setPage(myPbancVO.getPage());
    	obj.setSrchText(myPbancVO.getSrchText());
    	
        if (obj == null)
        	throw new BusinessException("해당하는 지원사업의 사업공고가 존재하지 않습니다.");
        
        // 상세조회용 모델 바인딩
        model.addAttribute("model", obj);
        
        // 검색조건용 모델 정의
        model.addAttribute("search", getSearchVO(myPbancVO));
        
        return "usr/mypage/pbanc/viewPbanc";
    }
    
    /**
     * 검색조건 값만 재정의하여 반환한다.
     */
    private MyPbancVO getSearchVO(MyPbancVO pbancVO) {
    	
    	MyPbancVO search = MyPbancVO.builder()
    	.showMode       	(pbancVO.getShowMode    	  ())
    	.divisionBkmk       (pbancVO.getDivisionBkmk      ())
        .srchText       	(pbancVO.getSrchText    	  ())
        .build();
        search.setPage(pbancVO.getPage());
        
        return search;
    }
    
    
    @RequestMapping("/usr/mypage/pbanc/formPbanc.do")
    public String formPbanc(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute MyPbancVO myPbancVO) throws Exception {
		
    	setGlobalSessionVO(request, myPbancVO);
        // 처리모드 설정
        if (CommUtils.isNotEmpty(myPbancVO.getBizPbancNo())){
        	// 사업공고 신청 상세조회에 넣기 
        	MyPbancVO data = myPbancService.viewPbanc(myPbancVO);
			
			if (data == null)
				throw new ModalBusinessException(message.getMessage("exception.notKey"));
			if(! CommUtils.isEqual(data.getRcptSeCd(),"20")) {
				if(CommUtils.isEqual(data.getRcptBgngDt().substring(8, 10), "00") && CommUtils.isEqual(data.getRcptEndDt().substring(8, 10), "00")){
					data.setRcptBgngYmd(data.getRcptBgngDt().substring(0, 7));
					data.setRcptEndYmd(data.getRcptEndDt().substring(0, 7));
				}
			}
			data.setMode(CommConst.UPDATE);
			model.addAttribute("model", data);
        }else {
        	myPbancVO.setMode(CommConst.INSERT);
        	
        	MyPbancVO data = myPbancService.viewCrdnsBzentyNm(myPbancVO);
        	if(CommUtils.isNotEmpty(data)) {
        		data.setMode(CommConst.INSERT);
        		data.setCrdnsBzentyNo(myPbancVO.getGsBzentyNo());
        		model.addAttribute("model", data);
        	}else {
        		model.addAttribute("model", myPbancVO);
        	}
        }
        return "usr/mypage/pbanc/formPbanc";
    }
    
    
    /**
     * TODO 마이페이지 - 사업공고등록 저장처리 (등록,수정,삭제)
     */
    @RequestMapping("/usr/mypage/pbanc/savePbanc.do")
    @ResponseBody
    public Map savePbanc(@ModelAttribute MyPbancVO myPbancVO
			    		, HttpServletRequest request
						, BindingResult bindingResult) throws Exception {
    	
    	setGlobalSessionVO(request, myPbancVO);
		// 세션사용자번호 정의
		if (myPbancVO.getUserInfo(request) != null)
			myPbancVO.setGsUserNo(myPbancVO.getUserInfo(request).getUserNo());
    	
		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = null;
		try {
    		files = fileManager.multiFileUploadByName(request, null);
        } catch (NullPointerException e) {
        	logger.error("Error :: ", e);
        } catch (Exception e) {
        	logger.error("Error :: ", e);
        }
		 
        // 사업공고관리를 저장한다.
    	String result =  myPbancService.savePbanc(validateMyPbanc(myPbancVO),files);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }
    
	/**
	 * 게시판 탭목록JSON 반환
	 */
	@RequestMapping("/usr/mypage/pbanc/getListPbancTab.do")
	@ResponseBody
	public Map getListBbsTab(ModelMap model, 
			HttpServletRequest request,
			@ModelAttribute MyPbancVO myPbancVO) throws Exception {
		setGlobalSessionVO(request, myPbancVO);
		
		List list = myPbancService.listPbancTab(myPbancVO);
			
			Map<String, String> firstObject = new HashMap<>();
			firstObject.put("code", "00");
			firstObject.put("text", "지원사업 공고등록 내역");
			list.add(0, firstObject);
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
    
    /**
     * 등록 하기전 유효성 검사 수정
     */	
	private MyPbancVO validateMyPbanc(MyPbancVO myPbancVO) {
		String bizFld = myPbancVO.getBizFld();
		String bizTrgt = myPbancVO.getBizTrgt();
		if(CommUtils.isNotEmpty(bizFld)) {
			if (bizFld.startsWith(",")) {
				bizFld = bizFld.substring(1);
			}
		}
		if(CommUtils.isNotEmpty(bizTrgt)) {
			if (bizTrgt.startsWith(",")) {
				bizTrgt = bizTrgt.substring(1);
			}
		}
	    myPbancVO.setBizFld(bizFld);
	    myPbancVO.setBizTrgt(bizTrgt);
		
		return myPbancVO;
	}
	
	/**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, MyPbancVO myPbancVO) {
    	
        setGlobalSession(request, myPbancVO);

        if (myPbancVO.getUserInfo(request) != null) {
        	myPbancVO.setGsUserNo  (myPbancVO.getUserInfo(request).getUserNo  ());
        	myPbancVO.setGsRoleId  (myPbancVO.getUserInfo(request).getRoleId  ());
        	myPbancVO.setGsBzentyNo(myPbancVO.getUserInfo(request).getBzentyNo());
        }
    }
	
}
