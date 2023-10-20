<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="app"    uri="/WEB-INF/tld/app.tld" %>
<%@ taglib prefix="f"      uri="/WEB-INF/tld/f.tld" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<%@ page import="common.user.UserInfo" %>
<%@ page import="common.util.UserUtils"%>
<%
// UserInfo 세션
UserInfo userInfo = UserUtils.getUserInfo(request);
%>
	<meta charset="UTF-8">
	<meta name="Generator"   content="">
	<meta name="Author"      content="">
	<meta name="Keywords"    content="">
	<meta name="Description" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">

<%--
##========================================================================================
## 공통 스타일시트, 자바스크립트
##========================================================================================
--%>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>" />
    <script>
    	<%-- 
    	서버의 CONTEXT PATH를 자바스크립트 전역변수로 설정 
    	(comm_utils.js 에서 사용한다.) 
    	--%>
    	var ROOT_PATH = '${pageContext.request.contextPath}'; 
    </script>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.4.1.js'/>"></script><!-- jQuery -->
	<script type="text/javascript" src="<c:url value='/design/js/commons.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-ui.js'/>"></script><!-- jQuery UI -->
    <script type="text/javascript" src="<c:url value='/jquery/bootstrap-5.2.3-dist/js/bootstrap.bundle.js'/>"></script><!-- bootstrap-5.2.3-dist -->
    <script type="text/javascript" src="<c:url value='/jquery/bootstrap-datepicker-1.9.0-dist/js/bootstrap-datepicker.js'/>"></script><!-- bootstrap-datepicker-1.9.0-dist -->
    <script type="text/javascript" src="<c:url value='/jquery/bootstrap-datepicker-1.9.0-dist/locales/bootstrap-datepicker.ko.min.js'/>"></script><!-- bootstrap-datepicker-1.9.0-dist -->
    <script type="text/javascript" src="<c:url value='/jquery/swiper-4.5.0/dist/js/swiper.js'/>"></script><!-- Swiper-4.5.0 -->
	
    <script type="text/javascript" src="<c:url value='/jquery/jquery.json-2.3.js'/>"></script>               <!-- jQuery FORM Plugin-->
    <script type="text/javascript" src="<c:url value='/jquery/jquery.form.min-4.2.2.js'/>"></script>               <!-- jQuery FORM Plugin-->
    <script type="text/javascript" src="<c:url value='/jquery/jquery.inputmask.bundle.js'/>"></script>             <!-- inputmask -->
    <script type="text/javascript" src="<c:url value='/jquery/jquery.serializeObject.js'/>"></script>              <!-- serializeObject -->
    <script type="text/javascript" src="<c:url value='/jquery/jquery.validate.js'/>"></script>                     <!-- jQuery Validation -->
    <script type="text/javascript" src="<c:url value='/jquery/jquery.number.js'/>"></script>                       <!-- jQuery Format Number -->

    <script type="text/javascript" src="<c:url value='/js/com/comm_message.js?version=${ver}'/>"></script>          <!-- Message Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_const.js?version=${ver}'/>"></script>            <!-- Constants Script -->
    
    <script type="text/javascript" src="<c:url value='/js/com/comm_utils.js?version=${ver}'/>"></script>            <!-- Utils Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_ajaxutils.js?version=${ver}'/>"></script>        <!-- Utils Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_formutils.js?version=${ver}'/>"></script>        <!-- Utils Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_formatutils.js?version=${ver}'/>"></script>      <!-- Utils Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_fileutils.js?version=${ver}'/>"></script>        <!-- Utils Script -->
        
    <script type="text/javascript" src="<c:url value='/js/com/comm_validate.js?version=${ver}'/>"></script>         <!-- Validate Function Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_popup.js?version=${ver}'/>"></script>            <!-- Popup Function Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_modal.js?version=${ver}'/>"></script>            <!-- Modal Function Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_element.js?version=${ver}'/>"></script>          <!-- Form Element Component Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_component.js?version=${ver}'/>"></script>        <!-- Business Component Script -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_layout.js?version=${ver}'/>"></script>           <!-- User Define Component Script -->

    <script type="text/javascript" src="<c:url value='/plugins/ckeditor/ckeditor.js?t=B37D54V'/>"></script>         <!-- CKEditor Plugin -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_ckeditor.js?version=${ver}'/>"></script>         <!-- CKEditor Function Script -->

    <script type="text/javascript" src="<c:url value='/plugins/easyui-1.9.15/jquery.easyui.min.js'/>"></script>      <!-- EasyUI Plugin -->
    <script type="text/javascript" src="<c:url value='/plugins/easyui-1.9.15/locale/easyui-lang-ko.js'/>"></script>  <!-- EasyUI Language Plugin -->
    <script type="text/javascript" src="<c:url value='/js/com/comm_easyui.js?version=${ver}'/>"></script>           <!-- EasyUI Function Script -->
	
    <script type="text/javascript" src="<c:url value='/plugins/pdfobject-2.2.5/pdfobject.js'/>"></script>           <!-- PDFObject Plugin -->
	<script type="text/javascript" src="<c:url value='/plugins/exceljs/exceljs.min.js'/>"></script>				<!-- ExcelObject Plugin -->
	<script type="text/javascript" src="<c:url value='/plugins/exceljs/FileSaver.min.js'/>"></script>			<!-- ExcelObject Plugin -->

    
    <%-- 업무용 스크립트 --%>

    <script type="text/javascript" src="<c:url value='/js/com/app_bbsgrid.js?version=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/com/app_bbsfile.js?version=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/com/app_entfile.js?version=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/com/app_bizfile.js?version=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/com/app_bizpapefile.js?version=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/com/app_papefile.js?version=${ver}'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/com/app_popup.js?version=${ver}'/>"></script>
    
    <script type="text/javascript" src="<c:url value='/js/com/app_bizutils.js?version=${ver}'/>"></script>
	
	
	
	<%-- 화면관련 상수 정의 --%>
	<c:set var="userInfo" value="<%=userInfo%>" />
	<script type="text/javascript">
	doScreen({
		ROOT_PATH: '<c:out value="${pageContext.request.contextPath}"/>',
		MENU_ID  : '<c:out value="${PAGEINFO.pageInfo.menuId       }"/>',
		MENU_NM  : '<c:out value="${PAGEINFO.pageInfo.titleNm      }"/>',
		PROG_ID  : '<c:out value="${PAGEINFO.pageInfo.progId       }"/>',
		PROG_NM  : '<c:out value="${PAGEINFO.pageInfo.progNm       }"/>',
		ROLE_ID  : '<c:out value="${userInfo.roleId                }"/>',
		USER_NO  : '<c:out value="${userInfo.userNo                }"/>',
		BZENT_NO : '<c:out value="${userInfo.bzentyNo              }"/>',
		LOGIN    :('<c:out value="${userInfo.login                 }"/>' === 'true'),
		ROLE     :{
			ADM  :('<c:out value="${userInfo.admin                 }"/>' === 'true'),
			USR  :('<c:out value="${userInfo.usr                   }"/>' === 'true'),
			BIZ  :('<c:out value="${userInfo.biz                   }"/>' === 'true'),
			INV  :('<c:out value="${userInfo.inv                   }"/>' === 'true'),
			ENT  :('<c:out value="${userInfo.ent                   }"/>' === 'true'),
			RIS  :('<c:out value="${userInfo.ris                   }"/>' === 'true'),
		},
		MENU_LIST: [
			{id:'<c:out value="${PAGEINFO.pageInfo.firstMenuId }"/>', name:'<c:out value="${PAGEINFO.pageInfo.firstMenuNm }"/>'},
			{id:'<c:out value="${PAGEINFO.pageInfo.secondMenuId}"/>', name:'<c:out value="${PAGEINFO.pageInfo.secondMenuNm}"/>'},
			{id:'<c:out value="${PAGEINFO.pageInfo.thirdMenuId }"/>', name:'<c:out value="${PAGEINFO.pageInfo.thirdMenuNm }"/>'}
		]
	});
	</script>
