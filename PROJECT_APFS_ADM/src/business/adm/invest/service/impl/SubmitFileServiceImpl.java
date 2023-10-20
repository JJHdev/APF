package business.adm.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.invest.service.SubmitFileService;
import business.adm.invest.service.SubmitFileVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 운영관리-제출서류관리 Service
 * 
 * @class   : DscsnService
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("SubmitFileService")
@SuppressWarnings({"all"})
public class SubmitFileServiceImpl extends BaseService implements SubmitFileService {

    @Resource(name = "SubmitFileDAO")
    private SubmitFileDAO submitFileDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * 제출서류 페이징목록조회
     */
    @Override
    public PaginatedArrayList listSubmitFile(SubmitFileVO submitFileVO, int currPage, int pageSize) throws Exception {
    	return submitFileDAO.listSubmitFile(submitFileVO, currPage, pageSize);
    }

    /**
     * 제출서류 목록조회
     */
    @Override
    public List listSubmitFile(SubmitFileVO submitFileVO) throws Exception {
    	return submitFileDAO.listSubmitFile(submitFileVO);
    }

    /**
     * 제출서류 상세 조회
     */
	@Override
	public SubmitFileVO viewSubmitFile(SubmitFileVO submitFileVO) throws Exception {
		return submitFileDAO.viewSubmitFile(submitFileVO);
	}

    /**
     * 제출서류 조회, 수정, 삭제
     */
	@Override
	public String saveSubmitFile(SubmitFileVO submitFileVO) throws Exception {
		if (submitFileVO == null)
			throw processException("error.comm.notTarget");
		int ret = 0;
		
		String mode = submitFileVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 상담일지 수정
			ret = submitFileDAO.updtSubmitFile(submitFileVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 상담일지 등록
			ret = submitFileDAO.regiSubmitFile(submitFileVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 상담일지 삭제
			ret = submitFileDAO.deltSubmitFile(submitFileVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

}