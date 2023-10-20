/**
*******************************************************************************
*** 파일명    : app_bizutils.js
*** 설명      : 업무용 각종 컨트롤
***
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/

$.bizUtils = {};

// 상단제목 검색박스 또는 버튼 표시
$.bizUtils.showHeaderBox = function( args ) {
	
	let elm = $('#appHeaderBox');
	elm.html('');

	// 버튼형태인 경우
	if (args.type == 'button') {
		let btn = $('<button type="button"></button>');
		btn.prop('id'  , args.id);
		btn.append('<i class="'+args.icon+' mr-5px"></i>');
		btn.append(args.value);
		btn.click(args.callback);
		
		elm.append(btn);
		elm.prev().addClass('flex-b-md-200px');
	}
	// 검색박스형태인 경우
	else if (args.type == 'searchbox') {
		let fnSearch = function() {
			let v = $('#'+args.id).val();
			args.callback(v);
			return false;
		};
		let inp = $('<input type="text" class="fw-600" title="input"/>');
		inp.prop('id'  , args.id);
		inp.prop('name', args.name);
		if (args.placeholder)
			inp.prop('placeholder', args.placeholder);
		if (args.value)
			inp.prop('value', args.value);
		if (args.color)
			inp.css('color', args.color);
		
		let btn = $('<a href="javascript:void(0)" class="icon-search"><span class="hiddenATag">숨김</span></a>');
		btn.click(fnSearch);
		
		bindEnter(inp, btn);
	
		let div = $('<div class="form-area-box1"></div>');
		div.append('<div class="ele-icon-box"></div>');
		div.find('.ele-icon-box').append(inp);
		div.find('.ele-icon-box').append(btn);
		
		elm.append(div);
	}
};

// 북마크 선택/해제
$.bizUtils.saveBookmark = function( args ) {
	let mode = MODE.INSERT;
	let btn  = args.btn;
	if (btn.hasClass('active')) {
		mode = MODE.REMOVE;
	}
	let p = $.extend(args.params, {mode: mode});
	// 북마크 선택/해제
	$.ajaxUtil.ajaxLoad(
		getUrl('/usr/mypage/bookmark/saveBookmark.do'), 
		p, 
		function() {
			args.callback(mode);
		}
	);
};

// 북마크 버튼
$.bizUtils.getBookmarkButton = function(o) {
	let bk = $('<button type="button" class="btn-combi-2 bs-l"></button>');
	bk.data('trgt' , o['bzentyNo']);
	bk.data('secd' , CODE.BKMK_SE.ENT);
	if (o['gsBkmkYn'] == 'Y') {
		bk.append('<i class="icon-bookmark-plus F mr-5px"></i>');
		bk.append('북마크 완료');
		bk.addClass("active");
	}
	else {
		bk.append('<i class="icon-bookmark-plus mr-5px"></i>');
		bk.append('북마크 하기');
		bk.removeClass("active");
	}
	if (SCREEN.ROLE.BIZ) {
		bk.bind('click', function() {
			// 북마크 선택/해제
			$.bizUtils.saveBookmark({
				btn: $(this),
				params: {
					trgtBzentyNo : $(this).data('trgt'),
					bkmkSeCd     : $(this).data('secd')
				},
				callback: function( mode ) {
					bk.html('');
					if (mode == MODE.INSERT) {
						bk.append('<i class="icon-bookmark-plus F mr-5px"></i>');
						bk.append('북마크 완료');
						bk.addClass("active");
					}
					else {
						bk.append('<i class="icon-bookmark-plus mr-5px"></i>');
						bk.append('북마크 하기');
						bk.removeClass("active");
					}
				}
			});
		});
	}
	return bk;
};

// 미팅신청 버튼
$.bizUtils.getMeetingButton = function(o) {
	
	let mt = $('<button type="button" class="btn-primary bs-l"></button>');
	mt.append('<i class="icon-chat-dots-check mr-5px"></i>');
	mt.append('미팅신청');
	mt.data('bzno' , o['bzentyNo']);
	mt.data('bznm' , o['bzentyNm']);

	if (SCREEN.ROLE.BIZ) {
		mt.bind('click', function() {
			// 미팅신청 팝업 오픈
			popup.openMeeting({
				params: {
					trgtBzentyNo: $(this).data('bzno'),
					trgtBzentyNm: $(this).data('bznm')
				}
			});
		});
	}
	return mt;
};

// 코드목록조회
$.bizUtils.getCodeList = function( group ) {
	return $.ajaxUtil.ajaxDefault(
		getUrl('/com/common/getComboCode.do'),
		{upCdId: group}
	);
};

// 경영체 대표이미지표시
$.bizUtils.getEntImage = function(o, mode) {
	
	let src = false;
	let bno = o['bzentyNo'];
	if (o['rprsFileSn']) {
		// 대표이미지
		src = getUrl('/usr/file/linkEntFile.do?sn='+btoa(o['rprsFileSn']));
	}
	else {
		if ($.commUtil.empty(bno))
			bno = $.commUtil.getRandomNumber(1,10);
		// 임시이미지
		src = getUrl('/images/app/ENT_IMAGE'+(bno%10)+'.jpg');
	}

	if (mode == MODE.LIST) {
		return '<img src="'+src+'" class="img1 app-ent-listimage" alt="경영체">';
	}
	else {
		return '<img src="'+src+'" height="300" width="600" class="app-ent-viewimage" alt="경영체"/>';
	}
};

// 경영체 슬라이드 기본정보
$.bizUtils.getEntInfo = function(o, mode) {
	
	// 경영체명
	let name = $('<p class="txt1">'+o['bzentyNm']+'</p>');
	let info = $('<div class="row info-box align-items-start"></div>');
	info.append('<div class="col-12 app-info-inv" style="margin-bottom:2px;min-height:35px;max-height:40px;line-height:37px;overflow-y:auto"></div>');
	info.append('<div class="col-12 app-info-biz" style="margin-bottom:2px;min-height:35px;max-height:40px;line-height:37px;overflow-y:auto"></div>');
	// 투자분야
	if (o['invtFldText']) {
		$.each(o['invtFldText'].split(','), function(i,s) {
			if (mode == MODE.LIST && i > 1) {
				info.find('.app-info-inv').append('<span class="bg-lavendar-t15 info text-lavendar app-my3">...</span>');
				return false;
			}
			info.find('.app-info-inv').append('<span class="bg-lavendar-t15 info text-lavendar app-my3">'+s+'</span>');
		});
	}
	// 사업분야
	if (o['bizFldText']) {
		$.each(o['bizFldText'].split(','), function(i,s) {
			if (mode == MODE.LIST && i > 1) {
				info.find('.app-info-biz').append('<span class="bg-mint-t15 info text-mint app-my3">...</span>');
				return false;
			} 
			info.find('.app-info-biz').append('<span class="bg-mint-t15 info text-mint app-my3">'+s+'</span>');
		});
	}
	
	// 투자희망금액
	let amtval = $.formatUtil.toInvtHopeAmt(o['invtHopeAmt']);
	let amt = $.domUtil.getRow({
		items: ['<p class="txt2"><i class="icon-coins-M text-primary"></i>투자 희망금액</p>'
		       ,'<p class="txt3">'+amtval.digits+'<em>'+amtval.word+'</em></p>'
		]
	});
	let box = $('<div class="txt-box"></div>');

	// 상세조회인 경우
	if (mode == MODE.VIEW) {
		// 버튼 설정
		let btn = $('<div class="bottom"></div>');
		btn.append($.domUtil.getRow({
			items: [{formatHtml: $.bizUtils.getBookmarkButton(o)} // 북마크하기 버튼
			       ,{formatHtml: $.bizUtils.getMeetingButton (o)} // 미팅신청 버튼
			]
		}));
		
		box.append($.domUtil.getRow({
			items: [{colCls: 'col-12',formatHtml: [name, info]}
			       ,{colCls: 'col-12',formatHtml: $('<div class="center"></div>').append(amt)}
			       ,{colCls: 'col-12',formatHtml: btn}
			]
		}));
	}
	else {
		box.addClass('app-pointer');
		box.append(name);
		box.append(info);
		box.append( $('<div class="bottom"></div>').append(amt) );
		// 목록인 경우 텍스트영역에 링크처리
		box.data('no', o['bzentyNo']);
		box.click(function() {
			// 상세보기
			$.formUtil.submitForm(getUrl('/usr/invest/ent/viewEnt.do'), {
				params: {bzentyNo: $(this).data('no')}
			});
		});
	}
	return box;
};

//우주투자사례 슬라이드 기본정보 - 메인페이지용
$.bizUtils.getMainInvestCaseInfo = function(o) {
	
	//이미지
	let img = null;
	if (o['files']) {
	    // 대표이미지
	    img = $('<img src="'+getUrl('/usr/file/linkBbsFile.do?sn='+o['files'])+'" class="img2" alt="우수투자사례">');
	}
	else {
	    // 임시이미지
		img = $('<img src="'+getUrl('/images/sub/img4.svg')+'" class="img2" alt="우수투자사례">');
	}
	//우수투자사례
	let pTitle          = '<p class="txt1">' + truncateString(o.pstTtl, 18) + '</p>';
	// 필요한 HTML 요소 생성
	let decodedString = decodeHtml(o.pstCn);
	let pContent = $("<p></p>").addClass("txt2").html(truncateString(decodedString,90));
	// HTML 콘텐츠 설정
	let infoRegYmd    = '<div class="col"><p class="txt3"><i class="icon-alarm-check"></i>' + o.regDate + '</p></div>' 
	let infoInqCnt    = '<div class="col"><p class="txt3"><i class="icon-eye"></i>' + o.inqCnt + '</p></div>';
	
	let box = $('<div class="txt-box w-100"></div>');
	box.append(img);
	box.append(pTitle);
	box.append(pContent);
	let infoDiv = $('<div class="info"></div>');
	let rowDiv = $('<div class="row"></div>');

	rowDiv.append(infoRegYmd);
	rowDiv.append(infoInqCnt);

	infoDiv.append(rowDiv);
	box.append(infoDiv);

	box.append(infoDiv);
	
	return box;
};

// 우수투자사례 슬라이드카드 - 메인페이지
$.bizUtils.drawMainInvestCaseSlide = function( div, data, isPrint ) {

	div.addClass("swiper mySwiper3 overflow-hidden w-100 h-100");
	div.append($('<div class="swiper-wrapper"></div>'));
	
	data.forEach(function(e) {
		let cardDiv = $('<div class="card-box-main" style="cursor:pointer;"></div>');
		cardDiv.attr('value', e['pstNo']);
		cardDiv.append($.bizUtils.getMainInvestCaseInfo(e));
		let slideDiv = $('<div class="swiper-slide"></div>').append(cardDiv);
		div.find('.swiper-wrapper').append(slideDiv);
	});
	
	if (data) {
		// Initialize Swiper
		new Swiper(".mySwiper3", {
			slidesPerView: "auto",
			spaceBetween:32,
			slidesPerView:4,
			breakpoints: {												
				740: {
				slidesPerView: 1,
				spaceBetween: 0,
				},												
			},
			navigation: {
				nextEl: ".swiper-button-next.ver3",
				prevEl: ".swiper-button-prev.ver3",
			},
			autoplay: {
				delay: 3000,
				disableOnInteraction: false,
			},
		});
	}
};

// 파일 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadFile = function() {
	let url = $(this).data('url');
	let sn  = $(this).data('sn');

	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {sn: btoa(sn)},
		progress: {
	 		start: function(url) {
	 			$('#downloadFrame').html(url);
	 		},
	 		ing: function(pr) {
	 			$('#downloadFrame').html('loaded='+pr.loaded+',total='+pr.total);
	 		},
	 		end: function(fileName) {
	 			$('#downloadFrame').html(fileName);
	 		}				
		}
	});
};

// 안내문 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadNotice = function() {
	let url = getUrl('/usr/mypage/support/downloadNotice.do');
	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {},
		progress: {
	 		start: function(url) {
	 			$('#downloadFrame').html(url);
	 		},
	 		ing: function(pr) {
	 			$('#downloadFrame').html('loaded='+pr.loaded+',total='+pr.total);
	 		},
	 		end: function(fileName) {
	 			$('#downloadFrame').html(fileName);
	 		}				
		}
	});
};

// 2023.09.12 LSH
// 업무용 첨부파일 다운로드 (버튼클릭시 이벤트 적용)
// sn 조건이 아닌 다중조건으로 파일조회시 사용함
$.bizUtils.downloadBizFile = function( params ) {
	let url = getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/downloadBizFileByParams.do');
	// AJAX용 첨부파일 다운로드
	$.fileUtil.downloadAjax({
		url: url,
		params: params
	});
	return false;
};

// 2023.07.04 LSH 메인페이지로 이동하기
$.bizUtils.goHome = function() {
	goUrl( getUrl(CURL.MAIN) );
	return false;
};

// 2023.07.04 LSH 로그인으로 이동하기
$.bizUtils.login = function() {
	goUrl( getUrl(CURL.LOGIN) );
	return false;
};

// 2023.07.04 LSH 로그아웃 처리
$.bizUtils.logout = function() {
	goUrl( getUrl(CURL.LOGOUT) );
	return false;
};

// 2023.07.04 LSH 모빌리언스 휴대폰 본인인증 팝업오픈
$.bizUtils.openMobilians = function() {
	let url = getUrl("/com/common/popupMobilians.do");
	let opt = "width=480,height=880, scrollbars=yes, resizable=yes";
	let key = "mobilians_pop";
	window.open(url, key, opt);
	return false;
};

// 2023.07.12 LSH 회원가입 - 기업정보입력으로 이동하기
$.bizUtils.goJoinBiz = function( args ) {
	let opts = {params: {stepCd: 'FBIZ'}};
	if (args) {
		// 팝업창에서 부모창을 이동하려는 경우
		if (args.child) {
			window.opener.name = "joinBizTarget";
			$.extend(opts, {target: "joinBizTarget"});
		}
		// 팝업창으로 이동하려는 경우
		else if (args.target){
			$.extend(opts, {target: args.target});
		}
	}
    $.formUtil.submitForm(getUrl('/com/user/openJoin.do'), opts);
	return false;
};

// 2023.07.12 LSH 회원가입 - 가입완료로 이동하기
$.bizUtils.goJoinDone = function( args ) {
	let opts = {params: {stepCd: 'DONE'}};
	if (args) {
		// 팝업창에서 부모창을 이동하려는 경우
		if (args.child) {
			window.opener.name = "joinDoneTarget";
			$.extend(opts, {target: "joinDoneTarget"});
		}
		// 팝업창으로 이동하려는 경우
		else if (args.target){
			$.extend(opts, {target: args.target});
		}
	}
    $.formUtil.submitForm(getUrl('/com/user/openJoin.do'), opts);
	return false;
};

// 2023.07.13 LHB 경영체 데이터 업로드 양식 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadSprtUld = function() {
	let url = getUrl('/usr/inform/bbs/downloadSprtUld.do');
	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {},
		progress: {
	 		start: function(url) {
	 			$('#downloadFrame').html(url);
	 		},
	 		ing: function(pr) {
	 			$('#downloadFrame').html('loaded='+pr.loaded+',total='+pr.total);
	 		},
	 		end: function(fileName) {
	 			$('#downloadFrame').html(fileName);
	 		}				
		}
	});
};

// ASSIST BI AI 파일 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadBiAi = function() {
	let url = getUrl('/usr/inform/intro/downloadBiAi.do');
	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {},
		progress: {
	 		start: function(url) {
	 			$('#downloadFrame').html(url);
	 		},
	 		ing: function(pr) {
	 			$('#downloadFrame').html('loaded='+pr.loaded+',total='+pr.total);
	 		},
	 		end: function(fileName) {
	 			$('#downloadFrame').html(fileName);
	 		}				
		}
	});
};

// ASSIST BI PNG 파일 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadBiPng = function() {
	let url = getUrl('/usr/inform/intro/downloadBiPng.do');
	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {},
		progress: {
	 		start: function(url) {
	 			$('#downloadFrame').html(url);
	 		},
	 		ing: function(pr) {
	 			$('#downloadFrame').html('loaded='+pr.loaded+',total='+pr.total);
	 		},
	 		end: function(fileName) {
	 			$('#downloadFrame').html(fileName);
	 		}				
		}
	});
};

// ASSIST BI AI 파일 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadBiJpg = function() {
	let url = getUrl('/usr/inform/intro/downloadBiJpg.do');
	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {},
		progress: {
	 		start: function(url) {
	 			$('#downloadFrame').html(url);
	 		},
	 		ing: function(pr) {
	 			$('#downloadFrame').html('loaded='+pr.loaded+',total='+pr.total);
	 		},
	 		end: function(fileName) {
	 			$('#downloadFrame').html(fileName);
	 		}				
		}
	});
};