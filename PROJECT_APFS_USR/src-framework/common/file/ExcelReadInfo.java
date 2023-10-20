package common.file;

import java.util.ArrayList;
import java.util.List;

public class ExcelReadInfo {

    private int          headerRowCount  = 1;
    private List<String> cellMappingKeys ;
    private String       fileFullPathName;
    // 2021.08.23 LSH 추가
    private int          startRowIndex   = 1; // 데이터 시작 행 INDEX
    private int          startColIndex   = 0; // 데이터 시작 열 INDEX (현재적용안함)
    // 2021.12.01 LSH 추가
    private int          sheetIndex      = -1; // 처리할 Sheet INDEX (-1이면 전체 SHEET를 수행)
    
    /**
     * 각 row가 정의된 컬럼수와 다름을 허용할지여부
     * 허용하지 않으면 Exception 발생함
     *
     * true : exception을 발생하지 않고 null로 적용
     * false : exception을 발행하고 parsing작업을 중지
     * default는 true
     */
    private boolean      isAllowExcelFormatError = true ;

    public ExcelReadInfo() {
        headerRowCount  = 0;
        cellMappingKeys = new ArrayList<String>();
    }

    public int getHeaderRowCount() {
        return headerRowCount;
    }
    public ExcelReadInfo setHeaderRowCount(int headerRowCount) {
        this.headerRowCount = headerRowCount;
        return this;
    }
    public int getStartRowIndex() {
        return startRowIndex;
    }
    public ExcelReadInfo setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
        return this;
    }
    public int getStartColIndex() {
        return startColIndex;
    }
    public ExcelReadInfo setStartColIndex(int startColIndex) {
        this.startColIndex = startColIndex;
        return this;
    }

	public int getSheetIndex() {
		return sheetIndex;
	}

	public ExcelReadInfo setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
        return this;
	}
    
    public List<String> getCellMappingKeys() {
        //return cellMappingKeys;
        List<String> retnList = new ArrayList<String>();
        retnList.addAll(this.cellMappingKeys);
        return retnList;
    }
    public ExcelReadInfo setCellMappingKeys(List<String> cellMappingKeys) {
        //this.cellMappingKeys = cellMappingKeys;
        this.cellMappingKeys = new ArrayList<String>();
        this.cellMappingKeys.addAll(cellMappingKeys);
        return this;
    }
    public String getFileFullPathName() {
        return this.fileFullPathName;
    }
    public ExcelReadInfo setFileFullPathName(String path) {
        this.fileFullPathName = path;
        return this;
    }
    

    public boolean isAllowExcelFormatError() {
        return isAllowExcelFormatError;
    }

    public ExcelReadInfo setAllowExcelFormatError(boolean isAllowExcelFormatError) {
        this.isAllowExcelFormatError = isAllowExcelFormatError;
        return this;
    }

    @Override
    public String toString() {
        return "ExcelReadInfo [headerRowCount=" + headerRowCount +
			                ", sheetIndex=" + sheetIndex +
                            ", startRowIndex=" + startRowIndex +
                            ", startColIndex=" + startColIndex +
                            ", cellMappingKeys=" + cellMappingKeys +
                            ", fileFullPathName=" + fileFullPathName + "]";
    }
}
