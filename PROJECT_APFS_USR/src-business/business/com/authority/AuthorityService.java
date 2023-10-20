package business.com.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * 인터페이스클래스 [권한체크 Service]
 *
 * @class   : AuthorityService
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@SuppressWarnings("all")
public interface AuthorityService {

    /* 페이지 접속권한 조회 */
    public Map getAuthorityUser(Map paramMap)  throws Exception;

    /* 페이지(프로그램) 정보 조회 */
    public Map getProgInfo(Map paramMap)  throws Exception;

    /* 메뉴 정보 조회 */
    public List getMenuList(Map paramMap) throws Exception;

}