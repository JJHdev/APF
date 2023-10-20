package business.usr.mypage.web;

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
import business.usr.invest.web.EntValidator;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 마이페이지 - IR작성하기 Controller
 * 
 * @class   : MyEntIrController
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
public class MyEntController extends BaseController {

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
     * 마이페이지 - IR작성하기 화면 오픈
     * 
     * 경영체의 IR정보가 없는 경우 기본IR정보를 생성한다.
     * : 기타지원이력 팝업 작성시 IR번호가 필요함
     * 
     */
    @RequestMapping("/usr/mypage/ent/openEntIr.do")
    public String openEntIr(ModelMap model
    		, HttpServletRequest request
            , @ModelAttribute EntVO entVO) throws Exception {
				
        // 세션정보 모델 바인딩
        setGlobalSessionVO(request, entVO, false);
        
        // 최종IR번호 조회
        String irNo = entIrService.getLastEntIrNo(entVO.getBzentyNo());

        // 경영체이면서
        // 수정권한이 있고
        // IR정보가 없는 경우
        // 2023.09.12 관리자/농금원 포함
        if ((entVO.getUserInfo(request).isEnt  () ||
        	 entVO.getUserInfo(request).isMng  () ||
        	 entVO.getUserInfo(request).isAdmin()   ) &&
        	CommConst.YES.equals(entVO.getUpdateYn()) &&
        	CommUtils.isEmpty(irNo)) {
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
        // IR번호 정의
        entVO.setIrNo(irNo);

        model.addAttribute("model", entVO);
    	
        return "usr/mypage/ent/openEntIr";
    }

    /**
     * [act:II] 마이페이지 - IR작성하기 - 대시보드 저장처리
     */
    @RequestMapping("/usr/mypage/ent/saveEntIr.do")
    @ResponseBody
    public Map saveEntIr(@RequestBody EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        // 세션정보 모델 바인딩
        setGlobalSessionVO(request, entVO, true);
        
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
     * [act:IF] 마이페이지 - IR작성하기 - 상세정보 저장처리 (파일업로드) 
     */
    @RequestMapping("/usr/mypage/ent/saveEntIrFile.do")
    @ResponseBody
    public Map saveEntIrFile(@ModelAttribute EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        // 세션정보 모델 바인딩
        setGlobalSessionVO(request, entVO, true);

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
    @RequestMapping("/usr/mypage/ent/saveEntOthsptHst.do")
    @ResponseBody
    public Map saveEntOthsptHst(@RequestBody EntVO entVO
    		, HttpServletRequest request
    		, BindingResult bindingResult) throws Exception {

        setGlobalSessionVO(request, entVO, true);

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
    private void setGlobalSessionVO(HttpServletRequest request, EntVO entVO, boolean saveMode) {
    	
        setGlobalSession(request, entVO);

        if (entVO.getUserInfo(request) != null) {
        	entVO.setGsUserNo  (entVO.getUserInfo(request).getUserNo  ());
        	entVO.setGsRoleId  (entVO.getUserInfo(request).getRoleId  ());
        	entVO.setGsBzentyNo(entVO.getUserInfo(request).getBzentyNo());
        }
        // 화면구분이 없을 경우 대시보드를 기본화면으로 정의
        if (CommUtils.isEmpty(entVO.getPageCd())) {
        	entVO.setPageCd(CodeConst.MYPG_ENTIR_DASH);
        }
        // 업체번호 정의
    	entVO.setBzentyNo(entVO.getGsBzentyNo());
    	entVO.setUpdateYn(CommConst.NO);
    	entVO.setSelectYn(CommConst.NO);
    	
        // 그룹관리 권한설정에 따른 기업정보 접근권한코드
    	String authCd = groupService.getGrpAuthCd(
        	entVO.getGsUserNo(), 
			CodeConst.GROUP_MENU_BZENTY
		);
    	// 수정권한이 있는 경우
        if (CodeConst.GROUP_AUTH_MODIFY.equals(authCd)) {
        	entVO.setUpdateYn(CommConst.YES);
        	entVO.setSelectYn(CommConst.YES);
        }
        // 조회권한이 있는 경우
        else if (CodeConst.GROUP_AUTH_VIEW.equals(authCd)) {
        	entVO.setSelectYn(CommConst.YES);
        }
    	
        // 그룹관리 권한설정에 따른 접근권한 체크
    	groupService.access(
			entVO.getGsUserNo(), 
			entVO.getGsRoleId(), 
			CodeConst.GROUP_MENU_IRREGIST,
			saveMode);
    }
}
