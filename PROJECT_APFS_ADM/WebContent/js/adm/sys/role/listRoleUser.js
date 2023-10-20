/**
******************************************************************************************
*** 파일명 : listRoleUser.js
*** 설명글 : 역할별사용자관리 관리 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.09.06    LSH
*** 1.0         2021.11.02    LSH   디자인적용 및 개발 수정
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RGRID  = $('#roleGrid'   ); // 역할별 사용자 그리드 객체
    var P_MGRID  = $('#userGrid'   ); // 사용자 그리드 객체
    var P_SFORM  = $('#searchForm' ); // 검색폼 객체
    var P_FORMAT = {
	    roleId: function(value, row) {
		    return row['roleNm']+' ['+value+']';
        }
    };

    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_RGRID.datagrid({
		// 화면영역 맞춤
		fit: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',    
        // 그리드 데이터연동 방식
        method: 'POST',
        // 2022.01.09 LSH 데이터 건이 많으면 rownumbers 로 인해 느려져 주석처리함
        // 그리드 행번호 표시여부
        //rownumbers: true,
        // 체크박스 KEY값필드
        idField:'userNo',
        // 칼럼정의
        columns: [[
            {field:'chckId',checkbox: true},
            {field:'roleId',width:300,halign:'center',align:'left',title:'역할', formatter: P_FORMAT.roleId},
            {field:'userNm',width:300,halign:'center',align:'left',title:'사용자명'},
            {field:'userNo',width:180,halign:'center',align:'left',title:'사용자번호'}
        ]]
    });
    P_MGRID.datagrid({
		// 화면영역 맞춤
		fit: true,
        // 그리드 결과데이터 타입
        contentType: 'application/json',
        // 그리드 결과데이터 타입
        dataType: 'json',    
        // 그리드 데이터연동 방식
        method: 'POST',
        // 체크박스 KEY값필드
        idField:'userNo',
        // 칼럼정의
        columns: [[
            {field:'chckId',checkbox: true},
            {field:'userNm',width:300,halign:'center',align:'left',title:'사용자명'},
            {field:'userNo',width:180,halign:'center',align:'left',title:'사용자번호'}
        ]]
    });

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//
	$('#srchRoleId').appComboBox({
		url: getUrl('/com/common/getComboRole.do'),
		// 콤보 값변경시 검색 처리
        change:   doSearch,
		// 콤보 값로딩후 실행 처리
		callback: doSearch

	});
    $('#srchSysCd').appComboBox({
		params: {upCdId: CODE.SYSTEM},
		// 콤보 값변경시 검색 처리
        change:   doSearch,
		// 콤보 값로딩후 실행 처리
		callback: doSearch
    });

    // 역할별사용자관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        doSearchRole();
        doSearchUser();
    }
    
    // 역할별사용자목록 검색처리
    //--------------------------------------------------------//
    function doSearchRole() {
		// 선택된 항목 CLEAR
		P_RGRID.datagrid('clearSelections');
        // 검색폼 데이터 객체화
        var obj = P_SFORM.serializeObject();
        // 그리드 목록조회 URL
        P_RGRID.datagrid('options')['url'] = getUrl('/adm/sys/role/getListRoleUser.do');
        // 검색폼 그리드 검색
        P_RGRID.datagrid('load', obj);
    }
    
    // 추가할사용자목록 검색처리
    //--------------------------------------------------------//
    function doSearchUser() {
		// 선택된 항목 CLEAR
		P_MGRID.datagrid('clearSelections');
        // 그리드 목록조회 URL
        P_MGRID.datagrid('options')['url'] = getUrl('/adm/sys/user/getListUserInfo.do');
        // 권한설정이 안되어 있는 사용자 검색
        P_MGRID.datagrid('load', {srchRoleYn:'N'});
    }

    // 역할별사용자관리 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
        // 역할별사용자목록의 선택 항목
        const rows = P_RGRID.datagrid('getSelections');
        if (rows.length == 0) {
            $.easyMsg.alert('제외할 사용자를 선택하세요.');
            return false;
        }
        
        $.easyMsg.confirm(rows.length+"명의 사용자를 제외하시겠습니까?", function() {
            // AJAX로 저장처리
            $.ajaxUtil.ajaxSave(
                getUrl('/adm/sys/role/saveRoleUser.do'), 
                JSON.stringify({
                    mode: MODE.REMOVE,
                    roleList: rows
                }),
                function(ret) {
					$.easyMsg.success(ret, '성공적으로 제외되었습니다.', doSearch);
                }
            );
        });
        return false;
    }

    // 역할별사용자관리 추가하기
    //--------------------------------------------------------//
    function doAppend() {
        // 추가할사용자목록의 선택 항목
        const rows = P_MGRID.datagrid('getSelections');
        // 역할ID
        const roleId = $('#srchRoleId').val();
        
        if (rows.length == 0) {
            $.easyMsg.alert('추가할 사용자를 선택하세요.');
            return false;
        }
        if ($.commUtil.empty(roleId)) {
            $.easyMsg.alert('역할ID를 확인할 수 없습니다.');
            return false;
        }
        
        $.commMsg.confirm(rows.length+"명의 사용자를 추가하시겠습니까?", function() {
            // AJAX로 저장처리
            $.ajaxUtil.ajaxSave(
                getUrl('/adm/sys/role/saveRoleUser.do'), 
                JSON.stringify({
                    mode: MODE.INSERT,
                    roleId: roleId,
                    roleList: rows
                }),
                function(ret) {
					$.easyMsg.success(ret, '성공적으로 추가되었습니다.', doSearch);
                }
            );
        });
        return false;
    }
    
    // 추가 클릭시 이벤트 처리
    $('#btnAppend').bind('click', doAppend);
    // 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);
});
