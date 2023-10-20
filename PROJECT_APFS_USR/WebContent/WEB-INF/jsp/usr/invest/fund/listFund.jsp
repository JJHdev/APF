<%--
*******************************************************************************
***    명칭: listFund.jsp
***    설명: 투자서비스 - 투자자검색 - 스마트검색 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.19    LSH        First Coding.
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

<%-- ############################ 내용 (시작) ############################ --%>

<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">

	<!-- 스마트검색 영역 -->
	<div id="appSmartSearch"></div>

	<!-- 목록검색결과 영역 -->
	<div id="appGridResult" class="tit-box-2 mb-16px"></div>
	
	<!-- 목록툴바 영역 -->
	<div id="appGridToolbar" ></div>
</form>

<!-- 목록 영역 -->
<div id="appGridPanel">
	<div class="shadow-box-1 px-16px pb-24px">
		<!-- 목록 컨텐츠 영역 -->
		<div id="appGrid"></div>

		<!-- 목록 페이징 영역 -->
		<div class="mb-0 paging-box pb-24px">
			<ul id="appGridPagination"></ul>
		</div>
	</div>
</div>

<%-- ############################ 내용 (종료) ############################ --%>
