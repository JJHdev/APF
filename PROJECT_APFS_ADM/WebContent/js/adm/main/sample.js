/**
******************************************************************************************
*** 파일명    : sample.js
*** 설명      : Chartjs 사용 샘플
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.07.06              LSH
******************************************************************************************
**/
$(function() {
	
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
				responsive: true
			}
		}, args));
	};
	// BAR CHART 샘플
	_drawChart('appBarChart', {
	    type: 'bar',

		// TODO 데이터 부분을 AJAX로 조회해서 처리해야함
		data: {
			labels:   ['2018', '2019', '2020', '2021'],
			datasets: [{
				//backgroundColor: '#FFCE00',
				label: '자본총계', 
				order: 2, 
				data : [9755,9598,10665,11572],
			},{
				//backgroundColor: '#FD4703',
				label: '부채총계', 
				order: 1, 
				data : [14470,15325,16298,10290],
			},{
				//backgroundColor: '#FFE63B',
				label: '자산총계', 
				order: 0, 
				data : [24226,24924,26964,21862],
			}]
		}
	});

	// AREA CHART 샘플
	_drawChart('appAreaChart', {
	    type: 'line',

		// TODO 데이터 부분을 AJAX로 조회해서 처리해야함
		data: {
			labels:   ['2018', '2019', '2020', '2021'],
			datasets: [{
				//backgroundColor: '#FFCE00',
				label: '자본총계', 
				order: 2, 
				data : [9755,9598,10665,11572],
				fill: true
			},{
				//backgroundColor: '#FD4703',
				label: '부채총계', 
				order: 1, 
				data : [14470,15325,16298,10290],
				fill: true
			},{
				//backgroundColor: '#FFE63B',
				label: '자산총계', 
				order: 0, 
				data : [24226,24924,26964,21862],
				fill: true
			}]
		}
	});
	// 메일보내기
	$('#btnMail').bind('click', function() {
        $.ajaxUtil.ajaxLoad(
            getUrl('/com/common/sendEmail.do'), 
            $('#mailForm').serializeObject(), 
            function() {
				$.commMsg.alert('성공적으로 전송되었습니다.');
            }
        );
         return false;
	});
	
});
