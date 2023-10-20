package business.sys.file.service;

import business.com.CommConst;
import common.base.BaseModel;
import common.file.FileModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class AplySampleVO extends BaseModel implements FileModel {
	
	private String aplyNo;
	private String aplyOder;
	private String aplyYmd;
	private String prgreStusCd;

	// 파일경로 정의
	@Override
	public String getFilePath() {
		return "/" + getAplyYmd();
	}
	@Override
	public String getDcmtNo() {
		return getAplyNo();
	}

	@Override
	public String getDtlDcmtNo() {
		return getAplyOder();
	}

	@Override
	public String getStatusCd() {
		return CommConst.PROCESS_SUBMIT;
	}

}
