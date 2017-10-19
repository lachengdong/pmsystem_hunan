<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
	String applyvalue = (String) request.getAttribute("applyvalue");
%>
<SCRIPT LANGUAGE="JavaScript">
//国籍弹出
 	function guojiTanChu(aipObj,strName){
 		vRet = window.showModalDialog("<%=path%>/toJiGuanSelect.action?qtype=jiguan&gkzxddd="+Math.random(),"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:500px");
		if(vRet){
			aipObj.SetValue(strName,"");
			aipObj.SetValue(strName,vRet[0]);//详细地址
			aipObj.SetValue("guojia","");
			aipObj.SetValue("guojia",vRet[1]);//国家
		}
 	}
	function punishmentCrimSelect(aipObj){
		url = "<%=path%>/batchRewardPunishlPointsCrimSelect.action?1=1";
		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:800px");
		if(vRet){
			aipObj.SetValue("Page1.rewardcrimid","");
			aipObj.SetValue("Page1.punishmentname","");
			aipObj.SetValue("Page1.rewardcrimid",vRet[0]);
			aipObj.SetValue("Page1.punishmentname",vRet[1]);
		}
	}
	function punsuggestSelect(aipObj){
		url = "<%=path%>/punsuggestSelect.action?1=1";
		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:800px");
		if(vRet){
		    var suggesttext = "自" + vRet[1] + "起" + vRet[0] + vRet[2] + "天。";
		    aipObj.SetValue("Page1.pun_suggest1","");
			aipObj.SetValue("Page1.pun_suggest1",suggesttext);
		    aipObj.SetValue("Page1.pundate","");
			aipObj.SetValue("Page1.pundate",vRet[1].replace("年","").replace("月","").replace("日",""));
		    aipObj.SetValue("Page1.puntime","");
			aipObj.SetValue("Page1.puntime",vRet[2]);						
		}		
	}
	//插入页码
	function insertpagenum(){
		var num=document.all.HWPostil1.PageCount;
		document.all.HWPostil1.Login("sys_admin",5, 65535, "DEMO", "");
		//跳过两页（封皮，目录）从第三页开始增加页码
		for(var i=2;i<num;i++){
			var j=i+1;
			document.all.HWPostil1.InsertNote("Page"+j+".pagenum",i,3,24000,47000,1500,1000);//插入编辑区
			document.all.HWPostil1.SetValue("Page"+j+".pagenum",":PROP::LABEL:3");//只读,只能通过接口赋值
			document.all.HWPostil1.SetValue("Page"+j+".pagenum",":PROP:BORDER:0");//没有边框
			document.all.HWPostil1.SetValue("Page"+j+".pagenum",":PROP:FRONTCOLOR:0");//设置字体颜色
			document.all.HWPostil1.SetValue("Page"+j+".pagenum",":PROP:FONTSIZE:12");//设置字体大小
			document.all.HWPostil1.SetValue("Page"+j+".pagenum",j-2);//设置页码
			//设置档案查看目录
			var dangantype = document.all.HWPostil1.GetValueEx("Page"+j+".dangantype", 2, "", 0, "");
			if(dangantype!=''){
				document.all.HWPostil1.SetValue("Page2."+dangantype,j-2)
			}
		}
		document.all.HWPostil1.Logout();
	}
	//判断提交时是否保存过呈批表
	function iscommit(flowdraftid){
		var flag;
		$.ajax({
	        url: "<%=path%>/flow/validatecpb.json", 
	        data: {flowdraftid:flowdraftid},
	        type: "POST",
	        dataType:"json",
	        async:false,
	        success: function (text){
	        	var obj = eval(text);
	        	flag = obj;
	        }
		});
		return flag;
	}  
</SCRIPT> 