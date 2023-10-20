package business.usr.support.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 상담신청 Service Interface
 * 
 * @class   : DscsnService
 * @author  : LSH
 * @since   : 2023.05.25
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface DscsnService {

    /**
     * 상담신청 페이징목록 조회
     */
    public PaginatedArrayList listDscsn(DscsnVO dscsnVO, int currPage, int pageSize) throws Exception;

    /**
     * 상담신청 목록조회
     */
    public List listDscsn(DscsnVO dscsnVO) throws Exception;

    /**
     * 상담신청 상세조회
     */
    public DscsnVO viewDscsn(DscsnVO dscsnVO) throws Exception;

    /**
     * 상담신청 있는지 확인
     */
    public boolean existDscsn(String dscsnAplyNo);

    /**
     * 상담신청 등록,수정,삭제
     */
    public String saveDscsn(DscsnVO dscsnVO) throws Exception;
}