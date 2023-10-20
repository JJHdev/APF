package business.usr.inform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.file.service.BbsFileService;
import business.usr.file.service.BbsFileVO;
import business.usr.inform.service.BbsService;
import business.usr.inform.service.BbsVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 게시판 Service 구현 클래스
 * 
 * @class   : BbsServiceImpl
 * @author  : JH
 * @since   : 2023.08.07
 * @version : 1.1
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("BbsService")
@SuppressWarnings({"all"})
public class BbsServiceImpl extends BaseService implements BbsService {

    @Resource(name = "BbsDAO")
    private BbsDAO bbsDAO;

    @Resource(name = "BbsFileService")
    private BbsFileService bbsFileService;
	
    /**
     * 게시판 페이징목록조회
     */
    @Override
    public PaginatedArrayList listBbs(BbsVO bbsVO, int currPage, int pageSize) throws Exception {
    	return bbsDAO.listBbs(bbsVO, currPage, pageSize);
    }

    /**
     * 게시판 목록조회
     */
    @Override
    public List listBbs(BbsVO bbsVO) throws Exception {
   	return bbsDAO.listBbs(bbsVO);
    }

    /**
     * 게시판 탭목록 조회
     */
    public List listBbsTab(BbsVO bbsVO) throws Exception {
    	
    	if (bbsVO == null)
    		return null;
    	// 투자자대상 회원인 경우
    	if (CommConst.isInvtTargetRole(bbsVO.getGsRoleId()))
    		bbsVO.setGsInvtYn(CommConst.YES);
    	
    	return bbsDAO.listBbsTab(bbsVO);
    }

    /**
     * 게시판 상세조회
     */
	@Override
	public BbsVO viewBbs(BbsVO bbsVO) throws Exception {
		
		BbsVO obj = bbsDAO.viewBbs(bbsVO);
		if (obj != null)
			obj.setGsContext(bbsVO.getGsContext());
		// 이미지경로치환
		_decodeBbs(obj);
		return obj;
	}
	
    /**
     * 게시판 등록,수정,삭제
     */
	@Override
	public String saveBbs(BbsVO bbsVO, List<FileInfo> files) throws Exception {
		
		if (bbsVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = bbsVO.getMode();

		// 2023.08.31 LSH Context Path 치환처리
		if (CommConst.UPDATE.equals(mode) ||
			CommConst.INSERT.equals(mode)) {
			_encodeBbs(bbsVO);
		}
		if (CommConst.UPDATE.equals(mode)) {
			// 게시판 수정
			ret = bbsDAO.updtBbs(bbsVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 게시판 등록
			ret = bbsDAO.regiBbs(bbsVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 게시판 삭제
			ret = bbsDAO.deltBbs(bbsVO);
		}
		if (ret > 0) {
			// 첨부파일 저장처리
			BbsFileVO fileVO = BbsFileVO.builder()
					.taskSeCd(bbsVO.getBbsSeCd())
					.pstNo   (bbsVO.getPstNo())
					.gsUserNo(bbsVO.getGsUserNo())
					.build();
			
			fileVO.setMode(mode);
			
			bbsFileService.saveBbsFile(fileVO, files);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	 /**
     * 홍보영상, 제목, 조회수 최신화
     */
	@Override
	public String updtPromotion(BbsVO bbsVO, List<FileInfo> files) throws Exception {
		int ret = bbsDAO.updtPromotion(bbsVO);
		// 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 1:1문의 관리자 답변 조회
     */
	@Override
	public BbsVO viewQnaBbs(BbsVO bbsVO) throws Exception {
		
		BbsVO obj = bbsDAO.viewQnaBbs(bbsVO);
		if (obj != null)
			obj.setGsContext(bbsVO.getGsContext());
		// 이미지경로치환
		_decodeBbs(obj);
		return obj;
	}

    /**
     * 1:1문의 유형 코드 조회
     */
	@Override
	public List getSysCodeData(BbsVO bbsVO) throws Exception {
		return bbsDAO.getSysCodeData(bbsVO);
	}

    /**
     * 우수투자사례 상세조회 이미지 번호 조회
     */
	@Override
	public BbsVO getBbsFileSn(BbsVO bbsVO) throws Exception {
		return bbsDAO.getBbsFileSn(bbsVO);
	}

    /**
     * 홍보영상 유튜브 목록 업데이트 유무 조회
     */
	@Override
	public int listBbsPromotionCount(BbsVO bbsVO) throws Exception {
		return bbsDAO.listBbsPromotionCount(bbsVO);
	}

    /**
     * 조회수 증가
     */
	@Override
	public int updtBbsInqCnt(BbsVO bbsVO) throws Exception {
		return bbsDAO.updtBbsInqCnt(bbsVO);
	}

    /**
     * 유튜브영상 최근 40개 삭제 되었는지 (유튜브 홈페이지에서 삭제시 DB에서 삭제용 List 조회)
     */
	@Override
	public List<BbsVO> listPromotionBbs(BbsVO bbsVO) throws Exception {
		return (List<BbsVO>)bbsDAO.listPromotionBbs(bbsVO);
	}

	//테스트용 홍보영상 전부 지우고 넣기
	@Override
	public void deleteTestBbs() throws Exception {
		bbsDAO.deleteTestBbs();
	}
	
	private void _decodeBbs(BbsVO obj) {
		// 2023.08.31 LSH Context Path 치환처리
		if (obj != null && 
			obj.getPstCn() != null) {
			String pcn = obj.getPstCn();
			String org = CommConst.CKEDITOR_IMAGE_CONTEXT;
			String trg = obj.getGsContext();
			obj.setPstCn(CommUtils.replace(pcn, org, trg));
		}
	}

	private void _encodeBbs(BbsVO obj) {
		// 2023.08.31 LSH Context Path 치환처리
		if (obj != null && 
			obj.getPstCn() != null) {
			String pcn = obj.getPstCn();
			String org = "src=\""+obj.getGsContext()+CommConst.CKEDITOR_IMAGE_URL+"?";
			String trg = "src=\""+CommConst.CKEDITOR_IMAGE_CONTEXT+CommConst.CKEDITOR_IMAGE_URL+"?";
			obj.setPstCn(CommUtils.replace(pcn, org, trg));
		}
	}

	//홍보영상 중복된거 찾기.	
	@Override
	public List<BbsVO> findDuplicatesByName(BbsVO bbsDataVO) throws Exception {
		return (List<BbsVO>)bbsDAO.findDuplicatesByName(bbsDataVO);
	}
	
	/**
	 * 홍보영상 같은 제목 있을시 임의로 삭제 요청
	 */
	@Override
	public void deletePromotion(BbsVO bbsVO, List<FileInfo> files) throws Exception {
		bbsDAO.deletePromotion(bbsVO,files);
	}

}