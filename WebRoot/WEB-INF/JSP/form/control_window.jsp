<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
	String applyvalue = (String) request.getAttribute("applyvalue");
%>
<SCRIPT LANGUAGE="JavaScript">
/****************************************************
*	
*				替换各个会议意见函数
*
*			
****************************************************/
/*
 * 监狱减刑假释
 */
function jxjsJailDanChu1(aipObj,strName){
	var fjqSuggest = '';
	var jqSuggest = '';
	var ksSuggest = '';
	var pshSuggest = '';
	var jySuggest = '';
	var jiashi ="假释";
	var jianxing = "减刑";
	var suggest2 = aipObj.GetValueEx("suggest2",2,"",0,"");
	var suggest3 = aipObj.GetValueEx("suggest3",2,"",0,"");
	var suggest4 = aipObj.GetValueEx("suggest4",2,"",0,"");
	var suggest5 = aipObj.GetValueEx("suggest5",2,"",0,"");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0",2,"",0,""); //刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1",2,"",0,""); 
	var jxjs_2 = aipObj.GetValueEx("jxjs_2",2,"",0,""); 
	var jxjs_3 = aipObj.GetValueEx("jxjs_3",2,"",0,""); 
	var jxjs_4 = aipObj.GetValueEx("jxjs_4",2,"",0,""); 
	var jxjs_5 = aipObj.GetValueEx("jxjs_5",2,"",0,""); 
	var jxjs_6 = aipObj.GetValueEx("jxjs_6",2,"",0,""); 
	var jxjs_7 = aipObj.GetValueEx("jxjs_7",2,"",0,""); 
	var jxjs_8 = aipObj.GetValueEx("jxjs_8",2,"",0,""); 
	var jxjs_9 = aipObj.GetValueEx("jxjs_9",2,"",0,""); 
	var jxjs_10 = aipObj.GetValueEx("jxjs_10",2,"",0,""); 
	var jxjs_11 = aipObj.GetValueEx("jxjs_11",2,"",0,""); 
	var jxjs_12 = aipObj.GetValueEx("jxjs_12",2,"",0,""); 
	var jxjs_13 = aipObj.GetValueEx("jxjs_13",2,"",0,""); 
	var jxjs_14 = aipObj.GetValueEx("payeverymon",2,"",0,""); 
	var jxjs_15 = aipObj.GetValueEx("isagree",2,"",0,""); 
	var jxjs_16 = aipObj.GetValueEx("reason",2,"",0,""); 
	var crimid =  aipObj.GetValueEx("criminalid",2,"",0,""); 
	var datetime = aipObj.GetValueEx("casedate",2,"",0,"");
	var boquan = aipObj.GetValueEx("boquan",2,"",0,"");

	var  tempids = '9560,9453,9573,9574,10341';
	$.ajax({
			type:"POST", 
			url: "<%=path%>/findContent.json",
			data:{crimid:crimid,tempids:tempids},
			cache:false,
			async:false,
			success:function (text) {
				var obj = eval(text);
				if(obj){
					fjqSuggest=obj[0];
					jqSuggest=obj[1];
					ksSuggest=obj[2];
					pshSuggest=obj[3];
					jySuggest=obj[4];
				}
		}});
	//var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," + jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:"+jxjs_0);
	jxjsyj.push("jxjs_1:"+jxjs_1);
	jxjsyj.push("jxjs_2:"+jxjs_2);
	jxjsyj.push("jxjs_3:"+jxjs_3);
	jxjsyj.push("jxjs_4:"+jxjs_4);
	jxjsyj.push("jxjs_5:"+jxjs_5);
	jxjsyj.push("jxjs_6:"+jxjs_6);
	jxjsyj.push("jxjs_7:"+jxjs_7);
	jxjsyj.push("jxjs_8:"+jxjs_8);
	if(jxjs_9=="1.#QNAN" || jxjs_9==null) {
		jxjs_9="";
	}
	jxjsyj.push("jxjs_9:"+jxjs_9);
	jxjsyj.push("jxjs_10:"+jxjs_10);
	jxjsyj.push("jxjs_11:"+jxjs_11);
	jxjsyj.push("jxjs_12:"+jxjs_12);
	jxjsyj.push("jxjs_13:"+jxjs_13);
	jxjsyj.push("jxjs_14:"+jxjs_14);
	jxjsyj.push("jxjs_15:"+jxjs_15);
	jxjsyj.push("jxjs_16:"+jxjs_16);
	jxjsyj.push("datetime:"+datetime);
	jxjsyj.push("boquan:"+boquan);
	/*此处已改回*/
	url = "<%=path%>/tanChuOpinionPage.action?crimid=" + crimid + "&jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1","");
		aipObj.SetValue("Page1.jxjs_2","");
		aipObj.SetValue("Page1.jxjs_3","");
		aipObj.SetValue("Page1.jxjs_4","");
		aipObj.SetValue("Page1.jxjs_5","");
		aipObj.SetValue("Page1.jxjs_6","");
		aipObj.SetValue("Page1.jxjs_7","");
		aipObj.SetValue("Page1.jxjs_8","");
		aipObj.SetValue("Page1.jxjs_9","");
		aipObj.SetValue("Page1.jxjs_10","");
		aipObj.SetValue("Page1.jxjs_11","");
		aipObj.SetValue("Page1.jxjs_12","");
		aipObj.SetValue("Page1.jxjs_13","");
		aipObj.SetValue("Page1.payeverymon","");
		aipObj.SetValue("Page1.isagree","");
		aipObj.SetValue("Page1.reason","");
		aipObj.SetValue("Page1.jxjs_date","");
		aipObj.SetValue("Page1.casedate","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1",vRet[0]);
		aipObj.SetValue("Page1.jxjs_2",vRet[1]);
		aipObj.SetValue("Page1.jxjs_3",vRet[2]);
		aipObj.SetValue("Page1.jxjs_4",vRet[3]);
		aipObj.SetValue("Page1.jxjs_5",vRet[4]);
		aipObj.SetValue("Page1.jxjs_6",vRet[5]);
		aipObj.SetValue("Page1.jxjs_7",vRet[6]);
		aipObj.SetValue("Page1.jxjs_8",vRet[7]);
		aipObj.SetValue("Page1.jxjs_9",vRet[8]);
		aipObj.SetValue("Page1.jxjs_10",vRet[9]);
		aipObj.SetValue("Page1.jxjs_11",vRet[10]);
		aipObj.SetValue("Page1.jxjs_12",vRet[11]);
		aipObj.SetValue("Page1.jxjs_13",vRet[12]);
		aipObj.SetValue("Page1.payeverymon",jxjs_14);
		aipObj.SetValue("Page1.isagree",vRet[13]);
		aipObj.SetValue("Page1.iscommitagree",vRet[13]);
		aipObj.SetValue("Page1.reason",vRet[14]);
		aipObj.SetValue("Page1.jxjs_date",vRet[16]);
		aipObj.SetValue("Page1.casedate",vRet[17]);
		if(vRet[0]=='1'){
			aipObj.SetValue("Page1.jxorjs","");
			aipObj.SetValue("Page1.jxorjs","罪犯假释审核表");
			fjqSuggest = fjqSuggest.replace(jianxing,jiashi);
			jqSuggest = jqSuggest.replace(jianxing,jiashi);
			ksSuggest = ksSuggest.replace(jianxing,jiashi);
			pshSuggest = pshSuggest.replace(jianxing,jiashi);
			jySuggest = jySuggest.replace(jianxing,jiashi);
		}else{
			aipObj.SetValue("Page1.jxorjs","");
			aipObj.SetValue("Page1.jxorjs","罪犯减刑审核表");
		}
		//减刑假释监狱经办人意见选择----------
		if(strName=='suggest2'){
			//清空意见
			aipObj.SetValue("Page2.suggest2","");
			aipObj.SetValue("Page2.suggest3","");
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest2",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest2",fjqSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest3",jqSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest1",ksSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest4",pshSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest5",jySuggest.replace('@',vRet[15]));
					aipObj.SetValue("commenttext",fjqSuggest.replace('@',vRet[15]));
					document.getElementById("commenttext").value= fjqSuggest.replace('@',vRet[15]);
				}
			}
		}
		//减刑假释监狱监区长意见选择----------
		if(strName=='suggest3'){
			//清空意见
			aipObj.SetValue("Page2.suggest3","");
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			aipObj.SetValue("Page1.text11","");
			//填充意见信息                         							}
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest3",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest3",jqSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest1",ksSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest4",pshSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest5",jySuggest.replace('@',vRet[15]));
					aipObj.SetValue("commenttext",jqSuggest.replace('@',vRet[15]));
					document.getElementById("commenttext").value = jqSuggest.replace('@',vRet[15]);
				}
				aipObj.SetValue("Page2.text11",formatDate(new Date()));
			}
		}
		//减刑假释监狱科室意见选择----------
		if(strName=='suggest1'){
			//清空意见
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest1",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest1",pshSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest4",pshSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest5",jySuggest.replace('@',vRet[15]));
					aipObj.SetValue("commenttext",pshSuggest.replace('@',vRet[15]));
					document.getElementById("commenttext").value = pshSuggest.replace('@',vRet[15]);
				}
				aipObj.SetValue("Page2.text13",formatDate(new Date()));
			}
		}
		//减刑假释监狱评审会意见选择----------
		if(strName=='suggest4'){
			//清空意见
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest4",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest4",pshSuggest.replace('@',vRet[15]));
					aipObj.SetValue("Page2.suggest5",jySuggest.replace('@',vRet[15]));
					aipObj.SetValue("commenttext",pshSuggest.replace('@',vRet[15]));
					document.getElementById("commenttext").value = pshSuggest.replace('@',vRet[15]);
				}
				aipObj.SetValue("Page2.text13",formatDate(new Date()));
			}
		}
		//减刑假释监狱监狱长意见选择----------
		if(strName=='suggest5'){
			//清空意见
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				aipObj.SetValue("Page2.suggest5",jySuggest.replace('@',vRet[15]));
				aipObj.SetValue("commenttext",jySuggest.replace('@',vRet[15]));
				document.getElementById("commenttext").value = jySuggest.replace('@',vRet[15]);
			}//拼接的意见有值结束
			aipObj.SetValue("Page2.text18",formatDate(new Date()));
		}//suggest5结束
		
	}				
}

