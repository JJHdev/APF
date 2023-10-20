/**
 * ****************************************************************************** **
 * 명칭: modalDscsnForm.js ** 설명: 운영관리-상담일지 화면 ** **
 * ----------------------------- Modified Log --------------------------- ** 버전
 * 버전 		수정일자 				수정자 		내용 **
 * --------------------------------------------------------------------------- **
 * 1.0 		2023.06.27 			J H 		작업완료.
 * ******************************************************************************
 */
var P_BZFLD 			= false; // 사업분야 체크박스 객체
var P_RFORM 			= $('#registForm'	);			// 등록폼 객체
var P_MODE 				= $('#mode'			).val(); 	// 처리 모드
var P_SN 				= $('#sn'			).val(); 	// 게시글 키정보
var P_PIC_TELNO 		= $('#picTelno'	).val(); 	// 담당자 전화번호

$(function() {
	// ========================================================//
	// FORM ELEMENTS 정의
	// --------------------------------------------------------//
	// 성별 라디오버튼 및 콤보박스 정의
	$('#appSexdstn').appSelectBox({
		form 	: 'radio',
		name 	: 'sexdstn',
		colCls 	: '',
		wrapCls : 'check-radio-box',
		params 	: {
			upCdId : CODE.SXDST_SE.code
		},
		click : function() {
			return true;
		},
	});

	$('#dscsnCntrCd').appComboBox({
		params : {
			upCdId : CODE.DSCSN_CNTR.code
		},
		loadFilter : function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});

	$('#dscsnMthdCd').appComboBox({
		params : {
			upCdId : CODE.DSCSN_MTHD.code
		},
		loadFilter : function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});

	$('#bzentyTypeCd').appComboBox({
		params : {
			upCdId : CODE.BZENTY_TYPE.code
		},
		loadFilter : function(data) {
			data.unshift(COMBO.INIT_SELECT);
			return data;
		},
	});

	$('#dtlSeCd').appComboBox({
		url : getUrl('/com/common/getComboDcmnt.do'),
		params : {
			sprtAplySeCd : CODE.SPRT_APLY_SE.AFTER
		},
		loadFilter : function(data2) {
			data2.unshift(COMBO.INIT_SELECT);
			return data2;
		},
	});

	// ========================================================//
	// 인풋박스 정의
	// --------------------------------------------------------//
	P_BZFLD = $('#appBizFldBox').appSelectBox({
		form : 'checkbox',
		name : 'bizFld',
		params : {
			upCdId : CODE.BIZ_RLM.code
		},
		callback : function() {	P_BZFLD.setSplitValues(P_BZFLD.data('value'), ',')},
		click : function() {return true;},
	});

	// 전화번호 검증
	jQuery.validator.addMethod('phoneCustom',function(phone_number, element) {
		phone_number = phone_number.replace(/\s|-/g, '');
		return this.optional(element)|| phone_number.match(/^(0[2-9]{1,2}[0-9]{3,4}[0-9]{4}|010[0-9]{4}[0-9]{4})$/);
	}, '전화번호를 정확하게 입력하세요.');
	
	jQuery.validator.addMethod('brnoNumber', function(business_number, element) {
		if(business_number ==''){
			return true;
		}
	    business_number = business_number.replace(/\s|-/g, ''); //하이픈 제거
	    if (business_number.length != 10) {
	        return false;
	    }
	    let sum = 0;
	    const arr = [1, 3, 7, 1, 3, 7, 1, 3, 5];
	    for (let i = 0; i < 9; i++) {
	        sum += parseInt(business_number.charAt(i)) * arr[i];
	    }
	    sum += Math.floor(parseInt(business_number.charAt(8)) * 5 / 10);
	    let last = (10 - (sum % 10)) % 10;
	    return this.optional(element) || last === parseInt(business_number.charAt(9));
	}, '사업자번호가 유효하지 않는 번호 입니다.');
	// ========================================================//
	// 등록폼 VALIDATION RULE 정의
	// --------------------------------------------------------//
	P_RFORM.validate({
		debug : false,
		onsubmit : false,
		onfocusout : false,
		// 검증룰 정의
		rules : {
			dscsnYmd 		: 	{	date 		: true},
			cnslrNm 		: 	{	maxlength 	: 15},
			bzentyNm 		: 	{	maxlength 	: 100},
			brno	 		: 	{	bzno 		: true,
									brnoNumber 	: true},
			rprsvNm 		: 	{	maxlength 	: 15},
			brdt 			: 	{	date 		: true},
			picNm 			: 	{	maxlength 	: 15},
			picDeptNm 		: 	{	maxlength 	: 150},
			picTelno 		: 	{	phoneCustom : true},
			picEmlAddr 		: 	{ 	email 		: true},
			fndnYmd 		: 	{	date 		: true},
			empCnt 			: 	{	numeric  	: true},
			invtFldNm		: 	{	maxlength 	: 80},
		    tpbizSeNm		: 	{	maxlength 	: 80},
			industSeNm		: 	{	maxlength 	: 80},
			slsAmt			: 	{	maxlength 	: 13},
			hmpgAddr		: 	{	WWWUrl 		: true},
			lctnAddr1		: 	{	maxlength 	: 170},
   			lctnAddr2		: 	{	maxlength 	: 170},
	   		bizCn			: 	{	maxlength 	: 3500},
	   		dscsnCn			: 	{	maxlength 	: 20000},
		},
		// 검증메세지 정의
		messages : {
			dscsnYmd 		: 	{	date 		: '상담일을 형식에 맞게 입력해주세요.'},
			cnslrNm 		: 	{	maxlength 	: jQuery.validator.format('상담 담당자명은 최대 {0}자 이하 입니다.')},
			bzentyNm 		: 	{	maxlength 	: jQuery.validator.format('경영체 명은 최대 {0}자 이하 입니다.')},
		  	brno 			: 	{	bzno 		: '사업자번호에 맞게 양식을 입력해주세요.'},
			rprsvNm 		: 	{	maxlength 	: jQuery.validator.format('대표자 명은 최대 {0}자 이하 입니다.')},
			brdt 			: 	{	date 		: '대표자 생년월일을 형식에 맞게 입력해주세요.'	},
			picNm 			: 	{	maxlength 	: jQuery.validator.format('상담 담당자명은 최대 {0}자 이하 입니다.')},
			picDeptNm 		: 	{	maxlength 	: jQuery.validator.format('부서 직책명은 최대 {0}자 이하 입니다.')},
			picTelno 		: 	{	phoneCustom : '전화번호 형식에 맞게 작성해주세요.'	},
			picEmlAddr 		:	{	email 		: '이메일 형식에 맞게 작성해주세요.'},
			fndnYmd 		: 	{	date 		: '설립연도를 형식에 맞게 입력해주세요.'},
			empCnt 			: 	{	numeric 	: '임직원 수(명)은 정수만 입력가능합니다.'	},
			invtFldNm		: 	{	maxlength 	: jQuery.validator.format('투자분야는 최대 {0}자 이하 입니다.')},
  			tpbizSeNm		: 	{	maxlength 	: jQuery.validator.format('업종구분은 최대 {0}자 이하 입니다.')},
		  	industSeNm		: 	{	maxlength 	: jQuery.validator.format('산업구분은 최대 {0}자 이하 입니다.')},
			slsAmt	    	:   {	maxlength 	: jQuery.validator.format('전년도 매출액은 최대 {0}자 이하 입니다.')},				  
		    hmpgAddr		:	{	WWWUrl 		: 'URL의 양식에 맞게 작성해주세요.'},
		    lctnAddr1		: 	{	maxlength 	: jQuery.validator.format('본사 소재지는 최대 {0}자 이하 입니다.')},
			lctnAddr2		:   {	maxlength 	: jQuery.validator.format('대표공장 소재지는 최대 {0}자 이하 입니다.')},				  
			bizCn			:   {	maxlength 	: jQuery.validator.format('사업내용(주생상품)은 최대 {0}자 이하 입니다.')},				  
			dscsnCn			:   {	maxlength 	: jQuery.validator.format('상담내용은 최대 {0}자 이하 입니다.')},			  
		},
		invalidHandler : validateHandler,
		errorPlacement : validatePlacement
	});
	// 숫자만입력
	bindOnlyNumber($("#brno"));
	bindOnlyNumber($("#slsAmt"));
	bindOnlyNumber($("#picTelno"));
	
	//숫자 포맷
	bindEditMoney($("#empCntForm"));
	var formattedEmpCnt = $.formatUtil.toNumber($("#empCnt").val());
	$('#empCntForm').val(formattedEmpCnt);
	//숫자 포맷
	bindEditMoney($("#slsAmtForm"));
	var formattedSlsAmt = $.formatUtil.toNumber($("#slsAmt").val());
	$('#slsAmtForm').val(formattedSlsAmt);
	
	
	$('#brno').keyup(function(event) {
		var input = $(this).val();
		input = input.replace(/-/g, '');

		if (input.length > 10) {
			$.commMsg.alert('사업자 번호는 10자리를 초과할 수 없습니다.');
			input = input.substring(0, 10); // 10자리까지만 남긴다.
		}
		$('#brno').val(input); // hidden 필드에 하이픈이 제거된 사업자번호를 설정

		if (input.length > 3 && input.length <= 5) {
			input = input.replace(/(\d{3})(\d{1,2})/, '$1-$2');
		} else if (input.length > 5) {
			input = input.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
		}
		$(this).val(input);
	});

	var brno = $('#brno').val();
	if (brno) { // brno가 존재할 때만 실행
		brno = brno.replace(/-/g, '');
		if (brno.length > 3 && brno.length <= 5) {
			brno = brno.replace(/(\d{3})(\d{1,2})/, '$1-$2');
		} else if (brno.length > 5) {
			brno = brno.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
		}
		$('#brno').val(brno);
	}
	
	$('#picTelno').keyup(function(event) {
		var input = $(this).val();
		input = input.replace(/-/g, '');

		if (input.length > 11) {
			$.commMsg.alert('전화번호는 11자리를 초과할 수 없습니다.');
			input = input.substring(0, 11); // 11자리까지만 남긴다.
		}
		$('#picTelno').val(input); // hidden 필드에 하이픈이 제거된 전화번호를 설정

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
	});

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
		$('#picTelno').val(picTelno);
	}

	// 세부지원사업 저장하기
	// --------------------------------------------------------//
	function doSave() {
		var numberSlsAmt = $("#slsAmtForm").val().replace(/,/g, "");
		$("#slsAmt").val(numberSlsAmt);
		var numberEmpCnt = $("#empCntForm").val().replace(/,/g, "");
		$("#empCnt").val(numberEmpCnt);
		
		// 등록폼의 VALIDATION 기능 활성화
		if (P_RFORM.validate().settings)
			P_RFORM.validate().settings.ignore = false;

		// FORM VALIDATION
		if (P_RFORM.valid() === false)
			return false;

		let msg = "상담일지를 수정하시겠습니까?";
		if (P_RFORM.find('[name="mode"]').val() == MODE.INSERT)
			msg = "상담일지를 등록하시겠습니까?";
		
		$.commMsg.confirm(msg, function() {
			// 등록폼을 AJAX로 저장처리
			P_RFORM.ajaxForm({
				url : getUrl('/adm/invest/dscsn/saveDscsn.do'),
				// 오류시 처리로직
				error : $.ajaxUtil.error,
				// 저장후 처리로직
				success : function(ret) {
					$.ajaxUtil.success(ret, function(data) {
						// 목록으로 이동
						$.commMsg.alert('성공적으로 저장되었습니다.', function() {
							// 부모창의 목록재검색 및 팝업닫기
							P_MODAL.doSearch();
							return false;
						});
					});
				}
			}).submit();
		});
		return false;
	}

	// 상담일지목록 삭제하기
	// --------------------------------------------------------//
	function doRemove() {

		$.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode : MODE.REMOVE, // 삭제모드
			}, P_RFORM);

			// 등록폼을 AJAX로 저장처리
			P_RFORM.ajaxForm({
				url : getUrl('/adm/invest/dscsn/saveDscsn.do'),
				// 오류시 처리로직
				error : $.ajaxUtil.error,
				// 저장후 처리로직
				success : function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
						P_MODAL.doSearch();
					});
				}
			}).submit();
		});
		return false;
	}

	// 취소버튼 클릭시 이벤트처리
	// --------------------------------------------------------//
	$('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
		return false;
	});
	// 저장버튼 클릭시 이벤트 처리
	$('#btnSave').bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
	$('#btnRemove').bind('click', doRemove);
	
	// 각 해당하는 날짜 유효성에 맞는 달력 출력 
	$('.datepicker-input'           ).datepicker(OPTIONS.DATEPICKER);
	$('.datepickerNotTomorrow-input').datepicker(OPTIONS.DATEPICKER_NOT_TOMORROW);
	$('.datepickerYear-input'		).datepicker(OPTIONS.DATEPICKER_YEAR);

});