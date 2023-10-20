package business.sys.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.processing.FilerException;

import org.springframework.stereotype.Service;

import com.sun.star.util.FileIOException;

import business.adm.CodeConst;
import business.adm.file.service.BbsFileService;
import business.adm.file.service.BbsFileVO;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.file.service.impl.BizFileDAO;
import business.com.CommConst;
import business.sys.code.service.CodeVO;
import business.sys.file.service.PapeCodeService;
import business.sys.file.service.PapeCodeVO;
import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;
import common.base.BaseModel;
import common.base.BaseService;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;
import common.util.FileUtils;
import common.util.properties.ApplicationProperty;

/**
 * [서비스클래스] - 서류코드관리 Service 구현 클래스
 *
 * @class   : PapeCodeServiceImpl
 * @author : JH
 * @since : 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("PapeCodeService")
@SuppressWarnings({"all"})
public class PapeCodeServiceImpl extends BaseService implements PapeCodeService {

    @Resource(name = "PapeCodeDAO")
    private PapeCodeDAO papeCodeDAO;

    @Resource(name = "BizFileService")
    private BizFileService bizFileService;

    @Resource(name = "BizFileDAO")
    private BizFileDAO bizFileDAO;
    
    @Resource(name="fileManager")
    protected FileManager fileManager;
    
    /**
     * 서류코드관리 목록조회
     */
    @Override
    public List listPapeCode(PapeCodeVO papeCodeVO) throws Exception {
    	return papeCodeDAO.listPapeCode(papeCodeVO);
    }

    /**
     * 서류코드관리 상세조회
     */
	@Override
	public PapeCodeVO viewPapeCode(PapeCodeVO papeCodeVO) throws Exception {
	    PapeCodeVO vo = papeCodeDAO.viewPapeCode(papeCodeVO);
	    String fileNm = vo.getFileNm();

	    if(vo != null && !CommUtils.isEmpty(fileNm)) {
	        String fileExt    = fileNm.substring(fileNm.indexOf(".")+1);
	        String papeNm     = FileUtils.convertInvalidFileName(CommUtils.nvlTrim(vo.getDcmntNm()));
            String downFileNm = "[양식]"+papeNm + "." + fileExt;

	    }
		return vo;
	}
	
	 /**
     * 서류코드관리 등록,수정,삭제
     */
	@Override
	public String savePapeCode(PapeCodeVO papeCodeVO, List<FileInfo> saveFiles) throws Exception {
		if (papeCodeVO == null)
			throw processException("error.comm.notTarget");
		int ret = 0;
		String mode = papeCodeVO.getMode();
		// 이름지정  저장은 되었음
		// 이름지정 
		  for (FileInfo fileInfo : saveFiles) {
			  if(CommUtils.isNotEmpty(fileInfo.getFileName())) {
				  StringBuilder filePath = new StringBuilder();
				  filePath.append(FileDirectory.FORMFILE +"/");
				  
				  papeCodeVO.setFilePath(filePath.toString());
				  papeCodeVO.setFileNm(fileInfo.getFileName());
				  saveFormFile(papeCodeVO, saveFiles);
			  }
		  };
		if (CommConst.UPDATE.equals(mode)) {
			// 게시판 수정
			ret = papeCodeDAO.updtPapeCode(papeCodeVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 게시판 등록
			ret = papeCodeDAO.regiPapeCode(papeCodeVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			
			// 게시판 삭제
			ret = papeCodeDAO.deltPapeCode(papeCodeVO);
			
			// 하위 코드관리 삭제
			if(CommUtils.isEqual(papeCodeVO.getUpDcmntCd() ,CommConst.ROOT_CODE)){
			    
			    PapeCodeVO underCodeVO = new PapeCodeVO();
			    underCodeVO.setSrchType(papeCodeVO.getDcmntCd());
			    
			    List<HashMap<String, Object>> list = papeCodeDAO.listUnderPapeCode(underCodeVO);
			    
			    for (HashMap<String, Object> rowMap : list) {
			    	
			    	PapeCodeVO row = new PapeCodeVO();
			        row.setUpDcmntCd((String) rowMap.get("upDcmntCd"));
			        row.setDcmntCd((String) rowMap.get("dcmntCd"));

			        // 역할별메뉴관리 삭제
			        ret += papeCodeDAO.deltPapeCode(row);
			    }
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
    /**
     * 다운로드 카운트 층가 저장
     */
    public int updtPapeCodeDownCount(PapeCodeVO papeCodeVO) throws Exception {
        return papeCodeDAO.updtPapeCodeDownCount(papeCodeVO);
    }

    /**
     * 코드페이징리스트
     */
	@Override
	public PaginatedArrayList listPapeCode(PapeCodeVO papeCodeVO, int currPage, int pageSize) throws Exception {
		 return papeCodeDAO.listPapeCode(papeCodeVO, currPage, pageSize);
    }
	
	/**
     * 업무첨부파일 저장처리
     * 업무첨부파일을 업무정보와 함께 업로드하여 저장할때 사용한다.
     */
	public void saveFormFile(PapeCodeVO papeCodeFileVO, List<FileInfo> saveFiles) throws Exception {
		if (papeCodeFileVO == null)
			throw processException("error.comm.notTarget");
		if (papeCodeFileVO.getMode() == null)
			throw processException("error.comm.notTarget");
		if (papeCodeFileVO.getGsUserNo() == null)
			throw processException("error.comm.notTarget");
		
        // 첨부파일 경로
        FileDirectory d = FileDirectory.FORMFILE;
        // 파일경로 정의 
        // (/Form/업무구분/문서번호)
        StringBuilder filePath = new StringBuilder();
        filePath.append(d.getPath() +"/");
		
		// 첨부파일목록 조회
        List<PapeCodeVO> orgnFiles = papeCodeDAO.listPapeCodeFile(papeCodeFileVO);
		int ret = 0;
		String mode = papeCodeFileVO.getMode();
		
		// 하위코드 파일도 삭제 Y표시 및 remove로 이동
        if(CommConst.DELETE.equals(mode) && CommUtils.isEqual(papeCodeFileVO.getUpDcmntCd(), CommConst.ROOT_CODE)) {
    		List<HashMap<String, Object>> list = papeCodeDAO.listUnderPapeCode(papeCodeFileVO);
    		for (HashMap<String, Object> rowMap: list) {
    			BizFileVO underBizFileVO = new BizFileVO();
    			underBizFileVO.setDocNo((String) rowMap.get("dcmntCd"));
    			underBizFileVO.setTaskSeCd((String) rowMap.get("UpDcmntCd"));
    			for(PapeCodeVO fileInfo : orgnFiles) {
    				orgnFiles.add(fileInfo); 
    			}
    		}
        }
        
		// 삭제인 경우
		if (CommConst.DELETE.equals(mode)) {
			// 파일이 있으면 물리적 파일을 삭제경로로 이동한다.
			// 이동후 실제파일은 삭제한다.
			// 파일정보의 del_yn을 N으로 변경한다.
	        if (CommUtils.isNotEmptyList(orgnFiles)) {
	        	for (PapeCodeVO vo : orgnFiles) {
	        		vo.setGsUserNo(papeCodeFileVO.getGsUserNo());
	        		_deltFormFile(vo);
	        	}
	        }
		}
		// 등록,수정인 경우
		else if (CommConst.INSERT.equals(mode) ||
				 CommConst.UPDATE.equals(mode)) {
	        // 첨부파일이 있는 경우
	        // 삭제대상 파일이 있는 경우
			for (PapeCodeVO vo : orgnFiles) {
				if (CommUtils.isNotEmpty(vo.getFileNm())) {
					// 파일 삭제
	        		vo.setGsUserNo(papeCodeFileVO.getGsUserNo());
	        		_deltFormFile(vo);
				}
	        }
	        if (CommUtils.isNotEmptyList(saveFiles)) {
	        	for (FileInfo f : saveFiles) {
	    			// 파일이 첨부된 경우
	    			if ("Y".equals(f.getFileYn())) {
	    				// 파일경로 정의
	    				f.setFilePath(filePath.toString());
		        		// 첨부파일 실제경로로 이동처리
		        		fileManager.saveFile(d, f);
	    			}
	        	}
	        }
		}
	}
	
	
	private void _deltFormFile(PapeCodeVO papeCodeFileVO) throws Exception {
			String papeFilePath = ApplicationProperty.get("upload.real.dir")+papeCodeFileVO.getFilePath();	
			String saveName =papeCodeFileVO.getDcmntCd()+'.'+FileUtils.getFileExt(papeCodeFileVO.getFileNm());
			try {
				File originFile = new File(papeFilePath, saveName);
				originFile.delete();
			} catch(NullPointerException e) {
				logger.error("NullPointerException :: ", e);
			} catch(Exception e) {
				logger.error("Exception :: ", e);
			}
	}
}