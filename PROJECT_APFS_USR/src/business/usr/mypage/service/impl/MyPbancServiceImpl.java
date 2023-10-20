package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.file.service.BizFileService;
import business.usr.file.service.BizFileVO;
import business.usr.mypage.service.MyPbancService;
import business.usr.mypage.service.MyPbancVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;
import common.util.CommUtils;

/**
 * [서비스클래스] - 사업공고관리 Service 구현 클래스
 * 
 * @class   : PbancServiceImpl
 * @author  : LSH
 * @since   : 2023.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("MyPbancService")
@SuppressWarnings({"all"})
public class MyPbancServiceImpl extends BaseService implements MyPbancService {

    @Resource(name = "MyPbancDAO")
    private MyPbancDAO myPbancDAO;
    
    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * 사업공고관리 페이징목록조회
     */
    @Override
    public PaginatedArrayList listPbanc(MyPbancVO myPbancVO, int currPage, int pageSize) throws Exception {
    	return myPbancDAO.listPbanc(myPbancVO, currPage, pageSize);
    }

    /**
     * 사업공고관리 목록조회
     */
    @Override
    public List listPbanc(MyPbancVO myPbancVO) throws Exception {
    	return myPbancDAO.listPbanc(myPbancVO);
    }

    /**
     * 사업공고관리 상세조회
     */
	@Override
	public MyPbancVO viewPbanc(MyPbancVO myPbancVO) throws Exception {
		return myPbancDAO.viewPbanc(myPbancVO);
	}

    /**
     * 사업공고관리 등록,수정,삭제
     */
	@Override
	public String savePbanc(MyPbancVO myPbancVO, List<FileInfo> files) throws Exception {
		
		if (myPbancVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = myPbancVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 사업공고관리 수정
			ret = myPbancDAO.updtPbanc(myPbancVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 사업공고관리 등록
			ret = myPbancDAO.regiPbanc(myPbancVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 사업공고관리 삭제
			ret = myPbancDAO.deltPbanc(myPbancVO);
		}
		
		if (ret > 0) {
			// 첨부파일 저장처리
			BizFileVO fileVO = BizFileVO.builder()
					.taskSeCd(CodeConst.TASK_PBNC)
					.docNo   (myPbancVO.getBizPbancNo())
					.dtlDocNo(myPbancVO.getCrdnsBzentyNo())
					.gsUserNo(myPbancVO.getGsUserNo())
					.build();
			
			fileVO.setMode(mode);
			
			bizFileService.saveBizFile(fileVO, files);
		}
		
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 마이페이지 사업공고관리 북마크 구분 탭 조회
     */
	@Override
	public List listPbancTab(MyPbancVO myPbancVO) {
		return myPbancDAO.listPbancTab(myPbancVO);
	}

    /**
     * 마이페이지 사업공고관리 세션 등록기관명 조회
     */
	@Override
	public MyPbancVO viewCrdnsBzentyNm(MyPbancVO myPbancVO) {
		return myPbancDAO.viewCrdnsBzentyNm(myPbancVO);
	}

}