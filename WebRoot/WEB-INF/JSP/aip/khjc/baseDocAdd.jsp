<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String topsearchkey = "";
	Object topsearchkeyObj = request.getAttribute("topsearchkey");
	if(null != topsearchkeyObj){
		topsearchkey = topsearchkeyObj.toString();
	}
	
	String flag = (String) request.getAttribute("flag");
	String flowdefid = (String) request.getAttribute("flowdefid");
	String realPath1 = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body onload="onLoad();">
	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
	<input id="username" name="username" class="mini-hidden" value="${username}"/>
	<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	<input id="currnodeid" name="currnodeid" class="mini-hidden" value="${currnodeid}"/>
	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
	<input id="applyid" name="applyid" class="mini-hidden" value="${applyid}"/>
	<input id="solutionid" name="solutionid" class="mini-hidden" value="${solutionid}"/>
	<input id="engine"  class="mini-hidden" value="${engine}"/>
	<input id="temp1" name="temp1" class="mini-hidden" />
	<input id="temp2" name="temp2" class="mini-hidden" />
	<input id="objid" name="objid" class="mini-hidden" value="${objid}"/>
	<input id="noeditnode" name="noeditnode" class="mini-hidden" value="${noeditnode}"/>
	
	<!-- 报表所用参数 -->
	<input id="flag" name="flag" class="mini-hidden" value="${flag}"/>
	<input id="reportdata" name="reportdata" class="mini-hidden" value="${reportdata}"/>
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
			<a class="mini-button"  style="" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span>
			${topsearch}
	    	${topstr}
	    	<span id="flowinfo" style="padding-left:5px;color:#FF0000;"></span>    	
	    	<a class="mini-button"  iconCls="icon-close" plain="true" onclick="Close();">关闭</a>
	    	<span class="separator"></span>
	    	  
	    	<a class="mini-button"  style="" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<!--
	    	<a class="mini-button"  style="" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="sealOperate();"></a>&nbsp;
	    	-->
	    	<!--  
	    	<a class="mini-button"  style="" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	-->
	    	<a class="mini-button"  style="" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="612500" iconCls="icon-gk_search" tooltip="检索" plain="true" onclick="doMenueButton(17);"></a>
	    	<a class="mini-button"  style="" id="932725" iconCls="icon-reload" tooltip="刷新" plain="true" onclick="refreshPage();"></a>
	    	<a class="mini-button"  style="" id="1014156" iconCls="icon-gk_camera" tooltip="高拍仪" plain="true" onclick="doMenueButton(20);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="1014157" iconCls="icon-gk_file" tooltip="文件" plain="true" onclick="doMenueButton(21);"></a>
	    	<a class="mini-button"  style="display:none" id="report" iconCls="icon-node" plain="true" onclick="yulanexport();">导出预览</a>
	    	
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
  
  <div id="form2" align="left" style="padding-left: 100px;">
  </div>
	
  	<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
	<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
	<script type="text/javascript">document.all("MyViewer").Init(window, document,600);</script>
    <script type="text/javascript">
        mini.parse();
        mini_useShims=true;
        var path = '<%=path%>';
        
        function showOrHide(flag){
        	var myform = new mini.Form("form2");
        	if(1==flag){
        		$("#form1").hide();//HWPostil1
        		$("#MyViewer").hide();
        		
        		//var myform = new mini.Form("form2");
        		myform.loading("加载中...");
        	}else if(0==flag){
        		myform.unmask();
        		$("#form2").hide();
        		
        		$("#form1").show();
        		$("#MyViewer").show();
        	}
        }
        
        //重写签章　　doMenueButton(5);
        function sealOperate(){
        	var tempid = mini.get('tempid').getValue();
        	var flowdefid = mini.get('flowdefid').getValue();
        	var url = "<%=path%>/sign/getUserSignatureScheme.json?1=1";
        	var signatureScheme = null;
			$.ajax({
	            url: url,
	            data: {tempid:tempid, flowdefid:flowdefid},
	            type: 'POST',
	            async:false,
	            success: function (text){
	            	if(text){
	            		signatureScheme = mini.decode(text);
	            	}
	            },
	            error: function (jqXHR, textStatus, errorThrown){
	            }
	        });
	        var protectnode = null;
	        if(signatureScheme){
	        	if(signatureScheme.length == 1){
	        		protectnode = signatureScheme[0].protectnode;
		        }else if(signatureScheme.length > 1){
		        	var data = [];
		        	for(var i=0; i<signatureScheme.length ; i++){
		        		var map = {};
		        		map["text"] = signatureScheme[i].name;
		        		map["value"] = signatureScheme[i].protectnode;
		        		data.push(map);
		        	}
		        	signatureScheme = mini.encode(data);
		        	var vRet = selectSignatureScheme(signatureScheme);
		        	if(vRet && vRet[0]==1){
						protectnode = vRet[1];
					}else if(vRet && vRet[0]==0){
						return;
					}
		        }
	        }
	        doMenueButton(5);
	        if(protectnode){
	        	var aipObj = document.getElementById("HWPostil1");
	        	signatureProtect(aipObj,protectnode);
	        }
        }
        
        function selectSignatureScheme(signatureScheme){
        	var url = "<%=path%>/sign/toSelectSignatureSchemPage.page?1=1&signatureScheme="+ encodeURI(encodeURI(signatureScheme));
			var vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:180px;dialogWidth:320px");
			return vRet;
        }
        
        function signatureProtect(aipObj, protectnode){
			//数字签名保护
			if(protectnode){
				// 将abc,cde,efg解析成 : <+abc-><+cde-><+efg->
				var protectnodeStr = parseProtectnodeStr(protectnode);
				//    "<+sjzsuggest3-><+sjzsuggest3->"
				var signatureFlag = aipObj.SetValue("SET_BINDLIST_FORSEAL", protectnodeStr);
				if(signatureFlag <=0){
					alert("电子签名失败！");
				}
			}
	   }
	   
	   //数字签名保护的节点解析:
	   //		将abc,cde,efg解析成 : <+abc-><+cde-><+efg->
	   function parseProtectnodeStr(protectnode){
	   		if(protectnode){
	   			var protectnodeArr = protectnode.split(",");
	   			var protectnodeStr = "";
	   			for(var i=0, l = protectnodeArr.length; i<l ; i++ ){
	   				protectnodeStr += '<+' + protectnodeArr[i] + '->'
	   			}
	   			return protectnodeStr;
	   		}
	   }
   
   
        
        
        function yulanexport(){
        	//var reportdata = mini.get("reportdata").getValue();
        	var win=mini.open({
                url: "<%=path%>/toYulanExportPage.action",
	            showMaxButton: true,
	            contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
	            allowResize: true, 
	            title: "预览"
            });  
            win.max();
        }
        
        function onLoad(){
        	var flag = mini.get('flag').getValue();
        	if(flag && flag == 'report'){
        		var reportdata = mini.get('reportdata').getValue();
        		loadReporter(reportdata);
        	}else if(flag && flag == 'pdfFile'){
	        	var object = document.getElementById("HWPostil1");
	        	var pdfpath = '<%=realPath1%>/28c8edde3d61a0411511d3b1866f0636/'+"${pdfpath}";
	        	object.LoadFile(pdfpath);//打开服务器端某盘符下的文件 
        	}
		}
        
        function loadReporter(reportdata){
			if(reportdata){
				$("#report").show().css("display","inline-block");
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
		
        
        
        function operateMasterDataTwo(operateType,bizName){
        	//如果是提交、退回、拒绝时，要判断签章进程
        	if('yes'==operateType||'back'==operateType||'no'==operateType){
        		//提示操作
        		var chsOperateType = getOperateType(operateType);
        		var confirmInfo = '确定要'+chsOperateType+'吗？';
        		var confFlag = confirm(confirmInfo);
        		if(false == confFlag){
        			return;
        		}
        		
        		buttonLock();
        		//判断某些表单是否已制作
        		var flag = isPapersMaked(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		
        		//判断
        		var flag = controlOperateBehavior(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		
        		//判断退回删除当前插入章对应在表单上对应章
        		if('back'==operateType){
        			//deleteSeal(); 这边有问题，章删不掉，死循环，暂时关闭。
        		}
        	}
        	
      		mini.open({
   			   url: "multiPromptClick.json?1=1",
   		       title: "请输入拒绝/退回意见", width: 600, height: 360,
   		       async:false,
   		       onload: function () {
   		           var iframe = this.getIFrameEl();
   		           var data1 = {action: "new"};
   		           iframe.contentWindow.SetData(data1);
   		       },
   		       ondestroy: function (action) {
   		    	   var iframe = this.getIFrameEl();
   		    	   var data1 = iframe.contentWindow.GetData();
   	               data1 = mini.clone(data1);
   	               //通过全局变量 获取拒绝或退回的理由
   	               backnoreason = data1.reason;
   	               var flag = savedata('master',operateType,bizName);
   	        	   if(false == flag) {
   	        		   buttonUnlock();
   	        		   return flag;
   	        	   }
   	        	
   	        	   if(operateType=='yes'||operateType=='no'||operateType=='back'){
   	        	    	toNext(1);
   	        	   }
   			  }
   		   });        		
        	
        }
        
        
        function operateMasterData(operateType,bizName){
        	//如果是提交、退回、拒绝时，要判断签章进程
        	if('yes'==operateType||'back'==operateType||'no'==operateType){
        		//提示操作
        		var chsOperateType = getOperateType(operateType);
        		//var confirmInfo = '确定要'+chsOperateType+'吗？';
        		var confirmInfo = chsOperateType;
        		
        		var confFlag = confirm(confirmInfo);
        		if(false == confFlag){
        			return;
        		}
        		
        		buttonLock();
        		//判断某些表单是否已制作
        		var flag = isPapersMaked(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		
        		//判断
        		var flag = controlOperateBehavior(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		
        		//判断退回删除当前插入章对应在表单上对应章
        		if('back'==operateType){
        			//deleteSeal(); 这边有问题，章删不掉，死循环，暂时关闭。
        		}
        	}
      	   var flag = savedata('master',operateType,bizName);
       	   if(false == flag) {
       		   buttonUnlock();
       		   return flag;
       	   }
       	
       	   if(operateType=='yes'||operateType=='no'||operateType=='back'){
       	    	toNext(1);
       	   }
        }
        //oa办理流程，关闭
         function oaMasterData(operateType,bizName){
        	//如果是提交、退回、拒绝时，要判断签章进程
        	if('yes'==operateType||'back'==operateType||'no'==operateType){
        		//提示操作
        		var chsOperateType = getOperateType(operateType);
        		var confirmInfo = '确定要'+chsOperateType+'吗？';
        		var confFlag = confirm(confirmInfo);
        		if(false == confFlag){
        			return;
        		}
        		
        		buttonLock();
        		//判断某些表单是否已制作
        		var flag = isPapersMaked(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		
        		//判断
        		var flag = controlOperateBehavior(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		
        		//判断退回删除当前插入章对应在表单上对应章
        		if('back'==operateType){
        			//deleteSeal(); 这边有问题，章删不掉，死循环，暂时关闭。
        		}
        	}
        	
        	
      	   var flag = savedata('master',operateType,bizName);
       	   if(false == flag) {
       		   buttonUnlock();
       		   return flag;
       	   }
       	
       	   if(operateType=='yes'||operateType=='no'||operateType=='back'){
       		Close();
       	   }
        }
        
        function freeFlowOperate(operateType, dnodeid, bizName){
        	//如果是提交、退回、拒绝时，要判断签章进程/某些表单是否已制作
        	if('yes'==operateType||'back'==operateType||'no'==operateType){
        		//提示操作
        		var chsOperateType = getOperateType(operateType);
        		//var confirmInfo = '确定要'+chsOperateType+'吗？';
        		var confirmInfo = chsOperateType;
        		var confFlag = confirm(confirmInfo);
        		if(false == confFlag){
        			return;
        		}
        		
        		buttonLock();
        		//判断某些表单是否已制作
        		var flag = isPapersMaked(operateType);
        		if(false == flag){
        			buttonUnlock();
        			return;
        		}
        		//判断
        		//var flag = controlOperateBehavior(operateType);
        		//if(false == flag){
        		//	return;
        		//}
        	}
        	var flag = savedata('master',operateType,bizName, dnodeid);
        	if(false == flag) {
        		buttonUnlock();
        		return flag;
        	}
        	
        	if(operateType=='yes'||operateType=='no'||operateType=='back'){
        		toNext(1);
        	}
        	
        }
        
        function operateSlaveData(operateType,bizName){
        	savedata('slave','save',bizName);
        }
        
         //拒绝退回 不加意见的函数
        function operateData(operateType,bizName){
        	 operateMasterData(operateType,bizName);
         }
         
         //拒绝退回填写拒绝退回意见的函数
        function operateDataTwo(operateType,bizName){
        	 operateMasterDataTwo(operateType,bizName);
         }
        
        function cancelCase(){
        	alert("案件撤销！一会再做");
        }
        
        //控制
      function controlOperateBehavior(operateType){
        	var flag = false;
        	
        	var flowdraftid = mini.get('flowdraftid').getValue();
        	var flowdefid = mini.get('flowdefid').getValue();
        	var tempid = mini.get('tempid').getValue();
        	var currnodeid = mini.get('currnodeid').getValue();
        	
        	flag = controlSealProcedure(operateType, flowdraftid, flowdefid, tempid, currnodeid);
			
        	if(flag == false){
        		return flag;
        	}
        	flag = controlIsExistFormData(operateType, flowdraftid, flowdefid, tempid);
        	return flag;
        	
        }
        
        //判断文书是否已制作
        function isPapersMaked(operateType){
        	var flag = false;
        	var flowdraftid = mini.get('flowdraftid').getValue();
        	var flowdefid = mini.get('flowdefid').getValue();
        	if(window.parent.mini.get('flowMenuid')){
        		var flowMenuid = window.parent.mini.get('flowMenuid').getValue();
        		flag = isThePapersMaked(flowMenuid, operateType,flowdraftid,flowdefid); 
        	}else{
        		flag = true;
        	}
        	return flag;
        }
        
        //如果是提交、退回、拒绝时，要判断签章进程
      function controlSealProcedure(operateType, flowdraftid, flowdefid, tempid, currnodeid){
			var flag = true;
        	if(operateType=='save'){
        		return flag;
        	}
        	//
        	var aipObj = document.getElementById("HWPostil1");
        	//var notation = aipObj.GetNoteNum(1);//手写批注的个数
			var sealnum = aipObj.GetNoteNum(251);//电子签章的个数
        	var url = "<%=path%>/sign/singleCheckSeal.json?1=1";
			$.ajax({
	            url: url,
	            data: {operateType:operateType, flowdraftid:flowdraftid, flowdefid:flowdefid, tempid:tempid,
	            		  sealnum:sealnum, currnodeid:currnodeid},
	            type: 'POST',
	            async:false,
	            success: function (text){
	            	text = mini.decode(text);
                	if(text.errorcode == "2"){
                    	alert(text.info);
						flag = false;
                    }
	            },
	            error: function (jqXHR, textStatus, errorThrown){
	            	
	            }
	        });
			return flag;
        }
        
        //控制如果某些大字段不存在，不能提交
      function controlIsExistFormData(operateType, flowdraftid, flowdefid, tempid){
        	return true;
        	
        	var url = '';
			$.ajax({
	            url: url,
	            data: {operateType:operateType, flowdraftid:flowdraftid, flowdefid:flowdefid, tempid:tempid },
	            type: 'POST',
	            async:false,
	            success: function (text){
	            		
	            },
	            error: function (jqXHR, textStatus, errorThrown){
	             	   
	            }
	        });
        }
        
        function onValueChanged(e) {
        	search();
        };
        
        var topsearchkey = '<%=topsearchkey%>';
        function search(){
        	//var topsearchkey = '${topsearchkey}';
        	var topsearchkeyArr = [];
        	if(topsearchkey){
        		topsearchkeyArr = topsearchkey.split(',');
        	}
        	
        	var url = window.location.href;
        	var start = url.indexOf('?');
        	var param = url.substring(start+1);
        	url = url.substring(0,start);
        	var paramArr = param.split('&');
        	var paramMap = {};
        	var key = '';
        	var value = '';
        	var keyValue = '';
        	for(var i=0,l=paramArr.length; i<l ; i++){
        		keyValue = paramArr[i].split('=');
        		if(keyValue.length==2){
        			var key = keyValue[0];
        			var value = keyValue[1];
        			paramMap[key] = value;
        		} 
        	}
        	for(var i=0,l=topsearchkeyArr.length; i<l ; i++){
        		key = topsearchkeyArr[i]
        		value = mini.get(key).getValue();
        		if(value){
        			paramMap[key] = encodeURI( value );
            	}
        	}
        	
        	var paramstr = '';
        	for(var skey in paramMap){
        		paramstr += '&'+skey+'='+ paramMap[skey];
        	}
        	//判断是否以&开头
        	var andCharIndex = paramstr.indexOf('&');
        	if(andCharIndex == 0){
        		paramstr = paramstr.substring(1);
        	}
        	url += '?' + paramstr;
            window.location.href = url;
        };
        
        function SetData(data){
        	data = mini.clone(data);
        	var applyids = data['applyids'];
        	if(applyids){
        		mini.get('temp1').setValue(applyids);
        	}
        	var flowdraftids = data['flowdraftids'];
        	if(flowdraftids){
        		mini.get('temp2').setValue(flowdraftids);
        	}
        	//
        	var ajaxLoad = data.ajaxLoad;
        	if(ajaxLoad){
        		HWPostil1_onload();
        		if(1==ajaxLoad){//ajax加载表单
        			ajaxLoadForm(data);
        		}else if(2==ajaxLoad){//ajax加载报表
        			showOrHide(1);//显示遮罩
        			ajaxLoadReporter(data);
        			showOrHide(0);//去掉遮罩
        		}
        	}
        }
        
        
		function ajaxLoadForm(data){
			var url = "<%=path%>/ajaxGetFormData.json?1=1";
        	//{ menuid:menuid, flowdraftid:flowdraftid, tempid:tempid, flowdefid:flowdefid, applyid:crimid}
        	$.ajax({
	             url: url,
	             data: data,
	             contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	             cache: false,
	             dataType: 'text',
	             type: "post",
	             async: false,
	             success: function (text){
	            	 var dataMap = mini.decode(text);
	            	 
	            	 var formDatajson = null;
	            	 if(dataMap.formDatajson){
	            		 formDatajson = replaceAllStr(dataMap.formDatajson,"\\\\n","\\n");
	            	 }
	            	 loadFormData(dataMap.docconent, formDatajson, dataMap.selectDatajson);
	             },
	             error: function (jqXHR, textStatus, errorThrown) {
	            	 alert("加载失败!");
	             }
	         });
        }
        
        function ajaxLoadReporter(data){
        	var url = "<%=path%>/ajaxGetReportData.json?1=1";
        	//{ menuid:menuid, flowdraftid:flowdraftid, tempid:tempid, flowdefid:flowdefid, applyid:crimid}
        	$.ajax({
	             url: url,
	             data: data,
	             contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	             cache: false,
	             dataType: 'text',
	             type: "post",
	             async: false,
	             success: function (text){
	            	 loadReporter(text);//虚拟打印出来
	             },
	             error: function (jqXHR, textStatus, errorThrown) {
	            	 alert("加载失败!");
	             }
	         });
        }
        
        
        // 刷新本页面
		function refreshPage(){
			location.reload();
		};
        
		
	      function updatedata(){
	    	  var content = saveFile();
	    	  var flowdraftid = mini.get("flowdraftid").getValue();
	    	  var tempid = mini.get("tempid").getValue();
	     		var url = "<%=path%>/updatedaziduan.json?1=1";
	             $.ajax({
	                 url: encodeURI(encodeURI(url)),
	                 data: {content:content,flowdraftid:flowdraftid,tempid:tempid},
	                 type: "post",
	                 success: function (text) {
	                 	alert("操作成功!");
	                 },
	                 error: function (jqXHR, textStatus, errorThrown) {
	                 	alert("操作失败!");
	                 }
	             });
	           
	        } 
		
		//下一个
		function toNext(isCommit){
			if(window.parent){
				window.parent.next(isCommit);
			}else{
				goBack();
			}
		}
		
		//上一个
	    function toPrevious(){
	    	if(window.parent){
	    		window.parent.previous();
	    	}else{
	    		goBack();
	    	}
	    }
	      
		
		
		function operateYgrzMasterData(operateType,bizName){//员工入职等级表单添加校验
        	
	          var aipObj = getmyvalue("HWPostil1");
	    	  var employeeid = aipObj.GetValueEx("employeeid",2,"",0,"");//员工编号
	    	    	
	    	  if(! employeeid){
	    	   	alert("员工编号不能为空！");
	    	    		return;
	    	   }else{
	    		   operateMasterData(operateType,bizName);
	    		   
	    	   }
	            	
			}
    </script>
    <script src="<%=path %>/scripts/publicjs.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/batchOperate.js" type="text/javascript"></script>
</body>
</html>
