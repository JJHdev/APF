package business.com.common.service;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 메인 VO
 *
 * @class   : MainVO
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
public class MainVO extends BaseModel {
	//조회 카운트 컬럼
	private int bzentyCnt;
	private int SBCnt;
	private int SACnt;
	private int SCCnt;
	private int bbsCnt;
	
	private int userCnt;
	private int compCnt;
	private int fundCnt;
	private int irCnt;
	
	// 검색 날짜
	private String srchAcsYm;
	// 검색 회원유형
	private String roleId;
	
}