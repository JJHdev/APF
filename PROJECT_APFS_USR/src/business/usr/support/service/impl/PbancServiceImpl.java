package business.usr.support.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.support.service.PbancService;
import business.usr.support.service.PbancVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 사업공고관리 Service 구현 클래스
 * 
 * @class   : PbancServiceImpl
 * @author  : LSH
 * @since   : 2023.04.30
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
	
    /**
     * 사업공고관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listPbanc(PbancVO pbancVO, int currPage, int pageSize) throws Exception {
    	return pbancDAO.listPbanc(pbancVO, currPage, pageSize);
    }

    /**
     * 사업공고관리 목록조회
     */
    @Override
    public List listPbanc(PbancVO pbancVO) throws Exception {
    	return pbancDAO.listPbanc(pbancVO);
    }

    /**
     * 사업공고관리 상세조회
     */
	@Override
	public PbancVO viewPbanc(PbancVO pbancVO) throws Exception {
		return pbancDAO.viewPbanc(pbancVO);
	}

    /**
     * 사업공고관리 등록,수정,삭제
     */
	@Override
	public String savePbanc(PbancVO pbancVO) throws Exception {
		
		if (pbancVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = pbancVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 사업공고관리 수정
			ret = pbancDAO.updtPbanc(pbancVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 사업공고관리 등록
			ret = pbancDAO.regiPbanc(pbancVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 사업공고관리 삭제
			ret = pbancDAO.deltPbanc(pbancVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}