package business.sys.user.service;

import java.util.List;
import java.util.Map;

import commf.paging.PaginatedArrayList;
import common.user.UserInfo;

/**
 * [인터페이스클래스] - 사용자정보 조회
 *
 * @class   : UserInfoService
 * @author  : ntarget
 * @since   : 2021.02.08
 * @version : 1.0
 */

@SuppressWarnings({"all"})
public interface UserInfoService {

    /**
     * 2021.09.09 LSH 추가
     * 사용자정보 페이징목록 조회
     */
    public PaginatedArrayList listUserInfo(Map userMap, int currPage, int pageSize) throws Exception;

    /**
     * 2021.09.09 LSH 추가
     * 사용자정보 목록조회
     */
    public List listUserInfo(Map userMap) throws Exception;

    /**
     * 2021.09.09 LSH 추가
     * 사용자정보 상세조회
     */
    public UserInfoVO viewUserInfo(String userNo) throws Exception;

    /**
     * 2021.09.09 LSH 추가
     * 사용자정보 등록,수정,삭제
     */
    public String saveUserInfo(UserInfoVO userInfoVO) throws Exception;

    /**
     * 사용자아이디 중복체크
     */
    public boolean existUserId(String userId) throws Exception;

	/**
	 * 2023.08.12 LSH
	 * 사용자 비밀번호 재설정 및 메일발송 처리
	 * @param userInfoVO.userNo   사용자 번호
	 * @param userInfoVO.gsUserNo 세션사용자 번호
	 */
	public String savePswdReset(UserInfoVO userInfoVO) throws Exception;

}
