/**
******************************************************************************************
*** 파일명    : comm_popup.js
*** 설명      : 공통 팝업 자바스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.03.14              LSH
******************************************************************************************
**/
//===========================================================================//
// 공통팝업
//===========================================================================//
$.fn.appPopup = function ( args ) {

	var options = $.extend({
		// 팝업타입 (base: 기본타입, pageload: 화면로드타입)
		type: 'base',
		// (선택) 팝업제목
		title: false,
		// (선택) 팝업제목 아이콘
		icon: false,
		// (선택) 추가 스타일시트
		cls: false,
		// (선택) 다이얼로그 스타일시트
		dialogCls: false,
		// (선택) 헤더 스타일시트
		headerCls: false,
		// (기본) 헤더 스타일시트
		headerBcls: 'border-0',
		// (기본) 바디 스타일시트
		bodyCls: 'pt-0',
		// 팝업내용 (문자열 또는 함수)
		message: false,
		// 버튼 목록 배열 {id,code,text,click}
		buttons: false,
		// 제목 버튼 목록
		titleButton: false,
		// 팝업생성시 오픈여부
		autoopen: true,
		// 팝업오픈시 로드여부
		autoload: false,
		// 닫을때 객체삭제
		destroy:  true,
		// 오픈시 BODY에 추가
		appendBody: false,
		// 오픈후 콜백함수
		onshown:  false,
		// 닫기후 콜백함수
		onhidden: false,
		// 페이지로드후 콜백함수
		onloaded: false,
		// 페이지로드시 URL
		url: false,
		// 페이지로드시 조회조건
		params: false,
		// 2023.05.17 추가
		modalOptions: false,
		// 스타일 추가
		style: false
	}, args);

	// 페이지로드 타입인 경우 기본설정변경
	if (options.type == 'pageload') {
		$.extend(options, {
			autoload:  true,
			autoopen:  false,
			destroy:   true
		});
		$.extend(options, args);
	}

	//현재객체
	let thisCmp = this;
	//레이어객체
	let thisElm = $(this);
	let thisDialog = false;


	// 옵션정보 반환
	this.getOptions = function() {
		return options;
	};
	// 팝업 버튼객체 반환
	this.getButton = function( btnId ) {
		return thisElm.find('[id="'+btnId+'"]');
	};
	// 팝업 내용객체 반환
	this.getMessage = function() {
		return options.message;
	};
	// 다이얼로그객체 반환
	this.getDialog = function() {
		return thisDialog;
	};
	// 팝업 조회조건 반환
	this.getParams = function() {
		return options.params;
	};
	// 팝업 조회조건 반환
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
					options.message = s;
					thisElm.find('.modal-body'  ).html('');
					thisElm.find('.modal-body'  ).html(s);
					thisElm.find('.modal-footer').html('');
				}
			});
		}
	};

	// 팝업 내용HTML 로딩
	this.loadHtml = function( html ) {

		if (!html)
			return;

		// 내용담기
		options.message = html;
		thisElm.find('.modal-body').html('');
		thisElm.find('.modal-body').html(html);

		if (options.onloaded)
			options.onloaded(thisCmp);

		if (thisElm.find('.modal-body input').length > 0)
			thisElm.find('.modal-body input').eq(0).focus();
	};

	// 팝업 내용HTML 리셋
	this.resetHtml = function() {
		thisElm.find('.modal-body').html('');
	};
	
	// 팝업레이아웃 생성
	this.create = function() {
		thisElm.addClass("custum-modal fade");
		thisElm.prop("tabindex", "-1");
		thisElm.prop("aria-hidden", "true");
		thisElm.append('<div class="cutum-modal-dialog"></div>');
		thisElm.find('.cutum-modal-dialog').append('<div class="modal-content"></div>');
		if (options.style)
			thisElm.find('.cutum-modal-dialog').prop('style', options.style);
		thisElm.find('.modal-content').append(thisCmp.createTitle());
		thisElm.find('.modal-content').append(thisCmp.createInner());
		
		if (options.modalOptions)
			thisElm.modal($.extend({
				backdrop: 'static', 
				keyboard: false
			}, options.modalOptions));
							
		// 버튼 생성
		if (options.buttons &&
			options.buttons.length > 0) {
			let div = $('<div class="modal-footer"></div>');
			let wrp = $('<div class="bottom-box-btn"></div>');
			wrp.append(thisCmp.createButtons(options.buttons));
			div.append(wrp);
			thisElm.find('.modal-content').append(div);
		}
		
		thisElm.find("button.close-btn").bind('click', thisCmp.close);
		
		if (options.cls)
			thisElm.addClass(options.cls);
		if (options.dialogCls)
			thisElm.find('.cutum-modal-dialog').addClass(options.dialogCls);

		if (options.appendBody)
			thisElm.appendTo('body');
			
		if (thisElm.find('input').length > 0) {
			thisElm.find('input').eq(0).focus();
		}
		else if (thisElm.find('button').length > 0)
			thisElm.find('button').eq(0).focus();
	};
	// 팝업제목 생성
	this.createTitle = function() {
		let div = $('<div class="modal-header"></div>');
		div.addClass(options.headerBcls);
		if (options.headerCls)
			div.addClass(options.headerCls);
		
		div.append('<h4 class="modal-title d-none d-xl-inline-flex"></h4>');
		div.append('<button type="button" class="close-btn d-none d-xl-block"><i class="icon-times F"></i></button>');
		div.append('<button type="button" class="close-btn d-xl-none"><i class="icon-angle-left-circle"></i></button>');
		div.append('<h4 class="modal-title d-xl-none"></h4>');
		if (options.icon) {
			div.find('.modal-title').append(options.icon);
		}
		div.find('.modal-title').append('<span>'+options.title+'</span>');
		
		// 제목버튼이 있는 경우
		if (options.titleButton) {
			div.find('.modal-title').append(thisCmp.createButton(0, options.titleButton));
		}
		return div;
	};

	// 내용 영역 생성
	this.createInner = function() {
		let inner = $('<div class="modal-body '+options.bodyCls+'"></div>');

		// 내용생성
        if ($.type(options.message) == 'function')
			inner.append(options.message(thisCmp));
		else
			inner.append(options.message);
		return inner;
	};
	
	// 버튼 생성
	this.createButton = function( i, b ) {

		let obj = $('<button type="button"></button>');
		if (b.btnCls)
			obj.addClass(b.btnCls);
		else
			obj.addClass("w-100 bs-l");
		if (b.icon)
			obj.append('<i class="'+b.icon+'"></i>&nbsp;');
		
		obj.append(b.text);
		obj.data('text', b.text);
		obj.prop('tabindex', i);
		if (b.code)       obj.data('code', b.code);
		if (b.cls)        obj.addClass(b.cls);
		if (b.id )        obj.prop('id', b.id);
		if (b.clickClose) obj.bind('click', function() { thisCmp.close(); return false; });
		else if (b.click) obj.bind('click', function() {b.click(thisCmp); return false; });
		
		return obj;
	};

	// 버튼목록 생성
	this.createButtons = function( buttons ) {

		let row = $('<div class="row"></div>');
							
		$.each(buttons, function(i,b) {
			let col = $('<div class="col"></div>');
			col.append(thisCmp.createButton(i, b));
			row.append(col);
		});
		
		return row;
	};
	
	// 닫기 함수
	this.close = function() {
    	thisElm.removeClass('show');
		$('body').removeClass('overflow-hidden');
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
		thisCmp.create();

		// 팝업오픈시 로드
		if (options.autoload)
			thisCmp.load( opts );

		if (options.onshown)
			options.onshown(thisCmp);
		
		thisElm.addClass('show');
		$('body').addClass('overflow-hidden');
		return thisCmp;
	};
	// 객체생성시 팝업오픈
	if (options.autoopen)
		this.open();
	
	return this;
};

