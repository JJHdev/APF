package business.sys.code.service;

import java.util.List;
import java.util.Map;

import commf.paging.PaginatedArrayList;

/**
 * [인터페이스 클래스] - 코드관리
 *
 * @class   : CodeService
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@SuppressWarnings({"all"})
public interface CodeService {

    /**
     * 코드리스트 조회
     */
    public List listCode(CodeVO codeVO) throws Exception;

    /**
     * 코드 페이징리스트 조회
     */
    public PaginatedArrayList listCode(CodeVO codeVO, int currPage, int pageSize) throws Exception;

    /**
     * 코드상세조회
     */
    public CodeVO viewCode(CodeVO codeVO) throws Exception;

    /**
     * 코드 저장 (등록,수정,삭제)
     */
    public String saveCode(CodeVO codeVO) throws Exception;
}