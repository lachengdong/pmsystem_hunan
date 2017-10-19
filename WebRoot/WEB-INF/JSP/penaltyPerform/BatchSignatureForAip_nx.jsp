<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"
	import="java.util.*,com.sinog2c.model.system.SignatureScheme"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String type = (String) request.getAttribute("type");//区分是省局还是监狱
List<SignatureScheme> signatureSchemes = (List<SignatureScheme>)request.getAttribute("signatureSchemes");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>批量签章(宁夏分离)</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
			body {
				margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
			}
		</style>
		<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
		<script language="JavaScript" for="HWPostil1" event="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
			document.all.HWPostil1.JSEnv = 1;
		</script>
	</head>
	<body>
		<div id="form1" class="mini-splitter" vertical="true" style="width: 100%; height: 100%; border: 0px;">
			<div size="30px;" showCollapseButton="false" style="border-top: 0px; border-left: 0px; border-right: 0px;">
				<div class="mini-toolbar" style="padding: 2px; border-bottom: 0;">
					<input type="hidden" id="otherid" name="otherid" value='' />
					<input type="hidden" id="docid" name="docid" value='' />
					<input type="hidden" id="jyotherid" name="jyotherid" value='' />
					<input type="hidden" id="tempid" name="tempid" value='' />
					<input type="hidden" id="scheme" name="scheme" value='' />
					<input type="hidden" id="signtype" name="signtype" value='' />
					<input type="hidden" id="content" name="content" value='' />
					<input type="hidden" id="remark" name="remark" value='' />
					<input type="hidden" id="progress" name="progress" value='' />
					<input type="hidden" id="picizhivalue" name="picizhivalue" value='' />
					<input type="hidden" id="hiddenmessage" name="hiddenmessage" value='' />
					<input type="hidden" id="flowdefid" name="flowdefid" value=""/>
					<input type="hidden" id="isnotcheckprogress" name="isnotcheckprogress" value=""/>
					<input type="hidden" id="warden" name="warden" value=""/>
					<input id="casetype" name="casetype" value="" class="mini-hidden" />
<!--					<a class="mini-button" id="dakai" iconCls="icon-lock" plain="true" onclick="dakai()">工具栏</a> &nbsp;-->
					          
					
					年号：（<!--  <input style="width: 32px" value="${caseyear}" required="true" id="caseyear" name="caseyear" />-->
					       <select id="caseyear" name="caseyear">
						             <%
						                 List listYear = (ArrayList)request.getAttribute("listYear");
						                 Calendar c = Calendar.getInstance();//获取当前年
						                 String cyear = String.valueOf(c.get(Calendar.YEAR));
					                	 String selected = "";
						                 for(int i=0;i<listYear.size();i++){
						                	 Object value = listYear.get(i);
						                	 if(cyear.equals(value.toString())){
						                		 selected="selected='selected'";
						                	 }
						                     %>
						                	    <option <%=selected %> value="<%=value %>"><%=value %></option>
						                     <%
						                  }
						              %> 
					         </select>
					）
					<input value="${casedepsimplename}" id="casedepsimplename" style="width: 60px" name="casedepsimplename" />
					<select id="casetype2" name="casetype2">
						<%
						if(type==null && !"shengju".equals(type)){
			      		%>
				      		<option value="jx">
								减字
							</option>
							<option value="js">
								假字
							</option>
							<option value="cbz">
								初保字
							</option>
							<option value="xbz">
								续保字
							</option>
						<%}else{%>
							<option value="sjjx">
								减字
							</option>
							<option value="cbz">
								初保字
							</option>
							<option value="xbz">
								续保字
							</option>
						<% } %>
					</select>
					第
					<input class="mini-textbox" style="width: 160px;" emptyText="案件号：1,2或3-5或1,2,3-5" name="fanwei" id="fanwei" vtype="maxLength:100" />
					号
					<!--			<input class="mini-checkbox" id="pici"  style="display:none;" name="pici" onvaluechanged="genggaipici()"/>当前批次-->
					&nbsp;签章日期：<input type="text" name="sealdate" id="sealdate" readonly="readonly" value="${sealdate}" style="width: 70px" />&nbsp;
					<select id="fangan" name="fangan" style="width: 200px">
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
					&nbsp;
					<a class="mini-button" plain="true" onclick="huoquqianzhangfangan(0);">签章</a> &nbsp;