//===========================================================================//
// 2021.10.27 LSH 공통메세지처리
//===========================================================================//
$.fn.appMessage = function ( args ) {

	var options = $.extend({
		// alert, confirm
		type: 'alert',
		// 제목
		title: 'MESSAGE',
		// (선택) 추가 스타일시트
		cls: false,
		// 팝업내용 (문자열 또는 함수)
		message: false,
		// 버튼 목록 배열 {id,code,text,click}
		buttons: false,
		// 오픈후 콜백함수
		onshown:  false,
		// 닫기후 콜백함수
		onhidden: false,
		// 확인버튼 클릭시 처리함수
		clickOk: false,
		// 취소버튼 클릭시 처리함수
		clickClose: false,
		// X버튼 표시여부
		closable: true

	}, args);

	//현재객체
	let thisCmp = this;
	//레이어객체
	let thisElm = $(this);

	// 옵션정보 반환
	this.getOptions = function() {
		return options;
	};
	// 팝업 버튼객체 반환
	this.getButton = function( btnId ) {
		return thisElm.find('[id="'+btnId+'"]');
	};
	// 팝업 내용객체 반환
	this.getMessage = function() {
		return options.message;
	};

	if (!options.buttons) {
		if (options.type == 'confirm') {
			options.buttons = [{
				id: 'btn_msg_ok',
				cls: 'btn-primary',
				text: '확인',
				icon: 'icon-check',
				click: function() {
					thisCmp.close();
					if (options.clickOk)
						options.clickOk(thisCmp);
				}
			},{
				id: 'btn_msg_close',
				cls: 'btn-combi-3',
				text: '취소',
				icon: 'icon-times F',
				click: function() {
					thisCmp.close();
					if (options.clickClose)
						options.clickClose(thisCmp);
				}
			}];
		}
		else {
			options.buttons = [{
				id: 'btn_msg_ok',
				cls: 'btn-primary',
				text: '확인',
				icon: 'icon-check',
				click: function() {
					thisCmp.close();
					if (options.clickClose)
						options.clickClose(thisCmp);
				}
			}];
		}
	}

	// 팝업레이아웃 생성
	this.create = function() {
		thisElm.addClass("custum-modal fade");
		thisElm.prop("tabindex", "-1");
		thisElm.prop("aria-hidden", "true");
		thisElm.append('<div class="cutum-modal-dialog"></div>');
		thisElm.find('.cutum-modal-dialog').append('<div class="modal-content"></div>');
		thisElm.find('.modal-content').append(thisCmp.createTitle());
		thisElm.find('.modal-content').append(thisCmp.createInner());
		if (options.cls)
			thisElm.addClass(options.cls);
	};
	
	// 팝업제목 생성
	this.createTitle = function() {
		
		let div = $('<div class="modal-header"></div>');
		div.append('<h4 class="modal-title d-none d-xl-inline-flex"></h4>');
		if (options.closable) {
			div.append('<button type="button" class="close-btn d-none d-xl-block" style="outline:none"><i class="icon-times F"></i></button>');
			div.append('<button type="button" class="close-btn d-xl-none"><i class="icon-angle-left-circle"></i></button>');
		}
		div.append('<h4 class="modal-title d-xl-none"></h4>');
		div.find('.modal-title').append('<span>'+options.title+'</span>');
		div.find('.close-btn').bind('click', this.close);
		return div;
	};

	// 내용 영역 생성
	this.createInner = function() {
		let inner = $('<div class="modal-body"></div>');

		// 내용생성
        if ($.type(options.message) == 'function')
			inner.append(options.message(thisCmp));
		else
			inner.append(options.message);

		// 버튼 생성
		if (options.buttons &&
			options.buttons.length > 0) {
			inner.append(thisCmp.createButtons(options.buttons));
		}
		return inner;
	};

	// 버튼 생성
	this.createButtons = function( buttons ) {

		let div = $('<div class="bottom-box-btn py-24px"></div>');
		div.append('<div class="row"></div>');

		$.each(buttons, function(i,b) {
			
			let col = $('<div class="col"></div>');
			let obj = $('<button type="button" class="app-msg-click w-100 bs-l" style="outline:none"></button>');
			
			if (b.icon)
				obj.append('<i class="'+b.icon+'"></i>&nbsp;');
			
			obj.append(b.text);
			obj.data('text', b.text);
			obj.prop('tabindex', i);
			if (b.code)       obj.data('code', b.code);
			if (b.cls)        obj.addClass(b.cls);
			if (b.id )        obj.prop('id', b.id);
			if (b.clickClose) obj.bind('click', function() { thisCmp.close(); return false; });
			else if (b.click) obj.bind('click', function() {b.click(thisCmp); return false; });
			col.append(obj);
			div.find('.row').append(col);
		});
		return div;
	};

	// 닫기 함수
	this.close = function() {
    	thisElm.removeClass('show');
		$('body').removeClass('overflow-hidden');
		if (options.onhidden)
			options.onhidden(thisCmp);
		thisElm.remove();
	};
	// 오픈 함수
	this.open = function() {
		thisCmp.create();
		
		thisElm.appendTo('body');
		thisElm.addClass('show');

		if (options.onshown)
			options.onshown(thisCmp);

		$('body').removeClass('overflow-hidden').addClass('overflow-hidden');
		
		$('#btn_msg_ok').prop("tabindex", "-1");
		setTimeout( function(){ $('#btn_msg_ok').focus(); }, 1 );
		return thisCmp;
	};
	this.open();

	return this;
};

