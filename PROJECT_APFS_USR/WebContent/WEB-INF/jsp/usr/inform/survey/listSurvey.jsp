<%--
*******************************************************************************
***    명칭: listSurvey.jsp
***    설명: 설문조사 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%-- ############################# 내용 (시작) ############################# --%>
<form id="searchForm" name="searchForm" method="post"
	onsubmit="return false;">
	<input type="hidden" id="page" name="page"
		value="<c:out value='${model.page}'/>" /> <input type="hidden"
		id="srchText" name="srchText"
		value="<c:out value='${model.srchText}'/>" /> <input type="hidden"
		id="bbsSeCd" name="bbsSeCd" value="<c:out value='${model.bbsSeCd}'/>" />
	<input type="hidden" id="pstClsfCd" name="pstClsfCd"
		value="<c:out value='${model.pstClsfCd}'/>" /> <input type="hidden"
		id="gsRoleId" name="gsRoleId"
		value="<c:out value='${model.gsRoleId}'/>" />
</form>

<!-- 목록 영역 -->
<div id="appGridPanel">
	<div class="shadow-box-1 px-16px pb-24px">
		<!-- 목록 컨텐츠 영역 -->
		<div id="appGrid"></div>
		<!-- 목록 페이징 영역 -->
		<div class="mb-0 paging-box pb-24px px-0">
			<ul id="appGridPagination"></ul>
		</div>
	</div>
</div>

<div class="custum-modal fade" id="exampleModal" tabindex="-1"
	aria-hidden="true">
	<div class="cutum-modal-dialog"
		style="max-width: 780px; top: 0; transform: none;">
		<div class="modal-content">

			<div class="modal-header border-black">
				<h4 class="modal-title d-none d-xl-inline-flex">
					<img src="<c:url value='/images/sub/Sketch_on_smartphone.svg'/>" alt="설문테스트"> 
					<span class="survyStaticHead srvyTitle">2023년투자유치 후 지원사업 수요조사</span>
				</h4>
				<button type="button" class="close-btn d-none d-xl-block"
					onclick="$('#exampleModal').removeClass('show');">
					<i class="icon-times F"></i>
				</button>
				<button type="button" class="close-btn d-xl-none"
					onclick="$('#exampleModal').removeClass('show');">
					<i class="icon-angle-left-circle"></i>
				</button>
			</div>
			<div class="modal-body pt-0">
				<div class="surveybox-1">
					<div class="infobox">
						<div class="row justify-content-start">
							<div class="col">
								<p class="txt1">
									<i class="icon-user"></i> 
									<span>작성자</span> 
									<em class="survyStaticHead regiNm">관리자</em>
								</p>
							</div>
							<div class="col">
								<p class="txt1">
									<i class="icon-user-edit"></i> 
									<span>참여인원</span> 
									<em class="survyStaticHead attendCnt">총 2,000명</em>
								</p>
							</div>

							<div class="col">
								<p class="txt1">
									<i class="icon-calendar"></i> 
									<span>조사기간</span> 
									<em class="survyStaticHead stDt">2022.02.25</em> ~ <em class="survyStaticHead edDt">2023.02.25</em>
								</p>
							</div>
						</div>
					</div>
					<div id="survyStaticDiv"></div>
				</div>
				<div class="bottom-box-btn py-24px">
					<div class="row">

						<div class="col">
							<button class="btn-primary w-100 bs-l" onclick="survySubmit();">
								<i class="icon-edit"></i>제출하기
							</button>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<%-- ############################# 내용 (종료) ############################# --%>
