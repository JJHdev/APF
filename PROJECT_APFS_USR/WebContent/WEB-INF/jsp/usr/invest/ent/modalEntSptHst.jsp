<%--
*******************************************************************************
***    명칭: modalEntSptHst.jsp
***    설명: 기타지원이력 작성하기 팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.24    LSH        First Coding.
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

<form:form commandName="model" id="popupForm" name="popupForm" method="post" onsubmit="return false;">
<form:hidden path="mode" id="p_mode" />
<form:hidden path="sn"   id="p_sn"   />
<form:hidden path="irNo" id="p_irNo" />

	<div class="form-box-1 border-bottom-0 mb-24px pb-0">
		<div class="designbox4 mb-24px">
			<div class="align-items-center row">
				<div class="col-12">
					<img src="<c:url value="/images/sub/MyPage4.svg"/>" alt="">
				</div>
				<div class="col-12">
					<p class="txt1">등록되어 있는 기관 외 타 기관에서 지원받은 이력 등을 작성하고 투자유치 가능성을 높여보세요</p>										
				</div>
				
			</div>
		</div>
		<div class="box mb-24px">
			<div class="form-area-box ">
				<label>분야</label>
				<div class="ele-icon-box">
					<form:input path="fldCn" id="p_fldCn" maxlength="50" placeholder="금융대출/정부지원/펀드유치 등 분야를 작성해 주세요"/>
					<i class="app-input-clear icon-times-circle F text-ele-TextDisable"></i>
				</div>
			</div>
		</div>
		
		<div class="box mb-24px">
			<div class="form-area-box">
				<label>지원기관</label>
				<div class="ele-icon-box">
					<form:input path="instNm" id="p_instNm" maxlength="50" placeholder="어디서 지원을 받으셨나요? 지원받은 기관/기업명을 작성해 주세요"/>
					<i class="app-input-clear icon-times-circle F text-ele-TextDisable"></i>
				</div>
				
			</div>
			
		</div>
		<div class="box mb-24px">
			<div class="form-area-box">
				<label>사업명</label>
				<div class="ele-icon-box">
					<form:input path="bizNm" id="p_bizNm" maxlength="50" placeholder="지원받은 사업명을 작성해 주세요"/>
					<i class="app-input-clear icon-times-circle F text-ele-TextDisable"></i>
				</div>
				
			</div>
			
		</div>
		<div class="box mb-24px">
			<div class="form-area-box">
				<label>세부내용</label>
				<div class="ele-icon-box">
					<form:textarea path="dtlCn" id="p_dtlCn" maxlength="50" style="height: 174px;" placeholder="50자 이내 작성"/>
				</div>
				
			</div>
		</div>
	</div>
	<div class="bottom-box-btn py-24px">
		<div class="row">
			<div class="col">
				<button id="p_btnSptHstSave" data-oper="S" type="button" class="btn-combi-3 w-100 bs-l"><i class="icon-floppy-disk"></i>저장</button>
			</div>
		<c:if test="${model.mode == 'I'}">
			<div class="col">
				<button id="p_btnSptHstSaveAdd" data-oper="A" type="button" class="btn-primary w-100 bs-l"><i class="icon-clipboard-plus"></i>저장 후 추가</button>
			</div>
		</c:if>
		<c:if test="${model.mode == 'U'}">
			<div class="col">
				<button id="p_btnSptHstRemove" data-oper="D" type="button" class="btn-primary w-100 bs-l"><i class="icon-trash"></i>삭제</button>
			</div>
		</c:if>
			<div class="col">
				<button id="p_btnSptHstCancel" type="button" class="btn-black w-100 bs-l"><i class="icon-times"></i>취소</button>
			</div>
		</div>
	</div>
	
</form:form>

<%-- ############################ 내용 (종료) ############################ --%>
