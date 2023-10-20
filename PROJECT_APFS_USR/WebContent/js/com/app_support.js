/**
*******************************************************************************
*** 파일명    : app_support.js
*** 설명      : 지원서비스 - 지원사업신청 관련 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.05.22              LSH
*******************************************************************************
**/

$.sprtUtils = {
	// 투자유치 전 지원 버튼
	getBfreButton: function(params) {
		return {
			cls:   'btn-primary w-100 bs-xl',
			icon:  'icon-edit',
			value: '투자유치 전 지원',
			click: function() {
				$.formUtil.submitForm(getUrl("/usr/support/support/formSprt.do"), {params: params});
			}
		};
	},
	// 투자유치 후 지원 버튼
	getAftrButton: function(params) {
		return {
			cls:   'btn-primary w-100 bs-xl',
			icon:  'icon-edit',
			value: '투자유치 후 지원',
			click: function() {
				$.formUtil.submitForm(getUrl("/usr/support/support/formSprt.do"), {params: params});
			}
		};
	},
	// 농식품 크라우드펀딩 지원 버튼
	getCrwdButton: function(params) {
		return {
			cls:   'btn-primary w-100 bs-xl',
			icon:  'icon-edit',
			value: '농식품 크라우드펀딩 지원 신청',
			click: function() {
				$.formUtil.submitForm(getUrl("/usr/support/support/formSprt.do"), {params: params});
			}
		};
	},
	// 크라우드 펀딩 전용관 이동 버튼
	getAgroButton: function() {
		return {
			cls:   'bs-xl btn-combi-3 w-100',
			icon:  'icon-send',
			value: '크라우드 펀딩 전용관 이동',
			click: function() {
				goNewUrl(CURL.AGRO_CROWD);
			}
		};
	},
	// 취소버튼
	getCnclButton: function( formId ) {
		return {
			cls:   'btn-combi-3 w-100 bs-xl',
			icon:  'icon-times',
			value: '취소',
			click: function() {
		   	    $.formUtil.submitForm(getUrl("/usr/support/support/openSprt.do"), {formId : formId});
			}
		};
	},
	// 이전버튼
	getPrevButton: function( actionCallback ) {
		return {
			cls:   'btn-primary w-100 bs-xl',
			icon:  'icon-arrow-left',
			value: '이전',
			code:  'PREV',
			click: actionCallback
		};
	},
	// 다음버튼
	getNextButton: function( actionCallback ) {
		return {
			cls:   'btn-primary w-100 bs-xl',
			icon:  'icon-arrow-right',
			value: '다음',
			code:  'NEXT',
			click: actionCallback
		};		
	},
	// 임시저장버튼
	getSaveButton: function( actionCallback ) {
		return {
			cls:   'bs-xl btn-black-ouline w-100',
			icon:  'icon-desktop',
			value: '임시저장',
			code:  'TEMP',
			click: actionCallback
		};
	},
	// 제출버튼
	getSbmtButton: function( actionCallback ) {
		return {
			cls:   'bs-xl btn-deep-green w-100',
			icon:  'icon-floppy-disk',
			value: '제출',
			code:  'SBMT',
			click: actionCallback
		};
	},
	// 메인이동버튼
	getMainButton: function() {
		return {
			cls:   'bs-xl btn-combi-3 w-100',
			icon:  'icon-home-roof',
			value: '메인화면으로 돌아가기',
			click: function() {
				$.bizUtils.goHome();
			}
		};
	},
	// 마이페이지버튼
	getMypgButton: function() {
		return {
			cls:   'btn-primary w-100 bs-xl',
			icon:  'icon-user',
			value: '마이페이지',
			click: function() {
				goUrl( getUrl('/usr/mypage/support/openSprt.do') );
			}
		};
	},
};

// 지원종류 구분탭 생성
//=============================================================================
$.fn.appSprtTab = function( params ) {
	
	let code = params.sprtSeCd;
	return $(this).appTabs({
		url:    getUrl('/com/common/getComboCode.do'),
		params: {upCdId: CODE.SPRT_APLY_SE.code},
		value:  params.sprtSeCd,
		icons: [
			'icon-presentation-pen',
			'icon-briefcase-check',
			'icon-cloud-connection'
		],
		// 탭선택 처리
		select: function(value) {
			if (value != code) {
				$.formUtil.submitForm(
					getUrl('/usr/support/support/openSprt.do'), {
						params: {
							sprtSeCd: value,
							stepNo: 1
						}
					}
				);
				return false;
			}
			code = '';
		}
	});
};

// 지원내용 로드
//=============================================================================
$.fn.appSprtContents = function( params ) {
	let url = getUrl('/html/content'+params.sprtSeCd+'.html');
	let ret = $.ajaxUtil.ajaxHtml(url, {});
	$(this).html(ret);
	return this;
};

// 지원신청 버튼
//=============================================================================
$.fn.appSprtButtons = function( buttons, btnCls ) {
	
	function _createButton(b) {
		let btn = $('<button type="button"></button>');
		if (b.code)
			btn.data('code', b.code);
		btn.addClass(b.cls);
		btn.append('<i class="'+b.icon+'"></i>');
		btn.append(b.value);
		btn.click(b.click);
		return $('<div class="col"></div>').append(btn);
	};
	
	let row = $('<div class="row"></div>');
	
	$.each(buttons, function(i,btn) {
		if ($.type(btn) === 'array') {
			let r = $('<div class="row"></div>');
			if (i == 0)
				r.addClass('justify-content-start');
			else if (i == (buttons.length-1))
				r.addClass('justify-content-end');
			else
				r.addClass('justify-content-center');
			$.each(btn, function(j,bitem) {
				r.append( _createButton(bitem) );
			});
			row.append( $('<div class="col"></div>').append(r) );
		}
		else {
			row.append( _createButton(btn) );
		}
	});
	if (btnCls)
		$(this).addClass(btnCls);

	$(this).html('');
	$(this).append(row);
	return this;
};

// 지원신청 버튼
//=============================================================================
$.fn.appSprtAplyButtons = function( params ) {
	let code = params.sprtSeCd;
	let bzyn = params.bizYn;
	let args = {
		sprtSeCd: code,
		stepNo  : 1
	};
	let buttons = [];
	if      (code == CODE.SPRT_APLY_SE.BEFORE)
		buttons.push($.sprtUtils.getBfreButton(args));
	else if (code == CODE.SPRT_APLY_SE.AFTER) {
		if (bzyn == 'Y')
			buttons.push($.sprtUtils.getAftrButton(args));
	}
	else if (code == CODE.SPRT_APLY_SE.CROWD) {
		buttons.push($.sprtUtils.getAgroButton());
		buttons.push($.sprtUtils.getCrwdButton(args));
	}
	$(this).appSprtButtons(buttons);
	return this;
};

