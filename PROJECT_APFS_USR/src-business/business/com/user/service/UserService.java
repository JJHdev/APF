package business.com.user.service;

import java.util.List;
import java.util.Map;

import business.usr.invest.service.EntVO;
import business.usr.invest.service.FundVO;
import common.user.UserInfo;

/**
 * [서비스인터페이스] - 회원관리 Service Interface
 * 
 * @class   : UserService
 * @author  : LSH
 * @since   : 2023.03.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
public interface UserService {

	// 이미 가입된 회원인지 확인
	public boolean existUser(String userId) throws Exception;
	
	// 개인회원 회원가입 저장처리
	public int saveJoin(UserVO userVO) throws Exception;
	
	// 기업회원 회원가입 저장처리
	public int saveJoinEnt(UserVO userVO, EntVO entVO) throws Exception;

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
	public int updtUser(UserVO userVO) throws Exception;
	
    /**
     * 2023.07.05 LSH 회원정보 탈퇴처리
     * 마이페이지 - 기본정보 - 개인정보에서 사용됨
     * 
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.whdwlYmd   탈퇴일자
     * @param userVO.useSttsCd  사용상태
     */
	public int deltUser(UserVO userVO) throws Exception;
	
	// 사용자정보 조회
    public UserVO getUser(String userNo) throws Exception;
	// 로그인 사용자정보 조회
    public UserInfo getUserInfo(String userId) throws Exception;
    
    // OAUTH 로그인시 사용자정보 조회
    public UserInfo getUserInfoByOauth(UserVO userVO) throws Exception;
    
    // 패스워드 실패시 카운터 업데이트 및 잠금시간 등록
    public int updtPswdErrCnt(String userId, int pswdErrCnt) throws Exception;

    // 로그인시간 등록
    public int updtLoginTime(String userId) throws Exception;

    // 사용자아이디,비밀번호 일치여부 확인
    public boolean existUserPswd(String userId, String password) throws Exception;

    /**
     * 2023.07.31 LSH 로그인시 업체 승인 여부 확인
     */
	public boolean approveBzenty(String bzentyNo) throws Exception;

    /**
     * 2023.06.28 LSH 업체회원의 업체번호 초기화 처리 (NULL업데이트)
     * 마이페이지 - 그룹관리 - 멤버관리에서 사용됨
     * 
     * @param params.gsUserNo 세션사용자번호
     * @param params.userNo   대상사용자번호
     */
    public int updtUserBzentyReset(Map<String,String> params) throws Exception;

    /**
     * 2023.06.28 LSH 업체회원의 권한변경처리
     * 마이페이지 - 그룹관리 - 멤버관리에서 사용됨
     * 
     * @param params.gsUserNo  세션사용자번호
     * @param params.userNo    대상사용자번호
     * @param params.roleId    대상사용자권한
     * @param params.newRoleId 신규사용자권한
     */
    public int updtUserBzentyRole(Map<String,String> params) throws Exception;

    /**
     * 2023.06.28 LSH 회원의 권한조회
     */
	public String getUserRole(String userNo) throws Exception;
	
    /**
     * 2023.07.18 LSH 회원의 업체번호 업데이트
     * 회원가입 - 기업회원에서 사용됨
     * 
     * @param userVO.gsUserNo   세션사용자번호
     * @param userVO.userNo     사용자번호
     * @param userVO.bzentyNo   업체번호
     */
	public int updtUserBzenty(UserVO userVO) throws Exception;
	
    /**
     *  2023.07.31 LSH 업체의 대표계정 회원정보 조회
     */
    public UserVO getUserRprs(String bzentyNo) throws Exception;
    
    /**
     *  2023.08.01 LHB
     *  ID/PW 찾기 사용자정보 조회
     */
    public UserVO getUserInfoByIdPw(UserVO userVO) throws Exception;
    
    /**
     *  2023.08.01 LHB
     *  임시비밀번호 메일 발송
     */
	public void sendMail(UserVO userVO, String tempPswd) throws Exception;
	
	/**
	 * 2023.09.05 LSH
	 * 기업회원 반려업체 수정처리
	 */
	public int saveEntCmpl(UserInfo user, EntVO entVO) throws Exception;

    /**
     * 2023.09.11 LSH
     * 특정업체에 속한 회원목록 조회
     */
    public List<UserVO> listUserByEnt(String bzentyNo) throws Exception;
}