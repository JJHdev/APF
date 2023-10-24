/**
******************************************************************************************
*** 파일명    : comm_login.js
*** 설명      : 공통 로그인 기능 자바스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.03.20              LSH
******************************************************************************************
**/
$(document).ready(function () {
	// 회원 로그인
	Login.doInit();
});

var Login = {
	doInit: function() {
		// Validation Rule 정의
		$('#loginForm').validate({
			debug: false,
			onfocusout: false,
			onsubmit: false,
			rules: {
				username: { required: true},
				password: { required: true}
			},
			messages: {
				username: { required: '이메일을 입력해 주세요.'},
				password: { required: '비밀번호를 입력해 주세요.'}
			},
			invalidHandler: validateHandler,
			errorPlacement: validatePlacement
		});
	    // 로그인 클릭
	    $("#btnLogin").bind("click", Login.doLogin);
	    // 회원가입 클릭
	    $("#btnJoin" ).bind("click", Login.doJoin);
	    // 소셜로그인 클릭
	    $(".btnOauth").bind("click", Login.doOauth);

	    // 아이디찾기 클릭
	    $("#btnFindId"  ).bind("click", Login.doFindId);
	    // 비밀번호찾기 클릭
	    $("#btnFindPswd").bind("click", Login.doFindPswd);

		bindEnter($('#username'), $("#btnLogin"));
		bindEnter($('#password'), $("#btnLogin"));

		$('#username').focus();
		
		// 마지막로그인계정표시
		Login.doShowLast();
		
	},
	//회원 로그인
	//--------------------------------------------------------//
	doLogin: function() {
		let loginForm = $('#loginForm');
		if (loginForm.validate().settings)
			loginForm.validate().settings.ignore = false;
		if (!loginForm.valid())
			return false;
			
	    let param   = loginForm.serializeObject();
	    let result  = $.ajaxUtil.ajaxDefault(getUrl('/com/common/loginCheck.do'), param, false);
	    if (result && result != 'syserr') {
			let failFlag = result.failFlag;
			let message  = result.message;
			// 성공이면
			if (failFlag == LOGIN.SUCCESS) {
				goUrl(getUrl("/com/common/loginSucc.do"));
			}
	        // 실패
			else {
				message = $.commUtil.restoreHtml(message);
				$('#password').val('');
				
				if (failFlag == LOGIN.REJECT) {
					// 재신청 메시지
					Login.doReApply(message);
				}
				else {
		            // 에러 메시지
					$.commMsg.alert(message);
				}
			}
	    }
	},
	// 재신청 메시지 팝업
	//--------------------------------------------------------//
	doReApply: function(message, callback) {
		$('<div></div>').appMessage({
			type: 'alert',
			message: message,
			buttons: [{
				id: 'btn_msg_ok',
				cls: 'btn-primary',
				text: '재신청',
				icon: 'icon-check',
				click: function(cmp) {
					cmp.close();
					Login.doEntCmpl(callback);
				}
			}]
		});
	},
	// OAUTH 로그인시 재신청 처리
	//--------------------------------------------------------//
	doReApplyOauth: function(failFlag, message) {
		if (failFlag == LOGIN.REJECT) {
			// 재신청 메시지
			Login.doReApply(message, function() {
				window.close();
			});
		}
	},
	// 팝업 로그인체크 샘플
	//--------------------------------------------------------//
	doPopupLogin: function() {
		let url = getUrl('/com/common/popupLoginSample.do');
		let opt = "width=480,height=880, scrollbars=yes, resizable=yes";
		let key = "oauth_pop";
		window.open(url, key, opt);
		return false;
	},
	// 회원 가입 페이지로 이동
	//--------------------------------------------------------//
	doJoin: function() {
		goUrl(getUrl(CURL.JOIN));
		return false;
	},
	// 소셜 로그인 (네이버/카카오)
	//--------------------------------------------------------//
	doOauth: function() {
		let url = $(this).data('url');
		let opt = "width=480,height=880, scrollbars=yes, resizable=yes";
		let key = "oauth_pop";
		window.open(url, key, opt);
		return false;
	},
	// 아이디 찾기 팝업
	//--------------------------------------------------------//
	doFindId: function() {
		let options = {
			title     		: '아이디 찾기',
			icon      		: '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
			loadUrl   		: getUrl('/com/common/modalFindId.do'),
			loadParams		: {},
		};
		modal = $('<div></div>').appPopup({
			url:        	options.loadUrl,
			params:     	JSON.stringify(options.loadParams),
			title:      	options.title,
			icon:       	options.icon,
			type:      	 	'pageload',
			dialogCls:  	'w-ver2',
			appendBody: true
		}).open();
	},
	// 비밀번호 찾기 팝업
	//--------------------------------------------------------//
	doFindPswd: function() {
		let options = {
			title     		: '비밀번호 찾기',
			icon      		: '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
			loadUrl   		: getUrl('/com/common/modalFindPswd.do'),
			loadParams		: {},
		};
		modal = $('<div></div>').appPopup({
			url:        	options.loadUrl,
			params:     	JSON.stringify(options.loadParams),
			title:      	options.title,
			icon:       	options.icon,
			type:      	 	'pageload',
			dialogCls:  	'w-ver2',
			appendBody: true
		}).open();
	},
	// 반려 업체 보완제출 팝업
	//--------------------------------------------------------//
	doEntCmpl: function( callback ) {
		let options = {
			title     		: '기업 정보 수정',
			icon      		: '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
			loadUrl   		: getUrl('/com/common/modalEntCmpl.do'),
			loadParams		: {},
		};
		modal = $('<div></div>').appPopup({
			url:        	options.loadUrl,
			params:     	JSON.stringify(options.loadParams),
			title:      	options.title,
			icon:       	options.icon,
			type:      	 	'pageload',
			dialogCls:  	'w-ver2 app-modal-top',
			appendBody:     true,
			onhidden: function() {
				if (callback)
					callback();
			}
		}).open();
	},
	// 마지막로그인 계정표시
	//--------------------------------------------------------//
	doShowLast: function() {
		let code = $.getCookie('APFS_COOK_LAST');
		if (!$.commUtil.empty(code)) {
			$('.app-last-login').each(function() {
				if ($(this).data('code') == code) {
					$(this).addClass("arrow-center"      );
					$(this).addClass("toltip-only"       );
					$(this).addClass("py-8px"            );
					$(this).addClass("w-auto"            );
					$(this).addClass("white-space-nowrap");
					$(this).addClass("on"                );
					$(this).append("마지막으로 로그인한 계정입니다");
					return true;
				}
			});
		}
		return false;
	}
};

var modal;