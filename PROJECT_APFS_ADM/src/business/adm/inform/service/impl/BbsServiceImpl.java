package business.adm.inform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.file.service.BbsFileService;
import business.adm.file.service.BbsFileVO;
import business.adm.inform.service.BbsService;
import business.adm.inform.service.BbsVO;
import business.adm.inform.service.SrchWordVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 게시판 Service 구현 클래스
 * 
 * @class   : BbsServiceImpl
 * @author  : JH
 * @since   : 2023.07.11
 * @version : 1.2
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
     * 다음 페이지 조회
     */
	@Override
	public BbsVO nextBbs(BbsVO bbsVO) throws Exception {
		
		BbsVO obj = bbsDAO.nextBbs(bbsVO);
		if (obj != null)
			obj.setGsContext(bbsVO.getGsContext());
		// 이미지경로치환
		_decodeBbs(obj);
		return obj;
	}

    /**
     * 이전 페이지 조회
     */
	@Override
	public BbsVO beforeBbs(BbsVO bbsVO) throws Exception {
		
		BbsVO obj = bbsDAO.beforeBbs(bbsVO);
		if (obj != null)
			obj.setGsContext(bbsVO.getGsContext());
		// 이미지경로치환
		_decodeBbs(obj);
		return obj;
	}

    /**
     * 게시판 ComboBox코드 조회
     */
	@Override
	public List getSysCodeData(BbsVO bbsVO) throws Exception {
		return bbsDAO.getSysCodeData(bbsVO);
	}


	/**
     * 검색어 관리 페이징목록 조회
     */
	@Override
	public PaginatedArrayList getListSrchWord(SrchWordVO srchWordVO, int page, int size) throws Exception {
		return bbsDAO.getListSrchWord(srchWordVO, page, size);
	}

    /**
     * 검색어 관리  조회
     */
	@Override
	public List getListSrchWord(SrchWordVO srchWordVO) throws Exception {
		return bbsDAO.getListSrchWord(srchWordVO);
	}

    /**
     * 검색어 관리  삭제
     */
	@Override
	public int deleteSrchWord(SrchWordVO srchWordVO) throws Exception {
		return bbsDAO.deleteSrchWord(srchWordVO);
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

	@Override
	public List downSrchWordExcel(SrchWordVO srchWordVO) throws Exception {
		return bbsDAO.downSrchWordExcel(srchWordVO);
	}
}