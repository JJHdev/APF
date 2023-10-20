package business.com.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.com.user.service.UserVO;
import common.base.BaseDAO;
import common.user.UserInfo;

/**
 * [DAO클래스] - 회원가입 관리 DAO 클래스
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
 * @class   : UserDAO
 * @author  : LSH
 * @since   : 2023.03.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Repository("UserDAO")
public class UserDAO extends BaseDAO {
	
	// 회원가입 - 회원정보 저장처리
	public int regiUser(UserVO userVO) {
		return insert("User.regiUser", userVO);
	}
	
	// 회원가입 - 회원ROLE 저장처리
	public int regiRole(UserVO userVO) {
		return insert("User.regiRole", userVO);
	}
	
	// 회원가입 - 회원ROLE 등록되어 있는지 확인
	public boolean existRole(UserVO userVO) {
		return (Boolean)view("User.existRole", userVO);
	}

	// 회원가입 - 이미 가입된 회원인지 확인
	public boolean existUser(String userId) {
		return (Boolean)view("User.existUser", userId);
	}
	
    /**
     *  사용자정보 조회
     */
    public UserVO getUser(String userNo) throws Exception {
        return (UserVO)view("User.getUser", userNo);
    }
    /**
     *  로그인 사용자정보 조회
     */
    public UserInfo getUserInfo(String userId) throws Exception {
        return (UserInfo)view("User.getUserInfo", userId);
    }
    /**
     *  OAUTH 로그인시 사용자정보 조회
     */
    public UserInfo getUserInfoByOauth(UserVO userVO) throws Exception {
        return (UserInfo)view("User.getUserInfoByOauth", userVO);
    }
    /**
     *  23.07.28 LHB
     *  ID/PW 찾기 사용자정보 조회
     */
    public UserVO getUserInfoByIdPw(UserVO userVO) throws Exception {
        return (UserVO) view("User.getUserByIdPw", userVO);
    }

    /**
     * 패스워드 실패시 카운터 업데이트 및 잠금시간 등록
     */
    public int updtPswdErrCnt(Map<String,Object> userMap) throws Exception {
        return update("User.updtPswdErrCnt", userMap);
    }

    /**
     * 로그인시간 등록
     */
    public int updtLoginTime(String userId) throws Exception {
        return update("User.updtLoginTime", userId);
    }

    /**
     * 사용자아이디,비밀번호 일치여부 확인
     */
	public boolean existUserPswd(String userId, String password) {
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("userId"  , userId);
    	params.put("password", password);
    	return (Integer)view("User.existUserPswd", params) > 0;
    }

    /**
     * 2023.07.31 LSH 로그인시 업체 승인 여부 확인
     */
	public boolean approveBzenty(String bzentyNo) {
		return (Boolean)view("User.approveBzenty", bzentyNo);
    }

    /**
     * 2023.06.28 LSH 업체회원의 업체번호 초기화 처리 (NULL업데이트)
     * 마이페이지 - 그룹관리 - 멤버관리에서 사용됨
     * 
     * @param params.gsUserNo 세션사용자번호
     * @param params.userNo   대상사용자번호
     */
    public int updtUserBzentyReset(Map<String,String> params) throws Exception {
        return update("User.updtUserBzentyReset", params);
    }

    /**
     * 2023.06.28 LSH 업체회원의 권한변경처리
     * 마이페이지 - 그룹관리 - 멤버관리에서 사용됨
     * 
     * @param params.gsUserNo  세션사용자번호
     * @param params.userNo    대상사용자번호
     * @param params.roleId    대상사용자권한
     * @param params.newRoleId 신규사용자권한
     */
    public int updtUserBzentyRole(Map<String,String> params) throws Exception {
        return update("User.updtUserBzentyRole", params);
    }

    /**
     * 2023.06.28 LSH 회원의 권한조회
     */
	public String getUserRole(String userNo) {
    	return (String)view("User.getUserRole", userNo);
    }
	
	
    /**
     * 2023.07.05 LSH 회원정보 변경처리
     * 마이페이지 - 기본정보 - 개인정보에서 사용됨
     *
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.userNm     사용자이름 (선택항목)
     * @param userVO.mblTelno   휴대폰번호 (선택항목)
     * @param userVO.pswd       비밀번호   (선택항목)
     * 
     */
	public int updtUser(UserVO userVO) {
		return update("User.updtUser", userVO);
	}
	
    /**
     * 2023.07.05 LSH 회원정보 탈퇴처리
     * 마이페이지 - 기본정보 - 개인정보에서 사용됨
     * 
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.whdwlYmd   탈퇴일자
     * @param userVO.useSttsCd  사용상태
     */
	public int updtUserFinish(UserVO userVO) {
		return update("User.updtUserFinish", userVO);
	}

    /**
     * 2023.07.05 LSH 회원의 권한정보 삭제
     * 마이페이지 - 기본정보 - 개인정보에서 사용됨
     * 
     * @param userVO.userNo     사용자번호
     */
	public int deltUserRole(String userNo) {
		return update("User.deltUserRole", userNo);
	}
	
    /**
     * 2023.07.18 LSH 회원의 업체번호 업데이트
     * 회원가입 - 기업회원에서 사용됨
     * 
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.bzentyNo   업체번호
     */
	public int updtUserBzenty(UserVO userVO) {
		return update("User.updtUserBzenty", userVO);
	}
	
    /**
     *  2023.07.31 LSH 업체의 대표계정 회원정보 조회
     */
    public UserVO getUserRprs(String bzentyNo) throws Exception {
        return (UserVO)view("User.getUserRprs", bzentyNo);
    }
}