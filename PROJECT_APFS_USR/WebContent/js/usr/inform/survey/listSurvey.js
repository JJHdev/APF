var surveyInfoCnt = 0;

$(function() {
	//========================================================//
	// 화면 스크립트 내에 사용할 객체,상수 정의
	//--------------------------------------------------------//
	let P_GRID   		= $('#appGrid'     ); // 목록 GRID
	let P_FORM   		= $('#searchForm'  ); // 검색폼	
    let P_BBS_CD  		= $('#bbsSeCd'  ).val();// 게시판 구분코드
    let P_CLSF_CD		= $('#pstClsfCd').val();// 게시판 분류코드
    let P_CLSF			  ;// 게시판 분류코드
	let P_SRCHTXT 		= $('#srchText' ).val();// 검색텍스트 (이전에서 넘어온항목)
	let P_GS_ROLE_ID 	= $('#gsRoleId' ).val();// 유저권한 
	
    //========================================================//
    // 목록 GRID 정의 (EasyUI datagrid)
    //--------------------------------------------------------//
	P_GRID.appBbsGrid({
		// 목록 KEY필드
		idField:    'srvyNo',
		// 목록 제목
		title:      '설문조사',
		// 검색 URL
		url:         getUrl('/usr/inform/survey/getListSurveyPagenation.do'),
		//목록 검색 페이징 객체
		pagination: {display: 10},
		// 페이지 표시 객체
		paging:     '#appGridPagination',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '등록된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['78px','562px','160px','*','*'],
			// 목록칼럼 정의
			columns: [
	            {name:'srvyNo'		,cls:'app-c',label:'번호', rownumbers: true},
	            {name:'srvyCn'       ,cls:'app-l	',label:'제목'},
	            {name:'srvyBgngYmd'      ,cls:'app-c',label:'시작일', formatter: $.formatUtil.toDashDate},
	            {name:'srvyEndYmd'       ,cls:'app-c',label:'종료일', formatter: $.formatUtil.toDashDate},
	            {name:'particiPation'       ,cls:'app-c',label:'참여여부'},
			],
	        // 행선택시 상세조회
	        select: doSelect
		},
		autoload: true,
    }).appBbsGrid('init');

	function doSelect(row) {
		if(row.particiPation == '미참여'){
			var data = {srvyNo : row.srvyNo}
			surveyInfoCnt = 0;
			$('#survyStaticDiv').empty();//기존 요소 제거
			$('.survyStaticHead').empty();//기존 요소 제거
		
			var today = getToday();
			
			if(row['srvyBgngYmd'] <= today && today <= row['srvyEndYmd'] ) {
				$.ajax({
					url : getUrl('/usr/inform/survey/ajax/getSurveyInfo.do'),
					dataType : 'json',
					data : data,
					contentType : "application/json; charset=utf-8",
					async : false,
					type : 'GET',
					success : function(data) {
						$('.regiNm').append(data.popHeadInfo.userNm);
						$('.attendCnt').append(data.attendCnt.count.toString());
						$('.stDt').append(toDotDate(data.popHeadInfo.srvyBgngYmd));
						$('.edDt').append(toDotDate(data.popHeadInfo.srvyEndYmd));
						$('.srvyTitle').append(data.popHeadInfo.srvyCn);
						
						for(var i=0; i<data.surveyInfoList.length; i++){
							var item_div = "";
							if(data.surveyInfoList[i].qitemType=="QT1"){//라디오
								for(var j=0; j<data.surveyInfoItemList.length; j++){
									if(data.surveyInfoList[i].qitemNo==data.surveyInfoItemList[j].qitemNo){
										if(data.surveyInfoItemList[j].artclCn==""){
											item_div += '<div class="col flex-grow-0 white-space-nowrap">'+
															'<div class="check-radio-box">'+
																'<input type="radio" id="r_'+j+'" name="survyRadio_'+i+'" value="'+data.surveyInfoList[i].srvyNo+'_'+data.surveyInfoItemList[j].qitemNo+'_'+data.surveyInfoItemList[j].artclNo+'">'+
																'<label for="r_'+j+'"><textarea placeholder="기타입력" style="height:25px;width:100px;overflow-y:hidden;resize:none;"></textarea></label>'+
															'</div>'+
														'</div>';
										}
										else{
											item_div += '<div class="col flex-grow-0 white-space-nowrap">'+
															'<div class="check-radio-box">'+
																'<input type="radio" id="r_'+j+'" name="survyRadio_'+i+'" value="'+data.surveyInfoList[i].srvyNo+'_'+data.surveyInfoItemList[j].qitemNo+'_'+data.surveyInfoItemList[j].artclNo+'">'+
																'<label for="r_'+j+'">'+data.surveyInfoItemList[j].artclCn+'</label>'+
															'</div>'+
														'</div>';
										}
									}
								}					
							}
							if(data.surveyInfoList[i].qitemType=="QT2"){//체크
								for(var j=0; j<data.surveyInfoItemList.length; j++){
									if(data.surveyInfoList[i].qitemNo==data.surveyInfoItemList[j].qitemNo){
										if(data.surveyInfoList[i].qitemNo==data.surveyInfoItemList[j].qitemNo){
											if(data.surveyInfoItemList[j].artclCn==""){
												item_div += '<div class="col-12 col-md-4">'+
																'<div class="check-radio-box">'+
																	'<input type="checkbox" id="c_'+j+'" name="survyCheckBox_'+i+'" value="'+data.surveyInfoList[i].srvyNo+'_'+data.surveyInfoItemList[j].qitemNo+'_'+data.surveyInfoItemList[j].artclNo+'">'+
																	'<label for="c_'+j+'"><textarea placeholder="기타입력" style="height:25px;width:100px;overflow-y:hidden;resize:none;"></textarea></label>'+
																'</div>'+
															'</div>';
											}
											else{
												item_div += '<div class="col-12 col-md-4">'+
																'<div class="check-radio-box">'+
																	'<input type="checkbox" id="c_'+j+'" name="survyCheckBox_'+i+'" value="'+data.surveyInfoList[i].srvyNo+'_'+data.surveyInfoItemList[j].qitemNo+'_'+data.surveyInfoItemList[j].artclNo+'">'+
																	'<label for="c_'+j+'">'+data.surveyInfoItemList[j].artclCn+'</label>'+
																'</div>'+
															'</div>';
											}
										}
									}
								}
							}
							if(data.surveyInfoList[i].qitemType=="QT3"){//인풋
								for(var j=0; j<data.surveyInfoItemList.length; j++){
									if(data.surveyInfoList[i].qitemNo==data.surveyInfoItemList[j].qitemNo){
										item_div = '<div class="form-area-box">'+
														'<div class="ele-icon-box">'+
															'<textarea id="survyTextBox_'+i+'" class="'+data.surveyInfoList[i].srvyNo+'_'+data.surveyInfoList[i].qitemNo+'_'+data.surveyInfoItemList[j].artclNo+'" placeholder="" style="height:103px;"></textarea>'+
														'</div>'+
													'</div>';
									}
								}
							}
							var div = '<div class="box shadow-box-1 py-8px px-24px mb-24px">'+
											'<p class="txt1">'+data.surveyInfoList[i].qitemCn+'</p>'+
											'<div class="content">'+
												'<div class="row">'+
													item_div +
												'</div>'+
											'</div>'+
										'</div>';
							$('#survyStaticDiv').append(div);
							surveyInfoCnt++;
						}
						$('.custum-modal').addClass('show');
					},
					error : function(request, status, error) {
						console.log("데이터 조회 오류\ncode:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
					}
				});
			} else {
				$.commMsg.alert('설문에 참여할 수 있는 기간이 아닙니다.');
			}
			
		}
		else{
			$.commMsg.alert('이미 참여한 설문입니다.');
		}		
    }
	
});

