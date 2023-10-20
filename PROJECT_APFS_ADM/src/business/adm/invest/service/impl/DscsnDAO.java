 package business.adm.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.invest.service.DscsnVO;
import business.adm.invest.service.SubmitFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
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
 * @class   : DscsnDAO
 * @author  : JH
 * @since   : 2023.06.27
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("DscsnDAO")
@SuppressWarnings({"all"})
public class DscsnDAO extends BaseDAO {

    /**
     * 상담일지 페이징목록 조회
     */
	public PaginatedArrayList listDscsn(DscsnVO dscsnVO, int page, int size) {
		return pageList("Dscsn.listDscsn", dscsnVO, page, size);
	}

    /**
     * 상담일지 관리 목록 조회
     */
	public List listDscsn(DscsnVO dscsnVO) {
		return list("Dscsn.listDscsn", dscsnVO);
	}

    /**
     * 상담일지 상세조회
     */
	public DscsnVO viewBbs(DscsnVO dscsnVO) {
		return (DscsnVO)view("Dscsn.viewDscsn", dscsnVO);
	}
	
	/**
	 * 상담일지 등록
	 */
	public int regiDscsn(DscsnVO dscsnVO) {
		return insert("Dscsn.regiDscsn", dscsnVO);
	}

    /**
     * 상담일지 수정
     */
	public int updtDscsn(DscsnVO dscsnVO) {
		return update("Dscsn.updtDscsn", dscsnVO);
	}

    /**
     * 상담일지 삭제
     */
	public int deltDscsn(DscsnVO dscsnVO) {
		return delete("Dscsn.deltDscsn", dscsnVO);
	}

    
}