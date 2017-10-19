<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户通知</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
   		.mini-panel-border{
   			border:0;
   		}
    </style> 
    <script type="text/javascript">
		var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var confirmM = "确认发送？";
     	var noCriminalsMessage = "没有要发送的用户！";
	</script>
</head>
<body>
	<input id="operator" name="operator" value="new" class="mini-hidden" />
	<input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
	<div class="mini-splitter" style="width:100%;height:100%;padding:0;" borderStyle="border:1px solid #99bce8;">
    	<div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
			<!--Tree-->
			<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
	            <span style="padding-left:5px;">用户：</span>
	            <input class="mini-textbox" vtype="maxLength:30;"  id="keyorg" style="width:80px;" onenter="onKeyEnter"/>
	            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="search()">查找</a>                  
	        </div>
	        <div  class="mini-fit" >
			<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
				url="getAllUserForNotice.action" showTreeIcon="true" onnodedblclick="onNodeDblClick" expandOnDblClick="false" 
			    resultAsTree="false" textField="name" idField="orgid" parentField="porgid" onbeforenodecheck="onBeforeNodeCheck"
			    contextMenu="#treeMenu" onnodeselect="onTreeNodeSelect">
			</ul>
			</div>
    	</div>
    	<form id="form1" method="post">
    	 <div showCollapseButton="false" style="overflow: auto;padding:0px;"  borderStyle="border:solid 1px #aaa;">
    	 <div region="north" height="80"  showSplit="false" showHeader="false" showCollapseButton="false">
    	 <br />
    	 <br />
    		<table>
    			<tr>
    				<td style="padding-left:37px;">接收人&nbsp;&nbsp;</td>
    				<td>
    					<input id="receiveUser" class="mini-textboxlist" name="receiveUser" textName="tblName" required="true" style="width:400px;"
        					valueField="userid" textField="username" />
    				</td>
    			</tr>
    			<tr>
    				<td><br/></td>
    				<td><br/></td>
    			</tr>
    			<tr>
    				<td style="padding-left:37px;">主&nbsp;&nbsp;题&nbsp;&nbsp;</td>
    				<td>
    					<input id="noticeTitle" name="noticeTitle" required="true" class="mini-textbox" vtype="maxLength:15;"  style="width:400px;"/></td>
    				</td>
    			</tr>    			
    			<tr>
    				<td style="padding-left:37px;">内&nbsp;&nbsp;容&nbsp;&nbsp;</td>
    				<td  >
    					<br />
    					<textarea id="noticeContent" name="noticeContent" required="true" class="mini-textarea" vtype="maxLength:140;" style="width:400px;height:170px;"></textarea>
    				</td>
    			</tr>
    			<tr>
    				<td style="padding-left:37px;"></td>
    				<td style="padding-left:317px;">
    					<a class="mini-button" iconCls="icon-ok" onclick="saveData()" style="width:60px">发送</a>
    				</td>
    			</tr>    			
    		</table>
    	 </div>
    	 </div>
    	 </form>
	</div>
    <script type="text/javascript">
        mini.parse();
		var grid2 = mini.get("grid2");
		var tree = mini.get("tree1");
		var form = new mini.Form("form1");

		function onTreeNodeSelect(e) {
		    var node = e.node;
		    if(!onBeforeNodeCheck(e)) {
            	var orgidvalues = "";
            	orgidvalues = getreceiveUserValue();
            	var orgids = "";
            	if(orgidvalues!="") {
            		orgids = orgidvalues + ",";
            	}
            	if(orgids.indexOf(node.orgid + ",")==-1) {
            		orgids = orgids + node.orgid + ",";
            	}

				var namevalues = "";
				namevalues = getreceiveUserName();
				var names = "";
				if(namevalues!="") {
					names = namevalues + ",";
				}
				if(names.indexOf(node.name + ",")==-1) {
					names = names + node.name + ",";
				}
    			
    			var t = mini.get("receiveUser");
    			names = names.substr(0,names.length-1);
    			orgids = orgids.substr(0,orgids.length-1);
            	t.setValue(orgids);
            	t.setText(names);
            }
		}
		
        function onBeforeNodeCheck(e) {
            var tree = e.sender;
            var node = e.node;
            return tree.hasChildren(node);
        }
            
        function getreceiveUserValue() {
            var t = mini.get("receiveUser");
            return t.getValue();
        }
        
        function getreceiveUserName() {
            var t = mini.get("receiveUser");
            return t.getText();
        }          	
        
        function saveData() {
        	var useridvalue = getreceiveUserValue();
        	var noticeValue = mini.get("noticeContent").getValue();
        	var noticeTitle = mini.get("noticeTitle").getValue();
        	form.validate();
        	if (form.isValid('all') == false) return;
            $.ajax({
                url: "sendMessage.action?1=1",
                data: { message : noticeValue, userids : useridvalue, noticeTitle : noticeTitle},
                type:"POST",
                cache: false,
                success: function (text) {
                	text = eval(text);
                	if(text!='error'){
                		alert("发送成功！");
	                	document.getElementById("noticeContent").value="";
	                	noticeTitle = document.getElementById("noticeTitle").value="";
	                	var t = mini.get("receiveUser");
            			t.setValue();
            			t.setText();
                	}else{
                		alert("发送失败！");
	                	CloseWindow("save");
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("发送失败！");
                    CloseWindow();
                }
            });        	
        }	
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("keyorg").getValue();
            var tree = mini.get("tree1");
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase(); 
                tree.filter(function (node) {
                    var text = node.name?node.name.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }   
    </script>
 </body>
</html>