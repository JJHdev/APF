<%--
*******************************************************************************
***    명칭: openSearchNotice.jsp
***    설명: 통합검색 - 공지사항 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자            내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.04      LHB         First Coding.
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
<form:form commandName="model" id="searchForm" name="searchForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="page"		/>
	<form:hidden path="bbsSeCd"		/>
	<form:hidden path="srchText"	/>
</form:form>

<div class="pt-36px pt-md-36px">
	<div class="box mb-32px">
		<div class="tit-box-2 mb-16px">
			<div class="row">
				<div class="col">
					<p class="txt1">
						<img src="<c:url value="/images/titimg-1.svg"/>" alt="icon" />											
						<span>공지사항</span><em class="ml-4px" id="appSearchCnt">N</em>개
					</p>
				</div>
			</div>
		</div>
		<div class="shadow-box-1 p-24px" id="appSearch">
			<div class="one-list">
				<div class="row">
					<div class="col-12">
						<div class="box">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="mb-0 paging-box pb-24px px-0">
		<ul id="appGridPagination"></ul>
	</div>
</div>

<%-- ############################# 내용 (종료) ############################# --%>
