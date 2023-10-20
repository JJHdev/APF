/**
******************************************************************************************
*** 파일명    : comm_const.js
*** 설명      : 공통상수 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.08.05              LSH
******************************************************************************************
**/
// 시스템구분
const SYSTEM = {
	CODE:  'USR',
	ADMIN: {code: 'ADM', text: '관리자시스템'},
	USER:  {code: 'USR', text: '사용자시스템'}
};

const BBS_TASK_CD = {
	CODE:  'BB1',
};

// 화면 상수
let SCREEN = {
	ROOT_PATH: '',
	MENU_LIST: [],
	MENU_ID  : '',
	MENU_NM  : '',
	PROG_ID  : '',
	PROG_NM  : '',
	ROLE_ID  : '',
	USER_NO  : '',
	BZENT_NO : '',
	LOGIN    : false,
	ROLE     : {
		ADM: false,
		USR: false,
		BIZ: false,
		INV: false,
		ENT: false,
		RIS: false,
		MNG: false,
	}
};
// 화면 상수 정의
const doScreen = function( args ) {
	$.extend(true, SCREEN, args);
};

// 설정 상수
const CONFIG = {
	// 지원신청 동의항목 사용여부
	SPRT_AGREE: false,
	// 회원가입 본인인증 사용여부
	JOIN_MOBILIANS: true
};

// 로그인 상수
const LOGIN = {
	SUCCESS: 'OK',
	REJECT : 'ER'
};
// 숫자 상수
const NUMBER = {
	// 첨부파일 용량제한 BYTES
	// (바이트단위: 500MB), 524288000
	// (바이트단위: 200MB), 209715200
	// (바이트단위: 100MB), 104857600
	// 2023.08.31 100MB -> 200MB로 변경
	FILE_MAXBYTES: 209715200,
	// 첨부파일 파일명 최대길이 (60자)
	FILE_MAXLENGTH: 60,
	// 펀드규모 최소값
	FUND_MIN: 0,
	// 펀드규모 최대값 (1000억)
	FUND_MAX: 1000,
	// 매출액 기준년도개수
	FNNR_CNT: 3
};

// 문자열 상수
const STRING = {
	NOINFO: '정보없음',
	// TODO 매출액 계정코드 (나중에 확인해야함)
	FNNR_CODE: '7001170100',
	// 금액단위
	UNIT: {
		KKRW: '천원',
		MKRW: '백만원',
		BKRW: '억원'
	}
};

// 공통 상수
const COMMONS = {
	// 첨부파일 기본허용 확장자 배열
	FILE_EXTENSIONS: [
		"txt" ,"pdf", "hwp", "doc", "docx", "ppt", "pptx", "xls","xlsx",
		"jpg", "jpeg", "png", "gif", "bmp", "svg",
		"zip", "alz", "7z"
	],
	// 이미지 첨부파일 허용 확장자 배열
	IMAGE_EXTENSIONS: ["jpg", "jpeg", "png", "gif", "bmp", "pdf", "svg"],
	// 이미지 첨부파일 허용 확장자 배열 (PDF,BMP제외)
	IMAGE_NOT_PDF_EXTENSIONS: ["jpg", "jpeg", "png", "gif", "svg"],
	// 문서 첨부파일 허용 확장자 배열
	DOCS_EXTENSIONS: ["txt","pdf","hwp","doc","docx","ppt","pptx","xls","xlsx"]
};

// 화면 공통 URL
const CURL = {
	MAIN       : '/usr/main/main.do',
	LOGIN      : '/com/common/login.do',
	LOGOUT     : '/com/common/logout.do',
	JOIN       : '/com/user/openJoin.do',
	AGRO_CROWD : 'http://agrocrowd.kr/',
};
// 편집 모드
const MODE = {
	INSERT:  'I',
	UPDATE:  'U',
	REMOVE:  'D',
	SAVE:    'S',
	LOAD:    'L',
	LIST:    'LIST',
	VIEW:    'VIEW',
	SUBMIT:  'SUBMIT',
	TMPSAVE: 'TMPSAVE',
	MYPAGE:  'MYPAGE',
	ADD:     'ADD',
	LOGIN:   'LOGIN',
	MAIN:    'MAIN',
	SEARCH:  'SEARCH',
	SELECT:  'SELECT',
	MATCH:   'MATCH',
	GIS:     'GIS',
	JOIN:    'JOIN',
	OPENAPI: 'OPENAPI'
};

// 메뉴 코드
const MENU = {
	SEARCH: 'MU_USR_SRC' // 통합검색
};

// 액션 모드
const ACT = {
	GROUP_CODE : 'CODE', // 그룹코드저장
	GROUP_ROLE : 'ROLE', // 그룹권한저장
	GROUP_RPRS : 'RPRS', // 대표계정변경
	BZ_INFO    : 'BI'  , // 회원가입/기업정보 - 기본정보 (등록/수정)
	BZ_FILE    : 'BF'  , // 회원가입/기업정보 - 상세정보 (등록/수정)
	IR_INFO    : 'II'  , // IR작성하기 - 대시보드 (등록/수정)
	IR_FILE    : 'IF'  , // IR작성하기 - 상세정보 (등록/수정)
};

