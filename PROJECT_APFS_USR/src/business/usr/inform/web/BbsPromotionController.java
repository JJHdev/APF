package business.usr.inform.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import business.usr.inform.web.validator.BbsDataValidator;
import common.base.BaseController;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [컨트롤러클래스] - 게시판 > 자료실 Controller
 * 
 * @class : BbsDataController
 * @author  : JH
 * @since   : 2023.08.07
 * @version : 1.4
 */
@Controller
@SuppressWarnings({ "all" })
public class BbsPromotionController extends BaseController {

	@Resource(name = "BbsService")
	private BbsService bbsService;

	@Resource(name = "fileManager")
	private FileManager fileManager;

	// 데이터 검증용 VALIDATOR
	@Autowired
	private BbsDataValidator bbsDataValidator;

	// 자료실 구분코드
	private static final String BBS_CODE = CodeConst.BBS_PROMOTION;

	/**
	 * 정보서비스-홍보영상-목록조회 화면 오픈
	 */
	@RequestMapping("/usr/inform/bbs/listPromotion.do")
	public String listPromotion(@ModelAttribute BbsVO bbsVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		setGlobalSession(request, bbsVO);
		model.addAttribute("model", bbsVO);
		return "usr/inform/bbs/listPromotion";
	}

	/**
	 * 정보서비스 - 우수투자사례 목록JSON 반환 (일반 GRID)
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/usr/inform/bbs/getListPromotion.do")
	@ResponseBody
	public Map getListPromotion(ModelMap model, 
			HttpServletRequest request, 
			@RequestParam Map<String, String> reqMap, 
			@ModelAttribute BbsVO bbsVO) throws Exception {
		
		setGlobalSessionVO(request, bbsVO);
		// -------------------- Default Setting End -----------------------//
		List list = null;
		if (reqMap.containsKey("page")) {
			int page = CommUtils.getInt(reqMap, "page");
			int size = CommUtils.getInt(reqMap, "rows");
			list = bbsService.listBbs(bbsVO, page, size);
		} else {
			list = bbsService.listBbs(bbsVO);
		}
		// 일반 GRID용 결과값 반환
		return getPaginatedResult(list);
	}
	
	@Configuration
	@EnableScheduling
	public class SchedulerConfig { }
	
	// 24시간마다 유튜브 채널 데이터 load 실행하여 DB 입력
	//@Scheduled(fixedRate = 86400000)  //24시간
	@Scheduled(fixedRate = 43200000)
	//@Scheduled(fixedRate = 10000)
	public void loadPromotion() throws Exception {
		
		String apiUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=UU-DicLNuWtiE1w16X6HkEZw&key=AIzaSyDWpWZixLN90ShzmoFeH9IFAfJgVHEWUaM";
		
		BbsVO bbsDataVO = new BbsVO();
		bbsDataVO.setBbsSeCd(BBS_CODE);
		List<BbsVO> listDBData = bbsService.listPromotionBbs(bbsDataVO);
		List<BbsVO> listDeleteData = new ArrayList<BbsVO>();
		List<String> listYouTubeTtl = new ArrayList<String>();
		List<String> listIsYouTubeTtl = new ArrayList<String>();
		List<FileInfo> files = new ArrayList<FileInfo>();
		//테스트용 전체 삭제 후 입력 로직
		//bbsService.deleteTestBbs(); 
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			conn.disconnect();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(content.toString());
			JsonNode items = jsonObj.get("items");
			
			for (int i = 0; i < items.size(); i++) {
				JsonNode item = items.get(i);
				JsonNode snippet = item.get("snippet");
				String videoId = snippet.get("resourceId").get("videoId").asText();

				apiUrl = "https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=" + videoId
						+ "&key=AIzaSyDWpWZixLN90ShzmoFeH9IFAfJgVHEWUaM";
				url = new URL(apiUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				conn.disconnect();

				jsonObj = mapper.readTree(content.toString());
				JsonNode videoDetails = jsonObj.get("items").get(0);
				String thumbnailUrl = snippet.get("thumbnails").get("high").get("url").asText();
				
				BbsVO bbsVO = BbsVO.builder()
						.pstTtl(snippet.get("title").asText())
						.pstLinkUrl("https://www.youtube.com/watch?v=" + videoId)
						.regDttm(videoDetails.get("snippet").get("publishedAt").asText().replace("T", " ").substring(0, 19))
						.inqCnt(Long.parseLong(videoDetails.get("statistics").get("viewCount").asText()))
						.bbsSeCd(BBS_CODE).build();
				
				listYouTubeTtl.add(bbsVO.getPstTtl());
				
				int isBbs = bbsService.listBbsPromotionCount(bbsVO);
				
				if (isBbs == 0) {
					bbsVO.setMode(CommConst.INSERT);
					bbsService.saveBbs(bbsVO, files);
				}else {
					bbsVO.setMode(CommConst.UPDATE);
					bbsService.updtPromotion(bbsVO, files);
				}
			}
		} catch (NullPointerException e) {
			logger.error("error : ", e);
		} catch (Exception e) {
			logger.error("error : ", e);
		}
		
		String apiUrlCrowed = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=UULF4t-LpTCdoiWFW5pJ0mRtrw&key=AIzaSyDWpWZixLN90ShzmoFeH9IFAfJgVHEWUaM";
		
		try {
			URL url = new URL(apiUrlCrowed);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			conn.disconnect();
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(content.toString());
			JsonNode items = jsonObj.get("items");
			
			for (int i = 0; i < items.size(); i++) {
				JsonNode item = items.get(i);
				JsonNode snippet = item.get("snippet");
				String videoId = snippet.get("resourceId").get("videoId").asText();
				
				apiUrl = "https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=" + videoId
						+ "&key=AIzaSyDWpWZixLN90ShzmoFeH9IFAfJgVHEWUaM";
				url = new URL(apiUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				conn.disconnect();
				
				jsonObj = mapper.readTree(content.toString());
				JsonNode videoDetails = jsonObj.get("items").get(0);
				String thumbnailUrl = snippet.get("thumbnails").get("high").get("url").asText();
				
				BbsVO bbsVO = BbsVO.builder()
						.pstTtl(snippet.get("title").asText())
						.pstLinkUrl("https://www.youtube.com/watch?v=" + videoId)
						.regDttm(videoDetails.get("snippet").get("publishedAt").asText().replace("T", " ").substring(0, 19))
						.inqCnt(Long.parseLong(videoDetails.get("statistics").get("viewCount").asText()))
						.bbsSeCd(BBS_CODE).build();
				
				listYouTubeTtl.add(bbsVO.getPstTtl());
					
				int isBbs = bbsService.listBbsPromotionCount(bbsVO);
				
				if (isBbs == 0) {
					bbsVO.setMode(CommConst.INSERT);
					bbsService.saveBbs(bbsVO, files);
				}else {
					bbsVO.setMode(CommConst.UPDATE);
					bbsService.updtPromotion(bbsVO, files);
				}
			}
			//유튜브에는 없고 DB에만 있을경우 삭제된 것이니 삭제 처리
			for(int i=0; i < listDBData.size(); i++) {
				if(!listYouTubeTtl.contains(listDBData.get(i).getPstTtl())) {
					listDBData.get(i).setMode(CommConst.DELETE);
					bbsService.saveBbs(listDBData.get(i), files);
				}
			}
			//DB에 같은 제목이 있을 경우 한개만 제외하고 삭제하기.
			List<BbsVO> duplicates = bbsService.findDuplicatesByName(bbsDataVO);
		        for (int i = 0; i < duplicates.size(); i++) { // 첫 번째 항목은 제외하고 나머지 항목을 모두 삭제합니다.
		        	for(int z = 1; z < duplicates.get(i).getPromotionIsCnt(); z++) {
		        		duplicates.get(i).setMode(CommConst.DELETE);
						bbsService.deletePromotion(duplicates.get(i), files);
		        	}
		        }
		} catch (NullPointerException e) {
			logger.error("error : ", e);
		} catch (Exception e) {
			logger.error("error : ", e);
		}
	}

	/**
	 * 세션정보를 모델의 변수에 바인딩한다
	 */
	private void setGlobalSessionVO(HttpServletRequest request, BbsVO bbsVO) {

		setGlobalSession(request, bbsVO);

		// 게시판구분 정의
		bbsVO.setBbsSeCd(BBS_CODE);

		if (bbsVO.getUserInfo(request) != null) {
			bbsVO.setGsUserNo(bbsVO.getUserInfo(request).getUserNo());
			bbsVO.setGsRoleId(bbsVO.getUserInfo(request).getRoleId());
			bbsVO.setGsBzentyNo(bbsVO.getUserInfo(request).getBzentyNo());
		}
		// Context
		bbsVO.setGsContext(request.getContextPath());
	}
}
