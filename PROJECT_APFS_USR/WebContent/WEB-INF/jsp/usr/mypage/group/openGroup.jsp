<%--
*******************************************************************************
***    명칭: openGroup.jsp
***    설명: 마이페이지 - 그룹관리 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.27    LSH        First Coding.
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
	<input type="hidden" id="pageCd"   name="pageCd"   value="<c:out value="${model.pageCd    }"/>"/>
	<input type="hidden" id="pageNm"   name="pageNm"   value="<c:out value="${model.pageNm    }"/>"/>
	<input type="hidden" id="bzentyNo" name="bzentyNo" value="<c:out value="${model.gsBzentyNo}"/>"/>
	<input type="hidden" id="accessYn" name="accessYn" value="<c:out value="${model.accessYn  }"/>"/>
	<input type="hidden" id="rprsYn"   name="rprsYn"   value="<c:out value="${model.rprsYn    }"/>"/>
	<input type="hidden" id="groupCd"  name="groupCd"  value="<c:out value="${model.groupCd   }"/>"/>
</form>

<form id="groupForm" name="groupForm" method="post" onsubmit="return false;">
	<!-- 탭 영역 -->
	<div id="appMenuTab"></div>
	
	<!-- 컨텐츠 영역 -->
	<div id="appContents"></div>
</form>

<%-- ############################ 내용 (종료) ############################ --%>