// 공통코드
const CODE = {
	USER: {
		USR: 'U', // 일반회원
		ENT: 'B'  // 기업회원
	},
	// 시스템구분
	SYS_SE         : {code: 'CT.SYS_SE'         
		,ADMIN: 'ADM'
		,USER : 'USR'
	},
	// 가입구분
	JOIN_CD        : {         
		 USR:  'U' // 개인회원가입
		,BIZ:  'B' // 기업회원가입
	},
	// 파일타입
	FILE_TYPE      : {
		FRM: 'FRM', // 양식파일
		BBS: 'BBS',
		BIZ: 'BIZ',
		ENT: 'ENT'
	},
	// 로그인구분
	LGN_SE         : {code: 'CT.LGN_SE'         
		,NAVER:  'N' // 네이버 회원가입
		,KAKAO:  'K' // 카카오 회원가입
		,PERSON: 'P' // 일반 회원가입
	},
	// 권한구분
	AUTH_SE        : {code: 'CT.AUTH_SE'        },
	// 프로그램분류
	PROG_TY        : {code: 'CT.PROG_TY'        },
	// 프로그램구분(투자유치 전)
	PRGRM_SE	   : {code: 'CT.PRGRM_SE'		},
	// 프로그램 신청구분
	APLY_SE   	   : {code: 'CT.APLY_SE'		},
	// 유선번호구분
	TELNO_SE       : {code: 'CT.TELNO_SE'       },
	// 휴대번호구분
	MBL_TELNO_SE   : {code: 'CT.MBL_TELNO_SE'   },
	// 이메일구분
	EML_DOMAIN_SE  : {code: 'CT.EML_DOMAIN_SE'  },
	// 시도구분
	CTPV_SE        : {code: 'CT.CTPV_SE'        },
	// 성별구분
	SXDST_SE       : {code: 'CT.SXDST_SE'       },
	// 파일구분
	FILE_SE        : {code: 'CT.FILE_SE'        
		,IMGE: '00' // 이미지파일
		,FILE: '01' // 일반파일
		,BREG: '02' // 사업자등록증
		,DLGT: '03' // 위임장
		,OFFC: '04' // 재직증명
		,PLAN: '05' // 사업계획서
		,ADOC: '06' // IR첨부서류
	},
	// 게시판업무구분
	BBS_DTY_SE     : {code: 'CT.BBS_DTY_SE'     },
	// 접속구분
	CNTN_SE        : {code: 'CT.CNTN_SE'        },
	// 서류업무구분코드
	DCMNT_TASK_SE  : {code: 'CT.DCMNT_TASK_SE'  },
	// 문서구분
	DCMNT_SE       : {code: 'CT.DCMNT_SE'       },
	// 정렬구분
	SORT_SE        : {code: 'CT.SORT_SE'        },
	// 송수신구분
	TRSMRCV_SE     : {code: 'CT.TRSMRCV_SE'     },
	// 처리결과
	PRCS_RSLT      : {code: 'CT.PRCS_RSLT'      },
	// 발송결과
	SNDNG_RSLT     : {code: 'CT.SNDNG_RSLT'     },
	// 사용상태
	USE_STUS       : {code: 'CT.USE_STUS'
	    ,UNUSED: '0'  // 미사용
	    ,USABLE: '1'  // 사용(승인)
	    ,REJECT: '9'  // 반려
	},
	// 승인상태
	APRV_STTS	   : {code: 'CT.APRV_STTS'      },
	// 진행상태
	PRGRE_STUS1    : {code: 'CT.PRGRE_STUS1'    
		,ING : '10' // 모집중
		,PRE : '20' // 모집예정
		,END : '90' // 모집종료
	},
	// 진행상태(지원사업)
	PRGRS_STTS1       : {code: 'CT.PRGRS_STTS1'       },
	// 진행세부상태
	PRGRS_DETAIL_STTS : {code: 'CT.PRGRS_DETAIL_STTS' },
	// 지원상태
	SPRT_STUS      : {code: 'CT.SPRT_STUS'      
		,WAIT: '10' // 미검토		
		,HOLD: '20' // 보류		
		,PASS: '90' // 적합		
	},
	// 신청상태
	APLY_STUS      : {code: 'CT.APLY_STUS'      },
	// 진행상태(지원사업)
	PRGRE_STUS3    : {code: 'CT.PRGRE_STUS3'    },
	// 상담센터
	DSCSN_CNTR     : {code: 'CT.DSCSN_CNTR'     },
	// 상담종류
	DSCSN_MTHD     : {code: 'CT.DSCSN_MTHD'     },
	// 차수
	PRCS_CYCL	   : {code: 'CT.PRCS_CYCL'      },
	
	// 게시판구분
	BBS_SE         : {code: 'CT.BBS_SE'         
		,QNA01: 'B20'//1:1질문
		,FAQ01: 'B21'//자주묻는질문
	},
	// 게시물분류
	NTT_CL         : {code: 'CT.NTT_CL'
		,BBS00: '00'  // 게시판 전체 분류
		,NTC01: 'B101'// 경영체대상공고', '공지사항'
		,NTC02: 'B102'// 투자자대상공고', '공지사항'
		,FAQ01: 'B211'// 지원사업', '자주묻는질문'
		,FAQ02: 'B212'// 농림수산식품크라우드펀딩', '자주묻는질문'
		,FAQ03: 'B213'// 농림수산식품모태펀드', '자주묻는질문'
		,FAQ04: 'B214'// 투자절차', '자주묻는질문'
		,FAQ05: 'B215'// 모태펀드소개', '자주묻는질문'
		,DTA01: 'B301'// 연구개발', '자료실'
		,DTA02: 'B302'// 학술연구자료', '자료실'
		,DTA03: 'B303'// 정책동향', '자료실'
		,DTA04: 'B304'// 투자유치', '자료실'
		,DTA05: 'B305'// 크라우드펀딩', '자료실'
		,DTA06: 'B306'// 기타', '자료실'
	},
	// 문의유형
	INQRY_CL       : {code: 'CT.INQRY_CL'       },
	// 문의상태
	INQRY_STUS     : {code: 'CT.INQRY_STUS'     },
	// 설문대상
	QSTNN_TRGT     : {code: 'CT.QSTNN_TRGT'     },
	// 설문문항유형
	QESITM_TY      : {code: 'CT.QESITM_TY'      },
	// 업체구분
	BZENTY_SE      : {code: 'CT.BZENTY_SE'      
		,INV: '10' // 투자자
		,ENT: '20' // 경영체
		,RIS: '30' // 유관기관
	},
	// 업체형태
	BZENTY_STLE    : {code: 'CT.BZENTY_STLE'    },
	// 업체유형
	BZENTY_TYPE    : {code: 'CT.BZENTY_TYPE'    
		,LEGAL: '1' // 법인기업
		,SOLE : '2' // 개인기업
		,INDV : '3' // 개인회원
	},
	// 업체규모
	BZENTY_SCALE   : {code: 'CT.BZENTY_SCALE'   },
	// 사업분야
	BIZ_RLM        : {code: 'CT.BIZ_RLM'        },
	// 지원분야
	SPRT_RLM       : {code: 'CT.SPRT_RLM'       },
	// 지원대상
	SPRT_TRGT      : {code: 'CT.SPRT_TRGT'      
		,SOLE  : 'ST1' // 개인사업자
		,LEGAL : 'ST2' // 법인사업자
		,PRE   : 'ST3' // 예비창업자
		,ETC   : 'ST9' // 기타
	},
	// 지원연령
	SPRT_AGE       : {code: 'CT.SPRT_AGE'       },
	// 창업기간
	FNTN_WHL       : {code: 'CT.FNTN_WHL'       
		,PRE   	: 'U00' // 예비창업자
		,NM   	: '예비창업자' // 예비창업자
	},
	// 접수기간구분
	RCPT_WHL_SE    : {code: 'CT.RCPT_WHL_SE'    },
	// 접수방법
	RCPT_MTHD      : {code: 'CT.RCPT_MTHD'      },
	// 마이페이지 신청구분
	MYPG_APLY_SE   : {code: 'CT.MYPG_APLY_SE'   
		,BEFORE  : 'SB' // 투지유치 전 지원
		,AFTER   : 'SA' // 투지유치 후 지원
		,CROWD   : 'SC' // 농식품 크라우드펀딩
		,IR      : 'IR' // IR 지원현황
		,BOOKMARK: 'BM' // 북마크
		,MEETING : 'MT' // 미팅 신청 내역
		,FUND    : 'FD' // 경영체 지원현황
	},
	// 지원신청구분
	SPRT_APLY_SE   : {code: 'CT.SPRT_APLY_SE'   
		,BEFORE : 'SB' // 투지유치 전 지원
		,AFTER  : 'SA' // 투지유치 후 지원
		,CROWD  : 'SC' // 농식품 크라우드펀딩 지원
	},
	// 지원신청종류
	PRGRM_NO : { 
		 DISCUSS   : 'SA000' // 상담신청		
		,PURCHASE  : 'SA001' // 구매상담회		
		,MENTORING : 'SA002' // 수출초보기업 멘토링데이		
		,POPSTORE  : 'SA003' // 팝업스토어		
		,DOMEXPO   : 'SA004' // 국내 대규모 박람회 참가		
		,OVSEXPO   : 'SA005' // 해외 박람회 참가		
		,USRMKT    : 'SA006' // 수요자 제안형 마케팅		
		,INFMKT    : 'SA007' // 인플루언서 마케팅		
		,PRCMKT    : 'SA008' // PR·콘텐츠 마케팅		
		,ONLINE    : 'SA009' // 온라인교육	
		,ONLMKT    : 'SA010' // 온라인마케팅	
		,SCALEUP   : 'SA011' // 스케일업 프로그램		
		,AFTER     : 'SA012' // 후속투자 유치지원		
		,CFRNC     : 'SA013' // 농식품 산업 투자생태계 활성화 컨퍼런스		
		,COATING   : 'SC014' // 현장코칭 지원		
		,CONSULT   : 'SC015' // 컨설팅 비용지원		
		,FEE       : 'SC016' // 수수료 비용지원
	},
	// 지원신청그룹
	PRGRM_TY : { 
		 DSCSN     : {code:'PT0', color: 'black' } // 상담신청 (코드없음)
		,OFFLINE   : {code:'PT1', color: 'blue'  } // 오프라인
		,ONLINE    : {code:'PT2', color: 'green' } // 온라인
		,PROPOSE   : {code:'PT3', color: 'orange'} // 수요자제안형
		,ETC       : {code:'PT4', color: 'yellow'} // 기타
		,CROWD     : {code:'PTC', color: 'orange'} // 크라우드펀딩 (코드없음)		
	},
	// 유관기관구분
	CRDNS_SE       : {code: 'CT.CRDNS_SE'       },
	// 투자관심도
	INVT_INTRST    : {code: 'CT.INVT_INTRST'    },
	// 처리상태
	PRCS_STUS_CD   : {code: 'CT.PRCS_STUS_CD'   },
	// 지적재산권구분
	ILLT_REG_SE    : {code: 'CT.ILLT_REG_SE'    },
	// 공고상태
	PBANC_STUS     : {code: 'CT.PBANC_STUS'     
		,PRE    	:'10' //접수기간 : 예정
		,OPEN   	:'20' //접수기간 : 상시모집
		,DTINS      :'30' //접수기간 : 기간입력
	},
	// 재무구분
	FNNR_SE        : {code: 'CT.FNNR_SE'        
		,FNLT : '1' // 재무상태표
		,PLOS : '2' // 손익계산서
	},
	// 데이터구분
	DATA_SE        : {code: 'CT.DATA_SE'        
		,KODATA : 'K' // KODATA
		,MANUAL : 'M' // 수기입력
	},
	// 투자희망금액구분(펀드규모)
	FUND_SIZE      : {code: 'CT.FUND_SIZE'      },
	// 신청처리결과
	APLY_PRC_RSLT  : {code: 'CT.APLY_PRC_RSLT'  },
	// 조합상태
	ASSC_STUS      : {code: 'CT.ASSC_STUS'      },
	// 업무구분
	TASK_SE        : {code: 'CT.TASK_SE'        
		,FUND  : 'TS001' // 펀드정보		
		,ENTIR : 'TS002' // 기업IR정보		
		,PBANC : 'TS003' // 사업공고		
		,SPRT  : 'TS004' // 투자지원신청		
		,EVENT : 'TS005' // 행사참여경영체		
	},
	// 연계코드
	LINK_SE        : {code: 'CT.LINK_SE'        },
	// 분야구분
	FLD_SE         : {code: 'CT.FLD_SE'         },
	// 권한항목구분
	AUTH_ITM_SE    : {code: 'CT.AUTH_ITM_SE'    },
	// 특허상표구분
	PTNT_SE        : {code: 'CT.PTNT_SE'        },
	// 북마크구분
	BKMK_SE        : {code: 'CT.BKMK_SE'        
		,ENT   : 'BM1' // 경영체
		,PBANC : 'BM2' // 지원사업
	},
	// 설정구분
	STNG_SE        : {code: 'CT.STNG_SE'        },
	// 업종
	INDUTY_SE      : {code: 'CT.INDUTY_SE'      },
	// 투자금액구분
	INVT_SE        : {code: 'CT.INVT_SE'      
		,FUND: 'IAF' // 농식품모태펀드 투자금액
		,ETC : 'IAR' // 기타 투자금액
	},
	// 투자단계구분
	INVT_STEP_SE   : {code: 'CT.INVT_STEP_SE'   },
	// 지원신청 서류구분
	PAPE_GROUP  : {
		 BEFORE : 'D00' // 투자유치 전 지원   
		,AFTER  : 'D01' // 투지유치 후 지원
		,CROWD  : 'D02' // 농식품 크라우드펀딩 지원
	},
	// 지원신청 서류신청구분
	PAPE_TYPE   : {   
		 ENT  : '20' // 경영체
		,USR  : '40' // 개인
	},
	// 진행상태(코드없음)
	STATUS_CD   : {    
		 TMPSAVE: '00' // 임시저장
		,WAITING: '10' // 심사필요
		,REJECT : '20' // 반려
		,SPLMNT : '30' // 보완요청
		,REVIEW : '40' // 심사완료
		,FINISH : '90' // 사업종료
	},
	// 검색모드
	SRCH_MODE   : {    
		 MATCHING : 'M' // 매칭서비스
		,BOOKMARK : 'B' // 북마크
		,APPLY    : 'A' // 지원목록
	},
	// 마이페이지 그룹관리구분
	MYPG_GROUP_SE : { code: 'CT.MYPG_GROUP_SE'
		,GCODE   : 'GC' // 그룹코드
		,GMEMBER : 'GM' // 멤버관리
		,GROLE   : 'GR' // 권한관리
	},
	// 마이페이지 IR작성하기구분(코드없음)
	MYPG_ENTIR_SE : {
		 DASH    : 'ED' // 대시보드
		,INFO    : 'EI' // 상세정보
	}
};

