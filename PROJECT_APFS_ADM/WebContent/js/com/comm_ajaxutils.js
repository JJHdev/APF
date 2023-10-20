/**
******************************************************************************************
*** 파일명    : comm_formutils.js
*** 설명      : 공통 AJAX 처리 관련 JavaScript
***             $.ajaxUtil
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-02-10              ntarget
******************************************************************************************
**/

/**
 * 공통 ajax 처리
 */
$.ajaxSetup({ cache: false });
$.ajaxUtil = {
    // Ajax default (타입 json)
    // return true, false, data
    // $.ajaxUtil.ajaxDefault(url, param)
    ajaxDefault: function (url, param) {
        var isResult = true;

        $.ajax({
            url: url,
            data: (param ? param : {}),
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data == 'true') {
                    isResult = true;
                } else if (data == 'false') {
                    isResult = false;
                }
                else {
                    isResult = data;
                }
            }, error: function (request, status, error) {
                // error 처리(권한)
                if (request.status == '401' || request.status == '403') {
                    // MSG : 권한이 없거나 세션이 만료되었습니다.
                    alert(MSG_COMM_E016);
                }
                else {
                    // MSG : 시스템 오류가 발생하였습니다.
                    alert(MSG_COMM_1002);
                }
                isResult = 'syserr';
            }
        });

        return isResult;
    },
    // Ajax 배열 전송 (타입 json)
    // return true, false, data;
    // $.ajaxUtil.ajaxArray(url, param)
    ajaxArray: function (url, param) {
        $.ajaxSettings.traditional = true;
        var isResult = true;

        $.ajax({
            url: url,
            data: param,
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data == 'true') {
                    isResult = true;
                } else if (data == 'false') {
                    isResult = false;
                }
                else {
                    isResult = data;
                }
            },
            error: function (request, status, error) {
                // error 처리(권한)
                if (request.status == '401' || request.status == '403') {
                    alert(MSG_COMM_E016);
                }
                else {
                    alert(MSG_COMM_1002);
                }
                isResult = 'syserr';
            }
        });

        return isResult;
    },
    /**
     * Ajax (타입 Html) 
     * 사용 예)
     * var result = $.ajaxUtil.loadHtml("test-tab", ROOT_PATH +"/usr/temp/listTemp.do", {
     *                  testParam  : "test",
     *                  testParam2 : "test2"
     *              });
     */        
    ajaxHtml : function(url, param, etcOption) {
        var isResult = true;

        etcOption = etcOption ? etcOption : {};

        $.ajax({
            url: url,
            data: param,
            type: 'post',
            dataType: 'html',
            async: false,
            success: function (data) {
                isResult = data;
            },
            error: function (request, status, error) {

                if (etcOption.errorFn && etcOption.errorFn != null && $.type(etcOption.errorFn) == "function") {
                    errorFn(request, status, error);
                }
                else {
                    // error 처리(권한)
                    if (request.status == '401' || request.status == '403') {
                        alert(MSG_COMM_E016);
                    }
                    else {
                        alert(MSG_COMM_1002);
                    }
                }
                isResult = 'syserr';
            }
        });

        return isResult;
    },
    /**
     * jquery-form 플러그인을 이용한..... SUBMIT 기능구현
     * 
     * 파일첨부 가능
     * 
     * jquery.form.js 파일이 포함되어야 함.
     * arg 속성 :
     *     - formId   : 대상 fora태그의 id
     *     - target   : form의 target 속성에 적용 (기본값:_self)
     *     - params   : 추가 파라미터 정의
     *
     * 사용 예)
     *      var result = $.ajaxUtil.ajaxForm(ROOT_PATH+"/usr/temp/deltSampleJson.do", {
     *          formId   : "form1",
     *          target   : "_self",
     *          params   : {
     *              aaaa : "aaaval",
     *              bbbb : "bbbval"
     *          }
     *      })
     *
     * 사용 예2) 기본은 async 속성이 false로 됨, 동시에 여러 ajax를 수행할 수 없음. true로 바꾸기 위해
     *           'asyncSuccess' 함수를 구현해주면 async 속성이 true 로 변경되면서 구현된 함수를 통해 결과를 처리할 수 있음.
     *           이 때 리턴받은 result는 의미 없음
     *
     *      var result = $.ajaxUtil.ajaxForm(ROOT_PATH+"/usr/temp/regiSampleExcel.do", {
     *          formId       : "form1",
     *          asyncSuccess : function(result){
     *              // 결과 처리
     *              if (result && result.rtnCode == '1') }{
     *                  ...
     *              }
     *      });
     */
    ajaxForm : function(url, args) {
        var isResult = true ;
        var errMsg   = null ;
        var isAlert  = false;   // alert 창으로 설정오류 표시 여부

        if (!args) {
            errMsg = '[ajaxUtil.ajaxForm] : 함수인자가 정의되지 않았습니다.';
            if (isAlert) alert(errMsg);
            return false;
        }
        if (!args.formId || args.formId == undefined || args.formId == null) {
            errMsg = '[ajaxUtil.ajaxForm] : 대상 FORM의 아이디가 지정되지 않았습니다.';
            if (isAlert) alert(errMsg);
            return false;
        }

        //----------------------------
        // form 객체 및 form 기본설정
        //----------------------------
        var $tgFormObj = $('#' + args.formId);

        if ($tgFormObj.length == 0) {
            errMsg = '[ajaxUtil.ajaxForm] : 지정된 FORM이 존재하지 않습니다.';
            if (isAlert) alert(errMsg);
            return false;
        }

        var enctypeVal  = 'multipart/form-data';    // enctype 기본 값

        //----------------------------
        // 파일객체 확인 및 객체 name 재설정
        //----------------------------
        var $fileObjs   = $tgFormObj.find('input[type=file]');
        if ($fileObjs.length > 0){
            // multipart로 변경
            enctypeVal  = 'application/x-www-form-urlencoded';

            // 파일 객체의 name 항목이 동일하지 않게 변경(동일하면 1개로 인식되는 문제있음)
            var fileIdx = 0;
            $fileObjs.each(function(){

                var $fileObj = $(this);
                var fileObjNm = "";
                var orgObjNm  = $fileObj.data("org-name");

                if(orgObjNm != undefined && orgObjNm != null && orgObjNm != ""){
                    fileObjNm = orgObjNm;
                }
                else {
                    fileObjNm = $fileObj.attr("name");
                    // 원래명 백업
                    $fileObj.attr("data-org-name", fileObjNm);
                }
                // 파일명을 순서대로 _0, _1, _2, ...를 붙여 준다
                $fileObj.attr("name", fileObjNm+"_"+fileIdx)

                fileIdx ++;
            });
        }

        //----------------------------
        // 최종 option 설정 및 submit 처리
        //----------------------------
        var formOptions = {
            //action  : (args.url),
            method: ((args.target) ? args.target : '_self'),
            enctype: enctypeVal,
        };
        if (url != null) {
            formOptions.action = url;
        }

        // form 객체 기본 속성설정
        $tgFormObj.attr(formOptions);

        // jquery-form 속성 설정
        var option = {
            dataType: 'json',
            processData: false,
            //contentType  : false,
            type: 'POST',
            data: args.params || {},
            async: false,
            success: function (result) {
                isResult = result;
            },
            error: function (request, status, error) {
                console.log(request.status +"/"+ request.responseText );
                // error 처리(권한)
                if (request.status == '401' || request.status == '403') {
                    alert(MSG_COMM_E016);
                }
                else {
                    alert(MSG_COMM_1002);
                }
                isResult = 'syserr';
            }
        }

        //----------------------------
        // async 속성을 false로 해서 진행하고 싶을 때
        // asyncSuccess (함수) 속성을 정의하면 됨
        //----------------------------
        var asyncSuccessFn = args.asyncSuccess;
        if (asyncSuccessFn && asyncSuccessFn != null && $.type(asyncSuccessFn) =="function" ) {
            option.async = true;
            option.success = function(result){
                asyncSuccessFn(result);
            }
        }

        //----------------------------
        // 최종 submit 처리
        //----------------------------
        // ajaxForm을 설정 (jquery-form plugin)
        $tgFormObj.ajaxForm(option);
        // 설정후 submit 처리하면 수행 됨.
        $tgFormObj.submit();

        //----------------------------
        // 파일 객체명 원복
        // - 파일 객체명(name)이 변경됐을 때 발생할 문제 방지를 위해 원래대로 변경
        //----------------------------
        $fileObjs.each(function(){
            var $fileObj = $(this);
            var orgObjNm = $fileObj.data("org-name");
            $fileObj.attr("name", orgObjNm);
        });

        return isResult;
    },
    /**
     * Ajax Load (타입 json)
     * return true, false
     * AJAX 로딩후 결과데이터를 받아서 처리하는 콜백함수를 인자로 받는다.
     * $.ajaxUtil.ajaxLoad(url, param, callback)
     * 2021.07.15 LSH add
     */
    ajaxLoad: function (url, param, callback) {
        var isResult = true;

        $.ajax({
            url: url,
            data: param,
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data == 'true') {
                    isResult = true;
                } else if (data == 'false') {
                    isResult = false;
                }
                else {
                    isResult = data;
                	if (callback) {
                		callback(data);
                	}
                }
            }, error: function (request) {
				isResult = $.ajaxUtil.error(request);
            }
        });
        return isResult;
    },
    /**
     * Ajax Save (타입 json List)
     * return true, false
     * JSON LIST 데이터를 저장한다.
     * $.ajaxUtil.ajaxSave(url, param, callback)
     * 2021.08.10 LSH add
     */
    ajaxSave: function (url, param, callback) {
        $.ajax({
            url: url,
            data: param,
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                if (data && callback) {
               		callback(data);
                }
            },
            error: $.ajaxUtil.error
        });
    },
    ajaxGet: function (url, param) {
        var isResult = true;

        $.ajax({
            url: url,
            data: (param ? param : {}),
            type: 'get',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data == 'true') {
                    isResult = true;
                } else if (data == 'false') {
                    isResult = false;
                }
                else {
                    isResult = data;
                }
            }, error: function (request, status, error) {
                // error 처리(권한)
                if (request.status == '401' || request.status == '403') {
                    // MSG : 권한이 없거나 세션이 만료되었습니다.
                    alert(MSG_COMM_E016);
                }
                else {
                    // MSG : 시스템 오류가 발생하였습니다.
                    alert(MSG_COMM_1002);
                }
                isResult = 'syserr';
            }
        });

        return isResult;
    },
    /**
     * AJAX 통신 결과 메세지 처리
     * 2022.02.07 LSH ADD
     */
	result: function (ret, callback) {
		let r = $(ret);
		if (r.find('.exception_detail_message') &&
			r.find('.exception_detail_message').length > 0) {
			$.commMsg.alert(r.find('.exception_detail_message').html());
			return false;
		}
		if (r.find('.exception_message') &&
			r.find('.exception_message').length > 0) {
			$.commMsg.alert(r.find('.exception_message').html());
			return false;
		}
		if (r.find('h3.error') &&
			r.find('h3.error').length > 0) {
			$.commMsg.alert(r.find('h3.error').html());
			return false;
		}
		if (ret['Code'] < 0) {
			let msg = '[Code:' + ret['Code'] + '] ';
			if (ret['Messages'])
				msg += ret['Messages'].join('<br>');
			else if (ret['Message'])
				msg += ret['Message'];
			$.commMsg.alert(msg);
			return;
		}
		else {
			if (callback) 
				callback(ret);
		}
	},
    /**
     * AJAX 통신 오류 발생시 공통 처리 로직
     * ajax의 error 함수에 맵핑하여 사용한다.
     * 2021.08.11 LSH ADD
     */
    error: function(request) {
        // error 처리(권한)
        if (request.status == '401' || request.status == '403') {
            // MSG : 권한이 없거나 세션이 만료되었습니다.
            alert(MSG_COMM_E016);
        }
        else {
			// 2022.02.07 결과메세지 공통처리
			$.ajaxUtil.result(request.responseText, function() {
                // MSG : 시스템 오류가 발생하였습니다.
                alert(MSG_COMM_1002);
			})
        }
    },
    /**
     * AJAX 통신 결과 공통 처리 로직
     * ajax의 success 함수에서 필요시 사용한다.
     * 2021.08.11 LSH ADD
     * 2021.12.01 LSH 에러페이지 확인로직 추가
     */
    success: function(data, callback) {
		// 2022.02.07 결과메세지 공통처리
		$.ajaxUtil.result(data, callback);
    }
};
// END of '$.ajaxUtil'
