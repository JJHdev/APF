/**
******************************************************************************************
*** 파일명 : modalSprtUldForm.js
*** 설명글 : 운영관리-경영체 데이터 업로드 모달 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.07.13               LHB                      신규 작성
******************************************************************************************
**/
$(function() {
	
	let P_ULDNO	= $('#uldNo').val();// 업로드 번호
	let P_GRID	= $('#appGrid2');	// 지원사업이력 그리드
	let P_VFORM	= $('#viewForm');	// 지원사업이력 목록 폼
	
	let P_FILE	= $('#fileNm');		// 파일명 객체
	
	//========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
    P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'sn',
		// 목록 제목
		title:		'지원사업이력',
		// 검색 URL
		url:		getUrl('/adm/support/biz/getListSprtBiz.do'),
        // 조회 파라미터 전달
		queryParams: {
	    },
		//목록 검색 페이징 객체
		pagination: { display: 10 },
		// 페이지 표시 객체
		paging:     '#appGridPagination2',
		// 검색결과 표시 객체
		result:     '#appGridResult2',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 지원사업이력이 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['40px','100px','100px','180px','50px', '100px', '100px', '100px', '100px', '100px', '100px', '100px', '100px', '40px', '100px', '80px'],
			// 목록칼럼 정의
			columns: [
				{name:'sn'			, cls:'app-c', label:'',
					formatter:function(v, o) {
						let i = $('<input type="checkbox" name="sn" value="'+v+'">');
						if(o['delYn'] == 'Y')
							i.prop('disabled', true);
						return i;
					}
				},
				//{rownumbers:true	,cls:'app-c',label:'번호',},
	            {name:'brno'		,cls:'app-c',label:'사업자번호'},
				{name:'bzentyNm'	,cls:'app-l',label:'경영체명'},
				{name:'bizNm'		,cls:'app-l',label:'지원사업명'},
				{name:'bizYr'		,cls:'app-c',label:'연도'},
				{name:'artclNm1'	,cls:'app-l',label:'항목명1'},
				{name:'artclCn1'	,cls:'app-l',label:'항목내용1'},
				{name:'artclNm2'	,cls:'app-l',label:'항목명2'},
				{name:'artclCn2'	,cls:'app-l',label:'항목내용2'},
				{name:'artclNm3'	,cls:'app-l',label:'항목명3'},
				{name:'artclCn3'	,cls:'app-l',label:'항목내용3'},
				{name:'artclNm4'	,cls:'app-l',label:'항목명4'},
				{name:'artclCn4'	,cls:'app-l',label:'항목내용4'},
				{name:'directInptYn',cls:'app-c',label:'강제업로드'},
				{name:'mdfrNoNm'	,cls:'app-c',label:'삭제자'},
				{name:'mdfDate'		,cls:'app-c',label:'삭제일자'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1 overtable",
		},
		callback: function(elem, rows, params) {
			rows.forEach(function(e, i) {
				if(e['delYn'] == 'Y') 
					elem.find('tbody tr').eq(i).css('background-color', 'var(--light-gray)');
			})
		}
	}).appBbsGrid('init');
	
	// 지원사업이력 목록 검색처리
    //--------------------------------------------------------//
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_VFORM.serializeObject();
        // 검색폼 그리드 검색
        P_GRID.appBbsGrid('search', obj);
        return false;
    }

	// 선택한 데이터 삭제하기
    //--------------------------------------------------------//
    function doDelete() {
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
                getUrl('/adm/support/biz/saveSprtBiz.do'), 
                {arrSnStr: sn.join(',')}, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록 재호출
						$.commMsg.alert('성공적으로 삭제되었습니다.');
                        doSearch();
                    });
                }
            );
        });
        return false;
    }

	// 첨부파일 다운로드
    //--------------------------------------------------------//
    function doDownload() {
		$.bizUtils.downloadSprtBizFile(P_ULDNO);
	}
	
	// 파일 다운로드
	$.bizUtils.downloadSprtBizFile = function(uldNo) {
		let url		= getUrl('/adm/support/biz/downloadSprtUldFile.do');
	
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

	$('#btnDelete').bind('click', doDelete);
	P_FILE.bind('click',          doDownload);

	doSearch();
	
});