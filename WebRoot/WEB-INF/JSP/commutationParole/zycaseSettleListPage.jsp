<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
<title>案件整理（重要罪犯）</title>
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
</head>
<body onload="onload()"> 
	<div class="mini-toolbar" style="padding:2px;border-bottom:0px;">
		<table>
			<tr>
				<td style="width:100%;">
					<!-- <s:property value="#request.menuid"/> --> 
					起始案件号<input id="anjianhao" class="mini-spinner" style="width:60px;"  maxValue="20000" /> 
					<a class="mini-button" plain="true" iconCls="icon-edit" plain="true" onclick="changeanjianhao()">批量更改</a>
				</td>
				<td style="white-space:nowrap;">

					<div id="anhao" name="anhao" class="mini-checkbox" checked="false"
						readOnly="false" text="重号筛选" onvaluechanged="search">重号筛选</div>

					<input id="caseyear" class="mini-combobox" style="width:80px"
					emptyText="年份" url="<%=path %>/getDateUPDownArea.action"
					value="${year}" onenter="onKeyEnter" onvaluechanged="search"/> 
<!-- 					<input id="casetype" class="mini-combobox" textField="name" valueField="codeid" name="casetype" style="width:80px;" showNullItem="true" nullItemText="全部类型"  -->
<!-- 						onvaluechanged="search" url="getCaseTypeMap.action?1=1&codetype=GK9999" /> -->
                    <input id="casetype" class="mini-combobox" valueField="id" textField="text" url="<%=path%>/getCodeByCode.json?1=1&codeType=GK7799&pcodeid=GK7799" 
    				 style="width:70px;" onvaluechanged="search" />
                                       第<input class="mini-textbox" id="casenums"
					name="casenums" class="mini-textbox" style="width:100px;"
					emptyText="例：1/1,2/1-10" onenter="onKeyEnter" />号
					<input name="jianqu" id="jianqu" name="jianqu" class="mini-combobox" style="width:110px;" valueField="ORGID" textField="NAME"
					emptyText="请选择监区" url="<%=path%>/getDepartList.action?1=1&qtype=jianqu" required="false" showNullItem="true" nullItemText="请选择监区" onvaluechanged="search"/> 
					
					<input class="mini-textbox" id="key" class="mini-textbox" style="width:85px;" emptyText="编号/姓名/拼音" onenter="onKeyEnter" /> 
					<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>&nbsp; <a class="mini-button"
					plain="true" iconCls="icon-help" onclick="chakanshuoming('10095')"> 
				    </a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid" allowMoveColumn="false" allowCellEdit="true"
			style="width:100%;height:100%;" class="mini-datagrid"
			url="<%=path%>/imGetCaseLookList.json?approval=yes&im=im" idField=""
			allowCellEdit="true" allowCellSelect="true" multiSelect="true"
			editNextOnEnterKey="true" allowAlternating="true"
			sizeList="[10,20,50,100]" pageSize="20">
			<div property="columns">
				<div type="checkcolumn" width="15"></div>
				<div visible="false" field="ccid" name="ccid" id="ccid"
					headerAlign="center" allowSort="true" align="center">
					<input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div visible="false" field="crimid" name="crimid" id="crimid"
					headerAlign="center" allowSort="true" align="center">
					<input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="applyid" headerAlign="center" allowSort="true"
					align="center" readOnly="true">
					罪犯编号 <input property="editor" class="mini-textbox"
						style="width:100%;" />
				</div>
				<div field="applyname" headerAlign="center" allowSort="true"
					align="center" readOnly="true">
					姓名 <input property="editor" class="mini-textbox"
						style="width:100%;" />
				</div>
				<div field="areaname" headerAlign="center" allowSort="false"
					align="center" readOnly="true">
					监区 <input property="editor" class="mini-textbox"
						style="width:100%;" />
				</div>
				<div field="anjianhao" headerAlign="center" allowSort="true"
					align="center" readOnly="true">
					案件号 <input property="editor" class="mini-textbox"
						style="width:100%;" />
				</div>
				<div field="casenumber" headerAlign="center" align="center"
					allowSort="false" style="background: #50F0B4;">
					案号(可编辑) <input property="editor" class="mini-spinner"
						style="text-align: center;" minValue="0" maxValue="20000" />
				</div>
			</div>
		</div>
	</div>
	<div showCollapseButton="false">
		<div id="showContent"
			style="height:30px;overflow:auto;display:none;background:#EFF2F5;color:#3789B8;text-align:center;">
			<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
		</div>
	</div>
	<script type="text/javascript"> 
		    mini.parse();
			var grid= mini.get("datagrid");
		    grid.on("drawcell",function(e){
				     var field = e.field;
		             if (field == "casenumber" ) {
		                	e.cellStyle = "background:#D9E7F8";
		             }
			});
		    mini.get("casetype").select(0);
		    mini.get('anjianhao').setValue(null); 
		    function onload(){
		    	search();
		    }
			function search() {
		         var caseyear = mini.get("caseyear").getValue();
		         var casenums = mini.get("casenums").getValue();
		         if(caseyear != ''){
		        	 if(caseyear.length != 4){
	                     alert("年份输入不规范:"+caseyear);return;
				     }
			     }
		         var key = mini.get("key").getValue();
		         //此处获取 text原因 ：sql语句中直接使用字号这个字段=casetype的值 ，现在casetype的值是codeid所以无法查询正确
		         var casetype = mini.get("casetype").getText();
		         var jianqu = mini.get("jianqu").getValue();
		         var anhao = mini.get("anhao").getValue();
		         mini.get("datagrid").load({ key: key,caseyear: caseyear,casenums:casenums,casetype:encodeURI(casetype),jianqu:jianqu,anhao:anhao});
		    }
	        function onKeyEnter(e) {
	            search();
	        }
	        $("#key").bind("keydown", function (e) {
	            if (e.keyCode == 13) {
	                search();
	            }
	        });
	        
	        
	        function changeanjianhao(){
	        	var qishi=mini.get("anjianhao").getValue();
        		var grid= mini.get("datagrid");
	            var rows = grid.getSelecteds();
	            var im = "1";
	            if(qishi && qishi > 0){ 
	            	if (rows.length > 0) { 
		            	var notice = rows.length==1?"确定选择<"+qishi+">初始案件号进行更改？":"确定选择<"+qishi+">初始案件号批量更改？"; 
			            if (confirm(notice)) {
			    	   		var messageid = mini.loading("批量保存中 请稍后 ...", "处理中");
			            	var ids = [];
			            	 for (var i = 0, l = rows.length; i < l; i++) {
		                        var r = rows[i];
		                        ids.push(r.flowdraftid);
		                        $.ajax({
			                        url:"<%=path%>/commutationParole/ajaxGetAnnexcontent.json?1=1",
			                        data: {flowdraftid:r.flowdraftid,parolenumber:qishi+i,text6:r.text6,im:im},
			                        type: 'POST',
			                        async:false,
			                        success: function (text) {
			                        	//var objs = eval('('+text+')');
			                        	//var aipObj = document.getElementById("HWPostil1");
		  	  							//aipObj.CloseDoc(0);
		  	  							//aipObj.LoadFileBase64(objs.aipFileStr);
		  	  							//if(aipObj.IsOpened()){
		  	  							//	var parolenumber = objs.parolenumber;
		  	  							//	aipObj.setValue("Page1.parolenumber","");
		  	  							//	aipObj.setValue("Page1.parolenumber",parolenumber);
		  	  							//	aipObj.SleepSecond(1);
											//保存表单
										//	var newaipFileStr=aipObj.GetCurrFileBase64();
										//	$.ajax({
									    //         url: "<%=path%>/commutationParole/ajaxSaveAnnexcontent.json?1=1",
									    //        data: {flowdraftid:r.flowdraftid,aipFileStr:newaipFileStr},
									    //         contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
									    //        cache: false,
									    //         type: 'POST',
									    //         success: function (text) {
									             	
									    //         },
									    //         error: function (jqXHR, textStatus, errorThrown) {
									    //            alert(errorThrown);
									    //         }
									    //     });
		  	  							//}
			                        },
			                        error: function () {
			                        	alert("更改失败,请联系管理员!");
			                        }
			                    });
		                    }
                            mini.hideMessageBox(messageid);
			            }
			            grid.reload();
		        	}else{
		        		alert("请选择要更改的案件！");
		        	}
	            }else{
	            	alert("请填写起始案件号！");
	            }
	        }
	        
	        
	        function savechangeanjianhao(){ 
	        	var grid = mini.get("datagrid");
	            var data = grid.getChanges();
	            if (data.length > 0) {
	            	var notice = data.length==1?'确定更改案件号？':'确定批量更改案件号？';
		            if (confirm(notice)) {
		            	 for (var i = 0, l = data.length; i < l; i++) {
	                        var r = data[i];
	                        $.ajax({
	                        url:"<%=path%>/commutationParole/getCaseLookList1.json?1=1",
	                        data: {flowdraftid:r.flowdraftid,parolenumber:r.casenumber,text6:r.text6},
	                         type: 'POST',
	                        success: function (text) {
	                        	//var objs = eval('('+text+')');
	                        	//var aipObj = document.getElementById("HWPostil1");
  	  							//aipObj.CloseDoc(0);
  	  							//aipObj.LoadFileBase64(objs.aipFileStr);
  	  							//if(aipObj.IsOpened()){
  	  							//	var parolenumber = objs.parolenumber;
  	  							//	aipObj.setValue("Page1.parolenumber","");
  	  							//	aipObj.setValue("Page1.parolenumber",parolenumber);
  	  							//	aipObj.SleepSecond(1);
									//保存表单
								//	var newaipFileStr=aipObj.GetCurrFileBase64();
								//	$.ajax({
							    //         url: "<%=path%>/commutationParole/ajaxSaveAnnexcontent.json?1=1",
							    //         data: {flowdraftid:r.flowdraftid,aipFileStr:newaipFileStr},
							    //         contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
							    //        cache: false,
							    //         type: 'POST',
							    //         success: function (text) {
							    //         	
							    //         },
							    //         error: function (jqXHR, textStatus, errorThrown) {
							    //             alert(errorThrown);
							    //         }
							    //     });
  	  							//}
	                        },
	                        error: function () {
	                        	
	                        }
	                    });
	               }
	            }
	            }
	            grid.reload();
	        }
	        
		</script>
</body>
</html>