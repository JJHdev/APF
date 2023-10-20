package business.adm.invest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.adm.CodeConst;
import business.adm.file.service.BizFileService;
import business.adm.file.service.BizFileVO;
import business.adm.file.service.EntFileService;
import business.adm.file.service.EntFileVO;
import business.adm.invest.service.EventService;
import business.adm.invest.service.EventVO;
import business.com.CommConst;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.file.FileInfo;

/**
 * [서비스클래스] - 투자정보관리 - 투자설명회등록 Service 구현 클래스
 * 
 * @class   : EventServiceImpl
 * @author  : LHB
 * @since   : 2023.04.17
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("EventService")
@SuppressWarnings({"all"})
public class EventServiceImpl extends BaseService implements EventService {

    @Resource(name = "EventDAO")
    private EventDAO eventDAO;

    // 업무첨부파일
    @Resource(name = "BizFileService")
    private BizFileService bizFileService;
	
    /**
     * 투자설명회 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEvent(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return eventDAO.listEvent(eventVO, currPage, pageSize);
    }

    /**
     * 투자설명회 목록조회
     */
    @Override
    public List listEvent(EventVO eventVO) throws Exception {
    	return eventDAO.listEvent(eventVO);
    }

    /**
     * 투자설명회 상세조회
     */
	@Override
	public EventVO viewEvent(EventVO eventVO) throws Exception {
		return eventDAO.viewEvent(eventVO);
	}

    /**
     * 투자설명회 등록,수정,삭제
     */
	@Override
	public String saveEvent(EventVO eventVO) throws Exception {
		
		if (eventVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = eventVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 투자설명회 수정
			ret = eventDAO.updtEvent(eventVO);
		} else if (CommConst.INSERT.equals(mode)) {
			// 투자설명회 등록
			ret = eventDAO.regiEvent(eventVO);
		} else if (CommConst.DELETE.equals(mode)) {
			// 투자설명회 삭제
			ret = eventDAO.deltEvent(eventVO);
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	
	
	/**
     * 참여기업(투자자) 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEventInvt(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return eventDAO.listEventInvt(eventVO, currPage, pageSize);
    }
    
    /**
     * 참여기업(투자자) 목록조회
     */
    @Override
    public List listEventInvt(EventVO eventVO) throws Exception {
    	return eventDAO.listEventInvt(eventVO);
    }
    
    /**
     * 참여기업(투자자) 등록,삭제
     */
	@Override
	public String saveEventInvt(EventVO eventVO) throws Exception {
		
		if (eventVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = eventVO.getMode();
		
		if (CommConst.INSERT.equals(mode)) {
			// 참여기업(투자자) 등록
			String[] bzentyNoArr = eventVO.getBzentyNoArr();
			for(int i=0 ; i<bzentyNoArr.length ; i++) {
				eventVO.setBzentyNo(bzentyNoArr[i]);
				
				ret = eventDAO.regiEventInvt(eventVO);
			}
		} else if (CommConst.DELETE.equals(mode)) {
			// 참여기업(투자자) 삭제
			String[] bzentyNoArr = eventVO.getBzentyNoArr();
			for(int i=0 ; i<bzentyNoArr.length ; i++) {
				eventVO.setBzentyNo(bzentyNoArr[i]);
				
				ret = eventDAO.deltEventInvt(eventVO);
			}
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	/**
     * 참여기업(경영체) 페이징목록조회
     */
    @Override
    public PaginatedArrayList listEventEnt(EventVO eventVO, int currPage, int pageSize) throws Exception {
    	return eventDAO.listEventEnt(eventVO, currPage, pageSize);
    }
    
    /**
     * 참여기업(경영체) 목록조회
     */
    @Override
    public List listEventEnt(EventVO eventVO) throws Exception {
    	return eventDAO.listEventEnt(eventVO);
    }
	
	/**
     * 참여기업(경영체) 등록,삭제
     */
	@Override
	public String saveEventEnt(EventVO eventVO, List<FileInfo> files) throws Exception {
		
		if (eventVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = eventVO.getMode();
		
		if (CommConst.INSERT.equals(mode)) {
			// 참여기업(경영체) 등록
			ret = eventDAO.regiEventEnt(eventVO);
			
			// LSH. 첨부파일 사용시 다음과 같이 처리할것
			if (ret > 0) {
				// 첨부파일 저장처리
				BizFileVO fileVO = BizFileVO.builder()
						.taskSeCd(CodeConst.TASK_EVNT)
						.docNo   (eventVO.getEvntPartcptnNo())
						.gsUserNo(eventVO.getGsUserNo())
						.build();
				
				fileVO.setMode(mode);
				
				bizFileService.saveBizFile(fileVO, files);
			}
		} else if (CommConst.DELETE.equals(mode)) {
			// 참여기업(투자자) 삭제
			String[] evntPartcptnNoArr = eventVO.getEvntPartcptnNoArr();
			for(int i=0 ; i<evntPartcptnNoArr.length ; i++) {
				eventVO.setEvntPartcptnNo(evntPartcptnNoArr[i]);
				
				ret = eventDAO.deltEventEnt(eventVO);
				
				// LSH. 첨부파일 사용시 다음과 같이 처리할것
				if (ret > 0) {
					// 첨부파일 저장처리
					BizFileVO fileVO = BizFileVO.builder()
							.taskSeCd(CodeConst.TASK_EVNT)
							.docNo   (eventVO.getEvntPartcptnNo())
							.gsUserNo(eventVO.getGsUserNo())
							.build();
					
					fileVO.setMode(mode);
					
					bizFileService.saveBizFile(fileVO, files);
				}
			}
		}

        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	/**
     * 참여기업(경영체) 추가 가능 여부 확인
     */
    public boolean getEventEntExist(EventVO eventVO) throws Exception {
    	return eventDAO.getEventEntExist(eventVO);
    }
}