package business.adm.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BbsFileVO;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.file.service.EntFileService;
import business.adm.file.service.EntFileVO;
import business.adm.invest.service.EventService;
import business.adm.invest.service.InvestFieldService;
import business.adm.invest.service.InvestFieldVO;
import business.adm.invest.service.InvestFieldVO;
import business.adm.invest.service.IrOpnnService;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 투자정보관리-투자분야관리 Service 구현 클래스
 * 
 * @class   : InvestFieldServiceImpl
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일        수정자                수정내용
 *  --------   --------    ---------------------------
 * 23.06.09      LHB              First Coding.
 */

@Service("InvestFieldService")
@SuppressWarnings({"all"})
public class InvestFieldServiceImpl extends BaseService implements InvestFieldService {

    @Autowired
    private InvestFieldDAO investFieldDAO;
	
    /**
     * 투자정보관리-투자분야관리 페이징목록 조회
     */
    @Override
    public PaginatedArrayList listInvestField(InvestFieldVO investFieldVO, int currPage, int pageSize) throws Exception {
    	return investFieldDAO.listInvestField(investFieldVO, currPage, pageSize);
    }

    /**
     * 투자정보관리-투자분야관리 목록 조회
     */
    @Override
    public List listInvestField(InvestFieldVO investFieldVO) throws Exception {
    	return investFieldDAO.listInvestField(investFieldVO);
    }

    /**
     * 투자정보관리-투자분야관리 상세 조회
     */
	@Override
	public InvestFieldVO viewInvestField(InvestFieldVO investFieldVO) throws Exception {
		return investFieldDAO.viewInvestField(investFieldVO);
	}
	
	/**
     * 투자정보관리-투자분야관리 등록,수정,삭제
     */
	@Override
	public String saveInvestField(InvestFieldVO investFieldVO) throws Exception {
		
		if (investFieldVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = investFieldVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 배너관리 수정
			ret = investFieldDAO.updtInvestField(investFieldVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 배너관리 등록
			ret = investFieldDAO.regiInvestField(investFieldVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 배너관리 삭제
			ret = investFieldDAO.deltInvestField(investFieldVO);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}