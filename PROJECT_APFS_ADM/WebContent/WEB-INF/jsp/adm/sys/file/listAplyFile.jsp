<%--
*******************************************************************************
***    명칭: listAplyFile.jsp
***    설명: 신청파일 관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2021.10.02    김주호        First Coding.
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

		<div class="boxWrap type1">
			<div class="boxInner">
				<div class="boxTit type1">
					<h3>검색조건</h3>
				</div>
				<div class="searchForm formLayout">
		            <form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
		            	<div id="appStepBox">
							<div class="formGroup">
	                            <div class="inputWrap">
	                                <p>업무구분</p>
	                                <select id="srchDtySeCd" name="srchDtySeCd" style="width:170px;"></select>
	                            </div>
	                        </div>
							<div class="formGroup">
	                            <div class="inputWrap">
	                                <p>서류분류</p>
	                                <select id="srchUpPapeCd" name="srchUpPapeCd"  style="width:190px;"></select>
	                                <select id="srchPapeCd"   name="srchPapeCd"    style="width:400px;"></select>
	                            </div>
	                        </div>
						</div>
                        <div class="formGroup" id="appRcptTermBox"></div><%-- 접수일자 --%>
                        <div class="formGroup">
                            <div class="inputWrap">
                                <p>접수번호</p>
                                <input type="text" id="srchDcmtNo" name="srchDcmtNo"  style="width:170px;"/>
                            </div>
                        </div>
		            </form>
				</div>
			</div>
			<div class="btnWrap type1">
                <app:button id="btnSearch" title="검색" cls="app-m3 blue" />
                <app:button id="btnReset"  title="초기화" cls="app-m3" />
			</div>
		</div>

		<div class="app-space40"></div>

		<div class="listWrap box">
			<!-- 테이블 -->
			<div class="tableGroup">
				<div class="subTit type2">
					<h4 class="app-left app-pt20">신청파일목록</h4>
					<div class="btnDiv">
						<app:button id="btnDownload" title="다운로드" cls="btn"/>
					</div>
				</div>
				<table id="appGrid" class="easyui-datagrid" style="height: 500px"></table>
			</div>
		</div>

<%-- ############################# 내용 (종료) ############################# --%>
