<%--
*******************************************************************************
***    명칭: listSprtUld.jsp
***    설명: 정보서비스 - 경영체 데이터 업로드 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자            내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13      LHB        First Coding.
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

<style>
	td { word-wrap: break-word; }
</style>

<%-- ############################ 내용 (시작) ############################ --%>
<div class="designbox2 mb-24px">
	<div class="align-items-center row">
		<div class="col-12 col-md flex-md-grow-0">
			<!-- <img src="/images/sub/ServiceImg2.svg" alt=""> -->
		</div>
		<div class="col">
			<p class="txt1">
				반드시 <em class="">양식을 다운로드 받아 작성 후 업로드</em>해주세요.
			</p>
			<p class="txt1">한 번에 1개 파일만 업로드 가능합니다.</p>
			<p class="txt1">
				업로드하신 파일은 본 플랫폼의 경영체 검색 결과에 표출됩니다.
				(KoDATA 사업자번호 연계하여 IR 정보 및 경영체의 정부지원사업 참여 이력 표출)
			</p>
		</div>
	</div>
</div>
<div class="grid row">
	<div id="gridUld" class="col">
		<form:form commandName="model" id="searchForm" name="searchForm" method="post" onsubmit="return false;">
			<form:hidden path="page" />
			<div class="shadow-box-1 mb-16px px-24px py-16px">
				<div class="design-txt-1">
					<div class="row">
						<div class="col flex-b-md-210px">
							<p class="fs-15px fw-600">전체 <em class="fw-500 text-primary" id="total"></em>건</p>
						</div>
						<div class="col flex-b180px" >
							<div class="form-area-box ">
								<div class="ele-icon-box">
									<div class="dropdown">
										<button class="dropdown-toggle" type="button" id="btnSrchType" data-bs-toggle="dropdown" aria-expanded="false">
											정렬선택
										</button>
										<ul class="dropdown-menu w-100 bg-white">
										</ul>
										<form:hidden path="srchType" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
		
		<!-- 목록 영역 -->
		<div id="appGridPanel">
			<div class="shadow-box-1 px-16px pb-24px">
				<!-- 목록 컨텐츠 영역 -->
				<div id="appGrid"></div>
				<!-- 목록 페이징 영역 -->
				<c:if test="${model.gsUserNo != null}">
					<div class="paging-box pb-24px justify-content-between px-0 mb-0">
						<ul id="appGridPagination" class="d-inline-block"></ul>
						<!-- 버튼영역 -->
						<div>
							<button id="btnDownload" class="btn-black bs-m bs-md-l"><i class="icon-download mr-5px"></i>양식 다운로드</button>
							<button id="btnUpload"   class="btn-black bs-m bs-md-l"><i class="icon-edit mr-5px"    ></i>파일 업로드</button>
						</div>
					</div>
				</c:if>
				<c:if test="${model.gsUserNo == null}">
					<div class="mb-0 paging-box pb-24px px-0">
						<ul id="appGridPagination" class="d-inline-block"></ul>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<div id="gridBiz" class="col mt-5 d-none" >
		<div class="shadow-box-1 mb-16px px-24px py-16px">
			<div class="design-txt-1">
				<div class="row justify-content-end">
					<div class="col">
						<p class="fs-13px fw-600">선택한 데이터</p>
					</div>
					<div class="col" >
						<button type="button" id="btnDelete" class="btn-combi-3 bs-m "><i class="icon-trash"></i>삭제</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 목록 영역 -->
		<div id="appGridPanel2">
			<div class="shadow-box-1 px-16px pb-24px">
				<!-- 목록 컨텐츠 영역 -->
				<div id="appGridBiz"></div>
				<!-- 목록 페이징 영역 -->
				<div class="mb-0 paging-box pb-24px px-0">
					<ul id="appGridPaginationBiz" class="d-inline-block"></ul>
				</div>
			</div>
		</div>
	</div>
</div>
<%-- 로그인 상태에서만 보이도록 처리 --%>
	
<%-- ############################ 내용 (종료) ############################ --%>
