<%--
*******************************************************************************
***    명칭: openEntIr.jsp
***    설명: 관리자IR작성 화면
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

<%-- 경영체정보 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_dashboard.js'/>"></script>

<%-- IR작성하기 공통 스크립트 --%>
<script type="text/javascript" src="<c:url value='/js/com/app_entir.js'/>"></script>

<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chart.umd.js'/>"></script>
<script src="<c:url value='/plugins/charts/chartjs-4.3.0/chartjs-plugin-datalabels.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/com/comm_chart.js'/>"></script>

<div id="appBzenty" class="mb-24px"></div>

<form id="registForm" name="registForm" method="post" onsubmit="return false;">
<input type="hidden" id="mode"     name="mode"     value="<c:out value="${model.mode      }"/>"/>
<input type="hidden" id="pageCd"   name="pageCd"   value="<c:out value="${model.pageCd    }"/>"/>
<input type="hidden" id="bzentyNo" name="bzentyNo" value="<c:out value="${model.bzentyNo  }"/>"/>
<input type="hidden" id="bzentyNm" name="bzentyNm" value="<c:out value="${model.bzentyNm  }"/>"/>
<input type="hidden" id="irNo"     name="irNo"     value="<c:out value="${model.irNo      }"/>"/>
<input type="hidden" id="updateYn" name="updateYn" value="<c:out value="${model.updateYn  }"/>"/>
<input type="hidden" id="selectYn" name="selectYn" value="<c:out value="${model.selectYn  }"/>"/>
</form>

<div id="appMenuTab"></div>

<div id="appContent"></div>

<%-- ############################ 내용 (종료) ############################ --%>
