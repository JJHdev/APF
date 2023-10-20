 package business.adm.file.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.adm.file.service.BizFileVO;
import business.adm.file.service.PapeFileVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 업무첨부파일을 관리하는 DAO 클래스
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
 * @class   : PapeFileDAO
 * @author 	: JH
 * @since 	: 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("PapeFileDAO")
@SuppressWarnings({"all"})
public class PapeFileDAO extends BaseDAO {

    /**
     * 업무첨부파일 목록 조회
     */
    public List listPapeFile(PapeFileVO papeFileVO) {
        return list("PapeFile.listPapeFile", papeFileVO);
    }

    /**
     * 서류양식 상세 조회
     */
	public PapeFileVO viewPapeFile(String dcmntCd) {
		 return (PapeFileVO)view("PapeFile.viewPapeFile", dcmntCd);
    }

}