/**
 * 공통 Alert, Confirm
 * $.commMsg.alert('message');
 */
$.commMsg = {
    alert: function (msg, clickClose) {
		$('<div></div>').appMessage({
			type: 'alert',
			message: msg,
			clickClose: clickClose
		});
    },
    confirm: function (msg, clickOk, clickClose) {
		$('<div></div>').appMessage({
			type: 'confirm',
			message: msg,
			clickOk: clickOk,
			clickClose: clickClose
		});
    },
    /**
     * AJAX 통신 결과 공통 처리 로직
     * ajax의 success 함수에서 필요시 사용한다.
     * 성공 메세지
     * 2021.10.26 LSH ADD
     */
    success: function(data, msg, callback) {
		// 2022.02.07 결과메세지 공통처리
		$.ajaxUtil.result(data, function() {
			if (msg) {
				$.commMsg.alert(msg, function() {
					if (callback)
		            	callback(data);
				});
			}
			else {
				if (callback)
	            	callback(data);
			}
		})
    },
    /**
     * AJAX 통신 오류 발생시 공통 처리 로직
     * ajax의 error 함수에 맵핑하여 사용한다.
     * 2021.10.26 LSH ADD
     */
    error: function(request, status, error) {
		// 2022.02.07 결과메세지 공통처리
		$.ajaxUtil.error(request, status, error);
    },
    /**
     * 엑셀로드 결과 공통 처리 로직
     * 2022.01.28 LSH ADD
     */
    successLoad: function(data, msg, callback) {
		// 2022.02.07 결과메세지 공통처리
		$.ajaxUtil.result(data, function() {
			$.commMsg.alert(msg, callback);
		})
    },
}
