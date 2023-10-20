<%--
*******************************************************************************
***    명칭: openMatching.jsp
***    설명: 마이페이지 - 매칭설정 화면
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

<%-- 매칭서비스 관련 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_matching.js'/>"></script>

<form id="matchForm" name="matchForm" method="post" onsubmit="return false;">

	<input type="hidden" id="updateYn" name="updateYn" value="<c:out value="${model.updateYn  }"/>"/>
	<input type="hidden" id="selectYn" name="selectYn" value="<c:out value="${model.selectYn  }"/>"/>

	<!-- 매칭설정 영역 -->
	<div id="appMatchingConfig"></div>
	
	<div class="bottom-box-btn my-16px">
		<div class="row">
		<c:if test="${model.updateYn == 'Y'}">
			<div class="col">
				<button id="btnSave" class="btn-combi-3 w-100 bs-l"><i class="icon-floppy-disk mr-5px"></i>저장하기</button>
			</div>
		</c:if>
			<div class="col">
				<button id="btnList" class="btn-primary w-100 bs-l">매칭결과<i class="icon-arrow-right ml-5px"></i></button>
			</div>
		</div>
	</div>
					
</form>

<%-- ############################ 내용 (종료) ############################ --%>
