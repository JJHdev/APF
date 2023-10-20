package business.usr.invest.web;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.user.service.GroupService;
import business.usr.CodeConst;
import business.usr.invest.service.EntIrService;
import business.usr.invest.service.EntOthsptHstService;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 관리자IR작성 Controller
 * 
 * @class   : EntIrController
 * @author  : LSH
 * @since   : 2023.08.21
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Controller
@SuppressWarnings({"all"})
public class EntIrController extends BaseController {

	@Resource(name="EntService")
    protected EntService entService;
	
	@Resource(name="EntIrService")
    protected EntIrService entIrService;
	
    // 정부기타지원이력
    @Resource(name="EntOthsptHstService")
    protected EntOthsptHstService entOthsptHstService;

    @Resource(name="GroupService")
    protected GroupService groupService;

	@Resource(name="CommService")
    protected CommService commService;

	@Resource(name = "fileManager")
	protected FileManager fileManager;
    
    // 데이터 검증용 VALIDATOR
    @Autowired 
    private EntValidator validator;

    /**
     * 관리자IR작성 화면 오픈
     */
    @RequestMapping("/usr/invest/ent/openEntIr.do")
    public String openEntIr(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
				
        // 세션정보 모델 바인딩
        setGlobalSessionVO(request, entVO);
        
        // 업체번호가 있을 경우 업체정보 조회
        if (CommUtils.isNotEmpty(entVO.getBzentyNo())) {
        	EntVO obj = entService.getEnt(entVO.getBzentyNo());
        	if (obj != null) {
        		entVO.setBzentyNm(obj.getBzentyNm());
        	}
        }
        model.addAttribute("model", entVO);
    	
        return "usr/invest/ent/openEntIr";
    }

    /**
     * 업체선택 모달팝업 오픈
     */
    @RequestMapping("/usr/invest/ent/modalEnt.do")
    public String modalEnt(ModelMap model) throws Exception {
        return "usr/invest/ent/modalEnt";
    }

    /**
     * 선택한 경영체 정보 조회JSON 반환
     */
    @RequestMapping("/usr/invest/ent/getEntIr.do")
    @ResponseBody
    public EntVO getEntIr(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
    	
    	setGlobalSessionVO(request, entVO);
        
        // 최종IR번호 조회
        String irNo = entIrService.getLastEntIrNo(entVO.getBzentyNo());

        if (CommUtils.isEmpty(irNo)) {
        	EntVO obj = EntVO.builder()
        			.gsUserNo   (entVO.getGsUserNo()    )
        			.bzentyNo   (entVO.getBzentyNo()    )
        			.rlsYn      (CommConst.NO           )
        			.prgrsSttsCd(CodeConst.PRGRS_WRITING)
        			.build      ();
        	obj.setMode(CommConst.INSERT);
        	// IR정보 생성
        	entIrService.saveEntIr(obj);
        	irNo = obj.getIrNo();
        }
        
        return EntVO.builder()
   			.pageCd     (entVO.getPageCd  ()    )
			.bzentyNo   (entVO.getBzentyNo()    )
			.updateYn   (entVO.getUpdateYn()    )
			.selectYn   (entVO.getSelectYn()    )
			.irNo       (irNo)
			.build      ();
    }

    /**
     * [act:II] 관리자IR작성 - 대시보드 저장처리
     */
    @RequestMapping("/usr/invest/ent/saveEntIr.do")
    @ResponseBody
    public Map saveEntIr(@RequestBody EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        // 세션정보 모델 바인딩
        setGlobalSessionVO(request, entVO);
        
        // 재무정보 생성을 위한 계정코드 조회
        List codes = new ArrayList <Map> ();
        Collections.addAll(codes, commService.listCode(CodeConst.FNNR_FNTL_SE).toArray());
        Collections.addAll(codes, commService.listCode(CodeConst.FNNR_PLOS_SE).toArray());

    	// 저장데이터에 맞게 REBUILD
    	entVO.rebuildIrProperties(codes);
    	
        // 저장할 입력값 검증
    	//validator.validate(entVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        //if (bindingResult.hasErrors()) {
        //	logger.info("Ent IR Dashboard Validate Error...", bindingResult.getAllErrors());
        //	return getFailure(bindingResult);
        //}
        // 기업정보를 저장한다.
    	String result = entService.saveEnt(entVO);
    	// 성공결과 반환
        return getSuccess(result);
    }

    /**
     * [act:IF] 관리자IR작성 - 상세정보 저장처리 (파일업로드) 
     */
    @RequestMapping("/usr/invest/ent/saveEntIrFile.do")
    @ResponseBody
    public Map saveEntIrFile(@ModelAttribute EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        // 세션정보 모델 바인딩
        setGlobalSessionVO(request, entVO);

        // 상세정보 저장ACTION
        entVO.setAct(CommConst.ACT_IR_FILE);
        // 상세정보 수정모드
        entVO.setMode(CommConst.UPDATE);

		// 다중파일을 임시경로에 업로드한다.
		List<FileInfo> files = fileManager.multiFileUploadAdditional(request, null);
		entVO.setSaveFiles(files);

    	// 저장데이터에 맞게 REBUILD
    	entVO.rebuildIrProperties(null);
    	
        // 저장할 입력값 검증
    	//validator.validate(entVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        //if (bindingResult.hasErrors()) {
        //	logger.info("Ent IR File Validate Error...", bindingResult.getAllErrors());
        //	return getFailure(bindingResult);
        //}
        // 기업정보를 저장한다.
    	String result = entService.saveEnt(entVO);
    	// 성공결과 반환
        return getSuccess(result);
    }

    /**
     * [act:IS] 마이페이지 - IR작성하기 - 기타지원이력팝업 저장처리
     */
    @RequestMapping("/usr/invest/ent/saveEntOthsptHst.do")
    @ResponseBody
    public Map saveEntOthsptHst(@RequestBody EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, entVO);

        // 기타지원이력 저장ACTION
        entVO.setAct(CommConst.ACT_IR_SPTHST);
        
        // 저장할 입력값 검증
		//validator.validate(entVO, bindingResult);
    	
    	// 입력값 검증시 오류가 있을 경우
        //if (bindingResult.hasErrors()) {
        //	logger.info("Ent OthsptHst Validate Error...", bindingResult.getAllErrors());
        //	return getFailure(bindingResult);
        //}
        // 기타지원이력을 저장한다.
    	String result = entOthsptHstService.saveEntOthsptHst(entVO);
    	// 성공결과 반환
        return getSuccess("Message", result);
    }

    /**
     * 세션정보를 모델의 변수에 바인딩한다
     */
    private void setGlobalSessionVO(HttpServletRequest request, EntVO entVO) {
    	
        setGlobalSession(request, entVO);

        if (entVO.getUserInfo(request) != null) {
        	entVO.setGsUserNo  (entVO.getUserInfo(request).getUserNo  ());
        	entVO.setGsRoleId  (entVO.getUserInfo(request).getRoleId  ());
        }
        // 화면구분이 없을 경우 대시보드를 기본화면으로 정의
        if (CommUtils.isEmpty(entVO.getPageCd())) {
        	entVO.setPageCd(CodeConst.MYPG_ENTIR_DASH);
        }
    	entVO.setUpdateYn(CommConst.YES);
    	entVO.setSelectYn(CommConst.YES);
    	entVO.setAdmin   (true);
    }
}
