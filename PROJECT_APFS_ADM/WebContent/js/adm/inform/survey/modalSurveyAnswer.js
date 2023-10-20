/**
*******************************************************************************
***    명칭: modalSurveyAnswer.js
***    설명: 설문관리 - 설문상세 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.08.04    KSU        First Coding.
*******************************************************************************
**/
$(function() {
	var srvyList = $("#srvyList").val();
	srvyList = srvyList.replace(' ', '');
	var chartDataArr = JSON.parse(srvyList);
	if(!!chartDataArr){
		gridChart(chartDataArr);
	}
});

function gridChart(data){
	var classArr = [];
	$('.chartDiv').each(function(index,item){
		classArr.push($(this).attr('class').replace('chartDiv ', ''));
	});
	
	var obj = {};
	data.forEach(function(e, i) {
		if (e['artclCn'] != '0'){
			var className = e['srvyNo'] + '_' + e['qitemNo'];
		    if (obj[className])
		        obj[className].push(e['count']);
		    else
		        obj[className] = [e['count']];
		}
	});
	
	for(var i=0; i<Object.keys(obj).length; i++){
		if(!!classArr[i]){
			var words = classArr[i].split('_');
			var label = $(".label_"+words[1]).text();
			var dataset = {label: label, data: obj[classArr[i]]}
			var labels = [];
			
			data.forEach(function(e, i) {
				if (e['artclCn'] != '0' && e['qitemNo'] == words[1]){
					labels.push(e['artclCn']);
				}
			});
			var datasets={datasets:[dataset], labels:labels}
			
			const ctx = document.getElementById('chart_'+classArr[i]).getContext('2d');
			const config = {
				type: 'pie',
				data: datasets,
				options: {
					responsive: true,
					plugins: {
						legend: {
							position: 'right',
					    }
					}
				}
			}
			var pieChart = new Chart(ctx, config);
		}
	}
}