const CODES = {
	// 대표 유관기관 목록
	CRNDS_LIST: [
		 {code: '01', key: 'APFS', bzentyNo: '500149'} // 농업정책보험금융원
		,{code: '02', key: 'IPET', bzentyNo: '500150'} // 농림식품기술기획평가원
		,{code: '03', key: 'KOAT', bzentyNo: '500151'} // 한국농업기술진흥원
		,{code: '04', key: 'FOOD', bzentyNo: '500152'} // 한국식품산업클러스터진흥원
		,{code: '99', key: 'ETCS', bzentyNo: ''} // 기타
	],
	ICON_LIST: [
		 'icon-book-bookmark'
		,'icon-book-open-text'
		,'icon-briefcase'
		,'icon-building'
		,'icon-calendar'
		,'icon-clipboard-edit'
		,'icon-clipboard-text'
		,'icon-coins'
		,'icon-document-list'
		,'icon-file-list'
		,'icon-globe-earth'
		,'icon-layer-group'
		,'icon-lightbulb'
		,'icon-presentation-text'
		,'icon-puzzle'
		,'icon-user-alt'
		,'icon-user-viewfinder'
	],
	// 가입단계 목록
	JOIN_STEP_LIST: [
		{code : 'CHOS', icon: 'icon-cursor-click'          , text : '가입 유형 선택', filter: function() { return false; }},
		{code : 'AGRE', icon: 'icon-clipboard-list-check F', text : '약관 수집 동의'},
		{code : 'FUSR', icon: 'icon-pen-line'              , text : '기본 정보 입력'},
		{code : 'FBIZ', icon: 'icon-file-text-edit'        , text : '기업 정보 입력', filter: function(key) { return (key == CODE.JOIN_CD.BIZ); }},
		{code : 'DONE', icon: 'icon-check-double'          , text : '가입 완료'}
	],
	// 지원단계 목록
	SPRT_STEP_LIST: [
		{code : 'CHOS', icon: 'icon-cursor-click'        , text : '지원사업 선택' , filter: function(secd) { return (secd != CODE.SPRT_APLY_SE.BEFORE); }},
		{code : 'AGRE', icon: 'icon-clipboard-list-check', text : '약관 수집 동의', 
			filter: function(secd, prno) {
				// 2023.09.19 수수료,컨설팅비용 약관동의 추가
				if (secd == CODE.SPRT_APLY_SE.CROWD && ( prno == CODE.PRGRM_NO.FEE || prno == CODE.PRGRM_NO.CONSULT))
					return true;
				else
					return CONFIG.SPRT_AGREE; 
			}
		},
		{code : 'FORM', icon: 'icon-pen-line'            , text : '신청서 작성'},
		{code : 'FILE', icon: 'icon-file-text-edit'      , text : '제출서류', filter: function(secd, prno) { return !(secd == CODE.SPRT_APLY_SE.AFTER && prno == CODE.PRGRM_NO.DISCUSS); }},
		{code : 'APLY', icon: 'icon-check-double'        , text : '접수완료'}
	],
	// 처리상태 목록
	STATUS_LIST: [
		{code: '10', btn: 'btn-lavendar-ouline-hover'  , icon: 'icon-pen-tool'       , text: '심사중'  }, 
		{code: '20', btn: 'btn-red-ouline-hover'       , icon: 'icon-file-export-alt', text: '반려'    }, 
		{code: '30', btn: 'btn-red-ouline-hover'       , icon: 'icon-file-export-alt', text: '보완요청'}, 
		{code: '40', btn: 'btn-deep-green-ouline-hover', icon: 'icon-check-double'   , text: '심사완료'}, 
		{code: '90', btn: 'btn-deep-green-ouline-hover', icon: 'icon-check-double'   , text: '사업종료'} 
	]
};