// 신청단계박스
//=============================================================================
$.fn.appSprtStepBox = function( args ) {
	
	let thisElm  = $(this);
	let thisStep = false;

	function _create( params ) {

		let prgrmNo  = params.prgrmNo;
		let sprtSeCd = params.sprtSeCd; 
		let stepNo   = params.stepNo;
		let len = 0;
		let row = $('<div class="row"></div>');
		let idx = parseInt(stepNo);
		
		$.each(CODES.SPRT_STEP_LIST, function(i, step) {
			
			if (step.filter && 
				!step.filter( sprtSeCd, prgrmNo ))
				return true;
			
			
			let col = $('<div class="col app-step-item"></div>');
			col.append('<i class="'+step.icon+'"></i>');
			col.append('<p class="txt1">STEP '+(len+1)+'</p>');
			col.append('<span class="txt2">'+step.text+'</span>');
			col.data('code', step.code);
			col.data('step', (len+1));
			if (len == (idx-1))
				thisStep = $.extend({value: stepNo}, step);
			
			row.append(col);
			len++;
		});
		thisElm.html('');
		thisElm.append(row);
		
		// 활성화처리
		thisElm.addClass("sprt-step-box-"+len);
		thisElm.addClass("mb-40px");
		thisElm.addClass("active-"+idx);
		for (var i = 0; i < idx; i++) {
			let c = thisElm.find('.app-step-item').eq(i);
			c.addClass('active');
			c.find('i').addClass('F');
		}
	};
	_create( args );
	
	return thisStep;
};

// 지원사업 제목박스
//=============================================================================
$.fn.appSprtTitle = function( args ) {
	let thisElm  = $(this);
	thisElm.addClass("align-items-center d-flex fs-32px fw-600 justify-content-center mb-16px");
	thisElm.append('<img src="'+getUrl('/images/app/MaleCustomerService_'+args.prgrmNo+'.svg')+'" class="mr-8px">');
	thisElm.append(args.prgrmNm);
	return this;
};

// 약관동의 표시
//=============================================================================
$.fn.appSprtAgree = function( args ) {
	
	let options = $.extend({
		bizYn    :   'N', // 입력값
		formId   : false, // 입력값
		btnId    : false, // 입력값
		sprtSeCd : false, // 입력값
		sprtSeNm : false, // 입력값
		stepNo   : false, // 입력값
		stepCd   : 'ARGE',
		stepNm   : '약관 수집 동의',
		prgrmNo  : false, // 입력값
		prgrmNm  : false, // 입력값
	}, args);
	
	let _this   = $(this);
	let _checks = [];
	let _agree  = CONFIG.SPRT_AGREE;
	
	function _createHtml( params ) {
		
		let _name  = params.name;
		let _label = params.label;
		let _title = params.title;
		let _url   = params.url;
		
		_checks.push({name: _name, label: _label});
		
		// 약관내용
		let ret = $.ajaxUtil.ajaxHtml(_url, {});
		
		let txt = $('<div class="d-flex round-8px shadow-box-1"></div>');
		txt.addClass('app-agree');
		txt.append(ret);
		
		let box = $('<div class="box mb-16px"></div>');
		box.append('<div class="top border-0 pb-0"></div>');
		box.append('<div class="body"></div>');
		box.find('.top').append('<p class="text-icon-3 app-agree-title"></p>');
		box.find('.app-agree-title').append('<i class="icon-lightbulb cycle-ver-1"></i>');
		box.find('.app-agree-title').append(_title);
		box.find('.body').append(txt);
		
		let btn = $('<div class="bg-primary-t5 mb-16px p-16px round-8px text-box-1"></div>');
		btn.append('<div class="row"></div>');
		btn.find('.row').append('<div class="col text-end text-red"></div>');
		btn.find('.col').append('<div class="check-radio-box ver1"></div>');
		btn.find('.check-radio-box').append('<input type="checkbox" id="'+_name+'" name="'+_name+'">');
		btn.find('.check-radio-box').append('<label for="'+_name+'">'+_label+'</label>');
		
		_this.append(box);
		_this.append(btn);
	};
	
	// 초기화
	$(this).html('');

	if (_agree) {
		// 기본약관 생성
		_createHtml({
			url   : getUrl('/html/agree'+options.sprtSeCd+'.html'),
			name  : 'prvcClctAgreYn',
			label : '개인정보 수집·이용·및 제공동의',
			title : options.sprtSeNm + ' 위한 개인정보 수집·이용·및 제공동의'
		});
	}
	
	// 수수료지원인지 확인하는 함수
	// 컨설팅지원인지 확인하는 함수
	if (FILTER.isFeeCode(options.prgrmNo) ||
		FILTER.isConsultCode(options.prgrmNo)) {
		// 중기시스템 활용 동의서	
		_createHtml({
			url   : getUrl('/html/agree'+options.sprtSeCd+'02.html'),
			name  : 'unityMngSysAgreYn',
			label : '중소기업 지원사업 통합관리시스템 정보활용 동의',
			title : '중소기업 지원사업 통합관리시스템 정보 활용을 위한 동의서',
		});
	}

	// 저장처리
	let _doSave = function() {
		let saveUrl = getUrl('/usr/support/support/saveSprtAgree.do');
		let nextUrl = getUrl('/usr/support/support/formSprt.do');
		let prevUrl = getUrl('/usr/support/support/formSprt.do');
		let oper    = $(this).data('code');
		let step    = 1;
		let prm = {
			sprtSeCd: $('#sprtSeCd').val(),
			prgrmNo : $('#prgrmNo' ).val(),
			stepNo  : parseInt(options.stepNo)
		};
		if      (oper == 'PREV') prm['stepNo'] -= step;
		else if (oper == 'NEXT') prm['stepNo'] += step;
		
		if (oper == 'PREV') {
			$.formUtil.submitForm(prevUrl, {params: prm});
		}
		else {
			let valid = false;
			// 데이터 검증
			$.each(_checks, function(i, chk) {
				if ($('#'+chk['name']).is(':checked') === false) {
					$.commMsg.alert(chk['label']+'에 체크하셔야 합니다.');
					valid = false;
					return false;
				}
				prm[chk['name']] = 'Y';
				valid = true;
			});
			if (valid) {
				// 저장처리
				$.ajaxUtil.ajaxSave(saveUrl, JSON.stringify(prm), function() {
					$.formUtil.submitForm(nextUrl, {params: prm});
					return false;
				});
			}
		}
		return false;
	};
	
	let _buttons = [];
	_buttons.push($.sprtUtils.getCnclButton(options.formId));
	// 투자전지원이 아닌 경우
	if (options.sprtSeCd != CODE.SPRT_APLY_SE.BEFORE)
		_buttons.push($.sprtUtils.getPrevButton(_doSave));
		
	_buttons.push($.sprtUtils.getNextButton(_doSave));

	if (options.btnId) {
		// 이동버튼 표시
		$(options.btnId).appSprtButtons(_buttons);
	}
	return this;
};

