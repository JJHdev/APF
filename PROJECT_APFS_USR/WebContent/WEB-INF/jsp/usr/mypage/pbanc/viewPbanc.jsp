<%--
*******************************************************************************
***    명칭: openPbanc.jsp
***    설명: 마이페이지 - 사업공고등록 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.21    J H        작업완료.
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

<form:form commandName="model" id="selectForm" name="selectForm" method="post" onsubmit="return false;">
	<form:hidden path="bizPbancNo" 		/><%-- 사업공고번호 --%>
	<form:hidden path="mode"       		/><%-- 처리모드 --%>
	<form:hidden path="rcptBgngDt"      /><%-- 처리모드 --%>
	<form:hidden path="rcptBgngTm"      /><%-- 처리모드 --%>
	<form:hidden path="rcptEndDt"       /><%-- 처리모드 --%>
	<form:hidden path="rcptEndTm"       /><%-- 처리모드 --%>
	<form:hidden path="rcptSeCd"       	/><%-- 처리모드 --%>
	<form:hidden path="sprtCn"       	/><%-- 처리모드 --%>
	<form:hidden path="sbmsnDcmntCn"    /><%-- 처리모드 --%>
	<form:hidden path="aplyExclTrgtCn"  /><%-- 처리모드 --%>
	<form:hidden path="aplyQlfcCn"      /><%-- 처리모드 --%>
	<form:hidden path="rcptMthdCn"		/><%-- 처리모드 --%>
	<form:hidden path="page"			/><%-- 처리모드 --%>
	<form:hidden path="divisionBkmk"	/><%-- 처리모드 --%>
	
	<!-- 상세조회 -->
	<div class="post-details-1">
		<div class="top">
			<p class="txt1"><c:out value="${model.bizPbancNm}"/></p>
			<div class="infobox">
				<div class="row">
					<div class="col-12">
						<p class="txt1">
							<i class="icon-notebook"></i>
							<span>사업분야</span>
							<em><c:out value="${model.bizFldNm}"/></em>
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-xl-4">
						<p class="txt1">
							<i class="icon-briefcase"></i>
							<span>지원대상</span>
							<em><c:out value="${model.bizTrgtNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-4">
						<p class="txt1">
							<i class="icon-building"></i>
							<span>기관명</span>
							<em><c:out value="${model.crdnsBzentyNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-4">
						<p class="txt1">
							<i class="icon-flag"></i>
							<span>담당부서</span>
							<em><c:out value="${model.tkcgDeptNm}"/></em>
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-xl-4">
						<p class="txt1">
							<i class="icon-calendar-clock"></i>
							<span>창업기간</span>
							<em><c:out value="${model.bizTrgtFntnPdNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-4">
						<p class="txt1">
							<i class="icon-user"></i>
							<span>담당자명</span>
							<em><c:out value="${model.picNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-4">
						<p class="txt1">
							<i class="icon-phone"></i>
							<span>연락처</span>
							<em><c:out value="${model.picTelno}"/></em>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="box">
			<div class="top">
				<p class="text-icon-3">
					<i class="icon-lightbulb cycle-ver-1"></i>신청방법 및 대상
				</p>
			</div>
			<div class="body">
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">신청기간</p>
					</div>
					<div class="col">
						<p class="txt2">
						<c:if test="${model.rcptSeCd != '20'}">
							<c:out value="${model.rcptBgngDt}"/> 
							<label id="F_rcptBgngDt"></label>
							<label id="F_rcptBgngTm"></label>
								~
							<c:out value="${model.rcptEndDt}"/>
							<label id="F_rcptEndDt"></label>
							<label id="F_rcptEndTm"></label>
						</c:if>
						<c:if test="${model.rcptSeCd == '20'}">
							<label>상시모집</label>
						</c:if>
						</p>
					</div>
				</div>
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">신청방법</p>
					</div>
					<div class="col">
						<p class="txt2" id="t_rcptMthdCn"></p>
					</div>
				</div>
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">신청대상</p>
					</div>
					<div class="col">
						<p class="txt2" id="t_aplyQlfcCn"></p>
					</div>
				</div>
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">제외대상</p>
					</div>
					<div class="col">
						<p class="txt2" id="t_aplyExclTrgtCn"></p>
					</div>
				</div>
				<div class="text-box-1 p-16px bg-red-t5 mb-16px ">
					<div class="row">
						<div class="col "><i class="icon-octagon-exclamation text-red"></i></div>
						<div class="col-12 col-md text-red">신청 시 요청하는 정보(개인정보포함)는 사업운영기관에서 관리되오니 이점 반드시 유의하여 주시기 바랍니다.</div>
					</div>
				</div>
			</div>

		</div>
		<div class="box">
			<div class="top">
				<p class="text-icon-3">
					<i class="icon-clipboard-list-check cycle-ver-1"></i>제출서류
				</p>
			</div>
			<div class="body">
				<div class="row mb-16px">
					<div class="col-12">
						<p class="txt1">신청필수서류 및 발표자료, 기타 서류 등</p>
					</div>
					
				</div>
				
				<div class="row mb-2">
					
					<div class="col">
						<p class="txt2" id="t_sbmsnDcmntCn"></p>
					</div>
				</div>
				<div class="text-box-1 p-16px bg-red-t5 mb-16px">
					<div class="row">
						<div class="col"><i class="icon-octagon-exclamation text-red"></i></div>
						<div class="col-12 col-md text-red">제출하신 서류는 사업운영기관에서 관리되오니 서류 반황 등 문의는 해당기관으로 하시기 바랍니다.</div>
					</div>
				</div>
			</div>

		</div>
		<div class="box">
			<div class="top">
				<p class="text-icon-3">
					<i class="icon-presentation-text cycle-ver-1"></i>지원내용
				</p>
			</div>
			<div class="body">
				<div class="row mb-16px">
					<div class="col">
						<p class="txt2" id="t_sprtCn"></p>
					</div>
				</div>
			</div>
		</div>
		<div class="box py-32px ver1">
			<div class="body" id="attachFile"></div>
		</div>
	</div>
	<div class="bottom-box-btn">
		<div class="row">
		<c:if test="${not empty model.bizGuidanceUrl}">
			<div class="col">
				<button id="btnLink" data-value="<c:out value="${model.bizGuidanceUrl}"/>"class="btn-combi-3 w-100 bs-xl"><i class="icon-edit"></i>신청 바로가기</button>
			</div>
		</c:if>
			<div class="col">
				<button id="btnList" class="btn-primary w-100 bs-xl"><i class="icon-list"></i>목록으로 돌아가기</button>
			</div>
			<div class="col">
				<div id="btnModify" class="btn-primary w-100 bs-xl"><i class="icon-rotate-right mr-5px"></i>수정</div>
			</div>		
			<div class="col">
				<div id="btnRemove" class="btn-primary w-100 bs-xl"><i class="icon-trash"></i>삭제하기</div>
			</div>		
		</div>
	</div>
</form:form>

<form:form commandName="search" id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<form:hidden id="s_page"          path="page"           />
	<form:hidden id="s_showMode"      path="showMode"       />
	<form:hidden id="s_srchText"      path="srchText"       />
	<form:hidden id="s_divisionBkmk"      path="divisionBkmk"       />
</form:form>
<%-- ############################ 내용 (종료) ############################ --%>
