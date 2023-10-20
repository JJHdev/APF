<%--
*******************************************************************************
***    명칭: modalData.jsp
***    설명: 정보서비스 - 자료실 글작성 팝업
***
***    -----------------------------    Modified Log   ---------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.06.09    J H        First Coding.
***    1.1      2023.06.28    J H        작업완료.
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

<form:form commandName="model" id="p_registForm" name="registForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden id="p_pstNo"   path="pstNo"   /><%-- 게시물번호 --%>
	<form:hidden id="p_bbsSeCd" path="bbsSeCd" /><%-- 게시물구분 --%>
	<form:hidden id="p_mode"    path="mode"    /><%-- 처리모드 --%>
	
	<div class="form-box-1 border-bottom-0 mb-16px pb-0">
		<div class="box">
			<div class="form-area-box">
				<label>제목<em></em></label>
				<div class="ele-icon-box">
					<form:input id="p_pstTtl" path="pstTtl" maxlength="100" placeholder="제목을 80자 이내로 입력해주세요"/>
				</div>
			</div>
		</div>
		<div class="box">
			<div class="row custum-row-1">
				<div class="col-3">
					<div id="appTopic" data-value="<c:out value="${model.pstClsfCd}"/>"></div>
				</div>
				<div class="col-3">
					<div id="appNoticeYn" data-value="<c:out value="${model.fixingYn}"/>"></div>
				</div>
				<div class="col-6">
					<div class="form-area-box">
						<label class="fw-400">공지글 게시기간</label>
						<div class="day">
							<div class="row">
								<div class="col">
									<div class="ele-icon-box">
										<i class="icon-edit"></i>
										<form:input id="p_fixingBgngYmd" path="fixingBgngYmd" maxlength="10" placeholder="날짜입력" cssClass="datepicker-input"/>
										<a href="#" class="icon-calendar"></a>
									</div>
								</div>
								<div class="col wave">
									<span>~</span>
								</div>
								<div class="col">
									<div class="ele-icon-box">
										<i class="icon-edit"></i>
										<form:input id="p_fixingEndYmd" path="fixingEndYmd" maxlength="10" placeholder="날짜입력" cssClass="datepicker-input"/>
										<a href="#" class="icon-calendar"></a>
									</div>
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>

		<div class="box">
			<div class="form-area-box">
				<label>내용<em>*</em></label>
				<div class="ele-icon-box">
					<form:textarea id="p_pstCn" path="pstCn" cssStyle="height: 174px;"/>
				</div>
			</div>
		</div>
		<%-- 첨부파일영역 --%>
		<div class="box" id="p_appBbsFile"></div>
	</div>
	
	<!-- 버튼영역 -->
	<div class="bottom-box-btn py-24px">
		<div class="row">
			<div class="col">
				<button id="p_btnSave" class="btn-primary w-100 bs-l"><i class="icon-edit"></i>작성완료</button>
			</div>
			<div class="col">
				<button id="p_btnCancel" class="btn-combi-3 w-100 bs-l"><i class="icon-times"></i>취소</button>
			</div>
		</div>
	</div>
</form:form>

<script>
$(function() {
    var dropdownId = "appTopic";
    var selectedValue = $('#p_pstClsfCd').val(); // hidden 필드 값을 가져옵니다.
    var $dropdown = $('#' + dropdownId);

    // a 태그의 dropdown-item 클래스를 반복해서 확인합니다.
    $dropdown.find('a.dropdown-item').each(function() {
        var $item = $(this);
        if ($item.attr('data-value') === selectedValue) {
            // 선택된 아이템에 "active" 클래스를 부여하고
            $item.addClass('active');
            // 드롭다운 버튼의 텍스트를 선택된 아이템의 텍스트로 설정합니다.
            $dropdown.find('.dropdown-toggle').html($item.text());
        } else {
            // 다른 아이템에서는 "active" 클래스를 제거합니다.
            $item.removeClass('active');
        }
    });
});
</script>

<%-- ############################# 내용 (종료) ############################# --%>

