/**
*******************************************************************************
*** 파일명    : app_mypage.js
*** 설명      : 마이페이지 관련 컨트롤
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.06.21              LSH
*******************************************************************************
**/

// 공통포맷 정의
let appFormat = {
	// 사업자등록증 파일포맷
	//---------------------------------------
	toBizRegFile: function(v,o) {
		return $('<div></div>').appEntFile({
			mode: MODE.VIEW,
			screen: 'SIMPLE',
			initData: {
	            fileType: CODE.FILE_TYPE.ENT,
				docuCd:   CODE.FILE_SE.BREG,
	            docuNo:   o['bzentyNo']
			}
		}).appEntFile('init');
	},
	// 인증여부 포맷
	//---------------------------------------
	toCertText: function(v,o) {
		if (v == CODE.USE_STUS.USABLE) {
			let a = [];
			if (o['aplyYmd']) 
				a.push($.formatUtil.toDashDate(o['aplyYmd']));
			a.push('승인완료')
			return a.join(' '); 
		}
		else if (v == CODE.USE_STUS.UNUSED) {
			return '관리자 승인 대기중';
		}
		return o['useSttsNm'];
	},
};

// 기업기본정보 - 그룹관리에서 사용됨
//=============================================================================
$.fn.appBizInfoCard = function ( args ) {
	
	// 칼럼목록
	let _items = [];
	
	// 경영체의 경우
	if (SCREEN.ROLE.ENT) {
		_items = [
			{colCls: 'col-12 col-md-6 col-xl-4', name:'bzentyNm'     ,label:'회사명'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'brno'         ,label:'사업자번호', formatter: $.formatUtil.toBizNo},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'bzentyStleNm' ,label:'기업형태'},

			{colCls: 'col-12 col-md-6 col-xl-4', name:'bzentyTypeNm' ,label:'기업유형'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'fndnYmd'      ,label:'설립일', formatter: $.formatUtil.toKorDate},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'bzentyScaleNm',label:'기업규모'},

			{colCls: 'col-12 col-md-6 col-xl-4', name:'tpbizNm'      ,label:'업종'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'hmpgAddr'     ,label:'홈페이지'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'rprsvNm'      ,label:'대표자'},

			{colCls: 'col-12 col-md-6 col-xl-4', name:'emlAddr'      ,label:'이메일'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'rprsTelno'    ,label:'TEL', formatter: $.formatUtil.toPhone},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'fxno'         ,label:'FAX', formatter: $.formatUtil.toPhone},

			{colCls: 'col-12'                  , name:'lctnAddr'     ,label:'소재지'},
		];
	}
	else {
		_items = [
			{colCls: 'col-12 col-md-6 col-xl-4', name:'bzentyNm'     ,label:'회사명'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'brno'         ,label:'사업자번호'  , formatter: $.formatUtil.toBizNo},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'fndnYmd'      ,label:'설립일'      , formatter: $.formatUtil.toKorDate},
	
			{colCls: 'col-12 col-md-6 col-xl-4', name:'useSttsCd'    ,label:'인증여부'    , formatter: appFormat.toCertText},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'rprsvNm'      ,label:'대표자'},
			{colCls: 'col-12 col-md-6 col-xl-4', name:'bzentyNo'     ,label:'사업자등록증', formatter: appFormat.toBizRegFile}
		];
	}
	return $(this).appCard($.extend({
				// 칼럼목록
				items: _items,
				// 빈값텍스트
				defaultValue: STRING.NOINFO,
				// 정보데이터
				//data: data,
				// 제목설정
				//titleOptions: titleOptions
			}, args));
};

