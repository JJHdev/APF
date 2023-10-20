<%--
*******************************************************************************
***    명칭: listRoleUser.jsp
***    설명: 역할별사용자관리 관리 화면
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2021.09.06    LSH        First Coding.
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
						<div class="formGroup">
							<div class="inputWrap">
								<p>역할</p>
			                    <select id="srchRoleId" name="srchRoleId" style="width:200px"></select>
							</div>
							<div class="inputWrap">
								<p>시스템</p>
			                    <select id="srchSysCd" name="srchSysCd" style="width:200px"></select>
							</div>
						</div>
		            </form>
				</div>
			</div>
		</div>
		
		<div class="app-space40"></div>
		
		<div class="menu-list box">
			<div class="menuTable curr">
				<div class="subTit type2">
					<h4>권한 적용 사용자 목록</h4>
				</div>
				<div style="height: 800px">
					<table id="roleGrid" class="easyui-datagrid"></table>
				</div>
			</div>
			
			<div class="moveMenu">
				<a href="#void" id="btnAppend" class="left"></a>
				<a href="#void" id="btnRemove" class="right"></a>
			</div>
			
			<div class="menuTable add">
				<div class="subTit type2">
					<h4>추가 대상 사용자 목록</h4>
				</div>
				<div style="height: 800px">
					<table id="userGrid" class="easyui-datagrid"></table>
				</div>
			</div>
		</div>


<%-- ############################# 내용 (종료) ############################# --%>
