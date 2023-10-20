/**
*******************************************************************************
*** 파일명    : app_join.js
*** 설명      : 회원가입 관련 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.07.11              LSH
*** 1.0         2023.07.11              LSH					이용약관및 개인정보 수집 및 이용동의 작업(전체 체크박스 생성)
*** 1.0         2023.08.17              J H					appJoinAgreeVer2작성 () 전체 체크박스 및 개별 체크박스 이미지 변경및 추가 작업
*******************************************************************************
**/
$.appUtils = {

	// 제목표시
	//=====================================================
	getTitle: function( args ) {
		let dom = $('<div class="top border-0 pb-0"></div>');
		dom.append('<p class="text-icon-3"></p>');
		dom.find('p').append('<i class="'+args.icon+' cycle-ver-1"></i>');
		dom.find('p').append(args.title);
		if (args.must)
			dom.find('p').append('<em class="start"></em>');
		return dom;
	},

	// 설명글표시
	//=====================================================
	getComment: function( comment ) {
		let dom = $('<div class="bg-primary-t5 p-16px round-8px mb-16px"></div>');
		dom.append('<p class="fs-11px fw-300 lh-16px"></p>');
		dom.find('p').append(comment);
		return dom;
	},

	// 이동버튼표시
	//=====================================================
	getButtons: function( buttons, functions ) {
		let r = $('<div class="row"></div>');
		$.each(buttons, function(i,btn) {
			let b = $.domUtil.getButton($.extend(btn, {click: functions[btn['click']]}));
			r.append( $('<div class="col"></div>').append(b) );
		});
		return $('<div class="bottom-box-btn"></div>').append(r);
	},
	
	// 진행단계표시
	//=====================================================
	getSteps: function( args ) {
		let step = FILTER.getJoinStepNo(args.stepCd, args.joinCd);
		let r = $('<div class="row"></div>');
		let n = 1;
		$.each(CODES.JOIN_STEP_LIST, function(i, s) {
			if (s.filter && !s.filter(args.joinCd))
				return true; 
			let c = $('<div class="col"></div>');
			c.append('<i class="'+s['icon']+'"></i>');
			c.append('<p class="txt1">STEP '+n+'</p>');
			c.append('<span class="txt2">'+s['text']+'</span>');
			
			if (n <= step)
				c.addClass('active');
			r.append(c);
			n++;
		});
		let _this = $('<div></div>');
		if (n > 4) _this.addClass('step-box-3');
		else       _this.addClass('step-box-2');
		_this.addClass('mb-40px');
		_this.addClass('active-'+step);
		_this.append(r);
		return _this;
	},
	
	// 모빌리언스 본인인증팝업창 결과처리함수
	//=====================================================
	certifyCallback: function(data) {
		//console.log(data['Result'  ]); // 결과 (SUCCESS)
		//console.log(data['Name'    ]); // 이름
		//console.log(data['No'      ]); // 휴대전화번호
		//console.log(data['Socialno']); // 생년월일
		
		let nm 	= data['Name'];
		let no	= data['No'  ];
		if (data['Result'] == 'SUCCESS') {
			$.commMsg.alert('성공적으로 휴대폰 본인인증이 완료되었습니다.', function() {
				$('#userNm'  ).val(nm);
				$('#mblTelno').val(no);
				// 휴대폰인증 완료여부
				$('#certYn'  ).val('Y');
			});
		}
	},
	// 08.10추가 JH 약관동의용 title 작업
	// 제목표시
	//=====================================================
	getTitleAgre: function( args ) {
		let dom = $('<div class="top border-0 pb-0"></div>');
		dom.append('<p class="text-icon-3"></p>');
		dom.find('p').append('<i class="'+args.icon+' cycle-ver-1"></i>');
		dom.find('p').append(args.title);
	    // 체크박스 요소를 추가
		let checkboxElement = 
			'<label id="agreeChck" style="display: flex; align-items: center; margin-left: 30px;" for="'+ args.field +'1" >' +
			    '<input type="checkbox" id="'+ args.field +'1" name='+ args.field +'>' +
			    '<span class="checkmark"></span>' +
			'</label>';
		dom.find('p').append(checkboxElement);
		
		return dom;
	},
};

// 회원가입 - 종류선택
//=============================================================================
$.fn.appJoinChoice = function ( args ) {

	let options = $.extend({
		stepCd  : 'CHOS',
		joinCd  : CODE.JOIN_CD.USR,
		cls     : 'img-list-1 pt-36px pt-md-72px',
		items   : [{
			code    : CODE.JOIN_CD.BIZ,
			title   : '기업회원',
			image   : getUrl('/images/sub/member2.png'),
			comment : [
				 '농림수산식품경영체(개인사업자, 법인) <span class="fw-600">대표 및 임·직원</span>'
				,'농림수산식품모태펀드 운용사 대표 및 임·직원(VC)'
				,'농식품 유관기관(농업정책보험금융원 사전 협조 필요)'
			]
		},{
			code    : CODE.JOIN_CD.USR,
			title   : '개인회원',
			image   : getUrl('/images/sub/member1.png'),
			comment : [
				'사업자등록번호가 없는 <span class="fw-600">창업예정자</span> 등'
			]
		}],
		click  : function() {
			let joinCd = $(this).data('code');
			// 저장처리
			$.ajaxUtil.ajaxSave(
				getUrl('/com/user/saveChoose.do'), 
				JSON.stringify({joinCd: joinCd}), 
				function() {
			        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
						params: {stepCd:'AGRE', joinCd: joinCd}
					});
					return false;
				}
			);
		}
	}, args);

	let _this = $(this);
	
	function _createRow() {
		let row = $('<div class="row justify-content-center" style="margin: -20px;"></div>');
		$.each(options.items, function(i,item) {
			let c = $('<div class="card-box-member"></div>');
			c.data('code', item['code']);
			c.append('<img src="'+item['image']+'" alt="">');
			c.append('<p class="txt1">'+item['title']+'</p>');
			c.append('<ul></ul>');
			$.each(item.comment, function(i,cmt) {
				c.find('ul').append( $('<li class="txt2"></li>').append(cmt) );
			});
			c.click(options.click);
			row.append( $('<div class="col-12 p-20px"></div>').append(c) );
		});
		return row;
	}

	_this.addClass(options.cls);
	_this.append(_createRow());
	return this;
};

