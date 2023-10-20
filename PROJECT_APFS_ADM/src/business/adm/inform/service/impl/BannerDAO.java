 package business.adm.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BannerVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 게시판을 관리하는 DAO 클래스
 * 
 * 사용 가능한  DAO Statement Method
 * 1. list          : 리스트 조회시 사용함.
 * 2. pageListOra   : 페이징처리용 리스트조회시 사용함. for Oracle, Tibero
 * 3. view          : 단건조회, 상세조회시 사용함.
 * 4. save          : INSERT, UPDATE, DELETE 모두 사용가능. (Return Type : Integer)
 * 5. insert        : INSERT (Return String : Key 채번 사용함.)
 * 6. update        : UPDATE (Return Type : Integer)
 * 7. delete        : DELETE (Return Type : Integer)
 * 
 *
 * @class   : BannerDAO
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 * 23.06.09      LHB            First Coding.
 */

@Repository("BannerDAO")
@SuppressWarnings({"all"})
public class BannerDAO extends BaseDAO {

    /**
     * 배너관리 페이징목록 조회
     */
    public PaginatedArrayList listBanner(BannerVO bannerVO, int currPage, int pageSize) {
        return pageList("Banner.listBanner", bannerVO, currPage, pageSize);
    }

    /**
     * 배너관리 목록 조회
     */
    public List listBanner(BannerVO bannerVO) {
        return list("Banner.listBanner", bannerVO);
    }

    /**
     * 배너관리 상세 조회
     */
    public BannerVO viewBanner(BannerVO bannerVO) {
        return (BannerVO)view("Banner.viewBanner", bannerVO);
    }

    /**
     * 배너관리 등록
     */
    public int regiBanner(BannerVO bannerVO) {
        return insert("Banner.regiBanner", bannerVO);
    }

    /**
     * 배너관리 수정
     */
    public int updtBanner(BannerVO bannerVO) {
        return update("Banner.updtBanner", bannerVO);
    }

    /**
     * 배너관리 삭제
     */
    public int deltBanner(BannerVO bannerVO) {
        return update("Banner.deltBanner", bannerVO);
    }
    
}