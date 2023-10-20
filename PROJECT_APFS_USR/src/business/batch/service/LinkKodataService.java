package business.batch.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;

import business.usr.CodeConst;
import business.usr.invest.service.EntVO;
import commf.dao.CommonMapperImpl;
import common.base.BaseService;
import common.util.CommUtils;
import common.util.FileUtils;
import common.util.properties.ApplicationProperty;

/**
 * KODATA연계 서비스 
 * 
 * @class   : LinkodataServiceImpl
 * @author  : LHB
 * @since   : 2023.07.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.13      LHB              First Coding.
 */

@Service("LinkKodataService")
@SuppressWarnings({"all"})
public class LinkKodataService extends BaseService {

	@Autowired
	private CommonMapperImpl dao;
	
	@Autowired 
	private SqlSessionFactory sqlSessionFactory;
	
	private static String host			= ApplicationProperty.get("KODATA.SFTP.HOST"         );
	private static String port			= ApplicationProperty.get("KODATA.SFTP.PORT"         );
	private static String user			= ApplicationProperty.get("KODATA.SFTP.USER"         );
	private static String password		= ApplicationProperty.get("KODATA.SFTP.PASSWORD"     );
	private static String uploadPath	= ApplicationProperty.get("KODATA.SFTP.UPLOAD.PATH"  );
	private static String downloadPath	= ApplicationProperty.get("KODATA.SFTP.DOWNLOAD.PATH");
	private static String localPath		= ApplicationProperty.get("KODATA.SFTP.LOCAL.PATH"   );
	private static final int OVERWRITE	= 0;
	
	private static final int UPLOAD_HOUR	= 18;
	private static final int UPLOAD_MIN		= 55;
	private static final int UPLOAD_SEC		= 0;
	
	private static final int LIMIT_SIZE		= 20000;
	
	private static String uploadRealDir	= ApplicationProperty.get("upload.real.dir"          );
	private static String removeDir		= ApplicationProperty.get("upload.remove.dir"        );
	
	private final String sep		= "/";

