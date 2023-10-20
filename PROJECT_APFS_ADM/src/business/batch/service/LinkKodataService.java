package business.batch.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;

import business.adm.CodeConst;
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
 * @since   : 2023.06.13
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.13      LHB              First Coding.
 */

@Service
@SuppressWarnings({"all"})
@EnableScheduling
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
	
	// DB INSERT시에 위치정보 수정 여부
	private static final boolean isExecuteLocinfo	= ApplicationProperty.getBoolean("KODATA.SFTP.LOCINFO");
	// DB INSERT시에 프로시저 실행 여부
	private static final boolean isExecuteProc		= ApplicationProperty.getBoolean("KODATA.SFTP.PROC"   );
	
	private static final int UPLOAD_HOUR	= 18;
	private static final int UPLOAD_MIN		= 55;
	private static final int UPLOAD_SEC		= 0;
	
	private static final int LIMIT_SIZE		= 20000;
	
	private static String uploadRealDir	= ApplicationProperty.get("upload.real.dir"          );
	private static String removeDir		= ApplicationProperty.get("upload.remove.dir"        );
	
	private final SFTPUtil sftpUtil = new SFTPUtil();
	private final String sep		= "/";
	
	// jar 파일 다운로드 및 INSERT가 실행되었는지 판단하는 변수
	private static String lastDownload		= null;
	private static String lastInsert		= null;

	/**
     * KODATA -> 로컬 .jar 파일 다운로드
     */
	public void downloadKodata() throws Exception {
		
		String trsmrcvSe	= CodeConst.TRSMRCV_RCV;	// 수신
		String pvsnInstId	= CodeConst.INST_KODATA;	// 제공기관
		String rcptnInstId	= CodeConst.INST_APFS;		// 수신기관
		IntrfcHstVO hstVO	= IntrfcHstVO.builder()
								.pvsnInstId(pvsnInstId)
								.rcptnInstId(rcptnInstId)
								.trsmrcvSeCd(trsmrcvSe)
								.build();
		
		// 현재 날짜 : YYYYMMDD
		String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		if (lastDownload != null && lastDownload.equals(nowDate)) {
			// 해당 날짜 실행된 기록 있으면 진행하지 않음
			return;
		}
		
		try {
			Boolean isConnected = sftpUtil.init(host, user, password, Integer.parseInt(port), null);
			
			if (!isConnected) {
				// SFTP 연결 실패
				logger.info("######다운로드 - SFTP 연결 실패 - E01");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E01);
				return;
			}
			
			if (!sftpUtil.exists(downloadPath + sep + nowDate)) {
				// SFTP 기업정보 다운로드 디렉토리 존재하지 않음.
				logger.info("######다운로드 - SFTP 디렉토리 존재하지 않음 - E02");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E02);
				return;
			}
			
			String fileName = "apfs_" + nowDate + ".jar";
			
			if (!sftpUtil.exists(downloadPath + sep + nowDate + sep + fileName)) {
				// SFTP 기업정보 디렉토리 내에 압축파일 존재하지 않음.
				logger.info("######다운로드 - SFTP 디렉토리 내에 압축파일 존재하지 않음 - E03");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E03);
				return;
			}
			
			// 파일 다운로드할 로컬 디렉토리 없으면 생성
			FileUtils.makeDirectories(uploadRealDir + localPath);
			
			// SFTP로 파일 다운로드
			if (!sftpUtil.download(downloadPath + sep + nowDate, fileName, uploadRealDir + localPath + fileName)) {
				// SFTP 파일 다운로드 실패.
				logger.info("######다운로드 - SFTP 파일 다운로드 실패 - E04");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E04);
				return;
			}
			
			// 파일 이관(다운로드) 성공 로그
			logger.info("######다운로드 - SFTP 파일 다운로드 성공 - S00");
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_S00);
			
		} catch (NullPointerException e) {
			logger.error("######다운로드 - NULL POINTER EXCEPTION : ", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E04);
			return;
		} catch (Exception e) {
			// 잡지 못한 Exception
			logger.error("######다운로드 - EXCEPTION : ", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E04);
			return;
		} finally {
			// SFTP 연결 종료
			sftpUtil.disconnection();
			// 로그 기록
			log(hstVO);
		}
		
		logger.info("######파일다운로드 완료######");
	}
	
	/**
     * 인터페이스 테이블 데이터 INSERT
     */
	public void regiKodata() throws Exception {
		
		String trsmrcvSe	= CodeConst.TRSMRCV_RCV;	// 수신
		String pvsnInstId	= CodeConst.INST_KODATA;	// 제공기관
		String rcptnInstId	= CodeConst.INST_APFS;		// 수신기관
		
		IntrfcHstVO hstVO	= IntrfcHstVO.builder()
								.pvsnInstId(pvsnInstId)
								.rcptnInstId(rcptnInstId)
								.trsmrcvSeCd(trsmrcvSe)
								.build();
		
		// 현재 날짜 : YYYYMMDD
		String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		logger.info("###### lastInsert :: " + lastInsert);
		
		/*
		if (lastInsert != null && lastInsert.equals(nowDate)) {
			// 해당 날짜 실행된 기록 있으면 진행하지 않음
			return;
		}
		*/
		
		String dirPath			= uploadRealDir + localPath + "apfs_" + nowDate + "/";
		String fileName			= "apfs_" + nowDate + ".jar";
		String filePath			= uploadRealDir + localPath + fileName;
		String dirRemovePath	= uploadRealDir + removeDir + localPath;
		
		BufferedReader br		= null;
		
		String code				= null;
		String codeUpperCase	= null;
		
		try {
			
			if (!FileUtils.existFile(filePath)) {
				// 로컬 경로에 압축파일 존재하지 않는 경우
				logger.info("######DB인서트 - 로컬에 압축파일 존재하지 않음 - E11");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E11);
				// 로그 기록
				log(hstVO);
				return;
			}
			
			
			if (!SFTPUtil.extractJar(filePath, dirPath)) {
				// 압축파일 압축해제 실패한 경우
				logger.info("######DB인서트 - 압축 해제 실패 - E12");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E12);
				return;
			}
			
			
			// dirPath에서 txt 파일 목록 호출
			File[] txtFiles = FileUtils.getFileNames(dirPath, "txt");
			if (txtFiles == null || txtFiles.length < 1) {
				// 텍스트 파일이 존재하지 않는 경우
				logger.info("######DB인서트 - 압축 파일 내 텍스트 파일 없음 - E13");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E13);
				// 로그 기록
				log(hstVO);
				return;
			}
			
			// 연계그룹번호 발급
			long grpNo = (long) dao.selectOne("Kodata.viewLinkGrpNo");
			
			// 타임 체크
			long totalStart = System.currentTimeMillis();
			long totalEnd;
			long sectorStart;
			long sectorEnd;
			
			for (int i=0 ; i<txtFiles.length ; i++) {
				sectorStart	= System.currentTimeMillis();
				File file				= txtFiles[i];
				String fileNm			= file.getName();
				String ext				= fileNm.substring(fileNm.lastIndexOf(".") + 1);
				if(!ext.equals("txt")) {
					continue;
				}
				
				code			= file.getName().substring(3, 7);
				codeUpperCase	= code.toUpperCase();
				
				if (!_existCode(CodeConst.LINK_SE, codeUpperCase)) {
					// 연계 코드가 존재하지 않는 경우
					logger.info("######DB인서트 - 존재하지 않는 연계 코드 - E14");
					hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E14);
					// 로그 기록
					log(hstVO);
					return;
				}
				
				//br = new BufferedReader(new FileReader(file));
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
				String line;
				List list = new ArrayList<HashMap>();
				HashMap data = new HashMap();
				int ret;
				while ((line = br.readLine()) != null) {
					// 마지막 구분자 뒤의 공백까지 포함해서
					data = _getHashMap(codeUpperCase, line, grpNo);
					list.add(data);
					
					if(list.size() == LIMIT_SIZE) {
						// list LIMIT_SIZE 개 단위로 끊어서 처리
						_regiSampleBatchBatis(list, "Kodata.regi"+codeUpperCase);
						list.clear();
					}
				}
				
				if(list.size() > 0) {
					// LIMIT_SIZE 개 단위로 끊고 남은 list
					_regiSampleBatchBatis(list, "Kodata.regi"+codeUpperCase);
					list.clear();
				}
				
				br.close();
				
				// 기존데이터 일괄 삭제
				dao.delete("Kodata.delt" + codeUpperCase);
				
				sectorEnd = System.currentTimeMillis();
				logger.info(codeUpperCase + " 총 실행 시간 : " + (sectorEnd-sectorStart)/1000.0 + "초");
			}
			totalEnd = System.currentTimeMillis();
			logger.info("BATCH UPLOAD 총 실행 시간 : " + (totalEnd-totalStart)/1000.0 + "초");
			
			// DB 업로드 성공 로그
			logger.info("######DB인서트 - DB 인서트 성공 - S10");
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_S10);
		
		} catch (SQLException e) {
			// SQLException 발생한 경우
			logger.error(codeUpperCase + " SQL EXCEPTION :: ", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E15);
			return;
		} catch (IOException e) {
			logger.error("######DB인서트 - IOException :: ", e);
		} catch (Exception e) {
			// 배치 작업중 에러 (SQLException) 발생한 경우
			logger.error(codeUpperCase + " SQL EXCEPTION :: ", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E15);
			// 로그 기록
			log(hstVO);
			return;
		} finally {
			if (br != null) {
				br.close();
			}
		}
		
		// Procedure 호출
		try {
			if (isExecuteProc) {
				procedure(hstVO);
			}
		} catch (NullPointerException e) {
			logger.error("Procedure Exception :: " + e);
		} catch (Exception e) {
			logger.error("Procedure Exception :: " + e);
		}
		
		// 위치정보 수정
		try {
			if (isExecuteLocinfo) {
				logger.info("##### 위치정보 수정 시작");
				long start = System.currentTimeMillis();
				modifyEntLcinfo();
				long end = System.currentTimeMillis();
				logger.info("##### 위치정보 수정 종료 (took " + (end-start)/1000 + " seconds.");
			}
		} catch (NullPointerException e) {
			logger.error("Procedure Exception :: " + e);
		} catch (Exception e) {
			logger.error("Lcinfo modify Exception :: " + e);
		}
		
		try {
			// jar 압축 해제된 폴더 삭제
			FileUtils.removeDirectories(dirPath, false);
			
			// 파일 이동
			FileUtils.moveFile(filePath, dirRemovePath + fileName, true);
		} catch (NullPointerException e) {
			// 파일 이동이 되지 않았을 경우
			logger.error("######DB인서트 - 파일 REMOVED 경로 이동 실패 - E16", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E16);
			
			// 로그 기록
			log(hstVO);
			return;
		} catch (Exception e) {
			// 파일 이동이 되지 않았을 경우
			logger.error("######DB인서트 - 파일 REMOVED 경로 이동 실패 - E16", e);
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E16);
			
			// 로그 기록
			log(hstVO);
			return;
		}
		
		logger.info("######DB인서트 완료######");
	}
	
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
     * 로컬 -> KODATA .txt 파일 업로드
     */
	public void uploadKodata() throws Exception {
		
		String trsmrcvSe	= CodeConst.TRSMRCV_TRSM;	// 송신
		String pvsnInstId	= CodeConst.INST_APFS;		// 제공기관
		String rcptnInstId	= CodeConst.INST_KODATA;	// 수신기관
		IntrfcHstVO hstVO	= IntrfcHstVO.builder()
								.pvsnInstId(pvsnInstId)
								.rcptnInstId(rcptnInstId)
								.trsmrcvSeCd(trsmrcvSe)
								.build();
		
		// 현재 날짜 : YYYYMMDD
		String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		String dirPath			= null;
		String fileName			= null;
		String filePath			= null;
		String dirRemovePath	= null;
		
		try {
			dirPath		= uploadRealDir + localPath;
			fileName	= nowDate + ".txt";
			filePath	= uploadRealDir + localPath + fileName;
			
			File file = new File(filePath);
			
			if(!file.exists()) {
				// 파일이 존재하지 않을 경우
				logger.info("######업로드 - 파일 미존재 에러 - E22");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E22);
				// 로그 기록
				log(hstVO);
				return;
			}
			
			Boolean isConnected = sftpUtil.init(host, user, password, Integer.parseInt(port), null);
			
			if (!isConnected) {
				// SFTP 연결 실패
				logger.info("######업로드 - SFTP 연결 실패 - E23");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E23);
				// 로그 기록
				log(hstVO);
				return;
			}
			
			if (!sftpUtil.exists(uploadPath)) {
				// SFTP 기업정보 업로드 디렉토리 존재하지 않음.
				logger.info("######업로드 - SFTP 업로드 디렉토리 존재하지 않음 - E24");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E24);
				// 로그 기록
				log(hstVO);
				return;
			}
			
			Boolean isUploaded = sftpUtil.upload(uploadPath, file, OVERWRITE); // OVERWRITE 덮어쓰기
			
			if (!isUploaded) {
				// 파일 업로드 실패
				logger.info("######업로드 - SFTP 업로드 실패 - E25");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E25);
				// 로그 기록
				log(hstVO);
				return;
			}
			
			// 파일 이관(업로드) 성공 로그
			logger.info("######업로드 - SFTP 업로드 성공 - S20");
			hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_S20);
			
			// 파일 이동
			dirRemovePath = uploadRealDir + removeDir + localPath;
			FileUtils.deleteFile(dirRemovePath + fileName);
			FileUtils.moveFile(filePath, dirRemovePath + fileName);
			
			if (!FileUtils.existFile(dirRemovePath + fileName)) {
				// 파일 이동이 되지 않았을 경우
				logger.info("######업로드 - 파일 REMOVED 경로 이동 실패 - E26");
				hstVO.setPrcsRsltCd(CodeConst.PRCS_RSLT_E26);
				// 로그 기록
				log(hstVO);
				return;
			}
			
		} catch (NullPointerException e) {
			logger.error("######업로드 - Exception :: ", e);
		} catch (Exception e) {
			// 처리하지 못한 EXCEPTION
			logger.error("######업로드 - Exception :: ", e);
		} finally {
			// SFTP 연결 종료
			sftpUtil.disconnection();
			
			// 로그 기록
			log(hstVO);
		}
		
		logger.info("######파일업로드 완료######");
	}
	
	/**
     * Code에 해당하는 테이블 형식에 맞는 Key, Value 형태로 String 값 쪼개서 HashMap에 담아서 반환
     *
     * @param jarFilePath			jar 파일 경로
     * @param destinationDirPath	압축 해제될 폴더 경로
     */
	private HashMap _getHashMap(String code, String line, Long grpNo) {
		HashMap result = new HashMap();
		
		// 코드
		result.put("code", code);
		// 연계상태
		result.put("linkStus", "N");
		// 연계그룹번호
		result.put("linkGrpNo", grpNo);
		
		String[] values = line.split("[|]", -1);
		result.put("data", values);
		
		List<Integer> keyIndexes = new ArrayList<>();
		switch(code) {
			case CodeConst.LINK_50D1: {
				keyIndexes.add(0);
				break;
			}
			case CodeConst.LINK_50D2: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				break;
			}
			case CodeConst.LINK_5038: {
				keyIndexes.add(0);
				break;
			}
			case CodeConst.LINK_5039: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				keyIndexes.add(3);
				keyIndexes.add(4);
				break;
			}
			case CodeConst.LINK_5041: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				break;
			}
			case CodeConst.LINK_5056: {
				keyIndexes.add(0);
				break;
			}
			case CodeConst.LINK_5057: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				keyIndexes.add(3);
				break;
			}
			case CodeConst.LINK_5058: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				keyIndexes.add(3);
				keyIndexes.add(4);
				break;
			}
			case CodeConst.LINK_5062: {
				keyIndexes.add(0);
				break;
			}
			case CodeConst.LINK_5063: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				keyIndexes.add(3);
				break;
			}
			case CodeConst.LINK_50TA: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				break;
			}
			case CodeConst.LINK_50TB: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				break;
			}
			case CodeConst.LINK_50TF: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				keyIndexes.add(3);
				break;
			}
			case CodeConst.LINK_50TG: {
				keyIndexes.add(0);
				keyIndexes.add(14);
				break;
			}
			case CodeConst.LINK_50TH: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				break;
			}
			case CodeConst.LINK_50TI: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				break;
			}
			case CodeConst.LINK_50TK: {
				keyIndexes.add(0);
				keyIndexes.add(14);
				break;
			}
			case CodeConst.LINK_50TL: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				keyIndexes.add(2);
				keyIndexes.add(3);
				break;
			}
			case CodeConst.LINK_50PA: {
				keyIndexes.add(0);
				keyIndexes.add(1);
				break;
			}
		}
		
		// keyIndexes에 해당하는 값들을 key1, key2, ... 의 value 값으로 세팅
		for(int i=0 ; i<keyIndexes.size(); i++) {
			result.put("key"+(i+1), values[keyIndexes.get(i)]);
		}
		
		return result;
	}
	
	/**
	 * 배치 등록
	 */
	public int _regiSampleBatchBatis(final List list, final String statement) throws Exception {
		// 이중 Transation 처리 문제 Batch ExcutorType 세션 별도 사용.
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		long start	= 0L;
		long end	= 0L;
		try {
			start = System.currentTimeMillis();
			
			for (int i = 0; i < list.size(); i++) {
				sqlBatchSession.insert(statement, list.get(i));
			}
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			return 1;
		} catch (NullPointerException e) {
			logger.error("######_regiSampleBatchBatis() - Exception :: ", e);
			sqlBatchSession.rollback();
			return 0;
		} catch (Exception e) {
			logger.error("######_regiSampleBatchBatis() - Exception :: ", e);
			sqlBatchSession.rollback();
			return 0;
		} finally {
			sqlBatchSession.close();
			end = System.currentTimeMillis();
			logger.info("##### Took " + (end-start) + " milliseconds.");
		}
	}
	
	/**
     * LSH. 코드값에 해당하는 코드가 있는지 확인
     */
    public boolean _existCode(String upCdId, String cdId) {
		if (CommUtils.isEmpty(upCdId))
			return false;
		if (CommUtils.isEmpty(cdId))
			return false;
		try {
			Map<String,Object> code = _getCode(upCdId, cdId);
			if (code != null)
				return true;
		} catch(IOException e) {
			logger.error("_existCode IOException :: ", e);
		} catch(Exception e) {
			logger.error("_existCode Exception :: ", e);
		}
		return false;
    }
    
    /**
     * 코드상세조회
     */
    public Map<String,Object> _getCode(String upCdId, String cdId) throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("upCdId", upCdId);
    	params.put("cdId"  , cdId);
    	return dao.selectOne("Kodata.viewCode", params);
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
 				
 				if (jsonArr.size() > 0) {
 					String lon = (String) ((JSONObject) jsonArr.get(0)).get("x");
 	 				String lat = (String) ((JSONObject) jsonArr.get(0)).get("y");
 	 				
 	 				if (CommUtils.isNotEmpty(lon) && CommUtils.isNotEmpty(lat)) {
 	 					result.put("long",	lon);
 	 	 				result.put("lat",	lat);
 	 	 				
 	 	 				return result;
 	 				}
 				}
 			}
 		} catch (IOException e) {
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
    
    public void modifyEntLcinfo() throws Exception {
    	try {
			// 수정일자가 없거나, 수정일이 기준일자 전이거나, 위치정보가 없는 경우
			List listEnt = dao.selectList("Kodata.listEntNoGeometry");
			for (int i=0 ; i<listEnt.size(); i++) {
				HashMap data	= (HashMap) listEnt.get(i);
				String address	= CommUtils.nvlTrim((String) data.get("lctnAddr")) + CommUtils.nvlTrim((String) data.get("lctnDaddr"));
				if(CommUtils.isEmpty(address)) {
					continue;
				}
				HashMap lcinfo	= _getPointByAddress(address);
				if (CommUtils.isNotEmpty(lcinfo)) {
					data.put("lat",		lcinfo.get("lat"));
					data.put("long",	lcinfo.get("long"));
					dao.update("Kodata.updtEntNoGeometry", data);
				}
			}
			
		} catch (NullPointerException e) {
			logger.error("###### 위치정보 수정중 Exception", e);
		} catch (Exception e) {
			logger.error("###### 위치정보 수정중 Exception", e);
		}
    }
    
    public boolean procedure(IntrfcHstVO logData) throws Exception {
    	boolean result = false;
    	
    	String trsmrcvSe	= CodeConst.TRSMRCV_RCV;	// 수신
		String pvsnInstId	= CodeConst.INST_KODATA;	// 제공기관
		String rcptnInstId	= CodeConst.INST_APFS;		// 수신기관
		
		IntrfcHstVO hstVO;
		if (CommUtils.isEmpty(logData)) {
			hstVO	= IntrfcHstVO.builder()
					.pvsnInstId(pvsnInstId)
					.rcptnInstId(rcptnInstId)
					.trsmrcvSeCd(trsmrcvSe)
					.build();
		} else {
			hstVO = logData;
		}
    	
    	// Procedure 호출
		try {
			if (isExecuteProc) {
				logger.info("##### Procedure Started.");
				long start = System.currentTimeMillis();
				// 230703. procedure
				HashMap procedure = dao.selectOne("Kodata.callSpBatchKodata");
				long end = System.currentTimeMillis();
				logger.info("##### Procedure Took " + (end-start)/1000 + " seconds.");
				
				// 로그 기록
				log(hstVO);
				
				result = true;
			}
		} catch (NullPointerException e) {
			logger.error("Procedure Exception :: " + e);
			result = false;
		} catch (Exception e) {
			logger.error("Procedure Exception :: " + e);
			result = false;
		}
		
		return result;
    }
    
    
}