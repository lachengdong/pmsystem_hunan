<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
		<title></title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
    	</style>
   
</head>
<body>
	<div id="datagrid" style="width:100%;height:100%;">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
               <td style="width:100%;">
                	<a class="mini-button" iconCls="icon-add" plain="true" onclick="newCert();">新增</a> 
                </td>
                <td style="white-space:nowrap;">
                
                	<input name="select_orgid" id="select_orgid" class="mini-combobox" style="width:120px;" valueField="ORGID" textField="NAME"  emptyText="请选择部门" 
                		onvaluechanged="onValueChanged" url="getDepartList_new.action?1=1" required="false" showNullItem="true" nullItemText="请选择部门"/>
                            
                    <input class="mini-textbox"  id="key"  name="key"  style="width:100px;"   emptyText="帐号/姓名"   onenter="onKeyEnter" />
                    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
                	
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
	    <div id="datagrid1" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="<%=path%>/getUserCertList.json?1=1" idField="" multiSelect="true" allowAlternating="true"
	    	sizeList="[10,20,50,100]" pageSize="50"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="20"></div> 
	        	<div type="indexcolumn" width="20" headerAlign="center" align="center" allowSort="true">序号</div>
	    		<div field="userid" headerAlign="center" align="center" allowSort="true" width="50">帐号</div>
	    		<div field="name" headerAlign="center" align="center" allowSort="true" width="60">姓名</div>
	    		<div field="explian" headerAlign="center" align="center" allowSort="true" width="100" >证书主题</div>
	    		<div field="certsn" headerAlign="center" align="center" allowSort="true" width="120">证书序列号</div>
	    		<div field="dn" headerAlign="center"  align="center" allowSort="true"width="180">证 书 DN</div>
	    		<div field="orgname" headerAlign="center"  align="center" allowSort="true" width="50px">用户所属部门</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="50px">操作</div>
	        </div>
	    </div>
    </div>
    
	</div>
    <script type="text/javascript"> 
    	mini.parse();
    	var grid = mini.get("datagrid1");
        grid.load();
        
        var path = '<%=path%>';
        
      	function onActionRenderer(e) {
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:editCert()" >修改</a>';
            s += ' <a class="Edit_Button" href="javascript:deleteCert()" >删除</a>';
            return s;
        }
      	
      	function newCert(){
      		var url = "manageCertPage.page?1=1";

      		var win = mini.open({
      			url: url,
      			width:600,
      			height:400,
      			showMaxButton: true,
      			allowResize: false,
      			title: "新增", 
      			onload: function (){
      				var iframe = this.getIFrameEl();
      				var data = { action: "new"};
      				iframe.contentWindow.SetData(data);
      			},
      			ondestroy: function (action){
      				grid.reload();
      			}
      	   });	
      	   //win.max();
      	}
      	
      	function editCert(){
      		var row = grid.getSelected();
      		var url = "manageCertPage.page?1=1";
			url += "&id="+row.id;
			//
      		var win = mini.open({
      			url: url,
      			width:600,
      			height:400,
      			showMaxButton: true,
      			allowResize: false,
      			title: "修改", 
      			onload: function (){
      				var iframe = this.getIFrameEl();
      				var data = { action: "edit"};
      				iframe.contentWindow.SetData(data);
      			},
      			ondestroy: function (action){
      				grid.reload();
      			}
      	   });	
      	}
      	
      	function deleteCert(){
      		var row = grid.getSelected();
      		var url = "deleteUserCert.json";
      		$.ajax({
      			url: url,
      			type: "post",
      			data: row,
      			async: false,
      			success: function (message) {
      				var info = message["info"];
    				alert(info);
    				grid.reload();
      			},
      			error: function (){
      				alert("操作失败!");
      			}
      		});
      	}
      	
        
        function search(){
	       	var data = {};
	       	var select_orgid = mini.get('select_orgid').getValue();
	       	if(select_orgid){
	       		data['select_orgid'] = select_orgid;
	       	}
	       	//
	       	var key = mini.get('key').getValue();
	       	if(key){
	       		data['key'] = key;
	       	}
	       	//
	        grid.load(data);
	    };
	    
    	//
    	function onKeyEnter(){
        	search();
        }
        //
    	function onValueChanged(){
        	search();
        }
	        
</script>
</body>
</html>  