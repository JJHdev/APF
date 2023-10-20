<%--
*******************************************************************************
***    명칭: openOpnnIrEnt.jsp
***    설명: 마이페이지 - IR 투자자 검토의견 등록 - 투자설명회 상세
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.23    KYW        First Coding.
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
	<input type="hidden" id="page" name="page" value='<c:out value="${model.page}" />' /> 
	<input type="hidden" id="evntNo" name="evntNo" value='<c:out value="${model.evntNo}" />' /> 
	<input type="hidden" id="srchYr" name="srchYr" value='<c:out value="${model.srchYr}" />' /> 
</form>

<div class="tit-box-3 mb-24px">
	<div class="row">
		<div class="col">
			<p class="txt1">
				<img src="<c:url value='/images/titimg-2.svg' />" alt="" /> <c:out value="${model.srchYr}" />년 <c:out value="${model.evntNm}" /> 투자설명회 사업계획서
			</p>
		</div>
		<div class="col text-end">
			<button id="btnList" class="bs-m btn-primary-ouline">
				<i class="F icon-list"></i>목록으로
			</button>
		</div>
	</div>
</div>
<!-- 목록 컨텐츠 영역 -->
<div id="appGrid" class="opnnIrEntGrid"></div>
<!-- 목록 페이징 영역 -->
<div class="mb-0 paging-box pb-24px px-0">
	<ul id="appGridPagination"></ul>
</div>



<%-- ############################ 내용 (종료) ############################ --%>
