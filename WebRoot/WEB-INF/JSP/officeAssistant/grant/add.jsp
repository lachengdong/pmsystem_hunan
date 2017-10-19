<%@ page language="java" import="java.util.*"	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sinog2c.model.officeAssistant.TbuserNotice"%>
<%!
// 转换为字符串
public String to_str(Object object){
	if(null == object){
		return "";
	} else {
		return object.toString();
	}
}
//
public String to_html(Object object){
	String s = to_str(object);
	s = s.replaceAll("&", "&amp;");
	s = s.replaceAll("<", "&lt;");
	s = s.replaceAll(">", "&gt;");
	s = s.replaceAll("\"", "&quot;");
	s = s.replaceAll(" ", "&nbsp;");
	s = s.replaceAll("\r\n", "\n");
	s = s.replaceAll("\r", "\n");
	s = s.replaceAll("\n", "<p>");
	return s;
}
//
public String to_textarea_html(Object object){
	String s = to_str(object);
	s = s.replaceAll("&", "&amp;");
	s = s.replaceAll("<", "&lt;");
	s = s.replaceAll(">", "&gt;");
	s = s.replaceAll("\r\n", "\n");
	s = s.replaceAll("\r", "\n");
	return s;
}
//
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
// 转换为日期
public String to_date_str(Object object){
	if(null == object){
		return "";
	} 
	if(object instanceof Date){
		return dateFormat.format(object);
	} else {
		return object.toString();
	}
}

// 构造一个新的对象
public TbuserNotice generateNewGrantNotice(){

	TbuserNotice notice = new TbuserNotice();
	Date startTime = new Date();
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(startTime);
	cal.add(Calendar.MONTH, 1); // 加一个月
	cal.add(Calendar.DATE, -1); // 减一天
	//
	Date endTime = cal.getTime();
	
	notice.setStarttime(startTime);
	notice.setEndtime(endTime);
	notice.setMessagetype(1);
	notice.setState((short)0);
	notice.setNoticeid(0);
	notice.setTitle("用户授权记录");
	notice.setContent("用户授权记录");
	return notice;
}

