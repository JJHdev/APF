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

<div class="w-100">
	<div class="designbox2 mb-24px">
		<div class="align-items-center row">
			<div class="col text-center">
				<p class="fs-14px fw-600">
					KODATA 데이터와 매핑 실패한 사업자번호 입니다.</br>
					오탈자가 없는지 검토 후 이상 없을 시</br>
					양식의 <span class="text-primary">강제업로드 항목에 Y표시하여 업로드</span>해주세요.
				</p>
			</div>
		</div>
	</div>
	<!-- 컨텐츠시작 -->
	<div class="row">
		<div class="col ps-4">
			<p class="txt1 fw-600">
				KODATA 매핑 실패
				<span id="dataNum" class="text-primary fs-18px fw-700">N</span>건
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-12">
			<!-- 목록 영역 -->
			<div id="appGridPanel" style="overflow-y: auto; max-height: 520px;">
				<div class="shadow-box-1 px-16px pb-24px">
					<!-- 목록 컨텐츠 영역 -->
					<div id="appGridFail"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 컨텐츠종료 -->
</div>

<%-- ############################ 내용 (종료) ############################ --%>
