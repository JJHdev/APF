package business.batch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.util.properties.ApplicationProperty;

public class DBInsert {
	
	protected static final Logger logger = LoggerFactory.getLogger(DBInsert.class);
	
	// DB
	private static Connection 		 PG_CONN 	= null;
	private static Statement		 PG_STMT 	= null;
	private static PreparedStatement PG_PSMT	= null;
	
	private static final String PGS_DRIVER		= ApplicationProperty.get("Globals.PostgreSQL.Driver");
	private static final String PGS_CONNECT 	= ApplicationProperty.get("Globals.PostgreSQL.Url");
	private static final String PGS_USERID		= ApplicationProperty.get("Globals.PostgreSQL.UserName");
	private static final String PGS_PASSWORD	= ApplicationProperty.get("Globals.PostgreSQL.Password");
	
	private static final String LINK_STUS		= "N";
	
	private static String	DIR_PATH				= null;
	private static String	LINK_GRP_NO				= null;
	private static int		FILE_CNT				= 0;
	private static String	CODE					= null;
	private static int		COL_CNT					= 0;
	private static int 		TOTAL_COL_CNT			= 0;
	
	// 배치 등록 그룹 카운트
	private static final int LIMIT_COUNT		= 10000;
	
	/**
	 * 초기 설정
	 */
	private static void init(String[] args) throws SQLException {
		try {
			if(args.length > 1) {
				DIR_PATH	= args[0];
				LINK_GRP_NO	= args[1];
			} else {
				throw new Exception("파일 경로와 그룹 번호 인자가 입력되지 않았습니다.");
			}
			
	       	// DB Connection 연결
			Class.forName(PGS_DRIVER);
			PG_CONN = DriverManager.getConnection(PGS_CONNECT, PGS_USERID, PGS_PASSWORD);
			
			PG_CONN.setAutoCommit(false);
			
		} catch (Exception e) {
			logger.error("DB Connection error :: ", e);
		}
	}
	
	/**
	 * Main
	 * @param args
	 * @throws SQLException 
	 */
    public static void main(String[] args) throws Exception {
    	
    	long start	= System.currentTimeMillis();
    	long end	= 0;
    	
        try {
        	// 1. 초기설정 (DB연결등..)
        	init(args);
        	
        	// 2. TXT파일 -> DB등록
        	readFiles();
        	        	
        } catch(Exception e) {
        	logger.error("Error :: ", e);
        } finally {
        	if (PG_CONN != null) PG_CONN.close();
        	end = System.currentTimeMillis();
        	
        	logger.info("");
			logger.info("Total Count[TXT FILE] = " + FILE_CNT);
            logger.info("#####[소요 시간 :: " + (end-start) + "ms]#####");
        }
    }
	
	/**
	 * CSV 파일 처리 (전체 파일)
	 * 1. CSV 파일 읽기
	 * 2. CSV 파일 내용 DB 등록처리
	 * @throws Exception
	 */
	private static void readFiles() throws Exception {
		File f = new File(DIR_PATH);

		if (f == null || !f.exists() || !f.isDirectory()) {
			throw new Exception("해당 경로가 존재하지 않습니다. (" + DIR_PATH + ")");
		}
		
		// 전체 파일리스트 (CSV)
		File[] list = getFileNames(DIR_PATH, "txt");
		FILE_CNT = list.length;
		
		if (list==null || FILE_CNT == 0) {
			throw new Exception("해당 경로에 처리할 파일(txt)이 없습니다. (" + DIR_PATH + ")");
		}

		try {
    		for(int i=0 ; i < FILE_CNT ; i++) {
    			File target = list[i];
    			
    			readFileNInsert(target);
    			
    		}
		} catch (Exception e) {
			logger.error("read Files Error :: ", e);
		} finally {
            // CSV파일 DB 등록완료
		}
	}
	
