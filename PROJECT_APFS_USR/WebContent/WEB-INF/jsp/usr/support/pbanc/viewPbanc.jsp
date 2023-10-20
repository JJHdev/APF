<%--
*******************************************************************************
***    명칭: viewPbanc.jsp
***    설명: 지원서비스 - 지원사업 통합검색 상세조회 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.02    LSH        First Coding.
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
	<form:hidden path="bizPbancNo" /><%-- 사업공고번호 --%>
	<form:hidden path="mode"       /><%-- 처리모드 --%>
	<!-- 상세조회 -->
	<div class="post-details-1">
		<div class="top">
			<p class="txt1"><c:out value="${model.bizPbancNm}"/></p>
			<div class="infobox">
				<div class="row">
					<div class="col-12 col-xl-6">
						<p class="txt1">
							<i class="icon-notebook"></i>
							<span>사업분야</span>
							<em class="app-pre"><c:out value="${model.bizFldNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-3">
						<p class="txt1">
							<i class="icon-briefcase"></i>
							<span>지원연령</span>
							<em><c:out value="${model.bizTrgtAgeNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-3">
						<p class="txt1">
							<i class="icon-building"></i>
							<span>기관명</span>
							<em><c:out value="${model.crdnsBzentyNm}"/></em>
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-xl-6">
						<p class="txt1">
							<i class="icon-briefcase"></i>
							<span>지원대상</span>
							<em><c:out value="${model.bizTrgtNm}"/></em>
						</p>
					</div>

					<div class="col-12 col-xl-3">
						<p class="txt1">
							<i class="icon-calendar-clock"></i>
							<span>창업기간</span>
							<em><c:out value="${model.bizTrgtFntnPdNm}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-3">
						<p class="txt1">
							<i class="icon-flag"></i>
							<span>담당부서</span>
							<em><c:out value="${model.tkcgDeptNm}"/></em>
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-xl-6">
					</div>
					<div class="col-12 col-xl-3">
						<p class="txt1">
							<i class="icon-phone"></i>
							<span>연락처</span>
							<em><c:out value="${model.picTelno}"/></em>
						</p>
					</div>
					<div class="col-12 col-xl-3">
						<p class="txt1">
							<i class="icon-user"></i>
							<span>담당자명</span>
							<em><c:out value="${model.picNm}"/></em>
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
						<p class="txt2"><c:out value="${model.rcptBgngDt}"/> ~ <c:out value="${model.rcptEndDt}"/></p>
					</div>
				</div>
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">신청방법</p>
					</div>
					<div class="col">
						<p class="txt2 app-pre"><c:out value="${model.rcptMthdCn}"/></p>
					</div>
				</div>
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">신청대상</p>
					</div>
					<div class="col">
						<p class="txt2 app-pre"><c:out value="${model.aplyQlfcCn}"/></p>
					</div>
				</div>
				<div class="row mb-2">
					<div class="col w-ver1">
						<p class="txt1">제외대상</p>
					</div>
					<div class="col">
						<p class="txt2 app-pre"><c:out value="${model.aplyExclTrgtCn}"/></p>
					</div>
				</div>
				<div class="text-box-1 p-16px bg-red-t5 mb-16px ">
					<div class="row">
						<div class="col-12 text-red"><i class="icon-octagon-exclamation text-red"></i> 신청 시 요청하는 정보(개인정보포함)는 사업운영기관에서 관리되오니 이점 반드시 유의하여 주시기 바랍니다.</div>
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
						<p class="txt2 app-pre"><c:out value="${model.sbmsnDcmntCn}"/></p>
					</div>
				</div>
				<div class="text-box-1 p-16px bg-red-t5 mb-16px">
					<div class="row">
						<div class="col-12 text-red"><i class="icon-octagon-exclamation text-red"></i> 제출하신 서류는 사업운영기관에서 관리되오니 서류 반환 등 문의는 해당기관으로 하시기 바랍니다.</div>
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
						<p class="txt2 app-pre"><c:out value="${model.sprtCn}"/></p>
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
		</div>
	</div>
</form:form>

<form:form commandName="search" id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<form:hidden id="s_page"          path="page"           />
	<form:hidden id="s_showMode"      path="showMode"       />
	<form:hidden id="s_ordrField"     path="ordrField"      />
	<form:hidden id="s_srchText"      path="srchText"       />
	<form:hidden id="s_exPrgrsCd"     path="exPrgrsCd"      />
	<form:hidden id="s_prgrsSttsCd"   path="prgrsSttsCd"    />
	<form:hidden id="s_sprtFldCd"     path="sprtFldCd"      />
	<form:hidden id="s_bizFld"        path="bizFld"         />
	<form:hidden id="s_bizTrgt"       path="bizTrgt"        />
	<form:hidden id="s_bizTrgtAge"    path="bizTrgtAge"     />
	<form:hidden id="s_bizTrgtFntnPd" path="bizTrgtFntnPd"  />
	<form:hidden id="s_rcptSeCd"      path="rcptSeCd"       />
	<form:hidden id="s_rcptMthdCd"    path="rcptMthdCd"     />
	<form:hidden id="s_pbancSttsCd"   path="pbancSttsCd"    />
</form:form>
			
<%-- ############################ 내용 (종료) ############################ --%>
