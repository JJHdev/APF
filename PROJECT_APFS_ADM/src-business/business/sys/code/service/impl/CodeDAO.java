package business.sys.code.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.sys.code.service.CodeVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO 클래스] - 코드관리
 *
 * @class   : CodeDAO
 * @author  : JH
 * @since   : 2023.07.21
 * @version : 1.1
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("CodeDAO")
@SuppressWarnings({"all"})
public class CodeDAO extends BaseDAO {

    /**
     *  코드리스트 조회
     */
    public List listCode(CodeVO codeVO) throws Exception {
        return list("Code.listCode", codeVO);
    }

    /**
     * 코드페이징리스트
     */
    public PaginatedArrayList listCode(CodeVO codeVO, int currPage, int pageSize) {
        return pageList("Code.listCode", codeVO, currPage, pageSize);
    }

    /**
     * 2021.09.03 LSH 추가
     * 코드상세조회
     */
    public CodeVO viewCode(CodeVO codeVO) {
        return (CodeVO)view("Code.viewCode", codeVO);
    }

    /**
     * 코드 중복확인(코드)
     */
    public int confCode(CodeVO codeVO) {
        return (Integer)view("Code.confCode", codeVO);
    }

    /**
     * 코드 중복확인(상위코드)
     */
    public int confUpperCode(String cdId) {
        return (Integer)view("Code.confUpperCode", cdId);
    }

    /**
     * 코드 중복확인(하위코드)
     */
    public int confLowerCode(String cdId) {
        return (Integer)view("Code.confLowerCode", cdId);
    }

    /**
     * 코드 등록
     */
    public int regiCode(CodeVO codeVO) {
        return save("Code.regiCode", codeVO);
    }

    /**
     * 코드 수정
     */
    public int updtCode(CodeVO codeVO) {
        return save("Code.updtCode", codeVO);
    }

    /**
     * 코드 삭제
     */
    public int deltCode(CodeVO codeVO) {
        return delete("Code.deltCode", codeVO);
    }
    
    /**
     *  특정 상위코드에 해당하는 코드 리스트 조회
     */
    public List listCodeByUpper(Map map) {
        return list("Comm.listCodeByUpper", map);
    }

	public List listUnderCode(CodeVO underCodeVO) {
        return list("Code.listUnderCode", underCodeVO);
    }
}