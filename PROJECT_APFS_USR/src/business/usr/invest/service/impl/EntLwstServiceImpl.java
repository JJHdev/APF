package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntLwstService;
import business.usr.invest.service.EntVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 소송현황 Service 구현 클래스
 * 
 * @class   : EntLwstServiceImpl
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntLwstService")
@SuppressWarnings({"all"})
public class EntLwstServiceImpl extends BaseService implements EntLwstService {

    @Resource(name = "EntLwstDAO")
    private EntLwstDAO entLwstDAO;

    /**
     * 소송현황 목록조회
     */
    @Override
    public List listEntLwst(EntVO entVO) throws Exception {
    	return entLwstDAO.listEntLwst(entVO);
    }

    /**
     * 소송현황 상세조회
     */
	@Override
	public EntVO viewEntLwst(EntVO entVO) throws Exception {
		return entLwstDAO.viewEntLwst(entVO);
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
     * 소송현황 다중저장처리
     */
	@Override
	public String saveEntLwst(EntVO entVO, List<EntVO> list) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		if (list == null)
			return null;
		
		int ret = 0;
		
		// 소송현황 목록조회
		List<EntVO> originals = listEntLwst(entVO);
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
				// 소송현황 수정
				ret += entLwstDAO.updtEntLwst(item);
				
			}
			// 등록인 경우
			else if (CommConst.INSERT.equals(mode)) {
				// 소송현황 등록
				ret += entLwstDAO.regiEntLwst(item);
			}
			else
				throw processException("error.comm.notProcess");
		}
		// 삭제대상에 있는 경우
		if (originals.size() > 0) {
			for (EntVO target : originals) {
				// 소송현황 삭제
				ret += entLwstDAO.deltEntLwst(target);
			}
		}		
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		return null;
	}
}