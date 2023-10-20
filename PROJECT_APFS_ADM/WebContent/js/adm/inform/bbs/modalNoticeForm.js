/**
*******************************************************************************
***    명칭: openNotice.jsp

***    설명: 운영관리-공지사항 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
***    1.1      2023.06.27    J H        작업 완료.
*******************************************************************************
**/
$(function() {
	
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE = CODE.FILE_TYPE.BBS; // 첨부파일 종류
	let P_FILE_BOX  = $('#attachFile' );  // 첨부파일 컨트롤 객체
    let P_RFORM     = $('#registForm' );  // 등록폼 객체
    let P_MODE      = $('#mode'   ).val();// 처리 모드
    let P_PST_NO    = $('#pstNo'  ).val();// 게시글 키정보
    let P_BBS_CD    = $('#bbsSeCd').val();// 게시판 구분코드
	let P_CONTENT   = 'pstCn';            // 웹에디터 칼럼명

    //========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
	P_FILE_BOX.appBbsFile({
		mode: P_MODE,    // 처리모드
		initCount: 1,    // 초기 표시 갯수
		maxCount:  3,    // 추가 최대 갯수
		multiple: true,  // 다중 가능 여부
		maxLengthName:150,
		message: '※ 500MB 이내 3개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장',  // 하단 메세지
		initData: { // 파일 조건 데이터
			fileSe	: CODE.FILE_SE.FILE,
			fileType: P_FILE_TYPE,
			docuCd	: BBS_TASK_CD.CODE,
			docuNo	: P_PST_NO
		}  
	}).appBbsFile('init');
	//========================================================//
	// 웹에디터 정의 (ckeditor)
	//--------------------------------------------------------//
	// CKEDITOR 초기화 (comm_ckeditor.js 참고)
	// 개인적인 설정
	const BBS_CKEDITOR_CONFIG = {
	    extraPlugins: 'wordcount',
	    wordcount: {
	        showParagraphs: true,
	        showWordCount: true,
	        showCharCount: true,
	        countSpacesAsChars: true,
	        countHTML: false,
	        maxWordCount: -1,
	        maxCharCount: 20000,
	    }
	};

	// 공통 설정과 개인적인 설정을 병합
	const FINAL_CONFIG = Object.assign({}, CKEDITOR_CONFIG, BBS_CKEDITOR_CONFIG);
	
	// CKEDITOR 초기화 함수
	const CKEDITOR_INIT = function( id ) {
		if (CKEDITOR) {
		    // CKEDITOR 설정바인딩
			CKEDITOR.replace(id, FINAL_CONFIG);
			
		    // 2022.10.26 ntarget 추가 (이미지업로드 탭 정의)
		    CKEDITOR.on('dialogDefinition', function (ev) {
		        var dialogName = ev.data.name;
		        var dialog = ev.data.definition.dialog;
		        var dialogDefinition = ev.data.definition;
		
		        if (dialogName == 'image') {
		            dialog.on('show', function (obj) {
		                this.selectPage('Upload'); //업로드탭으로 시작
		            });
		            dialogDefinition.removeContents('advanced'); // 자세히탭 제거
		            dialogDefinition.removeContents('Link'); // 링크탭 제거
		        }
		    });
		}
	};

	CKEDITOR_INIT(P_CONTENT);
	
	//팝업창 가로세로 크기시 이미지 크기 조절
	$(document).ready(function(){
	    // popupAr와 popupHg 입력 필드에서 keyup 이벤트를 처리.
	    $("input[name='popupAr'], input[name='popupHg']").on('keyup', function() {
	        var popupAr = $("input[name='popupAr']").val();
	        var popupHg = $("input[name='popupHg']").val();

	        // 가로 또는 세로가 최대 크기를 초과하면 경고 메시지를 표시.
	        if (popupAr > 1920 ) {
	        	$.commMsg.alert("가로 최대 크기를 초과하였습니다. 가로는 1920px을 넘지 않아야 합니다.");
		        $("input[name='popupAr']").val('');
	            return;
	        }
	        
	        if(popupHg > 1080){
	        	$.commMsg.alert("세로 최대 크기를 초과하였습니다. 세로는 1080px을 넘지 않아야 합니다.");
	        	$("input[name='popupHg']").val('');
	        	return;
	        }
	    });
	});
	
    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 분류 라디오박스
	$('#appPstClsfCd').appSelectBox({
		form		: 'radio', 
		name		: 'pstClsfCd',
		colCls		: '',
		wrapCls		: "check-radio-box",
		params		: {upCdId: CODE.NTT_CL.code},
		filter		: function(d) {
							if (d['code'].indexOf(P_BBS_CD) == 0)
								return true;
							return false;
						}
	});
	// 상단고정여부
	$('#appFixingYn').appSwitchBox({id: 'fixingYn', name: 'fixingYn', value: 'Y'});
	// 팝업여부
	$('#appPopupYn').appSwitchBox({id: 'popupYn', name: 'popupYn', value: 'Y'});
    
    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
	
	// 새로운 검증 메서드 추가
	jQuery.validator.addMethod("maxNumeric", function(value, element, param) {
	    return this.optional(element) || ((parseFloat(value) <= parseFloat(param)) && (!isNaN(value)));
	}, jQuery.validator.format("최대 {0}까지 입력 가능합니다."));
	
	jQuery.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) >= new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val()) 
            || (Number(value) >= Number($(params).val())); 
    },'종료 날짜는 시작 날짜보다 이후이어야 합니다.');
	
    P_RFORM.validate({
        // true일 경우 디버깅이 가능하도록 
        // 입력값이 유효해서 submit하지 않는다.
        debug: false,
        // true일 경우 포커스가 떠날때 유효성 검사를 한다.
        onfocusout: false,
        // true일 경우 유효성체크없이 무조건 submit한다.
        onsubmit: false,
        // 검증룰 정의
        rules: {
            pstTtl     		: {required			: true	,
                			   maxlength		: 250  	},
            pstClsfCd  		: {required			: true	},
            pstCn      		: {required			: true	},
            pstgBgngYmd		: {required			: function() {
								    					if ($('#popupYn').is(':checked'))
								    						return true;
								    					return false;
								    			 },
							   date				: true	
			},
			pstgEndYmd		: {required			: function() {
							    					// 상단고정 체크시에만 유효성 검사 진행
							    					if ($('#popupYn').is(':checked'))
							    						return true;
							    					return false;
							    				},
				    		   date				: true,
		    				   greaterThan		: '#pstgBgngYmd'
			},
		    popupAr			: {maxNumeric		: 1920  ,
		    				   required			: function() {
													// 상단고정 체크시에만 유효성 검사 진행
													if ($('#popupYn').is(':checked'))
														return true;
													return false;
												}
		    },
		    popupHg			: {maxNumeric		: 1080  ,
							   required			: function() {
													// 상단고정 체크시에만 유효성 검사 진행
													if ($('#popupYn').is(':checked'))
														return true;
													return false;
												}
			},
			fixingBgngYmd	: {required			: function() {
							    					// 상단고정 체크시에만 유효성 검사 진행
							    					if ($('#fixingYn').is(':checked'))
							    						return true;
							    					return false;
							    				},
		    				   date				: true,
			},
			fixingEndYmd	: {required			: function() {
							    					// 상단고정 체크시에만 유효성 검사 진행
							    					if ($('#fixingYn').is(':checked'))
							    						return true;
							    					return false;
							    				},
			    				date			: true,
			    				greaterThan		: '#fixingBgngYmd',
			},
			popupYn			: {required			: function() {
								    					// 게시기간 기입 시 상단고정 Y체크 필요
								    					if ($('#popupHg').val() || $('#popupAr').val() || $('#pstgBgngYmd').val() || $('#pstgEndYmd').val())
								    						return true;
								    					return false;
							    				 }
			},
			fixingYn		: {required			: function() {
								    					 // 게시기간 기입 시 상단고정 Y체크 필요
								    		            if ($('#fixingBgngYmd').val() || $('#fixingEndYmd').val())
								    		                return true;
								    		            return false;
								    			  }
			}
        },
        // 검증메세지 정의
        messages: {
            pstTtl         : {required 		: '제목은 필수 입력 사항입니다.',
                    	      maxlength		: jQuery.validator.format('제목은 최대 {0}자 이하 입니다.')},
            pstClsfCd      : {required		: '분류는 필수 선택 사항입니다.'},
            pstCn          : {required 		: '내용은 필수 입력 사항입니다.'},
            pstgBgngYmd    : {required 		: '팝업여부 체크시 게시기간은 필수 입력 사항입니다.',
					  	      date     		: '날짜 형식에 맞게 입력해주세요'},
            pstgEndYmd     : {required 		: '팝업여부 체크시 게시기간은 필수 입력 사항입니다.',
            				  date     		: '날짜 형식에 맞게 입력해주세요'},
            popupAr        : {required 		: '팝업여부 체크시 팝업창 가로는 필수 입력 사항입니다.',
            				  maxNumeric	: jQuery.validator.format('팝업창의 가로 크기는 최대 {0}px까지 입력 가능합니다.')},
    		popupHg        : {required 		: '팝업여부 체크시 팝업창 세로는  필수 입력 사항입니다.',
    						  maxNumeric	: jQuery.validator.format('팝업창의 세로 크기는 최대 {0}px까지 입력 가능합니다.')},
    		fixingBgngYmd  : {required 		: '상단고정 체크시 게시기간은 필수 입력 사항입니다.',
					  		  date     		: '날짜 형식에 맞게 입력해주세요'},
    		fixingEndYmd   : {required 		: '상단고정 체크시 게시기간은 필수 입력 사항입니다.',
					  		  date     		: '날짜 형식에 맞게 입력해주세요'},
            popupYn        : {required 		: '게시기간및 팝업창 크기입력시 팝업여부는 필수 입력 사항입니다.'},
            fixingYn       : {required 		: '게시기간을 기입시 상단고정여부는 필수 입력 사항입니다.'}
        },
        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
        invalidHandler: validateHandler,
        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
        errorPlacement: validatePlacement
    });
	// 숫자만입력
	bindOnlyNumber($("#popupAr"));
	bindOnlyNumber($("#popupHg"));

    // 저장버튼 클릭시 이벤트 처리
    //--------------------------------------------------------//
    $('#btnSave').bind('click', function() {
    	
    	 // CKEDITOR 내용 업데이트
        CKEDITOR.instances[P_CONTENT].updateElement();

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

		// 첨부파일 업로드 VALIDATION (app_bbsfile.js)
		if (P_FILE_BOX.appBbsFile('validate', {
				isAlert: true,
				isExt:   true,
				isLimit: true
			}) === false)
            return false;

		let msg = "공지사항을 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "공지사항을 등록하시겠습니까?";
	
    	$.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/inform/bbs/saveBbs.do'),
                enctype : 'multipart/form-data',
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
                	$.ajaxUtil.success(ret, function(data) {
                        // 목록으로 이동
                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
							// 부모창의 목록재검색 및 팝업닫기
							P_MODAL.doSearch();
							return false;
						});
                	});
                }
            }).submit();
    	});
        return false;
	});
    
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    function doCancel() {
    	// 부모창의 doClose 호출
    	P_MODAL.doClose();
    	return false;
    }
    
    // 이전버튼 클릭시 이벤트처리
    $('#btnCancel ').bind('click', doCancel);
});
