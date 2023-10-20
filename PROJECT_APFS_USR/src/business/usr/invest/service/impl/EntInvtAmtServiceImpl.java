package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.CodeConst;
import business.usr.invest.service.EntInvtAmtService;
import business.usr.invest.service.EntVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 투자금액 Service 구현 클래스
 * 
 * @class   : EntInvtAmtServiceImpl
 * @author  : LSH
 * @since   : 2023.05.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntInvtAmtService")
@SuppressWarnings({"all"})
public class EntInvtAmtServiceImpl extends BaseService implements EntInvtAmtService {

    @Resource(name = "EntInvtAmtDAO")
    private EntInvtAmtDAO entEtcinvtAmtDAO;

    /**
     * 투자금액 목록조회
     */
    @Override
    public List listEntInvtAmt(EntVO entVO) throws Exception {
    	return entEtcinvtAmtDAO.listEntInvtAmt(entVO);
    }

    /**
     * 투자금액 상세조회
     */
	@Override
	public EntVO viewEntInvtAmt(EntVO entVO) throws Exception {
		return entEtcinvtAmtDAO.viewEntInvtAmt(entVO);
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
     * 투자금액 다중저장처리
     */
	@Override
	public String saveEntInvtAmt(EntVO entVO, List<EntVO> list) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		if (list == null)
			return null;
		
		int ret = 0;
		
		// 투자금액 수정대상 조건 : 기타투자금액
		entVO.setInvtSeCd(CodeConst.INVT_AMT_ETC);
		
		// 투자금액 목록조회
		List<EntVO> originals = listEntInvtAmt(entVO);
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
				// 투자금액 수정
				ret += entEtcinvtAmtDAO.updtEntInvtAmt(item);
				
			}
			// 등록인 경우
			else if (CommConst.INSERT.equals(mode)) {
				// 투자금액 등록
				ret += entEtcinvtAmtDAO.regiEntInvtAmt(item);
			}
			else
				throw processException("error.comm.notProcess");
		}
		// 삭제대상에 있는 경우
		if (originals.size() > 0) {
			for (EntVO target : originals) {
				// 투자금액 삭제
				ret += entEtcinvtAmtDAO.deltEntInvtAmt(target);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		return null;
	}
}