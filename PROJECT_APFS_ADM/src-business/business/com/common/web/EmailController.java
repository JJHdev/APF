package business.com.common.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import business.com.common.service.EmailService;
import business.com.common.service.EmailVO;
import common.base.BaseController;
import common.file.FileDirectory;
import common.file.FileInfo;

/**
 * [컨트롤러클래스] - 이메일발송 컨트롤러
 *
 * @class   : EmailController
 * @author  : LSH
 * @since   : 2023.03.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Controller
@SuppressWarnings("rawtypes")
public class EmailController extends BaseController {
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("/com/common/sendEmail.do")
	@ResponseBody
	public Map sendEmail(HttpServletRequest request, @ModelAttribute EmailVO emailVO) throws Exception {
		
		// 세션사용자정보를 정의
		setGlobalSession(request, emailVO);
		
		// 세션 사용자정보의 사용자번호를 모델객체에 맵핑
		emailVO.setGsUserNo(emailVO.getUserInfo(request).getUserNo());
		
		if (emailVO.isUseTemplate()) {
			
			Map<String,Object> data = new HashMap<String,Object> ();
			data.put("name", emailVO.getToName());
			data.put("subscriptionDate", new Date());
			data.put("homepageUrl", "http://localhost:9081/apfs/");
			data.put("naverUrl", "naver.com");

			List<String> hobbies = new ArrayList<String> ();
			hobbies.add("swimming");
			hobbies.add("fishing");
			hobbies.add("riding a bike");
			hobbies.add("cooking");
			hobbies.add("travelling");
			data.put("hobbies", hobbies);
			
			emailVO.setData(data);
			
			Map<String,String> image = new HashMap<String,String>();
			image.put("label", "imageResourceName");
			image.put("name" , "email.jpg");
			List<Map<String,String>> images = new ArrayList<Map<String,String>>();
			images.add(image);
			emailVO.setImages(images);
			emailVO.setImageDirectory(FileDirectory.THYMELEAF);
		}
		// 첨부파일 추가
		List<FileInfo> files = new ArrayList<FileInfo>();
		files.add(FileInfo.builder()
			.docuNo("1")
			.fileName("전문 협의를 위한 샘플DB.xlsx")
			.saveName("20230324-160201.xlsx")
			.fullFile(FileDirectory.SAMPLE.getRealName("20230324-160201.xlsx"))
			.build()
		);
		files.add(FileInfo.builder()
			.docuNo("2")
			.fileName("코데이터.hwp")
			.saveName("20230324-160202.hwp")
			.fullFile(FileDirectory.SAMPLE.getRealName("20230324-160202.hwp"))
			.build()
		);
		emailService.sendEmail(emailVO, files);
		
		return getSuccess();
	}
}
