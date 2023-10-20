package business.adm.support.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.support.service.IrService;
import business.adm.support.service.IrVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 운영관리-IR지원현황 Service 구현 클래스
 * 
 * @class   : IrServiceImpl
 * @author  : LHB
 * @since   : 2023.06.08
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.08      LHB            First Coding.
 */

@Service("IrService")
@SuppressWarnings({"all"})
public class IrServiceImpl extends BaseService implements IrService {

    @Resource(name = "IrDAO")
    private IrDAO irDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
    
    @Autowired
    private CommService commService;
	
    /**
     * 운영관리-IR지원현황 페이징목록조회
     */
    @Override
    public PaginatedArrayList listIr(IrVO irVO, int currPage, int pageSize) throws Exception {
    	return irDAO.listIr(irVO, currPage, pageSize);
    }

    /**
     * 운영관리-IR지원현황 목록조회
     */
    @Override
    public List listIr(IrVO irVO) throws Exception {
    	return irDAO.listIr(irVO);
    }

    /**
     * 운영관리-IR지원현황 상세조회
     */
	@Override
	public IrVO viewIr(IrVO irVO) throws Exception {
		return irDAO.viewIr(irVO);
	}

    /**
     * 운영관리-IR지원현황 등록,수정,삭제
     */
	@Override
	public String saveIr(IrVO irVO, List<FileInfo> files) throws Exception {
		
		if (irVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = irVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 사업공고 수정
			ret = irDAO.updtIr(irVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 사업공고 등록
			ret = irDAO.regiIr(irVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 사업공고 삭제
			ret = irDAO.deltIr(irVO);
		}
		
		// LSH. 첨부파일 사용시 다음과 같이 처리할것
		if (ret > 0) {
			// 첨부파일 저장처리
			BizFileVO fileVO = BizFileVO.builder()
					.taskSeCd(CodeConst.TASK_PBNC)
					.docNo   (irVO.getFundNo())
					.dtlDocNo(irVO.getBzentyNo())
					.gsUserNo(irVO.getGsUserNo())
					.build();
			
			fileVO.setMode(mode);
			
			bizFileService.saveBizFile(fileVO, files);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
}