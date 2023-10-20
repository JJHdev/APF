 package business.usr.invest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 업체정보을 관리하는 DAO 클래스
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
 * @class   : EntDAO
 * @author  : LSH
 * @since   : 2023.04.27
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("EntDAO")
@SuppressWarnings({"all"})
public class EntDAO extends BaseDAO {

    /**
     * 업체정보 페이징목록 조회
     */
    public PaginatedArrayList listEnt(EntVO entVO, int currPage, int pageSize) {
        return pageList("Ent.listEnt", entVO, currPage, pageSize);
    }

    /**
     * 업체정보 목록 조회
     */
    public List listEnt(EntVO entVO) {
        return list("Ent.listEnt", entVO);
    }

    /**
     * 북마크한 업체정보 페이징목록 조회
     */
    public PaginatedArrayList listEntBkmk(EntVO entVO, int currPage, int pageSize) {
        return pageList("Ent.listEntBkmk", entVO, currPage, pageSize);
    }

    /**
     * 업체정보 상세 조회
     */
    public EntVO viewEnt(EntVO entVO) {
        return (EntVO)view("Ent.viewEnt", entVO);
    }

    /**
     * 2023.07.25 LSH
     * IR작성용 업체정보 상세조회
     */
    public EntVO viewEntForIr(EntVO entVO) {
        return (EntVO)view("Ent.viewEntForIr", entVO);
    }

    /**
     * 업체정보 등록
     */
    public int regiEnt(EntVO entVO) {
        return insert("Ent.regiEnt", entVO);
    }

    /**
     * 업체정보 수정
     */
    public int updtEnt(EntVO entVO) {
        return update("Ent.updtEnt", entVO);
    }

    /**
     * 업체정보 삭제
     */
    public int deltEnt(EntVO entVO) {
        return delete("Ent.deltEnt", entVO);
    }
    
    /**
     * 업체분야정보(투자분야/사업분야) 등록
     */
    public int regiEntRlm(EntVO entVO) {
        return insert("Ent.regiEntRlm", entVO);
    }

    /**
     * 업체분야정보(투자분야/사업분야) 삭제
     * (업체번호/분야구분 기준)
     */
    public int deltEntRlm(EntVO entVO) {
        return delete("Ent.deltEntRlm", entVO);
    }

    /**
     * 업체분야정보(투자분야/사업분야) 업체번호 기준 삭제
     * (업체번호/분야구분 기준)
     */
    public int deltEntRlmByBzentyNo(String bzentyNo) {
        return delete("Ent.deltEntRlmByBzentyNo", bzentyNo);
    }

	/**
	 * 2023.07.17 LSH
	 * 사업자번호 기준 업체번호/업체유형/대표여부를 조회한다.
	 * 
	 * @param  brno           사업자번호
	 * @return map.bzentyNo   업체번호
	 * @return map.bzentySeCd 업체유형
	 * @return map.bzentyNm   회사명
	 * @return map.rprsvNm    대표자
	 * @return map.fndnYmd    설립일
	 * @return map.rprsTelno  대표번호
	 * @return map.rprsYn     대표여부
	 */
	public Map<String,Object> viewEntByBrno(String brno) throws Exception {
		return (Map)view("Ent.viewEntByBrno", brno);
	}

	/**
     * 2023.07.18 LSH
     * 회원가입 - KODATA 조회시 해당 사업자번호 기준으로 투자지원신청정보 업데이트 처리
     * 
     * @param entVO.brno     사업자번호 (조회조건)
     * @param entVO.userNo   사용자번호
     * @param entVO.bzentyNo 업체번호
     */
    public int updtSprtByBrno(EntVO entVO) {
    	return update("Ent.updtSprtByBrno", entVO);
    }

	/**
     * 2023.07.19 LSH
     * 회원가입 - KODATA 조회시 해당 사업자번호 기준으로 행사참여경영체 업데이트 처리
     * 
     * @param entVO.brno     사업자번호 (조회조건)
     * @param entVO.userNo   사용자번호
     * @param entVO.bzentyNo 업체번호
     */
    public int updtEventByBrno(EntVO entVO) {
    	return update("Ent.updtEventByBrno", entVO);
    }

	/**
     * 2023.07.19 LSH
     * 회원가입 - KODATA 조회시 해당 사업자번호 기준으로 펀드투자자 업데이트 처리
     * 
     * @param entVO.brno     사업자번호 (조회조건)
     * @param entVO.userNo   사용자번호
     * @param entVO.bzentyNo 업체번호
     */
    public int updtFundInvstrByBrno(EntVO entVO) {
    	return update("Ent.updtFundInvstrByBrno", entVO);
    }
    
    /**
     * 2023.07.28 LSH
     * 업체정보 조회이력 등록
     */
    public int regiEntInqHst(EntVO entVO) {
        return insert("Ent.regiEntInqHst", entVO);
    }

	/**
     * 2023.09.05 LSH
     * 반려 업체의 정보 수정 처리
     * 
     * @param entVO.bzentySeCd  업체유형
     * @param entVO.invtHopeAmt 투자희망금액
     * @param entVO.gsUserNo    사용자번호
     * @param entVO.bzentyNo    업체번호
     */
    public int updtEntCmpl(EntVO entVO) {
    	return update("Ent.updtEntCmpl", entVO);
    }
}