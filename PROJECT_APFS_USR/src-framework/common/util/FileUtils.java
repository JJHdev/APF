package common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import commf.exception.BusinessException;
import common.file.FileDirectory;
import common.file.FileDocument;
import common.file.FileInfo;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;

/**
 * Program Name 	: FileUtil
 * Description 		: 파일 처리 관련된 유틸리티 API.
 * Programmer Name 	: ntarget
 * Creation Date 	: 2021-02-08
 * Used Table 		:
 */

public class FileUtils {
    /**
     * Logging output for this class.
     */
    protected static Log log = LogFactory.getLog(FileUtils.class);
    private static String ENCODING = "EUC-KR";
    
    public static final int BUFFER_SIZE = 4096;

    /**
     * path 파라미터의 파일이나 디렉터리가 없으면, 해당 디렉터리를 생성한다.
     * 만일 파일이나 디렉터리가 존재한다면 false를 반환한다.
     *
     * @param path
     *         	생성할 디렉터리 위치
     * @return boolean
     * 			<code>true</code> 성공적으로 디렉터리를 생성한 경우.
     *         	<code>false</code> 디렉터리를 생성하지 않은 경우.
     */
    public static boolean makeDirectories(String path) {
        if ( StringUtils.isBlank(path)) {
            throw new BusinessException("Given path parameter is blank. Thus can't make directory.");
        }

        File f = new File(path);

        if (f.exists()) {
            return false;
        } else {
            if (log.isDebugEnabled()) {
                log.debug(" Path does not exist on the file system. Creating folders...");
            }
            f.mkdirs();
            return true;

            /*
            f.setExecutable(false, true);
            f.setReadable(true);
            f.setWritable(false, true);

            if (log.isDebugEnabled()) {
                log.debug(" Path does not exist on the file system. Creating folders...");
            }

            return f.mkdirs();
            */
        }
    }

    /**
     * 파일시스템에서 지정된 디렉터리를 삭제한다.
     *
     * @param path
     *            삭제한 디렉터리
     * @todo implement flag
     */
    public static void removeDirectories(String path, boolean removeWithContents) {
        if (log.isDebugEnabled()) {
            log.debug(" Attempting to remove folders for path: " + path);
        }
        if (StringUtils.isBlank(path)) {
            throw new BusinessException("Given path is blank.");
        }

        File f = new File(path);

        try {
            org.apache.commons.io.FileUtils.deleteDirectory(f);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 지정된 파일의 위치를 옮긴다.
     *
     * @param fromFile 원본 위치
     * @param toFile 대상 위치
     * @throws Exception
     */
    public static void moveFile(String fromFile, String toFile, boolean overwrite) throws Exception {
        if( StringUtils.isNotBlank(toFile)){
            String replacedPath = replacePathToSlash(toFile);
            makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));
        }
        else{
            throw new BusinessException("Given target file path is blank. Thus can't move source file.");
        }

        copyFile(fromFile, toFile, overwrite);
        deleteFile(fromFile);
    }
    
