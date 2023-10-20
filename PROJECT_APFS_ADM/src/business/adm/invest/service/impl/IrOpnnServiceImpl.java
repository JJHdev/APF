package business.adm.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.file.service.EntFileService;
import business.adm.file.service.EntFileVO;
import business.adm.invest.service.EventService;
import business.adm.invest.service.EventVO;
import business.adm.invest.service.IrOpnnService;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 투자정보관리-IR검토의견서관리 Service 구현 클래스
 * 
 * @class   : IrOpnnServiceImpl
 * @author  : LHB
 * @since   : 2023.06.07
 * @version : 1.0
 *
 *   수정일        수정자                수정내용
 *  --------   --------    ---------------------------
 * 23.06.07      LHB              First Coding.
 */

@Service("IrOpnnService")
@SuppressWarnings({"all"})
public class IrOpnnServiceImpl extends BaseService implements IrOpnnService {

    @Autowired
    private IrOpnnDAO irOpnnDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
    
    /**
     * IR검토의견서관리 투자자 페이징목록 조회
     */
    @Override
    public PaginatedArrayList listIrOpnnInvt(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return irOpnnDAO.listIrOpnnInvt(eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 투자자 목록 조회
     */
    @Override
    public List listIrOpnnInvt(EventVO eventVO) throws Exception {
    	return irOpnnDAO.listIrOpnnInvt(eventVO);
    }
    
    /**
     * IR검토의견서관리 경영체 페이징목록 조회
     */
    @Override
    public PaginatedArrayList listIrOpnnEnt(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return irOpnnDAO.listIrOpnnEnt(eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 경영체 목록 조회
     */
    @Override
    public List listIrOpnnEnt(EventVO eventVO) throws Exception {
    	return irOpnnDAO.listIrOpnnEnt(eventVO);
    }
    
    
	
    /**
     * IR검토의견서관리 의견서 페이징목록 조회
     */
    @Override
    public PaginatedArrayList listIrOpnn(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return irOpnnDAO.listIrOpnn(eventVO, currPage, pageSize);
    }

    /**
     * IR검토의견서관리 의견서 목록 조회
     */
    @Override
    public List listIrOpnn(EventVO eventVO) throws Exception {
    	return irOpnnDAO.listIrOpnn(eventVO);
    }

    /**
     * IR검토의견서관리 의견서 상세 조회
     */
	@Override
	public EventVO viewIrOpnn(EventVO eventVO) throws Exception {
		return irOpnnDAO.viewIrOpnn(eventVO);
	}
}