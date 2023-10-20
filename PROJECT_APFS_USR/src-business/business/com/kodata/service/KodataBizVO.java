package business.com.kodata.service;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  [컨트롤러클래스] - 한국평가데이터 KODATA 연계 기업정보 모델 클래스
 *
 * @class   : KodataBizVO
 * @author  : LSH
 * @since   : 2023.03.08
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KodataBizVO implements KodataVO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String kedcd;	              // KED코드 : 기업식별ID
	private String enpNm;	              // 기업체명 : 정식기업체명 (예 : 엘지씨엔에스)
	private String enpNmTrd;	          // 기업체TradeName : 일반적으로 통용되는 기업체명 (예 : LG CNS)
	private String enpNmEng;	          // 기업체명_영문 : 기업체공식영문명 (예 : LG CNS CO.,LTD.)
	private String enpTyp;	              // 기업유형 : 법인기업/개인기업 구분 ( 코드클래스 : 0019 )
	private String enpSze;	              // 기업규모 : 기업규모에 따른 구분 ( 코드클래스 : 0017 )
	private String grdtPlnDt;	          // 졸업예정일 : 한시적중소기업 졸업예정일(YYYYMMDD)
	private String enpFcd;	              // 기업형태코드 : 기업등록형태에 따른 구분 ( 코드클래스 : 0021 )
	private String estbFcd;	              // 설립형태 : 기업 설립형태 구분 ( 코드클래스 : 0037 )
	private String enpScd;	              // 기업상태 : 기업 상태 구분 ( 코드클래스 : 0018 )
	private String enpScdChgDt;	          // 기업상태변동일 : 기업상태 관련 변동일(YYYYMMDD)
	private String pubiFcd;	              // 정부및공공기관구분 : 정부및지자체, 공공기관 여부 구분 ( 코드클래스 : 0058 )
	private String venpYn;	              // 벤쳐기업유무 : 벤쳐기업유무 구분 ( 코드클래스 : 0032 )
	private String enpFormFr;	          // 기업형태앞뒤구분 : 기업형태 약어의 위치 구분 ( 예 : (주)xxx, xxx(주) ) ( 코드클래스 : 0020 )
	private String bzcCd;	              // 업종코드 (표준산업분류 10차) : 기업이 영위하는 주업종코드 ( 코드클래스 : 0042 )
	private String fsBzcCd;	              // 재무업종코드 : 재무제표체계가 구분 적용되는 업종 분류 ( 코드클래스 : 0055 )
	private String grpCd;	              // 그룹코드 : 기업이 계열사로 소속된 그룹코드 ( 별도제공안함 : 그룹명 활용 )
	private String grpNm;	              // 그룹명 : 기업이 계열사로 소속된 그룹명
	private String conoPid;	              // 법인_주민등록번호 : 법인의 법인번호 또는 개인사업자의 주민번호
	private String estbDt;	              // 설립일자 : 법인의 설립일 또는 개인사업자의 영업개시일 (YYYYMMDD)
	private String ipoCd;	              // 기업공개코드 : 상장/코스닥등록/외감/일반법인/개인사업자 ( 코드클래스 : 0016 )
	private String trdbzTptNo;	          // 무역업신고번호 : 무역업신고번호
	private String listDt;	              // 상장일자 : 거래소 상장일자 또는 코스닥 등록일자 (YYYYMMDD)
	private String dlistDt;	              // 상장폐지일자 : 거래소 상장폐지일자 또는 코스닥 등록폐지일자 (YYYYMMDD)
	private String mtxBnkCd;	          // 주거래여신기관코드 : 여신잔액이 가장 많은 금융기관코드 ( 별도제공안함 : 주거래여신기관명 활용 )
	private String mtxBnkNm;	          // 주거래여신기관명 : 여신잔액이 가장 많은 금융기관명
	private String ovdTxBnkCd;	          // 당좌거래은행코드 : 당좌개설은행코드 ( 별도제공안함 : 당좌거래은행명 활용 )
	private String ovdTxBnkNm;	          // 당좌거래은행명 : 당좌개설은행명
	private String acctEddt;	          // 결산기준월일 : 결산기준월일(MMDD)
	private String hpageUrl;	          // 홈페이지 : 홈페이지URL(www.XXX.XXX)
	private String email;	              // 이메일 : 회사 이메일 연락처
	private String stdDt;	              // 정보기준일자 : 최종 입력된 정보의 기준일자(YYYYMMDD)
	private String bzno;	              // 사업자번호 : 본사 사업장의 사업자번호
	private String locZip;	              // 소재지우편번호 : 본사 소재지 우편번호
	private String locAddra;	          // 소재지우편번호주소 : 본사 소재지 우편번호 주소
	private String locAddrb;	          // 소재지_나머지주소 : 본사 소재지 우편번호 이하 주소
	private String telNo;	              // 전화번호 : 본사 전화번호
	private String faxNo;	              // 팩스번호 : 본사 팩스번호
	private long   laborerSum;	          // 상시근로자수(합계) : 해당 직능직급 직전년도 상시근로자(합계)
	private String pdNm;	              // 제품명 : 주요제품 한글명
	private String ksic9BzcCd;	          // 업종코드 (표준산업분류 9차) : 2008년 2월 변경된 표준산업분류코드 ( 코드클래스 : 0104 )
	private String relKedcd;	          // 관련기업KED코드 : 2012.05.02 추가
	private String relEstbDt;	          // 관련기업설립일 : 2012.05.02 추가
	private String locRdnmZip;	          // 도로명소재지우편번호 : 2012.05.02 추가
	private String locRdnmAddra;	      // 도로명소재지 우편번호주소 : 2012.05.02 추가
	private String locRdnmAddrb;	      // 도로명소재지 우편번호이하주소 : 2012.05.02 추가
	private String locRdnmAddrbConfYn;	  // 도로명소재지 주소확인여부 : 2012.05.02 추가 ( 코드클래스 : 1041 )
	private String bzcNm;	              // 업종명 : 수동추가
	private String bzcNmL;	              // 업종명_대분류 : 수동추가
	private String mpd;	                  // 주생산품 : 수동추가
	private String reperName;	          // 대표자명 : 수동추가
	
	// 모니터링정보
	private String baseDt;                // 기준일자
	private String lqdtCls;               // 휴폐업구분 : 국세청제공 기업상태정보 (01: 휴업자, 02: 폐업자)
	private String lqdtDt;                // 휴폐업일자 (YYYYMMDD)
	private String lqdtRsn;               // 휴폐업사유
	
	// 추가정보
	private String mode;

	@Override
	public void setMntInfo(String baseDt, String lqdtCls, String lqdtDt, String lqdtRsn) {
		setBaseDt  (baseDt);
		setLqdtCls (lqdtCls);
		setLqdtDt  (lqdtDt);
		setLqdtRsn (lqdtRsn);
	}

	// 전문상세코드 (JSON 결과 객체명으로 사용됨)
	@Override
	public String getJmnoCode() {
		return "ENPintr0000"; // 기업정보
	}
	/*
	setKedcd              ((String)json.get("kedcd"));
	setEnpNm              ((String)json.get("enp_nm"));
	setEnpNmTrd           ((String)json.get("enp_nm_trd"));
	setEnpNmEng           ((String)json.get("enp_nm_eng"));
	setEnpTyp             ((String)json.get("enp_typ"));
	setEnpSze             ((String)json.get("enp_sze"));
	setGrdtPlnDt          ((String)json.get("grdt_pln_dt"));
	setEnpFcd             ((String)json.get("enp_fcd"));
	setEstbFcd            ((String)json.get("estb_fcd"));
	setEnpScd             ((String)json.get("enp_scd"));
	setEnpScdChgDt        ((String)json.get("enp_scd_chg_dt"));
	setPubiFcd            ((String)json.get("pubi_fcd"));
	setVenpYn             ((String)json.get("venp_yn"));
	setEnpFormFr          ((String)json.get("enp_form_fr"));
	setBzcCd              ((String)json.get("bzc_cd"));
	setFsBzcCd            ((String)json.get("fs_bzc_cd"));
	setGrpCd              ((String)json.get("grp_cd"));
	setGrpNm              ((String)json.get("grp_nm"));
	setConoPid            ((String)json.get("cono_pid"));
	setEstbDt             ((String)json.get("estb_dt"));
	setIpoCd              ((String)json.get("ipo_cd"));
	setTrdbzTptNo         ((String)json.get("trdbz_rpt_no"));
	setListDt             ((String)json.get("list_dt"));
	setDlistDt            ((String)json.get("dlist_dt"));
	setMtxBnkCd           ((String)json.get("mtx_bnk_cd"));
	setMtxBnkNm           ((String)json.get("mtx_bnk_nm"));
	setOvdTxBnkCd         ((String)json.get("ovd_tx_bnk_cd"));
	setOvdTxBnkNm         ((String)json.get("ovd_tx_bnk_nm"));
	setAcctEddt           ((String)json.get("acct_eddt"));
	setHpageUrl           ((String)json.get("hpage_url"));
	setEmail              ((String)json.get("email"));
	setStdDt              ((String)json.get("std_dt"));
	setBzno               ((String)json.get("bzno"));
	setLocZip             ((String)json.get("loc_zip"));
	setLocAddra           ((String)json.get("loc_addra"));
	setLocAddrb           ((String)json.get("loc_addrb"));
	setTelNo              ((String)json.get("tel_no"));
	setFaxNo              ((String)json.get("fax_no"));
	setLaborerSum         ((Long)json.get("laborer_sum"));
	setPdNm               ((String)json.get("pd_nm"));
	setKsic9BzcCd         ((String)json.get("ksic9_bzc_cd"));
	setRelKedcd           ((String)json.get("rel_kedcd"));
	setRelEstbDt          ((String)json.get("rel_estb_dt"));
	setLocRdnmZip         ((String)json.get("loc_rdnm_zip"));
	setLocRdnmAddra       ((String)json.get("loc_rdnm_addra"));
	setLocRdnmAddrb       ((String)json.get("loc_rdnm_addrb"));
	setLocRdnmAddrbConfYn ((String)json.get("loc_rdnm_addrb_conf_yn"));
	setBzcNm              ((String)json.get("bzc_nm"));
	setBzcNmL             ((String)json.get("bzc_nm_l"));
	setMpd                ((String)json.get("mpd"));
	setReperName          ((String)json.get("reper_name"));
	*/
	
}
/* 전문샘플
 * 
    
    {
    	"KED":{
    		"HEADER":{
    			"userid":"apfsonl",
    			"process":"s",
    			"bzno":"6038118351",
    			"cono":"",
    			"pid":"",
    			"pidagryn":"N",
    			"jmno":"apf001",
    			"format":"JSON",
    			"resptm":"20230717113447",
    			"errcd":"00",
    			"errctt":"OK"
    		},
    		"CONTENTS":{
    			"ENPintr0000":{
    				"kedcd":"0007124701",
    				"enp_nm":"수영전기",
    				"enp_nm_trd":"(주)수영전기",
    				"enp_nm_eng":"SU-YOUNG ELECTRIC CO.,LTD.",
    				"bzno":"6038118351",
    				"cono_pid":"1801110694297",
    				"reper_name":"정장재",
    				"enp_typ":"1",
    				"enp_sze":"06",
    				"enp_fcd":"01",
    				"ipo_cd":"5",
    				"enp_scd":"01",
    				"enp_scd_chg_dt":"",
    				"enp_form_fr":"1",
    				"bzc_cd":"F42311",
    				"bzc_nm":"일반전기 공사업",
    				"loc_zip":"46982",
    				"loc_addra":"부산 사상구 괘감로",
    				"loc_addrb":"131, 707호 (감전동,삼주오피스텔)",
    				"tel_no":"051 208 1487",
    				"fax_no":"051 208 7446",
    				"hpage_url":"",
    				"email":"hrunyilz@nate.com",
    				"estb_dt":"20100205",
    				"grp_nm":"",
    				"mpd":"전기공사",
    				"std_dt":"20220728"
    			}
    		}
    	}
    }
 */