// 기업기본정보 - 기본정보에서 사용됨
//=============================================================================
$.fn.appBizBasicInfo = function( args ) {
	
	let options = $.extend({
		data    : false,
		userNo  : false,      // 사용자번호
		bzentyNo: false,      // 업체번호
		accessYn: false,      // 승인여부
		selectYn: false,      // 조회가능여부
		updateYn: false,      // 수정가능여부
		kodataYn: false,      // KODATA여부
		rprsYn  : false,      // 대표여부
		act     : ACT.BZ_INFO,// 작업구분
		mode    : MODE.UPDATE,// 저장구분
		formId  : 'infoForm',
		cardId  : 'appBizInfoCard',
		title   : '기업 기본정보',
	}, args);
	if      (SCREEN.ROLE.INV) options.title = '기업정보';
	else if (SCREEN.ROLE.RIS) options.title = '소속정보';
	
	let _this = $(this);
	let _form = false;
	let _card = false;
	let _functions = {
		// 업종 트리팝업 오픈
	    //--------------------------------------------------------//
		doOpenTree: function() {
			// 업종트리 선택팝업 (app_popup.js)
			popup.openTpbizTree({
				// 업종선택시 tpbizCd, tpbizNm 입력박스 맵핑
				callback: function(data) {
					$.formUtil.setValue(_form, 'tpbizCd', data['code']);
					$.formUtil.setValue(_form, 'tpbizNm', data['text']);
				}
			});
		},
		// 기업정보 로드
	    //--------------------------------------------------------//
		doLoad: function() {
			let ret = $.ajaxUtil.ajaxDefault(getUrl('/usr/mypage/mypage/getEntInfo.do'),{});
			_card.doLoad(false, ret);
		},
		// 기업기본정보 필수검증 및 저장하기
	    //--------------------------------------------------------//
		doSave: function() {
			// 데이터 검증
			let data = _card.doValidate();
			if (!data)
				return false;
			$.extend(data, {mode : MODE.UPDATE});

	        $.commMsg.confirm("저장하시겠습니까?", function() {
	            // AJAX로 저장처리
	            $.ajaxUtil.ajaxSave(
	                getUrl('/usr/mypage/mypage/saveEnt.do'), 
	                JSON.stringify(data),
	                function(ret) {
	                    $.ajaxUtil.success(ret, function() {
							$.commMsg.alert('저장이 완료되었습니다.', _functions.doLoad);
	                    });
	                }
	            );
	        });
			return false;
		}
	};

	function _create() {
		_this.append(_createForm());
		_form = _this.find('form');
		_createInfo(options.data);
	}
	
	function _createInfo(data) {
		_form.html('');
		_form.append('<input type="hidden" name="act"      value="'+options.act     +'"/>');
		_form.append('<input type="hidden" name="mode"     value="'+options.mode    +'"/>');
		_form.append('<input type="hidden" name="userNo"   value="'+options.userNo  +'"/>');
		_form.append('<input type="hidden" name="bzentyNo" value="'+options.bzentyNo+'"/>');
		_form.append(
			$.domUtil.getTitleBox({
				titleBox: 'table-top-1',
				icon    : '<i class="cycle-ver-1 icon-buildings titicon"></i>',
				title   : options.title, 
				buttons : (options.updateYn != 'Y' ? false : [{value:'저장',id:'btnSaveBizInfo',cls:'bs-m btn-primary mr-5px',icon:'icon-floppy-disk', click: _functions.doSave}])
			})
		);
		_card = $('<div id="'+options.cardId+'"></div>').appFormCard(_createOptions());
		_form.append(_card);
		_card.doLoad(false, data);
	}

	function _createForm() {
		let f = $('<form method="post" onsubmit="return false;"></form>');
		f.prop('id'  , options.formId);
		f.prop('name', options.formId);
		return f;
	}
	
	function _createOptions() {
		
		let _items = [];
		
		// 경영체인 경우
		if (SCREEN.ROLE.ENT) {
			_items = [
				{cls: 'col-12 col-md-6 col-xl-4', name:'bzentyNm'    ,label:'회사명', valueBox: true},
				{cls: 'col-12 col-md-6 col-xl-4', name:'brno'        ,label:'사업자번호', valueBox: true, formatter: $.formatUtil.toBizNo},
				{cls: 'col-12 col-md-6 col-xl-4', must: true, name:'bzentyStleNm',label:'기업형태', 
					input: {id:'bzentyStleCd', name:'bzentyStleCd', type:'select',options: {params: {upCdId: CODE.BZENTY_STLE.code}}}
				},
				{cls: 'col-12 col-md-6 col-xl-4', must: true, name:'bzentyTypeNm',label:'기업유형', 
					input: {id:'bzentyTypeCd', name:'bzentyTypeCd', type:'radio',
						options: {
							params: {upCdId: CODE.BZENTY_TYPE.code},
							filter: function(o) {
								if (SCREEN.ROLE.USR && 
									o['code'] == CODE.BZENTY_TYPE.INDV)
									return true;
								if (SCREEN.ROLE.BIZ && 
									o['code'] != CODE.BZENTY_TYPE.INDV)
									return true;
								return false;
							}
						}
					} 
				},
				{cls: 'col-12 col-md-6 col-xl-4', name:'fndnYmd',label:'설립일', valueBox: true, formatter: $.formatUtil.toKorDate},
				{cls: 'col-12 col-md-6 col-xl-4', must: true, name:'bzentyScaleNm',label:'기업규모' , 
					input: {id:'bzentyScaleCd', name:'bzentyScaleCd', type:'select',options: {params: {upCdId: CODE.BZENTY_SCALE.code}}}
				},
				{cls: 'col-12 col-md-6 col-xl-4', must: true, name:'tpbizNm',label:'업종', 
					inputHtml: function() {
						let i1 = $('<input type="text" name="tpbizCd" value="" readonly="readonly" class="app-readonly">');
						let i2 = $('<input type="text" name="tpbizNm" value="" readonly="readonly" class="app-readonly">');
						let b1 = $('<button type="button" class="btn-combi-1 bs-m">선택</button>');
						// 업종트리팝업 오픈
						b1.click(_functions.doOpenTree);
						return $.domUtil.getEleIconGroup({cls:'input-btn'},[{box:i1},{box:i2},{dom:b1,cls:'box-btn'}]);
					} 
				},
				{cls: 'col-12 col-md-6 col-xl-4', name:'hmpgAddr',label:'홈페이지', 
					input: {id:'hmpgAddr', name:'hmpgAddr', type:'text', maxlength: 100}
				},
				{cls: 'col-12 col-md-6 col-xl-4', name:'rprsvNm',label:'대표자', valueBox: true},
				{cls: 'col-12 col-md-6 col-xl-4', must: true, name:'emlAddr',label:'이메일',  
					//input: {id:'emlAddr', name:'emlAddr', type:'text', maxlength: 50, valid: {method:'email'}}
					hidden: {id:'emlAddr', name:'emlAddr', type:'hidden', 
						valid: {method:'email'},
						merge: {count:2, type:'email'}
					}, 
					input: [
						{id:'emlAddr1' , name:'emlAddr1' , type:'text', maxlength: 50},
						{text:'@', cls:'wave'},
						{id:'emlAddr2' , name:'emlAddr2' , type:'text', maxlength: 50},
						{id:'emlDomain', name:'emlDomain', type:'select', notvalid: true, 
							options: {
								type:'static', 
								init: COMBO.INIT_DIRECT,
								rows: STORE.EML_CC,
								// 콤보 값변경시 입력처리
								change: function() {
									let domain   = $(this).closest('.day').find('[name="emlAddr2"]');
									let newValue = $(this).find('option:checked').val();
									let newLabel = $(this).find('option:checked').text();
									if (newValue == '') {
										//domain.val('');
										domain.prop('readonly', false);
									}
									else {
										domain.val(newLabel);
										domain.prop('readonly', true);
									}
								}
							}
						},
			 	 	]
				},
				{cls: 'col-12 col-md-6 col-xl-4', must: true, name:'rprsTelno', label:'TEL', formatter: $.formatUtil.toPhone,
					input: {id:'rprsTelno', name:'rprsTelno', type:'text', maxlength: 14, phone: true, valid: {method:'phone'}, valueFormatter: $.formatUtil.toPhone }
				},
				{cls: 'col-12 col-md-6 col-xl-4', name:'fxno',label:'FAX', formatter: $.formatUtil.toPhone,
					input: {id:'fxno', name:'fxno', type:'text', maxlength: 14, phone: true, valid: {method:'phone'}, valueFormatter: $.formatUtil.toPhone}
				},
				{cls: 'col-12', must: true, name:'lctnAddr', label:'소재지', 
					input: {id:'lctnAddr', name:'lctnAddr', type:'text', maxlength: 100}
				}
			];
			// 수정불가인 경우 모두 valueBox로 전환
			if (options.updateYn != 'Y') {
				$.each(_items, function(i,item) {
					item['must'      ] = false;
					item['input'     ] = false;
					item['hidden'    ] = false;
					item['inputHtml' ] = false;
					item['valueBox'  ] = true;
				});
			}
			// KODATA인 경우 일부항목 valueBox로 전환
			else if (options.kodataYn == 'Y') {
				let _keys = ['bzentyStleNm','bzentyTypeNm','bzentyScaleNm','tpbizNm'];
				$.each(_items, function(i,item) {
					if ($.inArray(item.name, _keys) >= 0) {
						item['must'      ] = false;
						item['input'     ] = false;
						item['hidden'    ] = false;
						item['inputHtml' ] = false;
						item['valueBox'  ] = true;
					}
				});
			}
		}
		else {
			_items = [
				{cls: 'col-12 col-md-6 col-xl-4', name:'bzentyNm'     ,label:'회사명'      , valueBox: true},
				{cls: 'col-12 col-md-6 col-xl-4', name:'brno'         ,label:'사업자번호'  , valueBox: true, formatter: $.formatUtil.toBizNo},
				{cls: 'col-12 col-md-6 col-xl-4', name:'fndnYmd'      ,label:'설립일'      , valueBox: true, formatter: $.formatUtil.toKorDate},
				{cls: 'col-12 col-md-6 col-xl-4', name:'useSttsCd'    ,label:'인증여부'    , valueBox: true, formatter: appFormat.toCertText},
				{cls: 'col-12 col-md-6 col-xl-4', name:'rprsvNm'      ,label:'대표자'      , valueBox: true},
				{cls: 'col-12 col-md-6 col-xl-4', name:'bzentyNo'     ,label:'사업자등록증', valueBox: true, formatter: appFormat.toBizRegFile}
			];
		}
		return {
			// 추가 CLS
			cls: 'mb-32px',
			// 박스 CLS
			boxCls: 'shadow-box-1 p-32px',
			// 그룹 CLS
			groupCls: 'grid-ratio-1',
			// 입력영역 CLS
			wrapCls: 'form-area-box-input',			
			// 빈값텍스트
			defaultValue: STRING.NOINFO,
			// 제목
			title: false,
			// 폼객체
			form: _this.find('form'),
			// 칼럼목록
			items: [{items: _items}]
		};
	}
	_create();
	return this;
};

