<%--
*******************************************************************************
***    명칭: openSprt.jsp
***    설명: 마이페이지 - 신청내역 화면
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

<form id="mypageForm" name="mypageForm" method="post" onsubmit="return false;">
	<input type="hidden" id="aplyCd"   name="aplyCd"   value="<c:out value="${model.aplyCd  }"/>"/>
	<input type="hidden" id="aplyNm"   name="aplyNm"   value="<c:out value="${model.aplyNm  }"/>"/>
	<input type="hidden" id="aplyType" name="aplyType" value="<c:out value="${model.aplyType}"/>"/>
	<input type="hidden" id="prgrmNo"  name="prgrmNo"  value="<c:out value="${model.prgrmNo }"/>"/>
	<input type="hidden" id="updateYn" name="updateYn" value="<c:out value="${model.updateYn}"/>"/>
</form>

<!-- 버튼탭 영역 -->
<div id="appButtonTabs"></div>

<!-- 제목 영역 -->
<div id="appMenuTitle"></div>

<!-- 탭 영역 -->
<div id="appMenuTab"></div>

<!-- 컨텐츠 영역 -->
<div id="appContents"></div>

<%-- ############################ 내용 (종료) ############################ --%>
