package business.com.authority.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.base.BaseDAO;

/**
 * DAO 클래스 [권한체크]
 *
 * @class   : AuthorityDAO
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일         수정자                수정내용
 *  -------        --------      ---------------------------
 *
 */

@Repository("AuthorityDAO")
@SuppressWarnings({"all"})
public class AuthorityDAO extends BaseDAO {

    @Resource(name = "AuthorityDAO")
    private AuthorityDAO authorityDAO;

    /**
     * 페이지 접속권한 조회
     * @param paramMap
     * @return
     */
    public Map getAuthorityUser(Map paramMap) {
        return (HashMap)authorityDAO.view("Authority.getAuthorityUser", paramMap);
    }

    /**
     * 페이지(프로그램/메뉴) 정보 조회
     * @param paramMap
     * @return
     */
    public Map getProgInfoWithMenu(Map paramMap) {
        return (HashMap)authorityDAO.view("Authority.getProgInfoWithMenu", paramMap);
    }

    /**
     * 페이지(프로그램) 정보 조회
     * @param paramMap
     * @return
     */
    public Map getProgInfo(Map paramMap) {
        return (HashMap)authorityDAO.view("Authority.getProgInfo", paramMap);
    }

    /**
     * 메뉴 정보 조회
     * @param paramMap
     * @return
     */
    public List getMenuList(Map paramMap) throws Exception {
        return (List)authorityDAO.list("Authority.getMenuList", paramMap);
    }
}
