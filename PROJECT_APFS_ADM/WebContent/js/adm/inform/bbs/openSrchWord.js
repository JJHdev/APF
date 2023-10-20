/**
*******************************************************************************
***    명칭: openSrchWord.jsp
***    설명: 운영관리-우수투자사례 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.25    JH          First Coding.
***    1.1      2023.06.27    JH          작업 완료.
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID    = $('#appGrid'        ); // 목록 GRID
    var P_SFORM   = $('#searchForm'		); // 검색폼 객체
    //========================================================//
    // 목록 GRID 정의 (appBbsGrid : app_bbsgrid.js)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'sn',
		// 목록 제목
		title:      '검색어관리',
		// 검색 URL
		url:         getUrl('/adm/inform/bbs/getListSrchWord.do'),
		// bbsSeCd코드 전달
		queryParams: { },
		//목록 검색 페이징 객체
		pagination: {display: 10},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 검색결과 표시 객체
		result:     '#appGridResult',
		resultText : '1~3순위 검색어 키워드가 플랫폼에 노출됩니다.',
		// 리스트옵션
		listOptions: {
			bodyCls: "t-t-c ts-m bs-1",
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['200px','*px','150px','150px'],
			// 목록칼럼 정의
			columns: [
	            {name:'rank'            ,cls:'app-c',label:'순위'},
	            {name:'srchwrd'       	,cls:'app-l',label:'검색어 키워드'},
	            {name:'count'           ,cls:'app-c',label:'검색건수'},
	            {name:'button'			,cls:'app-c',label:'버튼', 
	             formatter: function(value, row, rowIndex, columnIndex) {
	             	return '<button class="bs-s btn-black" data-srchwrd="' + row.srchwrd + '" data-bs-toggle="modal" data-bs-target="#myModal1">삭제</button>';
	             }}
			],
		}
    }).appBbsGrid('init');
		
    // 검색 실행
    doReset();
    $('.app-grid-table thead tr th:last').remove();
    $('.app-grid-table thead tr th:last').attr('colspan', 2);
    
    // 검색어관리 검색처리
    //--------------------------------------------------------//
    function doSearch() {
    	// 검색폼 데이터 객체화
    	var obj = P_SFORM.serializeObject();
    	
    	// 그리드 목록조회 URL
    	P_GRID.appBbsGrid('search', obj);
    	
    	return false;
    }

    // 검색어관리 검색리셋
    //--------------------------------------------------------//
    function doReset() {
        // 검색폼 입력데이터 초기화
    	P_SFORM.form('reset');
        // 검색폼 그리드 검색 처리
        doSearch();
        return false;
    }
    // 삭제버튼 클릭시 모달창에 srchwrd번호 전달
    $('#myModal1').on('show.bs.modal', function (event) {
	  // 버튼을 클릭한 요소
	  var button = $(event.relatedTarget);
	  // 데이터 속성에서 'srchwrd' 값을 얻기
	  var srchwrd = button.data('srchwrd');
	  // 모달에 'srchwrd' 값을 저장
	  $(this).data('srchwrd', srchwrd);
	});

    // 모달창에서 클릭시 srchwrd번호로 삭제
    $('#myModal1').on('click', '#btnRemove', function () {
      var srchwrd = $('#myModal1').data('srchwrd');
      // AJAX로 삭제처리
      $.ajaxUtil.ajaxLoad(
          getUrl('/adm/inform/bbs/deleteSrchWord.do'), 
          {srchWrd: srchwrd}, 
          function(ret) {
              $.ajaxUtil.success(ret, function() {
                  // 목록으로 이동
                  $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
                    // 부모창의 목록재검색 처리 및 팝업닫기
                    $("#btnCancel").click();
                    doReset();
                  });
              });
          }
      );
    });
    
    
    
    function downloadExcel() {   
    	//새 workbook 생성
	    var workbook = new ExcelJS.Workbook();
	    // 새 worksheet 생성
	    var worksheet = workbook.addWorksheet('검색어 관리');
	    
	    var headerStyle = {
	    		  font: { bold: true },
	    		  fill: { type: 'pattern', pattern:'solid', fgColor:{argb:'FFFF00'}},
	    		  border: {
	    	        top: { style: 'thin' },
	    	        left: { style: 'thin' },
	    	        bottom: { style: 'thin' },
	    	        right: { style: 'thin' }
	    		  }
    		};
	    var tableStyle = {
	    	    border: {
	    	        top: { style: 'thin' },
	    	        left: { style: 'thin' },
	    	        bottom: { style: 'thin' },
	    	        right: { style: 'thin' }
	    	    }
	    	};
	    // 헤더 데이터와 스타일 지정
	    worksheet.columns = [
	        {header: '순위'			, key: 'rank'			, width: 20, style:tableStyle},
	        {header: '검색어'			, key: 'srchwrd'		, width: 50, style:tableStyle},
	        {header: '검색수'			, key: 'count'			, width: 50, style:tableStyle}
	    ];
	    $.ajax({
	        url: getUrl('/adm/inform/bbs/downSrchWordExcel.do'),
	        method: 'GET',
	        data: P_SFORM.serializeObject(),
	        success: function(response) {
	        	var rawData = response.rows;
	        	// 데이터를 워크시트에 추가하고 스타일 적용
	        	rawData.forEach((item, index) => {
	        	    var row = worksheet.addRow({
	        	    	rank		: item.rank,
	        	        srchwrd		: item.srchwrd,
	        	        count		: item.count,
	        	    });
	        	    row.eachCell({ includeEmpty: true }, function(cell) {
	        	        cell.style = tableStyle;
	        	    });
	        	});
	        	// 헤더 스타일 적용
	        	worksheet.getRow(1).eachCell({ includeEmpty: true }, function(cell) {
	        	    cell.style = headerStyle;
	        	});
	        	// 엑셀 파일로 저장
	        	workbook.xlsx.writeBuffer().then(function(buffer) {
	        	    saveAs(new Blob([buffer]), '검색어 관리.xlsx');
	        	});
	        },
	        error: function(error) {
	            console.error("Data fetching failed", error);
	        }
	    });
	};
    
    // 엑셀다운로드 버튼 클릭시 이벤트 처리
    $('#btnExcel').bind('click', downloadExcel);
});

