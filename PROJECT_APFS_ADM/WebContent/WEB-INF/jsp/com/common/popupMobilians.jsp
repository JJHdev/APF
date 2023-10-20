<%--
*******************************************************************************
***	명칭: popupMobilians.jsp
***	설명: KG 모빌리언스 본인확인 요청 페이지
***
***	-----------------------------    Modified Log   ---------------------------
***	버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***	1.0      2023.03.20    LSH        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<%-- ############################# 내용 (시작) ############################# --%>

<%-- KG 모빌리언스 본인인증페이지 오픈 --%>
<c:if test="${mode=='OPEN'}">
	<%--
	/*****************************************************************************************
	가맹점에서는 아래 js 파일을 반드시 include
	실 결제환경 구성시 모빌리언스 담당자와 상의 요망
	 *****************************************************************************************/
	--%>
	<script src="https://auth.mobilians.co.kr/js/ext/ext_inc_comm.js"></script>
	<script>
		$(function() {
			//아래와 같이 ext_inc_comm.js에 선언되어 있는 함수를 호출
			MCASH_PAYMENT(document.payForm);
		});
	</script>
	<form:form commandName="form" id="payForm" name="payForm" method="post" accept-charset="euc-kr">
	<form:hidden path="CASH_GB"      /><%-- 대표결제수단 --%>
	<form:hidden path="CI_SVCID"     /><%-- 서비스아이디 --%>
	<form:hidden path="Cryptyn"      /><%-- 암호화사용여부 --%>
	<form:hidden path="Keygb"        /><%-- 암호화Key구분 --%>
	<form:hidden path="CALL_TYPE"    /><%-- 결제창 호출방식 --%>
	<form:hidden path="LOGO_YN"      /><%-- 가맹점 로고 사용여부 --%>
	<form:hidden path="CI_Mode"      /><%-- 결제단계구분 --%>
	<form:hidden path="DI_CODE"      /><%-- DI웹사이트코드 --%>
	<form:hidden path="Siteurl"      /><%-- 가맹점도메인 --%>
	<form:hidden path="SUB_CPID"     /><%-- SUB대행사식별 --%>
	<form:hidden path="Callback"     /><%-- 발신번호 --%>
	<form:hidden path="Smstext"      /><%-- SMS문구 --%>
	<form:hidden path="Lmstitle"     /><%-- LMS제목 --%>
	<form:hidden path="Lmstext"      /><%-- LMS본문 --%>
	<form:hidden path="Tradeid"      /><%-- 가맹점거래번호 --%>
	<form:hidden path="PAY_MODE"     /><%-- 거래종류 --%>
	<form:hidden path="Okurl"        /><%-- 성공URL(Okurl) --%>
	<form:hidden path="Notiurl"      /><%-- 인증요청결과URL(Notiurl) --%>
	<form:hidden path="Closeurl"     /><%-- Closeurl --%>
	<form:hidden path="CI_FIXCOMMID" /><%-- 이통사고정 --%>
	</form:form>

</c:if>

<%-- KG 모빌리언스 본인인증페이지 결과 처리 --%>
<c:if test="${mode=='RESULT'}">
	<%--
	/*********************************************************************************
	* 인증 성공시 웹 페이지 전환으로 호출되는 페이지이며 가맹점에서 구현해야하는 화면입니다.
	*********************************************************************************/
	--%>
	<script>
		var DATA = {
			mode    : '${mode}'         , // 처리모드
			Result  : '${form.result}'  , // 처리결과
			Name    : '${form.name}'    , // 이름
			No      : '${form.no}'      , // 휴대전화번호
			Socialno: '${form.socialno}'  // 생년월일
		};
		$(function() {
			if (DATA.Result == "SUCCESS") {
				opener.certifyCallback(DATA);
				window.close();
			}
			else {
				$.commMsg.alert('본인확인 중 데이터가 위·변조되었습니다');
			}
		});
	</script>
</c:if>

<%-- ############################# 내용 (종료) ############################# --%>
