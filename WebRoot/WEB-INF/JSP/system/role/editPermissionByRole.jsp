<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//
Object _roleid = request.getAttribute("roleid");
if(null != _roleid){
	
}
%>
<html>
  <head>
    <title>角色权限</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
    html,body
    {
        padding:0;
        margin:0;
        border:0;
        width:100%;
        height:100%;
        overflow:hidden;
    }
    </style>
  </head>
  <body>
  <!-- 隐藏四字段，已经存在的不写 创建人和更新人 更新时间 -->
    <input name="createmender" class="mini-hidden" />
    <input name="updatemender" class="mini-hidden" />
    <input id="updatetime" name="updatetime" class="mini-hidden" />
    <input name="createtime" class="mini-hidden" />
    <div id="div_one"  style="width:100%;height:100%;">
     <input id="id" class="mini-hidden"/>
      <div class="mini-toolbar" style="padding:0px;border:0;text-align:left" >
      		<table style="width:100%;">
      		<tr>
      			<td>
		           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
		           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          	<%--
		          	<input id="key" class="mini-textbox" emptyText="请输入关键字" style="width:120px;" onenter="onKeyEnter"/>
		          <a class="mini-button" style="width:60px;"  plain="true" onclick="search()">查询</a>
		           --%>
      			</td>
      		</tr>
          </table>
        </div>
    <div class="mini-fit">
        <ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
            showTreeIcon="true" textField="spdiscribe" idField="spid" parentField="spparent" resultAsTree="false"
            showCheckBox="true" expandOnLoad="false" allowSelect="false"  showFolderCheckBox="true"
            checkRecursive="false" autoCheckParent="true"
            >
        </ul>
    </div>
    </div>
<script type="text/javascript">
    mini.parse();
    var tree = mini.get("tree1");
    function GetCheckedNodes() {
        var nodes = tree.getCheckedNodes();
        return nodes;
    };
    function GetData() {
        var nodes = tree.getCheckedNodes();
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            var node = nodes[i];
            if(i == nodes.length-1){
            	ids = ids + node.spid;
            }else{
            	ids = ids + node.spid + ",";
            }
        }
        return ids;
    };
       function SetData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);
                mini.get("id").value=data.id;
                var url = "ajax/getPermissionTreeByRole.json?1=1&qroleids="+data.id;
                tree.setUrl(url);
                //tree.load();
            }else{
            	 document.getElementById("operatetype").value="new";//走新增的action
            }
        };
    function search() {
         var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase();
                tree.filter(function (node) {
                    var text = node.spdiscribe ? node.spdiscribe.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
    };
    function onKeyEnter(e) {
        search();
    };
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    };
    function onOk() {
    	var srid = mini.get("id").value; //获取选中的角色id
        //var values = tree.getValue (false);//选中的权限id拼接的字符串  1,2,3 ...
        var values = GetData();
       	 $.ajax({
                url: "ajax/savePermissionForRole.json?1=1",
                data: {srid:srid, spids:values },
                type: "post",
                success: function (text) {
                	//alert("success");
                  	  CloseWindow("ok");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
    };
    function onCancel() {
        CloseWindow("cancel");
    };
</script>
  </body>
</html>