<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String realPath1 = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
	String formcontent = (String) (request.getAttribute("formcontent") == null?"":request.getAttribute("formcontent"));
	String formDatajson = (String)(request.getAttribute("formDatajson") == null?"":request.getAttribute("formDatajson"));
	String selectDatajson = (String)(request.getAttribute("selectDatajson") == null?"":request.getAttribute("selectDatajson"));
	String ismultipage = (String)request.getAttribute("ismultipage") == null?"1":(String)request.getAttribute("ismultipage");
	String pagenum = (String)request.getAttribute("pagenum") == null?"":(String)request.getAttribute("pagenum");
	//System.out.println(this.getClass()+":formDatajson:\n"+formDatajson);
%>
	<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
	<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
		var aipObj = document.all.HWPostil1;
		document.getElementById("HWPostil1").JSEnv=1;
		HWPostil1_modelReady('<%=formcontent%>','<%=ismultipage%>','<%=pagenum%>');
		HWPostil1_display('<%=formDatajson%>','<%=selectDatajson %>');
		isEditAttribute();
// 		该函数必须加在isEditAttribute后面
		openAip("${flowdefid}");
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
	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyChangeValue(nodeName,nodeValue)>
		//表单节点的值发生变化时触发事件，nodeName：包括页码的节点名称
		HWPostil1_NotifyChangeValue(nodeName,nodeValue);
	</SCRIPT>
	<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
	<script src="<%=path%>/scripts/form/loadaip.js"></script>
	<!--浏览区原因导致控件灰色不能点击时，注释下面的打开上面试下-->
	<!--<jsp:include page="/WEB-INF/JSP/form/control_seal.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/JSP/form/control_window.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/JSP/form/control_approve.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/JSP/form/control_other.jsp"></jsp:include>
	-->
	
	<script src="<%=path%>/scripts/form/control/control_seal.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/control/control_window.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/control/control_approve.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/control/control_other.js" type="text/javascript"></script>
