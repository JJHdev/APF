package business.com.common.service;

import java.util.List;

import business.usr.inform.service.BbsVO;

/**
 * [서비스인터페이스] - 개발탬플릿(개발자 표준 가이드용)
 *
 * @class   : MainService
 * @author  : ntarget
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */

@SuppressWarnings("all")
public interface MainService {
	
	/**
	 * 배너 조회
	 */
	public List listBanner(MainVO mainVO) throws Exception;
	
	/**
	 * 팝업 공지사항 조회
	 */
	public List listPopupNotice(BbsVO bbsVO) throws Exception;
	
}