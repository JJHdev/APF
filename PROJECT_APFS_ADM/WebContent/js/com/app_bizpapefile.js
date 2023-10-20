/**
*******************************************************************************
*** 파일명    : app_bizpapefile.js
*** 설명      : 서류코드용 업무첨부파일 파일처리 컴포넌트
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.06.05              LSH
*******************************************************************************
**/

//===========================================================================//
// 업무첨부파일 컨트롤
//===========================================================================//
(function($){

	const WIDGET = 'appBizPapeFile';
	
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
			loadBox(target);
		}
		return this;
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

	// 서류양식 로드 및 업로드 박스 생성
	function loadBox(target) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 첨부파일 목록 초기화
		destroy(target);
		
		// 서류양식그룹 로드
		loadGroup(target);
		
		// 서류양식그룹별 서류양식 로드
		tdom.find('.'+opts.papeWrap).each(function() {
			// 그룹코드
			let group = $(this).data('code');
			// 서류양식목록 조회
			loadPape(target, $(this), group);
		});
	};
	
	// 서류양식 그룹 로드
	function loadGroup(target) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// [AJAX] 서류양식그룹 목록조회
		let ret = $.ajaxUtil.ajaxDefault(opts.url.groupUrl, {
			upDcmntCd: opts.initData['upDcmntCd'],
			dtlSeCd  : opts.initData['dtlSeCd' ],
			aplySeCd : opts.initData['aplySeCd']
		});
		let groups = ret.rows;
		if (groups && 
			groups.length > 0) {
			// 서류그룹 단위 카드 생성
			$.each(groups, function(i, group) {
				tdom.append( createGroup(target, group) );
			});
		}
		// 서류그룹이 없을 경우
		else {
			tdom.append( createGroup(target) );
		}
	};
	
	// 서류그룹 생성
	function createGroup(target, group) {
		
		var opts = $.data(target, WIDGET).options;
		
		let txt = $('<p class="text-icon-3"></p>');
		txt.addClass('app-pape-title');
		txt.append('<i class="icon-file-text-edit cycle-ver-1"></i>');
		//별도의 명칭을 사용하지 않고 기본제목으로 대체함
		//txt.append(group['dcmntNm']);
		txt.append(opts.title);
		
		// 설명글이 있는 경우
		if (opts.comment) {
			let c = $('<span class="text-red fw-300 fs-13px align-self-end ml-10px app-comment"></span>').append(opts.comment);
			txt.append(c);	
		}
		
		let box = $('<div class="box pb-0"></div>');
		box.append('<div class="top border-0 pb-0"></div>');
		box.find('.top').append(txt);
		
		if (group) {
			let itm = $('<div class="form-box-of-input shadow-box-1 p-16px p-md-32px"></div>');
			itm.addClass(opts.papeWrap);
			itm.data('code', group['dcmntCd']);
			itm.data('text', group['dcmntNm']);
			box.append(itm);
		}
		else {
			let itm = $('<div class="form-box-of-input shadow-box-1 p-16px p-md-32px"></div>');
			itm.append(opts.emptyText);
			box.append(itm);
		}
		return box;
	};
	
	// 서류양식 로드
	function loadPape(target, elm, group) {
		var opts = $.data(target, WIDGET).options;

		// [AJAX] 서류양식목록 조회
		let ret = $.ajaxUtil.ajaxDefault(opts.url.papeUrl, {
			taskSeCd : opts.initData['taskSeCd'],
			dtlSeCd  : opts.initData['dtlSeCd' ],
			aplySeCd : opts.initData['aplySeCd'],
			docNo    : opts.initData['docNo'   ], // 문서번호(수정시에만)
			upDcmntCd: group
		});
		let papes = ret.rows;
		let len = papes.length;
		let row = $('<div class="row"></div>');
		// 서류양식별 파일박스 생성
		$.each(papes, function(i, pape) {
			// 첨부파일영역
			let file = $('<div id="'+opts.fileKey+pape['dcmntCd']+'" class="col"></div>')
			file.addClass(opts.fileCls);
			file.data('code' , pape['dcmntCd']); // 서류코드
			file.data('esntl', pape['esntlYn']); // 필수여부
			file.data('limit', pape['limtCnt']); // 최대파일갯수
			file.data('title', pape['dcmntNm']); // 서류명칭
			file.data('index', i);
			file.append('<div class="ele-icon-box app-readonly"></div>');

			// 첨부파일 버튼
			let btn = $('<label for="file-input3" class="btn-black bs-l"></label>');
			btn.addClass(opts.fileBtn);
			btn.append('<i class="icon-paperclip"></i>');
			btn.append('파일찾기');
			btn.data('code' , pape['dcmntCd']); // 서류코드
			btn.data('esntl', pape['esntlYn']); // 필수여부
			btn.data('limit', pape['lmtCnt' ]); // 최대파일갯수
			btn.data('index', i);

			// 양식파일 버튼
			let doc = $('<button type="button" class="btn-black bs-l"></button>');
			doc.addClass(opts.papeBtn);
			doc.append('<i class="icon-download"></i>');
			doc.append('양식다운로드');
			doc.data('code', pape['dcmntCd']);
			doc.data('type', CODE.FILE_TYPE.FRM);

			let r = $('<div class="row"></div>');
			r.append( file );
			r.append( $('<div class="col flex-grow-0 white-space-nowrap"></div>').append(btn) );
			if (pape['formYn'] == 'Y')
				r.append( $('<div class="col flex-grow-0 white-space-nowrap"></div>').append(doc) );

			
			let col = $('<div class="col-12 pb-32px"></div>');
			if (len > 1)
				col.addClass('col-md-6');
				
			col.append('<div class="form-area-box "></div>');
			col.find('.form-area-box').append('<label></label>');
			col.find('.form-area-box > label').append(pape['dcmntNm']);
			// 필수인 경우
			if (pape['esntlYn'] == 'Y')
				col.find('.form-area-box > label').append('<em>*</em>');
			
			// 파일영역 추가
			col.find('.form-area-box').append($('<div class="day"></div>').append(r));
			// 서류설명글이 있는 경우 
			if (pape['dcmntCmNm']) {
				let btm = $('<div class="bottom-lable"></div>');
				btm.append('<div class="row"><div class="col"><p class="text-red"></p></div></div>');
				btm.find('p').append('<i class="icon-exclamation-circle"></i>');
				btm.find('p').append(pape['dcmntCmNm']);
				col.find('.form-area-box').append(btm);
			}
			row.append(col);
		});
		elm.append(row);
		
		// 양식파일 다운로드 이벤트 바인딩
		elm.find('.'+opts.papeBtn).each(function() {
			$(this).bind('click', function() {
				// 양식파일 다운로드 실행
				download(target, {
					url: opts.url.downloadPapeUrl,
					params: {dcmntCd: btoa($(this).data('code'))}
				});				
			});
		});
		// 파일업로드창 오픈 이벤트 바인딩
		elm.find('.'+opts.fileBtn).each(function() {
			$(this).bind('click', function() {
				// 파업업로드팝업 오픈
				openUpload(target, {
					dcmntCd: $(this).data('code' ),
					dcmntNm: $(this).data('title'),
					limtCnt: $(this).data('limit'),
					esntlYn: $(this).data('esntl')
				});
			});
		});
		// 수정인경우
		if (opts.mode == MODE.UPDATE) {
			// 첨부파일로드
			loadFile(target);
		}
	};
	
	// (수정시) 개별첨부파일 로드
	function loadFile(target) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 양식항목 LOOP
		tdom.find('.'+opts.fileCls).each(function(i) {
			let code = $(this).data('code' ); // 서류코드
			// [AJAX] 서류양식별 첨부파일목록 조회
			let ret = $.ajaxUtil.ajaxDefault(opts.url.loadUrl, {
				dcmntCd  : code,
				docNo    : opts.initData['docNo']
			});
			let rows = ret['rows'];
			if (!rows || rows.length == 0)
				return true;
			
			let file = $(this);
			file.html('');
			
			$(rows).each(function(i, row) {
				// 파일항목 추가
				file.append( createFile(target, row) );
			});
		});
	};

	// 첨부파일 개별항목 생성
	function createFile(target, row) {
		
		var opts = $.data(target, WIDGET).options;
		
		$.extend(row, {fileYn:'N',mode: MODE.VIEW});
		// 등록파일에 추가
		opts._state_variables['_append_files'][row['sn']] = row;
		
		// 파일세부항목 생성
		let fdom = $('<div class="'+opts.itemCls+' app-both"></div>');
		fdom.data('sn', row['sn']);
		
		let file = $('<a class="'+opts.downBtn+'" href="javascript:void(0);"></a>');
		// 파일명 마스킹 처리 (사용안함) $.fileUtil.getMaskName(row['fileNm'], opts.maskLen)
		file.append(row['fileNm']);
		file.data('code', row['dcmntCd']);
		file.data('sn'  , row['sn'     ]);
		// 파일다운로드 이벤트 바인딩
		file.bind('click', function() {
			// 파일 다운로드 실행
			download(target, {
				url: opts.url.downloadUrl,
				params: {sn: btoa($(this).data('sn'))}
			});				
		});
		fdom.append(file);
		
		// 파일삭제 버튼
		let dbtn = $('<button type="button" class="'+opts.deltBtn+' filedel">삭제</button>');
		dbtn.data('code', row['papeCd']);
		dbtn.data('temp', row['tempYn']);
		dbtn.data('sn'  , row['sn'    ]);
		dbtn.bind('click', function() {
			// 첨부파일 삭제 
			let dom = $(this);
			let sn  = $(this).data('sn');
			let tmp = $(this).data('temp');
			$.commMsg.confirm('정말 삭제하시겠습니까?', function() {
				// 임시파일인 경우 실제 삭제처리
				if (tmp == 'Y') {
			   		$.ajaxUtil.ajaxLoad(opts.url.removeUrl, {sn: btoa(sn)}, function() {
						let idom = dom.closest('.'+opts.itemCls); 
						if (idom.length > 1) {
							idom.parent().remove();
						}
						else {
							idom.remove();
						}
					});
				}
				// 기저장파일인 경우 실제 삭제되지 않으며 저장시 삭제처리가 완료됨
				else {
					let aprow = opts._state_variables['_append_files'][sn];
					if (aprow) {
						opts._state_variables['_remove_files'][sn] = aprow;
						delete opts._state_variables['_append_files'][sn];
					}
					// 해당 요소 모두 삭제
					let idom = dom.closest('.'+opts.itemCls); 
					if (idom.length > 1) {
						idom.parent().remove();
					}
					else {
						idom.remove();
					}
				}
			});
			return false;
		});
		fdom.append(dbtn);
		
		return $('<div class="ele-icon-box app-readonly"></div>').append(fdom);		
	};
	
	// 첨부파일 다운로드
	function download(target, downArgs) {
		// 진행상태 표시 다운로드 실행
		$.fileUtil.downloadProgress({
			url:    downArgs.url,
			params: downArgs.params,
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

	// 첨부파일 목록조회 (상세조회시에 호출)
	// args.loadUrl    : 첨부파일 목록조회 URL
	// args.params     : 첨부파일 목록조회 조건
	// args.downloadUrl: 첨부파일 다운로드 URL
	function select(target, args) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		var params         = args.params      || opts.initData;
		var loadUrl        = args.loadUrl     || opts.url.loadUrl;
		var downloadUrl    = args.downloadUrl || opts.url.downloadUrl;
		
		let table = $('<table></table>');
		table.append('<colgroup></colgroup>');
		table.append('<thead class="bs-1 px-v-m ts-l "></thead>');
		table.append('<tbody class="bs-1 px-v-m ts-s"></tbody>');
		table.find('colgroup').append('<col width="162px">');
		table.find('colgroup').append('<col width="250px">');
		table.find('colgroup').append('<col width="*">');
		table.find('thead').append('<tr class=""></tr>');
		table.find('thead > tr').append('<th>등록일자</th>');
		table.find('thead > tr').append('<th>제출서류명</th>');
		table.find('thead > tr').append('<th>제출서류</th>');
		table.find('tbody').append('<tr><td class="text-center" colspan="3">검색된 결과가 없습니다.</td></tr>');
		tdom.html('');
		tdom.append('<div class="table-box-1"></div>');
		tdom.find('.table-box-1').append(table);
		
		$.ajaxUtil.ajaxLoad(loadUrl, params, function(result) {
			var rows = result.rows;
			if (rows && rows.length && rows.length > 0) {
				tdom.find('tbody').html('');
				$.each(rows, function(i,row) {
					var r = $('<td class="btnFileDown app-pointer"></td>');
					r.append('<i class="icon-download mr-5px"></i>');
					r.append(row['fileNm']);
					r.data('sn', row['fileNo']);
					r.click(function() {
						// 파일 다운로드 실행
						download(target, {
							url: downloadUrl,
							params: {sn: btoa($(this).data('sn'))}
						});				
					});
					var p = $('<tr></tr>');
					p.append($('<td class="text-center"></td>').append(row['regDate']));
					p.append($('<td></td>').append(row['dcmntNm']));
					p.append(r);
					tdom.find('tbody').append(p);
				});
				
				// 23.08.28 LHB. 제출서류 일괄 다운로드 행 추가
				var p = $('<tr></tr>');
				var r = $('<td class="text-center app-pointer fw-600" colspan="3"><i class="icon-download mr-5px"></i>제출서류 일괄 다운로드</td>');
				r.click(function() {
					// 제출서류 일괄 다운로드
					$.each(rows, function (i, row) {
						download(target, {
							url: downloadUrl,
							params: {sn: btoa(row['fileNo'])}
						});
					});
				});
				p.append(r);
				tdom.find('tbody').append(p);
			}
		});
	};
	
	// 첨부파일 저장 VALIDATION 및 저장 데이터 반환
	// args.required : 필수검증 여부 
	function validate(target, args ) {
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 파일 필수검증여부
		let needYn = false;
		if (args && args.required)
			needYn = true;

		// 필수체크, 최대갯수 체크
		let check = true;
		
		// 서류항목만큼 LOOP
		tdom.find('.'+opts.fileCls).each(function(i) {
			let esntl = $(this).data('esntl'); // 필수여부
			let limit = $(this).data('limit'); // 갯수제한
			let title = $(this).data('title'); // 서류명칭
			let item  = $(this).find('.'+opts.itemCls);
			
			title = '['+title+']';
			
			// 파일 필수검증여부가 true인 경우
			// 파일 필수체크가 'Y'인 경우
			if (needYn && esntl == 'Y') {
				if (item.length == 0) {
					$.commMsg.alert(title+ ' 항목은 반드시 등록하셔야 합니다.');
					check = false;
					return false;
				}
			}
			// 파일갯수 체크
			if (item.length > limit) {
				$.commMsg.alert(title+ ' 항목은 '+limit+'개까지 등록 가능합니다.');
				check = false;
				return false;
			}
		});
		if (check) 
			return getSaveData(target);
		return false;
	};
	
	// 서류코드 기준 첨부파일 업로드여부 반환
	function existFile( target, papeCd ) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		// 업로드여부 체크
		let upload = false;
		// 서류항목만큼 LOOP
		tdom.find('.'+opts.fileCls).each(function(i) {
			// 서류코드
			let code = $(this).data('code'); 
			let item = $(this).find('.'+opts.itemCls);
			// 해당 서류코드의 파일정보가 있으면
			if (code == papeCd && item.length > 0) {
				upload = true;
				return false;
			}
		});
		return upload;
	};
	
	// 첨부파일 저장데이터 반환
	function getSaveData(target) {
		
		var opts = $.data(target, WIDGET).options;
		let fnAr = function(v) { return [v] };
		return {
			saveFiles   : $.map(opts._state_variables['_append_files'], fnAr),
			removeFiles : $.map(opts._state_variables['_remove_files'], fnAr)
		};
	};
	
    //========================================================//
    // 업로드팝업 오픈
	// params.dcmntCd : 서류코드
	// params.dcmntNm : 서류명칭
	// params.limtCnt : 제한수
	// params.esntlYn : 필수여부
    //--------------------------------------------------------//
	function openUpload(target, params) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		// TODO 수정인 경우엔 SKIP if ($.commUtil.empty($(this).data('sn'))) {}
		// 제한갯수
		let limit  = parseInt(params['limtCnt']);
		// 파일갯수
		let count = tdom.find('[id="'+opts.fileKey+params['dcmntCd']+'"]')
						.find('.'+opts.itemCls)
						.length;
		if (count == limit) {
			let msg = '해당 항목은 '+limit+'개까지 등록 가능합니다.'
			        + '수정하시려면 삭제 후에 등록해 주세요.'; 
			$.commMsg.alert(msg);
			return false;
		}
		
		opts._state_variables['_popup'] = $('<div></div>').appPopup({
			params: JSON.stringify(params),
			appendBody: true,
			title: '파일업로드',
			icon:  '<img src="'+getUrl('/images/sub/iconimg1.svg')+'">',
			message: function() {
				/* form은 반드시 onsubmit="return false;" 속성이 있어야 
				 * ajaxForm 실행시 두번씩 실행되지 않는다.
				 */
				let form = $('<form name="uploadForm" class="usr-form-group" method="post" onsubmit="return false;"></form>');
				form.append(
					createBox(target, {
						fileType: opts.fileType,
						docuCd  : opts.initData['taskSeCd'],
						docuNo  : opts.initData['docNo'   ],
						docuSeq : opts.initData['dtlDocNo'],
						fileSe  : opts.initData['fileSeCd'],
						papeCd  : params['dcmntCd'],
						needYn  : params['esntlYn'],
						//fileNo: params['sn'],
					})
				);
				// 오픈시 파일선택창이 열린다.
				form.find('.'+ opts.fileBtn).trigger('click');
				return form;
			}
		});
		return false;
	};
	
	// 파일선택박스 생성
	function createBox(target, data) {

		var opts = $.data(target, WIDGET).options;
		
		let row = $('<div class="row"></div>');

		// 파일선택박스
		let ele = $('<div class="'+opts.clsOptions.boxCls+'"></div>');
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
		ele.append('<input type="hidden" name="fileYn"   value="Y"/>');
		ele.append('<input type="file"   name="upfile"   class="d-none '+opts.fileBox+'">');
		row.append($('<div class="col"></div>').append(ele));
		
		// 파일버튼
		let fbtn = $('<button type="button" class="'+opts.fileBtn+' bs-l btn-black"><i class="icon-paperclip"></i>파일찾기</button>')
		//  업로드버튼
		let ubtn = $('<button type="button" class="'+opts.upldBtn+' bs-l btn-primary"><i class="icon-file-upload"></i>업로드</button>')

		row.append($('<div class="col flex-grow-0 white-space-nowrap '+opts.btnWrap+'"></div>').append(fbtn));
		row.append($('<div class="col flex-grow-0 white-space-nowrap '+opts.btnWrap+'"></div>').append(ubtn));

		let box = $('<div class="'+opts.clsOptions.areaCls+' '+opts.fileWrap+'"></div>');
		box.append('<div class="day '+opts.boxWrap+'"></div>');
		box.find('.day').append(row);
		bindBox(target, box);

		return box;
	};

	// 파일선택박스 이벤트 바인딩
	function bindBox(target, elm) {

		var opts = $.data(target, WIDGET).options;

		var wrp  = '.'+ opts.fileWrap;
		var fbtn = '.'+ opts.fileBtn;
		var fupl = '.'+ opts.upldBtn;

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
		});
		// 파일선택 클릭 이벤트
		elm.find(fbtn).click(function() {
			var oform = $(this).closest(wrp); 
			oform.find(fbox).trigger('click');
		});
		// 업로드 클릭 이벤트
		elm.find(fupl).click(function() {
			saveUpload(target);
		});
	};

    //========================================================//
    // 업로드팝업 첨부파일 업로드 처리
	// args.isAlert    : 메세지 alert 표시 여부
	// args.isExt      : 확장자 체크 여부
	// args.extensions : 허용가능 확장자 배열 (확장자 체크시에만 필수)
	// args.isLimit    : 용량 체크 여부
	// args.maxBytes   : 허용가능 바이트단위 용량크기 (용량 체크시에만 필수)
    //--------------------------------------------------------//
	function saveUpload(target, args) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		var tpop = opts._state_variables['_popup'];
		
		let felm = tpop.find('form[name="uploadForm"]');
		let fobj = felm.find('input[name="upfile"   ]');
		let ftxt = felm.find('input[name="fileName" ]').val();

		let extensions = ((args && args.extensions   ) || opts.extensions   );
		let maxBytes   = ((args && args.maxBytes     ) || opts.maxBytes     );
		let maxLength  = ((args && args.maxLengthName) || opts.maxLengthName);
		let isAlert    = ((args && args.isAlert      ) || opts.isAlert      );
		let isExt      = ((args && args.isExt        ) || opts.isExt        );
		let isLimit    = ((args && args.isLimit      ) || opts.isLimit      );
		
		// 첨부파일 업로드 VALIDATION
		if (fobj.val() == '') {
			$.commMsg.alert('파일을 선택해 주세요.');
			return false;
		}
		// 파일명 길이체크
		if ($.fileUtil.checkMaxLength(ftxt, maxLength, isAlert) == false)
			return false;
		// 확장자 체크
		if (isExt && $.fileUtil.checkExtension(fobj, extensions, isAlert) == false)
			return false;
		// 용량 체크
		if (isLimit && $.fileUtil.checkMaxbytes(fobj, maxBytes, isAlert) == false)
			return false;

		// 업로드 클릭 이벤트
		felm.find('.'+ opts.upldBtn).unbind('click');

		// 폼을 AJAX로 저장처리
		felm.ajaxForm({
			url: opts.url.uploadUrl,
			enctype : 'multipart/form-data',
			// 에러시 처리로직
			error: $.ajaxUtil.error,
			// 저장후 처리로직
			success: function(ret) {
				if (ret && ret['File']) {
					let data  = ret['File'];
					let orgSn = data['orgSn'];
					let newSn = data['sn'   ];
					// [부모창] 업로드한 파일정보 추가
					$.extend(data, {mode: MODE.INSERT, fileYn: 'Y'}); 
					// 파일영역
					let tfile = tdom.find('[id="'+opts.fileKey+data['dcmntCd']+'"]');
		
					// 등록인 경우 등록파일 추가
					if ($.commUtil.empty(orgSn)) {
						opts._state_variables['_append_files'][newSn] = data;
					}
					// 이전 파일번호가 있는 경우 (수정기능) 이전 파일 삭제등록
					else {
						let apRow = opts._state_variables['_update_files'][orgSn];
						if (apRow) {
							opts._state_variables['_remove_files'][orgSn] = apRow;
							delete opts._state_variables['_update_files'][orgSn];
						}
						// 수정파일 추가
						opts._state_variables['_update_files'][newSn] = data;
						// 이전파일 객체삭제
						tfile.find('.'+opts.itemCls).each(function() {
							if ($(this).data('sn') == orgSn) {
								$(this).remove();
								return false;
							}
						});
					}
					if (tfile.find('.'+opts.itemCls).length == 0)
						tfile.html('');
					
					// 파일객체 추가 
					tfile.append( createFile(target, data) );
				}
				tpop.close();
			}
		}).submit();
	};

	$.fn[WIDGET] = function(options, param1,param2,param3,param4){
		if (typeof options == 'string'){
			return $.fn[WIDGET].methods[options](this, param1,param2,param3,param4);
		}
		options = options || {};
		if (options.fileType)
			options.initData['fileType'] = options.fileType;
		else
			options.fileType = options.initData['fileType'];
		
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
		// args.loadUrl: 첨부파일 목록조회 URL
		// args.params: 첨부파일 목록조회 조건
		select: function(jq, args) {
			return jq.each(function(){
				select(this, args);
			});
		},
		// 첨부파일 저장 검증 및 저장데이터 반환
		validate: function(jq, args) {
			return validate(jq[0], args);
		},
		// 첨부파일 저장데이터 반환
		getSaveData: function(jq) {
			return getSaveData(jq[0]);
		},
		// 서류코드 기준 첨부파일 업로드 여부 체크
		existFile: function(jq, code) {
			return existFile(jq[0], code);
		}
	};
	
	$.fn[WIDGET].defaults = {
		
		// 처리모드 (등록/수정/조회)
		mode: MODE.INSERT,
		// 기본제목
		title: '제출서류',
		// 레이아웃 스타일시트
		cls: false,
		// 각종 스타일시트
		clsOptions: {
			areaCls: 'form-area-box',
			boxCls : 'ele-icon-box',
			btnCls : 'bs-l btn-black'
		},
		// 기본 URL
		url: {
			papeUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListPape.do'        ), // 서류양식 목록 조회 URL
			groupUrl       : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListPapeGroup.do'   ), // 서류그룹 목록 조회 URL
			loadUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListBizFile.do'     ), // 파일 목록 조회 URL
			saveUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/saveBizFile.do'        ), // 파일 저장 처리 URL (사용안함)
			removeUrl      : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/deleteBizFile.do'      ), // 파일 단일 삭제 URL
			uploadUrl      : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/uploadBizFile.do'      ), // 파일 임시업로드 URL (팝업용)
			downloadUrl    : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/downloadBizFile.do'    ), // 파일 다운로드 URL
			downloadPapeUrl: getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/downloadPapeFile.do'   )  // 서류양식 다운로드 URL
		},
		// 생성시 기본 데이터
		initData: {
			//fileType: false, // 파일타입(BIZ,ENT,BBS)
			//papeCd  : false, // 서류코드
			//docuCd  : false, // 업무구분(TASK)
			//docuNo  : false, // 문서번호
			//docuSeq : false, // 세부문서번호
		},
		// 파일 타입
		fileType: CODE.FILE_TYPE.BIZ,
		// 빈값인 경우 텍스트
		emptyText: '별도 제출서류 없음',
		// 서류그룹 설명글
		// '※ 100MB 이내 1개 PDF 파일 첨부 가능'
		comment: false, 
		// 파일컨트롤 전체를 감싸는 영역
		areaWrap: 'app-file-area-wrap',
		// 파일박스 전체를 감싸는 영역
		fileWrap: 'app-file-wrap',
		// 서류그룹 전체를 감싸는 영역
		papeWrap: 'app-pape-group',
		// 파일선택박스를 감싸는 영역
		boxWrap: 'app-fboxwrap',
		// 버튼을 감싸는 영역
		btnWrap: 'app-fbtnwrap',
		// 파일선택박스 객체
		fileBox: 'input_file',
		// 파일텍스트박스 객체
		textBox: 'input_text',
		// 파일선택 버튼
		fileBtn: 'app-btn-file',
		// 양식파일 다운로드 버튼
		papeBtn: 'app-btn-papefile',
		// 다운로드 버튼
		downBtn: 'app-btn-download',
		// 업로드 버튼
		upldBtn: 'app-btn-upload',
		// 파일삭제 버튼
		deltBtn: 'app-btn-filedelt',
		// 파일영역 스타일시트
		fileCls: 'app-file-wrap',
		// 파일항목 스타일시트
		itemCls: 'app-file-item',
		// 파일영역 ID PREFIX
		fileKey: 'app-file-',

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
		// 메세지 alert 여부
		isAlert: false,
		// 확장자 체크 여부
		isExt:   false,
		// 용량 제한 체크 여부
		isLimit: false,

		// 추가,삭제 가능 여부
		multiple: false,
		// 초기 표시 갯수
		initCount: 1,
		// 추가 최대 갯수
		maxCount: 5,

		// 내부변수
		_state_variables : {
			// 업로드팝업 객체
			_popup: false,
			// 신규등록 파일 (로드된파일 포함)
			_append_files: {},
			// 수정등록 파일
			_update_files: {},
			// 삭제대상 파일
			_remove_files: {}
		}
	};
		
})(jQuery);


