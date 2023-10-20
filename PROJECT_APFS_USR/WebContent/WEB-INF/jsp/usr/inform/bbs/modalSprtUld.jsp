<%--
*******************************************************************************
***    명칭: modalSprtUld.jsp
***    설명: 정보서비스-경영체 데이터 업로드 파일업로드 모달팝업 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13      LHB     First Coding.
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

<!-- FORM START -->
<form id="registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<input type="hidden" id="mode" name="mode" value="I" />
	<div class="w-100">
		<!-- 컨텐츠시작 -->
		<div class="row">
			<div class="col-12">
				<div class="form-area-box">
					<div class="day pt-16px">
						<div class="input-btn" id="attachFile"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- 컨텐츠종료 -->
	</div>
	<div class="bottom-btn-box" style="float: right;">
		<div class="row">
			<div class="col"><a id="btnSave"   href="javascript:void(0)" class="bs-l btn-primary px-14px"><i class="icon-copy-alt"></i>저장</a></div>
		</div>
	</div>
</form>
<!-- FORM END -->

<%-- ############################ 내용 (종료) ############################ --%>
