<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>建议书批量保存</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
			html,body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				/*background-color: #ccffcc;*/
				background-attachment: fixed;
			}
	    </style>
</head>
<body>
	
	<div style="display:none;">
		<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
		<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
		<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
	</div>
	
	<div id="zhezhao" class="mini-splitter" vertical="true" style="height:100%;width:100%;border:0;" handlerSize="0">
	    <div size="0" style="border:none">
	        <div class="mini-toolbar" style="padding:0px;border-bottom:0;">
	        	 <table style="width:100%;">
		            <tr>
		                <td style="width:100%;"> 
		                
		                	<input id="crimid" name="crimid"  class="mini-hidden"/>
		                	<input id="menuid" name="menuid"  value="${menuid}" class="mini-hidden"/>
						    <input id="tempid" name="tempid"  value="${tempid}" class="mini-hidden"/>
						    <input id="flowdraftid" name="flowdraftid" class="mini-hidden"/> 
		                    <input id="flowdefid" name="flowdefid" value="${flowdefid}" class="mini-hidden"/> 
		                    <!--  
			            	<a class="mini-button" id="dakai" iconCls="icon-goto" plain="true" onclick="modify()">模版修改</a> 
			            	-->
			             	<label >案件号:</label>
			             	(<input class="mini-textbox" style="width:40px" value="${curyear}" vtype="maxLength:4;minLength:4;int;" 
			             		required="true" id="curyear" name="curyear" onenter="onKeyEnter" />)
			             	<input class="mini-textbox" style="width:40px" value="${shortname}"  required="true" id="shortname" name="shortname"/>
			             	<!--  
			             	<input class="mini-textbox" style="width:50px" value="${casetype}"  required="true" id="casetype" name="casetype" onenter="onKeyEnter" />
						    -->
						    <input id="casetype"  name="casetype"  class="mini-combobox" valueField="id" textField="text" style="width:70px"
								url="<%=path%>/getCodeByCode.json?1=1&codeType=GK7799&pcodeid=GK7799&scearch=JXJS" emptyText="" onvaluechanged="onKeyEnter"/>
								
						    <input id="jailcasechg" name="jailcasechg" class="mini-combobox" valueField="id" textField="text" url="<%=path%>/getCodeByCode.json?1=1&codeType=GK8899&pcodeid=GK8899"
    				 		style="width:50px;"   onvaluechanged="onKeyEnter" />
    				 		
						    <input class="mini-textbox" style="width:200px;height:20px" id="casenums" name="casenums" onenter="onKeyEnter" 
						    	emptyText="此处输入案件号：1,2或1,3-5,8" />号
						    
						    <!--  
							<input id="nowpunishmenttype"  name="nowpunishmenttype"  class="mini-combobox" valueField="codeid" textField="name" 
								url="ajaxListByMap.action?codetype=GB022&codeids=1,2,3" emptyText="现刑种" onvaluechanged="search"/>
							-->
							<!--  
							<input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择单位"
		                            showNullItem="true" nullItemText="--全部--" url="getJailListUnderCourt.action" style="width:100px;" onvaluechanged="onjianyuchanged"/>
		                    -->
		                            
							<a class="mini-button"  iconCls="icon-search" plain="true"  onclick="search();">查询</a>
							<!--
							<a class="mini-button"  iconCls="icon-save" plain="true" onclick="savewenshu();">保存</a> 
							<a class="mini-button"  iconCls="icon-print" plain="true" onclick="dayin();">打印</a>
							<a class="mini-button"  iconCls="icon-print" plain="true" onclick="viewbaobiao();">预览</a>
							-->
							
							<input id="color"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:70px;"
									url="<%=path%>/txt/stype.txt" emptyText="样式选择" value="green" onvaluechanged="onselectChanged();"/>
						    <input id="font"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:45px;" 
									url="<%=path%>/txt/font.txt" emptyText="字号"  value="zhong" onvaluechanged="onselectChanged();"/>
                            <input id="suggesttime" name="suggesttime" class="mini-datepicker" value="" required="true" />
							${topsearch}
	    					${topstr}	
	    					
							<!--  
							<a class="mini-button"  iconCls="icon-add" plain="true" onclick="insertwenshu();">新增</a>
							-->
		                </td>
		            </tr>
		        </table>
        	</div>

	    </div>
	    
	    <div style="border:none">
	        <div class="mini-splitter" style="width:100%;height:100%;margin: 0;padding: 0;border:0;">
	        	
	        	<div size="35%" align="center">
	        		<div id="datagrid1" class="mini-datagrid" showFilterRow="true" style="width:100%;height:100%;border: 0" 
	        			sizeList="[10,20,50,100,500]" pageSize="20"
			       		url="getPublicListData.json?1=1&flowdefid=${flowdefid}&tempid=${tempid}&solutionid=700763" 
			       		 allowAlternating="true" multiSelect="true" onrowclick="lookBigText">
				        <div property="columns">
				        	<div type="checkcolumn" width="15"></div>
				            <div field="anjianhao1" width="25" headerAlign="center" align="center" allowSort="true"  >案号</div> 
				            <div field="crimid" width="40" headerAlign="center" align="center" allowSort="true" >罪犯编号</div>  
				            <div field="orgname" width="40" headerAlign="center" align="center" allowSort="true" visible=false  >所在监区</div>     
				            <div field="name" width="30" headerAlign="center" align="center" allowSort="true">姓名
				            <input id="nameFilter" property="filter" emptyText="姓名查询" class="mini-textbox" onvaluechanged="onNameFilterChanged" style="width:100%;" />
				            </div>
				            <div field="hasmaked" width="25" headerAlign="center" align="center" allowSort="false" >文书</div>  
				            <div field="suggesttime" width="0" headerAlign="center" align="center" allowSort="true"  >建议日期</div> 
