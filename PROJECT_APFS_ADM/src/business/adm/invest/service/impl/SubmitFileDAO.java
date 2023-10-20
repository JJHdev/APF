 package business.adm.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.invest.service.DscsnVO;
import business.adm.invest.service.SubmitFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 운영관리 - 제출서류관리  DAO 클래스
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
 * @version : 1.1
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("SubmitFileDAO")
@SuppressWarnings({"all"})
public class SubmitFileDAO extends BaseDAO {
	
    /**
     * 제출서류 관리 페이징목록 조회
     */
    public PaginatedArrayList listSubmitFile(SubmitFileVO submitFileVO, int currPage, int pageSize) {
        return pageList("SubmitFile.listSubmitFile", submitFileVO, currPage, pageSize);
    }

    /**
     * 제출서류 관리 목록 조회
     */
    public List listSubmitFile(SubmitFileVO submitFileVO) {
        return list("SubmitFile.listSubmitFile", submitFileVO);
    }

    /**
     * 제출서류 상세조회
     */
	public SubmitFileVO viewSubmitFile(SubmitFileVO submitFileVO) {
		return (SubmitFileVO)view("SubmitFile.viewSubmitFile", submitFileVO);
	}

    /**
     * 제출서류 수정하기
     */
	public int updtSubmitFile(SubmitFileVO submitFileVO) {
		return insert("SubmitFile.updtSubmitFile", submitFileVO);
	}

    /**
     * 제출서류 등록하기
     */
	public int regiSubmitFile(SubmitFileVO submitFileVO) {
		return update("SubmitFile.regiSubmitFile", submitFileVO);
	}

    /**
     * 제출서류 삭제하기
     */
	public int deltSubmitFile(SubmitFileVO submitFileVO) {
		return delete("SubmitFile.deltSubmitFile", submitFileVO);
	}
    
}