    /**
     * 지정된 파일의 위치를 옮긴다.
     *
     * @param fromFile 원본 위치
     * @param toFile 대상 위치
     * @throws Exception
     */
    public static void moveFile(String fromFile, String toFile) throws Exception {
        if( StringUtils.isNotBlank(toFile)){
            String replacedPath = replacePathToSlash(toFile);
            makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));
        }
        else{
            throw new BusinessException("Given target file path is blank. Thus can't move source file.");
        }

        copyFile(fromFile, toFile);
        deleteFile(fromFile);
    }

    /**
     * 파일을 복사한다. 대상 파일이 이미 존재하는 경우, 런타임 예외를 발생시킨다.
     *
     * @param fromFile 원본 파일
     * @param toFile 대상 파일
     * @throws Exception
     * @throws Exception
     */
    public static void copyFile(String fromFile, String toFile) throws Exception {
    	copyFile(fromFile, toFile, false);
    }

    /**
     * 2021.10.13 LSH
     * 파일을 복사한다.
     * 덮어쓰기가 true이면 동일한 명칭의 파일이 존재하는 경우 덮어쓰기를 한다.
     * 덮어쓰기가 false이면 동일한 명칭의 파일이 존재하는 경우,
     * 런타임 예외를 발생시킨다.
     *
     * @param fromFile 원본 파일
     * @param toFile 대상 파일
     * @param overwrite 덮어쓰기여부
     * @throws Exception
     * @throws Exception
     */
    public static void copyFile(String fromFile, String toFile, boolean overwrite) throws Exception {

        // retrieve the file data
        FileInputStream fis  = null;
        FileOutputStream fos = null;

        try {
        	if (overwrite == false) {
                if (new File(toFile).exists()) { // 대상 파일이 이미 존재하면 예외 처리
                    throw new BusinessException("Given target file exist already. : " + toFile);
                }
        	}

            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;

//            while ((bytesRead = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
//                fos.write(buffer, 0, bytesRead);
//            }
            while (true) {
                bytesRead = fis.read(buffer, 0, BUFFER_SIZE);
                if (bytesRead == -1) {
                   break;
                }
                fos.write(buffer, 0, bytesRead);
            }

            //close the stream
            fis.close();
            fos.close();
        } catch (FileNotFoundException fnfe) {
            throw new BusinessException(fnfe);
        } catch (Exception ioe) {
            throw new BusinessException(ioe);
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }

    /**
     * 2021.10.13 LSH
     * 파일을 복사한다.
     * 해당 경로가 없을 경우 경로를 생성해준다.
     *
     * @param fromFile 원본 파일
     * @param toFile 대상 파일
     * @param overwrite 덮어쓰기여부
     * @throws Exception
     * @throws Exception
     */
    public static void copyFileWithDir(String fromFile, String toFile, boolean overwrite) throws Exception {

        if( StringUtils.isNotBlank(toFile)){
            String replacedPath = replacePathToSlash(toFile);
            makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));
        }
        else{
            throw new BusinessException("Given target file path is blank. Thus can't move source file.");
        }
        copyFile(fromFile, toFile, overwrite);
    }

    /**
     * 파일을 복사한다.
     *
     * @param in byte[] 복사할 원본의 바이너리
     * @param outPathName String 목표 파일명
     * @throws IOException
     */
    public static void copyFile(byte[] in, String outPathName) throws IOException {
        Assert.notNull(in, "No input byte array specified");
        File out = new File(outPathName);
        if (out.exists()) {
            throw new BusinessException("Given target file exist already. : " + outPathName);
        }
        copyFile(in, out);
    }

    /**
     * 파일을 복사한다.
     *
     * @param in byte[]
     * @param out File
     * @throws IOException
     */
    public static void copyFile(byte[] in, File out) throws IOException {
        Assert.notNull(in, "No input byte array specified");
        Assert.notNull(out, "No output File specified");
        ByteArrayInputStream inStream = new ByteArrayInputStream(in);

        String replacedPath = replacePathToSlash(out.getPath());
        makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));

        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
        copyFile(inStream, outStream);
    }

    /**
     * 파일을 복사한다.
     *
     * @param in InputStream
     * @param out OutputStream
     * @return
     * @throws IOException
     */
    public static int copyFile(InputStream in, OutputStream out) throws IOException {
        Assert.notNull(in, "No InputStream specified");
        Assert.notNull(out, "No OutputStream specified");
        try {
            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
//			while ((bytesRead = in.read(buffer)) != -1) {
//				out.write(buffer, 0, bytesRead);
//				byteCount += bytesRead;
//			}
            while (true) {
                bytesRead = in.read(buffer);
                if(bytesRead == -1 ) {
                    break;
                }
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException ex) {
                log.warn("Could not close InputStream", ex);
            }
            try {
                out.close();
            }
            catch (IOException ex) {
                log.warn("Could not close OutputStream", ex);
            }
        }
    }

    /**
     * 지정된 위치의 파일을 byte[]로 반환한다.
     *
     * @param fullFilePath
     * @return
     */
    public static byte[] getFileToByteArray(String fullFilePath) throws IOException {
        Assert.notNull(fullFilePath, "No input byte array specified");
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        		FileInputStream fis = new FileInputStream(fullFilePath)) {
        	byte[] buffer = new byte[1024];
        	while (true) {
        		int rsize = fis.read(buffer);
        		if (rsize < 0) break;
        		else if (rsize > 0)
        			baos.write(buffer, 0, rsize);
        	}
        	return baos.toByteArray();
        } catch (FileNotFoundException e) {
            throw new BusinessException(e);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 지정된 파일을 삭제한다.
     * @param fullFilePath 파일 위치 문자열
     * @return true이면 제거 성공, false이면 실패
     */
    public static boolean deleteFile(String fullFilePath) {
        File file = new File(fullFilePath);
        boolean retn = false;
        try {
            if (file.exists()) {
                retn = file.delete();
            }
            else{
                log.debug("Given path's file do not exist. : " + fullFilePath);
            }
        } catch (SecurityException e) {
            throw new BusinessException(e);
        }
        return retn;
    }

    /**
     * 2022.01.10 CSLEE 추가
     * 지정된 결로가 디렉토리이면 삭제 처리 (하위 폴더 및 파일 모두 제거)
     * @param path
     * @return
     */
    public static boolean deleteDirectory(String path) {

        boolean retn = false;
        File clsFolder = new File( path );
        try {
            if(clsFolder.exists() && clsFolder.isDirectory()) {
                // 폴더에 포함된 모든 파일 및 폴더를 삭제한다.
                File [] arrFile = clsFolder.listFiles();
                
                if (arrFile == null) {
                	return retn;
                }
                
                for( int i = 0; i < arrFile.length; ++i ) {
                    if( arrFile[i].isDirectory() ) {
                        // 폴더이면 재귀적으로 호출한다.
                        deleteDirectory( arrFile[i].getPath() );
                    }
                    arrFile[i].delete();
                }
                retn = clsFolder.delete();
            }
        } catch (SecurityException e) {
            throw new BusinessException(e);
        }

        return retn;
    }

    /**
     * 파일이나 디렉터리 명을 "/"(슬래시)기반으로 변경하여 반환.
     *
     * @param path 변경할 패스 문자열
     * @return
     */
    public static String replacePathToSlash(String path){
        return (StringUtils.isBlank(path)) ?
                path :
                path.replaceAll("[\\\\]+", "/").replaceAll("[/]{2,}", "/");
    }

    /**
     * 파일 Disposition 지정하기.
     */
    public static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	String browser = getBrowser(request);
        String encodedFilename = encodeFileName(filename, request);
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename);

        if ("Opera".equals(browser)) {
            response.setContentType("application/octet-stream;charset=UTF-8");
        }
    }

    /**
     * 문자열 깨짐방지를 위한 인코딩된 파일명 반환
     * 2021.08.13 추가
     * @throws UnsupportedEncodingException
     */
    public static String encodeFileName(String filename, HttpServletRequest request) throws UnsupportedEncodingException {

        String browser = FileUtils.getBrowser(request);
        String encodedFilename = null;

        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else {
            throw new BusinessException("Not supported browser");
        }
        return encodedFilename;
    }

    /**
     * 브라우저 구분 얻기.
     */
    private static String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
            return "Trident";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }

    // 첨부파일 확장자 가능 체크
    public static boolean isAtthAllowedFileType(String fileNm, String allowedExtNms) {
        String extNm = fileNm.substring(fileNm.toLowerCase().lastIndexOf(".") + 1);

        extNm = extNm.toLowerCase();

        if (allowedExtNms.indexOf(extNm) >= 0){
            return true;
        }

        return false;
    }

    /**
     * 파일 확장자 반환
     */
    public static String getFileExt( final String fileName ) {
        int index = fileName.lastIndexOf(".");

        if (index == -1) {
            return "";
        }
        else {
            return fileName.substring(index+1, fileName.length());
        }
    }

    /**
     * 2023.01.31 파일 확장자를 제외한 명칭 반환
     */
    public static String getFileRemoveExt( final String fileName ) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex < 0) {
        	return fileName;
        }
        return fileName.substring(0, lastIndex);
    }
    
    // 2021.07.21 LSH 추가
    public static final String SEPARATOR = "/";

    // 2021.07.21 LSH 추가
	public static String mergePath(String path1, String path2) {

		if (path2 == null)
			return path1;

		path1 = replacePathToSlash(path1);
		path2 = replacePathToSlash(path2);

		String path = path1 + (path1.endsWith(SEPARATOR) ? "" : SEPARATOR) + path2;

		return replacePathToSlash(path);
	}


	/**
	 * 2021.08.03 LSH 파일 압축하기
	 * @param targets 압축시킬 폴더
	 * @param zipName 압축하여 생겨날 파일의 이름과 풀 경로
	 */
	public static void compress(List<FileInfo> targets, String zipName) {

		ZipArchiveOutputStream  zout = null;
		try {
			zout = new ZipArchiveOutputStream(new FileOutputStream(zipName));

			for (FileInfo target : targets) {
	            String fullName = FileUtils.mergePath(target.getFullPath(), target.getSaveName());
	            String fileName = target.getFileName();

				File f = new File(fullName);
				compress(zout, f, fileName);
			}
		} catch (IOException e) {
            throw new BusinessException(e);
        } finally {
			try {
				if (zout != null)
					zout.close();
			} catch (IOException e) {
				throw new BusinessException(e);
			}
		}
	}

	private static void compress(ZipArchiveOutputStream zout, File file, String fileName) {

		// 압축시 스트림 버퍼크기
		final int BUFFER = 1024;

		BufferedInputStream origin = null;

		try {
			byte data[] = new byte[BUFFER];

			origin = new BufferedInputStream(new FileInputStream(file), BUFFER);
			ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
			zout.putArchiveEntry(entry);

			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				zout.write(data, 0, count);
			}
			zout.closeArchiveEntry();
		}
		catch (IOException e) {
			throw new BusinessException(e);
		}
		finally {
			try {
				if (origin != null)
					origin.close();
			}
			catch (IOException e) {
				throw new BusinessException(e);
			}
		}
	}

	/**
	 * 2023.01.17 LSH
	 * Sign 파일을 저장한다.
	 */
	public static void saveSignFile(FileDirectory fd, String fileName, String signData) {
		try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(fd.getRealName(fileName)), Base64.getDecoder().decode(signData));
		} catch (IOException e) {
            throw new BusinessException(e);
        }
	}

	/**
	 * 2021.11.25 LSH
	 * 문서/미디어 웹 URL 링크용 HttpEntity 반환
	 * @param fullName  물리적경로 전체를 포함한 파일명
	 */
	public static HttpEntity<byte[]> getMediaEntity(String fullName) throws IOException {

        // 파일 확장자
        String extension = FileUtils.getFileExt(fullName);

        byte[] model = org.apache.commons.io.FileUtils.readFileToByteArray(new File(fullName));
        
        if (model != null) {
        	HttpHeaders headers = new HttpHeaders();
            headers.setContentType(FileDocument.getContentMediaType(extension));
            headers.setContentLength(model.length);
            return new HttpEntity<byte[]>(model, headers);
        } else {
        	return new HttpEntity<byte[]>(null, new HttpHeaders());
        }
	}

	/**
	 * 2021.12.08 CSLEE
	 * 파일명으로 사용할 수 없는 문자 치환
	 * @param fileName
	 * @return
	 */
	public static String convertInvalidFileName(String fileName) {
	    // 윈도우 파일명으로 사용할수 없는 문자
	    // CSLEE - (','와' ')의 경우에 다운로드문제가 발행하여 (','와' ')도 '_'로 변환 (원래 파일명에는 사용할 수 있음)
	    String[] invalidName = {"\\\\","/",":","[*]","[?]","\"","<",">","[|]","[,]", " "};

	    for(int i=0;i<invalidName.length;i++)
	        fileName = fileName.replaceAll(invalidName[i], "_"); // 언더바로 치환

	    return fileName;
	}
	
	/**
	 * 2022.02.09 LSH 파일이 존재하는지 확인
	 */
	public static boolean existFile(String fullName) {
		File f = new File(fullName);
		if (f != null &&
			f.exists() && 
			f.isFile())
			return true;
		return false;
	}
	
	/**
	  * 2022.10.31 JWH
	  * 새로운 파일 생성 및 내용 추가
	  * @param		fileName, str
	  * @returns	true or false
	  */
	public static boolean writeFile(String fileName, String str){
		if(fileName==null || str==null){
			return false;
		}else{
			boolean existFile = existFile(fileName);
			if(!existFile){
				// 파일 생성
				createNewFile(fileName);
			}
			// 내용 작성
			writeTextFile(fileName, str);
			
			return existFile;
		}
	}
	
	/**
	  * 2022.10.31 JWH
	  * 새로운 파일 생성 및 내용 추가
	  * @param		fileName, str
	  * @returns	true or false
	  */
	public static boolean createNewFile(String fileName, String str){
		if(fileName==null || str==null){
			return false;
		}else{
			
			if(!existFile(fileName)) {
				// 파일 삭제
				deleteFile(fileName);
			}
			// 파일 생성&내용추가
			if(createNewFile(fileName)){
				// 내용 작성
				writeTextFile(fileName, str);
			}
			return existFile(fileName);
		}
	}
	
	/**
	  * 2022.10.31 JWH
	  * 새로운 파일(디렉토리가 없으면 생성) 생성
	  * @param		fileName
	  * @returns	true or false
	  */
	public static boolean createNewFile(String fileName)
	{
		if(fileName==null){
			return false;
		}else{
			try {
				File file = new File(fileName);
				String replacedPath = replacePathToSlash(fileName);
				makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));
				file.createNewFile();
				return true;
			}catch(SecurityException se) {
				log.debug("SecurityException : {fileName:"+fileName+"} " + se.getMessage());
				return false;
			}catch(IOException ioe) {
				log.debug("IOException : {fileName:"+fileName+"} " + ioe.getMessage());
				return false;
			}catch(Exception e) {
				log.debug("Exception : {fileName:"+fileName+"} " + e.getMessage());
				return false;
			}
		}
	}
	
	/**
	  * 2022.10.31 JWH
	  * 문자열을 텍스트 파일에 기록
	  * @param		fileName 파일 이름
	  * @param		contents 기록할 내용
	  * @returns	성공 여부
	  */
	public static boolean writeTextFile(String fileName, String contents)
	{
		return writeTextFile(fileName, contents, ENCODING);
	}
	public static boolean writeTextFile(String fileName, String contents, String encoding)
	{
		if(fileName==null || contents==null || encoding==null){
			return false;
		}else{
			try (FileOutputStream fs = new FileOutputStream(fileName, true);
					OutputStreamWriter os = new OutputStreamWriter(fs,encoding);
					BufferedWriter bw = new BufferedWriter(os);){
				bw.write(contents);
				bw.flush();
			}catch(IOException ioe) {
				log.debug("IOException : {fileName:"+fileName+",contents:"+contents+",encoding:"+encoding+"} " + ioe.getMessage());
				return false;
			}catch(Exception e) {
				log.debug("Exception : {fileName:"+fileName+",contents:"+contents+",encoding:"+encoding+"} " + e.getMessage());
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 2023.03.13 LSH
	 * XDocReport에서 사용할 ImageProvider 반환 함수
	 * 
	 * @param imagePath 이미지가 위치한 물리적 경로
	 * @param imageName 이미지파일명
	 * @return 
	 */
	public static IImageProvider getImageProvider(String imagePath, String imageName) {
		
		File f = new File(FileUtils.mergePath(imagePath, imageName));
		
		if (f.exists()) {
			return new FileImageProvider(f);
		}
		return null;
	}
	
	/**
	 * 2023.06.26 ntarget
	 * fileFilter 처리
	 * @param targetDirName	파일경로
	 * @param fileExt		대상 확장자
	 * @return
	 */
	public static File[] getFileNames(String targetDirName, String fileExt) {
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
}