package business.usr.file.service;

import business.com.common.service.FileVO;
import common.base.BaseModel;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 업체첨부파일 모델 클래스
 *
 * @class   : EntFileVO
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class EntFileVO extends BaseModel implements FileVO {

    // 일련번호
    private Long sn;
    // 업체번호
    private String bzentyNo;
    // 문서구분코드
    private String docSeCd;
    // 파일명
    private String fileNm;
    // 저장파일명
    private String strgFileNm;
    // 파일경로
    private String filePath;
    // 파일크기
    private Long fileSz;
    // 대표여부
    private String rprsYn;
    // 삭제여부
    private String delYn;
    // 등록자번호
    private String rgtrNo;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    
    // 문서구분조건
    private String[] docuCds;
    
	@Override
	public FileDirectory getFileDirectory() {
		return FileDirectory.ENT;
	}
    
	// FileInfo 객체 생성 반환
	@Override
	public FileInfo getFileInfo() {
		return FileInfo.builder()
				.fullPath(getFileDirectory().getRealPath(getFilePath()))
				.filePath(getFilePath())
				.saveName(getStrgFileNm())
				.fileName(getFileNm())
				.docuNo  (getBzentyNo())
				.docuCd  (getDocSeCd())
				.docuYn  (getRprsYn())
				.build();
	}

	@Override
	public String getUserNo() {
		if (getUserInfo() == null)
			return null;
		return getUserInfo().getUserNo();
	}

	@Override
	public String getRoleId() {
		if (getUserInfo() == null)
			return null;
		return getUserInfo().getRoleId();
	}

	@Override
	public void setFileInfo(FileInfo fileInfo) {
		setFilePath  (fileInfo.getFilePath());
		setFileNm    (fileInfo.getFileName());
		setStrgFileNm(fileInfo.getSaveName());
		setFileSz    (fileInfo.getFileSize());
		
		if (CommUtils.isNotEmpty(fileInfo.getDocuNo()))
			setBzentyNo  (fileInfo.getDocuNo());
		if (CommUtils.isNotEmpty(fileInfo.getDocuCd()))
			setDocSeCd   (fileInfo.getDocuCd());
		if (CommUtils.isNotEmpty(fileInfo.getDocuYn()))
			setRprsYn    (fileInfo.getDocuYn());
		if (CommUtils.isNotEmpty(fileInfo.getFileNo()))
			setSn (new Long(fileInfo.getFileNo()));
	}

	@Override
	public String getFileNo() {
		return String.valueOf(sn);
	}

	@Override
	public String getDocuCd() {
		return docSeCd;
	}

	@Override
	public String getDocuNo() {
		return bzentyNo;
	}
	@Override
	public String getDocuYn() {
		return rprsYn;
	}

	@Override
	public void setFileNo(String fileNo) {
		this.sn = CommUtils.getLong(fileNo, 0);
	}

	@Override
	public void setDocuCd(String docuCd) {
		this.docSeCd = docuCd;
	}

	@Override
	public void setDocuNo(String docuNo) {
		this.bzentyNo = docuNo;
	}

	@Override
	public void setDocuYn(String docuYn) {
		this.rprsYn = docuYn;
	}
	
	@Override
	public String getDocuSeq() { return null; }
	@Override
	public String getFileSe() { return null; }
	@Override
	public String getPapeCd() { return null; }
	@Override
	public String getSttsCd() { return null; }
	@Override
	public String getDocuCn() { return null; }

	@Override
	public void setDocuSeq(String docuSeq) {}
	@Override
	public void setFileSe(String fileSe) {}
	@Override
	public void setPapeCd(String papeCd) {}
	@Override
	public void setSttsCd(String sttsCd) {}
	@Override
	public void setDocuCn(String docuCn) {}
}
