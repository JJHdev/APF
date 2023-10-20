<%--
*******************************************************************************
***    명칭: viewNotice.jsp
***    설명: 정보서비스 - 자료실 상세보기 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.05.18    LSH        First Coding.
***    1.1      2023.06.02    J H        그리드 표출및 상세조회.
***    1.2      2023.06.28    J H        작업완료.
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
	<form:hidden path="pstNo"   	/><%-- 게시물번호 --%>
	<form:hidden path="bbsSeCd" 	/><%-- 게시물구분 --%>
	<form:hidden path="mode"    	/><%-- 처리모드 --%>
	<form:hidden path="pstClsfCd"   /><%-- 구분코드 --%>
	
	<div class="post-details-1">
		<div class="top">
			<p class="txt1"><c:out value="${model.pstTtl}"/></p>
			<div class="infobox ">
				<div class="row justify-content-start">
					<div class="col">
						<p class="txt1">
							<i class="icon-calendar"></i>
							<span>작성일</span>
							<em><c:out value="${model.regDate}" /></em>
						</p>
					</div>
					<div class="col">
						<p class="txt1">
							<i class="icon-user"></i>
							<span>작성자명</span>
							<em>
							<c:choose>
							    <c:when test="${not empty model.mdfrNm}">
							        <c:out value="${model.mdfrNm}" />
							    </c:when>
							    <c:otherwise>
							        <c:out value="${model.rgtrNm}" />
							    </c:otherwise>
							</c:choose>
							</em>
						</p>
					</div>
					<div class="col">
						<p class="txt1">
							<i class="icon-user"></i>
							<span>조회수</span>
							<em><c:out value="${model.inqCnt}" /></em>
						</p>
					</div>
					<div class="col">
						<p class="txt1 app-pointer" onclick="STORE.copyLink('pstNo')">
							<i class="icon-link"></i>
							<span>주소복사</span>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="box fs-15px fw-500 ckEdit">
			<c:out value="${model.pstCn}" escapeXml="false"/>
		</div>
		<div class="box py-32px ver1">
			<div class="body">
				<div class="row ver1">
					<div class="col w-ver2">
						<p class="txt1 ver1"><i class="icon-paperclip mr-2px"></i>첨부파일</p>
					</div>
					<div class="col">
						<%-- 첨부파일영역 --%>
						<div id="attachFile"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 버튼영역 -->
	<div class="bottom-box-btn">
		<div class="row">
			<div class="col">
				<button id="btnList" class="btn-primary w-100 bs-xl"><i class="icon-list"></i>목록으로 돌아가기</button>
			</div>
		</div>
	</div>
</form:form>
<!-- 검색폼 -->		
<form:form commandName="search" id="searchForm" name="searchForm" method="post" onsubmit="return false;">
	<form:hidden path="page"     />
	<form:hidden path="srchText" />
	<form:hidden path="bbsSeCd"   id="s_bbsSeCd"/>
	<form:hidden path="pstClsfCd" id="s_pstClsfCd"/>
</form:form>
<%-- ############################ 내용 (종료) ############################ --%>
