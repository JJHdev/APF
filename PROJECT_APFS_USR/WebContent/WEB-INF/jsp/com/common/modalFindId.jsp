<%--
*******************************************************************************
***    명칭: modalFindId.jsp
***    설명: 아이디 찾기 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자                내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.28      LHB             First Coding.
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

<script type="text/javascript" src="<c:url value='/js/com/common/modalFindId.js'/>"></script>

<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<input type="hidden" name="mode" value="FID" />
	<div class="modal-body pt-0">
		<div class="form-box-1 border-bottom-0 mb-24px pb-0">
			<div class="box mb-24px">
				<div class="form-area-box">
					<label>이름</label>
					<div class="ele-icon-box">
						<input type="text" name="userNm" id="userNm" placeholder="이름을 입력하세요.">
					</div>
				</div>
			</div>
			<div class="box mb-24px">
				<div class="form-area-box">
					<label>휴대전화번호</label>
					<div class="ele-icon-box">
						<input type="text" name="mblTelno" id="mblTelno" placeholder="- 제외 숫자만 입력하세요.">
					</div>
				</div>
			</div>
		</div>
		<div id="result-div" class="border-0 box mb-0 pb-0" style="display: none;">
			<p class="fs-16px fw-300 text-center text-red"><i class="icon-info-circle text-red"></i>일치하는 회원정보가 없습니다.</p>
			<p class="fs-16px fw-300 text-center">회원님의 아이디는 <em class="fs-16px text-primary">abc123@naver.com</em> 입니다.</p>
		</div>
		<div class="bottom-box-btn py-24px">
			<div class="row">
				<div class="col">
					<button id="btnSearch" class="btn-primary w-100 bs-l"><i class="icon-search"></i>찾기</button>
				</div>
				<div class="col">
					<button id="btnClose"  class="btn-black w-100 bs-l"><i class="icon-times"></i>닫기</button>
				</div>
			</div>
		</div>
	</div>
</form>

<%-- ############################# 내용 (종료) ############################# --%>