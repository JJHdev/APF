/**
******************************************************************************************
*** 파일명 : viewChgPwd.js
*** 설명글 : 사용자 비밀번호 변경 화면 스크립트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021.12.30    CSLEE
******************************************************************************************
**/
$(function() {

    //========================================================//
    // 화면 스크립트 내에 사용할 객체,상수 정의
    //--------------------------------------------------------//
    var P_RFORM  = $('#registForm' ); // 등록폼 객체

    //========================================================//
    // 등록폼 VALIDATION RULE 정의
    //--------------------------------------------------------//
    P_RFORM.validate({
        debug: false,
        onsubmit: false,
        onfocusout: false,
        // 검증룰 정의
        rules: {
            pswd      : {
                required: true,
                minlength: 9,
                maxlength: 30,
                regx: /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
            },
            pswdCnfm  : {
                required: true,
                equalTo: '#pswd'
            },
        },
        // 검증메세지 정의
        messages: {
            pswd      : {
                required: '비밀번호는 필수 입력 사항입니다.',
                minlength: jQuery.validator.format('최소 {0}자 이상'),
                maxlength: jQuery.validator.format('최대 {0}자 이하'),
                regx: '비밀번호 형식이 잘못되었습니다.'
            },
            pswdCnfm  : {
                required: '비밀번호 확인은 필수 입력 사항입니다.',
                equalTo: '입력한 비밀번호가 서로 일치하지 않습니다.'
            },
        },
        //invalidHandler: $.easyValidate.handler,
        //errorPlacement: $.easyValidate.placement
        invalidHandler: validateHandler,
        errorPlacement: validatePlacement
    });

    //========================================================//
    // FUNCTION 정의
    //--------------------------------------------------------//

    // 최초 Loading 수행
    //--------------------------------------------------------//
    function initLoad(){
        // 버튼 초기화
        $(".btnWrap a").attr("href", "javascript:void(0);");
    }

    // 사용자정보 저장하기
    //--------------------------------------------------------//
    function doMain(){
        goUrl(getUrl('/adm/main/main.do'));
    }

    // 비밀번호 변경하기
    //--------------------------------------------------------//
    function doSave() {
        // 등록폼의 VALIDATION 기능 활성화
        if (P_RFORM.validate().settings)
            P_RFORM.validate().settings.ignore = false;

        //FORM VALIDATION
        if (P_RFORM.valid() === false)
            return false;

        $.commMsg.confirm("비밀번호를 변경하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/user/savePswdSelf.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
                    console.log("## ret :", ret);
                    if(ret && ret.Code == 0){
                        $.commMsg.success(ret, ret.Message, function() {
                            doMain();
                        });
                    }
                    else {
                        $.commMsg.alert( ret.Message );
                    }

                }
            }).submit();
        });
        return false;
    }

    // 다음에 변경하기로
    //--------------------------------------------------------//
    function doSaveNext() {

        $.commMsg.confirm("다음에 변경하시겠습니까?", function() {
            // 등록폼을 AJAX로 저장처리
            P_RFORM.ajaxForm({
                url: getUrl('/adm/sys/user/savePswdSelfNext.do'),
                // 오류시 처리로직
                error: $.ajaxUtil.error,
                // 저장후 처리로직
                success: function(ret) {
                    if(ret && ret.Code == 0){
                        doMain();
                    }
                    else {
                        $.commMsg.alert( ret.Message );
                    }
                }
            }).submit();
        });
        return false;
    }

    //========================================================//
    // 이벤트 연결
    //--------------------------------------------------------//

    // '변경' 버튼
    $('#btnSave').bind('click', doSave);
    // '다음에 변경' 버튼
    $('#btnNext').bind('click', doSaveNext);

    initLoad();
});