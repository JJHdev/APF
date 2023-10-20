/**
*******************************************************************************
***    명칭: openSprt.js
***    설명: 마이페이지 - 신청내역 화면
***
***    $.fn.appMyPageSprt          : 신청내역 구성
***    $.fn.appPopupMyPageSprtView : 신청정보조회 팝업 구성
***    $.fn.appPopupMyPageSprtForm : 신청정보수정 팝업 구성
***    $.fn.appMyPageSprtStatus    : 신청상태조회 (결과확인/진행현황) 구성
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	
	let P_APLY_CD  = $('#aplyCd'  ).val(); // 신청종류코드
	let P_APLY_NM  = $('#aplyNm'  ).val(); // 신청종류명칭
	let P_APLY_TY  = $('#aplyType').val(); // 신청타입
	let P_PRGRM_NO = $('#prgrmNo' ).val(); // 프로그램번호
	let P_UPDATE_YN= $('#updateYn').val(); // 수정가능여부
	
	// 화면 내의 기본검색조건
	let P_SEARCH   = {};

    //========================================================//
    // 버튼 탭 생성
    //--------------------------------------------------------//
	$('#appButtonTabs').appButtonTabs({
		url    : getUrl('/usr/mypage/support/getListAplyGroup.do'),
		params : {},
		value  : P_APLY_CD,
		select : function(code) {
			if (code != P_APLY_CD) {
				$('#aplyCd' ).val(code);
				$('#prgrmNo').val('');
				$.formUtil.submitForm(getUrl('/usr/mypage/support/openSprt.do'), {
					formId: 'mypageForm'
				});
			}
		}
	});

    //========================================================//
    // 제목 생성
    //--------------------------------------------------------//
	let _titOpts = {
		title    : P_APLY_NM,
		titleBox : 'tit-box-3 mb-24px',
		icon     : '<img src="'+getUrl('/images/titimg-2.svg')+'" alt="">'
	};
	// 미팅신청 (투자자)인 경우 정렬버튼 표시
	if (P_APLY_CD == CODE.MYPG_APLY_SE.MEETING) {
		$.extend(_titOpts, {
			// 우측영역 포맷터
			rightFormatter: function(dom) {
				dom.addClass('flex-b210px');
				let div = $('<div class="capsule-box"></div>');
				div.append('<div class="row"></div>');
				div.find('.row').append('<div class="col app-order active" data-value="A">최신순</div>');
				div.find('.row').append('<div class="col app-order" data-value="B">오래된순</div>');
				div.find('.app-order').click(function() {
					div.find('.app-order').removeClass('active');
					$(this).addClass('active');
					// 정렬버튼 클릭시 검색 처리
					P_SEARCH['srchType'] = $(this).data('value');
					$('#appGrid').appGrid('search', P_SEARCH);
				});
				dom.append(div);
			}
		});
	}
	$('#appMenuTitle').append($.domUtil.getTitleBox(_titOpts));
		
    //========================================================//
    // 구분 탭 생성
    //--------------------------------------------------------//
	// 크라우드펀딩 또는 경영체지원인 경우 탭표시
	if (P_APLY_CD == CODE.MYPG_APLY_SE.CROWD ||
		P_APLY_CD == CODE.MYPG_APLY_SE.FUND  ) {
			
		let _opts = {wrapCls : 'shadow-box-1 mb-24px px-24px'};
		if (P_APLY_CD == CODE.MYPG_APLY_SE.CROWD) {
			$.extend(_opts, {
				url     : getUrl('/usr/support/support/getListPrgrm.do'),
				params  : {sprtSeCd: P_APLY_CD},
				value   : P_PRGRM_NO,
				select  : function(v) {
					// 세부프로그램 기준 검색 처리
					P_SEARCH['prgrmNo'] = v;
					$('#prgrmNo').val(v);
					$('#appGrid').appGrid('search', P_SEARCH);
				}
			});
		}
		else {
			$.extend(_opts, {
				items : $.merge([{code: 'ALL', text:'전체'}],STORE.MYPG_STUS),
				value : 'ALL',
				select: function(v) {
					// 상태구분탭 기준 검색 처리
					P_SEARCH['sprtSttsCd'] = (v == 'ALL' ? '' : v);
					$('#appGrid').appGrid('search', P_SEARCH);
				}
			});
		}
		$('#appMenuTab').appBbsTabs(_opts);	
	}

    //========================================================//
    // 신청내역 생성
    //--------------------------------------------------------//
	$('#appContents').appMyPageSprt({
		aplyCd   : P_APLY_CD,  // 신청종류코드
		aplyNm   : P_APLY_NM,  // 신청종류명칭
		aplyType : P_APLY_TY,  // 신청타입
		prgrmNo  : P_PRGRM_NO, // 프로그램번호
		updateYn : P_UPDATE_YN,
		// 검색 콜백함수
		searchCallback: function( params ) {
			let p = {};
			$.extend(p, P_SEARCH);
			if (params)
				$.extend(p, params);
			$('#appGrid').appGrid('search', p);
		}
	});
});


// 신청내역
//=============================================================================
$.fn.appMyPageSprt = function( args ) {
	
	let options = $.extend({
		aplyCd   : false, // 신청종류코드
		aplyNm   : false, // 신청종류명칭
		aplyType : false, // 신청타입
		prgrmNo  : false, // 프로그램번호
		updateYn : false, // 수정간으여부
		// 검색 콜백함수
		searchCallback: function() {}
	}, args);
	
	let _this = $(this);

	// 목록포맷
	let _format = {
		// 지원신청 - 접수일 포맷
		//---------------------------------------
		rcptDate: function(v,o) {
			// 임시저장인 경우
			if (v == CODE.STATUS_CD.TMPSAVE)
				return '-';
			return $.formatUtil.toDashDate(v);
		},
		// 지원신청 - 처리결과 포맷
		//---------------------------------------
		status: function(v,o) {
			// 임시저장인 경우
			if (v == CODE.STATUS_CD.TMPSAVE)
				return '-';
			let sts = FILTER.getStatus(v);
			if (sts) {
				let b = $('<button type="button"></button>');
				b.addClass(sts['btn']);
				b.addClass('border-0 bs-s w-100');
				b.append('<i class="'+sts['icon']+'"></i>');
				b.append(sts['text']);
				return b;
			}
			return '';
		},
		// 지원신청 - 진행현황버튼 포맷
		//---------------------------------------
		btnPrgrs: function(v,o) {
			
			let r = $('<div class="row" style="margin:0 -2px"></div>');
			// 투자유치후이면서 심사완료인 경우 안내문 표시
			if (CODE.MYPG_APLY_SE.AFTER == options.aplyCd &&
				CODE.STATUS_CD.REVIEW   == v) {
				// 안내문 다운로드
				let b1 = $('<button type="button" class="btn-primary-ouline bs-s w-100"></button>');
				b1.append('<i class="icon-presentation-trend-up"></i>');
				b1.append('안내문');
				b1.click($.bizUtils.downloadNotice);
				r.append( $('<div class="col px-2px"></div>').append(b1) );
			}
			// 심사완료/반려/보완요청인 경우 진행현황버튼 표시
			// 2023.08.11 사업종료시에도 진행현황버튼 표시
			if ($.inArray(v,[CODE.STATUS_CD.REJECT
							,CODE.STATUS_CD.SPLMNT
							,CODE.STATUS_CD.REVIEW
							,CODE.STATUS_CD.FINISH]) >= 0) {
				// 진행현황 버튼
				let b2 = $('<button type="button" class="btn-black bs-s w-100"></button>'); // "btn-primary-ouline bs-s"
				b2.data('aplyno', o['sprtAplyNo']);
				b2.append('<i class="icon-clipboard-check"></i>');
				b2.append('진행현황');
				b2.click(function() {
					// 진행현황조회 팝업 오픈
					$('<div></div>').appPopupMyPageSprtView({
						params: {
							mode    : MODE.VIEW,
							aplyNo  : $(this).data('aplyno'),
							aplyCd  : options.aplyCd,
							prgrmNo : options.prgrmNo,
						},
						searchCallback: options.searchCallback
					});
				});
				r.append( $('<div class="col px-2px"></div>').append(b2) );
			}
			return r;
		},
		// 경영체지원(투자자) - IR보기버튼 포맷
		//---------------------------------------
		btnEntIR: function(v,o) {
			let b = $('<button type="button" class="btn-primary bs-s">IR보기</button>');
			b.data('no' , v);
			b.data('url', getUrl('/usr/invest/ent/viewEnt.do'));
			// IR보기 이동처리
			b.click(function() {
				$.formUtil.submitForm($(this).data('url'), {
					params: {bzentyNo: $(this).data('no')}
				});
			});
			return b;
		},
		// 경영체지원(투자자) - 파일다운로드버튼 포맷
		//---------------------------------------
		btnDownload: function(v,o) {
			if ($.commUtil.empty(v))
				return '';
				
			let b = $('<button type="button" class="bs-s btn-mint px-4px"><i class="icon-file-xls"></i></button>');
			b.data('sn' , v);
			b.data('url', getUrl('/usr/file/downloadBizFile.do'));
			// 파일다운로드 적용
			b.click($.bizUtils.downloadFile);
			return b;
		},
		// 경영체지원(투자자) - 지원상테아이콘 포맷
		//---------------------------------------
		btnSprtStts: function(v,o) {
			
			let color = false;
			if      (v == CODE.SPRT_STUS.WAIT) color = 'red';
			else if (v == CODE.SPRT_STUS.HOLD) color = 'gray';
			else if (v == CODE.SPRT_STUS.PASS) color = 'primary';
			
			let c = $('<p class="shapes-box justify-content-center "><em class="square"></em></p>');
			c.append(o['sprtSttsNm']);
			if (color) {
				c.find('em').addClass('bg-'+color);
				c.addClass('text-'+color);
			}
			return c;
		},
		// 경영체지원(투자자) - 업체명 체크박스 포맷
		//---------------------------------------
		checkEnt: function(v,o) {
			let c = $('<div></div>');
			if (o['newYn'] == 'Y')
				c.append('<span class="bs-xs btn-primary-ouline newicon">NEW</span>');
				
			let box = $('<input type="checkbox" id="bzentyNo'+v+'" name="bzentyNo" class="app-bzentyno" value="'+v+'">');
			box.data('bzentyno', o['bzentyNo'  ]);
			box.data('fundno'  , o['fundNo'    ]);
			box.data('status'  , o['sprtSttsCd']);
			c.append('<div class="check-radio-box"></div>');
			c.find('.check-radio-box').append(box);
			c.find('.check-radio-box').append('<label for="bzentyNo'+v+'">'+o['bzentyNm']+'</label>');
			return c;
		},
		// 경영체지원(투자자) - 상태변경 저장처리
		//---------------------------------------
		saveStatus: function() {
			let b = $('<button type="button" class="btn-primary bs-m">저장</button>');
			b.click(function() {
				let obj = {
					mode:       MODE.UPDATE,
					sprtSttsCd: $('#sprtSttsCd').val(),
					sprtList  : []
				};
				$('input.app-bzentyno').each(function() {
					if ($(this).is(':checked'))
						obj.sprtList.push({
							bzentyNo  : $(this).data('bzentyno'),
							fundNo    : $(this).data('fundno'  ),
							sprtSttsCd: $(this).data('status'  )
						});
				});
				if (!$.formUtil.validExist(obj,'sprtSttsCd', '변경할 상태를 선택하세요.'))
					return false;
				if (!$.formUtil.validExist(obj,'sprtList', '지원정보를 하나이상 선택하세요.'))
					return false;
				for (let i = 0; i < obj.sprtList.length; i++) {
					if (obj.sprtList[i]['sprtSttsCd'] == obj['sprtSttsCd']) {
						$.commMsg.alert('['+(i+1)+']번 항목의 상태값이 변경하려는 상태값과 동일합니다.');
						return false;
					}
				}
		        $.commMsg.confirm("상태를 변경하시겠습니까?", function() {
		            // AJAX로 저장처리
		            $.ajaxUtil.ajaxSave(
		                getUrl('/usr/mypage/support/updtSprt.do'), 
		                JSON.stringify(obj),
		                function(ret) {
		                    $.ajaxUtil.success(ret, function() {
								$.commMsg.alert('성공적으로 변경되었습니다.', function() {
									// 검색콜백함수 실행
									options.searchCallback();
								});
		                    });
		                }
		            );
		        });
			});
			return b;
		},
		// 미팅신청(투자자) - 받는사람 포맷
		//---------------------------------------
		linkEntIR: function(v,o) {
			let a = $('<a href="javascript:void(0"></a>');
			a.data('no' , o['trgtBzentyNo']);
			a.data('url', getUrl('/usr/invest/ent/viewEnt.do'));
			a.append(v);
			// IR 페이지로 이동처리
			a.click(function() {
				$.formUtil.submitForm($(this).data('url'), {
					params: {bzentyNo: $(this).data('no')}
				});
			});
			return a;
		}
	};

	// 목록 옵션 반환
	let _getGridOptions = function() {
		
		// 목록옵션
		let _options = {
			title:      options.aplyNm,
			autoload:   true,
			pagination: {display: 10},
			paging:     '#appGridPagination',
			listOptions: {
				headCls:   "bs-1 ts-xl",
				bodytrCls: "px-v-m t-t-c",
				colgroup:  [],
				headers:   [],
				columns:   []
			}
		}; 
		
		// 목록칼럼
		let _cols = [];
		
		// 투자전지원, 투자후지원, 크라우드펀딩 (경영체)
		if (FILTER.isSupportCode(options.aplyCd)) {
		    _cols.push({width: '150px', cols: 1, rownumbers: true, cls:'app-c',label:'연번'});
			// 기업회원이면
			if (!SCREEN.ROLE.USR) {
				_cols.push({width: '*', cols: 1, name:'aplcntNm'  ,cls:'app-l',label:'신청자명'});
			}
			// 투자후지원이면
			if (options.aplyCd == CODE.MYPG_APLY_SE.AFTER) {
				_cols.push({width: '*', cols: 1, name:'prgrmNm'   ,cls:'app-l',label:'사업명'});
			}
			_cols.push({width: '200px', cols: 1, name:'rcptYmd'   ,cls:'app-c',label:'신청서접수일', formatter: _format.rcptDate});
	
			// 투자전지원이면
			if (options.aplyCd == CODE.MYPG_APLY_SE.BEFORE) {
			    _cols.push({width: '100px', cols: 2, name:'rcptText'  ,cls:'app-c',label:'접수상태'});
				_cols.push({width: '100px', cols: 0, name:'statusCd'  ,cls:'app-l',label:'', formatter: _format.btnPrgrs});
			}
			// 투자후지원이면
			else if (options.aplyCd == CODE.MYPG_APLY_SE.AFTER) {
			    _cols.push({width: '100px', cols: 1, name:'rcptText'  ,cls:'app-c',label:'접수상태'});
				_cols.push({width: '100px', cols: 2, name:'statusCd'  ,cls:'app-l',label:'처리결과', formatter: _format.status});
				_cols.push({width: '160px', cols: 0, name:'statusCd'  ,cls:'app-l',label:''        , formatter: _format.btnPrgrs});
			}
			// 크라우드펀딩
			else {
			    _cols.push({width: '100px', cols: 1, name:'rcptText'  ,cls:'app-c',label:'접수상태'});
				_cols.push({width: '100px', cols: 2, name:'statusCd'  ,cls:'app-l',label:'처리결과', formatter: _format.status});
				_cols.push({width: '100px', cols: 0, name:'statusCd'  ,cls:'app-l',label:''        , formatter: _format.btnPrgrs});
			}
			$.extend(_options, {
				idField: 'sprtAplyNo',
				url:     getUrl('/usr/support/support/getListSprt.do'),
				params: {
					sprtSeCd: options.aplyCd,
					prgrmNo : options.prgrmNo
				}
			});
			// 2023.08.04 LSH 행선택시 이동처리
			_options.listOptions.select = function(json) {
				if (json['statusCd'] != CODE.STATUS_CD.TMPSAVE)
					return false;
				$.formUtil.submitForm(getUrl('/usr/support/support/formSprt.do'), {
					params: {
						mode       : MODE.UPDATE,
						sprtAplyNo : json['sprtAplyNo'],
						sprtSeCd   : json['sprtSeCd'  ],
						prgrmNo    : json['prgrmNo'   ],
						stepCd     : 'FORM'
					}
				});
			};
		}
		// IR지원 (경영체)
		else if (options.aplyCd == CODE.MYPG_APLY_SE.IR) {
			_cols.push({width:    '*', cols: 1, cls:'app-l', name:'fundNm'       , label:'펀드명',   formatter: $.gridUtils.FORMAT.fundNm});
			_cols.push({width:'150px', cols: 1, cls:'app-l', name:'invtFldNm'    , label:'투자분야'});
			_cols.push({width:'100px', cols: 1, cls:'app-c', name:'makeYr'       , label:'조성년도', formatter: $.formatUtil.toYear});
			_cols.push({width:'150px', cols: 1, cls:'app-c', name:'invtPrd'      , label:'투자기간', formatter: $.gridUtils.FORMAT.fundPrd});
			_cols.push({width:'160px', cols: 1, cls:'app-l', name:'invtBzentyNm' , label:'투자자',   formatter: $.gridUtils.FORMAT.invtNm});
			_cols.push({width:'140px', cols: 1, cls:'app-c', name:'rprsTelno'    , label:'전화'    , formatter: $.formatUtil.toPhone});
			_cols.push({width:'115px', cols: 1, cls:'app-r', name:'fundOperScale', label:'펀드규모', formatter: $.formatUtil.toFundScale});
			_cols.push({width:'110px', cols: 1, cls:'app-c', name:'sprtYmd'      , label:'지원일자', formatter: $.formatUtil.toDashDate});
			
			$.extend(_options, {
				idField: 'fundNo',
				url:     getUrl('/usr/invest/fund/getListFund.do'),
				params:  {srchMode: CODE.SRCH_MODE.APPLY}
			});
		}
		// 북마크 (투자자)
		else if (options.aplyCd == CODE.MYPG_APLY_SE.BOOKMARK) {
			$.extend(_options, {
				mode:    'CARD',
				idField: 'bzentyNo',
				url:     getUrl('/usr/invest/ent/getListEntBkmk.do'),
				params:  {srchMode: CODE.SRCH_MODE.BOOKMARK, bkmkSeCd: CODE.BKMK_SE.ENT},
				cardOptions: {
					formatter: $.gridUtils.FORMAT.entCard, // (app_gridutils.js)
					// 카드선택이벤트
					//select: function(row) {
					//	// 상세보기
					//	$.formUtil.submitForm(getUrl('/usr/invest/ent/viewEnt.do'), {
					//		params: {bzentyNo: row['bzentyNo']}
					//	});
					//},
					emptyText: '<p class="fs-24px fw-600 lh-1 mb-2">검색결과가 없습니다.</p>'
				}
			});
		}
		// 미팅신청 (투자자)
		else if (options.aplyCd == CODE.MYPG_APLY_SE.MEETING) {
			_cols.push({width:'155px', cols: 1, cls:'app-l', name:'trgtBzentyNm' , label:'받는사람', formatter: _format.linkEntIR});
			_cols.push({width:    '*', cols: 1, cls:'app-l', name:'aplyCn'       , label:'내용'});
			_cols.push({width:'155px', cols: 1, cls:'app-c', name:'aplyYmd'      , label:'작성일'  , formatter: $.formatUtil.toDashDate});
	
			$.extend(_options, {
				idField: 'sn',
				url:     getUrl('/usr/mypage/meeting/getListMeeting.do'),
				params:  {srchMode: 'I'}
			});
			$.extend(_options.listOptions, {
				// INLINE VIEW 적용
				extendToggle : true,
				extendRow    : function(row) {
					let tr = $('<tr class="grid-answer-row"></tr>');
					tr.addClass('px-v-xl py-v-xl');
					tr.append('<td colspan="3"><div class="answer"></div></td>');
					tr.find('.answer').append('<div class="top" style="padding-left: 205px;"></div>');
					tr.find('.answer > .top').append('<pre>'+row['aplyCn']+'</pre>');
					tr.hide();
					return tr;
				}
			});
		}
		// 경영체지원 (투자자)
		else if (options.aplyCd == CODE.MYPG_APLY_SE.FUND) {
	
			_cols.push({width:'205px', cols: 1, cls:'app-c', name:'bzentyNo'     , label:'지원정보'     , formatter: _format.checkEnt});
			_cols.push({width:    '*', cols: 1, cls:'app-l', name:'fundNm'       , label:'펀드명'       , formatter: $.gridUtils.FORMAT.fundNm});
			_cols.push({width:'150px', cols: 1, cls:'app-c', name:'sprtYmd'      , label:'제출일자'     , formatter: $.formatUtil.toDashDate});
			_cols.push({width:'150px', cols: 1, cls:'app-c', name:'bzentyNo'     , label:'경영체 IR자료', formatter: _format.btnEntIR}); 
			_cols.push({width:'100px', cols: 1, cls:'app-c', name:'fileNo'       , label:'첨부파일'     , formatter: _format.btnDownload});
			_cols.push({width:'150px', cols: 1, cls:'app-c', name:'sprtSttsCd'   , label:'상태'         , formatter: _format.btnSprtStts});
			
			$.extend(_options, {
				idField: 'fundNo',
				url:     getUrl('/usr/invest/fund/getListFundSprt.do'),
				params:  {},
			});
			
			if (options.updateYn == 'Y') {
				$.extend(_options, {
					toolbar:    '#appGridToolbar',
					toolbarOptions: {
						cls:  '',
						boxCls: 'design-txt-1 mb-16px',
						items: [
							{cls: 'col flex-grow-1', format: function() { return '';}},
							{cls: 'col'            , format: function() { return '<span class="fs-16px fw-600">상태변경</span>';}},
							{cls: 'col flex-b150px', key: 'sprtSttsCd', type:'appDropdownBox', options: {
								label: '상태선택',
								type:  'static',
								rows:   STORE.MYPG_STUS, // comm_const.js
								input: {id:'sprtSttsCd', name:'sprtSttsCd', value:''}
							}},
							{cls: 'col', format: _format.saveStatus}
						]
					}
				});
			}
			$.extend(_options.listOptions, {bodytrCls: 'px-v-xl t-t-c'});
		}
	
		$.each(_cols, function(i,c) {
			_options.listOptions.colgroup.push(c['width']);				
			if (c['cols'] > 0) {
				_options.listOptions.headers.push({label: c['label'], colspan: (c['cols'] > 1 ? c['cols'] : false)});
			} 
			delete c.width;
			delete c.cols;
			_options.listOptions.columns.push(c);
		});
		return _options;
	};
	
	// 목록 객체 생성
	let _create = function() {
		
		let gopts = _getGridOptions();
		
		_this.append('<div id="appGridToolbar"></div>');

		// 목록영역 생성
		let dom = $('<div></div>');
		// 북마크가 아닌 경우
		if (options.aplyCd != CODE.MYPG_APLY_SE.BOOKMARK)
			dom.addClass('shadow-box-1 px-16px pb-24px');
		
		dom.append('<div id="appGrid"></div>');
		dom.append('<div class="mb-0 paging-box pb-24px"></div>');
		dom.find('.paging-box').append('<ul id="appGridPagination" class="d-inline-block"></ul>');
		_this.append(dom);
		
	    $('#appGrid').appGrid(gopts).appGrid('init');
	};
	
	_create();
	
	return this;
};

// [팝업] 신청정보조회 팝업
//=============================================================================
$.fn.appPopupMyPageSprtView = function( args ) {
	
	let params   = args.params;
	let callback = args.searchCallback;
	
	let options = {
		url:        getUrl('/usr/mypage/support/modalSprtView.do'),
		params:     JSON.stringify(params),
		title:      '신청상세정보',
		icon:       '<img src="'+getUrl('/images/sub/Sketch_on_smartphone.svg')+'">',
		type:       'pageload',
		dialogCls:  'position-nomal',
		headerBcls: 'border-black',
		appendBody: true,
		onloaded:   function(pop) {
			// 신청상세조회
			$('#appPopupSprtView').appMyPageSprtStatus({
				mode     : MODE.VIEW,
				aplyNo   : $('#s_aplyNo').val(), // 신청번호
				aplyCd   : $('#aplyCd'  ).val(), // 신청종류코드
				prgrmNo  : $('#prgrmNo' ).val(), // 프로그램번호
				appKeys  : {
					result  : 'appPopupSprtViewResult'  , // 진행현황 영역
					info    : 'appPopupSprtViewInfo'    , // 담당자정보 영역
					link    : 'appPopupSprtViewLink'    , // 펀딩확인링크 영역
					file    : 'appPopupSprtViewFile'    , // 제출서류목록 영역
					comment : 'appPopupSprtViewComment'   // 코멘트 영역
				},
			});
			// 조회팝업 - 닫기 클릭 이벤트 처리
			$('#s_btnClose').bind('click', function() {
				pop.close();
				return false;
			});
			// 조회팝업 - 수정 클릭 이벤트 처리
			$('#s_btnUpdate').bind('click', function() {
				// 신청정보수정 팝업 오픈
				$('<div></div>').appPopupMyPageSprtForm({
					params: {
						mode    : MODE.UPDATE,
						aplyNo  : params.aplyNo,
						aplyCd  : params.aplyCd,
						prgrmNo : params.prgrmNo
					},
					searchCallback: callback
				});
				// 조회팝업 닫기
				pop.close();
				return false;
			});
		}
	};
	// 투자유치전/투자유치후인 경우
	//if (FILTER.isAfterCode (params.aplyCd) ||
	//	FILTER.isBeforeCode(params.aplyCd)) {
		options.style = 'max-width:988px;';
	//}
	return $(this).appPopup(options).open();
};

// [팝업] 신청정보수정 팝업
//=============================================================================
$.fn.appPopupMyPageSprtForm = function( args ) {
	
	let params   = args.params;
	let callback = args.searchCallback;
	
	let options = {
		url:        getUrl('/usr/mypage/support/modalSprtForm.do'),
		params:     JSON.stringify(params),
		title:      '신청정보수정',
		icon:       '<img src="'+getUrl('/images/sub/Sketch_on_smartphone.svg')+'">',
		type:       'pageload',
		dialogCls:  'position-nomal',
		headerBcls: 'border-black',
		appendBody: true,
		onloaded:   function(pop) {
			// 신청수정조회
			let cmp = $('#appPopupSprtForm').appMyPageSprtStatus({
				mode     : MODE.UPDATE,
				aplyNo   : $('#u_aplyNo').val(), // 신청번호
				aplyCd   : $('#aplyCd'  ).val(), // 신청종류코드
				prgrmNo  : $('#prgrmNo' ).val(), // 프로그램번호
				appKeys  : {
					result  : 'appPopupSprtFormResult'  , // 진행현황 영역
					info    : 'appPopupSprtFormInfo'    , // 담당자정보 영역
					link    : 'appPopupSprtFormLink'    , // 펀딩확인링크 영역
					file    : 'appPopupSprtFormFile'    , // 제출서류목록 영역
					comment : 'appPopupSprtFormComment'   // 코멘트 영역
				},
				// 수정완료후 콜백함수
				saveCallback: function() {
					// 부모창목록 재검색
					callback();
					pop.close();
				}
			});
			// 닫기 클릭 이벤트 처리
			$('#u_btnClose').bind('click', function() {
				pop.close();
				return false;
			});
			// 저장하기 클릭 이벤트 처리
			$('#u_btnSave').bind('click', function() {
				// 저장처리
				cmp.doSave();
				return false;
			});
		}
	};
	// 투자유치전/투자유치후인 경우
	//if (FILTER.isAfterCode (params.aplyCd) ||
	//	FILTER.isBeforeCode(params.aplyCd)) {
		options.style = 'max-width:988px;';
	//}
	
	return $(this).appPopup(options).open();
};

// 신청상태조회 (결과확인/진행현황)
//=============================================================================
$.fn.appMyPageSprtStatus = function( args ) {
	
	let options = $.extend(true, {
		mode     : MODE.VIEW, // 처리모드
		aplyNo   : false, // 신청번호
		aplyCd   : false, // 신청종류코드
		prgrmNo  : false, // 프로그램번호
		appKeys  : {
			result  : 'appPopupSprtResult'  , // 진행현황 영역
			info    : 'appPopupSprtInfo'    , // 담당자정보 영역
			link    : 'appPopupSprtLink'    , // 펀딩확인링크 영역
			file    : 'appPopupSprtFile'    , // 제출서류목록 영역
			comment : 'appPopupSprtComment'   // 코멘트 영역
		},
		// 정보조회 URL
		loadUrl  : getUrl('/usr/mypage/support/getSprt.do'),
		// 정보수정 URL
		saveUrl  : getUrl('/usr/mypage/support/saveFile.do')
	}, args);
	let _this = $(this);
	_this.append('<div id="'+options.appKeys.result  +'"></div>');
	_this.append('<div id="'+options.appKeys.info    +'"></div>');
	_this.append('<div id="'+options.appKeys.link    +'"></div>');
	_this.append('<div id="'+options.appKeys.file    +'"></div>');
	_this.append('<div id="'+options.appKeys.comment +'"></div>');
	
	// 신청정보 조회
	let ret = $.ajaxUtil.ajaxDefault(options.loadUrl, {aplyNo: options.aplyNo});
	let data = {};
	if (ret && ret['Data'])
		data = ret['Data'];
		
	let cards = [];

	// 투자유치전/투자유치후인 경우 진행상황 표시
	if (FILTER.isAfterCode (options.aplyCd) ||
		FILTER.isBeforeCode(options.aplyCd)) {
		// 진행현황 목록
		cards.push(
			$('#'+options.appKeys.result).appAccordionCard({
				data    : data,
				active  : true,
				mode    : 'GRID',
				title   : '진행현황',
				gridId  : 'appGrid',
				gridOptions: {
					title    : '진행현황',
					autoload : true,
					paging   : false,
					idField  : 'sprtAplyNo',
					url      : getUrl('/usr/support/support/getListSprtPrgre.do'),
					params   : {sprtAplyNo: data['sprtAplyNo']},
					listOptions: {
						// 검색결과 없을경우 메세지
						emptyText:  '검색된 {title} 정보가 없습니다.',
						headCls:   "bs-1 ts-l",
						headthCls: '',
						colgroup: ['119px','100px','120px','120px','145px','*'],
						columns:  [
							{name:'prgrmSeNm'        , label:'사업명', cls: 'fw-600'},
							{name:'prgrsDetailSttsNm', label:'진행상태'},
							{name:'prcsYmd'          , label:'처리일자', formatter: $.formatUtil.toDashDate},
							{name:'picNm'            , label:'담당자'},
							{name:'picTelno'         , label:'담당자 연락처', formatter: $.formatUtil.toPhone},
							{name:'prgrsCn'          , label:'비고', cls: 'text-start'}
						]
					}
				}
			})
		);
	}
	// 기업회원인 경우 담당자정보 표시
	if (SCREEN.ROLE.BIZ) {
		cards.push(
			$('#'+options.appKeys.info).appAccordionCard({
				data    : data,
				active  : true,
				mode    : 'COL',
				colCnt  : 2,
				title   : '경영체 담당자 정보',
				tableOptions: {
					tbRowCls : "px-v-m t-t-c",
				},
				columns : [
					{name:'picNm'     , label:'이름'     , thCls: 'fw-600 bg-white'},
					{name:'picDeptNm' , label:'부서/직급', thCls: 'fw-600 bg-white'},
					{name:'picTelno'  , label:'전화번호' , thCls: 'fw-600 bg-white', formatter: $.formatUtil.toPhone},
					{name:'picEmlAddr', label:'E-mail'   , thCls: 'fw-600 bg-white'},
				]
			})
		);
	}
	// 투자유치후/크라우드펀딩인 경우 제출서류목록 표시
	if (FILTER.isAfterCode(options.aplyCd) ||
		FILTER.isCrowdCode(options.aplyCd)) {
		// 컨설팅비용인 경우 펀딩시도 표시
		if (FILTER.isConsultCode(options.prgrmNo)) {
			cards.push(
				$('#'+options.appKeys.link).appAccordionCard({
					data    : data,
					active  : true,
					mode    : 'COL',
					colCnt  : 1,
					title   : '펀딩시도 확인여부',
					colgroup: ['200px','*'],
					columns : [
						{name:'fundLinkUrl', label:'펀딩시도확인URL', thCls: 'fw-600 bg-white'},
					]
				})
			);
		}
		// 첨부파일 초기화 (app_bizpapefile.js)
		cards.push(
			$('#'+options.appKeys.file).appAccordionCard({
				data    : data,
				active  : true,
				mode    : 'FILE',
				title   : '제출서류',
				fileId  : options.appKeys.file+'Cmp',
				fileComponent: 'appBizPapeFile',
				fileOptions: {
					mode   : options.mode,
					screen : 'LIST',
					title  : false,
					initData: {
						upDcmntCd: FILTER.getPapeGroup(options.aplyCd),
						taskSeCd:  CODE.TASK_SE.SPRT,
						dtlSeCd :  options.prgrmNo,
						// 2023.08.03 사용자권한에 따른 표출
						aplySeCd:  FILTER.getPapeType(SCREEN.ROLE.BIZ ? 'Y' : 'N'),
						docNo   :  options.aplyNo
					},
					extensions:['pdf'], 
					isAlert: true,
					isExt:   true,
					isLimit: true
					
				}
			})
		);
	}

	// 크라우드펀딩인 경우 코멘트 표시
	if (FILTER.isCrowdCode (options.aplyCd)) {
		// 코멘트
		cards.push(
			$('#'+options.appKeys.comment).appAccordionCard({
				data    : data,
				active  : true,
				mode    : 'HTML',
				title   : '코멘트',
				content : $.commUtil.nvl(data['prcsRsltCn'], '&nbsp;')
			})
		);
	}
	
	// 저장처리	
	this.doSave = function() {
		if (options.mode != MODE.UPDATE)
			return false;
		
		let saveData = {
			mode     : options.mode,
			aplyNo   : options.aplyNo, // 신청번호
			aplyCd   : options.aplyCd, // 신청종류코드
			prgrmNo  : options.prgrmNo // 프로그램번호
		};
		$.each(cards, function(i,card) {
			let valid = card.validate();
			if (valid)
				$.extend(saveData, valid);
			else {
				saveData = false;
				return false;
			}
		});
		if (!saveData) {
			return false;
		}
    	$.commMsg.confirm("저장하시겠습니까?", function() {
            // AJAX로 저장처리
            $.ajaxUtil.ajaxSave(
                options.saveUrl, 
                JSON.stringify(saveData),
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
						$.commMsg.alert('성공적으로 저장되었습니다.', options.saveCallback);
                    });
                }
            );
    	});
	}
	return this;
};

