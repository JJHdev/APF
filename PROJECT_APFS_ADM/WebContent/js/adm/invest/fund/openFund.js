/**
******************************************************************************************
*** 파일명 : openFund.js
*** 설명글 : 투자정보관리 - 모태펀드등록 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.05.03               LHB                      신규 작성
*** 1.1         2023.06.02               LHB                   모달 추가 작업 완료
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
	var P_TITLE			= '모태펀드'        ;		// 화면 제목
    var P_GRID			= $('#appGrid'   );		// 그리드 객체
	var P_GRID2			= $('#grid-mmbs' );		// 조합원 목록 추가그리드 객체
    var P_SFORM			= $('#searchForm');		// 검색폼 객체

    //========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'fundNo',
		// 목록 제목
		title:		'모태펀드',
		// 검색 URL
		url:		getUrl('/adm/invest/fund/getListFund.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 모태펀드가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['30px','300px','200px','50px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true    ,cls:'app-c',label:'번호',},
	            {name:'fundNm'      ,cls:'app-l',label:'펀드명'},
	            {name:'invtFldCdNm' ,cls:'app-c',label:'투자분야'},
	            {name:'makeYr'      ,cls:'app-c',label:'결성년도'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
	        // 행선택시 상세조회
	        select: doSelect	
		},
	}).appBbsGrid('init');

	P_GRID2.datagrid({
		// 화면영역 맞춤
		fit: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',
        // 그리드 데이터연동 방식
        method: 'POST',
        // 그리드 단일행만 선택여부
        singleSelect: false,
        // 그리드 페이징처리 여부
        pagination:true,
        // 그리드 행번호 표시여부
        rownumbers:true,
        // 한 페이지 출력수
        pageSize: 30,
        // 칼럼정의
        columns: [[
			{field:'fundNo'   ,width:80, halign:'center',align:'center',title:'펀드번호', hidden: true},
			{field:'bzentyNo' ,width:80, halign:'center',align:'center',title:'업체번호', hidden: true},
			{field:'chk'	  ,width:80, halign:'center',align:'center',checkbox: true,},
			{field:'bzentyNm' ,width:120,halign:'center',align:'center',title:'소속',},
            {field:'brno'     ,width:100,halign:'center',align:'center',title:'사업자번호'},
            {field:'rprsvNm'  ,width:60, halign:'center',align:'center',title:'대표자'},
            {field:'fndnYmd'  ,width:100,halign:'center',align:'center',title:'설립일'},
        ]],
        // 행선택시 수정하기
        onSelect: function(index, row) {
		},
		// no-data 표출 메시지
		emptyMsg: '조합원 목록이 없습니다.',
		// 목록로딩 완료후 처리
		onLoadSuccess: function(data) {
		}
    });

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	// 검색용 투자분야
	$('#srchInvtFldCd').appComboBox({
		url: getUrl('/com/common/getInvtRlmComboCode.do'),
		prompt: '투자분야',
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});

    // 모태펀드 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

    // 모태펀드 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
        P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();

        return false;
    }

    // 모태펀드 수정하기
    //--------------------------------------------------------//
    function doSelect(row) {
		// 상세조회 모달팝업 오픈
		P_MODAL.doOpenSelect(row);
		
		// 대표연락처 초기화
		if (row['rprsTelno'] == null) {
			P_MODAL.modal.find('#rprsTelno2').val('');
			P_MODAL.modal.find('#rprsTelno3').val('');
		}
		
        return false;
    }

    // 모태펀드 등록하기
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
			//cls:        'w-ver1',
			title:      '모태펀드 상세조회',
			url:        getUrl('/adm/invest/fund/modalFundForm.do'),
			params:     JSON.stringify({
				fundNo     : row['fundNo'],
				
			})
		}).open();
	},
	// 신규등록 모달팝업 오픈
	doOpenRegist: function(row) {
		P_MODAL.modal.appModal({
			//cls:        'w-ver1',
			title:      '모태펀드 신규등록',
			url:        getUrl('/adm/invest/fund/modalFundForm.do'),
			params:     JSON.stringify({
				mode       : MODE.INSERT,
			})
		}).open();
	},
	// 수정하기 모달팝업 오픈
	doOpenUpdate: function(row) {
		P_MODAL.modal.appModal({
			//cls:        'w-ver1',
			title:      '모태펀드 수정하기',
			url:        getUrl('/adm/invest/fund/modalFundForm.do'),
			params:     JSON.stringify({
				mode       : MODE.UPDATE,
				fundNo     : row['fundNo'],
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

// 조합원 삭제하기
//--------------------------------------------------------//
function doDeltInvt(elem) {
	console.log(elem);
	var sn		= $(elem).parent().find('input[name="sn"]').val();
	var fundNo	= $.formUtil.getValue($('#registForm'), 'fundNo');
	
	$.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
		// form 안에 bzentyNo의 name을 가진 input 태그 세팅
		var html = '';
		html += '<input type="hidden" name="sn" value="' + sn + '"/>';
		html += '<input type="hidden" name="mode" value="D"/>';
		html += '<input type="hidden" name="fundNo" value="' + fundNo + '"/>';
		$('#mmbsForm').html(html);

        // 등록폼을 AJAX로 저장처리
        $('#mmbsForm').ajaxForm({
            url: getUrl('/adm/invest/fund/saveFundInvstr.do'),
            // 오류시 처리로직
            error: $.ajaxUtil.error,
            // 저장후 처리로직
            success: function(ret) {
				$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
					P_MODAL.doClose();
					P_MODAL.doOpenSelect({fundNo: fundNo, });
				});
            }
        }).submit();
    });

    return false;
}