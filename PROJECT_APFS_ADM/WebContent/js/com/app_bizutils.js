/**
*******************************************************************************
*** 파일명    : app_bizutils.js
*** 설명      : 업무용 각종 컨트롤
***
***
*** -----------------------------    Modified Log   ---------------------------
*** ver             date                author                  description
*** ---------------------------------------------------------------------------
*** 1.0         2023.04.20              LSH
*******************************************************************************
**/

$.bizUtils = {};

// 경영체 데이터 업로드 양식 다운로드 (버튼클릭시 이벤트 적용)
$.bizUtils.downloadSprtUld = function() {
	let url = getUrl('/adm/support/biz/downloadSprtUld.do');
	// 다운로드 실행
	$.fileUtil.downloadProgress({
		url:    url,
		params: {},
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