<%--
*******************************************************************************
***    명칭: openBbs.jsp
***    설명: 마이페이지 - 문의내역 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.14    LSH        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%-- ############################ 내용 (시작) ############################ --%>
<%-- CKEDITOR 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/plugins/ckeditor/ckeditor.js?t=B37D54V'/>"></script>
<script type="text/javascript" src="<c:url value='/js/com/comm_ckeditor.js?version=${ver}'/>"></script>

<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<input type="hidden" id="page" name="page" value='<c:out value="${model.page}" />' /> 
	<input type="hidden" id="bbsSeCd" name="bbsSeCd" value='<c:out value="${model.bbsSeCd}" />' />
	<input type="hidden" id="pstClsfCd" name="pstClsfCd" value='<c:out value="${model.pstClsfCd}" />' />
	<input type="hidden" id="gsUserNo" name="gsUserNo" value='<c:out value="${model.gsUserNo}" />' />
</form>

<%-- 경영체의 경우에만 탭이 표시됨 --%>
<c:if test="${model.userInfo.ent}">
	<!-- 서브탭 메뉴 -->
	<div class="shadow-box-1 mb-24px px-24px">
		<div id="appMenuTab"></div>
	</div>
</c:if>

<div class="tit-box-3 mb-24px">
	<div class="row">
		<div class="col"></div>
		<div class="col flex-grow-0">
			<button id="btnQnA" class="btn-primary-ouline-hover border-0 bs-m bs-md-l">
				<i class="icon-chat-check-alt"></i>자주 묻는 질문
			</button>
		</div>
		<div class="col flex-grow-0">
			<button id="btnRegist" class="btn-primary-ouline-hover border-0 bs-m bs-md-l"
				onclick="$('#exampleModal').removeClass('show');">
				<i class="icon-messages-dots"></i>1:1문의하기
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
<!--//-->

<%-- ############################ 내용 (종료) ############################ --%>