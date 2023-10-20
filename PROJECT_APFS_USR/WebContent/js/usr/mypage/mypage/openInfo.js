/**
*******************************************************************************
***    명칭: openInfo.js
***    설명: 마이페이지 - 기본정보 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
*******************************************************************************
**/
$(function() {

	let P_ARGS = {
		userNo  : $('#userNo'  ).val(),      // 사용자번호
		bzentyNo: $('#bzentyNo').val(),      // 업체번호
		accessYn: $('#accessYn').val(),      // 승인여부
		selectYn: $('#selectYn').val(),      // 조회가능여부
		updateYn: $('#updateYn').val(),      // 수정가능여부
		kodataYn: $('#kodataYn').val(),      // KODATA여부
		rprsYn  : $('#rprsYn'  ).val()       // 대표여부
	};

	// 개인정보
	$('#appUsrInfo').appMyPageUsrInfo(P_ARGS);
	if (!SCREEN.ROLE.USR) {
		// 기업정보
		$('#appBizInfo').appMyPageBizInfo(P_ARGS);
	}
	// 입력박스 포커스 디자인 적용
	bindFocus();
});

// 개인정보
//=============================================================================
$.fn.appMyPageUsrInfo = function( args ) {
	
	let options = $.extend({
		title   : '개인정보', // 제목
		userNo  : false,      // 사용자번호
		rprsYn  : false,      // 대표여부
		formId  : 'userForm',
		cardIdA : 'appUserAccount',
		cardIdP : 'appUserPrivacy'
	}, args);
	
	let _this = $(this);
	let _form = false;
	let _user = false;

	let _functions = {
		// 휴대폰 본인인증
	    //--------------------------------------------------------//
		doCertify: function() {
			// 모빌리언스 본인인증 팝업 오픈
			$.bizUtils.openMobilians();
			return false;
		},
		// 회원탈퇴
	    //--------------------------------------------------------//
		doFinish: function() {
			
			if (options.rprsYn == 'Y') {
				$.commMsg.alert('대표계정은 회원탈퇴를 하실 수 없습니다. 담당자에게 문의하세요.');
				return false;
			}
	    	$.commMsg.confirm("정말 회원탈퇴를 진행하시겠습니까?", function() {
		        // FORM을 AJAX로 저장처리
		        _form.ajaxForm({
		            url: getUrl('/com/user/deltUser.do'),
		            // 오류시 처리로직
		            error: $.ajaxUtil.error,
		            // 저장후 처리로직
		            success: function(ret) {
						// 탈퇴완료 후 로그아웃으로 이동
						$.commMsg.success(
							ret, 
							'회원탈퇴가 정상적으로 처리되었습니다.',
							$.bizUtils.logout
						);
		            }
		        }).submit();
	    	});
		},
	    // 개인정보 필수검증 및 저장하기
	    //--------------------------------------------------------//
		doSave: function() {

	        // VALIDATION 기능 활성화
	        if (_form.validate().settings)
	            _form.validate().settings.ignore = false;
	        // FORM 검증
	        if (!_form.valid())
	            return false;
		
			let obj = _form.serializeObject();
			if (obj['certYn'] != 'Y' && $.commUtil.empty(obj['pswd'])) {
				$.commMsg.alert('변경하신 개인정보가 없습니다.');
				return false;
			}

	    	$.commMsg.confirm("정말 개인정보를 변경하시겠습니까?", function() {
		        // FORM을 AJAX로 저장처리
		        _form.ajaxForm({
		            url: getUrl('/com/user/updtUser.do'),
		            // 오류시 처리로직
		            error: $.ajaxUtil.error,
		            // 저장후 처리로직
		            success: function(ret) {
						$.commMsg.success(
							ret, 
							'성공적으로 저장되었습니다.', 
							_create
						);
		            }
		        }).submit();
	    	});
		},
	};
	
	function _create() {

		// 기본정보 조회
		_user = $.ajaxUtil.ajaxDefault(getUrl('/usr/mypage/mypage/getUsrInfo.do'),{});
		_this.html('');
		
		let buttons  = [{
			id:    'btnUserSave', 
			cls:   'btn-primary bs-m', 
			icon:  'icon-edit', 
			value: '저장하기', 
			click: _functions.doSave
		}];
		
		// 개인정보제목
		_this.append(
			$.domUtil.getTitleBox({
				title   : options.title, 
				icon    : '<img src="'+getUrl('/images/titimg-4.svg')+'" alt="icon"/>',
				buttons :(_user ? buttons : false)
			})
		);
		_this.append(_createForm());
 		_form = _this.find('form');
		_this.find('.app-item1').append(_createAccount(_user));
		_this.find('.app-item2').append(_createPrivacy(_user));
		
		// 검증룰 정의
		_form.validate({
			debug: false,
			onfocusout: false,
			onsubmit: false,
			rules: {
				pswd: {
	                minlength: 8,
	                maxlength: 30,
	                regx:      /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).{8,30}$/
				},
				pswdCnfm : {
					required: function() {
						return !$.commUtil.empty($("#pswd").val());
					}, 
					equalTo: '#pswd'
				}
			},
			messages: {
	            pswd      : {
	                minlength: $.validator.format('비밀번호를 {0}자 이상 입력해 주세요.'),
	                maxlength: $.validator.format('비밀번호를 {0}자 이하 입력해 주세요.'),
	                regx:     '비밀번호를 영대소문자 + 숫자 + 특수문자 조합으로 입력해 주세요.'
	            },
	            pswdCnfm  : {
					required: '비밀번호를 다시 한번 입력해 주세요.',
	                equalTo:  '재입력한 비밀번호가 일치하지 않습니다.'
				}
			},
			invalidHandler: validateHandler,
			errorPlacement: validatePlacement
		});
		
	}
	
	function _createForm() {
		let f = $('<form method="post" onsubmit="return false;"></form>');
		f.prop('id'  , options.formId);
		f.prop('name', options.formId);
		f.append('<div class="row" style="margin:-12px;"></div>');
		f.find('.row').append('<div class="col-12 col-md p-12px app-item1"></div>');
		f.find('.row').append('<div class="col-12 col-md p-12px app-item2"></div>');
		return f;
	}
	// 계정관리 생성
	function _createAccount(data) {
		return $('<div id="'+options.cardIdA+'"></div>').appDesignCard({
			data: data,
			titleOptions: {
				title  : '계정 관리',
				icon   : '<img src="'+getUrl('/images/sub/MyPage1.svg')+'" alt="icon"/>',
				comment: '서비스에서 사용하는 내 계정 정보를 관리할 수 있습니다.'
			},
			items: [
				{name: 'userId'  , label: '이메일'},
				{name: 'userNm'  , label: '이름', cls:'app-user-name'},
				{name: 'mblTelno', label: '휴대폰 번호', 
					formatter: function(v,o) {
						let s = $.formatUtil.toPhone(v);
						let d = $('<div class="form-area-box small-ver"></div>');
						d.append('<div class="ele-icon-box"></div>');
						d.find('.ele-icon-box').append('<input type="text"   name="mblTelno" id="mblTelno" value="'+s+'" readonly="true" class="app-readonly" title="telNo">');
						d.find('.ele-icon-box').append('<input type="hidden" name="userNm"   id="userNm"   value="'+o['userNm']+'">');
						d.find('.ele-icon-box').append('<input type="hidden" name="userId"   id="userId"   value="'+o['userId']+'">');
						d.find('.ele-icon-box').append('<input type="hidden" name="certYn"   id="certYn"   value="N">');
						return d;
					},
					button: {value:'본인인증', click: _functions.doCertify}
				}
			]
		});
	}
	
	// 개인정보보호 생성
	function _createPrivacy(data) {
		return $('<div id="'+options.cardIdP+'"></div>').appDesignCard({
			data: data,
			titleOptions: {
				title  : '개인 정보 보호',
				icon   : '<img src="'+getUrl('/images/sub/MyPage2.svg')+'" alt="icon"/>',
				comment: '내 계정을 안전하게 보호하기 위한 정보를 관리할 수 있습니다.'
			},
			items: [
				{name:'userNo', label: '비밀번호 변경', formatter: function(v) {
					return $.domUtil.getRow({
						colCls: 'col-12 py-2px',
						items: [{
							format: function() {
								return $.domUtil.getEleIconBox({
									cls  : 'form-area-box small-ver',
									input: '<input type="password" name="pswd" id="pswd" title="pswd" maxlength="30" placeholder="※ 8자 이상 영대소문자+숫자+특수문자 조합">',
									icon : '<i class="icon-times-circle F text-ele-TextDisable app-clear"></i>',
									clickIcon: bindClear
								});
							}
						},{
							format: function() {
								return $.domUtil.getEleIconBox({
									cls  : 'form-area-box small-ver',
									input: '<input type="password" name="pswdCnfm" id="pswdCnfm" title="pswd" maxlength="30" placeholder="비밀번호를 다시 한번 입력하세요.">',
									icon : '<i class="icon-times-circle F text-ele-TextDisable app-clear"></i>',
									clickIcon: bindClear
								});
							}
						}]
					});
				}},
				{label: '회원 탈퇴', button: {value:'회원탈퇴', icon: 'icon-user-ban', click: _functions.doFinish}}
			]
		});
	}
	_create();
	return this;
};

