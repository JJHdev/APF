package common.file;

import business.com.CommConst;
import common.util.CommUtils;
import common.util.FileUtils;
import common.util.properties.ApplicationProperty;

public enum FileDirectory {

	  ROOT           ( ""             , "ROOT"         , null           , "기본경로")
	, TEST           ( "/test"        , "TEST"         , null           , "테스트경로")    // 개발샘플용
	, EXCEL          ( "/excel"       , "EXCEL"        , null           , "엑셀로드경로")  // 개발샘플용
	, DOCUMENT       ( "/document"    , "DOCU"         , null           , "문서경로")      // 개발샘플용
	, SAMPLE         ( "/sample"      , "SAMPLE"       , null           , "샘플파일경로")  // 개발샘플용
	, BBS            ( "/bbs"         , "BBS"          , null           , "게시판파일경로")
	, BIZ            ( "/biz"         , "BIZ"          , null           , "업무파일경로")
	, ENT            ( "/ent"         , "ENT"          , null           , "업체파일경로")
	, PAPER          ( "/paper"       , "PAPE"         , null           , "신청파일경로")
	, FORMFILE       ( "/formfile"    , "FORMFILE"     , null           , "양식파일경로")
	, CKIMAGE        ( "ckimage"      , "CKIMAGE"      , null           , "CKEDITOR이미지경로")
	, THYMELEAF      ( "/thymeleaf"   , "TLIMAGE"      , null           , "메일템플릿이미지경로")
	;
	
	private String path;
    private String type;
    private String code;
	private String name;
	
	private FileDirectory(String path, String type, String code, String name) {
		this.path = path;
		this.type = type;
		this.code = code;
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public String getPath() {
		return path;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	
	public String getTempDir() {
		return ApplicationProperty.get("upload.temp.dir");
	}
	public String getRealDir() {
		return ApplicationProperty.get("upload.real.dir");
	}
	public String getRealPath() {
		return FileUtils.mergePath(getRealDir(), path);
	}
	public String getRealPath(String filePath) {
		return FileUtils.mergePath(getRealDir(), filePath);
	}
	public String getTempName(String fileName) {
		return FileUtils.mergePath(getTempDir(), fileName);
	}
	public String getRealName(String fileName) {
		return FileUtils.mergePath(getRealPath(path), fileName);
	}
	public String getRealName(String filePath, String fileName) {
		return FileUtils.mergePath(getRealPath(filePath), fileName);
	}
	// 2022.01.11 LSH 임시경로의 현재날짜 경로 반환 ( "~~/temp/20220101/" )
	public String getTempDateDir() {
		return FileUtils.mergePath(getTempDir(), CommUtils.getCurDateString()+FileUtils.SEPARATOR);
	}
	// 2021.11.26 LSH 삭제파일경로로 파일 이동 처리
	public void moveToRemoved(String filePath, String fileName) throws Exception {
		// 실제경로에 저장된 파일의 파일명포함 전체 경로
		String realName = getRealName(filePath, fileName);
		// 삭제경로에 이동할 파일의 파일명포함 전체 경로
		String deltName = getRealName(CommConst.REMOVE_PATH+filePath, fileName);
		
		// 2022.02.09 LSH 파일이 있을 경우에만 삭제처리가 진행되도록 수정함.
		if (FileUtils.existFile(realName)) {
			// 실제 경로에 저장된 파일을 삭제된파일 저장경로로 이동처리한다.
			FileUtils.moveFile(realName, deltName);
		}
	}
	
	public static FileDirectory get(String type) {
		if (type == null)
			return null;
		for (FileDirectory c : values()) {
			if (type.equals(c.getType()))
				return c;
		}
		return null;
	}
	// 코드에 따른 경로정보 반환
	public static FileDirectory getByCode(String code) {
		if (code == null)
			return null;
		for (FileDirectory c : values()) {
			if (code.equals(c.getCode()))
				return c;
		}
		return null;
	}
}
