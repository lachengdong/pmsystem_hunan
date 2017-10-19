<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>奖励审批表</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body>
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
			<input name="action" id="action" class="mini-hidden" value="${action}"/>
			<jsp:include page="/WEB-INF/JSP/form/formButtonBefore.jsp"></jsp:include>
			${topsearch}
			${topstr}
			<!--  
			<a class="mini-button" style="display:none;" id="600856" iconCls="icon-save" plain="true" onclick="doSubmitFlow('600856','save','other_khrewardsp','TKH_REWARDSPB');">存盘</a>
	    	<a class="mini-button" style="display:none;" id="600855" iconCls="icon-save" plain="true" onclick="doSubmitFlow('600855','save','other_khrewardsp','TKH_REWARDSPB');">存盘</a>
	    	<a class="mini-button" style="display:none;" id="600857" iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('600857','yes','other_khrewardsp','TKH_REWARDSPB');">提交</a>
	    	<a class="mini-button" style="display:none;" id="600887" iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('600857','yes','other_khrewardsp','TKH_REWARDSPB');">提交</a>
	    	
	    	<a class="mini-button" style="display:none;" id="600888" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('600888','yes','');">同意</a>
	    	<a class="mini-button" style="display:none;" id="600890" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('600890','no','');">拒绝</a>
	    	
	    	<a class="mini-button" style="display:none;" id="600891" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('600891','yes','');">同意</a>
	    	<a class="mini-button" style="display:none;" id="600892" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('600892','no','');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="600893" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('600893','back','');">退回</a>
	    	
	    	<a class="mini-button" style="display:none;" id="600894" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('600894','yes','');">同意</a>
	    	<a class="mini-button" style="display:none;" id="600895" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('600895','no','');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="600896" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('600896','back','');">退回</a>
	    	-->
	    	<a class="mini-button" id="" iconCls="icon-close" plain="true" onclick="back();">关闭</a>
	    	<jsp:include page="/WEB-INF/JSP/form/formButtonAfter.jsp"></jsp:include>
	    	
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
    <script type="text/javascript">
       mini.parse();
        function SetData(data) {
            data = mini.clone(data);
            mini.get("action").setValue(data.action); 
        }
         function saveNext(){
       		back();
        }
    </script>
</body>
</html>
