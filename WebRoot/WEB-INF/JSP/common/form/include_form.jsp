<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String realPath1 = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
	String formcontent = (String) (request.getAttribute("formcontent") == null?"":request.getAttribute("formcontent"));
	String formDatajson = (String)(request.getAttribute("formDatajson") == null?"":request.getAttribute("formDatajson"));
	String selectDatajson = (String)(request.getAttribute("selectDatajson") == null?"":request.getAttribute("selectDatajson"));
	String ismultipage = (String)request.getAttribute("ismultipage") == null?"1":(String)request.getAttribute("ismultipage");
	System.out.println(this.getClass()+":formDatajson:\n"+formDatajson);
%>
	<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
	<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
		var aipObj = document.all.HWPostil1;
		document.getElementById("HWPostil1").JSEnv=1;
		HWPostil1_modelReady('<%=formcontent%>','<%=ismultipage%>');
		HWPostil1_display('<%=formDatajson%>','<%=selectDatajson %>');
		isEditAttribute();
	</script>
	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyDocOpened()>
	</SCRIPT>
	<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyLineAction(lPage,lStartPos,lEndPos)">
	// 控件"HWPostil1" 的 NotifyLineAction
		NotifyLineAction(lPage,lStartPos,lEndPos);
	</script>
	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=JSNotifyBeforeAction(lActionType,lType,strName,strValue)>
		HWPostil1_JSNotifyBeforeAction(lActionType,lType,strName,strValue);
	</SCRIPT>
	<jsp:include page="/WEB-INF/JSP/common/form/includjs.jsp"></jsp:include>
	<script src="<%=path%>/scripts/form/loadaip.js"></script>
	
