<%--
*******************************************************************************
***    명칭: modalBbs.jsp
***    설명: 마이페이지 - 문의작성 팝업
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.20    KYW        First Coding.
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

<form:form commandName="model" id="p_registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden id="p_pstNo"   path="pstNo"   /><%-- 게시물번호 --%>
	<form:hidden id="p_bbsSeCd" path="bbsSeCd" /><%-- 게시물구분 --%>
	<form:hidden id="p_mode"    path="mode"    /><%-- 처리모드 --%>
	
	<div class="form-box-1 border-bottom-0 mb-10px pt-2 pb-0">
		<div class="designbox2 mb-24px">
			<div class="align-items-center row">
				<div class="col-12 col-md flex-md-grow-0">
					<img src='<c:url value="/images/sub/ServiceImg2.svg" />' alt="">
				</div>
				<div class="col">
					<p class="txt1">
						게시판에 글쓰기를 하는 경우, 본문 또는 첨부파일 내에 개인정보(주민등록번호, 성명, 연락처 등)가 포함되어
						게시되지 않도록 유의 하시기 바랍니다. 
					</p>
					<p class="txt1">개인정보를 포함하여 게시하는 경우 불특정 다수에게 개인정보가 노출되어 악용될 수
						있으며, 특히 타인의 개인정보가 노출되는 경우에는 개인정보보호법에 따라 처벌을 받을 수 있습니다.</p>
					<p class="txt1 ">
						<em>상담하기에 작성되는 모든 글은 비밀글로 작성자와 관리자만 확인이 가능합니다.</em>
					</p>
				</div>

			</div>
		</div>
		<div class="box mb-24px">
			<div class="row custum-row-1">
				<div class="col-4">
					<div id="appTopic" data-value="<c:out value="${model.pstClsfCd}"/>"></div>
				</div>
				<div class="col-8">
					<div class="form-area-box">
				<label>제목<em></em></label>
				<div class="ele-icon-box">
					<form:input id="p_pstTtl" path="pstTtl" maxlength="100" placeholder="제목을 80자 이내로 입력해주세요"/>
				</div>
				
			</div>
				</div>
			</div>
		</div>

		<div class="box mb-24px">
			<div class="form-area-box">
				<label>내용<em>*</em></label>
				<div class="ele-icon-box">
					<form:textarea id="p_pstCn" path="pstCn" cssStyle="height: 174px;"/>
				</div>
			</div>
		</div>
		<%-- 첨부파일영역 --%>
		<div class="box">
			<div id="p_appBbsFile"></div>
		</div>
	</div>
	
	<!-- 버튼영역 -->
	<div class="bottom-box-btn py-20px">
		<div class="row">
			<div class="col">
				<button id="p_btnSave" class="btn-primary w-100 bs-l"><i class="icon-edit"></i>작성완료</button>
			</div>
			<div class="col">
				<button id="p_btnCancel" class="btn-combi-3 w-100 bs-l"><i class="icon-times"></i>취소</button>
			</div>
		</div>
	</div>
</form:form>


<%-- ############################# 내용 (종료) ############################# --%>

