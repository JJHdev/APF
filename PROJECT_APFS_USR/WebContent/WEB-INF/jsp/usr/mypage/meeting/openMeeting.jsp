<%--
*******************************************************************************
***    명칭: openMeeting.jsp
***    설명: 마이페이지 - 문의내역 - 투자자미팅요청내역 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%-- ############################ 내용 (시작) ############################ --%>
<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<div class="shadow-box-1 mb-24px px-24px">
		<div id="appMenuTab"></div>
	</div>
	<div class="tit-box-3 mb-24px">
		<div class="row">
			<div class="col">
				<p class="txt1">
					<img src="/images/titimg-2.svg" alt=""> <span
						class="text-primary"
						style="font-size: inherit; font-weight: inherit;"><c:out value="${model.meetingCount}" /></span> 개의 투자사가
					미팅을 요청했습니다.
				</p>
			</div>
			<div class="col flex-b210px">
				<div id="appSortTab"></div>
			</div> 
		</div>
	</div>			
									
</form>
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
