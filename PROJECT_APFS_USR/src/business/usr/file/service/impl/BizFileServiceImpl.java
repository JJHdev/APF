package business.usr.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
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
 * @class   : BizFileServiceImpl
 * @author  : LSH
 * @since   : 2023.04.23
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("BizFileService")
@SuppressWarnings({"all"})
public class BizFileServiceImpl extends BaseService implements BizFileService {

    @Resource(name = "BizFileDAO")
    private BizFileDAO bizFileDAO;

    @Resource(name="fileManager")
    protected FileManager fileManager;

    /**
     * 업무첨부파일 목록조회
     */
    @Override
    public List listBizFile(BizFileVO bizFileVO) throws Exception {
    	return bizFileDAO.listBizFile(bizFileVO);
    }

    /**
     * 업무첨부파일 상세조회
     */
	@Override
	public BizFileVO viewBizFile(Long sn) throws Exception {
		return bizFileDAO.viewBizFile(sn);
	}

    /**
     * 2023.09.12 LSH
     * 업무첨부파일 상세조회
     */
	@Override
	public BizFileVO viewBizFileByParams(BizFileVO bizFileVO) throws Exception {
		return bizFileDAO.viewBizFileByParams(bizFileVO);
	}

	/**
     * 서류양식 목록 조회
     */
    @Override
    public List listPape(BizFileVO bizFileVO) throws Exception {
    	return bizFileDAO.listPape(bizFileVO);
    }
    
    /**
     * 서류양식그룹 목록 조회
     */
    @Override
    public List listPapeGroup(BizFileVO bizFileVO) throws Exception {
    	return bizFileDAO.listPapeGroup(bizFileVO);
    }

    /**
     * 서류양식 상세 조회
     */
    @Override
    public BizFileVO viewPape(String dcmntCd) throws Exception {
    	return bizFileDAO.viewPape(dcmntCd);
    }

