<%--
*******************************************************************************
***    명칭: formSprt.jsp
***    설명: 투자지원신청 폼화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.25    LSH        First Coding.
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

<%-- 투자지원신청 관련 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_support.js'/>"></script>

<form id="selectForm" name="selectForm" method="post" onsubmit="return false;">
	<input type="hidden" id="mode"         name="mode"         value="<c:out value="${model.mode        }"/>"/>
	<input type="hidden" id="stepNo"       name="stepNo"       value="<c:out value="${model.stepNo      }"/>"/><%-- 신청단계번호 --%>
	<input type="hidden" id="prgrmNo"      name="prgrmNo"      value="<c:out value="${model.prgrmNo     }"/>"/><%-- 신청종류코드 --%>
	<input type="hidden" id="prgrmNm"      name="prgrmNm"      value="<c:out value="${model.prgrmNm     }"/>"/><%-- 신청종류명칭 --%>
	<input type="hidden" id="sprtSeCd"     name="sprtSeCd"     value="<c:out value="${model.sprtSeCd    }"/>"/><%-- 신청구분코드 --%>
	<input type="hidden" id="sprtSeNm"     name="sprtSeNm"     value="<c:out value="${model.sprtSeNm    }"/>"/><%-- 신청구분명칭 --%>
	<input type="hidden" id="gsBzentyYn"   name="gsBzentyYn"   value="<c:out value="${model.gsBzentyYn  }"/>"/><%-- 업체회원여부 --%>
	<input type="hidden" id="dscsnAplyNo"  name="dscsnAplyNo"  value="<c:out value="${model.dscsnAplyNo }"/>"/><%-- 상담신청번호 --%>
	<input type="hidden" id="bzentyNo"     name="bzentyNo"     value="<c:out value="${model.gsBzentyNo  }"/>"/><%-- 신청업체번호 --%>

<!-- 서브탭 메뉴 -->
<div id="appSprtTab"></div>

<p id="appSprtTitle"></p>
					
<!-- 단계표시 박스 -->
<div id="appStepBox"></div>

<!-- 컨텐츠 영역 -->
<div id="appContent" class="post-details-2"></div>

<!-- 버튼 영역 -->
<div id="appButtons" class="bottom-box-btn"></div>

</form>

<%-- ############################ 내용 (종료) ############################ --%>
