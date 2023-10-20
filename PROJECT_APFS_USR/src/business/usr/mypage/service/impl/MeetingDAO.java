 package business.usr.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import business.usr.mypage.service.MeetingVO;
import commf.paging.PaginatedArrayList;
import common.base.BaseDAO;

/**
 * [DAO클래스] - 미팅신청을 관리하는 DAO 클래스
 * 
 * 사용 가능한  DAO Statement Method
 * 1. list          : 리스트 조회시 사용함.
 * 2. pageListOra   : 페이징처리용 리스트조회시 사용함. for Oracle, Tibero
 * 3. view          : 단건조회, 상세조회시 사용함.
 * 4. save          : INSERT, UPDATE, DELETE 모두 사용가능. (Return Type : Integer)
 * 5. insert        : INSERT (Return String : Key 채번 사용함.)
 * 6. update        : UPDATE (Return Type : Integer)
 * 7. delete        : DELETE (Return Type : Integer)
 * 
 *
 * @class   : MeetingDAO
 * @author  : LSH
 * @since   : 2023.05.16
 * @version : 1.0
 *
 *   수정일     수정자               수정내용
 *  -------    --------    ---------------------------
 *
 */

@Repository("MeetingDAO")
@SuppressWarnings({"all"})
public class MeetingDAO extends BaseDAO {

    /**
     * 미팅신청 페이징목록 조회
     */
    public PaginatedArrayList listMeeting(MeetingVO meetingVO, int currPage, int pageSize) {
        return pageList("Meeting.listMeeting", meetingVO, currPage, pageSize);
    }

    /**
     * 미팅신청 목록 조회
     */
    public List listMeeting(MeetingVO meetingVO) {
        return list("Meeting.listMeeting", meetingVO);
    }

    /**
     * 미팅신청 상세 조회
     */
    public MeetingVO viewMeeting(MeetingVO meetingVO) {
        return (MeetingVO)view("Meeting.viewMeeting", meetingVO);
    }

    /**
     * 미팅신청 등록
     */
    public int regiMeeting(MeetingVO meetingVO) {
        return insert("Meeting.regiMeeting", meetingVO);
    }

    /**
     * 미팅신청 수정
     */
    public int updtMeeting(MeetingVO meetingVO) {
        return update("Meeting.updtMeeting", meetingVO);
    }

    /**
     * 미팅신청 삭제
     */
    public int deltMeeting(MeetingVO meetingVO) {
        return delete("Meeting.deltMeeting", meetingVO);
    }

    /**
     * 미팅개수 조회
     */
    public int listMeetingCount(MeetingVO meetingVO) {
        return (int) view("Meeting.listMeetingCount", meetingVO);
    }
}