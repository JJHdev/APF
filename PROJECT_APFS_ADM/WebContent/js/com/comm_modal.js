/**
******************************************************************************************
*** 파일명    : comm_modal.js
*** 설명      : 관리자시스템용 공통 모달팝업 자바스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.06.01              LSH
******************************************************************************************
**/
//===========================================================================//
// 공통 모달팝업
//===========================================================================//
$.fn.appModal = function ( args ) {
	$(document).ready(function(){
		var lev1 =$(".lev-menu-box .lev1>li>a");
		var lev2 =$(".lev-menu-box .lev2>li>a");
		var lev3 =$(".lev-menu-box .lev2>li>a");
		lev1.click(function(){
			
			$(this).parent().addClass("active").siblings().removeClass("active");
		});
		lev2.click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active");
		});
		lev3.click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active");
		});
		$('.datepicker-input').datepicker({
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
	      }).on('changeDate', function (e) {
	         /* 이벤트의 종류 */
	         //show : datePicker가 보이는 순간 호출
	         //hide : datePicker가 숨겨지는 순간 호출
	         //clearDate: clear 버튼 누르면 호출
	         //changeDate : 사용자가 클릭해서 날짜가 변경되면 호출 (개인적으로 가장 많이 사용함)
	         //changeMonth : 월이 변경되면 호출
	         //changeYear : 년이 변경되는 호출
	         //changeCentury : 한 세기가 변경되면 호출 ex) 20세기에서 21세기가 되는 순간
	 
	         // e.date를 찍어보면 Thu Jun 27 2019 00:00:00 GMT+0900 (한국 표준시) 위와 같은 형태로 보인다.
	      });
	});

	var options = $.extend({
		// 페이지로드시 URL
		url: false,
		// 페이지로드시 조회조건
		params: false,
		// 기본 스타일시트
		modalCls: 'detail-pop-1',
		// (선택) 추가 스타일시트
		cls: false,
		
		// 제목
		title: '',
		// 제목 아이콘
		icon: 'icon-pen',
		// header 스타일시트
		headCls: 'eletabletop1',
		// body 스타일시트
		bodyCls: 'detail-input-box p-16px',
		
		// 팝업생성시 오픈여부
		autoopen:  false,
		// 팝업오픈시 로드여부
		autoload:  true,
		// 닫을때 객체삭제
		destroy:  true,
		// 오픈시 BODY에 추가
		appendBody: true,
		// 오픈후 콜백함수
		onshown:  false,
		// 닫기후 콜백함수
		onhidden: false,
		// 페이지로드후 콜백함수
		onloaded: false,
		
		// 창닫기 버튼 id
		closeBtnId: false
	}, args);

	//현재객체
	let thisCmp = this;
	//레이어객체
	let thisElm = $(this);

	// 옵션정보 반환
	this.getOptions = function() {
		return options;
	};
	// 조회조건 반환
	this.getParams = function() {
		return options.params;
	};
	// 조회조건 반환
	this.getParseParams = function() {
		return JSON.parse(options.params);
	};

	// 팝업 내용로드
	this.load = function( opts ) {

		if (opts) {
			if (opts.url)    options.url    = opts.url;
			if (opts.params) options.params = opts.params;
		}
		if (options.url &&
			options.params) {
			$.ajax({
				url: options.url,
				dataType : "html",
				type: 'post',
				async: false,
                contentType: 'application/json',
				data: options.params,
				success: thisCmp.loadHtml,
				error: function (request) {
					let s = '';
	                // error 처리(권한)
	                if (request.status == '401' || 
						request.status == '403') {
	                    // MSG : 권한이 없거나 세션이 만료되었습니다.
						s = MSG_COMM_E016;
	                }
	                else {
	                    // MSG : 시스템 오류가 발생하였습니다.
						s = MSG_COMM_1002;
                	}
					thisCmp.loadHtml(s);
				}
			});
		}
	};

	// 내용HTML 로딩
	this.loadHtml = function( html, init ) {

		if (!html)
			return;

		thisElm.find('.app-modal-body').html('');
		thisElm.find('.app-modal-body').html(html);

		if (init && options.onloaded)
			options.onloaded(thisCmp);
	};

	// 내용HTML 리셋
	this.resetHtml = function() {
		thisElm.find('.app-modal-body').html('');
	};
	
	// 팝업레이아웃 생성
	this.create = function() {
		thisElm.html('');
		thisElm.addClass(options.modalCls);
		if (options.cls)
			thisElm.addClass(options.cls);

		thisElm.append(thisCmp.createHeader());
		thisElm.append(thisCmp.createInner());
		
		if (options.appendBody)
			thisElm.appendTo('body');
	};
	// 헤더 생성
	this.createHeader = function() {
		
		let div = $('<div class="'+options.headCls+'"></div>');
		div.append('<div class="row"></div>');
		div.find('.row').append('<div class="col"><p class="txt1 app-modal-title"></p></div>');
		div.find('.row').append('<div class="app-modal-button col element"></div>');
		
		div.find('.app-modal-title').append('<i class="'+options.icon+'"></i>');
		div.find('.app-modal-title').append(options.title);
		
		let btn = $('<a href="javascript:void(0)" class="bs-m btn-brown fs-15px"><i class="icon-times"></i> 창닫기</a>');
		if (options.closeBtnId) btn.attr('id', options.closeBtnId);
		btn.click(thisCmp.close);
		div.find('.app-modal-button').append(btn);
		return div;
	};

	// 내용 생성
	this.createInner = function() {
		let body = $('<div></div>');
		body.addClass(options.bodyCls);
		body.addClass('app-modal-body');
		return body;
	};
	
	// 닫기 함수
	this.close = function() {
		thisElm.removeClass('trans');
		if (options.onhidden)
			options.onhidden(thisCmp);
		if (options.destroy) {
			if (options.appendBody)
				thisElm.remove();
			else {
				thisElm.removeClass();
				thisElm.children().remove();
			}
		}
	};
	// 오픈 함수
	this.open = function( opts ) {
		
		thisCmp.close();
		thisCmp.create();

		// 팝업오픈시 로드
		if (options.autoload)
			thisCmp.load( opts );

		if (options.onshown)
			options.onshown(thisCmp);
		
		thisElm.addClass('trans');
		return thisCmp;
	};
	// 객체생성시 오픈
	if (options.autoopen)
		this.open();
	
	return this;
};


