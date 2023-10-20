package business.usr.mypage.service;

import java.util.ArrayList;
import java.util.List;

import common.base.BaseModel;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 매칭설정관리 모델 클래스
 *
 * @class   : MatchingVO
 * @author  : LSH
 * @since   : 2023.04.29
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
public class MatchingVO extends BaseModel {

    // 사용자번호
    private String userNo;
    // 설정구분코드
    private String stngSeCd;
    // 설정세부코드
    private String stngDtlCd;
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
    // 수정권한여부
    private String updateYn;
    // 조회권한여부
    private String selectYn;
    // 그룹권한
    private String authCd;
    
    // 저장항목
    //---------------------------------
    // 매칭설정 저장대상 목록
    private List<MatchingVO> matchList;
    
    // 투자분야 다중선택값
    private List<String> invtFldList;
    // 사업분야 다중선택값
    private List<String> bizFldList;
    // 펀드규모 다중선택값
    private List<String> fundScaleList;
    // 투자희망금액 다중선택값
    private List<String> invtHopeList;
    // 지원대상 다중선택값
    private List<String> bizTrgtList;
    // 지원연령 다중선택값
    private List<String> bizTrgtAgeList;
    // 창업기간 다중선택값
    private List<String> bizTrgtFntnPdList;
    
    /**
     * 매칭설정 항목별 Enum Class
     * 배열로 넘어온 항목별 배열들을 설정항목별로 정의하여 담는다.
     */
    private enum Setting {
    	INVT_FLD   ("SS1", "invtFldList"      ), // 투자분야
        BIZ_FLD    ("SS2", "bizFldList"       ), // 사업분야
        INVT_HOPE  ("SS3", "invtHopeList"     ), // 투자희망금액
        FUND_SCALE ("SS4", "fundScaleList"    ), // 펀드규모
        BIZ_TRGT   ("SS5", "bizTrgtList"      ), // 지원대상
        BIZ_AGE    ("SS6", "bizTrgtAgeList"   ), // 지원연령
        BIZ_FNTN   ("SS7", "bizTrgtFntnPdList"); // 창업기간
    	
    	String code;
    	String field;
    	
    	Setting(String code, String field) {
    		this.code  = code;
    		this.field = field;
    	}
		
    	public String getCode() {
			return code;
		}
		public String getField() {
			return field;
		}
    	@SuppressWarnings("unchecked")
		List<String> getValueList(MatchingVO obj) {
    		return (List<String>)CommUtils.getMethodValue(obj, getField());
    	}
    	void setValueList(MatchingVO obj, List<String> data) {
    		CommUtils.setMethodValue(obj, getField(), data, List.class);
    	}

    	// 항목단위 문자열배열을 VO를 담은 List로 변환하여 반환한다.
    	List<MatchingVO> getList(List<String> list) {
    		if (CommUtils.isEmptyList(list))
    			return null;
    		List<MatchingVO> ret = new ArrayList<MatchingVO> ();
    		for (String c : list) {
    			// 값이 빈값인 경우 SKIP
    			if (CommUtils.isEmpty(c))
    				continue;
    			
    			ret.add(MatchingVO.builder().stngSeCd(getCode()).stngDtlCd(c).build());
    		}
    		return ret;
    	}

		static Setting get(String stngCd) {
    		if (stngCd == null)
    			return null;
    		for (Setting stng : values()) {
    			if (stngCd.equals(stng.getCode()))
    				return stng;
    		}
    		return null;
    	}
    	
    	// 설정항목 기준으로 입력받은 각 항목배열을 List로 변환, 병합한다.
		static void buildForSave(MatchingVO obj) {
    		if (obj == null)
    			return;
    		List<MatchingVO> ret = new ArrayList<MatchingVO> ();
    		for (Setting stng : values()) {
    			List<String>     value = stng.getValueList(obj);
    			List<MatchingVO> items = stng.getList(value);
    			if (items != null)
    				ret.addAll(items);
    		}
    		obj.setMatchList(ret);
    	}

    	// 조회목록을 화면에 맞는 설정데이터로 변환한다.
		static void buildForSelect(MatchingVO obj, List<MatchingVO> settings) {

    		if (obj == null || CommUtils.isEmptyList(settings))
    			return;
    		
        	for (MatchingVO stg : settings) {
        		Setting stng = Setting.get(stg.getStngSeCd());
        		if (stng != null) {
        	    	List<String> value = stng.getValueList(obj);
        			if (value == null)
        				value = new ArrayList<String> ();

        			value.add(stg.getStngDtlCd());
        			// 항목배열 데이터 추가
        	    	stng.setValueList(obj, value);
        		}
        	}
    	}
    }

    /**
     * 저장시 데이터를 항목에 맞게 REBUILD 한다.
     * : 각 배열항목들을 묶어서 저장형태의 배열로 변환한다.
     */
    public void rebuildProperties() {
    	
    	// 사용자번호를 세션정보로 정의
        setUserNo(getGsUserNo());
    	
    	// 항목별 문자열배열을 저장형태의 객체배열로 변환
    	Setting.buildForSave(this);
    }

    /**
     * 설정목록 데이터를 화면에 맞게 REBUILD 한다.
     * : 목록 데이터를 배열항목으로 변환한다.
     */
    public void rebuildResults(List<MatchingVO> stngList) {
    	if (stngList == null)
    		return;
    	// 조회목록을 화면에 맞는 설정데이터로 변환
    	Setting.buildForSelect(this, stngList);
    }
}
