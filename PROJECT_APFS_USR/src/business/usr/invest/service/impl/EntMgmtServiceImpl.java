package business.usr.invest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntMgmtService;
import business.usr.invest.service.EntVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 경영진정보 Service 구현 클래스
 * 
 * @class   : EntMgmtServiceImpl
 * @author  : LSH
 * @since   : 2023.05.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntMgmtService")
@SuppressWarnings({"all"})
public class EntMgmtServiceImpl extends BaseService implements EntMgmtService {

    @Resource(name = "EntMgmtDAO")
    private EntMgmtDAO entMgmtDAO;

    /**
     * 경영진정보 목록조회
     */
    @Override
    public List listEntMgmt(EntVO entVO) throws Exception {
    	return entMgmtDAO.listEntMgmt(entVO);
    }

    /**
     * 경영진정보 상세조회
     */
	@Override
	public EntVO viewEntMgmt(EntVO entVO) throws Exception {
		return entMgmtDAO.viewEntMgmt(entVO);
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
     * 경영진정보 다중저장처리
     */
	@Override
	public String saveEntMgmt(EntVO entVO, List<EntVO> list) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		if (list == null)
			return null;
		
		int ret = 0;
		
		// 경영진정보 목록조회
		List<EntVO> originals = listEntMgmt(entVO);
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
				// 경영진정보 수정
				ret += entMgmtDAO.updtEntMgmt(item);
				
			}
			// 등록인 경우
			else if (CommConst.INSERT.equals(mode)) {
				// 경영진정보 등록
				ret += entMgmtDAO.regiEntMgmt(item);
			}
			else
				throw processException("error.comm.notProcess");
		}
		// 삭제대상에 있는 경우
		if (originals.size() > 0) {
			for (EntVO target : originals) {
				// 경영진정보 삭제
				ret += entMgmtDAO.deltEntMgmt(target);
			}
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		return null;
	}
}