<!-- 				            <div field="" width="30" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>   -->
				        </div>
    				</div> 
	        </div>
	        	
		    <div showCollapseButton="false" id="editForm1" >
		        <input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
		    </div>  
			          
			</div>
	    </div> 
	           
	</div>
	
	<script type="text/javascript">
		document.all("MyViewer").Init(window, document,600);
	</script>
	
	<script type="text/javascript">
        mini.parse();
        mini.get("casetype").select(0);
        //页面打开初始化建议日期
	    var nowdate = new Date();
	    nowdate = nowdate.getFullYear()+"-"+(nowdate.getMonth()+1)+"-"+nowdate.getDate()
	    mini.get("suggesttime").setValue(nowdate);
	    
	    mini.get("jailcasechg").select(0);
	    
        var form = new mini.Form("editForm1");
        form.setChanged(false);
        var zhezhao = new mini.Form("zhezhao");
         
        var grid = mini.get("datagrid1");
        grid.load();
        //var grid2 = mini.get("datagrid2");
        
     function onActionRenderer(e) {
            var record = e.record;
            var s = ' <a class="Edit_Button" href="javascript:lookBigText()" >操作</a>';
            //s += '&nbsp;&nbsp; <a class="Edit_Button" href="javascript:savewenshu()" >保 存</a>';
            return s;
        }
        
        function onActionRenderer1(e) {
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:viewbaobiao()" >查看文书</a>'; 
            return s;
        }
        
        
        function batchLookWenShu(menuid){
       		var rows = grid.getSelecteds();
            if (rows.length > 0){
            	var idArr = [];
	            for (var i = 0, l = rows.length; i < l; i++){
	               var row = rows[i];
	               if(row.menuid){
	               		menuid = row.menuid;
	               }
	               idArr.push(row.flowdraftid+'@'+menuid);
	            }
				var ids = idArr.join(',');
				
		       	//用于返回到主页面的url
		       	var furl = encodeURIComponent(location.href);
	            var url = "toFatherTabPage.action?1=1&ids="+ids+"&indexFlag="+0+"&furl="+furl;
	            
	            var win= mini.open({
	                url: url,
	                showMaxButton: true,
	             	allowResize: false, 
	                title: "", 
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { action:"show" };
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (text){
	                    grid.reload();
	                }
	            });
	            win.max();
           		
            }else {
                alert( "请至少选中一条记录！");
            }
       }
        
        
        function lookBigText(){
        	//savePreDoc();//保存前一个笔录
        	if(form.isChanged()){
        		alert("文书已经修改，请先保存！");
        		return;
        	}
        	onselectChanged();
        	var row = grid.getSelected();
        	var flowdraftid = row.flowdraftid;
        	var crimid = row.crimid;
        	var suggesttime = row.suggesttime;
        	mini.get("flowdraftid").setValue(flowdraftid);
        	mini.get("crimid").setValue(crimid);
        	if(suggesttime == null){
        		mini.get("suggesttime").setValue(nowdate);
        	}else{
        		mini.get("suggesttime").setValue(suggesttime);
        	}
        	
        	//top["win"].setHiddenValue("flowdraftid",flowdraftid);
        	var flowdefid = mini.get("flowdefid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	//alert(tempid+"||"+crimid+"||"+flowdraftid+"||"+flowdefid);
        	//加载文书记录
        	//grid2.setUrl("getCriminalCourtHandleCaseList.action?flowdraftid="+flowdraftid+"&tempid="+tempid);
        	//grid2.load();
        	makeTextPage(tempid, crimid, flowdraftid, flowdefid);
        }
        
        function makeTextPage(tempid, crimid, flowdraftid, flowdefid){
        	var result = generateTextPage(tempid, crimid, flowdraftid, flowdefid);
        	form.setData(result);
        }
        
        function getBigText(tempid, crimid, flowdraftid, flowdefid){
        	var result = generateTextPage(tempid, crimid, flowdraftid, flowdefid);
        	return result.annexcontent;
        }
        
        function generateTextPage(tempid, crimid, flowdraftid, flowdefid){
        	var result = null;
        	data = {};
        	data['applyid'] = crimid;
        	data['flowdefid'] = flowdefid;
        	data['solutionid'] = 700764;
        	data['tempid'] = tempid;
        	data['flowdraftid'] = flowdraftid;
        	data['optype'] = 'edit';
        	
        	var url = "<%=path%>/getSysTemplateData.json?1=1";
       		$.ajax({
                 url: url,
                 type: "post",
                 data : data,
                 async: false,
                 success: function (text){
                 	if(text){
                 		result = mini.decode(text);
                    	//form.setData(result);
                 	}
                 }
	         });
       		return result;
        }
        
      //保存文书
      function savewenshu(){
    	  	form.validate();
          	if (form.isValid() == false){
          		return false;
          	}
          
      		var data = form.getData();
         	if(!data.annexcontent){
         		alert("内容不能为空！");
         		return;
         	}
         	var keywords=/[<>]/;
         	if(keywords.test(data.annexcontent)){
         		alert("不能包含特殊字符！例如 ‘<’ ‘>’等特殊字符");
         		return ;
         	}
         	zhezhao.loading("保存中，请稍后......");
      		var flowdraftid = mini.get("flowdraftid").getValue();
      		var tempid = mini.get("tempid").getValue();
      		var flowdefid = mini.get("flowdefid").getValue();
      		var suggesttime = mini.get("suggesttime").getValue();
      		suggesttime = suggesttime.getFullYear()+"/"+(suggesttime.getMonth()+1)+"/"+suggesttime.getDate();
      		//var otherid = mini.get("otherid").getValue();
      		//
        	var docconent = data.annexcontent;
        	data.annexcontent = null;
      		
        	data["suggesttime"] = suggesttime;
        	data["docconent"] = docconent;
    		data["flowdraftid"] = flowdraftid;
    		data["tempid"] = tempid;
    		data["flowdefid"] = flowdefid;
    		data["masterslave"] = 'slave';
    		
    		var url="ajaxSaveBaseDoc.action?1=1";
            //var json = mini.encode([data]);
            $.ajax({
                url: encodeURI(encodeURI(url)),
                data: data,
                type: "post",
                success: function (text) {
                	text = mini.decode(text);
                	if(text["rows"]==1){
                		//mini.get("otherid").setValue(obj.otherid);
                		saveShengChengHouDaZiDuan(flowdraftid);
                		alert("操作成功！");
                		grid.reload();
                		//grid2.reload();
                	}else{
                		alert(text["status"]);
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
            form.setChanged(false);
            zhezhao.unmask();
        }
      
      	//保存文书
      function makeWenShu(docconent,flowdraftid){
      		//var flowdraftid = mini.get("flowdraftid").getValue();
      	    var tempid = mini.get("tempid").getValue();
      		var flowdefid = mini.get("flowdefid").getValue();
      		var suggesttime = mini.get("suggesttime").getValue();
      		suggesttime = suggesttime.getFullYear()+"/"+(suggesttime.getMonth()+1)+"/"+suggesttime.getDate();
        	var data = {};
        	data["docconent"] = docconent;
    		data["flowdraftid"] = flowdraftid;
    		data["tempid"] = tempid;
    		data["flowdefid"] = flowdefid;
    		data["masterslave"] = 'slave';
    		data["suggesttime"] = suggesttime;
    		var url = "<%=path%>/ajaxSaveBaseDoc.action?1=1";
            //var json = mini.encode([data]);
            $.ajax({
                url: encodeURI(encodeURI(url)),
                data: data,
                type: "post",
                async: false,
                success: function (text) {
                	text = mini.decode(text);
                	if(text["rows"]==1){
                		saveShengChengHouDaZiDuan(flowdraftid);
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
        }
        
        
        //保存生成正式文书后的大字段
      function saveShengChengHouDaZiDuan(flowdraftid){
	       	var menuid = mini.get("menuid").getValue();
	       	//var flowdraftid = mini.get("flowdraftid").getValue();
	      var tempid = mini.get("tempid").getValue();
	       	var flowdefid = mini.get("flowdefid").getValue();
	       	var crimid = mini.get("crimid").getValue();
	       	
			$.ajax({
	             url: "<%=path%>/getReportData.json?1=1",
	             data: { menuid:menuid, flowdraftid:flowdraftid, tempid:tempid, flowdefid:flowdefid, applyid:crimid},
	             contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	             cache: false,
	             dataType: 'text',
	             type: "post",
	             async: false,
	             success: function (text){
	             	dayinwenshu(text);//虚拟打印出来
	             	saveDaZiDuan(flowdraftid);//保存打印出来的大字段
	             },
	             error: function (jqXHR, textStatus, errorThrown) {
	            	 //alert("操作失败!");
	             }
	         });
        }
        
        function saveDaZiDuan(flowdraftid){
        	//var otherid = mini.get("otherid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	//var flowdraftid = mini.get("flowdraftid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
            var aipObj=document.getElementById("HWPostil1");
        	var mydata=aipObj.GetCurrFileBase64();
           	tempid = tempid+"report";
           	
           	var data = {};
           	data["docconent"] = mydata;
           	data["flowdraftid"] = flowdraftid;
           	data["tempid"] = tempid;
           	data["flowdefid"] = flowdefid;
           	data["masterslave"] = 'slave';
           	
           	var url="ajaxSaveBaseDoc.action?1=1";
           	$.ajax({
           		url:encodeURI(encodeURI(url)),
                data: data,
                type:"POST",
                cache: false,
                async: false,
                success: function (text) {
                	text = mini.decode(text);
                	if(text["rows"]==1){
                		//alert("操作成功！");
                	}else{
                		alert(text["status"]);
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                      alert("操作失败！");
                }
            });
        }
        
        
        function dayinwenshu(reportdata){
			//var aipObj=document.getElementById("HWPostil1");
			//var reportdata = eval(text);
			if(reportdata){
				//reportdata = mini.decode(reportdata);
				document.all("MyViewer").ShowProgress = false;
				document.all.HWPostil1.BeforeConvert("");
				document.all("RMVIEWER_DATA").value=reportdata;
				document.all("MyViewer").GetReportData(reportdata);
				document.all("MyViewer").ShowPrintDialog = false;
				document.all("MyViewer").PrintReport();
				document.all.HWPostil1.WaitConverting(4000);
				document.all.HWPostil1.AfterConvert();
			}
		}
        //批量制作文书
      function batchMakePage(){
        	var rows = grid.getSelecteds();

        	if(rows.length > 0){
    	   		//建议书批量提交的时候添加一个遮罩层 防止重复提交
    	   	var messageid = mini.loading("批量保存中 请稍后 ...", "处理中");
        		if(1 == rows.length){
        			savewenshu();
        		}else{
        			
        			//采集flowdraftid
    	    		var flowdraftidArr = [];
    	            for (var i = 0, l = rows.length; i < l; i++){
    	               var row = rows[i];
    	               flowdraftidArr.push(row.flowdraftid);
    	            }
    				var flowdraftids = flowdraftidArr.join(',');
    				var tempid = mini.get('tempid').getValue();
    				//过滤掉已经作好文书的flowdraftid
    				//var url = "<%=path%>/filterFlowdraftidOfMakedPage.json?1=1";
    				//$.ajax({
    		         //   url: url,
    		          //  data:{flowdraftids:flowdraftids,tempid:tempid},
    		           // type: 'POST',
    		            //async:false,
    		            //dataType: "text",
    		            //success: function (text){
    		            //	alert(flowdraftids);
    		           // },
    		           // error: function (jqXHR, textStatus, errorThrown){
    		            //}
    		        //});
    			   	flowdraftidArr = flowdraftids.split(",");
    			   	var tempid = mini.get('tempid').getValue();
    			   	var flowdefid = mini.get('flowdefid').getValue();
    			   	if(flowdraftids){
    			   		for(var i=0; i<flowdraftidArr.length; i++){
    				   		var flowdraftid = flowdraftidArr[i];
    				   		singleMakePage(tempid,flowdraftid,flowdefid);
    				   	}
    			   		alert("操作成功！");
    			   		grid.reload();
    			   	}else{
    			   		alert("所选案件先前已制作好！");
    			   	}
        			
        		}
    	        mini.hideMessageBox(messageid);
        	}else{
        		alert("请至少选中一条记录！");
        		return false;
        	}
        }
        
        
        function singleMakePage(tempid,flowdraftid,flowdefid){
        	var bigText = getBigText(tempid, null, flowdraftid, flowdefid);
        	makeWenShu(bigText,flowdraftid);
        }
        
        
        
        	//预览打印
         function viewbaobiao(){
	         	
	         	var tempid = mini.get('tempid').getValue() + 'report';
        		var flowdraftid = mini.get('flowdraftid').getValue();
        		var flowdefid = mini.get('flowdefid').getValue();
        		var applyid = mini.get('crimid').getValue();
        		
	         	//检查是否存了模板，没存模板无法预览
	        		$.ajax({
	                  url: "checkSingleSuggestDocumentData.action?1=1",
	                  type: "post",
	                  async: false,
	                  data:{flowdraftid:flowdraftid, tempid:tempid, applyid:applyid},
	                  success: function (text) {
	                  	returnValue = eval(text);
	                  }
	 	         });
	 	         if(returnValue =='-1'){
	 	         	 alert("请先保存文书！");
	 	         	 return;
	 	         }else if(returnValue != '1'){
	 	         	 alert("操作失败！");
	 	         	 return;
	 	         }
        		
        		if(flowdraftid){
        			//var myurl= "getReducePenaltyByAnJianHao.action?1=1&menuid="+resid+"&otherid="+otherid;
     		      var url = "<%=path%>/baseDocOperate.page?1=1&tempid="+tempid+"&flowdraftid="+flowdraftid+"&flowdefid="+flowdefid;
     		      var win=mini.open({
     	                url: url,
     		            showMaxButton: true,
     		            //contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
     		            //data: { tempid:tempid,flowdraftid:flowdraftid},
     		            allowResize: true, 
     		            title: "预览"
     	             });
                     win.max();
        		}
		   }
        	
        //名称查询
		function onNameFilterChanged(e) {
	         var textbox = e.sender;
	         var key = textbox.getValue().toLowerCase();
	         grid.filter(function (row) {
	             var notekey = String(row.name).toLowerCase();
	             if (notekey.indexOf(key) != -1) return true;
	             return false;
	         });
	     }		
          
        
        function onselectChanged(){//案件字号改变时自动查询,刑期改变时自动查询
        	var color=mini.get("color").getValue();
        	var font=mini.get("font").getValue();
        	document.getElementById("annexcontent").className="mini-textarea";
        	mini.get("annexcontent").setCls(color);
        	mini.get("annexcontent").setCls(font);
         } 
         
        function onKeyEnter(e) {
            search();
        }
        
        function search() {
         	//var jailid = mini.get("jailid").getValue();//执行单位
         var casenums = mini.get("casenums").getValue();//案件范围
         var curyear = mini.get("curyear").getValue();//年度
         var jailcasechg = mini.get("jailcasechg").getValue();//年度
         var casetype = mini.get("casetype").getText();
             
         grid.load({year:curyear,casenums:casenums, jailcasechg:jailcasechg,casetype:casetype});
        }
        
        
        
        
        
      ////////////////////////////////////////////////////////////////////////////////////////////////////////
        
      //新增文书
        function insertwenshu(){
        	/*
      		$.ajax({
        		url: "generateDocText.action?id="+tmid+"&zuheid="+zuheid+"&zhujianstr="+zuheid+"&wsparm=YES",
                 type: "post",
                 success: function (text) {
                   var o = mini.decode(text);
                   if(o.annexcontent){
	                 	mini.get("annexcontent").setValue(o.annexcontent);
	                 	xinzengdoc();
                 	}
             	 }
             });
             */
         var row = grid.getSelected();
             var tempid = mini.get("tempid").getValue();
             var flowdraftid = mini.get("flowdraftid").getValue();
             var flowdefid = mini.get("flowdefid").getValue();
             if(!flowdraftid){
             	alert("请先选择案件！");
             	return;
             }
             scnwb(tempid, flowdraftid, flowdefid,row.crimid)
             
        }   
        
      
      	function xinzengdoc(){
        	//开庭笔录
        	var url = "saveCourtModelContent.action?1=1&qtype="+qtype+"&id="+zuheid+"&reportid=BB"+reportid+"&stype=annex&operatetype=insert";       	
        	var form1 = new mini.Form("editForm1");
           	var data = form1.getData();
            var json = mini.encode([data]);
            form1.loading("保存中，请稍后......");
            $.ajax({
                url: url,
                data: { data: json},
                type: "post",
                success: function (text) {
                	form1.unmask();
                	if(text == "success"){
                		//alert("保存成功!");
                		//grid2.reload();
                	}else{
                		alert("保存失败！");
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
        }
        
        //打印当前
      function dayin(){
          	savewenshu(true);
        }
        
      	//预览批量打印
        function yulandayin(){
        	var courttype = mini.get("courttype").getValue();
        	var resid = mini.get("resid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var win = mini.open({
                url: "printCourtPage.action?1=1&courttype="+courttype,
                title: "笔录打印页面", width: 600, height: 360,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new",resid:resid, courttype:courttype, flowdefid:flowdefid, tempid:tempid};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    //grid.reload();
                }
            });
            win.max();
        }
      	
      	
        function modify(){
        	var tempid = mini.get("tempid").getValue();
       		var win = mini.open({
                    url: "<%=path%>/toMeetingModel.action?1=1",
                    title: "模板编辑", width: 800, height: 500,
                    showMaxButton: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {action: "edit",tempid:tempid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                    }
             });
             win.max();
             
        }
        
        function savePreDoc(){//查看之前保存前一个文书，如果有的话
        	var flowdraftid = mini.get("flowdraftid").getValue();
        	if(flowdraftid){
        		savewenshu();
        	}
        }
        
        function edit(){
        	//savePreDoc();//保存前一个笔录
        	onselectChanged();
        	var row = grid2.getSelected();
        	if(row){
        		var flowdraftid = row.flowdraftid;
        		var crimid = row.crimid;
        		var otherid = row.otherid;
	        	var flowdefid = mini.get("flowdefid").getValue();
	        	var tempid = mini.get("tempid").getValue();
        		makeTextPage(tempid, crimid, flowdraftid, flowdefid, otherid);
        	}
        }
        
        
        function scnwb(tempid, flowdraftid, flowdefid,crimid){
       		$.ajax({
	                 url: "getNewCourtPageDocumentData.action",
	                 type: "post",
	                 data:{tempid:tempid, flowdraftid:flowdraftid, flowdefid:flowdefid,crimid:crimid},
	                 success: function (text){
	                 	if(text){
	                 		var o = mini.decode(text);
                     		form.setData(o);
                     		mini.get("flowdraftid").setValue(flowdraftid);
                     		mini.get("otherid").setValue("");
      		 				savewenshu();
	                 	}
	                 }
	         });
        }

    </script>
    
	</body>
</html>