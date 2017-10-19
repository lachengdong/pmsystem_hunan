<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>档案鉴定</title>
    
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>

  </head>
  
  <body onload="init('1600_001_002');">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<input id="archiveShenPiSeal" name="archiveShenPiSeal" class="mini-hidden" value="${archiveShenPiSeal}"/>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<table>
				<tr>
					<td align="left" style="width: 50%;">
				      <a class="mini-button" style="display:none;" id="10613" iconCls="icon-downgrade" plain="true" onclick="batchapproveSeal('10613','yes');">批量审批</a>
				      <!--  <a class="mini-button" style="display:none;" id="10621" iconCls="icon-downgrade" plain="true" onclick="batchapprove('10621','no');">批量退回</a>--> 
				      <a class="mini-button" style="display:none;" id="4000143" iconCls="icon-downgrade" plain="true" onclick="remove('4000143','no');">批量删除</a>
					</td> 
					<td style="width: 40%;" align="center">
						<c:if test="${provincecode != '1300'}">
							涉密级别:<input name="rank" id="rank" class="mini-combobox" style="width: 60px;" data="Ranks" value="1" required="true"/>
                	    </c:if>
						保存期限: <input name="retention" id="retention" class="mini-combobox" style="width: 60px;" value="1" data="Retentions" onvalidation="onNumberValidation" required="true" /> 
					</td>
					<td style="white-space: nowrap;" >
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" style="width: 150px;"  id="key" class="mini-textbox" emptyText="罪犯编号、姓名、档案名称" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('1600_001_002');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/flow/getarchives.action?1=1&flowdefid=arch_zfdajdsp"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20" >
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div type="indexcolumn" width="8" headerAlign="center" align="center">序号</div>
					<div field="archiveid" width="15" headerAlign="center" allowSort="true" align="center" visible="false">archiveid</div>
					<div field="applyid" width="15" headerAlign="center" allowSort="true" align="center">罪犯编号</div>
					<div field="applyname" width="15" headerAlign="center" allowSort="true" align="center">姓名</div>
					<div field="conent" width="35" headerAlign="center" allowSort="true" align="left">档案名称</div>
					<div field="startsummry" width="40" headerAlign="center" align="center" allowSort="true">开始摘要</div> 
					<!-- 
						<div field="endsummry" width="40" headerAlign="center" align="center" allowSort="true">结束摘要</div> 
						<div field="commenttext" width="25" headerAlign="center" align="center" allowSort="true">审批意见</div>   
					 -->
					<div field="opname" width="15" headerAlign="center" align="center" allowSort="true">提交人</div>
					<div field="optime" width="30" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true" renderer="onDateRenderer">提交时间</div>
					<div width="15" headerAlign="center" align="center"
					 	allowSort="false" renderer="onCaoZuo">操作</div>  
				</div>
			</div>
		</div>
		<div style="height: 0px;border: 0px;">
			<table>
				<tr>
					<td height="100%">
					<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
			var Ranks = [{ id: 1, text: '公开' },{ id: 2, text: '非涉密'},{ id: 3, text: '涉密'},
		   			 { id: 4, text: '内部'}, { id: 5, text: '秘密'},{ id: 6, text: '机密'}];
			var Retentions = [{ id: 1, text: '1年' }, { id: 5, text: '5年'}, { id: 10, text: '10年'}, { id: 50, text: '50年'}, { id: 100, text: '100年'}];
		
			mini.parse();
			var path = '<%=path%>';
		 	var grid = mini.get("datagrid");
			grid.load();
			grid.sortBy("optime", "desc");
			function search() {
			     var key = mini.get("key").getValue();
			     grid.load({ key: key });
			}
			function onKeyEnter(e) {
			   search();
			}
			var countIndex = 0;
			function batchapproveSeal(resid,approve){
				var archiveShenPiSeal = mini.get("archiveShenPiSeal").getValue();//审批是否需要签章
				if(archiveShenPiSeal=='1' && approve != 'no'){//审批需要签章
					var rows = grid.getSelecteds();
					if (rows.length > 0) {
						var aipObj = document.getElementById("HWPostil1");
	              		var dtrer=0;
						aipObj.Logout();
						dtrer = aipLogin(aipObj);
						if(dtrer==-200)
						{
							alert("未发现智能卡");return;
						}
						else if(dtrer!=0){
							alert("登录失败。错误ID:"+dtrer);return;
						}
						if(countIndex < rows.length){
		                    getArchivesContentForSeal(rows,resid);
		                }
					}else{
						alert("请选中一条记录");
					}
				}else{//不需要签章
					batchapprove(resid,approve);
				}
			}
			//resid 和流程相关的按钮ID snodeid 源节点ID
			function batchapprove(resid,approve){
				var rows = grid.getSelecteds();
				var rank = '1';
				if('${provincecode}' != '1300') rank = mini.get("rank").getValue();
				var commenttext = "同意";
				if(approve == 'no') commenttext = "不同意";
				var retention = mini.get("retention").getValue();
	            if (rows.length > 0) {
	            	var conents = [];
	            	var flowids = [];
	            	var flowdraftids = [];
	            	var applyids = [];
	            	var applynames = [];
	            	var archiveids = [];
	            	var orgids = [];
	            	
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        conents.push(r.conent);
                        applyids.push(r.applyid);
                        flowids.push(r.flowid);
                        flowdraftids.push(r.flowdraftid);
                        applynames.push(r.applyname);
                        archiveids.push(r.archiveid);
                        orgids.push(r.orgid);
                    }
                    var conent = conents.join(',');
                    var flowid = flowids.join(',');
                    var flowdraftid = flowdraftids.join(',');
                    var applyid = applyids.join(',');
                    var applyname = applynames.join(',');
                    var archiveid = archiveids.join(',');
                    var orgid = orgids.join(',');
	            	$.ajax({
		   	            url:path+'/flow/batchapprove.action?1=1', 
		   	            data: {applyids:applyid,applynames:applyname,conents:conent,flowids:flowid,resid:resid,isapprove:'yes',orgids:orgid,commenttext:commenttext,
	            		rank:rank,retention:retention,archiveids:archiveid,approve:approve,flowdraftids:flowdraftid,flowdefid:'arch_zfdajdsp'},
		   	            type: "post",
		   	            cache:false,
		   	            async:false,
		   	            success: function (text){
		   					grid.reload();
		   	            }
		   			});
	            }else{
	            	alert("请选中一条记录");
	            }
			}
		   //获取要签章的档案大字段
			function getArchivesContentForSeal(rows,resid){
				var flowid = rows[countIndex].flowid;
				$.ajax({
		             url: "printArchives.action?1=1",
		             data: { flowids:flowid},
		             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		             type: "post",
		             success: function (text) {
		            	var aipObj=document.getElementById("HWPostil1");	
   		        	 	aipObj.CloseDoc(0);
   		             	aipObj.LoadFileBase64(text.replace(/\"/g,"").replace(/\"/g,"").replace("[","").replace("]","").replace(/\\/g,""));//打开模板文件
   		             	var vNoteName ="Page1.PageText4";
						aipObj.InsertNote(vNoteName,0,3,"37000","46200","7000","1600");//添加编辑域
					    aipObj.Setvalue(vNoteName,":PROP:BORDER:0");//设置区域无边框
						aipObj.Setvalue(vNoteName,":PROP::LABEL:1"); //设置为不可编辑
						aipObj.Setvalue(vNoteName,":PROP::LABEL:2"); //可选,(对于编辑器不可点击)	
						aipObj.Setvalue(vNoteName,":PROP:VALIGN:1");//设置竖直剧中
					    aipObj.Setvalue(vNoteName,":PROP:FACENAME:黑体");//设置字体
					    aipObj.Setvalue(vNoteName,":PROP:FRONTCOLOR:0");//设置字体颜色
					    aipObj.Setvalue(vNoteName,":PROP:FONTSIZE:16" );//设置文字域字体大小
					    aipObj.Setvalue(vNoteName,":PROP:FONTBOLD:1"); //设置文字是否为BOLD	
					    aipObj.Setvalue(vNoteName,":PROP:FONTITALIC:0"); //设置文字是否为斜体0正
					    aipObj.Setvalue(vNoteName,"审核人：");//设置值
					    var sealcontent="";
						var sealdate="";
						var cppoopiniondate="";
						var cppoopinion="";
						var strSignatureNoteInfo ="节点类型:1,页码:1,上:47000,左:46000,高:7500,宽:2000";
						InsertNote(strSignatureNoteInfo,aipObj,sealcontent,sealdate,cppoopiniondate,cppoopinion);
						saveArchivesContentForSeal(rows,aipObj,resid);
		             },
		             error: function (jqXHR, textStatus, errorThrown) {
		            	 alert("操作失败!");
		             }
		 		 });
			}
			//保存签章后文件
			function saveArchivesContentForSeal(rows,aipObj,resid){
				var docconent = aipObj.GetCurrFileBase64();//获取大字段
				var r = rows[countIndex]; 
                var conent = r.conent;
                var flowid = r.flowid;
                var flowdraftid = r.flowdraftid;
                var applyid = r.applyid;
                var applyname = r.applyname;
                var archiveid = r.archiveid;
                var orgid = r.orgid;
                var rank = '1';
				if('${provincecode}' != '1300') rank = mini.get("rank").getValue();
				var retention = mini.get("retention").getValue();
				$.ajax({
			         url:"batchapprove.action?1=1", 
		   	            data: {docconent:docconent,applyids:applyid,applynames:applyname,conents:conent,flowids:flowid,resid:resid,isapprove:'yes',orgids:orgid,commenttext:'同意',
	            		rank:rank,retention:retention,archiveids:archiveid,approve:'yes',flowdraftids:flowdraftid,flowdefid:'arch_zfdajdsp'},
			         type: "POST",
			         async:false,
			         success: function (text){
		         		countIndex = countIndex+1;
		         		if(countIndex == rows.length){
		         			countIndex = 0;
		         			alert("操作成功");
		         			grid.reload();
		         		}else{
		         			getArchivesContentForSeal(rows,resid);
		         		}
			         }
			     }); 
			}
			//操作
			function onCaoZuo(e){
				var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
	            var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
			    return s;
			}
			function lookarchives(e){
	            var url = 'flow/lookarchives.action?1=1&resid=10623';
	            var row = grid.getSelected();
	            if (row) {
                    var flowid = row.flowid;
                    var rank = '1';
					if('${provincecode}' != '1300') rank = mini.get("rank").getValue();
    				var retention = mini.get("retention").getValue();
                    var win = mini.open({
	                    url: url+"&flowids="+flowid,
	                    title: "档案", width: 600, height: 360,
	                    onload: function () {
	                        var iframe = this.getIFrameEl();
	                        var data = { action: "edit",orgid:row.orgid,conent:row.conent,flowid:flowid,flowdefid:row.flowdefid,
	                        flowdraftid:row.flowdraftid,rank:rank,retention:retention,archiveid:row.archiveid,applyid:row.applyid,
	                        applyname:row.applyname};
	                        iframe.contentWindow.SetData(data);
	                    },
	                    ondestroy: function (action) {
	                        grid.reload();
	                    }
	                });
                    win.max();
	            } else {
	                alert("请选中一条记录");
	            }
			}
			//只能为1~3位数字
			function onNumberValidation(e) {
	            if (e.isValid) {
	                var pattern = /\d*/;
	                if (e.value.length < 1 || e.value.length > 3 || pattern.test(e.value) == false) {
	                    e.errorText = "必须输入1~3位数字";
	                    e.isValid = false;
	                }
	            }
	        }
			
			
			function remove(resid){
				var url = 'removearchives.json?1=1';
	            var rows = grid.getSelecteds();
	            if (rows && rows.length>0) {
	            	if (confirm("确定删除选中记录？")){
	            		var archiveids = [];
	                    for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        archiveids.push(r.archiveid);
	                    }
	                    var archiveid = archiveids.join(',');
	                    $.ajax({
	                        url: url,
	                        type: 'POST',
	                        cache:false,
	       	             	async:false,
	                        data:{resid:resid,archiveid:archiveid},
	                        success: function (text) {
	                            alert("操作成功!");
	                            grid.reload();
	                        },error: function () {
	                        	alert("操作失败!");
	                        }
	                    });
	            	}
	            } else {
	                alert("请选中一条记录");
	            }
			}

		</script>
  </body>
</html>