<%--
*******************************************************************************
***    명칭: listInvestCase.jsp
***    설명: 정보서비스 - 우수투자사례 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09     JH        우수투자사례 작업
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

<%-- ############################ 내용 (시작) ############################ --%>
<form:form commandName="model"  id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<form:hidden path="page"	 		 	 />
	<form:hidden path="srchText"	 		 />
	<form:hidden path="bbsSeCd"	 		 	 />
	<form:hidden path="pstClsfCd"	 		 />
<div class="shadow-box-1 mb-16px px-24px py-16px">
	<div class="design-txt-1">
		<div class="row">
			<div class="col flex-b-md-210px">
				<p class="fs-15px fw-600">전체 <em class="fw-500 text-primary" id="total"></em>건</p>
			</div>
			<div class="col flex-b180px" >
				<div class="form-area-box ">
					<div class="ele-icon-box">
						<div class="dropdown">
							<button class="dropdown-toggle" type="button" id="btnSrchType" data-bs-toggle="dropdown" aria-expanded="false">
								정렬선택
							</button>
							<ul class="dropdown-menu w-100 bg-white">
							</ul>
							<form:hidden path="srchType" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form:form>

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
<%-- ############################ 내용 (종료) ############################ --%>
