package business.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import common.file.ExcelReadHelper;
import common.file.ExcelReadInfo;
import common.util.CommUtils;

/**
 * 엑셀 로드 설정 클래스
 * 
 * @class   : CommExcel
 * @author  : LSH
 * @since   : 2021.11.30
 * @version : 1.0
 *
 *    수정일      수정자                 수정내용
 *  --------   --------    ---------------------------
 *  2021.11.30  LSH        세부의료비 산정결과 설정추가
 *  2023.07.06  LHB        정부지원사업 설정추가
 */
public enum CommExcel {

	SPRT ("정부지원사업", 0, 8, 8, 0, true, 
			new String[] {
				"crdnsBzentyNo"	, "bizNm"		, "bzentyNm"	, "brno"		, "bizYr"		,
				"artclNm1"		, "artclCn1"	, "artclNm2"	, "artclCn2"	, "artclNm3"	,
				"artclCn3"		, "artclNm4"	, "artclCn4"	, "directInptYn"
			},
			new String[] {
				"유관기관코드"	, "사업명"	, "기관명"	, "사업자등록번호"	, "사업연도"	,
				"항목명1"		, "항목내용1"	, "항목명2"	, "항목내용2"		, "항목명3"	,
				"항목내용3"	, "항목명4"	, "항목내용4"	, "강제업로드"
			},
			new boolean[] {	
				true	, true	, true	, true	, true	,
				false	, false	, false	, false	, false	, 
				false	, false	, false	, false
			},
			// 길이체크가 필요한 값만 0보다 큰값으로 설정
			new int[] {	
				2	, 100	, 100	, 12	, 4		,
				100	, 300	, 100	, 300	, 100	,
				300	, 100	, 300	, 1
			}
	)
	;
	private int sheetIdx;   // SHEET INDEX
	private int headerCnt;  // 헤더 행갯수
	private int startRno;   // 시작 행INDEX
	private int startCno;   // 시작 열INDEX
	private boolean allow;  // 포맷오류 허용여부
	private String[]  keys;    // 데이터 칼럼 KEY 배열
	private String[]  names;   // 데이터 칼럼 명칭 배열
	private boolean[] needs;   // 데이터 칼럼 필수여부 배열
	private int[]     maxlens; // 데이터 칼럼 최대길이 배열
	private String[]  types;   // 데이터 칼럼 타입 배열
	private String remark;
	
	private CommExcel(String remark, int sheetIdx, int headerCnt,
			int startRno, int startCno, boolean allow,
			String[] keys, String[] names, 
			boolean[] needs, int[] maxlens) {

		this.remark    = remark;
		this.sheetIdx  = sheetIdx;
		this.headerCnt = headerCnt;
		this.startRno  = startRno;
		this.startCno  = startCno;
		this.allow     = allow;
		this.keys      = keys;
		this.names     = names;
		this.needs     = needs;
		this.maxlens   = maxlens;
	}
	
	private CommExcel(String remark, int sheetIdx, int headerCnt,
			int startRno, int startCno, boolean allow,
			String[] keys, String[] names, 
			boolean[] needs, int[] maxlens,
			String[] types) {

		this.remark    = remark;
		this.sheetIdx  = sheetIdx;
		this.headerCnt = headerCnt;
		this.startRno  = startRno;
		this.startCno  = startCno;
		this.allow     = allow;
		this.keys      = keys;
		this.names     = names;
		this.needs     = needs;
		this.maxlens   = maxlens;
		this.types     = types;
	}

	public List<String> getMappingKeys() {
		return Arrays.asList(keys);
	}

