<%--
*******************************************************************************
***    명칭: modalSprtForm.jsp
***    설명: 마이페이지 - 신청내역 - 신청정보조회 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.22    LSH        First Coding.
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

<form id="popupSelectForm" name="popupSelectForm" method="post" onsubmit="return false;">

	<input type="hidden" id="s_aplyNo"   name="aplyNo"   value="<c:out value="${model.aplyNo  }"/>"/>
	<input type="hidden" id="s_aplyCd"   name="aplyCd"   value="<c:out value="${model.aplyCd  }"/>"/>
	<input type="hidden" id="s_prgrmNo"  name="prgrmNo"  value="<c:out value="${model.prgrmNo }"/>"/>

	<div id="appPopupSprtView"></div>

</form>
							
<div class="bottom-box-btn py-24px">
	<div class="row">
		<c:if test="${model.updateYn == 'Y'}">
			<div class="col flex-grow-1"></div>
			<div class="col">
				<button id="s_btnClose" class="btn-primary w-100 bs-l"><i class="icon-times"></i>닫기</button>
			</div>
			<div class="col flex-grow-1 text-end">
				<button id="s_btnUpdate" class="btn-combi-3 bs-l"><i class="icon-repeat"></i>수정</button>
			</div>
		</c:if>
		<c:if test="${model.updateYn != 'Y'}">
			<div class="col">
				<button id="s_btnClose" class="btn-primary w-100 bs-l"><i class="icon-times"></i>닫기</button>
			</div>
		</c:if>
	</div>
</div>
							
<%-- ############################# 내용 (종료) ############################# --%>