// 지원사업선택
//=============================================================================
$.fn.appSprtChoose = function( args ) {
	
	let options = $.extend({
		bizYn    :   'N', // 입력값
		formId   : false, // 입력값
		btnId    : false, // 입력값
		sprtSeCd : false, // 입력값
		sprtSeNm : false, // 입력값
		stepNo   : false, // 입력값
		stepCd   : 'CHOS',
		stepNm   : '지원사업 선택',
		prgrmNo  : false, // 입력값
		prgrmNm  : false, // 입력값
	}, args);
	
	// 초기화
	$(this).html('');
	
	let url = getUrl('/usr/support/support/getListPrgrm.do');
	let params = {
		sprtSeCd: options.sprtSeCd 
	};
	let ret = $.ajaxUtil.ajaxDefault(url, params);
	
	if (ret && ret.length > 0) {
		let cnt   = ret.length;
		let cls   = (cnt > 3 ? 'col-3' : 'col');
		let step  = parseInt(options.stepNo);

		let row = $('<div class="row"></div>');
		$.each(ret, function(i,item) {
			let code   = item['code'  ];
			let text   = item['text'  ];
			let group  = item['group' ];
			let active = item['active'];
			let color  = FILTER.getPrgrmColor(group);
			let next   = step + 1;
			// 상담신청인 경우
			if (FILTER.isDscsnCode(code)) {
				next += (CONFIG.SPRT_AGREE ? 1 : 0);
				// 2023.08.31 TODO 상담신청 임시 제외
				return true;
			}
			let box = $('<div class="box shadow-box-2"></div>');
			box.data('next', next);
			box.data('code', code);

			//box.append('<img src="'+getUrl('/images/app/MaleCustomerService_'+code+'.svg')+'">');
			box.append('<p class="txt1 mt-8px">'+text+'</p>');
			box.append('<span class="cornerbg"></span>');
			box.find('.cornerbg').addClass(color);
			
			if (active) {
				box.click(function() {
					let prm = {
						sprtSeCd : options.sprtSeCd,
						stepNo   : $(this).data('next'),
						prgrmNo  : $(this).data('code')
					};
					let url = getUrl('/usr/support/support/formSprt.do');
					// 상담신청인 경우
					if (FILTER.isDscsnCode(prm['prgrmNo']))
						url = getUrl('/usr/support/support/formDscsn.do');
					$.formUtil.submitForm(url, {params: prm});
				});
			}
			else {
				box.addClass('disable');
			}
			row.append( $('<div class="'+cls+'"></div>').append(box) );
		});
		$(this).append(row);
	}
	$(this).addClass('categorybox');
	
	return this;
};

// 접수완료
//=============================================================================
$.fn.appSprtApply = function( args ) {

	let options = $.extend({
		bizYn    :   'N', // 입력값
		formId   : false, // 입력값
		btnId    : false, // 입력값
		sprtSeCd : false, // 입력값
		sprtSeNm : false, // 입력값
		stepNo   : false, // 입력값
		stepCd   : 'APLY',
		stepNm   : '접수완료',
		prgrmNo  : false, // 입력값
		prgrmNm  : false, // 입력값
	}, args);
	
	let _sprtSe  = options.sprtSeCd;
	let _prgrmNo = options.prgrmNo;
	let _title  = false;
	let _cmmnt = false;
	let _rmark = [
		'신청 내역은 마이페이지에서 확인할 수 있습니다.',
		'궁금하신점은 1:1문의 또는 FAQ를 확인해주시기 바랍니다.'
	].join('');
	
	if (_sprtSe == CODE.SPRT_APLY_SE.BEFORE) {
		_title = options.sprtSeNm+'사업 신청서가 접수되었습니다.';
		_cmmnt = '담당자 확인 후 유선안내 드릴 예정입니다.';
	}
	else {
		// 상담신청인 경우
		if (FILTER.isDscsnCode(_prgrmNo)) {
			_title = '상담신청이 접수되었습니다.';
			_cmmnt = '담당자 확인 후 유선안내 드릴 예정입니다.';
		}
		else {
			_title = options.prgrmNm+' 신청서가 접수되었습니다.';
			
			if (_sprtSe == CODE.SPRT_APLY_SE.CROWD) {
				_cmmnt = '신청서 제출 완료 대상부터 순차적으로 담당자 검토 진행되며,<br>';
				// 현장코칭인 경우
				if (FILTER.isCoatingCode(_prgrmNo))
					_cmmnt += '<em class="text-red">선착순 마감 시 현장코칭 지원이 불가</em>';
				else
					_cmmnt += '<em class="text-red">예산 소진 시 비용지원이 불가</em>';
				_cmmnt += '할 수 있습니다.';
			}
			else {
				_cmmnt = '순차적으로 담당자 심사 예정이며,<br>';
				_cmmnt += '참여기업 수 제한이 있거나 예산 소진된 사업의 경우 ';
				_cmmnt += '지원이 어려울 수 있습니다.';
			}
		}
	}

	// 초기화
	$(this).html('');
	$(this).addClass('shadow-box-1 pb-56px py-40px text-center mb-40px');
	$(this).append('<div class="app-imgarea"><img src="'+getUrl('/images/sub/searchicon2.png')+'"/></div>');	
	$(this).append('<p class="fs-24px fw-600 mb-16px app-apply-text"></p>');
	$(this).append('<p class="fs-20px fw-500 mb-16px app-apply-text"></p>');
	$(this).append('<span class="fs-15px fw-300 app-apply-text"></span>');
	
	$(this).find('.app-apply-text').eq(0).append(_title);
	$(this).find('.app-apply-text').eq(1).append(_cmmnt);
	$(this).find('.app-apply-text').eq(2).append(_rmark);
	
	if (options.btnId) {
		// 이동버튼 표시
		$(options.btnId).appSprtButtons([
			$.sprtUtils.getMainButton(), // 메인이동
			$.sprtUtils.getMypgButton()  // 마이페이지
		]);			
	}
	return this;
};