function jxjsJailDanChu(aipObj,strName){
	
	var fjqSuggest = '经分监区警察集体评议，';
	var jqSuggest = '经监区长办公会审核，';
	var ksSuggest = '经科务会审查，';
	var pshSuggest = '经评审委员会评审，';
	var jySuggest = '经监狱长办公会研究，';
	var suggest2 = aipObj.GetValueEx("suggest2",2,"",0,"");
	var suggest3 = aipObj.GetValueEx("suggest3",2,"",0,"");
	var suggest4 = aipObj.GetValueEx("suggest4",2,"",0,"");
	var suggest5 = aipObj.GetValueEx("suggest5",2,"",0,"");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0",2,"",0,""); //刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1",2,"",0,""); 
	var jxjs_2 = aipObj.GetValueEx("jxjs_2",2,"",0,""); 
	var jxjs_3 = aipObj.GetValueEx("jxjs_3",2,"",0,""); 
	var jxjs_4 = aipObj.GetValueEx("jxjs_4",2,"",0,""); 
	var jxjs_5 = aipObj.GetValueEx("jxjs_5",2,"",0,""); 
	var jxjs_6 = aipObj.GetValueEx("jxjs_6",2,"",0,""); 
	var jxjs_7 = aipObj.GetValueEx("jxjs_7",2,"",0,""); 
	var jxjs_8 = aipObj.GetValueEx("jxjs_8",2,"",0,""); 
	var jxjs_9 = aipObj.GetValueEx("jxjs_9",2,"",0,""); 
	var jxjs_10 = aipObj.GetValueEx("jxjs_10",2,"",0,""); 
	var jxjs_11 = aipObj.GetValueEx("jxjs_11",2,"",0,""); 
	var jxjs_12 = aipObj.GetValueEx("jxjs_12",2,"",0,""); 
	var jxjs_13 = aipObj.GetValueEx("jxjs_13",2,"",0,""); 
	var jxjs_14 = aipObj.GetValueEx("payeverymon",2,"",0,""); 
	var jxjs_15 = aipObj.GetValueEx("isagree",2,"",0,""); 
	var jxjs_16 = aipObj.GetValueEx("reason",2,"",0,""); 
	var crimid =  aipObj.GetValueEx("criminalid",2,"",0,""); 
	var datetime = aipObj.GetValueEx("casedate",2,"",0,"");
	//var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," + jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:"+jxjs_0);
	jxjsyj.push("jxjs_1:"+jxjs_1);
	jxjsyj.push("jxjs_2:"+jxjs_2);
	jxjsyj.push("jxjs_3:"+jxjs_3);
	jxjsyj.push("jxjs_4:"+jxjs_4);
	jxjsyj.push("jxjs_5:"+jxjs_5);
	jxjsyj.push("jxjs_6:"+jxjs_6);
	jxjsyj.push("jxjs_7:"+jxjs_7);
	jxjsyj.push("jxjs_8:"+jxjs_8);
	if(jxjs_9=="1.#QNAN" || jxjs_9==null) {
		jxjs_9="";
	}
	jxjsyj.push("jxjs_9:"+jxjs_9);
	jxjsyj.push("jxjs_10:"+jxjs_10);
	jxjsyj.push("jxjs_11:"+jxjs_11);
	jxjsyj.push("jxjs_12:"+jxjs_12);
	jxjsyj.push("jxjs_13:"+jxjs_13);
	jxjsyj.push("jxjs_14:"+jxjs_14);
	jxjsyj.push("jxjs_15:"+jxjs_15);
	jxjsyj.push("jxjs_16:"+jxjs_16);
	jxjsyj.push("datetime:"+datetime);
	/*此处已改回*/
	url = "<%=path%>/tanChuOpinionPage.action?crimid=" + crimid + "&jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1","");
		aipObj.SetValue("Page1.jxjs_2","");
		aipObj.SetValue("Page1.jxjs_3","");
		aipObj.SetValue("Page1.jxjs_4","");
		aipObj.SetValue("Page1.jxjs_5","");
		aipObj.SetValue("Page1.jxjs_6","");
		aipObj.SetValue("Page1.jxjs_7","");
		aipObj.SetValue("Page1.jxjs_8","");
		aipObj.SetValue("Page1.jxjs_9","");
		aipObj.SetValue("Page1.jxjs_10","");
		aipObj.SetValue("Page1.jxjs_11","");
		aipObj.SetValue("Page1.jxjs_12","");
		aipObj.SetValue("Page1.jxjs_13","");
		aipObj.SetValue("Page1.payeverymon","");
		aipObj.SetValue("Page1.isagree","");
		aipObj.SetValue("Page1.reason","");
		aipObj.SetValue("Page1.jxjs_date","");
		aipObj.SetValue("Page1.casedate","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1",vRet[0]);
		aipObj.SetValue("Page1.jxjs_2",vRet[1]);
		aipObj.SetValue("Page1.jxjs_3",vRet[2]);
		aipObj.SetValue("Page1.jxjs_4",vRet[3]);
		aipObj.SetValue("Page1.jxjs_5",vRet[4]);
		aipObj.SetValue("Page1.jxjs_6",vRet[5]);
		aipObj.SetValue("Page1.jxjs_7",vRet[6]);
		aipObj.SetValue("Page1.jxjs_8",vRet[7]);
		aipObj.SetValue("Page1.jxjs_9",vRet[8]);
		aipObj.SetValue("Page1.jxjs_10",vRet[9]);
		aipObj.SetValue("Page1.jxjs_11",vRet[10]);
		aipObj.SetValue("Page1.jxjs_12",vRet[11]);
		aipObj.SetValue("Page1.jxjs_13",vRet[12]);
		aipObj.SetValue("Page1.payeverymon",jxjs_14);
		aipObj.SetValue("Page1.isagree",vRet[13]);
		aipObj.SetValue("Page1.iscommitagree",vRet[13]);
		aipObj.SetValue("Page1.reason",vRet[14]);
		aipObj.SetValue("Page1.jxjs_date",vRet[16]);
		aipObj.SetValue("Page1.casedate",vRet[17]);
		if(vRet[0]=='1'){
			aipObj.SetValue("Page1.jxojs","");
			aipObj.SetValue("Page1.jxojs","罪犯假释审核表");
		}else{
			aipObj.SetValue("Page1.jxojs","");
			aipObj.SetValue("Page1.jxojs","罪犯减刑审核表");
		}
		//减刑假释监狱经办人意见选择----------
		if(strName=='suggest2'){
			//清空意见
			aipObj.SetValue("Page2.suggest2","");
			aipObj.SetValue("Page2.suggest3","");
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest2",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest2",fjqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest3",jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest1",ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4",pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",fjqSuggest + vRet[15]);
					document.getElementById("commenttext").value= fjqSuggest + vRet[15];
				}
			}
		}
		//减刑假释监狱监区长意见选择----------
		if(strName=='suggest3'){
			//清空意见
			aipObj.SetValue("Page2.suggest3","");
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			aipObj.SetValue("Page1.text11","");
			//填充意见信息                         							}
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest3",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest3",jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest1",ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4",pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",jqSuggest + vRet[15]);
					document.getElementById("commenttext").value = jqSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text11",formatDate(new Date()));
			}
		}
		//减刑假释监狱科室意见选择----------
		if(strName=='suggest1'){
			//清空意见
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest1",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest1",pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4",pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",pshSuggest + vRet[15]);
					document.getElementById("commenttext").value = pshSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text13",formatDate(new Date()));
			}
		}
		//减刑假释监狱评审会意见选择----------
		if(strName=='suggest4'){
			//清空意见
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest4",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest4",pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",pshSuggest + vRet[15]);
					document.getElementById("commenttext").value = pshSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text13",formatDate(new Date()));
			}
		}
		//减刑假释监狱监狱长意见选择----------
		if(strName=='suggest5'){
			//清空意见
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				aipObj.SetValue("Page2.suggest5",jySuggest + vRet[15]);
				aipObj.SetValue("commenttext",jySuggest + vRet[15].replace('建议','同意提请'));
				document.getElementById("commenttext").value = jySuggest + vRet[15];
			}//拼接的意见有值结束
			aipObj.SetValue("Page2.text18",formatDate(new Date()));
		}//suggest5结束
		
	}				
}


