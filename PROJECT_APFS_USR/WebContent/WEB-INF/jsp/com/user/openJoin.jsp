<%--
*******************************************************************************
***    명칭: openJoin.jsp
***    설명: 회원가입 - 유형선택
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.03.14    LSH        First Coding.
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

<%-- 회원가입 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_join.js'/>"></script>

<form id="joinForm" name="joinForm" method="post" onsubmit="return false;">
<input type="hidden" id="g_userId"   name="userId"   value="<c:out value="${model.userId  }"/>"/>
<input type="hidden" id="g_joinCd"   name="joinCd"   value="<c:out value="${model.joinCd  }"/>"/>
<input type="hidden" id="g_stepCd"   name="stepCd"   value="<c:out value="${model.stepCd  }"/>"/>
<input type="hidden" id="g_naverUrl" name="naverUrl" value="<c:out value="${model.naverUrl}"/>"/>
<input type="hidden" id="g_kakaoUrl" name="kakaoUrl" value="<c:out value="${model.kakaoUrl}"/>"/>
</form>
	
<div id="appContent"></div>

<%-- ############################# 내용 (종료) ############################# --%>