const ROOT_CODE = {code: 'NONE', text: '최상위코드'};
const ROOT_MENU = {code: 'NONE', text: '최상위메뉴'};
const ROOT_ROLE = {code: 'NONE', text: '최상위역할'};

const COMBO = {
	INIT_ALL    : {code:'', text:'전체'},
	INIT_DIRECT : {code:'', text:'직접입력'},
	INIT_NOT    : {code:'', text:'선택안함'},
	INIT_SELECT : {code:'', text:'선택'},
	INIT_SUBMITFILE_SELECT : {code:'', text:'지원 사업을 선택해야 표출됩니다.'},
};
const RADIO = {
	INIT_ALL: {code:'', text:'전체'},
	USE_YN	: [{code:'Y',text:'필수'},{code:'N',text:'선택'}],    //제출서류관리 필수제출 여부
	ESNTL_YN: [{code:'Y',text:'표출'},{code:'N',text:'숨김'}]	    //제출서류관리 항목표출 여부
};

const STORE = {
	USE_STTS: [{code:'1',text:'사용'},{code:'0',text:'중지'}],
	USE_YN  : [{code:'Y', text:'사용'},{code:'N', text:'중지'}],
	ESNTL_YN: [{code:'Y', text:'필수'},{code:'N', text:'아님'}],
    DOWNTRGT_YN  : [{code:'Y', text:'대상'},{code:'N', text:'아님'}],   // 다운로드대상여부
	POP_YN  : [{code:'Y', text:'팝업'},{code:'N', text:'일반'}],
	PERM_YN : [{code:'Y', text:'허용'},{code:'N', text:'불가'}],
	YES_NO  : [{code:'Y', text:'예'},{code:'N', text:'아니오'}],
	PROG_TY : [{code:'url', text:'url'}],
	EML_AT  : [{code:'Y',text:'이메일 정보 수신에 동의합니다.'}],
	MBL_AT  : [{code:'Y',text:'휴대전화 정보 수신에 동의합니다.'}],
	SX_DST  : [{code:'M',text:'남자'},{code:'F',text:'여자'}],
	OX_DST  : [{code:'Y',text:'○'},{code:'N',text:'X'}],
	YN_DST  : [{code:'Y',text:'Y'},{code:'N',text:'N'}],
	SBMT_YN : [{code:'Y',text:'제출'},{code:'N',text:'미제출'}],
	NTC_YN  : [{code:'N',text:'사용하지 않음'},{code:'Y', text:'사용자 설정'}],
	// 2023.06.20 경영체지원현황 상태목록
	MYPG_STUS: [
		{code: '90', text:'적합'},
		{code: '20', text:'보류'}
	],
	// 2023.04.25 경영체 정렬기준
	SORT_ENT: [
		{code:'A',text:'가나다순'},
		{code:'B',text:'등록일자순'},
		{code:'C',text:'조회순'},
		{code:'D',text:'낮은 투자희망금액순'},
		{code:'E',text:'높은 투자희망금액순'}
	],
	// 2023.08.25 펀드 정렬기준
	SORT_FUND: [
		{code:'A',text:'펀드규모 오름차순'},
		{code:'B',text:'펀드규모 내림차순'},
		{code:'C',text:'조성년도 오름차순'},
		{code:'D',text:'조성년도 내림차순'},
		{code:'E',text:'투자분야 가나다순'}
	],
	// 2023.04.30 지원사업 정렬기준
	SORT_PBANC: [
		{code:'A',text:'등록순'},
		{code:'B',text:'마감순'},
	],
	// 2023.06.13 우수투자사례 정렬기준
	SORT_INVESTCASE: [
		{code:'A',text:'조회순'},
		{code:'B',text:'최신순'},
	],
	// 2023.07.13 경영체 데이터 업로드 정렬기준
	SORT_SPRTULD: [
		{code:'A',text:'최신순'},
		{code:'B',text:'오래된순'},
	],
	// 2023.04.27 모집상태
	FUND_STTS: [
		 {code: '10', text: '모집중'  , icon: 'icon-alarm-check', key: 'ING'}
		,{code: '20', text: '모집예정', icon: 'icon-alarm-minus', key: 'PRE'}
		,{code: '90', text: '모집종료', icon: 'icon-alarm-minus', key: 'END'}
	],
	// 모집여부 (코드없음)
	INVT_YN  : [    
		 {code: CODE.PRGRE_STUS1.ING, text: '진행중'}
		,{code: CODE.PRGRE_STUS1.END, text: '종료'  }
	],
	TEL_CC : [
		{code:'02' , text:'02'},
		{code:'031', text:'031'},
		{code:'032', text:'032'},
		{code:'033', text:'033'},
		{code:'041', text:'041'},
		{code:'042', text:'042'},
		{code:'043', text:'043'},
		{code:'044', text:'044'},
		{code:'051', text:'051'},
		{code:'052', text:'052'},
		{code:'053', text:'053'},
		{code:'054', text:'054'},
		{code:'055', text:'055'},
		{code:'061', text:'061'},
		{code:'062', text:'062'},
		{code:'063', text:'063'},
		{code:'064', text:'064'},
		{code:'070', text:'070'}
	],
	EML_CC : [
		{code:'naver.com'  , text:'naver.com'},		
		{code:'nate.com'   , text:'nate.com'},		
		{code:'daum.net'   , text:'daum.net'},		
		{code:'hotmail.com', text:'hotmail.com'},		
		{code:'gmail.com'  , text:'gmail.com'},		
		{code:'korea.kr'   , text:'korea.kr'},		
	],
	getName: function(group, code) {
		if (STORE[group] &&
			$.type(STORE[group]) == 'array') {
			let s = '';
			$.each(STORE[group], function(i,o) {
				if (o['code'] == code) {
					s = o['text'];
					return false;
				}
			});
			return s;
		}
		return '';
	},
	getItem: function(group, code) {
		let item = false;
		if (STORE[group] &&
			$.type(STORE[group]) == 'array') {
			$.each(STORE[group], function(i,o) {
				if (o['code'] == code) {
					item = o;
					return false;
				}
			});
		}
		return item;
	},
	getAge: function(min, max, sort, formatter) {

		min = min || 10;
		max = max || 100;
		sort = sort || 'asc';
		var rows  = [];
		if (sort == 'asc') {
			for (var i = min; i <= max; i++) {
				var row = {code:i, text:i};
				if (formatter)
					row['text'] = formatter(row['text']);
				rows.push(row);
			}
		}
		else {
			for (var i = max; i >= min; i--) {
				var row = {code:i, text:i};
				if (formatter)
					row['text'] = formatter(row['text']);
				rows.push(row);
			}
		}
		return rows;
	},
	getYears: function(diff, formatter, len) {
		var rows  = [];
		var year = (new Date()).getFullYear()+(diff || 0);
		len = len || 80;
		for (var i = 0; i < len; i++) {
			var row = {code:year, text:year};
			if (formatter)
				row['text'] = formatter(row['text']);
			rows.push(row);
			year--;
		}
		return rows;
	},
	getMonths: function(formatter) {
		var rows  = [];
		for (var i = 1; i <= 12; i++) {
			var value = (i < 10 ? '0'+i : i+'');
			var row = {code:value, text:value};
			if (formatter)
				row['text'] = formatter(row['text']);
			rows.push(row);
		}
		return rows;
	},
	getDays: function(formatter) {
		var rows  = [];
		for (var i = 1; i <= 31; i++) {
			var value = (i < 10 ? '0'+i : i+'');
			var row = {code:value, text:value};
			if (formatter)
				row['text'] = formatter(row['text']);
			rows.push(row);
		}
		return rows;
	},
	getHours: function(nameFormatter, valueFormatter) {
		var rows  = [];
		for (var i = 1; i <= 24; i++) {
			var value = (i < 10 ? '0'+i : i+'');
			var row = {code:value, text:value};
			if (valueFormatter)
				row['code'] = valueFormatter(row['code']);
			if (nameFormatter)
				row['text'] = nameFormatter(row['text']);
			rows.push(row);
		}
		return rows;
	},
	copyLink: function(id) {
		var value = $('#' + id).val();
		$.commUtil.copyText(window.location.href + '?' + id + '=' + value);
		$.commMsg.alert("주소가 복사되었습니다.");
	}
};

