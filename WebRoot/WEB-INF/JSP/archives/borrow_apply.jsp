<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>借阅申请</title>

	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>

  </head>
  
  <body onload="init('1600_001_003');">
  		<form id="form1" method="post">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<table>
				<tr>
					<td style="width: 30%;">
						<a class="mini-button" style="display:none;" id="10624" iconCls="icon-downgrade" plain="true" onclick="borrowapply('10624');">批量申请</a>
					</td> 
					<td style="width: 70%;" align="center">
                    	查看时间:<input id="starttime" name="starttime"  property="editor" format="yyyy-MM-dd HH:mm:ss" value="javascript: new Date();" showTime="true" 
                    	class="mini-datepicker" emptyText="开始时间" style="width:160px;" onvaluechanged="timeVerification('start');" allowInput="true"  required="true"/>
                    	至<input id="endtime" name="endtime" property="editor" format="yyyy-MM-dd HH:mm:ss" showTime="true" class="mini-datepicker" emptyText="结束时间" 
                    	allowInput="true" style="width:160px;" onvaluechanged="timeVerification('end');" required="true" />&nbsp;&nbsp;&nbsp;<span id="validGroup1"></span>
					</td>
					<td style="white-space: nowrap;" >
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" id="key" style="width:150px;" emptyText="罪犯编号、姓名、档案名称" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('1600_001_003');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/flow/getborrowarchives.action?1=1&flowdefid=arch_zfdajysp"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div type="indexcolumn" width="10" headerAlign="center" align="center">序号</div>
					<div field="personid" width="20" headerAlign="center" allowSort="true" align="center">所属人编号</div>
					<div field="personname" width="20" headerAlign="center" allowSort="true" align="center">所属人</div>
					<div field="docyear" width="15" headerAlign="center" allowSort="true" align="center">档案年份</div>
					<div name="rank" field="rank" width="15" renderer="onRankRenderer" align="center" headerAlign="center">涉密级别
						<input property="editor" class="mini-textbox" style="width:100%;" data="Ranks"/>
					</div>
					<div field="isattached" width="15" renderer="onIsattachedRenderer" headerAlign="center" allowSort="true" align="center">
						正(副)卷<input property="editor" class="mini-textbox" style="width:100%;" data="Isattacheds"/>
					</div>
					<div field="classification"  width="15" renderer="onClassificationRenderer" headerAlign="center" allowSort="true" align="center">
						档案类别<input property="editor" class="mini-textbox" style="width:100%;" data="Classifications"/>
					</div>
					<div field="retention" width="14" renderer="onRetentionRenderer" headerAlign="center" allowSort="true" align="center">
						保存期限<input property="editor" class="mini-textbox" style="width:100%;" data="Retentions"/>
					</div>
					<div field="name"  headerAlign="left" allowSort="true" align="left">档案名称</div>
					<div width="15" headerAlign="center" align="center" allowSort="false" renderer="onCaoZuo">操作</div>  
				</div>
			</div>
		</div>
		<script type="text/javascript">
			var path = '<%=path %>';
			mini.parse();
			var form = new mini.Form("form1");
		 	var grid = mini.get("datagrid");
			grid.load();
			grid.sortBy("personid", "desc");
			if('${provincecode}'=='1300'){//河北不需要涉密级别
	        	grid.hideColumn("rank");;
	        }
	        
			function search() {
			     var key = mini.get("key").getValue();
			     grid.load({ key: key });
			}
			function onKeyEnter(e) {
			   search();
			}
			//批量申请  resid 和流程相关的按钮ID snodeid 源节点ID flowdefid 流程自定义ID
			function borrowapply(resid){
				//if (form.isValid() == false) return;alert(11);
            	if (timeVerification() == false) return;
				var starttime = mini.formatDate(mini.get("starttime").getValue(),"yyyy-MM-dd HH:mm:ss");
				var endtime = mini.formatDate(mini.get("endtime").getValue(),"yyyy-MM-dd HH:mm:ss");
				if(!endtime){
					document.getElementById("validGroup1").innerHTML = "结束时间不能为空";
					alert("结束时间不能为空！");return;
				}else document.getElementById("validGroup1").innerHTML = "";
				var rows = grid.getSelecteds();
	            if (rows.length > 0) {
	            	var conents = [];
	            	var archiveids = [];
	            	var personids = [];
	            	var personnames = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        conents.push(r.name);
                        personids.push(r.personid);
                        personnames.push(r.personname);
                        archiveids.push(r.archiveid);
                    }
                    var conent = conents.join(',');
                    var personid = personids.join(',');
                    var personname = personnames.join(',');
                    var archiveid = archiveids.join(',');
	            	$.ajax({
		   	            url:path+"/flow/borrowapply.action?1=1", 
		   	            data: {conents:conent,resid:resid,archiveids:archiveid,flowdefid:'arch_zfdajysp',
	            		personid:personid,personname:personname,starttimes:starttime,endtimes:endtime,operateType:'yes'},
		   	            type: "post",
		   	            cache:false,
		   	            async:false,
		   	            success: function (text){
		   					grid.reload();
		   	            }
		   			});
	            }else{
	            	alert("请至少选中一条数据");
	            }
			}
			//操作
			function onCaoZuo(){
				 var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
			     return s;
			}
			
	        function onRankRenderer(e) {
	            for (var i = 0, l = Ranks.length; i < l; i++) {
	                var g = Ranks[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
	        function onIsattachedRenderer(e) {
	            for (var i = 0, l = Isattacheds.length; i < l; i++) {
	                var g = Isattacheds[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
	        function onRetentionRenderer(e) {
	            for (var i = 0, l = Retentions.length; i < l; i++) {
	                var g = Retentions[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
	        var Ranks = [{ id: 1, text: '公开' },{ id: 2, text: '非涉密'},{ id: 3, text: '涉密'},
			   			 { id: 4, text: '内部'}, { id: 5, text: '秘密'},{ id: 6, text: '机密'}];
   			var Retentions = [{ id: 1, text: '1年' }, { id: 5, text: '5年'}, { id: 10, text: '10年'}, { id: 50, text: '50年'}, { id: 100, text: '100年'}];

			var Isattacheds = [{ id: 0, text: '正卷' },{ id: 1, text: '副卷'}];
		</script>
  </body>
</html>