package business.com.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import business.com.common.service.MainVO;
import business.usr.inform.service.BbsVO;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 메인 DAO
 *
 * @class   : MainDAO
 * @author  :
 * @since   : 2023.07.31
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */
@Repository("MainDAO")
@SuppressWarnings({"all"})
public class MainDAO extends BaseDAO {
	/**
	 * 메인화면 배너
	 */
	public List listBanner(MainVO mainVO) {
		return list("Main.listBanner", mainVO);
	}
	
	/**
	 * 메인화면 공지사항 팝업창
	 */
	public List listPopupNotice(BbsVO bbsVO) {
		return list("Main.listPopupNotice", bbsVO);
	}
} 
