package business.adm.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.file.service.PapeFileService;
import business.adm.file.service.PapeFileVO;
import business.com.CommConst;
import commf.exception.BusinessException;
import common.base.BaseModel;
import common.base.BaseService;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;

/**
 * [서비스클래스] - 업무첨부파일 Service 구현 클래스
 * 
 * @class   : PapeFileServiceImpl
 * @author 	: JH
 * @since 	: 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("PapeFileService")
@SuppressWarnings({"all"})
public class PapeFileServiceImpl extends BaseService implements PapeFileService {

    @Resource(name = "PapeFileDAO")
    private PapeFileDAO papeFileDAO;

    @Resource(name="fileManager")
    protected FileManager fileManager;

    /**
     * 업무첨부파일 목록조회
     */
    @Override
    public List listPapeFile(PapeFileVO papeFileVO) throws Exception {
    	return papeFileDAO.listPapeFile(papeFileVO);
    }

    /**
     * 서류양식 상세 조회
     */
	@Override
	public PapeFileVO viewPapeFile(String dcmntCd) throws Exception  {
		return papeFileDAO.viewPapeFile(dcmntCd);
    }
}