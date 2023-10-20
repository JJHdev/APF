/**
******************************************************************************************
*** 파일명    : comm_formutils.js
*** 설명      : 공통 FORM 처리 관련 JavaScript
***             $.formUtil
***             $.fn.emptySelect
***             $.fn.loadSelect
***             $.fn.modalReset
***             $.fn.resetForm
***             $.fn.serializeObject
***             $.setTagVal
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-02-10              ntarget
******************************************************************************************
**/
/**
 * form 관련 유틸 함수
 */
$.formUtil = {
    /**
     * form에 대해 submit 처리하는 유틸 함수
     *
     * <기능>
     * - formId가 지정되지 않거나 해당 id의 form이 존재하지 않으면 임시 form을 생성
     * - 존재하는 formId 값이 지정되면 해당 form으로 submit 진행
     * - 해당 form에 첨부파일객체(type='file')이 있으면 enctype을 'application/x-www-form-urlencoded'로 자동 설정
     *
     * <option 항목>
     *      formId : form의 id 항목 (지정되지 않으면 임시 form 생성)
     *      method : form의 method 속성값 (default : post)
     *      target : form의 target 속성값 (default : _self)
     *      params : 추가 파라미터 정의 (hidden 객체로 추가됨)
     *
     * <샘플 1> : form1 지정
     * $.formUtil.submitForm(ROOT_PATH +"/usr/temp/viewSample.do", {
     *           formId : 'form1',
     *           method : 'post',
     *           params : {
     *               tempSeq : tempSeqVal
     *           }
     *       });
     *
     * <샘플 2> : form 지정 안함(임시 form 생성)
     * $.formUtil.submitForm(ROOT_PATH +"/usr/temp/viewSample.do", {
     *           params : {
     *               tempSeq : tempSeqVal
     *           }
     *       });
     */
    submitForm : function(url, option){
        var defOpt = {
            method : "post",
            target : "_self",
            params : {}
        };
        var newOption   = $.extend({}, defOpt, option);
        var formId      = newOption.formId;
        var tempParamCls= "TEMP_PARAM_89874578JAFU766FHKF";
		
		if (formId && formId.charAt(0) == '#')
			formId = formId.substring(1);

        //----------------------------
        // 지정된 formId가 없거나 해당 form이 존재하지 않을 때 임시 form 객체를 생성
        //----------------------------
        if (formId == undefined || formId == null || $.trim(formId) == "" || $("#"+formId).length == 0 ) {
            // 임시 form id
            formId  = "56137EYAF-COMM-FORM845KJFI";

            // 임시 form 생성
            $("#"+formId).remove();
            $("body").append("<form id='"+formId+"'></form>");
        }

        // form 객체
        var enctypeVal  = 'multipart/form-data';    // enctype 기본 값
        var $form       = $("#"+formId);

        // 만일 form 내에 임시 생성한 param이 존재하면 삭제처리
        $form.find("."+tempParamCls).remove();

        //----------------------------
        // 파일객체 확인 및 객체 name 재설정
        //----------------------------

        var $fileObjs   = $form.find('input[type=file]');
        if ($fileObjs.length > 0) {
            // multipart로 변경
            enctypeVal  = 'application/x-www-form-urlencoded';

            // 파일 객체의 name 항목이 동일하지 않게 변경(동일하면 1개로 인식되는 문제있음)
            var fileIdx = 0;
            $fileObjs.each(function() {

                var $fileObj = $(this);
                var fileObjNm = "";
                var orgObjNm  = $fileObj.data("org-name");

                if (orgObjNm != undefined && orgObjNm != null && orgObjNm != "") {
                    fileObjNm = orgObjNm;
                } else {
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
        // 지정된 파라미터 추가 작업
        //----------------------------
        if (newOption.params) {
            for (var prm in newOption.params ) {
                var $lastChildObj = $form.find(":last");
                var objHtml       = "<input type='hidden' name='"+prm+"' value='"+newOption.params[prm]+"' class='"+tempParamCls+"'/>";
                
                // child가 없으면 (form의 html으로 대체)
                if ($lastChildObj.length == 0) {
                    $form.html(objHtml);
                }
                // 존재하면 (객체 뒤에 추가)
                else {
                    $lastChildObj.after(objHtml);
                }
            }
        }

        //----------------------------
        // 최종 submit 처리
        //----------------------------
        if (url && $.trim(url) != "") {
            $form.attr("action",  url)
                 .attr("method",  newOption.method)
                 .attr("target",  newOption.target)
                 .attr("enctype", enctypeVal)
                 // 2020.08.25 제거
                 //.submit()
                 ;

            // 2020.08.25 수정 : form을 jquery의 객체로 submit했을 때 해당 form이 jquery-form으로 사용되었다면 이상 동작을 함 (ajax로 전송됨)
            // => html dom객체로 submit하면 해결됨
            $form.get(0).submit();
        }
        else {
            console.error("[FORM ACTION NULL ERROR!!]");
        }
    },  
    // END of 'submitForm'

    /**
     * 폼체크
     * $.formUtil.formCheck( $obj,     formNm,   isRequired, maxLength)
     * $.formUtil.formCheck( $("#id"), '아이디', true,       50);
     *
     * <항목>
     * $obj : 해당 항목으로 전달(jquery 객체), radio, checkbox -> $("input[name=useStts]")
     * formNm     : 해당 항목 명
     * isRequired : true이면 필수, false이면 필수 아님
     * maxLength  : 최대 허용 문자열 길이
     *
     */
    formCheck : function($obj, formNm, isRequired, maxLength){
        // 전달된 object가 없을 때
        if($obj == undefined || $obj == null || $obj.length == 0) {
            console.log("#Not found object");
            return null;
        }

        if(!maxLength || maxLength == undefined || maxLength == null){
            maxLength = 0;
        }

        var tagName = $obj.prop("tagName").toLowerCase();
        var formType= $obj.attr("type");
        var formVal = $.trim($obj.val());
        var $form   = $obj.closest("form");
        var msgTail = " 항목을 입력(선택)하세요.";
        var errorMsg= "";

        var isInput = true;
        if(isRequired) {
            switch(formType) {
                case "text":
                    if( formVal == '' || formVal == null){
                        $obj.val("");
                        $obj.focus();
                        isInput = false;
                    }
                    break;
                case "checkbox":
                    if($obj.is(":checked") == false) {
                        msgTail = " 항목을 선택하세요."
                        isInput = false;
                    }
                    break;
                case "radio":
                    if($obj.is(":checked") == false) {
                        msgTail = " 항목을 선택하세요."
                        isInput = false;
                    }
                    break;
                default : // textarea
                    if( formVal == '' || formVal == null){
                        $obj.val("");
                        $obj.focus();
                        isInput = false;
                    }
                    break;
            }// end of swicth

            if(!isInput){
                errorMsg = formNm + msgTail;
                // TODO : 메시지 출력은 추후 출력 방식에 맞게 변경해야 함
                alert(errorMsg);
                return false;
            }
        }// end of 'if(isRequired)'

        if(maxLength > 0 ){

            if(maxLength < parseInt($.commUtil.getStrByteLength(formVal))) {
                errorMsg = formNm+" 항목은 한글 " + parseInt(maxLength/2)+"자, 영문 "+parseInt(maxLength)+"자, 숫자 " + parseInt(maxLength) + "자 이상 입력할 수 없습니다.";
                // TODO : 메시지 출력은 추후 출력 방식에 맞게 변경해야 함
                alert(errorMsg);
                return false;
            }
        }

        return true;
    }, 
    // END of 'formCheck'
    /**
     * 객체의 데이터를 폼 객체 개별항목(ID기준)에 셋팅하는 함수
     * 2021.07.21 LSH 추가
     */
    toForm: function(data, form, prefix) {

        for (var p in data) {
            
            var key = (prefix ? prefix : '')+p;
            var val = data[p];
    
            if (form) {
                var obj = form.find('[name="'+key+'"]');
                if (!obj || !obj.length)
                    continue;
                if (obj.is("input:checkbox") || 
                	obj.is("input:radio")) {
                	obj.each(function() {
                		if($(this).val() == val)
                			$(this).prop('checked', true);
                	});
                }
                else {
	                obj.val(val);
                }
            }
            else {
                var obj = $('#'+key);
                
                if (!obj || !obj.length)
                    continue;
        
                if (obj.is("span") || obj.is("div") || obj.is("p")) {
                    obj.html(val);
                }
                else if (obj.is("input:checkbox") || obj.is("input:radio")) {
                    if (obj.val() == val)
                        obj.prop("checked", true);
                }
                else {
                    obj.val(val);
                }
            }
        }
    },
    /**
     * 객체의 데이터를 폼 객체 개별항목(ID기준)에 셋팅하는 함수
	 * 빈값이 아닌 경우 SKIP한다
     * 2023.05.30 LSH 추가
     */
    toRestForm: function(data, form, callback) {

        for (var key in data) {
            
            var val = data[p];
            var obj = form.find('[name="'+key+'"]');

            if (!obj || !obj.length)
                continue;
			
			var pre = $.formUtil.getValue(form, key);
			if (!$.commUtil.empty(pre))
				continue;
			
			$.formUtil.setValue(form, key, val);
			
			if (callback)
				callback(obj, key, val);
        }
    },

    /**
     * 폼객체의 element name 기준 value를 반환한다.
     * 2023.03.20 LSH 추가
     */
    getValue: function(form, name, isArr) {

		var obj = form.find('[name="'+name+'"]');
		
        if (!obj || !obj.length)
            return null;
        if (obj.is("input:checkbox") || 
        	obj.is("input:radio")) {
			let v = [];		
			obj.each(function() {
				if ($(this).is(':checked')) {
					v.push($(this).val());
				}
			});
			if (isArr)
				return v;
			return (v.length == 1 ? v[0] : v);
        }
		if (isArr)
			return [obj.val()];
		return obj.val();
    },

    /**
     * 폼객체의 element name 기준 value를 정의한다.
     */
    setValue: function(form, name, value) {

		var obj = form.find('[name="'+name+'"]');
		
        if (!obj || !obj.length)
            return null;

        if (obj.is("input:checkbox") || 
        	obj.is("input:radio")) {
			obj.each(function() {
				let v = $(this).val();
				if ($.isArray(value)) {
					$(this).prop('checked', $.inArray(v, value) >= 0);
				}
				else {
					$(this).prop('checked', v == value);
				}
			});
        }
		else {
			obj.val(value);
		}
    },

    /**
     * 폼 박스의 값을 객체로 생성, 반환하는 함수
     * serializeObject와 비슷하나, 
     * 인자로 넘어온 키에 해당하는 데이터만 생성한다.
     * 2021.08.06 LSH 추가
     * 
     * $.formUtil.toObject( $('#selectForm'), ['userId','userNm']);
     */
    toObject: function(form, keys) {
    	
    	var obj = form.serializeObject();
    	var ret = {};

    	$.each(keys, function(i,key) {
            if (obj && obj[key]) {
            	ret[key] = $.commUtil.nvl(obj[key]);
            }
    	});
    	return ret;
    },

    /**
     * 특정영역의 form element name 기준 value를 객체로 반환한다.
     * 2023.05.29 LSH 추가
     */
    getObject: function(form) {
	
		let ret = {};
		
		form.find('[name]').each(function() {
			let obj = $(this);
			let key = obj.prop('name');
			let val = ret[key];
			if (obj.is("input:text"  ) ||
				obj.is("select"      ) ||
				obj.is("input:hidden") ||
				obj.is("textarea"    ))
				ret[key] = obj.val();
			else {
				if ($(this).is(':checked')) {
					if (val) {
						if ($.type(val) != 'array') {
							ret[key] = [val];
						}
						ret[key].push(obj.val());
					}
					else {
						ret[key] = obj.val();
					}
				}
			}
		});
		return ret;
    },

    /**
     * 객체의 값을 KEY에 해당하는 ID를 가진 레이어에 셋팅하는 함수
     * 
     * 2021.08.06 LSH 추가
     * 
     * $.formUtil.toHtml( $('#selectForm'), data, 'select-' );
     */
    toHtml: function(dom, data, prefix) {
    	dom.find("[id^='"+prefix+"']").each(function(i,e) {
            var key = $(this).prop('id').substring(prefix.length);

            if (data[key]) 
                $(this).html(data[key]);
            else
                $(this).html('&nbsp;');
        });
    },        
    /**
     * prefix로 시작하는 ID를 가진 레이어의 값을 리셋하는 함수
     * 
     * 2021.10.20 LSH 추가
     * 
     * $.formUtil.toHtmlReset( $('#selectForm'), 'select-' );
     */
    toHtmlReset: function(dom, prefix) {
    	dom.find("[id^='"+prefix+"']").each(function(i,e) {
            var key = $(this).prop('id').substring(prefix.length);
            $(this).html('&nbsp;');
        });
    },        

    // 아이디 유효성 체크 영문자(소) + 숫자 + 6~15자리
    // $.formUtil.isIdCheck(obj);
    isIdCheck: function (obj) {
        var chk1 = /[\S]{6,15}$/;
        var chk2 = /^[a-z0-9]+$/g;

        return chk1.test(obj) && chk2.test(obj) ? true : false;
    },
    // 비밀번호체크 9~20자리 + 영문자(대)+영문자(소)+숫자+특수문자
    // $.formUtil.isPwdCheck(obj);
    isPwdCheck: function (obj) {
        var chk1 = /[\S]{9,20}$/;
        var chk2 = /[a-z]/;
        var chk3 = /[A-Z]/;
        var chk4 = /\d/;
        var chk5 = /[^a-zA-Z0-9]/;

        return chk1.test(obj) && chk2.test(obj) && chk4.test(obj) && chk5.test(obj) ? true : false;
    },
    // 비밀번호 반복문자 4개 이상 처리
    // $.formUtil.isPwdOverCheck(obj);
    isPwdOverCheck: function (obj) {
        var chk1 = /(\w)\1\1\1/;

        return chk1.test(obj) ? true : false;
    },
    // list 체크박스 array id
    // $.formUtil.isArrCheck(obj);
    isArrCheck: function (obj) {
        var arr = new Array();
        var i = 0;

        $('input[id="' + obj + '"]:checked').each(function () {
            arr[i] = $(this).val();
            i++;
        });

        return arr;
    },
    // list 체크박스 array name
    // $.formUtil.isArrNameCheck(obj);
    isArrNameCheck: function (obj) {
        var arr = new Array();
        var i = 0;

        $('input[name="' + obj + '"]:checked').each(function () {
            arr[i] = $(this).val();
            i++;
        });

        return arr;
    },
    // list 체크박스 array id, attr
    // $.formUtil.isArrAttrCheck(obj, attrName);
    isArrAttrCheck: function (obj, attrName) {
        var arr = new Array();
        var i = 0;

        $('input[id="' + obj + '"]:checked').each(function () {
            arr[i] = $(this).attr(attrName);
            i++;
        });

        return arr;
    },
    // list 체크박스 array id, attr, 암호화
    // $.formUtil.isArrAttrCheckHash(obj, attrName);
    isArrAttrCheckHash: function (obj, attrName) {
        var arr = new Array();
        var i = 0;

        $('input[id="' + obj + '"]:checked').each(function () {
            arr[i] = hex_sha512($(this).attr(attrName)).toLowerCase();
            i++;
        });

        return arr;
    },
    // $.formUtil.isCheckBoxAll(obj); obj = '.변수명'
    isCheckBoxAll: function (obj) {
        if ($(obj + ':checked').length != $(obj).length) {
            return false;
        }
        else {
            return true;
        }
    },
    // 텍스트박스 array id
    // $.formUtil.isArrIdText(obj);
    isArrIdText: function (obj) {
        var arr = new Array();
        var i = 0;

        $('input[id="' + obj + '"]').each(function () {
            if ($.trim($(this).val()) != '') {
                arr[i] = $.trim($(this).val());
                i++;
            }
        });

        return arr;
    },
    // 텍스트박스 array name
    // $.formUtil.isArrNameText(obj);
    isArrNameText: function (obj) {
        var arr = new Array();
        var i = 0;

        $('input[name="' + obj + '"]').each(function () {
            if ($.trim($(this).val()) != '') {
                arr[i] = $.trim($(this).val());
                i++;
            }
        });

        return arr;
    },
    // 텍스트박스 숫자 Check
    // $.formUtil.isNumCheck(obj);
    isNumCheck: function (obj, msg) {
        var reg = /^(\s|\d)+$/;

        if (reg.test($(obj).val())) {
            return true;
        }
        else {
            $.commMsg.alert(msg + '은(는) 숫자만 입력하세요.');
            $(obj).val('');
            return false;
        }
    },
    // radio id
    // $.formUtil.isIdRadio(obj);
    isIdRadio: function (obj) {
        return val = $(':radio[id="' + obj + '"]:checked').val();
    },
    // radio name
    // $.formUtil.isNameRadio(obj);
    isNameRadio: function (obj) {
        return $(':radio[name="' + obj + '"]:checked').val();
    },
    // Birthday check
    // $.formUtil.isBirthday(obj);
    isBirthday: function (obj) {
        var reg = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
        if (reg.test($(obj).val())) {
            return true;
        }
        else {
            $.commMsg.alert('생년월일을 정확히 입력해주세요.');
            $(obj).val('');
            return false;
        }
    },
    /**
     * Email Check
     * @param   email
     * @return  boolean
     * 
     * $.formUtil.isEmail(obj);
     */
    isEmail: function(email) {
        re = /[^@]+@[A-Za-z0-9_-]+[.]+[A-Za-z]+/;

        if (re.test(email)) {
            return  true;
        }
        return  false;
    },
    /**
     * 2023.06.08 LSH Form Element Name 기준 빈값 체크
     */
    isEmptyCheck: function(form, name, msg) {
		let obj = form.find('[name="'+name+'"]');
		if (obj && obj.length > 0) {
			if ($.commUtil.empty(obj.val())) {
				if (msg) {
					$.commMsg.alert(msg, function() {
						obj.focus();
					});
				}
				return false;
			}
			return true;
		}
		return false;
    },
    /**
     * 2023.06.22 LSH Object의 KEY 기준 빈값 체크
     */
    validExist: function(obj, key, msg) {
		let vld = false;
		if (obj && key && obj[key]) {
			if ($.type(obj[key]) === 'array')
				vld = (obj[key].length > 0);
			else
				vld = (!$.commUtil.empty(obj[key]));
		}
		if (!vld && msg) {
			$.commMsg.alert(msg);
		}
		return vld;
    },
    /**
     * 2021.10.06 LSH
     * 주민등록번호,이메일,전화번호,휴대전화번호,날짜,사업자등록번호,사업분야를 분리하며 INPUT에 맵핑
     * 분리된 데이터가 담길 element는 idKey+(1부터 시작하는 sequence) 로 
	 * ID가 정의되어 있어야 한다.
     * @param idKey form element 기준ID
     * @param stype 종류('rno', 'email', 'phone', 'mobile', 'date', 'month', 'bzno', 'bizFld')
     * 
     * $.formUtil.splitData('emlAddr', 'email');
     */
    splitData: function(idKey, stype) {
		
		let data = $('#'+idKey).val();
		
		if ($.commUtil.empty(data))
			return;
		
		if (stype == 'email') {
			let arr = data.split('@');
			$(arr).each(function(i,d) {
				let sel = $('#'+idKey+(i+1));
				if (sel && sel.length > 0) {
					sel.val(d);
				}
			});
		}
		else if (stype == 'rno') {
			data = data.replace(/\-/g, ''); // 구분자 '-' 제거
			if (data.length == 13) {
				$('#'+idKey+'1').val(data.substring(0,6));
				$('#'+idKey+'2').val(data.substring(6));
			}
		}
		else if (stype == 'phone' || stype == 'mobile') {
			// 전화번호 형식화
			data = $.commUtil.phoneFormatter(data);
			let arr = data.split('-');
			$(arr).each(function(i,d) {
				let sel = $('#'+idKey+(i+1));
				sel.val(d);
			});
		}
		// 날짜(YYYYMMDD 타입)
		else if (stype == 'date') {
			data = data.replace(/\-/g, ''); // 구분자 '-' 제거
			data = data.replace(/\./g, ''); // 구분자 '.' 제거
			if (data.length == 8) {
				$('#'+idKey+'1').val(data.substring(0,4));
				$('#'+idKey+'2').val(data.substring(4,6));
				$('#'+idKey+'3').val(data.substring(6,8));
			}
		}
		// 년월(YYYYMM 타입)
		else if (stype == 'month') {
			data = data.replace(/\-/g, ''); // 구분자 '-' 제거
			data = data.replace(/\./g, ''); // 구분자 '.' 제거
			if (data.length == 6) {
				$('#'+idKey+'1').val(data.substring(0,4));
				$('#'+idKey+'2').val(data.substring(4,6));
			}
		}
		else if (stype == 'bzno') {
			data = data.replace(/\-/g, ''); // 구분자 '-' 제거
			if (data.length == 10) {
				$('#'+idKey+'1').val(data.substring(0,3));
				$('#'+idKey+'2').val(data.substring(3,5));
				$('#'+idKey+'3').val(data.substring(5));
			}
		}
		else if (stype == 'bizFld') {
			//data = data.replace(/\,/g, ''); // 구분자 ',' 제거
			data = data.split(/\,/g);
			if (data.length > 0) {
				data.forEach(function(e, i) {
					$('#'+idKey+(i+1)).prop("checked", true);
				});
			}
		}
    },
    /**
     * 2023.06.08 LSH mergeElement 사용으로 변경함
     * 각종 형식화된 항목의 값을 병합하여 Form Element에 ID 기준 맵핑
     * $.formUtil.mergeData('emlAddr', 'email');
     */
    mergeData: function(idKey, stype, len, dataObj) {

		$.formUtil.mergeElement({
			stype   : stype,
			idKey   : idKey,
			len     : len,
			dataObj : dataObj,
			setValue: function(val, key) {
				let obj = $('#'+key);
				if (obj && obj.length > 0) {
					obj.val(val);
				}
			},
			getValue: function(key) {
				return $('#'+key).val();
			}
		});
    },
    /**
     * 2023.06.08 LSH splitElement 사용
     * 각종 형식화된 항목의 값을 분리하여 Form Element에 NAME 기준 맵핑
     * $.formUtil.splitName('emlAddr', 'email');
     */
    splitName: function(form, nameKey, stype, dataObj) {
		
		$.formUtil.splitElement({
			stype   : stype,
			form    : form,
			idKey   : nameKey,
			dataObj : dataObj,
			setValue: function(val, key) {
				let obj = form.find('[name="'+key+'"]');
				if (obj && obj.length > 0) {
					obj.val(val);
				}
			},
			getValue: function(key) {
				return form.find('[name="'+key+'"]').val();
			}
		});
    },
    /**
     * 2023.06.08 LSH mergeElement 사용으로 변경함
     * 각종 형식화된 항목의 값을 병합하여 Form Element에 NAME 기준 맵핑
     * $.formUtil.mergeName('emlAddr', 'email');
     */
    mergeName: function(form, nameKey, stype, len, dataObj) {

		$.formUtil.mergeElement({
			stype   : stype,
			form    : form,
			len     : len,
			idKey   : nameKey,
			dataObj : dataObj,
			setValue: function(val, key) {
				let obj = form.find('[name="'+key+'"]');
				if (obj && obj.length > 0) {
					obj.val(val);
				}
			},
			getValue: function(key) {
				return form.find('[name="'+key+'"]').val();
			}
		});
    },

    /**
     * 2021.10.06 LSH
     * 주민등록번호,이메일,전화번호,휴대전화번호,날짜,사업자등록번호를 분리하며 INPUT에 맵핑
     * 분리된 데이터가 담길 element는 idKey+(1부터 시작하는 sequence) 로 
	 * ID가 정의되어 있어야 한다.
     * @param args.stype    종류('rno', 'email', 'phone', 'mobile', 'date', 'month', 'bzno', 'crno')
     * @param args.idKey    element id 또는 name
     * @param args.form     (선택옵션) name 기준 처리시 필요한 dom 객체
     * @param args.dataObj  (선택옵션) 분리된 값을 담을 데이터 객체
     * @param args.setValue 폼데이터 SET 함수 function(val, key, form)
     *                      form인자는 선택옵션이며 form인자가 있을경우에는 name 기준 처리 
     * @param args.getValue 폼데이터 GET 함수 function(key, form)
     *                      form인자는 선택옵션이며 form인자가 있을경우에는 name 기준 처리 
     * 
     * $.formUtil.splitElement( args );
     */
    splitElement: function( args ) {

		let _stype    = args.stype;
		let _idKey    = args.idKey;
		let _form     = args.form;
		let _dataObj  = args.dataObj;
		let _setValue = args.setValue;
		let _getValue = args.getValue;
		
		let data = _getValue(_idKey, _form);
		
		if ($.commUtil.empty(data))
			return;
			
		let arr = [];
		switch(_stype) {
			case 'email':// 이메일
				arr = data.split('@');
				break;
			case 'rno' : // 주민등록번호
			case 'crno': // 법인등록번호
				data = data.replace(/\-/g, ''); // 구분자 '-' 제거
				if (data.length == 13) {
					arr.push(data.substring(0,6));
					arr.push(data.substring(6));
				}
				break;
			case 'bzno': // 사업자등록번호
				data = data.replace(/\-/g, ''); // 구분자 '-' 제거
				if (data.length == 10) {
					arr.push(data.substring(0,3));
					arr.push(data.substring(3,5));
					arr.push(data.substring(5));
				}
				break;
			case 'phone' : // 전화번호 형식화
			case 'mobile': // 전화번호 형식화
				data = $.commUtil.phoneFormatter(data);
				arr = data.split('-');
				break;
			case 'date': // 날짜(YYYYMMDD 타입)
				data = data.replace(/\-/g, ''); // 구분자 '-' 제거
				data = data.replace(/\./g, ''); // 구분자 '.' 제거
				if (data.length == 8) {
					arr.push(data.substring(0,4));
					arr.push(data.substring(4,6));
					arr.push(data.substring(6,8));
				}
				break;
			case 'month': // 년월(YYYYMM 타입)
				data = data.replace(/\-/g, ''); // 구분자 '-' 제거
				data = data.replace(/\./g, ''); // 구분자 '.' 제거
				if (data.length == 6) {
					arr.push(data.substring(0,4));
					arr.push(data.substring(4,6));
				}
				break;
		}
		$(arr).each(function(i,d) {
			_setValue(d, _idKey+(i+1), _form);
			if (_dataObj)
				_dataObj[_idKey+(i+1)] = d;
		});
    },
    /**
     * 2021.10.06 LSH
     * 분리된 주민번호,이메일,전화번호,휴대전화번호,날짜,사업자등록번호를 병합하여 INPUT에 맵핑
     * 분리된 데이터가 담긴 element는 idKey+(1부터 시작하는 sequence) 로 
	 * ID가 정의되어 있어야 한다.
     * @param args.stype    종류('rno', 'email', 'phone', 'mobile', 'date', 'month', 'bzno', 'crno')
     * @param args.idKey    element id 또는 name
     * @param args.len      (선택옵션) 분리된 element 갯수 (없을 경우 종류에 따라 자동 설정됨)
     * @param args.form     (선택옵션) name 기준 처리시 필요한 dom 객체
     * @param args.dataObj  (선택옵션) 병합된 값을 담을 데이터 객체
     * @param args.setValue 폼데이터 SET 함수 function(val, key, form)
     *                      form인자는 선택옵션이며 form인자가 있을경우에는 name 기준 처리 
     * @param args.getValue 폼데이터 GET 함수 function(key, form)
     *                      form인자는 선택옵션이며 form인자가 있을경우에는 name 기준 처리 
     * 
     * $.formUtil.mergeElement( args );
     */
    mergeElement: function( args ) {
	
		let types = {
			 email : {count: 2, sep: '@'}
			,rno   : {count: 2, sep: ''}
			,phone : {count: 3, sep: ''}
			,mobile: {count: 3, sep: ''}
			,date  : {count: 3, sep: ''}
			,month : {count: 2, sep: ''}
			,bzno  : {count: 3, sep: ''}
			,crno  : {count: 2, sep: ''}
		};

		let _setValue = args.setValue;
		let _getValue = args.getValue;
		let _dataObj  = args.dataObj;
		let _form     = args.form;
		let _idKey    = args.idKey;
		let _stype    = args.stype;
		let _typeObj  = types[_stype];
		let _len      = (args.len || _typeObj.count)+1;

		_setValue('', _idKey, _form);

		let arr = [];
		for (let i = 1; i < _len; i++) {
			let v = _getValue(_idKey+i, _form);
			if (!$.commUtil.empty(v))
				arr.push(v);
		}
		if (arr.length != _typeObj.count)
			return;

		let val = arr.join(_typeObj.sep);
		_setValue(val, _idKey, _form);
		if (_dataObj)
			_dataObj[_idKey] = val;
    },

    /**
     * 폼의 특정 element의 readonly 속성을 변경한다.
     * 2023.03.22 LSH 추가
     */
    setReadonly: function(form, readonly, names, cls) {
		$.each(names, function(i, name) {
			let obj = form.find('[name="'+name+'"]');
			if (obj.is('select')) {
				if (readonly)
					obj.find("option").not(':selected').prop('disabled',true);
				else
					obj.find("option").prop('disabled', false);
			}
			else {
				obj.attr('readonly', readonly);
			}
			if (cls) {
				if (readonly) 
					obj.addClass(cls);
				else
					obj.removeClass(cls);
			}
		});
    },
    setDisabled: function(form, disabled, names, cls) {
		$.each(names, function(i, name) {
			form.find('[name="'+name+'"]').attr('disabled', disabled);
		});
    },
	setArrayData: function(data, name) {
		// 다중선택을 ModelAttribute 데이터로 변환
		if (data[name] && 
		    $.type(data[name]) === 'array') {
			$.each(data[name], function(i,v) {
				data[name+'['+i+']'] = v;
			});
			delete data[name];
		}
	},
    /**
     * 2023.06.08 LSH 추가
     * Form Element Name 기준 focus 처리
     */
    bindFocus: function(form, name) {
		form.find('[name="'+name+'"]').focus();
    }

}; 
// END of '$.formUtil'
/**
 * 폼 태그 값 Clear
 * 
 * $.fn.resetForm('#form1 .style li', false);
 * $.fn.resetForm('대상 Form Object', hidden 태그 대상여부);
 */
$.fn.resetForm = function (objForm, isClearHidden) {
    var $formObj = null;

    if (objForm && $.type(objForm) == 'string') {
        $formObj = $(objForm);
    }
    else {
        $formObj = objForm;
    }
    if ($formObj != null && $formObj.length > 0) {
        var isHidden = true;

        if (isClearHidden != undefined && ($.type(isClearHidden) == 'boolean' && !isClearHidden)) {
            isHidden = false;
        }
        if (isHidden) {
            $formObj.find('input[type=hidden]').val('');
        }

        $formObj.find('input:text').val('');
        $formObj.find('input:password').val('');
        $formObj.find('textarea').val('');
        $formObj.find('input:radio').prop('checked', false);
        $formObj.find('input:checkbox').prop('checked', false);
        $formObj.find('select').each(function () {
            // aria-controls attribute를 보유하고 있는 대상은 제외한다.
            if( !$(this).is('[aria-controls]')) {
                $(this).find('option:eq(0)').prop('selected', true);
                $(this).trigger('change');
            }
        });
    }

    return $formObj;
};

/**
 * 콤보박스 로드
 * 
 * $("#srchRoleId").loadSelect(result.list).trigger('change');
 */
$.fn.loadSelect = function (optionsDataArray, firstIdx) {
    return this.emptySelect(firstIdx).each(function () {
        if (this.tagName == 'SELECT') {
            var selectElement = this;

            $.each(optionsDataArray, function (idx, optionData) {
                var option = new Option(optionData.text, optionData.code);

                selectElement.add(option, null);
            });
        }
    });
};
$.fn.emptySelect = function (firstIdx) {
    return this.each(function () {
        if (firstIdx == undefined) firstIdx = 1;
        if (this.tagName == 'SELECT') this.options.length = firstIdx;
    });
};

$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/*
 * 공통 modal RESET
 * 
 * $.fn.modalReset('myModal');
 */
$.fn.modalReset = function (obj) {
    $(obj).on('hidden.bs.modal', function (e) {
        $(this).find('input').val('').end().find('input[type=checkbox], input[type=radio]').prop('checked', '').end();
    });
}

/*
 * HTML TAG 데이터 입력
 * 
 * $.setTagVal(tagObj, key, data); 
 */
$.setTagVal = function (tagObj, key, data) {
    if ($.commUtil.nvlTrim(data) != '') {
        switch (tagObj.prop('tagName')) {
            case 'SELECT':
                tagObj.val(data).trigger('change');
                break;
            case 'TEXTAREA':
                tagObj.val(data);
                break;
            case 'INPUT':
                switch (tagObj.prop('type')) {
                    case 'hidden':
                    case 'text':
                        tagObj.val(data);
                        break;
                    case 'checkbox':
                        var arr = data.split(',');
                        for (var i in arr) {
                            $("input:checkbox[name="+key+"][value="+arr[i]+"]").prop("checked", true);
                        }
                        break;
                    case 'radio':
                        $("input:radio[name="+key+"][value="+data+"]").prop("checked", true);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }
};
