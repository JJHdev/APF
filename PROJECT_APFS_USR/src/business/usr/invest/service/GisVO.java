package business.usr.invest.service;

import java.util.List;

import common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - Gis 모델 클래스
 *
 * @class   : GisVO
 * @author  : LHB
 * @since   : 2023.06.13
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
public class GisVO extends BaseModel {
	
	// 서비스
	private String service;
	// 버전
	private String version;
	// request type
	private String reqType;
	// 레이어명
	private String layerNm;
	// 최대 피쳐 개수
	private int maxFeatures;
	// output 포맷
	private String outputFormat;
	
	// 업체번호
    private String bzentyNo;
    // 상위업체번호
    private String upBzentyNo;
    // 업체구분코드
    private String bzentySeCd;
    private String bzentySeNm;
    // 사업자등록번호
    private String brno;
    // 법인등록번호
    private String crno;
    // 업체명
    private String bzentyNm;
    // 대표자명
    private String rprsvNm;
    // 설립일자
    private String fndnYmd;
    // 대표전화번호
    private String rprsTelno;
    // 팩스번호
    private String fxno;
    // 업종코드
    private String tpbizCd;
    // 업종명
    private String tpbizNm;
    // 업체유형코드
    private String bzentyTypeCd;
    private String bzentyTypeNm;
    // 업체형태코드
    private String bzentyStleCd;
    private String bzentyStleNm;
    // 투자희망금액
    private Long invtHopeAmt;
    // 이메일주소
    private String emlAddr;
    // 홈페이지주소
    private String hmpgAddr;
    // 우편번호
    private String zip;
    // 소재지주소
    private String lctnAddr;
    // 소재지상세주소
    private String lctnDaddr;
    // 직원수
    private Long empCnt;
    // 관리자메모
    private String mngrMemo;
    // 승인일자
    private String aprvYmd;
    // 반려일자
    private String rjctYmd;
    // 사용상태코드
    private String useSttsCd;
    private String useSttsNm;
    // 위치정보
    private String lcinfo;
    // 위치정보 - 위도
    private String lat;
    // 위치정보 - 경도
    private String lot;

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
    
	// 세션정보
    //---------------------------------
    // 세션사용자번호
    private String gsUserNo;
    // 세션사용자권한
	private String gsRoleId;
    // 세션업체번호
	private String gsBzentyNo;
    
}
