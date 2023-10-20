<%--
*******************************************************************************
***    명칭: openSrchWord.jsp
***    설명: 운영관리-검색어 관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.25    JH          First Coding.
***    1.1      2023.06.27    JH          작업 완료.
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
<!-- CENTER CONTENT START << ---------->
<div class="content">
<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
  	<input type="hidden" name="page" id="page" value="<c:out value='${model.page}'/>"/>
</form>
		<div class="eletabletop1">
			<div class="row">
				<div class="col">
					<!-- 목록검색결과 영역 -->
					<p class="txt1" id="appGridResult"></p>
				</div>
				<div class="col element">
					<button class="btn-mint bs-m" id="btnExcel"><i class="icon-file-xls"></i> 엑셀 다운로드
				</button>
			</div>
			</div>
		</div>
		<div class="custom-ext-box pb-24px pt-0 px-16px tbox-prent">
			<!-- 목록 컨텐츠 영역 -->
			<div id="appGrid" class="pb-8px"></div>
			
			<div class="row justify-content-between">
				<div class="col flex-grow-0 white-space-nowrap">
				</div>
			</div>
		</div>
<!-- CENTER CONTENT END   >> ---------->
</div>
<!--//detail-box-->
<div class="modal fade" id="myModal1" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog custom-modal design1" role="document">
		<div class="modal-content" style="width: 531px;">
			<div class="modal-header">
				<p class="txt1">
					<i class="icon-copy-alt"></i>검색어 키워드 삭제
				</p>
				<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="icon-times"></i></button>
			</div>
			
			<div class="modal-body">
				<div class="align-content-center align-items-sm-center bg-primary-t5 d-flex fs-14px justify-content-center py-30px round-16px text-primary">
					<i class="fs-28px icon-info-circle mr-8px text-primary"></i>검색 키워드를 삭제하시겠습니까?
				</div>
			</div>
			<div class="modal-footer">
				<div class="bottom-btn-box mt-16px">
					<div class="row">
						<div class="col">
							<a href="" class="btn-combi-3 bs-m " id="btnCancel" data-bs-dismiss="modal"><i class="icon-times"></i>취소</a>
						</div>
						<div class="col">
							<a href="" class="btn-black bs-m" id="btnRemove"><i class="icon-edit"></i> 삭제</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%-- ############################# 내용 (종료) ############################# --%>
