/**
*******************************************************************************
***    명칭: openAgency.js
***    설명: 플랫폼소개 - 유관기관 안내 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.14     J H        작업완료.
*******************************************************************************
**/
$(function() {
    $("#AFInfo, #RDAInfo, #IPETInfo").hide();
    $("#AFInfo").show();
    $("#AF").click(function(event) {
        event.preventDefault();
        $("#AFInfo").show();
        $("#RDAInfo").hide();
        $("#IPETInfo").hide();
        $("#AF").addClass('active');
        $("#RDA").removeClass('active');
        $("#IPET").removeClass('active');
    });
    $("#RDA").click(function(event) {
    	event.preventDefault();
    	$("#AFInfo").hide();
    	$("#RDAInfo").show();
    	$("#IPETInfo").hide();
    	$("#AF").removeClass('active');
    	$("#RDA").addClass('active');
    	$("#IPET").removeClass('active');
    });
    $("#IPET").click(function(event) {
    	event.preventDefault();
    	$("#AFInfo").hide();
    	$("#RDAInfo").hide();
    	$("#IPETInfo").show();
    	$("#AF").removeClass('active');
    	$("#RDA").removeClass('active');
    	$("#IPET").addClass('active');
    });
    
	function goAFInfo() {
		goNewUrl('https://www.ipet.re.kr/');
		return false;
	}
	function goRDAInfo() {
		goNewUrl('https://www.foodpolis.kr/index.php');
		return false;
	}
	function goIPETInfo() {
		goNewUrl('https://www.koat.or.kr/main.do');
		return false;
	}
    
	$('#btnGoAFInfo').bind('click', goAFInfo);
	$('#btnGoRDAInfo').bind('click', goRDAInfo);
	$('#btnGoIPETInfo').bind('click', goIPETInfo);
});