/**
*******************************************************************************
*** 파일명    : app_bizpapefile.js
*** 설명      : 서류코드용 업무첨부파일 파일처리 컴포넌트
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.09.19               J H			지원사업관리 - 신청현황관리 첨부파일 수정기능
*******************************************************************************
**/
//===========================================================================//
// 업무첨부파일 컨트롤
//===========================================================================//
(function($){
	const WIDGET = 'appBizUpdatePapeFile';
	// 첨부파일박스 생성
	function init( target, data ) {
		$.fn[WIDGET].defaults._state_variables._append_files = {};
		$.fn[WIDGET].defaults._state_variables._remove_files = {};
		
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
			loadBox(target);
		}
		return this;
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

	// 서류양식 로드 및 업로드 박스 생성
	function loadBox(target) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 첨부파일 목록 초기화
		destroy(target);
		
		// 서류양식그룹 로드
		loadGroup(target);
		
		// 서류양식그룹별 서류양식 로드
		tdom.find('.'+opts.papeWrap).each(function() {
			// 그룹코드
			let group = $(this).data('code');
			// 서류양식목록 조회
			loadPape(target, $(this), group);
		});
	};
	
	// 서류양식 그룹 로드
	function loadGroup(target) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// [AJAX] 서류양식그룹 목록조회
		let ret = $.ajaxUtil.ajaxDefault(opts.url.groupUrl, {
			upDcmntCd: opts.initData['upDcmntCd'],
			dtlSeCd  : opts.initData['dtlSeCd' ],
			aplySeCd : opts.initData['aplySeCd']
		});
		let groups = ret.rows;
		if (groups && 
			groups.length > 0) {
			// 서류그룹 단위 카드 생성
			$.each(groups, function(i, group) {
				tdom.append( createGroup(target, group) );
			});
		}
		// 서류그룹이 없을 경우
		else {
			tdom.append( createGroup(target) );
		}
	};
	
	// 제목박스 생성
	function createTitle(target) {
		var opts = $.data(target, WIDGET).options;
		// 제목이 있을경우에만 제목표시
		if (!opts.title)
			return false;
		let txt = $('<p class="text-icon-3"></p>');
		txt.addClass('app-pape-title');
		txt.append('<i class="icon-file-text-edit cycle-ver-1"></i>');
		//별도의 명칭을 사용하지 않고 기본제목으로 대체함
		//txt.append(group['dcmntNm']);
		txt.append(opts.title);
		
		// 설명글이 있는 경우
		if (opts.comment) {
			let c = $('<span class="text-red fw-300 fs-13px align-self-end ml-10px app-comment"></span>').append(opts.comment);
			txt.append(c);	
		}
		let top = $('<div class="top border-0 pb-0"></div>');
		top.append(txt);
		return top;
	};
	
	// 서류그룹 생성
	function createGroup(target, group) {
		var opts = $.data(target, WIDGET).options;
		
		let box = $('<div></div>');
		if (opts.title) {
			box.append(createTitle(target));
		}
		// 목록형 스타일인 경우
		if (opts.screen == 'LIST') {
			box.addClass(opts.listOptions.wrapCls);
			
			let table = $('<table></table>');
			table.append('<colgroup></colgroup>');
			table.append('<tbody></tbody>');
			table.find('tbody').addClass(opts.listOptions.tbodyCls);
			$.each(opts.listOptions.colgroup, function(i,c) {
				table.find('colgroup').append('<col width="'+c+'">');
			});
			if (group) {
				table.addClass(opts.papeWrap);
				table.data('code', group['dcmntCd']);
				table.data('text', group['dcmntNm']);
			}
			else {
				table.find('tbody').append('<tr><td colspan="2">'+opts.emptyText+'</td></tr>');
				table.find('tbody > tr').addClass(opts.listOptions.trCls);
				
			}
			box.append(table);
		}
		// 박스형 스타일인 경우
		else {
			box.addClass("box pb-0");
			let itm = $('<div class="form-box-of-input shadow-box-1 p-16px p-md-32px"></div>');
			if (group) {
				itm.addClass(opts.papeWrap);
				itm.data('code', group['dcmntCd']);
				itm.data('text', group['dcmntNm']);
			}
			else {
				itm.append(opts.emptyText);
			}
			box.append(itm);
		}
		return box;
	};
	
	// 서류양식 파일박스 생성
	function createFileBox(target, idx, pape) {
		var opts = $.data(target, WIDGET).options;
		// 양식버튼 표시여부
		let docYn = (pape['formYn'] == 'Y');
		// 목록형인 경우 양식다운로드 제외
		if (opts.screen == 'LIST') {
			docYn = false;
		}
		// 첨부파일영역
		let file = $('<div id="'+opts.fileKey+pape['dcmntCd']+'" class="col"></div>')
		file.addClass(opts.fileCls);
		file.data('code' , pape['dcmntCd']); // 서류코드
		file.data('esntl', pape['esntlYn']); // 필수여부
		file.data('limit', pape['limtCnt']); // 최대파일갯수
		file.data('title', pape['dcmntNm']); // 서류명칭
		file.data('index', idx);
		file.append('<div class="ele-icon-box app-readonly app-l"></div>');

		// 첨부파일 버튼
		let btn = $('<label for="file-input3"></label>');
		btn.addClass(opts.screen == 'LIST' ? opts.listOptions.btnCls : opts.boxOptions.btnCls);
		btn.addClass(opts.fileBtn);
		btn.append('<i class="icon-paperclip"></i>');
		btn.append('파일찾기');
		btn.data('code' , pape['dcmntCd']); // 서류코드
		btn.data('esntl', pape['esntlYn']); // 필수여부
		btn.data('limit', pape['lmtCnt' ]); // 최대파일갯수
		btn.data('index', idx);

		
		// 양식파일 버튼
		let doc = $('<button type="button"></button>');
		doc.addClass(opts.screen == 'LIST' ? opts.listOptions.btnCls : opts.boxOptions.btnCls);
		doc.addClass(opts.papeBtn);
		doc.append('<i class="icon-download"></i>');
		doc.append('양식다운로드');
		doc.data('code', pape['dcmntCd']);
		doc.data('type', CODE.FILE_TYPE.FRM);

		let r = $('<div class="row"></div>');
		r.append( file );
		r.append( $('<div class="col flex-grow-0 white-space-nowrap"></div>').append(btn) );
		if (docYn)
			r.append( $('<div class="col flex-grow-0 white-space-nowrap"></div>').append(doc) );

		// 목록형인 경우
		if (opts.screen == 'LIST') {
			let box = $('<div></div>');
			box.addClass('form-area-box');
			box.append('<div class="day"></div>');
			box.find('.day').append(r);
			return box;
		}
		else {
			return $('<div class="day"></div>').append(r);
		}
	};
	
	// 서류양식 서류명 생성
	function createPapeBox(pape) {
		let box = $('<div class="form-area-box"></div>');
		box.append('<label></label>');
		box.find('label').append(pape['dcmntNm']);
		// 필수인 경우
		if (pape['esntlYn'] == 'Y')
			box.find('label').append('<em>*</em>');
		return box;
	};
	
	// 서류양식 라인 생성
	function createPapeRow(target, idx, pape, count) {
		
		var opts = $.data(target, WIDGET).options;
		
		let file = createFileBox(target, idx, pape);
		
		// 목록형인 경우
		if (opts.screen == 'LIST') {

			let tr = $('<tr></tr>');
			tr.addClass(opts.listOptions.trCls);
			// 서류영역 추가
			tr.append($('<td class="'+opts.listOptions.thCls+'"></td>').append( createPapeBox(pape)));
			// 파일영역 추가
			tr.append($('<td class="'+opts.listOptions.tdCls+'"></td>').append( file));
			// 서류설명글은 제외함
			return tr;
		}
		// 박스형인 경우
		else {
			let col = $('<div></div>');
			col.addClass("col-12 pb-32px");
			if (count > 1)
				col.addClass('col-md-6');
			
			col.append( createPapeBox(pape) );
			// 파일영역 추가
			col.find('.form-area-box').append(file);
			
			// 서류설명글이 있는 경우 
			if (pape['dcmntCmNm']) {
				let btm = $('<div class="bottom-lable"></div>');
				btm.append('<div class="row"><div class="col"><p class="text-red"></p></div></div>');
				btm.find('p').append('<i class="icon-exclamation-circle"></i>');
				btm.find('p').append(pape['dcmntCmNm']);
				col.find('.form-area-box').append(btm);
			}
			return col;
		}
	};
	// 서류양식 로드
	function loadPape(target, elm, group) {
		
		var opts = $.data(target, WIDGET).options;

		// [AJAX] 서류양식목록 조회
		let ret = $.ajaxUtil.ajaxDefault(opts.url.papeUrl, {
			taskSeCd : opts.initData['taskSeCd'],
			dtlSeCd  : opts.initData['dtlSeCd' ],
			aplySeCd : opts.initData['aplySeCd'],
			docNo    : opts.initData['docNo'   ], // 문서번호(수정시에만)
			upDcmntCd: group
		});
		
		let papes = ret.rows;
		let count = papes.length;
		// 목록형이면
		if (opts.screen == 'LIST') {
			// 서류양식별 파일박스 생성
			$.each(papes, function(i, pape) {
				elm.find('tbody').append( createPapeRow(target, i, pape, count) );
			});
		}
		// 박스형이면
		else {
			let row = $('<div class="row"></div>');
			// 서류양식별 파일박스 생성
			$.each(papes, function(i, pape) {
				row.append( createPapeRow(target, i, pape, count) );
			});
			elm.append(row);
		}
		// 양식파일 다운로드 이벤트 바인딩
		elm.find('.'+opts.papeBtn).each(function() {
			$(this).bind('click', function() {
				// 양식파일 다운로드 실행
				download(target, {
					url: opts.url.downloadPapeUrl,
					params: {dcmntCd: btoa($(this).data('code'))}
				});				
			});
		});
		// 파일업로드창 오픈 이벤트 바인딩
		elm.find('.'+opts.fileBtn).each(function() {
			$(this).bind('click', function() {
				// 파업업로드팝업 오픈
				openUpload(target, {
					dcmntCd: $(this).data('code' ),
					dcmntNm: $(this).data('title'),
					limtCnt: $(this).data('limit'),
					esntlYn: $(this).data('esntl')
				});
			});
		});
		// 수정인경우
		if (opts.mode == MODE.UPDATE) {
			// 첨부파일로드
			loadFile(target);
		}
	};
	
	// (수정시) 개별첨부파일 로드
	function loadFile(target) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 양식항목 LOOP
		tdom.find('.'+opts.fileCls).each(function(i) {
			let code = $(this).data('code' ); // 서류코드
			// [AJAX] 서류양식별 첨부파일목록 조회
			let ret = $.ajaxUtil.ajaxDefault(opts.url.loadUrl, {
				dcmntCd  : code,
				docNo    : opts.initData['docNo']
			});
			let rows = ret['rows'];
			if (!rows || rows.length == 0)
				return true;
			
			let file = $(this);
			file.html('');
			
			$(rows).each(function(i, row) {
				// 파일항목 추가
				file.append( createFile(target, row) );
			});
		});
	};

	// 첨부파일 개별항목 생성
	function createFile(target, row) {
		
		var opts = $.data(target, WIDGET).options;
		
		$.extend(row, {fileYn:'N',mode: MODE.VIEW});
		// 등록파일에 추가
		opts._state_variables['_append_files'][row['sn']] = row;
		
		// 파일세부항목 생성
		let fdom = $('<div class="'+opts.itemCls+' app-both"></div>');
		fdom.data('sn', row['sn']);
		
		let file = $('<a class="'+opts.downBtn+'" href="javascript:void(0);"></a>');
		// 파일명 마스킹 처리 (사용안함) $.fileUtil.getMaskName(row['fileNm'], opts.maskLen)
		file.append(row['fileNm']);
		file.data('code', row['dcmntCd']);
		file.data('sn'  , row['sn'     ]);
		// 파일다운로드 이벤트 바인딩
		file.bind('click', function() {
			// 파일 다운로드 실행
			download(target, {
				url: opts.url.downloadUrl,
				params: {sn: btoa($(this).data('sn'))}
			});				
		});
		fdom.append(file);
		
		// 파일삭제 버튼
		let dbtn = $('<button type="button" class="'+opts.deltBtn+' filedel">삭제</button>');
		dbtn.data('code', row['papeCd']);
		dbtn.data('temp', row['tempYn']);
		dbtn.data('sn'  , row['sn'    ]);
		dbtn.bind('click', function() {
			// 첨부파일 삭제 
			let dom = $(this);
			let sn  = $(this).data('sn');
			let tmp = $(this).data('temp');
			$.commMsg.confirm('정말 삭제하시겠습니까?', function() {
				// 임시파일인 경우 실제 삭제처리
				if (tmp == 'Y') {
			   		$.ajaxUtil.ajaxLoad(opts.url.removeUrl, {sn: btoa(sn)}, function() {
						let idom = dom.closest('.'+opts.itemCls); 
						if (idom.length > 1) {
							idom.parent().remove();
						}
						else {
							idom.remove();
						}
						let aprow = opts._state_variables['_append_files'][sn];
						if (aprow) {
							opts._state_variables['_remove_files'][sn] = aprow;
							delete opts._state_variables['_append_files'][sn];
						}
					});
				}
				// 기저장파일인 경우 실제 삭제되지 않으며 저장시 삭제처리가 완료됨
				else {
					let aprow = opts._state_variables['_append_files'][sn];
					if (aprow) {
						opts._state_variables['_remove_files'][sn] = aprow;
						delete opts._state_variables['_append_files'][sn];
					}
					// 해당 요소 모두 삭제
					let idom = dom.closest('.'+opts.itemCls); 
					if (idom.length > 1) {
						idom.parent().remove();
					}
					else {
						idom.remove();
					}
				}
			});
			return false;
		});
		fdom.append(dbtn);
		
		return $('<div class="ele-icon-box app-readonly app-l"></div>').append(fdom);		
	};
	
	// 첨부파일 다운로드
	function download(target, downArgs) {
		// 진행상태 표시 다운로드 실행
		$.fileUtil.downloadProgress({
			url:    downArgs.url,
			params: downArgs.params,
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

	// 첨부파일 목록조회 (상세조회시에 호출)
	// args.loadUrl    : 첨부파일 목록조회 URL
	// args.params     : 첨부파일 목록조회 조건
	// args.downloadUrl: 첨부파일 다운로드 URL
	function select(target, args) {

		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		var params         = args.params      || opts.initData;
		var loadUrl        = args.loadUrl     || opts.url.loadUrl;
		var downloadUrl    = args.downloadUrl || opts.url.downloadUrl;
		
		let table = $('<table></table>');
		table.append('<colgroup></colgroup>');
		table.append('<tbody class="bs-1 ts-m"></tbody>');
		table.find('colgroup').append('<col width="200px">');
		table.find('colgroup').append('<col width="*">');
		table.find('tbody > tr').append('<td colspan="2">검색된 결과가 없습니다.</td>');
		
		tdom.html('');
		tdom.append('<div class="table-box-1 m-overbox"></div>');
		tdom.find('.table-box-1').append(table);
		
		$.ajaxUtil.ajaxLoad(loadUrl, params, function(result) {
			var rows = result.rows;
			if (rows &&
				rows.length &&
				rows.length > 0) {
				tdom.find('tbody').html('');
				$.each(rows, function(i,row) {
					var r = $('<p class="align-items-center d-flex btnFileDown app-pointer"></p>');
					r.append('<i class="fs-18px icon-file-pdf mr-5px text-primary"></i>');
					r.append(row['fileNm']);
					r.data('sn', row['fileNo']);
					r.click(function() {
						// 파일 다운로드 실행
						let p = {sn: btoa($(this).data('sn'))};
						$.formUtil.submitForm(downloadUrl, {params: p});
					});
					
					var p = $('<tr class="px-v-m py-v-m "></tr>');
					p.append('<td class="fw-600 text-start"></td>');
					p.append('<td></td>');
					p.find('td:first').append(row['dcmntNm']);
					p.find('td:last' ).append(r);
					tdom.find('tbody').append(p);
				});
			}
		});
	};
	
	// 첨부파일 저장 VALIDATION 및 저장 데이터 반환
	// args.required : 필수검증 여부 
	function validate(target, args ) {
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 파일 필수검증여부
		let needYn = false;
		if (args && args.required)
			needYn = true;

		// 필수체크, 최대갯수 체크
		let check = true;
		
		// 서류항목만큼 LOOP
		tdom.find('.'+opts.fileCls).each(function(i) {
			let esntl = $(this).data('esntl'); // 필수여부
			let limit = $(this).data('limit'); // 갯수제한
			let title = $(this).data('title'); // 서류명칭
			let item  = $(this).find('.'+opts.itemCls);
			
			title = '['+title+']';
			
			// 파일 필수검증여부가 true인 경우
			// 파일 필수체크가 'Y'인 경우
			if (needYn && esntl == 'Y') {
				if (item.length == 0) {
					$.commMsg.alert(title+ ' 항목은 반드시 등록하셔야 합니다.');
					check = false;
					return false;
				}
			}
			// 파일갯수 체크
			if (item.length > limit) {
				$.commMsg.alert(title+ ' 항목은 '+limit+'개까지 등록 가능합니다.');
				check = false;
				return false;
			}
		});
		if (check) 
			return getSaveData(target);
		return false;
	};
	
	// 서류코드 기준 첨부파일 업로드여부 반환
	function existFile( target, papeCd ) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);

		// 업로드여부 체크
		let upload = false;
		// 서류항목만큼 LOOP
		tdom.find('.'+opts.fileCls).each(function(i) {
			// 서류코드
			let code = $(this).data('code'); 
			let item = $(this).find('.'+opts.itemCls);
			// 해당 서류코드의 파일정보가 있으면
			if (code == papeCd && item.length > 0) {
				upload = true;
				return false;
			}
		});
		return upload;
	};
	
	// 첨부파일 저장데이터 데이터 셋팅
	function getSaveData(target) {
		// 이전데이터 제거
		var opts = $.data(target, WIDGET).options;
		let fnAr = function(v) { return [v] };
		
		return {
			saveFiles   : $.map(opts._state_variables['_append_files'], fnAr),
			removeFiles : $.map(opts._state_variables['_remove_files'], fnAr)
		};
	};
	
	
    //========================================================//
    // 업로드팝업 오픈
	// params.dcmntCd : 서류코드
	// params.dcmntNm : 서류명칭
	// params.limtCnt : 제한수
	// params.esntlYn : 필수여부
    //--------------------------------------------------------//
	function openUpload(target, params) {
		
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		
		// 제한갯수
		let limit  = parseInt(params['limtCnt']);
		// 파일갯수
		let count = tdom.find('[id="'+opts.fileKey+params['dcmntCd']+'"]')
						.find('.'+opts.itemCls)
						.length;
		if (count == limit) {
			let msg = '해당 항목은 '+limit+'개까지 등록 가능합니다.'
			        + '수정하시려면 삭제 후에 등록해 주세요.'; 
			$.commMsg.alert(msg);
			return false;
		}
		
		opts._state_variables['_popup'] = $('<div></div>').appPopupFile({
			params: JSON.stringify(params),
			appendBody: true,
			title: '파일업로드',
			message: function() {
				/* form은 반드시 onsubmit="return false;" 속성이 있어야 
				 * ajaxForm 실행시 두번씩 실행되지 않는다.
				 */
				let form = $('<form name="uploadForm" class="usr-form-group" method="post" onsubmit="return false;"></form>');
				form.append(
					createBox(target, {
						fileType: opts.fileType,
						docuCd  : opts.initData['taskSeCd'],
						docuNo  : opts.initData['docNo'   ],
						docuSeq : opts.initData['dtlDocNo'],
						fileSe  : opts.initData['fileSeCd'],
						dtlDocNo: opts.initData['dtlDocNo'],
						papeCd  : params['dcmntCd'],
						needYn  : params['esntlYn'],
						//fileNo: params['sn'],
					})
				);
				// 오픈시 파일선택창이 열린다.
				form.find('.'+ opts.fileBtn).trigger('click');
				return form;
			}
		});
		return false;
	};
	
	// 파일선택박스 생성
	function createBox(target, data) {
		
		var opts = $.data(target, WIDGET).options;
		
		let row = $('<div class="row"></div>');

		// 파일선택박스
		let ele = $('<div class="'+opts.clsOptions.boxCls+'"></div>');
		ele.append('<input type="text"   name="fileName" value="'+$.commUtil.nvlTrim(data['fileNm'])+'" class="'+opts.textBox+'" title="filebox" readonly="readonly"/>');
		ele.append('<input type="hidden" name="docuCd"   value="'+$.commUtil.nvlTrim(data['docuCd'  ])+'"/>');
		ele.append('<input type="hidden" name="docuNo"   value="'+$.commUtil.nvlTrim(data['docuNo'  ])+'"/>');
		ele.append('<input type="hidden" name="docuSeq"  value="'+$.commUtil.nvlTrim(data['docuSeq' ])+'"/>');
		ele.append('<input type="hidden" name="dtlDocNo" value="'+$.commUtil.nvlTrim(data['dtlDocNo' ])+'"/>');
		ele.append('<input type="hidden" name="fileSe"   value="'+$.commUtil.nvlTrim(data['fileSe'  ])+'"/>');
		ele.append('<input type="hidden" name="papeCd"   value="'+$.commUtil.nvlTrim(data['papeCd'  ])+'"/>');
		ele.append('<input type="hidden" name="sttsCd"   value="'+$.commUtil.nvlTrim(data['sttsCd'  ])+'"/>');
		ele.append('<input type="hidden" name="docuCn"   value="'+$.commUtil.nvlTrim(data['docuCn'  ])+'"/>');
		//ele.append('<input type="hidden" name="docuYn"   value="'+$.commUtil.nvlTrim(data['docuYn'  ])+'"/>');
		ele.append('<input type="hidden" name="fileType" value="'+$.commUtil.nvlTrim(data['fileType'])+'"/>');
		ele.append('<input type="hidden" name="filePath" value="'+$.commUtil.nvlTrim(data['filePath'])+'"/>');
		ele.append('<input type="hidden" name="fileNo"   value="'+$.commUtil.nvlTrim(data['fileNo'  ])+'"/>');
		ele.append('<input type="hidden" name="needYn"   value="'+$.commUtil.nvlTrim(data['needYn'  ])+'"/>');
		ele.append('<input type="hidden" name="fileYn"   value="Y"/>');
		ele.append('<input type="file"   name="upfile"   class="d-none '+opts.fileBox+'">');
		row.append($('<div class="col"></div>').append(ele));
		
		// 파일버튼
		let fbtn = $('<button type="button" class="'+opts.fileBtn+' bs-l btn-black"><i class="icon-paperclip"></i>파일찾기</button>')
		//  업로드버튼
		let ubtn = $('<button type="button" class="'+opts.upldBtn+' bs-l btn-primary"><i class="icon-file-upload"></i>업로드</button>')

		row.append($('<div class="col flex-grow-0 white-space-nowrap '+opts.btnWrap+'"></div>').append(fbtn));
		row.append($('<div class="col flex-grow-0 white-space-nowrap '+opts.btnWrap+'"></div>').append(ubtn));

		let box = $('<div class="'+opts.clsOptions.areaCls+' '+opts.fileWrap+'"></div>');
		box.append('<div class="day '+opts.boxWrap+'"></div>');
		box.find('.day').append(row);
		bindBox(target, box);

		return box;
	};

	// 파일선택박스 이벤트 바인딩
	function bindBox(target, elm) {

		var opts = $.data(target, WIDGET).options;

		var wrp  = '.'+ opts.fileWrap;
		var fbtn = '.'+ opts.fileBtn;
		var fupl = '.'+ opts.upldBtn;

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
		});
		// 파일선택 클릭 이벤트
		elm.find(fbtn).click(function() {
			var oform = $(this).closest(wrp); 
			oform.find('input[name="fileNo"]'  ).val("");
			oform.find('input[name="fileName"]').val("");
			oform.find(fbox).trigger('click');
		});
		// 업로드 클릭 이벤트
		elm.find(fupl).click(function() {
			saveUpload(target);
		});
	};

    //========================================================//
    // 업로드팝업 첨부파일 업로드 처리
	// args.isAlert    : 메세지 alert 표시 여부
	// args.isExt      : 확장자 체크 여부
	// args.extensions : 허용가능 확장자 배열 (확장자 체크시에만 필수)
	// args.isLimit    : 용량 체크 여부
	// args.maxBytes   : 허용가능 바이트단위 용량크기 (용량 체크시에만 필수)
    //--------------------------------------------------------//
	function saveUpload(target, args) {
		var opts = $.data(target, WIDGET).options;
		var tdom = $(target);
		var tpop = opts._state_variables['_popup'];
		
		let felm = tpop.find('form[name="uploadForm"]');
		let fobj = felm.find('input[name="upfile"   ]');
		let ftxt = felm.find('input[name="fileName" ]').val();

		let extensions = ((args && args.extensions   ) || opts.extensions   );
		let maxBytes   = ((args && args.maxBytes     ) || opts.maxBytes     );
		let maxLength  = ((args && args.maxLengthName) || opts.maxLengthName);
		let isAlert    = ((args && args.isAlert      ) || opts.isAlert      );
		let isExt      = ((args && args.isExt        ) || opts.isExt        );
		let isLimit    = ((args && args.isLimit      ) || opts.isLimit      );
		
		// 첨부파일 업로드 VALIDATION
		if (fobj.val() == '') {
			$.commMsg.alert('파일을 선택해 주세요.');
			return false;
		}
		// 파일명 길이체크
		if ($.fileUtil.checkMaxLength(ftxt, maxLength, isAlert) == false)
			return false;
		// 확장자 체크
		if (isExt && $.fileUtil.checkExtension(fobj, extensions, isAlert) == false)
			return false;
		// 용량 체크
		if (isLimit && $.fileUtil.checkMaxbytes(fobj, maxBytes, isAlert) == false)
			return false;

		// 업로드 클릭 이벤트
		felm.find('.'+ opts.upldBtn).unbind('click');

		// 폼을 AJAX로 저장처리
		felm.ajaxForm({
			url: opts.url.uploadUrl,
			enctype : 'multipart/form-data',
			// 에러시 처리로직
			error: $.ajaxUtil.error,
			// 저장후 처리로직
			success: function(ret) {
				if (ret && ret['File']) {
					let data  = ret['File'];
					let orgSn = data['orgSn'];
					let newSn = data['sn'   ];
					// [부모창] 업로드한 파일정보 추가
					$.extend(data, {mode: MODE.INSERT, fileYn: 'Y'}); 
					// 파일영역
					let tfile = tdom.find('[id="'+opts.fileKey+data['dcmntCd']+'"]');
		
					// 등록인 경우 등록파일 추가
					if ($.commUtil.empty(orgSn)) {
						opts._state_variables['_append_files'][newSn] = data;
					}
					// 이전 파일번호가 있는 경우 (수정기능) 이전 파일 삭제등록
					else {
						let apRow = opts._state_variables['_update_files'][orgSn];
						if (apRow) {
							opts._state_variables['_remove_files'][orgSn] = apRow;
							delete opts._state_variables['_update_files'][orgSn];
						}
						// 수정파일 추가
						opts._state_variables['_update_files'][newSn] = data;
						// 이전파일 객체삭제
						tfile.find('.'+opts.itemCls).each(function() {
							if ($(this).data('sn') == orgSn) {
								$(this).remove();
								return false;
							}
						});
					}
					if (tfile.find('.'+opts.itemCls).length == 0)
						tfile.html('');
					
					// 파일객체 추가 
					tfile.append( createFile(target, data) );
				}
				tpop.close();
			}
		}).submit();
	};

	$.fn[WIDGET] = function(options, param1,param2,param3,param4){
		if (typeof options == 'string'){
			return $.fn[WIDGET].methods[options](this, param1,param2,param3,param4);
		}
		options = options || {};
		if (options.fileType)
			options.initData['fileType'] = options.fileType;
		else
			options.fileType = options.initData['fileType'];
		
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
		// args.loadUrl: 첨부파일 목록조회 URL
		// args.params: 첨부파일 목록조회 조건
		select: function(jq, args) {
			return jq.each(function(){
				select(this, args);
			});
		},
		// 첨부파일 저장 검증 및 저장데이터 반환
		validate: function(jq, args) {
			return validate(jq[0], args);
		},
		// 첨부파일 저장데이터 반환
		getSaveData: function(jq) {
			return getSaveData(jq[0]);
		},
		// 서류코드 기준 첨부파일 업로드 여부 체크
		existFile: function(jq, code) {
			return existFile(jq[0], code);
		}
	};
	
	$.fn[WIDGET].defaults = {
		// 처리모드 (등록/수정/조회)
		mode: MODE.INSERT,
		// 화면스타일(BOX / LIST)
		screen: 'BOX',
		// 기본제목
		title: '제출서류',
		// 레이아웃 스타일시트
		cls: false,
		// 각종 스타일시트
		clsOptions: {
			areaCls: 'form-area-box',
			boxCls : 'ele-icon-box',
			btnCls : 'bs-l btn-black'
		},
		// 기본 URL
		url: {
			papeUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListPape.do'        ), // 서류양식 목록 조회 URL
			groupUrl       : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListPapeGroup.do'   ), // 서류그룹 목록 조회 URL
			loadUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/getListBizFile.do'     ), // 파일 목록 조회 URL
			saveUrl        : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/saveBizFile.do'        ), // 파일 저장 처리 URL (사용안함)
			removeUrl      : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/deleteBizFile.do'      ), // 파일 단일 삭제 URL
			uploadUrl      : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/uploadBizFile.do'      ), // 파일 임시업로드 URL (팝업용)
			downloadUrl    : getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/downloadBizFile.do'    ), // 파일 다운로드 URL
			downloadPapeUrl: getUrl('/'+SYSTEM.CODE.toLowerCase()+'/file/downloadPapeForm.do')  // 서류양식 다운로드 URL
		},
		// 생성시 기본 데이터
		initData: {
			//fileType: false, // 파일타입(BIZ,ENT,BBS)
			//papeCd  : false, // 서류코드
			//docuCd  : false, // 업무구분(TASK)
			//docuNo  : false, // 문서번호
			//docuSeq : false, // 세부문서번호
		},
		// 파일 타입
		fileType: CODE.FILE_TYPE.BIZ,
		// 빈값인 경우 텍스트
		emptyText: '별도 제출서류 없음',
		// 서류그룹 설명글
		// '※ 100MB 이내 1개 PDF 파일 첨부 가능'
		comment: false, 
		// 파일컨트롤 전체를 감싸는 영역
		areaWrap: 'app-file-area-wrap',
		// 파일박스 전체를 감싸는 영역
		fileWrap: 'app-file-wrap',
		// 서류그룹 전체를 감싸는 영역
		papeWrap: 'app-pape-group',
		// 파일선택박스를 감싸는 영역
		boxWrap: 'app-fboxwrap',
		// 버튼을 감싸는 영역
		btnWrap: 'app-fbtnwrap',
		// 파일선택박스 객체
		fileBox: 'input_file',
		// 파일텍스트박스 객체
		textBox: 'input_text',
		// 파일선택 버튼
		fileBtn: 'app-btn-file',
		// 양식파일 다운로드 버튼
		papeBtn: 'app-btn-papefile',
		// 다운로드 버튼
		downBtn: 'app-btn-download bizFileUpdate',
		// 업로드 버튼
		upldBtn: 'app-btn-upload',
		// 파일삭제 버튼
		deltBtn: 'app-btn-filedelt',
		// 파일영역 스타일시트
		fileCls: 'app-file-wrap',
		// 파일항목 스타일시트
		itemCls: 'app-file-item',
		// 파일영역 ID PREFIX
		fileKey: 'app-file-',

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
		// 메세지 alert 여부
		isAlert: false,
		// 확장자 체크 여부
		isExt:   false,
		// 용량 제한 체크 여부
		isLimit: false,

		// 추가,삭제 가능 여부
		multiple: false,
		// 초기 표시 갯수
		initCount: 1,
		// 추가 최대 갯수
		maxCount: 5,
		// 목록형인 경우 설정옵션
		listOptions: {
			// Wrap CLS
			wrapCls: 'table-box-1 m-overbox',
			// COLGROUP
			colgroup: ['200px','*'],
			// TBODY CLS
			tbodyCls: 'bs-1 t-t-c',
			// TR CLS
			trCls: 'px-v-m py-v-m t-t-c',
			// TH CLs
			thCls: 'fw-600 text-start',
			// TD CLS
			tdCls: '',
			// 버튼 CLS
			btnCls: 'btn-black bs-m'
		},
		// 박스형인 경우 설정옵션
		boxOptions: {
			// 버튼 CLS
			btnCls: 'btn-black bs-l'
		},
		// 내부변수
		_state_variables : {
			// 업로드팝업 객체
			_popup: false,
			// 신규등록 파일 (로드된파일 포함)
			_append_files: {},
			// 수정등록 파일
			_update_files: {},
			// 삭제대상 파일
			_remove_files: {}
		}
	};
		
})(jQuery);

