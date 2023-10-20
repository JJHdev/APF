package business.sys.prog.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 프로그램관리을 관리하는 Service Interface
 * 
 * @class   : ProgService
 * @author  : LSH
 * @since   : 2021.09.05
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface ProgService {

    /**
     * 프로그램관리 페이징목록 조회
     */
    public PaginatedArrayList listProg(ProgVO progVO, int currPage, int pageSize) throws Exception;

    /**
     * 프로그램관리 목록조회
     */
    public List listProg(ProgVO progVO) throws Exception;

    /**
     * 프로그램관리 상세조회
     */
    public ProgVO viewProg(ProgVO progVO) throws Exception;

    /**
     * 프로그램관리 등록,수정,삭제
     */
    public String saveProg(ProgVO progVO) throws Exception;
}