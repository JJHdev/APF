package business.sys.log.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.sys.log.service.DownLogService;
import business.sys.log.service.LogVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 다운로드이력 Service 구현 클래스
 *
 * @class   : DownLogServiceImpl
 * @author  : LSH
 * @since   : 2021.11.04
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("DownLogService")
@SuppressWarnings({"all"})
public class DownLogServiceImpl extends BaseService implements DownLogService {

    @Resource(name = "DownLogDAO")
    private DownLogDAO downLogDAO;

    /**
     * 다운로드이력 페이징목록조회
     */
    @Override
    public PaginatedArrayList listDownLog(LogVO logVO, int currPage, int pageSize) throws Exception {
    	return downLogDAO.listDownLog(logVO, currPage, pageSize);
    }

    /**
     * 다운로드이력 목록조회
     */
    @Override
    public List listDownLog(LogVO logVO) throws Exception {
    	return downLogDAO.listDownLog(logVO);
    }

    /**
     * 다운로드이력 등록
     */
    @Override
    public String regiDownLog(LogVO logVO) throws Exception {
		if (logVO == null)
			throw processException("error.comm.notTarget");
		
		List<Long> keys = logVO.getAtchFileSns();

		// 2022.01.07 LSH 문서번호가 있을 경우
        if (CommUtils.isNotEmpty(logVO.getDtySeCd()) &&
        	CommUtils.isNotEmpty(logVO.getDcmtNo())) {
        	keys = downLogDAO.listDownFile(logVO);
        }

        int cnt = 0;
		// 단건처리
		if (CommUtils.isEmptyList(keys)) {
			cnt += downLogDAO.regiDownLog(logVO);
		}
		// 다중처리
		else {
	        for (Long key : keys) {
	        	logVO.setAtchFileSn(key);
	        	cnt += downLogDAO.regiDownLog(logVO);
	        }
		}
        // 저장결과를 반환한다.
		if (cnt > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
    }

    /**
     * 다운로드이력 다중삭제
     */
	@Override
	public String deltDownLog(LogVO logVO) throws Exception {

		if (logVO == null)
			throw processException("error.comm.notTarget");

		int ret = 0;
		List<LogVO> rows = logVO.getLogList();

		if (rows == null || rows.size() == 0)
			throw processException("error.comm.notTarget");

		for (LogVO row : rows) {
			// 다운로드이력 삭제
			ret += downLogDAO.deltDownLog(row);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
}