//=============================================================================
//지원서비스 신청현황관리 2023.09.19 제출서류 수정하기 로직  / JH
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
	
	let P_MODAL = {
		// 모달팝업 객체
		modal: $('<div></div>'),
		// 상세조회 모달팝업 오픈
		doOpenSelect: function(row) {
			P_MODAL.modal.appModal({
				cls:        'w-ver1',
				title:      '신청현황관리 상세조회',
				url:        getUrl('/adm/support/support/modalInvtSprtAplyForm.do'),
				params:     JSON.stringify({
					sprtAplyNo : row['sprtAplyNo'],
				})
			}).open();
		},
	};
	
	// 제출서류 저장처리
	let _doSaveFile = function() {
		let saveUrl = getUrl('/adm/support/support/saveFile.do');
		let oper    = $(this).data('code');
		let step    = 1;
		let prm = {
			act       : MODE.TMPSAVE,
			mode      : $('#mode'      ).val(),
			sprtAplyNo: $('#sprtAplyNo').val(),
			sprtSeCd  : $('#sprtSeCd'  ).val(),
			prgrmNo   : $('#prgrmNo'   ).val(),
			stepCd    : options.stepCd,
			stepNo    : parseInt(options.stepNo),
			dtlDocNo  : options.aplyBzentyNo
		};
		
		if      (oper == 'SBMT') prm['act'   ]  = MODE.SUBMIT;
		// 첨부파일 업로드 VALIDATION
		let data = _this.appBizUpdatePapeFile('validate', {required: (oper == 'SBMT')});
		
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
						$('.detail-pop-1.w-ver1.trans').remove();
						P_MODAL.doOpenSelect(prm);
                    });
                }
            );
    	});
	return false;
	};
	
	// 첨부파일 초기화 (app_bizpapefile.js)
	_this.appBizUpdatePapeFile({
		// 처리모드
		mode : MODE.UPDATE,
		// 설명글
		comment: options.comment,
		// 초기값
		initData: {
			upDcmntCd	:  FILTER.getPapeGroup(options.sprtSeCd),
			taskSeCd	:  CODE.TASK_SE.SPRT,
			dtlSeCd 	:  options.prgrmNo,
			aplySeCd	:  options.bzentySeCd,
			docNo   	:  options.aplyNo,
			dtlDocNo    :  options.aplyBzentyNo,
		},
		extensions:['pdf'],
		isAlert: true,
		isExt:   true,
		isLimit: true
	}).appBizUpdatePapeFile('init');
	
	if (options.btnId) {
		$(options.btnId).appSprtButtons([
			// RIGHT BUTTON
			[$.sprtUtils.getCnclButton(options.formId)
			,$.sprtUtils.getSbmtButton(_doSaveFile)]
		], 'ver1');			
	}	
	return this;
};
$.sprtUtils = {
	// 취소버튼
	getCnclButton: function( formId ) {
		return {
			cls:   'btn-combi-3 bs-xl',
			icon:  'icon-times',
			value: '취소',
			click: function() {
				 $(".modal").modal('hide');
			}
		};
	},
	// 제출버튼
	getSbmtButton: function( actionCallback ) {
		return {
			cls:   'btn-primary bs-xl',
			icon:  'icon-floppy-disk',
			value: '수정하기',
			code:  'SBMT',
			click: actionCallback
		};
	},
};

//지원신청 버튼
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

