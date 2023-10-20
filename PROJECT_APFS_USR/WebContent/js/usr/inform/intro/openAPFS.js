/**
*******************************************************************************
***    명칭: openAPFS.js
***    설명: 플랫폼소개 - 농금원 안내 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.14     J H        작업완료.
*******************************************************************************
**/
$(document).ready(function() {
    // 최상위 레벨 탭을 제어하는 코드
    $("#APFSInfomation, #INVEInfomation").hide();
    $("#APFSInfomation").show();
    $("#firstTab, #secondTab").hide();
    $("#firstTab").show();

    $("#APFSInfo").click(function(event) {
        event.preventDefault();
        $("#INVEDirections1, #INVEDirections2, #INVEDirections3").hide();
        $("#APFSInfomation, #INVEInfomation").hide();
        $("#APFSInfomation").show();
        $("#firstTab").hide();
        $("#secondTab").hide();
        $("#firstTab").show();
        $("#APFSInfo").addClass('active');
        $("#INVEInfo").removeClass('active');
    });

    $("#INVEInfo").click(function(event) {
        event.preventDefault();
        $("#APFSInfomation, #INVEInfomation").hide();
        $("#INVEInfomation").show();
        $("#firstTab").hide();
        $("#secondTab").hide();
        $("#secondTab").show();
        $("#INVEInfo").addClass('active');
        $("#APFSInfo").removeClass('active');
        if ($("#INVEInfo").hasClass("active") && $("#btnINVEDirections").hasClass("active")) {
        	event.preventDefault();
            $("#INVEDirections1, #INVEDirections2, #INVEDirections3").show();
        }
    });

    // 하위 레벨 탭을 제어하는 코드
    $("#overview, #organization, #directions, #outline, #INVEDirections1, #INVEDirections2, #INVEDirections3").hide();
    $("#overview, #outline").show();

    $("#btnOverview").click(function(event) {
        event.preventDefault();
        $("#overview, #organization, #directions").hide();
        $("#overview").show();
    });

    $("#btnOrganization").click(function(event) {
        event.preventDefault();
        $("#overview, #organization, #directions").hide();
        $("#organization").show();
    });

    $("#btnDirections").click(function(event) {
        event.preventDefault();
        $("#overview, #organization, #directions").hide();
        $("#directions").show();
    });

    $("#btnOutline").click(function(event) {
        event.preventDefault();
        $("#outline, #INVEDirections1, #INVEDirections2, #INVEDirections3").hide();
        $("#outline").show();
    });

    $("#btnINVEDirections").click(function(event) {
        event.preventDefault();
        $("#outline, #INVEDirections1, #INVEDirections2, #INVEDirections3").hide();
        $("#INVEDirections1").show();
        $("#INVEDirections2").show();
        $("#INVEDirections3").show();
    });
    
    $("#btnOverview, #btnOrganization, #btnDirections").click(function(event) {
        event.preventDefault();
        
        let target = $(this).attr("href");
        $("#btnOverview, #btnOrganization, #btnDirections").removeClass("active").addClass("bg-gray-t10");
        $(this).addClass("active").removeClass("bg-gray-t10");
    });
    
    $("#btnOutline, #btnINVEDirections").click(function(event) {
    	event.preventDefault();
    	
    	let target = $(this).attr("href");
    	$("#btnOutline, #btnINVEDirections").removeClass("active").addClass("bg-gray-t10");
    	$(this).addClass("active").removeClass("bg-gray-t10");
    });
    
    $("#INVEInfomation").insertAfter($(".sub-content .datum-point").eq(0));
    $("#btnOutline").click(function(){
    	$("#INVEInfomation").insertAfter($(".sub-content .datum-point").eq(0));
    });
    $("#btnINVEDirections").click(function(){
    	$("#INVEInfomation1").appendTo($(".sub-content .datum-point").eq(0));
    	$("#INVEInfomation2").appendTo($(".sub-content .datum-point").eq(0));
    	$("#INVEInfomation3").appendTo($(".sub-content .datum-point").eq(0));
    });
    
	// 농업정책 보험금융원 새창이동
	function doLink() {
			goNewUrl('https://www.apfs.kr/front/user/main.do');
		return false;
	}
	
	function downRogoAi() {
		 let f = document.createElement('form');
		 f.setAttribute('method', 'post');
		 f.setAttribute('action', getUrl('/usr/file/downloadFormFile.do'));
		 let obj;
	        obj = document.createElement('input'		);
	        obj.setAttribute('type' , 'hidden'  		);
	        obj.setAttribute('name' , 'fileType' 		);   // input field의 name을 설정합니다.
	        obj.setAttribute('id'   , 'fileType' 		);   // input field의 id을 설정합니다.
	        obj.setAttribute('value', '03'				);   // input field의 value를 설정합니다.
	     f.appendChild(obj);
        document.body.appendChild(f);
        f.submit();
        return false;
	}
	function downRogoPng() {
		let f = document.createElement('form');
		f.setAttribute('method', 'post');
		f.setAttribute('action', getUrl('/usr/file/downloadFormFile.do'));
		let obj;
		obj = document.createElement('input'		);
		obj.setAttribute('type' , 'hidden'  		);
		obj.setAttribute('name' , 'fileType' 		);   // input field의 name을 설정합니다.
		obj.setAttribute('id'   , 'fileType' 		);   // input field의 id을 설정합니다.
		obj.setAttribute('value', '04'				);   // input field의 value를 설정합니다.
		f.appendChild(obj);
		document.body.appendChild(f);
		f.submit();
		return false;
	}
	function downRogoJpg() {
		 let f = document.createElement('form');
		 f.setAttribute('method', 'post');
		 f.setAttribute('action', getUrl('/usr/file/downloadFormFile.do'));
		 let obj;
	        obj = document.createElement('input'		);
	        obj.setAttribute('type' , 'hidden'  		);
	        obj.setAttribute('name' , 'fileType' 		);   // input field의 name을 설정합니다.
	        obj.setAttribute('id'   , 'fileType' 		);   // input field의 id을 설정합니다.
	        obj.setAttribute('value', '05'				);   // input field의 value를 설정합니다.
	     f.appendChild(obj);
        document.body.appendChild(f);
        f.submit();
        return false;
	}
    
	// 농업정책 보험금융원 버튼 클릭
	$('#btnLink'				).bind('click', doLink);
	$('#btnRogoAi'		).bind('click', downRogoAi);
	$('#btnRogoPng'		).bind('click', downRogoPng);
	$('#btnRogoJpg'		).bind('click', downRogoJpg);
});