	/**
     * 로컬 파일에 기업정보 작성
     */
	public void writeKodata(String trgtDate, List<String[]> enpList) throws Exception {
		
		String trsmrcvSe	= CodeConst.TRSMRCV_TRSM;	// 송신
		String pvsnInstId	= CodeConst.INST_APFS;		// 제공기관
		String rcptnInstId	= CodeConst.INST_APFS;	// 수신기관
		IntrfcHstVO hstVO	= IntrfcHstVO.builder()
								.pvsnInstId(pvsnInstId)
								.rcptnInstId(rcptnInstId)
								.trsmrcvSeCd(trsmrcvSe)
								.build();
		
		// 현재 날짜 : YYYYMMDD
		String nowDate;
		if (CommUtils.nvlTrim(trgtDate).isEmpty()) {
			LocalTime now			= LocalTime.now();
			LocalTime uploadTime	= LocalTime.of(UPLOAD_HOUR, UPLOAD_MIN, UPLOAD_SEC);
			
			if (now.isAfter(uploadTime)) {
				// SFTP 업로드 시간(18시 50분) 이후인 경우 다음 날짜의 파일에 작성
				Calendar currCal = Calendar.getInstance();
				currCal.add(Calendar.DATE, 1);
				nowDate	= new SimpleDateFormat("yyyyMMdd").format(new Date(currCal.getTimeInMillis()));
			} else {
				nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
		} else {
			nowDate	= trgtDate;
		}
		
		if (enpList==null || enpList.size()<1) {
			logger.info("######파일입출력 - 기업 정보 누락");
			throw new Exception("기업 정보가 누락되었습니다.");
		}
		
		BufferedWriter		bw		= null;
		OutputStreamWriter	osw		= null;
		FileOutputStream	fos		= null;
		
		File file					= null;
		
		String dirPath				= null;
		String fileName				= null;
		String filePath				= null;
		String dirRemovePath		= null;
		
		try {
			// 파일 다운로드할 로컬 디렉토리 없으면 생성
			FileUtils.makeDirectories(uploadRealDir + localPath);
			
			dirPath		= uploadRealDir + localPath;
			fileName	= nowDate + ".txt";
			filePath	= uploadRealDir + localPath + fileName;
			
			file = new File(filePath);
			
			if(!file.exists()) {
				// 파일이 존재하지 않을 경우 생성
				file.createNewFile();
			}
			
			fos = new FileOutputStream(filePath, true);
			osw = new OutputStreamWriter(fos, "euc-kr");
			bw = new BufferedWriter(osw);
			String line = null;
			
			for(int i=0 ; i<enpList.size() ; i++) {
				String[] enp = enpList.get(i);
				String custNo	= _getFixedStr(enp[0], 13, " ", true);
				String brno		= _getFixedStr(enp[1], 10, " ", true);
				String crno		= _getFixedStr(enp[2], 13, " ", true);
				String bzentyNm	= _getFixedStr(enp[3], 50, " ", true);
				String noSe		= _getFixedStr(enp[4], 1,  " ", true);
				String delYn	= _getFixedStr(enp[5], 1,  " ", true);
				line = custNo + brno + crno + bzentyNm + noSe + delYn;
				
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			// 파일 입출력 에러 발생한 경우
			logger.error("######파일입출력 - 파일 입출력 에러 - E21", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E21);
			return;
		} catch (Exception e) {
			// 그 외 에러 발생한 경우
			logger.error("######파일입출력 - 파일 입출력 에러 - E21", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E21);
			return;
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (osw != null) {
				osw.close();
			}
			if (fos != null) {
				fos.close();
			}
			
			// 로그 기록
			log(hstVO);
		}
		
		logger.info("######파일입출력 완료######");
	}
    
    /**
     * 고정된 자릿수의 문자열 반환
     * 
     * @param str		고정된 자릿수로 만들 문자열
     * @param length 	고정된 자릿수
     * @param sep		채울 문자
     * @param trunc		절삭 여부 (초기 입력된 파라미터가 자릿수보다 클 경우 뒷자리 자를지 여부)
     */
    public String _getFixedStr(String str, int length, String sep, boolean trunc) throws Exception {
    	if (str.length() < length) {
    		StringBuffer result = new StringBuffer();
    		
    		result.append(str);
    		for(int i=0 ; i<length-str.length() ; i++) {
    			result.append(sep);
    		}
    		
    		return result.toString();
    	} else {
    		if(trunc) {
    			return str.substring(0, length);
    		} else {
    			return str;
    		}
    	}
    }
    
    /**
     * 카카오 API 사용하여 주소 -> 좌표 변환
     */
 	private HashMap _getPointByAddress(String address) {
 		
 		HashMap result = new HashMap();
 		
 		try {
 			String strUrl	= "https://dapi.kakao.com/v2/local/search/address.json?query=" + URLEncoder.encode(address, "UTF-8");
 			String strKey	= ApplicationProperty.get("OAUTH.KAKAO.CLIENT_ID");
 			strKey			= "KakaoAK " + strKey;
 			URL url = new URL(strUrl);
 			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
 			con.setConnectTimeout(5000);
 			con.setReadTimeout(5000);
 			con.setRequestMethod("GET");
 			con.setRequestProperty("Authorization", strKey);
 			con.setDoOutput(false); 

 			StringBuilder sb = new StringBuilder();
 			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
 				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
 				String line;
 				while ((line = br.readLine()) != null) {
 					sb.append(line).append("\n");
 				}
 				br.close();
 				
 				String jsonStr = sb.toString();
 				JSONParser parser = new JSONParser();
 				JSONObject jsonObj = (org.json.simple.JSONObject) parser.parse(jsonStr);
 				JSONArray jsonArr = (org.json.simple.JSONArray) jsonObj.get("documents");
 				
 				String lon = (String) ((JSONObject) jsonArr.get(0)).get("x");
 				String lat = (String) ((JSONObject) jsonArr.get(0)).get("y");
 				result.put("long",	lon);
 				result.put("lat",	lat);
 				
 				return result;
 			}
 		} catch (NullPointerException e) {
 			logger.error("Kakao RestAPI Error : ", e);
 		} catch (Exception e) {
 			logger.error("Kakao RestAPI Error : ", e);
 		}
 		
 		return null;
 	}
    
    /**
     * 로그 기록
     */
    public void log(IntrfcHstVO hstVO) throws Exception {
    	dao.insert("IntrfcHst.regiIntrfcHst", hstVO);
    }
    
    
}