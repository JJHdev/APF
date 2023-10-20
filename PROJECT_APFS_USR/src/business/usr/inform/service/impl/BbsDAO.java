 package business.usr.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.usr.inform.service.BbsVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;
import common.file.FileInfo;

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
 * @class   : BbsDAO
 * @author  : JH
 * @since   : 2023.08.07
 * @version : 1.1
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("BbsDAO")
@SuppressWarnings({"all"})
public class BbsDAO extends BaseDAO {

    /**
     * 게시판 페이징목록 조회
     */
    public PaginatedArrayList listBbs(BbsVO bbsVO, int currPage, int pageSize) {
        return pageList("Bbs.listBbs", bbsVO, currPage, pageSize);
    }

    /**
     * 게시판 목록 조회
     */
    public List listBbs(BbsVO bbsVO) {
        return list("Bbs.listBbs", bbsVO);
    }

    /**
     * 게시판 탭목록 조회
     */
    public List listBbsTab(BbsVO bbsVO) {
        return list("Bbs.listBbsTab", bbsVO);
    }

    /**
     * 게시판 상세 조회
     */
    public BbsVO viewBbs(BbsVO bbsVO) {
        return (BbsVO)view("Bbs.viewBbs", bbsVO);
    }

    /**
     * 게시판 등록
     */
    public int regiBbs(BbsVO bbsVO) {
        return insert("Bbs.regiBbs", bbsVO);
    }

    /**
     * 게시판 수정
     */
    public int updtBbs(BbsVO bbsVO) {
        return update("Bbs.updtBbs", bbsVO);
    }

    /**
     * 게시판 삭제
     */
    public int deltBbs(BbsVO bbsVO) {
        return update("Bbs.deltBbs", bbsVO);
    }
    
    /**
     * 게시판 조회수 증가
     */
    public int updtBbsInqCnt(BbsVO bbsVO) {
        return update("Bbs.updtBbsInqCnt", bbsVO);
    }

    /**
     * 게시판 추천수 증가
     */
    public int updtBbsRecmdtCnt(BbsVO bbsVO) {
        return update("Bbs.updtBbsRecmdtCnt", bbsVO);
    }

    /**
     * 1:1문의 관리자 답변 조회
     */
	public BbsVO viewQnaBbs(BbsVO bbsVO) {
		return (BbsVO)view("Bbs.viewQnaBbs", bbsVO);
	}

    /**
     * 1:1문의 유형 코드 조회
     */
	public List getSysCodeData(BbsVO bbsVO) {
		return list("Bbs.getSysCode", bbsVO);
	}

    /**
     * 우수투자사례 상세조회 이미지 번호 조회
     */
	public BbsVO getBbsFileSn(BbsVO bbsVO) {
	    List<String> files = getSqlSession().selectList("Bbs.getBbsFileSn", bbsVO);
	    bbsVO.setFiles(files);
		return bbsVO;
	}

    /**
     *  홍보영상 유튜브 목록 업데이트 유무 조회
     */
	public int listBbsPromotionCount(BbsVO bbsVO) {
		return (int) selectOne("Bbs.listBbsPromotionCount", bbsVO);
	}

    /**
     * 유튜브영상 최근 40개 삭제 되었는지 (유튜브 홈페이지에서 삭제시 DB에서 삭제용 List 조회)
     */
	public List<BbsVO> listPromotionBbs(BbsVO bbsVO) {
		return (List<BbsVO>)list("Bbs.listPromotionBbs", bbsVO);
	}

	//테스트용 홍보영상 전부 지우고 넣기
	public void deleteTestBbs() {
		delete("Bbs.deleteTestBbs");
	}
	 /**
     * 홍보영상, 제목, 조회수 최신화
     */
	public int updtPromotion(BbsVO bbsVO) {
		 return update("Bbs.updtPromotion", bbsVO);
	}
	
	//홍보영상 중복된거 찾기.	
	public List<BbsVO> findDuplicatesByName(BbsVO bbsDataVO) {
		return (List<BbsVO>)list("Bbs.findDuplicatesByName", bbsDataVO);
	}
	
	/**
	 * 홍보영상 같은 제목 있을시 임의로 삭제 요청
	 */
	public void deletePromotion(BbsVO bbsVO, List<FileInfo> files) {
		delete("Bbs.deletePromotion", bbsVO);
	}
	
}