// 신청서 작성
//=============================================================================
$.fn.appSprtForm = function( args ) {
	
	let options = $.extend({
		bizYn    :   'N', // 입력값
		formId   : false, // 입력값
		btnId    : false, // 입력값
		sprtSeCd : false, // 입력값
		sprtSeNm : false, // 입력값
		stepNo   : false, // 입력값
		stepCd   : 'FORM',
		stepNm   : '신청서 작성',
		prgrmNo  : false, // 입력값
		prgrmNm  : false, // 입력값
	}, args);
	
	// 투자전 : 신청자정보/담당자정보(개인회원제외)
	// 투자후-지원사업 : 신청자정보/투자조합정보/담당자정보
	// 크라우드-컨설팅 : 신청자정보/펀딩시도/담당자정보(개인회원제외)
	// 크라우드-현장코칭/수수료 : 신청자정보/담당자정보(개인회원제외)
	let _bizYn   = options.bizYn;
	let _sprtSe  = options.sprtSeCd;
	let _prgrmNo = options.prgrmNo;
	let _cards   = {};
	let _options = {
	    infoYn  :  true, // 신청정보폼
	    fnnrYn  :  true, // 매출액항목
		prevYn  :  true, // 이전버튼여부
	    picgYn  : false, // 담당자정보폼
	    invtYn  : false, // 투자조합폼
	    linkYn  : false, // 펀딩시도항목
	};
	// 투자전지원 경우
	if (_sprtSe == CODE.SPRT_APLY_SE.BEFORE) _options.prevYn = CONFIG.SPRT_AGREE;
	// 크라우드펀딩인 경우
	if (_sprtSe == CODE.SPRT_APLY_SE.CROWD)	 _options.fnnrYn = false;
	// 컨설팅지원인 경오
	if (FILTER.isConsultCode(_prgrmNo))      _options.linkYn = true;
	// 업체회원인 경우
	if (_bizYn == 'Y')                       _options.picgYn = true;
	// 투자후지원인 경우
	if (_sprtSe == CODE.SPRT_APLY_SE.AFTER) {
		_options.invtYn = true;
		_options.infoYn = true;
	}
	// 경영체정보 조회
	let _doLoadEnt = function() {
		// 경영체정보 조회
		let url    = getUrl('/usr/invest/ent/getEnt.do');
		let params = {bzentyNo: $('#bzentyNo').val()};
		const ent = $.ajaxUtil.ajaxDefault(url, params);
		if (ent) {
			let data = ent['Data'];
			data['brdt'   ] = data['rprsvBrdt'   ];
			data['sexdstn'] = data['rprsvSexdstn'];
			// 2023.09.11 생년월일 필터처리
			data['brdt'] = FILTER.filterBrdt(data['brdt']);
			// 매출액 정보 조회
			url = getUrl('/usr/invest/ent/getListEntFnnr.do');
			const ret = $.ajaxUtil.ajaxDefault(url, $.extend(params, {
				irNo       : data['irNo'],
				dataSeCd   : CODE.DATA_SE.KODATA,
				fnnrSeCd   : CODE.FNNR_SE.FNTL,
				fnnrYrCnt  : NUMBER.FNNR_CNT,
				fnnrAcntCd : STRING.FNNR_CODE
			}));
			let grid = {};
			if (ret) {
				grid['fnnrList'] = [];
				$.each(ret['rows'], function(i,row) {
					grid['fnnrList'].push($.extend(row, {
						sn:'', 
						disabled: true,
						mode:MODE.INSERT 
					}));
				});
				$.each(_cards, function(key, card) {
					if (key == 'info')
						card.doLoad(true, data, grid);
				});
			}
		}
	};
	// 회원정보 조회
	let _doLoadUsr = function() {
		let url   = getUrl('/usr/support/support/getUser.do');
		const usr = $.ajaxUtil.ajaxDefault(url, {});
		if (usr) {
			_cards['info'].doLoad(true, usr['Data'], {});
		}
	};

	// 수정인 경우 상세정보 조회
	let _doLoadSprt = function() {
		if ($('#mode').val() != MODE.UPDATE)
			return;
		let url    = getUrl('/usr/support/support/getSprt.do');
		let params = {sprtAplyNo: $('#sprtAplyNo').val()};
		const ret  = $.ajaxUtil.ajaxDefault(url, params);
		if (ret) {
			let data = ret['Data'];
			
			if (!$.commUtil.empty(data['brdt'])) {
				// 2023.09.11 생년월일 필터처리
				data['brdt'] = FILTER.filterBrdt(data['brdt']);
			}
			$.extend(data, {bizFldCode: data['bizFld']});
			let grid = {
				fnnrList: ret['fnnrList'],
				mxtrList: ret['mxtrList']
			};
			$.each(_cards, function(key, card) {
				card.doLoad(false, data, grid, function(d) {
					if (d['picEmlAddr2'] && d['picEmlAddr2'].length > 0)
						d['picEmlDomain'] = d['picEmlAddr2'];
				});
			});
		}
	};

	if (_options.infoYn) _cards['info'] = $('<div class="app-sprt-info"></div>').appSprtInfoCard(_options);
	if (_options.invtYn) _cards['invt'] = $('<div class="app-sprt-invt"></div>').appSprtInvtCard(_options);
	if (_options.picgYn) _cards['picg'] = $('<div class="app-sprt-picg"></div>').appSprtPicgCard(_options);

	let _this = $(this);
	// 초기화
	_this.html('');
	$.each(_cards, function(key, card) {
		_this.append(card);
	});
	
	// TEXTAREA 입력문자열 길이표시 (comm_formatutils.js)
	$.eventUtil.showStringLength(
		_this.find('[name="bizCn"]'), 
		_this.find('.app-bizCn-length'), 
		100 
	);
	
	// 일반회원인 경우 회원정보 조회
	if (SCREEN.ROLE.USR) {
		_doLoadUsr();
	}
	// 기업회원인 경우 KODATA정보 조회
	else {
		_doLoadEnt();
	}
	// 수정인 경우 저장된 신청정보 조회
	_doLoadSprt();
	
	// 지원사업 저장처리
	let _doSave = function() {
		let saveUrl = getUrl('/usr/support/support/saveSprt.do');
		let nextUrl = getUrl('/usr/support/support/formSprt.do');
		let prevUrl = getUrl('/usr/support/support/formSprt.do');
		let oper    = $(this).data('code');
		let step    = 1;
		let prm = {
			act       : MODE.TMPSAVE,
			mode      : $('#mode'      ).val(),
			sprtAplyNo: $('#sprtAplyNo').val(),
			sprtSeCd  : $('#sprtSeCd'  ).val(),
			prgrmNo   : $('#prgrmNo'   ).val(),
			bzentyNo  : $('#bzentyNo'  ).val(),
			userNo    : $('#userNo'    ).val(),
			stepCd    : options.stepCd,
			stepNo    : parseInt(options.stepNo)
		};
		if      (oper == 'PREV') prm['stepNo'] -= step;
		else if (oper == 'NEXT') prm['stepNo'] += step;
		else if (oper == 'SBMT') prm['stepNo'] += step;
		if      (oper == 'SBMT') prm['act'   ]  = MODE.SUBMIT;
		else                     prm['act'   ]  = MODE.TMPSAVE;                     
		
		if (oper == 'PREV') {
			$.formUtil.submitForm(prevUrl, {params: prm});
		}
		else {
			let data    = {};
			let valid   = false;
			// 데이터 검증
			$.each(_cards, function(key, card) {
				let gridValidate = false;
				if (key == 'info') {
					gridValidate = $.extend({}, {
						// 매출액 행단위 검증
						fnnrList: function( row, idx, tr ) {
							// 행단위 메시지 제목
							let t = '매출액 ['+(idx+1)+'번째행] ';
							// 빈값 체크
							if (!$.formUtil.isEmptyCheck(tr, 'fnnrYmd', t+'결산년도를 입력하세요.'))	return false;
							if (!$.formUtil.isEmptyCheck(tr, 'fnnrAmt', t+'매출액을 입력하세요.'))		return false;
							// 매출액 포맷제거
							$.extend(row, {fnnrAmt: $.commUtil.nvlInt(row['fnnrAmt'])});
							return true;
						}
					});
				}
				else if (key == 'invt') {
					gridValidate = $.extend({}, {
						// 투자조합정보 행단위 검증
						mxtrList: function( row, idx, tr ) {
							// 투자일자 병합처리
							$.formUtil.mergeName(tr, 'invtYmd', 'month', 2, row);
							// 행단위 메시지 제목
							let t = '투자조합정보 ['+(idx+1)+'번째행] ';
							// 빈값 체크
							if (!$.formUtil.isEmptyCheck(tr, 'invtMxtrNm', t+'투자조합명을 입력하세요.'))	return false;
							if (!$.formUtil.isEmptyCheck(tr, 'invstrNm'  , t+'운용사명을 입력하세요.'))		return false;
							if (!$.formUtil.isEmptyCheck(tr, 'invtYmd'   , t+'투자일자를 선택하세요.'))		return false;
							if (!$.formUtil.isEmptyCheck(tr, 'invtAmt'   , t+'투자금액을 입력하세요.'))		return false;
							// 투자금액 포맷제거
							$.extend(row, {invtAmt: $.commUtil.nvlInt(row['invtAmt'])});
							return true;
						}
					});
				}
				let d = card.doValidate( gridValidate );
				if (d === false) {
					valid = false;
					return false;
				}
				valid = true;
				$.extend(data, d);
				$.extend(data, prm);
			});
			if (valid) {
		        $.commMsg.confirm("저장하시겠습니까?", function() {
		            // AJAX로 저장처리
		            $.ajaxUtil.ajaxSave(
		                saveUrl, 
		                JSON.stringify(data),
		                function(ret) {
		                    $.ajaxUtil.success(ret, function() {
								if (oper == 'TEMP')
									$.commMsg.alert('임시저장이 완료되었습니다.');
								else {
									$.extend(prm, {sprtAplyNo: ret['Data']})
									$.formUtil.submitForm(nextUrl, {params: prm});
								}
		                    });
		                }
		            );
		        });
			}
		}
		return false;
	};

	if (options.btnId) {
		// LEFT BUTTON
		let lbtns = [$.sprtUtils.getSaveButton(_doSave)];
		// RIGHT BUTTON
		let rbtns = [];
		rbtns.push($.sprtUtils.getCnclButton(options.formId));
		if (_options.prevYn)
			rbtns.push($.sprtUtils.getPrevButton(_doSave));
		rbtns.push($.sprtUtils.getNextButton(_doSave));
		
		// 이동버튼 표시
		$(options.btnId).appSprtButtons([lbtns, rbtns], 'ver1');			
	}	
	return this;
};

