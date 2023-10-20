<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
##========================================================================================
## 화면 상단 HTML 공통 영역
##
##========================================================================================
 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="ckeditor" uri="http://ckeditor.com"%>

<!-- HEADER START << ---------->
<div class="headadmin1" data-v-2eb60c26="">
	<div class="row">
		<div class="col page-title">
			<%-- 페이지 제목 --%>
			<p class="txt1"><i class="icon-notebook"></i> ${PAGEINFO.pageInfo.titleNm}</p>
			<button type="button" class="btn btnFc-1">
				<i class="icon-search-minus mr-3px"></i>검색조건 닫기
			</button>
		</div>
		<div class="col justify-content-end">
			<%-- 페이지 네비게이션 --%>
			<ul id="page-breadcrumbs"
				class="sitemap2 justify-content-end"
				data-path="<c:out value="${PAGEINFO.pageInfo.menuPath}"/>"
				data-menu="<c:out value="${PAGEINFO.pageInfo.menuId}"/>"
				data-url="<c:out value="${PAGEINFO.pageInfo.urlPath}"/>"></ul>
		</div>
	</div>
<!-- HEADER END   >> ---------->
</div>
