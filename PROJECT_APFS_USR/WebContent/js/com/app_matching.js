/**
*******************************************************************************
*** 파일명    : app_matching.js
*** 설명      : 매칭서비스 관련 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.06.17              LSH
*******************************************************************************
**/

// 매칭결과 화면구성
//=============================================================================
$.fn.appMatchingResult = function(args) {
	
	let options = $.extend({
		// 화면모드
		showMode: '',
		// 검색모드
		srchMode: '',
		// 매칭모드
		mtchMode: ''
	}, args);
	
	let _this = $(this);

	// 정부지원사업 생성
	let _createPbanc = function( key, params ) {
		return $.gridUtils.appPbancGrid({
			form: $('#'+key+'Form'),
			gridOptions: {
				id:     '#app'+key+'Grid',
				paging: '#app'+key+'GridPagination',
				result: '#app'+key+'GridResult',
				toolbar:'#app'+key+'GridToolbar',
				// 검색 URL
				url: getUrl('/usr/support/pbanc/getListPbanc.do'),
				// 검색 조건
				params: params
			}
		});
	};
	// 모태펀드 생성
	let _createFund = function( key, params ) {
		let grid = $.gridUtils.appFundGrid({
			form: $('#'+key+'Form'),
			gridOptions: {
				id:     '#app'+key+'Grid',
				paging: '#app'+key+'GridPagination',
				result: '#app'+key+'GridResult',
				// 자동 로딩
				autoload: true,
				// 검색 URL
				url:     getUrl('/usr/invest/fund/getListFund.do'),
				// 검색 조건
				params: params,
				// 검색결과 Formatter
				resultFormatter: function(dom) {
					let col = $('<div class="col-12 col-md flex-b-md-320px"></div>');
					col.append($('<div></div>').appSortBox({
						// 선텍 이벤트 
						select: function() {
							grid.doSearch();
						},
						// 정렬 숨김박스
						inputs: {
							field: {id:'ordrField',name:'ordrField',value:''},
							order: {id:'ordrType' ,name:'ordrType' ,value:''},
						},
						// 정렬 항목
						items: [
							{cls: 'col-6', field: 'fund_oper_scale', order:  'ASC', icon: 'icon-arrow-down-from-line-F', label: '낮은 펀드규모순'},
							{cls: 'col-6', field: 'fund_oper_scale', order: 'DESC', icon: 'icon-arrow-up-from-line-F'  , label: '높은 펀드규모순'}
						]
					}));
					dom.find('.row').append(col);
				},
				toolbar:        false,
				toolbarOptions: false,
			}
		});
		return grid;
	};
	// 경영체 생성
	let _createEnt = function(key, params) {
		return $.gridUtils.appEntGrid({
			form: $('#'+key+'Form'),
			gridOptions: {
				id:     '#app'+key+'Grid',
				paging: '#app'+key+'GridPagination',
				result: '#app'+key+'GridResult',
				toolbar:'#app'+key+'GridToolbar',
				// 검색 URL
				url: getUrl('/usr/invest/ent/getListEntBiz.do'),
				// 검색 조건
				params: params
			}
		});
	};
	
	// 목록영역생성
	let _createForm = function(key, dom) {

		let form = $('<form id="'+key+'Form" name="'+key+'Form" method="post" onsubmit="return false;"></form>');
		form.append('<div id="app'+key+'GridResult" class="tit-box-2 mb-16px"></div>');
		form.append('<div id="app'+key+'GridToolbar"></div>');
		
		dom.append(form);
		
		let panel = $('<div id="app'+key+'GridPanel"></div>');
		panel.append('<div class="shadow-box-1 px-16px pb-24px"></div>');
		panel.find('.shadow-box-1').append('<div id="app'+key+'Grid"></div>');
		panel.find('.shadow-box-1').append('<div class="mb-0 paging-box pb-24px"></div>');
		panel.find('.paging-box'  ).append('<ul id="app'+key+'GridPagination"></ul>');
		
		dom.append(panel);
	};
	
	// 탭생성
	let _createTab = function(dom) {
		dom.append('<div id="appGridTabBox"></div>');
		dom.append('<div id="appFundTab"></div>');
		dom.append('<div id="appPbancTab"></div>');
					
		// 탭생성
		$('#appGridTabBox').appTabs({
			// 최초 선택값
			value: 'FUND',
			// 버튼항목
			items: [
				{value: 'FUND', tab: '#appFundTab' , icon: 'icon-papers-text'      , label: '모태펀드'},
				{value: 'SPRT', tab: '#appPbancTab', icon: 'icon-presentation-text', label: '정부지원사업'}
			]
		});
	};
	// 화면생성
	let _create = function() {
		// 경영체인 경우
		if (options.showMode == 'B') {
			
			// 탭생성
			_createTab(_this);

			// 모태펀드 생성
			_createForm('fund', $('#appFundTab'));
			_createFund('fund', {
				srchMode: options.srchMode,
				showMode: options.showMode
			});
			// 정부지원사업 생성
			_createForm('pbanc', $('#appPbancTab'));
			_createPbanc('pbanc', {
				srchMode: options.srchMode,
				showMode: options.showMode
			});
		}
		// 일반회원인 경우
		else if (options.showMode == 'U') {
			
			// 정부지원사업 생성
			_createForm('pbanc', _this);
			_createPbanc('pbanc', {
				srchMode: options.srchMode,
				showMode: options.showMode
			});
		}
		// 투자자/유관기관인 경우
		else if (options.showMode == 'I') {
			
			// 경영체 생성
			_createForm('ent', _this);
			_createEnt('ent', {
				srchMode: options.srchMode,
				showMode: options.showMode
			});
		}
	};
	
	_create();
	
	return this;
};

