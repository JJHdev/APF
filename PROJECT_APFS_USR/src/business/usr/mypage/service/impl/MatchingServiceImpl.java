package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.mypage.service.MatchingService;
import business.usr.mypage.service.MatchingVO;
import business.usr.support.service.SprtFnnrVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 매칭설정관리 Service 구현 클래스
 * 
 * @class   : MatchingServiceImpl
 * @author  : LSH
 * @since   : 2023.04.29
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("MatchingService")
@SuppressWarnings({"all"})
public class MatchingServiceImpl extends BaseService implements MatchingService {

    @Resource(name = "MatchingDAO")
    private MatchingDAO matchingDAO;

    /**
     * 매칭설정관리 목록조회
     */
    @Override
    public List listMatching(MatchingVO matchingVO) throws Exception {
    	return matchingDAO.listMatching(matchingVO);
    }

    /**
     * 매칭설정 다중등록,삭제
     * 세션사용자번호를 기준으로 처리된다.
     */
	@Override
	public String saveMatching(MatchingVO matchingVO) throws Exception {
		
		if (matchingVO == null)
			throw processException("error.comm.notTarget");
		
		if (matchingVO.getMatchList() == null)
			throw processException("error.comm.notTarget");

		if (CommUtils.isEmpty(matchingVO.getGsUserNo()))
			throw processException("error.comm.notValid");

		// 사용자번호기준 매칭설정 삭제처리
		matchingDAO.deltMatchingAll(matchingVO.getGsUserNo());
		
		int ret = 0;
		for (MatchingVO item : matchingVO.getMatchList()) {
			item.setUserNo  (matchingVO.getGsUserNo());
			item.setGsUserNo(matchingVO.getGsUserNo());
			// 매칭설정 등록처리
			ret += matchingDAO.regiMatching(item);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 매칭설정여부 확인
     */
	@Override
    public boolean existMatching(String userNo) throws Exception {
		return matchingDAO.existMatching(userNo);
	}
	
}