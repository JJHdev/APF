package business.adm.inform.service;

import java.util.List;

import commf.paging.PaginatedArrayList;
import common.file.FileInfo;

/**
 * [서비스인터페이스] - 운영관리-배너관리 Service Interface
 * 
 * @class   : BannerService
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자             수정내용
 *  --------   --------    ---------------------------
 * 23.06.09      LHB           First Coding.
 */
@SuppressWarnings("all")
public interface BannerService {

    /**
     * 게시판 페이징목록 조회
     */
    public PaginatedArrayList listBanner(BannerVO bannerVO, int currPage, int pageSize) throws Exception;

    /**
     * 게시판 목록조회
     */
    public List listBanner(BannerVO bannerVO) throws Exception;

    /**
     * 게시판 상세조회
     */
    public BannerVO viewBanner(BannerVO bannerVO) throws Exception;

    /**
     * 게시판 등록,수정,삭제
     */
    public String saveBanner(BannerVO bannerVO, List<FileInfo> files) throws Exception;

}