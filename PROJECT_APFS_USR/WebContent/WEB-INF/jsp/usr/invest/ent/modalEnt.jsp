<%--
*******************************************************************************
***    명칭: modalEnt.jsp
***    설명: 업체 선택 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.08.21    LSH        First Coding.
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
	<div class="form-area-box mb-24px">
		<div class="day">
			<div class="row">
				<div class="col">
					<div class="ele-icon-box">
						<input type="text" name="srchText" id="p_srchText" maxlength="50" placeholder="업체번호/업체명 등을 입력하세요.">
						<i class="app-input-clear icon-times-circle F text-ele-TextDisable"></i>
					</div>
				</div>
				<div class="col flex-grow-0 white-space-nowrap">
					<button id="p_btnEntSearch" type="button" class="btn-primary w-100 bs-l"><i class="icon-search"></i>검색</button>
				</div>
			</div>
		</div>
	</div>
	<div class="bg-primary-t5 p-16px round-8px">
		<p class="fs-13px fw-500 lh-16px text-primary">
			<i class="icon-info-circle ml-5px text-primary"></i>
			목록에서 IR 작성할 업체를 선택하세요.
		</p>
	</div>
	
	<!-- 목록 영역 -->
	<div id="appPopupGrid"></div>
	
	<!-- 목록 페이징 영역 -->
	<div class="mb-0 paging-box pb-24px">
		<ul id="appPopupGridPagination"></ul>
	</div>
	
</form>

<%-- ############################ 내용 (종료) ############################ --%>
