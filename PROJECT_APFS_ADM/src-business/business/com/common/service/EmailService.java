package business.com.common.service;

import java.util.List;
import java.util.Map;

import common.file.FileInfo;

public interface EmailService {
	
	public boolean sendEmail(EmailVO emailVO) throws Exception;
	
	public boolean sendEmail(EmailVO emailVO, List<FileInfo> files) throws Exception;

	public boolean sendBizEmail(Map<String,String> params, String cdId) throws Exception;
}
