/**
*******************************************************************************
***    명칭: modalUserInfoForm.js
***    설명: 회원관리-사용자관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.12      LHB      First Coding.
*******************************************************************************
**/
$(function() {
    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM			= $('#registForm');			// 등록폼 객체
    var P_MODE			= $('#mode'      ).val();	// 처리 모드
	var P_BZENTY_NO		= $('#bzentyNo'  ).val();	// 업체번호
	var P_RPRS_YN		= $('#rprsYnOrg' ).val();	// 대표여부
	var P_USE_STTS		= $('#useSttsOrg').val();	// 사용상태
	var P_MODAL1		= $('#myModal1');			// 기업검색 모달
	var P_GRID_ENT		= $('#grid-ent');			// 기업검색 그리드
	var P_COMBO_ROLE    = false;                    // 사용자권한 콤보박스
	var P_RADIO_RPRS    = false;                    // 대표여부 라디오박스
	
	//========================================================//
    // 목록 GRID 정의 (일반 GRID)
    //--------------------------------------------------------//
	// 업체 검색 그리드
	P_GRID_ENT.appBbsGrid({
		// 목록 KEY필드
		idField:    'bzentyNo',
		// 목록 제목
		title:		'업체목록',
		// 검색 URL
		url:		getUrl('/adm/invest/ent/getListEnt.do'),
        // 조회 파라미터 전달
		queryParams: {
	    },
		//목록 검색 페이징 객체
		pagination: { display: 10 },
		// 페이지 표시 객체
		paging:     '#appGridPagination-ent',
		// 검색결과 표시 객체
		result:     '#appGridResult-ent',
		// 검색결과 메세지
		resultText: '<em class="ml-4px">{total}</em>개의 업체가 있습니다.',
		// 리스트옵션
		listOptions: {
			// 검색결과 없을경우 메세지
			emptyText:  '검색된 {title}가 없습니다.',
			// 목록칼럼 너비
			colgroup: ['80px','300px','150px'],
			// 목록칼럼 정의
			columns: [
				{rownumbers:true, cls:'app-c', label:'번호'},
	            {name:'bzentyNm', cls:'app-l', label:'업체명'},
				{name:'brnoForm', cls:'app-c', label:'사업자등록번호'},
			],
			// 목록형인 경우 기본 스타일시트
			gridCls: "table-box-1 mh-400px",
	        // 행선택시 상세조회
	        select: function(row) {
				$('#bzentyNo'  ).val(row['bzentyNo'  ]);
				$('#bzentyNm'  ).val(row['bzentyNm'  ]);
				$('#bzentySeCd').val(row['bzentySeCd']);
				P_COMBO_ROLE.load();
				P_MODAL1.modal('hide');
				
				let secd = $.formUtil.getValue(P_RFORM, 'bzentySeCdOrg');
				let mode = $.formUtil.getValue(P_RFORM, 'mode');
				if (!$.commUtil.empty(secd) && secd != row['bzentySeCd']) {
					$.commMsg.alert([
						'업체유형이 다른 업체를 선택하셨습니다.',
						'이에 따라 사용자 권한도 함께 변경됩니다.'].join('<br>'));
				}
				// 업체가 다른 경우 계정구분은 멤버계정으로 설정
				if (P_BZENTY_NO != row['bzentyNo']) {
					$.formUtil.setValue(P_RFORM, 'rprsYn', 'N');
					// 수정시 계정구분 비활성 처리
					if (mode == MODE.UPDATE) {
						P_RADIO_RPRS.disabled(false);
					}
				}
			}
		},
    }).appBbsGrid('init');

    //========================================================//
    // FORM ELEMENTS 정의
    //--------------------------------------------------------//
	// 권한
	$('#admRoleId').appComboBox({
		url: getUrl('/com/common/getComboRole.do'),
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_NOT);
			return data;
		},
		filter: function(row) {
			let currRole = $('#admRoleId').data('value');
			if (currRole != 'ROLE_ADM_SYS' && row['code'] == 'ROLE_ADM_SYS') {
				return false;
			}
			// 표출 항목
			if (row['code'] == 'ROLE_USR_RESTRICTED' || row['code'].indexOf('ROLE_USR') > -1)
				return false;
			return true;
		}
	});
	P_COMBO_ROLE = $('#usrRoleId').appComboBox({
		url: getUrl('/com/common/getComboRole.do'),
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_NOT);
			return data;
		},
		filter: function(row) {
			let currRole = $('#usrRoleId').data('value');
			if (currRole != 'ROLE_USR_SYS' && row['code'] == 'ROLE_USR_SYS') {
				return false;
			}
			
			// 표출 항목
			if (row['code'] == 'ROLE_USR_RESTRICTED' || 
				row['code'].indexOf('ROLE_ADM') > -1)
				return false;
			
			// 2023.08.11 LSH 회원구분에 따라 가능한 권한항목 표시
			let jncd = $.formUtil.getValue(P_RFORM, 'joinCd');
			// 2023.08.11 LSH 업체구분에 따라 가능한 권한항목 표시
			let secd = $.formUtil.getValue(P_RFORM, 'bzentySeCd');
			if (jncd == 'U') {
				if (row['code'] == '') 
					return true;
				return (row['code'] == 'ROLE_USR_USR');
			} 
			else if (jncd == 'B') {
				if ($.commUtil.empty(secd)) {
					return (row['code'] != 'ROLE_USR_USR');
				}
				else {
					if      (row['code'] == 'ROLE_USR_EBZ' && secd == CODE.BZENTY_SE.ENT) return true;
					else if (row['code'] == 'ROLE_USR_MNG' && secd == CODE.BZENTY_SE.RIS) return true;
					else if (row['code'] == 'ROLE_USR_EIV' && secd == CODE.BZENTY_SE.INV) return true;
					else if (row['code'] == 'ROLE_USR_EIS' && secd == CODE.BZENTY_SE.RIS) return true;
					return false;
				}
			}
			return false;
		}
	});
	// 휴대전화국번
	$('#mblTelno1').appComboBox({
		params: {upCdId: CODE.MBL_TELNO_SE.code},
		callback: function() {
			$.formUtil.splitData('mblTelno' , 'mobile');
		}
	});
	// 유선전화
	$('#rprsTelno1').appComboBox({
		params: {upCdId: CODE.TELNO_SE.code},
		callback: function() {
			$.formUtil.splitData('rprsTelno', 'mobile');
		}
	});
	// 기업유형
	$('#appBzentySeCd').appDropdownBox({
		area:    false,
		label:   '기업유형',
		iconCls: 'icon-edit',
		input:  {id:'bzentySeCd', name:'bzentySeCd'},
		params: {upCdId: CODE.BZENTY_SE.code}
	});
	// 사용상태
	//$('#useSttsCd').appComboBox({
	//	params: {upCdId: CODE.USE_STUS.code},
	//	loadFilter: function(data) {
	//		data = data.filter(function(e) { return e.code != '9'; });
	//		return data;
	//	}
	//});
	// 대표여부
	P_RADIO_RPRS = $('#appRprsYn').appSelectBox({
		form: 'radio',
		name: 'rprsYn',
		type: 'static',
		rows: [
			{code: 'Y', text: '대표계정'},
	        {code: 'N', text: '멤버계정'},
		],
		click: function() {
			// 계정구분 변경시
			if ($(this).val() == 'Y') {
				if ($.formUtil.getValue(P_RFORM, 'bzentyNo') == '') {
					$.commMsg.alert('소속기업을 먼저 선택하셔야 합니다.');
					$.formUtil.setValue(P_RFORM, 'rprsYn', 'N');
					return false;
				}
				let nm = $.formUtil.getValue(P_RFORM, 'bzentyNm');
				$.commMsg.alert([
					'대표계정으로 설정되면',
					'해당 업체['+nm+']의 기존 대표계정은',
					'멤버계정(제한권한)으로 변경됩니다.'].join('<br>'));
				return true;
			}
		}
	});

    //========================================================//
    // VALIDATION RULE 정의
    //--------------------------------------------------------//
	// 아이디 중복체크용
	var fnRemote = {
	    type: 'post',
		url: getUrl('/adm/sys/user/checkDuplicate.do'),
		data: {
			userId: function() {
				if (P_MODE == MODE.UPDATE) {
					return '';
				}
					
				return $('#userId').val();
			}
		}
	};

    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            userId    : {required: true,
                         email: true,
                         remote: fnRemote},// 아이디 중복 체크
            userNm    : {required: true,
                         maxlength: 40},
            pswd      : {
				required: function() {
					// 등록시에만 필수
					if (P_MODE != null && P_MODE == MODE.INSERT) {
						return true;
					}
					return false;
				},
                minlength: 9,
                maxlength: 30,
				// 2023.09.08 LSH 비밀번호 검증로직 변경
				pswd: true
                //regx: /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
			},
            pswdCnfm  : {
				required: function() {
					// 등록시에만 필수
					if (P_MODE == MODE.INSERT && $('#pswdCnfm').val() != null) {
						return true;
					}
					return false;
				},
                equalTo: '#pswd'
			},
            mblTelno   : {required: true,
                          mobile: true},
            joinCd   : {
				required: function() {
					if ($.formUtil.getValue(P_RFORM, 'useSttsCd') == '0')
						return false;
					return true;
				}
			},
			bzentyNo : {
				required: function() {
					if ($.formUtil.getValue(P_RFORM, 'useSttsCd') == '0')
						return false;
					if($('input[name="joinCd"]:checked').val() == 'B') {
						return true;
					}
					return false;
				}
			},
			//useSttsCd : {required: true},
			admRoleId : {required: function() {
							if ($.formUtil.getValue(P_RFORM, 'useSttsCd') == '0')
								return false;
							if($('#usrRoleId').val() == '') {
								return true;
							}
							return false;
						}},
			usrRoleId : {required: function() {
							if ($.formUtil.getValue(P_RFORM, 'useSttsCd') == '0')
								return false;
							if($('#admRoleId').val() == '') {
								return true;
							}
							return false;
						}},
			rprsYn    : {required: function() {
							if ($.formUtil.getValue(P_RFORM, 'useSttsCd') == '0')
								return false;
							if($('input[name="joinCd"]:checked').val() == 'B') {
								return true;
							}
							return false;
						}
						},
        },
        // 검증메세지 정의
        messages: {
            userId    : {
	            required: '아이디는 필수 입력사항입니다.',
                email:    '아이디를 이메일 형식에 맞게 입력해 주세요.',
				remote: '이미 등록된 아이디입니다.'
            },
            userNm    : {required: '성명은 필수 입력사항입니다.',
                         maxlength: jQuery.validator.format('최대 {0}자 이하')},
            pswd      : {
				required: '비밀번호는 필수 입력사항입니다.',
                minlength: jQuery.validator.format('최소 {0}자 이상'),
                maxlength: jQuery.validator.format('최대 {0}자 이하'),
				// 2023.09.08 LSH 비밀번호 검증로직 변경
				pswd: '비밀번호를 8자이상 30자이내의 영대소문자 + 숫자 + 특수문자 조합으로 입력해 주세요.'
                //regx: '비밀번호 형식이 잘못되었습니다.'
            },
            pswdCnfm  : {
				required: '비밀번호 확인은 필수 입력사항입니다.',
                equalTo: '입력한 비밀번호가 서로 일치하지 않습니다.'
            },
            mblTelno   : {
				required: '휴대전화는 필수 입력사항입니다.',
				mobile:   '휴대전화 형식이 잘못되었습니다.'
			},
			joinCd : {
				required: '회원구분은 필수 입력사항입니다.',
			},
			bzentyNo : {
				required: '소속기업은 필수 선택사항입니다.',
			},
			//useSttsCd : {
			//	required: '사용상태는 필수 선택사항입니다.',
			//},
			admRoleId : { required: '최소 한 가지 권한은 필수 선택사항입니다.' },
			usrRoleId : { required: '최소 한 가지 권한은 필수 선택사항입니다.' },
			rprsYn : { required: '계정구분은 필수 선택사항입니다.'}
        },
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });

	// 사용자정보 저장하기
    //--------------------------------------------------------//
    function doSave() {

		// 휴대전화 병합
		$.formUtil.mergeData('mblTelno',  'mobile', 3);
		// 대표번호 병합
		$.formUtil.mergeData('rprsTelno', 'mobile', 3);

        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

		var rprsYn	= $.formUtil.getValue(P_RFORM, 'rprsYn');
		var message = '저장하시겠습니까?';
		if (rprsYn != P_RPRS_YN && 
			rprsYn == 'Y') {
			message += '<br>(대표계정을 선택한 경우 기존의 대표계정은 멤버계정으로 변경됩니다.)';
		}

        $.commMsg.confirm(message, function() {
			// 일반회원인 경우 업체정보 초기화
			if ($.formUtil.getValue(P_RFORM, 'joinCd') == 'U') {
				$.formUtil.setValue(P_RFORM, 'bzentyNo', '');
				$.formUtil.setValue(P_RFORM, 'bzentyNm', '');
			}
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/user/saveUserInfo.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 저장되었습니다.', function() {
                        P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 아이디 중복확인
    //--------------------------------------------------------//
    function doDuplicate() {
        var params = $.formUtil.toObject(P_RFORM, ['userId']);
        if ($.commUtil.empty(params['userId'])) {
            $.commMsg.alert('사용자ID를 입력하세요.');
            return false;
        }
		var check = $.ajaxUtil.ajaxDefault(
					getUrl('/adm/sys/user/checkDuplicate.do'),
					params);
		if (check) $.commMsg.alert('사용가능한 아이디입니다.');
		else       $.commMsg.alert('이미 등록된 아이디입니다.');
        return false;
    }

	// 업체 조회 이벤트
    //--------------------------------------------------------//
	function doSearchEnt() {
		
		// 업체대표는 업체변경 불가처리
		if (P_RPRS_YN == 'Y')
			return false;
		
		$('#myModal1').modal('show');

		// 검색폼 데이터 객체화
        var obj = $('#prgrsForm1').serializeObject();

        // 검색폼 그리드 검색
        P_GRID_ENT.appBbsGrid('search', obj);

        return true;
	}

	// 사용자 삭제하기
    //--------------------------------------------------------//
    function doRemove() {

		// 중지상태인지 확인
		if (P_USE_STTS != '1') {
			$.commMsg.alert('이미 삭제 완료된 계정입니다.');
			return false;
		}
		// 업체대표는 삭제불가
		if (P_RPRS_YN == 'Y') {
			$.commMsg.alert([
				'업체대표는 삭제가 불가합니다.',
				'업체대표를 삭제하시려면',
				'대표계정을 다른 회원에게 위임하신 후',
				'삭제하시기 바랍니다.'
			].join('<br>'));
			return false;
		}
        $.commMsg.confirm("정말로 삭제하시겠습니까?", function() {
			$.formUtil.toForm({
				mode     : MODE.REMOVE, // 삭제모드
			}, P_RFORM);
	
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/user/saveUserInfo.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
					$.commMsg.success(ret, '성공적으로 삭제되었습니다.', function() {
                        P_MODAL.doSearch();
					});
                }
            }).submit();
        });
        return false;
    }

	// 회원유형 클릭 이벤트
    //--------------------------------------------------------// 
	function doChangeJoinCd() {
		var value = $.formUtil.getValue(P_RFORM, 'joinCd');
		if (value == 'U') {
			// 개인 회원
			$('div.ent').hide();
		} else if (value == 'B') {
			// 기업 회원
			$('div.ent').show();
		}
		// 권한 재구성
		P_COMBO_ROLE.load();
	}

	// 대표계정인 경우 초기화 처리
    //--------------------------------------------------------// 
	function doSetRprs( rprsYn ) {
		// 대표계정인 경우
		// 업체변경 불가
		// 멤버전환 불가 (다른계정의 대표설정으로 처리됨)
		// 삭제버튼 제외
		if (rprsYn == 'Y') {
			$('#bzentyNm').addClass('app-readonly');
			P_RADIO_RPRS.disabled(true);
			$('#btnRemove').hide();
		}
		return true;
	}

    // 비밀번호 초기화
    //--------------------------------------------------------//
    function doResetPswd() {
		
		var params = {userNo: $.formUtil.getValue(P_RFORM, 'userNo')};
		let userNm = $.formUtil.getValue(P_RFORM, 'userNm');
		let userId = $.formUtil.getValue(P_RFORM, 'userId');
		
		$.commMsg.confirm([
			'비밀번호 초기화를 실행하시면',
			'임시 생성한 비밀번호가',
			'[<b class="text-orange">'+userNm+'</b>] 회원에게',
			'[<b class="text-green">'+userId+'</b>] 이메일로 전송되며,',
			'비밀번호 오류횟수가 초기화됩니다.',
			'비밀번호 초기화를 진행하시겠습니까?'].join('<br>'), function() {
	        // AJAX로 저장처리
	        $.ajaxUtil.ajaxSave(
	            getUrl('/adm/sys/user/savePswdReset.do'), 
	            JSON.stringify(params),
	            function(ret) {
                    $.ajaxUtil.success(ret, function() {
						$.commMsg.alert('성공적으로 초기화되었습니다.', function() {
							P_MODAL.doSearch();
						});
                    });
	            }
	        );
		});
    }

    // 저장버튼 클릭시 이벤트 처리
    $('#btnSave'  ).bind('click', doSave);
	// 삭제버튼 클릭시 이벤트 처리
    $('#btnRemove').bind('click', doRemove);
	// 업체 조회버튼 클릭시 이벤트 처리
	$('#bzentyNm, #btnBzenty').bind('click', doSearchEnt);
	// 업체명/사업자 등록번호 엔터 입력시 이벤트 처리
	$('#srchTextEnt').bind('keydown', function(e) {
		if (e.keyCode != 13)
			return;
		doSearchEnt();
	});
	
    // 취소버튼 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
	
	// 회원유형 클릭시 이벤트처리
    //--------------------------------------------------------//
    $('#btnCancel').bind('click', function() {
		// 부모창의 doClose 호출
		P_MODAL.doClose();
        return false;
	});
	
	// 기업 검색 모달 팝업시 발생하는 함수
	P_MODAL1.on('shown.bs.modal', function (e) {
		$('.modal-backdrop').hide();
	});
	
	// ID 중복확인 클릭 이벤트 처리
	$('#btnDuplicate').bind('click', doDuplicate);
	
	// 회원유형 클릭 이벤트 처리
	$('input[name="joinCd"]').bind('click', doChangeJoinCd);

	// 비밀번호 초기화버튼 클릭시 이벤트 처리
	$('#btnResetPswd').bind('click', doResetPswd);

	// 휴대전화, 대표번호 세팅
	$.formUtil.splitData('mblTelno' , 'mobile');
	$.formUtil.splitData('rprsTelno', 'mobile');
	
	if (P_BZENTY_NO) {
		$('#joinCdB').click();
		$('div.ent').show();
	} else {
		$('#joinCdU').click();
		$('div.ent').hide();
	}
	
	if (P_MODE != MODE.INSERT) {
		$('#userId').prop('readonly', 'true');
		// 대표계정시 처리
		doSetRprs(P_RPRS_YN);
	} else {
		$('#userId').valid();
	}
	
	// datepicker 활성화
	$('.datepicker-input').datepicker(OPTIONS.DATEPICKER);
});
