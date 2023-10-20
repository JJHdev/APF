<%--
*******************************************************************************
***    명칭: openRelief.jsp
***    설명: 관리자 - 구제급여 - 신청 (대상 선택)
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2021.10.18    LSH        First Coding.
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

<style>
.formLayout {
    width:60%;
}
.relief-select .formLayout {
    margin:25px auto 0;
    display:block;
    width:700px;
    max-width:100%;
    padding:30px 60px;
    font-weight:500;
    color:#000;
    border-radius:50px;
    text-align:left;
}
</style>

        <div style="height:80px;"></div>
        <div class="relief-select">
            <p>
                소중한 개인정보를 보호하고 계정도용으로 인한 피해를 예방하기 위해<br>
                <span>3개월 주기로 비밀번호 변경</span>을 안내 드리고 있습니다.
            </p>
            <form id="registForm" name="registForm" method="post" onsubmit="return false;">
            <div class="formLayout">
                <div class="boxInner type2 box">
                    <div class="formGroup">
                        <p class="must">새 비밀번호&nbsp;</p>
                        <div class="inputWrap">
                            <input id="pswd" name="pswd"
                                type="password"
                                placeholder="9자리 이상 대문자,특수문자,숫자 포함"
                                maxlength="30"
                                style="width:300px"/>
                        </div>
                    </div>
                    <div class="formGroup">
                        <p class="must">새 비밀번호 확인&nbsp;</p>
                        <div class="inputWrap">
                            <input id="pswdCnfm" name="pswdCnfm"
                                type="password"
                                placeholder="비밀번호 확인"
                                maxlength="30"
                                style="width:300px"/>
                        </div>
                    </div>
                </div>
            </div>
            </form>
        </div>

        <div style="height:20px;"></div>

        <div class="btnWrap type1">
            <app:button id="btnSave"   title="변경"       cls="app-m3 blue" />
            <app:button id="btnNext"   title="다음에 변경" cls="app-m3" />
        </div>

        <div style="height:20px; text-align: center;">
            <span style="margin-top:10px;">※ 다음에 변경하기를 선택하신 경우 30일 동안 변경없이 사용 가능합니다.</span>
        </div>

<%-- ############################# 내용 (종료) ############################# --%>
