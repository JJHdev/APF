 package business.sys.file.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.sys.file.service.PapeCodeVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 서류코드관리을 관리하는 DAO 클래스
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
 * @class   : PapeCodeDAO
 * @author : JH
 * @since : 2023.08.04
 * @version : 1.2
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("PapeCodeDAO")
@SuppressWarnings({"all"})
public class PapeCodeDAO extends BaseDAO {

    /**
     * 서류코드관리 목록 조회
     */
    public List listPapeCode(PapeCodeVO papeCodeVO) {
        return list("PapeCode.listPapeCode", papeCodeVO);
    }
    /**
     * 서류코드관리 목록 조회
     */
    public List listUnderPapeCode(PapeCodeVO papeCodeVO) {
    	return list("PapeCode.listUnderPapeCode", papeCodeVO);
    }

    /**
     * 서류코드관리 상세 조회
     */
    public PapeCodeVO viewPapeCode(PapeCodeVO papeCodeVO) {
        return (PapeCodeVO)view("PapeCode.viewPapeCode", papeCodeVO);
    }

    /**
     * 중복확인(코드)
     */
    public int confPapeCode(PapeCodeVO papeCodeVO) {
        return (Integer)view("PapeCode.confPapeCode", papeCodeVO);
    }

    /**
     * 중복확인(상위코드)
     */
    public int confUpperPapeCode(String papeCd) {
        return (Integer)view("PapeCode.confUpperPapeCode", papeCd);
    }

    /**
     * 중복확인(하위코드)
     */
    public int confLowerPapeCode(String papeCd) {
        return (Integer)view("PapeCode.confLowerPapeCode", papeCd);
    }

    /**
     * 서류코드관리 등록
     */
    public int regiPapeCode(PapeCodeVO papeCodeVO) {
        return insert("PapeCode.regiPapeCode", papeCodeVO);
    }

    /**
     * 서류코드관리 수정
     */
    public int updtPapeCode(PapeCodeVO papeCodeVO) {
        return update("PapeCode.updtPapeCode", papeCodeVO);
    }

    /**
     * 서류코드관리 삭제
     */
    public int deltPapeCode(PapeCodeVO papeCodeVO) {
        return delete("PapeCode.deltPapeCode", papeCodeVO);
    }

    /**
     * 다운로드 카운트 층가 저장
     */
    public int updtPapeCodeDownCount(PapeCodeVO papeCodeVO) {
        return update("PapeCode.updtPapeCodeDownCount", papeCodeVO);
    }
    /**
     * 코드 페이징리스트 조회
     */
	public PaginatedArrayList listPapeCode(PapeCodeVO papeCodeVO, int currPage, int pageSize) {
		return pageList("PapeCode.listPapeCode", papeCodeVO, currPage, pageSize);

	}
    /**
     * 파일 save 확인용 조회
     */
	public List<PapeCodeVO> listPapeCodeFile(PapeCodeVO papeCodeFileVO) {
		return list("PapeCode.listPapeCodeFile", papeCodeFileVO);
	}

}