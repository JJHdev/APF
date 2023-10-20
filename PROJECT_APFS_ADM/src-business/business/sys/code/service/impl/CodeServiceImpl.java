package business.sys.code.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.code.service.CodeService;
import business.sys.code.service.CodeVO;
import business.sys.role.service.RoleVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스 클래스] - 코드관리
 *
 * @class   : CodeServiceImpl
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@Service("CodeService")
@SuppressWarnings({"all"})
public class CodeServiceImpl extends BaseService implements CodeService {

    @Resource(name = "CodeDAO")
    private CodeDAO codeDAO;

    /**
     *  코드리스트 조회
     */
    @Override
    public List listCode(CodeVO codeVO) throws Exception {
        return codeDAO.listCode(codeVO);
    }

    /**
     * 코드페이징리스트
     */
    @Override
    public PaginatedArrayList listCode(CodeVO codeVO, int currPage, int pageSize) throws Exception {
        return codeDAO.listCode(codeVO, currPage, pageSize);
    }
    
    /**
     * 코드상세조회
     */
	@Override
	public CodeVO viewCode(CodeVO codeVO) throws Exception {
		return codeDAO.viewCode(codeVO);
	}

    /**
     * 코드 저장 (등록, 수정, 삭제)
     */
    @Override
    public String saveCode(CodeVO codeVO) throws Exception {
		
		if (codeVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = codeVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 코드관리 수정
			ret = codeDAO.updtCode(codeVO);
			
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 코드관리 등록
			ret = codeDAO.regiCode(codeVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 최상위 코드관리 삭제
			ret = codeDAO.deltCode(codeVO);
			
			// 하위 코드관리 삭제
			if(CommUtils.isEqual(codeVO.getUpCdId(),CommConst.ROOT_CODE)){
			    
			    CodeVO underCodeVO = new CodeVO();
			    underCodeVO.setSrchType(codeVO.getCdId());
			    List<HashMap<String, Object>> underCodeList =  codeDAO.listUnderCode(underCodeVO);
			    
			    for (HashMap<String, Object> rowMap : underCodeList) {
			    	
			        CodeVO row = new CodeVO();
			        row.setUpCdId((String) rowMap.get("upCdId"));
			        row.setCdId((String) rowMap.get("cdId"));
			        
			        // 역할별메뉴관리 삭제
			        ret += codeDAO.deltCode(row);
			    }
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
    }

    /**
     * 코드를 등록한다.
     */
    private int regiCode(CodeVO codeVO) throws Exception {
    	
    	String cdId = codeVO.getCdId();
    	String upCdId = codeVO.getUpCdId();
    	
        // 상위 코드와 하위 코드가 동일한 경우
    	if (CommUtils.isEqual(cdId, upCdId)) {
            throw processException("exception.adm.sameUpperLowerCode", new String[] {cdId});
    	}
        int count = 0;

        // 코드를 확인한다.
        count = (Integer) codeDAO.confCode(codeVO);

        // 코드가 존재하는 경우
        if (count > 0) {
            // 상위 코드인 경우
            if (CommUtils.isEmpty(upCdId)) {
                throw processException("exception.adm.duplUpperCode", new String[] {cdId});
            }
            // 하위 코드인 경우
            else {
                throw processException("exception.adm.duplLowerCode", new String[] {upCdId, cdId});
            }
        }

        // 상위 코드를 확인한다.
        count = (Integer) codeDAO.confUpperCode(cdId);

        // 상위 코드가 존재하는 경우
        if (count > 0) {
            throw processException("exception.adm.useUpperCode", new String[] {cdId});
        }

        // 코드를 등록한다.
        return codeDAO.regiCode(codeVO);
    }

    /**
     * 코드를 수정한다.
     */
    private int updtCode(CodeVO codeVO) throws Exception {
        // 코드를 수정한다.
        return codeDAO.updtCode(codeVO);
    }

    /**
     * 코드를 삭제한다.
     */
    private int deltCode(CodeVO codeVO) throws Exception {
    	
    	String cdId = codeVO.getCdId();
    	
        // 하위 코드를 확인한다.
        int count = (Integer) codeDAO.confLowerCode(cdId);

        // 하위 코드가 존재하는 경우
        if (count > 0) {
            throw processException("exception.adm.hasLowerCode", new String[] {cdId});
        }

        // 코드를 삭제한다.
        return codeDAO.deltCode(codeVO);
    }

}