//山西宁夏分离 
function jxjsJailDanChu_sx(aipObj,strName){
	var jing = '    经';
	var fjqSuggest = '监区警察集体评议，';
	var jqSuggest = '监区长办公会审核，';
	var ksSuggest = '科务会审查，';
	var pshSuggest = '评审委员会评审，';
	var jySuggest = '监狱长办公会决定，';
	var suggest2 = aipObj.GetValueEx("suggest2",2,"",0,"");
	var suggest3 = aipObj.GetValueEx("suggest3",2,"",0,"");
	var suggest4 = aipObj.GetValueEx("suggest4",2,"",0,"");
	var suggest5 = aipObj.GetValueEx("suggest5",2,"",0,"");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0",2,"",0,""); //刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1",2,"",0,""); 
	var jxjs_2 = aipObj.GetValueEx("jxjs_2",2,"",0,""); 
	var jxjs_3 = aipObj.GetValueEx("jxjs_3",2,"",0,""); 
	var jxjs_4 = aipObj.GetValueEx("jxjs_4",2,"",0,""); 
	var jxjs_5 = aipObj.GetValueEx("jxjs_5",2,"",0,""); 
	var jxjs_6 = aipObj.GetValueEx("jxjs_6",2,"",0,""); 
	var jxjs_7 = aipObj.GetValueEx("jxjs_7",2,"",0,""); 
	var jxjs_8 = aipObj.GetValueEx("jxjs_8",2,"",0,""); 
	var jxjs_9 = aipObj.GetValueEx("jxjs_9",2,"",0,""); 
	var jxjs_10 = aipObj.GetValueEx("jxjs_10",2,"",0,""); 
	var jxjs_11 = aipObj.GetValueEx("jxjs_11",2,"",0,""); 
	var jxjs_12 = aipObj.GetValueEx("jxjs_12",2,"",0,""); 
	var jxjs_13 = aipObj.GetValueEx("jxjs_13",2,"",0,""); 
	var jxjs_14 = aipObj.GetValueEx("payeverymon",2,"",0,""); 
	var jxjs_15 = aipObj.GetValueEx("isagree",2,"",0,""); 
	var jxjs_16 = aipObj.GetValueEx("reason",2,"",0,""); 
	var crimid =  aipObj.GetValueEx("criminalid",2,"",0,""); 
	var datetime = aipObj.GetValueEx("casedate",2,"",0,"");
	//var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," + jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:"+jxjs_0);
	jxjsyj.push("jxjs_1:"+jxjs_1);
	jxjsyj.push("jxjs_2:"+jxjs_2);
	jxjsyj.push("jxjs_3:"+jxjs_3);
	jxjsyj.push("jxjs_4:"+jxjs_4);
	jxjsyj.push("jxjs_5:"+jxjs_5);
	jxjsyj.push("jxjs_6:"+jxjs_6);
	jxjsyj.push("jxjs_7:"+jxjs_7);
	jxjsyj.push("jxjs_8:"+jxjs_8);
	if(jxjs_9=="1.#QNAN" || jxjs_9==null) {
		jxjs_9="";
	}
	jxjsyj.push("jxjs_9:"+jxjs_9);
	jxjsyj.push("jxjs_10:"+jxjs_10);
	jxjsyj.push("jxjs_11:"+jxjs_11);
	jxjsyj.push("jxjs_12:"+jxjs_12);
	jxjsyj.push("jxjs_13:"+jxjs_13);
	jxjsyj.push("jxjs_14:"+jxjs_14);
	jxjsyj.push("jxjs_15:"+jxjs_15);
	jxjsyj.push("jxjs_16:"+jxjs_16);
	jxjsyj.push("datetime:"+datetime);
	url = "<%=path%>/tanChuOpinionPage_nx.action?crimid=" + crimid + "&jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1","");
		aipObj.SetValue("Page1.jxjs_2","");
		aipObj.SetValue("Page1.jxjs_3","");
		aipObj.SetValue("Page1.jxjs_4","");
		aipObj.SetValue("Page1.jxjs_5","");
		aipObj.SetValue("Page1.jxjs_6","");
		aipObj.SetValue("Page1.jxjs_7","");
		aipObj.SetValue("Page1.jxjs_8","");
		aipObj.SetValue("Page1.jxjs_9","");
		aipObj.SetValue("Page1.jxjs_10","");
		aipObj.SetValue("Page1.jxjs_11","");
		aipObj.SetValue("Page1.jxjs_12","");
		aipObj.SetValue("Page1.jxjs_13","");
		aipObj.SetValue("Page1.payeverymon","");
		aipObj.SetValue("Page1.isagree","");
		aipObj.SetValue("Page1.reason","");
		aipObj.SetValue("Page1.casedate","");
		aipObj.SetValue("Page1.jxjs_date","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1",vRet[0]);
		aipObj.SetValue("Page1.jxjs_2",vRet[1]);
		aipObj.SetValue("Page1.jxjs_3",vRet[2]);
		aipObj.SetValue("Page1.jxjs_4",vRet[3]);
		aipObj.SetValue("Page1.jxjs_5",vRet[4]);
		aipObj.SetValue("Page1.jxjs_6",vRet[5]);
		aipObj.SetValue("Page1.jxjs_7",vRet[6]);
		aipObj.SetValue("Page1.jxjs_8",vRet[7]);
		aipObj.SetValue("Page1.jxjs_9",vRet[8]);
		aipObj.SetValue("Page1.jxjs_10",vRet[9]);
		aipObj.SetValue("Page1.jxjs_11",vRet[10]);
		aipObj.SetValue("Page1.jxjs_12",vRet[11]);
		aipObj.SetValue("Page1.jxjs_13",vRet[12]);
		aipObj.SetValue("Page1.payeverymon",jxjs_14);
		aipObj.SetValue("Page1.isagree",vRet[13]);
		aipObj.SetValue("Page1.iscommitagree",vRet[13]);
		aipObj.SetValue("Page1.reason",vRet[14]);
		aipObj.SetValue("Page1.jxjs_date",vRet[16]);
		aipObj.SetValue("Page1.casedate",vRet[17]);
		if(vRet[0]=='1'){
			aipObj.SetValue("Page1.jxojs","");
			aipObj.SetValue("Page1.jxojs","罪犯假释审核表");
		}else{
			aipObj.SetValue("Page1.jxojs","");
			aipObj.SetValue("Page1.jxojs","罪犯减刑审核表");
		}
		//减刑假释监狱经办人意见选择----------
		if(strName=='suggest2'){
			//清空意见
			aipObj.SetValue("Page2.suggest2","");
			aipObj.SetValue("Page2.suggest3","");
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest2",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest2",jing+vRet[17]+fjqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest3",jing+vRet[17]+jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest1",jing+vRet[17]+ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4",jing+vRet[17]+pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jing+vRet[17]+jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",jing+vRet[17]+fjqSuggest + vRet[15]);
					document.getElementById("commenttext").value= jing+vRet[17]+fjqSuggest + vRet[15];
				}
			}
		}
		//减刑假释监狱监区长意见选择----------
		if(strName=='suggest3'){
			//清空意见
			aipObj.SetValue("Page2.suggest3","");
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			aipObj.SetValue("Page1.text11","");
			//填充意见信息                         							}
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest3",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest3",jing+vRet[17]+jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest1",jing+vRet[17]+ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4",jing+vRet[17]+pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jing+vRet[17]+jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",jing+vRet[17]+jqSuggest + vRet[15]);
					document.getElementById("commenttext").value = jing+vRet[17]+jqSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text11",formatDate(new Date()));
			}
		}
		//减刑假释监狱科室意见选择----------
		if(strName=='suggest1'){
			//清空意见
			aipObj.SetValue("Page2.suggest1","");
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest1",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest1",jing+vRet[17]+pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4",jing+vRet[17]+pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jing+vRet[17]+jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",jing+vRet[17]+pshSuggest + vRet[15]);
					document.getElementById("commenttext").value = jing+vRet[17]+pshSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text13",formatDate(new Date()));
			}
		}
		//减刑假释监狱评审会意见选择----------
		if(strName=='suggest4'){
			//清空意见
			aipObj.SetValue("Page2.suggest4","");
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				if(vRet[13]==0){
					aipObj.SetValue("Page2.suggest4",vRet[15]);
				}else{
					aipObj.SetValue("Page2.suggest4",jing+vRet[17]+pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5",jing+vRet[17]+jySuggest + vRet[15].replace('建议','同意提请'));
					aipObj.SetValue("commenttext",jing+vRet[17]+pshSuggest + vRet[15]);
					document.getElementById("commenttext").value = jing+vRet[17]+pshSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text13",formatDate(new Date()));
			}
		}
		//减刑假释监狱监狱长意见选择----------
		if(strName=='suggest5'){
			//清空意见
			aipObj.SetValue("Page2.suggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				aipObj.SetValue("Page2.suggest5",jing+vRet[17]+jySuggest + vRet[15]);
				aipObj.SetValue("commenttext",jing+vRet[17]+jySuggest + vRet[15].replace('建议','同意提请'));
				document.getElementById("commenttext").value =jing+vRet[17]+ jySuggest + vRet[15];
			}//拼接的意见有值结束
			aipObj.SetValue("Page2.text18",formatDate(new Date()));
		}//suggest5结束
		
	}				
}

