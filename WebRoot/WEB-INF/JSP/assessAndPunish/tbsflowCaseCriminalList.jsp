<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>暂缓解除</title>
		<style type="text/css">
	body {
		margin: 0;
		padding: 0;
		border: 0;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}
	</style>
	<script type="text/javascript">
	</script>
	</head>
	<body>
		 <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;" >
            <table style="width:100%">
               <tr>
                <td style="width:33%">
	      			<a class="mini-button" iconCls="icon-downgrade" plain="true" onclick="remove();">批量解除</a>
                </td>
                <td style="white-space:nowrap;" align="right">
                	<span><font size=2>暂缓时间：</font></span>
                	<input id="defertime" name="defertime"  allowInput="false" class="mini-datepicker" value="javascript:new Date()" dateFormat="yyyy-MM-dd" onenter="onKeyEnter"/>
                	<input name="key" id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号" style="width:130px;font-size: 12" onenter="onKeyEnter" />
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">快速查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick=""></a>
                </td>
               </tr>
		</table>
    </div>		
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false"
				style="width: 100%; height: 100%;" class="mini-datagrid"
				allowResize="false"
				url="tbsflowCaseCriminalList.action" idField=""
				multiSelect="true" sizeList="[10,20,50,100]"
				pageSize="20" >
				<div property="columns">
					<div type="checkcolumn" width="30"></div>
					<div field="anjianhao" width="50px" headerAlign="center"
						allowSort="true" align="center">
						案件号
					</div>
					<div field="anjianhaotype" width="30px" headerAlign="center"
						allowSort="true" align="center">
						案件类型
					</div>
					<div field="crimid" width="20px" headerAlign="center"
						allowSort="true" align="center">
						罪犯编号
					</div>
					<div field="crimname" width="20px" headerAlign="center"
						allowSort="true" align="center">
						罪犯姓名
					</div>
					<div field="defermonth" width="30px" headerAlign="center"
						allowSort="true" align="center" renderer="onDateRenderer">
						暂缓月个数
					</div>
					<div field="isdefer" width="30px" headerAlign="center"
						allowSort="true" align="center" renderer="onRecordReader">
						暂缓状态
					</div>
					<div name="action" width="20" headerAlign="center" align="center"
						renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
				</div>
			</div>
		</div>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid");
	grid.load();
		
	function remove(){
		var rows=grid.getSelecteds();
		if(rows.length>0){
			if(confirm('确定解除选中记录吗？')){
				var ids=[];
				for(var i=0;i<rows.length;i++){
					var r=rows[i];
					ids.push(r.crimid)
				}
				var id=ids.join(',');
				grid.load();
				
				$.ajax({
					url:"tbsflowCaseCriminalPiLiangJieChu.action?id="+id,
					success:function(text){
						alert("操作成功");
						grid.load();
					},
					error: function(){
					alert("操作失败");
					return;
					}
				});
			}
		}else{
			alert("请至少选中一条记录");
		}
	}
	//快速查询
	function search(){
		var key=mini.get("key").getValue();
		key=encodeURI(key);
		var defertime=mini.formatDate(mini.get("defertime").getValue(),"yyyy-MM-dd");
		grid.load({key:key,defertime:defertime});
	}
	//按下回车键是执行查询
	function onKeyEnter(e) {
            search();
        }
        
    function onActionRenderer(e){
    	var s="";
    	s='<a class=New_Button href="javascript:jiechu()">解除</a>';
    	return s;
    }
    function jiechu(){
    	var row = grid.getSelected();
    	if(confirm("确定要解除吗？")){
    	grid.load();
	    	$.ajax({
	    		url:"tbsflowCaseCriminalJieChu.action?id="+row.crimid,
	    		success:function(text){
	    			alert("操作成功");
	    			grid.load();
	    		},
	    		error:function(){
	    			alert("操作失败");
	    		}
	    	});
    	}
    }
        
    function onRecordReader(e){
    	if(e.value == 1){
    		return "暂缓"; 
    	}else if(e.value == 0){
    		return "正常";
    	}
    }
 	</script>
	</body>
</html>
