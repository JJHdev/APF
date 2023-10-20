 package business.adm.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.inform.service.SrchWordVO;
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
 * @class   : BbsDAO
 * @author  : JH
 * @since   : 2023.07.11
 * @version : 1.2
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
     * 다음 페이지 조회
     */
	public BbsVO nextBbs(BbsVO bbsVO) {
		return (BbsVO)view("Bbs.nextBbs", bbsVO);
	}

    /**
     * 이전 페이지 조회
     */
	public BbsVO beforeBbs(BbsVO bbsVO) {
		return (BbsVO)view("Bbs.beforeBbs", bbsVO);
	}

    /**
     * 게시판 ComboBox코드 조회
     */
	public List getSysCodeData(BbsVO bbsVO) {
		return list("Bbs.getSysCode", bbsVO);
	}

	
    /**
     * 게시판 페이징목록 조회
     */
    public PaginatedArrayList getListSrchWord(SrchWordVO srchWordVO, int currPage, int pageSize) {
        return pageList("Bbs.listSrchWord", srchWordVO, currPage, pageSize);
    }

    /**
     * 게시판 목록 조회
     */
    public List getListSrchWord(SrchWordVO srchWordVO) {
        return list("Bbs.listSrchWord", srchWordVO);
    }

	public int deleteSrchWord(SrchWordVO srchWordVO) {
		return delete("Bbs.deleteSrchWord", srchWordVO);
	}

	public List downSrchWordExcel(SrchWordVO srchWordVO) {
		 return list("Bbs.downSrchWordExcel", srchWordVO);
	}
    
}