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
*** 1.0         2023.05.17              LSH
*******************************************************************************
**/
var popup = {
	// 사업자등록번호 조회팝업
	openBizNo : false,
	// 업종선택 팝업 [2023.07.04]
	openTpbizTree: false,
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
			$("#p_brno1").focus();
			
			let _getComment = function( comment ) {
				let dom = $('<div class="box py-24px text-center" style="border-top: 1px solid var(--Elements-Line);"></div>');
				dom.append('<p class="fs-22px fw-600 lh-1 mb-16px">'+comment+'</p>');
				return dom;
			};

			// 검색 클릭
			$('#p_btnBizSearch').bind('click', function() {
		        if (P_BIZFORM.validate().settings)
		            P_BIZFORM.validate().settings.ignore = false;
		        if (P_BIZFORM.valid() === false)
		            return false;

				//사업자등록번호 병합
				$.formUtil.mergeData('p_brno','bzno', 3);

				$('#p_bizResult').html('');
				$('#p_bizResult').append(_getComment('사업자 정보를 조회중입니다...'));
				
				// 사업자정보 조회
				const result = $.ajaxUtil.ajaxDefault(getUrl('/com/common/getKodata.do'),{bzno: $('#p_brno').val()});
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

// IR 검토의견서 (투자자별) 팝업 
//=========================================================================
popup.openIrOpnn = function ( args ) {
	
	console.log(args);
	
	let options = $.extend({
		title     : 'IR 투자자 검토의견서',
		loadUrl   : getUrl('/adm/invest/event/modalIrOpnn.do'),
		loadParams: {},
	}, args);
	
	console.log(options);

	$('<div></div>').appPopup({
		url:        options.loadUrl,
		params:     options.loadParams,
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		onloaded:   function( pop ) {
			console.log (pop);
		}
	}).open();

};