/**
*******************************************************************************
***    명칭: listData.js
***    설명: 정보서비스 - 자료실 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    	= $('#appGrid'     		); // 목록 GRID
	let P_FORM    	= $('#searchForm'  		); // 검색폼	
    let P_BBS_CD  	= $('#bbsSeCd'  ).val();// 게시판 구분코드
    let P_CLSF_CD 	= $('#pstClsfCd').val();// 게시판 분류코드
	let P_SRCHTXT 	= $('#srchText' ).val();// 검색텍스트 (이전에서 넘어온항목)
	let P_SRCH_TYPE = $('#srchType' ).val();// 검색텍스트 (이전에서 넘어온항목)
	let P_CONTENT   = 'p_pstCn';            // 웹에디터 칼럼명
    let P_PST_NO    = $('#pstNo'  	).val();// 게시글 키정보
    let P_PAGE	    = $('#page').val();		// 게시판 페이지
	// 게시판분류코드 기본값 처리
	if ($.commUtil.empty(P_CLSF_CD)) {
		P_CLSF_CD = CODE.NTT_CL.BBS00;
	}
	
    //========================================================//
    // 목록 GRID 정의 (app_bbsgrid.js)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '자료실',
		// 검색 URL
		url:         getUrl('/usr/inform/bbs/getListData.do'),
		//목록 검색 페이징 객체
		pagination: { display: 10},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 리스트옵션
		listOptions: {
			emptyText: emptyText(),
			// 목록칼럼 너비
			colgroup: ['78px','180px','520px','130px','130px','70px'],
			// 목록칼럼 정의
			columns: [
	            {rownumbers: true		, cls:'app-c',label:'번호', formatter: function(v,o) {
																		// 공지인 경우
																		let currentDate = new Date();
																		currentDate.setHours(0, 0, 0, 0);
																		let beginDate   = new Date(o['fixingBgngYmd']);
																		beginDate.setHours(0, 0, 0, 0);
																		let endDate     = new Date(o['fixingEndYmd']);
																		endDate.setHours(23, 59, 59, 999);
																		if (o['fixingYn'] == 'Y' && currentDate >= beginDate && currentDate <= endDate)
																			return '<i class="icon-megaphone fs-18px text-primary"></i>';
																		return v;
																	}},
				{name:'pstClsfNm'      ,cls:'app-c',label:'분류'	},
	            {name:'pstTtl'         ,cls:'app-l',label:'제목'	},
	            {name:'rgtrNm'         ,cls:'app-c',label:'작성자'},
	            {name:'regDate'        ,cls:'app-c',label:'작성일'},
	            {name:'inqCnt'         ,cls:'app-r',label:'조회수'}
			],
	        // 행선택시 상세조회
	        select: doSelect
		}
    }).appBbsGrid('init');
	
    //========================================================//
    // 분류 탭 생성
    //--------------------------------------------------------//
	$('#appDataTab').appBbsTabs({
		url:    getUrl('/usr/inform/bbs/getListBbsTab.do'),
		params: {bbsSeCd: P_BBS_CD},
		value:  $('#srchType' ).val() ? $('#srchType' ).val() : P_CLSF_CD,
		select: function(v) {
			$('#pstClsfCd').val(v);
			$('#srchType' ).val(v)
			doSearch();
		}
	});

    // 게시판 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        
		// 이전 페이지번호
		var page = obj['page'];
        // 검색폼 그리드 검색
		if (page && parseInt(page) > 1) {
			// 페이지번호 기준 검색 (초기 로딩시)
			P_GRID.appBbsGrid('search', obj, page);
			// 페이지번호 초기화
			$('#page').val('');
		}
		else
			P_GRID.appBbsGrid('search', obj);
        return false;
    }

    // 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row) {
		let pg = P_GRID.appBbsGrid('getPagination');
        $.formUtil.submitForm( getUrl('/usr/inform/bbs/viewData.do'), {
			params: {
				pstNo     : row['pstNo'  ],
				bbsSeCd   : row['bbsSeCd'],
				pstClsfCd : $('#pstClsfCd').val(),
				srchText  : $('#srchText' ).val(),
				srchType  : $('#srchType' ).val(),
				page      : pg['page'    ]
			}
		});
        return false;
    }
	// 상단 검색박스 표시 (app_bizutils.js 참고)
	$.bizUtils.showHeaderBox({
		type : 'searchbox',
		id   : 'topSrchText',
		name : 'topSrchText',
		value: P_SRCHTXT,
		placeholder: '검색어를 입력하세요.',
		callback: function(v) {
			$('#srchText').val(v);
			doSearch();
		}
	});

	$('#btnRegist').bind('click', function() {
		$('<div></div>').appPopupData({
			bbsSeCd: P_BBS_CD
		}, doSearch);
	});
	
	// 메인페이지이동 23.08.31 김예원
	if (initParams) {
		$('#appDataTab').find('li a').removeClass('active');
		$('#appDataTab').find('li').eq(2).find('a').addClass('active');
		$('#pstClsfCd').val(initParams.pstClsfCd);
		$('#srchType' ).val(initParams.pstClsfCd);
		doSearch();
	} else {
		doSearch();
	}

});


// [팝업] 글작성 팝업
//=============================================================================
$.fn.appPopupData = function( params, saveCallback ) {
	let P_CONTENT   = 'p_pstCn';            // 웹에디터 칼럼명
	if (!params) {
		$.commMsg.alert('조회조건을 확인할 수 없습니다.');
		return false;
	}

	let options = {
		title     : '자료실 글 작성하기',
		icon      : '<img src="'+getUrl('/images/sub/Sketch_on_smartphone.svg')+'">',
		loadUrl   : getUrl('/usr/inform/bbs/modalDataForm.do'),
		loadParams: {},
	};

	return $(this).appPopup({
		url:        options.loadUrl,
		params:     JSON.stringify(options.loadParams),
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		onloaded:   function(pop) {
			// 입력폼
			let pform = $('#p_registForm');
			// 파일객체
			let pfile = $('#p_appBbsFile');
			
			// 분류박스 정의
			$('#appTopic').appDropdownBox({
				title   : '토픽<em></em>',
				iconCls : 'icon-edit',
				input   : {id: 'p_pstClsfCd', name: 'pstClsfCd', value: ''},
				params  : {upCdId: CODE.NTT_CL.code, cdIdStart: params['bbsSeCd']}
			});

			// 공지글표시 정의
			$('#appNoticeYn').appDropdownBox({
				title   : '공지글 표시<em></em>',
				type    : 'static',
				iconCls : 'icon-edit',
				input   : {id: 'p_fixingYn', name: 'fixingYn', value: 'N'},
				value   : 'N',
				rows    : STORE.NTC_YN,
				select  : function(v) {
					if (v == 'Y') {
						$('#p_fixingBgngYmd').prop('disabled', false);
						$('#p_fixingEndYmd' ).prop('disabled', false);
					}
					else {
						$('#p_fixingBgngYmd').prop('disabled', true);
						$('#p_fixingEndYmd' ).prop('disabled', true);
					}
				}
			});
			// 달력설정 (comm_const.js 참고)
			$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);
			
			// 첨부파일영역 정의 (app_bbsfile.js 참고)
			pfile.appBbsFile({
				cls: 'border-0 mb-0 pb-0',
				// 처리모드
				mode: MODE.INSERT,
				// 초기 표시 갯수
				initCount: 1,
				// 추가 최대 갯수
				maxCount:  3,
				// 다중 가능 여부				
				multiple: true,
				// 하단 메세지
				message: '※ 500MB 이내 3개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장',
				// 파일 조건 데이터
				initData: {
					fileSe	: CODE.FILE_SE.FILE,
		            fileType: CODE.FILE_TYPE.BBS,
					docuCd	: BBS_TASK_CD.CODE,
		            docuNo	: params['pstNo'] // 수정인 경우 pstNo 설정해야함
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

			$(function() {
				CKEDITOR_INIT(P_CONTENT);
			});
			
  			// 게시시작일 보다 종료일 초과 X
  			jQuery.validator.addMethod("greaterThan", function(value, element, params) {
  		        if (!/Invalid|NaN/.test(new Date(value))) {
  		            return new Date(value) >= new Date($(params).val());
  		        }
  		        return isNaN(value) && isNaN($(params).val()) 
  		            || (Number(value) >= Number($(params).val())); 
  		    },'종료 날짜는 시작 날짜보다 이후이어야 합니다.');
		    //========================================================//
		    // VALIDATION RULE 정의
		    //--------------------------------------------------------//
		    pform.validate({
		        // true일 경우 디버깅이 가능하도록 
		        // 입력값이 유효해서 submit하지 않는다.
		        debug: false,
		        // true일 경우 포커스가 떠날때 유효성 검사를 한다.
		        onfocusout: false,
		        // true일 경우 유효성체크없이 무조건 submit한다.
		        onsubmit: false,
		        // 검증룰 정의
		        rules: {
		                pstTtl     : 'required', // 제목 필수입력
		                pstClsfCd  : 'required', // 분류 필수선택
		                pstCn      : 'required', // 내용 필수입력
		    			fixingYn: {
		    				required: function() {
		    					 // 게시기간 기입 시 상단고정 Y체크 필요
		    		            if ($('#p_fixingBgngYmd').val() || $('#p_fixingEndYmd').val())
		    		                return true;
		    		            return false;
		    				}
		    			},
		    			fixingBgngYmd: {
		    				required: function() {
		    					// 상단고정 사용자 설정 시에만 유효성 검사 진행
		    					if ($('#p_fixingYn').val() === 'Y')
		    						return true;
		    					return false;
		    				}
		    			},
		    			fixingEndYmd: {
		    				required: function() {
		    					// 상단고정 체크시에만 유효성 검사 진행
		    					if ($('#p_fixingYn').val() === 'Y')
		    						return true;
		    					return false;
		    				},greaterThan: '#p_fixingBgngYmd',
		    			}
		        },
		        // 검증메세지 정의
		        messages: {
		                pstTtl         : '제목은 필수 입력 사항입니다.',
		                pstClsfCd      : '토픽은 필수 선택 사항입니다.',
		                pstCn          : '내용은 필수 입력 사항입니다.',
		                fixingYn       : '게시기간을 기입시 상단고정여부는 필수 입력 사항입니다.',
		    			fixingBgngYmd  : '공지글 표시 선택시 게시기간은 필수 입력 사항입니다.',
		    			fixingEndYmd   : {required : '공지글 표시 선택시 게시기간은 필수 입력 사항입니다.',
								   			greaterThan : '종료 날짜는 시작 날짜보다 이후이어야 합니다.' }
		        },
		        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
		        invalidHandler: validateHandler,
		        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
		        errorPlacement: validatePlacement
		    });
			
			// 작성완료버튼 클릭 이벤트 처리
			$('#p_btnSave').bind('click', function() {

		        // CKEDITOR 내용 업데이트
		        CKEDITOR.instances[P_CONTENT].updateElement();
		
		        // 등록폼의 VALIDATION 기능 활성화
		        if (pform.validate().settings)
		            pform.validate().settings.ignore = false;
		
		        //FORM VALIDATION
		        if (pform.valid() === false)
		            return false;
		
				// 첨부파일 업로드 VALIDATION (app_bbsfile.js)
				if (pfile.appBbsFile('validate', {
						isAlert: true,
						isExt:   true,
						isLimit: true
					}) === false)
		            return false;
			
		    	$.commMsg.confirm('자료를 등록하시겠습니까?', function() {
			
					// 등록모드 설정
					pform.find('[name="mode"]').val(MODE.INSERT);
					
		            // 등록폼을 AJAX로 저장처리
		            pform.ajaxForm({
		                url: getUrl('/usr/inform/bbs/saveData.do'),
		                enctype : 'multipart/form-data',
		                // 오류시 처리로직
		                error: $.ajaxUtil.error,
		                // 저장후 처리로직
		                success: function(ret) {
		                	$.ajaxUtil.success(ret, function() {
		                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
									// 저장후 콜백함수 실행
									saveCallback();
									// 팝업 닫기
									pop.close();
									return false;
								});
		                	});
		                }
		            }).submit();
		    	});
		        return false;
			});
			// 취소 클릭 이벤트 처리
			$('#p_btnCancel').bind('click', function() {
				pop.close();
				return false;
			});
		}
	}).open();
};

