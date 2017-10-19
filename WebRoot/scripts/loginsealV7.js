function makeSeal() {
	var s = "";
	s += "<OBJECT id=HWPostil1  align='middle'  style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 100%' CLASSID=CLSID:3F1A0364-AD32-4E2F-B550-14B878E2ECB1 codeBase='DownLoad/RMViewer.cab#version=3,1,3,6'> </OBJECT>";
	s += "<object  id='DWebSignSeal' classid='CLSID:77709A87-71F9-41AE-904F-886976F99E3E' codeBase='/DownLoad/WebSign.dll#version=4,4,9,8'></OBJECT>";
	document.getElementById("longinSeal").innerHTML = s;
}
function checkInputState() {
	//
	// 使用className属性(Dom元素,css类名)  
    function removeClassName(element,cName){  
        var className = element.className || "";  
  
        // 去掉首尾的空白  
        cName = cName.replace(/^\s*|\s*$/g,"");  
        // cName 中间如果含有空白字符，则失败. 如果要好好处理，可以拆分为数组，单个处理  
        var blankReg = /\s+/;  
        if(blankReg.test(cName)){
            return false;  
        }  
        // 正则， \b 表示可见连续字符的边界，可以这么理解:  
        // "hide2 hide hide myname" 那么，  
        // hide2 的前后各有一个虚拟的\b ,hide 前后也有，  
        // 但是 hi 和 de之间则没有。  
        // g 表示单行全局  
        //var rep = /\bhide\b/g;  
        var rep = new RegExp("\\b" + cName + "\\b", "g");  
        if(rep.test(className)){  
            className = className.replace(rep,"");  
        } else {  
            //className += " "+cName;  
        }  
        // 替换新className。  
        element.className = className;  
        return true;  
    };
	try {
		// 取得对应的元素
		if(document.querySelector){
			var usernameInput = document.querySelector("input[name=username]");
			var passwordInput = document.querySelector("input[name=password]");
			var errorMessage = document.querySelector(".errorMessage");
			var errorMessageWrap = document.querySelector(".errorMessageWrap");
		} else {
			//
			var usernameInput = document.getElementById("username");
			var passwordInput = document.getElementById("password");
			var errorMessage = document.getElementById("errorMessage");
			var errorMessageWrap = document.getElementById("errorMessageWrap");
		}
		//
		if (errorMessage) {
			errorMessage.innerHTML = ("");
		}
		//
		if (usernameInput) {
			var username = usernameInput.value;
			if (!username) {
				usernameInput.focus();
				//
				if (errorMessage) {
					errorMessage.innerHTML = ("请输入用户");
					!!errorMessageWrap
						&& errorMessageWrap.classList
							&& errorMessageWrap.classList.remove("hide");
					//
					if(errorMessageWrap){
						if(!errorMessageWrap.classList){
							// 手工操作
							removeClassName(errorMessageWrap,"hide");
						}
					}
				}
				return false;
			}
		} else{
			
		}
		if (passwordInput) {
			var password = passwordInput.value;
			if (!password) {
				passwordInput.focus();
				//
				if (errorMessage) {
					errorMessage.innerHTML = ("请输入密码");
					errorMessageWrap
							&& errorMessageWrap.classList
							&& errorMessageWrap.classList.remove("hide");
					//
					if(errorMessageWrap){
						if(!errorMessageWrap.classList){
							// 手工操作
							removeClassName(errorMessageWrap,"hide");
						}
					}
				}
				return false;
			}
		}
	} catch (ex) {
		// 可能是不支持新式校验
	}
	//document.loginform.submit();
	return true;
};
// 用户登录
function validateUser() {
	var validated = checkInputState();
	if (validated) {
		// 正常执行路径,如果没出错就跳到此处开始执行.
		//document.loginform.action = "login.action";
		document.loginform.submit();
	}
};
document.onkeydown = function(event) {
	var e = event ? event : (window.event ? window.event : null);
	// Enter 键
	if (e.keyCode == 13) {
		validateUser();
	}
}