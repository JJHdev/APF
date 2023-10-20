/**
******************************************************************************************
*** 파일명    : comm_session.js
*** 설명      : 세션시간 관리 자바스크립트
***				Egov 공통 참조
*	관련소스  : comm_session.js, 
*				servlet-interceptor.xml,
*				SessionInterceptor.java,
*				LoginController.java
*				header.jsp,
*				style.css
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2022.10.04              ntarget
******************************************************************************************
**/

var objLeftTime;
var objClickInfo;
var latestTime;
var expireTime;
var timer;
var timeInterval 		= 1000; // 1초 간격 호출
var firstLocalTime 		= 0;
var elapsedLocalTime 	= 0;
var stateExpiredTime 	= false;
var logoutUrl 			= getUrl("/com/common/logout.do");
var refreshUrl			= getUrl('/com/common/refreshSessionTimeout.do');

$(document).ready(function () {
	// 초기화
	init();
	
	// 시간연장 버튼 클릭
	$("#clickInfo").click(function() {
		reqTimeAjax();
	});

	// 로그아웃 버튼 클릭
    $("#logoutBtn").click(function() {
    	doLogout();
    });
});

function init() {
	objLeftTime = $('#leftTimeInfo');
	
	if (objLeftTime == null) {
		console.log("'leftTimeInfo' ID is not exist!");
		return;
	}
	
	if (!isLogin) {
		console.log("not login.");
		return;
	}

	objClickInfo = $('#clickInfo');

	latestTime = $.getCookie(COOK_LATEST_TIME);
	expireTime = $.getCookie(COOK_EXPIRE_TIME);
	
	//elapsedTime = 0;
	firstLocalTime = (new Date()).getTime();
	showRemaining();
	
	timer = setInterval(showRemaining, timeInterval); // 1초 간격 호출		
}

function showRemaining() {
	elapsedLocalTime = (new Date()).getTime() - firstLocalTime;
	
	var timeRemaining = expireTime - latestTime - elapsedLocalTime;
		
	if ( timeRemaining < timeInterval ) {
		clearInterval(timer);
		
		objLeftTime.html("00:00:00");
		objClickInfo.text('시간만료'); //시간만료
		stateExpiredTime = true;
		
		//alert('세션 유효시간이 초과되어 자동 로그아웃 되었습니다.');
		$.commMsg.alert('세션 유효시간이 초과되어 자동 로그아웃 되었습니다.', doLogout);
		
		// 로그인 남은시간 영역
		//$("#sessionInfo").hide();
		//$(location).attr('href',logoutUrl);

		return;
	}
	
	var timeHour 	= Math.floor(timeRemaining/1000/60 / 60); 
	var timeMin 	= Math.floor((timeRemaining/1000/60) % 60);
	var timeSec 	= Math.floor((timeRemaining/1000) % 60);

	objLeftTime.html($.lpad(timeHour,2,'0') +":"+ $.lpad(timeMin,2,'0') +":"+ $.lpad(timeSec,2,'0'));
}

/* 시간연장 */
function reqTimeAjax() {
  	if (stateExpiredTime==true) {
  		alert('시간을 연장 할 수 없습니다.');
  		return;
  	}

  	var result  = $.ajaxUtil.ajaxDefault(refreshUrl, {});
  	
  	if (result) {
        //서버로부터 정상적으로 응답이 왔을 때 실행
    	latestTime = $.getCookie(COOK_LATEST_TIME);
    	expireTime = $.getCookie(COOK_EXPIRE_TIME);
    	//console.log("latestServerTime = "+latestTime);
    	//console.log("expireSessionTime = "+expireTime);

    	init();
  	} else {
  		//서버로부터 응답이 정상적으로 처리되지 못햇을 때 실행
    	alert("err : "+ result);
  	}

	return false;
}

/* 로그 아웃 */
function doLogout() {
	$("#sessionInfo").hide();	
	$(location).attr('href',logoutUrl);
}