package business.com.common.service;

import common.file.FileDirectory;
import common.file.FileInfo;

public interface FileVO {
	
	FileDirectory getFileDirectory();

	FileInfo getFileInfo();
	
	void setFileInfo(FileInfo fileInfo);
	
	String getUserNo();
	
	String getRoleId();
	
	String getFileNo();
	String getDocuCd();
	String getDocuNo();
	String getDocuSeq();
	String getFileSe();
	String getPapeCd();
	String getSttsCd();
	String getDocuCn();
	String getDocuYn();

	void setFileNo (String fileNo );
	void setDocuCd (String docuCd );
	void setDocuNo (String docuNo );
	void setDocuSeq(String docuSeq);
	void setFileSe (String fileSe );
	void setPapeCd (String papeCd );
	void setSttsCd (String sttsCd );
	void setDocuCn (String docuCn );
	void setDocuYn (String docuYn );
}
