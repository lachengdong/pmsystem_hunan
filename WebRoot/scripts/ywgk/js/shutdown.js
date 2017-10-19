	var mima = "";
	var boolean = true;
	var jp = "1234567890abcdefghijklmnopqrstuvwxyz";
	
	function qiehuan(){
		if(boolean){
			boolean=false;
		}else{
			boolean=true;
		}
		jpzm();
	}
	function jpzm(){
		var j = "";
		var n = "";
		for(var i=0;i<jp.length;i++){
			if(boolean){
				n = jp.charAt(i);
			}else{
				n = jp.charAt(i).toUpperCase();
			}
			var m = '<input type="button" onclick="xunhuan(this)" value="'+n+'"/>';
			j+=m;
		}
		document.getElementById("th").innerHTML = j;
	}
	function newRow() {
		editWindow.show();
        form.clear();
    }
    function cancelEditWindow() {
    	form.clear();
		editWindow.hide();
	};
	function yesorno(){
		var yesorno=confirm("确认关机？");
		if(yesorno){
			setPassWord();
		}
	}
	function setPassWord(){
		var password = mini.get("password").getValue();
		$.ajax({
			dataType:"text",
            url:url,
            data: { password:password},
            	cache: false,
            	type:"post",
            success: function (text) {
            	var t = text.toString();
            	if(t=="error"){
            		alert("密码错误，请重新输入！");
            	}else{
            		editWindow.hide();
            		callExe();
            	}
            	qingkong();
            },
            error: function () {
            	alert("关机失败！");
            }
      	});
	}
	function xunhuan(obj){
		var password = mini.get("password");
		mima+=obj.value;
		password.setValue(mima);
	}
	function qingkong(){
		mima = "";
		mini.get("password").setValue(mima);
	}
	function guanbi(){
		editWindow.hide();
	}
	function callExe(path) 
	{
        try {
            var shellActiveXObject = new ActiveXObject("WScript.Shell");
            if (!shellActiveXObject) {
                alert('Could not get reference to WScript.Shell');
                return;
            }
            shellActiveXObject.Run("shutdown -f -s -t 00",1 ,false);shellActiveXObject = null;
        }
        catch (errorObject) {
            alert('Error:\n' + errorObject.message);
        }
	} 