// 상담신청 - 신청서 작성
//=============================================================================
$.fn.appDscsnForm = function( args ) {
	
	let options = $.extend({
		bizYn    :   'N', // 입력값
		formId   : false, // 입력값
		btnId    : false, // 입력값
		sprtSeCd : false, // 입력값
		sprtSeNm : false, // 입력값
		stepNo   : false, // 입력값
		stepCd   : 'FORM',
		stepNm   : '신청서 작성',
		prgrmNo  : false, // 입력값
		prgrmNm  : false, // 입력값
	}, args);
	
	let _card = $('<div class="app-sprt-picg"></div>').appSprtPicgCard({});
	// 초기화
	$(this).html('');
	$(this).append(_card);

	// 상담신청 저장처리
	let _doSave = function() {
		let saveUrl = getUrl('/usr/support/support/saveDscsn.do');
		let nextUrl = getUrl('/usr/support/support/formDscsn.do');
		let prevUrl = getUrl('/usr/support/support/formSprt.do' );
		let oper    = $(this).data('code');
		let step    = (CONFIG.SPRT_AGREE ? 2 : 1);
		let prm = {
			act       : MODE.SUBMIT,
			mode      : $('#mode'      ).val(),
			sprtAplyNo: $('#sprtAplyNo').val(),
			sprtSeCd  : $('#sprtSeCd'  ).val(),
			prgrmNo   : $('#prgrmNo'   ).val(),
			stepCd    : options.stepCd,
			stepNo    : parseInt(options.stepNo)
		};
		if      (oper == 'PREV') prm['stepNo'] -= step;
		else if (oper == 'NEXT') prm['stepNo'] += step;
		
		if (oper == 'PREV') {
			$.formUtil.submitForm(prevUrl, {params: prm});
		}
		else {
			let data = {};
			// 데이터 검증
			let d = _card.doValidate();
			if (!d)
				return false;
			$.extend(data, d);
			$.extend(data, prm);
	        $.commMsg.confirm("저장하시겠습니까?", function() {
	            // AJAX로 저장처리
	            $.ajaxUtil.ajaxSave(
	                saveUrl, 
	                JSON.stringify(data),
	                function(ret) {
	                    $.ajaxUtil.success(ret, function() {
							$.extend(prm, {dscsnAplyNo: ret['Data']})
							$.formUtil.submitForm(nextUrl, {params: prm});
	                    });
	                }
	            );
	        });
		}
		return false;
	};

	// 수정인 경우 상세정보 조회
	let _doLoadDscsn = function() {
		if ($('#mode').val() != MODE.UPDATE)
			return;
		let url    = getUrl('/usr/support/support/getDscsn.do');
		let params = {dscsnAplyNo: $('#dscsnAplyNo').val()};
		const ret  = $.ajaxUtil.ajaxDefault(url, params);
		if (ret) {
			let data = ret['Data'];
			_card.doLoad(false, data, false);
		}
	};
	
	// 수정인 경우 저장된 신청정보 조회
	_doLoadDscsn();
	
	if (options.btnId) {
		// 이동버튼 표시
		$(options.btnId).appSprtButtons([
			 $.sprtUtils.getCnclButton(options.formId)
			,$.sprtUtils.getPrevButton(_doSave)
			,$.sprtUtils.getNextButton(_doSave)
		], false);
	}	
	return this;
};