// 회원가입 - 가입완료
//=============================================================================
$.fn.appJoinDone = function ( args ) {

	let options = $.extend({
		form    : false,      // 폼객체
		userId  : false,
		stepCd  : 'DONE',
		joinCd  : CODE.JOIN_CD.USR,
		cls     : 'pt-36px pt-md-72px',
		boxCls  : 'shadow-box-1 py-md-56px py-40px text-center mb-40px',
		image   : getUrl('/images/sub/3d icon.svg'),
		buttons : [{
			data   : {oper: 'MAIN'},
			cls    : 'btn-combi-3 w-100 bs-xl',
			icon   : 'icon-home-roof',
			value  : '메인화면으로 가기',
			click  : 'doClick'
		},{
			data   : {oper: 'LOGIN'},
			cls    : 'btn-primary w-100 bs-xl',
			iconr  : 'icon-arrow-right',
			value  : '로그인하러 가기',
			click  : 'doClick'
		}],
		message:['농식품 투자정보 플랫폼 회원가입 신청이 완료 되었습니다.',
		         '최초로 가입한 대표계정은 농금원 관리자 승인 이후',
		         '로그인이 가능합니다.'
				].join('<br>'),
		comment: '회원 정보 확인 및 수정은 마이페이지에서 가능합니다.'
	}, args);
	
	let _this = $(this);
	
	let _functions = {

		// 이동 버튼 클릭
	    //--------------------------------------------------------//
		doClick: function() {
			let c = $(this).data('oper');
			if (c == 'MAIN') {
				$.bizUtils.goHome();
			}
			else if (c == 'LOGIN') {
				$.bizUtils.login();
			}
			return false;
		}
	};
	
	function _createContent() {
		let dom = $('<div></div>');
		dom.addClass(options.boxCls);
		dom.append('<div class="app-imgarea p-38px w-auto"><img src="'+options.image+'"/></div>');
		dom.append('<p    class="fs-16px fs-md-24px fw-600 mb-8px"></p>');
		dom.append('<span class="d-block fs-12px fs-md-15px fw-300 mb-8px"></span>');
		dom.append('<div  class="app-userid bg-mint-t10 d-inline-block fs-15px fw-600 lh-1 mx-auto py-16px px-24px round-8px text-center"></div>');
		
		dom.find('p'   ).append(options.message);
		dom.find('span').append(options.comment);
		dom.find('div.app-userid' ).append('아이디 : ' + options.userId);
		return dom;
	};
	
	function _create() {
		
		_this.addClass(options.cls);
		// 단계표시
		_this.append( $.appUtils.getSteps(options) );
		// 내용표시
		_this.append( _createContent() );
		// 버튼표시
		_this.append( $.appUtils.getButtons(options.buttons, _functions) );
	};
	
	_create();
	return this;
};

//08.10 JH 추가작업
//회원가입 - 약관동의 수정버전 () 기존 동의,동의X 변경및 체크박스 추가 css변경
//=============================================================================
$.fn.appJoinAgreeVer2 = function ( args ) {

	let options = $.extend({
		stepCd  : 'AGRE',
		joinCd  : CODE.JOIN_CD.USR,
		formId  : 'agreeForm',
		cls     : 'pt-36px pt-md-72px',
		style   : 'max-width:650px;',
		boxCls  : 'post-details-2 mb-24px mx-auto',
		items   : [{
			title  : '홈페이지 약관 (필수)<span class="special-dot">˙</span>',
			field  : 'srvcTrmsAgreYn',
			url    : getUrl('/html/agreeService.html')
		},{
			title  : '<span class="fs-md-20px fw-600 text-left" id="titleArea"> 농림수산식품모태펀드 투자유치 지원과 농업정책보험금융원 투자 <span class="special-dot">˙</span><br/> 지원사업 관리를 위한 개인·기업 정보 수집·이용 동의서 (필수)</span>',
			field  : 'prvcClctAgreYn',
			url    : getUrl('/html/agreePrivacy.html')
		}],
		buttons : [{
			data   : {oper:'PREV'},
			cls    : 'btn-combi-3 w-100 bs-xl',
			icon   : 'icon-arrow-left mr-4px',
			value  : '이전단계',
			click  : 'doClick'
		},{
			data   : {oper:'NEXT'},
			cls    : 'btn-primary w-100 bs-xl',
			icon   : 'icon-arrow-right ml-4px',
			value  : '다음단계',
			click  : 'doClick'
		}]
	}, args);
	
	let _this = $(this);
	let _form = false;
	
	let _functions = {

		// 이전단계, 다음단계 버튼 클릭
	    //--------------------------------------------------------//
		doClick: function() {
			let f = _form;
			let c = $(this).data('oper');
			if (c == 'PREV') {
				goUrl( getUrl(CURL.JOIN) );
			}
			else if (c == 'NEXT') {
				let valid = true;
				$.each(options.items, function(i, o) {
					if ($.formUtil.getValue(f, o['field']) != 'Y') {
						$.commMsg.alert(
							$.commUtil.getPostKor(stripHtmlTags(o['title']), 0) + 
							' 반드시 동의하셔야 합니다.'
						);
						valid = false;
						return false;
					}
				});
				if (!valid) 
					return false;
				
				// 저장처리
				$.ajaxUtil.ajaxSave(
					getUrl('/com/user/saveAgree.do'), 
					JSON.stringify(f.serializeObject()), 
					function() {
						$.formUtil.setValue(f, 'stepCd', 'FUSR');
				        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
							formId: options.formId
						});
						return false;
					}
				);
			}
			return false;
		}
	};
	
	function stripHtmlTags(inputString) {
	    var text = $("<div>").html(inputString).text();
	    return text.replace(/˙/g, '');
	}
	
	function _createTerms(item) {
		// 약관내용
		let ret = $.ajaxUtil.ajaxHtml(item['url'], {});

		let dom = $('<div></div>');
		dom.addClass(options.boxCls);
		dom.prop('style', options.style);
		
		dom.append('<div class="box mb-16px"></div>');
		dom.find('.box').append( $.appUtils.getTitleAgre({title: item['title'], icon: 'icon-lightbulb', field: item['field'],}));
		dom.find('.box').append('<div class="body"></div>'); 
		dom.find('.box > .body').append('<div class="d-flex round-16px shadow-box-1" style="justify-content: center; height: 490px; overflow:auto;"></div>');
		dom.find('.box > .body > .shadow-box-1').append(ret);
		
		return dom;
	};
	
	function _createForm() {
		let f = $('<form method="post" onsubmit="return false;"></form>');
		f.prop('id'  , options.formId);
		f.prop('name', options.formId);
		f.append('<input type="hidden" name="stepCd" value=""/>')
		return f;
	};
	
	function _create() {
		
		_this.addClass(options.cls);
		// 단계표시
		_this.append( $.appUtils.getSteps(options) );
		// 폼생성
		_form = _createForm();
		// 약관표시
		$.each(options.items, function(i,item) {
			_form.append( _createTerms(item) );
		});
		_this.append( _form );
		// 버튼표시
		_this.append( $.appUtils.getButtons(options.buttons, _functions) );
	}
	_create();
	return this;
};