// 기업상세정보 (경영체만) : 썸네일 / 사업분야 / 투자분야 / 투자희망금액
//=============================================================================
$.fn.appBizMoreInfo = function( args ) {
	
	let options = $.extend({
		data    : false,
		userNo  : false,      // 사용자번호
		bzentyNo: false,      // 업체번호
		accessYn: false,      // 승인여부
		selectYn: false,      // 조회가능여부
		updateYn: false,      // 수정가능여부
		kodataYn: false,      // KODATA여부
		rprsYn  : false,      // 대표여부
		act     : ACT.BZ_FILE,// 작업구분
		mode    : MODE.UPDATE,// 저장구분
		formId  : 'moreForm',
		cardId  : 'appBizMoreCard',
		title   : '기업 상세정보'
	}, args);

	if (options.selectYn != 'Y')
		return this;

	let _this = $(this);
	let _form = false;
	let _functions = {
	    // 기업상세정보 필수검증 및 저장하기
	    //--------------------------------------------------------//
		doSave: function() {
	        // 데이터 객체화
	        var data = _form.serializeObject();
	
			if ($.commUtil.empty(data['invtFldList'])) {
				$.commMsg.alert('투자분야는 필수 선택사항입니다.');
				return false;
			}
			if ($.commUtil.empty(data['bizFldList'])) {
				$.commMsg.alert('투자분야는 필수 선택사항입니다.');
				return false;
			}
			if ($.commUtil.empty(data['invtHopeAmt'])) {
				$.commMsg.alert('투자희망금액은 필수 입력사항입니다.');
				return false;
			}
			// 첨부파일 VALIDATION
			if (!$('#appThumbnail').appEntFile('validate', {isAlert: true, isLimit: true, isExt: true})) {
	            return false;
			}
			
	    	$.commMsg.confirm("저장하시겠습니까?", function() {

				// 저장전 금액 포맷제거
				$.formUtil.setValue(_form, 'invtHopeAmt', $.commUtil.nvlInt(data['invtHopeAmt']));

				// 등록폼을 AJAX로 저장처리
				_form.ajaxForm({
	                url: getUrl('/usr/mypage/mypage/saveEntFile.do'), 
	                enctype : 'multipart/form-data',
	                error: $.ajaxUtil.error,
	                success: function(ret) {
	                	$.ajaxUtil.success(ret, function() {
	                    	$.commMsg.alert('성공적으로 저장되었습니다.', _functions.doLoad);
	                	});
	                }
	            }).submit();
	    	});
	        return false;				
		},
		// 기업정보 로드
	    //--------------------------------------------------------//
		doLoad: function() {
			let ret = $.ajaxUtil.ajaxDefault(getUrl('/usr/mypage/mypage/getEntInfo.do'),{});
			_createInfo(ret);
		},
		// 입력박스 비활성처리
	    //--------------------------------------------------------//
		doDisabled: function(cmp) {
			$(cmp).find('label').unbind('click');
			cmp.disabled(true);
		}
	};

	function _create() {
		_this.append(_createForm());
		_form = _this.find('form');
		_createInfo(options.data);
	}

	function _createInfo(data) {
		_form.html('');
		_form.append('<input type="hidden" name="act"      value="'+options.act     +'"/>');
		_form.append('<input type="hidden" name="mode"     value="'+options.mode    +'"/>');
		_form.append('<input type="hidden" name="userNo"   value="'+options.userNo  +'"/>');
		_form.append('<input type="hidden" name="bzentyNo" value="'+options.bzentyNo+'"/>');
		_form.append(
			$.domUtil.getTitleBox({
				titleBox: 'table-top-1',
				icon    : '<i class="cycle-ver-1 icon-buildings titicon"></i>',
				title   : options.title, 
				buttons : (options.updateYn != 'Y' ? false : [{value:'저장',id:'btnSaveBizMore',cls:'bs-m btn-primary mr-5px',icon:'icon-floppy-disk', click: _functions.doSave}])
			})
		);
		_form.append(_createOptions(data));
	}

	function _createForm() {
		let f = $('<form method="post" onsubmit="return false;"></form>');
		f.prop('id'  , options.formId);
		f.prop('name', options.formId);
		return f;
	}
	
	function _createOptions(data) {
		return $('<div></div>').appSmartSearch({
			// 모드: 마이페이지
			mode     : MODE.MYPAGE,
			form     : _form,
			items    : [[{ // 썸네일
				must            : true,
				label           : '썸네일',
				id              : 'appThumbnail',
				type            : 'appFile', 
				typeKey         : 'appEntFile',
				inputOptions    : {
					mode        : MODE.UPDATE,
					label       : false,
					maxCount    : 3,
					multiple    : true,
					downbtn     :(options.updateYn != 'Y'),
					initData    : {
			            fileType: CODE.FILE_TYPE.ENT,
						docuCd  : CODE.FILE_SE.IMGE,
			            docuNo  : data['bzentyNo']
					},
					message     : 'JPG,PNG,BMP만 가능, 총 100MB 이내 3개까지 등록 가능',
					messageCls  : 'fs-10px',
					extensions  : ['jpg','bmp','png'],
					extra       : {type: 'radio', name: 'rprsYn', label: '대표이미지'}
				},
			}],[{ // 투자분야
				must            : true,
				label           : '투자분야',
				name            : 'invtFldList',
				type            : 'appSelectBox', 
				inputOptions    : {
					separator   : ',',
					colCls      : 'col',
					form        : 'checkbox', 
					name        : 'invtFldList',
					value       : data['invtFldCode'],
					url         : getUrl('/com/common/getInvtRlmComboCode.do'),
					init        : (options.updateYn != 'Y' ? false : {code:'ALL', text:'전체', click: bindCheckAll}), // (comm_formatutils.js)
					click       : (options.updateYn != 'Y' ? false : bindUnlockAll),
					callback    : (options.updateYn == 'Y' ? false : _functions.doDisabled)
				},
			}],[{ // 사업분야
				must            : true,
				label           : '사업분야',
				name            : 'bizFldList',
				type            : 'appSelectBox', 
				inputOptions    : {
					separator   : ',',
					colCls      : 'col',
					form        : 'checkbox', 
					name        : 'bizFldList',
					value       : data['bizFldCode'],
					params      : {upCdId: CODE.BIZ_RLM.code},
					init        : (options.updateYn != 'Y' ? false : {code:'ALL', text:'전체', click: bindCheckAll}), // (comm_formatutils.js)
					click       : (options.updateYn != 'Y' ? false : bindUnlockAll),
					callback    : (options.updateYn == 'Y' ? false : _functions.doDisabled)
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
					value       : $.formatUtil.toNumber(data['invtHopeAmt']),
					placeholder : '투자희망금액',
					maxlength   : 6,
					wrap        : {postfix: {text:'백만원',cls:'align-self-center flex-b50px'}},
					callback    : (options.updateYn == 'Y' ? false : _functions.doDisabled)
				},
			}]]
		});
	}
	_create();
	return this;
};