/*
 * 监狱保外就医
 */

//监狱保外就医审批表（暂予监外执行 审批表）     弹出意见
function bwjyspbTanChu(aipObj,strName){
	var fjqSuggest = '经分监区全体警察集体评议，';
	var jqSuggest = '经监区长办公会审核，';
	var ksSuggest = '经保外评审委员会评审，';
	var jySuggest = '经监狱长办公会议决定，';
	var crimid =  aipObj.GetValueEx("criminalid",2,"",0,""); 
	//var unlimit =  aipObj.GetValueEx("unlimit",2,"",0,""); 
	var bystartdate =  aipObj.GetValueEx("bystartdate",2,"",0,""); 
	var byenddate =  aipObj.GetValueEx("byenddate",2,"",0,""); 
	//var fcrq =  aipObj.GetValueEx("fcrq",2,"",0,""); 
	var select3 =  aipObj.GetValueEx("select3",2,"",0,""); 
	url = "<%=path%>/bwjyspbTanChuOpinionPage.action?crimid=" + crimid + "&bystartdate="+ encodeURI(encodeURI(bystartdate))+"&byenddate="+ encodeURI(encodeURI(byenddate))+"&select3="+encodeURI(encodeURI(select3));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:350px");
	var reg=new RegExp("-","g"); 
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.bystartdate","");
		aipObj.SetValue("Page1.byenddate","");
		//aipObj.SetValue("Page1.unlimit","");
		//aipObj.SetValue("Page1.fcrq","");
		aipObj.SetValue("Page1.select3","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.bystartdate",vRet[0].replace(reg, ''));
		aipObj.SetValue("Page1.byenddate",vRet[1].replace(reg, ''));
		//aipObj.SetValue("Page1.unlimit",vRet[2]);
		//aipObj.SetValue("Page1.fcrq",vRet[5]);
		aipObj.SetValue("Page1.select3",vRet[4]);
		aipObj.SetValue("Page1.jxjs_1","2");
		
		//经办人
		if(strName=='bwjyzhpgyj'){
			aipObj.SetValue("Page2.bwjyzhpgyj","");
			aipObj.SetValue("Page2.bwjyjqyj","");
			aipObj.SetValue("Page2.bwjyksyj","");
			aipObj.SetValue("Page2.bwjyjyyj","");
			if(vRet[3]){
					aipObj.SetValue("Page2.bwjyzhpgyj",fjqSuggest + vRet[3]);
					aipObj.SetValue("Page2.bwjyjqyj",jqSuggest + vRet[3]);
					aipObj.SetValue("Page2.bwjyksyj",ksSuggest + vRet[3]);
					aipObj.SetValue("Page2.bwjyjyyj",jySuggest + vRet[3]);
			}
		}
		//监区
		if(strName=='bwjyjqyj'){
			aipObj.SetValue("Page2.bwjyjqyj","");
			aipObj.SetValue("Page2.bwjyksyj","");
			aipObj.SetValue("Page2.bwjyjyyj","");
			if(vRet[3]){
					aipObj.SetValue("Page2.bwjyjqyj",jqSuggest + vRet[3]);
					aipObj.SetValue("Page2.bwjyksyj",ksSuggest + vRet[3]);
					aipObj.SetValue("Page2.bwjyjyyj",jySuggest + vRet[3]);
					aipObj.SetValue("Page2.text12",formatDate(new Date()));
			}
		}
		//科室
		if(strName=='bwjyksyj'){
			aipObj.SetValue("Page2.bwjyksyj","");
			aipObj.SetValue("Page2.bwjyjyyj","");
			if(vRet[3]){
					aipObj.SetValue("Page2.bwjyksyj",ksSuggest + vRet[3]);
					aipObj.SetValue("Page2.bwjyjyyj",jySuggest + vRet[3]);
					aipObj.SetValue("Page2.text14",formatDate(new Date()));
			}
		}
		//监狱
		if(strName=='bwjyjyyj'){
			aipObj.SetValue("Page2.bwjyjyyj","");
			if(vRet[3]){
					aipObj.SetValue("Page2.bwjyjyyj",jySuggest + vRet[3]);
					aipObj.SetValue("Page2.text15",formatDate(new Date()));
			}
		}
	}				
}

 	//监狱保外就医审批表（暂予监外执行 审批表）     弹出意见(宁夏)
 	function bwjyspbTanChu_nx(aipObj,strName){
 	 	var jingguo = '    经';
 		var crimid =  aipObj.GetValueEx("criminalid",2,"",0,"");
 		var cbiname = aipObj.GetValueEx("name",2,"",0,"");
 		//var unlimit =  aipObj.GetValueEx("unlimit",2,"",0,""); 
 		var bystartdate =  aipObj.GetValueEx("bystartdate",2,"",0,""); 
 		var byenddate =  aipObj.GetValueEx("byenddate",2,"",0,""); 
 		//var fcrq =  aipObj.GetValueEx("fcrq",2,"",0,""); 
 		var select3 =  aipObj.GetValueEx("select3",2,"",0,""); 

 		var strContent = aipObj.GetValueEx(strName,2,"",0,""); //点击 当前节点的值
 		url = "<%=path%>/bwjyspbTanChuOpinionPage.action?crimid=" + crimid + "&bystartdate="+ encodeURI(encodeURI(bystartdate))+"&strName="+encodeURI(encodeURI(strName))+"&strContent="+encodeURI(encodeURI(strContent))+"&cbiname="+encodeURI(encodeURI(cbiname));
 		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:350px");
 		var reg=new RegExp("-","g"); 
 		if(vRet){
 			//清空隐藏域原来的值
 			aipObj.SetValue("Page1.bystartdate","");
 			aipObj.SetValue("Page1.byenddate","");
 			aipObj.SetValue("Page1.jxjs_1","2");
 			
 			//经办人
 			if(strName=='bwjyzhpgyj'){
 				aipObj.SetValue("Page2.bwjyzhpgyj","");
 				if(vRet[0]){
 						aipObj.SetValue("Page2.bwjyzhpgyj",vRet[0]);
 				}
 			}
 			//监区
 			if(strName=='bwjyjqyj'){
 				aipObj.SetValue("Page2.bwjyjqyj","");
 				if(vRet[0]){
 						aipObj.SetValue("Page2.bwjyjqyj",vRet[0]);
 						aipObj.SetValue("Page2.text12",formatDate(new Date()));
 				}
 			}
 			//科室
 			if(strName=='bwjyksyj'){
 				aipObj.SetValue("Page2.bwjyksyj","");
 				if(vRet[0]){
 						aipObj.SetValue("Page2.bwjyksyj",vRet[0]);
 						aipObj.SetValue("Page2.text14",formatDate(new Date()));
 				}
 			}
 			//监狱
 			if(strName=='bwjyjyyj'){
 				aipObj.SetValue("Page2.bwjyjyyj","");
 				if(vRet[0]){
 					 aipObj.SetValue("Page2.bwjyjyyj",vRet[0]);
 					 aipObj.SetValue("Page2.text15",formatDate(new Date()));
 				}
 			}
 		}				
 	}
 	
	
	function rewardPunishsPointsCrimSelect(aipObj){
		url = "<%=path%>/batchRewardPunishlPointsCrimSelect.action?1=1";
		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:800px");
		if(vRet){
			aipObj.SetValue("Page1.pljkfcrimids","");
			aipObj.SetValue("Page1.pljkfnames","");
			aipObj.SetValue("Page1.pljkfcrimids",vRet[0]);
			aipObj.SetValue("Page1.pljkfnames",vRet[1]);
		}
	}
	
	function rewardApprovalCrimSelect(aipObj){
	    var pastseasonrewardcrimidval = aipObj.GetValueEx("pastseasonrewardcrimid",2,"",0,"");
		url = "<%=path%>/batchRewardApprovalCrimSelect.action?1=1&selected=" + pastseasonrewardcrimidval + "&sortval=seasonsort";
		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:550px;dialogWidth:800px");
		
		if(vRet){
			aipObj.SetValue("Page1.rewardcrimid","");
			aipObj.SetValue("Page1.seasonrewardname","");
			aipObj.SetValue("Page1.seasonrewardcrimid","");
			
			aipObj.SetValue("Page1.seasonrewardname", vRet[1]);
			aipObj.SetValue("Page1.seasonrewardcrimid", vRet[0]);
			
			if(pastseasonrewardcrimidval!="") {
				aipObj.SetValue("Page1.rewardcrimid", vRet[0] + "," + pastseasonrewardcrimidval);
			} else {
				aipObj.SetValue("Page1.rewardcrimid", vRet[0]);
			}
		}
	}
	
	function rewardPastseasonApprovalCrimSelect(aipObj){
	    var seasonrewardcrimidval = aipObj.GetValueEx("seasonrewardcrimid",2,"",0,"");
		url = "<%=path%>/batchRewardApprovalCrimSelect.action?1=1&selected=" + seasonrewardcrimidval + "&sortval=pastseasonsort";
		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:550px;dialogWidth:800px");
		
		if(vRet){
			aipObj.SetValue("Page1.rewardcrimid","");
			aipObj.SetValue("Page1.pastseasonrewardname","");
			aipObj.SetValue("Page1.pastseasonrewardcrimid","");
			
			aipObj.SetValue("Page1.pastseasonrewardname", vRet[1]);
			aipObj.SetValue("Page1.pastseasonrewardcrimid", vRet[0]);
			if(seasonrewardcrimidval!="") {
				aipObj.SetValue("Page1.rewardcrimid", vRet[0] + "," + seasonrewardcrimidval);
			} else {
				aipObj.SetValue("Page1.rewardcrimid", vRet[0]);
			}
		}
	}

	//省局罪犯监外执行 审批表（暂予监外执行 审批表）     弹出意见
