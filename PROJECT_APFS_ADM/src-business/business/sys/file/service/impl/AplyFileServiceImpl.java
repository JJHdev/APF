package business.sys.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.file.service.AplyFileService;
import business.sys.file.service.AplyFileVO;
import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileDirectory;
import common.file.FileModel;
import common.user.UserInfo;
import common.util.CommUtils;
import common.util.FileUtils;

/**
 * [서비스클래스] - 신청첨부파일 Service 구현 클래스
 *
 * @class   : AplyFileServiceImpl
 * @author  : LSH
 * @since   : 2021.10.07
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("AplyFileService")
@SuppressWarnings({"all"})
public class AplyFileServiceImpl extends BaseService implements AplyFileService {

    @Resource(name = "AplyFileDAO")
    private AplyFileDAO aplyFileDAO;

    /**
     * 신청첨부파일 페이징목록조회
     */
    @Override
    public PaginatedArrayList listAplyFile(AplyFileVO aplyFileVO, int currPage, int pageSize) throws Exception {
    	return aplyFileDAO.listAplyFile(aplyFileVO, currPage, pageSize);
    }

    /**
     * 신청첨부파일 목록조회
     */
    @Override
    public List listAplyFile(AplyFileVO aplyFileVO) throws Exception {
    	return aplyFileDAO.listAplyFile(aplyFileVO);
    }

    /**
     * 신청첨부파일 상세조회
     */
	@Override
	public AplyFileVO viewAplyFile(String sn) throws Exception {
		return aplyFileDAO.viewAplyFile(sn);
	}

    /**
     * 신청첨부파일 등록,수정,삭제
     */
	@Override
	public String saveAplyFile(AplyFileVO aplyFileVO) throws Exception {

		if (aplyFileVO == null)
			throw processException("error.comm.notTarget");

		int ret = 0;
		String mode = aplyFileVO.getMode();

		if (CommConst.UPDATE.equals(mode)) {
			// 신청첨부파일 수정
			ret = aplyFileDAO.updtAplyFile(aplyFileVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 신청첨부파일 등록
			ret = aplyFileDAO.regiAplyFile(aplyFileVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 신청첨부파일 삭제
			ret = aplyFileDAO.deltAplyFile(aplyFileVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 신청파일 실제저장처리
     * 
	 * @param saveFiles    저장대상 신청파일목록
	 * @param removeFiles  삭제대상 신청파일목록
	 */
	@Override
	public int saveAplyFile(
			FileModel model,
			List<AplyFileVO> saveFiles, 
			List<AplyFileVO> removeFiles, 
			UserInfo userInfo) throws Exception {
		
        // 첨부파일 경로 정보
        FileDirectory d = FileDirectory.PAPER;
		
		// 세션 사용자번호
		String gsUserNo = userInfo.getUserNo();
		// 세션 사용자ROLE
		String gsRoleId = userInfo.getRoleId();

		// 물리적삭제 대상목록
		List<String> removes = new ArrayList<String>();

		int ret = 0;

		// 삭제대상 파일이 있으면
		if (removeFiles != null &&
			removeFiles.size() > 0) {

			// 삭제대상파일의 권한체크 Validation
			for (AplyFileVO file : removeFiles) {
				// 파일정보 조회
				AplyFileVO org = viewAplyFile(file.getSn());
		    	// 파일처리 권한이 없는 경우
		    	// (관리자가 아니고 해당 파일생성자가 아닌 경우)
		    	if (org != null &&
		    		!CommConst.isAdminRole(gsRoleId) &&
		    		!CommUtils.isEqual(gsUserNo, org.getRgtrNo()))
		    		throw new BusinessException("error.file.notAccess");
			}

			for (AplyFileVO file : removeFiles) {
				// 임시경로에 업로드된 파일이면
				if ("Y".equals(file.getTempYn())) {
				    // 파일의 물리적 경로를 포함한 FULL NAME
					String phyFile = d.getTempName(file.getStrgNm());
					// 물리적 삭제 대상에 추가한다.
					removes.add(phyFile);
					// 파일 데이터를 삭제한다.
					aplyFileDAO.deltAplyFile(file);
				}
				// 기저장된 파일이면
				else {
					// 실제 파일
					String orgnName = d.getRealName(file.getFilePath(), file.getStrgNm());
					// 삭제된 파일 저장 경로
					String trgtPath = FileUtils.mergePath(CommConst.REMOVE_PATH, file.getFilePath());
					// 전체 경로를 포함한 실제 파일
					String trgtName = d.getRealName(trgtPath, file.getStrgNm());
					// 물리적 파일을 removed 경로에 복사한다.
					FileUtils.copyFileWithDir(orgnName, trgtName, true);
					// 물리적 삭제 대상에 추가한다.
					removes.add(orgnName);

					AplyFileVO vo = AplyFileVO.builder()
							.gsUserNo(gsUserNo)
							.filePath(trgtPath)
							.delYn   ("Y")
							.sn      (file.getSn())
							.build();
					// 파일경로와 삭제여부를 업데이트한다.
					aplyFileDAO.updtAplyFile(vo);
				}
			}
		}
		// 저장대상 파일이 있으면
		if (saveFiles != null &&
			saveFiles.size() > 0) {
			for (AplyFileVO file : saveFiles) {
				
				AplyFileVO vo = AplyFileVO.builder()
		        		.gsUserNo  (gsUserNo)
		        		.sn        (file.getSn())
		        		.dcmtNo    (model.getDcmtNo   ())
		        		.dtlDcmtNo (model.getDtlDcmtNo())
		        		.prcsStusCd(model.getStatusCd ())
		                .build();
				
				// 임시경로에 업로드된 파일이면
				if ("Y".equals(file.getTempYn())) {
					// 파일경로정의
					vo.setFilePath(
						d.getPath()
						+ "/" + file.getUpPapeCd()
						+ "/" + file.getPapeCd()
						+ model.getFilePath()
					);
					// 저장파일명
					String strgName = file.getStrgNm();
				    // 파일의 물리적 경로를 포함한 FULL NAME
					String orgnFile = d.getTempName(strgName);
					String trgtFile = d.getRealName(vo.getFilePath(), strgName);
					// 물리적 파일을 실제 경로에 복사한다.
					FileUtils.copyFileWithDir(orgnFile, trgtFile, true);
					// 물리적 삭제 대상에 추가한다.
					removes.add(orgnFile);
				}
				// 기업로드된 파일이면
				else {
					// 등록자번호 맵핑
					vo.setRgtrNo(gsUserNo);
				}
		        // 파일의 KEY정보를 업데이트한다.
				aplyFileDAO.updtAplyFile(vo);
		        ret++;
			}
		}

		// 물리적 삭제 대상이 있으면 일괄 삭제한다.
		if (removes.size() > 0) {
			for (String fileName : removes) {
				// 대상 파일을 삭제한다.
				FileUtils.deleteFile(fileName);
			}
		}
		return ret;		
	}
}