// 투자조합정보
//=============================================================================
$.fn.appBizInvstrList = function( args ) {
	
	let options = $.extend({
		userNo  : false,      // 사용자번호
		bzentyNo: false,      // 업체번호
		accessYn: false,      // 승인여부
		rprsYn  : false,      // 대표여부
		cardId  : 'appBizInvstrCard'
	}, args);
	
	let _this  = $(this);
	_this.append(
		$.domUtil.getTitleBox({
			title   : '조합정보', 
			titleBox: 'table-top-1',
			icon    : '<i class="cycle-ver-1 icon-buildings titicon"></i>'
		})
	);
	// 투자조합 목록조회
	let _rows = $.ajaxUtil.ajaxDefault(getUrl('/usr/mypage/mypage/getListInvstr.do'),{});
	
	if (_rows && 
		_rows.length > 0) {
		$.each(_rows, function(i, row) {
			_this.append(
				$('<div class="'+options.cardId+'"></div>').appCard({
					cls          : 'mb-16px', 
					titleOptions : false, 
					defaultValue : STRING.NOINFO,
					data         : row,
					items        : [
						{colCls: 'col-12 col-md-6 col-xl-4', name:'fundNm'        ,label:'펀드명'      },
						{colCls: 'col-12 col-md-6 col-xl-4', name:'invtFldNm'     ,label:'투자유형'    },
						{colCls: 'col-12 col-md-6 col-xl-4', name:'fundOperScale' ,label:'펀드규모'  , formatter: $.formatUtil.toFundScale},
						{colCls: 'col-12 col-md-6 col-xl-4', name:'orgzYmd'       ,label:'결성일'    , formatter: $.formatUtil.toDashDate},
						{colCls: 'col-12 col-md-6 col-xl-4', name:'invtPrd'       ,label:'투자기간'  , formatter: $.gridUtils.FORMAT.fundPrd},
						{colCls: 'col-12 col-md-6 col-xl-4', name:'rprsTelno'     ,label:'대표연락처', formatter: $.formatUtil.toPhone},
						{colCls: 'col-12 col-md-6 col-xl-4', name:'rprsHmpgAddr'  ,label:'대표홈페이지'}
					]
				})
			);
		});
	}
	else {
		_this.append(
			$.domUtil.getEmptyBox('- '+STRING.NOINFO)
		);
	}
	return this;
};
