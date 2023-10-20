package business.usr.support.service;

import business.com.CommConst;
import business.usr.CodeConst;
import common.base.BaseModel;
import common.util.CommUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * [VO클래스] - 상담신청 모델 클래스
 *
 * @class   : DscsnVO
 * @author  : LSH
 * @since   : 2023.05.25
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
public class DscsnVO extends BaseModel {

    // 상담신청번호
    private String dscsnAplyNo;
    // 신청업체번호
    private String bzentyNo;
    private String bzentyNm;
    // 상담신청일자
    private String dscsnAplyYmd;
    // 담당자명
    private String picNm;
    // 담당자부서명
    private String picDeptNm;
    // 담당자전화번호
    private String picTelno;
    // 담당자이메일주소
    private String picEmlAddr;
    // 상담내용
    private String dscsnCn;
    // 상담일자
    private String dscsnYmd;
    // 진행상태코드
    private String prgrsSttsCd;
    // 처리결과내용
    private String prcsRsltCn;
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
    // 세션업체회원여부
    private String gsBzentyYn;
    
    // 기본조건
    //---------------------------------
	// 화면단계
    private String stepNo;
    private String stepCd;
    private String stepNm;
    // 신청구분
    private String sprtSeCd;
    private String sprtSeNm;
    // 프로그램
    private String prgrmNo;
    private String prgrmNm;

    // 검색항목
    //---------------------------------
    
    /**
     * 저장시 데이터를 항목에 맞게 REBUILD 한다.
     * 상담신청은 임시저장 없이 제출처리된다.
     */
    public void rebuildProperties() {
    	// 업체번호를 세션정보로 정의
        setBzentyNo(getGsBzentyNo());
    	// 상담신청일자 정의
   		setDscsnAplyYmd(CommUtils.getCurDateString());
    	// 진행상태 정의
   		setPrgrsSttsCd(CodeConst.getSprtPrgrsCode(sprtSeCd, CommConst.SUBMIT));
    }

}
