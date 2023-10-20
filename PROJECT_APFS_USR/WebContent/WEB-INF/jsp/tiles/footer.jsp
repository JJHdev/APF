<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<c:set var="screen" value="sub" />
<c:if test="${gsScreen != null}">
	<c:set var="screen" value="${gsScreen}" />
</c:if>

<div class="footer <c:out value="${screen}"/>">
	<div class="row custum-row-1">
		<div class="col-12 col-md">
			<img src="<c:url value='/images/app/footer-logo-${screen}.svg'/>" alt="ASSIST" class="footer-img">
		</div>
		<div class="col-12 col-md">
			<div class="row custum-row-2">
				<div class="col-12 col-xl">
					<p class="txt1">
						<i class="icon-map-pin"></i>
						<spring:message code="title.com.footer.zip"/>
						<spring:message code="title.com.footer.addr"/>
					</p>
				</div>
				<div class="col-12 col-xl">
					<p class="txt1">
						<i class="icon-phone-office"></i>Help Desk : <spring:message code="title.com.footer.helpdesk"/>
					</p>
				</div>
				<div class="col-12 col-xl">
					<p class="txt1">
						<i class="icon-phone"></i>TEL : <spring:message code="title.com.footer.tel"/>
					</p>
				</div>
				<div class="col-12 col-xl">
					<p class="txt1">
						<i class="icon-phone-office"></i>FAX : <spring:message code="title.com.footer.fax"/>
					</p>
				</div>
				<!-- 
				<div class="col-12 col-xl">
					<p class="txt1">
						<i class="icon-envelope"></i>Mail : <spring:message code="title.com.footer.mail"/>
					</p>
				</div>
				 -->
				<div class="col-12 col-xl-6 align-self-center">
					<p class="txt1">
						<spring:message code="title.com.footer.copyright"/>
					</p>
				</div>
				<div class="col-12 col-xl-6">
					<div class="row justify-content-end row" style="margin: 0 -4px;">
						<div class="col-12 col-md flex-b-md-214px px-4px">
							<div class="form-area-box ">											
								<div class="ele-icon-box">
									<div class="dropdown">
										<button class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
											패밀리 사이트
										</button>
										<ul class="dropdown-menu w-100">
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://blog.naver.com/apfs0519')"><img src="<c:url value='/images/app/family-naver.png'/>" class="app-family-logo" alt="naver"/>농식품모태펀드 블로그</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://www.youtube.com/c/농식품모태펀드apfs')"><img src="<c:url value='/images/app/family-youtube.png'/>" class="app-family-logo" alt="youtube"/>농식품모태펀드 유튜브</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://www.instagram.com/apfs0519/')"><img src="<c:url value='/images/app/family-instagram.png'/>" class="app-family-logo" alt="instagram"/>농식품모태펀드 인스타그램</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://blog.naver.com/agrocrowd_apfs')"><img src="<c:url value='/images/app/family-naver.png'/>" class="app-family-logo" alt="naver"/>농식품크라우드펀딩 블로그</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://www.youtube.com/channel/UC4t-LpTCdoiWFW5pJ0mRtrw')"><img src="<c:url value='/images/app/family-youtube.png'/>" class="app-family-logo" alt="youtube"/>농식품크라우드펀딩 유튜브</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://www.instagram.com/apfs_official/')"><img src="<c:url value='/images/app/family-instagram.png'/>" class="app-family-logo" alt="instagram"/>농식품크라우드펀딩 인스타그램</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('https://www.facebook.com/2022crowdfunding')"><img src="<c:url value='/images/app/family-facebook.png'/>" class="app-family-logo" alt="facebook"/>농식품크라우드펀딩 페이스북</a></li>
											<li><a class="dropdown-item justify-content-start" href="javascript:goNewUrl('http://www.agrocrowd.kr/')"><img src="<c:url value='/images/app/family-agro.png'/>" class="app-family-logo" alt="agro"/>농식품크라우드펀딩 투자전용관</a></li>
										</ul>
									</div>
								</div>
								
							</div>
						</div>
						<div class="col-12 col-md flex-b-md-214px px-4px">
							<div class="form-area-box ">											
								<div class="ele-icon-box">
									
									<div class="dropdown">
										<button class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
											유관기관
										</button>
										<ul class="dropdown-menu w-100">
											<li><a class="dropdown-item" href="javascript:goNewUrl('https://www.ipet.re.kr/index.asp')">농림식품기술기획평가원</a></li>
											<li><a class="dropdown-item" href="javascript:goNewUrl('https://www.koat.or.kr/main.do')">한국농업기술진흥원</a></li>
											<li><a class="dropdown-item" href="javascript:goNewUrl('https://www.foodpolis.kr/')">한국식품산업클러스터진흥원</a></li>
											<li><a class="dropdown-item" href="javascript:goNewUrl('https://www.mafra.go.kr/sites/home/index.do')">농림축산식품부</a></li>
											<li><a class="dropdown-item" href="javascript:goNewUrl('https://www.mof.go.kr/index.do')">해양수산부</a></li>
										</ul>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%-- 첨부파일 업로드 프레임 --%>
<div id="uploadFrame"></div>

<%-- 첨부파일 다운로드 프레임 --%>
<div id="downloadFrame"></div>
