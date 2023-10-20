package business.com;

import common.file.FileDirectory;

/**
 * 다운로드 양식 파일 정보 클래스
 * 
 * @class   : CommFormFile
 * @author  : LSH
 * @since   : 2021.11.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 */
public enum CommFormFile {
	
	SPRT_NOTICE 		("SPRT_NOTICE"  , "투자후지원사업_안내문.pdf"   						, "sprt_notice.pdf"),
	SPRT_ULD    		("SPRT_ULD"     , "경영체데이터업로드_양식.xlsx"						, "sprtuld.xlsx"),
	ENT_PLAN    		("ENT_FILE_05"  , "사업계획서.pdf"              					, "ent_plan.pdf"),
	ENT_DLGT    		("ENT_FILE_03"  , "위임장.hwp"                 					, "ent_dlgt.hwp"),
	// 08.02 JH 추가 투자유치가이드 _ 지원사업 운영기준 양식
	APLY_SE_CROWD_INFO  ("FORM_FILE_01" , "2023년 농식품 크라우드펀딩 지원사업 운영기준(안).hwp" 	, "aply_se_crowd_info.hwp"),
	BUSIN_PLAN_STANDARDS("FORM_FILE_02" , "[붙임1]사업계획서 작성요령.pdf" 					, "business_plan.pdf"),
	// 23.08.17 LHB BI 파일 다운로드
	BI_AI				("BI_AI"		, "ASSIST_AI.zip"								, "ASSIST_AI.zip"),
	BI_PNG				("BI_PNG"		, "ASSIST_PNG.zip"								, "ASSIST_PNG.zip"),
	BI_JPG				("BI_JPG"		, "ASSIST_JPG.zip"								, "ASSIST_JPG.zip"),
	// 23.09.08 JB ROGO 파일 다운로드
	ROGO_AI				("FORM_FILE_03"		, "ROGO-AI.ai"									, "ROGO-AI.ai"),
	ROGO_PNG			("FORM_FILE_04"		, "ROGO-JPG.jpg"								, "ROGO-JPG.jpg"),
	ROGO_JPG			("FORM_FILE_05"		, "ROGO-PNG.png"								, "ROGO-PNG.png"),
	;

	private String formType;
	private String fileName;
	private String saveName;
	
	private CommFormFile(String formType, String fileName, String saveName) {
		this.formType = formType;
		this.fileName = fileName;
		this.saveName = saveName;
	}
	
	public static CommFormFile get(String formType) {
		for (CommFormFile cf : values()) {
			if (formType.equals(cf.getFormType()))
				return cf;
		}
		return null;
	}

	public String getFormType() {
		return formType;
	}

	public String getSaveName() {
		return saveName;
	}

	public String getFileName() {
		return fileName;
	}
	public String getFullPath() {
		return FileDirectory.FORMFILE.getRealPath();
	}
	
	public String getFullName() {
		return FileDirectory.FORMFILE.getRealName(saveName);
	}
	
}
