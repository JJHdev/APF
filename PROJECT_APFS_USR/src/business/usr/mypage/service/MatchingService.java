package business.usr.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 매칭설정관리 Service Interface
 * 
 * @class   : MatchingService
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface MatchingService {

    /**
     * 매칭설정관리 목록조회
     */
    public List listMatching(MatchingVO matchingVO) throws Exception;

    /**
     * 매칭설정관리 등록,수정,삭제
     */
    public String saveMatching(MatchingVO matchingVO) throws Exception;

    /**
     * 매칭설정여부 확인
     */
    public boolean existMatching(String userNo) throws Exception;
}