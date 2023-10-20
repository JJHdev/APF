package business.adm.support.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.support.service.PbancService;
import business.adm.support.service.PbancVO;
import business.adm.support.service.SupportVO;
import business.com.CommConst;
import business.com.common.service.CommService;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 운영관리 - 사업공고관리 Service 구현 클래스
 * 
 * @class   : PbancServiceImpl
 * @author  : LHB
 * @since   : 2023.04.24
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("PbancService")
@SuppressWarnings({"all"})
public class PbancServiceImpl extends BaseService implements PbancService {

    @Resource(name = "PbancDAO")
    private PbancDAO pbancDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
    
    @Autowired
    private CommService commService;
	
    /**
     * 사업공고 페이징목록조회
     */
    @Override
    public PaginatedArrayList listPbanc(PbancVO pbancVO, int currPage, int pageSize) throws Exception {
    	return pbancDAO.listPbanc(pbancVO, currPage, pageSize);
    }

    /**
     * 사업공고 목록조회
     */
    @Override
    public List listPbanc(PbancVO pbancVO) throws Exception {
    	return pbancDAO.listPbanc(pbancVO);
    }

    /**
     * 사업공고 상세조회
     */
	@Override
	public PbancVO viewPbanc(PbancVO pbancVO) throws Exception {
		return pbancDAO.viewPbanc(pbancVO);
	}

    /**
     * 사업공고 등록,수정,삭제
     */
	@Override
	public String savePbanc(PbancVO pbancVO, List<FileInfo> files) throws Exception {
		
		if (pbancVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = pbancVO.getMode();
		
		if (pbancVO.getRcptSeCd().equals(CodeConst.RCPT_WHL_SE2)) {
			// 접수구분이 '상시모집'인 경우 날짜 삭제
			pbancVO.setRcptBgngDt (null);
			pbancVO.setRcptBgngTm (null);
			pbancVO.setRcptBgngYmd(null);
			pbancVO.setRcptEndDt  (null);
			pbancVO.setRcptEndTm  (null);
			pbancVO.setRcptEndYmd (null);
		} else if (pbancVO.getRcptSeCd().equals(CodeConst.RCPT_WHL_SE1)) {
			// 접수구분이 '예정'인 경우 월까지만 세팅
			if (CommUtils.isNotEmpty(pbancVO.getRcptBgngYmd())) {
				pbancVO.setRcptBgngYmd(pbancVO.getRcptBgngYmd() + "-00");
			}
			if (CommUtils.isNotEmpty(pbancVO.getRcptEndYmd())) {
				pbancVO.setRcptEndYmd(pbancVO.getRcptEndYmd() + "-00");
			}
			pbancVO.setRcptBgngTm("00");
			pbancVO.setRcptEndTm ("00");
		}
		
		if (CommConst.UPDATE.equals(mode)) {
			// 사업공고 수정
			ret = pbancDAO.updtPbanc(pbancVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 사업공고 등록
			ret = pbancDAO.regiPbanc(pbancVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 사업공고 삭제
			ret = pbancDAO.deltPbanc(pbancVO);
		}
		
		// LSH. 첨부파일 사용시 다음과 같이 처리할것
		if (ret > 0) {
			// 첨부파일 저장처리
			BizFileVO fileVO = BizFileVO.builder()
					.taskSeCd(CodeConst.TASK_PBNC)
					.docNo   (pbancVO.getBizPbancNo())
					.dtlDocNo(pbancVO.getCrdnsBzentyNo())
					.gsUserNo(pbancVO.getGsUserNo())
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