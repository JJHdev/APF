/**
******************************************************************************************
*** 파일명 : openUserInfo.js
*** 설명글 : 회원관리-사용자관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.09.13               LSH                    First Coding.
*** 2.0         2023.06.12               LHB                       모달 변경
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE  = '사용자'          ; // 화면 제목
    var P_GRID   = $('#appGrid'    ); // 그리드 객체
    var P_SFORM  = $('#searchForm' ); // 검색폼 객체
    var P_RFORM  = $('#registForm' ); // 등록폼 객체

	// GRID 포맷정의
    //--------------------------------------------------------//
	var P_FORMAT = {
		// 사용상태에 따른 행색상 정의
		//---------------------------------//
		rowColor: function(row,json) {
			if (json['useSttsCd'] != '1') 
				row.addClass('app-bg-gray');
		},
		// 비밀번호 오류표시
		//---------------------------------//
		pswdErr: function(v) {
			if (v == '')
				return v;
			return (v == 5 ? '계정잠김' : v+'회');
		},
		// 대표여부 표시
		//---------------------------------//
		rprsYn: function(v) {
			 return (v == 'Y' ? '대표' : '');
		}
	};

    //========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'userNo',
		// 목록 제목
		title:		'사용자',
		// 검색 URL
		url:		getUrl('/adm/sys/user/getListUserInfo.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 사용자가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['50px','70px','150px','100px','80px','80px','120px','60px','80px','80px','100px','90px','90px'],
			// 목록칼럼 정의
			columns: [
	            {rownumbers:true    ,cls:'app-c', label:'번호', formatRow: P_FORMAT.rowColor},
	            {name:'userNm'		,cls:'app-l', label:'성명'},
				{name:'userId'		,cls:'app-l', label:'아이디'},
				{name:'mblTelno'	,cls:'app-c', label:'휴대전화', formatter:$.formatUtil.toPhone},
				{name:'admRoleNm'	,cls:'app-c', label:'관리자권한'},
				{name:'usrRoleNm'	,cls:'app-c', label:'사용자권한'},
				{name:'bzentyNm'	,cls:'app-l', label:'소속업체'},
				{name:'rprsYn'   	,cls:'app-c', label:'대표여부', formatter: P_FORMAT.rprsYn},
				{name:'joinYmd'		,cls:'app-c', label:'가입일자'},
				{name:'pswdErrCnt'	,cls:'app-c', label:'비밀번호<br>오류횟수', formatter: P_FORMAT.pswdErr},
				{name:'lastLgnDt'	,cls:'app-c', label:'최종로그인'},
				{name:'regDate'		,cls:'app-c', label:'등록일자'},
				{name:'mdfDate'		,cls:'app-c', label:'수정일자'},
			],
	        // 행선택시 상세조회
	        select: doSelect	
		},
    }).appBbsGrid('init');

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 권한
	$('#srchRoleId').appComboBox({
		url: getUrl('/com/common/getComboRole.do'),
		prompt: '사용자 권한',
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
		filter: function(row) {
			// 표출 항목
			if (row['code'] == 'ROLE_USR_RESTRICTED')
				return false;
			return true;
		}
	});

    //========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//

    // 사용자정보 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		// 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

    // 사용자정보 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 사용자정보 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
	
		console.log(row);
	
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
		
		// 대표연락처 초기화
		if (row['rprsTelno'] == null) {
			P_MODAL.modal.find('#rprsTelno2').val('');
			P_MODAL.modal.find('#rprsTelno3').val('');
		}
		
        return false;
    }

    // 사용자정보 등록하기
    //--------------------------------------------------------//
    function doRegist() {
        P_MODAL.doOpenRegist({});

        return false;
    }

    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 등록버튼 클릭시 이벤트처리
    $('#btnRegist').bind('click', doRegist);

    // 취소버튼 클릭시 이벤트처리
    $('#btnUndo'  ).bind('click', doRegist);

    // 검색 실행
    doSearch();

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));

});

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
			cls:        'w-ver1',
			title:      '사용자관리 상세조회',
			url:        getUrl('/adm/sys/user/modalUserInfoForm.do'),
			params:     JSON.stringify({
				userNo     : row['userNo'],
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '사용자관리 신규등록',
			url:        getUrl('/adm/sys/user/modalUserInfoForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			cls:        'w-ver1',
			title:      '사용자관리 수정하기',
			url:        getUrl('/adm/sys/user/modalUserInfoForm.do'),
			params:     JSON.stringify({
				mode       : MODE.UPDATE,
				userNo     : row['userNo'],
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
	}
};