// 기업정보
//=============================================================================
$.fn.appMyPageBizInfo = function( args ) {
	
	let options = $.extend({
		title   : '기업정보', // 제목
		userNo  : false,      // 사용자번호
		bzentyNo: false,      // 업체번호
		accessYn: false,      // 승인여부
		selectYn: false,      // 조회가능여부
		updateYn: false,      // 수정가능여부
		kodataYn: false,      // KODATA여부
		rprsYn  : false,      // 대표여부
	}, args);
	
	if (options.selectYn != 'Y')
		return this;
	
	let _this = $(this);
	
	// 기본정보 조회
	let _data = $.ajaxUtil.ajaxDefault(getUrl('/usr/mypage/mypage/getEntInfo.do'),{});
	// 기업정보 제목
	_this.append(
		$.domUtil.getTitleBox({
			title: options.title, 
			icon: '<img src="'+getUrl('/images/sub/iconimg1.svg')+'" alt="icon"/>'
		})
	);
	// 기업 기본정보
	_this.append($('<div></div>').appBizBasicInfo($.extend({data:_data}, args)));
	// 기업 상세정보
	if (SCREEN.ROLE.ENT) 
		_this.append($('<div></div>').appBizMoreInfo($.extend({data:_data}, args)));
	// 투자 조합정보
	if (SCREEN.ROLE.INV)
		_this.append($('<div></div>').appBizInvstrList(args));
	return this;
};

// 휴대폰 본인인증 팝업창에서 호출하는 함수
function certifyCallback( data ) {
	//console.log(data['Result'  ]); // 결과 (SUCCESS)
	//console.log(data['Name'    ]); // 이름
	//console.log(data['No'      ]); // 휴대전화번호
	//console.log(data['Socialno']); // 생년월일
	
	let nm 	= data['Name'];
	let no	= data['No'  ];
	
	if (data['Result'] == 'SUCCESS') {
		$.commMsg.alert([
			'성공적으로 휴대폰 본인인증이 완료되었습니다.',
			'[저장하기]를 완료하셔야 개인정보에 반영됩니다.'
		].join('<br>'), function() {
			$('.app-user-name').html(nm);
			$('#userNm'       ).val(nm);
			$('#mblTelno'     ).val($.formatUtil.toPhone(no));
			$('#certYn'       ).val('Y'); // 휴대폰인증 완료여부
		});
	}
}