	@Override
	public String deltBizFile(BizFileVO bizFileVO) throws Exception {
        // 삭제결과를 반환한다.
		if (_deltBizFile(bizFileVO) > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 업무첨부파일 저장처리
     * 업무첨부파일을 업무정보와 함께 업로드하여 저장할때 사용한다.
     */
	@Override
	public void saveBizFile(BizFileVO bizFileVO, List<FileInfo> saveFiles) throws Exception {
		
		if (bizFileVO == null)
			throw processException("error.comm.notTarget");
		if (bizFileVO.getMode() == null)
			throw processException("error.comm.notTarget");
		if (bizFileVO.getTaskSeCd() == null)
			throw processException("error.comm.notTarget");
		if (bizFileVO.getDocNo() == null)
			throw processException("error.comm.notTarget");
		if (bizFileVO.getGsUserNo() == null)
			throw processException("error.comm.notTarget");
		
        // 첨부파일 경로
        FileDirectory d = bizFileVO.getFileDirectory();
        // 파일경로 정의 
        // (/biz/업무구분/문서번호)
        StringBuilder filePath = new StringBuilder();
        filePath.append(d.getPath()            +"/");
        filePath.append(bizFileVO.getTaskSeCd()+"/");
        filePath.append(bizFileVO.getDocNo()   +"/");
		
		// 첨부파일목록 조회
        List<BizFileVO> orgnFiles = bizFileDAO.listBizFile(bizFileVO);
		int ret = 0;
		String mode = bizFileVO.getMode();
		
		// 삭제인 경우
		if (CommConst.DELETE.equals(mode)) {
			// 파일이 있으면 물리적 파일을 삭제경로로 이동한다.
			// 이동후 실제파일은 삭제한다.
			// 파일정보의 del_yn을 N으로 변경한다.
	        if (CommUtils.isNotEmptyList(orgnFiles)) {
	        	for (BizFileVO vo : orgnFiles) {
	        		vo.setGsUserNo(bizFileVO.getGsUserNo());
	        		_deltBizFile(vo);
	        	}
	        }
		}
		// 등록,수정인 경우
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
		        		bizFileVO.setFileInfo(f);
		                // 첨부파일 저장
		        		bizFileDAO.regiBizFile(bizFileVO);
	    			}
	    			else if (orgnFiles != null) {
	   					// 삭제대상 파일이 있는지 체크
	    				for (BizFileVO vo : orgnFiles) {
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
				for (BizFileVO vo : orgnFiles) {
					// 파일 삭제
	        		vo.setGsUserNo(bizFileVO.getGsUserNo());
	        		_deltBizFile(vo);
				}
	        }
		}
	}
	
	private int _deltBizFile(BizFileVO bizFileVO) throws Exception {
		// 임시파일인 경우 실제삭제 처리
		if (CommConst.YES.equals(bizFileVO.getTempYn())) {
			
		    // 파일의 물리적 경로를 포함한 FULL NAME
			String tempFile = bizFileVO.getFileDirectory().getTempName(bizFileVO.getStrgFileNm());
			if (FileUtils.existFile(tempFile)) {
				// 파일이 없을 경우 SKIP 되도록 처리 
				try {
					// 대상 파일을 삭제한다.
					FileUtils.deleteFile(tempFile);
				} catch (NullPointerException e) {
					logger.error("DELETE FILE ERROR", e);
				} catch(Exception e) {
					logger.error("DELETE FILE ERROR", e);
				}
			}
			// 파일정보 실제삭제처리
			return bizFileDAO.deltBizFileActually(bizFileVO.getSn());
		}
		// 저장파일인 경우 삭제플래그 변경
		else {
			// 파일이 없을 경우 SKIP 되도록 처리 
			try {
				// 물리적파일 백업경로로 이동 후 삭제
				fileManager.removeFile(bizFileVO.getFileDirectory(), bizFileVO.getFileInfo());
			} catch (NullPointerException e) {
				logger.error("DELETE FILE BACKUP ERROR", e);
			} catch (Exception e) {
				logger.error("DELETE FILE BACKUP ERROR", e);
			}
			// 파일정보 삭제처리 (삭제플래그 변경)
			return bizFileDAO.deltBizFile(bizFileVO);
		}
	}

    /**
     * 서류코드맵핑이 필요한 업무첨부파일 임시저장처리
     * 업무첨부파일을 업로드팝업을 통해 임시경로에 저장할때 사용한다.
     * 업무연결 KEY와 맵핑되기 전에 저장된다.
     */
	@Override
    public BizFileVO saveTempFile(FileInfo fileInfo) throws Exception {

    	if (fileInfo == null)
    		return null;

        // 파일이 첨부된 경우
        if (!"Y".equals(fileInfo.getFileYn()))
        	return null;
        
        // 임시저장파일이므로 파일경로 삭제
        fileInfo.setFilePath(null);
        
        BizFileVO bizFileVO = BizFileVO.builder()
                .delYn (CommConst.YES)
                .tempYn(CommConst.YES)
                .fileSeCd(CodeConst.FILE_GNRL)
                .gsUserNo(fileInfo.getGsUserNo())
                .build();
        
		// 파일정보 정의
		bizFileVO.setFileInfo(fileInfo);
        // 첨부파일 저장
		if (bizFileDAO.regiBizFile(bizFileVO) > 0)
            return bizFileVO;

        return null;
    }

    /**
     * 서류코드맵핑이 필요한 업무첨부파일 실제저장처리
     * - 임시경로의 파일을 실제 경로로 이동한다.
     * - 파일의 업무연결 KEY를 맵핑한다.
     */
	@Override
	public int savePapeFile(BaseModel modelVO
			, List<BizFileVO> saveFiles
			, List<BizFileVO> removeFiles) throws Exception {
		
        // 첨부파일 경로 정보
        FileDirectory d = FileDirectory.PAPER;
		
		// 세션 사용자번호
		String gsUserNo = modelVO.getUserInfo().getUserNo();
		// 세션 사용자ROLE
		String gsRoleId = modelVO.getUserInfo().getRoleId();

		// 물리적삭제 대상목록
		List<String> removes = new ArrayList<String>();

		int ret = 0;

		// 삭제대상 파일이 있으면
		if (removeFiles != null &&
			removeFiles.size() > 0) {
			
			//// 삭제대상파일의 권한체크 Validation (제외함)
			//for (BizFileVO file : removeFiles) {
			//	// 파일정보 조회
			//	BizFileVO org = viewBizFile(file.getSn());
		    //	// 파일처리 권한이 없는 경우
		    //	// (관리자가 아니고 해당 파일생성자가 아닌 경우)
		    //	if (org != null &&
		    //		!CommConst.isAdminRole(gsRoleId) &&
		    //		!CommUtils.isEqual(gsUserNo, org.getRgtrNo()))
		    //		throw processException("error.file.notAccess");
			//}

			for (BizFileVO file : removeFiles) {
				// 임시경로에 업로드된 파일이면
				if ("Y".equals(file.getTempYn())) {
					// 임시경로의 파일을 물리적 파일 삭제 대상에 추가한다.
					removes.add(d.getTempName(file.getStrgFileNm()));
					// 파일정보 실제 삭제처리
					bizFileDAO.deltBizFileActually(file.getSn());
				}
				// 기저장된 파일이면
				else {
					// 파일을 백업경로에 복사한다.
					String orgnName = fileManager.backupFile(d, file.getFileInfo());
					// 삭제대상 파일을 물리적 파일 삭제 대상에 추가한다.
					removes.add(orgnName);
					// 세션사용자 정의
					file.setGsUserNo(gsUserNo);
					// 파일정보 삭제처리 (삭제플래그 변경)
					bizFileDAO.deltBizFile(file);
				}
			}
		}
		// 저장대상 파일이 있으면
		if (saveFiles != null &&
			saveFiles.size() > 0) {
			for (BizFileVO file : saveFiles) {
				// 각 VO별로 연결KEY와 파일경로를 정의한다.
				file.buildPath(modelVO);
				// 파일삭제여부 정의
				file.setDelYn(CommConst.NO);
				
				// 임시경로에 업로드된 파일이면
				if ("Y".equals(file.getTempYn())) {
					// 임시경로의 파일을 실제경로로 복사
					String tempFile = fileManager.copyFile(file.getFileDirectory(), file.getFileInfo());
					// 임시경로의 파일을 물리적 삭제 대상에 추가한다.
					removes.add(tempFile);
				}
		        // 파일의 KEY정보를 업데이트한다.
				bizFileDAO.updtBizFile(file);
		        ret++;
			}
		}
		// 물리적 삭제 대상이 있으면 일괄 삭제한다.
		if (removes.size() > 0) {
			for (String fileName : removes) {
				// 파일이 없을 경우 SKIP 되도록 처리 
				try {
					// 대상 파일을 삭제한다.
					FileUtils.deleteFile(fileName);
				} catch (NullPointerException e) {
					logger.error("DELETE FILE ERROR", e);
				} catch (Exception e) {
					logger.error("DELETE FILE ERROR", e);
				}
			}
		}
		return ret;
	}
}