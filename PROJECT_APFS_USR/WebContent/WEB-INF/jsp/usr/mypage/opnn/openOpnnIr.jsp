<%--
*******************************************************************************
***    명칭: openOpnnIr.jsp
***    설명: 마이페이지 - IR검토의견등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
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
<!-- 오늘 날짜 -->
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy" var="bgngYear"/>

<!-- 검색시 전송 form -->
<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<input type="hidden" id="page" name="page" value='<c:out value="${model.page}" />' /> 
	<input type="hidden" id="srchYr" name="srchYr" value="<c:out value="${empty model.srchYr ? bgngYear: model.srchYr}" />" /> 
	
</form>

<div class="tit-box-3 mb-24px">
	<div class="row">
		<div class="col">
			<p class="txt1">
				<img src="<c:url value='/images/titimg-2.svg' />" alt="icon" /> IR 투자자 검토의견 등록
			</p>
		</div>

	</div>
</div>

<!-- 목록검색결과 영역 -->
<div class="design-txt-1 mb-16px">
	<div class="row">
		<div class="col flex-grow-1">
			<p class="txt1">
				<em class="sYear text-primary"><c:out value="${empty model.srchYr? bgngYear: model.srchYr}" />년 </em>투자설명회 목록
			</p>
		</div>
		<div class="col">
			<button id="btnGuide" class="bs-m btn-mint-ouline">
				<i class="F icon-book-bookmark"></i>작성가이드
			</button>
		</div>
		<div class="col flex-b150px">
			<div class="form-area-box">
				<div class="ele-icon-box">
					<div class="dropdown">
						<button id="btnYr" class="dropdown-toggle t-chge" type="button" data-bs-toggle="dropdown" aria-expanded="false">
							<c:out value="${empty model.srchYr? bgngYear: model.srchYr}" /></button>
						<ul class="dropdown-menu w-100 bg-white">
							<c:forEach begin="0" end="${bgngYear-2010}" var="year" step="1">
								<li><a class="dropdown-item dropdown-srchYr" href="javascript:void(0)"><c:out value="${bgngYear-year}"/></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col">
			<button id="btnSearch" class="btn-primary bs-m">
				<i class="icon-search"></i>검색
			</button>
		</div>
	</div>
</div>		

<!-- 목록 영역 -->
<div id="appGridPanel">
	<div class="shadow-box-1 px-16px pb-24px">
		<!-- 목록 컨텐츠 영역 -->
		<div id="appGrid"></div>
		<!-- 목록 페이징 영역 -->
		<div class="mb-0 paging-box pb-24px px-0">
			<ul id="appGridPagination"></ul>
		</div>
	</div>
</div>



<%-- ############################ 내용 (종료) ############################ --%>