// 각종 설정 기본옵션
const OPTIONS = {
	DATEPICKER: {
         format: 'yyyy-mm-dd', //데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
         //startDate: '-10d', //달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
        // endDate: '+10d', //달력에서 선택 할 수 있는 가장 느린 날짜. 이후로 선택 불가 ( d : 일 m : 달 y : 년 w : 주)
         autoclose: true, //사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
         calendarWeeks: false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
         //clearBtn: false, //날짜 선택한 값 초기화 해주는 버튼 보여주는 옵션 기본값 false 보여주려면 true
         //datesDisabled: ['2019-06-24', '2019-06-26'], //선택 불가능한 일 설정 하는 배열 위에 있는 format 과 형식이 같아야함.
         //daysOfWeekDisabled: [0, 6], //선택 불가능한 요일 설정 0 : 일요일 ~ 6 : 토요일
         //daysOfWeekHighlighted: [3], //강조 되어야 하는 요일 설정
        // disableTouchKeyboard: false, //모바일에서 플러그인 작동 여부 기본값 false 가 작동 true가 작동 안함.
        // immediateUpdates: false, //사용자가 보는 화면으로 바로바로 날짜를 변경할지 여부 기본값 :false
        // multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false
        // multidateSeparator: ',', //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
         templates: {
            leftArrow: '<i class="icon-angle-left"></i>',
            rightArrow:'<i class="icon-angle-right"></i>',
         }, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징
         showWeekDays: true, // 위에 요일 보여주는 옵션 기본값 : true
         //title: '테스트', //캘린더 상단에 보여주는 타이틀
        // todayHighlight: true, //오늘 날짜에 하이라이팅 기능 기본값 :false
        // toggleActive: true, //이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
        // weekStart: 0, //달력 시작 요일 선택하는 것 기본값은 0인 일요일
         language: 'ko', //달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
	},
	DATEPICKER_YEAR: {
	    format: 'yyyy', //데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
	    autoclose: true, //사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
	    minViewMode: 'years',
	    endDate: 'today', // 오늘 날짜 이후를 선택할 수 없게 하는 옵션
	    templates: {
	       leftArrow: '<i class="icon-angle-left"></i>',
	       rightArrow:'<i class="icon-angle-right"></i>',
	    }, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징
	    language: 'ko', //달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
	},
	DATEPICKER_NOT_TOMORROW: {
	    format: 'yyyy-mm-dd', 
	    autoclose: true, 
	    endDate: 'today',
	    calendarWeeks: false,
	    templates: {
	       leftArrow: '<i class="icon-angle-left"></i>',
	       rightArrow:'<i class="icon-angle-right"></i>',
	    }, 
	    showWeekDays: true,
	    language: 'ko', 
	},
	DATEPICKER_MONTH: {
	    format: 'yyyy-mm', 
	    autoclose: true,
	    minViewMode: 'months',
	    templates: {
	       leftArrow: '<i class="icon-angle-left"></i>',
	       rightArrow:'<i class="icon-angle-right"></i>',
	    }, 
	    language: 'ko',
		changeMonth: true, 
    	changeYear: true,  
		showButtonPanel: true, 
	    onClose: function(dateText, inst) {
	    	var year = $(".datepicker-years .year.active").text();
	    	var month = $(".datepicker-months .month.active").index()+1;
	    	$(this).datepicker('setDate', new Date(year, month - 1, 1));
	    }
	}
};

