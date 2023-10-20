package business.adm.file.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.adm.file.service.BbsFileService;
import business.adm.file.service.BbsFileVO;
import business.adm.file.service.BizFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileDirectory;
import common.file.FileInfo;
import common.file.FileManager;
import common.util.CommUtils;

/**
 * [서비스클래스] - 게시판첨부파일 Service 구현 클래스
 * 
 * @class   : BbsFileServiceImpl
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("BbsFileService")
@SuppressWarnings({"all"})
public class BbsFileServiceImpl extends BaseService implements BbsFileService {

    @Resource(name = "BbsFileDAO")
    private BbsFileDAO bbsFileDAO;

    @Resource(name="fileManager")
    protected FileManager fileManager;


    /**
     * 게시판첨부파일 목록조회
     */
    @Override
    public List listBbsFile(BbsFileVO bbsFileVO) throws Exception {
    	return bbsFileDAO.listBbsFile(bbsFileVO);
    }

    /**
     * 게시판첨부파일 상세조회
     */
	@Override
	public BbsFileVO viewBbsFile(Long sn) throws Exception {
		return bbsFileDAO.viewBbsFile(sn);
	}


    /**
     * 게시판첨부파일 등록,수정,삭제
     */
	@Override
	public void saveBbsFile(BbsFileVO bbsFileVO, List<FileInfo> saveFiles) throws Exception {
		
		if (bbsFileVO == null)
			throw processException("error.comm.notTarget");
		if (bbsFileVO.getMode() == null)
			throw processException("error.comm.notTarget");
		if (bbsFileVO.getTaskSeCd() == null)
			throw processException("error.comm.notTarget");
		if (bbsFileVO.getPstNo() == null)
			throw processException("error.comm.notTarget");
		
		
        // 게시판 첨부파일 경로
        FileDirectory d = bbsFileVO.getFileDirectory();
        // 파일경로 정의 
        // (/bbs/게시판코드/게시글번호/등록일자/)
        StringBuilder filePath = new StringBuilder();
        filePath.append(d.getPath()            +"/");
        filePath.append(bbsFileVO.getTaskSeCd()+"/");
        filePath.append(bbsFileVO.getPstNo()   +"/");
        filePath.append(CommUtils.getCurDateString());
		
		// 게시판 첨부파일목록 조회
        List<BbsFileVO> orgnFiles = bbsFileDAO.listSaveBbsFile(bbsFileVO);
		int ret = 0;
		String mode = bbsFileVO.getMode();
		
		// 게시판 삭제인 경우
		if (CommConst.DELETE.equals(mode)) {
			// 파일이 있으면 물리적 파일을 삭제경로로 이동한다.
			// 이동후 실제파일은 삭제한다.
			// 파일정보의 del_yn을 N으로 변경한다.
	        if (CommUtils.isNotEmptyList(orgnFiles)) {
	        	for (BbsFileVO vo : orgnFiles) {
	        		vo.setGsUserNo(bbsFileVO.getGsUserNo());
	        		_deltBbsFile(vo);
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
		        		bbsFileVO.setFileInfo(f);
		                // 첨부파일 저장
		        		bbsFileDAO.regiBbsFile(bbsFileVO);
	    			}
	    			else if (orgnFiles != null) {
	   					// 삭제대상 파일이 있는지 체크
	    				for (BbsFileVO vo : orgnFiles) {
	    					if (CommUtils.isNotEmpty(vo.getSn()) &&
	    						CommUtils.isNotEmpty(f.getFileNo()) &&
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
				for (BbsFileVO vo : orgnFiles) {
					// 파일 삭제
	        		vo.setGsUserNo(bbsFileVO.getGsUserNo());
	        		_deltBbsFile(vo);
				}
	        }
		}
	}
	
	private int _deltBbsFile(BbsFileVO bbsFileVO) throws Exception {
		// 파일이 없을 경우 SKIP 되도록 처리 
		try {
			// 물리적파일 백업경로로 이동 후 삭제
			fileManager.removeFile(bbsFileVO.getFileDirectory(), bbsFileVO.getFileInfo());
		} catch (NullPointerException e) {
			logger.error("DELETE FILE BACKUP ERROR", e);
		} catch(Exception e) {
			logger.error("DELETE FILE BACKUP ERROR", e);
		}
		// 파일정보 삭제처리 (삭제플래그 변경)
		return bbsFileDAO.deltBbsFile(bbsFileVO);
	}

	@Override
	public String deltBbsFile(BbsFileVO bbsFileVO) throws Exception {
        // 삭제결과를 반환한다.
		if (_deltBbsFile(bbsFileVO) > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}