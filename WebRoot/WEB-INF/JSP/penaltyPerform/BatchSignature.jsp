<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,com.sinog2c.model.system.SignatureScheme" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
List<SignatureScheme> signatureSchemes = (List<SignatureScheme>)request.getAttribute("signatureSchemes");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>批量签章1</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <script language="JavaScript" src="<%=path%>/js/Calendar.js"></script>
     <style type="text/css">
    body{
       margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
	<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
  </head>
  <script language="JavaScript" for="HWPostil1" event="NotifyCtrlReady">
	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.all.HWPostil1.JSEnv = 1;
	</script>
 <body>
<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border: 0px;">
	<div size="30px;" showCollapseButton="false" style="border-top: 0px;border-left: 0px;border-right: 0px;">
	    <div class="mini-toolbar" style="padding:2px;border-bottom:0;">
		    <input type="hidden"  id="tempid" name="tempid" value=''/>
			<input type="hidden"  id="scheme" name="scheme" value='' />
			<input type="hidden"  id="content" name="content" value='' />
			<input type="hidden" id="remark" name="remark" value='' />
			<input type="hidden" id="progress" name="progress" value='' />
			<input type="hidden" id="picizhivalue" name="picizhivalue" value='' />
			<input type="hidden" id="hiddenmessage" name="hiddenmessage" value='' />
			<input id="casetype" name="casetype" value="<s:property value='#request.casetype'/>" class="mini-hidden"/> 
	    	<a class="mini-button" id="dakai" style="display:none;" iconCls="icon-lock" plain="true" onclick="dakai()">工具栏</a> 
			&nbsp;案件号：（<input style="width:28px" value="${caseyear}" required="true" id="caseyear" name="caseyear"/>）
			<input  value="${casedepsimplename}"  id="casedepsimplename" style="width:28px" name="casedepsimplename"/>
			<select id="casetype2" name="casetype2">
      		<option value="jxjs">减字</option>
			</select>
			第
			<input class="mini-textbox"  style="width:160px;" emptyText="案件号：1,2或3-5或1,2,3-5" name="fanwei" id="fanwei"  vtype="maxLength:100"/>
			号
<!--			<input class="mini-checkbox" id="pici"  style="display:none;" name="pici" onvaluechanged="genggaipici()"/>当前批次-->
			&nbsp;签章日期：
			<input type="text" name="sealdate" id="sealdate" readonly="readonly" value="${sealdate}" style="width:70px"/>
			&nbsp;
			<select id="fangan" name="fangan">
			<%
			if(signatureSchemes!=null && signatureSchemes.size()>0){
				for (int i=0;i<signatureSchemes.size();i++) {
				    SignatureScheme signatureScheme = signatureSchemes.get(i);
      		%>
      		<option value="<%=signatureScheme.getSignid()%>"><%=signatureScheme.getName()%></option>
      		<%
					}
				}
			%>
			</select>
			&nbsp;<a class="mini-button" plain="true"  onclick="huoquqianzhangfangan(0);">签章</a>
			&nbsp;<a class="mini-button" plain="true"  onclick="batchexport();">批量导出</a>
			&nbsp;<a class="mini-button" plain="true"  onclick="huoquqianzhangfangan(1);">撤销签章</a>
		</div>
		
	</div>
	<div showCollapseButton="false">
	        <div style="width:100%;height:20px;text-align: center;font-size: 14px;font-family: 微软雅黑;">
                 <span id="savePdf"></span>
            </div>
			<div id="showContent" style="height:30px;overflow:auto;display:none;background:#EFF2F5;color:#3789B8;text-align:center;"></div> 
	         <script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
    </div>       
 </div>
 <jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
 <div id="editWindow" class="mini-window" title="选择导出类型" style="width:300px;height:150px"
		    showModal="true" allowResize="true" allowDrag="true">
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="towBatchExport()" plain="true"
									style="width: 60px">确认</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true"
									style="width: 60px;">取消</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 11px; padding-bottom: 5px;">
					<table style="table-layout: fixed;">
						<tr>
							<td style="width: 90px;">
								导出类型
							</td>
							<td style="width: 90px;">
								<input id="schemetype"  name="schemetype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                        		 style="width:120px" emptyText="请选择文书类别" data="writType"/>
							</td>
						</tr>
					</table>
				</div>
		</div>
 <SCRIPT type="text/javascript">
    var writType = [{id:'JXJS_JXJSSHB',text:'减刑假释审核表'},{id:'suggestreport',text:'减刑假释建议书'},{id:'casecheckreport',text:'监督意见书'}];
	mini.parse();
 	var obj2=document.getElementById("HWPostil1");
 	obj2.ShowToolBar = 0;//工具栏是否显示
  	obj2.ShowDefMenu = 0;//菜单栏
  	obj2.ShowScrollBarButton = 1;
	var nowindex=-1;
	/**
	function genggaipici() {
		 var pici = mini.get("pici");
		 if(pici.getChecked()){
		 	mini.get("fanwei").setValue("");
		 	mini.get("fanwei").setReadOnly(true);   
		}
		else{
			mini.get("fanwei").setReadOnly(false);  
		}
	}
	/**
	*浏览器暂停并强制输出流内容
	*/
	function pause(numberMillis) {
	        var dialogScript = 
	           'window.setTimeout(' +
	           ' function () { window.close(); }, ' + numberMillis + ');';
	        var result = 
	// For IE5.
	         window.showModalDialog(
	           'javascript:document.writeln(' +
	            '"<script>' + dialogScript + '<' + '/script>")'); 
	     }
	//查询签章方案信息
  function huoquqianzhangfangan(delFlag){
  	nowindex = -1;
  	var fangan=document.getElementById("fangan").value;
  	var hiddenmessage=document.getElementById('hiddenmessage');
  	var obj=document.getElementById("HWPostil1");
  	if(fangan!=""){
  		if(aipLogin(obj)==0)
  		{
  			$.ajax({
	             url: "<%=path%>/sign/ajax/select.json?1=1",
	             data: {signatureid:fangan},
	             type: "post",
	             success: function (text) {
	             	var objs = eval('('+text+')');
	             	document.getElementById('tempid').value=objs.tempid;
	             	document.getElementById('scheme').value=objs.scheme;
	             	document.getElementById('content').value=objs.content;
	             	document.getElementById('remark').value=objs.remark;
	             	document.getElementById('progress').value=objs.progress;
	             	//获取签章方案成功后进行批量签章
	             	mutilSignature(objs.tempid,objs.scheme,objs.content,objs.remark,objs.progress,delFlag)
	             },
	             error: function (jqXHR, textStatus, errorThrown) {
	            	 alert("操作失败!");
	             }
	         });
  		}else{
  			alert("电子签章未连接！");
  		}
  	}else{
  		alert("请选择签章方案");
  	}
  }
  //批量签章按钮事件
  function mutilSignature(tempid,scheme,content,remark,progress,delFlag)
  {
    var form = new mini.Form("form1");
	//var pici = mini.get("pici");
	var picidayin='no';
	//if(pici && pici.getChecked()){
	// 	picidayin='yes';
	//}
	var o = form.getData(); 
	var json = mini.encode([o]);
	var fangan=document.getElementById('fangan').value;
	var fanwei = mini.get("fanwei").getValue();
	if (fanwei == ""){
		alert("请输入案件号范围(示例：1-9 或者1,6,9)或者选择当前批次！");
		return;
	}
	form.validate();
	if (form.isValid() == false) return;
	if(fangan!=""){
		$.ajax({
             url: "<%=path%>/penaltyPerform/getSignatureSchemeList.json?1=1",
             data: {fanwei:fanwei},
             type: "post",
             success: function (text) {
             	document.getElementById('picizhivalue').value=eval(text);
             	 getNextValue();
	         	 huoquneirong(delFlag);
             },
             error: function (jqXHR, textStatus, errorThrown) {
            	 alert("操作失败!");
             }
         });
	}else{
		alert("请选择签章方案");
	}
  }
  function getNextValue(){
        var picidayin='no';
		var zhaodao="no";
		var shifoujieshu="no";
		if(nowindex==-1){
			zhaodao="yes";
		}
		var picizhivalue=document.getElementById('picizhivalue').value;
		var piciarr=picizhivalue.split(',');
		for(var m=0;m<piciarr.length;m++){
			if(zhaodao=='yes'){ 
				if(nowindex!=piciarr[m]){
					nowindex=piciarr[m];
					shifoujieshu="yes";
					break;
				}
			}else if(nowindex==parseInt(piciarr[m])){
				zhaodao='yes';
			}
		}
		if(shifoujieshu=="no"){
			nowindex=-99;
		}
  }
  //获取文书内容
  function huoquneirong(delFlag){
    var progress=document.getElementById('progress').value;
    var tempid=document.getElementById('tempid').value;
  	var caseyear=document.getElementById("caseyear").value;
  	var casedepsimplename=document.getElementById("casedepsimplename").value;
  	var casetype= mini.get("casetype").value;
  	var aamesage=document.getElementById('hiddenmessage').value;
  	var showContent=document.getElementById('showContent');
  	var fanganname = "";//方案名称
  	var obj=document.getElementById("fangan");
    for(i=0;i<obj.length;i++) {//下拉框的长度就是他的选项数
		if(obj[i].selected==true) {
        	fanganname = obj[i].text;//获取方案名称
     	}
	}

  	if(nowindex!=-1&&nowindex!=-99)
  	{
  		$.ajax({
             url: "<%=path%>/penaltyPerform/handleAllSeal.json?1=1",
             data: { progress:progress,tempid:tempid,fanganname:fanganname,caseyear:caseyear,casedepsimplename:casedepsimplename,casetype:casetype,cpdnumber:nowindex,delFlag:delFlag},
             type: "post",
             success: function (text) {
             	var objs = eval('('+text+')');
             	jinxingqianzhang(objs.aipFileStr,objs.baobiaobiaodanString,objs.keyiqianzhangString,objs.title,tempid,delFlag);
             	showContent.innerHTML=objs.title;
             	showContent.style.display = "";
             },
             error: function (jqXHR, textStatus, errorThrown) {
            	 alert("操作失败!");
             }
         });
  	}
  }
  function jinxingqianzhang(aipFileStr,baobiaobiaodanString,keyiqianzhangString,title,tempid,delFlag){
  	  	var aipObj = document.getElementById("HWPostil1");
  	  	var caseyear=document.getElementById("caseyear").value;
  	  	var content = document.getElementById('content').value;
  	  	aipObj.CloseDoc(0);
		var dtrer=0;
		aipObj.Logout();
		dtrer = aipLogin(aipObj);
		if(dtrer==-200)
		{
			alert("未发现智能卡");
		}
		else if(dtrer!=0){
			alert("登录失败。错误ID:"+dtrer);
		}
		else{
		  	if(keyiqianzhangString=="yes"){
		  	  	if(baobiaobiaodanString=="biaodan"){
		  	  		aipObj.LoadFileBase64(aipFileStr);
		  		}else{
		  			document.all("MyViewer").ShowProgress = false;
					document.all("MyViewer").GetReportData(aipFileStr);
					aipObj.BeforeConvert("");
					document.all("MyViewer").ShowPrintDialog = false;
					document.all("MyViewer").PrintReport();
					document.all.HWPostil1.WaitConverting(5000);
					aipObj.AfterConvert();
		  		}
		  		if(aipObj.IsOpened()){
					aipObj.ForceSignType=7;
					aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
					if(delFlag==1){
						DeleteSeal(aipObj);
					}else{
						var strSignatureNoteInfo=document.getElementById('scheme').value;
						var strSignatureInfoArray = strSignatureNoteInfo.split("@");
						if(null!=strSignatureNoteInfo&&strSignatureNoteInfo!=''&&strSignatureInfoArray.length>0)
						{
						var date = document.getElementById("sealdate").value;
						if(date){
							date = date.replace(/\-/g,"");//把yyyy-MM-dd格式转换成yyyyMMdd
							}
							for(var i=0;i<strSignatureInfoArray.length;i++)
							{
								var content="";
								var sealdate=date;
								var cppoopiniondate="";
								var cppoopinion="";
								InsertNote(strSignatureInfoArray[i],aipObj,content,sealdate,cppoopiniondate,cppoopinion);
							}
						}
					}
					aipObj.SleepSecond(1);
					//保存签章文件
					var newaipFileStr=aipObj.GetCurrFileBase64();
					$.ajax({
			             url: "<%=path%>/penaltyPerform/ajaxsavepiliangqianzhang.json?1=1",
			             data: {aipFileStr:newaipFileStr,baobiaobiaodanString:baobiaobiaodanString,tempid:tempid,caseyear:caseyear,cpdnumber:nowindex,delFlag:delFlag},
			             type: 'POST',
			             success: function (text) {
			             	
			             },
			             error: function (jqXHR, textStatus, errorThrown) {
			                 alert(errorThrown);
			             }
			         });
				}
		  	}
		  	getNextValue();
			huoquneirong(delFlag);
		}
  }
	
	  //工具栏打开关闭方法	
	  var flag2 = true;
      function dakai(){
     		var da = mini.get("dakai");
     		obj2.HideMenuItem(16384);
      	if(flag2){
      		da.setIconCls("icon-unlock");
      		obj2.ShowToolBar = 1;//工具栏是否显示
      		flag2 = false;
      	}else{
      		da.setIconCls("icon-lock");
      		obj2.ShowToolBar = 0;//工具栏是否显示
      		flag2 = true;
      	}
      }

      function batchexport(){
           var url ="<%=path%>/gotoWritTypePage.action";
           vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
           //alert();
           startBatchExport(vRet[0]);
      }
      
	  var editWindow = mini.get("editWindow");
      //点击 批量导出 触发 函数( 暂时不适用)
      //function batchexport(){       
		//   editWindow.show();
      //}
      function towBatchExport(){
	      //var form = new mini.Form("#editform");
          var schemeType = mini.get("schemetype").getValue();
          //alert(schemeType);
          if(schemeType == ''){
               alert('请选择文件类型!');
               return;
          }
          editWindow.hide();
          startBatchExport(schemeType);
      }
      //批量导入 按钮事件
      function startBatchExport(writtype){
           //alert(writtype);
    	   var objAip=document.getElementById("HWPostil1");
           var fanwei = mini.get("fanwei").getValue();
           if(fanwei == ''){
               alert("请选择填写案件号!");
               return;
           }
           var casenum = "";
           var fans = fanwei.split(",");
           for(var i=0;i<fans.length;i++){
               var wei = fans[i];
               var weis = wei.split("-");
               if(weis.length != 1){
                   var start = weis[0];
                   var end = weis[weis.length-1];
                   for(var j=start;j<=end;j++){
                	   casenum += j+",";
                   }
               }else{
            	   casenum += weis+",";
               }
           }
           //去掉最后一个字符串 
           casenum = casenum.substr(0,casenum.length-1);
           var diskUrl = "C:\\PDF\\";
           var deleteDiskUrl = "C:\\PDF";
           //判断 文件夹是否存在 (exist, create folder)
           var fso = new ActiveXObject("Scripting.FileSystemObject");
           //创建folder之前 也要进行删除 操作 
           //fso.DeleteFolder(deleteDiskUrl);
           fso.CreateFolder(diskUrl);
           var object = [];
           var url = "<%=path%>/penaltyPerform/batchExportHandCaseFile.json?1=1&fanwei="+casenum+"&writtype="+writtype;
           $.ajax({
                url:url,
                type:'POST',
                async:false,
                success:function (text){
                    var data = eval('('+text+')');
                    var count = 0;
                    for(var i=0;i<data.length;i++){
                        var docctent = data[i].docconent;//大字段
                        var crimid = data[i].crimid;//罪犯编号
                        var criname = data[i].criname;//罪犯姓名 
                        var flowdraftid = data[i].flowdraftid;//流程草稿id(唯一标示 罪犯 减刑信息)
                        objAip.LoadFileBase64(docctent);
                        objAip.SaveTo(diskUrl+crimid+"_"+writtype+"_"+criname+"_"+flowdraftid+".pdf","pdf",0);
                        object.push(""+crimid+"_"+writtype+"_"+criname+""+"_"+flowdraftid);
                        count++;
                    }
                    $("#savePdf").text("共导出罪犯信息-->"+count+"份!(注:未办理结束或者不是本部门的罪犯案件无法导出!)");
                }
           });
           //读取pdf文件 上传到服务器上面
           var flag = 0;
           for(var n=0;n<object.length;n++){
               $.ajax({
                   type:'post',
                   url:'savePDFDocToService.action?1=1&diskUrl='+diskUrl,
                   data:{data:object[n]},
                   async:false,
                   success:function (text){
                       
                   }
              });
           }
           //数据 放到 服务器上面 以后 删除文件夹 
           fso.DeleteFolder(deleteDiskUrl);
      }
      //点击 取消 触发的函数 
      function cancelEditWindow() {
			editWindow.hide();
	  };
 </SCRIPT>
</body>
</html>