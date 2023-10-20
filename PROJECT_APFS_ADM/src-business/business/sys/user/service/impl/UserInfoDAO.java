package business.sys.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.com.user.service.UserVO;
import business.sys.user.service.UserInfoVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;
import common.user.UserInfo;

/**
 * [DAO 클래스] - 사용자정보 조회
 *
 * @class   : UserInfoDAO
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 * 2021.09.09   LSH        사용자관리 기능 추가
 */

@Repository("UserInfoDAO")
@SuppressWarnings({"all"})
public class UserInfoDAO extends BaseDAO {

    /**
     * 사용자정보 페이징목록 조회
     */
    public PaginatedArrayList listUserInfo(Map userMap, int currPage, int pageSize) {
        return pageList("UserInfo.listUserInfo", userMap, currPage, pageSize);
    }

    /**
     * 사용자정보 목록 조회
     */
    public List listUserInfo(Map userMap) {
        return list("UserInfo.listUserInfo", userMap);
    }

    /**
     * 사용자정보 상세 조회
     */
    public UserInfoVO viewUserInfo(String userNo) {
        return (UserInfoVO)view("UserInfo.viewUserInfo", userNo);
    }

    /**
     * 사용자정보 상세 조회 (ID기준)
     */
    public UserInfo viewUserInfoById(String userId) {
        return (UserInfo)view("UserInfo.viewUserInfoById", userId);
    }

    /**
     * 사용자정보 등록
     */
    public int regiUserInfo(UserInfoVO userInfoVO) {
        return insert("UserInfo.regiUserInfo", userInfoVO);
    }

    /**
     * 사용자정보 수정
     */
    public int updtUserInfo(UserInfoVO userInfoVO) {
        return update("UserInfo.updtUserInfo", userInfoVO);
    }

    /**
     * 사용자정보 삭제
     */
    public int deltUserInfo(String userNo) {
        return update("UserInfo.deltUserInfo", userNo);
    }

    /**
     * 사용자아이디 중복체크
     */
    public boolean existUserId(String userId) {
    	return (Integer)view("UserInfo.existUserId", userId) > 0;
    }

    /**
     *  2023.08.07 LHB
     *  LSH 업체의 대표계정 회원정보 조회 코드 적용
     */
    public UserVO getUserRprs(String bzentyNo) throws Exception {
        return (UserVO)view("User.getUserRprs", bzentyNo);
    }

	/**
	 * 2023.08.12 LSH
	 * 사용자 비밀번호 변경
	 * 
	 * @param userInfoVO.gsUserNo 세션사용자번호
	 * @param userInfoVO.userNo   대상사용자번호
	 * @param userInfoVO.pswd     변경할 비밀번호
	 */
	public int updtUserPswd(UserInfoVO userInfoVO) throws Exception {
		return update("UserInfo.updtUserPswd", userInfoVO);
	}
}