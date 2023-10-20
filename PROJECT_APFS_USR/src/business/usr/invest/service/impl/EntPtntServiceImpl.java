package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntPtntService;
import business.usr.invest.service.EntVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;

/**
 * [서비스클래스] - 특허상표권현황 Service 구현 클래스
 * 
 * @class   : EntPtntServiceImpl
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntPtntService")
@SuppressWarnings({"all"})
public class EntPtntServiceImpl extends BaseService implements EntPtntService {

    @Resource(name = "EntPtntDAO")
    private EntPtntDAO entPtntDAO;
	
    /**
     * 특허상표권현황 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEntPtnt(EntVO entVO, int currPage, int pageSize) throws Exception {
    	return entPtntDAO.listEntPtnt(entVO, currPage, pageSize);
    }

    /**
     * 특허상표권현황 목록조회
     */
    @Override
    public List listEntPtnt(EntVO entVO) throws Exception {
    	return entPtntDAO.listEntPtnt(entVO);
    }

    /**
     * 특허상표권현황 구분별 합계건수 목록조회
     */
    @Override
    public List listEntPtntSummary(EntVO entVO) throws Exception {
    	return entPtntDAO.listEntPtntSummary(entVO);
    }

    /**
     * 특허상표권현황 상세조회
     */
	@Override
	public EntVO viewEntPtnt(EntVO entVO) throws Exception {
		return entPtntDAO.viewEntPtnt(entVO);
	}
	
	/**
	 * 삭제대상에서 제외처리
	 */
	private void _removeItem(List<EntVO> originals, EntVO item) {
		for (int i = 0 ; i < originals.size(); i++) {
			EntVO target = originals.get(i);
			if (target.getSn().equals(item.getSn())) {
				originals.remove(i);
				return;
			}
		}
	}

    /**
     * 2023.07.27 LSH
     * 특허상표권현황 다중저장처리
     */
	@Override
	public String saveEntPtnt(EntVO entVO, List<EntVO> list) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		if (list == null)
			return null;
		
		int ret = 0;
		
		// 특허상표권현황 목록조회
		entVO.setManualYn(CommConst.YES); // 수정대상
		List<EntVO> originals = listEntPtnt(entVO);
		if (originals == null)
			originals = new ArrayList<EntVO> ();
		
		for (EntVO item : list) {
			
			item.setGsUserNo(entVO.getGsUserNo());
			item.setBzentyNo(entVO.getBzentyNo());
			item.setIrNo    (entVO.getIrNo    ());
			
			String mode = item.getMode();
			Long   sn   = item.getSn();
			
			// 수정인 경우
			if (CommConst.UPDATE.equals(mode)) {
				
				if (sn == null)
					throw processException("error.comm.notProcess");
				
				// 삭제대상에서 제외처리
				_removeItem(originals, item);
				// 특허상표권현황 수정
				ret += entPtntDAO.updtEntPtnt(item);
				
			}
			// 등록인 경우
			else if (CommConst.INSERT.equals(mode)) {
				// 특허상표권현황 등록
				ret += entPtntDAO.regiEntPtnt(item);
			}
			else
				throw processException("error.comm.notProcess");
		}
		// 삭제대상에 있는 경우
		if (originals.size() > 0) {
			for (EntVO target : originals) {
				// 특허상표권현황 삭제
				ret += entPtntDAO.deltEntPtnt(target);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		return null;
	}

    /**
     * 2023.07.24 LSH
     * 특허상표권현황 KODATA 등록여부 조회
     */
	@Override
    public boolean existKodata(String bzentyNo) throws Exception {
        return entPtntDAO.existKodata(bzentyNo);
    }
}