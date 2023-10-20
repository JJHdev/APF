package business.com.common.service;

import java.util.Calendar;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 인증관련 공통 모델 클래스
 *
 * @class   : LoginVO
 * @author  : LSH
 * @since   : 2021.10.05
 * @version : 1.0
 *
 *   수정일     수정자              수정내용
 *  -------    --------    ---------------------------
 *  2021.11.08  LSH        인증관련 항목추가
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RandomVO extends BaseModel {
	// 랜덤번호
	private String smsNo;
	// 랜덤번호 종료시간
	private Calendar expired;
}