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
<div class="w-100">
	<div class="box">
		<!-- 컨텐츠시작 -->
		<div class="eletabletop1" data-v-2eb60c26="">
			<div class="row">
				<div class="col">
					<p class="txt2">
						<i class="icon-note"></i>
						<c:if test="${ exceptionMessage == null }">
							<c:out value="${errorTitle}"/>
						</c:if>
						<c:if test="${ exceptionMessage != null }">
							안내 메시지
						</c:if>
					</p>
				</div>
			</div>
		</div>
		<div class="box-in-box input-grid-1 pb-16px">
			<div class="row">
				<div class="col-12">
					<%-- 오류 메시지 START ================================================== --%>
						<c:if test="${ exceptionMessage == null }">
							<p class="exception_message" style="min-height:100px">
								${errorMessage}
							</p>
						</c:if>
						<c:if test="${ exceptionMessage != null }">
							<p class="exception_message" style="min-height:100px">
								<span class="exception_detail_message">${exceptionMessage}</span>
							</p>
						</c:if>
					<%-- 오류 메시지 END ==================================================== --%>
				</div>
			</div>
		</div>
	</div>
</div>
