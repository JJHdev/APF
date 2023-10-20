/**
*******************************************************************************
*** 파일명    : app_popup.js
*** 설명      : 업무용 공통팝업 자바스크립트
***             comm_popup.js 참고
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/
var popup = {
	// 사업자등록번호 조회팝업
	openBizNo : false,
	// 투자분야선택 팝업
	openInvestField: false,
	// 사업분야선택 팝업
	openBizField: false,
	// 펀드지원하기 팝업
	openApplyFund: false,
	// 미팅신청 팝업
	openMeeting: false,
	// 업종선택 팝업 [2023.07.04]
	openTpbizTree: false,
	// 업체선택 팝업 [2023.08.21]
	openEnt: false,
	// 투자단계 팝업 [2023.09.19]
	openInvestStep: false
};

// 사업자등록번호 조회팝업
//=========================================================================
popup.openBizNo = function ( args ) {

	// 항목선택시 콜백함수
	if (!args.callback) {
		$.commMsg.alert('사업자등록번호 선택 함수를 확인할 수 없습니다.');
		return false;
	}
	$('<div></div>').appPopup({
		url        : getUrl('/com/common/modalBizNo.do'),
		params     : JSON.stringify({}),
		title      : '사업자등록번호 조회',
		icon       : '<img src="'+getUrl('/images/sub/Search.svg')+'">',
		type       : 'pageload',
		style      : 'max-width:626px;',
		dialogCls  : 'position-nomal',
		headerBcls : 'border-black',
		bodyCls    : '',
		appendBody : true,
		onloaded   : function(pop) {
			
			let P_BIZFORM = $('#kodataForm');
			
			// 검증 룰 정의
			P_BIZFORM.validate({
				debug:      false,
				onfocusout: false,
				onsubmit:   false,
				rules: {
					brno1  : {required: true, digits: true, equalLength: 3},
					brno2  : {required: true, digits: true, equalLength: 2},
					brno3  : {required: true, digits: true, equalLength: 5}
				},
				messages: {
					brno1  : {
						required    : '사업자등록번호 첫번째 항목을 입력해 주세요.',
						digits      : '사업자등록번호 첫번째 항목을 숫자로만 입력해 주세요.',
						equalLength : $.validator.format('사업자등록번호 첫번째 항목을 {0}자리 숫자로 입력해 주세요.')
					},
					brno2  : {
						required    : '사업자등록번호 두번째 항목을 입력해 주세요.',
						digits      : '사업자등록번호 두번째 항목을 숫자로만 입력해 주세요.',
						equalLength : $.validator.format('사업자등록번호 두번째 항목을 {0}자리 숫자로 입력해 주세요.')
					},
					brno3  : {
						required    : '사업자등록번호 세번째 항목을 입력해 주세요.',
						digits      : '사업자등록번호 세번째 항목을 숫자로만 입력해 주세요.',
						equalLength : $.validator.format('사업자등록번호 세번째 항목을 {0}자리 숫자로 입력해 주세요.')
					}
				},
				invalidHandler: $.validateUtil.handler,
				errorPlacement: $.validateUtil.placement
			});
			bindOnlyNumber ($("#p_brno1"));
			bindOnlyNumber ($("#p_brno2"));
			bindOnlyNumber ($("#p_brno3"));
			bindFocusNext  ($("#p_brno1"), $("#p_brno2"));
			bindFocusNext  ($("#p_brno2"), $("#p_brno3"));
			bindEnter      ($("#p_brno3"), $("#p_btnBizSearch"));
			$("#p_brno1").focus();
			
			let _getComment = function( comment ) {
				return $('<div class="box py-24px text-center" style="border-top: 1px solid var(--Elements-Line);"></div>')
					.append('<p class="fs-22px fw-600 lh-1 mb-16px">'+comment+'</p>');
			};

			// 검색 클릭
			$('#p_btnBizSearch').bind('click', function() {
		        if (P_BIZFORM.validate().settings)
		            P_BIZFORM.validate().settings.ignore = false;
		        if (!P_BIZFORM.valid())
		            return false;

				$('#p_bizResult').html(_getComment([
					'<div class="d-flex fs-22px fw-600 align-items-center justify-content-center">',
					'사업자 정보를 조회중입니다...', 
					' <img src="'+getUrl('/images/sub/loading_bar.gif')+'"/>',
					'</div>'
				].join('')));

				//사업자등록번호 병합
				$.formUtil.mergeData('p_brno','bzno', 3);

				// 사업자정보 조회
				// 2023.08.16 LSH 호출방식을 Async로 변경함
				$.ajaxUtil.ajaxAsync(
		            getUrl('/com/common/getKodata.do'),
					{bzno: $('#p_brno').val(), mode: MODE.VIEW},
		            function(result) {
						if (result && result.Data) {
							let biz = result.Data;
							let dom = $('<div id="p_bizRow" class="box app-pointer"></div>');
							dom.append('<p class="txt1 app-name"></p>');
							dom.append('<div class="txt2box"></div>');
							dom.append('<span class="txt3 app-addr"></span>');
							dom.find('.txt2box').append('<div class="row"></div>');
							dom.find('.txt2box > .row').append('<div class="col"><p class="txt2">전화번호<em class="app-tel"></em></p></div>');
							dom.find('.txt2box > .row').append('<div class="col"><p class="txt2">대표자명<em class="app-rep"></em></p></div>');
							dom.find('.app-name').append(biz['enpNm']);
							dom.find('.app-addr').append([biz['locAddra'],biz['locAddrb']].join(' '));
							dom.find('.app-tel' ).append(biz['telNo']);
							dom.find('.app-rep' ).append(biz['reperName']);
							$('#p_bizResult').html('');
							$('#p_bizResult').append(dom);
							$('#p_bizRow').bind('click', function() {
								// 사업자등록번호 선택 콜백함수 호출
								args.callback(biz);
								// 팝업창 닫기
								pop.close();
								return false;
							});
						}
						else {
							// 신규등록 클릭
							let btn = $('<button type="button" class="bs-m btn-combi-3"><i class="icon-plus"></i>신규등록</button>');
							btn.click(function() {
								// 사업자등록번호 선택 콜백함수 호출
								args.callback({bzno: $('#p_brno').val(), mode: MODE.INSERT});
								// 팝업창 닫기
								pop.close();
								return false;
							});
							let dom = _getComment('검색결과가 없습니다.');
							dom.append(btn);
							
							$('#p_bizResult').html('');
							$('#p_bizResult').append(dom);
						}
		            }
		        );
				return false;
			});
		}
	}).open();
};

// 투자분야선택 팝업
//=========================================================================
popup.openInvestField = function ( args ) {
	
	let options = $.extend(true, {
		title     : '투자분야',
		name      : 'invtFldList',
		icon      : '<img src="'+getUrl('/images/sub/Bookmark.svg')+'">',
		loadUrl   : getUrl('/usr/invest/fund/modalInvestField.do'),
		loadParams: {},
		gridUrl   : getUrl('/com/common/getInvtRlmComboCode.do'),
		gridParams: {},
		// 초기화버튼 명칭
		btnResetName : '초기화',
		// 초기화버튼 콜백함수
		onClickReset : false,
		// 검색버튼 명칭
		btnSearchName: '검색',
		// 검색버튼 콜백함수
		onClickReset : false,

	}, args);

	$('<div></div>').appPopup({
		url:        options.loadUrl,
		params:     options.loadParams,
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		onloaded:   function( pop ) {
			let P_FORM = $('#popupForm');
			// 목록정의
			$('#appPopupGrid').appGrid({
				mode:    'LIST',
				url:     options.gridUrl,
				params:  options.gridParams,
				autoload: true,
				listOptions: {
					gridCls: 'table-box-1',
					headCls: 't-t-c ts-l',
					bodyCls: 'px-v-s t-t-c ts-xl py-v-m',
					scroll:   true,
					colgroup:['180px','*','50px'],
					columns: [
						{name:'text'   , label: options.title, cls:'fw-600'},
						{name:'tooltip', label:'설명', cls:'text-start', formatter: $.formatUtil.toPreHtml},
						{name:'code'   , label:'선택',
							formatter: function(value) {
								let d = $('<div class="check-radio-box ver1 only-ver"></div>');
								d.append('<input type="checkbox" id="p_'+options.name+value+'" name="'+options.name+'" value="'+value+'">');
								d.append('<label for="p_'+options.name+value+'"></label>');
								return d;
							}
						}
					]
				}
			}).appGrid('init');
			
			// 버튼정의
			$('#appPopupButton').appButtons({
				cls       : 'mt-16px',
				buttons   : [{
					id    : 'p_btnReset',
					cls   : 'btn-combi-3 w-100 bs-l',
					icon  : 'icon-rotate-left',
					value : options.btnResetName,
					click : 'doReset'
				},{
					id    : 'p_btnSearch',
					cls   : 'btn-primary w-100 bs-l',
					icon  : 'icon-search',
					value : options.btnSearchName,
					click : 'doSearch'
				}],
				functions : {
					// 초기화버튼 클릭
					doReset: function() {
						P_FORM[0].reset();
						if (args.onClickReset)
							args.onClickReset();
						return false;
					},
					// 검색버튼 클릭
					doSearch: function() {
						// 선택값 가져오기
						let v = $.formUtil.getValue(P_FORM, options.name, true);
						if (args.onClickSearch)
							args.onClickSearch(v);
						pop.close();
						return false;
					}
				}
			});
		}
	}).open();

};

// 사업분야선택 팝업
//=========================================================================
popup.openBizField = function ( args ) {
	
	let options = $.extend({
		title     : '사업분야',
		name      : 'bizFldList',
		icon      : '<img src="'+getUrl('/images/sub/Bookmark.svg')+'">',
		loadUrl   : getUrl('/usr/invest/ent/modalBizField.do'),
		loadParams: {},
		gridUrl   : getUrl('/com/common/getComboCode.do'),
		gridParams: {upCdId: CODE.BIZ_RLM.code},
		// 초기화버튼 명칭
		btnResetName : '초기화',
		// 초기화버튼 콜백함수
		onClickReset : false,
		// 검색버튼 명칭
		btnSearchName: '검색',
		// 검색버튼 콜백함수
		onClickReset : false,
	}, args);

	$('<div></div>').appPopup({
		url:        options.loadUrl,
		params:     options.loadParams,
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		onloaded:   function( pop ) {
			let P_FORM = $('#popupForm');
			// 목록정의
			$('#appPopupGrid').appGrid({
				mode:    'LIST',
				url:     options.gridUrl,
				params:  options.gridParams,
				autoload: true,
				listOptions: {
					gridCls: 'table-box-1',
					headCls: 't-t-c ts-l',
					bodyCls: 'px-v-s t-t-c ts-xl py-v-m',
					scroll:   true,
					colgroup:['180px','*','50px'],
					columns: [
						{name:'text'   , label: options.title, cls:'fw-600'},
						{name:'cdCn'   , label:'설명', cls:'text-start', formatter: $.formatUtil.toPreHtml},
						{name:'code'   , label:'선택',
							formatter: function(value) {
								let d = $('<div class="check-radio-box ver1 only-ver"></div>');
								d.append('<input type="checkbox" id="p_'+options.name+value+'" name="'+options.name+'" value="'+value+'">');
								d.append('<label for="p_'+options.name+value+'"></label>');
								return d;
							}
						}
					]
				},
			}).appGrid('init');
			
			// 버튼정의
			$('#appPopupButton').appButtons({
				cls       : 'mt-16px',
				buttons   : [{
					id    : 'p_btnReset',
					cls   : 'btn-combi-3 w-100 bs-l',
					icon  : 'icon-rotate-left',
					value : options.btnResetName,
					click : 'doReset'
				},{
					id    : 'p_btnSearch',
					cls   : 'btn-primary w-100 bs-l',
					icon  : 'icon-search',
					value : options.btnSearchName,
					click : 'doSearch'
				}],
				functions : {
					// 초기화버튼 클릭
					doReset: function() {
						P_FORM[0].reset();
						if (args.onClickReset)
							args.onClickReset();
						return false;
					},
					// 검색버튼 클릭
					doSearch: function() {
						// 선택값 가져오기
						let v = $.formUtil.getValue(P_FORM, options.name, true);
						if (args.onClickSearch)
							args.onClickSearch(v);
						pop.close();
						return false;
					}
				}
			});
		}
	}).open();
};

// 펀드 지원하기 팝업
//=========================================================================
popup.openApplyFund = function ( args ) {

	if (!args.params) {
		$.commMsg.alert('조회조건을 확인할 수 없습니다.');
		return false;
	}
	let params = args.params;
	$('<div></div>').appPopup({
		url:   getUrl('/usr/invest/fund/modalApplyFund.do'),
		params: JSON.stringify(params),
		appendBody: true,
		type:  'pageload',
		title: '지원하기',
		icon:  '<img src="'+getUrl('/images/sub/Bookmark.svg')+'">',
		onloaded: function( pop ) {
			// 첨부파일영역 정의
			$('#p_appBizFile').appBizFile({
				cls: 'border-0 mb-0 pb-0',
				// 처리모드
				mode: MODE.INSERT,
				// 설명글
				comment: {
					check: '작성하신 IR자료가 투자자에 공개됩니다.',
					input: '100MB 이내 1개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장'
				},
				// 체크박스
				check: {
					id:    'p_irRlsYn',
					name:  'irRlsYn',
					value: 'Y',
					label: ' 내 IR 자료',
					icon:  'icon-angle-right pl-60px',
					click: function() {
						// IR 작성여부 확인
						let r = $.ajaxUtil.ajaxDefault(getUrl('/usr/invest/ent/existIr.do'));
						// IR자료 필수 제출해야함
						if (!r) {
							let msg = $('<div></div>').appMessage({
								title:   '안내 메시지',
								message:['IR 자료가 작성되어 있지 않습니다. ',
								         '먼저 IR 자료를 작성하세요.'].join(''),
								buttons: [{	
									cls:  'btn-primary', 
									icon: 'icon-arrow-forward-square-alt-M', 
									text: '작성하러 가기', 
									click: function() {
										goUrl(getUrl('/usr/mypage/ent/openEntIr.do'));
									}
								},{	
									cls:  'btn-gray',
									icon: 'icon-times F', 
									text: '취소', 
									click: function() {
										$('#p_irRlsYn').prop('checked', false);
										msg.close();
									}
								}]
							});
						}
					}
				},
				initData: {
					fileType: CODE.FILE_TYPE.BIZ,
					docuNo:   $('#p_fundNo'  ).val(),
					docuSeq:  $('#p_bzentyNo').val()
				}
			});
			// 첨부파일영역 초기화
			$('#p_appBizFile').appBizFile('init');
			
			// 제출버튼 클릭 이벤트 처리
			$('#p_btnSubmit').bind('click', function() {

				let p = $('#applyForm');
				let fobj = p.serializeObject();
				if ($.commUtil.empty(fobj['fundNo'])) {
					$.commMsg.alert('펀드정보를 확인할 수 없습니다.');
					return false;
				}
				if ($.commUtil.empty(fobj['bzentyNo'])) {
					$.commMsg.alert('지원정보를 확인할 수 없습니다.');
					return false;
				}
				if ($.commUtil.empty(fobj['irRlsYn'])) {
					$.commMsg.alert('IR 자료는 필수 제출하셔야 합니다.');
					return false;
				}
				$.formUtil.setValue(p, 'mode', MODE.INSERT);

				// 첨부파일 VALIDATION 
				var fvalidate = $('#p_appBizFile').appBizFile('validate', {
					isAlert: true,
					isExt:   true,
					isLimit: true
				});
		        if (!fvalidate)
		            return false;
		        
		        // 여기에서 새로운 팝업창뜨게 메소드 호출 ㅎ
				let message = "<div style='text-align:center;'>농식품 투자정보 플랫폼은 시간이나 공간의 제약없이 간편하게<br/> 회사 정보를 다수의 투자자들에게 소개할 목적으로 개발되었습니다.<br/><br/> </div>" +
			    "<div style='font-weight:600; text-align:center;'>본 플랫폼을 통해 투자자들에게 투자유치 제안서를 제출해도 <br/>투자자들은 귀사의 투자 제안 내용에 대해<br/>관심을 표명하지 않을 수 있음을 알려드립니다.</div><br/>" +
			    "<div style='text-align: center; font-size:larger'>위 내용을 확인 했습니다." +
			        '<label id="agreeChck" style="display: initial; align-items: center; margin-left: 30px;">' +
			        	'<input type="checkbox" id="agreeCh" name="agreeCh" hidden>' +
			            '<span class="checkmark"></span>' +
			        '</label>' +
			    '</div>' +
			    "<script>" +
			        "$(document).ready(function(){" +
			        	"$('#btn_msg_ok, #btn_msg_close').hide();" +
			            "$('#agreeCh').on('change', function() {" +
				            "if ($(this).is(':checked')) {" +
		                    " $('#btn_msg_ok, #btn_msg_close').show();" +
		                    "console.log('Y');" +
		                "} else {" +
		                    "$('#btn_msg_ok, #btn_msg_close').hide();" +
		                    "console.log('N');" +
		                "}" +
		            "});" +
		        "});" +
			    "</script>";
		        $.commMsg.confirm(message, function() {
	    			// 등록폼을 AJAX로 저장처리
	    			p.ajaxForm({
	    				url: getUrl('/usr/invest/fund/saveFundSprt.do'), 
	    				enctype : 'multipart/form-data',
	    				// 오류시 처리로직
	    				error: $.ajaxUtil.error,
	    				// 저장후 처리로직
	    				success: function(ret) {
	    					$.ajaxUtil.success(ret, function() {
	    						$.commMsg.alert('성공적으로 제출되었습니다.', function() {
	    							if (args.callback)
	    								args.callback();
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
			 $('#agreeCh').on('change', function() {
			        if ($(this).is(':checked')) {
			            console.log('Y');
			        } else {
			            console.log('N');
			        }
			    });
			}
	}).open();
};

// 미팅신청 팝업
//=========================================================================
popup.openMeeting = function ( args ) {

	if (!args.params) {
		$.commMsg.alert('조회조건을 확인할 수 없습니다.');
		return false;
	}
	let params = args.params;
	$('<div></div>').appPopup({
		url:   getUrl('/usr/mypage/meeting/modalMeeting.do'),
		params: JSON.stringify(params),
		appendBody: true,
		type:  'pageload',
		title: '미팅신청',
		icon:  '<img src="'+getUrl('/images/sub/Bookmark.svg')+'">',
		onloaded: function( pop ) {

			// 내용입력 길이표시
			$.eventUtil.showStringLength('#p_aplyCn','#p_aplyCnLength', 200);

			// 보내기버튼 클릭 이벤트 처리
			$('#p_btnSubmit').bind('click', function() {

				let p = $('#meetingForm');
				let fobj = p.serializeObject();
				if ($.commUtil.empty(fobj['aplcntNo'])) {
					$.commMsg.alert('작성자 정보를 확인할 수 없습니다.');
					return false;
				}
				if ($.commUtil.empty(fobj['trgtBzentyNo'])) {
					$.commMsg.alert('경영체 정보를 확인할 수 없습니다.');
					return false;
				}
				if ($.commUtil.empty(fobj['aplyCn'])) {
					$.commMsg.alert('내용은 필수 입력하셔야 합니다.');
					return false;
				}
				$.formUtil.setValue(p, 'mode', MODE.INSERT);
		
		    	$.commMsg.confirm("신청하시겠습니까?", function() {
					$.ajaxUtil.ajaxSave(
						getUrl('/usr/mypage/meeting/saveMeeting.do'),
						JSON.stringify(p.serializeObject()),
						function() {
	                    	$.commMsg.alert('성공적으로 신청되었습니다.', function() {
								if (args.callback)
									args.callback();
								pop.close();
								return false;
							});
						}
					);
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

// 업종트리 선택팝업
//=========================================================================
popup.openTpbizTree = function ( args ) {

	// 항목선택시 콜백함수
	if (!args.callback) {
		$.commMsg.alert('업종선택시 처리함수를 확인할 수 없습니다.');
		return false;
	}
	
	$('<div></div>').appPopup({
		url        : getUrl('/com/common/modalTpbizTree.do'),
		params     : JSON.stringify({}),
		title      : '업종검색',
		icon       : '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
		type       : 'pageload',
		style      : 'max-width:626px;',
		dialogCls  : 'position-nomal',
		headerBcls : 'border-black',
		appendBody : true,
		onloaded   : function(pop) {
			// 업종선택 트리생성 (comm_component.js)
			$('#appPopupTree').appTpbizTree({callback: function(data) {
				args.callback(data);
				pop.close();
			}});
			// 닫기 클릭 이벤트 처리
			$('#p_btnClose').bind('click', function() {
				pop.close();
				return false;
			});
		}
	}).open();
};

// 2023.08.21 업체선택 팝업
//=========================================================================
popup.openEnt = function ( args ) {
	
	let options = $.extend({
		title     : '업체선택',
		icon      : '<img src="'+getUrl('/images/sub/Search.svg')+'">',
		loadUrl   : getUrl('/usr/invest/ent/modalEnt.do'),
		loadParams: {},
		gridUrl   : getUrl('/usr/invest/ent/getListEntBiz.do'),
		gridParams: {},
		// 업체선택 콜백함수
		callback  : function() {}
	}, args);

	$('<div></div>').appPopup({
		url:        options.loadUrl,
		params:     options.loadParams,
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		onloaded:   function( pop ) {
			
			bindEnter($('#p_srchText'), $('#p_btnEntSearch'));
			
			$('#p_btnEntSearch').click(function() {
		        let obj = {srchText: $('#p_srchText').val()};
				$('#appPopupGrid').appGrid('search', obj);
			});
			// 목록정의
			$('#appPopupGrid').appGrid({
				mode:    'LIST',
				url:     options.gridUrl,
				params:  options.gridParams,
				autoload: true,
				// 페이지 표시 객체
				paging:     '#appPopupGridPagination',
				listOptions: {
					gridCls: 'table-box-1',
					//headCls: 't-t-c ts-l',
					//bodyCls: 'px-v-s t-t-c ts-xl py-v-m',
					scroll:   true,
					colgroup: ['60px','150px','100px','*','100px'],
					// 목록칼럼 정의
					columns: [
						{cls:'app-c', name:'bzentyNo'     , label:'번호', rownumbers: true},
						{cls:'app-l', name:'bzentyNm'     , label:'경영체명'},
						{cls:'app-l', name:'invtFldText'  , label:'투자분야'},
						{cls:'app-l', name:'bizFldText'   , label:'사업분야'},
						{cls:'app-c', name:'rprsvNm'      , label:'대표자명'},
					],
					select: function(row) {
						options.callback(row);
						pop.close();
					}
				},
			}).appGrid('init');
		}
	}).open();
};


// 투자단계 팝업
//=========================================================================
popup.openInvestStep = function () {

	$('<div id="appPopupInvestStep"></div>').appPopup({
		title:      '투자단계',
		icon:       '<img src="'+getUrl('/images/sub/Bookmark.svg')+'">',
		dialogCls:  'w-ver1',
		appendBody: true,
		message:    '<div id="appPopupGrid" class="mb-24px"></div>',
		onshown:  function(pop) {
			$('#appPopupGrid').appGrid({
				mode: 'LIST',
				autoload: true,
				dataRows: [{
					"stepNm": "시드단계", 
					"stepCn": "수천만 ~ 5억", 
					"stepMd": "보통주, SAFE", 
					"stepVu": "몇억원에서 30억", 
					"stepVr": "엔젤투자자,엑셀러레이터,마이크로 VC,초기 전문 VC"
				},{	"stepNm": "시리즈A", 
					"stepCn": "5억 ~ 50억", 
					"stepMd": "RCPS", 
					"stepVu": "수십억 ~ 200억", 
					"stepVr": "엔젤투자자,VC"
				},{	"stepNm": "시리즈B", 
					"stepCn": "50억 ~ 200억", 
					"stepMd": "RCPS", 
					"stepVu": "100억 ~ 수백억", 
					"stepVr": "VC"
				},{	"stepNm": "시리즈C", 
					"stepCn": "수백억 ~ 수천억", 
					"stepMd": "RCPS, CB, BW", 
					"stepVu": "수백억 ~ 수천억", 
					"stepVr": "VC,헤지펀드,투자은행,사모펀드"
				}],
				listOptions: {
					gridCls: 'table-box-1',
					headCls: 'bs-1 ts-l',
					bodyCls: 'bs-1 ts-m',
					headthCls: '',
					colgroup:['100px','150px','150px','200px','*'],
					columns: [
						{name:'stepNm' , label: '투자 단계', cls: 'app-c'},
						{name:'stepCn' , label: '투자 금액', cls: 'app-r'},
						{name:'stepMd' , label: '투자 방식', cls: 'app-c'},
						{name:'stepVu' , label: '기업 가치', cls: 'app-r'},
						{name:'stepVr' , label: '대표적인 투자자'}
					]
				}
			}).appGrid('init');
		}
	});
};
