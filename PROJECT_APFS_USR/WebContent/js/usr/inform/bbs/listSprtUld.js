/**
*******************************************************************************
***    명칭: listSprtUld.js
***    설명: 정보서비스 - 경영체 데이터 업로드 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자           내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13      LHB       First Coding.
*******************************************************************************
**/
$(function() {
	//========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE			= '업로드 이력';			// 화면 제목
    var P_SFORM			= $('#searchForm' );	// 검색폼 객체
	P_GRID				= $('#appGrid');		// 그리드 객체
	let P_GRID_BIZ		= $('#appGridBiz');		// 데이터 그리드 객체
    //========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'uldNo',
		// 목록 제목
		title:		P_TITLE,
		// 검색 URL
		url:		getUrl('/usr/inform/bbs/getListSprtUld.do'),
        // 조회 파라미터 전달
		queryParams: {
	    },
		//목록 검색 페이징 객체
		pagination: { display: 10 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 {title}이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','280px','100px','170px','80px', '60px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true		, cls:'app-c', label:'번호' },
	            {name:'fileNm'			, cls:'app-l', label:'파일명'},
	            {name:'bzentyNm'		, cls:'app-c', label:'기관명'},
	            {name:'rgtrNoNm'		, cls:'app-c', label:'작성자'},
				{name:'regDate'			, cls:'app-c', label:'업로드 일자'},
				{name:'fileDownload'	, cls:'app-c', label:'다운로드', 
					formatter: function(v, o) {
						let b = $('<button type="button" class="bs-s w-100 border-0 btnExclDown"></button>');
						b.data('uldNo', o['uldNo']);
						b.append('<i class="icon-file-xls"></i>');

						b.bind('click', function() {
							fileDownload(o['uldNo']);
						})
						return b;
					}
				},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
			select: doSelect,
		},
		callback: function(elem, rows, params) {
			$('#total').text(params['total']);
		},
	}).appBbsGrid('init');
	
	P_GRID_BIZ.appBbsGrid({
		// 목록 KEY필드
		idField:    'sn',
		// 목록 제목
		title:		'업로드한 파일 상세내역',
		// 검색 URL
		url:		getUrl('/usr/inform/bbs/getListSprtBiz.do'),
        // 조회 파라미터 전달
		queryParams: {
	    },
		//목록 검색 페이징 객체
		pagination: { display: 10 },
		// 페이지 표시 객체
		paging:     '#appGridPaginationBiz',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['40px','130px','140px','200px','200px','60px','140px','200px','140px','200px','140px','200px','140px','200px','90px','200px'],
			// 목록칼럼 정의
			columns: [
				{name:'sn'			  , cls:'app-c', label:'',
					formatter:function(v, o) {
						let i = $('<input type="checkbox" name="sn" value="'+v+'">');
						if(o['delYn'] == 'Y')
							i.prop('disabled', true);
						return i;
					}
				},
	            {name:'brno'          , cls:'app-c', label:'사업자번호'},
	            {name:'crdnsBzentyNm' , cls:'app-c', label:'기업체명'},
	            {name:'bizNm'		  , cls:'app-c', label:'지원사업명'},
				{name:'bizYr'		  , cls:'app-c', label:'연도'},
				{name:'artclNm1'	  , cls:'app-c', label:'항목명1'},
				{name:'artclCn1'	  , cls:'app-c', label:'항목내용1'},
				{name:'artclNm2'	  , cls:'app-c', label:'항목명2'},
				{name:'artclCn2'	  , cls:'app-c', label:'항목내용2'},
				{name:'artclNm3'	  , cls:'app-c', label:'항목명3'},
				{name:'artclCn3'	  , cls:'app-c', label:'항목내용3'},
				{name:'artclNm4'	  , cls:'app-c', label:'항목명4'},
				{name:'artclCn4'	  , cls:'app-c', label:'항목내용4'},
				{name:'directInptYn'  , cls:'app-c', label:'강제업로드'},
				{name:'mdfrNoNm'	  , cls:'app-c', label:'삭제자'},
				{name:'mdfDate'	      , cls:'app-c', label:'삭제일자'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
		},
		callback: function(elem, rows, params) {
			rows.forEach(function(e, i) {
				if(e['delYn'] == 'Y') 
					elem.find('tbody tr').eq(i).css('background-color', 'var(--light-gray)');
			})
		},
	}).appBbsGrid('init');
	
	// 정렬방법에 따른 게시물 기준 정렬
	function initHourTime() {
	    // '시간' 버튼이 클릭되었을 때 실행
	    $('#btnSrchType').click(function() {
	        var SrchTypeList = STORE.SORT_SPRTULD; // STORE.getHours()에서 시간 값을 가져옴
	        // 존재하는 항목들을 모두 삭제
	        $(".dropdown-menu").eq(0).empty();
	        // 시간 값들을 <li> 항목으로 만들어서 dropdown-menu에 추가
	        for (var i = 0; i < SrchTypeList.length; i++) {
	            var SrchType = SrchTypeList[i];
	            var liItem = '<li><a class="dropdown-item" href="javascript:void(0)" data-code="' + SrchType.code + '" data-text="' + SrchType.text + '">' + SrchType.text + '</a></li>';
	            $(".dropdown-menu").eq(0).append(liItem);
	        }
	    });
	
	    // dropdown-item이 클릭되었을 때 실행
	    $(".dropdown-menu").eq(0).on('click', '.dropdown-item', function() {
	        var text = $(this).data('text'); // 클릭된 항목의 시간 값을 가져옴
	        var code = $(this).data('code'); // 클릭된 항목의 시간 값을 가져옴
	        $('#btnSrchType').text(text); // 버튼의 텍스트를 선택된 시간 값으로 설정
	        $('#srchType').val(code); // 버튼의 텍스트를 선택된 시간 값으로 설정
	        
	        doSearch($('#page').val());
	    });
	}
	
	$(document).ready(initHourTime);
	
	if( $('#srchType').val()){
		var SrchTypeList = STORE.SORT_INVESTCASE; // STORE.getHours()에서 시간 값을 가져옴
		for (var i = 0; i < SrchTypeList.length; i++) {
            var SrchType = SrchTypeList[i];
            var liItem = '<li><a class="dropdown-item" href="javascript:void(0)" data-code="' + SrchType.code + '" data-text="' + SrchType.text + '">' + SrchType.text + '</a></li>';
            $(".dropdown-menu").eq(0).append(liItem);
            if($('#srchType').val()==SrchType.code ){
            	$('#btnSrchType').text(SrchType.text);
            }
        }
	}
	
	// 업로드 현황 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

	// 행 클릭 시 데이터 조회
    //--------------------------------------------------------//
    function doSelect(row) {
		$('#gridBiz').removeClass('d-none');
		$('#btnDelete').data('uldNo', row['uldNo']);
		P_GRID_BIZ.appBbsGrid('search', {uldNo: row['uldNo']});

        return true;
    }

	// 업로드 현황 등록하기
    //--------------------------------------------------------//
    function doUpload() {
		P_MODAL2 = $('<div></div>').appPopupSprtUld({
		}, function() {
			return false;
		});
    }

	// 양식 다운로드하기
    //--------------------------------------------------------//
    function doDownload() {
		$.bizUtils.downloadSprtUld();
    }

	// 파일 다운로드하기
    //--------------------------------------------------------//
    function fileDownload(uldNo) {
		$.bizUtils.downloadSprtBizFile(uldNo);
    }

	// 선택한 데이터 삭제하기
    //--------------------------------------------------------//
    function doDelete() {
		var uldNo = $(this).data('uldNo');
		var sn = new Array();
		$('input[name="sn"]:checked').each(function(i, e) {
			sn.push(e.value);
		});
		
		if (!sn || sn.length < 1) {
			$.commMsg.alert('삭제할 이력을 선택해주세요.');
			return false;
		}
		
		$.commMsg.confirm("정말 삭제하시겠습니까?", function() {
            // AJAX로 삭제처리
            $.ajaxUtil.ajaxLoad(
                getUrl('/usr/inform/bbs/saveSprtBiz.do'), 
                {arrSnStr: sn.join(',')}, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록으로 이동
                        $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
							// 목록 초기화
                        	P_GRID_BIZ.appBbsGrid('search', {uldNo: uldNo});
        					return false;
						});
                    });
                }
            );
        });
        return false;
    }

	// 파일업로드 버튼 클릭시 이벤트처리
	$('#btnUpload'  ).bind('click', doUpload);
	// 파일다운로드 버튼 클릭시 이벤트처리
	$('#btnDownload').bind('click', doDownload);
	// 삭제 버튼 클릭시 이벤트처리
	$('#btnDelete').bind('click', doDelete);
	

	doSearch();
	
	
});

let P_GRID;
let P_FILE_BOX;

let P_MODAL2;
//[팝업] 파일업로드 팝업
//=============================================================================
$.fn.appPopupSprtUld = function( params, saveCallback ) {
	
	let options = {
		title     		: '파일 업로드',
		icon      		: '<i class="icon-copy-alt"></i>',
		loadUrl			: getUrl('/usr/inform/bbs/modalSprtUld.do'),
		loadParams		: params,
	};
	
	return $(this).appPopup({
		url:        	options.loadUrl,
		params:     	JSON.stringify(options.loadParams),
		title:      	options.title,
		icon:       	options.icon,
		style:			'width: 600px',
		type:      	 	'pageload',
		dialogCls:  	'w-ver1',
		appendBody: 'body',
		onloaded:   function(pop) {
			return false;
		}
	}).open();
};

// 파일 다운로드
$.bizUtils.downloadSprtBizFile = function(uldNo) {
	let url		= getUrl('/usr/inform/bbs/downloadSprtUldFile.do');

	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {uldNo: uldNo},
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