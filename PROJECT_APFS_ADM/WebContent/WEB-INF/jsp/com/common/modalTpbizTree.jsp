<%--
*******************************************************************************
***    명칭: modalTpbizTree.jsp
***    설명: 공통 - 업종트리 선택팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.03    LSH        First Coding.
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

<form id="treeForm" name="treeForm" method="post" onsubmit="return false;">
	<div id="appPopupTree"></div>
</form>

<div class="bottom-box-btn py-24px">
	<div class="row">
		<div class="col">
			<button type="button" id="p_btnClose" class="btn-black w-100 bs-l"><i class="icon-times"></i>닫기</button>
		</div>
	</div>
</div>

<%-- ############################# 내용 (종료) ############################# --%>