<!--					<a class="mini-button" plain="true" onclick="batchexport(0);">批量导出</a> &nbsp;-->
					<a class="mini-button" plain="true" onclick="huoquqianzhangfangan(1);">撤销签章</a>
				</div>
			</div>
			<div showCollapseButton="false">
				<div id="showContent" style="height: 30px; overflow: auto; display: none; background: #EFF2F5; color: #3789B8; text-align: center;"></div>
				<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
			</div>
		</div>
	<script type="text/javascript">	
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
  		if(aipLogin(obj)==0){
  	  		//此方法 查询签章方案
  			$.ajax({
	             url: "<%=path%>/sign/ajax/select_nx.json?1=1",
	             data: {signatureid:fangan},
	             type: "post",
	             success: function (text) {
	             	var objs = eval('('+text+')');
	             	document.getElementById('tempid').value=objs.tempid;
	             	document.getElementById('scheme').value=objs.scheme;
	             	document.getElementById('content').value=objs.content;
	             	document.getElementById('remark').value=objs.remark;
	             	document.getElementById('progress').value=objs.progress;
	             	document.getElementById('signtype').value=objs.signtype;
	             	document.getElementById('flowdefid').value=objs.flowdefid;
	             	document.getElementById('isnotcheckprogress').value=objs.isnotcheckprogress;
	             	document.getElementById('warden').value=objs.warden;
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
  function mutilSignature(tempid,scheme,content,remark,progress,delFlag){
    var form = new mini.Form("form1");
    
    /* 以前注释的代码删掉了，以下是没用到的代码，mutilSignature方法重的参数大部分也没用到
	var picidayin='no';
	var o = form.getData(); 
	var json = mini.encode([o]);
	form.validate();
	if (form.isValid() == false) return;*/
	
	var fangan=document.getElementById('fangan').value;
	var fanwei = mini.get("fanwei").getValue();
	if (fanwei == ""){
		alert("请输入案件号范围(示例：1-9 或者1,6,9)！");
		return;
	}
	if(fangan){
		//该方法 后台组织案件号的内容
		$.ajax({
             url: "<%=path%>/penaltyPerform/getSignatureSchemeListForAip_nx.json?1=1",
             data: {fanwei:fanwei},
             type: "post",
             success: function (text) {
                 //后台 组织的案件号 text
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
    var scheme=document.getElementById('scheme').value;
    var signtype=document.getElementById('signtype').value;
    var tempid=document.getElementById('tempid').value;
  	//var caseyear=document.getElementById('caseyear').value;
  	var caseyear = $("#caseyear").val();
  	var casedepsimplename=document.getElementById('casedepsimplename').value;
  	var casetype= document.getElementById('casetype2').value;
  	var aamesage=document.getElementById('hiddenmessage').value;
  	var isnotcheckprogress =$("#isnotcheckprogress").val();
  	var warden =$("#warden").val();
  	var showContent=document.getElementById('showContent');
  	var flowdefid = $("#flowdefid").val();
  	var content = $("#showContent").text();
  	var fanganname = "";//方案名称
  	var obj=document.getElementById("fangan");
    for(i=0;i<obj.length;i++) {//下拉框的长度就是他的选项数
		if(obj[i].selected==true) {
        	fanganname = obj[i].text;//获取方案名称
     	}
	}

  	if(nowindex!=-1&&nowindex!=-99){
  		$.ajax({
             url: "<%=path%>/penaltyPerform/handleAllSealForAip_nx.json?1=1",
             data: { progress:progress,scheme:scheme,signtype:signtype,
                     tempid:tempid,fanganname:fanganname,
                     caseyear:caseyear,casedepsimplename:casedepsimplename,
                     casetype:casetype,cpdnumber:nowindex,delFlag:delFlag,
                     flowdefid:flowdefid,isnotcheckprogress:isnotcheckprogress, warden:warden},
             type: "post",
             success: function (text) {
             	var objs = eval('('+text+')');
             	document.getElementById('otherid').value=objs.otherid;
             	document.getElementById('jyotherid').value=objs.jyotherid;
             	document.getElementById('docid').value=objs.docid;
             	//text内容： 如果到当前签章进程，并且 aipFileStr或otherFileStr的内容不为空，就可以签章 ，否则无法签章
             	jinxingqianzhang(objs.aipFileStr,objs.otherFileStr,objs.baobiaobiaodanString,objs.keyiqianzhangString,objs.title,tempid,delFlag);
                //把 返回文本 放入到 对应的div里面
             	showContent.innerHTML= objs.title;
             	showContent.style.display = "";
             },
             error: function (jqXHR, textStatus, errorThrown) {
            	 alert("操作失败!");
             }
         });
  	 }
  }
  function jinxingqianzhang(aipFileStr,otherFileStr,baobiaobiaodanString,keyiqianzhangString,title,tempid,delFlag){
  	  	var aipObj = document.getElementById("HWPostil1");
  	  	var scheme=document.getElementById('scheme').value;
  	  	var caseyear=document.getElementById("caseyear").value;
  	  	var content = document.getElementById('content').value;
  	  	var otherid = document.getElementById('otherid').value;
  	  	var docid = document.getElementById('docid').value;
  	  	var jyotherid = document.getElementById('jyotherid').value;
  	  	var strSignatureNoteInfo=document.getElementById('scheme').value;
		var strSignatureInfoArray = strSignatureNoteInfo.split("@");
		var date = document.getElementById("sealdate").value;
  	  	aipObj.CloseDoc(0);
		var dtrer=0;
		aipObj.Logout();
		dtrer = aipLogin(aipObj);
		if(dtrer==-200){
			alert("未发现智能卡");
		}else if(dtrer!=0){
			alert("登录失败。错误ID:"+dtrer);
		}else{
		  	if(keyiqianzhangString=="yes"){
		  	  	aipObj.LoadFileBase64(aipFileStr);
		  		if(aipObj.IsOpened()){
					aipObj.ForceSignType=7;
					aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
					if(delFlag==1){
						//删除印章操作
						DeleteSeal(aipObj);
					}else{
						if(null!=strSignatureNoteInfo&&strSignatureNoteInfo!=''&&strSignatureInfoArray.length>0){
							if(date){
								date = date.replace(/\-/g,"");//把yyyy-MM-dd格式转换成yyyyMMdd
							}
							for(var i=0;i<strSignatureInfoArray.length;i++){
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
			             url: "<%=path%>/penaltyPerform/ajaxsavepiliangqianzhangForAip_nx.json?1=1",
			             data: {aipFileStr:newaipFileStr,otherid:otherid},
			             type: 'POST',
			             success: function (text) {
			             	
			             },
			             error: function (jqXHR, textStatus, errorThrown) {
			                 alert(errorThrown);
			             }
			         });
				}
				
				aipObj.CloseDoc(0);
             	if(otherFileStr){
	             	aipObj.LoadFileBase64(otherFileStr);
             	}
             	if(aipObj.IsOpened()){
					aipObj.ForceSignType=7;
					aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
					if(delFlag==1){
						DeleteSeal(aipObj);
					}else{
						if(null!=strSignatureNoteInfo&&strSignatureNoteInfo!=''&&strSignatureInfoArray.length>0){
							if(date){
								date = date.replace(/\-/g,"");//把yyyy-MM-dd格式转换成yyyyMMdd
							}
							for(var i=0;i<strSignatureInfoArray.length;i++){
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
					var newOtheraipFileStr=aipObj.GetCurrFileBase64();
					$.ajax({
			             url: "<%=path%>/penaltyPerform/ajaxsavepiliangqianzhangForAip_nx.json?1=1",
			             data: {otherAipFileStr:newOtheraipFileStr,docid:docid,jyotherid:jyotherid},
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

 </script>
	</body>
</html>