/**
*******************************************************************************
***    명칭: openPbanc.js
***    설명: 마이페이지 - 사업공고등록 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.21    J H        작업완료.
*******************************************************************************
**/

$(function() {
	
	var P_FILE_BOX			= $('#attachFile');   // 첨부파일(수정, 등록) 컨트롤 객체
	var P_BIZ_PBANC_NO		= $('#bizPbancNo').val();			// 게시글 키정보
	var bizTrgtNm 			= $("#bizTrgtNm").text();
	var bizTrgtFntnPdNm 	= $("#bizTrgtFntnPdNm").text();
	
	if(bizTrgtNm.includes('예비창업자') && !bizTrgtFntnPdNm.includes('예비창업자')){
		$("#bizTrgtFntnPdNm").prepend('예비창업자,');
	}
	// 신청페이지로 새창이동
	function doLink() {
		let url = $(this).data('value');
		if (url) {
		    if (url) {
		        if (!url.startsWith('http://') && !url.startsWith('https://')) {
		            url = 'https://' + url;
		        }
		        goNewUrl(url);
		    }
		}
		return false;
	}
		
	// 목록페이지로 이동
	function doList() {
		$.formUtil.submitForm( getUrl('/usr/mypage/pbanc/openPbanc.do'), {
			formId: 'searchForm'
		});
		return false;
	}
	
	// 목록페이지로 이동
	function doModify() {
		$.formUtil.submitForm( getUrl('/usr/mypage/pbanc/formPbanc.do'), {
			formId: 'selectForm'
		});
		return false;
	}
	
	 // 등록폼 선택된 날짜에서 요일 추기하기 가져오기
    function updateDayOfWeek(colum) {
    	var dateValue = $('#'+colum).val(); 
    	if(dateValue.length != 7){
    		var days = ["(일)", "(월)", "(화)", "(수)", "(목)", "(금)", "(토)"];
    		var Stdate = new Date(dateValue); // Date 객체를 생성합니다
    		var dayOfWeek = days[Stdate.getDay()]; // 요일을 가져옵니다
    		document.getElementById("F_"+colum).innerText = " " + dayOfWeek + " "; // 날짜와 요일을 출력합니다
    	}else if (dateValue.length == 7){
    		document.getElementById("F_"+colum).innerText =  "월"; // 날짜와 요일을 출력합니다
    	}
    }
    
    function formatTime(colum) {
        var dateValue = $('#'+colum).val(); 
        if (dateValue != '0000') {
            var formattedTime = dateValue.substring(0, 2) + ":" + dateValue.substring(2, 4);  
            $('#F_'+colum).text(formattedTime); 
        }
    }
    
	//========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
    P_FILE_BOX.appBizFile({
        label: false,
        // 처리모드
        mode: MODE.VIEW,
        initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.PBANC,
			docuNo  : P_BIZ_PBANC_NO,
			docuSeq : '',
			fileYn  : 'Y',
        }
    }).appBizFile('init');
    
   	 // textarea에서 값 양식 적용 가져옵니다
    var colum = ["sprtCn", "sbmsnDcmntCn", "aplyExclTrgtCn", "aplyQlfcCn", "rcptMthdCn"];
    colum.forEach(function(id) {
        // textarea에서 값을 가져옵니다
        var text = $('#' + id).val();
        // 줄바꿈 문자를 <br> 태그로 바꿉니다
        var formattedText = text.replace(/\n/g, '<br>');
        // 값을 <span> 요소에 넣습니다
        $('#t_' + id).html(formattedText);
    });
    
   	 var text = $('#sprtCn').val();
   	 // 줄바꿈 문자를 <br> 태그로 바꿉니다
   	 var formattedText = text.replace(/\n/g, '<br>');
   	 // 값을 <span> 요소에 넣습니다
   	 $('#t_sprtCn').html(formattedText);
    
    if($('#rcptSeCd').val() != CODE.PBANC_STUS.OPEN){
    	formatTime('rcptBgngTm');
    	formatTime('rcptEndTm');
    	
    	updateDayOfWeek('rcptBgngDt');
    	updateDayOfWeek('rcptEndDt');
    }
 // 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
        $.commMsg.confirm("정말로 사업공고를 삭제하시겠습니까?", function() {
            // AJAX로 삭제처리
            $.ajaxUtil.ajaxLoad(
        		getUrl('/usr/mypage/pbanc/savePbanc.do'),
                {bizPbancNo   	: P_BIZ_PBANC_NO,
                 mode    		: MODE.REMOVE}, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록으로 이동
                        $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
							// 부모창의 목록재검색 처리 및 팝업닫기
                        	doList();
						});
                    });
                }
            );
        });
        return false;
    }
	
	// 신청하기 버튼 클릭
	$('#btnLink').bind('click', doLink);
	// 목록으로 버튼 클릭
	$('#btnList').bind('click', doList);
	// 수정하기 버튼 클릭
	$('#btnModify').bind('click', doModify);
	// 삭제하기 버튼 클릭
	$('#btnRemove').bind('click', doRemove);
	
});

