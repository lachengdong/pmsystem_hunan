<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>专项奖扣分编辑页</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    .s{
    	width:100%;height:100%;_height:94.8%;
    }
    .mini-menu-border{
    	border:0;
    }
    </style>
</head>
<body>
    <input id="ruleid" name="ruleid" class="mini-hidden" />
    <input id="uplimit" name="uplimit" class="mini-hidden" />
    <input id="downlimit" name="downlimit" class="mini-hidden" />
    <div id="layout1" class="mini-layout" style="width:100%;height:100%;"> 
        <div title="奖惩条款" showSplitIcon="true"  expanded="false"  region="west" width="250px;" showCollapseButton="false" style="border-left:1px solid #90b5e3;">
			<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
	            <span style="padding-left:5px;">名称：</span>
	            <input class="mini-textbox" vtype="maxLength:30;" id="key" name="key" style="width:130px;" emptyText="搜索码/条款名称" onenter="onKeyEnter"/>
	            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="searchOrg()" >查找</a>                  
	        </div>
            <div class="mini-fit">
                <ul id="tree1" class="mini-tree" url="ajaxRewardPunishRules.action?1=1" style="width:100%;height:100%;"
                    showTreeIcon="true" textField="introduction" idField="ruleid" parentField="rulepid" resultAsTree="false" 
                    expandOnNodeClick="true" showArrow="true">        
                </ul>
            </div>
        </div>
        <div showCollapseButton="false">
            <ul id="menu2" class="mini-menubar" style="width:100%;">
		        <li iconCls="icon-remove" onclick="clear()">清空</li>
		        <li iconCls="icon-save" onclick="onOk()">确定</li>
		        <li iconCls="icon-close" onclick="onCancel()">关闭</li>
		        <li> <div id="schemeTypeId" style="display:inline;"></div></li>
            </ul>
            <div class="mini-fit">
                <div id="form1" class="mini-form" size="500">
                    <fieldset style="width:99%;height:98%;position:relative;padding:0px;border:0;">
                        <div style="padding:5px;">
                            <table style="width:100%;">
						        <tr>
									<td>理由：</td>
									<td>
										<input id="introduction" name="introduction" class="mini-textbox" required="true"/>
									</td>
									<td width="60px;">搜索码：</td>
									<td>
										<input id="searchcode" name="searchcode" class="mini-textbox" /> 
									</td>
						        </tr>
						        <tr>
						        	<td>类型：</td>
						        	<td>
						        		<input id="isaward" name="isaward" value="" class="mini-combobox" data="temparry" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." required="true"/>
						        	</td>
						        	<td>分值：</td>
						        	<td>
						        		<input id="recommendvalue" name="recommendvalue" class="mini-textbox" required="true"/> 
						        	</td>
						        </tr>
						        <tr>
						            <td width="65px;">条款内容：</td>
						            <td colspan="3"><input id="content" name="content" maxlength="6000" class="mini-textarea" style="width:415px;height:430px;"  required="true"/></td>
						        </tr>
                            </table>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var temparry = [{id:"0",text:"扣分"},{id:"1",text:"奖分"}];
        mini.parse();
	 	var layout = mini.get("layout1");
		var tree = mini.get("tree1");//方案树
		var form = mini.get("form1");
		$(document).ready(function(){
		  	layout.updateRegion("west", { expanded: true });
		});
        tree.on("nodeselect", function (e) {
        	var url="getRewardPunishRulesDetail.action?1=1&ruleid="+e.node.ruleid;
            $.ajax({
                 url: url,
                 type: "post",
                 success: function (text) {
                    var o =  mini.decode(text);
                    if(o != ""){
						mini.get("introduction").setValue(o.introduction);
						mini.get("searchcode").setValue(o.searchcode);
						mini.get("isaward").setValue(o.isaward);
						mini.get("recommendvalue").setValue(o.recommendvalue);
						mini.get("content").setValue(o.content);
						mini.get("ruleid").setValue(o.ruleid);
						mini.get("uplimit").setValue(o.uplimit);
						mini.get("downlimit").setValue(o.downlimit);   
                    }
                 }
             });  
        });
        
        function searchOrg() {
        	var key = mini.get("key").getValue();
			tree.load({ key: key });
        }
        function onKeyEnter(e) {
            searchOrg();
        }
        
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
            var checkform = new mini.Form("form1");
            checkform.validate();
            if (checkform.isValid() == false) return;
            
        	var row = new Array([6]);
        	row[0] = mini.get("ruleid").getValue();
        	row[1] = mini.get("introduction").getValue();
			row[2] = mini.get("isaward").getValue();
			row[3] = mini.get("recommendvalue").getValue();
			row[4] = mini.get("content").getValue();
        	if(row[0]=="") {
        		alert("请选择条款！");
        		return;
        	}
        	var uplimit = mini.get("uplimit").getValue();
        	var downlimit = mini.get("downlimit").getValue();
        	
			if((uplimit && uplimit<row[3]) || (downlimit && row[3]<downlimit)){
				alert("所选条款的分值应在"+downlimit+"-"+uplimit+"分之间！");
				return;
			}		
			window.returnValue = row;
        	CloseWindow("cancel");
        }
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
        function clear() {
			mini.get("introduction").setValue("");
			mini.get("searchcode").setValue("");
			mini.get("isaward").setValue("");
			mini.get("recommendvalue").setValue("");
			mini.get("content").setValue("");
			mini.get("ruleid").setValue("");    
			mini.get("uplimit").setValue("");
			mini.get("downlimit").setValue("");      
        }
    </script>
</body>
</html>