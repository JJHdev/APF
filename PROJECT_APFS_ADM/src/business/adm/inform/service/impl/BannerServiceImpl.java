package business.adm.inform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BbsFileService;
import business.adm.file.service.BbsFileVO;
import business.adm.inform.service.BannerService;
import business.adm.inform.service.BannerVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 운영관리-배너관리 Service 구현 클래스
 * 
 * @class   : BannerServiceImpl
 * @author  : LHB
 * @since   : 2023.06.09
 * @version : 1.0
 *
 *   수정일       수정자                 수정내용
 *  --------   --------    ---------------------------
 * 23.06.09      LHB              First Coding.
 */

@Service("BannerService")
@SuppressWarnings({"all"})
public class BannerServiceImpl extends BaseService implements BannerService {

    @Resource(name = "BannerDAO")
    private BannerDAO bannerDAO;

    @Resource(name = "BbsFileService")
    private BbsFileService bbsFileService;
	
    /**
     * 배너관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listBanner(BannerVO bannerVO, int currPage, int pageSize) throws Exception {
    	return bannerDAO.listBanner(bannerVO, currPage, pageSize);
    }

    /**
     * 배너관리 목록조회
     */
    @Override
    public List listBanner(BannerVO bannerVO) throws Exception {
    	return bannerDAO.listBanner(bannerVO);
    }

    /**
     * 배너관리 상세조회
     */
	@Override
	public BannerVO viewBanner(BannerVO bannerVO) throws Exception {
		return bannerDAO.viewBanner(bannerVO);
	}

    /**
     * 배너관리 등록,수정,삭제
     */
	@Override
	public String saveBanner(BannerVO bannerVO, List<FileInfo> files) throws Exception {
		
		if (bannerVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = bannerVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 배너관리 수정
			ret = bannerDAO.updtBanner(bannerVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 배너관리 등록
			ret = bannerDAO.regiBanner(bannerVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 배너관리 삭제
			ret = bannerDAO.deltBanner(bannerVO);
		}
		
		if (ret > 0) {
			// 첨부파일 저장처리
			BbsFileVO bbsFileVO = BbsFileVO.builder()
					.taskSeCd(CodeConst.BBS_DTY_SE3)
					.pstNo   (bannerVO.getBannerNo())
					.fileSeCd("00")
					.gsUserNo(bannerVO.getGsUserNo())
					.build();
			
			bbsFileVO.setMode(mode);
			
			bbsFileService.saveBbsFile(bbsFileVO, files);
		}
		
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}