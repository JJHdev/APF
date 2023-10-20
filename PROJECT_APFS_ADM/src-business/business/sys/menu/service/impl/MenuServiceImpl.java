package business.sys.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.sys.code.service.CodeVO;
import business.sys.menu.service.MenuService;
import business.sys.menu.service.MenuVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 메뉴관리을 관리하는 Service 구현 클래스
 * 
 * @class   : MenuServiceImpl
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("MenuService")
@SuppressWarnings({"all"})
public class MenuServiceImpl extends BaseService implements MenuService {

    @Resource(name = "MenuDAO")
    private MenuDAO menuDAO;
	
    /**
     * 메뉴관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listMenu(MenuVO menuVO, int currPage, int pageSize) throws Exception {
    	return menuDAO.listMenu(menuVO, currPage, pageSize);
    }

    /**
     * 메뉴관리 목록조회
     */
    @Override
    public List listMenu(MenuVO menuVO) throws Exception {
    	return menuDAO.listMenu(menuVO);
    }

    /**
     * 메뉴관리 상세조회
     */
	@Override
	public MenuVO viewMenu(MenuVO menuVO) throws Exception {
		return menuDAO.viewMenu(menuVO);
	}

    /**
     * 메뉴관리 등록
     */
    private int regiMenu(MenuVO menuVO) throws Exception {
        return menuDAO.regiMenu(menuVO);
    }

    /**
     * 메뉴관리 수정
     */
    private int updtMenu(MenuVO menuVO) throws Exception {
        return menuDAO.updtMenu(menuVO);
    }

    /**
     * 메뉴관리 삭제
     */
    private int deltMenu(MenuVO menuVO) throws Exception {
        return menuDAO.deltMenu(menuVO);
    }

    /**
     * 메뉴관리 등록,수정,삭제
     */
	@Override
	public String saveMenu(MenuVO menuVO) throws Exception {
		
		if (menuVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode   = menuVO.getMode();
		String menuId = menuVO.getMenuId();
		String[] args = { menuId };
		
		if (CommConst.UPDATE.equals(mode)) {
			// 메뉴관리 수정
			ret = updtMenu(menuVO);
		}
		else if (CommConst.INSERT.equals(mode)) {

			// 메뉴ID 중복체크
			if (viewMenu(menuVO) != null) {
				// [{0}] 메뉴는 이미 등록되어 있습니다.
				throw processException("exception.adm.duplMenu", args);
			}
			// 메뉴관리 등록
			ret = regiMenu(menuVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			
			// 메뉴관리 삭제
			ret = deltMenu(menuVO);
			
			// 하위 코드관리 삭제
			if(CommUtils.isEqual(menuVO.getUpMenuId(),CommConst.ROOT_CODE)){
			    
				MenuVO underMenuVO = new MenuVO();
				underMenuVO.setUnderMenuId(menuVO.getMenuId());
			    List<HashMap<String, Object>> underMenuList =  menuDAO.listUnderMenu(underMenuVO);
			    
			    for (HashMap<String, Object> rowMap : underMenuList) {
			    	
			    	MenuVO row = new MenuVO();
			        row.setMenuId((String) rowMap.get("menuId"));

			        // 역할별메뉴관리 삭제
			        ret += menuDAO.deltMenu(row);
			    }
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}