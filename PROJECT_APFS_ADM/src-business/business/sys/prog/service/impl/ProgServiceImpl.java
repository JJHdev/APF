package business.sys.prog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.prog.service.ProgService;
import business.sys.prog.service.ProgVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 프로그램관리을 관리하는 Service 구현 클래스
 * 
 * @class   : ProgServiceImpl
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("ProgService")
@SuppressWarnings({"all"})
public class ProgServiceImpl extends BaseService implements ProgService {

    @Resource(name = "ProgDAO")
    private ProgDAO progDAO;
	
    /**
     * 프로그램관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listProg(ProgVO progVO, int currPage, int pageSize) throws Exception {
    	return progDAO.listProg(progVO, currPage, pageSize);
    }

    /**
     * 프로그램관리 목록조회
     */
    @Override
    public List listProg(ProgVO progVO) throws Exception {
    	return progDAO.listProg(progVO);
    }

    /**
     * 프로그램관리 상세조회
     */
	@Override
	public ProgVO viewProg(ProgVO progVO) throws Exception {
		return progDAO.viewProg(progVO);
	}

    /**
     * 프로그램관리 등록
     */
    private int regiProg(ProgVO progVO) throws Exception {
        return progDAO.regiProg(progVO);
    }

    /**
     * 프로그램관리 수정
     */
    private int updtProg(ProgVO progVO) throws Exception {
        return progDAO.updtProg(progVO);
    }

    /**
     * 프로그램관리 삭제
     */
    private int deltProg(ProgVO progVO) throws Exception {
        return progDAO.deltProg(progVO);
    }

    /**
     * 프로그램관리 등록,수정,삭제
     */
	@Override
	public String saveProg(ProgVO progVO) throws Exception {
		
		if (progVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode   = progVO.getMode();
		String progId = progVO.getPrgrmId();
		String[] args = { progId };
		
		if (CommConst.UPDATE.equals(mode)) {
			// 프로그램관리 수정
			ret = updtProg(progVO);
		}
		else if (CommConst.INSERT.equals(mode)) {

			// 프로그램ID 중복체크
			if (viewProg(progVO) != null) {
				// [{0}] 프로그램은 이미 등록되어 있습니다.
				throw processException("exception.adm.duplProg", args);
			}
			// 프로그램관리 등록
			ret = regiProg(progVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 프로그램관리 삭제
			ret = deltProg(progVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}