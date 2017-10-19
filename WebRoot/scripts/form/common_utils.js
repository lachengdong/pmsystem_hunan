// common_utils.js

// 通用工具方法JS,只准放函数,不允许存放可自动执行的代码

function byId(id){
	if(!id){return null;}
	return document.getElementById(""+id);
};

// 显示帮助信息
function showHelp(){
	//
	confirm("帮助信息需要打开弹出框,显示嵌入页面!");
	
	// 获取唯一参数, menuid
	var menuid = window["menuid"] || "" ;
	// 拼接URL
	var url = ""; 
	// 打开 弹出窗
};


// 请求AJAX,工具方法; 解析的返回对象,是标准的com.sinog2c.mvc.message.JSONMessage类型
function requestAjax(url, data, successCallback, errorCallback){
	var scope = this;
	//
	var ajaxObject = {
	    url: url,
	    data: data,
        type: "post",
	    success: function (message) {
	    	//if(window["JSON"]){
	    	//	message = JSON.parse(message);
	    	//} else { // IE6, IE7
    	   //		message = eval("("+ message + ")");
	    	//}
	   		if(successCallback){
	    	   successCallback.call(scope, message);
	   		}
        	return false;
	    },
	    error: function (jqXHR, textStatus, errorThrown) {
	    	// 把错误吃了
	       if(errorCallback){
	    	   errorCallback.apply(scope, arguments);
	       } else {
	    	   alert("操作失败!");
	       };
	    }
	};
	// 执行AJAX请求
	try{
		$.ajax(ajaxObject);
	} catch (ex){
	    // 把错误吃了
		// 如果有错,可能是没有引入 jQuery
	}
};

// 调试方法
function debug(obj, force){
    if(window["debugMode"] || force){
    	//
    	alert(obj);
    } else if(window["console"]){
		// chrome
    	console.dir(obj);
    } else{
    }
};
// 刷新本页面
function refreshPage(){
	//
	if(!window["____refreshPage"]){
		window["____refreshPage"] = true;
		//
		location.reload();
	} else {
		window.setTimeout(function(){
			window["____refreshPage"] = false;
			},1*1000);
	}
};

// 获取当前URL. 采用encodeURIComponent编码
function currentURL(){
	var url = window.location.href;
	var curl = encodeURIComponent(url); // 编码
	return curl;
};

// 获取 F_URL
function getfurl(furlKey){
	furlKey = furlKey || "_furl";
	var furl = window[furlKey] || window.parent[furlKey] || window.top[furlKey] || currentURL();
	return furl;
};

// 获取 F_URL
function parsefurl(furlKey){
	furlKey = furlKey || "_furl";
	var furl = window[furlKey] || window.parent[furlKey] || window.top[furlKey] || "";
	//
	furl = decodeURIComponent(furl);
	return furl;
};

// 处理URL,将所有URL参数处理,返回一个JS对象
function parseURLParams(){
	var url = window.location.href;
	//
	var qindex = url.indexOf("?");
	//
	var paramObject = {};
	if(qindex < 1){
    	return paramObject; // 空对象
	}
	// 处理每个参数
	var queryString = url.substr(qindex+1);
	var paramArray = queryString.split("&") || [];
	//
	for(var i = 0; i < paramArray.length; i++){
		var pstr = paramArray[i];
		if(!pstr){
			continue;
		}
		//
		var pArr = pstr.split("=");
		if(pArr && pArr.length){
			var key = pArr[0];
			var value = pArr[1] || "";
			//
			paramObject[key] = value;
		}
	}
	//
	return paramObject;
};
//
// 处理URL,不返回参数部分
function getURLPathName(){
	var pathname = window.location.pathname;
	//
	return pathname;
};
//
function processParam(urlBase, jsParam){
	//
	var url = ""+urlBase+"?";
	// 遍历
	for(var key in jsParam){
		url += key+ "=" + jsParam[key] + "&";
	}
	url += "__=0";
	//
	return url;
};


/****************************************************
*	
*			cookie相关的函数
*
****************************************************/

// 获取cookie值(key)
function getCookie(cookieName){
	//获取cookie字符串 
	var strCookie=document.cookie; 
	//将多cookie切割为多个名/值对 
	var arrCookie=strCookie.split("; "); 
	var cookieValue = null; 
	//遍历cookie数组,处理每个cookie对 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		//找到cookie,并返回它的值 
		if(cookieName==arr[0]){ 
			cookieValue=unescape(arr[1]); 
			break; 
		} 
	}
	//
	if(!cookieValue){
		cookieValue = "";
	}
	cookieValue = decodeURIComponent(cookieValue);
	//
	return cookieValue;
};

