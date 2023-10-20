package business.com.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.common.service.MainService;
import business.com.common.service.MainVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 공통 ServiceImpl
 *
 * @class   : MainServiceImpl
 * @author  :
 * @since   : 2023.07.12
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
    
	@Override
	public MainVO listToDoCount(MainVO mainVO) throws Exception {
		return mainDAO.listToDoCount(mainVO);
	}
	
	@Override
	public MainVO listNumCount(MainVO mainVO) throws Exception {
		return mainDAO.listNumCount(mainVO);
	}
	
	@Override
	public List listAcs(MainVO mainVO) throws Exception {
		return mainDAO.listAcs(mainVO);
	}

}
