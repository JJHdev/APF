/**
*******************************************************************************
*** 파일명    : app_dashboard.js
*** 설명      : 경영체 - 대시보드 관련 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/

// 경영체 데이터 목록정보
const ENTLIST = {
	INVT : {list: 'invtList'    , title: '투자받은 금액'},
	IFND : {list: 'invtFndList' , title: '농심모태펀드 투자금액'},
	IETC : {list: 'invtEtcList' , title: '기타 투자금액'},
	PTNT : {list: 'ptntList'    , title: '특허 및 상표권 보유현황'},
	OTSP : {list: 'othspSumList', title: '유관기관별 지원이력'},
	COHT : {list: 'coHstList'   , title: '회사연혁'},
	RPHT : {list: 'rprsHstList' , owner: true, title: '대표자 이력'},
	MGMT : {list: 'mgmtList'    , owner: true, title: '경영진 및 기술진'},
	SHLD : {list: 'shroldrList' , owner: true, title: '주주 현황'},
	LWST : {list: 'lwstList'    , owner: true, title: '소송중인 내용'}
};

// 경영체 카드정보
const ENTCARD = {
	INFO: { widget: 'appEntInfoCard'  , id: 'appInfoCard'   , validate: false, title: '기업 기본정보'},
	INVT: { widget: 'appEntInvtCard'  , id: 'appInvtCard'   , validate:  true, title: '주요 투자정보'},	
	PTNT: { widget: 'appEntPtntCard'  , id: 'appPtntCard'   , validate:  true, title: '특허 및 상표권 보유현황'}, 
	FNKO: { widget: 'appEntFnnrCard'  , id: 'appFnnrCard'   , validate:  true, title: '결산재무정보', params: {dataSe: CODE.DATA_SE.KODATA}, chart: true}, 
	FNMN: { widget: 'appEntFnnrCard'  , id: 'appFnnrAddCard', validate:  true, title: '업체입력 재무정보', params: {dataSe: CODE.DATA_SE.MANUAL}, chart: true}, 
	SPHT: { widget: 'appEntSptHstCard', id: 'appSptHstCard' , validate: false, title: '정부지원사업 이력'}, 
	COHT: { widget: 'appEntCoHstCard' , id: 'appCoHstCard'  , validate:  true, title: '회사연혁'}, 
	OWNR: { widget: 'appEntOwnerCard' , id: 'appOwnerCard'  , validate:  true, title: '경영진 및 주주 현황'} 
};

// 경영체 이미지 슬라이드
$.bizUtils.drawEntSwiper = function( dom, bzentyNo ) {
	
	let _createItem = function(o) {
		let r = $('<div class="swiper-slide h-100"></div>');
		r.append($.bizUtils.getEntImage(o, MODE.VIEW));
		return r;
	};

	let _create = function(rows) {
		let div = $('<div class="swiper Myswiper-1 overflow-hidden position-relative"></div>');
		div.append('<div class="swiper-wrapper h-100"></div>');
		div.append('<div class="swiper-pagination"></div>');
		div.append('<div class="swiper-button-next"><i class="icon-angle-right"></i></div>');
		div.append('<div class="swiper-button-prev"><i class="icon-angle-left"></i></div>');
		
		if (rows.length == 0) {
			div.find('.swiper-wrapper').append(_createItem({bzentyNo:bzentyNo}));
		}
		else {
			$.each(rows, function(i,row) {
				div.find('.swiper-wrapper').append(_createItem({bzentyNo:bzentyNo, rprsFileSn: row['sn']}));
			});
		}
		return $('<div class="app-swiper"></div>').append(div);
	};
	// 이미지파일 조회
	let data = $.ajaxUtil.ajaxDefault(
		getUrl('/usr/file/getListEntFile.do'),
		{docSeCd:'00', bzentyNo: bzentyNo}
	);
	if (data && 
		data.rows) {
		dom.append(_create(data.rows));
	}
	// 2023.08.18 이미지 없을 경우 랜덤이미지 처리
	else {
		dom.append(_create([]));
	}
	// Initialize Swiper
	new Swiper(".swiper.Myswiper-1", {							
		slidesPerView:1,
		pagination: {
			el: ".swiper.Myswiper-1 .swiper-pagination",
			clickable: true,   
		},
		navigation: {
			nextEl: ".swiper.Myswiper-1 .swiper-button-next",
			prevEl: ".swiper.Myswiper-1 .swiper-button-prev",
		},
	});
};

// 경영체 슬라이드카드
$.bizUtils.drawEntSlide = function( div, data, isPrint ) {

	div.addClass("card-box-slide1 mb-24px");
	div.append($.domUtil.getRow({
		items: [{colCls: 'col-12 col-md-6 app-image-slide',formatHtml: ''}
		       ,{colCls: 'col-12 col-md-6 app-info-card'  ,formatHtml: $.bizUtils.getEntInfo(data, MODE.VIEW)}
		]
	}));
	// 이미지슬라이드
	$.bizUtils.drawEntSwiper(div.find('.app-image-slide'), data['bzentyNo']);
};

// 설명항목 정의
//=============================================================================
$.fn.appEntDescript = function() {
	$(this).addClass("text-box-1 px-24px py-8px bg-primary-t5 mb-40px");
	$(this).append('<div class="row"></div>');
	$(this).find('.row').append('<div class="col-12"></div>');
	$(this).find('.col-12').append([
		'<i class="icon-pen-line cycle-icon mr-5px"></i>',
		'정보는 경영체 수기 작성한 내용이며, ',
		'그 외 정보는 한국평가데이터에서 수집한 각종 정보를 기반으로 제공하는것 으로, ', 
		'DB업데이트 시점 또는 평가 시점 등에 따라 실제와 차이가 있을 수 있으니 ',
		'참고용으로 활용하시기 바랍니다.'
	].join(''));
};

// 기업 기본정보 정의
//=============================================================================
$.fn.appEntInfoCard = function ( args ) {
	
	let options = $.extend(true, {
		cls    : false,
		mode   : MODE.VIEW,
		data   : false,
		params : false,
		updateYn: false,
		selectYn: false,
		buttons: []
	}, args);
	
	if (options.mode == MODE.VIEW) {
		options.buttons.push({
			id:    'btnPrint', 
			cls:   'bs-l btn-combi-2', 
			icon:  'icon-print', 
			value: '보고서 인쇄', 
			click: function() {
				window.print();
			}
		});
	}

	return $(this).appCard({
		cls: options.cls,
		// 정보데이터
		data: options.data,
		// 빈값텍스트
		defaultValue: STRING.NOINFO,
		// 제목설정
		titleOptions: {
			title:  '기업 기본정보',
			buttons: options.buttons,
		},
		// 칼럼목록
		items: [
			{colCls: 'col-6 col-xl-4', name:'bzentyNm'     ,label:'회사명'},
			{colCls: 'col-6 col-xl-4', name:'brno'         ,label:'사업자번호', formatter: $.formatUtil.toBizNo},
			{colCls: 'col-6 col-xl-4', name:'bzentyStleNm' ,label:'기업형태'},

			{colCls: 'col-6 col-xl-4', name:'bzentyTypeNm' ,label:'기업유형'},
			{colCls: 'col-6 col-xl-4', name:'fndnYmd'      ,label:'설립일', formatter: $.formatUtil.toKorDate},
			{colCls: 'col-6 col-xl-4', name:'bzentyScaleNm',label:'기업규모'},

			{colCls: 'col-6 col-xl-4', name:'tpbizNm'      ,label:'업종'},
			{colCls: 'col-6 col-xl-4', name:'hmpgAddr'     ,label:'홈페이지'},
			{colCls: 'col-6 col-xl-4', name:'rprsvNm'      ,label:'대표자'},

			{colCls: 'col-6 col-xl-4', name:'emlAddr'      ,label:'이메일'},
			{colCls: 'col-6 col-xl-4', name:'lctnAddr'     ,label:'소재지'},
			{colCls: 'col-6 col-xl-4', name:'rprsTelno'    ,label:'TEL/FAX', 
				formatter: function(v,o) {
					let a = [$.formatUtil.toPhone(v)];
					if (o['fxno']) 
						a.push($.formatUtil.toPhone(o['fxno']));
					return a.join(' / '); 
				}
			}
		]
	});
};

// 주요 투자정보 정의 (조회/등록)
//=============================================================================
$.fn.appEntInvtCard = function ( args ) {

	let options = $.extend(true, {
		cls    : false,
		mode   : MODE.VIEW,
		data   : false,
		params : false,
		updateYn: false,
		selectYn: false,
		title  : '주요 투자정보',
		icon   : '<img src="'+getUrl('/images/sub/iconimg1.svg')+'" alt="icon"/>',
		url    : getUrl('/usr/invest/ent/getListEntInvtAmt.do'),
		formId : 'invtForm',
		cardOptions: {
			boxCls   : 'shadow-box-1 p-32px',
			groupCls : 'grid-ratio-1',
			wrapCls  : 'form-area-box-input',
			autoload : true,
			title    : false
			
		}
	}, args);
	
	let _this = $(this);
	let _data = false;
	let _card = false;
	let _form = false;
	
	let _functions = {

		// 투자금액 목록생성
	    //--------------------------------------------------------//
		getGrid: function( code ) {
			let key = ENTLIST.INVT.list;
			if (code == CODE.INVT_SE.FUND)
				key = ENTLIST.IFND.list;
			else if (code == CODE.INVT_SE.ETC)
				key = ENTLIST.IETC.list;

			if (options.mode == MODE.VIEW &&
				options.data              &&
				options.data[key]         &&
				$.commUtil.empty(options.data[key]))
				return $.domUtil.getEmptyBox('- '+STRING.NOINFO, 'mt-24px');

			let opts = {
				id                : key+'Grid',
				name              : key,
				options           : {
					id            : '#'+key+'Grid',
					name          : key,
					mode          : 'LIST',
					idField       : 'sn',
					title         : '투자금액정보',
					listOptions   : {
						tableCls  : 'app-fs14px',
						headCls   : "bs-1 ts-m",
						bodyCls   : "bs-1 ts-m t-t-c",
						headthCls : ""
					},
					createCallback: function(target) {
						$(target).find('.app-invt-step').click(function() {
							popup.openInvestStep();
							return false;
						});
					}
				}
			};
			let footer = [
				'※ 투자단계는 <em class="text-primary fw-600">누적 투자금액(누계) 기준에 따른 분류</em>입니다.',
				'<br>※ 투자받은 금액 중 <em class="text-primary fw-600">2020년 이후</em> 농식품모태펀드에서 투자받은 금액은 ',
				'농업정책보험금융원에서 <em class="text-primary fw-600">주기적으로</em> 입력합니다.',
				'<br><em class="pl-14px">경영체가</em> 자율적으로 사전에 입력할 수 있지만 금액이 중복하여 부정확한 정보가 노출될 수 있습니다.'
			].join('');

			if (options.mode == MODE.VIEW) {
				opts.options['footer'] = footer;
				$.extend(true, opts.options.listOptions, {
					mergeColumn: 'invtYr',
					bodytrCls: "",
					colgroup : ['10%','20%','20%','*'],
					columns  : [
						{cls:'app-c', name:'invtYr'    , label:'년도'},
						{cls:'app-r', name:'summAmt'   , label:'투자받은금액<em class="text-primary pl-5px fw-600">(누계)</em>', 
							formatter: function(v,r) {
								let s1 = $.formatUtil.toBillion(r['invtAmt']);
								let s2 = $.formatUtil.toBillion(r['summAmt']);
								return s1+'<em class="text-primary pl-5px">('+s2+')</em>';
							}
						},
						{cls:'app-c', name:'invtStepNm', label:'투자단계 <a class="icon-info-circle ml-5px text-primary app-invt-step"></a>'},
						{cls:'app-l', name:'invtSeCd', label:'비고', 
							formatter: function(v) {
								if (v == CODE.INVT_SE.FUND)
									return '농식품 모태펀드 투자금액';
								return '';
							}
						}
					]
				});
			}
			else if (code == CODE.INVT_SE.FUND) {
				$.extend(true, opts.options.listOptions, {
					bodytrCls: "",
					colgroup : ['10%','*'],
					columns  : [
						{cls:'app-c', name:'invtYr'    , label:'년도'},
						{cls:'app-r', name:'invtAmt'   , label:'투자받은금액(원)', formatter: $.formatUtil.toKorMoney}
					]
				});
			}
			else {
				opts.options['footer'] = footer;
				$.extend(true, opts.options.listOptions, {
					emptyText  : false,
					editable   : true,
					editDefData: {invtSeCd: code},
					editDefCnt : 0,
					editMinCnt : 0,
					editMaxCnt : 3,
					editBoxCls : 'ele-icon-box app-full',
					colgroup   : ['10%','*'],
					columns    : [
						{cls:'app-c', name:'invtYr', label:'년도', 
							hidden :[{type:'hidden', name:'sn'}],
							input  : {type:'text'  , name:'invtYr', maxlength: 4, clear: true, number: true, cls: 'app-full'}
						},
						{cls:'app-r', name:'invtAmt', label:'투자받은금액(원)', 
							input  : {type:'text', name:'invtAmt', maxlength:15, clear: true, money: true, cls: 'app-full text-end'}, unit: '&nbsp;원'
						}
					]
				});
			}
			return opts;
		},
		// 위와동일 클릭 처리
	    //--------------------------------------------------------//
		doSameClick: function() {
			$('#picNm'   ).val(options.data['rprsvNm']);
			$('#picTelno').val($.formatUtil.toPhone(options.data['rprsTelno']));
		},
		// 칼럼목록 생성
	    //--------------------------------------------------------//
		getItems: function(c) {
			let items = [
				{cls: 'col-12', name:'mainBizCn', label:'주요사업', must: true, valueBox: true, labelIcon: 'icon-pen-line cycle-ver-1',
					input: {id:'mainBizCn', name:'mainBizCn', type:'text', maxlength: 100, clear: true, placeholder: 'ex 식품제조 가공업, 건강기능식품 개발 전문 업체, 지능형 농업기계 솔루션 등'}
				},
				{cls: 'col-12', name:'coreItmCn', label:'핵심아이템', must: true, valueBox: true, labelIcon: 'icon-pen-line cycle-ver-1',
					input: {id:'coreItmCn', name:'coreItmCn', type:'text', maxlength: 100, clear: true, placeholder: 'ex 헛개나무 추출분말, 즉석 간편식, Daily 배송 시스템, 수제맥주, 통합 제어 시스템 등'}
				},
				{cls: 'col-12', name:'bizCn', label:'투자요소', must: true, valueBox: true, labelIcon: 'icon-pen-line cycle-ver-1',
					input: {id:'bizCn', name:'bizCn', type:'textarea', rows: 2, cols: 60, maxlength: 100, clear: true, placeholder: 'ex OO음료 원료 공급, 독보적인 기술력, 자체 식품제조를 통해 신선 제품 배송 가능, OO지역 배송시스템 구축, 최신 트렌드를 반영한 OO제품 개발, 기술 차별화에 의한 높은 제조 마진 및 원가절감 등'}
				}
			];
			// 조회인 경우 단일 그리드
			if (options.mode == MODE.VIEW) {
				items.push(
					{cls: 'col-12', name:'invtList', labelIcon: false, must: false, grid: _functions.getGrid(), label:'투자받은 금액'}
				);
			}
			else {
				items.push(
					{cls: 'col-12', name:'invtFndList', labelIcon: false, must: false, grid: _functions.getGrid(CODE.INVT_SE.FUND), label:'농식품모태펀드 투자금액'},
					{cls: 'col-12', name:'invtEtcList', labelIcon: 'icon-pen-line cycle-ver-1', grid: _functions.getGrid(CODE.INVT_SE.ETC ), label:'기타 투자금액'}
				);
			}
			items.push(
				{cls: 'col-6 col-xl-4', name:'picNm', label:'담당자', must: true, valueBox: true, 
					labelIcon : 'icon-pen-line cycle-ver-1',
					labelCls  : 'd-flex',
					labelExtra: function(c) {
						let box = $('<input type="checkbox" id="sameYn'+c.name+'">');
						box.click(_functions.doSameClick);
						let lbl = $('<label for="sameYn'+c.name+'" class="fs-14px fs-inherit" style="font-size: inherit;">위와 동일</label>');
						let chk = $('<span class="check-radio-box ml-5px"></span>');
						chk.append(box);
						chk.append(lbl);
						return chk;
					},
					input: {id:'picNm', name:'picNm', type:'text', maxlength: 10, clear: true, placeholder: '담당자 성함을 입력헤 주세요.'}
				},
				{cls: 'col-6 col-xl-4', name:'picTelno', label:'담당자연락처', must: true, valueBox: true, labelIcon: 'icon-pen-line cycle-ver-1', formatter: $.formatUtil.toPhone,
					input: {id:'picTelno', name:'picTelno', type:'text', maxlength: 14, clear: true, placeholder: '담당자 연락처를 작성해 주세요.', valid: {method:'phone'}, valueFormatter: $.formatUtil.toPhone, phone: true}
				}
			);
			// 조회인 경우 모두 valueBox로 전환
			if (options.mode == MODE.VIEW) {
				$.each(items, function(i,item) {
					item['must'      ] = false;
					item['input'     ] = false;
					item['hidden'    ] = false;
					item['labelCls'  ] = false;
					item['labelExtra'] = false;
				});
			}
			// 등록인 경우
			else {
				$.each(items, function(i,item) {
					item['valueBox'  ] = false;
				});
			}
			return items;
		}
	};
	
	_data = $.extend({gridData:{}}, options.data);
	// 조회인 경우 단일 그리드
	if (options.mode == MODE.VIEW) {
		_data['gridData'][ENTLIST.INVT.list] = options.data[ENTLIST.INVT.list];
	}
	else {
		_data['gridData'][ENTLIST.IFND.list] = options.data[ENTLIST.IFND.list];
		_data['gridData'][ENTLIST.IETC.list] = options.data[ENTLIST.IETC.list];
	}
	
	_card = $('<div></div>').appFormCard({
		formId      : options.formId,
		data        : _data,
		cls         : options.cls,
		boxCls      : options.cardOptions.boxCls  ,
		groupCls    : options.cardOptions.groupCls,
		wrapCls     :(options.mode == MODE.VIEW ? options.cardOptions.wrapCls : 'form-area-box') ,
		autoload    : options.cardOptions.autoload,
		title       : options.cardOptions.title   ,
		defaultValue: STRING.NOINFO,
		items       : [{items: _functions.getItems()}],
		// 추가검증항목
		validates   : [
			{key: 'invtYr'    , method: 'required', message: '[투자정보] 년도를 입력하세요.'    },
			{key: 'invtAmt'   , method: 'required', message: '[투자정보] 투자금액을 입력하세요.'}
		]
	});
	
	_this.append($.domUtil.getTitleBox({title: options.title, icon: options.icon}));
	_this.append(_card);
	
	_form = _card.getForm();
	
	// 저장항목 검증 및 데이터 정제
	this.doValidate = function() {
		let v = _card.doValidate();
		if (!v)
			return false;
		
		let lst = [];
		if (v['invtEtcList']) {
			$.each(v['invtEtcList'], function(i,o) {
				o['invtAmt'] = $.commUtil.nvlInt(o['invtAmt']);
				lst.push(o);
			});
		}
		return {
		    "mainBizCn"  : v["mainBizCn"  ],
		    "coreItmCn"  : v["coreItmCn"  ],
		    "bizCn"      : v["bizCn"      ],
		    "picNm"      : v["picNm"      ],
		    "picTelno"   : v["picTelno"   ].replace(/[^0-9]/g,""),
			"invtList"   : lst
		};
	};
	
	return this;
};

// 특허 및 상표권 보유현황 정의 (조회/등록)
//=============================================================================
$.fn.appEntPtntCard = function ( args ) {
	
	let options = $.extend(true, {
		cls          : false,
		mode         : MODE.VIEW,
		form         : false,
		data         : false,
		params       : false,
		updateYn     : false,
		selectYn     : false,
		kodata       : (args.data && args.data['ptntKodataYn'] == 'Y'),
		title        : '특허 및 상표권 보유현황',
		icon         : '<img src="'+getUrl('/images/sub/iconimg2.svg')+'" alt="icon"/>',
		formId       : false,
		cardOptions  : {
			boxCls   : 'shadow-box-1 px-32px pb-24px',
			groupCls : 'grid-ratio-1',
			wrapCls  : 'form-area-box-input',
			autoload : true,
			title    : false
		}
	}, args);
	
	let _this = $(this);
	let _data = false;
	let _card = false;
	let _form = false;

	let _functions = {
		
		// 메인제목 옵션 가져오기
	    //--------------------------------------------------------//
		getTitle: function() {
			return {
				title  : options.title, 
				icon   : options.icon, 
				buttons: _functions.getMoreButton()
			};
		},

		// 더보기 버튼
	    //--------------------------------------------------------//
		getMoreButton: function() {
			if (options.mode == MODE.VIEW)
				return [{
					id   : 'btnPtnt', 
					icon : 'icon-plus', 
					value: '더보기', 
					click: _functions.doOpen
				}];
			return false;
		},

		// 특허 상표권현황 목록 생성
	    //--------------------------------------------------------//
		getGrid: function(key, title) {

			// 조회모드에서 빈값인 경우
			if (options.mode == MODE.VIEW &&
				options.data              &&
				options.data[key]         &&
				$.commUtil.empty(options.data[key]))
				return $.domUtil.getEmptyBox('- '+STRING.NOINFO, 'mt-24px');

			let opts = {
				id                : key+'Grid',
				name              : key,
				options           : {
					id            : '#'+key+'Grid',
					name          : key,
					mode          : 'LIST',
					idField       : 'sn',
					title         : title,
					listOptions   : {
						tableCls  : 'app-fs14px',
						headCls   : "bs-1 ts-xl",
						bodyCls   : "bs-1 ts-m t-t-c",
						headthCls : "border-top-0",
						headtrCls : "",
						bodytrCls : "",
						emptyText : '검색된 내용이 없습니다.',
						colgroup  : _functions.getColgroup(),
						columns   : _functions.getColumns()
					}
				}
			};
			if (!options.kodata) {
				$.extend(true, opts.options.listOptions, {
					emptyText  : false,
					editable   : true,
					editDefData: {dataSeCd: CODE.DATA_SE.MANUAL},
					editDefCnt : 0,
					editMinCnt : 0,
					editMaxCnt : 100
				});
			}
			return opts;
		},

		// 지적재산권 구분 가져오기
	    //--------------------------------------------------------//
		getStore: function(c) {
			return $.ajaxUtil.ajaxDefault(getUrl('/com/common/getComboCode.do'), {
				upCdId: CODE.ILLT_REG_SE.code
			});
		},
		// 칼럼너비생성
	    //--------------------------------------------------------//
		getColgroup: function() {
			if (options.kodata) {
				return [ '78px','100px','150px','*','160px','120px'];
			}
			else {
				return ['118px','130px','200px','*','170px','140px'];
			}
		},		
		// 칼럼항목생성
	    //--------------------------------------------------------//
		getColumns: function() {
			if (options.kodata) {
				return [
					{cls:'app-c', name:'patentSeNm'  , label:'구분'},
					{cls:'app-c', name:'applnm'      , label:'출원인'},
					{cls:'app-l', name:'patntrtMan'  , label:'특허권자'},
					{cls:'app-l', name:'nm'          , label:'명칭'},
					{cls:'app-c', name:'illtRegNo'   , label:'등록번호'},
					{cls:'app-c', name:'patentRegYmd', label:'등록일자', formatter: $.formatUtil.toDashDate}
				];
			}
			else {
				let store = _functions.getStore();
				return [
					{cls:'app-c', name:'patentSeNm'  , label:'구분', 
						hidden :[{type:'hidden', name:'sn'}
						        ,{type:'hidden', name:'dataSeCd'}],
						input  : {type:'select', name:'patentSeCd', cls: 'app-full', options:{type:'static', rows: store}}
					},
					{cls:'app-c', name:'applnm'      , label:'출원인'  , input: {type:'text'  , maxlength: 200, clear: true, cls: 'app-full'}},
					{cls:'app-l', name:'patntrtMan'  , label:'특허권자', input: {type:'text'  , maxlength: 200, clear: true, cls: 'app-full'}},
					{cls:'app-l', name:'nm'          , label:'명칭'    , input: {type:'text'  , maxlength: 500, clear: true, cls: 'app-full'}},
					{cls:'app-c', name:'illtRegNo'   , label:'등록번호', input: {type:'text'  , maxlength:  20, clear: true, cls: 'app-full'}},
					{cls:'app-c', name:'patentRegYmd', label:'등록일자', input: {type:'text'  , maxlength:  10, cls: 'app-full', calendar: true}}
				];
			}
		},
		// 카드생성
	    //--------------------------------------------------------//
		getCard: function() {

			if (options.mode == MODE.VIEW) {
				let items = options.data['ptntSumList'];
				$.each(items, function(i, item) {
					item['colCls'    ] = 'col';
					item['formatHtml'] = function() {
						return $('<div class="card-box-nomar1 hover-ani-none shadow-box-1 px-40px py-24px"></div>').append(
							$.domUtil.getRow({
								items: [
									 '<p class="txt1"><img src="'+getUrl('/images/app/ILLT_REG_'+item['code'])+'.svg"> '+item['text']+'</p>'
									,'<p class="txt2">'+item['count']+'<em>건</em></p>'
								]
							})
						);
					};
				});
				return $('<div></div>').appCard({titleOptions: false, cls: options.cls, items: items});
			}
			else {
				let obj  = ENTLIST.PTNT;
				_data = $.extend({gridData: {}}, options.data);
				_data['gridData'][obj.list] = options.data[obj.list];
				return $('<div></div>').appFormCard({
					form        : options.form,
					cls         : options.cls,
					boxCls      : options.cardOptions.boxCls  ,
					groupCls    : options.cardOptions.groupCls,
					wrapCls     :(options.mode == MODE.VIEW ? options.cardOptions.wrapCls : 'form-area-box') ,
					autoload    : options.cardOptions.autoload,
					title       : options.cardOptions.title   ,
					defaultValue: STRING.NOINFO,
					data        : _data,
					items       : [{
						items   : [{
							cls   : 'col-12', 
							name  : obj.list, 
							must  : true,
							//label : obj.title, 
							grid  : _functions.getGrid(obj.list, obj.title)
						}]
					}],
					// 추가검증항목
					validates   : [
						{key: 'patentSeCd'  , method: 'required', message: '['+ENTLIST.LWST.title+'] 구분을 선택하세요.'    },
						{key: 'applnm'      , method: 'required', message: '['+ENTLIST.PTNT.title+'] 출원인을 입력하세요.'  },
						//{key: 'patntrtMan'  , method: 'required', message: '['+ENTLIST.PTNT.title+'] 특허권자를 입력하세요.'},
						//{key: 'nm'          , method: 'required', message: '['+ENTLIST.PTNT.title+'] 명칭을 입력하세요.'    },
						//{key: 'illtRegNo'   , method: 'required', message: '['+ENTLIST.PTNT.title+'] 등록번호를 입력하세요.'},
						//{key: 'patentRegYmd', method: 'required', message: '['+ENTLIST.PTNT.title+'] 등록일자를 입력하세요.'}
					]
				});
			}
		},
		// 특허 상표권현황 팝업 오픈
	    //--------------------------------------------------------//
		doOpen: function() {
			$('<div></div>').appPopupEntPtnt({
				params: options.params
			});
			return false;
		}
	};
	
	_card = _functions.getCard();
	
	_this.append($.domUtil.getTitleBox(_functions.getTitle()));
	_this.append(_card);
	
	if (options.mode != MODE.VIEW) {
		_form = _card.getForm();
	}
	
	// 저장항목 검증
	this.doValidate = function() {
		if (options.mode == MODE.VIEW) {
			return false;
		}
		let v = _card.doValidate();
		if (!v)
			return false;
		
		if (v['ptntList']) {
			$.each(v['ptntList'], function(i,o) {
				o['patentRegYmd'] = o['patentRegYmd'].replace(/[^0-9]/g,"");
			});
		}
		return {
			"ptntList": v["ptntList"]
		};
	};
	
	return this;
};

// 재무정보 정의 (조회/등록)
//=============================================================================
$.fn.appEntFnnrCard = function ( args ) {
	
	let options = $.extend(true, {
		cls          : false,
		mode         : MODE.VIEW,
		data         : false,
		dataSe       : false,
		params       : $.extend(args.params, {dataSeCd: args.dataSe}),
		updateYn     : false,
		selectYn     : false,
		kodata       : (args.data && args.data['fnnrKodataYn'] == 'Y'),
		title        : '결산재무정보',
		icon         : '<img src="'+getUrl('/images/sub/iconimg3.svg')+'" alt="icon"/>',
		formId       : 'fnnr'+args.dataSe+'Form',
		comment      : false,
		buttons      : false,
		cardOptions  : {
			boxCls   : 'shadow-box-1 p-32px',
			groupCls : 'grid-ratio-1',
			wrapCls  : 'form-area-box-input',
			autoload : true,
			title    : false
		},
		cardObject   : {
			FNLT     : {
				id   : 'appFnlt',
				name : 'fnnrFnlt',
				icon : 'icon-clipboard-list',
				type :  CODE.FNNR_SE.FNLT,
				title: '재무상태표'
			},
			PLOS     : {
				id   : 'appPlos',
				name : 'fnnrPlos',
				icon : 'icon-document-list',
				type :  CODE.FNNR_SE.PLOS,
				title: '손익계산서'
			}
		}
	}, args);
	
	let _this    = $(this);
	let _card    = false;
	let _form    = false;
	let _charts  = {};
	let _objects = [];
	let _data    = $.extend({gridData: {}}, options.data);

	let _functions = {
		
		// 빈값표시여부 체크
	    //--------------------------------------------------------//
		isEmpty: function( data ) {
			// 빈값인 경우
			if ($.commUtil.empty(data['rows'])) {
				// 주요재무정보인 경우
				if (options.dataSe == CODE.DATA_SE.KODATA) {
					return true;
				}
				// 조회모드에서 빈값인 경우
				else if (options.mode == MODE.VIEW) {
					return true;
				}
			}
			return false;
		},

		// 편집여부 체크
	    //--------------------------------------------------------//
		isEditable: function() {
			if (options.mode   != MODE.VIEW &&
				options.dataSe != CODE.DATA_SE.KODATA) {
				return true;
			}
			return false;
		},

		// 메인제목 옵션 가져오기
	    //--------------------------------------------------------//
		getTitle: function() {
			
			let cls = false;
			
			// 주요재무정보인 경우
			if (options.dataSe == CODE.DATA_SE.KODATA) {
				options.title   = '결산재무정보';
				options.buttons = [{
					id    : 'btnFnnrFnlt',
					icon  : options.cardObject.FNLT.icon,
					value : options.cardObject.FNLT.title,
					data  : {code: CODE.FNNR_SE.FNLT,
					         secd: options.params['dataSeCd'],
							 irno: options.params['irNo'    ]},
					click : _functions.doOpen
				},{
					id    : 'btnFnnrPlos',
					icon  : options.cardObject.PLOS.icon,
					value : options.cardObject.PLOS.title,
					data  : {code: CODE.FNNR_SE.PLOS,
					         secd: options.params['dataSeCd'],
							 irno: options.params['irNo'    ]},
					click : _functions.doOpen
				}];
				
				// 2023.09.08 버튼숨김처리
				//if (options.mode != MODE.VIEW && 
				//    options.updateYn == 'Y') {
				//	options.buttons.push(' ');
				//	options.buttons.push({
				//		cls   : 'bs-m bs-md-l btn-primary',
				//		id    : 'btnFnnrManual',
				//		icon  : 'icon-plus',
				//		value : '업체입력 재무정보 입력하기',
				//		click : _functions.doForm
				//	});
				//	cls = 'flex-grow-0';
				//}
			}
			else {
				options.title   = '업체입력 재무정보';
				options.comment = '※ 한국평가데이터와는 무관하게 경영체에서 수기로 작성한 내용임을 참고 바랍니다.';
			}
			return {
				title  : options.title, 
				icon   : options.icon, 
				buttons: options.buttons,
				comment: options.comment,
				addCls : cls
			};
		},
				
		// 재무상태표/손익계산서 세부내역 팝업 오픈
	    //--------------------------------------------------------//
		doOpen: function() {
			let code  = $(this).data('code');
			let seCd  = $(this).data('secd');
			let irNo  = $(this).data('irno');
			let title = $(this).val();
			// 세부내역 팝업
			$('<div></div>').appPopupEntFnnr({
				title  : title, 
				params : {fnnrSeCd: code, dataSeCd: seCd, irNo: irNo}
			});
			return false;
		},
				
		// 추가재무정보 입력하기
	    //--------------------------------------------------------//
		doForm: function() {
			$('input[name="fnnrYr"]').eq(0).focus();
			return false;
		},
		
		// 데이터 가져오기
	    //--------------------------------------------------------//
		getData: function( type ) {
			let years = false;
			let rows  = false;
			let rkey  = false;
			let ekey  = false;
			let edits = false;
			if (CODE.FNNR_SE.FNLT == type) {
				rkey = (CODE.DATA_SE.KODATA == options.dataSe ? 'fnnrKoFntl' : 'fnnrMnFntl');
				ekey = 'fnnrFntlList';
			}
			else {
				rkey = (CODE.DATA_SE.KODATA == options.dataSe ? 'fnnrKoPlos' : 'fnnrMnPlos');
				ekey = 'fnnrPlosList';
			}
			if (options.data[rkey]) {
				years = options.data[rkey]['yearList'];
				rows  = options.data[rkey]['fnnrList'];
			}
			if (_functions.isEditable()) {
				edits = options.data[ekey];
			}
			return {
				years: years,
				rows : rows,
				edits: edits
			};
		},
		
		// 차트 데이터 가져오기
	    //--------------------------------------------------------//
		getChartData: function( type ) {
			let data     = _functions.getData(type);
			let years    = data['years'];
			let rows     = data['rows' ];
			if (years.length == 0)
				return false;
			let datasets = [];
			// 랜덤 색상
			let colors   = shuffleColors(COLORS.STANDARD);
			let _index   = rows.length-1;
			$.each(rows, function(j,row) {
				let dataset = {
					backgroundColor : colors[j],
					label           : row['fnnrNm'], 
					order           : _index, 
					data            : [],
				};
				if (type == CODE.FNNR_SE.FNLT) {
					if      (j == 2) $.extend(dataset, {type:'line', borderColor:colors[j]});
				}
				else {
					if      (j == 1) $.extend(dataset, {type:'line', borderColor:colors[j]});
					else if (j == 2) $.extend(dataset, {type:'line', borderColor:colors[j], fill:true});
				}
				$.each(years, function(i) {
					dataset.data.push(row['fnnrAmt'+(i+1)]);
				});
				datasets.push(dataset);
				_index--;
			});
			return {
				colors   : colors,
				labels   : years,
				datasets : datasets,
				lastIdx  : (years.length-1)
			};
		},
		// 금액 단위 가져오기
	    //--------------------------------------------------------//
		getUnit: function( cls ) {
			let btm = $('<div class="table-bottom-1"></div>');
			if (cls)
				btm.addClass(cls);
			btm.append($.domUtil.getRow({items: ['<p class="txt1 text-end">단위 : '+STRING.UNIT.KKRW+'</p>']}));
			return btm;
		},

		// 설명글 가져오기
	    //--------------------------------------------------------//
		getNotice: function() {
			let btm = $('<div class="text-box-1 p-16px bg-primary-t5"></div>');
			btm.append($.domUtil.getRow({colCls:'col fs-12px',items: [
				'<i class="fs-26px icon-octagon-exclamation text-primary mr-10px"></i>',
				[
					'상기 정보는 한국평가데이터에서 수집한 각종 정보를 기반으로 제공하는 것으로 ',
					'DB업데이트 시점 또는 평가 시점 등에 따라 실제와 차이가 있을 수 있습니다.',
					'정보 업데이트가 필요하시면 아래 링크 이용 부탁드립니다.<br>',
					'<a href="https://new.cretop.com/PL/CC/PLCC610M1?h=1678253104423" target="_blank">',
					'https://new.cretop.com/PL/CC/PLCC610M1?h=1678253104423',
					'</a>'
				].join('')
			]}));
			return btm;
		},
		// 차트생성
	    //--------------------------------------------------------//
		drawChart: function( id, data ) {
			
			// Chart Canvas Element
			let element = document.getElementById(id);
			// DataLabels 플러그인 Register
			Chart.register(ChartDataLabels);
			// 이전 Chart 객체 Destroy
			if (_charts[id])
				_charts[id].destroy();
			// Chartjs 의 Chart 생성
			_charts[id] = new Chart(element, {
			    type: 'bar',
				data: data,
			    options: {
			    	plugins: {
				        legend: {
				        	display: true,
							position: 'bottom'
				        },
						datalabels: {
		    				color:   'black',
		    				display: true,
		    				font: {size:14, weight: 'bold'},
							anchor: 'end',
		                    align: 'start',
							offset: -20,
							formatter: function(value, context) {
								if (context.datasetIndex == data.lastIdx)
									return value;
								return '';
							}
		  				}
			        },
					responsive: true,
					scales: {
						x: {stacked: true},
	      				y: {stacked: true}
	    			},
				}
			});
		},

		// 그래프 닫기 클릭 이벤트 처리
	    //--------------------------------------------------------//
		doClickClose: function() {
			if ($(this).hasClass('active')){
				$(this).html('<i class="icon-clipboard-list mr-5px"></i>그래프 닫기');
				$(this).removeClass('active');
				$(this).closest('.app-target-table').next().find('.btnFc-2-target').show();
			} else {
				$(this).html('<i class="icon-clipboard-list mr-5px"></i>그래프 열기');
				$(this).addClass('active');
				$(this).closest('.app-target-table').next().find('.btnFc-2-target').hide();
			}
		},

		// 그래프 닫기 버튼 생성
	    //--------------------------------------------------------//
		getSubButtons: function(d) {
			// 수정인 경우엔 주요재무정보만 버튼존재
			if (_functions.isEditable())
				return false;
			if (!d.rows || d.rows.length == 0)
				return false;
			return [{
				cls   : 'bs-m btn-black btnFc-2',
				icon  : 'icon-clipboard-list', 
				value : '그래프 닫기',
				click : _functions.doClickClose
			}];
		},

		//  그리드 칼럼제목 가져오기
	    //--------------------------------------------------------//
		getLabels: function( type ) {
			// 재무상태
			if (type == CODE.FNNR_SE.FNLT) {
				return ['자산총계(천원)', '부채총계(천원)', '자본총계(천원)'];
			}
			// 손익계산서
			else {
				return ['매출액(천원)', '영업이익(천원)', '당기순이익(천원)'];
			}
		},

		// 그리드 데이터 가져오기
	    //--------------------------------------------------------//
		getGridData: function( type ) {

			let data     = _functions.getData(type);
			let years    = data['years'];
			let rows     = data['rows' ];
			let labels   = _functions.getLabels(type);
			let colgroup = [];
			let columns  = [];
			
			// 편집형인 경우
			if (_functions.isEditable()) {
				rows     = data['edits'];
				colgroup = ['25%','25%','25%','25%'];
				columns.push(
					{cls: 'app-c', name:'fnnrYr', label: '결산년도', 
						hidden :[{type:'hidden', name:'fnnrSeCd'}
								,{type:'hidden', name:'dataSeCd'}],
						input  : {type:'text', name:'fnnrYr', maxlength: 4, clear: true, cls: 'app-full', number: true}
					}
				);
				
				$.each(labels, function(i, str) {
					columns.push(
						{cls: 'app-r', name:'fnnrAmt'+(i+1), label: str, 
							hidden : {type:'hidden', name:'fnnrAcntCd'+(i+1)},
							input  : {type:'text', name:'fnnrAmt'+(i+1), maxlength:20, clear: true, cls: 'app-full text-end', minus: true}, 
							unit   : '&nbsp;'+STRING.UNIT.KKRW
						}
					);
				});
			}
			// 조회인 경우
			else {
				if (years.length == 0)
					return {
						rows    : false,
						columns : false,
						colgroup: false
					};
				colgroup = ['200px','*'];
				columns.push({cls: 'app-c', name: 'fnnrNm', label: '구분'});
				$.each(years, function(i,year) {
					columns.push({cls: 'app-r txt1', name: 'fnnrAmt'+(i+1), label: year, formatter: $.formatUtil.toUnitKilo});
				});
			}
			return {
				rows    : rows,
				columns : columns,
				colgroup: colgroup
			};
		},

		// 그리드 옵션 가져오기
	    //--------------------------------------------------------//
		getGrid: function( opts ) {
			
			// 빈값인 경우
			if (_functions.isEmpty(opts.data))
				return $.domUtil.getEmptyBox('- '+STRING.NOINFO, 'mt-24px');

			// 그리드 데이터 정의
			_data['gridData'][opts.name] = opts.data['rows'] || [];

			let gridOptions = {
				id                : opts.gridId,
				name              : opts.name,
				options           : {
					id            : '#'+opts.gridId,
					name          : opts.name,
					mode          : 'LIST',
					idField       : 'fnnrCd',
					title         : opts.title,
					listOptions   : {
						gridCls   : "table-box-1 m-overbox",
						tableCls  : "app-fs14px",
						headCls   : "bs-1 ts-l",
						bodyCls   : "bs-1 t-t-r ts-m",
						headthCls : "",
						headtrCls : "",
						bodytrCls : "",
						emptyText : '검색된 내용이 없습니다.',
						colgroup  : opts.data['colgroup'] || [],
						columns   : opts.data['columns' ] || []
					}
				}
			};
			// 편집형인 경우
			if (_functions.isEditable()) {
				$.extend(true, gridOptions.options.listOptions, {
					emptyText  : false,
					editable   : true,
					editDefData: {dataSeCd:CODE.DATA_SE.MANUAL, fnnrSeCd: opts.type},
					editDefCnt : 0,
					editMinCnt : 0,
					editMaxCnt : 3,
					editBoxCls : 'ele-icon-box flex-b200px'
				});
			}
			return gridOptions;
		},

		// 소제목 옵션 생성
	    //--------------------------------------------------------//
		getSubTitle: function(c, d) {
			return $.domUtil.getTitleBox({
				title    : c.label,
				titleBox : 'table-top-1',
				icon     : '<i class="icon-user-circle cycle-ver-1"></i>',
				buttons  : _functions.getSubButtons(d)
			})
		},

		// 카드항목생성
	    //--------------------------------------------------------//
		getItems: function() {
			let items = [];
			$.each(options.cardObject, function(k, o) {
				let obj = $.extend({}, o, {
					name   : o['name'] + 'List',
					id     : o['id'] + options.dataSe,
					chartId: o['id'] + options.dataSe + 'Chart',
					gridId : o['id'] + options.dataSe + 'Grid',
					dataSe : options.dataSe,
					// rows / colgroup / columns
					data   : _functions.getGridData( o.type )
				});
				_objects.push(obj);

				items.push({
					must           : true,
					cls            : 'col-12 app-target-table', 
					name           : obj.gridId, 
					label          : obj.title, 
					labelFormatter : function(c) { return _functions.getSubTitle(c, obj.data); },
					grid           : _functions.getGrid(obj)
				});
				if (!$.commUtil.empty(obj.data['rows'])) {
					items.push({
						must  : true,
						cls   : 'col-12 app-target-table', 
						name  : obj.chartId, 
						chart : {name: obj.chartId, comment: _functions.getUnit(), cls: 'btnFc-2-target'}
					});
				}
			});
			return items;
		},
		// 영역 생성
	    //--------------------------------------------------------//
		create: function() {
			
			// 제목 생성
			_this.append($.domUtil.getTitleBox(_functions.getTitle()));
			
			let items = _functions.getItems();
			
			_card = $('<div></div>').appFormCard({
				formId      : options.formId,
				data        : _data,
				cls         : options.cls,
				boxCls      : options.cardOptions.boxCls  ,
				groupCls    : options.cardOptions.groupCls,
				wrapCls     :(options.mode == MODE.VIEW ? options.cardOptions.wrapCls : 'form-area-box') ,
				autoload    : options.cardOptions.autoload,
				title       : options.cardOptions.title   ,
				defaultValue: STRING.NOINFO,
				items       : [{items: items}],
			});
			_this.append(_card);

			if (options.mode   != MODE.VIEW &&
				options.dataSe == CODE.DATA_SE.KODATA) {
				_this.find('.shadow-box-1:last').append(_functions.getNotice());
			}
			_form = _card.getForm();
		},

		// 차트 생성
	    //--------------------------------------------------------//
		createChart: function() {
			$.each(_objects, function(k, o) {
				let chartOptions = _functions.getChartData( o['type'] );
				if (chartOptions) {
					// 차트 생성
					_functions.drawChart(o['chartId'], chartOptions);
				}
			});
		}
	};
	
	// 차트 생성
	this.createChart = function() {
		_functions.createChart();
	};
		
	// 저장항목 검증
	this.doValidate = function() {
		let gridValidate = {};
		$.each(_objects, function(k, o) {
			let labels = _functions.getLabels(o.type);
			gridValidate[o.name] = function( row, idx, tr ) {
				// 빈값 체크
				if (!$.formUtil.isEmptyCheck(tr, 'fnnrYr', '['+o.title+'] 결산년도를 입력하세요.'))	
					return false;
				for (let i = 0; i < labels.length; i++) {
					if (!$.formUtil.isEmptyCheck(tr, 'fnnrAmt'+(i+1), '['+o.title+'] '+$.commUtil.getPostKor(labels[i],1)+' 입력하세요.')) {
						return false;
					}
					// 금액 포맷제거
					row['fnnrAmt'+(i+1)] = $.commUtil.nvlInt(row['fnnrAmt'+(i+1)]);
				}
				return true;
			};
		});
		let v = _card.doValidate(gridValidate);
		if (!v)
			return false;
			
		let lst = [];
		lst = lst.concat(v["fnnrFnltList"] || []);
		lst = lst.concat(v["fnnrPlosList"] || []);

		return {
			"fnnrList": lst
		};
	};
		
	_functions.create();
	
	return this;
};

// 정부지원사업 이력 정의 (조회/팝업등록)
//=============================================================================
$.fn.appEntSptHstCard = function ( args ) {
	
	let options = $.extend(true, {
		cls    : false,
		mode   : MODE.VIEW,
		data   : false,
		params : false,
		updateYn: false,
		selectYn: false,
		title  : '정부지원사업 이력',
		icon   : '<img src="'+getUrl('/images/sub/iconimg4.svg')+'" alt="icon"/>',
		url    : getUrl('/usr/invest/ent/getListEntOthsptHstSummary.do'),
		saveUrl: getUrl('/usr/mypage/ent/saveEntOthsptHst.do'),
	}, args);
	let _this = $(this);

	let _functions = {
		// 유관기관별 카드버튼 생성
	    //--------------------------------------------------------//
		getCard: function( o ) {
			let code  = o['code' ];
			let count = o['count'];
			let label = '기타지원이력';
			if (code != '99') {
				label = [
					'<img src="'+getUrl('/images/app/CRDNS_'+code+'.svg')+'" alt="ent"/>'
				   ,'<img src="'+getUrl('/images/app/CRDNS_'+code+'hover.svg')+'" class="hover" alt="ent"/>'
				].join('');
			}
			let icon = $('<div class="card-box-nomar2 shadow-box-1"></div>');
			// 2023.08.18 LSH 기타지원이력 메달 제외
			if (code != '99') {
				icon.append('<span class="medal"><img src="'+getUrl('/images/app/CRDNS_icon.svg')+'" alt="ent"/></span>');
			}
			icon.append('<div class="row"></div>');
			icon.find('.row').append('<div class="col-12"><p class="txt1">'+label+'</p></div>');
			icon.find('.row').append('<div class="col-12"><p class="txt2">'+count+'<em>건</em></p></div>');
			icon.data('code' , code);
			icon.data('count', count);
			icon.on('click', _functions.doClickCard);
			return icon;
		},
		// 유관기관별 카드버튼 클릭 이벤트 처리
	    //--------------------------------------------------------//
		doClickCard: function() {
			$(this).closest('.app-tab-lst').find('.card-box-nomar2').removeClass('active');
			_this.find('.app-tab-items').removeClass("on");

			// 버튼 ACTIVE
			$(this).addClass('active');
			
			// 2023.08.18 LSH 0건이 아닌경우
			if ($(this).data('count') > 0) {
				let dom = $('#app-tab-'+$(this).data('code')); 
				dom.addClass("on");
				dom.find('.app-swiper-button').eq(0).trigger('click');
			}
		},
		// 유관기관별 그리드 생성
	    //--------------------------------------------------------//
		getGrid: function( code ) {
			let gridArgs = {
				idField      : 'sn',
				titleField   : 'bizNm',
				titleIcon    : false,
				swiperOptions: {name: 'Myswiper-'+code},
				params       : options.params,
				complete     : function(tot, json, g) {
					// 그리드 Swiper 처리
					g.initialize();
				}
			};
			if (code == '99') {
				$.extend(gridArgs, {
					url: getUrl('/usr/invest/ent/getListEntOthsptHst.do'),
					items: [
						 {colCls: 'col-12 col-xl-12', labelValue: '기관명'  , valueField: 'instNm', labelIcon: 'icon-layer-group'}
						,{colCls: 'col-12 col-xl-12', labelValue: '분야내용', valueField: 'fldCn' , labelIcon: 'icon-layer-group'}
						,{colCls: 'col-12 col-xl-12', labelValue: '세부내용', valueField: 'dtlCn' , labelIcon: 'icon-layer-group'}
					],
					appendHtml: function(dom, json) {
						if (options.mode == MODE.VIEW)
							return false;
						if (options.updateYn != 'Y')
							return false;
						let col = $('<div class="text-end"></div>');
						let mbtn = $.domUtil.getButton({icon:'icon-edit' , value:'수정', cls:'bs-s btn-primary mx-2px', data: {irno: json['irNo'], sn: json['sn']}, click: _functions.doOpenEdit});
						let dbtn = $.domUtil.getButton({icon:'icon-trash', value:'삭제', cls:'bs-s btn-black mx-2px'  , data: {irno: json['irNo'], sn: json['sn']}, click: _functions.doRemoveItem});
						col.append(mbtn);
						col.append(dbtn);
						dom.append(col);
					}
				});
			}
			else {
				$.extend(gridArgs, {
					url: getUrl('/usr/support/sprt/getListSprtBiz.do'),
					items: [
						 {colCls: 'col-12 col-xl-6', labelField: 'artclNm1', valueField: 'artclCn1', labelIcon: 'icon-layer-group'}
						,{colCls: 'col-12 col-xl-6', labelField: 'artclNm2', valueField: 'artclCn2', labelIcon: 'icon-layer-group'}
						,{colCls: 'col-12 col-xl-6', labelField: 'artclNm3', valueField: 'artclCn3', labelIcon: 'icon-layer-group'}
						,{colCls: 'col-12 col-xl-6', labelField: 'artclNm4', valueField: 'artclCn4', labelIcon: 'icon-layer-group'}
						//,{colCls: 'col-12 col-xl-6', labelField: 'artclNm5', valueField: 'artclCn5', labelIcon: 'icon-layer-group'}
					],
					titleIcon : '<img src="'+getUrl('/images/app/CRDNS_'+code+'.svg')+'"/>'
				});
			}
			return $('<div></div>').appSwiperGrid(gridArgs);
		},
		// 그리드 년도조건 버튼 클릭 이벤트
	    //--------------------------------------------------------//
		doClick: function() {
			let p = {
				crdnsCd : $(this).data('code'),
				bizYr   : $(this).data('year')
			};
			let g = $(this).data('grid');
			$(g).find('.app-swiper-button').removeClass('active');
			$(this).addClass('active');
			// 목록 재검색
			g.doLoad(p);
		},
		// 그리드 년도조건 버튼 생성
	    //--------------------------------------------------------//
		getButton: function( code ) {
			// 년도조건 목록 가져오기
			let url  = getUrl('/usr/invest/ent/getListEntOthsptHstYears.do');
			let rows = $.ajaxUtil.ajaxDefault(url, $.extend({crdnsCd: code}, options.params))['rows'];
			// 검색조건 버튼 표시
			let btns  = [{
				selector : 'app-swiper-button',
				data     : {year:'ALL', code: code}, 
				value    : '전체',
				active   : true, 
				click    : _functions.doClick
			}];
			$.each(rows, function(i, o) {
				btns.push({
					selector : 'app-swiper-button',
					data     : {year: o['year'], code: code}, 
					value    : o['year']+'년 '+o['count']+'건',
					click    : _functions.doClick
				});
			});
			return btns;
		},
		// 기타 지원이력 작성하기 팝업 오픈
	    //--------------------------------------------------------//
		doOpen: function() {
			let pargs = {
				title     : '기타 지원이력 작성하기',
				loadParams: options.params,
				saveUrl   : options.saveUrl,
				callback  : _functions.drawGrid
			};
			$('<div></div>').appPopupEntSptHst(pargs);
		},
		// 기타 지원이력 수정하기 팝업 오픈
	    //--------------------------------------------------------//
		doOpenEdit: function() {
			let pargs = {
				title     : '기타 지원이력 수정하기',
				loadParams: $.extend({
					mode  : MODE.UPDATE,
					irNo  : $(this).data('irno'),
					sn    : $(this).data('sn')
				}, options.params),
				saveUrl   : options.saveUrl,
				callback  : _functions.drawGrid
			};
			if (options.saveUrl)
				pargs.saveUrl = options.saveUrl;
			$('<div></div>').appPopupEntSptHst(pargs);
		},
		// 기타 지원이력 삭제하기
	    //--------------------------------------------------------//
		doRemoveItem: function() {
			let sn = $(this).data('sn');
			let no = $(this).data('irno');
	    	$.commMsg.confirm("정말 삭제하시겠습니까?", function() {
				let obj = $.extend({
					mode: MODE.REMOVE,
					irNo: no,
					sn  : sn
				}, options.params);

				$.ajaxUtil.ajaxSave(
					options.saveUrl,
					JSON.stringify(obj),
					function() {
                    	$.commMsg.alert('성공적으로 삭제되었습니다.', _functions.drawGrid);
					}
				);
	    	});
	        return false;
		},
		// 지원사업 신청하기 이동처리
	    //--------------------------------------------------------//
		doClickApply: function() {
			$.commMsg.confirm([
				'지원사업 신청하기로 이동하시면 현재 작성중인 IR정보는 저장되지 않습니다.',
				'바로 지원사업 신청하기로 이동하시겠습니까?'
			].join('<br>'), function() {
				goUrl( getUrl('/usr/support/support/openSprt.do') );
			});
			return false;
		},
		// 수정시 버튼표시
	    //--------------------------------------------------------//
		getEditButton: function() {
			if (options.mode == MODE.VIEW)
				return false;
			if (options.updateYn != 'Y')
				return false;
			return [{
				id   : 'btnSptHst', 
				icon : 'icon-plus', 
				cls  : 'bs-m bs-md-l btn-primary',
				value: '기타 지원이력 작성하기', 
				click: _functions.doOpen
			}];
		},
		// 유관기관별 지원건수 요약정보 가져오기
	    //--------------------------------------------------------//
		getDataRows: function( reload ) {

			let items = false;
			// 재검색이면
			if (reload) {
				let url = getUrl('/usr/invest/ent/getListEntOthsptHstSummary.do');
				items   = $.ajaxUtil.ajaxDefault(url, options.params)['rows'];
			}
			else {
				if (options.data && 
					options.data[ENTLIST.OTSP.list])
					items = options.data[ENTLIST.OTSP.list];
			}
			if ($.commUtil.empty(items))
				return false;
			if (options.mode == MODE.VIEW)
				return items;
			
			let cnt = 0;
			$.each(items, function(i,item) {
				cnt += item['count'];
			});
			if (cnt > 0)
				return items;
			return false;
		},
		// 유관기관별 지원건수 요약 그리드 생성
	    //--------------------------------------------------------//
		createGrid: function( items ) {

			_this.find('.app-tab-empty').remove();

			if (_this.find('.app-tab-lst').length > 0) {
				_this.find('.app-tab-lst').empty();
				_this.find('.app-tab-box').empty();
			}
			else {
				_this.append('<div class="grid-ratio-2"><div class="app-tab-lst row"></div></div>');
				_this.append('<div class="app-tab-box tab-box mt-16px"></div>');
			}
			$.each(items, function(i, box) {
				let code  = box['code'];
				// 카드버튼 생성
				_this.find('.app-tab-lst').append($('<div class="col"></div>').append(_functions.getCard(box)));
				// 카드그리드 생성
				let grid = _functions.getGrid(code);
				_this.find('.app-tab-box').append($('<div id="app-tab-'+code+'" class="app-tab-items"></div>').append(grid));
				// 그리드 로드
				grid.doLoad();
				// 그리드 년도조건버튼 생성
				grid.createButtons(_functions.getButton(code)); 
			});
		},
		// 유관기관별 지원건수 요약 그리드 생성
	    //--------------------------------------------------------//
		drawGrid: function() {
			// 유관기관별 지원이력건수 조회
			let items = _functions.getDataRows( true );

			if (!$.commUtil.empty(items))
				_functions.createGrid(items);
			else {
				// 지원이력건수가 없는 경우
				let btn = $('<button type="button" class="align-items-center bg-black-t5 border-0 bs-m d-inline-flex fs-15px fw-600"></button>');
				btn.append('지원사업 신청하기');
				btn.append('<i class="fs-18px icon-arrow-right"></i>');
				btn.click(_functions.doClickApply);
				
				let dom = $('<div class="app-tab-empty p-24px shadow-box-1 text-center"></div>');
				dom.append('<img src="'+getUrl('/images/sub/MyPage3.png')+'" alt="" class="d-inline-block" style="width: 80px;">');
				dom.append('<span class="d-block mx-auto my-8px">지원 이력이 없습니다.</span>');
				dom.append(btn);
				_this.find('.app-tab-empty').remove();
				_this.append(dom);
			}
			// 첫번째 버튼 클릭
			//t.find('.card-box-nomar2').eq(0).trigger('click');
		},
		// 유관기관별 지원건수 요약 표시
	    //--------------------------------------------------------//
		create: function() {
			if (options.cls)
				_this.addClass(options.cls);
		
			_this.append(
				$.domUtil.getTitleBox({
					title  : options.title,
					icon   : options.icon,
					buttons  : _functions.getEditButton()
				})
			);
			_functions.drawGrid();
		}
	};
	
	_functions.create();

	return this;
};

// 회사연혁 정의 (조회/등록)
//=============================================================================
$.fn.appEntCoHstCard = function ( args ) {
	
	let options = $.extend(true, {
		cls          : false,
		mode         : MODE.VIEW,
		data         : false,
		params       : false,
		updateYn     : false,
		selectYn     : false,
		title        : '회사연혁',
		icon         : '<img src="'+getUrl('/images/sub/iconimg5.svg')+'" alt="icon"/>',
		formId       : 'cohstForm',
		cardOptions  : {
			boxCls   : 'shadow-box-1 px-32px pb-24px',
			groupCls : 'grid-ratio-1',
			wrapCls  : 'form-area-box-input',
			autoload : true,
			title    : false
		}
	}, args);
	
	let _this = $(this);
	let _data = false;
	let _card = false;
	let _form = false;

	let _functions = {
		// 그리드생성
	    //--------------------------------------------------------//
		getGrid: function(key, title) {

			// 조회모드에서 빈값인 경우
			if (options.mode == MODE.VIEW &&
				options.data              &&
				options.data[key]         &&
				$.commUtil.empty(options.data[key]))
				return $.domUtil.getEmptyBox('- '+STRING.NOINFO, 'mt-24px');

			let opts = {
				id                : key+'Grid',
				name              : key,
				options           : {
					id            : '#'+key+'Grid',
					name          : key,
					mode          : 'LIST',
					idField       : 'sn',
					title         : title,
					listOptions   : {
						tableCls  : 'app-fs14px',
						headCls   : "bs-1 ts-xl",
						bodyCls   : "bs-1 ts-m t-t-c",
						headthCls : "border-top-0",
						headtrCls : "",
						bodytrCls : "",
						emptyText : '검색된 내용이 없습니다.',
						colgroup  : ['*','50%','*'],
						columns   : _functions.getColumns()
					}
				}
			};
			if (options.mode != MODE.VIEW) {
				$.extend(true, opts.options.listOptions, {
					emptyText : false,
					editable  : true,
					editDefCnt: 0,
					editMinCnt: 0,
					editMaxCnt: 100
				});
			}
			return opts;
		},
		// 칼럼항목생성
	    //--------------------------------------------------------//
		getColumns: function() {
			if (options.mode == MODE.VIEW) {
				return [
					{cls:'app-l', name:'bgngCoYmd', label:'시기', formatter: $.formatUtil.toKorDate},
					{cls:'app-l', name:'cn'       , label:'내용'},
					{cls:'app-l', name:'rmrk'     , label:'비고'},
				];
			}
			else {
				return [
					{cls:'app-l', name:'bgngCoYmd', label:'시기', 
						hidden : {type:'hidden', name:'sn'},
						input  : {type:'text'  , name:'bgngCoYmd', maxlength: 8, clear: true, cls: 'app-full', number: true}
					},
					{cls:'app-l', name:'cn'  , label:'내용', input: {type:'text', maxlength: 4000, clear: true, cls: 'app-full'}},
					{cls:'app-l', name:'rmrk', label:'비고', input: {type:'text', maxlength: 1000, clear: true, cls: 'app-full'}},
				];
			}
		},
		// 카드항목생성
	    //--------------------------------------------------------//
		getItems: function() {
			let o = ENTLIST.COHT;
			return [{
				cls   : 'col-12', 
				name  : o.list + 'Grid', 
				must  : true,
				//label : o.title, 
				labelFormatter: function(c) {
					return $.domUtil.getTitleBox({
						title   : c.label,
						titleBox: 'table-top-1',
						icon    : '<i class="icon-user-circle cycle-ver-1"></i>',
					})
				},
				grid: _functions.getGrid(o.list, o.title)
			}];
		}
	};
	
	_data = $.extend({gridData: {}}, options.data);
	_data['gridData'][ENTLIST.COHT.list] = options.data[ENTLIST.COHT.list];

	_card = $('<div></div>').appFormCard({
		formId      : options.formId,
		cls         : options.cls,
		boxCls      : options.cardOptions.boxCls  ,
		groupCls    : options.cardOptions.groupCls,
		wrapCls     :(options.mode == MODE.VIEW ? options.cardOptions.wrapCls : 'form-area-box') ,
		autoload    : options.cardOptions.autoload,
		title       : options.cardOptions.title   ,
		defaultValue: STRING.NOINFO,
		data        : _data,
		items       : [{items: _functions.getItems()}],
		// 추가검증항목
		validates   : [
			{key: 'bgngCoYmd', method: 'required', message: '['+ENTLIST.COHT.title+'] 시기를 입력하세요.'},
			{key: 'cn'       , method: 'required', message: '['+ENTLIST.COHT.title+'] 내용을 입력하세요.'}
		]
	});

	_this.append($.domUtil.getTitleBox({title: options.title, icon: options.icon}));
	_this.append(_card);
	
	_form = _card.getForm();
	
	// 저장항목 검증
	this.doValidate = function() {
		let v = _card.doValidate();
		if (!v)
			return false;
		
		if (v['coHstList']) {
			$.each(v['coHstList'], function(i,o) {
				o['bgngCoYmd'] = o['bgngCoYmd'].replace(/[^0-9]/g,"");
			});
		}
		return {
			"coHstList": v["coHstList"]
		};
	};
	return this;
};

// 경영진 및 주주 현황 정의 (조회/등록)
//=============================================================================
$.fn.appEntOwnerCard = function ( args ) {

	let options = $.extend(true, {
		cls          : false,
		mode         : MODE.VIEW,
		data         : false,
		params       : false,
		updateYn     : false,
		selectYn     : false,
		title        : '경영진 및 주주 현황',
		icon         : '<img src="'+getUrl('/images/sub/iconimg6.svg')+'" alt="icon"/>',
		formId       : 'ownerForm',
		cardOptions  : {
			boxCls   : 'shadow-box-1 p-32px',
			groupCls : 'grid-ratio-1',
			wrapCls  : 'form-area-box-input',
			autoload : true,
			title    : false
		}
	}, args);
	
	let _this = $(this);
	let _data = false;
	let _card = false;
	let _form = false;
	
	let _functions = {
		// 기간표시
	    //--------------------------------------------------------//
		toTerm: function(v,o) {
			let s = $.formatUtil.toKorDate(v);
			if (o['endYmd'])
				s += (' ~ '+ $.formatUtil.toKorDate(o['endYmd']));
			return s;
		},
		// 그리드생성
	    //--------------------------------------------------------//
		getGrid: function(key, title) {

			// 조회모드에서 빈값인 경우
			if (options.mode == MODE.VIEW &&
				options.data              &&
				options.data[key]         &&
				$.commUtil.empty(options.data[key]))
				return $.domUtil.getEmptyBox('- '+STRING.NOINFO, 'mt-24px');

			let opts = {
				id                : key+'Grid',
				name              : key,
				options           : {
					id            : '#'+key+'Grid',
					name          : key,
					mode          : 'LIST',
					idField       : 'sn',
					title         : title,
					listOptions   : {
						tableCls  : 'app-fs14px',
						headCls   : "bs-1 ts-m",
						bodyCls   : "bs-1 ts-m t-t-c",
						headthCls : "",
						bodytrCls : "",
						emptyText : '검색된 내용이 없습니다.',
						colgroup  : _functions.getColgroup(key),
						columns   : _functions.getColumns(key)
					}
				}
			};
			if (options.mode != MODE.VIEW) {
				$.extend(true, opts.options.listOptions, {
					emptyText : false,
					editable  : true,
					// 대표자이력만 1개이상 입력되어야 함
					editDefCnt: (key == ENTLIST.RPHT.list ? 1 : 0),
					editMinCnt: (key == ENTLIST.RPHT.list ? 1 : 0),
					editMaxCnt: 100
				});
			}
			return opts;
		},
		// 칼럼너비생성
	    //--------------------------------------------------------//
		getColgroup: function(key) {
			let colgroup = [];
			if      (key == ENTLIST.RPHT.list) colgroup = ['350px',    '*'];
			else if (key == ENTLIST.MGMT.list) colgroup = ['200px','200px','200px',    '*']; 
			else if (key == ENTLIST.SHLD.list) colgroup = [    '*','200px','200px','200px']; 
			else if (key == ENTLIST.LWST.list) colgroup = ['200px','200px',    '*','200px']; 
			return colgroup;
		},
		// 칼럼항목생성
	    //--------------------------------------------------------//
		getColumns: function(key) {
			let columns = false;
			if (options.mode == MODE.VIEW) {
				if      (key == ENTLIST.RPHT.list)
					columns = [
						{cls:'app-l', name:'bgngYmd' , label:'시기', formatter: _functions.toTerm},
						{cls:'app-l', name:'hstryCn' , label:'주요 이력 및 경력'}
					];
				else if (key == ENTLIST.MGMT.list)
					columns = [
						{cls:'app-c', name:'jbpsNm'   , label:'직위'},
						{cls:'app-c', name:'mgtFlnm'  , label:'성명'},
						{cls:'app-c', name:'age'      , label:'연령', formatter: $.formatUtil.toAge},
						{cls:'app-l', name:'careerCn' , label:'주요경력'}
					];
				else if (key == ENTLIST.SHLD.list)
					columns = [
						{cls:'app-l', name:'flnm'     , label:'성명'},
						{cls:'app-r', name:'invtAmt'  , label:'투자액(원)', formatter: $.formatUtil.toNumber},
						{cls:'app-r', name:'qotaRt'   , label:'지분율'    , formatter: $.formatUtil.toRate},
						{cls:'app-c', name:'relCn'    , label:'관계'}
					];
				else if (key == ENTLIST.LWST.list)
					columns = [
						{cls:'app-c', name:'acusrNm'  , label:'원고'},
						{cls:'app-c', name:'dfdtNm'   , label:'피고'},
						{cls:'app-l', name:'lwstCn'   , label:'소송내용'},
						{cls:'app-r', name:'lwstAmt'  , label:'소송금액(원)', formatter: $.formatUtil.toNumber}
					];
			}
			else {
				if      (key == ENTLIST.RPHT.list)
					columns = [
						{cls:'app-l', name:'bgngYmd', label:'시기', 
							hidden : {type:'hidden' , name:'sn'},
							input  : [
								{colCls:'flex-b150px', type:'text', name:'bgngYmd', cls: 'app-full', maxlength: 10, calendar: true},
								{colCls:'flex-b30px' , text:' ~ '},
								{colCls:'flex-b150px', type:'text', name:'endYmd' , cls: 'app-full', maxlength: 10, calendar: true}
							]
						},
						{cls:'app-l', name:'hstryCn' , label:'주요 이력 및 경력', input: {type:'text', maxlength: 300, clear: true, cls: 'app-full'}}
					];
				else if (key == ENTLIST.MGMT.list)
					columns = [
						{cls:'app-c', name:'jbpsNm', label:'직위', 
							hidden : {type:'hidden', name:'sn'},
							input  : {type:'text'  , name:'jbpsNm', maxlength:  20, clear: true, cls: 'app-full'}
						},
						{cls:'app-c', name:'mgtFlnm'  , label:'성명'    , input: {type:'text', maxlength:  20, clear: true, cls: 'app-full'}},
						{cls:'app-c', name:'age'      , label:'연령'    , input: {type:'text', maxlength:   3, clear: true, cls: 'app-full text-end', number: true}, unit: '&nbsp;세'},
						{cls:'app-l', name:'careerCn' , label:'주요경력', input: {type:'text', maxlength: 300, clear: true, cls: 'app-full'}}
					];
				else if (key == ENTLIST.SHLD.list)
					columns = [
						{cls:'app-l', name:'flnm'  , label:'성명', 
							hidden : {type:'hidden', name:'sn'},
							input  : {type:'text'  , name:'flnm', maxlength:  20, clear: true, cls: 'app-full'}
						},
						{cls:'app-r', name:'invtAmt'  , label:'투자액', input: {type:'text', maxlength:  15, clear: true, cls: 'app-full text-end', number: true}, unit: '&nbsp;원'},
						{cls:'app-r', name:'qotaRt'   , label:'지분율', input: {type:'text', maxlength:   6, clear: true, cls: 'app-full text-end',  float: true}, unit: '&nbsp;%'},
						{cls:'app-c', name:'relCn'    , label:'관계'  , input: {type:'text', maxlength: 300, clear: true, cls: 'app-full'}}
					];
				else if (key == ENTLIST.LWST.list)
					columns = [
						{cls:'app-c', name:'acusrNm', label:'원고', 
							hidden : {type:'hidden', name:'sn'},
							input  : {type:'text'  , name:'acusrNm', maxlength:  20, clear: true, cls: 'app-full'}
						},
						{cls:'app-c', name:'dfdtNm'   , label:'피고'    , input: {type:'text', maxlength:  20, clear: true, cls: 'app-full'}},
						{cls:'app-l', name:'lwstCn'   , label:'소송내용', input: {type:'text', maxlength: 200, clear: true, cls: 'app-full'}},
						{cls:'app-r', name:'lwstAmt'  , label:'소송금액', input: {type:'text', maxlength:  15, clear: true, cls: 'app-full text-end', number: true}, unit: '&nbsp;원'}
					];
			}
			return columns;
		},
		// 카드항목생성
	    //--------------------------------------------------------//
		getItems: function() {
			let items = [];
			$.each(ENTLIST, function(k, o) {
				if (o.owner) {
					items.push({
						cls: 'col-12', 
						name: o.list + 'Grid', 
						must: true,
						label: o.title, 
						labelFormatter: function(c) {
							return $.domUtil.getTitleBox({
								title   : c.label,
								titleBox: 'table-top-1',
								icon    : '<i class="icon-user-circle cycle-ver-1"></i>',
							})
						},
						grid: _functions.getGrid(o.list, o.title)
					});
				}
			});
			return items;
		}
	};
	
	_data = $.extend({gridData: {}}, options.data);
	$.each(ENTLIST, function(k, o) {
		if (o.owner) {
			_data['gridData'][o.list] = options.data[o.list];
		}
	});
	
	_card = $('<div></div>').appFormCard({
		formId      : options.formId,
		data        : _data,
		cls         : options.cls,
		boxCls      : options.cardOptions.boxCls  ,
		groupCls    : options.cardOptions.groupCls,
		wrapCls     :(options.mode == MODE.VIEW ? options.cardOptions.wrapCls : 'form-area-box') ,
		autoload    : options.cardOptions.autoload,
		title       : options.cardOptions.title   ,
		defaultValue: STRING.NOINFO,
		items       : [{items: _functions.getItems()}],
		// 추가검증항목
		validates   : [
			{key:'bgngYmd'  , method: 'required' , message: '['+ENTLIST.RPHT.title+'] 시기를 입력하세요.'},
			{key:'endYmd'   , method: 'afterDate', message: '['+ENTLIST.RPHT.title+'] 종료일자가 시작일자보다 이전입니다.', param: 'input[name="bgngYmd"]'},
			{key:'hstryCn'  , method: 'required', message: '['+ENTLIST.RPHT.title+'] 주요 이력 및 경력을 입력하세요.'},
			{key:'jbpsNm'   , method: 'required', message: '['+ENTLIST.MGMT.title+'] 직위를 입력하세요.'},
			{key:'mgtFlnm'  , method: 'required', message: '['+ENTLIST.MGMT.title+'] 성명을 입력하세요.'},
			{key:'age'      , method: 'required', message: '['+ENTLIST.MGMT.title+'] 연령을 입력하세요.'},
			//{key:'careerCn' , method: 'required', message: '['+ENTLIST.MGMT.title+'] 주요경력을 입력하세요.'},
			{key:'flnm'     , method: 'required', message: '['+ENTLIST.SHLD.title+'] 성명을 입력하세요.'},
			{key:'invtAmt'  , method: 'required', message: '['+ENTLIST.SHLD.title+'] 투자액을 입력하세요.'},
			{key:'qotaRt'   , method: 'required', message: '['+ENTLIST.SHLD.title+'] 지분율을 입력하세요.'},
			{key:'relCn'    , method: 'required', message: '['+ENTLIST.SHLD.title+'] 관계를 입력하세요.'},
			{key:'acusrNm'  , method: 'required', message: '['+ENTLIST.LWST.title+'] 원고를 입력하세요.'},
			{key:'dfdtNm'   , method: 'required', message: '['+ENTLIST.LWST.title+'] 피고를 입력하세요.'},
			{key:'lwstCn'   , method: 'required', message: '['+ENTLIST.LWST.title+'] 소송내용을 입력하세요.'},
			{key:'lwstAmt'  , method: 'required', message: '['+ENTLIST.LWST.title+'] 소송금액을 입력하세요.'}
		]
	});
	_this.append($.domUtil.getTitleBox({title: options.title, icon: options.icon}));
	_this.append(_card);
	_form = _card.getForm();
	
	// 저장항목 검증
	this.doValidate = function() {
		let v = _card.doValidate();
		if (!v)
			return false;
		if (v['rprsHstList']) {
			$.each(v['rprsHstList'], function(i,o) {
				o['bgngYmd'] = o['bgngYmd'].replace(/[^0-9]/g,"");
				o['endYmd' ] = o['endYmd' ].replace(/[^0-9]/g,"");
			});
		}
		if (v['shroldrList']) {
			$.each(v['shroldrList'], function(i,o) {
				o['invtAmt'] = $.commUtil.nvlInt  (o['invtAmt']);
				o['qotaRt' ] = $.commUtil.nvlFloat(o['qotaRt' ]);
			});
		}
		if (v['lwstList']) {
			$.each(v['lwstList'], function(i,o) {
				o['lwstAmt'] = $.commUtil.nvlInt(o['lwstAmt']);
			});
		}
		
		return {
			"rprsHstList": v["rprsHstList"],
			"mgmtList"   : v["mgmtList"   ],
			"shroldrList": v["shroldrList"],
			"lwstList"   : v["lwstList"   ]
		};
	};

	return this;
};

// 사업계획서 정의
//=============================================================================
$.fn.appEntPlanFile = function ( entData ) {
	
	$(this).append(
		$.domUtil.getTitleBox({
			title  : '사업계획서',
			icon   : '<img src="'+getUrl('/images/sub/iconimg11.svg')+'" alt="icon">'
		})
	);
	$(this).append(
		$('<div></div>').appEntFile({
			mode: MODE.VIEW,
			initData: {
	            fileType: CODE.FILE_TYPE.ENT,
				docuCd:   CODE.FILE_SE.PLAN,
	            docuNo:   entData['bzentyNo']
			},
			emptyText: '- '+STRING.NOINFO
		}).appEntFile('init')
	);
	return this;
};

// 첨부자료 정의
//=============================================================================
$.fn.appEntAddFile = function ( entData ) {
	
	$(this).append(
		$.domUtil.getTitleBox({
			title  : '첨부자료',
			icon   : '<img src="'+getUrl('/images/sub/iconimg12.svg')+'">'
		})
	);
	$(this).append(
		$('<div></div>').appEntFile({
			mode: MODE.VIEW,
			initData: {
	            fileType: CODE.FILE_TYPE.ENT,
				docuCd:   CODE.FILE_SE.ADOC,
	            docuNo:   entData['bzentyNo']
			},
			emptyText: '- '+STRING.NOINFO
		}).appEntFile('init')
	);
	return this;
};

// 홍보영상 정의
//=============================================================================
$.fn.appEntPromotion = function ( entData ) {
	//2023.9.22 유튜브 동영상 주소 변환 JH
	let url = convertToEmbedURL(entData['prVidoUrl']);
	
	$(this).append(
		$.domUtil.getTitleBox({
			title  : '홍보영상',
			icon   : '<img src="'+getUrl('/images/sub/iconimg13.svg')+'">'
		})
	);

	if ($.commUtil.empty(url)) {
		$(this).append(
			$.domUtil.getEmptyBox('- '+STRING.NOINFO, 'mt-24px')
		);
		return this;
	}
	// TODO Youtube 링크시 SameSite 오류발생에 따른 처리
	document.cookie = "crossCookie=bar; SameSite=None; Secure";
	$(this).append([
		'<div style="aspect-ratio: 16 / 9;">',
		'<iframe ',
		'src="'+url+'"',
		'title="Video Player"',
		'frameborder="0" ',
		'style="display: block; width:100%; height:100%;" ',
		'class="d-block w-100" ',
		'allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" ',
		'allowfullscreen></iframe>',
		'</div>'
	].join(''));
	return this;
};

//2023.9.22 유튜브 동영상 주소 변환 JH
function convertToEmbedURL(url) {
    if (!url) { // url이 null 또는 undefined인 경우 원래 URL 반환
        return url;
    }
    // 이미 embed 형식인 경우, URL을 그대로 반환
    if (url.includes('youtube.com/embed/')) {
        return url;
    }
    if (url.includes('youtu.be/')) {
        var videoId = url.split('youtu.be/')[1];
        return "https://www.youtube.com/embed/" + videoId;
    }
    var videoIdParts = url.split('v=');
    if (videoIdParts.length < 2) {
        // 'v=' 파라미터가 없는 경우 원래 URL의 path 부분만 사용하여 embed 형식으로 변환
        var path = new URL(url).pathname;
        return "https://www.youtube.com/embed" + path;
    }
    var videoId = videoIdParts[1];
    var ampersandPosition = videoId.indexOf('&');
    if (ampersandPosition != -1) {
        videoId = videoId.substring(0, ampersandPosition);
    }
    return "https://www.youtube.com/embed/" + videoId;
}

// [팝업] 재무상태표/손익계산서 팝업
//=============================================================================
$.fn.appPopupEntFnnr = function( args ) {
	
	let options = {
		id        : 'app-ent-fnnr-popup',
		title     : args.title, // 재무상태표 or 손익계산서
		icon      : '<img src="'+getUrl('/images/sub/Data Transfer.svg')+'">',
		loadUrl   : getUrl('/usr/invest/ent/modalEntFnnr.do'),
		gridUrl   : getUrl('/usr/invest/ent/getEntFnnrDetails.do'),
		loadParams: {},
		gridParams: args.params
	};
	// 그리드 옵션 가져오기
	let _getGridOptions = function() {
		
		let p    = options.gridParams;
		let url  = options.gridUrl;
		let ret  = $.ajaxUtil.ajaxDefault(url, p);
		let years   = ret['years'];
		let rows    = ret['rows' ];
		let columns = [{cls:'app-l', name:'fnnrNm', label:'구분', 
		formatRow: function(el,r) {
			el.addClass('app-row'+r['fnnrLv']);
		},
		formatter: function(v,r) {
			return '<p class="app-blank'+r['fnnrLv']+'">'+v+'</p>';
		}}];
		let cgroups = ['*'];

		// 행단위 데이터를 년도 항목으로 피벗 처리
		$.each(years, function(i,year) {
			columns.push({
				cls:   'app-r', 
				name:  'fnnrAmt'+(i+1), 
				label: year, 
				formatter: $.formatUtil.toNumber
			});
			cgroups.push('160px');
		});
		return {
			mode:    'LIST',
			title:    options.title,
			dataRows: rows,
			autoload: true,
			listOptions: {
				gridCls: 'table-box-1',
				headCls: 'bs-1 ts-l',
				bodyCls: 'bs-1 t-t-r ts-m',
				headthCls: '',
				scroll:   true,
				colgroup: cgroups,
				columns:  columns
			}
		};
	}
	
	$(this).prop('id', options.id);
	//<div class="pb-53px">
	return $(this).appPopup({
		url:        options.loadUrl,
		params:     options.loadParams,
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		titleButton: {
			id:     'btn-popdown-pdf',
			icon:   'icon-file-pdf',
			text:   'PDF 다운 받기',
			btnCls: 'bs-m btn-combi-2',
			click:  function() {
				let grid = $('#appPopupGrid');
				let fpop = $('#'+options.id);
				let wrap = $('.wrap');
				
				//프린터 출력 전 이벤트
				window.onbeforeprint = function () {
					// 그리드 출력전 스크롤제거 
					grid.appGrid('beforePrint');
					// 활성화된 레이어 영역 복제
					var doc = fpop.find('.cutum-modal-dialog').clone();
					// 출력용 스타일시트 추가 
					doc.addClass('print-cutum-modal-dialog');
					// 프린트 영역 생성
				    $('body').append('<div class="print-div">'); 
					// 프린트 영역에 레이어 영역 복사
				    $('.print-div').append(doc); 
				    wrap.hide();
				    fpop.hide();
				};
				//프린터 출력 후 이벤트
				window.onafterprint = function () { 
					// 사용이 끝난 영역 삭제
				    $('.print-div').remove(); 
				    wrap.show();
				    fpop.show();
					// 그리드 출력전 스크롤복원 
					grid.appGrid('afterPrint');
				};
				window.print();
			}
		},
		onloaded:   function() {
			// 목록정의
			$('#appPopupGrid').appGrid(_getGridOptions()).appGrid('init');
		}
	}).open();
};

// [팝업] 특허및상표권보유현황 팝업
//=============================================================================
$.fn.appPopupEntPtnt = function( args ) {
	
	let options = {
		title     : '특허 및 상표권 보유현황',
		icon      : '<img src="'+getUrl('/images/sub/Bookmark.svg')+'">',
		loadUrl   : getUrl('/usr/invest/ent/modalEntPtnt.do'),
		gridUrl   : getUrl('/usr/invest/ent/getListEntPtnt.do'),
		loadParams: {},
		gridParams: args.params
	};

	return $(this).appPopup({
		url:        options.loadUrl,
		params:     options.loadParams,
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		dialogCls:  'w-ver1',
		appendBody: true,
		onloaded:   function() {
			// 목록정의
			$('#appPopupGrid').appGrid({
				mode:    'LIST',
				url:     options.gridUrl,
				params:  options.gridParams,
				autoload: true,
				// 페이지 표시 객체
				paging: '#appPopupGridPagination',
				listOptions: {
					gridCls: 'table-box-1',
					headCls: 'bs-1 ts-l',
					bodyCls: 'bs-1 ts-m',
					headthCls: '',
					colgroup:['80px','150px','100px','*','135px','120px'],
					columns: [
						{name:'patentSeNm'  , cls:'app-c', label:'구분'},
						{name:'applnm'      , cls:'app-l', label:'출원인'},
						{name:'patntrtMan'  , cls:'app-l', label:'특허권자'},
						{name:'nm'          , cls:'app-l', label:'명칭'},
						{name:'illtRegNo'   , cls:'app-c', label:'등록번호'},
						{name:'patentRegYmd', cls:'app-c', label:'등록일자', formatter: $.formatUtil.toDashDate}
					]
				}
			}).appGrid('init');
		}
	}).open();
};

// [팝업] 기타지원이력 작성하기 팝업
//=============================================================================
$.fn.appPopupEntSptHst = function( args ) {
	
	let options = $.extend({
		title     : '기타지원이력 작성하기',
		icon      : '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
		loadUrl   : getUrl('/usr/invest/ent/modalEntSptHst.do'),
		saveUrl   : getUrl('/usr/mypage/ent/saveEntOthsptHst.do'),
		loadParams: {}
	}, args);

	return $(this).appPopup({
		url:        options.loadUrl,
		params:     JSON.stringify(options.loadParams),
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		style:      'max-width: 626px;', 
		dialogCls:  'position-nomal',
		appendBody: true,
		onloaded:   function( pop ) {
			
			$('.app-input-clear').click(bindClear);
			
			let P_FORM = $('#popupForm');
			P_FORM.validate({
				debug      : false,
				onfocusout : false,
				onsubmit   : false,
				rules      : {
					fldCn  : {required: true},
					instNm : {required: true},
					bizNm  : {required: true},
					dtlCn  : {required: true},
				},
				messages   : {
					fldCn  : {required: '분야를 입력해 주세요.'},
					instNm : {required: '지원기관을 입력해 주세요.'},
					bizNm  : {required: '사업명을 입력해 주세요.'},
					dtlCn  : {required: '세부내용을 입력해 주세요.'},
				},
				invalidHandler: $.validateUtil.handler,
				errorPlacement: $.validateUtil.placement
			});
			
			// 저장처리
			let doSave = function() {
				
				let oper = $(this).data('oper');
				
		        if (P_FORM.validate().settings)
		            P_FORM.validate().settings.ignore = false;
		        if (!P_FORM.valid())
		            return false;
		    	$.commMsg.confirm("저장하시겠습니까?", function() {
			
					let obj = P_FORM.serializeObject();
					$.extend(obj, {mode: MODE.INSERT});
					$.extend(obj, options.loadParams);

					$.ajaxUtil.ajaxSave(
						options.saveUrl,
						JSON.stringify(obj),
						function() {
	                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
								args.callback();
								// 저장후 추가시
								if (oper == 'A') {
									P_FORM[0].reset();
									P_FORM.find('[name="fldCn"]').focus();
								}
								else {
									pop.close();
								}
								return false;
							});
						}
					);
		    	});
		        return false;
			};

			// 삭제처리
			let doRemove = function() {
				
		    	$.commMsg.confirm("정말 삭제하시겠습니까?", function() {
			
					let obj = P_FORM.serializeObject();
					$.extend(obj, {mode: MODE.REMOVE});

					$.ajaxUtil.ajaxSave(
						options.saveUrl,
						JSON.stringify(obj),
						function() {
	                    	$.commMsg.alert('성공적으로 삭제되었습니다.', function() {
								args.callback();
								pop.close();
								return false;
							});
						}
					);
		    	});
		        return false;
			};

			// 저장 버튼 클릭 이벤트 처리
			$('#p_btnSptHstSave').bind('click', doSave);
			// 저장 버튼 클릭 이벤트 처리
			$('#p_btnSptHstRemove').bind('click', doRemove);
			// 저장후추가 버튼 클릭 이벤트 처리
			$('#p_btnSptHstSaveAdd').bind('click', doSave);
			// 취소 클릭 이벤트 처리
			$('#p_btnSptHstCancel').bind('click', function() {
				pop.close();
				return false;
			});
		}
	}).open();
};

// [팝업] 미리보기 팝업
//=============================================================================
$.fn.appPopupEntPreview = function( args ) {
	
	let options = {
		title   : '미리보기',
		icon    : '<img src="'+getUrl('/images/sub/Tribute.svg')+'">',
		loadUrl : getUrl('/usr/invest/ent/modalEntPreview.do'),
		url     : getUrl('/usr/invest/ent/getEntAll.do'),
		params  : $.extend({}, args.params, {mode: MODE.VIEW})
	};

	return $(this).appPopup({
		url:        options.loadUrl,
		params:     {},
		title:      options.title,
		icon:       options.icon,
		type:       'pageload',
		style:      'max-width: 1280px;', 
		dialogCls:  'position-nomal',
		appendBody: true,
		onloaded:   function( pop ) {
			
			let dom = $('#appPopupContent');

			$.ajaxUtil.ajaxLoad(options.url, options.params, function(result) {

                var data = result.Data;

                if (data) {
					dom.append('<div id="appPopupSlideCard"></div>');
					dom.append('<div id="appPopupTab" class="appTab"></div>');
					dom.append('<div id="appPopupDashTab" class="app-dash-panel"></div>');
					dom.append('<div id="appPopupInfoTab" class="app-info-panel"></div>');
					
					// 슬라이드정보
					$.bizUtils.drawEntSlide($('#appPopupSlideCard'), data);
					// 설명글
					dom.find('.app-dash-panel').append($('<div></div>').appEntDescript());
					let cards = {};
					$.each(ENTCARD, function(key, obj) {
						cards[key] = $('<div></div>')[obj.widget]( $.extend({
							cls     : 'mb-32px',
							mode    : MODE.VIEW,
							data    : data,
							params  : {
								bzentyNo : data['bzentyNo'],
								irNo     : data['irNo']
							}
						}, obj.params) );
						dom.find('.app-dash-panel').append(cards[key]);
					});
					$.each(ENTCARD, function(key, obj) {
						if (obj.chart) {
							cards[key].createChart();
						}
					});
					dom.find('.app-info-panel').append('<div class="app-info-file mb-32px info-filelistbox-of-box"></div>');
					dom.find('.app-info-panel').append($('<div class="mb-32px"></div>').appEntPromotion( data ));
					dom.find('.app-info-file' ).append('<div class="row"></div>');
					dom.find('.app-info-file > .row').append($('<div class="col-12 col-md"></div>').appEntPlanFile( data ));
					dom.find('.app-info-file > .row').append($('<div class="col-12 col-md"></div>').appEntAddFile ( data ));
					$('#appPopupTab').appTabs({
						value: 'DASH',
						items: [
							{tab: '#appPopupDashTab', label:'데이터 대시보드', value:'DASH', icon:'icon-grid-square'},
							{tab: '#appPopupInfoTab', label:'사업계획서 및 홍보자료', value:'INFO', icon:'icon-list'}
						]
					});
					dom.find('button').hide();
                }
            });
			
			// 닫기 클릭 이벤트 처리
			$('#p_btnClose').bind('click', function() {
				pop.close();
				return false;
			});
			
		}
	}).open();
};
