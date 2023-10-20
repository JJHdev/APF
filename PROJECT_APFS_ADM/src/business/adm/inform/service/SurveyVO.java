package business.adm.inform.service;

import java.util.ArrayList;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 설문 VO
 *
 * @class   : SurveyVO
 * @author  : ntarget
 * @since   : 2023.07.10
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
public class SurveyVO extends BaseModel {
	
	// 설문번호
	private Long srvyNo;
	// 설문내용
	private String srvyCn;
	// 설문대상코드
	private String srvyTrgtCd;
	// 설문대상이름
	private String srvyTrgtNm;
	// 설문시작일자
	private String srvyBgngYmd;
	// 설문종료일자
	private String srvyEndYmd;
	// 문항번호
	private Long qitemNo;
	private Long[] qitemNoArr;
	// 문항내용
	private String qitemCn;
	private String qitemCnArr;
	// 문항유형
	private String qitemType;
	private String[] qitemTypeArr;
	// 표기번호
	private String markNo;
	private String[] markNoArr;
	// 항목번호
	private Long artclNo;
	private Long[] artclNoArr;
	// 항목내용
	private String artclCn;
	private String artclCnArr;
	
	// 등록자번호
	private String rgtrNo;
	// 등록자이름
	private String rgtrNm;
	// 등록일자
	private String regYmd;
	// 수정자번호
	private String mdfrNo;
	// 수정자이름
	private String mdfrNm;
	// 수정일자
	private String mdfcnYmd;
	
	// 문항개수
	private int qtNum;
	// 응답여부
	private boolean srvyRspn; 
	// 분류코드
	private String code;
	// 분류코드 이름
	private String text;
	// 설문대상 분류코드
	private String srchTrgtCd;
	// 설문제목 검색
	private String srchSrvyCn;
	
	// 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자이름
    private String gsUserNm;
    // 세션사용자권한
    private String gsRoleId;
	
}