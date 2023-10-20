/**
******************************************************************************************
*** 파일명 : listAccessLog.js
*** 설명글 : 프로그램접속이력 관리 화면 스크립트
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
    var P_GRID		= $('#appGrid'   );		// 그리드 객체
    var P_SFORM		= $('#searchForm');		// 검색폼 객체
	var P_DTBTN		= $('.btn-date');		// 행사기간 날짜 선택 버튼
	
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'sn',
		// 목록 제목
		title:		'접속이력',
		// 검색 URL
		url:		 getUrl('/adm/sys/log/getListAccessLog.do'),
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
		resultText: '<em class="ml-4px">{total}</em>개의 접속이력이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['80px','150px','80px','80px','100px','100px','150px'],
			// 목록칼럼 정의
			columns: [
				{name:'sn'           ,cls:'app-c',	label:'번호', rownumbers: true},
	            {name:'prgrmNm'     ,cls:'app-l',	label:'프로그램명'},
	            {name:'userNo'       ,cls:'app-c',	label:'사용자번호'},
	            {name:'userNm'       ,cls:'app-c',	label:'사용자명'},
	            {name:'cntnDt'       ,cls:'app-c',	label:'접속일시'},
	            {name:'ipAddr'       ,cls:'app-c',	label:'IP주소'},
	            {name:'prgrmUrl'     ,cls:'app-l',	label:'타겟 URL'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
		},
    }).appBbsGrid('init');

    //========================================================//
    // 콤보박스 정의
    //--------------------------------------------------------//

    // 행사(투자설명회) 검색처리
    //--------------------------------------------------------//
    function doSearch() {
		if (CONDITION.yearCheck($.formUtil.getValue(P_SFORM, 'srchStDt'),
		                        $.formUtil.getValue(P_SFORM, 'srchEnDt'),
                                '접속기간 시작일과 종료일의 값이 올바르지 않습니다.')) {
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

	// 검색조건 행사기간 버튼 이벤트
	function setSrchDate() {
		var bgngYmd	= $('#srchStDt');
		var endYmd	= $('#srchEnDt');
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
	
	function downloadExcel() {   
    	//새 workbook 생성
	    var workbook = new ExcelJS.Workbook();
	    // 새 worksheet 생성
	    var worksheet = workbook.addWorksheet('접속이력');
	    
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
	        {header: '번호'				, key: 'sn'				, width: 10, style:tableStyle},
	        {header: '프로그램명'			, key: 'prgrmNm'		, width: 30, style:tableStyle},
	        {header: '사용자번호'			, key: 'userNo'			, width: 30, style:tableStyle},
	        {header: '시스템관리자'			, key: 'userNm'			, width: 30, style:tableStyle},
	        {header: '접속일시'			, key: 'cntnDt'			, width: 30, style:tableStyle},
	        {header: 'IP주소'				, key: 'ipAddr'			, width: 40, style:tableStyle},
	        {header: '타켓URL'			, key: 'prgrmUrl'		, width: 40, style:tableStyle}
	    ];
	    $.ajax({
	        url: getUrl('/adm/sys/log/getListAccessLog.do'),
	        method: 'GET',
	        data: P_SFORM.serializeObject(),
	        success: function(response) {
	        	var rawData = response.rows;
	        	// 데이터를 워크시트에 추가하고 스타일 적용
	        	rawData.forEach((item, index) => {
	        	    var row = worksheet.addRow({
	        	        sn			: index + 1,
	        	        prgrmNm		: item.prgrmNm,
	        	        userNo		: item.userNo,
	        	        userNm		: item.userNm,
	        	        cntnDt		: item.cntnDt,
	        	        ipAddr		: item.ipAddr,
	        	        prgrmUrl	: item.prgrmUrl,
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
	        	    saveAs(new Blob([buffer]), '접속이력.xlsx');
	        	});
	        },
	        error: function(error) {
	            console.error("Data fetching failed", error);
	        }
	    });
	};
    // 검색버튼 클릭시 이벤트 처리
    $('#btnSearch').bind('click', doSearch);
    // 리셋버튼 클릭시 이벤트처리
    $('#btnReset' ).bind('click', doReset);
    // 엑셀다운로드 버튼 클릭시 이벤트 처리
    $('#btnExcel').bind('click', downloadExcel);
	// 행사기간 날짜 선택 버튼 클릭시 이벤트 처리
	P_DTBTN.bind('click', setSrchDate);

    // 검색 실행
    doSearch();

    // 검색어 입력 엔터 이벤트 처리
    bindEnter($('#srchText'), $('#btnSearch'));

});