function sjzfjwzxTanChu(aipObj,strName){
	var fjqSuggest = '';
	var jqSuggest = '';
	var ksSuggest = '';
	var jySuggest = '';
	var crimid =  aipObj.GetValueEx("criminalid",2,"",0,""); 
	var unlimit =  aipObj.GetValueEx("unlimit",2,"",0,""); 
	var startDate =  aipObj.GetValueEx("startDate",2,"",0,""); 
	var endDate =  aipObj.GetValueEx("endDate",2,"",0,""); 
	url = "<%=path%>/sjzfjwzxTanChuOpinionPage.action?crimid=" + crimid + "&unlimit=" + encodeURI(encodeURI(unlimit))+"&startDate="+ encodeURI(encodeURI(startDate))+"&endDate="+ encodeURI(encodeURI(endDate));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:285px;dialogWidth:350px");
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.startDate","");
		aipObj.SetValue("Page1.endDate","");
		aipObj.SetValue("Page1.unlimit","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.startDate",vRet[0]);
		aipObj.SetValue("Page1.endDate",vRet[1]);
		aipObj.SetValue("Page1.unlimit",vRet[2]);
		//单位意见
		if(strName=='sjdanweiyijian'){
			aipObj.SetValue("Page1.sjdanweiyijian","");
			aipObj.SetValue("Page1.sjshenheyijian","");
			aipObj.SetValue("Page1.sjchulingdaopishi","");
			if(vRet[3]){
					aipObj.SetValue("Page1.sjdanweiyijian",fjqSuggest + vRet[3]);
					aipObj.SetValue("Page1.sjshenheyijian",jqSuggest + vRet[3]);
					aipObj.SetValue("Page1.sjchulingdaopishi",ksSuggest + vRet[3]);
			}
		}
		//审核意见
		if(strName=='sjshenheyijian'){
			aipObj.SetValue("Page1.sjshenheyijian","");
			aipObj.SetValue("Page1.sjchulingdaopishi","");
			if(vRet[3]){
					aipObj.SetValue("Page1.sjshenheyijian",jqSuggest + vRet[3]);
					aipObj.SetValue("Page1.sjchulingdaopishi",ksSuggest + vRet[3]);
			}
		}
		//处领导批示 
		if(strName=='sjchulingdaopishi'){
			aipObj.SetValue("Page1.sjchulingdaopishi","");
			if(vRet[3]){
					aipObj.SetValue("Page1.sjchulingdaopishi",ksSuggest + vRet[3]);
			}
		}
	}				
}
	/*
	 * 法院弹出框
	 */
	function jxjsCourtDanChu(aipObj, strName){
	var cbFySuggest = '';
	var hyFySuggest = '';
	var ldFySuggest = '';
	var ld2FySuggest = '';
	var fysuggest2 = aipObj.GetValueEx("fysuggest2",2,"",0,"");
	var fysuggest3 = aipObj.GetValueEx("fysuggest3",2,"",0,"");
	var fysuggest4 = aipObj.GetValueEx("fysuggest4",2,"",0,"");
	var fysuggest5 = aipObj.GetValueEx("fysuggest5",2,"",0,"");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0",2,"",0,""); //刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1",2,"",0,""); 
	var jxjs_2 = aipObj.GetValueEx("jxjs_2",2,"",0,""); 
	var jxjs_3 = aipObj.GetValueEx("jxjs_3",2,"",0,""); 
	var jxjs_4 = aipObj.GetValueEx("jxjs_4",2,"",0,""); 
	var jxjs_5 = aipObj.GetValueEx("jxjs_5",2,"",0,""); 
	var jxjs_6 = aipObj.GetValueEx("jxjs_6",2,"",0,""); 
	var jxjs_7 = aipObj.GetValueEx("jxjs_7",2,"",0,""); 
	var jxjs_8 = aipObj.GetValueEx("jxjs_8",2,"",0,""); 
	var jxjs_9 = aipObj.GetValueEx("jxjs_9",2,"",0,""); 
	var jxjs_10 = aipObj.GetValueEx("jxjs_10",2,"",0,""); 
	var jxjs_11 = aipObj.GetValueEx("jxjs_11",2,"",0,""); 
	var jxjs_12 = aipObj.GetValueEx("jxjs_12",2,"",0,""); 
	var jxjs_13 = aipObj.GetValueEx("jxjs_13",2,"",0,""); 
	var jxjs_14 = aipObj.GetValueEx("payeverymon",2,"",0,""); 
	var jxjs_15 = aipObj.GetValueEx("isagree",2,"",0,""); 
	var jxjs_16 = aipObj.GetValueEx("reason",2,"",0,""); 
	var fdobj = document.getElementById("flowdraftid");
	var flowdraftid = '';
	if(fdobj){
		flowdraftid = fdobj.value;
	}
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:"+jxjs_0);
	jxjsyj.push("jxjs_1:"+jxjs_1);
	jxjsyj.push("jxjs_2:"+jxjs_2);
	jxjsyj.push("jxjs_3:"+jxjs_3);
	jxjsyj.push("jxjs_4:"+jxjs_4);
	jxjsyj.push("jxjs_5:"+jxjs_5);
	jxjsyj.push("jxjs_6:"+jxjs_6);
	jxjsyj.push("jxjs_7:"+jxjs_7);
	jxjsyj.push("jxjs_8:"+jxjs_8);
	jxjsyj.push("jxjs_9:"+jxjs_9);
	jxjsyj.push("jxjs_10:"+jxjs_10);
	jxjsyj.push("jxjs_11:"+jxjs_11);
	jxjsyj.push("jxjs_12:"+jxjs_12);
	jxjsyj.push("jxjs_13:"+jxjs_13);
	jxjsyj.push("jxjs_14:"+jxjs_14);
	jxjsyj.push("jxjs_15:"+jxjs_15);
	jxjsyj.push("jxjs_16:"+jxjs_16);
	jxjsyj.push("flowdraftid:"+flowdraftid);
	url = "<%=path%>/fytanChuOpinionPage.action?jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1","");
		aipObj.SetValue("Page1.jxjs_2","");
		aipObj.SetValue("Page1.jxjs_3","");
		aipObj.SetValue("Page1.jxjs_4","");
		aipObj.SetValue("Page1.jxjs_5","");
		aipObj.SetValue("Page1.jxjs_6","");
		aipObj.SetValue("Page1.jxjs_7","");
		aipObj.SetValue("Page1.jxjs_8","");
		aipObj.SetValue("Page1.jxjs_9","");
		aipObj.SetValue("Page1.jxjs_10","");
		aipObj.SetValue("Page1.jxjs_11","");
		aipObj.SetValue("Page1.jxjs_12","");
		aipObj.SetValue("Page1.jxjs_13","");
		aipObj.SetValue("Page1.payeverymon","");
		aipObj.SetValue("Page1.isagree","");
		aipObj.SetValue("Page1.reason","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1",vRet[0]);
		aipObj.SetValue("Page1.jxjs_2",vRet[1]);
		aipObj.SetValue("Page1.jxjs_3",vRet[2]);
		aipObj.SetValue("Page1.jxjs_4",vRet[3]);
		aipObj.SetValue("Page1.jxjs_5",vRet[4]);
		aipObj.SetValue("Page1.jxjs_6",vRet[5]);
		aipObj.SetValue("Page1.jxjs_7",vRet[6]);
		aipObj.SetValue("Page1.jxjs_8",vRet[7]);
		aipObj.SetValue("Page1.jxjs_9",vRet[8]);
		aipObj.SetValue("Page1.jxjs_10",vRet[9]);
		aipObj.SetValue("Page1.jxjs_11",vRet[10]);
		aipObj.SetValue("Page1.jxjs_12",vRet[11]);
		aipObj.SetValue("Page1.jxjs_13",vRet[12]);
		aipObj.SetValue("Page1.payeverymon",jxjs_14);
		aipObj.SetValue("Page1.isagree",vRet[13]);
		aipObj.SetValue("Page1.iscommitagree",vRet[13]);
		aipObj.SetValue("Page1.reason",vRet[14]);
		if(vRet[0]=='1'){
			aipObj.SetValue("Page1.jxorjs","");
			aipObj.SetValue("Page1.jxorjs","罪犯假释审批表");
		}else{
			aipObj.SetValue("Page1.jxorjs","");
			aipObj.SetValue("Page1.jxorjs","罪犯减刑审批表");
		}
		//减刑假释法院意见选择----------
		if(strName.indexOf('fysuggest2')!=-1){
			//清空意见
			aipObj.SetValue("fysuggest2","");
			aipObj.SetValue("fysuggest3","");
			aipObj.SetValue("fysuggest4","");
			aipObj.SetValue("fysuggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				 var templet;
			    if(vRet[8]!='') {//审批意见不为空
			    	templet = vRet[15];
			    	aipObj.SetValue("fysuggest2",templet);
			    } else if(vRet[6]) {
		            templet = vRet[6];
		        	if(templet.indexOf("[理由]") >= 0) {
		        		templet = templet.replace("[理由]",vRet[7]);
		        	}
		        	if(templet.indexOf("[建议减刑]") >= 0) {
		        		templet = templet.replace("[建议减刑]",vRet[18]);
		        	}
		        	if(templet.indexOf("[剥权]") >= 0) {
		        		templet = templet.replace("[剥权]",vRet[17]);
		        	}
		        	if(templet.indexOf("[罚金]") >= 0) {
		        		templet = templet.replace("[罚金]",vRet[5]);
		        	}
		        	aipObj.SetValue("fysuggest2",templet);
		        } else {
		        	templet = vRet[15];
		        	aipObj.SetValue("fysuggest2",templet);
		        }		
				aipObj.SetValue("fysuggest3",hyFySuggest + vRet[11]);
				//aipObj.SetValue("fysuggest4",ldFySuggest + templet);
				//aipObj.SetValue("fysuggest5",ld2FySuggest + templet);
				aipObj.SetValue("commenttext",cbFySuggest + templet);
				document.getElementById("commenttext").value=cbFySuggest + templet;
			}
		}
		//减刑假释法院意见选择----------
		if(strName.indexOf('fysuggest3')!=-1){
			//清空意见
			aipObj.SetValue("fysuggest3","");
			aipObj.SetValue("fysuggest4","");
			aipObj.SetValue("fysuggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				 var templet;
			    if(vRet[8]!='') {
			    	templet = vRet[15];
			    	aipObj.SetValue("fysuggest3",templet);
			    } else if(vRet[6]) {
		            templet = vRet[6];
		        	if(templet.indexOf("[理由]") >= 0) {
		        		templet = templet.replace("[理由]",vRet[7]);
		        	}
		        	if(templet.indexOf("[建议减刑]") >= 0) {
		        		templet = templet.replace("[建议减刑]",vRet[18]);
		        	}
		        	if(templet.indexOf("[剥权]") >= 0) {
		        		templet = templet.replace("[剥权]",vRet[17]);
		        	}
		        	if(templet.indexOf("[罚金]") >= 0) {
		        		templet = templet.replace("[罚金]",vRet[5]);
		        	}
		        	aipObj.SetValue("fysuggest3",templet);
		        } else {
		        	templet = vRet[15];
		        	aipObj.SetValue("fysuggest3",templet);
		        }		
				//aipObj.SetValue("fysuggest4",ldFySuggest + templet);
				//aipObj.SetValue("fysuggest5",ld2FySuggest + templet);
				aipObj.SetValue("commenttext",hyFySuggest + templet);
				document.getElementById("commenttext").value = hyFySuggest + templet;
			}
		}
		//减刑假释法院意见选择----------
		if(strName.indexOf('fysuggest4')!=-1){
			//清空意见
			aipObj.SetValue("fysuggest4","");
			aipObj.SetValue("fysuggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				var templet;
			    if(vRet[8]!='') {
			    	templet = vRet[15];
			    	aipObj.SetValue("fysuggest4",templet);
			    } else if(vRet[6]) {
		            templet = vRet[6];
		        	if(templet.indexOf("[理由]") >= 0) {
		        		templet = templet.replace("[理由]",vRet[7]);
		        	}
		        	if(templet.indexOf("[建议减刑]") >= 0) {
		        		templet = templet.replace("[建议减刑]",vRet[18]);
		        	}
		        	if(templet.indexOf("[剥权]") >= 0) {
		        		templet = templet.replace("[剥权]",vRet[17]);
		        	}
		        	if(templet.indexOf("[罚金]") >= 0) {
		        		templet = templet.replace("[罚金]",vRet[5]);
		        	}
		        	aipObj.SetValue("fysuggest4",templet);
		        } else {
		        	templet = vRet[15];
		        	aipObj.SetValue("fysuggest4",templet);
		        }		
				//aipObj.SetValue("fysuggest5",ld2FySuggest + templet);
				aipObj.SetValue("commenttext",ldFySuggest + templet);
				document.getElementById("commenttext").value = ldFySuggest + templet;
			}
		}
		//减刑假释法院意见2选择----------
		if(strName.indexOf('fysuggest5')!=-1){
			//清空意见
			aipObj.SetValue("fysuggest5","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				var templet;
			    if(vRet[8]!='') {
			    	templet = vRet[15];
			    	aipObj.SetValue("fysuggest5",templet);
			    } else if(vRet[6]) {
		            templet = vRet[6];
		        	if(templet.indexOf("[理由]") >= 0) {
		        		templet = templet.replace("[理由]",vRet[7]);
		        	}
		        	if(templet.indexOf("[建议减刑]") >= 0) {
		        		templet = templet.replace("[建议减刑]",vRet[15]);
		        	}
		        	aipObj.SetValue("fysuggest5",templet);
		        } else {
		        	templet = vRet[15];
		        	aipObj.SetValue("fysuggest5",templet);
		        }		
				aipObj.SetValue("commenttext",ld2FySuggest + templet);
				document.getElementById("commenttext").value = ld2FySuggest + templet;
			}
		}
	}
}
	//检查机关出庭意见弹出窗
