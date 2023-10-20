<%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
**  @(#)popup.jsp
**  @author     : ntarget
**  @version    : 1.0
**  @since      : 2023.03.16 error controller 적용
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --%>
<!DOCTYPE html>
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
		
<c:if test="${ exceptionMessage == null }">
	<div class="app-error-usr">
		<div class="error-title">
			<h2>${errorTitle}</h2>
		</div>
		
		<div class="error-wrap">
			<p class="exception_message">
				${errorMessage}
			</p>
		</div>
	</div>
</c:if>

<c:if test="${ exceptionMessage != null }">
	<div class="app-error-usr">
		<div class="error-title">
			<h2>안내 메시지</h2>
		</div>
		
		<div class="error-wrap">
			<p class="exception_message">
				<span class="exception_detail_message">${exceptionMessage}</span>
			</p>
		</div>
	</div>
</c:if>
	
<div>
	<a href="javascript:window.close();" class="blue" title="닫기">닫기</a>
</div>
