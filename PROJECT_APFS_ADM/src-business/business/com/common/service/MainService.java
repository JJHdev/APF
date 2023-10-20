package business.com.common.service;

import java.util.List;

import business.adm.inform.service.BbsVO;

/**
 * [서비스인터페이스] - 개발탬플릿(개발자 표준 가이드용)
 *
 * @class   : MainService
 * @author  : ntarget
 * @since   : 2023.07.12
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@SuppressWarnings("all")
public interface MainService {
	/**
     * 처리할일 조회
     */
	public MainVO listToDoCount(MainVO mainVO) throws Exception;
	
	/**
     *  조회
     */
	public MainVO listNumCount(MainVO mainVO) throws Exception;
	
	/**
     * 사용자 로그 통계 조회
     */
    public List listAcs(MainVO mainVO) throws Exception;
}