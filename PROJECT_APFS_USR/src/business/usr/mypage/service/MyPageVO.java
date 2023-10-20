package business.usr.mypage.service;

import java.util.List;

import business.usr.file.service.BizFileVO;
import business.usr.invest.service.FundVO;
import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 마이페이지 모델 클래스
 *
 * @class   : MyPageVO
 * @author  : LSH
 * @since   : 2023.06.19
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class MyPageVO extends BaseModel {

	// 신청번호
	private String aplyNo;
	// 신청종류코드
	private String aplyCd;
	// 신청종류명칭
	private String aplyNm;
	// 신청종류건수
	private Long   aplyCnt;
	// 신청구분타입
	private String aplyType;
	// 프로그램번호
	private String prgrmNo;

    // 공통정보
    //---------------------------------
    // 사용자번호
    private String userNo;
    // 등록자번호
    private String rgtrNo;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
    // 수정자번호
    private String mdfrNo;
    // 수정일시
    private String mdfDttm;
    // 수정일자
    private String mdfDate;
    
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
    private String gsRoleId;
    // 세션업체번호
    private String gsBzentyNo;
    // 업체회원여부
    private String gsBzentyYn;
    // 정렬방식
    private String ordrType;
    // 승인여부
    private String accessYn;
    // 대표여부
    private String rprsYn;
	// 수정필요여부
	private String updateYn;
	// KODATA항목여부
	private String kodataYn;
    // 접근권한코드 (기업정보)
    private String authCd;
	// 조회가능여부
	private String selectYn;
    
    // 저장대상 신청파일목록
    private List<BizFileVO> saveFiles;
    // 삭제대상 신청파일목록
    private List<BizFileVO> removeFiles;

    // 지원상태코드
    private String sprtSttsCd;
    // 펀드지원정보 다중입력값 (저장시사용)
    private List<FundVO> sprtList;
   
}
