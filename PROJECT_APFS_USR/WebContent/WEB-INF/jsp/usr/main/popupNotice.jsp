<%--
*******************************************************************************
***    명칭: popupNotice.jsp
***    설명: 메인화면 공지사항 팝업
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0        2023.07.18     KYW        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%-- ############################ 내용 (시작) ############################ --%>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/popupNotice.css'/>" /> 

<c:forEach var="lstPopup" items="${lstPopup}" varStatus="status"> 
	<div id="popup_box_${lstPopup.pstNo}" class="divpopLayer" 
			data-pst-no="${lstPopup.pstNo}" 
			data-pst-sj="${lstPopup.pstTtl}"
			data-popup-hg="${lstPopup.popupHg}"
			data-popup-ar="${lstPopup.popupAr}">
            
		<div class="window_topslice">
			<div class="window_button_holder">
				<a href="javascript:closePopup(${lstPopup.pstNo });" style="color: whitesmoke; font-weight: 500;">X</a>
			</div>
		</div>
        
    	<div class="window_content" id="cancel_${lstPopup.pstNo}">
			<div class="window_area">
				<h3>${lstPopup.pstTtl }</h3>
				<div class="discrip">${lstPopup.pstCn }</div>
			</div>
			<div class="closeToday">
				<form name="notice_form">
					<input id="ck_close_not_today_${lstPopup.pstNo }" type="checkbox" name="notToday" value="true">
					<label for="ck_close_not_today_${lstPopup.pstNo }"></label>
					오늘 하루 이 창을 열지 않음
					<a href="#" onclick="javascript:closePopup(${lstPopup.pstNo });">[닫기]</a>
				</form>
			</div>
		</div>
        
	</div>
</c:forEach>