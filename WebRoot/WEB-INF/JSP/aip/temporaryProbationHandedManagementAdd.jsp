<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>暂予监外执行移交管理</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body onload="init('${menuid}');">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<input name="tempid" id="tempid" class="mini-hidden" value="${tempid}"/>
			<input name="docid" id="docid" class="mini-hidden" value="${docid}"/>
			<input name="crimid" id="crimid" class="mini-hidden" value="${crimid}"/>
			<input name="operator" id="operator" class="mini-hidden" value=""/>
			<a class="mini-button"  iconCls="icon-save" plain="true" style="display:none;" id="11281" onclick="savedata();">存盘</a>	
			<a class="mini-button"  style="display:none;" id="12756" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
	    	<a class="mini-button"  iconCls="icon-close" plain="true"  onclick="close();">关闭</a>
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
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
        var form = new mini.Form("form1");
        var datagrid = mini.get("datagrid1");
	       function close() {
	          var url="temporaryProbationHandedManagement.action?1=1";
	          self.location.href=url;  
	       }
      //保存或更新
        function savedata() {
       		var content = saveFile();
       		var tempid = mini.get("tempid").getValue();
       		var docid = mini.get("docid").getValue();
       		var crimid = mini.get("crimid").getValue();
       		var url="saveTemporaryProbationHandedManagement.action?1=1";
       		var temp;
        	if(docid){
        	temp = "edit";
        	}else{
        	temp="new";
        	}
	            $.ajax({
	                url:encodeURI(encodeURI(url)),
	                data: {content:content,tempid:tempid,crimid:crimid,docid:docid,temp:temp},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功!");
	                	saveNext();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
            
        }
		//标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            mini.get("operator").setValue(data.action); 
        }

        
        //获取表单节点和其对应的节点属性值
		function getNoteMap() {
			var aipObj=document.getElementById("HWPostil1");
			var NoteInfo;
			var data = new Array();
		while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				var temp = NoteInfo.split(".")[1];
				var value = aipObj.GetValueEx(NoteInfo, 2, "", 0, "");
				data.push(temp+'^^'+value);
			}
			return data;
		}
		
		function saveNext(){
     	  var id = mini.get("id").getValue();
     	  var menuid = mini.get("menuid").getValue();
     	  var tempid = mini.get("tempid").getValue();
          var index = id.indexOf(",");
          if(index>0){
        	  id = id.substring(index+1,id.length);
              var url = "temporaryProbationHandedManagementAdd.action?1=1&id="+id+"&menuid="+menuid;
              self.location.href=url;
          }else{
         	  close();
          }
     }
    </script>
</body>
</html>
