/**
******************************************************************************************
*** 파일명    : main.js
*** 설명      : 메인 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.03.14              LSH
***	1.1			2023.07.07				KYW	
******************************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let DATEPICKER = $('.datepickerMonth-input'); // DATEPICKER
	
	// 그리드 
    //--------------------------------------------------------//
	// 목록 GRID 정의 (appBbsGrid : app_bbsgrid.js)
    //========================================================//
    // 경영체 회원가입 현황
    //--------------------------------------------------------//
    $('#appGridEnt').appBbsGrid({
		// 목록 KEY필드
		idField:    'bzentyNo',
		// 목록 제목
		title:      '경영체 회원가입 현황',
		// 검색 URL
		url:         getUrl('/adm/invest/ent/getListEnt.do'),
		// 정렬 컬럼 조건
		params: {
			useSttsCd: 0
	    },
		//목록 검색 페이징 객체
		pagination: { display: 5},
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '조회된 {title}내역이 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','250px','250px','100px','70px'],
			// 목록칼럼 정의
			columns: [
				{name:'bzentyNm'     ,cls:'app-c',label:'경영체명'},
	            {name:'invtFldNm'    ,cls:'app-c',label:'투자분야', 
					formatter:function(v){ if(v == '') return '-'; return v;}},
	            {name:'bizFldText' 	 ,cls:'app-c',label:'사업분야',
					formatter:function(v){ if(v == '') return '-'; return v;}},
	            {name:'invtHopeAmt'  ,cls:'app-c',label:'투자희망금액', formatter: $.formatUtil.toFundScale},
	            {name:'useSttsCdNm'  ,cls:'app-c',label:'상태', 
					formatter: function(v,o) {
						if(v== '승인대기') {
							return '<div class="btn-black-ouline bs-s">' + v + '</div>';
						} else {
							return '<div class="btn-primary-ouline bs-s">' + v + '완료</div>';
						}
							
					}},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
			// 목록형인 경우 헤더 스타일시트
			headCls: "bs-1 ts-m",
			// 목록형인 경우 헤더 th 스타일시트
			headthCls: "",
		},
		//최초로딩 여부
		autoload: true,
    }).appBbsGrid('init');
	
	// 자료실 인기 콘텐츠
    //--------------------------------------------------------//
    $('#appGridBbs').appBbsGrid({
		// 목록 KEY필드
		idField:    'pstNo',
		// 목록 제목
		title:      '자료실 인기 콘텐츠',
		// 검색 URL
		url:         getUrl('/adm/inform/bbs/getListBbs.do'),
		// 정렬 컬럼 조건
		params: {
			bbsSeCd	  : 'B30',
			ordrField : 'inq_cnt'
	    },
		//목록 검색 페이징 객체
		pagination: { display: 5},
		// 검색결과 표시 객체
		result:     '#appGridResult',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '조회된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['100px','200px','100px','100px','50px'],
			// 목록칼럼 정의
			columns: [
				{name:'pstClsfNm'    ,cls:'app-c',label:'분류'},
	            {name:'pstTtl'       ,cls:'app-l',label:'제목'},
	            {name:'rgtrNm'       ,cls:'app-c',label:'작성자'},
	            {name:'regDate'      ,cls:'app-c',label:'작성일'},
	            {name:'inqCnt'       ,cls:'app-c',label:'조회수', 
					formatter: function(v,o) {
						return '<span class="d-inline-flex align-items-center fw-300 fs-13px"><i class="icon-eye "></i>'+v+'</span>';
					}},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1",
			// 목록형인 경우 헤더 스타일시트
			headCls: "bs-1 ts-m",
			// 목록형인 경우 헤더 th 스타일시트
			headthCls: "",
		},
		//최초로딩 여부
		autoload: true,
    }).appBbsGrid('init');
	
	
	
    // 사용자 유형별 접속 통계 datepicker 
    //--------------------------------------------------------//
	DATEPICKER.datepicker(OPTIONS.DATEPICKER_MONTH);
	DATEPICKER.datepicker('setDate', 'today');
	
	// 차트
    //--------------------------------------------------------//
	// 차트 객체 모음
	let _charts = {};

	// 차트생성
	let _drawChart = function(id, args) {
		
		// Chart Canvas Element
		let element = document.getElementById(id);

		// DataLabels 플러그인 Register
		Chart.register(ChartDataLabels);
		
		// 이전 Chart 객체 Destroy
		if (_charts[id])
			_charts[id].destroy();

		// Chartjs 의 Chart 생성
		_charts[id] = new Chart(element, $.extend(true, {
		    options: {
		    	plugins: {
			        legend: {
			        	display: true,
						position: 'bottom'
			        },
					datalabels: {
	    				color:   'black',
	    				display: true,
	    				font: {size:14, weight: 'bold'},
						anchor: 'end',
	                    align: 'start',
						offset: -20,
						formatter: function(value, context) {
							if (context.datasetIndex == 3)
								return value;
							return '';
						}
					}
				},
				interaction: {
					intersect: false,
					mode: 'index',
				},
				responsive: true,
			}
		}, args));
	};
	
	getData();
	
	$('#srchAcsYm').bind('change', getData);
	$('.roleId').bind('change', getData);
	
	function getData() {
		var obj = $('#searchForm').serializeObject();
		$.ajaxUtil.ajaxLoad(getUrl('/adm/main/getListAccess.do'), obj, function(ret){
			let data = ret.Data;
			setChart(data);
		});
	};
	
	function setChart(data) {
		let labels = [];
		let totalList = [];
		let dataList = [];
		
		data.forEach(function(e, i) {
			labels.push(e.day + '일');
			totalList.push(e.total);
			dataList.push(e.dataRole);
		});
		
		_drawChart('appAreaChart', {
		    type: 'line',
			// TODO 데이터 부분을 AJAX로 조회해서 처리해야함
			data: {
				labels:   labels,
				datasets: [{
					label: '전체', 
					order: 1, 
					data : totalList,
					borderColor: '#00AAFF',
					//fill: true	
				},{
					label: '유형별', 
					order: 2, 
					data : dataList,
					borderColor: '#1F7BF4',
					//fill: true	
				}]
			}
		});
	};
});