// 회원가입 - 개인정보입력
//=============================================================================
$.fn.appJoinUsr = function ( args ) {

	let options = $.extend({
		stepCd  : 'FUSR',
		joinCd  : CODE.JOIN_CD.USR,
		naverUrl: false,
		kakaoUrl: false,
		formId  : 'userForm',
		cls     : 'pt-36px pt-md-72px',
		style   : 'max-width:650px;',
		boxCls  : 'post-details-2 mb-24px mx-auto',
		formCls : 'round-16px shadow-box-1 mx-auto',
		groupCls: 'form-box-of-member p-24px',
		oauth   : [{
			code   : 'N',
			icon   : 'icon-naver F',
			text   : '네이버로 회원가입',
			url    : args.naverUrl,
			click  : 'doOauth'
		},{
			code   : 'K',
			icon   : 'icon-kakao F',
			text   : '카카오로 회원가입',
			url    : args.kakaoUrl,
			click  : 'doOauth'
		}],
		buttons : [{
			data   : {oper:'PREV'},
			cls    : 'btn-combi-3 w-100 bs-xl',
			icon   : 'icon-arrow-left mr-4px',
			value  : '이전단계',
			click  : 'doClick'
		},{
			data   : {oper:'NEXT'},
			cls    : 'btn-primary w-100 bs-xl',
			icon   : 'icon-arrow-right ml-4px',
			value  : '다음단계',
			click  : 'doClick'
		}]
	}, args);
	
	let _this = $(this);
	let _form = false;
	let _card = false;
	
	let _functions = {

		// 이전단계, 다음단계 버튼 클릭
	    //--------------------------------------------------------//
		doClick: function() {
			let c = $(this).data('oper');
			if (c == 'PREV') {
		        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
					params : {stepCd: 'AGRE', joinCd: options.joinCd}
				});
			}
			else if (c == 'NEXT') {
				_functions.doSave();
			}
			return false;
		},

		// 쇼셜로그인 회원가입 (팝업)
	    //--------------------------------------------------------//
		doOauth: function() {
			let url = $(this).data('url');
			let opt = "width=480,height=880, scrollbars=yes, resizable=yes";
			let key = "oauth_pop";
			window.open(url, key, opt);
			return false;
		},

		// 휴대폰 본인인증 (팝업)
	    //--------------------------------------------------------//
		doCertify: function() {
			if (CONFIG.JOIN_MOBILIANS) {
				$.bizUtils.openMobilians();
			}
			else {
	   			// TODO 휴대폰 본인인증 없이 가입테스트 사용
				$.formUtil.setValue(_form, 'certYn', 'Y');
				$.formUtil.setReadonly(_form, false, ['userNm','mblTelno'], 'app-readonly');
				$('#userNm').focus();
			}
			return false;
		},

		// 기본정보 저장 (다음단계)
	    //--------------------------------------------------------//
		doSave: function() {
			let f      = _form;
			let joinCd = options.joinCd;
			let valid  = _card.doValidate();
	        // FORM 검증
	        if (!valid)
	            return false;
	        // FORM을 AJAX로 저장처리
	        f.ajaxForm({
	            url: getUrl('/com/user/saveJoin.do'),
	            // 오류시 처리로직
	            error: $.ajaxUtil.error,
	            // 저장후 처리로직
	            success: function(ret) {
					$.commMsg.success(ret, false, function() {
						if (joinCd == CODE.JOIN_CD.USR) {
					        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
								params : {
									stepCd: 'DONE', 
									joinCd: joinCd,
									userId: ret.userId
								}
							});
						}
						else {
					        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
								params : {
									stepCd: 'FBIZ', 
									joinCd: joinCd,
									userId: ret.userId
								}
							});
						}
					});
	            }
	        }).submit();
	        return false;
		}
	};

	function _createOauth() {
		
		let dom = $('<div></div>');
		dom.addClass(options.boxCls);
		dom.prop('style', options.style);
		dom.append('<div class="box mb-16px"></div>');
		dom.append('<div class="member-box-2"></div>');
		dom.find('.box').append( $.appUtils.getTitle({title: '간편 회원가입', icon: 'icon-tv'}));
		
		let r = $('<div class="row"></div>');
		$.each(options.oauth, function(i,o) {
			
			// TODO 카카오로 회원가입 현재 중지함 (검수완료후 오픈예정)
			//if (o.code == 'K')
			//	return true;
			let c = $('<div class="shadow-box-1 box w-100 justify-content-center"></div>');
			c.data('code', o['code']);
			c.data('url' , o['url' ]);
			c.append('<i class="'+o['icon']+'"></i>');
			c.append('<span>'+o['text']+'</span>');
			// 소셜로그인 회원가입
			c.click(_functions[o['click']]);
			
			r.append( $('<div class="col flex-grow-1"></div>').append(c) );
		});
		dom.find('.member-box-2').append(r);
		
		return dom;
	}
	
	function _createCard() {
		
		return $('<div></div>').appFormCard({
			// 추가 CLS
			//cls: 'mb-32px',
			style: options.style,
			// 박스 CLS
			boxCls: options.formCls,
			// 그룹 CLS
			groupCls: options.groupCls,
			// 빈값텍스트
			defaultValue: STRING.NOINFO,
			// 제목
			title: false,
			// 폼객체
			form: _form,
			// 칼럼목록
			items: [{items: [
				 {cls:'col-12 pb-24px', must: true, name:'userNm', label:'성명', wrapCls: 'input-btn', error: true,
					hidden: {type:'hidden', id:'certYn', name:'certYn'},
					input:[{type:'text', id:'userNm', name:'userNm', placeholder:'본인인증을 진행해 주세요.', cls:'app-readonly', maxlength: 15, readonly: true},
						   {type:'button', value:'본인인증', cls:'btn-black bs-l px-16px', click: _functions['doCertify'], colCls:'flex-b96px'}]
				 }
				,{cls:'col-12 pb-24px', must: true,   name:'mblTelno', label:'휴대폰번호', error: true,
					input: {type:'text',id:'mblTelno',name:'mblTelno', placeholder:'본인인증을 진행해 주세요.', cls:'app-readonly', maxlength: 15, phone: true, readonly: true}
				}
				,{cls:'col-12 pb-24px', must: true, name:'userId', label:'아이디', error: true,
					input: {type:'text',id:'userId',name:'userId', placeholder:'이메일을 입력해 주세요.', clear: true, maxlength: 50, valid: {method:'email'}}
				 }
				,{cls:'col-12 pb-24px', must: true,   name:'pswd', label:'비밀번호', error: true,
					input: {type:'password',id:'pswd',name:'pswd', placeholder:'비밀번호를 입력해 주세요.', clear: true, maxlength: 30, valid: {method:'pswd'}}, 
					comment: {icon: true, text:'※ 8자 이상 영대소문자 + 숫자+특수문자 조합'}
				}
				,{cls:'col-12 pb-24px', must: true,       name:'pswdCnfm', label:'비밀번호 확인', error: true,
					input: {type:'password',id:'pswdCnfm',name:'pswdCnfm', placeholder:'비밀번호를 다시 한번 입력해 주세요.', clear: true, maxlength: 30}
				}
			]}],
			validator: {
				debug      : false,
				onfocusout : false,
				onsubmit   : false,
				rules: {
					userNm   : {required: true},
					mblTelno : {required: true, mobile: true},
					userId   : {required: true, email: true, remote: {
						    type : 'post',
							url  : getUrl('/com/user/unique.do'),
							data : {
								userId: function() {
									return $('#userId').val();
								}
							}
						}
					},
					pswd     : {required: true, pswd: true},
					pswdCnfm : {required: true, equalTo: '#pswd'}
				},
				messages: {
					userNm   : {required: '본인인증을 진행해 주세요.'},
					mblTelno : {
						required: '본인인증을 진행해 주세요.',
						mobile:   '휴대폰번호를 형식에 맞게 입력해 주세요.'
					},
					userId   : {
						required : '아이디를 이메일 형식으로 입력해 주세요.',
						email    : '아이디를 이메일 형식에 맞게 입력해 주세요.', 
						remote   : '이미 가입된 이메일입니다.'
					},
		            pswd      : {
						required : '비밀번호를 입력해 주세요.',
		                pswd     : '비밀번호를 8자이상 30자이내의 영대소문자 + 숫자 + 특수문자 조합으로 입력해 주세요.'
		            },
		            pswdCnfm  : {
						required : '비밀번호 확인을 입력해 주세요.',
		                equalTo  : '입력한 비밀번호가 일치하지 않습니다.'
					}
				},
				invalidHandler: $.validateUtil.handler01,
				errorPlacement: $.validateUtil.tablePlacement
			}
		});
	};

	function _createForm() {
		let f = $('<form method="post" onsubmit="return false;"></form>');
		f.prop('id'  , options.formId);
		f.prop('name', options.formId);
		return f;
	};
	
	function _create() {
		_this.addClass(options.cls);
		// 단계표시
		_this.append( $.appUtils.getSteps(options) );
		// 폼객체 정의
		_form = _createForm();
		// OAUTH 인증 표시
		_form.append( _createOauth() );
		// 폼카드 정의
		_card = _createCard();
		// 기본정보 입력 표시
		_form.append( _card );
		// 폼객체 생성
		_this.append( _form );
		// 버튼표시
		_this.append( $.appUtils.getButtons(options.buttons, _functions) );
	};
	
	_create();
	return this;
};

