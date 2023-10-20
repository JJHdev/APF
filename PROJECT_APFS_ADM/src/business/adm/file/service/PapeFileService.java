package business.adm.file.service;

import java.util.List;

import common.base.BaseModel;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 업무첨부파일 Service Interface
 * 
 * @class   : PapeFileService
 * @author 	: JH
 * @since 	: 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface PapeFileService {

    /**
     * 업무첨부파일 목록조회
     */
    public List listPapeFile(PapeFileVO papeFileVO) throws Exception;

    /**
     * 서류양식 상세 조회
     */
	public PapeFileVO viewPapeFile(String dcmntCd) throws Exception;
}