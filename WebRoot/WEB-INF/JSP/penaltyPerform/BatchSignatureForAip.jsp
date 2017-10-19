<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,com.sinog2c.model.system.SignatureScheme" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>批量签章(通用)</title>
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
					<input class="mini-hidden" id="otherid" name="otherid" value=""/>
					<input class="mini-hidden" id="docid" name="docid" value=""/>
					<input class="mini-hidden" id="jyotherid" name="jyotherid" value=""/>
					<input class="mini-hidden" id="tempid" name="tempid" value=""/>
					<input class="mini-hidden" id="scheme" name="scheme" value=""/>
					<input class="mini-hidden" id="signtype" name="signtype" value=""/>
					<input class="mini-hidden" id="content" name="content" value=""/>
					<input class="mini-hidden" id="remark" name="remark" value=""/>
					<input class="mini-hidden" id="progress" name="progress" value=""/>
					<input class="mini-hidden" id="picizhivalue" name="picizhivalue" value=""/>
					<!--  <input class="mini-hidden" id="hiddenmessage" name="hiddenmessage" value=""/>-->
					<input class="mini-hidden" id="flowdefid" name="flowdefid" value=""/>
					<input class="mini-hidden" id="isnotcheckprogress" name="isnotcheckprogress" value=""/>
					<input class="mini-hidden" id="otherSealFlowdefid" name="otherSealFlowdefid" value=""/>
					<input class="mini-hidden" id="result" name="result" value=""/>
					<input class="mini-hidden" id="casetype" name="casetype" value=""/>
					<input class="mini-hidden" id="curTime" name="curTime" value=""/>
					<input class="mini-hidden" id="notExitCasenums" name="notExitCasenums" value=""/>
					<input class="mini-hidden" id="notCurrApprovalCasenums" name="notCurrApprovalCasenums" value=""/>
					<input class="mini-hidden" id="ischeckseal" name="ischeckseal" value="${ischeckseal}"/>
					案件号：（
					       <select id="caseyear" name="caseyear">
					       		<c:forEach items="${listYear}" var="year" >
					       			<c:if test="${year == cYear}">
					       				<option selected='selected' value='${year}'>${year}</option>
					       			</c:if>
					       			<c:if test="${year != cYear}">
					       				<option value='${year}'>${year}</option>
					       			</c:if>
					       		</c:forEach>
					      </select>
					）
					<input value="${casedepsimplename}" id="casedepsimplename" style="width: 60px" name="casedepsimplename" />
					<select id="casetype2" name="casetype2">
						<c:if test="${empty type}">
							<option value="jx">减字</option>
							<option value="js">假字</option>
							<option value="cbz">初保字</option>
							<option value="xbz">续保字</option>
						</c:if>
						<c:if test="${type eq 'shengju'}">
							<option value="sjjx">减字</option>
							<option value="cbz">初保字</option>
							<option value="xbz">续保字</option>
						</c:if>
					</select>
					第<input class="mini-textbox" style="width: 160px;" emptyText="案件号：1,2或3-5或1,2,3-5" name="fanwei" id="fanwei" vtype="maxLength:100" />号
					&nbsp; 签章日期：<input type="text" name="sealdate" id="sealdate" readonly="readonly" value="${sealdate}" style="width: 70px" />&nbsp;
					<select id="fangan" name="fangan" style="width: 200px">
						<c:if test="${not empty signatureSchemes}">
							<c:if test="${fn:length(signatureSchemes) gt 0}">
								<c:forEach items="${signatureSchemes}" var="signatureScheme" >
									<option value="${signatureScheme.signid}">${signatureScheme.name}</option>
								</c:forEach>
							</c:if>
						</c:if>
					</select>
					&nbsp;
					<a class="mini-button" plain="true" onclick="huoquqianzhangfangan(0);">签章</a> &nbsp;
					<a class="mini-button" plain="true" onclick="huoquqianzhangfangan(1);">撤销签章</a>
				</div>
			</div>
			<div showCollapseButton="false">
				<div id="showContent" style="height: 15%; overflow: auto; display: none; background: #EFF2F5; color: #3789B8; text-align: left;"></div>
				<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
			</div>
		</div>
	<script type="text/javascript">	
	mini.parse();
 	var obj2=document.getElementById("HWPostil1");
 	obj2.ShowToolBar = 0;//工具栏是否显示
  	obj2.ShowDefMenu = 0;//菜单栏
  	obj2.ShowScrollBarButton = 1;
	
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
   
  var nowindex=-1;//案件号初始化
  //查询签章方案信息
  function huoquqianzhangfangan(delFlag){
  	nowindex = -1;
  	mini.get('notExitCasenums').setValue("");
  	mini.get('picizhivalue').setValue("");
  	mini.get('notCurrApprovalCasenums').setValue("");
  	showContent.style.display = "none";
    mini.get("result").setValue("签章结果为：<br/>");
    
  	var fangan=$("#fangan").val();
  	var fanwei = mini.get('fanwei').getValue();
	if (fanwei == ""){
		alert("请输入案件号范围(示例：1-9 或者1,6,9)！");
		return;
	}
	//组织案件号的内容
	fanwei = formatFanwei(fanwei);
  	var caseyear = $("#caseyear").val();
  	var casetype= $('#casetype2').val();
  	//var hiddenmessage=mini.get('hiddenmessage').getValue();
  	var obj=document.getElementById("HWPostil1");
  	if(fangan!=""){
  		if(aipLogin(obj)==0){
  	  		//此方法 查询签章方案
  			$.ajax({
	             url: "<%=path%>/sign/ajax/select.json?1=1",
	             data: {signatureid:fangan, casenums:fanwei, caseyear:caseyear, casetype:casetype},
	             type: "post",
	             success: function (text) {
	             	var objs = eval('('+text+')');
	             	mini.get('tempid').setValue(objs.tempid);
	             	mini.get('scheme').setValue(objs.scheme);
	             	mini.get('content').setValue(objs.content);
	             	mini.get('remark').setValue(objs.remark);
	             	mini.get('progress').setValue(objs.progress);
	             	mini.get('signtype').setValue(objs.signtype);
	             	mini.get('flowdefid').setValue(objs.flowdefid);
	             	mini.get('isnotcheckprogress').setValue(objs.isnotcheckprogress);
	             	mini.get('otherSealFlowdefid').setValue(objs.otherSealFlowdefid);
	             	if(objs.notExitCasenums){
	             		mini.get('notExitCasenums').setValue(objs.notExitCasenums);
	             	}
	             	if(objs.currApprovalCasenums){
	             		mini.get('picizhivalue').setValue(objs.currApprovalCasenums);
	             	}
	             	if(objs.notCurrApprovalCasenums){
	             		mini.get('notCurrApprovalCasenums').setValue(objs.notCurrApprovalCasenums);
	             	}
	             	//获取签章方案成功后进行批量签章
	             	mutilSignature(objs.tempid,objs.scheme,objs.content,objs.remark,objs.progress,delFlag)
	             },
	             error: function (jqXHR, textStatus, errorThrown){
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
		var fangan=$('#fangan').val();
		if(fangan){
	         //mini.get('picizhivalue').setValue(fanwei);
	         //如果ischeckseal==0，即jyconfig配制为不要检查签章，则批量签章时，也不检查签章进程
	         var ischeckseal = mini.get('ischeckseal').getValue();
	         if(ischeckseal=='0'){
	         	mini.get('isnotcheckprogress').setValue('1');
	         }
	         getNextValue();
	      	 huoquneirong(delFlag);
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
		var picizhivalue=mini.get('picizhivalue').getValue();
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
    var progress=mini.get('progress').getValue();
    var scheme=mini.get('scheme').getValue();
    var signtype=mini.get('signtype').getValue();
    var tempid=mini.get('tempid').getValue();
  	var caseyear = $("#caseyear").val();
  	var casedepsimplename=$('#casedepsimplename').val();
  	var casetype= $('#casetype2').val();
  	//var aamesage=mini.get('hiddenmessage').getValue();
  	var isnotcheckprogress =mini.get("isnotcheckprogress").getValue();
  	var otherSealFlowdefid =mini.get("otherSealFlowdefid").getValue();
  	var flowdefid = mini.get("flowdefid").getValue();
  	var content = $("#showContent").text();
  	
  	var fanganname = "";//方案名称
  	var obj=document.getElementById("fangan");
    for(i=0;i<obj.length;i++) {//下拉框的长度就是他的选项数
		if(obj[i].selected==true) {
        	fanganname = obj[i].text;//获取方案名称
        	break;
     	}
	}
  	if(nowindex&&nowindex!=-1&&nowindex!=-99){
  		$.ajax({
             url: "<%=path%>/penaltyPerform/handleAllSealForAip.json?1=1",
             data: { progress:progress,scheme:scheme,signtype:signtype,
                     tempid:tempid,fanganname:fanganname,
                     caseyear:caseyear,casedepsimplename:casedepsimplename,
                     casetype:casetype,cpdnumber:nowindex,delFlag:delFlag,
                     flowdefid:flowdefid,isnotcheckprogress:isnotcheckprogress, otherSealFlowdefid:otherSealFlowdefid},
             type: "post",
             async: false,
             success: function (text){
             	var objs = eval('('+text+')');
             	mini.get('otherid').setValue(objs.otherid);
             	mini.get('jyotherid').setValue(objs.jyotherid);
             	mini.get('docid').setValue(objs.docid);
             	mini.get('curTime').setValue(objs.curTime);
             	
             	//text内容： 如果到当前签章进程，并且 aipFileStr或otherFileStr的内容不为空，就可以签章 ，否则无法签章
             	jinxingqianzhang(objs.aipFileStr,objs.otherFileStr,objs.baobiaobiaodanString,objs.keyiqianzhangString,objs.title,tempid,delFlag,objs.flowdraftid,progress,objs.commenttext);
             },
             error: function (jqXHR, textStatus, errorThrown) {
            	 alert("操作失败!");
             }
         });
  	 }else{
  	 	var aipObj = document.getElementById("HWPostil1");
  	 	aipObj.CloseDoc(0);
  	 	var showContent=document.getElementById('showContent');
  	 	var result = mini.get("result").getValue();
  	 	var notExitCasenums = mini.get("notExitCasenums").getValue();
  	 	var notCurrApprovalCasenums = mini.get("notCurrApprovalCasenums").getValue();
  	 	var caseyear = $("#caseyear").val();
  	 	var sdnameforshort = $("#casedepsimplename").val();
  	 	var cpdcasetypeObj = document.getElementById("casetype2");
  	 	var index = cpdcasetypeObj.selectedIndex;
  	 	cpdcasetype = cpdcasetypeObj.options[index].text; 
  	 	
  	 	var redstr1 = "<span style='color:red'>";
  	 	var redstr2 = "</span> ";
  	 	var curTime = mini.get("curTime").getValue();
  	 	if(notExitCasenums){
  	 		result += "【(" + caseyear + ")" + sdnameforshort + cpdcasetype + "第" + redstr1 + notExitCasenums + redstr2 + "号】案件的司法文书不存在"+"\t"+curTime+"<br/>";
  	 	}
  	 	if(notCurrApprovalCasenums){
  	 		result += "【(" + caseyear + ")" + sdnameforshort + cpdcasetype + "第" + redstr1 + notCurrApprovalCasenums + redstr2 + "号】案件的司法文书未到本级操作"+"\t"+curTime+"<br/>";
  	 	}
  	 	
        showContent.innerHTML= result;
        showContent.style.display = "";
  	 }
  }
  function jinxingqianzhang(aipFileStr,otherFileStr,baobiaobiaodanString,keyiqianzhangString,title,tempid,delFlag,flowdraftid,signature,commenttext){
  	  	var aipObj = document.getElementById("HWPostil1");
  	  	var scheme=mini.get('scheme').getValue();
  	  	var caseyear=$("#caseyear").val();
  	  	var content = mini.get('content').getValue();
  	  	var otherid = mini.get('otherid').getValue();
  	  	var docid = mini.get('docid').getValue();
  	  	var jyotherid = mini.get('jyotherid').getValue();
  	  	var strSignatureNoteInfo=mini.get('scheme').getValue();
		var strSignatureInfoArray = strSignatureNoteInfo.split("@");
		var date = $("#sealdate").val();
		
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
						if(null!=strSignatureInfoArray && strSignatureInfoArray.length>0){
							if(date){
								date = date.replace(/\-/g,"");//把yyyy-MM-dd格式转换成yyyyMMdd
							}
							for(var i=0;i<strSignatureInfoArray.length;i++){
								var content="";
								var sealdate=date;
								var cppoopiniondate="";
								var cppoopinion="";
								var sealResult = InsertNote(strSignatureInfoArray[i],aipObj,content,sealdate,cppoopiniondate,cppoopinion);
								if(sealResult == false){
									aipObj.CloseDoc(0);
									return;
								}
							}
						}
					}
					aipObj.SleepSecond(1);
				}
				var newaipFileStr=aipObj.GetCurrFileBase64();
				
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
						if(null!=strSignatureInfoArray && strSignatureInfoArray.length>0){
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
							//将省局减假审核意见给监狱审核表单上
							if(commenttext && tempid=='JXJS_JXSH'){
								aipObj.SetValue("Page2.suggest6","");
								aipObj.SetValue("Page2.suggest6",commenttext);
							}
						}
					}
					aipObj.SleepSecond(1);
		    	}
		    	
		    	//保存签章文件
				var newOtheraipFileStr2=aipObj.GetCurrFileBase64();
				
				//保存签章文件
				$.ajax({
		             url: "<%=path%>/penaltyPerform/ajaxsavepiliangqianzhangForAip.json?1=1",
		             data: {aipFileStr:newaipFileStr,otherid:otherid,
		             	otherAipFileStr:newOtheraipFileStr2,docid:docid,jyotherid:jyotherid,
		             	tempid:tempid, flowdraftid:flowdraftid, delFlag:delFlag, signature:signature
		             },
		             type: 'POST',
		             async:false,
		             success: function (text){
		             	var resultObj = eval(text);
		             	if(resultObj=='success'){
		             		title += "成功！";
		             	}else{
		             		title += "失败！";
		             	}
		             },
		             error: function (jqXHR, textStatus, errorThrown) {
		             	 title += "失败！";
		                 alert(errorThrown);
		             }
		         });
		  	}
		  	//把 返回文本 放入到 对应的div里面
		  	var showContent=document.getElementById('showContent');
		  	var curTime = mini.get("curTime").getValue();
		  	title += "\t"+curTime;
            showContent.innerHTML= title;
            showContent.style.display = "";
            var result = mini.get("result").getValue();
            result += title + "<br/>";
            mini.get("result").setValue(result);
		  	getNextValue();
			huoquneirong(delFlag);
		}
  }
  
  
  	//格式化案件号
  	function formatFanwei(fanwei){
		if(fanwei){
			var fanweilist = new Array();
			fanwei= fanwei.replace(/\，/g,",");
			var strArr = fanwei.split(",");
			for(var i=0;i<strArr.length;i++){
				var str = trim(strArr[i]);
				if(str.indexOf("-")>-1){
					var start = trim(str.split("-")[0]);
					var end = trim(str.split("-")[1]);
					if(start && end){
						for(var j=0;j <= parseInt(end)-parseInt(start);j++){
							fanweilist.push(parseInt(start)+j);
						}
					}
				}else{
					fanweilist.push(str);
				}
			}
			return fanweilist.join(",");
		}
	}
	
	/**
	* 删除左右两端的空格
	*/
	function trim(str){
		 return str.replace(/(^\s*)|(\s*$)/g, '');
	}

 </script>
	</body>
</html>