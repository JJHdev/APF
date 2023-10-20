package business.usr.support.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 사업공고관리 Service Interface
 * 
 * @class   : PbancService
 * @author  : LSH
 * @since   : 2023.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface PbancService {

    /**
     * 사업공고관리 페이징목록 조회
     */
    public PaginatedArrayList listPbanc(PbancVO pbancVO, int currPage, int pageSize) throws Exception;

    /**
     * 사업공고관리 목록조회
     */
    public List listPbanc(PbancVO pbancVO) throws Exception;

    /**
     * 사업공고관리 상세조회
     */
    public PbancVO viewPbanc(PbancVO pbancVO) throws Exception;

    /**
     * 사업공고관리 등록,수정,삭제
     */
    public String savePbanc(PbancVO pbancVO) throws Exception;
}