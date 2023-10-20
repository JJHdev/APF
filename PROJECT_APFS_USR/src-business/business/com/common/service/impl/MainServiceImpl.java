package business.com.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.common.service.MainService;
import business.com.common.service.MainVO;
import business.usr.inform.service.BbsVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 메인페이지 ServiceImpl
 *
 * @class   : MainServiceImpl
 * @author  :
 * @since   : 2023.07.31
 * @version : 1.0
 *
 *   수정일            수정자                             수정내용
 *  -------    --------    ---------------------------
 *
 */
@Service("MainService")
@SuppressWarnings("rawtypes")
public class MainServiceImpl extends BaseService implements MainService {

    @Resource(name = "MainDAO")
    private MainDAO mainDAO;
    
    public List listBanner(MainVO mainVO) throws Exception {
    	return mainDAO.listBanner(mainVO);
    }
    
	public List listPopupNotice(BbsVO bbsVO) throws Exception {
		return mainDAO.listPopupNotice(bbsVO);
	}
}