%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	Object noticeObj = request.getAttribute("notice");
	TbuserNotice notice = null;
	if(noticeObj instanceof TbuserNotice){
		notice = (TbuserNotice)noticeObj;
		//System.out.println("notice="+notice);
	} else {
		notice = generateNewGrantNotice();
		//System.out.println("notice=generateNewGrantNotice");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户授权</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
        <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
		<style type="text/css">
			html,body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
			}
		</style>
	</head>
	<body>
	    <input id="grantids" name="grantids" value="${grantids }" type="hidden"/><!-- 已经有的权限/获取已经赋值的权限集合 -->
	    <input id="action" name="action" value="${action }" type="hidden"/><!-- 操作状态 new/edit -->
	    <input id="noticeid" name="noticeid" value="${noticeid }" type="hidden"/><!-- 修改noticeid -->
	    
		<form id="form1" method="post">
	        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	        <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">   
		            		<a class="mini-button" onclick="onOk" plain="true" iconCls="icon-save" style="width:60px">存盘</a>       
		           			<a class="mini-button" onclick="onCancel"  plain="true" iconCls="icon-close" style="width:60px;">关闭</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>
	        </div>
			<div style="padding-left: 11px; padding-bottom: 5px;">
			<div style="width:50%;float:left;">
					<input class="mini-hidden" id="noticeid" name="noticeid" field="noticeid" 
						width="0px" value="<%=to_str(notice.getNoticeid()) %>"/>
					<div style="float:left;margin-top:5px;">开始时间：
						<input id="starttime" name="starttime"  property="editor" format="yyyy-MM-dd HH:mm" showTime="true" class="mini-datepicker" emptyText="开始时间" required="true"
							value="<%=to_date_str(notice.getStarttime()) %>" style="width:66%;*+.width:100%;" onvaluechanged="timeVerification('start');" allowInput="true"/>
					</div>
					<div style="float:left;margin-top:5px;">结束时间：
						<input id="endtime" name="endtime" property="editor" format="yyyy-MM-dd HH:mm" showTime="true" class="mini-datepicker" emptyText="结束时间" allowInput="true" 
							 value="<%=to_date_str(notice.getEndtime()) %>" style="width:66%;*+.width:100%;" onvaluechanged="timeVerification('end');" required="true"/>
					</div>
					<div style="float:left;margin-top:5px;">被授权人：
					<input name="username" id="username" class="mini-treeselect"  style="width:150px" 
						url="<%=path %>/grant/ajax/getOrgAndUserTree.json?1=1&type=${action}&noticeid=${noticeid}"
						multiSelect="false"  valueFromSelect="false"  popupWidth="250" 
				        textField="name" valueField="id" parentField="pid" pinyinField="id"
				        value="<%=to_str(notice.getUsername()) %>" 
				        onbeforenodeselect="beforenodeselect" allowInput="false"
				        showRadioButton="true" showFolderCheckBox="false"  expandOnLoad="0"
				        onvaluechanged="onUserChanged"
				    />
					</div>
					<div style="float:left;margin-top:5px;">授权状态：
						<input id="state" name="state" textField="text" valueField="id" class="mini-combobox" data="Comboboxdata" value="<%=notice.getState()%>" style="width:150px"/>
				    </div>
					<div style="float:left;margin-top:5px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
					<textarea id="remark" style="width:150px;"  class="mini-textarea"  rows="80"  name="remark"><%=to_textarea_html(notice.getRemark()) %></textarea></div>
			</div>
	 
			<div style="float:left;">
				所授权限：
				<div id="tree2" class="mini-tree"  style="width:250px;margin-left:30px;"
						 url="<%=path%>/grant/ajax/granttree.json?1=1&noticeid=<%=notice.getNoticeid() %>"
					 showTreeIcon="true"   textField="name" idField="resid" parentField="presid" resultAsTree="false" 
					 showCheckBox="true" checkRecursive="false"  showFolderCheckBox="true"  autoCheckParent="true" expandOnLoad="false"  allowSelect="false" enableHotTrack="false"  > </div>
			</div>
	
			</div>
		</form>
		<script type="text/javascript">
		var Comboboxdata = [{id:0, text:'生效'},{id:2, text:'废弃'}];
        mini.parse();
        var form = new mini.Form("form1");
        var tree = mini.get("tree2");
		var start = "${starttime}";
        //onUserChanged当选择被授权人的时候，把该被授权人已经被授过的权限显示出来
        function onUserChanged(){
        	//获取当前用户的 userid
            var user = mini.get("username");
            var username = user.getText();//用户 名称
            var userid = user.getValue();//账号
            //查询出所有的 已经被授的权限
            //包括已经有的权限，判断，如果该用户已经有的权限(或授权时间还未到是无法再次授权的)
            $.ajax({
                url:'checkXZUserBSGrant.action?1=1&userid='+userid,
                type:'post',
                success:function (text){
                    // 把字符串分隔为数组
                    var texts = text.replace(/"/g,'').split(",");
                    $("#grantids").val(texts);
                    //下面代码，动态选择已经有的权限tree.setValue('12103,802062');
                    //tree.setValue(texts);
                }
            });
        }
 		function SaveData() {
 			//表单验证 
            form.validate();
            if (form.isValid('all') == false) return;
            if (timeVerification() == false) return;

            var data = form.getData() || {};

            //被授权人必须保存的是 用户名称而不是账号
            var user = mini.get("username");
            data["name"] = user.getText();
            // 获取授权树
            var grantids = getGrantTreeIdData();//权限id
            var grantnames = getGrantTreeNameData();//权限名称
            if(!grantids){
            	alert("请选择授权权限!");
            	return;
            }
            //此处选择判断 ，如果之前已经授权过得权限，将不能再选择
            data["grantids"] = grantids;
            var grantidss = grantids.split(",");//选择的权限 (当前)
            var ygrantids = $("#grantids").val();//已经授过并且正在使用的权限
            var grantnames = grantnames.split(",");//权限名称
            var ygrantidss = ygrantids.split(",");

            var returnvalue = "";//不符合内容多余 ,所有的
            var j = 0;//外层循环 
            var m = 0;//内层循环
            var count = 0;//不符合个数 
            var countvalues = "";//不符合内容多余+(省略号)...
            for(j =0;j<grantidss.length;j++){
            	for(m =0;m<ygrantidss.length;m++){
                    if(grantidss[j] == ygrantidss[m]){
                    	returnvalue += '<'+grantnames[j]+'>,';
                    	//所授权限 如果改用已存在此权限个数大于10个，那么多余的加省略号(...)
                    	if(count < 10){
                    		countvalues += '<'+grantnames[j]+'>,';
                        }
                        count ++;
                    }
                }
            }
            if(returnvalue != ""){
                 alert("权限("+countvalues.substr(0,countvalues.length-1)+"...)已经授给该用户/该用户已有此权限!");
                 return;
            }

            var starttime= mini.get("starttime").getFormValue();
            var endtime= mini.get("endtime").getFormValue();
            data["starttime"] = starttime;
            data["endtime"] = endtime;
			
			var  url = "<%=path%>/grant/ajax/update.json?1=1";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
						CloseWindow("ok");
	               } else {
	               		alert(info);
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("保存失败");
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
        };
        function beforenodeselect(e) {
            //禁止选中父节点
            if(e.isLeaf == false){
            	e.cancel = true;
            }
            //禁止选中部门
            var node = e.node;
            if(!node["userid"]){
            	e.cancel = true;
            }
            window["console"] && console.dir(e);
        };

        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow){
            	return window.CloseOwnerWindow(action);
            } else {
            	window.close();
            }
        };
        function onOk(e) {
            SaveData();
        };
        function onCancel(e) {
            CloseWindow("cancel");
        };
        
        function getCheckedNodes() {
            var value = tree.getValue();
            alert(value);
        };
        
        function onButtonEdit(e) {
            var btnEdit = this;
            var url = "<%=path%>/grant/selectuser.page";
            mini.open({
                url: url,
                showMaxButton: false,
                title: "选择用户",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                        	document.getElementById("schemeid").value = data.resid;
                            btnEdit.setValue(data.resid);
                            btnEdit.setText(data.name);
                        }
                    }
                }
            });
        }; 
         //--------------------------------
         ////////////////////
        //标准方法接口定义
        //标准方法接口定义
       function SetData(data) {
       	   var starttime = "${starttime}";
       	   if(!starttime) starttime = new Date();
       	   mini.get("starttime").setValue(starttime);           
       }
        function getGrantTreeIdData() {
	        var nodes = tree.getCheckedNodes();
	        //
	        var residArray = [];
	        var ids = "";
	        for (var i = 0; i < nodes.length; i++) {
	            var node = nodes[i];
	            if(node && node.resid){
	            	residArray.push(node.resid);
	            }
	        }
	        return residArray.join(",");
    	};
    	function getGrantTreeNameData() {
	        var nodes = tree.getCheckedNodes();
	        //
	        var residArray = [];
	        var ids = "";
	        for (var i = 0; i < nodes.length; i++) {
	            var node = nodes[i];
	            if(node && node.resid){
	            	residArray.push(node.name);
	            }
	        }
	        return residArray.join(",");
    	};
    	
		// 请求AJAX,工具方法
		function requestAjax(url, data, successCallback, errotCallback){
			// 执行AJAX请求
			$.ajax({
			    url: url,
			    data: data,
		        type: "post",
			    success: function (message) {
			    	/*
			    	if(window["JSON"]){
			    		message = mini.decode(message) || {};
			    	} else { // IE6, IE7
	        	   		message = eval("("+ message + ")") || {};
			    	}
			    	*/
			    	message = mini.decode(message) || {};
			   		if(successCallback){
			    	   successCallback.call(window, message);
			   		}
	            	return false;
			    },
			    error: function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			       if(errotCallback){
			    	   errotCallback.apply(window, arguments);
			       } else {
			    	   alert("操作失败!");
			       };
			    }
			});
		};
    </script>
	</body>
</html>