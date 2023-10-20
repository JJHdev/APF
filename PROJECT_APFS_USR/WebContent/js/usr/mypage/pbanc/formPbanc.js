/**
*******************************************************************************
***    명칭: openPbanc.js
***    설명: 마이페이지 - 사업공고등록 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
*******************************************************************************
**/

$(function() {
	var P_FILE_BOX		= $('#attachFile');   // 첨부파일(수정, 등록) 컨트롤 객체
	var P_FILE_BOX2		= $('#attachFile2');  // 첨부파일(미리보기)   컨트롤 객체
	var P_MODE			= $('#mode'      ).val();			// 처리 모드
	var P_BIZ_PBANC_NO	= $('#bizPbancNo').val();			// 게시글 키정보
	var P_RFORM			= $('#registForm');					// 등록폼 객체
	
	$('#pbancImage'		).attr('src', getUrl('/images/sub/iconimg1.svg'));
	$('#popupPbancImage').attr('src', getUrl('/images/sub/Sketch_on_smartphone.svg'));
	$('#rcptBgngDt_hour').text($('#rcptBgngTm').val().substring(0, 2));
	$('#rcptEndDt_hour'	).text($('#rcptEndTm').val().substring(0, 2));
	
	// ========================================================//
	// 접수기간 FORMAT 설정
	// ========================================================//
	//데이터피커 각 값에 따른 format설정
	function datePickerFormat(value){
        if(value == CODE.PBANC_STUS.PRE) {
        	$(".datepicker-input1, .datepicker-input2").datepicker(OPTIONS.DATEPICKER_MONTH);
        	$("#btnBgngDtHour, #btnEndDtHour").hide();
        } else if(value == CODE.PBANC_STUS.OPEN) {
        	$("#btnBgngDtHour, #btnEndDtHour, #rcptDate").hide();
        	$(".datepicker-input1, .datepicker-input2").prop('disabled', true);
        	$(".datepicker-input1, .datepicker-input2").datepicker('destroy');
        } else if(value == CODE.PBANC_STUS.DTINS) {
        	$(".datepicker-input1, .datepicker-input2").datepicker(OPTIONS.DATEPICKER);
        	$('#rcptBgngDt_hour, #rcptEndDt_hour').prop('disabled', false);
        	$(document).ready(initHourTime);
        }
	}
	//데이터피커 초기화
	function datePickerReset(value){
    	$("#btnBgngDtHour, #btnEndDtHour, #rcptDate").show();
		$(".datepicker-input1, .datepicker-input2").prop('disabled', false);
		$('.datepicker-input1, .datepicker-input2').val("");
        $(".datepicker-input1, .datepicker-input2").datepicker('destroy');
        $('#p_rcptBgngDt, #p_rcptBgngTm, #p_rcptEndDt, #p_rcptEndTm').text("");
        $('#rcptBgngTm, #rcptEndTm').val("");
        $(".dropdown-menu").eq(0).empty();
        $('#rcptBgngDt_hour, #rcptEndDt_hour').prop('disabled', true).text('');
	}
	
	//기간 입력시사용되는 시간테이블 
	function initHourTime() {
	    // '시간' 버튼이 클릭되었을 때 실행
		if(P_MODE == MODE.INSERT){
			$("#rcptBgngDt_hour").text('시간입력');
			$("#rcptEndDt_hour"	).text('시간입력');
		}
	    $('#rcptBgngDt_hour, #rcptEndDt_hour').click(function() {
	        var timeList = STORE.getHours();
	        // 존재하는 항목들을 모두 삭제
	        $(".dropdown-menu").eq(0).empty();
	        $(".dropdown-menu").eq(1).empty();
	        // 시간 값들을 <li> 항목으로 만들어서 dropdown-menu에 추가
	        for (var i = 0; i < timeList.length; i++) {
	            var time = timeList[i];
	            var liItem = '<li><a class="dropdown-item" href="javascript:void(0)" data-time="' + time.code + '">' + time.text + '시</a></li>';
	            $(".dropdown-menu").eq(0).append(liItem);
	            $(".dropdown-menu").eq(1).append(liItem);
	        }
	    });
	    // dropdown-item이 클릭되었을 때 실행
	    $(".dropdown-menu").eq(0).on('click', '.dropdown-item', function() {
	        var time = $(this).data('time'); // 클릭된 항목의 시간 값을 가져옴
	        $('#rcptBgngDt_hour').text(time); // 버튼의 텍스트를 선택된 시간 값으로 설정
	        $('#rcptBgngTm').val(time+":00");
	    });
	    // dropdown-item이 클릭되었을 때 실행
	    $(".dropdown-menu").eq(1).on('click', '.dropdown-item', function() {
	        var time = $(this).data('time'); // 클릭된 항목의 시간 값을 가져옴
	        $('#rcptEndDt_hour').text(time); // 버튼의 텍스트를 선택된 시간 값으로 설정
	        $('#rcptEndTm').val(time+":00");
	    });
	}
	//접수기간 셀렉트박스
	$('#appRcptSeCd').appSelectBox({
		form 	: 'radio',
		name 	: 'rcptSeCd',
		colCls 	: '',
		wrapCls : 'check-radio-box pbanc-radio-box',
		params 	: {upCdId : CODE.PBANC_STUS.code},
		click 	: function() {
			var value = $(this).val();
			$('#appRcptSeCd').data('value', value);
			datePickerReset(value);
			datePickerFormat(value);
		},
		callback : function() {
			var value = $('#appRcptSeCd').data('value');
			datePickerFormat(value);
       },
	});
	
	// ========================================================//
	// 사업분야 Format
	// --------------------------------------------------------//
	P_BZFLD = $('#appBizFld').appSelectBox({
		form : 'checkbox',
		name : 'bizFld',
		params : {upCdId : CODE.BIZ_RLM.code},
		init  : COMBO.INIT_ALL,
		callback : function() { P_BZFLD.setSplitValues(P_BZFLD.data('value'), ',');
			 // '전체' 체크박스에 대한 이벤트 핸들러
	        $('#appBizFld input[value=""]').on('change', function() {
	            let isChecked = $(this).is(':checked');
	            $('#appBizFld input[name="bizFld"]').prop('checked', isChecked);
	        });
	        $('#appBizFld input[value=""]').prop('checked', false); // 체크 해제
        },
		click : function() {
			// hidden에 값넣기
			$('#appBizFld input[type="checkbox"]').on('change', function() {
			    var bizFldValues = [];
			    $('#appBizFld input[type="checkbox"]:checked').each(function() {
		            var bizFldCdNm = $(this).next('label').text().trim();
		            // '전체'라는 텍스트가 포함된 경우 해당 텍스트를 제거
		            if (bizFldCdNm.includes('전체')) {
		            	bizFldCdNm = bizFldCdNm.replace('전체', '');
		            }
		            bizFldValues.push(bizFldCdNm);
			    });
			    var bizFldCdNmHidden = bizFldValues.join(',');
			    // 결과 문자열의 맨 앞이 콤마인지 확인하고, 그렇다면 제거
			    if (bizFldCdNmHidden.startsWith(',')) {
			    	bizFldCdNmHidden = bizFldCdNmHidden.substring(1);
			    }
			    $('input[name="bizFldNm"]').val(bizFldCdNmHidden);
			});
			return true;},
	});
	
	// ========================================================//
	// 사업대상 Format 사업대상 셀렉트박스
	// --------------------------------------------------------//
	P_BZTRGT = $('#appBizTrgt').appSelectBox({
		form: 'checkbox',
		name: 'bizTrgt',
		params: {upCdId: CODE.SPRT_TRGT.code},
		init  : COMBO.INIT_ALL,
		callback: function() { P_BZTRGT.setSplitValues(P_BZTRGT.data('value'), ',');
		 // '전체' 체크박스에 대한 이벤트 핸들러
		   $('#appBizTrgt input[value=""]').on('change', function() {
		       let isChecked = $(this).is(':checked');
		       $('#appBizTrgt input[name="bizTrgt"]').prop('checked', isChecked);
		   });
		   $('#appBizTrgt input[value=""]').prop('checked', false); // 체크 해제
		},
		click: function(data) {
			//reset 작업
			$('input[name="bizTrgtFntnPd"]').not('[value='+CODE.FNTN_WHL.PRE+']').prop("checked",  false);
			$('input[name="bizTrgtFntnPd"]').not('[value='+CODE.FNTN_WHL.PRE+']').prop("disabled", false);
			$('input[name="bizTrgtFntnPd"][value='+CODE.FNTN_WHL.PRE+']').prop("checked", false);
			$('input[name="bizTrgtFntnPd"][value='+CODE.FNTN_WHL.PRE+']').prop("disabled", false);
			
			  // '전체'가 클릭된 경우
	        if($(data.target).val() == '') {
	            var isChecked = $(data.target).is(':checked');
	            $('input[name="bizTrgtInpt"]').prop("checked", isChecked); // '전체'의 체크 상태에 따라 모든 체크박스 체크 또는 체크 해제
	            
	         // 예비창업자만 선택된 경우
	        }else if($('input[value="' 	+ CODE.SPRT_TRGT.PRE  + '"]').is(":checked")       &&  
	        	     $('input[value="'  + CODE.SPRT_TRGT.SOLE + '"]').is(":not(:checked)") &&  
	        	     $('input[value="'  + CODE.SPRT_TRGT.LEGAL+ '"]').is(":not(:checked)") &&  
	        	     $('input[value="'  + CODE.SPRT_TRGT.ETC  + '"]').is(":not(:checked)")) {
	        	
				$('input[name="bizTrgtFntnPd"]').not('[value='+CODE.FNTN_WHL.PRE+']').prop("checked",  false);
				$('input[name="bizTrgtFntnPd"]').not('[value='+CODE.FNTN_WHL.PRE+']').prop("disabled", true);
				$('input[name="bizTrgtFntnPd"][value='+CODE.FNTN_WHL.PRE+']').prop("checked", true);
				$('input[name="bizTrgtFntnPdNm"]').val(CODE.FNTN_WHL.NM);
				
			// 예비창업자를 제외한 다른 것이 선택된 경우 선택된 경우 
	        }else if( ($('input[value="' + CODE.SPRT_TRGT.SOLE  + '"]').is(":checked")  ||
	        	       $('input[value="' + CODE.SPRT_TRGT.LEGAL + '"]').is(":checked")  || 
	        	       $('input[value="' + CODE.SPRT_TRGT.ETC   + '"]').is(":checked")) && 
	        	       $('input[value="' + CODE.SPRT_TRGT.PRE   + '"]').is(":not(:checked)")){
				$('input[name="bizTrgtFntnPd"][value='+CODE.FNTN_WHL.PRE+']').prop("disabled", true);
				$('input[name="bizTrgtFntnPdNm"]').val('');
				// 예비창업자를 와 다른 것이 선택된 경우 선택된 경우 
    		}else if($('input[value="' 	+ CODE.SPRT_TRGT.PRE  + '"]').is(":checked") &&(
	        	     $('input[value="'  + CODE.SPRT_TRGT.SOLE + '"]').is(":checked") ||  
	        	     $('input[value="'  + CODE.SPRT_TRGT.LEGAL+ '"]').is(":checked") ||  
	        	     $('input[value="'  + CODE.SPRT_TRGT.ETC  + '"]').is(":checked"))) {
    			// 아무것도 선택 안되었을때 예비창업자가 마지막으로 체크되어서 안될 경우
    		}else if ($(data.target).val()==CODE.SPRT_TRGT.PRE) {
    			$('input[name="bizTrgtFntnPdNm"]').val('');
    		}
			// hidden에 값넣기
			$('#appBizTrgt input[type="checkbox"]').on('change', function() {
			    var bizTrgtFntnPdValues = [];
			    $('#appBizTrgt input[type="checkbox"]:checked').each(function() {
		            var bizTrgtFntnPdNm = $(this).next('label').text().trim();
		            // '전체'라는 텍스트가 포함된 경우 해당 텍스트를 제거
		            if (bizTrgtFntnPdNm.includes('전체')) {
		            	bizTrgtFntnPdNm = bizTrgtFntnPdNm.replace('전체', '');
		            }
		            bizTrgtFntnPdValues.push(bizTrgtFntnPdNm);
			    });
			    var bizFldCdNmHidden = bizTrgtFntnPdValues.join(',');
			    // 결과 문자열의 맨 앞이 콤마인지 확인하고, 그렇다면 제거
			    if (bizFldCdNmHidden.startsWith(',')) {
			    	bizFldCdNmHidden = bizFldCdNmHidden.substring(1);
			    }
			    $('input[name="bizTrgtNm"]').val(bizFldCdNmHidden);
			});
		},
	});
	
	// ========================================================//
	// 사업대상연령 Format
	// --------------------------------------------------------//
	P_BZTRGTAGE = $('#appBizTrgtAge').appSelectBox({
		form: 'radio',
		name: 'bizTrgtAge',
		params: {upCdId: CODE.SPRT_AGE.code},
		click : function(data){
			// hidden에 값넣기
	        var bizTrgtAgeNm	= $(this).next('label').text().trim();
            $('input[name="bizTrgtAgeNm"]').val(bizTrgtAgeNm);
		}
	});
	
	// ========================================================//
	// 사업대상업력 Format
	// --------------------------------------------------------//
	P_BZTRGTFNTNPD = $('#appBizTrgtFntnPd').appSelectBox({
		form: 'radio',
		name: 'bizTrgtFntnPd',
		params: {upCdId: CODE.FNTN_WHL.code},
		click : function(data){
			// hidden에 값넣기
	        var bizTrgtFntnPdNm 	= $(this).next('label').text().trim();
	        var preBizTrgtFntnPdNm 	= $('input[value="' + CODE.SPRT_TRGT.PRE + '"]').next('label').text().trim();
	        
	        if ($('input[value="' + CODE.SPRT_TRGT.PRE + '"]').is(":checked")) {
	        	if(preBizTrgtFntnPdNm != bizTrgtFntnPdNm){
	        		$('input[name="bizTrgtFntnPdNm"]').val(preBizTrgtFntnPdNm + ',' + bizTrgtFntnPdNm);
	        	}else{
	        		$('input[name="bizTrgtFntnPdNm"]').val(bizTrgtFntnPdNm);
	        	}
	        } else {
	            $('input[name="bizTrgtFntnPdNm"]').val(bizTrgtFntnPdNm);
	        }
		},
		callback: function() {
			// hidden에 값넣기
	        var preBizTrgtFntnPdNm 		= $('input[value="' + CODE.SPRT_TRGT.PRE + '"]').next('label').text().trim();
	        var selectedBizTrgtFntnPd 	= $('input[type=radio][name=bizTrgtFntnPd]:checked');
	        var LabelBizTrgtFntnPd	  	= $("label[for='" + selectedBizTrgtFntnPd.attr('id') + "']").text().trim();
	        
	        if ($('input[value="' + CODE.SPRT_TRGT.PRE + '"]').is(":checked")) {
	        	if(preBizTrgtFntnPdNm != LabelBizTrgtFntnPd){
	        		$('input[name="bizTrgtFntnPdNm"]').val(preBizTrgtFntnPdNm + ',' + LabelBizTrgtFntnPd);
	        	}else{
	        		$('input[name="bizTrgtFntnPdNm"]').val(LabelBizTrgtFntnPd);
	        	}
	        } else {
	            $('input[name="bizTrgtFntnPdNm"]').val(LabelBizTrgtFntnPd);
	        }
	        if(!selectedBizTrgtFntnPd.length){
	        	$('input[name="bizTrgtFntnPdNm"]').val('');
	        }
		},
	});
	//========================================================//
    // 첨부파일 초기화 (app_bizfile.js 참고)
    //--------------------------------------------------------//
	P_FILE_BOX.appBizFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: P_MODE,
		// 설명글
		comment: {
			check: '',
			input: '100MB 이내 3개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장'
		},
		initCount: 1,    // 초기 표시 갯수
		maxCount:  3,    // 추가 최대 갯수
		multiple: true,  // 다중 가능 여부
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.PBANC,
			docuNo  : P_BIZ_PBANC_NO,
			docuSeq : '',
			fileYn  : 'Y',
		}
	}).appBizFile('init');
	
	P_FILE_BOX2.appBizFile({
		cls: 'border-0 mb-0 pb-0',
		// 처리모드
		mode: MODE.VIEW,
		// 설명글
		initData: {
			fileType: CODE.FILE_TYPE.BIZ,
			docuCd  : CODE.TASK_SE.PBANC,
			docuNo  : P_BIZ_PBANC_NO,
			docuSeq : '',
			fileYn  : 'Y',
		}
	}).appBizFile('init');
	
	$("#attachFile2").find('i').attr('class','fs-18px icon-file-hwp mr-6px');

	// 미리보기 이벤트처리
	//--------------------------------------------------------//
 	// 등록폼 객체 각 값 가져오기 (각 RegisterForm ID값으로 미리보기 ID값 매칭) 
	 function getFormValues(formId) {
	    var form = $('#' + formId);
	    var values = {};
	    $.each(form.serializeArray(), function(i, field) {
	        values[field.name] = field.value;
	    });
	    return values;
	}
	// 등록폼 선택된 날짜에서 요일 추기하기 가져오기 (미리보기 날짜 요일)
    function updateDayOfWeek(key, date) {
    	var days = ["(일)", "(월)", "(화)", "(수)", "(목)", "(금)", "(토)"];
        var Stdate = new Date(date); // Date 객체를 생성합니다
        var dayOfWeek = days[Stdate.getDay()]; // 요일을 가져옵니다
        document.getElementById("p_"+key).innerText = date + " " + dayOfWeek + " "; // 날짜와 요일을 출력합니다
    }
	// 미리보기 버튼 클릭시 이벤트처리
	//--------------------------------------------------------//
	$('#btnPreView').bind('click', function() {
		var values = getFormValues('registForm');
		//RegisterForm 에 있는 값 미리보기 ID값 매칭 입력
		$.each(values, function(key, value) {
		    if ($('#p_' + key).length) {
		        $('#p_' + key).text(value);
		    }
		    if(key=='bizTrgtFntnPd'){
		    }
		    // 신청자격 미리보기 출력
		    if(key=="aplyExclTrgtCn" || key == "aplyQlfcCn" || key == "sbmsnDcmntCn" || key == "sprtCn" || key == "rcptMthdCn"){
		    	// textarea에서 값을 가져옵니다
		    	var text = $('#'+key).val();
		    	// 줄바꿈 문자를 <br> 태그로 바꿉니다
		    	var formattedText = text.replace(/\n/g, '<br>');
		    	// 값을 <span> 요소에 넣습니다
		    	$('#p_'+key).html(formattedText);
		    }
		 });
		 
		 $.each(values, function(key, value) {
			// 날짜가 있을 경우 미리보기에서 요일 출력
			if(key == 'rcptBgngYmd' && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.DTINS && $('#rcptBgngYmd').val()){
				updateDayOfWeek(key, value);
			}
			if(key == 'rcptBgngTm' && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.DTINS && $('#rcptBgngTm').val()){
				if (value.includes(":")) {
					$('#p_' + key).text(value+' ~ ');
				}else{
					$('#p_' + key).text(value.substring(0, 2) + ":" + value.substring(2, 4)+' ~ ');
				}
			 }
		     if(key == 'rcptEndYmd' && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.DTINS && $('#rcptEndYmd').val()) {
		    	 updateDayOfWeek(key, value);
		     }
			 if(key == 'rcptEndTm' && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.DTINS && $('#rcptEndTm').val()){
				if (value.includes(":")) {
					$('#p_' + key).text(value);
				}else{
					$('#p_' + key).text(value.substring(0, 2) + ":" + value.substring(2, 4));
				}
			 }
		     if((key =='rcptBgngTm'  && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.OPEN)){
		    	 $('#p_rcptBgngYmd'	).text("");
		    	 $('#p_rcptEndYmd'	).text("");
		    	 $('#p_rcptBgngTm'	).text("");
		    	 $('#p_rcptEndTm'	).text("");
	    	    document.getElementById("p_"+key).innerText = "상시모집"; 
		     }
		     
		     if((key == 'rcptBgngYmd' && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.PRE && $('#rcptBgngYmd').val())){
		    	 var date = new Date(value); // 날짜 객체로 변환
		    	 // 연도와 월을 가져와서 합친 후 "월" 문자를 추가합니다
		    	 var newMonValue = date.getFullYear() + "-" + (date.getMonth() + 1) + "월";
		    	 document.getElementById("p_"+key).innerText = newMonValue + " ~";
		    	 $('#p_rcptBgngTm').text("");
		     }
		     if(( key == 'rcptEndYmd' && $('input[name="rcptSeCd"]:checked').val() == CODE.PBANC_STUS.PRE && $('#rcptEndYmd').val())){
		    	 var date = new Date(value); // 날짜 객체로 변환
		    	 // 연도와 월을 가져와서 합친 후 "월" 문자를 추가합니다
		    	 var newMonValue = date.getFullYear() + "-" + (date.getMonth() + 1) + "월";
		    	 document.getElementById("p_"+key).innerText = newMonValue + " ";
		    	 $('#p_rcptEndTm').text("");
		     }
		     
		 });
	     return false;
	});
	 
	// 미리보기에서 클라이언트 파일 올린거 보기
	$('#btnPreView').on('click', handleFileInput);
		
    function handleFileInput() {
        var downloadContainer = $('#downloadLinkDom');
        downloadContainer.empty(); // 초기화 작업
	       
        var count = $("input[name='fileName']").filter(function() {
            // 이 함수는 각 입력 필드에 대해 실행되며, 
            // 입력 필드의 값이 비어있지 않다면 true를 반환합니다.
            return $(this).val() !== "";
        }).length;
	        
        var fileInputs = Array.from($('input[type="file"]'));

        // 여기서 this는 .input_file을 의미합니다.
    	$('input[type="file"]').each(function(i, fileInput) {
            var files = fileInput.files;
	            
            if($("input[name='fileName']").eq(i).val() !=''){
	            	
            	for(var j = 0; j < files.length; j++) {
            		(function(file) {
            			if(file) {
            				
            				var reader = new FileReader();
            				reader.onload = function(e){
            					var data = e.target.result;
            					
            					var p = $('<p>').addClass('align-items-center d-flex fs-15px px-0 txt2');
            					var icon = $('<i>').addClass('fs-18px icon-file-hwp mr-6px');
            					var a = $('<a>').attr('id', 'downloadLink' + j);
            					
            					p.append(icon);
            					p.append(a);
            					
            					downloadContainer.append(p);
            					
            					a.attr('href', data);
            					a.attr('download', file.name);
            					a.text(file.name);
            				};
            				reader.readAsDataURL(file);
            			} else {
            			}
        		})(files[j]);
        	}}
        });
    }
	var fileInputs = document.getElementsByClassName('input_file');
	for(var i = 0; i < fileInputs.length; i++) {
	    fileInputs[i].style.width = '10px';
	    fileInputs[i].style.height = '10px';
	}
	 
	// ========================================================//
	// 등록폼 VALIDATION RULE 정의
	// --------------------------------------------------------//
	jQuery.validator.addMethod('phoneCustom',function(phone_number, element) {
			// phone_number에서 공백, 하이픈(-) 제거
			phone_number = phone_number.replace(/\s|-/g, '');
			// 일반 전화번호(지역번호 2~3자리 - 국번 3~4자리 - 번호 4자리)
			// 또는 휴대전화번호(010 - 국번 4자리 - 번호 4자리) 패턴 검사
			return this.optional(element)|| phone_number.match(/^(0[2-9]{1,2}[0-9]{3,4}[0-9]{4}|010[0-9]{4}[0-9]{4})$/);
		}, '전화번호를 정확하게 입력하세요.');
	jQuery.validator.addMethod("greaterThan", function(value, element, params) {
		if (!/Invalid|NaN/.test(new Date(value))) {
			return new Date(value) >= new Date($(params).val());
    }
        return isNaN(value) && isNaN($(params).val()) || (Number(value) >= Number($(params).val())); 
    },'종료 날짜는 시작 날짜보다 이후이어야 합니다.');
	
	P_RFORM.validate({
		// 입력값이 유효해서 submit하지 않는다.
		debug: false,
		// true일 경우 포커스가 떠날때 유효성 검사를 한다.
		onfocusout: false,
		// true일 경우 유효성체크없이 무조건 submit한다.
		onsubmit: false,
		// 검증룰 정의
		rules: {
			crdnsBzentyNm 		: {	required 	: true,
        							maxlength	: 15},
        	tkcgDeptNm 			: {	required 	: true,
        							maxlength	: 15},
        	picNm 				: {	required 	: true,
        							maxlength	: 15},
        	picTelno 			: {	required 	: true,
				 				 	phoneCustom : true},
			bizGuidanceUrl		: {	WWWUrl  	: true,
									maxlength	: 400},
			bizPbancNm 			: {	required 	: true,
									maxlength	: 70},
			rcptSeCd 			: {	required 	: true},
			rcptBgngYmd			: {	required	: function() {// 접수기간 체크시(예정,기간입력)에만 유효성 검사 진행
													if ($("input[name='rcptSeCd']:checked").val() == CODE.PBANC_STUS.PRE || $("input[name='rcptSeCd']:checked").val() == CODE.PBANC_STUS.DTINS)
														return true;
													return false;
													}, 
									date: true},
			rcptEndYmd			: {	required	: function() {// 접수기간 체크시(예정,기간입력)에만 유효성 검사 진행
													if ($("input[name='rcptSeCd']:checked").val() ==CODE.PBANC_STUS.PRE || $("input[name='rcptSeCd']:checked").val() ==CODE.PBANC_STUS.DTINS)
														return true;
													return false;
													}, 
									date: true,
				    				greaterThan		: '#rcptBgngYmd'},
			cptBgngTm			: {	required	: function() {
													// 기간입력 체크시에만 유효성 검사 진행
													if ($("input[name='rcptSeCd']:checked").val() ==CODE.PBANC_STUS.DTINS)
														return true;
													return false;
												}},
			rcptEndTm			: {	required	: function() {
													// 기간입력 체크시에만 유효성 검사 진행
													if ($("input[name='rcptSeCd']:checked").val() ==CODE.PBANC_STUS.DTINS)
														return true;
													return false;
												}},
			bizFld				: {	required	: function() {  
													// 상단고정 체크시에만 유효성 검사 진행
													if ($("input[name='bizFld']:checked").length > 0)
														return false;
													return true;
												}},
			bizTrgt				: {	required	: function() {  
													// 상단고정 체크시에만 유효성 검사 진행
													if ($("input[name='bizTrgt']:checked").length > 0)
														return false;
													return true;
												}},
			bizTrgtAge			: {	required	: function() {  
													// 상단고정 체크시에만 유효성 검사 진행
													if ($("input[name='bizTrgtAge']:checked").length > 0)
														return false;
													return true;
												}},
			bizTrgtFntnPd		: {	required	: function() {  
													// 상단고정 체크시에만 유효성 검사 진행
													if ($("input[name='bizTrgtFntnPd']:checked").length > 0)
														return false;
													return true;
												}},
			rcptMthdCn 			: {	required 	: true,
									maxlength	: 1800},
			aplyQlfcCn 			: {	required 	: true,
									maxlength	: 1800},
			aplyExclTrgtCn 		: {	maxlength	: 1800},
			sbmsnDcmntCn 		: {	required 	: true,
									maxlength	: 1800},
			sprtCn 				: {	required 	: true,
									maxlength	: 1800},
        },
        // 검증메세지 정의
        messages: {
        	crdnsBzentyNm		: {	required 	: '등록기관명은 필수 입력사항입니다.',
        							maxlength	: jQuery.validator.format('등록기관명은 최대 {0}자 이하 입니다.')},
        	tkcgDeptNm 			: {	required 	: '사업담당부서는 필수 입력사항입니다.',
        						   	maxlength	: jQuery.validator.format('사업담당부서는 최대 {0}자 이하 입니다.')},
        	picNm 				: {	required 	: '사업담당자는 필수 입력사항입니다.',
        						 	maxlength	: jQuery.validator.format('사업담당자는 최대 {0}자 이하 입니다.')},
        	picTelno 			: {	required 	: '전화번호는 필수 입력사항입니다.',
        		                  	phoneCustom : '전화번호 형식에 맞게 작성해주세요.'	},
        	bizGuidanceUrl		: {	WWWUrl 		: 'URL의 양식에 맞게 작성해주세요.',
								 	maxlength	: jQuery.validator.format('안내URL은 최대 {0}자 이하 입니다.')},
			bizPbancNm 			: {	required 	: '공고명은 필수 입력사항입니다.',
      			  					maxlength	: jQuery.validator.format('공고명은 최대 {0}자 이하 입니다.')},
			rcptSeCd 			: {	required 	: '접수기간은 필수 입력사항입니다.'},
			rcptBgngYmd 		: {	required 	: '접수기간체크시 접수기간 날짜는 필수 입력사항입니다.',
								 	date 		: '접수기간을 형식에 맞게 입력해주세요.'},
			rcptEndYmd 			: {	required 	: '접수기간체크시 접수기간 날짜는 필수 입력사항입니다.',
								 	date 		: '접수기간을 형식에 맞게 입력해주세요.'},
			rcptBgngTm 			: {	required 	: '기간입력 선택시 접수기간 시간은 필수 입력사항입니다.'},
			rcptEndTm 			: {	required 	: '기간입력 선택시 접수기간 시간은 필수 입력사항입니다.'},
			bizFld 				: {	required 	: '사업분야는 필수 입력사항입니다.'},
			bizTrgt 			: {	required 	: '사업대상은 필수 입력사항입니다.'},
			bizTrgtAge 			: {	required 	: '사업대상연령은 필수 입력사항입니다.'},
			bizTrgtFntnPd 		: {	required 	: '사업대상업력은 필수 입력사항입니다.'},
			rcptMthdCn 			: {	required	 : '공고접수방법은 필수 입력사항입니다.',
				 					maxlength	: jQuery.validator.format('사업담당자는 최대 {0}자 이하 입니다.')},
			aplyQlfcCn 			: {	required 	: '공고접수방법은 필수 입력사항입니다.',
									maxlength	: jQuery.validator.format('사업담당자는 최대 {0}자 이하 입니다.')},
			aplyExclTrgtCn 		: {	maxlength	: jQuery.validator.format('사업담당자는 최대 {0}자 이하 입니다.')},
			sbmsnDcmntCn 		: {	required 	: '공고접수방법은 필수 입력사항입니다.',
									maxlength	: jQuery.validator.format('사업담당자는 최대 {0}자 이하 입니다.')},
			sprtCn 				: {	required 	: '공고접수방법은 필수 입력사항입니다.',
									maxlength	: jQuery.validator.format('사업담당자는 최대 {0}자 이하 입니다.')},
        },
        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
        invalidHandler: validateHandler,
        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
        errorPlacement: validatePlacement
    });
	bindOnlyNumber($("#picTelnoForm"));	 
	
	var picTelno = $('#picTelno').val();
	if (picTelno) {
		picTelno = picTelno.replace(/-/g, '');
		if (picTelno.length === 10 && picTelno.startsWith('02')) {
			picTelno = picTelno.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
		} else if (picTelno.length === 10) {
			picTelno = picTelno.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
		} else if (picTelno.length === 11) {
			picTelno = picTelno.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		} else if (picTelno.length <= 9) {
			picTelno = picTelno.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
		}
		$('#picTelnoForm').val(picTelno);
	}

	$('#picTelnoForm').keyup(function(event) {
		var input = $(this).val();
		input = input.replace(/-/g, '');
		if (input.length === 10 && input.startsWith('02')) {
			input = input.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
		} else if (input.length === 10) {
			input = input.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
		} else if (input.length === 11) {
			input = input.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		} else if (input.length <= 9) {
			input = input.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
		}
		$(this).val(input);
		$('#picTelno').val(input.replace(/-/g, ''));
	});
	 
	// 저장버튼 클릭시 이벤트 처리 (DB입력용 1차 유효성입력)
    //--------------------------------------------------------//
	function formatPhoneNumber(id) {
	    var rawNum = $('#' + id + 'Form').val().replace(/-/g, '');
	    $('#' + id).val(rawNum);
	}
	
	function formatDateTime(startId, endId) {
	    var startTm  = ($('#' + startId	+ 'Tm').val().substring(0, 2)+"00");
	    var endTm    = ($('#' + endId	+ 'Tm').val().substring(0, 2)+"00");
	    var startYmd =  $('#' + startId + 'Ymd').val().replace(/-/g, '');
	    var endYmd   =  $('#' + endId 	+ 'Ymd').val().replace(/-/g, '');

	    if (startTm!='00' && startTm!='0000') {
	        $('#' + startId + 'Dt').val(startYmd + startTm);
	    } else {
	        $('#' + startId + 'Dt').val(startYmd.padEnd(12, '0'));
	    }

	    if (endTm != '00' && endTm !='0000') {
	        $('#' + endId + 'Dt').val(endYmd + endTm);
	    } else {
	        $('#' + endId + 'Dt').val(endYmd.padEnd(12, '0'));
	    }
	}
	function clearIfOpen(code, startId, endId) {
	    if($('#appRcptSeCd').data('value') == code){
	        $('#' + startId + 'Dt').val('');
	        $('#' + endId + 'Dt' ).val('');
	    }
	}
	
    $('#btnSave').bind('click', function() {
        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

		// 첨부파일 업로드 VALIDATION (app_bizfile.js)
		if (P_FILE_BOX.appBizFile('validate', {
				isAlert: true,
				isExt:   true,
				isLimit: true
			}) === false)
            return false;
		
		let msg = "사업공고를 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "사업공고를 등록하시겠습니까?";
		
		// 핸드폰 번호 이어붙이기 
		formatPhoneNumber('picTelno');
		// 월, 일, 시간 구분 조회
		formatDateTime('rcptBgng', 'rcptEnd');
		clearIfOpen(CODE.PBANC_STUS.OPEN, 'rcptBgngDt', 'rcptEndDt');
		
    	$.commMsg.confirm(msg, function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/usr/mypage/pbanc/savePbanc.do'),
                enctype : 'multipart/form-data',
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
                	$.ajaxUtil.success(ret, function(data) {
                        // 목록으로 이동
                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
							// 부모창의 목록재검색 및 팝업닫기
                    		goUrl(getUrl('/usr/mypage/pbanc/openPbanc.do'));
							return false;
						});
                	});
                }
            }).submit();
    	});
        return false;
	});
	// 목록페이지로 이동
	function doCancel() {
		$.formUtil.submitForm( getUrl('/usr/mypage/pbanc/openPbanc.do'), {
			formId: 'searchForm'
		});
		return false;
	}
    // 이전버튼 클릭시 이벤트처리
    $('#btnCancel ').bind('click', doCancel);

});

