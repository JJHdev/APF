<%--
*******************************************************************************
***    명칭: openInfo.jsp
***    설명: 마이페이지 - 기본정보 화면
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

<%-- 마이페이지 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_mypage.js'/>"></script>

<form id="mypageForm" name="mypageForm" method="post" onsubmit="return false;">
	<input type="hidden" id="userNo"   name="userNo"   value="<c:out value="${model.gsUserNo  }"/>"/>
	<input type="hidden" id="bzentyNo" name="bzentyNo" value="<c:out value="${model.gsBzentyNo}"/>"/>
	<input type="hidden" id="accessYn" name="accessYn" value="<c:out value="${model.accessYn  }"/>"/>
	<input type="hidden" id="selectYn" name="selectYn" value="<c:out value="${model.selectYn  }"/>"/>
	<input type="hidden" id="updateYn" name="updateYn" value="<c:out value="${model.updateYn  }"/>"/>
	<input type="hidden" id="kodataYn" name="kodataYn" value="<c:out value="${model.kodataYn  }"/>"/>
	<input type="hidden" id="rprsYn"   name="rprsYn"   value="<c:out value="${model.rprsYn    }"/>"/>
</form>
        
<!-- 개인정보 -->
<div id="appUsrInfo" class="mb-32px"></div>
<!-- 기업정보 -->
<div id="appBizInfo" class="mb-32px"></div>

<%-- ############################ 내용 (종료) ############################ --%>