// 제출서류
//=============================================================================
$.fn.appSprtFile = function( args ) {
	
	let options = $.extend({
		bizYn    :   'N', // 입력값
		formId   : false, // 입력값
		btnId    : false, // 입력값
		sprtSeCd : false, // 입력값
		sprtSeNm : false, // 입력값
		stepNo   : false, // 입력값
		stepCd   : 'FILE',
		stepNm   : '제출서류',
		prgrmNo  : false, // 입력값
		prgrmNm  : false, // 입력값
		aplyNo   : false,
		comment  : '※ 100MB 이내 1개 PDF 파일 첨부 가능'
	}, args);
	
	let _this = $(this);
	
	// 제출서류 저장처리
	let _doSaveFile = function() {
		let saveUrl = getUrl('/usr/support/support/saveFile.do');
		let nextUrl = getUrl('/usr/support/support/formSprt.do');
		let prevUrl = getUrl('/usr/support/support/formSprt.do');
		let oper    = $(this).data('code');
		let step    = 1;
		let prm = {
			act       : MODE.TMPSAVE,
			mode      : $('#mode'      ).val(),
			sprtAplyNo: $('#sprtAplyNo').val(),
			sprtSeCd  : $('#sprtSeCd'  ).val(),
			prgrmNo   : $('#prgrmNo'   ).val(),
			stepCd    : options.stepCd,
			stepNo    : parseInt(options.stepNo)
		};
		if      (oper == 'PREV') prm['stepNo'] -= step;
		else if (oper == 'SBMT') prm['stepNo'] += step;
		if      (oper == 'SBMT') prm['act'   ]  = MODE.SUBMIT;
		else                     prm['act'   ]  = MODE.TMPSAVE;                     
		
		if (oper == 'PREV') {
			$.formUtil.submitForm(prevUrl, {params: prm});
		}
		else {
			// 첨부파일 업로드 VALIDATION
			let data = _this.appBizPapeFile('validate', {required: (oper == 'SBMT')});
			if (!data)
	            return false;

			$.extend(data, prm);
	    	$.commMsg.confirm("저장하시겠습니까?", function() {
	            // AJAX로 저장처리
	            $.ajaxUtil.ajaxSave(
	                saveUrl, 
	                JSON.stringify(data),
	                function(ret) {
	                    $.ajaxUtil.success(ret, function() {
							if (oper == 'TEMP')
								$.commMsg.alert('임시저장이 완료되었습니다.');
							else {
								$.formUtil.submitForm(nextUrl, {params: prm});
							}
	                    });
	                }
	            );
	    	});
		}
		return false;
	};
	// 첨부파일 초기화 (app_bizpapefile.js)
	_this.appBizPapeFile({
		// 처리모드
		mode : MODE.UPDATE,
		// 설명글
		comment: options.comment,
		// 초기값
		initData: {
			upDcmntCd: FILTER.getPapeGroup(options.sprtSeCd),
			taskSeCd:  CODE.TASK_SE.SPRT,
			dtlSeCd :  options.prgrmNo,
			aplySeCd:  FILTER.getPapeType(options.bizYn),
			docNo   :  options.aplyNo
		},
		extensions:['pdf'], 
		isAlert: true,
		isExt:   true,
		isLimit: true
		
	}).appBizPapeFile('init');
	
	if (options.btnId) {
		// 이동버튼 표시
		$(options.btnId).appSprtButtons([
			// LEFT BUTTON
			[$.sprtUtils.getSaveButton(_doSaveFile)],
			// RIGHT BUTTON
			[$.sprtUtils.getCnclButton(options.formId)
			,$.sprtUtils.getPrevButton(_doSaveFile)
			,$.sprtUtils.getSbmtButton(_doSaveFile)]
		], 'ver1');			
	}	
	return this;
};

