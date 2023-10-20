package business.com.kodata.service;

import org.json.simple.JSONObject;

import commf.exception.BusinessException;
import common.util.JsonUtils;


/**
 *  [컨트롤러클래스] - 한국평가데이터 KODATA 연계 응답 모델 인터페이스
 *
 * @class   : KodataVO
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
public interface KodataVO {
	
	// 모니터링정보 정의
	void setMntInfo(String baseDt, String lqdtCls, String lqdtDt, String lqdtRsn);
	
	// 전문상세코드 반환
	String getJmnoCode();
	
	// API 결과 JSON 데이터를 객체 데이터로 맵핑
	default void fromJSON(JSONObject json) {
		
		if (json == null)
			throw new BusinessException("JSON 데이터가 없습니다.");
		
		// 모니터링정보 가져오기
		JSONObject mnt = (JSONObject)json.get("MNTlqdt0000");
		if (mnt != null) {
			setMntInfo(
				(String)mnt.get("base_dt" ),
				(String)mnt.get("lqdt_cls"),
				(String)mnt.get("lqdt_dt" ),
				(String)mnt.get("lqdt_rsn")
			);
		}
		// 기업일반정보 가져오기
		JSONObject enp = (JSONObject)json.get(getJmnoCode());
		if (enp != null) {
			// JSONObject 데이터를 현재 객체 데이터로 맵핑
			JsonUtils.jsonToObject(enp, this);
		}
	}
}