// 검증관련 포맷 상수
const VALIDATE = {
	 date    : {name:'날짜', ex: '2023-01-01'}
	,url     : {name:'URL' , ex: 'https://www.domain.com'}
	,email   : {name:'이메일', ex: 'sample@domain.com'}
	,phone   : {name:'전화번호', ex: '02-1234-5678'}
	,mobile  : {name:'휴대전화번호', ex: '010-1234-5678'}
	,rno     : {name:'주민등록번호', ex: '000000-0000000'}
	,bzno    : {name:'사업자번호', ex: '000-00-00000'}
	,crno    : {name:'법인등록번호', ex: '000000-0000000'}
	,yyyymm  : {name:'년월', ex: '202301'}
	,yyyymmdd: {name:'날짜', ex: '20230101'}
	,birthNo : {name:'생년월일', ex: '20230101'}
	,phoneNo : {name:'전화번호', ex: '02-1234-5678'}
	,mobileNo: {name:'휴대전화번호', ex: '010-1234-5678'}
	,rnoNo   : {name:'주민등록번호', ex: '000000-0000000'}
	,bznoNo  : {name:'사업자번호', ex: '000-00-00000'}
	,crnoNo  : {name:'법인등록번호', ex: '000000-0000000'}
	,pswd    : {name:'비밀번호', ex: '영대소문자+숫자+특수문자'}
};

