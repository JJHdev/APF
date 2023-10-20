package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.file.service.BizFileService;
import business.usr.invest.service.EventVO;
import business.usr.invest.service.OpnnIrService;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;

/**
 * [서비스클래스] - 투자정보관리-IR검토의견서관리 Service 구현 클래스
 * 
 * @class   : OpnnIrServiceImpl
 * @author  : LHB
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일        수정자                수정내용
 *  --------   --------    ---------------------------
 * 23.06.07      LHB              First Coding.
 */

@Service("OpnnIrService")
@SuppressWarnings({"all"})
public class OpnnIrServiceImpl extends BaseService implements OpnnIrService {

    @Autowired
    private OpnnIrDAO opnnIrDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * IR검토의견서관리 페이징목록 조회
     */
    @Override
    public PaginatedArrayList listOpnnIr(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return opnnIrDAO.listOpnnIr(eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 목록 조회
     */
    @Override
    public List listOpnnIr(EventVO eventVO) throws Exception {
    	return opnnIrDAO.listOpnnIr(eventVO);
    }

    /**
     * IR검토의견서관리 상세 조회
     */
	@Override
	public EventVO viewOpnnIr(EventVO eventVO) throws Exception {
		return opnnIrDAO.viewOpnnIr(eventVO);
	}
	
	 /**
     * IR검토의견서관리 등록
     */
	@Override
	public String saveOpnnIr(EventVO eventVO) throws Exception {
		
		if (eventVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = eventVO.getMode();
		String sprt = "#%%#";
		
		//변수 배열 풀어서 하나씩 insert
		String[] evntPartcptnNoArr = eventVO.getEvntPartcptnNoArr();
		
		String[] bizCnArr = eventVO.getBizCnArr().split(sprt, -1);
		String[] prdctCnArr = eventVO.getPrdctCnArr().split(sprt, -1);
		String[] coCnArr = eventVO.getCoCnArr().split(sprt, -1);
		String[] gnrlzOpnnArr = eventVO.getGnrlzOpnnArr().split(sprt, -1);
		
		for(int i=0; i<evntPartcptnNoArr.length; i++) {
			eventVO.setEvntPartcptnNo(evntPartcptnNoArr[i]);
			eventVO.setBizCn(bizCnArr[i]);
			eventVO.setPrdctCn(prdctCnArr[i]);
			eventVO.setCoCn(coCnArr[i]);
			eventVO.setGnrlzOpnn(gnrlzOpnnArr[i]);
			eventVO.setInvtItrstDgreeCd(eventVO.getInvtItrstDgreeCdArr()[i]);
			eventVO.setFllwMtgIntenYn(eventVO.getFllwMtgIntenYnArr()[i]);

			if (CommConst.INSERT.equals(mode)) {
				// 의견 등록
				ret = opnnIrDAO.regiOpnnIr(eventVO);
			} else if(CommConst.UPDATE.equals(mode)) {
				// 의견 수정
				ret = opnnIrDAO.updtOpnnIr(eventVO);
			}
			
		}
			
		// 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}