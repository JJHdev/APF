/**
*******************************************************************************
***    명칭: viewData.jsp
***    설명: 정보서비스 - 자료실 상세보기 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
*******************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    let P_FILE_TYPE = CODE.FILE_TYPE.BBS; // 첨부파일 종류
    let P_FILE_BOX  = $('#attachFile' );  // 첨부파일 컨트롤 객체
    let P_PST_NO    = $('#pstNo'  ).val();// 게시글 키정보
    let P_BBS_CD    = $('#bbsSeCd').val();// 게시판 구분코드
    let P_RFORM     = $('#selectForm' );  // 등록폼 객체
    
    // 삭제하기
    //--------------------------------------------------------//
    function doRemove() {
        $.commMsg.confirm("정말 삭제하시겠습니까?", function() {
            // AJAX로 삭제처리
            $.ajaxUtil.ajaxLoad(
                getUrl('/usr/inform/bbs/saveData.do'), 
                {pstNo   : P_PST_NO,
				 bbsSeCd : P_BBS_CD,
                 mode    : MODE.REMOVE
                }, 
                function(ret) {
                    $.ajaxUtil.success(ret, function() {
                        // 목록으로 이동
                        $.commMsg.alert('성공적으로 삭제처리되었습니다.', function() {
							// 부모창의 목록재검색 처리 및 팝업닫기
                        	goList();
						});
                    });
                }
            );
        });
        return false;
    }
    
    function doSearch() {
        // 검색폼 데이터 객체화
        var obj = P_FORM.serializeObject();
        // 검색폼 그리드 검색
		P_GRID.appBbsGrid('search', obj);
        return false;
    }

    //========================================================//
    // 첨부파일 초기화 (app_bbsfile.js 참고)
    //--------------------------------------------------------//
    P_FILE_BOX.appBbsFile({
        label: false,
        // 처리모드
        mode: MODE.VIEW,
        initData: {
            fileType: P_FILE_TYPE,
            docuCd:   P_BBS_CD,
            docuNo:   P_PST_NO
        }
    }).appBbsFile('init');

    // 목록이동
    //--------------------------------------------------------//
    function goList() {
           $.formUtil.submitForm(getUrl("/usr/inform/bbs/listData.do"), {
               formId : "searchForm"
           });
        return false;
    }
   
	$('#btnModify').bind('click', function() {
		$('<div></div>').appPopupData({
			pstNo :  P_PST_NO,
			bbsSeCd: P_BBS_CD
		}, goList);
	});
	
  // [팝업] 글수정 팝업
  //=============================================================================
  $.fn.appPopupData = function( params, saveCallback ) {
    let P_CONTENT   = 'p_pstCn';            // 웹에디터 칼럼명
  	if (!params) {
  		$.commMsg.alert('조회조건을 확인할 수 없습니다.');
  		return false;
  	}

  	let options = {
  		title     : '자료실 글 수정하기',
  		icon      : '<img src="'+getUrl('/images/sub/Sketch_on_smartphone.svg')+'">',
  		loadUrl   : getUrl('/usr/inform/bbs/modalDataForm.do'),
  		loadParams: {pstNo : params['pstNo']},
  	};

  	return $(this).appPopup({
  		url:        options.loadUrl,
  		params:     JSON.stringify(options.loadParams),
  		title:      options.title,
  		icon:       options.icon,
  		type:       'pageload',
  		dialogCls:  'w-ver1',
  		appendBody: true,
  		onloaded:   function(pop) {
  			// 입력폼
  			let pform = $('#p_registForm');
  			// 파일객체
  			let pfile = $('#p_appBbsFile');
  			
  			// 분류박스 정의
  			$('#appTopic').appDropdownBox({
  				title   : '토픽<em></em>',
  				iconCls : 'icon-edit',
  				input   : {id: 'p_pstClsfCd', name: 'pstClsfCd', value: ''},
  				params  : {upCdId: CODE.NTT_CL.code, cdIdStart: params['bbsSeCd']},
  				callback: function(){
  				    var dropdownId = "appTopic";
  				    var selectedValue = $('#p_pstClsfCd').val();
  				    var $dropdown = $('#' + dropdownId);

  				    $dropdown.find('.dropdown-item').each(function() {
  				        var $item = $(this);
  				        if ($item.attr('data-value') === selectedValue) {
  				            $item.addClass('active');
  				            $dropdown.find('.dropdown-toggle').text($item.text());
  				        } else {
  				            $item.removeClass('active');
  				        }
  				    });
  				}
  			});

  			// 공지글표시 정의
  			$('#appNoticeYn').appDropdownBox({
  				title   : '공지글 표시<em></em>',
  				type    : 'static',
  				iconCls : 'icon-edit',
  				input   : {id: 'p_fixingYn', name: 'fixingYn', value: ''},
  				rows    : STORE.NTC_YN,
  				select  : function(v) {
  					if (v == 'Y') {
						$('#p_fixingBgngYmd').prop('disabled', false);
						$('#p_fixingEndYmd' ).prop('disabled', false);
					}
					else {
						$('#p_fixingBgngYmd').prop('disabled', true);
						$('#p_fixingEndYmd' ).prop('disabled', true);
  					}
  				},
  				callback: function(){
  				    var dropdownId = "appNoticeYn";
  				    var selectedValue = $('#p_fixingYn').val();
  				    var $dropdown = $('#' + dropdownId);

  				    if(selectedValue == 'N'){
  						$('#p_fixingBgngYmd').prop('disabled', true);
  						$('#p_fixingEndYmd' ).prop('disabled', true);
  				    }

  				    $dropdown.find('.dropdown-item').each(function() {
  				        var $item = $(this);
  				        if ($item.attr('data-value') === selectedValue) {
  				            $item.addClass('active');
  				            $dropdown.find('.dropdown-toggle').text($item.text());
  				        } else {
  				            $item.removeClass('active');
  				        }
  				    });
  				}
  			});
  			// 달력설정 (comm_const.js 참고)
  			$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);
  			
  			// 첨부파일영역 정의 (app_bbsfile.js 참고)
  			pfile.appBbsFile({
  				cls: 'border-0 mb-0 pb-0',
  				// 처리모드
  				mode: MODE.UPDATE,
  				// 초기 표시 갯수
  				initCount: 1,
  				// 추가 최대 갯수
  				maxCount:  3,
  				// 다중 가능 여부				
  				multiple: true,
  				// 하단 메세지
  				message: '※ 500MB 이내 3개 파일 가능, 여러 파일 업로드 희망 시 압축파일 권장',
  				// 파일 조건 데이터
  				initData: {
					fileSe	: CODE.FILE_SE.FILE,
		            fileType: CODE.FILE_TYPE.BBS,
					docuCd	: BBS_TASK_CD.CODE,
		            docuNo	: params['pstNo'] // 수정인 경우 pstNo 설정해야함
  				}
  			}).appBbsFile('init');
  			
		//--------------------------------------------------------//
		// CKEDITOR 초기화 (comm_ckeditor.js 참고)
		// 개인적인 설정
		const BBS_CKEDITOR_CONFIG = {
		    extraPlugins: 'wordcount',
		    wordcount: {
		        showParagraphs: true,
		        showWordCount: true,
		        showCharCount: true,
		        countSpacesAsChars: true,
		        countHTML: false,
		        maxWordCount: -1,
		        maxCharCount: 20000,
		    }
		};
		
		// 공통 설정과 개인적인 설정을 병합
		const FINAL_CONFIG = Object.assign({}, CKEDITOR_CONFIG, BBS_CKEDITOR_CONFIG);
		
		// CKEDITOR 초기화 함수
		const CKEDITOR_INIT = function( id ) {
			if (CKEDITOR) {
			    // CKEDITOR 설정바인딩
				CKEDITOR.replace(id, FINAL_CONFIG);
				
			    // 2022.10.26 ntarget 추가 (이미지업로드 탭 정의)
			    CKEDITOR.on('dialogDefinition', function (ev) {
			        var dialogName = ev.data.name;
			        var dialog = ev.data.definition.dialog;
			        var dialogDefinition = ev.data.definition;
			
			        if (dialogName == 'image') {
			            dialog.on('show', function (obj) {
			                this.selectPage('Upload'); //업로드탭으로 시작
			            });
			            
			            dialogDefinition.removeContents('advanced'); // 자세히탭 제거
			            dialogDefinition.removeContents('Link'); // 링크탭 제거
			        }
			    });
			}
		};

		$(function() {
			CKEDITOR_INIT(P_CONTENT);
		});
		
  			// 게시시작일 보다 종료일 초과 X
  			jQuery.validator.addMethod("greaterThan", function(value, element, params) {
  		        if (!/Invalid|NaN/.test(new Date(value))) {
  		            return new Date(value) >= new Date($(params).val());
  		        }
  		        return isNaN(value) && isNaN($(params).val()) 
  		            || (Number(value) >= Number($(params).val())); 
  		    },'종료 날짜는 시작 날짜보다 이후이어야 합니다.');
  			
  		    //========================================================//
  		    // VALIDATION RULE 정의
  		    //--------------------------------------------------------//
  		    pform.validate({
  		        // true일 경우 디버깅이 가능하도록 
  		        // 입력값이 유효해서 submit하지 않는다.
  		        debug: false,
  		        // true일 경우 포커스가 떠날때 유효성 검사를 한다.
  		        onfocusout: false,
  		        // true일 경우 유효성체크없이 무조건 submit한다.
  		        onsubmit: false,
  		        // 검증룰 정의
  		        rules: {
  		                pstTtl     : 'required', // 제목 필수입력
  		                pstClsfCd  : 'required', // 분류 필수선택
  		                pstCn      : 'required', // 내용 필수입력
		    			fixingYn: {
		    				required: function() {
		    					 // 게시기간 기입 시 상단고정 Y체크 필요
		    		            if ($('#p_fixingBgngYmd').val() || $('#p_fixingEndYmd').val())
		    		                return true;
		    		            return false;
		    				}
		    			},
		    			fixingBgngYmd: {
		    				required: function() {
		    					// 상단고정 사용자 설정 시에만 유효성 검사 진행
		    					if ($('#p_fixingYn').val() === 'Y')
		    						return true;
		    					return false;
		    				},date: true
		    			},
		    			fixingEndYmd: {
		    				required: function() {
		    					// 상단고정 체크시에만 유효성 검사 진행
		    					if ($('#p_fixingYn').val() === 'Y')
		    						return true;
		    					return false;
		    				},date: true,
		    				greaterThan: '#p_fixingBgngYmd',
		    			}
  		        },
  		        // 검증메세지 정의
  		        messages: {
  		                pstTtl     		: '제목은 필수 입력 사항입니다.',
  		                pstClsfCd  		: '분류는 필수 선택 사항입니다.',
  		                pstCn      		: '내용은 필수 입력 사항입니다.',
		                fixingYn        : '게시기간을 기입시 상단고정여부는 필수 입력 사항입니다.',
		    			fixingBgngYmd   : {required : '공지글 표시 선택시 게시기간은 필수 입력 사항입니다.',
		    							   date		: '날짜 형식에 맞게 입력해주세요'},
		    			fixingEndYmd    : {required : '공지글 표시 선택시 게시기간은 필수 입력 사항입니다.',
							   			   date		: '날짜 형식에 맞게 입력해주세요',
							   			greaterThan : '종료 날짜는 시작 날짜보다 이후이어야 합니다.' }
  		        },
  		        // 에러발생시 에러메세지를 처리할 핸들러 (comm_biz.js에 정의)
  		        invalidHandler: validateHandler,
  		        // 에러발생시 에러메세지를 표시할 위치처리 핸들러 (comm_biz.js에 정의)
  		        errorPlacement: validatePlacement
  		    });
  			
  			// 작성완료버튼 클릭 이벤트 처리
  			$('#p_btnSave').bind('click', function() {

  		        // CKEDITOR 내용 업데이트
  		        CKEDITOR.instances['p_pstCn'].updateElement();
  		
  		        // 등록폼의 VALIDATION 기능 활성화
  		        if (pform.validate().settings)
  		            pform.validate().settings.ignore = false;
  		
  		        //FORM VALIDATION
  		        if (pform.valid() === false)
  		            return false;
  		
  				// 첨부파일 업로드 VALIDATION (app_bbsfile.js)
  				if (pfile.appBbsFile('validate', {
  						isAlert: true,
  						isExt:   true,
  						isLimit: true
  					}) === false)
  		            return false;
  			
  		    	$.commMsg.confirm('자료를 수정하시겠습니까?', function() {
  			
  					// 등록모드 설정
  					pform.find('[name="mode"]').val(MODE.UPDATE);
  					
  		            // 등록폼을 AJAX로 저장처리
  		            pform.ajaxForm({
  		                url: getUrl('/usr/inform/bbs/saveData.do'),
  		                enctype : 'multipart/form-data',
  		                // 오류시 처리로직
  		                error: $.ajaxUtil.error,
  		                // 저장후 처리로직
  		                success: function(ret) {
  		                	$.ajaxUtil.success(ret, function() {
  		                    	$.commMsg.alert('성공적으로 저장되었습니다.', function() {
  									// 저장후 콜백함수 실행
  									saveCallback();
  									// 팝업 닫기
  									pop.close();
  									return false;
  								});
  		                	});
  		                }
  		            }).submit();
  		    	});
  		        return false;
  			});
  			// 취소 클릭 이벤트 처리
  			$('#p_btnCancel').bind('click', function() {
  				pop.close();
  				return false;
  			});
  		}
  	}).open();
  };
       
    //========================================================//
    // 버튼 이벤트 처리
    //--------------------------------------------------------//
    // 목록버튼 클릭시 이벤트 처리
    $('#btnList'  ).bind('click', goList);
    // 삭제버튼 클릭시 이벤트처리
    $('#btnRemove').bind('click', doRemove);
});
