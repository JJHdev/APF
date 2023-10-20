/**
*******************************************************************************
***    명칭: openBbs.js
***    설명: 마이페이지 - 문의내역 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
***	   1.1		2023.06.19	  KYW	     CRUD
*******************************************************************************
**/

$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'     ); 				 	// 목록 GRID
	let P_URL 	  = getUrl('/usr/mypage/bbs/openBbs.do');
	let P_FORM    = $('#searchForm'  ); 					// 검색폼	
	let P_BBS_CD  = $('#bbsSeCd'  ).val();					// 게시판 구분코드
	let P_PST_NO  = 0; 										//게시판 번호
	let P_CONTENT = 'p_pstCn';           					// 웹에디터 칼럼명
	let STATE     = null;									// 상태(입력, 수정)
	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '문의내역',
		// 검색 URL
		url:         getUrl('/usr/mypage/bbs/getListBbs.do'),
		//목록 검색 페이징 객체
		pagination: { display: 10},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 리스트옵션
		listOptions: {
			// 목록 없을경우 메세지
			emptyText:  '{title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','*','160px','250px'],
			// 목록칼럼 정의
			columns: [
	            {name:'inqryClNm'    ,cls:'app-c',label:'문의유형'},
	            {name:'pstTtl'       ,cls:'app-l',label:'제목'},
	            {name:'regDate'      ,cls:'app-c',label:'작성일'},
	            {name:'prcsSttsCd'   ,cls:'app-c',label:'상태', 
	            	formatter: function(v,o) {
	            		let pstNo = o['pstNo'];

	            		let col = $('<div class="stateBtn"></div>');
	        			if (v == '10') { 
	        				col.appButtonGroupBox({
	        					items: [
        					        	{value:'', icon: 'icon-comment-edit', label: '처리중', cls: 'col btn-red-ouline-hover border-0 bs-s w-100 mx-4px'},
        					        	{value:pstNo, icon: 'icon-rotate-right', label: '수정', cls: 'btnModify col btn-primary bs-s w-100 mx-4px'},
        					        	{value:pstNo, icon: 'icon-trash', label: '삭제', cls: 'btnDelete col btn-black bs-s w-100 mx-4px'}
	        					],
	        					//선택이벤트
								select: function(v) {
									P_PST_NO = v;
								},
								//초기값
								value: false
	        				})
	        			} else { //답변완료상태 20
	        				col.appButtonGroupBox({
	        					items: [
        					        	{value:'', icon: 'icon-comment-text-check', label: '답변완료', cls: 'col btn-deep-green-ouline-hover border-0 bs-s w-100 mx-4px'}
	        					],
	        					//선택이벤트
								select: false,
								//초기값
								value: false
	        				})
	        			}
	        			return col;
            		}
	            },
			],
			// 목록형인 경우 내용 스타일시트
			bodyCls: "bs-1 t-t-c ts-m myGrid",
            //행단위 행확장이벤트
			extendRow    : function(row, rowidx) {
				let str = replaceStr(row['pstCn']);
				let tr = $('<tr class="grid-answer-row"></tr>');
				tr.addClass('px-v-xl py-v-xl t-t-c');
				tr.append('<td class="app-l" colspan="4"><div class="answer"></div></td>');
				tr.find('.answer').append('<div class="top" style="padding-left: 205px;"></div>');
				tr.find('.answer > .top').append('<pre>'+str+'</pre>');
		    	
		    	if(row['prcsSttsCd'] == '20') {
		    		$.ajaxUtil.ajaxLoad(
						// 조회 URL
			            getUrl('/usr/mypage/bbs/viewBbs.do'),
						// 조회 조건
						{	pstNo     : row['pstNo'  ], //게시글 번호
						},
						// 조회 콜백
			            function(result) {
							let dataAdm = result[1];
							let answer = replaceStr(dataAdm.pstCn);
							
							let span1 = $('<span class="fw-600"></span>');
							span1.append(dataAdm.rgtrNm);
							let span2 = $('<span><i class="ms-2 icon-alarm-clock "></i></span>');
							span2.append(dataAdm.regDate);
							let span3 = $('<span class="fw-600 text-primary"><i class="ms-2 icon-comment-text-check text-primary"></i></span>');
							span3.append('답변완료');
							tr.find('.answer').append('<div class="border my-4"></div><div class="bottom" style="padding-left: 205px;"></div>');
							let p = $('<p class="my-1"></p>');
							
					    	tr.find('.bottom').append(p.append(span1, span2, span3), answer);
						}
		    		);
				
				}
		    	
		    	tr.hide();
				return tr;
			},
            //행확장 토글이벤트 사용여부
            extendToggle: true,
	        // 행선택시 상세조회
	        select: doSelect
		}
    }).appBbsGrid('init');
    
    
    //========================================================//
    // 구분 탭 생성
    //--------------------------------------------------------//
	$('#appMenuTab').appBbsTabs({
		items: [{
			code: getUrl('/usr/mypage/meeting/openMeeting.do'), 
			text: '투자자 미팅 요청 내역'
		},{
			code: P_URL,
			text: '문의 내역'
		}],
		value:  P_URL,
		params: {bbsSeCd: P_BBS_CD},
		select: function(v) {
			if (v != P_URL)
				goUrl(v);
			doSearch();
		}
	});	
    
    // 문의내역 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search', obj);
		//버튼 css
		$('.stateBtn').removeClass('capsule-box');
        return false;
    }
    
    // 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row, rowidx) {
    	return false;
    }
    
    // 삭제하기
    //--------------------------------------------------------//
    function doRemove(P_PST_NO) {
        $.commMsg.confirm("정말 삭제하시겠습니까?", function() {
            // AJAX로 삭제처리
            $.ajaxUtil.ajaxLoad(
                getUrl('/usr/mypage/bbs/saveQNA.do'), 
                {pstNo   : P_PST_NO,
				 bbsSeCd : P_BBS_CD,
                 mode    : MODE.REMOVE
                }, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록으로 이동
                        $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
							// 목록 초기화
                        	doSearch();
						});
                    });
                }
            );
        });
        return false;
    }
    
    // 수정하기
    //--------------------------------------------------------//
    function doModify(P_PST_NO) {
    	STATE = 'UPDATE';
		$('<div></div>').appPopupBbs({
			bbsSeCd: P_BBS_CD,
			pstNo: P_PST_NO
		}, doSearch);
    }
    
    // 자주묻는질문버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnQnA').bind('click', function() {
    	goUrl(getUrl('/usr/inform/bbs/listFAQ.do'));
	});
    
    // 1:1문의하기 버튼 클릭시 이벤트처리 (팝업)
    //--------------------------------------------------------//
    $('#btnRegist').bind('click', function() {
    	STATE = 'INSERT';
		$('<div></div>').appPopupBbs({
			bbsSeCd: P_BBS_CD
		}, doSearch);
	});
    
    // 수정 버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $(document).on('click', '.btnModify', function() {
    	doModify(P_PST_NO);
	});
    
    // 삭제 버튼 클릭시 이벤트처리 
    //--------------------------------------------------------//
    $(document).on('click', '.btnDelete', function() {
    	doRemove(P_PST_NO);
	});
	
	//[팝업] 글작성 팝업
	//=============================================================================
    $.fn.appPopupBbs = function( params, saveCallback ) {
    	let options = {
    			title     		: '1:1 문의 작성하기',
    			icon      		: '<img src="'+getUrl('/images/sub/Sketch_on_smartphone.svg')+'">',
    			titleButton		: {
    				btnCls:  'goFaq btn-primary-ouline-hover border-0 bs-m bs-md-l', 
    				icon: 'icon-arrow-right', 
    				text: '자주 묻는 질문', 
    				click: function() {
    					goUrl(getUrl('/usr/inform/bbs/listFAQ.do'));
    				}
    			},
    			loadUrl   		: getUrl('/usr/mypage/bbs/modalBbs.do'),
    			loadParams		: {pstNo : params['pstNo']},
    	};
    	
    	return $(this).appPopup({
    		url:        	options.loadUrl,
    		params:     	JSON.stringify(options.loadParams),
    		title:      	options.title,
    		icon:       	options.icon,
    		titleButton:	options.titleButton,
    		type:      	 	'pageload',
    		dialogCls:  	'w-ver1',
    		appendBody: true,
    		onloaded:   function(pop) {
    			// 자주묻는질문 버튼 css 수정
    			$('.goFaq').removeClass('w-100');
    			// 입력폼
    			let pform = $('#p_registForm');
    			// 파일객체
    			let pfile = $('#p_appBbsFile');
    			
    			// 문의유형 정의
    			$('#appTopic').appDropdownBox({
    				title   : '문의유형<em></em>',
    				iconCls : 'icon-edit',
    				input   : {id: 'p_pstClsfCd', name: 'pstClsfCd', value: ''},
    				params  : {upCdId: CODE.INQRY_CL.code},
    				callback: function(){
    					var dropdownId = "appTopic";
    					var selectedValue = $('#p_pstClsfCd').val();
    					var $dropdown = $('#' + dropdownId);
    					
    					$dropdown.find('.dropdown-item').each(function() {
    						var $item = $(this);
    						if ($item.attr('data-value') === selectedValue) {
    							$item.addClass('active');
    							$dropdown.find('.dropdown-toggle').text($item.text());
    						} else {
    							$item.removeClass('active');
    						}
    					});
    				}
    			});
    			
    			// 첨부파일영역 정의 (app_bbsfile.js 참고)
    			pfile.appBbsFile({
    				cls: 'border-0 mb-0 pb-0 fs-10px',
    				// 처리모드
    				mode: MODE.UPDATE,
    				// 초기 표시 갯수
    				initCount: 1,
    				// 추가 최대 갯수
    				maxCount:  3,
    				// 다중 가능 여부				
    				multiple: true,
    				// 하단 메세지
    				message: '※ 100MB 이내 3개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장',
    				// 파일 조건 데이터
    				initData: {
    					fileType: CODE.FILE_TYPE.BBS,
    					docuCd:   params['bbsSeCd'],
    					docuNo:   params['pstNo'] // 수정인 경우 pstNo 설정해야함
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
				
				$(function() {
					CKEDITOR_INIT(P_CONTENT);
				});
    			
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
    				},
    				// 검증메세지 정의
    				messages: {
    					pstTtl         : '제목은 필수 입력 사항입니다.',
    					pstClsfCd      : '분류는 필수 선택 사항입니다.',
    					pstCn          : '내용은 필수 입력 사항입니다.',
    				},
    				// 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
    				invalidHandler: validateHandler,
    				// 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
    				errorPlacement: validatePlacement
    			});
    			
    			// 취소 클릭 이벤트 처리
    			$('#p_btnCancel').bind('click', function() {
    				pop.close();
    				return false;
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
    				
    				// 등록, 수정 모드 구분
    				let msg = null;
    				if(STATE == 'INSERT') {
    					msg = '문의를 등록하시겠습니까?';
    					// 등록모드 설정
    					pform.find('[name="mode"]').val(MODE.INSERT);
    				} else if (STATE == 'UPDATE') {
						msg = '문의를 수정하시겠습니까?';
						// 등록모드 설정
						pform.find('[name="mode"]').val(MODE.UPDATE);
    				}
    				
    				$.commMsg.confirm(msg, function() {
						// 등록폼을 AJAX로 저장처리
						pform.ajaxForm({
							url: getUrl('/usr/mypage/bbs/saveQNA.do'),
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
    			
    		}
    	}).open();
    };
    
    
	//문의 내용 태그 값 replace 함수
	//=============================================================================
    function replaceStr(str) {
    	str = str.replaceAll("&lt;", "<");
    	str = str.replaceAll("&gt;", ">");
    	str = str.replaceAll("&quot;", "'");
    	str = str.replaceAll(/(?:\r\n|\r|\n)/g, '<br />');
    	return str;
    };
    
    doSearch();
});

