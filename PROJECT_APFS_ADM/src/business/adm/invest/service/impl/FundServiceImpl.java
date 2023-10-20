package business.adm.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.file.service.BizFileService;
import business.adm.invest.service.FundService;
import business.adm.invest.service.FundVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;

/**
 * [서비스클래스] - 투자정보관리 - 모태펀드등록 Service 구현 클래스
 * 
 * @class   : FundServiceImpl
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("FundService")
@SuppressWarnings({"all"})
public class FundServiceImpl extends BaseService implements FundService {

    @Resource(name = "FundDAO")
    private FundDAO fundDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * 모태펀드 페이징목록조회
     */
    @Override
    public PaginatedArrayList listFund(FundVO fundVO, int currPage, int pageSize) throws Exception {
    	return fundDAO.listFund(fundVO, currPage, pageSize);
    }

    /**
     * 모태펀드 목록조회
     */
    @Override
    public List listFund(FundVO fundVO) throws Exception {
    	return fundDAO.listFund(fundVO);
    }

    /**
     * 모태펀드 상세조회
     */
	@Override
	public FundVO viewFund(FundVO fundVO) throws Exception {
		return fundDAO.viewFund(fundVO);
	}

    /**
     * 모태펀드 등록,수정,삭제
     */
	@Override
	public String saveFund(FundVO fundVO) throws Exception {
		
		if (fundVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = fundVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 모태펀드 수정
			ret = fundDAO.updtFund(fundVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 모태펀드 등록
			ret = fundDAO.regiFund(fundVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 모태펀드 삭제
			ret = fundDAO.deltFund(fundVO);
		}
		/* LSH. 첨부파일 사용시 다음과 같이 처리할것
		if (ret > 0) {
			// 첨부파일 저장처리
			BizFileVO fileVO = BizFileVO.builder()
					.taskSeCd(CodeConst.TASK_FUND)
					.docNo   (fundVO.getFundNo())
					.dtlDocNo(fundVO.getBzentyNo())
					.gsUserNo(fundVO.getGsUserNo())
					.build();
			
			fileVO.setMode(mode);
			
			bizFileService.saveBizFile(fileVO, files);
		}
		*/

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	
	
	/**
     * 전체 조합원 페이징목록조회
     */
    @Override
    public PaginatedArrayList listInvt(FundVO fundVO, int currPage, int pageSize) throws Exception {
    	return fundDAO.listInvt(fundVO, currPage, pageSize);
    }
    
    /**
     * 전체 조합원 목록조회
     */
    @Override
    public List listInvt(FundVO fundVO) throws Exception {
    	return fundDAO.listInvt(fundVO);
    }
    
    
	
	/**
     * 특정 조합원 페이징목록조회
     */
    @Override
    public PaginatedArrayList listFundInvstr(FundVO fundVO, int currPage, int pageSize) throws Exception {
    	return fundDAO.listFundInvstr(fundVO, currPage, pageSize);
    }
    
    /**
     * 특정 조합원 목록조회
     */
    @Override
    public List listFundInvstr(FundVO fundVO) throws Exception {
    	return fundDAO.listFundInvstr(fundVO);
    }
    
    /**
     * 조합원 등록,삭제
     */
	@Override
	public String saveFundInvstr(FundVO fundVO) throws Exception {
		
		if (fundVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = fundVO.getMode();
		
		if (CommConst.INSERT.equals(mode)) {
			// 조합원 등록
			String[] bzentyNoArr = fundVO.getBzentyNoArr();
			if (bzentyNoArr != null && bzentyNoArr.length > 0) {
				for(int i=0 ; i<bzentyNoArr.length ; i++) {
					fundVO.setBzentyNo(bzentyNoArr[i]);
					
					ret = fundDAO.regiFundInvstr(fundVO);
				}
			} else {
				ret = fundDAO.regiFundInvstr(fundVO);
			}
			
		} else if (CommConst.DELETE.equals(mode)) {
			// 조합원 삭제
			ret = fundDAO.deltFundInvstr(fundVO);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}