package business.adm.file.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.adm.file.service.BbsFileVO;
import business.adm.file.service.EntFileService;
import business.adm.file.service.EntFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [서비스클래스] - 업체첨부파일 Service 구현 클래스
 * 
 * @class   : EntFileServiceImpl
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntFileService")
@SuppressWarnings({"all"})
public class EntFileServiceImpl extends BaseService implements EntFileService {

    @Resource(name = "EntFileDAO")
    private EntFileDAO entFileDAO;

    @Resource(name="fileManager")
    protected FileManager fileManager;

    /**
     * 업체첨부파일 목록조회
     */
    @Override
    public List listEntFile(EntFileVO entFileVO) throws Exception {
    	return entFileDAO.listEntFile(entFileVO);
    }

    /**
     * 업체첨부파일 상세조회
     */
	@Override
	public EntFileVO viewEntFile(Long sn) throws Exception {
		return entFileDAO.viewEntFile(sn);
	}

    /**
     * 업체첨부파일 등록,수정,삭제
     */
	@Override
	public void saveEntFile(EntFileVO entFileVO, List<FileInfo> saveFiles) throws Exception {
		
		if (entFileVO == null)
			throw processException("error.comm.notTarget");
		if (entFileVO.getMode() == null)
			throw processException("error.comm.notTarget");
		if (entFileVO.getDocSeCd() == null)
			throw processException("error.comm.notTarget");
		if (entFileVO.getBzentyNo() == null)
			throw processException("error.comm.notTarget");
		
		
        // 게시판 첨부파일 경로
        FileDirectory d = entFileVO.getFileDirectory();
        // 파일경로 정의 
        // (/ent/업체번호/문서구분)
        StringBuilder filePath = new StringBuilder();
        filePath.append(d.getPath()            +"/");
        filePath.append(entFileVO.getBzentyNo()+"/");
        filePath.append(entFileVO.getDocSeCd() +"/");
		
		// 게시판 첨부파일목록 조회
        List<EntFileVO> orgnFiles = entFileDAO.listEntFile(entFileVO);
		int ret = 0;
		String mode = entFileVO.getMode();
		
		// 게시판 삭제인 경우
		if (CommConst.DELETE.equals(mode)) {
			// 파일이 있으면 물리적 파일을 삭제경로로 이동한다.
			// 이동후 실제파일은 삭제한다.
			// 파일정보의 del_yn을 N으로 변경한다.
	        if (CommUtils.isNotEmptyList(orgnFiles)) {
	        	for (EntFileVO vo : orgnFiles) {
	        		vo.setGsUserNo(entFileVO.getGsUserNo());
	        		_deltEntFile(vo);
	        	}
	        }
		}
		// 게시판 등록,수정인 경우
		else if (CommConst.INSERT.equals(mode) ||
				 CommConst.UPDATE.equals(mode)) {
	        // 첨부파일이 있는 경우
	        if (CommUtils.isNotEmptyList(saveFiles)) {
	        	for (FileInfo f : saveFiles) {
	    			// 파일이 첨부된 경우
	    			if ("Y".equals(f.getFileYn())) {
	    				// 파일경로 정의
	    				f.setFilePath(filePath.toString());
		        		// 첨부파일 실제경로로 이동처리
		        		fileManager.saveFile(d, f);
		        		// 파일정보 정의
		        		entFileVO.setFileInfo(f);
		                // 첨부파일 저장
		        		entFileDAO.regiEntFile(entFileVO);
	    			}
	    			else if (orgnFiles != null) {
	   					// 삭제대상 파일이 있는지 체크
	    				for (EntFileVO vo : orgnFiles) {
	    					if (vo.getSn() != null &&
	    						f.getFileNo() != null &&
	    						vo.getSn().longValue() == Long.parseLong(f.getFileNo())) {
	    						orgnFiles.remove(vo);
	    						break;
	    					}
	    				}
	    			}
	        	}
	        }
	        // 삭제대상 파일이 있는 경우
	        if (CommUtils.isNotEmptyList(orgnFiles)) {
				for (EntFileVO vo : orgnFiles) {
					// 파일 삭제
	        		vo.setGsUserNo(entFileVO.getGsUserNo());
	        		_deltEntFile(vo);
				}
	        }
		}
	}
	
	private int _deltEntFile(EntFileVO entFileVO) throws Exception {
		// 파일이 없을 경우 SKIP 되도록 처리 
		try {
			// 물리적파일 백업경로로 이동 후 삭제
			fileManager.removeFile(entFileVO.getFileDirectory(), entFileVO.getFileInfo());
		} catch (IOException e) {
			logger.error("DELETE FILE BACKUP ERROR", e);
		} catch (Exception e) {
			logger.error("DELETE FILE BACKUP ERROR", e);
		}
		// 파일정보 삭제처리 (삭제플래그 변경)
		return entFileDAO.deltEntFile(entFileVO);
	}

	@Override
	public String deltEntFile(EntFileVO entFileVO) throws Exception {
        // 삭제결과를 반환한다.
		if (_deltEntFile(entFileVO) > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
}