package business.batch.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.batch.service.LinkKodataService;
import common.base.BaseController;
import common.util.CommUtils;
import egovframework.com.utl.sim.service.EgovFileScrty;

/**
 * [컨트롤러클래스] - 배치 테스트용 Controller
 *
 * @class	: LinkKodataController
 * @author  : LHB
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings({"all"})
public class LinkKodataController extends BaseController {

	@Autowired
	private LinkKodataService linkKodataService;

	/**
	 * 배치 테스트(다운로드)를 위한 컨트롤러
	 */
	@RequestMapping("/batch/linkKodataDownload.do")
	@ResponseBody
	public void batchDownload() throws Exception {

		linkKodataService.downloadKodata();
		
		return;
	}
	
	/**
	 * 배치 테스트(DB INSERT)를 위한 컨트롤러
	 */
	@RequestMapping("/batch/linkKodataInsert.do")
	@ResponseBody
	public void insert() throws Exception {

		linkKodataService.regiKodata();
		
		return;
	}
	
	/**
	 * 배치 테스트(업로드)를 위한 컨트롤러
	 */
	@RequestMapping("/batch/linkKodataWrite.do")
	@ResponseBody
	public void fileWrite() throws Exception {

		List<String[]> enpList = new ArrayList<String[]>();
		enpList.add(new String[] { "6210261808", "6210261808", "5906091835411", "부일산업사",		"1", "N" });
		enpList.add(new String[] { "2210374250", "2210374250", "6008101338612", "서울철물(양구)",	"1", "N" });
		enpList.add(new String[] { "1208101957", "1208101957", "1101110220395", "쌍용건설(주)",	"2", "N" });
		enpList.add(new String[] { "1078154539", "1078154539", "1101111252793", "반도케미칼(주)",	"2", "N" });
		
		linkKodataService.writeKodata("20220522", enpList);
		
		return;
	}
	
	/**
	 * 배치 테스트(업로드)를 위한 컨트롤러
	 */
	@RequestMapping("/batch/linkKodataUpload.do")
	@ResponseBody
	public void upload() throws Exception {
		
		linkKodataService.uploadKodata();
		
		return;
	}
	
	/**
	 * 배치 테스트(프로시저)를 위한 컨트롤러
	 */
	@RequestMapping("/batch/linkKodataProcedure.do")
	@ResponseBody
	public void procedure() throws Exception {
		
		linkKodataService.modifyEntLcinfo();
		
		return;
	}
	
	/**
	 * 배치 테스트(위치정보 수정)를 위한 컨트롤러
	 */
	@RequestMapping("/batch/linkKodataEntLcinfo.do")
	@ResponseBody
	public void modifyEntLcinfo() throws Exception {
		
		linkKodataService.modifyEntLcinfo();
		
		return;
	}

}