//========================================================//
// 지원서비스 - 담당자정보
//--------------------------------------------------------//
$.fn.appSprtPicgCard = function( args ) {
	return $(this).appFormCard($.extend({
		title:      '담당자 정보',
		titleIcon:  'icon-user-alt',
		formId:     'picgForm',
		items: [
			{cls:'border-in-ver'
			,items:[
				 {cls:'col-12 col-md-6', label:'이름', must: true, input: {id:'picNm', name:'picNm', type:'text', maxlength: 20, placeholder: '담당자 이름을 입력하세요.'}}
				,{cls:'col-12 col-md-6', label:'부서/직급', input: {id:'picDeptNm', name:'picDeptNm', type:'text', maxlength: 20, placeholder: '부서/직급을 입력하세요.'}}
			]},
			{items:[
				 {cls:'col-12 col-md-6', label:'직통번호', must: true, 
					input: {id:'picTelno', name:'picTelno', type:'text', maxlength: 14, placeholder: '담당자 연락처를 입력하세요.', valid: {method:'phone'}, valueFormatter: $.formatUtil.toPhone, phone: true}
				}
				,{cls:'col-12 col-md-6', label:'E-mail', must: true, 
					hidden: {id:'picEmlAddr', name:'picEmlAddr', type:'hidden', 
						valid: {method:'email'},
						merge: {count:2, type:'email'}
					}, 
					input: [
						{id:'picEmlAddr1', name:'picEmlAddr1', type:'text', maxlength: 50},
						{text:'@', cls:'wave'},
						{id:'picEmlAddr2', name:'picEmlAddr2', type:'text', maxlength: 30}, 
						{id:'picEmlDomain', name:'picEmlDomain', type:'select', notvalid: true,
							options: {
								type:'static', 
								init: COMBO.INIT_DIRECT, 
								rows: STORE.EML_CC,
								change: function() {
									if ($.commUtil.nvl($(this).val()) == '') {
										//$('#picEmlAddr2').val('');
										$('#picEmlAddr2').prop('readonly', false);
										$('#picEmlAddr2').removeClass('app-readonly');
										$('#picEmlDomain').val('');
									}
									else {
										$('#picEmlAddr2').val($(this).val());
										$('#picEmlAddr2').prop('readonly', true);
										$('#picEmlAddr2').addClass('app-readonly');
									}
								}
							}
						}
			 	 	]
				}
			]},
		]
	}, args));
};

//========================================================//
// 지원서비스 - 투자조합정보
//--------------------------------------------------------//
$.fn.appSprtInvtCard = function( args ) {
	return $(this).appFormCard($.extend({
		title:      '농식품 투자조합 정보',
		titleIcon:  'icon-bowl-spoon F',
		formId:     'invtForm',
		items: [{
			cls:'pb-0',
			items:[{
				cls:'col-12',
				grid:{
					id: 'appMxtrGrid',
					name: 'mxtrList',
					options: {
						id:         '#appMxtrGrid',
						name:       'mxtrList',
						mode:       'LIST',
						idField:    'sn',
						title:      '투자조합정보',
						listOptions: {
							emptyText: false,
							headCls: "bs-1 ts-m",
							bodyCls: "bs-1 ts-m t-t-c",
							headthCls: '',
							editable: true,
							editDefCnt: 0,
							editMinCnt: 0,
							editMaxCnt: 3,
							columns: [
								{name:'invtMxtrNm', label:'투자조합명'  , input: {type:'text', cls: 'app-full'}},
								{name:'invstrNm'  , label:'운용사명'    , input: {type:'text', cls: 'app-full'}},
								{name:'invtYmd'   , label:'투자일자'    , formatter: function(o) {
									let ymd1 = '';
									let ymd2 = '';
									if (o && o.length > 5) {
										ymd1 = o.substring(0,4);
										ymd2 = o.substring(4,6);
									}
									let d = $('<div class="d-flex form-area-box1"></div>');
									d.append($('<div class="mr-5px"></div>').appComboBox({name:'invtYmd1', boxCls:'app-w100', wrapCls:'ele-icon-box', type:'static', value:ymd1, init: COMBO.INIT_SELECT, rows: STORE.getYears (0, $.formatUtil.toYear,20)}));
									d.append($('<div class="mr-5px"></div>').appComboBox({name:'invtYmd2', boxCls:'app-w100', wrapCls:'ele-icon-box', type:'static', value:ymd2, init: COMBO.INIT_SELECT, rows: STORE.getMonths($.formatUtil.toMonth)}));
									d.append('<input type="hidden" name="invtYmd" value="" />');
									return d;
								}},
								{name:'invtAmt', label:'투자금액(원)', input: {type:'text', money: true, cls: 'app-full text-end'}}
							],
							colgroup: ['25%','25%','25%','25%']
						}
					}
				}
			}]
		}]
	}, args));
};

