/**
*******************************************************************************
***    명칭: openSurvey.jsp
***    설명: 운영관리-설문관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'     ); // 목록 GRID
	let P_FORM    = $('#searchForm'  ); // 검색폼	
	//========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 분류 구분
	$('#srchTrgt').appComboBox({
		url: getUrl('/com/common/getComboCode.do'),
		prompt: '설문대상 분류코드',
		params: { 'upCdId': 'CT.QSTNN_TRGT' }, // 파라미터 설정
		loadFilter: function(data) {
			data.unshift({code: '', text: '전체'}); //첫번째 배열에 전체 추가
			return data;
		},
	});
	//========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'srvyNo',
		// 목록 제목
		title:      '설문관리 목록',
		// 검색 URL
		url:         getUrl('/adm/inform/survey/getListSurvey.do'),
		//목록 검색 페이징 객체
		pagination: { display	: 20 },
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 리스트옵션
		listOptions: {
			// 목록 없을경우 메세지
			emptyText:  '{title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','*','250px','130px','120px','120px'],
			// 목록칼럼 정의
			columns: [
	            {name:'srvyNo'    	 ,cls:'app-c'	,label:'번호', rownumbers: true},
	            {name:'srvyCn'       ,cls:'app-l'	,label:'설문제목'},
	            {name:'srvyBgngYmd'  ,cls:'app-c'	,label:'설문기간', formatter: function(v, o) {
					return v + ' ~ ' + o['srvyEndYmd'];
				}},
	            {name:'srvyTrgtNm'   ,cls:'app-c'	,label:'설문대상', formatter: function(v,o) {
					if(v==''||v==null) v = '전체';
					return v;
				}},
				{name:'rgtrNm'       ,cls:'app-c'	,label:'등록자'},
	            {name:'regDate'      ,cls:'app-c'	,label:'등록일'}
			],
			select: doSelect,
		},
		// 최초로딩여부
		autoload: true
    }).appBbsGrid('init');

	// 검색관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

	// 검색관리 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_FORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }
	
	// 설문 신규등록
    //--------------------------------------------------------//
    function doRegist() {
        P_MODAL.doOpenRegist({});

        return false;
    }

	// 게시판 상세조회
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect( row );
        return false;
    }
	
	// 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
	// 신규등록 버튼 클릭
	$('#btnRegist').bind('click', doRegist);
	
});

// 설문 신규등록, 설문 모달팝업
//=============================================================================
let P_MODAL = {
	// 모달팝업 객체
	modal: $('<div></div>'),
	// 상세조회 모달팝업 오픈
	doOpenSelect: function(row) {
		P_MODAL.modal.appModal({
			cls: 'w-ver1',
			title: row['srvyCn'],
			url: getUrl('/adm/inform/survey/modalSurveyView.do'),
			params: JSON.stringify({
				srvyNo: row['srvyNo'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls: 'w-ver1',
			title: '설문 신규등록',
			url: getUrl('/adm/inform/survey/modalSurveyForm.do'),
			params: JSON.stringify({
				mode: MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function() {
		P_MODAL.modal.appModal({
			cls: 'w-ver1',
			title: '사용자관리 수정하기',
			url: getUrl('/adm/inform/survey/modalSurveyForm.do'),
			params: JSON.stringify({
				mode: MODE.UPDATE,
				srvyNo: $('#srvyNo').val(),  
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
		//P_MODAL.modal.doClose();
		P_MODAL.doClose();
		// 검색폼 데이터 객체화
		var obj = $('#searchForm').serializeObject();
		// 검색폼 그리드 검색
		$('#appGrid').appBbsGrid('search', obj);
	},
	// [모달팝업 호출용] 설문 삭제 하기
    doRemove: function() {
        $.commMsg.confirm("정말 삭제하시겠습니까?", function() {
            // AJAX로 삭제처리
            $.ajaxUtil.ajaxLoad(
                getUrl('/adm/inform/survey/saveSurvey.do'), 
                {srvyNo   : $('#srvyNo').val(),
                 mode    : MODE.REMOVE
                }, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록으로 이동
                        $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
							// 목록 초기화
                        	P_MODAL.doSearch();
						});
                    });
                }
            );
        });
        return false;
    }
};