// 회원가입 - 기업정보입력
//=============================================================================
$.fn.appJoinBiz = function ( args ) {

	let options = $.extend({
		mode       : MODE.INSERT,
		params     : {
			bzentySeCd : false, // 수정시 입력되는 정보
			bzentyNo   : false, // 수정시 입력되는 정보
			brno       : false, // 수정시 입력되는 정보
			userNo     : false, // 수정시 입력되는 정보
			userId     : false, // 수정시 입력되는 정보
			kdCd       : false, // 수정시 입력되는 정보
			rprsYn     : false, // 수정시 입력되는 정보
			kodataYn   : false, // 수정시 입력되는 정보
			matchYn    : false, // 수정시 입력되는 정보
			existYn    : false, // 수정시 입력되는 정보
		},
		stepCd     : 'FBIZ',
		joinCd     : CODE.JOIN_CD.USR,
		formId     : 'bizForm',
		cls        : 'pt-36px pt-md-72px',
		style      : 'max-width:650px;',
		boxCls     : 'post-details-2 mb-24px mx-auto',
		cardCls    : 'box mb-56px',
		formCls    : 'round-16px shadow-box-1 mx-auto',
		groupCls   : 'form-box-of-member p-24px',
		boxEl      : 'app-biz-box',
		infoEl     : 'app-bizinfo-box',
		addtEl     : 'app-bizaddt-box',
		moreEl     : 'app-bizmore-box',
		buttons    : [{
			data   : {oper:'PREV'},
			cls    : 'btn-combi-3 w-100 bs-xl',
			icon   : 'icon-arrow-left mr-4px',
			value  : '이전단계',
			click  : 'doClick'
		},{
			data   : {oper:'NEXT'},
			cls    : 'btn-primary w-100 bs-xl',
			icon   : 'icon-arrow-right ml-4px',
			value  : '다음단계',
			click  : 'doClick'
		}]
	}, args);
					
	let _this      = $(this);
	let _form      = false;
	let _formData  = {};
	let _entData   = {};
	let _infoCard  = false;
	let _addtCard  = false;
	let _moreCard  = false;
	let _validator = {
		debug         : false,
		onfocusout    : false,
		onsubmit      : false,
		invalidHandler: validateHandler,
		errorPlacement: validatePlacement,
		rules         : {},
		messages      : {}
	};
	let _entfiles  = {};
	
	let _functions = {

		// 이전단계, 다음단계 버튼 클릭
	    //--------------------------------------------------------//
		doClick: function() {
			let c = $(this).data('oper');
			if (c == 'PREV') {
		        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
					params : {stepCd: 'FUSR', joinCd: options.joinCd}
				});
			}
			else if (c == 'NEXT') {
				_functions.doSave();
			}
			return false;
		},
		// 기본항목 읽기전용 활성/해제
	    //--------------------------------------------------------//
		doReadonly: function( onoff, emptyCheck ) {
			let cls     = 'app-readonly';
			let columns = [
				'bzentyNm',
				'rprsvNm',
				'fndnYmd',
				'rprsTelno',
				'bzentySeCd'
			];
			if (emptyCheck) {
				$.each(columns, function(i,c) {
					if ($.commUtil.empty($('#'+c).val()))
						$.formUtil.setReadonly(_form, false, [c], cls);
					else
						$.formUtil.setReadonly(_form, onoff, [c], cls);
				});
			}
			else {
				$.formUtil.setReadonly(_form, onoff, columns, cls);
			}
			if (!onoff) {
				// 날짜입력 마스크(YYYY-MM-DD)
				_form.find('[name="fndnYmd"]').inputmask("9999-99-99");
			}
		}, 
		// 업체정보 조회
	    //--------------------------------------------------------//
		getEntByBrno: function( brno ) {
			const ret = $.ajaxUtil.ajaxDefault(getUrl('/com/user/getEnt.do'), {brno: brno});
			return (ret && ret.Data ? ret.Data : false);
		},
		// 업체정보 조회
	    //--------------------------------------------------------//
		getEntByNo: function( bzentyNo ) {
			const ret = $.ajaxUtil.ajaxDefault(getUrl('/com/user/getEntByNo.do'), {bzentyNo: bzentyNo});
			return (ret && ret.Data ? ret.Data : {});
		},
		// 사업자등록번호 조회 팝업창에서 호출하는 함수
	    //--------------------------------------------------------//
		doCallback: function( data ) {
			
			if (!data)
				return;
			const brno   = data['bzno'];
			// 업체정보 조회
			const result = _functions.getEntByBrno(brno);
			// 기본정보 정의
			_formData = {
				brno       : $.formatUtil.toBizNo(brno),
				kdCd       : '',
				bzentyNo   : '',
				rprsYn     : 'Y',
				existYn    : 'N',
				kodataYn   : 'N',
				matchYn    : 'N'
			};
			// 기본항목 읽기전용 설정
			let readonly = false;
			// KODATA 신규등록 여부
			let kodata   = false;

			// 읽기전용 해제
			_functions.doReadonly(false);
			
			// 신규등록인 경우
			if (data.mode == MODE.INSERT) {
				// 업체정보가 있을 경우
				if (result) {
					$('<div></div>').appMessage({
						type: 'alert',
						closable: false,
						message: [
							'한국평가데이터에 등록된 정보가 없습니다.',
							'먼저 가입한 멤버가 입력한 기업정보를 자동으로 불러옵니다.'
						].join('<br>'),
						clickClose: function() {
							$.extend(true, _formData, {
								bzentyNm   : result['bzentyNm'  ],
								rprsvNm    : result['rprsvNm'   ],
								rmrk4      : '',
								fndnYmd    : $.formatUtil.toDashDate(result['fndnYmd'  ]),
								rprsTelno  : $.formatUtil.toPhone   (result['rprsTelno']),
								bzentySeCd : result['bzentySeCd'],
								bzentyNo   : result['bzentyNo'  ],
								rprsYn     : result['rprsYn'    ],
								existYn    : 'Y',
								matchYn    : 'N'
							});
							// 읽기전용 활성
							readonly = true;
							$.formUtil.toForm(_formData, _form);
							_functions.doReadonly(readonly, true);
							_functions.doChange();
						}
					});
				}
				// 업체정보가 없을 경우
				else {
					// 빈값 셋팅
					$.extend(true, _formData, {
						bzentyNm   : '',
						rprsvNm    : '',
						rmrk4      : '',
						fndnYmd    : '',
						rprsTelno  : '',
						bzentyNo   : '',
					});
					// 읽기전용 해제
					readonly = false;
					$.formUtil.toForm(_formData, _form);
					_functions.doReadonly(readonly, true);
					_functions.doChange();
				}
			}
			else {
				// KODATA 회원정보 일치여부 조회
				const match = $.ajaxUtil.ajaxDefault(getUrl('/com/user/matchKodata.do'), {});
				//console.log(data['bzno'     ]); // 사업자등록번호
				//console.log(data['enpNm'    ]); // 기업체명
				//console.log(data['reperName']); // 대표자명
				//console.log(data['estbDt'   ]); // 설립일자
				//console.log(data['telNo'    ]); // 대표번호
				//console.log(data['kedcd'    ]); // KODATA번호
				// 선택값 셋팅
				$.extend(true, _formData, {
					kdCd       : data['kedcd'    ],
					bzentyNm   : data['enpNm'    ],
					rprsvNm    : data['reperName'],
					rmrk4      : data['reperName'],
					fndnYmd    : $.formatUtil.toDashDate(data['estbDt']),
					rprsTelno  : $.formatUtil.toPhone   (data['telNo' ]),
					kodataYn   : 'Y',
					matchYn    : (match ? 'Y': 'N')
				});
				// 읽기전용 활성
				readonly = true;
				// 업체정보가 있을 경우
				if (result) {
					$.extend(true, _formData, {
						bzentySeCd : result['bzentySeCd'],
						bzentyNo   : result['bzentyNo'  ],
						rprsYn     : result['rprsYn'    ],
						existYn    : 'Y'
					});
				}
				// 업체정보가 없을 경우
				else {
					$.extend(true, _formData, {
						bzentyNo   : '' ,
						rprsYn     : 'Y',
						existYn    : 'N'
					});
					kodata = true;
				}
				$.formUtil.toForm(_formData, _form);
				_functions.doReadonly(readonly, true);
				if (kodata) {
					// KODATA 신규등록인 경우 유형 선택가능
					$.formUtil.setReadonly(_form, false, ['bzentySeCd']);
				}
				_functions.doChange();
			}
		},
		// 업체유형 변경시 처리
		doChange: function() {
			let seCd = $('#bzentySeCd').val();
			// 설명글 표시
			_createInfoComment( seCd );
			// 기업부가정보 표시
			_createAddtCard( seCd );
			// 기업상세정보 표시
			_createMoreCard( seCd );
		},

		// 사업자등록번호 조회
	    //--------------------------------------------------------//
		doSearch: function() {
			// 사업자등록번호 조회팝업 오픈 (app_popup.js)
			popup.openBizNo({
				callback: _functions.doCallback
			});
			return false;
		},

	    // 기업정보 필수검증
	    //--------------------------------------------------------//
		doValidate: function() {
			// 검증룰 정의
			_form.validate(_validator);
			// 저장전 필터링
			_infoCard.doFiltering();
	        // VALIDATION 기능 활성화
	        if (_form.validate().settings)
	            _form.validate().settings.ignore = false;

	        // FORM VALIDATION
			let valid = _form.valid();
	        if (!valid)
	            return false;
			// FILE VALIDATION
			$.each(_entfiles, function(key, val) {
				let obj = $('#'+val);
				if (!obj.appEntFile('validate', {
						isAlert: true, 
						isLimit: true, 
						isExt  : true
					})) {
					valid = false;
		            return false;
				}
			});
			return valid;
		},

	    // 필수검증 및 저장하기
	    //--------------------------------------------------------//
		doSave: function() {
			
			if (!_functions.doValidate())
				return false;
				
	    	$.commMsg.confirm("저장하시겠습니까?", function() {
				// 저장전 금액 포맷제거
				let amt = $.formUtil.getValue(_form, 'invtHopeAmt');
				$.formUtil.setValue(_form, 'invtHopeAmt', $.commUtil.nvlInt(amt));
				// 등록폼을 AJAX로 저장처리
				_form.ajaxForm({
	                url     : getUrl('/com/user/saveJoinEnt.do'), 
	                enctype : 'multipart/form-data',
		            // 오류시 처리로직
		            error   : $.ajaxUtil.error,
		            // 저장후 처리로직
		            success : function(ret) {
						$.commMsg.success(ret, false, function() {
					        $.formUtil.submitForm(getUrl('/com/user/openJoin.do'),{
								params : {
									stepCd: 'DONE', 
									joinCd: options.joinCd,
									userId: ret.userId
								}
							});
						});
		            }
	            }).submit();
	    	});
	        return false;				
		}
	};

	function _createInfoCard() {
		
		let items = [];
		if (options.mode == MODE.INSERT) {
			items = [
				{cls:'col-12 pb-24px', must:true, name:'brno', label:'사업자번호', error: true, wrapCls: 'input-btn', 
					input:[
						{type:'text'  , id:'brno', name:'brno', readonly: true, cls:'app-readonly', placeholder:'사업자번호 조회를 실행해 주세요.'},
						{type:'button', value:'조회', cls:'btn-black bs-l', click: _functions['doSearch'], colCls:'flex-b68px'}],
					comment: {icon:true, text:'본사 사업자번호 조회를 실행해 주세요.'}
				},
				{cls:'col-12 pb-24px', must:true, name:'bzentyNm'    , label:'회사명'    , error: true,input:{type:'text', readonly: true,id:'bzentyNm' , name:'bzentyNm' , maxlength: 50, placeholder:'회사명'}},
				{cls:'col-12 pb-24px', must:true, name:'rprsvNm'     , label:'대표자'    , error: true,
					input:{type:'text', readonly: true,id:'rprsvNm'  , name:'rprsvNm'  , maxlength: 50, placeholder:'대표자'},
					hidden:{type:'hidden', id:'rmrk4', name:'rmrk4'}
				},
				{cls:'col-12 pb-24px', must:true, name:'fndnYmd'     , label:'설립일'    , error: true,input:{type:'text', readonly: true,id:'fndnYmd'  , name:'fndnYmd'  , maxlength: 10, placeholder:'설립일'}},
				{cls:'col-12 pb-24px', must:true, name:'rprsTelno'   , label:'대표번호'  , error: true,input:{type:'text', readonly: true,id:'rprsTelno', name:'rprsTelno', maxlength: 14, placeholder:'대표번호', phone: true}},
			];
			$.extend(true, _validator, {
				rules: {
					brno         : {required: true},
					bzentyNm     : {required: true},
					rprsvNm      : {required: true},
					fndnYmd      : {required: true, dateHyphen: true},
					rprsTelno    : {required: true, phone: true},
				},
				messages: {
					brno            : {required: '사업자번호 조회를 실행해 주세요.'},
					bzentyNm        : {required: '회사명을 입력해 주세요.'},
					rprsvNm         : {required: '대표자를 입력해 주세요.'},
					fndnYmd         : {
						required    : '설립일을 입력해 주세요.', 
						dateHyphen  : '설립일을 형식에 맞게 입력해 주세요. (예.2023-01-01)'
					},
					rprsTelno       : {
						required    : '대표번호를 입력해 주세요.', 
						phone       : '대표번호를 형식에 맞게 입력해 주세요.'
					},
				}
			});
		}
		items.push(
			{cls:'col-12 pb-24px', must:true, name:'bzentySeCd'  , label:'기업유형'  , error: true,
				input:{
					type   :'select',
					id     :'bzentySeCd', 
					name   :'bzentySeCd',
					options:{
						params: {upCdId: CODE.BZENTY_SE.code}, 
						filter: function(o) { 
							return (o.code <= '30'); 
						},
						change: _functions.doChange,
						callback: function(cmp) {
							if (options.mode == MODE.UPDATE) {
								cmp.setValue(options.params.bzentySeCd);
							}
						}
					}
				},
				comment: {icon:true, text:'투자자 및 유관기관 회원은 관리자 승인 후 해당 메뉴 이용이 가능합니다.'}
			}
		);
		$.extend(true, _validator, {
			rules:    {bzentySeCd: {required: true}},
			messages: {bzentySeCd: {required: '기업유형을 선택해 주세요.'}}
		});

		// 정보카드 정의
		_infoCard = $('<div></div>').appFormCard({
			// 추가 CLS
			//cls: 'mb-32px',
			// 박스 CLS
			boxCls: options.formCls,
			// 그룹 CLS
			groupCls: options.groupCls,
			// 빈값텍스트
			defaultValue: STRING.NOINFO,
			// 제목
			title: false,
			// 폼객체
			form: _form,
			// 칼럼목록
			items: [{items: items}]
		});
		
		let info = $('<div></div>');
		info.addClass(options.cardCls);
		info.addClass(options.infoEl);
		info.append( $.appUtils.getTitle({title: '기업기본정보 입력', icon: 'icon-clipboard-list'}));
		info.append(_infoCard);
		
		_this.find('.'+options.boxEl+'>.'+options.infoEl).remove();
		_this.find('.'+options.boxEl).append(info);
		
		if (options.mode == MODE.INSERT) {
			// 읽기전용 활성
			_functions.doReadonly(true);
		}
		else {
			// 수정인 경우 업체정보 조회
			_entData = _functions.getEntByNo(options.params.bzentyNo);
			// 기본정보 정의
			_formData = {
				brno       : options.params.brno       ,
				kdCd       : options.params.kdCd       ,
				bzentyNo   : options.params.bzentyNo   ,
				kodataYn   : options.params.kodataYn   ,
				rprsYn     : options.params.rprsYn     ,
				existYn    : options.params.existYn    ,
				matchYn    : options.params.matchYn    ,
			};
			$.formUtil.toForm(_formData, _form);
			
			_functions.doChange();
		}
	};

	// 기업기본정보 설명글 추가
	function _createInfoComment( seCd ) {
		
		_infoCard.find('.app-col-comment').remove();

		// 대표계정이 아닌경우 SKIP
		if (_formData.rprsYn != 'Y')
			return;
		
		let comment = false;
		// 경영체인 경우
		if (seCd == CODE.BZENTY_SE.ENT) {
			comment = $.appUtils.getComment([
				'기업회원은 관리자 승인 후 기업회원 메뉴 이용이 가능합니다.',
				'승인 전에는 기본 메뉴 이용이 가능합니다. 시일 내로 담당자 승인 처리 도와드리겠습니다.'
			].join('<br>'));
		}
		// 투자자,유관기관인 경우
		else {
			comment = $.appUtils.getComment([
				'투자자 및 유관기관 회원은 관리자 승인 후 투자자 및 유관기관 메뉴 이용이 가능합니다.',
				'승인 전에는 기본 메뉴 이용이 가능합니다. 시일 내로 담당자 승인 처리 도와드리겠습니다.'
			].join('<br>'));
		}
		if (comment) {
			let col = $('<div class="col-12 app-col-comment"></div>').append(comment);
			_infoCard.find('.app-row-items').append(col);
		}
	};
	
	// 기업부가정보
	function _createAddtCard( seCd ) {

		_addtCard = false;
		_this.find('.'+options.boxEl+'>.'+options.addtEl).remove();

		delete _validator.rules   ['groupCd'];
		delete _validator.messages['groupCd'];
		delete _entfiles['dlgtFile'];
		delete _entfiles['bregFile']

		// 대표계정이면서 
		// KODATA가 일치하는 경우 
		// 부가정보 표시안함 
		if (_formData.rprsYn  == 'Y' &&
			_formData.matchYn == 'Y')
			return;

		let items = [];
		// 대표계정이면
		if (_formData.rprsYn == 'Y') {
			// KODATA가 일치하지 않는 경우
			if (_formData.matchYn != 'Y') {
				// 경영체인 경우
				if (seCd == CODE.BZENTY_SE.ENT) {
					items.push({
						cls      : 'col-12 pb-24px', 
						must     : true, 
						name     : 'dlgtFile', 
						label    : '위임장',
						comment  : {icon:true, text:'100MB 이내 PDF파일 업로드 가능'},
						inputHtml: function() {
							return $('<div id="appDlgtFile"></div>').appEntFile({
								mode        : options.mode,
								label       : false,
								title       : '위임장',
								formbtn     : true,
								initData    : {
						            fileType: CODE.FILE_TYPE.ENT,
						            needYn  : 'Y',
									docuCd  : CODE.FILE_SE.DLGT,
						            docuNo  : _entData['bzentyNo']
								},
								extensions  : ['pdf']
							}).appEntFile('init');
						}
					});
					_entfiles['dlgtFile'] = 'appDlgtFile';
				}
				items.push({
					cls      : 'col-12 pb-24px', 
					must     : true, 
					name     : 'bregFile', 
					label    : '사업자등록증',
					comment  : {icon:true, text:'100MB 이내 PDF파일 업로드 가능'},
					inputHtml: function() {
						return $('<div id="appBregFile"></div>').appEntFile({
							mode        : options.mode,
							label       : false,
							title       : '사업자등록증',
							initData    : {
					            fileType: CODE.FILE_TYPE.ENT,
					            needYn  : 'Y',
								docuCd  : CODE.FILE_SE.BREG,
					            docuNo  : _entData['bzentyNo']
							},
							extensions  : ['pdf']
						}).appEntFile('init');
					}
				});
				_entfiles['bregFile'] = 'appBregFile';
			}
			// 경영체인 경우
			if (seCd == CODE.BZENTY_SE.ENT) {
				items.push({
					cls      : 'col-12', 
					inputHtml: function() {
						return $.appUtils.getComment([
							'기업회원은 관리자 승인 후 기업회원 메뉴 이용이 가능합니다.',
							'승인 전에는 기본 메뉴 이용이 가능합니다. 시일 내로 담당자 승인 처리 도와드리겠습니다.'
						].join('<br>'));
					}
				});
			}
			// 투자자,유관기관인 경우
			else {
				items.push({
					cls      : 'col-12', 
					inputHtml: function() {
						return $.appUtils.getComment([
							'투자자 및 유관기관 회원은 관리자 승인 후 투자자 및 유관기관 메뉴 이용이 가능합니다.',
							'승인 전에는 기본 메뉴 이용이 가능합니다. 시일 내로 담당자 승인 처리 도와드리겠습니다.'
						].join('<br>'));
					}
				});
			}
		}
		// 일반계정이면
		else {
			items.push({
				cls     : 'col-12 pb-24px', 
				must    : true, 
				error   : true,
				name    : 'groupCd', 
				label   : '그룹코드', 
				comment : {icon:true, text:'소속기관 대표계정이 있습니다. 대표계정에게 그룹코드를 공유받아 입력해 주세요'},
				input   : {
					type       : 'text', 
					id         : 'groupCd', 
					name       : 'groupCd', 
					placeholder: '그룹코드'
				}
			});
			_validator.rules   ['groupCd'] = {required: true};
			_validator.messages['groupCd'] = {required: '대표계정에게 그룹코드를 공유받아 입력해 주세요.'};
		}

		// 정보카드 정의
		_addtCard = $('<div></div>').appFormCard({
			// 박스 CLS
			boxCls: options.formCls,
			// 그룹 CLS
			groupCls: options.groupCls,
			// 빈값텍스트
			defaultValue: STRING.NOINFO,
			// 제목
			title: false,
			// 폼객체
			form: _form,
			// 칼럼목록
			items: [{items: items}]
		});
		
		let addt = $('<div></div>');
		addt.addClass(options.cardCls);
		addt.addClass(options.addtEl);
		addt.append( $.appUtils.getTitle({title: '기업부가정보 입력', icon: 'icon-clipboard-list'}));
		addt.append(_addtCard);
		_this.find('.'+options.boxEl).append(addt);

	};
	
	// 기업상세정보 생성	
	function _createMoreCard( seCd ) {
		
		_moreCard = false;
		_this.find('.'+options.boxEl+'>.'+options.moreEl).remove();
		
		delete _validator.rules   ['invtFldList'];
		delete _validator.rules   ['bizFldList' ];
		delete _validator.rules   ['invtHopeAmt'];
		delete _validator.messages['invtFldList'];
		delete _validator.messages['bizFldList' ];
		delete _validator.messages['invtHopeAmt'];
		delete _entfiles['thmbFile'];

		// 대표계정이 아니거나 
		// 경영체가 아닌 경우
		// 상세정보 표시안함
		if (_formData.rprsYn != 'Y' ||
			seCd != CODE.BZENTY_SE.ENT)
			return;
		
		_moreCard = $('<div></div>').appSmartSearch({
			mode     : MODE.JOIN,
			form     : _form,
			items    : [[{
				label           : '썸네일',
				id              : 'appThmbFile',
				type            : 'appFile', 
				typeKey         : 'appEntFile',
				inputOptions    : {
					mode        : options.mode,
					label       : false,
					title       : '썸네일 이미지',
					maxCount    : 3,
					multiple    : true,
					initData    : {
			            fileType: CODE.FILE_TYPE.ENT,
						//needYn  : 'Y',
						docuCd  : CODE.FILE_SE.IMGE,
			            docuNo  : _entData['bzentyNo']
					},
					message     : 'JPG,PNG,BMP만 가능, 총 100MB 이내 3개까지 등록 가능',
					messageCls  : 'fs-10px',
					messageColor: 'text-primary',
					extensions  : ['jpg','bmp','png'],
					extra       : {type: 'radio', name: 'rprsImageYn', label: '대표이미지'}
				},
			}],[{
				must            : true,
				label           : '투자분야',
				name            : 'invtFldList',
				type            : 'appSelectBox', 
				info            : {
					click: function() {
						// 투자분야선택 팝업오픈(app_popup.js)
						popup.openInvestField({
							// 검색버튼 명칭
							btnSearchName: '등록',
							// 검색버튼 클릭
							onClickSearch: function( values ) {
								$.formUtil.setValue(_form, "invtFldList", values);
							}
						});
					}
				}, 
				inputOptions    : {
					separator   : ',',
					colCls      : 'col',
					form        : 'checkbox', 
					name        : 'invtFldList',
					value       : _entData['invtFldCode'],
					url         : getUrl('/com/common/getInvtRlmComboCode.do'),
					init        : {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click       : bindUnlockAll
				},
			}],[{
				must            : true,
				label           : '사업분야',
				name            : 'bizFldList',
				type            : 'appSelectBox', 
				info            : {
					click: function() {
						// 사업분야 팝업오픈(app_popup.js)
						popup.openBizField({
							// 검색버튼 명칭
							btnSearchName: '등록',
							// 검색버튼 클릭
							onClickSearch: function( values ) {
								$.formUtil.setValue(_form, "bizFldList", values);
							}
						});
					}
				},
				inputOptions    : {
					separator   : ',',
					colCls      : 'col',
					form        : 'checkbox', 
					name        : 'bizFldList',
					value       : _entData['bizFldCode'],
					params      : {upCdId: CODE.BIZ_RLM.code},
					init        : {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click       : bindUnlockAll
				},
			}],[{ // 투자희망금액
				must            : true,
				label           : '투자희망금액',
				name            : 'invtHopeAmt',
				type            : 'appInputBox', 
				inputOptions    : {
					area        : true,
					money       : true,
					colCls      : 'col',
					id          : 'invtHopeAmt', 
					name        : 'invtHopeAmt',
					value       : _entData['invtHopeAmt'],
					placeholder : '투자희망금액',
					maxlength   : 6,
					wrap        : {postfix: {text:'백만원',cls:'align-self-center flex-b50px'}}
				},
			}]]
		});
		let more = $('<div></div>');
		more.addClass(options.cardCls);
		more.addClass(options.moreEl);
		more.append( $.appUtils.getTitle({title: '기업상세정보 입력', icon: 'icon-clipboard-list'}));
		more.append( $.appUtils.getComment([
			'기업상세정보를 입력하면 투자자에게 내 기업정보를 노출하여 투자유치 가능성을 높일 수 있어요.',
			'(내 경영체 정보는 투자자 및 유관기관만 열람 가능해요. 다른 경영체는 볼 수 없어요)'
		].join('<br>')));
		more.append(_moreCard);
		
		_this.find('.'+options.boxEl).append(more);
		_validator.rules   ['invtFldList'] = {required: true};
		_validator.rules   ['bizFldList' ] = {required: true};
		_validator.rules   ['invtHopeAmt'] = {required: true};
		_validator.messages['invtFldList'] = {required: '투자분야를 하나 이상 선택해 주세요.'};
		_validator.messages['bizFldList' ] = {required: '사업분야를 하나 이상 선택해 주세요.'};
		_validator.messages['invtHopeAmt'] = {required: '투자희망금액을 입력해 주세요.'};
		_entfiles['thmbFile'] = 'appThmbFile';
	};

	function _getCreateForm( formId ) {
		let f = $('<form id="'+formId+'" name="'+formId+'" method="post" onsubmit="return false;"></form>');
		f.append('<input type="hidden" id="rprsYn"   name="rprsYn"   value="N"/>');
		f.append('<input type="hidden" id="existYn"  name="existYn"  value="N"/>');
		f.append('<input type="hidden" id="kodataYn" name="kodataYn" value="N"/>');
		f.append('<input type="hidden" id="bzentyNo" name="bzentyNo" value="" />');
		f.append('<input type="hidden" id="kdCd"     name="kdCd"     value="" />');
		return f;
	};

	function _getCreateGroup() {
		let dom = $('<div></div>');
		dom.addClass(options.boxCls);
		dom.addClass(options.boxEl);
		dom.prop('style', options.style);
		return dom;
	};

	function _create() {
		
		_this.addClass(options.cls);
		
		if (options.mode == MODE.INSERT) {
			// 단계표시
			_this.append( $.appUtils.getSteps(options) );
		}
		// 폼생성
		_this.append( _getCreateForm( options.formId ) );
		// 폼객체 정의
		_form = $('#'+options.formId);
		// 카드영역생성
		_this.find('form').append( _getCreateGroup() );

		if (options.mode == MODE.INSERT) {
			// 버튼표시
			_this.append( $.appUtils.getButtons(options.buttons, _functions) );
		}
		
		// 기본정보 표시
		_createInfoCard();
	};
	
	this.doSaveUpdate = function( callback ) {
		
		// 검증룰 정의
		_form.validate(_validator);
        // VALIDATION 기능 활성화
        if (_form.validate().settings)
            _form.validate().settings.ignore = false;

        // FORM VALIDATION
		let valid = _form.valid();
        if (!valid)
            return false;

		// FILE VALIDATION
		$.each(_entfiles, function(key, val) {
			let obj = $('#'+val);
			if (!obj.appEntFile('validate', {
					isAlert: true, 
					isLimit: true, 
					isExt  : true
				})) {
				valid = false;
	            return false;
			}
		});
		if (!valid)
			return false;
			
    	$.commMsg.confirm("재신청하시겠습니까?", function() {
			// 저장전 금액 포맷제거
			let amt = $.formUtil.getValue(_form, 'invtHopeAmt');
			$.formUtil.setValue(_form, 'invtHopeAmt', $.commUtil.nvlInt(amt));
			// 등록폼을 AJAX로 저장처리
			_form.ajaxForm({
                url     : getUrl('/com/user/saveEntCmpl.do'), 
                enctype : 'multipart/form-data',
	            // 오류시 처리로직
	            error   : $.ajaxUtil.error,
	            // 저장후 처리로직
	            success : function(ret) {
                    $.ajaxUtil.success(ret, function() {
						$.commMsg.alert([
							'성공적으로 재신청되었습니다.',
							'시일 내로 담당자 승인 처리 도와드리겠습니다.'
						].join('<br>'), callback);
                    });
	            }
            }).submit();
    	});
        return false;				
	};

	_create();
	return this;
};

