<%--
*******************************************************************************
***    명칭: modalInvestCaseView.jsp
***    설명: 운영관리-공지사항 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    JH        First Coding.
***    1.1      2023.06.27    JH        작업 완료.
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

<div class="w-100">
	<div class="box-max-height">
		<form:form commandName="model" id="selectForm" name="selectForm" method="post" onsubmit="return false;">
			<form:hidden path="pstNo"         /><%-- 게시물번호 --%>
			<form:hidden path="bbsSeCd"       /><%-- 게시물구분 --%>
			<form:hidden path="mode"          /><%-- 처리모드 --%>
			<form:hidden path="pstLinkUrl"    /><%-- 동영상 주소 --%>
			<div class="box">
				<!-- 컨텐츠시작 -->
				<div class="eletabletop1" data-v-2eb60c26="">
					<div class="row">
						<div class="col">
							<p class="txt2">
								<i class="icon-note"></i>
								<c:out value="${model.pstTtl}"/>
							</p>
						</div>
					</div>
				</div>
				<div class="box-in-box input-grid-1 pb-16px">
					<div class="row">
						<div class="col-4">
							<div class="form-area-box-input">
								<label>작성자</label>
								<div class="ele-icon-box">
								    <div class="value-box">
		    					    	<c:choose>
										    <c:when test="${not empty model.mdfrNm}">
										        <c:out value="${model.mdfrNm}" />
										    </c:when>
										    <c:otherwise>
										        <c:out value="${model.rgtrNm}" />
										    </c:otherwise>
										</c:choose>
								    </div>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="form-area-box-input">
								<label>조회수</label>
								<div class="ele-icon-box">
								    <div class="value-box"><c:out value="${model.inqCnt}" /></div>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="form-area-box-input">
								<label>등록일</label>
								<div class="ele-icon-box">
								    <div class="value-box"><c:out value="${model.regDate}" /></div>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="form-area-box-input">
								<label>동영상URL</label>
								<div class="ele-icon-box">
								    <c:out value="${model.pstLinkUrl}" />
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="form-area-box-input">
								<label>첨부파일</label>
								<div class="ele-icon-box">
									<%-- 첨부파일영역 --%>
									<div id="attachFile"></div>
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="form-area-box-input">
								<label>썸네일 이미지</label>
								<%-- 첨부파일영역 --%>
								<div class="ele-icon-box">
									<div id="thumAttachFile"></div>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="form-area-box-input">
								<label>내용</label>
								<div class="ele-icon-box h-auto">
									<div style="min-height:300px" class="ckEdit"> 
										<c:if test="${model.pstLinkUrl ne ''}">
											<div>
												<p style="display: block; margin: 2em 0; " id="bbsPstLinkUrl">
													<c:out value="${model.pstCn}" escapeXml="false" />
												</p>
											</div>
										</c:if>
									    <c:if test="${model.pstLinkUrl eq ''}">
								        	<c:out value="${model.pstCn}" escapeXml="false" />
									    </c:if>
								    </div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 컨텐츠종료 -->
			</div>
		</form:form>
	</div>
</div>
<div class="bottom-btn-box">
	<div class="row">
		<div class="col flex-grow-0"><a id="btnBefore" href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-arrow-left"></i> 이전</a></div>
		<div class="col flex-grow-0"><a id="btnNext"   href="javascript:void(0)" class="bs-l btn-combi-3"><i class="icon-arrow-right"></i> 다음</a></div>
		<div class="col flex-grow-1"></div>
		<div class="col flex-grow-0"><a id="btnRemove" href="javascript:void(0)" class="bs-l btn-black"><i class="icon-times"></i> 삭제하기</a></div>
		<div class="col flex-grow-0"><a id="btnModify" href="javascript:void(0)" class="bs-l btn-primary"><i class="icon-edit"></i> 수정하기</a></div>
	</div>
</div>
<%-- ############################ 내용 (종료) ############################ --%>

