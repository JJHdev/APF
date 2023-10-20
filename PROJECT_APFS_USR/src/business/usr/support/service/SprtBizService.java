package business.usr.support.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 지원사업이력 Service Interface
 * 
 * @class   : SprtBizService
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface SprtBizService {

    /**
     * 지원사업이력 페이징목록 조회
     */
    public PaginatedArrayList listSprtBiz(SprtBizVO sprtBizVO, int currPage, int pageSize) throws Exception;

    /**
     * 지원사업이력 목록조회
     */
    public List listSprtBiz(SprtBizVO sprtBizVO) throws Exception;

    /**
     * 지원사업이력 상세조회
     */
    public SprtBizVO viewSprtBiz(SprtBizVO sprtBizVO) throws Exception;

    /**
     * 지원사업이력 등록,수정,삭제
     */
    public String saveSprtBiz(SprtBizVO sprtBizVO) throws Exception;
}