package business.com.user.service;

import java.util.List;
import java.util.Map;

import commf.exception.BusinessException;
import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 그룹관리 Service Interface
 * 
 * @class   : GroupService
 * @author  : LSH
 * @since   : 2023.06.14
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface GroupService {

    /**
     * 마이페이지 메뉴 목록 조회
     * @param params.gsRoleId 세션사용자 권한ID
     * @param params.gsUserNo 세션사용자 번호
     * @param params.gsRprsYn 세션사용자 대표여부
     */
    public List listMypageMenu(Map params) throws Exception;

    /**
     * 마이페이지 메뉴별 접근가능여부
     * @param params.userNo   사용자번호
     * @param params.roleId   권한ID
     * @param params.menuId   메뉴ID
     * @param params.saveMode 수정모드여부
     */
    public boolean access(String userNo, String roleId, String menuId, boolean saveMode) throws BusinessException;

    /**
     * 마이페이지 메뉴별 접근권한조회
     * @param params.userNo   사용자번호
     * @param params.menuId   메뉴ID
     */
    public String getGrpAuthCd(String userNo, String menuId) throws BusinessException;

    /**
     * 멤버관리/권한관리 페이징목록 조회
     */
    public PaginatedArrayList listGroup(GroupVO groupVO, int currPage, int pageSize) throws Exception;

    /**
     * 멤버관리/권한관리 목록 조회
     */
    public List listGroup(GroupVO groupVO) throws Exception;

    /**
     * 세션사용자의 메뉴ID 기준 접근권한 확인
     * @param groupVO.userNo 세션사용자 번호
     * @param groupVO.menuId 접근화면의 메뉴ID
     */
    public boolean checkAccess(GroupVO groupVO) throws Exception;

    /**
     * 그룹코드 중복 확인 (중복된 값이 없는 경우 true 반환)
     */
    public boolean uniqueGroupCd(String groupCd) throws Exception;

    /**
     * 대표계정여부 확인
     * @param userNo 세션사용자 번호
     */
    public String getGrpRprsYn(String userNo) throws Exception;

	/**
	 * 그룹관리 등록,수정,삭제
	 */
	public String saveGroup(GroupVO groupVO) throws Exception;

    /**
     * 마이페이지 - 권한관리 - 권한변경처리
     * 
     * @param groupVO.gsUserNo      세션사용자번호
     * @param groupVO.userNo        사용자번호
     * @param groupVO.menuId        메뉴ID
     * @param groupVO.groupAuthrtCd 그룹권한
     */
	public int saveGrpAuth(GroupVO groupVO) throws Exception;

    /**
     * [그룹관리] 대표계정 변경처리
     * 
     * 1) 이전 대표계정에 대한 처리
     * - 권한있는 메뉴의 그룹권한을 V(뷰어)로 일괄수정
     * 2) 신규 대표계정에 대한 처리
     * - 권한있는 메뉴의 그룹권한을 M(수정)로 일괄수정
     * 
     * @param groupVO.gsUserNo   세션사용자번호 (기존대표계정)
     * @param groupVO.gsBzentyNo 세션업체번호
     * @param groupVO.userNo     대표사용자번호 (신규대표계정)
     */
	public int saveGrpRprs(GroupVO groupVO) throws Exception;

    /**
     * 2023.08.11 LSH
     * [그룹관리] 일반계정/대표계정으로 전환처리
     * 
     * 1) 해당 사용자를 일반계정으로 전환
     * 2) 해당 사용자의 그룹권한을 V(뷰어)로 수정
     * 
     * @param groupVO.userNo     대상사용자번호     
     * @param groupVO.gsUserNo   세션사용자번호
     * @param rprsYn             대표계정여부 (Y/N)
     */
	public int updtGrpRprs(GroupVO groupVO, String rprsYn) throws Exception;

    /**
     * 2023.08.11 LSH
     * [그룹관리] 해당 업체의 모든 사용자의 계정을 멤버계정으로 초기화 처리
     * 
     * @param groupVO.userNo     대상사용자번호     
     * @param groupVO.gsUserNo   세션사용자번호
     * @param groupVO.gsBzentyNo 대상업체번호
     */
	public int updtGrpUsrReset(GroupVO groupVO) throws Exception;

    /**
     * 2023.08.12 LSH
     * 업체회원의 대표계정 권한 일괄 수정
     * 
     * @param groupVO.gsUserNo      세션사용자번호
     * @param groupVO.gsBzentyNo    세션업체번호
     * @param groupVO.groupAuthrtCd 그룹권한
     */
    public int updtGrpAuthRprs(GroupVO groupVO) throws Exception;

    /**
     * [그룹관리] 그룹사용자 삭제처리 (일반회원으로 변경)
     * : 해당 사용자의 권한있는 메뉴 삭제
     * : 해당 사용자의 업체번호 빈값 업데이트
     * : 해당 사용자의 권한을 일반회원으로 권한변경처리
     * : 해당 사용자의 일반회원 기준 권한 등록
     * : 실제 삭제되지 않으며 권한만 변경된다.
     * 
     * @param groupVO.gsUserNo   세션사용자 번호
     * @param groupVO.gsRoleId   세션사용자 권한
     * @param groupVO.gsBzentyNo 세션사용자 업체번호
     * @param groupVO.userNo     삭제대상 사용자번호
     */
	public int deltGrpUsr(GroupVO groupVO) throws Exception;

    /**
     * 2023.08.11 LSH
     * [그룹관리] 그룹사용자 초기화처리
     * : 해당 사용자의 권한있는 메뉴 삭제
     * : 해당 사용자의 그룹사용자 삭제
     * 
     * @param userNo  삭제대상 사용자번호
     */
	public int resetGrpUsr(String userNo) throws Exception;

    /**
     * [회원가입] 그룹사용자 신규등록
     * : 해당 그룹에 사용자가 있는지 확인 후 대표여부를 설정한다.
     * : 해당 사용자의 그룹사용자 등록
     * : 해당 사용자의 그룹권한 등록
     * 
     * @param groupVO.bzentyNo   업체번호
     * @param groupVO.userNo     사용자번호
     * @param groupVO.roleId     권한ID
     * @param groupVO.gsUserNo   세션사용자 번호
     */
	public int regiGrpUsr(GroupVO groupVO) throws Exception;

    /**
     * 그룹코드 저장처리
     * 
     * @param groupVO.gsUserNo    세션사용자 번호
     * @param groupVO.gsBzentyNo  세션사용자 업체번호
     * @param groupVO.saveGroupCd 그룹코드
     */
	public int saveGrpCode(GroupVO groupVO) throws Exception;
	
    /**
     * 업체의 그룹코드 수정
     * 마이페이지 - 그룹관리 에서 사용됨
     * 2023.08.04 LSH EntService에서 이동함
     * 
     * @param entVO.bzentyNo  업체번호
     * @param entVO.groupCd   그룹코드
     * @param entVO.gsUserNo  세션사용자번호
     */
	public int updtGroupCd(GroupVO groupVO) throws Exception;
	
    /**
	 * 2023.09.05 LSH
     * 그룹사용자 리셋 및 재등록처리 (대표계정에 한함)
     * 
     * @param userNo     사용자번호
     * @param roleId     변경권한ID
     */
	public int updtGrpRprs(String userNo, String roleId) throws Exception;
	
}