	/**
	 * TXT 파일읽기
	 * @throws SQLException 
	 * @throws Exception
	 */
	private static void readFileNInsert(File f) throws SQLException {
        List rowList = new ArrayList();
        BufferedReader br = null;
        
        CODE = f.getName().substring(3, 7).toUpperCase();
        
        try {
        	br = new BufferedReader(new FileReader(f));

        	String 	line 	= "";
        	int		n		= 0;
        	
    		String stdDate 	= "";
    		int rowcnt		= 0;
    		
			PG_STMT = PG_CONN.createStatement();
            
            while((line = br.readLine()) != null) {
            	rowcnt++;
            	String arrLine[] = line.split("[|]", -1);	// -1 구분자 이후 마지막 빈값도 읽음.
            	COL_CNT			= arrLine.length;
            	TOTAL_COL_CNT	= COL_CNT + 2;				// ...values, linkStus, linkGrpNo
            	// Insert Query
                PG_PSMT = PG_CONN.prepareStatement(insertQry());
            	
            	List colList = new ArrayList<>(Arrays.asList(arrLine));
                colList.add(LINK_STUS);
                colList.add(LINK_GRP_NO);
                
                rowList.add(colList);
                
				// LIMIT_COUNT 사이즈마다 처리
				if (rowcnt % LIMIT_COUNT == 0) {
					// 배치실행
					runBatch(rowList);
					rowList.clear();
				}
            }
            
            // 나머지 처리
			if (rowList != null && rowList.size() > 0) {
				runBatch(rowList);
			}
			rowList.clear();
			
        } catch (Exception e) {
        	PG_CONN.rollback();
        	logger.error("readFileNInsert Error :: ", e);
        } finally {
            if (PG_STMT !=null)   try { PG_STMT.close(); } catch (Exception e1) { e1.printStackTrace(); }
            if (PG_PSMT !=null)   try { PG_PSMT.close(); } catch (Exception e1) { e1.printStackTrace(); }
            
            try {
                if (br != null) br.close(); 
            } catch (IOException e) {
                logger.error("IOException :: ", e);
            }
        }
	}
	
	/**
	 * Insert Query
	 * @return
	 */
	private static String insertQry() {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO IF_" + CODE + "            ");
		sql.append("VALUES (                               ");
		sql.append("        NEXTVAL('SEQ_IF_" + CODE + "'),");
		for(int i=0 ; i<TOTAL_COL_CNT ; i++) {
			sql.append("    ?,                             ");
		}
		sql.append("        NOW(),                         ");
		sql.append("        ?,                             ");
		sql.append("        ?,                             ");
		sql.append("       )                               ");
		
		return sql.toString();
	}
	
	/**
	 * CSV 파일 -> DB등록처리
	 * @throws SQLException 
	 * @throws Exception
	 */
	private static void runBatch(List rowList) throws SQLException {
		logger.info("====DB 등록 실행====");
		
        try {		
            // 데이터 등록
            for (int i = 0; i < rowList.size(); i++) {
            	List line	= (List)rowList.get(i);

            	for (int c=0 ; c<TOTAL_COL_CNT ; c++) {
            		if (c < line.size()) {
            			String celldata = nvlTrim((String)line.get(c)).replaceAll("\"", "");
            			
            			PG_PSMT.setString(c+1, celldata);
            		} else {
            			PG_PSMT.setString(c+1, "");
            		}
            	}
            	
            	PG_PSMT.addBatch();

            	PG_PSMT.clearParameters();
            }

            // 나머지실행
            PG_PSMT.executeBatch();
        	PG_PSMT.clearBatch();
            PG_CONN.commit();

        } catch (Exception e) {
        	logger.error("runBatch Error :: ", e);
        } 
	}
	
	
	
	// 특정 확정자 파일 리스트 조회
	private static File[] getFileNames(String targetDirName, String fileExt) {
		File dir = new File(targetDirName);

		File[] files = null;
		
		if (dir.isDirectory()) {
			final String ext = fileExt.toLowerCase();
			files = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return false;
					}
					return file.getName().toLowerCase().endsWith("." + ext);
				}
			});
		}

		return files;
	}
	
	// 빈 문자열 반환
	public static String nvl(String str) {
		String value = "";
		if (str != null && str.length() > 0) {
			value = str;
		}
		return value;
	}
	
	// 문자열 양쪽 공백 제거
	public static String nvlTrim(String str) {
		return nvl(str).trim();
	}
}