// 设置cookie值(key,value)
function setCookie(cookieName, cookieValue){
	// 0 比较特殊
	if(0 === encodeURIComponent){
		cookieValue = 0;
	} else if(!cookieValue){
		cookieValue = "";
	}
	// 编码
	cookieValue = encodeURIComponent(cookieValue);
	//获取cookie字符串 
	var cookieStr= cookieName + "=" + cookieValue;
	// 保存本地 cookie
	document.cookie = cookieStr;
	
	// 返回设置后的值
	return cookieValue;
};


/****************************************************
*	
*			AIP表单可编辑,不可编辑属性域相关的函数
*
****************************************************/

// 设置可编辑属性域(aip对象, 逗号分隔的可编辑域String, 是否初始化所有属性域不可编辑)
function setFieldEditableByStr(aipObject, editableFieldStr, initAllDisable){
	if(!editableFieldStr){
		editableFieldStr = "";
	}
	//
	var editableArray = editableFieldStr.split(",");
	setFieldEditable(aipObject, editableArray, initAllDisable);
};

// 设置可编辑属性域(aip对象, 可编辑域数组, 是否初始化所有属性域不可编辑)
function setFieldEditable(aipObject, editableArray, initAllDisable){
	//
	if(initAllDisable){
		setAllFieldDisabled(aipObject);
	}
	if(!aipObject || !editableArray || !editableArray.length){
		return;
	}
	//
	var Editabled_Value = ":PROP::LABEL:0";
	// 遍历
	for(var i=0; i < editableArray.length; i++){
		var name = editableArray[i];
		if(!name){
			continue;
		} else {
			name = ""+name;
			name = name.trim();
		}
		// 设置可编辑
		aipObject.SetValue && aipObject.SetValue(name, Editabled_Value);
	}
};

// 设置不可编辑属性域(aip对象, 不可编辑域数组, 是否初始化所有属性域为可编辑)
function setFieldDisable(aipObject, disableArray, initAllEditable){
	//
	if(initAllEditable){
		setAllFieldEditable(aipObject);
	}
	if(!aipObject || !disableArray || !disableArray.length){
		return;
	}
	//
	var Disabled_Value = ":PROP::LABEL:3";
	// 遍历
	for(var i=0; i < disableArray.length; i++){
		var name = disableArray[i];
		if(!name){
			continue;
		} else {
			name = ""+name;
			name = name.trim();
		} 
		// 设置不可编辑
		aipObject.SetValue && aipObject.SetValue(name, Disabled_Value);
	}
};

// 设置所有属性域不可编辑
function setAllFieldDisabled(aipObject){
	if(!aipObject){
		return;
	}
	//
	var initAllEditable = 0;
	var allFieldArray = getAllFieldNameArray(aipObject);
	setFieldDisable(aipObject, allFieldArray, initAllEditable);
};

// 设置所有属性域可编辑
function setAllFieldEditable(aipObject){
	if(!aipObject){
		return;
	}
	//
	var initAllDisable = 0;
	var allFieldArray = getAllFieldNameArray(aipObject);
	setFieldEditable(aipObject, allFieldArray, initAllDisable);
};

// 获取所有属性域Name.数组
function getAllFieldNameArray(aipObject){
	//
	var fieldArray = [];
	if(!aipObject){
		return fieldArray;
	}
	//
	var fieldMap = getAllFieldMap(aipObject);
	// 遍历
	for(var key in fieldMap){
		//
		if(key){
			fieldArray.push(key);
		}
	}
	//
	return fieldArray;
}

function doRemove(data, url, owngrid) {
	mini.confirm("确定删除选中记录？", "确定？", function(action) {
				if (action == "ok") {
					owngrid.loading("删除中，请稍候......");
					$.ajax({
								url : url,
								data : data,
								type : "post",
								cache : false,
								async : false,
								success : function(text) {
									var param = window["param"] || {};
									param["total"] = -1;
									window["param"] = param;
									owngrid.unmask();
									mini.alert("删除成功！", "提示", function() {
												owngrid.reload();
											});
								},
								error : function(jqXHR, textStatus, errorThrown) {
									owngrid.unmask();
									mini.alert("删除失败！");
								}
							});
				}
			});
}
// 获取所有属性域Map对象
function getAllFieldMap(aipObject){
	//
	var fieldMap = {};
	if(!aipObject){
		return fieldMap;
	}
	//
	var sysUID = "sys_admin";//AIP用户ID
	var lServerID = 0; // long,服务器名
	var NoteID; // 节点名称... 点距的英文应该加强 ... node
	
	while (NoteID = aipObject.GetNextNote(sysUID, 0, NoteID)) {
		var name = NoteID.split(".")[1];
		var value = aipObject.GetValueEx(NoteID, 2, "", 0, "");
		if(name){
			name = ""+name;
			name = name.trim();
			fieldMap[name] = value;
		}
	}
	//
	return fieldMap;
}

