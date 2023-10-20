package business.usr.inform.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - survey 모델 클래스
 *
 * @class   : surveyVO
 * @author  : SUK
 * @since   : 2023.07.18
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@EqualsAndHashCode(callSuper=true)
public class SurveyVO extends BaseModel {
	
	// 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
	private String gsRoleId;
	
    // 23.09.14 JH 페이지네이션 추가작업 (객체로 전달하기 위해)
	private Long   srvyNo;
	private String srvyCn;
	private String srvyTrgtCd;
	private String srvyBgngYmd;
	private String srvyBgngDate;
	private String srvyEndYmd;
	private String srvyEndDate;
    // 등록일시
    private String regDttm;
    // 등록일자
    private String regDate;
	private String userNm;
	private String trgtCode;
	private String particiPation;
    
}
