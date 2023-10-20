package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntCoHstService;
import business.usr.invest.service.EntVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 회사연혁 Service 구현 클래스
 * 
 * @class   : EntCoHstServiceImpl
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntCoHstService")
@SuppressWarnings({"all"})
public class EntCoHstServiceImpl extends BaseService implements EntCoHstService {

    @Resource(name = "EntCoHstDAO")
    private EntCoHstDAO entCoHstDAO;

    /**
     * 회사연혁 목록조회
     */
    @Override
    public List listEntCoHst(EntVO entVO) throws Exception {
    	return entCoHstDAO.listEntCoHst(entVO);
    }

    /**
     * 회사연혁 상세조회
     */
	@Override
	public EntVO viewEntCoHst(EntVO entVO) throws Exception {
		return entCoHstDAO.viewEntCoHst(entVO);
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
     * 회사연혁 다중저장처리
     */
	@Override
	public String saveEntCoHst(EntVO entVO, List<EntVO> list) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		if (list == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		
		// 회사연혁 목록조회
		List<EntVO> originals = listEntCoHst(entVO);
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
				// 회사연혁 수정
				ret += entCoHstDAO.updtEntCoHst(item);
				
			}
			// 등록인 경우
			else if (CommConst.INSERT.equals(mode)) {
				// 회사연혁 등록
				ret += entCoHstDAO.regiEntCoHst(item);
			}
			else
				throw processException("error.comm.notProcess");
		}
		// 삭제대상에 있는 경우
		if (originals.size() > 0) {
			for (EntVO target : originals) {
				// 회사연혁 삭제
				ret += entCoHstDAO.deltEntCoHst(target);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		return null;
	}
}