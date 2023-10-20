package business.com.authority.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.authority.AuthorityService;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * 서비스클래스 [권한체크]
 *
 * @class   : AuthorityService
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일         수정자                수정내용
 *  -------        --------      ---------------------------
 *
 */

@Service("AuthorityService")
@SuppressWarnings({"all"})
public class AuthorityServiceImpl extends BaseService implements AuthorityService {

    @Resource(name = "AuthorityDAO")
    private AuthorityDAO authorityDAO;

    /**
     * 페이지 접속권한 조회
     * @param paramMap
     * @return
     */
    public Map getAuthorityUser(Map paramMap) throws Exception {
        return (HashMap)authorityDAO.getAuthorityUser(paramMap);
    }

    /**
     * 페이지(프로그램) 정보 조회
     * @param paramMap
     * @return
     */
    public Map getProgInfo(Map paramMap) throws Exception {
    	// 메뉴정보를 포함하는 프로그램 정보 조회
        Map progInfo = (HashMap)authorityDAO.getProgInfoWithMenu(paramMap);
        
        if (progInfo == null) {
        	// 프로그램정보만 반환
        	return authorityDAO.getProgInfo(paramMap);
        }
        String[] menuPath   = CommUtils.split((String)progInfo.get("menuPath"), ",");
        String[] urlPath    = CommUtils.split((String)progInfo.get("urlPath"), ",");
        if (menuPath != null) {
            List pathList = new ArrayList();
            for (int i = 0; i < menuPath.length; i++) {
                Map pathmap = new HashMap();
                pathmap.put("menuPath", menuPath[i]);
                pathmap.put("urlPath", urlPath[i]);

                pathList.add(pathmap);
            }
	        progInfo.put("pathList", pathList);
        }
        return progInfo;
    }

    /**
     * 메뉴 정보 조회
     * @param paramMap
     * @return
     */
    public List getMenuList(Map paramMap) throws Exception {
        return (List)authorityDAO.getMenuList(paramMap);
    }
}
