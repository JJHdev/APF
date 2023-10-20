package egovframework.com.cst.security;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

/**
 * server security 의 사용자 로그인정보 매핑클래스
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
 *   2011.06.28  ntarget  DB 조회 컬럼 추가.
 *   2014.06.24  ntarget   3.0 업데이트로 Object -> EgovUserDetails 변경
 * </pre>
 */

public class EgovUserDetailsMapping extends EgovUsersByUsernameMapping {

    /**
     * EgovUserDetailsMapping 생성자
     * @param ds
     * @param usersByUsernameQuery
     */
    public EgovUserDetailsMapping(DataSource ds, String usersByUsernameQuery) {
        super(ds, usersByUsernameQuery);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.security.userdetails.jdbc
     * .EgovUsersByUsernameMapping
     * #mapRow(java.sql.ResultSet, int)
     */
    /**
     * EgovUsersByUsernameMapping 클래스를 상속받아
     * jdbc-user-service 에서 지정된 users-by-username-query
     * 의 쿼리문을 조회하여 ResultSet에 매핑된다.
     */
    @Override
    protected EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
        logger.debug("## EgovUsersByUsernameMapping mapRow ##");

        String userid 	= rs.getString(1);
        String password = rs.getString(2);
        boolean enabled = rs.getBoolean(3);
        String username = rs.getString(4);

        // TODO USERS 테이블 컬럼 변경
        // 세션에서 관리되는 항목 추가 - ntarget : not used. LoginController에서 User정보 다시 조회함.
        EgovUserDetailsVO egovUserDetailsVO = new EgovUserDetailsVO();

  		egovUserDetailsVO.setUserId(userid);
        egovUserDetailsVO.setPassword(password);
        egovUserDetailsVO.setUserName(username);

        return new EgovUserDetails(userid, password, enabled, egovUserDetailsVO);
    }

}

