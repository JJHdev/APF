<%--
*******************************************************************************
***    명칭: modalEntPtnt.jsp
***    설명: 특허 및 상표권 보유현황 세부목록 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.12    LSH        First Coding.
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

<form id="popupForm" name="popupForm" method="post" onsubmit="return false;">
	<!-- 목록 영역 -->
	<div id="appPopupGrid"></div>
		<p class="mb-0 pb-0 pt-10px fs-14px text-primary">
			<i class="icon-exclamation-circle"></i>
			세부내용은 특허정보검색서비스 키프리스(<a href="http://www.kipris.or.kr/" target="_blank">http://www.kipris.or.kr/</a>)에서 검색해주시기 바랍니다
		</p>
	<!-- 목록 페이징 영역 -->
	<div class="mb-0 pt-15px paging-box pb-24px">
		<ul id="appPopupGridPagination"></ul>
	</div>
	
</form>

<%-- ############################ 내용 (종료) ############################ --%>
