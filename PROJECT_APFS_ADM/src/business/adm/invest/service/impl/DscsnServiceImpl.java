package business.adm.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BbsFileVO;
import business.adm.invest.service.DscsnService;
import business.adm.invest.service.DscsnVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 운영관리 - 상담일지 Service 구현 클래스
 * 
 * @class   : DscsnServiceImpl
 * @author  : JH
 * @since   : 2023.06.27
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
     * 세부지원사업관리 페이징목록조회
     */
	@Override
	public PaginatedArrayList listDscsn(DscsnVO dscsnVO, int page, int size) throws Exception {
		return dscsnDAO.listDscsn(dscsnVO, page, size);
	}

    /**
     * 세부지원사업관리 목록조회
     */
	@Override
	public List listDscsn(DscsnVO dscsnVO) throws Exception {
    	return dscsnDAO.listDscsn(dscsnVO);
	}

    /**
     * 상담일지 상세조회
     */
	@Override
	public DscsnVO viewDscsn(DscsnVO dscsnVO) throws Exception {
		return dscsnDAO.viewBbs(dscsnVO);
	}

    /**
     * 상담일지 등록,수정,삭제
     * @throws Exception 
     */
	@Override
	public String saveDscsn(DscsnVO dscsnVO) throws Exception {
		
		if (dscsnVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = dscsnVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 상담일지 수정
			ret = dscsnDAO.updtDscsn(dscsnVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 상담일지 등록
			ret = dscsnDAO.regiDscsn(dscsnVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 상담일지 삭제
			ret = dscsnDAO.deltDscsn(dscsnVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

}