// 매칭설정 화면구성
//=============================================================================
$.fn.appMatching = function(args) {
	
	let options = $.extend({
		// 매칭설정폼
		form: $('#matchForm'),
		// 조회 URL
		url: getUrl('/usr/mypage/matching/getMatching.do'),
		// 조회 조건
		params: {},
		// 매칭설정 항목
		items: {
			// 투자분야
			invtFld: {
				must:  true,
				label: '투자분야',
				name:  'invtFldList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'checkbox', 
					name:   'invtFldList',
					url:    getUrl('/com/common/getInvtRlmComboCode.do'),
					init:   {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click:  bindUnlockAll
				}
			},
			// 사업분야
			bizFld: {
				must:  true,
				label: '사업분야',
				name:  'bizFldList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'checkbox', 
					name:   'bizFldList',
					url:    getUrl('/com/common/getBizRlmComboCode.do'),
					params: {upCdId: CODE.BIZ_RLM.code},
					init:   {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click:  bindUnlockAll
				}
			},
			// 펀드규모
			fundScale: {
				must:  true,
				label: '펀드규모',
				name:  'fundScaleList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'radio', 
					name:   'fundScaleList',
					params: {upCdId: CODE.FUND_SIZE.code},
					init:   {code:'ALL', text:'전체'}
				}
			},
			// 투자희망금액
			invtHope: {
				must:  true,
				label: '투자희망금액',
				name:  'invtHopeList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'radio', 
					name:   'invtHopeList',
					params: {upCdId: CODE.FUND_SIZE.code},
					init:   {code:'ALL', text:'전체'}
				}
			},
			// 지원대상
			bizTrgt: {
				must:  true,
				label: '지원대상',
				name:  'bizTrgtList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'checkbox', 
					name:   'bizTrgtList',
					params: {upCdId: CODE.SPRT_TRGT.code},
					init:   {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click:  bindUnlockAll
				}
			},
			// 지원연령
			bizTrgtAge: {
				must:  true,
				label: '지원연령',
				name:  'bizTrgtAgeList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'checkbox', 
					name:   'bizTrgtAgeList',
					params: {upCdId: CODE.SPRT_AGE.code},
					init:   {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click:  bindUnlockAll
				}
			},
			// 창업기간
			bizTrgtFntnPd: {
				must:  true,
				label: '창업기간',
				name:  'bizTrgtFntnPdList',
				type:  'appSelectBox', 
				inputOptions: {
					colCls: 'col',
					form:   'checkbox', 
					name:   'bizTrgtFntnPdList',
					params: {upCdId: CODE.FNTN_WHL.code},
					init:   {code:'ALL', text:'전체', click: bindCheckAll}, // (comm_formatutils.js)
					click:  bindUnlockAll
				}
			}
		},
		
	}, args);
	
	// 권한별 매칭설정 옵션정의
	let _getOptions = function() {
		// 매칭설정 옵션
		let _options = {
			appMatchingF: false,
			appMatchingP: false,
			appMatchingA: false,
		};
		// 일반회원인 경우
		if (SCREEN.ROLE.USR) {
			_options['appMatchingP'] = {
				title : '정부지원사업',
				items : [
					[options.items.bizFld       ], // 사업분야
					[options.items.bizTrgt      ], // 지원대상
					[options.items.bizTrgtAge   ], // 지원연령
					[options.items.bizTrgtFntnPd]  // 창업기간
				]
			};
		}
		// 관리자/투자자/유관기관인 경우
		// 2023.09.11 농금원관리자 포함
		else if (SCREEN.ROLE.ADM || SCREEN.ROLE.INV || SCREEN.ROLE.RIS || SCREEN.ROLE.RIS || SCREEN.ROLE.MNG) {
			_options['appMatchingA'] = {
				title : '',
				items : [
					[options.items.invtFld      ], // 투자분야
					[options.items.bizFld       ], // 사업분야
					[options.items.invtHope     ]  // 투자희망금액
				]
			};

		}
		// 경영체인 경우
		else if (SCREEN.ROLE.ENT) {
			_options['appMatchingF'] = {
				title : '모태펀드',
				items : [
					[options.items.invtFld      ], // 투자분야
					[options.items.fundScale    ]  // 펀드규모
				]
			};
			_options['appMatchingP'] = {
				title : '정부지원사업',
				items : [
					[options.items.bizFld       ], // 사업분야
					[options.items.bizTrgt      ], // 지원대상
					[options.items.bizTrgtAge   ], // 지원연령
					[options.items.bizTrgtFntnPd]  // 창업기간
				]
			};
		}
		return _options;
	};
	
	// 매칭설정 데이터로드
	let _getLoadData = function() {
		let ret = $.ajaxUtil.ajaxDefault(options.url, options.params);
		if (ret && ret.Data)
			return ret.Data;
		return false;
	};

	// 매칭설정 제목 생성
	let _createTitle = function() {
		// 매칭설정 설명글
		let _comment  = '관심있는 정보를 설정해 보세요.';
		// 일반회원인 경우
		if (SCREEN.ROLE.USR) {
			_comment += '적합한 정부지원사업을 매칭해 드립니다.';
		}
		// 관리자/투자자/유관기관인 경우
		else if (SCREEN.ROLE.ADM || SCREEN.ROLE.INV || SCREEN.ROLE.RIS) {
			_comment += '적합한 경영체를 매칭해 드립니다.';
		}
		// 경영체인 경우
		else if (SCREEN.ROLE.ENT) {
			_comment += '적합한 모태펀드/정부지원사업을 매칭해 드립니다.';
		}
		
		// 매칭서비스 제목
		$('#appMatchingTitle').append($.domUtil.getTitleBox({
			title:    '매칭서비스 설정',
			titleBox: 'tit-box-3 border-1 border-bottom border-ele-Line mb-24px pb-16px',
			icon:     '<img src="'+getUrl('/images/titimg-2.svg')+'" alt="">',
			comment:  _comment
		}));
	};
	
	// 매칭설정 객체
	let _matching = {};
	// 매칭설정 표시항목
	let _shows = [];
	// 데이터
	let _data = _getLoadData();
	// 설정옵션
	let _opts = _getOptions();
	let _this = $(this);
	_this.append('<div id="appMatchingTitle"></div>');
	_this.append('<div id="appMatchingA"></div>');
	_this.append('<div id="appMatchingF"></div>');
	_this.append('<div id="appMatchingP"></div>');
	// 제목생성
	_createTitle();
	
	// 매칭설정 컨트롤 (app_smartsearch.js)
	$.each(_opts, function(key, o) {
		if (!o)
			return true;
		$.each(o.items, function(n,its) {
			let item = its[0];
			// 데이터 바인딩
			if (_data && 
				_data[item.name]) {
				item.inputOptions.value = _data[item.name];
			}
			// 매칭설정 표시항목 추가
			_shows.push(item);
		});
		_matching[key] = $('#'+key).appSmartSearch($.extend(o, {
			mode : MODE.MATCH,
			form : options.form
		}));
	});
	
    // 필수검증 및 저장데이터 반환
    //--------------------------------------------------------//
	this.validate = function() {
        // 데이터 객체화
        var data =  options.form.serializeObject();
		// 설정항목 필수체크
		for (let i = 0; i < _shows.length; i++) {
			let obj   = _shows[i];
			let field = obj['name' ];
			let label = obj['label'];
			// 필수항목인 경우
			if (obj.must) {
				if ($.commUtil.empty(data[field])) {
					$.commMsg.alert($.commUtil.getPostKor(label,0)+' 필수 선택사항입니다.');
					return false;
				}
			}
			// 배열이 아닌 경우 배열로 변환
			if ($.isArray(data[field]) === false) {
				data[field] = [data[field]];
			}
		}
		return data;
	};

    // 로딩여부 확인
    //--------------------------------------------------------//
	this.isLoaded = function() {
		return (_data ? true : false);
	};
	return this;
};
