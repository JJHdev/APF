<%--
*******************************************************************************
***    명칭: listInfoLog.jsp
***    설명: 정보 접속 이력조회
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2022.03.18    ntarget        First Coding.
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
                        <div class="formGroup" id="appLogTermBox"></div>
                        <div class="formGroup">
                            <p>검색키워드</p>
                            <div class="inputWrap">
                                <input id="srchText" name="srchText" type="text" placeholder="조회자,정보주체의정보,IP 등을 입력하세요." style="width:500px"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="btnWrap type1">
                <app:button id="btnSearch" title="검색" cls="app-m3 blue" />
                <app:button id="btnReset"  title="리셋" cls="app-m3"  />
                <app:button id="btnExcel"  title="엑셀다운로드" cls="app-m3 blue" />
                <!--<app:button id="btnRemove" title="삭제" cls="app-m3 blue"  />-->
            </div>
        </div>

        <div class="app-space40"></div>
        <!-- 테이블 -->
        <div class="tableGroup">
            <table id="appGrid" class="easyui-datagrid" style="height:800px"></table>
        </div>

<%-- ############################# 내용 (종료) ############################# --%>
