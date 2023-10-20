package business.adm.invest.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.invest.service.EntVO;
import business.com.user.service.UserVO;
import business.adm.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 회원관리-업체관리를 관리하는 DAO 클래스
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
 * @author  : LHB
 * @since   : 2023.06.12
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 * 23.06.12      LHB             First Coding.
 */

@Repository("EntDAO")
@SuppressWarnings({"all"})
public class EntDAO extends BaseDAO {
	
    /**
     * 회원관리-업체관리 페이징목록 조회
     */
    public PaginatedArrayList listEnt(EntVO entVO, int currPage, int pageSize) {
        return pageList("Ent.listEnt", entVO, currPage, pageSize);
    }

    /**
     * 회원관리-업체관리 목록 조회
     */
    public List listEnt(EntVO entVO) {
        return list("Ent.listEnt", entVO);
    }

    /**
     * 회원관리-업체관리 상세 조회
     */
    public EntVO viewEnt(EntVO entVO) {
        return (EntVO) view("Ent.viewEnt", entVO);
    }

    /**
     * 회원관리-업체관리 등록
     */
    public int regiEnt(EntVO entVO) {
        return insert("Ent.regiEnt", entVO);
    }

    /**
     * 회원관리-업체관리 수정
     */
    public int updtEnt(EntVO entVO) {
        return update("Ent.updtEnt", entVO);
    }

    /**
     * 회원관리-업체관리 삭제
     */
    public int deltEnt(EntVO entVO) {
        return delete("Ent.deltEnt", entVO);
    }
    
    /**
     *  2023.07.31 LSH 업체의 대표계정 회원정보 조회
     */
    public UserVO getUserRprs(String bzentyNo) throws Exception {
        return (UserVO)view("User.getUserRprs", bzentyNo);
    }
    
}