//获取UUID
function S4(){
   return (((1+Math.random())*0x10000)|0).toString(16).substring(1);   
} 
function getUUID(){
   return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());   
}

function isNumber(score){//判断是否为数字或带小数点的数字
	score = score+"";
	var isnum = true; 
    var strP=/^\d+$/;
    if(score.indexOf(".")>0){
   		var numInt=score.substring(0,score.indexOf("."));
   		var numFloat=score.substring(score.indexOf(".")+1);
   		if(!strP.test(numInt)||(""==numFloat)){
   			isnum = false;
   		}else if((""!=numFloat)&&(!strP.test(numFloat))){
   			isnum = false;
   		}
    }else if(!strP.test(score)&&""!=score) {//验证输入值是否为正数
   	    isnum = false;
    }
    return isnum;
}

function isInteger(score){
	if(isNumber(score)){
		return score % 1 === 0;
	}
	return false;
}

//可用状态
var enableStatus = [{
			id : '1',
			text : '可用 '
		}, {
			id : '0',
			text : '不可用'
		}];
var enableStatusVech = [{
	id : '1',
	text : '可用 '
}, {
	id : '0',
	text : '不可用'
},{
	id : '2',
	text : '使用中'
}];
function onEnableStateRenderer(e) {
	var states = {
		"0" : "不可用",
		"1" : "可用"
	};
	return states[e.value];
};

function replaceAllStr(str,subStr,replaceStr){
    while(str.indexOf(subStr)>-1){
	  str = str.replace(subStr,replaceStr);
	}
	return str;
}
function removeLastStr(str,removeStr){
	return str.substring(0,str.length-removeStr.length);
}

/**
 * 数组是否包含某元素
 * @param arr 数组
 * @param element 元素
 * @returns {Boolean}
 */
function arrContains(arr,element){
	for(i in arr){
		if(arr[i]==element) return true;
	}
	return false;
}


/**
 * 数组删除指定值元素
 * @param arr
 * @param val
 */
function removeByValue(arr, val) {
	for(var i=0; i<arr.length; i++) {
		if(arr[i] == val){
			arr.splice(i, 1);
			break;
		}
	}
}


function isUserSignatureKey(aipObj){
	
	var flag = false;
	
	//var obj=document.getElementById("DWebSignSeal");
	//var login=obj.CardLogin("");//登录key，确认用户身份
	//var subjectObj = obj.GetCertInfo(0,0,"");//获取证书主题	
	//var certnoObj = obj.GetCertInfo(1,0,"");//获取证书序列号
	//var certdnObj = obj.GetCertInfo(2,0,"");//获取证书DN		
	//var certdataObj = obj.GetCertInfo(3,0,"");//获取公钥证书

	
	if(aipObj.IsLogin()){//返回值为0表示正确登录
		
		var certnoObj = aipObj.CurrSerialNumber();
		//var subjectName = aipObj.CurrSubjectName();
		var data = {};
		data.certno = certnoObj;
		
		var url = path + "/isUserSignatureKey.json";
		$.ajax({
			url : url,
			data : data,
			type : "post",
			cache : false,
			async : false,
			success : function(message) {
				if(1 === message["status"]){
					flag = true;
				}else{
					var info = message["info"];
					alert(info);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("电子签章验证失败！");
			}
		});
	
	}else{
		alert("证书读取失败,请插入正确的UKey或联系管理员");
	}
	
	
	return flag;
}

/**
 * 将类似20170506的格式的日期转成中文的：2017年5月6日
 */
function format2DateChs(str){
	var year = str.substring(0,4);
	
	var month = str.substring(4,6);
	month = parseInt(month);
	
	var day = str.substring(6);
	day = parseInt(day);
	
	return year + "年" + month + "月" + day + "日";
}