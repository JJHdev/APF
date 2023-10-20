package egovframework.com.cst.security;

import java.io.Serializable;


/**
 * server security 의 사용자 VO 클래스
 * @author 개발팀 홍길동
 * @since 2010.01.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.01.01  홍길동          최초 생성
 *   2011.06.28  ntarget   Model Entity 추가
 * </pre>
 */

@SuppressWarnings("serial")
public class EgovUserDetailsVO implements Serializable {
    private String userId;
    private String password;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