function jxjsJianChaJiGuanDanChu(aipObj, strName){
	var cbFySuggest = '';
	var hyFySuggest = '';
	var ldFySuggest = '';
	var ld2FySuggest = '';
	var fysuggest2 = aipObj.GetValueEx("fysuggest2",2,"",0,"");
	var fysuggest3 = aipObj.GetValueEx("fysuggest3",2,"",0,"");
	var fysuggest4 = aipObj.GetValueEx("fysuggest4",2,"",0,"");
	var fysuggest5 = aipObj.GetValueEx("fysuggest5",2,"",0,"");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0",2,"",0,""); //刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1",2,"",0,""); 
	var jxjs_2 = aipObj.GetValueEx("jxjs_2",2,"",0,""); 
	var jxjs_3 = aipObj.GetValueEx("jxjs_3",2,"",0,""); 
	var jxjs_4 = aipObj.GetValueEx("jxjs_4",2,"",0,""); 
	var jxjs_5 = aipObj.GetValueEx("jxjs_5",2,"",0,""); 
	var jxjs_6 = aipObj.GetValueEx("jxjs_6",2,"",0,""); 
	var jxjs_7 = aipObj.GetValueEx("jxjs_7",2,"",0,""); 
	var jxjs_8 = aipObj.GetValueEx("jxjs_8",2,"",0,""); 
	var jxjs_9 = aipObj.GetValueEx("jxjs_9",2,"",0,""); 
	var jxjs_10 = aipObj.GetValueEx("jxjs_10",2,"",0,""); 
	var jxjs_11 = aipObj.GetValueEx("jxjs_11",2,"",0,""); 
	var jxjs_12 = aipObj.GetValueEx("jxjs_12",2,"",0,""); 
	var jxjs_13 = aipObj.GetValueEx("jxjs_13",2,"",0,""); 
	var jxjs_14 = aipObj.GetValueEx("payeverymon",2,"",0,""); 
	var jxjs_15 = aipObj.GetValueEx("isagree",2,"",0,""); 
	var jxjs_16 = aipObj.GetValueEx("reason",2,"",0,""); 
	var fdobj = document.getElementById("flowdraftid");
	var flowdraftid = '';
	if(fdobj){
		flowdraftid = fdobj.value;
	}
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:"+jxjs_0);
	jxjsyj.push("jxjs_1:"+jxjs_1);
	jxjsyj.push("jxjs_2:"+jxjs_2);
	jxjsyj.push("jxjs_3:"+jxjs_3);
	jxjsyj.push("jxjs_4:"+jxjs_4);
	jxjsyj.push("jxjs_5:"+jxjs_5);
	jxjsyj.push("jxjs_6:"+jxjs_6);
	jxjsyj.push("jxjs_7:"+jxjs_7);
	jxjsyj.push("jxjs_8:"+jxjs_8);
	jxjsyj.push("jxjs_9:"+jxjs_9);
	jxjsyj.push("jxjs_10:"+jxjs_10);
	jxjsyj.push("jxjs_11:"+jxjs_11);
	jxjsyj.push("jxjs_12:"+jxjs_12);
	jxjsyj.push("jxjs_13:"+jxjs_13);
	jxjsyj.push("jxjs_14:"+jxjs_14);
	jxjsyj.push("jxjs_15:"+jxjs_15);
	jxjsyj.push("jxjs_16:"+jxjs_16);
	jxjsyj.push("flowdraftid:"+flowdraftid);
	url = "<%=path%>/fytanChuOpinionPage.action?jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if(vRet){
		//清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1","");
		aipObj.SetValue("Page1.jxjs_2","");
		aipObj.SetValue("Page1.jxjs_3","");
		aipObj.SetValue("Page1.jxjs_4","");
		aipObj.SetValue("Page1.jxjs_5","");
		aipObj.SetValue("Page1.jxjs_6","");
		aipObj.SetValue("Page1.jxjs_7","");
		aipObj.SetValue("Page1.jxjs_8","");
		aipObj.SetValue("Page1.jxjs_9","");
		aipObj.SetValue("Page1.jxjs_10","");
		aipObj.SetValue("Page1.jxjs_11","");
		aipObj.SetValue("Page1.jxjs_12","");
		aipObj.SetValue("Page1.jxjs_13","");
		aipObj.SetValue("Page1.payeverymon","");
		aipObj.SetValue("Page1.isagree","");
		aipObj.SetValue("Page1.reason","");
		//返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1",vRet[0]);
		aipObj.SetValue("Page1.jxjs_2",vRet[1]);
		aipObj.SetValue("Page1.jxjs_3",vRet[2]);
		aipObj.SetValue("Page1.jxjs_4",vRet[3]);
		aipObj.SetValue("Page1.jxjs_5",vRet[4]);
		aipObj.SetValue("Page1.jxjs_6",vRet[5]);
		aipObj.SetValue("Page1.jxjs_7",vRet[6]);
		aipObj.SetValue("Page1.jxjs_8",vRet[7]);
		aipObj.SetValue("Page1.jxjs_9",vRet[8]);
		aipObj.SetValue("Page1.jxjs_10",vRet[9]);
		aipObj.SetValue("Page1.jxjs_11",vRet[10]);
		aipObj.SetValue("Page1.jxjs_12",vRet[11]);
		aipObj.SetValue("Page1.jxjs_13",vRet[12]);
		aipObj.SetValue("Page1.payeverymon",jxjs_14);
		aipObj.SetValue("Page1.isagree",vRet[13]);
		aipObj.SetValue("Page1.iscommitagree",vRet[13]);
		aipObj.SetValue("Page1.reason",vRet[14]);
		if(strName.indexOf('prosecutor')!=-1){
			//清空意见
			aipObj.SetValue("prosecutor","");
			aipObj.SetValue("commenttext","");
			//填充意见信息
			if(vRet[15]){
				var templet;
				if(vRet[8]!='') {
					templet = vRet[15];
			    	aipObj.SetValue("prosecutor",templet);
			    } else if(vRet[6]) {
		            templet = vRet[6];
		        	if(templet.indexOf("[理由]") >= 0) {
		        		templet = templet.replace("[理由]",vRet[7]);
		        	}
		        	if(templet.indexOf("[建议减刑]") >= 0) {
		        		templet = templet.replace("[建议减刑]",vRet[18]);
		        	}
		        	if(templet.indexOf("[剥权]") >= 0) {
		        		templet = templet.replace("[剥权]",vRet[17]);
		        	}
		        	if(templet.indexOf("[罚金]") >= 0) {
		        		templet = templet.replace("[罚金]",vRet[5]);
		        	}
		        	aipObj.SetValue("prosecutor",templet);
		        } else {
		        	templet = vRet[15];
		        	aipObj.SetValue("prosecutor",templet);
		        }
				aipObj.SetValue("commenttext",hyFySuggest + templet);
				document.getElementById("commenttext").value = hyFySuggest + templet;
			}
		}
	}
}
</SCRIPT> 