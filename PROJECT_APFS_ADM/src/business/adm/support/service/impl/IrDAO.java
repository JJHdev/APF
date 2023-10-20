 package business.adm.support.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.adm.inform.service.BbsVO;
import business.adm.support.service.IrVO;
import business.adm.support.service.SupportProgressVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 운영관리-IR지원현황을 관리하는 DAO 클래스
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
 * @class   : IrDAO
 * @author  : LHB
 * @since   : 2023.06.08
 * @version : 1.0
 *
 *   수정일       수정자               수정내용
 *  -------    --------    ---------------------------
 * 23.06.08      LHB              First Coding.
 */

@Repository("IrDAO")
@SuppressWarnings({"all"})
public class IrDAO extends BaseDAO {
	
    /**
     * 운영관리-IR지원현황 페이징목록 조회
     */
    public PaginatedArrayList listIr(IrVO irVO, int currPage, int pageSize) {
        return pageList("Ir.listIr", irVO, currPage, pageSize);
    }

    /**
     * 운영관리-IR지원현황 목록 조회
     */
    public List listIr(IrVO irVO) {
        return list("Ir.listIr", irVO);
    }

    /**
     * 운영관리-IR지원현황 상세 조회
     */
    public IrVO viewIr(IrVO irVO) {
        return (IrVO) view("Ir.viewIr", irVO);
    }

    /**
     * 운영관리-IR지원현황 등록
     */
    public int regiIr(IrVO irVO) {
        return insert("Ir.regiIr", irVO);
    }

    /**
     * 운영관리-IR지원현황 수정
     */
    public int updtIr(IrVO irVO) {
        return update("Ir.updtIr", irVO);
    }

    /**
     * 세부지원사업관리 삭제
     */
    public int deltIr(IrVO irVO) {
        return delete("Ir.deltIr", irVO);
    }
    
}