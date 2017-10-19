<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String realPath1 = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
	String formcontent = (String) request.getAttribute("formcontent");
%>
	<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
	<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
		var aipObj = document.all.HWPostil1;
		aipObj.ShowToolBar = 1;
		document.getElementById("HWPostil1").JSEnv=1;
		HWPostil1_modelEdit();
	</script>
	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyDocOpened()>
	</SCRIPT>
	<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyLineAction(lPage,lStartPos,lEndPos)">
	// 控件"HWPostil1" 的 NotifyLineAction
		NotifyLineAction(lPage,lStartPos,lEndPos);
	</script>
	<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
	<script src="<%=path%>/scripts/form/loadaip.js"></script>
	
