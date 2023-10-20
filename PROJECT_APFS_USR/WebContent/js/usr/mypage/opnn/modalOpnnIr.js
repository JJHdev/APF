/**
*******************************************************************************
***    명칭: modalOpnnIr.js
***    설명: 마이페이지 - IR검토의견작성 모달 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.28    KYW        First Coding.
*******************************************************************************
**/
$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let M_FORM 		= $('#modalForm');			// 폼 객체
	let O_MODE		= $('#mode').val();			// 모드(입력, 조회, 수정)
	let EVNT_NO		= $('#evntNo').val();		// 행사번호
	let RGST_YN		= $('#irRgstYn').val();		// 작성여부
	let IR_DATA 	= null; 					// json배열
	let M_PAGE		= 0;						// 현재 페이지-1 = 현재 객체
	let M_TOTAL		= 0;						// 전체 페이지 = 전체 객체 개수
	// 작성 input 배열
	let inputType	= ['textarea', 'input[type="radio"]']; 
	// 필수요소, 메시지
	let rule = ['bizCn', 'prdctCn', 'coCn', 'gnrlzOpnn', 'invtItrstDgreeCd', 'fllwMtgIntenYn'];
	let messages = {
			bizCn : 'BUSINESS 검토의견은 필수 입력 사항입니다.',
			prdctCn : 'PRODUCT(TECH) 검토의견은 필수 입력 사항입니다.',
			coCn : 'COMPANY 검토의견은 필수 입력 사항입니다.',
			gnrlzOpnn : '종합의견은 필수 입력 사항입니다.',
			invtItrstDgreeCd : '투자관심도는 필수 선택 사항입니다.',
			fllwMtgIntenYn : '후속미팅 의향은 필수 선택 사항입니다.'
	};
	
	// 행사 정보 불러오기 (모달 오픈시 실행)
	//--------------------------------------------------------//
	function getEventInfo() {
		// ajax로 조회
		$.ajaxUtil.ajaxLoad(
            getUrl('/usr/mypage/opnn/viewOpnnIr.do'), 
            {
            	mode 		: O_MODE,
            	evntNo 		: EVNT_NO,
				irRgstYn	: RGST_YN
            }, 
            function(ret) {
                $.ajaxUtil.success(ret, function() {
                	IR_DATA = ret.Data;
                	M_TOTAL = IR_DATA.length;
					
                	setDataValue(M_PAGE);
                });
            }
        );
		
		if(O_MODE == MODE.VIEW) { // 조회 시 수정 불가
			inputType.forEach(function(e, i){
				$('.rgstTable').find(e).prop('disabled', true);
			});

			if(RGST_YN) {				
				$('.btnForView').removeClass('d-none');
			}
			$('.btnForInsert').addClass('d-none');
		} else if(O_MODE == MODE.UPDATE) { // 수정가능형태로 변환
			inputType.forEach(function(e, i){
				$('.rgstTable').find(e).prop('disabled', false);
			});
			$('.btnForView').addClass('d-none');
			
			// mode = update
			$.formUtil.setValue(M_FORM, 'mode', O_MODE);
		}
	}
	
	getEventInfo();
	
	// 데이터 인덱스로 불러오기 뿌리기
	//--------------------------------------------------------//
	function setDataValue(i) {
		if(M_TOTAL == 1) $('#btnRgstOpnn').removeClass('d-none');
		
		var data = IR_DATA[i];

    	Object.keys(data).forEach(function(e) {
			var elem = $('#modalForm').find('.value-box.' + e);

			if (elem.length > 0) { //textarea, td 
				var decodedValue = $('<div/>').html(data[e]).text();
				elem.text(data[e]);
				elem.val(decodedValue);
				
			} else { //input (radio)
				elem = $("#modalForm").find('input[name="' + e + '"][value="' + data[e] + '"]');
				
				if (elem.length > 0) {
					elem.prop("checked", true);	
				}
			}
		});
    	
    	// 페이지 박스 부분
    	$('#pageBox').text((i+1) + '/' + M_TOTAL);
    	
    	return false;
	};
	
	// 값 변경시 입력값 변수에 저장
	//--------------------------------------------------------//
	$('.rgstTable textarea').bind('change', saveData);
	$('.rgstTable input[type="radio"]').bind('change', saveData);
	
	function saveData() {
		var key = $(this).attr('name'); //name값으로 키값 설정
		var value = $(this).val();	
		
		IR_DATA[M_PAGE][key] = value; //해당 객체에 키값 추가
	}
	
	// 입력 값 초기화
	//--------------------------------------------------------//
	function resetForm() {
		inputType.forEach(function(e, i){
			if(e.includes('input')) {
				$('.rgstTable').find(e).prop('checked', false);
			} else {
				$('.rgstTable').find(e).val('');
			}
		});
	}
	
	// 버튼이벤트
	//--------------------------------------------------------//
	// 페이지 박스 이전, 다음 버튼 이벤트
	$('.btnPrev').bind('click', function() {
		if(M_PAGE != 0) {
			//등록하기 버튼 숨기기
			$('#btnRgstOpnn').addClass('d-none');
			$('#btnNextBtm').parent().removeClass('d-none');
			
			M_PAGE--;
			resetForm();
			setDataValue(M_PAGE);
		} 
	});
	$('.btnNext').bind('click', function() {
		if(M_PAGE < M_TOTAL-1) {
			M_PAGE++;
			resetForm();
			setDataValue(M_PAGE);
			//등록 버튼 표출
			if((O_MODE == MODE.INSERT || O_MODE == MODE.UPDATE) && M_PAGE == M_TOTAL-1) {
				$('#btnRgstOpnn').removeClass('d-none');
				$('#btnNextBtm').parent().addClass('d-none');
			}
		} 
	});
	
	// 수정 버튼 클릭 이벤트
	$('#btnMdfyOpnn').bind('click', function() {
		$.commMsg.confirm('IR 투자자 검토의견서를 수정하시겠습니까?', function() {
			//수정상태로 변환
			O_MODE = MODE.UPDATE;
			getEventInfo();
			//등록 버튼 표출
			if((O_MODE == MODE.INSERT || O_MODE == MODE.UPDATE) && M_PAGE == M_TOTAL-1) {
				$('#btnRgstOpnn').removeClass('d-none');
				$('#btnNextBtm').parent().addClass('d-none');
			}
		});
		
	});
	
	// 임시저장 버튼 클릭 이벤트
	$('#btnRgstTempOpnn').bind('click', function() {
		$.formUtil.setValue(M_FORM, 'prgrsSttsCd', '00'); //임시저장
		let msg = 'IR 투자자 검토의견서를 임시저장하시겠습니까?';
		
		rgstOpnnIr(msg);
	})
	
	// 등록 버튼 클릭 이벤트
	$('#btnRgstOpnn').bind('click', function() {
		$.formUtil.setValue(M_FORM, 'prgrsSttsCd', '10'); //작성완료
		let msg = 'IR 투자자 검토의견서를 등록하시겠습니까?';
		
		let check = checkValidation();
		if(check) { // 배열로 form에 넣기
			rgstOpnnIr(msg);
		}
	
	});
	
	// 등록 ajax
	//--------------------------------------------------------//
	function rgstOpnnIr(msg) {
		let arr = ['bizCn', 'prdctCn', 'coCn', 'gnrlzOpnn'];
		
		rule.push('evntPartcptnNo');
		rule.forEach(function(e) {
			var data = IR_DATA.map(function(row) {
				row[e]==undefined? row[e] = '' : row[e];
				return row[e];
			});
			if( data.length == 1 && e != 'evntPartcptnNo' )
				data.push('');
			
			if(arr.includes(e)) {			
				data = data.join('#%%#');
			}
			$.formUtil.setValue(M_FORM, e+'Arr', data);
		})

		$.commMsg.confirm(msg, function() {
			M_FORM.ajaxForm({
				url: getUrl('/usr/mypage/opnn/saveOpnnIr.do'),
				enctype : 'multipart/form-data',
				// 오류시 처리로직
				error: $.ajaxUtil.error,
				// 저장후 처리로직
				success: function(ret) {
					$.ajaxUtil.success(ret, function() {
						$.commMsg.alert('성공적으로 저장되었습니다.', function() {
							// 팝업 닫기
							modal.close();
							return false;
						});
					});
				}
			}).submit();
		});
	}
	
	// 필수입력 값 확인 - 모두 입력 됐을 시 return true;
	//--------------------------------------------------------//
	function checkValidation() {
		let check = true;
		IR_DATA.some(function(data,i){
			var dataArr = Object.keys(data);
			var include = rule.filter(function(r){
				if(!dataArr.includes(r) || data[r] == undefined || data[r] == '' || fn_spaceChk(data[r])) 
					return r;
			});
			if(include.length > 0) {
				check = false;
				$.commMsg.alert((i+1) + "페이지의 " + messages[include[0]]);
				return true;
			}
		});
		return check;
	}
	
	function fn_spaceChk(value){
	  var regExp = /\s/g;
	  if(value.replace(regExp, '').length == 0){
	    return true;
	  }
	  return false;
	}
	
	//tooltip
	$('.check-radio-box').tooltip();
	
});
