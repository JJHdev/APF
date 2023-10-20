<%--
*******************************************************************************
***    명칭: modalSurveyView.jsp
***    설명: 설문관리 - 상세 화면
***
*** -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.07.13     KYW        First Coding.
*******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld"%>
<%@ taglib prefix="f" uri="/WEB-INF/tld/f.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%-- ############################ 내용 (시작) ############################ --%>
<input type="hidden" id="srvyNo" value='<c:out value="${model.srvyNo }" />' />  <!-- 설문번호 -->
<div class="contentBox w-100" style="overflow:auto;">
	<div class="box mb-24px">
		<div class="box mb-24px">
			<div class="box-in-box py-16px">
				<div class="infobox ">
					<div class="row justify-content-start">
						<div class="col">
							<p class="txt1">
								<i class="icon-users"></i>
								<span class="lh-34px">설문대상</span>
								<em>
									<c:out value="${empty model.srvyTrgtNm ? '전체' : model.srvyTrgtNm }"/>
								</em>
							</p>
						</div>
						<div class="col">
							<p class="txt1">
								<i class="icon-user-alt"></i>
								<span class="lh-34px">작성자</span>
								<em>
									<c:choose>
									    <c:when test="${not empty model.mdfrNm}">
									        <c:out value="${model.mdfrNm}" />
									    </c:when>
									    <c:otherwise>
									        <c:out value="${model.rgtrNm}" />
									    </c:otherwise>
									</c:choose>
								</em>
							</p>
						</div>
						<div class="col">
							<p class="txt1">
								<i class="icon-calendar"></i>
								<span class="lh-34px">조사기간</span>
								<em><c:out value="${model.srvyBgngYmd}" /> ~ <c:out value="${model.srvyEndYmd}" /></em>
							</p>
						</div>
						<div class="col">
							<p class="txt1">
								<i class="icon-user-edit"></i>
								<span class="lh-34px">문항 수</span>
								<em><c:out value="${model.qtNum}" /></em>
							</p>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<c:forEach items="${listQstnn }" var="qstnn" varStatus="status">
			<div class="box mb-24px">
				<div class="box-in-box shadow-box py-8px">
					<div class="input-grid-2">
						<div class="row mb-0">
							<div class="col py-8px">
								<label for="" class="bs-m btn-primary fs-18px px-11px">Q.<c:out value="${qstnn.qitemMarkNo }" /></label>
								<span class="fs-16px fw-600 ml-8px"><c:out value="${qstnn.qitemCn }" /></span>
							</div>
						</div>
						<div class="row content-none mb-0">
							<c:forEach items="${listQitem }" var="qitem" varStatus="iStatus">
								<c:choose>
									<c:when test="${qstnn.qitemNo == qitem.qitemNo && qstnn.qitemType eq 'QT1'}">
										<div class="col flex-grow-0 py-14px white-space-nowrap d-flex align-items-center">
											<input type="radio" name="" id="" disabled>
											<label for="" class="fs-15px ms-2 d-flex align-items-center">
												<c:choose>
													<c:when test="${empty qitem.artclCn}">
														기타
													</c:when>
													<c:otherwise>
														<c:out value="${qitem.artclCn }" />
													</c:otherwise>
												</c:choose>
											</label>	
										</div>
									</c:when>
									<c:when test="${qstnn.qitemNo == qitem.qitemNo && qstnn.qitemType eq 'QT2'}">
										<div class="col flex-grow-0 py-14px white-space-nowrap d-flex align-items-center">
											<input type="checkbox" name="" id="" disabled>
											<label for="" class="fs-15px ms-2">
												<c:choose>
													<c:when test="${empty qitem.artclCn}">
														기타
													</c:when>
													<c:otherwise>
														<c:out value="${qitem.artclCn }" />
													</c:otherwise>
												</c:choose>
											</label>	
										</div>
									</c:when>
									<c:when test="${qstnn.qitemNo == qitem.qitemNo && qstnn.qitemType eq 'QT3'}">
										<div class="col-12 py-8px">
											<div class="form-area-box1">
													<div class="ele-icon-box">
														<textarea cols="30" rows="10" style="height: 103px;resize:none;" placeholder="서술형을 입력해주세요." readonly></textarea>
													</div>										
											</div>
										</div>
									</c:when>
								</c:choose>
							</c:forEach>
							
						</div>
					</div>
				</div>
	
			</div>
		</c:forEach>
		
	</div>
	<div class="modal-footer mt-3">
		<div class="bottom-btn-box">
			<c:if test="${!model.srvyRspn }">
				<div class="row">
					<div class="col">
						<a href="#" class="btn-combi-3 bs-m " onclick="P_MODAL.doRemove();">
							<i class="icon-trash"></i>
							삭제
						</a>
					</div>
					
						<div class="col">
							<a href="#" class="btn-primary bs-m" onclick="P_MODAL.doOpenUpdate();">
								<i class="icon-edit"></i>
								수정
							</a>
						</div>
				</div>
			</c:if>
		</div>
	</div> 
</div>


<!-- CENTER CONTENT END << ---------->
<%-- ############################ 내용 (종료) ############################ --%>