//========================================================//
// 지원서비스 - 기본정보
//--------------------------------------------------------//
$.fn.appSprtInfoCard = function( args ) {
	let validates = false;
	let items = [
		{cls:'border-in-ver'
		,items:[
			 {cls:'col-12 col-md-3', label:'경영체명'  , must: true, input: {id:'bzentyNm', name:'bzentyNm', type:'text', maxlength: 50}}
			,{cls:'col-12 col-md-3', label:'사업자번호', must: true, 
				hidden: {id:'brno', name:'brno', type:'hidden', 
					valid: {method:'bznoNo'},
					merge: {count:3, type:'bzno'}
				}, 
				input: [
					{id:'brno1', name:'brno1', type:'text', maxlength: 3, number: true},{text:'-', cls:'wave'},
					{id:'brno2', name:'brno2', type:'text', maxlength: 2, number: true},{text:'-', cls:'wave'},
					{id:'brno3', name:'brno3', type:'text', maxlength: 5, number: true}
			 	]
			 }
			,{cls:'col-12 col-md-3', label:'대표자명', must: true, input: {id:'rprsvNm', name:'rprsvNm', type:'text', maxlength: 20}}
			,{cls:'col-12 col-md-3', label:'설립일',   must: true, input: {id:'fndnYmd', name:'fndnYmd', type:'text', maxlength: 10, calendar: true, valid: {method:'date'}}}
		]},
		{cls:'border-in-ver'
		,items:[
			 {cls:'col-12 col-md-3', label:'임직원수', input: [{id:'empCnt', name:'empCnt', type:'text', maxlength: 5, number: true},{text:'명', cls:'col wave align-self-end flex-b17px'}]}
			,{cls:'col-12 col-md-3', label:'법인등록번호', 
				hidden: {id:'crno', name:'crno', type:'hidden',
					//valid: {method:'crnoNo'},boxCls
					notvalid: true,
					merge: {count:2, type:'crno'}
				}, 
				input: [
					{id:'crno1', name:'crno1', type:'text', maxlength: 6, number: true},{text:'-', cls:'wave'},
					{id:'crno2', name:'crno2', type:'text', maxlength: 7, number: true}
			 	]
			 }
			,{cls:'col-12 col-md-3', label:'생년월일', must: true, 
				hidden: {id:'brdt', name:'brdt', type:'hidden',
					valid: {method:'birthNo'},
					merge: {count:3, type:'date'}
				}, 
				input: [
					{id:'brdt1', name:'brdt1', type:'select', options:{type:'static', boxCls:'w-70px px-4px', rows: STORE.getYears (-20)}},{text:'년', cls:'wave flex-b20px'},
					{id:'brdt2', name:'brdt2', type:'select', options:{type:'static', boxCls:'w-50px px-4px', rows: STORE.getMonths()} },{text:'월', cls:'wave flex-b20px'},
					{id:'brdt3', name:'brdt3', type:'select', options:{type:'static', boxCls:'w-50px px-4px', rows: STORE.getDays  ()} },{text:'일', cls:'wave flex-b20px'},
			 	]
			 }
			,{cls:'col-12 col-md-3', label:'성별', must: true, boxCls:'form-area-box-input2', input: {id:'sexdstn', name:'sexdstn', type:'radio', options: {type:'static', rows:STORE.SX_DST}}}
		]},
		{cls:'border-in-ver'
		,items:[
			 {cls:'col-12 col-md-3', label:'주소', must: true, input: {id:'lctnAddr', name:'lctnAddr', type:'text', maxlength: 100}}
			,{cls:'col-12 col-md-3', label:'전화번호', must: true, input: {id:'rprsTelno', name:'rprsTelno', type:'text', maxlength: 14, valid: {method:'phone'}, valueFormatter: $.formatUtil.toPhone, phone: true}}
			,{cls:'col-12 col-md-3', label:'기업유형', must: true, input: {id:'bzentyTypeCd', name:'bzentyTypeCd', type:'select',options: {params: {upCdId: CODE.BZENTY_TYPE.code}, filter: FILTER.filterBzentyType}}}
			,{cls:'col-12 col-md-3', label:'FAX', input: {id:'fxno', name:'fxno', type:'text', maxlength: 14, valid: {method:'phone'}, valueFormatter: $.formatUtil.toPhone, phone: true}}
		]},
		{cls:'pb-0',items:[{cls:'col-12',label:'사업분야', must: true, boxCls:'form-area-box-input2', input: {id:'bizFldCode', name:'bizFldCode', type:'checkbox', grpcls: 'justify-content-between', options: {separator:',', params: {upCdId: CODE.BIZ_RLM.code}}}}]},
		{cls:'border-in-ver',items:[
			 {cls:'col', label:'홈페이지', input: {id:'hmpgAddr', name:'hmpgAddr', type:'text', maxlength: 100}}
			,{cls:'col', label:'투자희망금액', must: true,input: [{id:'invtHopeAmt', name:'invtHopeAmt', type:'text', maxlength: 15, number: true, cls:'text-end'},{text:'백만원', cls:'wave align-self-end flex-b50px'}]}
		]},
		{cls:'border-in-ver',items:[{cls:'col', label:'주요사업'  , must: true, 
			input: {id:'mainBizCn', name:'mainBizCn', type:'text', maxlength: 100, placeholder:'(작성예시) 식품제조 가공업, 건강기능식품 개발 전문 업체, 지능형 농업기계 솔루션 등'}
		}]},
		{cls:'border-in-ver',items:[{cls:'col', label:'핵심아이템', must: true, 
			input: {id:'coreItmCn', name:'coreItmCn', type:'text', maxlength: 100, placeholder:'(작성예시) 헛개나무 추출분말, 즉석 간편식 Daily 배송 시스템, 수제맥주, 통합 제어 시스템 등'}
		}]},
		{cls:'border-in-ver',items:[{cls:'col', label:'투자요소'  , must: true, 
			input: {id:'bizCn', name:'bizCn', type:'textarea', maxlength: 100, placeholder:'(작성예시) OO음료 원료 공급, 독보적인 기술력, 자체 식품제조를 통해 신선 제품 배송 가능, OO지역 배송시스템 구축, 최신 트렌드를 반영한 OO제품 개발, 기술 차별화에 의한 높은 제조 마진 및 원가절감 등'},
			comment: {cls:'text-end app-bizCn-length', text: '0 / 100'}
		}]}
	];
				
	if (args.fnnrYn) {
		items.splice( items.length-3, 0, {
			cls:'pb-0',
			items:[{
				cls:'col-12',label:'매출액', labelCls:'align-items-center d-flex', 
				grid:{
					id: 'appFnnrGrid',
					name: 'fnnrList',
					options: {
						id:         '#appFnnrGrid',
						name:       'fnnrList',
						mode:       'LIST',
						idField:    'sn',
						title:      '매출액',
						listOptions: {
							headCls: "bs-1 ts-m",
							bodyCls: "bs-1 ts-m t-t-c",
							headthCls: '',
							editable: true,
							editDefCnt: 0,
							editMinCnt: 0,
							editMaxCnt: 3,
							columns: [
								{name:'fnnrYmd', label:'결산년도'  , formatter: $.formatUtil.toYmdYear, input: {type:'text', year: true, number: true, maxlength: 4, cls: 'app-full'}},
								{name:'fnnrAmt', label:'매출액(원)', formatter: $.formatUtil.toNumber, input: {type:'text', money: true, maxlength: 15, cls: 'app-full text-end'}}
							],
							colgroup: ['150px','*']
						}
					}
				}
			}]
		});
		
		// 추가검증항목
		validates = [
			{key: 'fnnrYmd', method: 'year', message: '[매출액] 결산년도 형식에 맞게 입력하세요.'}
		];
	}
	if (args.linkYn) {
		items.push({items:[{cls:'col', label:'펀딩시도 확인 여부', must: true, input: {id:'fundLinkUrl', name:'fundLinkUrl', type:'text', maxlength: 500, placeholder:'펀딩링크 (URL)을 입력해주세요.'}}]});
	}
	let disableKeys = false;
	// 일반회원의 경우 비활성처리항목 정의
	if (SCREEN.ROLE.USR) {
		disableKeys = [
			 'bzentyNm','fndnYmd','empCnt'
			,'brno','brno1','brno2','brno3'
			,'crno','crno1','crno2'
		];
	}
	return $(this).appFormCard($.extend({
		title:      '신청자 정보',
		titleIcon:  'icon-lightbulb',
		formId:     'infoForm',
		items:      items,
		disableKeys:disableKeys,
		validates:  validates,
		saveFilter: function(d) {
			if (d && 
				d['bizFldCode'] && 
				$.type(d['bizFldCode']) !== 'array') {
				// 배열처리
				d['bizFldCode'] = [d['bizFldCode']];
			}
		}
	}, args));
};
