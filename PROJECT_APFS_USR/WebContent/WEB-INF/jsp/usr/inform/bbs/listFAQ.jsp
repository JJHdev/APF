<%--
*******************************************************************************
***    명칭: listFAQ.jsp
***    설명: 정보서비스 - 자주묻는질문 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.07    J H        First Coding.
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
	<input type="hidden" id="page"      name="page"      value="<c:out value='${empty model.page ? 1 : model.page}'/>"/>
	<input type="hidden" id="srchText"  name="srchText"  value="<c:out value='${model.srchText}'/>"/>
	<input type="hidden" id="bbsSeCd"   name="bbsSeCd"   value="<c:out value='${model.bbsSeCd}'/>"/>
	<input type="hidden" id="pstClsfCd" name="pstClsfCd" value="<c:out value='${model.pstClsfCd}'/>"/>
</form>

<!-- 서브탭 메뉴 -->
<div class="shadow-box-1 mb-24px px-24px">
	<div class="tabmenu-1">
		<div id="appFAQTab"></div>
	</div>
</div>
<!-- 목록 영역 -->
<div id="appGridPanel">
	<!-- 목록 컨텐츠 영역 -->
	<div class="datum-point">
		<div class="accordion">
			<ul></ul>
		</div>
	</div>
	<div class="paging-box mb-0 mt-24px pb-24px px-0">
	</div>
</div>
<div class="designbox1">
	<div class="align-items-center row">
		<div class="col flex-grow-0">
			<img id="FAQImage" src="<c:url value='/images/sub/ServiceImg1.svg'/>" alt="자주묻는질문" />
		</div>
		<div class="col">
			<p class="txt1">1:1 문의</p>
			<span class="txt2">추가적으로 궁금한 사항이 있으시다면 1:1문의를 이용해주세요</span>
		</div>
		<div class="col flex-grow-0 white-space-nowrap">
			<button class="btn-black bs-xl" id="btnGoQna"><i class="icon-messages-text mr-2px"></i>1:1 문의</button>
		</div>
	</div>
</div>
<%-- ############################# 내용 (종료) ############################# --%>
