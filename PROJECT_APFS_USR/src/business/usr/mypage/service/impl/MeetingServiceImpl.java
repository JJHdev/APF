package business.usr.mypage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.com.CommConst;
import business.com.common.service.CommService;
import business.com.common.service.EmailService;
import business.usr.CodeConst;
import business.usr.invest.service.EntService;
import business.usr.invest.service.EntVO;
import business.usr.mypage.service.MeetingService;
import business.usr.mypage.service.MeetingVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseService;
import common.util.CommUtils;

/**
 * [서비스클래스] - 미팅신청 Service 구현 클래스
 * 
 * @class   : MeetingServiceImpl
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Service("MeetingService")
@SuppressWarnings({"all"})
public class MeetingServiceImpl extends BaseService implements MeetingService {

    @Resource(name = "MeetingDAO")
    private MeetingDAO meetingDAO;
	
    @Resource(name = "EmailService")
	private EmailService emailService;
	
    @Resource(name = "EntService")
	private EntService entService;
	
    @Resource(name = "CommService")
	private CommService commService;
	
    /**
     * 미팅신청 페이징목록조회
     */
    @Override
    public PaginatedArrayList listMeeting(MeetingVO meetingVO, int currPage, int pageSize) throws Exception {
    	return meetingDAO.listMeeting(meetingVO, currPage, pageSize);
    }

    /**
     * 미팅신청 목록조회
     */
    @Override
    public List listMeeting(MeetingVO meetingVO) throws Exception {
    	return meetingDAO.listMeeting(meetingVO);
    }

    /**
     * 미팅신청 상세조회
     */
	@Override
	public MeetingVO viewMeeting(MeetingVO meetingVO) throws Exception {
		return meetingDAO.viewMeeting(meetingVO);
	}

    /**
     * 미팅신청 등록,수정,삭제
     */
	@Override
	public String saveMeeting(MeetingVO meetingVO) throws Exception {
		
		if (meetingVO == null)
			throw processException("error.comm.notTarget");
		
		int ret = 0;
		String mode = meetingVO.getMode();
		
		if (CommConst.UPDATE.equals(mode)) {
			// 미팅신청 수정
			ret = meetingDAO.updtMeeting(meetingVO);
		}
		else if (CommConst.INSERT.equals(mode)) {
			// 미팅신청 등록
			ret = meetingDAO.regiMeeting(meetingVO);
			if (ret > 0) {
				// 미팅신청 메일발송
				_sendMail(meetingVO);				
			}
		}
		else if (CommConst.DELETE.equals(mode)) {
			// 미팅신청 삭제
			ret = meetingDAO.deltMeeting(meetingVO);
		}
        // 저장결과를 반환한다.
		if (ret > 0)
	        return message.getMessage("prompt.success");
		else
			throw processException("error.comm.notProcess");
	}
	
	// 업체정보 가져오기
	private EntVO _getEnt(String bzentyNo) throws Exception {
		return entService.getEnt(bzentyNo);
	}
	
	// 미팅신청 메일발송처리
	private void _sendMail(MeetingVO meetingVO) throws Exception {
		
		// 대상자정보 조회
		EntVO trgt = _getEnt(meetingVO.getTrgtBzentyNo());
		// 신청자정보 조회
		EntVO aply = _getEnt(meetingVO.getGsBzentyNo());
		// 이메일정보가 없을 경우
		if (trgt == null || CommUtils.isEmpty(trgt.getEmlAddr()))
			return;
		if (aply == null)
			return;
		
		Map<String,String> params = new HashMap<String,String> ();
		params.put("context"     , meetingVO.getGsContext());
		params.put("toName"      , trgt.getBzentyNm());
		params.put("toAddress"   , trgt.getEmlAddr());
		params.put("bzentyNm"    , trgt.getBzentyNm());
		params.put("invtBzentyNm", aply.getBzentyNm());
		// TODO 마이페이지 - 문의내역으로 이동 처리는 불가 (로그인후 사용가능)

		// 메일발송
		emailService.sendBizEmail(params, CodeConst.BIZMAIL_MEETING);
	}

	/**
     * 미팅 개수 조회
     */
	@Override
	public int listMeetingCount(MeetingVO meetingVO) throws Exception {
		return meetingDAO.listMeetingCount(meetingVO);
	}
}