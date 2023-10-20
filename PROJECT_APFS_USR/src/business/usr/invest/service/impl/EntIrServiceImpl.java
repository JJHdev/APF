package business.usr.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.usr.invest.service.EntIrService;
import business.usr.invest.service.EntVO;
import common.base.BaseService;

/**
 * [서비스클래스] - 업체IR정보 Service 구현 클래스
 * 
 * @class   : EntIrServiceImpl
 * @author  : LSH
 * @since   : 2023.05.09
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EntIrService")
@SuppressWarnings({"all"})
public class EntIrServiceImpl extends BaseService implements EntIrService {

    @Resource(name = "EntIrDAO")
    private EntIrDAO entIrDAO;

    /**
     * 업체IR정보 목록조회
     */
    @Override
    public List listEntIr(EntVO entVO) throws Exception {
    	return entIrDAO.listEntIr(entVO);
    }

    /**
     * 업체IR정보 상세조회
     */
	@Override
	public EntVO viewEntIr(EntVO entVO) throws Exception {
		return entIrDAO.viewEntIr(entVO);
	}

    /**
     * 2023.07.27 LSH
     * 업체IR정보 저장처리
     */
	@Override
	public String saveEntIr(EntVO entVO) throws Exception {
		
		if (entVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = entVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 업체IR정보 수정
			ret = entIrDAO.updtEntIr(entVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 업체IR정보 등록
			ret = entIrDAO.regiEntIr(entVO);
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 업체IR정보 삭제
			ret = entIrDAO.deltEntIr(entVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}

    /**
     * 업체IR정보 작성여부 확인
     */
	@Override
    public boolean existEntIr(String bzentyNo) throws Exception {
    	return entIrDAO.existEntIr(EntVO.builder().bzentyNo(bzentyNo).build());
    }

    /**
     * 업체IR정보 작성여부 확인
     */
	@Override
    public boolean existEntIr(String bzentyNo, String prgrsSttsCd) throws Exception {
    	return entIrDAO.existEntIr(EntVO.builder().bzentyNo(bzentyNo).prgrsSttsCd(prgrsSttsCd).build());
    }

    /**
     * 2023.07.28 LSH
     * 업체번호 기준 최종IR번호 조회
     */
	@Override
    public String getLastEntIrNo(String bzentyNo) throws Exception {
    	return entIrDAO.getLastEntIrNo(EntVO.builder().bzentyNo(bzentyNo).build());
    }

    /**
     * 2023.07.28 LSH
     * 업체번호 기준 최종IR번호 조회
     */
	@Override
    public String getLastEntIrNo(String bzentyNo, String prgrsSttsCd) throws Exception {
    	return entIrDAO.getLastEntIrNo(EntVO.builder().bzentyNo(bzentyNo).prgrsSttsCd(prgrsSttsCd).build());
    }

    /**
     * 2023.07.28 LSH
     * 업체IR정보 조회수증가
     */
    public int updtEntIrInqCnt(String irNo) throws Exception {
        return entIrDAO.updtEntIrInqCnt(irNo);
    }
}