// 필터함수
const FILTER = {
	// 지원신청인지 확인하는 함수
	isSupportCode: function( code ) {
		return ($.inArray(code, [
			CODE.SPRT_APLY_SE.BEFORE,
			CODE.SPRT_APLY_SE.AFTER,
			CODE.SPRT_APLY_SE.CROWD
		]) >= 0);
	},
	// 투자전지원인지 확인하는 함수
	isBeforeCode: function( code ) {
		return (code == CODE.SPRT_APLY_SE.BEFORE);
	},
	// 투자후지원인지 확인하는 함수
	isAfterCode: function( code ) {
		return (code == CODE.SPRT_APLY_SE.AFTER);
	},
	// 크라우드펀딩인지 확인하는 함수
	isCrowdCode: function( code ) {
		return (code == CODE.SPRT_APLY_SE.CROWD);
	},
	// 상담신청인지 확인하는 함수
	isDscsnCode: function( code ) {
		return (code == CODE.PRGRM_NO.DISCUSS);
	},
	// 현장코칭인지 확인하는 함수
	isCoatingCode: function( code ) {
		return (code == CODE.PRGRM_NO.COATING);
	},
	// 수수료지원인지 확인하는 함수
	isFeeCode: function( code ) {
		return (code == CODE.PRGRM_NO.FEE);
	},
	// 컨설팅지원인지 확인하는 함수
	isConsultCode: function( code ) {
		return (code == CODE.PRGRM_NO.CONSULT);
	},
	// 지원신청종류 객체 반환
	getPrgrmType: function( group ) {
		let obj = CODE.PRGRM_TY;
		for (var t in obj) {
			if (obj[t] && obj[t].code == group) {
				return obj[t];
			}
		}
		return false;
	},
	// 지원신청종류 색상 반환
	getPrgrmColor: function( group ) {
		let obj = FILTER.getPrgrmType(group);
		if (obj)
			return obj['color'];
		return 'black';
	},
	// 지원구분별 서류그룹 반환
	getPapeGroup: function( code ) {
		if      (code == CODE.SPRT_APLY_SE.AFTER)
			return CODE.PAPE_GROUP.AFTER;
		else if (code == CODE.SPRT_APLY_SE.CROWD)
			return CODE.PAPE_GROUP.CROWD;
		else if (code == CODE.SPRT_APLY_SE.BEFORE)
			return CODE.PAPE_GROUP.BEFORE;
		else
			return false;
	},
	// 서류신청타입 반환
	getPapeType: function( bizYn ) {
		if (bizYn == 'Y')
			return CODE.PAPE_TYPE.ENT;
		return CODE.PAPE_TYPE.USR;
	},
	// 서류신청타입 반환
	getPapeType: function( bizYn ) {
		if (bizYn == 'Y')
			return CODE.PAPE_TYPE.ENT;
		return CODE.PAPE_TYPE.USR;
	},
	// 처리상태 객체반환
	getStatus: function( code ) {
		let ret = false;
		$.each(CODES.STATUS_LIST, function(i,o) {
			if (code == o['code']) {
				ret = o;
				return false;
			}
		});
		return ret;
	},
	// 가입단계 번호반환
	getJoinStepNo: function( code, joinCd ) {
		let ret  = 0;
		let step = 1;
		$.each(CODES.JOIN_STEP_LIST, function(i,o) {
			if (o.filter && !o.filter(joinCd))
				return true; 
			if (code == o['code']) {
				ret = step;
				return false;
			}
			step++;
		});
		return ret;
	},
	// 기업유형필터
	filterBzentyType: function( o ) {
		if (o['code'] == CODE.BZENTY_TYPE.INDV) {
			return SCREEN.ROLE.USR;
		}
		else {
			return SCREEN.ROLE.BIZ;
		}
	},
	// 2023.09.11 생년월일 정제필터
	filterBrdt: function( v ) {
		let s = $.commUtil.nvlTrim(v);
		if (s.length < 6)
			return '';
		if (s.length == 8)
			return s;
		
		s = s.substring(0,6);
		//20년이하면 20
		if (parseInt(s.substring(0,2)) < 20)
			s = '20'+s;
		else
			s = '19'+s;
		return s;
	}
};

