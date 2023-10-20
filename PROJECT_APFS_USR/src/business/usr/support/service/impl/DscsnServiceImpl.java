package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.support.service.DscsnService;
import business.usr.support.service.DscsnVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 상담신청 Service 구현 클래스
 * 
 * @class   : DscsnServiceImpl
 * @author  : LSH
 * @since   : 2023.05.25
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("DscsnService")
@SuppressWarnings({"all"})
public class DscsnServiceImpl extends BaseService implements DscsnService {

    @Resource(name = "DscsnDAO")
    private DscsnDAO dscsnDAO;
	
    /**
     * 상담신청 페이징목록조회
     */
    @Override
    public PaginatedArrayList listDscsn(DscsnVO dscsnVO, int currPage, int pageSize) throws Exception {
    	return dscsnDAO.listDscsn(dscsnVO, currPage, pageSize);
    }

    /**
     * 상담신청 목록조회
     */
    @Override
    public List listDscsn(DscsnVO dscsnVO) throws Exception {
    	return dscsnDAO.listDscsn(dscsnVO);
    }

    /**
     * 상담신청 상세조회
     */
	@Override
	public DscsnVO viewDscsn(DscsnVO dscsnVO) throws Exception {
		return dscsnDAO.viewDscsn(dscsnVO);
	}

    /**
     * 상담신청 있는지 확인
     */
    @Override
    public boolean existDscsn(String dscsnAplyNo) {
    	return dscsnDAO.existDscsn(dscsnAplyNo);
    }

    /**
     * 상담신청 등록,수정
     */
	@Override
	public String saveDscsn(DscsnVO dscsnVO) throws Exception {
		
		if (dscsnVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = dscsnVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 상담신청 수정
			ret = dscsnDAO.updtDscsn(dscsnVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 상담신청 등록
			ret = dscsnDAO.regiDscsn(dscsnVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
			return dscsnVO.getDscsnAplyNo();
		else
			throw processException("error.comm.notProcess");
	}
}