/**
*******************************************************************************
***    명칭: openGroup.js
***    설명: 마이페이지 - 그룹관리 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.27    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	
	let P_PAGE_CD  = $('#pageCd'  ).val(); // 페이지구분코드
	let P_ENT_NO   = $('#bzentyNo').val(); // 업체번호
	let P_ACCESS   = $('#accessYn').val(); // 승인여부
	let P_GROUP_CD = $('#groupCd' ).val(); // 그룹코드
	let P_RPRS_YN  = $('#rprsYn'  ).val(); // 대표여부
	let P_FORM     = $('#mypageForm'    ); // 그룹관리폼

    //========================================================//
    // 구분 탭 생성
    //--------------------------------------------------------//
	$('#appMenuTab').appBbsTabs({
		wrapCls : 'shadow-box-1 mb-24px px-6px px-md-24px',
		url     : getUrl('/com/common/getComboCode.do'),
		params  : {upCdId: CODE.MYPG_GROUP_SE.code},
		value   : P_PAGE_CD,
		filter  : function(r) {
			// 그룹코드인 경우
			if (r['code'] == CODE.MYPG_GROUP_SE.GCODE)
				return true;
			// 경영체인 경우
			if (SCREEN.ROLE.ENT)
				return true;
			// 유관기관은 권한관리 제외
			if (r['code'] == CODE.MYPG_GROUP_SE.GROLE && SCREEN.ROLE.RIS)
				return false;
			// 투자자/유관기관인 경우 승인후 관리가능
			if (P_ACCESS == 'Y')
				return true;
			return false;
		},
		select  : function(v) {
			
			$('#pageCd').val(v);
		    //========================================================//
		    // 그룹관리 생성
		    //--------------------------------------------------------//
			$('#appContents').appMyPageGroup({
				form     : P_FORM,    // 폼객체
				pageCd   : v,         // 페이지구분코드
				bzentyNo : P_ENT_NO,  // 업체번호
				accessYn : P_ACCESS,  // 승인여부
				rprsYn   : P_RPRS_YN, // 대표여부
				groupCd  : P_GROUP_CD // 그룹코드
			});
		}
	});	
});

// 그룹관리
//=============================================================================
$.fn.appMyPageGroup = function( args ) {
	
	let options = $.extend({
		form    : false,  // 폼객체
		pageCd  : false,  // 페이지구분코드
		pageNm  : false,  // 페이지구분명칭
		bzentyNo: false,  // 업체번호
		accessYn: false,  // 승인여부
		rprsYn  : false,  // 대표여부
		groupCd : false   // 그룹코드
	}, args);
	
	let _this = $(this);

	_this.html('');
	
	// 그룹코드
	if (options.pageCd == CODE.MYPG_GROUP_SE.GCODE) {
		// 업체기본정보 조회
		$.ajaxUtil.ajaxLoad(
			// 조회 URL
	        getUrl('/usr/invest/ent/getEnt.do'),
			// 조회 조건
			{bzentyNo: options.bzentyNo},
			// 조회 콜백
	        function(result) {
	            var data = result.Data;
	            if (data) {
					// 정보영역 생성
					_this.appMyPageGroupCode($.extend({
						data: data
					},options));
	            }
	        }
	    );
	}
	// 멤버관리 / 권한관리
	else if (options.pageCd == CODE.MYPG_GROUP_SE.GMEMBER ||
			 options.pageCd == CODE.MYPG_GROUP_SE.GROLE) {
		// 목록영역 생성
		_this.appMyPageGroupMembers(options);
	}
	return this;
};

// 코드설정 정의
//=============================================================================
$.fn.appMyPageGroupCode = function ( args ) {
	
	let options = $.extend({
		form    : false,  // 폼객체
		data    : false,  // 정보데이터
		pageCd  : false,  // 페이지구분코드
		pageNm  : false,  // 페이지구분명칭
		bzentyNo: false,  // 업체번호
		accessYn: false,  // 승인여부
		rprsYn  : false,  // 대표여부
		groupCd : false,  // 그룹코드
		errorId : 'app-error-comment', // 에러메세지 영역ID
		shareId : 'btnShare'         , // 공유하기 버튼ID
		saveId  : 'btnSave'          , // 저장하기 버튼ID
	}, args);
	
	let _this = $(this);
	
	// 기능함수목록
	let _functions = {
		// 그룹코드 저장하기 버튼생성
		//---------------------------------------
		getSaveButton: function() {
			let btn = $('<button type="button" id="'+options.saveId+'" class="btn-primary bs-m bs-md-l"><i class="icon-edit"></i>저장하기</button>');
			// 저장하기 버튼클릭 이벤트 처리
			btn.bind('click', _functions.doSaveCode);
			return btn;
		},
		// 그룹코드 공유하기 버튼생성
		//---------------------------------------
		getShareButton: function() {
			let btn = $('<button type="button" id="'+options.shareId+'" class="btn-black bs-m bs-md-l mr-5px"><i class="icon-paperclip"></i>공유하기</button>');
			// 공유하기 버튼클릭 이벤트 처리
			btn.bind('click', _functions.doShareCode);
			return btn;
		},
		// 그룹코드 입력 및 저장처리
		//---------------------------------------
		doSaveCode: function() {
			$('#'+options.errorId).html('');
			$('#'+options.shareId).hide();
			let t = $(this).closest('.day').find('input[name="saveGroupCd"]');
			let v = t.val();
			if (v.length < 10) {
				$.commMsg.alert('10자리의 [영문 대문자+숫자]를 입력하세요.', function() {
					if (options.groupCd)
						t.val(options.groupCd);
					t.focus();
				});
				return false;
			}
			v = v.toUpperCase();
			if (options.groupCd != v) {
				let b = $.ajaxUtil.ajaxDefault(getUrl('/usr/mypage/group/unique.do'),{groupCd: v});
				if (b == 'Y') {
					let obj = {
						act        : ACT.GROUP_CODE,
						mode       : MODE.UPDATE,
						pageCd     : options.pageCd,
						bzentyNo   : options.bzentyNo,
						saveGroupCd: v
					};
					$.commMsg.confirm("변경한 그룹코드로 저장하시겠습니까?", function() {
						// 그룹코드 저장처리
			            $.ajaxUtil.ajaxSave(
			                getUrl('/usr/mypage/group/saveGroupCode.do'), 
			                JSON.stringify(obj),
			                function(ret) {
			                    $.ajaxUtil.success(ret, function() {
									$.commMsg.alert('성공적으로 저장되었습니다.');
									_this.find('input[name="saveGroupCd"]').prop('readonly', true);
									_this.find('input[name="saveGroupCd"]').addClass('app-readonly');
									_this.find('.app-button').html('');
									_this.find('.app-button').append(_functions.getShareButton());
									options.groupCd = _this.find('input[name="saveGroupCd"]').val();
			                    });
			                }
			            );
					}, function() {
						t.val(options.groupCd);
						if ($.commUtil.empty(options.groupCd))
							$('#'+options.shareId).hide();
						else
							$('#'+options.shareId).show();
					});
				}
				else {
					$('#'+options.errorId).append('<i class="icon-exclamation-circle"></i>');
					$('#'+options.errorId).append('* 사용중인 코드입니다.');
					$('#'+options.shareId).hide();
				}
			}
		},
		// 그룹코드 공유처리 (버튼클릭 이벤트)
		//---------------------------------------
		doShareCode: function() {
			// 입력한 그룹코드값
			let text = $(this).closest('.day').find('input[name="saveGroupCd"]').val();
			
			try {
			    $.commUtil.copyText(text);
				// 복사가 완료되면 이 부분이 호출된다.
				$.commMsg.alert('그룹코드['+text+'] 복사가 완료되었습니다.');
			}
			catch(e) {
			    console.error(e);
			}
			
			// writeText()의 인자로 넣은 텍스트가 복사된다.
			//window.navigator.clipboard.writeText(text).then(() => {
				// 복사가 완료되면 이 부분이 호출된다.
				//$.commMsg.alert('그룹코드['+text+'] 복사가 완료되었습니다.');
			//});
		}
	};
	
	// 그룹코드박스 생성
	let _createCode = function() {
		let div = $('<div class="form-area-box"></div>');
		div.append('<div class="day"></div>');
		div.find('.day').append('<div class="row"></div>');
		div.find('.day > .row').append('<div class="col"><div class="ele-icon-box app-input"></div></div>');
		div.find('.day > .row').append('<div class="col flex-grow-0 white-space-nowrap app-button"></div>');
		// 오류메세지 라인
		div.find('.day').append('<div class="form-area-box app-error-line"></div>');
		div.find('.day > .app-error-line').append('<div class="bottom-lable"><div class="row"><div class="col"></div></div></div>');
		div.find('.day > .app-error-line .col').append('<p class="text-red text-end" id="'+options.errorId+'"></p>');
		
		// 승인여부 확인
		if (options.accessYn == 'Y') {
			let inp = $('<input type="text" name="saveGroupCd" value="'+options.groupCd+'" maxlength="10" style="text-transform: uppercase;">');
			inp.prop('placeholder', '10자리를 입력해 주세요 (영문 대문자+숫자)');
			inp.prop('title', 'input');
			// 영문숫자만 입력
			bindOnlyAlphaNumeric(inp);
			
			div.find('.app-input' ).append(inp);
			if ($.commUtil.empty(options.groupCd)) {
				div.find('.app-button').append( _functions.getSaveButton());
			}
			else {
				inp.prop('readonly', true);
				inp.addClass('app-readonly');
				div.find('.app-button').append( _functions.getShareButton());
			}
		}
		else {
			div.find('.app-input' ).append('<p class="text-red">* 관리자 승인 후 설정 가능합니다.</p>');
		}
		return div;
	};
	
	// 그룹관리 설명글 박스 생성
	let _createContent = function() {
		
		let items = [
			'소속된 기업의 사업자등록번호를 최초로 플랫폼에 등록한 회원이 관리자 승인 후에 해당 기업의 대표 계정으로 설정됩니다.',
			'대표 계정은 그룹관리 페이지를 통해 그룹관리가 가능합니다.',
			'그룹코드를 설정하고 내 그룹에 포함할 사용자에게 공유해 주세요. (공유하기 버튼 클릭 시 그룹코드가 복사됩니다)',
			'그룹코드를 공유받은 사용자는 가입시 (또는 마이페이지 기업회원 전환하기를 통해) 그룹코드 입력하면 해당 기업의 그룹으로 참여할 수 있습니다'
		];
		
		let ul = $('<ul class="app-dots"></ul>');
		$.each(items, function(i,item) {
			ul.append( $('<li class="dot"></li>').append(item) );
		});
		let div = $('<div class="designbox1 mb-32px"></div>');
		div.append('<div class="align-items-center row"></div>');
		div.find('.row').append('<div class="col-12 col-md flex-md-grow-0 app-col1"></div>');
		div.find('.row').append('<div class="col app-col2"></div>');
		div.find('.app-col1').append('<img src="'+getUrl('/images/sub/ServiceImg1.svg')+'" alt="">');
		div.find('.app-col2').append('<p class="txt1"></p>');
		div.find('.app-col2').append('<span class="txt2 d-block lh-20px mt-16px "></span>');
		div.find('.app-col2 > p'   ).append('우리 기업 사용자들을 하나의 그룹으로 관리해보세요!');
		div.find('.app-col2 > span').append(ul);
		return div;
	};
	

	_this.html('');
	// 그룹코드 설명글 생성
	_this.append(_createContent());
	// 기업정보영역 생성 (경영체인 경우)
	_this.append('<div id="appInfoCard" class="mb-32px"></div>');
	// 그룹코드영역 생성
	_this.append('<div id="appCodeCard" class="mb-32px"></div>');
	
	// 기업기본정보 정의
	$('#appInfoCard').appBizInfoCard({
		// 기업정보 데이터
		data: options.data,
		// 기업정보 제목 
		titleOptions: {
			title: '기업 기본정보', 
			icon: '<i class="cycle-ver-1 icon-buildings titicon"></i>'
		}
	});
	// 그룹코드정보 정의
	$('#appCodeCard').appCard({
		titleOptions: {
			title: '그룹코드', 
			icon: '<i class="cycle-ver-1 icon-unlock titicon"></i>'
		},
		items: [{colCls: 'col', formatHtml: _createCode}]
	});
	if ($.commUtil.empty(options.groupCd))
		$('#'+options.shareId).hide();
	else
		$('#'+options.shareId).show();
	
	return this;
};

// 멤버목록 정의
//=============================================================================
$.fn.appMyPageGroupMembers = function ( args ) {
	
	let options = $.extend({
		form    : false,  // 폼객체
		pageCd  : false,  // 페이지구분코드
		pageNm  : false,  // 페이지구분명칭
		bzentyNo: false   // 업체번호
	}, args);
	
	let _this = $(this);

	// 목록 옵션 반환
	let _getGridOptions = function() {
		
		// 권한콤보목록 가져오기 (목록 콤보박스에서 사용됨)
		let codes = $.ajaxUtil.ajaxDefault(
			getUrl('/com/common/getComboCode.do'), 
			{upCdId:CODE.AUTH_SE.code}
		);;

		// 목록포맷
		let _format = {
			// 신규아이콘
			//---------------------------------------
			newIcon: function(v,o) {
				let c = $('<div></div>');
				if (o['newYn'] == 'Y')
					c.append('<span class="bs-xs btn-primary-ouline newicon">NEW</span>');
				c.append(v);
				return c;
			},
			// 히든박스
			//---------------------------------------
			hiddenBox: function(v,o) {
				let c = $('<div></div>');
				if (o['newYn'] == 'Y')
					c.append('<span class="bs-xs btn-primary-ouline newicon">NEW</span>');
				c.append(v);
				c.append('<input type="hidden" name="userNo" value="'+o['userNo']+'"/>');
				return c;
			},
			// 삭제버튼
			//---------------------------------------
			btnRemove: function(v,o) {
				let btn = $('<button type="button" class="btn-black bs-s">삭제</button>');
				btn.data('oper'  , 'S');
				btn.data('usernm', o['userNm']);
				btn.data('userno', o['userNo']);
				btn.data('rprsyn', o['rprsYn']);
				// 버튼클릭시 삭제처리
				btn.click(_functions.doRemove);
				return btn;
			},
			// 체크박스
			//---------------------------------------
			checkBox: function(v,o) {
				let c = $('<div class="check-radio-box only-ver"></div>');
				c.append('<input type="checkbox" id="userNo'+v+'" name="userNo" class="app-userno app-user-item" value="'+v+'">');
				c.append('<label for="userNo'+v+'"></label>');
				c.find('input').data('usernm', o['userNm']);
				c.find('input').data('userno', o['userNo']);
				c.find('input').data('rprsyn', o['rprsYn']);
				c.find('input').bind('click', bindUnlockAll);
				return c;
			},
			// 체크박스(전체선택)
			//---------------------------------------
			checkAll: function() {
				let c = $('<div class="check-radio-box only-ver"></div>');
				c.append('<input type="checkbox" id="userNoALL" name="userNo" class="app-userno" value="ALL">');
				c.append('<label for="userNoALL"></label>');
				c.find('input').data('userNo', 'ALL');
				c.find('input').bind('click', bindCheckAll);
				return c;
			},
			// 권한 콤보박스
			//---------------------------------------
			comboBox: function( copts ) {
				
				let v = copts['value' ];
				let c = copts['column'];
				
				return $('<div></div>').appDropdownBox({
					type    : 'static',
					rows    : codes,
					value   : v,
					input   : {name: c['name']}
				});
			},
			// 대표계정변경(전체선택)
			//---------------------------------------
			switchCn: function(item) {
				let s = $('<span class="dot"></span>');
				s.append([
					'대표계정은 그룹당 1명만 허용됩니다.',
					'그룹 내 멤버 1인을 대표 계정으로 변경 설정 가능하며, ',
					'변경 설정 시 이전 대표계정은 멤버계정(제한권한)으로 변경됩니다.'
				].join(''));
				
				let c = $('<a href="#" class=" checkFc check-radio-box d-none d-md-inline-block"></a>');
				c.append('<label class="icon-info-circle ml-5px text-primary"></label>');
				c.append('<span class="toltip" style="width:300px;"></span>');
				c.find('.toltip').append('<p>대표계정 변경</p>');
				c.find('.toltip').append(s);
				c.hover(function(){
					$(this).parent().find('.toltip').addClass('on');
				},function(){
					$(this).parent().find('.toltip').removeClass('on');
			   	});
				let p = $('<b></b>');
				p.append(item['label']);
				p.append(c);
				
				return p;
			},
			// 대표계정변경 스위치박스
			//---------------------------------------
			switchBox: function(v,o) {
				
				let div = $('<div></div>');
				div.data('value' , (o['rprsYn']== 'Y' ? o['userNo'] : ''));
				div.data('usernm', o['userNm']);
				
				return div.appSwitchBox({
					name    : 'rprsYn',
					id      : 'rprsYn'+o['userNo'],
					label   : '대표',
					offLabel: '멤버',
					value   : o['userNo'],
					// 대표계정 변경처리
					change  : _functions.doChange
				});
			},
		};
		// 목록옵션
		let _options = {
			autoload:   true,
			idField:    'userNo',
			pagination: {display: 10},
			paging:     '#appGridPagination',
			toolbar:    '#appGridToolbar',
			title:      options.pageNm,
			params:     {srchMode: '', pageCd: options.pageCd},
			url:        getUrl('/usr/mypage/group/getListGroup.do'),
			listOptions: {
				gridCls:  'table-box-1 m-overbox overflow-md-visible',
				colgroup: [],
				columns:  []
			},
			toolbarOptions: {
				cls:    'shadow-box-1 mb-24px p-2',
				boxCls: 'design-txt-1',
				items:  []
			}
		};
		// 목록칼럼
		let _columns = [];
		// 툴바항목
		let _toolbar = [{
			cls:   'col-6 col-md flex-b-md-240px',
			format: function() {
				let div = $('<div class="form-area-box1"></div>');
				div.append('<div class="ele-icon-box"></div>');
				div.find('.ele-icon-box').append('<input type="text" id="srchName" name="srchName" placeholder="멤버 이름을 검색해 보세요" class="h-100">');
				div.find('.ele-icon-box').append('<a id="btnSearch" href="javascript:void(0)" class="icon-search"></a>');
				// 버튼클릭시 멤버이름 검색처리
				div.find('.icon-search').click(_functions.doSearch);
				// 엔터입력시 멤버이름 검색처리
				bindEnter(
					div.find('input[name="srchName"]'), 
					div.find('.icon-search')
				);
				return div;
			}
		}];
		// 권한별 제외항목
		let _excepts = [];
		// 멤버관리
		if (options.pageCd == CODE.MYPG_GROUP_SE.GMEMBER) {
			_columns.push({width:'100px', cls:'app-c', name:'userNo'   , label:'선택'        , formatter: _format.checkBox, labelFormatter: _format.checkAll});
		    _columns.push({width:'150px', cls:'app-c', name:'userNm'   , label:'이름'        , formatter: _format.newIcon});
		    _columns.push({width:'200px', cls:'app-c', name:'bzentyNm' , label:'소속'        });
		    _columns.push({width:'200px', cls:'app-c', name:'userId'   , label:'ID'          });
		    _columns.push({width:'150px', cls:'app-c', name:'joinYmd'  , label:'계정 생성일' , formatter: $.formatUtil.toDashDate});
		    _columns.push({width:'100px', cls:'app-c', name:'sttsNm'   , label:'멤버상태'    });
			_columns.push({width:    '*', cls:'app-c', name:'rprsYn'   , label:'대표계정변경', formatter: _format.switchBox, labelFormatter: _format.switchCn});
			_columns.push({width:'100px', cls:'app-c', name:'userNo'   , label:'관리'        , formatter: _format.btnRemove});
			// 권한별 제외처리
			if (SCREEN.ROLE.ENT || SCREEN.ROLE.INV)
				_excepts.push('rprsYn');
			_toolbar.push({cls: 'col', format: function() {
				let del = $('<button type="button" class="bs-m btn-black w-100"><i class="icon-trash"></i>선택한 사용자를삭제</button>');
				del.data('oper', 'M');
				// 버튼클릭시 다중삭제처리
				del.click(_functions.doRemove);
				return del;
			}});
		}
		// 권한관리
		else if (options.pageCd == CODE.MYPG_GROUP_SE.GROLE) {
			_cgroups = ['200px'];
			_columns.push({width: '200px', cls:'app-c', name:'userNm', label:'이름'          , formatter: _format.hiddenBox});
			_columns.push({width:     '*', cls:'app-c', name:'menu01', label:'IR작성하기'    , formatterObject: _format.comboBox});
			_columns.push({width:     '*', cls:'app-c', name:'menu02', label:'매칭설정'      , formatterObject: _format.comboBox});
		    _columns.push({width:     '*', cls:'app-c', name:'menu03', label:'신청내역'      , formatterObject: _format.comboBox});
			_columns.push({width:     '*', cls:'app-c', name:'menu04', label:'IR검토의견등록', formatterObject: _format.comboBox});
			_columns.push({width:     '*', cls:'app-c', name:'menu00', label:'기업정보'      , formatterObject: _format.comboBox});
			_columns.push({width: '150px', cls:'app-c', name:'rprsYn', label:'대표계정변경'  , formatter: _format.switchBox, labelFormatter: _format.switchCn});
			// 권한별 제외처리
			if (SCREEN.ROLE.ENT) {
				_excepts.push('menu04');
			}
			else if (SCREEN.ROLE.INV) {
				_excepts.push('menu00');
				_excepts.push('menu01');
				_excepts.push('menu02');
			}
			_toolbar.push({cls: 'col', format: function() {
				let btn = $('<button type="button" class="bs-m btn-primary"><i class="icon-edit"></i>저장</button>');
				// 버튼클릭시 저장처리
				btn.click(_functions.doSave);
				return btn;
			}});
		}

		$.each(_columns, function(i,c) {
			// 권한별 예외항목 SKIP
			if (_excepts.find((v,j) => v === c.name))
				return true;
			_options.listOptions.colgroup.push(c['width']);
			delete c['width'];
			_options.listOptions.columns.push(c);
		});
		_options.toolbarOptions.items = _toolbar;
		return _options;
	};
	
	// 기능함수목록
	let _functions = {
		// 멤버이름 검색
		//---------------------------------------
		doSearch: function() {
			let obj = options.form.serializeObject();
			// 멤버이름 검색어 추가
			$.extend(obj, {srchName: $('#srchName').val()});
			// 검색 처리
			$('#appGrid').appGrid('search', obj);
			return false;
		},
		// 멤버 선택삭제 / 개별삭제
		//---------------------------------------
		doRemove: function() {
			let oper = $(this).data('oper');
			let rows = [];
			let msg  = '';
			// 다중 삭제인 경우
			if (oper == 'M') {
				let chk  = true;
				_this.find('input[name="userNo"]').each(function() {
					if ($(this).is(':checked')) {
						let u = $(this).data('userno');
						let r = $(this).data('rprsyn');
						if (r == 'Y') {
							chk = false;
							return false;
						}
						rows.push({userNo: u});
					}
				});
				if (chk === false) {
					$.commMsg.alert('대표계정은 삭제하실 수 없습니다. 먼저 대표계정을 변경하세요.');
					return false;
				}
				if (rows.length == 0) {
					$.commMsg.alert('삭제할 그룹회원을 1명 이상 선택하세요.');
					return false;
				}
				msg = '['+rows.length+'명]의 그룹회원을';
			}
			// 단일 삭제인 경우
			else {
				let u = $(this).data('userno');
				let r = $(this).data('rprsyn');
				let n = $(this).data('usernm');
				if (r == 'Y') {
					$.commMsg.alert('대표계정은 삭제하실 수 없습니다. 먼저 대표계정을 변경하세요.');
					return false;
				}
				rows.push({userNo: u});
				
				msg = '['+n+'] 그룹회원을';
			}
			let obj = {
				mode     : MODE.REMOVE,
				pageCd   : options.pageCd,
				bzentyNo : options.bzentyNo,
				saveList : rows
			};
	        $.commMsg.confirm(msg+' 정말 삭제하시겠습니까?', function() {
				// 멤버삭제처리
	            $.ajaxUtil.ajaxSave(
	                getUrl('/usr/mypage/group/deltGroupMember.do'), 
	                JSON.stringify(obj),
	                function(ret) {
	                    $.ajaxUtil.success(ret, function() {
							$.commMsg.alert('성공적으로 삭제되었습니다.', function() {
								// 검색함수 실행
								_functions.doSearch();
							});
	                    });
	                }
	            );
	        });
		},
		// 권한설정 저장처리
		//---------------------------------------
		doSave: function() {
			let rows = [];
			let keys = ['menu00','menu01','menu02','menu03','menu04'];
			
			_this.find('[name="userNo"]').each(function(i) {
				let row = {userNo: $(this).val()};
				$.each(keys, function(j,key) {
					if (_this.find('[name="'+key+'"]').eq(i).length > 0)
						row[key] = _this.find('[name="'+key+'"]').eq(i).val();
				});
				rows.push(row);
			});
			if (rows.length == 0) {
				$.commMsg.alert('저장할 항목이 없습니다.');
				return false;
			}
			let obj = {
				act      : ACT.GROUP_ROLE,
				mode     : MODE.UPDATE,
				pageCd   : options.pageCd,
				bzentyNo : options.bzentyNo,
				saveList : rows
			};
	        $.commMsg.confirm("설정된 권한을 일괄 변경하시겠습니까?", function() {
				// 권한설정 저장처리
	            $.ajaxUtil.ajaxSave(
	                getUrl('/usr/mypage/group/saveGroupRole.do'), 
	                JSON.stringify(obj),
	                function(ret) {
	                    $.ajaxUtil.success(ret, function() {
							$.commMsg.alert('성공적으로 저장되었습니다.', function() {
								// 검색함수 실행
								_functions.doSearch();
							});
	                    });
	                }
	            );
	        });
		},
		// 대표계정 변경처리
		//---------------------------------------
		doChange: function() {
			if ($(this).is(':checked')) {
				let user = $(this).val();
				let cbox = $(this);
				let name = $(this).closest('.check-radio-box').data('usernm');
				
				$('<div></div>').appPopup({
					appendBody: true,
					title: '대표계정 변경',
					icon:  '<img src="'+getUrl('/images/sub/Sketch_on_smartphone.svg')+'">',
					dialogCls: 'position-nomal',
					style: 'max-width:800px;',
					// 닫기버튼 클릭시 처리함수
					onhidden: function() {
						cbox.prop('checked', false);
					},
					message: function(cmp) {
						// 취소버튼 클릭
						let cbtn = $('<button type="button" class="btn-combi-3 w-100 bs-l"><i class="icon-times"></i>취소</button>');
						cbtn.click(function() {
							cmp.close();
							return false;
						});
						// 변경버튼 클릭
						let mbtn = $('<button type="button" class="btn-primary w-100 bs-l"><i class="icon-edit"></i>변경</button>');
						mbtn.click(function() {
							cmp.close();
							let obj = {
								act      : ACT.GROUP_RPRS,
								mode     : MODE.UPDATE,
								pageCd   : options.pageCd,
								bzentyNo : options.bzentyNo,
								userNo   : user
							};
							// 대표계정 변경처리
				            $.ajaxUtil.ajaxSave(
				                getUrl('/usr/mypage/group/saveGroupRprs.do'), 
				                JSON.stringify(obj),
				                function(ret) {
				                    $.ajaxUtil.success(ret, function() {
										$.commMsg.alert('성공적으로 변경되었습니다.', function() {
											// 변경후 메인페이지로 이동 처리
											$.bizUtils.goHome();
										});
				                    });
				                }
				            );
							return false;
						});
						
						let box = $('<div class="designbox1 p-16px"></div>');
						box.append('<div class="align-items-center row"></div>');
						box.find('.row').append('<div class="col-12 col-md flex-md-grow-0"></div>');
						box.find('.row').append('<div class="col"></div>');
						box.find('.row > .col-12').append('<i class="fs-46px icon-question-circle text-primary"></i>');
						box.find('.row > .col'   ).append('<p class="txt1">'+name+' 님을 대표계정으로 설정 하시겠습니까?</p>');
						box.find('.row > .col'   ).append('<p class="txt2">변경 버튼 클릭 시 기존대표계정은 멤버계정(제한권한)으로 변경되며, 그룹관리 접근이 불가합니다.</p>');
						
						let btm = $('<div class="bottom-box-btn py-24px"></div>');
						btm.append('<div class="row"></div>');
						btm.find('.row').append('<div class="col"></div>');
						btm.find('.row').append('<div class="col"></div>');
						btm.find('.row > .col:first').append(cbtn);
						btm.find('.row > .col:last' ).append(mbtn);
						
						let div = $('<div></div>');
						div.append(box);
						div.append(btm);
						return div;
					}
				});
				return true;
			}
			else {
				$.commMsg.alert([
					'대표계정을 멤버계정으로 변경은 불가능합니다.',
					'다른 멤버계정를 대표계정으로 변경하시면', 
					'자동으로 대표계정이 해당 멤버계정에게 위임되며,',
					'기존의 대표계정은 멤버계정으로 전화됩니다.'
				].join('<br>'));
				if ($(this).val() == $(this).data('value')) {
					$(this).prop('checked', true);
				}
				return false;
			}
		}
	};
	
	// 목록영역 생성
	_this.html('');
	_this.append('<div id="appGridToolbar"></div>');
	_this.append('<div class="shadow-box-1 px-16px pb-24px"></div>');
	_this.find('.shadow-box-1').append('<div id="appGrid"></div>');
	_this.find('.shadow-box-1').append('<div class="mb-0 paging-box pb-24px"></div>');
	_this.find('.paging-box'  ).append('<ul id="appGridPagination" class="d-inline-block"></ul>');
	
	// 그리드 객체 생성
    $('#appGrid').appGrid(_getGridOptions()).appGrid('init');
	
	return this;
};
