<%--
*******************************************************************************
***    명칭: listPromotion.jsp
***    설명: 정보서비스 - 홍보영상 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.15    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
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
<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<input type="hidden" id="page"      name="page"      value="<c:out value='${model.page}'/>"/>
	<input type="hidden" id="srchText"  name="srchText"  value="<c:out value='${model.srchText}'/>"/>
	<input type="hidden" id="bbsSeCd"   name="bbsSeCd"   value="<c:out value='${model.bbsSeCd}'/>"/>
	<input type="hidden" id="pstClsfCd" name="pstClsfCd" value="<c:out value='${model.pstClsfCd}'/>"/>
	<input type="hidden" id="srchType"  name="srchType"  value="<c:out value='${model.srchType}'/>"/>
</form>

<!-- 목록 영역 -->
<div id="appGridPanel">
	<div class="shadow-box-1 p-24px">
		<div id="appGrid"></div>
		<!-- 목록 페이징 영역 -->
		<div class="mb-0 paging-box pb-24px">
			<ul id="appGridPagination"></ul>
		</div>
	</div>
</div>
<%-- ############################# 내용 (종료) ############################# --%>
