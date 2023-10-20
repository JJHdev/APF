 package business.com.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.com.user.service.GroupVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 그룹을 관리하는 DAO 클래스
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
 * @class   : GroupDAO
 * @author  : LSH
 * @since   : 2023.06.14
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("GroupDAO")
@SuppressWarnings({"all"})
public class GroupDAO extends BaseDAO {

    /**
     * 마이페이지 메뉴 목록 조회
     * @param params.gsRoleId 세션사용자 권한ID
     * @param params.gsUserNo 세션사용자 번호
     * @param params.gsRprsYn 세션사용자 대표여부
     */
    public List listMypageMenu(Map params) {
        return list("Group.listMypageMenu", params);
    }

    /**
     * 멤버관리/권한관리 페이징목록 조회
     */
    public PaginatedArrayList listGroup(GroupVO groupVO, int currPage, int pageSize) {
        return pageList("Group.listGroup", groupVO, currPage, pageSize);
    }

    /**
     * 멤버관리/권한관리 목록 조회
     */
    public List listGroup(GroupVO groupVO) {
        return list("Group.listGroup", groupVO);
    }

    /**
     * 세션사용자의 메뉴ID 기준 접근권한 확인
     * @param groupVO.userNo 세션사용자 번호
     * @param groupVO.menuId 접근화면의 메뉴ID
     */
    public boolean checkAccess(GroupVO groupVO) {
        return (Boolean)view("Group.checkAccess", groupVO);
    }

    /**
     * 그룹코드 중복 확인 (중복된 값이 없는 경우 true 반환)
     */
    public boolean uniqueGroupCd(String groupCd) throws Exception {
        return (Boolean)view("Group.uniqueGroupCd", groupCd);
    }

    /**
     * 대표계정여부 확인
     * @param userNo 세션사용자 번호
     */
    public String getGrpRprsYn(String userNo) {
        return (String)view("Group.getGrpRprsYn", userNo);
    }

    /**
     * 업체기준 사용자가 있는지 확인
     * 
     * @param bzentyNo 세션업체번호
     */
    public boolean existGrpUsr(String bzentyNo) {
        return (Boolean)view("Group.existGrpUsr", bzentyNo);
    }

    /**
     * 그룹권한관리 등록
     */
    public int regiGrpAuth(GroupVO groupVO) {
        return insert("Group.regiGrpAuth", groupVO);
    }

    /**
     * 그룹권한관리 ROLE기준 메뉴 일괄 등록
     * 
     * @param groupVO.userNo        사용자번호
     * @param groupVO.roleId        권한ID
     * @param groupVO.groupAuthrtCd 그룹권한
     * @param groupVO.gsUserNo      세션사용자 번호
     */
    public int regiGrpAuthByRole(GroupVO groupVO) {
        return insert("Group.regiGrpAuthByRole", groupVO);
    }

    /**
     * 그룹권한관리 수정
     */
    public int updtGrpAuth(GroupVO groupVO) {
        return update("Group.updtGrpAuth", groupVO);
    }

    /**
     * 그룹권한관리 사용자기준 권한 일괄 수정
     * 
     * @param groupVO.userNo        사용자번호
     * @param groupVO.groupAuthrtCd 그룹권한
     * @param groupVO.gsUserNo      세션사용자 번호
     */
    public int updtGrpAuthByUser(GroupVO groupVO) {
        return update("Group.updtGrpAuthByUser", groupVO);
    }

    /**
     * 그룹권한관리 삭제
     */
    public int deltGrpAuth(GroupVO groupVO) {
        return delete("Group.deltGrpAuth", groupVO);
    }

    /**
     * 그룹권한관리 사용자번호 기준 삭제
     * 
     * @param groupVO.userNo 사용자번호
     */
    public int deltGrpAuthByUser(GroupVO groupVO) {
        return delete("Group.deltGrpAuthByUser", groupVO);
    }

    /**
     * 그룹권한정보가 있는지 확인
     * 
     * @param groupVO.userNo 사용자번호
     * @param groupVO.menuId 메뉴ID
     */
    public boolean existGrpAuth(GroupVO groupVO) {
        return (Boolean)view("Group.existGrpAuth", groupVO);
    }

    /**
     * 그룹권한코드 조회
     * 
     * @param groupVO.userNo 사용자번호
     * @param groupVO.menuId 메뉴ID
     */
    public String getGrpAuthCd(GroupVO groupVO) {
        return (String)view("Group.getGrpAuthCd", groupVO);
    }
    
    /**
     * 그룹사용자관리 등록
     */
    public int regiGrpUsr(GroupVO groupVO) {
        return insert("Group.regiGrpUsr", groupVO);
    }

    /**
     * 그룹사용자관리 수정
     */
    public int updtGrpUsr(GroupVO groupVO) {
        return update("Group.updtGrpUsr", groupVO);
    }

    /**
     * 업체회원의 대표계정 비활성 처리
     * 
     * @param groupVO.gsUserNo   세션사용자번호
     * @param groupVO.gsBzentyNo 세션업체번호
     */
    public int updtGrpUsrReset(GroupVO groupVO) {
        return update("Group.updtGrpUsrReset", groupVO);
    }

    /**
     * 2023.08.12 LSH
     * 업체회원의 대표계정 권한 일괄 수정
     * 
     * @param groupVO.gsUserNo      세션사용자번호
     * @param groupVO.gsBzentyNo    세션업체번호
     * @param groupVO.groupAuthrtCd 그룹권한
     */
    public int updtGrpAuthRprs(GroupVO groupVO) {
        return update("Group.updtGrpAuthRprs", groupVO);
    }

    /**
     * 그룹사용자관리 삭제
     */
    public int deltGrpUsr(GroupVO groupVO) {
        return delete("Group.deltGrpUsr", groupVO);
    }

    /**
     * 그룹메뉴관리 등록
     */
    public int regiGrpMenu(GroupVO groupVO) {
        return insert("Group.regiGrpMenu", groupVO);
    }

    /**
     * 그룹메뉴관리 수정
     */
    public int updtGrpMenu(GroupVO groupVO) {
        return update("Group.updtGrpMenu", groupVO);
    }

    /**
     * 그룹메뉴관리 삭제
     */
    public int deltGrpMenu(GroupVO groupVO) {
        return delete("Group.deltGrpMenu", groupVO);
    }

    /**
     * 2023.08.04 LSH 업체 그룹코드 수정
     */
    public int updtGroupCd(GroupVO groupVO) {
        return update("Group.updtGroupCd", groupVO);
    }
}