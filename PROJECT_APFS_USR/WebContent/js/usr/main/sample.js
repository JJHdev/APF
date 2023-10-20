/**
******************************************************************************************
*** 파일명    : sample.js
*** 설명      : Chartjs 사용 샘플
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.07.06              LSH
******************************************************************************************
**/
$(function() {
	// 메일보내기
	$('#btnMail').bind('click', function() {
        $.ajaxUtil.ajaxLoad(
            getUrl('/com/common/sendEmail.do'), 
            $('#mailForm').serializeObject(), 
            function() {
				$.commMsg.alert('성공적으로 전송되었습니다.');
            }
        );
         return false;
	});
	
});
