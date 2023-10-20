package business.com.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.com.common.service.MainVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 공통 DAO
 *
 * @class   : MainDAO
 * @author  :
 * @since   : 2023.07.13
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *	23.07.13	KYW			First Coding.
 */
@Repository("MainDAO")
@SuppressWarnings({"all"})
public class MainDAO extends BaseDAO {
	
	/**
     * 처리할 일
     */
    public MainVO listToDoCount(MainVO mainVO) {
        return (MainVO)view("Main.listToDoCount", mainVO);
    }
    
    /**
     * 주요통계
     */
    public MainVO listNumCount(MainVO mainVO) {
        return (MainVO)view("Main.listNumCount", mainVO);
    }
	
	/**
     * 로그인 접속 통계
     */
    public List listAcs(MainVO mainVO) {
        return list("Main.listAcs", mainVO);
    }

    
}
