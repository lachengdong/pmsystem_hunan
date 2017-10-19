<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户事件列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
	<!-- 隐藏四字段，已经存在的不写 创建人和更新人 更新时间 -->     
    <div>
    <input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
            <table style="width:100%;">
                <tr>
                	<!--<td align="left">
                		<a class="mini-button"  onclick="dele()" >批量置为已读</a>
                	</td>
                    --><td align="right">
                        <input id="state" class="mini-combobox" valueField="id" textField="text" emptyText="状态过滤"
                            showNullItem="true" data="state"  style="width:150px;" onvaluechanged="onstatuschanged"
                           />
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div class="mini-fit">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
         url="getAllEvent.action?1=1" idField="" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
		        showLoading="true">
        <div property="columns">
            <div type="indexcolumn" width="10" headerAlign="center"  align="center" allowSort="true">序号</div> 
            <div type="checkcolumn" width="10"></div>
            <div field="noticeid" width="0"></div>
            <div field="opid" width="0"></div>
            <div field="content" width="200" headerAlign="center"  align="center"  allowSort="true">提醒内容</div> 
            <div field="messagetype" width="20" headerAlign="center"  align="center"  allowSort="true" renderer="type">提醒类型</div>
            <div field="state" width="20" headerAlign="center"  align="center"  allowSort="true" renderer="zhuangtai">状态</div>  
            <div header="操作" field="cbiname"  width="20"  headerAlign="center" align="center"  renderer="oncaozuo"></div>
        </div>
    </div>
  </div>

    <script type="text/javascript">
    	var state = [{id:0, text:'未读'},{id:1, text:'已读'}];
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
		
        function oncaozuo(e) {
        	var str=e.record;
            var s = ' <a class="Edit_Button" href="javascript:setstate()" >置为已读</a>';
            if(str.state=='1'){
            	s=' <a  >置为已读</a>';
            }
            return s;
        }
        function zhuangtai(e){
        	var s=e.record;
        	if(s.state=='1'){s='已读'}
        	if(s.state=='0'){s='未读'}
        	 return s;
        }
        function type(e){
        	var s=e.record;
        	if(s.messagetype=='1'){s='授权'}
        	if(s.messagetype=='2'){s='日程'}
        	if(s.messagetype=='3'){s='通知'}
        	return s;
        }
        function dele(){
	   			var rows = grid.getSelecteds();
       	 		if (rows.length > 0) {
           		 	if (confirm("确定删除选中记录？")) {
                	var ids = [];
                	for (var i = 0, l = rows.length; i < l; i++) {
                   	 	var r = rows[i];
                   	 	if(r.opid!='sysauto'){
                    	ids.push(r.noticeid);
                    	}else{alert("选项包含的公共消息未删除！");}
               		 }
	                var id = ids.join(',');
		                $.ajax({
		                    url: "delectIdList.action?1=1",
		                    type: "post",
		                    data: {noticeid:id},
	                    success: function (text) {
                        grid.reload();
	                    },
	                    error: function () {
	                    }
	                });
	            }
	        } else {
	        	 alert("请选中一条记录");
	        }
        }
        function setstate() {
	        var row = grid.getSelected();
	        if(row){
	        	if(confirm("确认置为已读？")){
					 $.ajax({
		                url: "setEvent.action?1=1",
		                data: {	
		                		noticeid : row.noticeid,
		                		messagetype : row.messagetype,  
		                		state:1      
		                	},
		                type: "post",
		                success: function (text) {
		                	grid.reload();
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                	alert("操作失败!");
		                }
		            });  
	            }    
            }  	
        }
        function oneventchanged(){
       		var eventtypeid2 = mini.get("eventtypeid").getValue();
			grid.load({eventtypeid : eventtypeid2});
       	}
       	function onstatuschanged(){
       		var state = mini.get("state").getValue();
			grid.load({state : state});
       	}
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
   </script>
</body>
</html>