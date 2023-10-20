package common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import business.com.CommConst;
import commf.exception.BusinessException;
import common.util.CommUtils;
import common.util.FileUtils;
import common.util.properties.ApplicationProperty;
import egovframework.com.cmm.EgovMessageSource;

/**
 * Program Name 	: FileManager
 * Description 		: Common File Management
 * Programmer Name 	: ntarget
 * Creation Date 	: 2021-02-08
 * Used Table 		:
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class FileManager {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource(name="message")
    protected EgovMessageSource message;

    /**
     * 첨부파일 업로드
     * 첨부파일 FORM Name : upfile로사용. ex) upfile1, upfile2 로 넘겨준다.
     * -- file[] 처럼 배열로 사용할경우 EgovFileMngUtil.java 참조.
     * 저장 디렉토리는 기본값을 사용
     *
     * @param request
     * @return
     * @throws IOException
     */
    public List<FileInfo> multiFileUpload(HttpServletRequest request) throws IOException {
        return multiFileUploadDetail(request, null);
    }

    /**
     * 첨부파일 업로드
     * 첨부파일 FORM Name : upfile로사용. ex) upfile1, upfile2 로 넘겨준다.
     * -- file[] 처럼 배열로 사용할경우 EgovFileMngUtil.java 참조.
     *
     * @param request
     * @param saveDir  파일 저장 디렉토리
     * @return
     * @throws IOException
     */
    public List<FileInfo> multiFileUpload(HttpServletRequest request, String saveDir) throws IOException {
        return multiFileUploadDetail(request, saveDir);
    }

    /**
     * 첨부파일 FORM 명을 원하는 대로 구성하여 처리 (단 name은 모두 달라야 함)
     * 저장 디렉토리는 기본값을 사용
     * @param request
     * @return
     * @throws IOException
     */
    public List<FileInfo> multiFileUploadArray(HttpServletRequest request) throws IOException {
        return multiFileUploadArrayDetail(request, null);
    }

    /**
     * 첨부파일 FORM 명을 원하는 대로 구성하여 처리 (단 name은 모두 달라야 함)
     * @param request
     * @param saveDir
     * @return
     * @throws IOException
     */
    public List<FileInfo> multiFileUploadArray(HttpServletRequest request, String saveDir) throws IOException {
        return multiFileUploadArrayDetail(request, saveDir);
    }

    /**
     * 2021.07.21 LSH 추가
     * 동일한 명칭(upfile)의 배열로 넘어오는 다중파일 업로드
     *
     * @param request
     * @param saveDir  파일 저장 디렉토리
     * @return
     * @throws Exception
     */
    public List<FileInfo> multiFileUploadByName(HttpServletRequest request, String saveDir) throws Exception {

        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch(ClassCastException e) {
            // ClassCastException이면 첨부파일 내용이 없는 것으로 확인하여 빈 객체를 리턴
            return new ArrayList<FileInfo>();
        }
    	return multiFileUploadDetailByName(request, multipartRequest, saveDir);
    }

    /**
     * 2023.07.10 LSH 추가
     * 동일한 명칭(upfile)의 배열로 넘어오는 다중파일 업로드
     * 
     * 부가정보의 업데이트가 필요한 경우에 사용한다.
     * 즉, 첨부된 파일이 없어도 부가정보가 있는 경우 해당 데이터가 담긴 파일객체가 반환된다.
     */
    public List<FileInfo> multiFileUploadAdditional(HttpServletRequest request, String saveDir) throws Exception {

        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch (ClassCastException e) {
        	logger.error("multiFileUploadAdditional Exception :: ", e);
        }
    	return multiFileUploadDetailByName(request, multipartRequest, saveDir);
    }

    /**
     * 2021.07.21 LSH 추가
     * 동일한 명칭(upfile)의 배열로 넘어오는 다중파일 업로드를 처리한다.
     * @throws Exception
     */
    private List<FileInfo> multiFileUploadDetailByName(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, String saveDir) throws Exception {

        List<FileInfo> listFile = new ArrayList<FileInfo>();

		// 선택항목
		String[] docuCds   = request.getParameterValues("docuCd");   // 연결된 문서타입
		String[] docuNos   = request.getParameterValues("docuNo");   // 연결된 문서번호
		String[] docuSeqs  = request.getParameterValues("docuSeq");  // 연결된 문서순번(있을경우에만)
		String[] fileNos   = request.getParameterValues("fileNo");   // 기저장된 파일번호(수정일때만)

		String[] fileSes   = request.getParameterValues("fileSe");   // 연결된 정보1
		String[] papeCds   = request.getParameterValues("papeCd");   // 연결된 정보2
		String[] sttsCds   = request.getParameterValues("sttsCd");   // 연결된 정보3
		String[] docuCns   = request.getParameterValues("docuCn");   // 연결된 정보4
		String[] docuYns   = request.getParameterValues("docuYn");   // 연결된 정보5

		// 필수항목
		String[] fileTypes = request.getParameterValues("fileType"); // 파일타입
		String[] fileNames = request.getParameterValues("fileName"); // 파일명
		String[] filePaths = request.getParameterValues("filePath"); // 파일경로 (ROOT제외)
		String[] needYns   = request.getParameterValues("needYn");   // 파일필수체크여부 (Y/N)
		String[] fileYns   = request.getParameterValues("fileYn");   // 파일업로드여부 (Y/N)

		List<MultipartFile> inFiles = null;
		
		if (multipartRequest != null)
			inFiles = multipartRequest.getFiles("upfile");

        // 임시 업로드 경로 생성
        String tempDir = saveDir;
        if (tempDir == null || tempDir.trim().length() == 0) {
            tempDir = ApplicationProperty.get("upload.temp.dir");
        }
        // 디렉토리 생성
        FileUtils.makeDirectories(tempDir);

		int index = 0;
		int i = 0;
		
		if (fileTypes == null)
			return null;

        for (String fileType : fileTypes) {

			FileInfo f = FileInfo.builder()
					.fileType (fileType)
					.filePath (filePaths[i])
					.fileName (fileNames[i])
					.needYn   (needYns[i])
					.fileYn   (fileYns[i])
					.fileIdx  (i+1)
					.build();
			
			if (fileNos   != null && fileNos.length   > i) f.setFileNo(fileNos[i]);
			if (docuCds   != null && docuCds.length   > i) f.setDocuCd(docuCds[i]);
			if (docuNos   != null && docuNos.length   > i) f.setDocuNo(docuNos[i]);
			if (docuSeqs  != null && docuSeqs.length  > i) f.setDocuSeq(docuSeqs[i]);

			if (fileSes   != null && fileSes.length   > i) f.setFileSe(fileSes[i]);
			if (papeCds   != null && papeCds.length   > i) f.setPapeCd(papeCds[i]);
			if (sttsCds   != null && sttsCds.length   > i) f.setSttsCd(sttsCds[i]);
			if (docuCns   != null && docuCns.length   > i) f.setDocuCn(docuCns[i]);
			if (docuYns   != null && docuYns.length   > i) f.setDocuYn(docuYns[i]);
			
			i++;

			// 파일이 첨부된 경우
			if (inFiles != null &&
				"Y".equals(f.getFileYn())) {
				MultipartFile inFile = inFiles.get(index);

	            // 파일을 디렉토리에 저장후 파일정보 리턴
	            FileInfo fileInfo = getUploadFileInfo(inFile, tempDir, i);

	            // 화면에서 넘어온 부가정보 맵핑
	            fileInfo.setFileType(f.getFileType());
	            fileInfo.setDocuCd  (f.getDocuCd());
	            fileInfo.setDocuNo  (f.getDocuNo());
	            fileInfo.setDocuSeq (f.getDocuSeq());
	            fileInfo.setNeedYn  (f.getNeedYn());
	            fileInfo.setFileYn  (f.getFileYn());
	            fileInfo.setFileIdx (f.getFileIdx());
	            fileInfo.setFileNo  (f.getFileNo()); // 이전파일정보
	            
				fileInfo.setFileSe  (f.getFileSe());
				fileInfo.setPapeCd  (f.getPapeCd());
				fileInfo.setSttsCd  (f.getSttsCd());
				fileInfo.setDocuCn  (f.getDocuCn());
				fileInfo.setDocuYn  (f.getDocuYn());

	            listFile.add(fileInfo);
				index++;
			}
			// 파일이 첨부되지 않은 경우
			else {
				listFile.add(f);
			}
        }
        return listFile;
    }

    /**
     * 첨부파일 업로드
     * 첨부파일 FORM Name : upfile로사용. ex) upfile1, upfile2 로 넘겨준다.
     * -- file[] 처럼 배열로 사용할경우 EgovFileMngUtil.java 참조.
     */
    private List<FileInfo> multiFileUploadDetail(HttpServletRequest request, String saveDir) throws IOException {
        List listFile = new ArrayList();

        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch(ClassCastException e) {
            // ClassCastException이면 첨부파일 내용이 없는 것으로 확인하여 빈 객체를 리턴
            return listFile;
        }
        Map<String, MultipartFile> files = multipartRequest.getFileMap();

        String tempDir = saveDir;
        if(tempDir == null || tempDir.trim().length() == 0) {
            tempDir = ApplicationProperty.get("upload.temp.dir");
        }

        //디렉토리 생성
        FileUtils.makeDirectories(tempDir);

        // 파일이름이 중복되면 spring에서 에러가 나므로 각각 다른 이름으로 받음.
        for (int i = 0; i < files.size(); i++) {
            String upfileNm 		= "upfile" + i;
            MultipartFile inFile 	= files.get(upfileNm);

            // 파일을 디렉토리에 저장후 파일정보 리턴
            FileInfo fileInfo = getUploadFileInfo(inFile, tempDir, i);
            listFile.add(fileInfo);
        }

        return listFile;
    }

    /**
     * 첨부파일 FORM 명을 원하는 대로 구성하여 처리 (단 name은 모두 달라야 함)
     * @param request
     * @return
     * @throws IOException
     */
    private List<FileInfo> multiFileUploadArrayDetail(HttpServletRequest request, String saveDir) throws IOException {
        List listFile = new ArrayList();
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch(ClassCastException e) {
            // ClassCastException이면 첨부파일 내용이 없는 것으로 확인하여 빈 객체를 리턴
            return listFile;
        }

        String tempDir = saveDir;
        if(tempDir == null || tempDir.trim().length() == 0) {
            tempDir = ApplicationProperty.get("upload.temp.dir");
        }

        //디렉토리 생성
        FileUtils.makeDirectories(tempDir);

        Iterator<String> iterator = multipartRequest.getFileNames();

        int i= 0;
        while(iterator.hasNext()) {
            MultipartFile inFile = multipartRequest.getFile(iterator.next());
            // 파일을 디렉토리에 저장후 파일정보 리턴
            FileInfo fileInfo = getUploadFileInfo(inFile, tempDir, i);
            listFile.add(fileInfo);
            i++;
        }

        return listFile;
    }

    /**
     * Multipart에서 파일 1개에 대해 지정된 디렉토리에 저장후 해당 파일 정보를 Map으로 구성하여 리턴
     * @param inFile
     * @param saveDir
     * @param index
     * @return
     */
    private FileInfo getUploadFileInfo(MultipartFile inFile, String saveDir, int index) {

        try {
        	// 2022.01.08 LSH 파일명에 허용되지 않는 문자를 제거하기 위해 추가함
            // 실제 파일명칭
            String realName = FileUtils.convertInvalidFileName(CommUtils.nvlTrim(inFile.getOriginalFilename()));
        	// 저장할 파일명칭
            String saveName = getFileName(saveDir, realName);

        	if (CommUtils.isEmpty(saveName))
        		return null;
            // 파일을 폴더에 저장함
        	// 해당 함수에서 close 처리가 이루어지므로 현단계에 close 처리가 필요없음
            FileUtils.copyFile(inFile.getInputStream(), new FileOutputStream(saveDir + saveName));

            return FileInfo.builder()
            		.saveName(saveName)
					.fileName(realName)
					.filePath(saveDir)
					.fileExt(FileUtils.getFileExt(saveName))
					.fileSize(inFile.getSize())
					.contType(inFile.getContentType())
					.fileIdx(index)
					.build();

        } catch (FileNotFoundException e) {
            throw new BusinessException(e);
        } catch (IOException e) {
            throw new BusinessException(e);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
    
    /**
     * Multipart에서 파일 1개에 대해 지정된 디렉토리에 저장후 해당 파일 정보를 Map으로 구성하여 리턴
     * @param inFile
     * @param saveDir
     * @param index
     * @return
     */
    private FileInfo getUploadPapeFileInfo(MultipartFile inFile, String saveDir, int index) {

        try {
        	// 2022.01.08 LSH 파일명에 허용되지 않는 문자를 제거하기 위해 추가함
            // 실제 파일명칭
            String realName = FileUtils.convertInvalidFileName(CommUtils.nvlTrim(inFile.getOriginalFilename()));
        	// 저장할 파일명칭
            String saveName = getFileName(saveDir, realName);

        	if (CommUtils.isEmpty(saveName))
        		return null;
            // 파일을 폴더에 저장함
        	// 해당 함수에서 close 처리가 이루어지므로 현단계에 close 처리가 필요없음
            FileUtils.copyFile(inFile.getInputStream(), new FileOutputStream(saveDir + saveName));

            return FileInfo.builder()
            		.saveName(saveName)
					.fileName(realName)
					.filePath(saveDir)
					.fileExt(FileUtils.getFileExt(saveName))
					.fileSize(inFile.getSize())
					.contType(inFile.getContentType())
					.fileIdx(index)
					.build();

        } catch (FileNotFoundException e) {
            throw new BusinessException(e);
        } catch (IOException e) {
            throw new BusinessException(e);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

	//첨부파일 실제경로로 이동처리
	public static void moveFile(String saveName, FileDirectory d) throws Exception {

		String tempFile = d.getTempName(saveName);
		String realFile = d.getRealName(saveName);

		//파일 이동 처리 (overwrite)
		FileUtils.moveFile(tempFile, realFile);
	}

	//첨부파일 실제경로로 이동처리 (SUBDIRECTORY 인자 추가
	public static void moveFile(String saveName, FileDirectory d, String subDir) throws Exception {

		String tempFile = d.getTempName(saveName);
		String realFile = d.getRealName(subDir, saveName);

		//파일 이동 처리 (overwrite)
		FileUtils.moveFile(tempFile, realFile);
	}

    /**
     * 서버에 저장될 파일명 생성 가져오기
     */
    public String getFileName(String dir, String originalFileName) {
        if (CommUtils.nvlTrim(originalFileName).equals(""))
            return "";

        String dotextension = originalFileName.substring(originalFileName.lastIndexOf("."));
        File currentPath = new File(dir);
        String[] fileList = null;

        SecureRandom random = new SecureRandom();
        FileNameFilter fileNameFilter = new FileNameFilter();

        // 감리조치
        StringBuffer sb = new StringBuffer();
        do {
            sb = new StringBuffer();
            sb.append(String.valueOf(System.currentTimeMillis()));
            sb.append(String.valueOf(random.nextLong()));
            sb.append(dotextension);
            fileNameFilter.setFileName(sb.toString());

            fileList = currentPath.list(fileNameFilter);
        } while (fileList.length > 0);

        return sb.toString();
    }

    static class FileNameFilter implements FilenameFilter {
        String sFileName = null;

        public void setFileName(String sFileName) {
            this.sFileName = sFileName;
        }

        public boolean accept(java.io.File directory, String name) {
            if (name.equals(sFileName)) {
                return true;
            }
            return false;
        }
    }

    /**
     * 파일 다운로드 처리 (파일정보를 map 객체로 전달받아 처리)
     */
    public void procFileDownload(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		FileInfo fileInfo
    	) throws Exception {

		if (fileInfo == null) {
            logger.info("### FILE DOWNLOAD ERROR : Not server File.");
            throw new BusinessException(message.getMessage("exception.notExistAttachFile"));
		}
    	String saveName = fileInfo.getSaveName();
        String fullPath = fileInfo.getFullPath();
        // 다운로드 파일 전체경로
        fileInfo.setFullFile( FileUtils.mergePath(fullPath, saveName) );

    	String fullFile = fileInfo.getFullFile();
        String fileName = fileInfo.getFileName();

        // 파일명 orgFileNm의 이름으로 다운로드 함
        File f = new File(fullFile);

        if(f.exists()) {
            logger.info("response charset : " +  response.getCharacterEncoding());
            
            String mimetype = "application/x-msdownload";
            response.setContentType(mimetype);

            // 파일명 인코딩
            FileUtils.setDisposition(fileName, request, response);

            byte[] buffer = new byte[1024];

            BufferedInputStream ins = new BufferedInputStream(new FileInputStream(f));
    		BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try {
                int read = 0;
                while((read = ins.read(buffer)) != -1) {
                	if (read > 0)
                		outs.write(buffer, 0, read);
                }
            }catch(IOException e) {
                //logger.info("### FILE DOWNLOAD ERROR");
                logger.error("### FILE DOWNLOAD ERROR", e);
            }finally {
                if(outs!=null) outs.close();
                if(ins!=null) ins.close();
        		// 2022.01.12 삭제 대상이면 복호화된 파일 삭제 처리
        		if (fileInfo.isDeleted()) {
        		    // 해당 파일 삭제
        			FileUtils.deleteFile(fileInfo.getFullFile());
        		}
            }
        }
        else {
            logger.info("### FILE DOWNLOAD ERROR - FILE NOT FOUND");
            throw new BusinessException(message.getMessage("error.file.notExist")+"("+fileName+")");
        }
    }

	/**
	 * 2021.08.03 LSH 파일 압축 다운로드 처리
	 * 단일 파일일 경우 그대로 다운로드 처리
	 */
    public void procFileZipDownload(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		List<FileInfo> files,
    		String prefix
    	) throws Exception {
    	if (files == null ||
    		files.size() == 0)
    		return;

    	// 단일 파일인 경우
    	if (files.size() == 1) {
    		procFileDownload(request, response, files.get(0));
    		return;
    	}
    	// 2022.01.11 압축파일 저장 경로 (temp/[현재날짜])
    	String zipPath = FileDirectory.ROOT.getTempDateDir();
		String zipName = prefix+CommUtils.getCurrDateTime2()+".zip";
		String zipFile = FileUtils.mergePath(zipPath, zipName);
		
		// 파일경로가 없을 경우 생성한다.
		FileUtils.makeDirectories(zipPath);

		//압축하기
		FileUtils.compress(files, zipFile);

		FileInfo fileInfo = FileInfo.builder()
				.saveName(zipName)
				.fileName(zipName)
				.fullPath(zipPath)
				.deleted (true) // 2022.01.12 압축파일 다운로드 후 삭제여부 정의
				.build();

		// 압축파일 다운로드
		procFileDownload(request, response, fileInfo);
    }

    /**
     * 2022.01.10 LSH 공통 미디어 URL 링크보기
     * - 이미지 파일 등에 사용한다.
     * 
     * 2023.07.11 LSH 이미지 표시에 사용되므로 Exception은 로그만 남기고 처리한다.
     *
     * @param filePath 물리적파일 경로
     * @param fileName 물리적파일 명칭
     * @param decryption 복호화대상여부
     * @return
     */
    public HttpEntity<byte[]> linkFile(
    		FileInfo fileInfo,
    		HttpServletRequest request,
    		HttpServletResponse response
    	) {

    	if (fileInfo == null) {
            logger.info("### FILE PREVIEW ERROR [Media] : Not server File.");
            return null;
		}
    	try {
        	String saveName = fileInfo.getSaveName();
            String fullPath = fileInfo.getFullPath();
            
            // 다운로드 파일 전체경로
            fileInfo.setFullFile( FileUtils.mergePath(fullPath, saveName) );

            // 문서/미디어 웹 URL 링크용 HttpEntity 반환
            return FileUtils.getMediaEntity(fileInfo.getFullFile());
    	} catch (NullPointerException e) {
    		logger.error(e.getMessage());
    		return null;
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return null;
    	}
    }

    /**
     * 2022.01.10 LSH 공통 문서 미리보기용 URL 링크보기
     * - PDF / HWP 등의 플러그인을 이용한 문서파일에 사용한다.
     *   (Header에 Content Range 포함)
     *
     * @param filePath 물리적파일 경로
     * @param fileName 물리적파일 명칭
     * @param decryption 복호화대상여부
     * @param request
     * @param response
     * @throws Exception
     */
    public void linkDoc(
    		FileInfo fileInfo,
    		HttpServletRequest request,
    		HttpServletResponse response
    	) throws Exception {

		if (fileInfo == null) {
            logger.info("### FILE PREVIEW ERROR [Document] : Not server File.");
            throw new BusinessException(message.getMessage("exception.notExistAttachFile"));
		}

    	try {
        	String saveName = fileInfo.getSaveName();
            String fullPath = fileInfo.getFullPath();
            // 다운로드 파일 전체경로
            fileInfo.setFullFile( FileUtils.mergePath(fullPath, saveName) );

            // 파일 스트리밍
            MultipartFileSender.fromPath(new File(fileInfo.getFullFile()).toPath())
                    .with(request)
                    .with(response)
                    .serveResource();
    	}
    	finally {
    		// 2022.01.12 삭제 대상이면 복호화된 파일 삭제 처리
    		if (fileInfo != null &&
    			fileInfo.isDeleted()) {
    		    // 해당 파일 삭제
                FileUtils.deleteFile(fileInfo.getFullFile());
    		}
    	}
    }
    
    /**
     * 파일삭제 처리
     * - 삭제경로로 백업파일 이동후 삭제
     */
    public void removeFile(FileDirectory d, FileInfo f) throws Exception {
    	
    	// 삭제할 파일을 백업경로에 복사
    	String orgnName = backupFile(d, f);
		// 실제 파일 삭제
		FileUtils.deleteFile(orgnName);
    }
    
    /**
     * 삭제할 파일을 백업경로로 복사 처리
     */
    public String backupFile(FileDirectory d, FileInfo f) throws Exception {
		// 실제 파일
		String orgnName = d.getRealName(f.getFilePath(), f.getSaveName());
		// 삭제된 파일 저장 경로
		String trgtPath = FileUtils.mergePath(CommConst.REMOVE_PATH, f.getFilePath());
		// 전체 경로를 포함한 실제 파일
		String trgtName = d.getRealName(trgtPath, f.getSaveName());
		if (FileUtils.existFile(orgnName)) {
			// 물리적 파일을 removed 경로에 복사한다.
			FileUtils.copyFileWithDir(orgnName, trgtName, true);
		}
		// 원본파일 경로 반환
		return orgnName;
    }
    
    /**
     * 임시경로의 저장할 파일을 실제 경로로 복사 처리
     */
    public String copyFile(FileDirectory d, FileInfo f) throws Exception {
		// 저장파일명
		String strgNm   = f.getSaveName();
	    // 파일의 물리적 경로를 포함한 FULL NAME
		String orgnFile = d.getTempName(strgNm);
		String trgtFile = d.getRealName(f.getFilePath(), strgNm);
		if (FileUtils.existFile(orgnFile)) {
			// 물리적 파일을 실제 경로에 복사한다.
			FileUtils.copyFileWithDir(orgnFile, trgtFile, true);
		}
		// 임시파일정보 반환
		return orgnFile;
    }
    
    /**
     * 임시경로의 파일을 실제저장경로로 이동처리
     */
    public boolean saveFile(FileDirectory d, FileInfo f) throws Exception {
		// 파일이 첨부된 경우
		if ("Y".equals(f.getFileYn())) {
			// 첨부파일 실제경로로 이동처리
			moveFile(f.getSaveName(), d, f.getFilePath());
    		return true;
		}
		return false;
    }

    /**
     * 2021.08.04 JH 추가
     * 동일한 명칭(upfile)의 배열로 넘어오는 다중파일 을 서류코드 관리에 맞게 처리한다.
     *
     * @param request
     * @param saveDir  파일 저장 디렉토리
     * @return
     * @throws Exception
     */
    public List<FileInfo> multiFileUploadByPapeName(HttpServletRequest request, String saveDir) throws Exception {

        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch(ClassCastException e) {
            // ClassCastException이면 첨부파일 내용이 없는 것으로 확인하여 빈 객체를 리턴
            return new ArrayList<FileInfo>();
        }
    	return multiFileUploadDetailByPapeName(request, multipartRequest, saveDir);
    }
    
    /**
     * 2021.08.04 JH 추가
     * 동일한 명칭(upfile)의 배열로 넘어오는 다중파일 을 서류코드 관리에 맞게 처리한다.
     * @throws Exception
     */
    private List<FileInfo> multiFileUploadDetailByPapeName(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, String saveDir) throws Exception {

        List<FileInfo> listFile = new ArrayList<FileInfo>();
		// 선택항목
		String[] docuCds   = request.getParameterValues("docuCd");   // 연결된 문서타입
		String[] docuNos   = request.getParameterValues("docuNo");   // 연결된 문서번호
		String[] docuSeqs  = request.getParameterValues("docuSeq");  // 연결된 문서순번(있을경우에만)
		String[] fileNos   = request.getParameterValues("fileNo");   // 기저장된 파일번호(수정일때만)

		String[] fileSes   = request.getParameterValues("fileSe");   // 연결된 정보1
		String[] papeCds   = request.getParameterValues("papeCd");   // 연결된 정보2
		String[] sttsCds   = request.getParameterValues("sttsCd");   // 연결된 정보3
		String[] docuCns   = request.getParameterValues("docuCn");   // 연결된 정보4
		String[] docuYns   = request.getParameterValues("docuYn");   // 연결된 정보5

		// 필수항목
		String[] fileTypes = request.getParameterValues("fileType"); // 파일타입
		String[] fileNames = request.getParameterValues("fileName"); // 파일명
		String[] filePaths = request.getParameterValues("filePath"); // 파일경로 (ROOT제외)
		String[] needYns   = request.getParameterValues("needYn");   // 파일필수체크여부 (Y/N)
		String[] fileYns   = request.getParameterValues("fileYn");   // 파일업로드여부 (Y/N)

		List<MultipartFile> inFiles = null;
		
		if (multipartRequest != null)
			inFiles = multipartRequest.getFiles("upfile");

        // 임시 업로드 경로 생성
        String tempDir = saveDir;
        if (tempDir == null || tempDir.trim().length() == 0) {
            tempDir = ApplicationProperty.get("upload.temp.dir");
        }
        // 디렉토리 생성
        FileUtils.makeDirectories(tempDir);
		int index = 0;
		int i = 0;
		
		if (fileTypes == null)
			return null;

        for (String fileType : fileTypes) {

			FileInfo f = FileInfo.builder()
					.fileType (fileType)
					.filePath (filePaths[i])
					.fileName (fileNames[i])
					.needYn   (needYns[i])
					.fileYn   (fileYns[i])
					.fileIdx  (i+1)
					.build();
			
			if (fileNos   != null && fileNos.length   > i) f.setFileNo(fileNos[i]);
			if (docuCds   != null && docuCds.length   > i) f.setDocuCd(docuCds[i]);
			if (docuNos   != null && docuNos.length   > i) f.setDocuNo(docuNos[i]);
			if (docuSeqs  != null && docuSeqs.length  > i) f.setDocuSeq(docuSeqs[i]);

			if (fileSes   != null && fileSes.length   > i) f.setFileSe(fileSes[i]);
			if (papeCds   != null && papeCds.length   > i) f.setPapeCd(papeCds[i]);
			if (sttsCds   != null && sttsCds.length   > i) f.setSttsCd(sttsCds[i]);
			if (docuCns   != null && docuCns.length   > i) f.setDocuCn(docuCns[i]);
			if (docuYns   != null && docuYns.length   > i) f.setDocuYn(docuYns[i]);
			
			i++;
			// 파일이 첨부된 경우
			if (inFiles != null &&
				"Y".equals(f.getFileYn())) {
				MultipartFile inFile = inFiles.get(index);
				
				String dcmntCd = request.getParameter("dcmntCd");
				if (dcmntCd == null) {
					listFile.add(f);
					continue;
				}
				
				dcmntCd = dcmntCd.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", "");
				
				String savaNm = dcmntCd+'.'+FileUtils.getFileExt(request.getParameter("fileName"));
	            // 파일을 디렉토리에 저장후 파일정보 리턴
	            FileInfo fileInfo = getUploadFilePapeInfo(inFile, tempDir, i, savaNm);

	            // 화면에서 넘어온 부가정보 맵핑
	            fileInfo.setFileType(f.getFileType());
	            fileInfo.setDocuCd  (f.getDocuCd());
	            fileInfo.setDocuNo  (f.getDocuNo());
	            fileInfo.setDocuSeq (f.getDocuSeq());
	            fileInfo.setNeedYn  (f.getNeedYn());
	            fileInfo.setFileYn  (f.getFileYn());
	            fileInfo.setFileIdx (f.getFileIdx());
	            fileInfo.setFileNo  (f.getFileNo()); // 이전파일정보
	            
				fileInfo.setFileSe  (f.getFileSe());
				fileInfo.setPapeCd  (f.getPapeCd());
				fileInfo.setSttsCd  (f.getSttsCd());
				fileInfo.setDocuCn  (f.getDocuCn());
				fileInfo.setDocuYn  (f.getDocuYn());

	            listFile.add(fileInfo);
				index++;
			}
			// 파일이 첨부되지 않은 경우
			else {
				listFile.add(f);
			}
        }
        return listFile;
    }
    /**
     * Multipart에서 파일 1개에 대해 지정된 디렉토리에 저장후 해당 파일 정보를 Map으로 구성하여 리턴
     * @param inFile
     * @param saveDir
     * @param index
     * @return
     */
    private FileInfo getUploadFilePapeInfo(MultipartFile inFile, String saveDir, int index , String saveNm) {

        try {
        	// 2022.01.08 LSH 파일명에 허용되지 않는 문자를 제거하기 위해 추가함
            // 실제 파일명칭
            String realName = FileUtils.convertInvalidFileName(CommUtils.nvlTrim(inFile.getOriginalFilename()));
            // 저장할 파일명칭
            String saveName = saveNm;

        	if (CommUtils.isEmpty(saveName))
        		return null;
            // 파일을 폴더에 저장함
        	// 해당 함수에서 close 처리가 이루어지므로 현단계에 close 처리가 필요없음
            FileUtils.copyFile(inFile.getInputStream(), new FileOutputStream(saveDir + saveName));

            return FileInfo.builder()
            		.saveName(saveName)
					.fileName(realName)
					.filePath(saveDir)
					.fileExt(FileUtils.getFileExt(saveName))
					.fileSize(inFile.getSize())
					.contType(inFile.getContentType())
					.fileIdx(index)
					.build();

        } catch (FileNotFoundException e) {
            throw new BusinessException(e);
        } catch (IOException e) {
            throw new BusinessException(e);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}

