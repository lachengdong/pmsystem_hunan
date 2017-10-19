<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>签章方案编辑</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			html,body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				overflow: hidden;
			}
			
		</style>
	</head>
	<body>
		<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;">
			<table style="width: 100%;">
				<tr>
					<td style="width: 100%;">
						<a class="mini-button" onclick="onOk" plain="true" style="width: 60px">保存</a>
						<a class="mini-button" onclick="onCancel" plain="true" style="width: 60px;">取消</a>
					</td>
					<td style="white-space: nowrap;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="mini-splitter" style="width:100%;height:100%;" >
    		<div size="70%" showCollapseButton="true" >
    			<div style="margin:0 auto; width:100%;">
				<form id="form1" method="post">
					<input id="signid" name="signid" class="mini-hidden" />
					<div style="padding-left: 11px; padding-bottom: 5px;">
						<table style="table-layout: fixed;">
							<tr>
								<td style="width: 70px;">
									方案名称：
								</td>
								<td colspan="5" style="width: 250px;">
									<input name="name" class="mini-textbox" required="true" autofocus style="width: 100%;" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									签章类型：
								</td>
								<!-- 这里是一棵树 -->
								<td colspan="5" style="width: 250px;">							
				                	<input id="signtype" name="signtype" style="width: 100%;" class="mini-buttonedit" required="true" onbuttonclick="onSignTypeEdit"/>
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									签章进程：
								</td>
								<td style="width: 50px;">
									<input name="progress" style="width: 100%;" class="mini-spinner" value="0" minValue="-10" maxValue="50" />
								</td>
								<td style="width: 70px;">
									签章个数：
								</td>
								<td style="width: 50px;">
									<input name="seal" style="width: 100%;" class="mini-spinner" value="0" minValue="0" maxValue="50" />
								</td>
								<td style="width: 70px;">
									批注个数：
								</td>
								<td style="width: 50px;">
									<input name="notation" style="width: 100%;" class="mini-spinner" value="0" minValue="0" maxValue="50" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									检查进程：
								</td>
								<td style="width: 50px;">
									<input id="isnotcheckprogress" class="mini-combobox" emptyText="否" name="isnotcheckprogress" data="[{ id: 0, text: '是' }, { id: 1, text: '否'}]" />
								</td>
								<td style="width: 70px;">
									检查签章：
								</td>
								<td style="width: 50px;">
									<input id="isseal" class="mini-combobox" style="width: 100%;" emptyText="否" name="isseal" data="[{ id: 1, text: '是' }, { id: 0, text: '否'}]"/>
								</td>
								<td style="width: 70px;">
									检查批注：
								</td>
								<td style="width: 50px;">
									<input id="isnotation" class="mini-combobox" style="width: 100%;" emptyText="否" name="isnotation" data="[{ id: 1, text: '是' }, { id: 0, text: '否'}]"/>
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									流程定义id：
								</td>
								<td colspan="5" style="width: 250px;">
									<input name="flowdefid" class="mini-textbox" required="true" style="width: 100%;" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									相关表单id：
								</td>
								<td colspan="5" style="width: 250px;">
									<input id="tempid" name="tempid" class="mini-textbox" required="true" style="width: 100%;" onvaluechanged="protectnodeOperate"/>
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									检查资源id：
								</td>
								<td colspan="5" style="width: 250px;">
									<input name="text2" class="mini-textbox"  style="width: 100%;" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									默认意见：
								</td>
								<td colspan="5" style="width: 250px;">
									<input name="content" class="mini-textbox"  style="width: 100%;"  value="同意"/>
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									方&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;案：
								</td>
								<td colspan="5" style="width: 250px;">
									<input name="scheme" class="mini-textbox"  required="true" style="width: 100%;" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：
								</td>
								<td colspan="5" style="width: 250px;">
									<!--  
									<input name="remark" style="width: 100%;" class="mini-textarea" />
									-->
									<input name="remark" class="mini-textbox"  style="width: 100%;" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限：
								</td>
								<td colspan="5" style="width: 250px;">
									<input id="resid" name="resid" allowInput="false" value="" style="width:100%;" class="mini-buttonedit" 
		                            onbuttonclick="onButtonEdit" />
								</td>
							</tr>
							<tr>
								<td style="width: 70px;">
									电子签名：
								</td>
								<td colspan="5" style="width: 250px;">
									<input id="protectnode" name="protectnode" style="width: 100%;height:80px;" class="mini-textarea" />
								</td>
							</tr>
						</table>
					</div>
				</form>
				</div>
			</div>
			
		    <div showCollapseButton="true">
		    	<div  class="mini-fit"  >
			    	<ul id="protectnodeselect" class="mini-tree"  style="width:350px;padding:5px;"  onnodecheck="onnodecheck"
				        showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="false"   expandOnLoad="true"       
				        showCheckBox="true">
				    </ul>
		    	</div>
		    	
		    </div>
		</div>
				
		<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		
		function onnodecheck(){
			var value = mini.get("protectnodeselect").getValue();
			mini.get("protectnode").setValue(value);
		}
		
		function protectnodeOperate(){
			var tempid = mini.get('tempid').getValue();
			if( tempid && ($.trim(tempid)) ){
				tempid = $.trim(tempid);
				var url = "<%=path%>/form/getSystemDictionaryTemplate.json?1=1&tempid=" + tempid;
				mini.get('protectnodeselect').load(url);
				
				var signid = mini.get('signid').getValue();
				if(signid){
					$.ajax( {
						url : "<%=path%>/form/getSelectTemplateNode.json?1=1",
						data : {signid:signid},
						cache : false,
						type : "post",
						async:false,
						success : function(text) {
							result = mini.decode(text);
							if(result){
								mini.get('protectnodeselect').setValue(result);
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
						
						}
					});
					
				}
			}
		}
		
		function SaveData() {
			var data = form.getData();
			form.validate();
			if (form.isValid() == false){
					return;
			}
			$.ajax( {
				url : "ajax/update.json?1=1",
				data : data,
				cache : false,
				type : "post",
				success : function(text) {
					CloseWindow("save");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
					CloseWindow();
				}
			});
		};
		//标准方法接口定义
		function SetData(data) {
			if(data){
				//跨页面传递的数据对象，克隆后才可以安全使用
				data = mini.clone(data);
				var signtype = data.signtype;
				form.setData(data);
				var signtypeInput = mini.get("signtype");
                    signtypeInput.setValue(signtype);
                    signtypeInput.setText(signtype);
				form.setIsValid(true);
				form.setChanged(false);
				
				protectnodeOperate();
			}
		};
		
		function GetData() {
			var data = form.getData();
			return data;
		};
		function huoqubiaodan() {
			var formtype = mini.get("formtype").value;
			var url = "ajaxGetBaoBiaoShuJu.action?formtype=" + formtype;
			mini.get("rid").load(url);
		};
		function CloseWindow(action) {
			if (action == "close" && form.isChanged()) {
				if (confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		};
		function onOk(e) {
			SaveData();
		};
		function onCancel(e) {
			CloseWindow("cancel");
		};
		function onCloseClick(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
		};
		
		// 编辑部门
        function onSignTypeEdit(e) {
			var signidInput = mini.get("signid");
			var signid = signidInput.value;
            var btnEdit = this;
            mini.open({
                url: "<%=path%>/sign/getsigntype.page?1=1" + "&signid="+signid,
                showMaxButton: false,
                title: "选择签章类型",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                        	var tempid = data.tempid || "";
                        	var tempname = data.tempname || "";
                            btnEdit.setValue(tempname);
                            btnEdit.setText(tempname);
                        }
                    }
                    
                }
            });   
        };
        
        function onButtonEdit(e) {
            var btnEdit = this;
            mini.open({
            	url: "toResourceTreePage.action?1=1",
                showMaxButton: false,
                title: "选择资源",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                            btnEdit.setValue(data.resid);
                            btnEdit.setText(data.name);
                        }
                    }
                }
            });
        }   
        
        
        
		</script>
	</body>
</html>
