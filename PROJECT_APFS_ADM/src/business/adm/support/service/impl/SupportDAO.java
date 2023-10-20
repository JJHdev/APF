 package business.adm.support.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.support.service.SupportVO;
import business.adm.support.service.SupportVO;
import business.adm.support.service.SupportVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 투자정보관리 - 모태펀드등록을 관리하는 DAO 클래스
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
 * @class   : SupportDAO
 * @author  : LHB
 * @since   : 2023.04.20
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("SupportDAO")
@SuppressWarnings({"all"})
public class SupportDAO extends BaseDAO {
	
    /**
     * 세부지원사업관리 페이징목록 조회
     */
    public PaginatedArrayList listInvtSprt(SupportVO supportVO, int currPage, int pageSize) {
        return pageList("Support.listInvtSprt", supportVO, currPage, pageSize);
    }

    /**
     * 세부지원사업관리 목록 조회
     */
    public List listInvtSprt(SupportVO supportVO) {
        return list("Support.listInvtSprt", supportVO);
    }

    /**
     * 세부지원사업관리 상세 조회
     */
    public SupportVO viewInvtSprt(SupportVO supportVO) {
        return (SupportVO) view("Support.viewInvtSprt", supportVO);
    }

    /**
     * 세부지원사업관리 등록
     */
    public int regiInvtSprt(SupportVO supportVO) {
        return insert("Support.regiInvtSprt", supportVO);
    }

    /**
     * 세부지원사업관리 수정
     */
    public int updtInvtSprt(SupportVO supportVO) {
        return update("Support.updtInvtSprt", supportVO);
    }

    /**
     * 세부지원사업관리 삭제
     */
    public int deltInvtSprt(SupportVO supportVO) {
        return delete("Support.deltInvtSprt", supportVO);
    }
    
    
    
    /**
     * 신청현황 페이징목록 조회
     */
    public PaginatedArrayList listInvtSprtAply(SupportVO supportVO, int currPage, int pageSize) {
        return pageList("Support.listInvtSprtAply", supportVO, currPage, pageSize);
    }

    /**
     * 신청현황 목록 조회
     */
    public List listInvtSprtAply(SupportVO supportVO) {
        return list("Support.listInvtSprtAply", supportVO);
    }

    /**
     * 신청현황 상세 조회
     */
    public SupportVO viewInvtSprtAply(SupportVO supportVO) {
        return (SupportVO) view("Support.viewInvtSprtAply", supportVO);
    }

    /**
     * 신청현황 등록
     */
    public int regiInvtSprtAply(SupportVO supportVO) {
        return insert("Support.regiInvtSprtAply", supportVO);
    }

    /**
     * 신청현황 수정
     */
    public int updtInvtSprtAply(SupportVO supportVO) {
        return update("Support.updtInvtSprtAply", supportVO);
    }

    /**
     * 신청현황 삭제
     */
    public int deltInvtSprtAply(SupportVO supportVO) {
        return delete("Support.deltInvtSprtAply", supportVO);
    }
    
    /**
     * 신청현황 엑셀 목록 조회
     */
    public List listInvtSprtAplyExcl(SupportVO supportVO) {
        return list("Support.listInvtSprtAplyExcl", supportVO);
    }
    
    
    
    /**
     * 신청현황 매출액 페이징목록 조회
     */
    public PaginatedArrayList listInvtSprtSls(SupportVO supportVO, int currPage, int pageSize) {
        return pageList("Support.listInvtSprtSls", supportVO, currPage, pageSize);
    }

    /**
     * 신청현황 매출액 목록 조회
     */
    public List listInvtSprtSls(SupportVO supportVO) {
        return list("Support.listInvtSprtSls", supportVO);
    }
    
    
    
    /**
     * 농식품 투자조합 페이징목록 조회
     */
    public PaginatedArrayList listInvtMxtr(SupportVO supportVO, int currPage, int pageSize) {
        return pageList("Support.listInvtMxtr", supportVO, currPage, pageSize);
    }

    /**
     * 농식품 투자조합 페이징목록 조회
     */
    public List listInvtMxtr(SupportVO supportVO) {
        return list("Support.listInvtMxtr", supportVO);
    }
    
    
    
    /**
     * 지원사업 진행현황 페이징목록 조회
     */
    public PaginatedArrayList listSprtBizPrgre(SupportVO supportVO, int currPage, int pageSize) {
        return pageList("Support.listSprtBizPrgre", supportVO, currPage, pageSize);
    }

    /**
     * 지원사업 진행현황 목록 조회
     */
    public List listSprtBizPrgre(SupportVO supportVO) {
        return list("Support.listSprtBizPrgre", supportVO);
    }

    /**
     * 지원사업 진행현황 등록
     */
    public int regiSprtBizPrgre(SupportVO supportVO) {
        return insert("Support.regiSprtBizPrgre", supportVO);
    }

    /**
     * 지원사업 진행현황 수정
     */
    public int updtSprtBizPrgre(SupportVO supportVO) {
        return update("Support.updtSprtBizPrgre", supportVO);
    }

    /**
     * 지원사업 진행현황 삭제
     */
    public int deltSprtBizPrgre(SupportVO supportVO) {
        return delete("Support.deltSprtBizPrgre", supportVO);
    }

    
	//2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 데이터조회
	public SupportVO viewInvtSprtAplyFileInfo(SupportVO supportVO) {
		return (SupportVO) view("Support.viewInvtSprtAplyFileInfo", supportVO);
	}
    //2023.09.19 지원사업관리- 신청현황관리 첨부파일 수정용 메소드
	public int updtSprt(SupportVO supportVO) {
		return update("Support.updtSprt", supportVO);
	}
    
}