package business.usr.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import commf.paging.PaginatedArrayList;

/**
 * [서비스인터페이스] - 미팅신청 Service Interface
 * 
 * @class   : MeetingService
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@SuppressWarnings("all")
public interface MeetingService {

    /**
     * 미팅신청 페이징목록 조회
     */
    public PaginatedArrayList listMeeting(MeetingVO meetingVO, int currPage, int pageSize) throws Exception;

    /**
     * 미팅신청 목록조회
     */
    public List listMeeting(MeetingVO meetingVO) throws Exception;

    /**
     * 미팅신청 상세조회
     */
    public MeetingVO viewMeeting(MeetingVO meetingVO) throws Exception;

    /**
     * 미팅신청 등록,수정,삭제
     */
    public String saveMeeting(MeetingVO meetingVO) throws Exception;
    
    /**
     * 미팅 개수 조회
     */
	public int listMeetingCount(MeetingVO meetingVO) throws Exception;
}