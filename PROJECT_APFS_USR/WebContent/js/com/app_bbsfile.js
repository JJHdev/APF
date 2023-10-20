/**
*******************************************************************************
*** 파일명    : app_bbsfile.js
*** 설명      : 게시판첨부파일 파일처리 컴포넌트
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.05.03              LSH
*******************************************************************************
**/

//===========================================================================//
// 게시판첨부파일 컨트롤
//===========================================================================//
(function($){

	const WIDGET = 'appBbsFile';
	
	// 첨부파일박스 생성
	function init( target, data ) {
		
		if (data) {
			$.data(target, WIDGET, {
				options: $.extend({}, $.fn[WIDGET].defaults, {initData: data})
			});
		}
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		// 이전데이터 제거
		destroy(target);
		
		if (opts.cls)
			tdom.addClass(opts.cls);
		// 조회인 경우
		if (opts.mode == MODE.VIEW) {
			select(target, {});
		}
		// 등록/수정인 경우
		else {
			// 파일영역 생성
			createArea(target);
			// 설명글영역 생성
			createInfo(target);
			// 수정일 경우 수정목록 로드
			if (opts.mode == MODE.UPDATE) {
				loadBox(target);
			}
		}
		return this;
	};

	// 파일설명글영역 생성
	function createInfo(target) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		if (!opts.comment)
			return;

		// 설명항목
		let dom = $('<div class="'+opts.clsOptions.areaCls+' '+opts.infoWrap+'"></div>');
		dom.append('<div class="bottom-lable"></div>');
		dom.find('.bottom-lable').append('<div class="row"></div>');
		dom.find('.bottom-lable > .row').append('<div class="app-file-check-comment col-5"></div>');
		dom.find('.bottom-lable > .row').append('<div class="app-file-input-comment col"></div>');
		// 설명글 표시여부가 TRUE인 경우 
		if (opts.comment) {
			// 체크박스 설명글이 있는 경우
			if (opts.comment.check) {
				dom.find('.app-file-check-comment').append('<p></p>');
				dom.find('.app-file-check-comment > p').append('<i class="icon-exclamation-square"></i>');
				dom.find('.app-file-check-comment > p').append(opts.comment.check);
			}
			// 파일첨부 설명글이 있는 경우
			if (opts.comment.input) {
				dom.find('.app-file-input-comment').append('<p></p>');
				dom.find('.app-file-input-comment > p').append('<i class="icon-message-dots"></i>');
				dom.find('.app-file-input-comment > p').append(opts.comment.input);
			}
		}
		tdom.append(dom);
	};


	// 파일영역 생성
	function createArea(target) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		tdom.append('<div class="'+opts.areaWrap+'"></div>');
		
		// 첨부파일 선택박스 생성
		for (let i = 0; i <opts.initCount; i++) {
			// 첨부파일 선택박스 생성
			tdom.find('.'+opts.areaWrap).append(
				createBox(target, i, opts.initData)
			);
		}
		
		// 하단 설명메시지가 있는 경우
		if (opts.message) {
			let btm = $('<div class="bottom-lable"></div>');
			btm.append('<div class="row"></div>');
			btm.find('.row').append('<div class="col"></div>');
			btm.find('.col').append('<p class="text-red"></p>');
			btm.find('p').append(opts.message);
			tdom.append(btm);
		}
	};
	
	// 체크박스 생성 (옵션)
	function createCheck(target) {

		var opts = $.data(target, WIDGET).options;
		
		// 내용체크표시가 FALSE인 경우
		if (!opts.check)
			return;

		// 내용체크박스
		let chk = $('<input type="checkbox"/>');
		chk.prop('id'   , opts.check.id);
		chk.prop('name' , opts.check.name);
		chk.prop('value', opts.check.value);
		if (opts.check.checked)
			chk.prop('checked', true);
		if (opts.check.click) {
			chk.bind('click', opts.check.click);
		}
		// 내용레이블
		let lbl = $('<label for="'+opts.check.id+'" class="text-ele-line"></label>');
		lbl.append(opts.check.label);
		if (opts.check.icon) {
			lbl.append('<i class="'+opts.check.icon+'"></i>');
		}
		
		let box = $('<div class="check-radio-box pl-10px"></div>');
		box.append(chk);
		box.append(lbl);
		return $('<div class="ele-icon-box" style="height: 40px;"></div>').append(box);
	};
	
	// 파일선택박스 생성
	function createBox(target, index, data) {

		var opts = $.data(target, WIDGET).options;
		
		let row = $('<div class="row"></div>');

		if (index == 0) {
			// 내용 체크표시
			if (opts.check) {
				row.append($('<div class="col flex-b188px app-file-check"></div>').append(createCheck(target)));
			}
		}

		// 파일선택박스
		let ele = $('<div class="ele-icon-box"></div>');
		ele.append('<input type="text"   name="fileName" value="'+$.commUtil.nvlTrim(data['fileNm'])+'" class="'+opts.textBox+'" title="filebox" readonly="readonly"/>');
		ele.append('<input type="hidden" name="docuCd"   value="'+$.commUtil.nvlTrim(data['docuCd'  ])+'"/>');
		ele.append('<input type="hidden" name="docuNo"   value="'+$.commUtil.nvlTrim(data['docuNo'  ])+'"/>');
		ele.append('<input type="hidden" name="docuSeq"  value="'+$.commUtil.nvlTrim(data['docuSeq' ])+'"/>');
		ele.append('<input type="hidden" name="fileSe"   value="'+$.commUtil.nvlTrim(data['fileSe'  ])+'"/>');
		ele.append('<input type="hidden" name="papeCd"   value="'+$.commUtil.nvlTrim(data['papeCd'  ])+'"/>');
		ele.append('<input type="hidden" name="sttsCd"   value="'+$.commUtil.nvlTrim(data['sttsCd'  ])+'"/>');
		ele.append('<input type="hidden" name="docuCn"   value="'+$.commUtil.nvlTrim(data['docuCn'  ])+'"/>');
		//ele.append('<input type="hidden" name="docuYn"   value="'+$.commUtil.nvlTrim(data['docuYn'  ])+'"/>');
		ele.append('<input type="hidden" name="fileType" value="'+$.commUtil.nvlTrim(data['fileType'])+'"/>');
		ele.append('<input type="hidden" name="filePath" value="'+$.commUtil.nvlTrim(data['filePath'])+'"/>');
		ele.append('<input type="hidden" name="fileNo"   value="'+$.commUtil.nvlTrim(data['fileNo'  ])+'"/>');
		ele.append('<input type="hidden" name="needYn"   value="'+$.commUtil.nvlTrim(data['needYn'  ])+'"/>');
		ele.append('<input type="hidden" name="fileYn"   value="N"/>');
		ele.append('<input type="file"   name="upfile"   class="'+opts.fileBox+'" title="upfile">');
		row.append($('<div class="col"></div>').append(ele));
		
		// 파일버튼
		let fbtn = $('<label class="'+opts.fileBtn+' '+opts.clsOptions.btnCls+'"><i class="icon-paperclip"></i>파일찾기</label>')
		row.append($('<div class="col flex-grow-0 white-space-nowrap '+opts.btnWrap+'"></div>').append(fbtn));
		
		// 다중갯수 허용이면
		if (opts.multiple) {
			row.append(
				$('<div class="col flex-grow-0 white-space-nowrap"></div>')
				.append('<button type="button" class="px-12px '+opts.addBtn+' '+opts.clsOptions.btnCls+'"><i class="icon-plus"></i></button>')
			);
			row.append(
				$('<div class="col flex-grow-0 white-space-nowrap"></div>')
				.append('<button type="button" class="px-12px '+opts.delBtn+' '+opts.clsOptions.btnCls+'"><i class="icon-trash"></i></button>')
			);
		}
		let box = $('<div class="'+opts.clsOptions.areaCls+' mb-16px '+opts.fileWrap+'"></div>');
		if (index == 0) {
			if (!opts.check && opts.label) {
				box.append('<label class="app-file-label">'+opts.label+'</label>');
			}
		}
		box.append('<div class="day '+opts.boxWrap+'"></div>');
		box.find('.day').append(row);
		bindBox(target, box);

		return box;
	};

	// 파일선택박스 이벤트 바인딩
	function bindBox(target, elm) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		var wrp  = '.'+ opts.fileWrap;
		var fbtn = '.'+ opts.fileBtn;
		var fadd = '.'+ opts.addBtn;
		var fdel = '.'+ opts.delBtn;
		var fbox = '.'+ opts.fileBox;
		var tbox = '.'+ opts.textBox;

		// 첨부파일 파일선택 변경시
		// 텍스트박스에 파일명을 표시해주는 이벤트
		elm.on('change', fbox, function() {

			//if (window.FileReader){ //modern browser
				var filename = $(this)[0].files[0].name;
			//}else { //old IE
			//	var filename = $(this).val().split("/").pop().split("\\").pop(); //파일명만 추출
			//} //추출한 파일명 삽입
			$(this).siblings(tbox).val(filename);

			// 파일명만 추출한다.
			//var fname = $(this).val().split("\\").pop();
			// 텍스트박스에 셋팅한다.
			//$(this).closest(wrp).find(tbox).val(fname);
		});
		// 파일선택 클릭 이벤트
		elm.find(fbtn).click(function() {
			var oform = $(this).closest(wrp); 
			oform.find(fbox).trigger('click');
		});
		// 파일선택 추가 이벤트
		elm.find(fadd).click(function() {
			var oform = $(this).closest(wrp); 
			// 최대갯수 체크
			if (tdom.find(fbox).length >= opts.maxCount) {
				$.commMsg.alert('추가할 최대 갯수는 '+opts.maxCount+'개 입니다.');
				return false;
			}
			// 객체복사
			var cform = oform.clone(true).hide();
			// 제목 제거
			cform.find('.app-file-label').remove();
			// 체크항목 제거
			cform.find('.app-file-check').remove();
			cform.find('input[name="fileNo"]'  ).val("");
			cform.find('input[name="fileName"]').val("");
			cform.end().find(fbox).off("change");
			cform.insertAfter(oform);
			cform.fadeIn(200);
			return false;
		});
		// 파일선택 삭제 이벤트
		elm.find(fdel).click(function() {
			var oform = $(this).closest(wrp);
			var title = oform.find(tbox).attr("title");
			var len   = oform.parent().find(tbox+"[title='" + title + "']").length;
			var obox  = oform.find(tbox);
			var ofno  = oform.find('input[name="fileNo"]');
			if (len <= 1) {
				$.commMsg.alert('파일은 최소 1개 이상 등록되어야 합니다.');
				return false;
			}
			// 화면에서 파일항목을 삭제한다.
			var fnDelete = function() {
				oform.fadeOut(200, function() { oform.remove(); });
				return false;
			};
			if (obox.val() == "" || ofno.val() == "") {
				fnDelete();
				return false;
			}
			$.commMsg.confirm('파일을 삭제하시겠습니까?', function() {
				fnDelete();
				return false;
			})
			return false;
		});
	};
	
	// 이전 데이터 제거
	function destroy(target) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		tdom.find("."+opts.fileWrap).each(function() {
			$(this).remove();
		});
	};

	// 파일박스 리셋
	function reset(target) {
		init(target);
	};

	// 첨부파일 데이터 로드 (수정시에 호출)
	function loadBox(target, url, params ) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		if (opts.mode == MODE.INSERT)
			return;
		
		if (!url)
			url = opts.url.loadUrl;
		
		if (!params)
			params = opts.initData;
		
		// 첨부파일 목록 초기화
		destroy(target);
		
		$.ajaxUtil.ajaxLoad(url, params, function(result) {
			var rows = result.rows;
			if (rows &&
				rows.length &&
				rows.length > 0) {
				// 행만큼 선택박스 생성
				$.each(rows, function(i, row) {
					tdom.find('.'+opts.areaWrap).append( createBox(target, i, $.extend({},params,row)) );
				});
			}
			else {
				// 기본선택박스 생성
				tdom.find('.'+opts.areaWrap).append( createBox(target, 0, params) );
			}
		});
	};

	// 2023.05.18 게시판디자인 적용완료
	// 첨부파일 목록조회 (상세조회시에 호출)
	// dom: 첨부파일목록이 표시될 레이어 객체
	// args.isEasyUI: EasyUI 그리드 사용여부
	// args.loadUrl: 첨부파일 목록조회 URL
	// args.params: 첨부파일 목록조회 조건
	// args.downloadUrl: 첨부파일 다운로드 URL
	function select(target, args) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		var params         = args.params      || opts.initData;
		var loadUrl        = args.loadUrl     || opts.url.loadUrl;
		var downloadUrl    = args.downloadUrl || opts.url.downloadUrl;
		$.ajaxUtil.ajaxLoad(loadUrl, params, function(result) {
			var rows = result.rows;
			if (rows &&
				rows.length &&
				rows.length > 0) {

				let p = $('<p class="txt3"></p>');
				$.each(rows, function(i,row) {
					var r = $('<span class="btnFileDown app-pointer"></span>');
					r.append('<i class="icon-file-hwp"></i>');
					r.append(row['fileNm']);
					r.data('type', row['fileType']);
					r.data(  'no', row['docuNo'  ]);
					r.data( 'seq', row['fileNo'  ]);
					p.append(r);
				});
				tdom.html('');
				tdom.append(p);
				tdom.find(".btnFileDown").on('click', function() {
					download(target, downloadUrl, {
						sn: btoa($(this).data('seq'))
					});
				});
			}
		});
	};

	// 첨부파일 업로드 VALIDATION
	// args.isAlert : 메세지 alert 표시 여부
	// args.isExt   : 확장자 체크 여부
	// args.extensions : 허용가능 확장자 배열 (확장자 체크시에만 필수)
	// args.isLimit : 용량 체크 여부
	// args.maxBytes : 허용가능 바이트단위 용량크기 (용량 체크시에만 필수)
	function validate(target, args) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		let check = true;
		let extensions = (args.extensions || opts.extensions);
		let maxBytes   = (args.maxBytes   || opts.maxBytes);
		let isAlert    = (args.isAlert    || opts.isAlert);
		let isExt      = (args.isExt      || opts.isExt);
		let isLimit    = (args.isLimit    || opts.isLimit);

		tdom.find('input[name="upfile"]').each(function(i) {
			let fobj  = $(this);
			let nfile = fobj.val();
			let ofile = tdom.find('input[name="fileName"]').eq(i).val();
			let need  = tdom.find('input[name="needYn"]'  ).eq(i).val();

			if (nfile === '')
				tdom.find('input[name="fileYn"]').eq(i).val('N');
			else
				tdom.find('input[name="fileYn"]').eq(i).val('Y');

			//이미 false 체크된 경우 SKIP
			if (check == false)
				return;

			if (nfile == '' && ofile == '') {
				//필수 체크가 아닌 경우 SKIP
				if (need != 'Y')
					return;
				if (isAlert)
					$.commMsg.alert('파일을 선택해 주세요.');
				check = false;
				return;
			}
			// 파일이 있는 경우
			if (!$.commUtil.empty(nfile)) {
				// 파일명 길이체크
				if ($.fileUtil.checkMaxLength(ofile, opts.maxLengthName, true) == false) {
					check = false;
					return false;
				}
				// 확장자 체크
				if (isExt) {
					if ($.fileUtil.checkExtension(fobj, extensions, isAlert) == false) {
						check = false;
						return;
					}
				}
				// 용량 체크
				if (isLimit) {
					if ($.fileUtil.checkMaxbytes(fobj, maxBytes, isAlert) == false) {
						check = false;
						return;
					}
				}
			}
		});
		return check;
	};

	// 첨부파일 삭제
	// url : 첨부파일 삭제 URL
	// sn  : 첨부파일 고유번호
	function remove(target, url, sn, callback ) {

		var opts = $.data(target, WIDGET).options;
		
		url = (url || opts.url.removeUrl);
		
		$.commMsg.confirm('정말 삭제하시겠습니까?', function() {
	   		$.ajaxUtil.ajaxLoad(url, {sn: btoa(sn)}, callback);
		});
	};

	// 첨부파일 다운로드 (링크 클릭시 처리)
	function download(target, url, params ) {

		var opts = $.data(target, WIDGET).options;
		
		url = (url || opts.url.downloadUrl);
		// 진행상태 표시 다운로드 실행
		$.fileUtil.downloadProgress({
			url:    url,
			params: params,
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

	$.fn[WIDGET] = function(options, param1,param2,param3,param4){
		if (typeof options == 'string'){
			return $.fn[WIDGET].methods[options](this, param1,param2,param3,param4);
		}
		options = options || {};
		if (options.fileType)
			options.initData['fileType'] = options.fileType;
		
		return this.each(function(){
			var state = $.data(this, WIDGET);
			if (state){
				$.extend(state.options, options);
			} else {
				$.data(this, WIDGET, {
					options: $.extend({}, $.fn[WIDGET].defaults, options)
				});
			}
		});
	};
	
	$.fn[WIDGET].methods = {
		options: function(jq){
			return $.data(jq[0], WIDGET).options;
		},
		// 첨부파일 초기화
		init: function( jq, args ) {
			return jq.each(function(){
				init(this, args);
			});
		},
		// 이전 데이터 제거
		destroy: function(jq) {
			return jq.each(function(){
				destroy(this);
			});
		},
		// 파일박스 리셋
		reset: function(jq) {
			return jq.each(function(){
				reset(this);
			});
		},
		// 첨부파일 데이터 로드 (수정시에 호출)
		loadBox: function( jq, url, params ) {
			return jq.each(function(){
				loadBox(this, url, params);
			});
		},
		// 첨부파일 목록조회 (상세조회시에 호출)
		// dom: 첨부파일목록이 표시될 레이어 객체
		// args.isEasyUI: EasyUI 그리드 사용여부
		// args.loadUrl: 첨부파일 목록조회 URL
		// args.params: 첨부파일 목록조회 조건
		// args.downloadUrl: 첨부파일 다운로드 URL
		select: function(jq, args) {
			return jq.each(function(){
				select(this, args);
			});
		},
		// 첨부파일 업로드 VALIDATION
		// args.isAlert : 메세지 alert 표시 여부
		// args.isExt   : 확장자 체크 여부
		// args.extensions : 허용가능 확장자 배열 (확장자 체크시에만 필수)
		// args.isLimit : 용량 체크 여부
		// args.maxBytes : 허용가능 바이트단위 용량크기 (용량 체크시에만 필수)
		validate: function(jq, args) {
			return validate(jq[0], args);
		},
		// 첨부파일 삭제
		// url : 첨부파일 삭제 URL
		// sn  : 첨부파일 고유번호
		remove: function( jq, url, sn, callback ) {
			return jq.each(function(){
				remove(this, url, sn, callback);
			});
		},
		// 첨부파일 다운로드 (링크 클릭시 처리)
		download: function( jq, url, params ) {
			return jq.each(function(){
				download(this, url, params);
			});
		}
	};
	
	$.fn[WIDGET].defaults = {
		
		// 처리모드 (등록/수정/조회)
		mode: MODE.INSERT,
		
		// 추가,삭제 가능 여부
		multiple: false,
		
		// 설명글 내용 (표시여부)
		// 	check: false // '작성하신 IR자료가 투자자에 공개됩니다.', // 체크박스 설명글
		//	input: false  // '100MB 이내 1개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장' // 파일첨부 설명글
		comment: false,
		
		// 체크항목 표시여부
		check: false,
		//check: {
		//	icon: 'icon-check-square',
		//	name: '',
		//	value: '',
		//	label: '',
		//	click: function() {}
		//},
		// 기본 URL
		url: {
			loadUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListBbsFile.do'     ), // 파일 목록 조회 URL
			removeUrl      : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/deleteBbsFile.do'      ), // 파일 단일 삭제 URL
			uploadUrl      : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/uploadBbsFile.do'      ), // 파일 업로드 URL
			downloadUrl    : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/downloadBbsFile.do'    )  // 파일 다운로드 URL
		},
		
		// 생성시 기본 데이터
		initData: {},
		// 파일컨트롤 전체를 감싸는 영역
		areaWrap: 'app-file-area-wrap',
		// 설명글을 감싸는 영역
		infoWrap: 'app-info-wrap',
		// 파일박스 전체를 감싸는 영역
		fileWrap: 'app-file-wrap',
		// 서류항목을 감싸는 영역
		docWrap: 'app-file-doc-wrap',
		// 파일선택박스를 감싸는 영역
		boxWrap: 'app-file-box-wrap',
		// 버튼을 감싸는 영역
		btnWrap: 'app-file-btn-wrap',
		// 파일선택박스 객체
		fileBox: 'input_file',
		// 파일텍스트박스 객체
		textBox: 'input_text',
		// 파일선택 버튼
		fileBtn: 'btn_file',
		// 파일추가 버튼
		addBtn: 'btn_add',
		// 파일삭제 버튼
		delBtn: 'btn_del',
		
		clsOptions: {
			areaCls: 'form-area-box',
			boxCls : 'ele-icon-box',
			btnCls : 'btn-black bs-m bs-md-l'
		},
		// 첨부파일 용량제한 (바이트단위: 100MB)
		maxBytes: NUMBER.FILE_MAXBYTES,
		// 2022.01.21 첨부파일 파일명 글자수 최대길이 (50자)
		maxLengthName: NUMBER.FILE_MAXLENGTH,
		// 첨부파일 확장자 제한 (허용하는 확장자 목록)
		extensions: COMMONS.FILE_EXTENSIONS,
		//[	"txt" ,"pdf", "hwp", "doc", "docx", "ppt", "pptx", "xls","xlsx",
		//	"jpg", "jpeg", "png", "gif", "bmp",
		//	"zip", "alz", "7z"
		//],
		// 초기 표시 갯수
		initCount: 1,
		// 추가 최대 갯수
		maxCount: 5,
		// 메세지 alert 여부
		isAlert: false,
		// 확장자 체크 여부
		isExt:   false,
		// 용량 제한 체크 여부
		isLimit: false,
		// 파일 타입
		fileType: false,
		
		cls: false,
		
		label: '첨부파일',
		// 하단 설명메시지
		message: false,
	};
		
})(jQuery);

