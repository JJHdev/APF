/**
*******************************************************************************
***    명칭: openInvestGuide.js
***    설명: 정보서비스 - 투자유치가이드 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.08.02      JH        작업완료.
*******************************************************************************
**/
$(function() {
    // 최상위 레벨 탭을 제어하는 코드
    $("#APFS_FAFF_Data, #APFSCrowdfunding_Data").hide();
    $("#APFS_FAFF_Data").show();
    $("#firstTab, #secondTab").hide();
    $("#firstTab").show();

    $("#APFS_FAFF").click(function(event) {
        event.preventDefault();
        $("#APFS_FAFF_Data, #APFSCrowdfunding_Data").hide();
        $("#firstTab").hide();
        $("#secondTab").hide();
        $("#firstTab").show();
        $("#APFS_FAFF_Data").show();
        $("#APFS_FAFF").addClass('active');
        $("#APFSCrowdfunding").removeClass('active');
    });

    $("#APFSCrowdfunding").click(function(event) {
        event.preventDefault();
        $("#APFS_FAFF_Data, #APFSCrowdfunding_Data").hide();
        $("#firstTab").hide();
        $("#secondTab").hide();
        $("#secondTab").show();
        $("#APFSCrowdfunding_Data").show();
        $("#APFSCrowdfunding").addClass('active');
        $("#APFS_FAFF").removeClass('active');
    });

    // 하위 레벨 탭을 제어하는 코드
    $("#introduceFAFF, #investment, #investmentTarget, #supportProjectBA, #introduceCrowd, #supportProject, #application, #fundingDedicated").hide();
    $("#introduceFAFF, #introduceCrowd").show();
    
    $("#btnIntroduceFAFF").click(function(event) {
        event.preventDefault();
        $("#introduceFAFF, #investment, #investmentTarget, #supportProjectBA").hide();
        $("#introduceFAFF").show();
    });

    $("#btnInvestment").click(function(event) {
        event.preventDefault();
        $("#introduceFAFF, #investment, #investmentTarget, #supportProjectBA").hide();
        $("#investment").show();
    });

    $("#btnInvestmentTarget").click(function(event) {
        event.preventDefault();
        $("#introduceFAFF, #investment, #investmentTarget, #supportProjectBA").hide();
        $("#investmentTarget").show();
    });

    $("#btnSupportProjectBA").click(function(event) {
        event.preventDefault();
        $("#introduceFAFF, #investment, #investmentTarget, #supportProjectBA").hide();
        $("#supportProjectBA").show();
    });
    
    $("#btnIntroduceCrowd").click(function(event) {
        event.preventDefault();
        $("#introduceCrowd, #supportProject").hide();
        $("#introduceCrowd").show();
    });

    $("#btnSupportProject").click(function(event) {
        event.preventDefault();
        $("#introduceCrowd, #supportProject").hide();
        $("#supportProject").show();
    });

    $("#btnIntroduceFAFF, #btnInvestment, #btnInvestmentTarget, #btnSupportProjectBA").click(function(event) {
        event.preventDefault();
        
        let target = $(this).attr("href");
        $("#btnIntroduceFAFF, #btnInvestment, #btnInvestmentTarget, #btnSupportProjectBA").removeClass("active").addClass("bg-gray-t10");
        $(this).addClass("active").removeClass("bg-gray-t10");
    });
    
    $("#btnIntroduceCrowd, #btnSupportProject").click(function(event) {
    	event.preventDefault();
    	
    	let target = $(this).attr("href");
    	$("#btnIntroduceCrowd, #btnSupportProject").removeClass("active").addClass("bg-gray-t10");
    	$(this).addClass("active").removeClass("bg-gray-t10");
    });
    
    $("#APFS_FAFF_Data").insertAfter($(".sub-content .datum-point").eq(0));
    $("#btnIntroduceFAFF").click(function(){
    	$("#APFS_FAFF_Data").insertAfter($(".sub-content .datum-point").eq(0));
    });
    
    $("#btnInvestment, #btnInvestmentTarget, #btnSupportProjectBA").click(function(){
    	$("#APFS_FAFF_Data").appendTo($(".sub-content .datum-point").eq(0));
    });
    
	// VC직접접촉 이동
    $("#btnIntroduceFAFF2").click(function(event) {
        event.preventDefault();
        $("#introduceFAFF, #investment, #investmentTarget, #supportProjectBA").hide();
        $("#introduceFAFF").show();
        $("#btnIntroduceFAFF, #btnInvestment, #btnInvestmentTarget, #btnSupportProjectBA").removeClass("active").addClass("bg-gray-t10");
        $("#btnIntroduceFAFF").addClass("active").removeClass("bg-gray-t10");
        scrollToElement('ContactVCPage');
    });
    
	// 신청페이지로 새창이동
	function goApplication() {
		$.formUtil.submitForm(
			getUrl('/usr/support/support/openSprt.do'), {
				params: {
					sprtSeCd: 'SC',
					stepNo: 1
				}
			}
		);
		return false;
	}
	// 투자유치 전 새창이동
	function goSprtAplyBefore() {
		$.formUtil.submitForm(
			getUrl('/usr/support/support/openSprt.do'), {
				params: {
					sprtSeCd: 'SB',
					stepNo: 1
				}
			}
		);
		return false;
	}
	// 투자유치 후 이동
	function goSprtAplyAfter() {
		$.formUtil.submitForm(
			getUrl('/usr/support/support/openSprt.do'), {
				params: {
					sprtSeCd: 'SA',
					stepNo: 1
				}
			}
		);
		return false;
	}
	
	function scrollToElement(elementId) {
	    var element = document.getElementById(elementId);
	    if (element) {
	        element.scrollIntoView({ behavior: "smooth" });
	    }
	}
	
	// 펀딩전용관 바로가기로 새창이동
	function goFundingDedicated() {
		goNewUrl('http://agrocrowd.kr/');
		return false;
	}
	
	// 관련법령 이동 
	function goStatuteFirst() {
		goNewUrl('https://www.law.go.kr/법령/농림수산식품투자조합결성및운용에관한법률');
		return false;
	}
	function goStatuteSecond() {
		goNewUrl('https://www.law.go.kr/법령/농림수산식품투자조합결성및운용에관한법률시행령');
		return false;
	}
	function goStatuteThird() {
		goNewUrl('https://www.law.go.kr/법령/농림수산식품투자조합결성및운용에관한법률시행규칙');
		return false;
	}
	function goStatuteFourth() {
		goNewUrl('https://www.law.go.kr/행정규칙/(농림축산식품부)%20농림수산식품투자모태조합%20및%20농림수산식품투자조합의%20운용%20등에%20관한%20기본규정');
		return false;
	}
	function goStatuteFifth() {
		goNewUrl('https://www.law.go.kr/행정규칙/그밖의농림수산식품산업관련업무에종사하는자');
		return false;
	}
	function goStatuteSixth() {
		goNewUrl('https://www.law.go.kr/행정규칙/국가과학기술표준분류체계');
		return false;
	}
	function goStatuteSeventh() {
		goNewUrl('https://www.law.go.kr/법령/조세특례제한법');
		return false;
	}
	function goStatuteEighth() {
		goNewUrl('https://www.law.go.kr/LSW/lsInfoP.do?lsId=013679&ancYnChk=0#0000');
		return false;
	}
	function goChecktInfo() {
		goNewUrl('https://www.youtube.com/watch?v=D1f-lsoMlPw');
		return false;
	}
	function downloadOperatingStandards() {
		 let f = document.createElement('form');
		 f.setAttribute('method', 'post');
		 f.setAttribute('action', getUrl('/usr/file/downloadFormFile.do'));
		 let obj;
	        obj = document.createElement('input'		);
	        obj.setAttribute('type' , 'hidden'  		);
	        obj.setAttribute('name' , 'fileType' 		);   // input field의 name을 설정합니다.
	        obj.setAttribute('id'   , 'fileType' 		);   // input field의 id을 설정합니다.
	        obj.setAttribute('value', '01'				);   // input field의 value를 설정합니다.
	     f.appendChild(obj);
         document.body.appendChild(f);
         f.submit();
         return false;
	}
	function downloadBusinPlanStandards() {
		 let f = document.createElement('form');
		 f.setAttribute('method', 'post');
		 f.setAttribute('action', getUrl('/usr/file/downloadFormFile.do'));
		 let obj;
	        obj = document.createElement('input'		);
	        obj.setAttribute('type' , 'hidden'  		);
	        obj.setAttribute('name' , 'fileType' 		);   // input field의 name을 설정합니다.
	        obj.setAttribute('id'   , 'fileType' 		);   // input field의 id을 설정합니다.
	        obj.setAttribute('value', '02'				);   // input field의 value를 설정합니다.
	     f.appendChild(obj);
        document.body.appendChild(f);
        f.submit();
        return false;
	}
	
	
	function goAplySe(prgrmNo){
		const attr = ['sprtSeCd','stepNo','prgrmNo'];
		const value = ['SC','2',prgrmNo];
		
		let f = document.createElement('form');
		f.setAttribute('method', 'post');
		f.setAttribute('action', getUrl('/usr/support/support/formSprt.do'));
		
		for(let i=0; i<attr.length; i++){
			let obj;
			obj = document.createElement('input');
			obj.setAttribute('type' , 'hidden'  );
			obj.setAttribute('id'   ,  attr[i] 	);   // input field의 id을 설정합니다.
			obj.setAttribute('name' ,  attr[i]	);
			obj.setAttribute('value',  value[i]	);   // input field의 value를 설정합니다.
			f.appendChild(obj					);
		}
		document.body.appendChild(f);
		f.submit();
		return false;
	}
	
	function goAplySePage(type) {
		if(type === CODE.PRGRM_NO.COATING){
			goAplySe(type);
		}else if(type === CODE.PRGRM_NO.CONSULT){
			goAplySe(type);
		}else if(type === CODE.PRGRM_NO.FEE){
			goAplySe(type);
		}
	}
	
	// 신청하기 버튼 클릭
	$('#btnApplication'			).bind('click', goApplication);
	// 펀딩전용관 바로가기 버튼 클릭
	$('#btnFundingDedicated'	).bind('click', goFundingDedicated);
	// 투자유치 전 지원 바로가기 버튼 클릭
	$('#btnSprtAplyBefore'		).bind('click', goSprtAplyBefore);
	$('#btnSprtAplyVCBefore'	).bind('click', goSprtAplyBefore);
	// 투자유치 후 지원 바로가기 버튼 클릭
	$('#btnSprtAplyAfter'		).bind('click', goSprtAplyAfter);
	// 관련법령 이동 버튼 클릭
	$('#btnStatuteFirst'		).bind('click', goStatuteFirst);
	$('#btnStatuteSecond'		).bind('click', goStatuteSecond);
	$('#btnStatuteThird'		).bind('click', goStatuteThird);
	$('#btnStatuteFourth'		).bind('click', goStatuteFourth);
	$('#btnStatuteFifth'		).bind('click', goStatuteFifth);
	$('#btnStatuteSixth'		).bind('click', goStatuteSixth);
	$('#btnStatuteSeventh'		).bind('click', goStatuteSeventh);
	$('#btnStatuteEighth'		).bind('click', goStatuteEighth);
	$('#btnChecktInfo'			).bind('click', goChecktInfo);
	// 운영기준 다운로드
	$('#btnOperatingStandards'	).bind('click', downloadOperatingStandards);
	$('#btnBusinPlanStandards'	).bind('click', downloadBusinPlanStandards);
	$('#btnPrgrmCoach'			).bind('click', function() {
		goAplySePage(CODE.PRGRM_NO.COATING);
	});
	$('#btnPrgrmConsult'		).bind('click', function() {
		goAplySePage(CODE.PRGRM_NO.CONSULT);
	});
	$('#btnPrgrmFee'			).bind('click', function() {
		goAplySePage(CODE.PRGRM_NO.FEE);
	});
});