	@SuppressWarnings("rawtypes")
	public List<Map> parseData(String fileName) throws Exception {

		List<String> mappingKeys = getMappingKeys(); // 데이터 칼럼 KEY 목록
	    //-------------------------
	    // 엑셀 파싱
	    //-------------------------
	    return new ExcelReadHelper(
	        new ExcelReadInfo()
	        	.setSheetIndex           (sheetIdx)    // SHEET INDEX 설정 (default : -1)
	            .setHeaderRowCount       (headerCnt)   // header row 개수 설정 (default : 1)
	            .setStartRowIndex        (startRno)    // 데이터 시작 행 INDEX 설정 (default : 1)
	            .setStartColIndex        (startCno)    // 데이터 시작 열 INDEX 설정 (default : 0)
	            .setCellMappingKeys      (mappingKeys) // 엑셀 mapping 키 리스트 설정
	            .setFileFullPathName     (fileName)    // 엑셀 파일 경로 설정
	            .setAllowExcelFormatError(allow)       // Format 오류 허용 여부 (내용이 다 들어가지 않아도 처리될 수있게 허용)
	    ).excelToDataList();
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> validateErrors(Map data) {
		
		List<String> errors = new ArrayList<String> ();
		int i = 0;
		for (String key : keys) {
			String value = (String)data.get(key);
			boolean need = needs[i];
			String  name = names[i];
			// 필수여부 체크
			if (need && CommUtils.isEmpty(value)) {
				errors.add(name);
			}
			i++;
		}
		if (errors.size() == 0)
			return null;
		return errors;
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> validateLengthErrors(Map data) {
		
		List<String> errors = new ArrayList<String> ();
		int i = 0;
		for (String key : keys) {
			String value = (String)data.get(key);
			String  name = names[i];
			int maxlen   = maxlens[i];
			// 최대길이 체크
			if (maxlen > 0 && value != null && value.length() > maxlen) {
				errors.add(name+"[최대길이: "+maxlen+"자]");
			}
			i++;
		}
		if (errors.size() == 0)
			return null;
		return errors;
	}
	
	@SuppressWarnings("rawtypes")
	public List<String> validateTypeErrors(Map data) {
		
		if (types == null)
			return null;
		
		List<String> errors = new ArrayList<String> ();
		int i = 0;
		for (String key : keys) {
			String value = (String)data.get(key);
			String name  = names[i];
			String type  = types[i];
			i++;
			
			if (type == null)
				continue;
			
			if (CommUtils.isEmpty(value))
				continue;
			
			// 데이터 타입 체크 (INT, DOUBLE, DATE)
			if ("INT".equals(type)) {
	    		try {
	        		Integer.parseInt(value);
	    		} catch(NumberFormatException ex) {
	    			errors.add(name+"[유효타입: 정수형]");
	    		}
			}
			else if ("DOUBLE".equals(type)) {
	    		try {
	    			Double.parseDouble(value);
	    		} catch(NumberFormatException ex) {
	    			errors.add(name+"[유효타입: 실수형]");
	    		}
			}
			else if ("DATE".equals(type)) {
				if (value.length() != 10) {
					errors.add(name+"[유효타입: 날짜형(예:2022.01.01)]");
				}
				else {
	    			try {
		    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
						sdf.parse(value);
					} catch (ParseException ex) {
						errors.add(name+"[유효타입: 날짜형(예:2022.01.01)]");
					}
				}
			}
		}
		if (errors.size() == 0)
			return null;
		return errors;
	}
	
	// 23.07.17 사용자가 소속된 유관기관 데이터인지 확인한다.
	@SuppressWarnings("rawtypes")
	public List<String> validateCrdnsNoErrors(Map data, String crdnsNo) {
		
		List<String> errors = new ArrayList<String> ();
		int i = 0;
		
		String value = (String)data.get("crdnsBzentyNo");
		if (!value.equals(crdnsNo)) {
			errors.add(names[0]);
		}
		
		if (errors.size() == 0) {
			return null;
		}
		
		return errors;
	}
	
	// 2023.01.03 KEY에 해당하는 항목의 최대길이를 가져온다.
	public int getMaxLength(String keyArg) {
		int i = 0;
		for (String key : keys) {
			if (key.equals(keyArg))
				return maxlens[i];
			i++;
		}
		return 0;
	}
	
	public int getHeaderCnt() {
		return headerCnt;
	}

	public int getStartRno() {
		return startRno;
	}

	public int getStartCno() {
		return startCno;
	}

	public boolean isAllow() {
		return allow;
	}

	public String[] getKeys() {
		return keys;
	}

	public String getRemark() {
		return remark;
	}

	public String[] getNames() {
		return names;
	}

	public int getSheetIdx() {
		return sheetIdx;
	}
}