function survySubmit(){
	
	var answerCnt = 0;
	var radioSrvyArr = [];
	var radioQitmArr = [];
	var radioArtclArr = [];
	var checkSrvyArr = [];
	var checkQitmArr = [];
	var checkArtclArr = [];
	var textSrvyArr = [];
	var textQitmArr = [];
	var textArtclArr = [];
	var textDetailArr = [];
	
	for(var i=0; i<surveyInfoCnt; i++){
		if(!!$('input[name="survyRadio_'+i+'"]:checked').val()){
			var radioVal = $('input[name="survyRadio_'+i+'"]:checked').val();
			var cutText = radioVal.split('_');
			radioSrvyArr.push(cutText[0]);
			radioQitmArr.push(cutText[1]);
			radioArtclArr.push(cutText[2]);
			if(radioArtclArr){
				answerCnt++;
			}
		}
		
		if($('input:checkbox[name="survyCheckBox_'+i+'"]').is(':checked')==true){
			$('input:checkbox[name="survyCheckBox_'+i+'"]').each(function (index) {
				if($(this).is(":checked")==true){
					var checkVal = $(this).val();
					var cutText = checkVal.split('_');
					checkSrvyArr.push(cutText[0]);
					checkQitmArr.push(cutText[1]);
					checkArtclArr.push(cutText[2]);
			    }
			});
			if(checkArtclArr){
				answerCnt++;
			}
		}
		
		if(!!$('#survyTextBox_'+i+'').val()){
			var textVal = $('#survyTextBox_'+i+'').attr('class');
			var textValDetail = $('#survyTextBox_'+i+'').val();
			var cutText = textVal.split('_');
			textSrvyArr.push(cutText[0]);
			textQitmArr.push(cutText[1]);
			textArtclArr.push(cutText[2]);
			textDetailArr.push(textValDetail);
			if(textDetailArr){
				answerCnt++;
			}
		}
	}
	
	if(surveyInfoCnt == answerCnt){
		$.ajax({
			url : getUrl('/usr/inform/survey/ajax/saveSurvey.do'),
			traditional: true,
			dataType: 'text',
			data : {'radioSrvyArr' : radioSrvyArr,'radioQitmArr' : radioQitmArr,'radioArtclArr' : radioArtclArr,'checkSrvyArr' : checkSrvyArr,
				'checkQitmArr' : checkQitmArr,'checkArtclArr' : checkArtclArr,'textSrvyArr' : textSrvyArr,'textQitmArr' : textQitmArr, 'textArtclArr' : textArtclArr, 'textDetailArr' : textDetailArr},
			contentType : "application/json; charset=utf-8",
			async : false,
			type : 'GET',
			success : function(ret) {
				if(ret=='true'){
					$.ajaxUtil.success(ret, function() {
						$.commMsg.alert('성공적으로 저장되었습니다.', function() {
							// 팝업 닫기
							$('.custum-modal').removeClass('show');
							location.reload();
						});
					});	
				}
				else{
					$.commMsg.alert('설문 제출 중 오류가 발생하였습니다.\n관리자에게 문의 해 주세요.');
					$('.custum-modal').removeClass('show');
					location.reload();
				}
			}
		});
	}
	else{
		$.commMsg.alert('모든 문항을 선택 또는 입력해주세요.');
	}
}

function toDotDate(s){
	if ($.commUtil.empty(s))
		return '';
	let n = s.replace(/-/gi, "");
	
	if (n.length != 8)
		return s;
	return n.substring(0,4)+'.'+
	       n.substring(4,6)+'.'+
	       n.substring(6);
}

function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + month + day;
}
