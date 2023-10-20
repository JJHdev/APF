/**
******************************************************************************************
*** 파일명 : openIrOpnn.js
*** 설명글 : 투자정보관리-IR검토의견서관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.06.07               LHB                      신규 작성
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE  = 'IR검토의견서관리'  ; // 화면 제목
    var P_GRID   = $('#appGrid'   ); // 그리드 객체
    var P_SFORM  = $('#searchForm'); // 검색폼 객체
    var P_RFORM  = $('#registForm'); // 등록폼 객체

	var P_DTBTN		= $('.btn-date');		// 행사기간 날짜 선택 버튼

    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'evntNo',
		// 목록 제목
		title:		'투자설명회',
		// 검색 URL
		url:		getUrl('/adm/invest/event/getListEvent.do'),
        // 조회 파라미터 전달
		queryParams: {
	    },
		//목록 검색 페이징 객체
		pagination: { display: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 투자설명회가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','300px','100px','100px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers: true   ,cls:'app-c',label:'번호'},
	            {name:'evntNm'      ,cls:'app-l',label:'행사명'},
	            {name:'evntBgngYmd' ,cls:'app-c',label:'행사일자'},
	            {name:'regDate'     ,cls:'app-c',label:'등록일자'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');
    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//

    // 행사(투자설명회) 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'srchBgngYmd'),
		                        $.formUtil.getValue(P_SFORM, 'srchEndYmd' ),
                                '행사기간 시작일과 종료일의 값이 올바르지 않습니다.')) {
	
			// 검색폼 데이터 객체화
	        var obj = P_SFORM.serializeObject();
	        // 검색폼 그리드 검색
	        P_GRID.appBbsGrid('search', obj);
	        return false;
		}
    }

    // 행사(투자설명회) 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 행사(투자설명회) 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);

        return false;
    }

    // 행사(투자설명회) 등록하기
    //--------------------------------------------------------//
    function doRegist() {
		P_MODAL.doOpenRegist({});
        return false;
    }
	

    // 행사(투자설명회) 저장하기
    //--------------------------------------------------------//
    function doSave() {

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("저장하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/event/saveEvent.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
                        doRegist();
                        doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 행사(투자설명회) 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
	
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/invest/event/saveEvent.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
                        doRegist();
                        doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 검색조건 행사기간 버튼 이벤트
	function setSrchDate() {
		var bgngYmd	= $('#srchBgngYmd');
		var endYmd	= $('#srchEndYmd');
		var dates	= Number($(this).data('dates'));
		
		var bgngDate;
		var endDate;
		
		if (!$.commUtil.isEmpty(bgngYmd.val()) && $.commUtil.isEmpty(endYmd.val())) {
			// 시작일자는 입력되어 있지만 종료일자는 입력되지 않은 경우
			endDate = new Date(new Date(bgngYmd.val()) + 1000 * 60 * 60 * 24 * dates);
			
			endYmd.val($.commUtil.toFormatDate(endDate, '-'));
		} else if ($.commUtil.isEmpty(bgngYmd.val()) && !$.commUtil.isEmpty(endYmd.val())) {
			// 시작일자는 입력되지 않았지만 종료일자는 입력된 경우
			bgngDate = new Date(new Date(endYmd.val()) - 1000 * 60 * 60 * 24 * dates);
			
			bgngYmd.val($.commUtil.toFormatDate(bgngDate, '-'));
		} else {
			bgngDate = new Date(new Date() - 1000 * 60 * 60 * 24 * dates);
			
			bgngYmd.val($.commUtil.toFormatDate(bgngDate, '-'));
			endYmd.val($.commUtil.toFormatDate(new Date(), '-'));
		}
	}

    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist').bind('click', doRegist);
	// 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);

    // 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
    // 취소버튼 클릭시 이벤트처리
    $('#btnUndo'  ).bind('click', doRegist);

	// 행사기간 날짜 선택 버튼 클릭시 이벤트 처리
	P_DTBTN.bind('click', setSrchDate);

    // 검색 실행
    doSearch();

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));
});

// 투자자별, 경영체별 IR 검토의견서에서 사용하기 위한 변수
let P_MODAL_INVT;
let P_MODAL_ENT;

//========================================================//
// 등록,수정,상세 모달팝업 정의
//  - 오픈된 모달창에서 사용하기 위해 외부에 정의함
//--------------------------------------------------------//
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver10',
			title:      'IR검토의견서관리 상세조회',
			url:        getUrl('/adm/invest/event/modalIrOpnnForm.do'),
			params:     JSON.stringify({
				evntNo		: row['evntNo'],
				evntType	: 'INVT',
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver10',
			title:      'IR검토의견서관리 신규등록',
			url:        getUrl('/adm/invest/event/modalIrOpnnForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver10',
			title:      'IR검토의견서관리 상세조회',
			url:        getUrl('/adm/invest/event/modalIrOpnnForm.do'),
			params:     JSON.stringify({
				mode		: MODE.UPDATE,
				evntNo		: row['evntNo'],
				evntType	: row['evntType'] ? row['evntType'] : 'INVT',
			})
		}).open();
	},
	// [모달팝업 호출용] 모달팝업 닫기
	doClose: function() {
		if (P_MODAL.modal && 
			P_MODAL.modal.close)
			P_MODAL.modal.close();
	},
	// [모달팝업 호출용] 목록 재검색 및 팝업 닫기
	doSearch: function() {
		// 모달팝업 닫기
		P_MODAL.doClose();
	    // 검색폼 데이터 객체화
	    var obj = $('#searchForm').serializeObject();
	    // 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	}
};