<%--
*******************************************************************************
***    명칭: modalEntCmpl.jsp
***    설명: 반려 업체 제출 서류 보완 제출 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자                내용
*** ---------------------------------------------------------------------------
***    1.0      2023.09.04      LSH             First Coding.
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

<script type="text/javascript" src="<c:url value='/js/com/common/modalEntCmpl.js'/>"></script>

<form:form commandName="model" id="popupForm" name="popupForm" method="post">
	<form:hidden id="p_bzentySeCd" path="bzentySeCd" />
	<form:hidden id="p_bzentyNo"   path="bzentyNo"   />
	<form:hidden id="p_brno"       path="brno"       />
	<form:hidden id="p_userNo"     path="userNo"     />
	<form:hidden id="p_userId"     path="userId"     />
	<form:hidden id="p_joinCd"     path="joinCd"     />
	<form:hidden id="p_kdCd"       path="kdCd"       />
	<form:hidden id="p_rprsYn"     path="rprsYn"     />
	<form:hidden id="p_kodataYn"   path="kodataYn"   />
	<form:hidden id="p_matchYn"    path="matchYn"    />
	<form:hidden id="p_existYn"    path="existYn"    />
</form:form>

	<div class="modal-body pt-0">
		<div class="form-box-1 border-bottom-0 mb-24px pb-0">
			<div id="p_appCard"></div>
		</div>
		<div class="bottom-box-btn py-24px">
			<div class="row">
				<div class="col">
					<button type="button" id="btnSubmit" class="btn-primary w-100 bs-l"><i class="icon-floppy-disk"></i>제출</button>
				</div>
				<div class="col">
					<button type="button" id="btnClose"  class="btn-black w-100 bs-l"><i class="icon-times"></i>닫기</button>
				</div>
			</div>
		</div>
	</div>

<%-- ############################# 내용 (종료) ############################# --%>
