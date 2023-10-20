 package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.mypage.service.MyPbancVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 사업공고관리을 관리하는 DAO 클래스
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
 * @class   : PbancDAO
 * @author  : LSH
 * @since   : 2023.04.30
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("MyPbancDAO")
@SuppressWarnings({"all"})
public class MyPbancDAO extends BaseDAO {

    /**
     * 사업공고관리 페이징목록 조회
     */
    public PaginatedArrayList listPbanc(MyPbancVO myPbancVO, int currPage, int pageSize) {
        return pageList("MyPbanc.listPbanc", myPbancVO, currPage, pageSize);
    }

    /**
     * 사업공고관리 목록 조회
     */
    public List listPbanc(MyPbancVO myPbancVO) {
        return list("MyPbanc.listPbanc", myPbancVO);
    }

    /**
     * 사업공고관리 상세 조회
     */
    public MyPbancVO viewPbanc(MyPbancVO myPbancVO) {
        return (MyPbancVO)view("MyPbanc.viewPbanc", myPbancVO);
    }

    /**
     * 사업공고관리 등록
     */
    public int regiPbanc(MyPbancVO myPbancVO) {
        return insert("MyPbanc.regiPbanc", myPbancVO);
    }

    /**
     * 사업공고관리 수정
     */
    public int updtPbanc(MyPbancVO myPbancVO) {
        return update("MyPbanc.updtPbanc", myPbancVO);
    }

    /**
     * 사업공고관리 삭제
     */
    public int deltPbanc(MyPbancVO myPbancVO) {
        return delete("MyPbanc.deltPbanc", myPbancVO);
    }

    /**
     * 마이페이지 사업공고관리 북마크 구분 탭 조회
     */
	public List listPbancTab(MyPbancVO myPbancVO) {
		return list("MyPbanc.listPbancTab", myPbancVO);
	}

    /**
     * 마이페이지 사업공고관리 세션 등록기관명 조회
     */
	public MyPbancVO viewCrdnsBzentyNm(MyPbancVO myPbancVO) {
		return (MyPbancVO)view("MyPbanc.viewCrdnsBzentyNm", myPbancVO);
	}

}