<%--
*******************************************************************************
***    명칭: modalBizNo.jsp
***    설명: 공통 - 사업자 등록번호 조회팝업
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.14    LSH        First Coding.
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

<form id="kodataForm" name="kodataForm" method="post" onsubmit="return false;">
	<input type="hidden" id="p_brno"  name="brno">
	<div class="form-box-of-member pt-0 mb-24">
		<div class="form-area-box">
			<label>본사사업자등록번호를 입력 후 검색해 주세요</label>
			<div class="input-btn">
				<div class="row">
					<div class="col">
						<div class="ele-icon-box">
							<input type="text" name="brno1" id="p_brno1" maxlength="3" placeholder="000">
						</div>
					</div>
					<div class="col">
						<div class="ele-icon-box">
							<input type="text" name="brno2" id="p_brno2" maxlength="2" placeholder="00">
						</div>
					</div>
					<div class="col">
						<div class="ele-icon-box">
							<input type="text" name="brno3" id="p_brno3" maxlength="5" placeholder="00000">
						</div>
					</div>
					<div class="col flex-b115px">
						<button type="button" id="p_btnBizSearch" class="btn-black bs-l">															
							<i class="icon-search"></i> 검색															
						</button>
					</div>
				</div>
			</div>
			<div class="bottom-lable">
				<div class="row">
					<div class="col"><p class="fs-10px text-red">
						<i class="icon-exclamation-circle"></i>
						지사사업자번호로 조회 불가
					</p></div>
					
				</div>
			</div>
		</div>
	</div>
	<div class="search-list-box">
		<p class="tit">검색결과</p>
		<div id="p_bizResult" class="list"></div>
	</div>				
	
</form>

<%-- ############################# 내용 (종료) ############################# --%>

