var maskmessage1 = "请在感应区放入卡片！";
var maskmessage2 = "未找到对应的身份信息，请先注册!...";
var maskmessage3 = "此身份关联多个人，请联系相关人员进行核查！";
var intervalTime = 1;
setTimeout("validateUser()",intervalTime);
function validateUser(){
	var state = rdcard.ReadCard2();//开始读卡
}
function getCard(){
	//alert(rdcard.readcard()+"-身份证1号："+rdcard.CardNo);
	//根据类型判断 是犯人胸卡牌还是家属身份证 传递不同的参数
	if(rdcard.CardNo){
		$("#maskid").hide();
		$.ajax({
		   	url: path+"/YwgkFront/getCardUser.json?1=1&idnumber="+rdcard.CardNo,
		   	type: "post",
		   	dataType:"json",
			async:false,
		   	success: function (text) {
			  var msg = text;
			  if(msg == 1){
					toSwingCard();
			  }else if(msg == 2){
					showMessage(maskmessage3);
           	  }else {
           		  	//showMessage(maskmessage1);
           		  	alert(maskmessage2);
           		  	location.reload();
			  }
		   }
		});//
		clearCardInfo();
	}
}
//关闭机器
function clearCardInfo(){
    var  pp = rdcard.ClearAll();
    pp=rdcard.readcard();
	rdcard.closeport();
}
function Unload(){
	rdcard.ReadCard2();
}
/*加载控件*/
function loadSdrdCard() {
	var s = "";
	s += '<OBJECT classid="clsid:F1317711-6BDE-4658-ABAA-39E31D3704D3" codebase="SDRdCard.cab#version=1,3,6,1" width=0 height=0 align=center hspace=0 vspace=0 id=idcard name=rdcard	></OBJECT>';
	document.getElementById("sdrdCardId").innerHTML = s;
}
//提示信息
function showMessage(maskmessage){
	$("#maskid").html(maskmessage);
	$('.mask_content2').fadeIn(400);
}
function toSwingCard(){
	var queryForm = document.getElementById("queryForm");
	queryForm.submit();
}
//查询码JS
var isflag = true;
var jp = "1234567890abcdefghijklmnopqrstuvwxyz";
var mima = "";
function keyboard(){
	var j = "";
	var n = "";
	var p='<input type="button" value="大写" style="margin-left:11px;" onclick="qiehuan()"/>'+
	'<input type="button" value="清空" style="margin-left:11px;" onclick="qingkong()"/>'+
	'<input type="button" value="关闭" style="margin-left:11px;" onclick="guanbi(),qingkong()"/>';
	for(var i=0;i<jp.length;i++){
		if(isflag){
			n = jp.charAt(i);
		}else{
			n = jp.charAt(i).toUpperCase();
		}
		var m = '<input type="button" onclick="xunhuan(this)" value="'+n+'"/>';
		j+=m;
	}
	j+=p;
	document.getElementById("keyboard").innerHTML = j;
	document.getElementById("chaXunID").value="";
	document.getElementById("chaXunID").value=mima;
}
function keyboardhide(){
	var k = '<span id="keyboard"></span>';
	document.getElementById("keyboard").innerHTML = k;
}
function qingkong(){
	mima = "";
	document.getElementById("chaXunID").value=mima;
}
function guanbi(){
	keyboardhide();
	location.reload();
}
function qiehuan(){
	if(isflag){
		isflag=false;
	}else{
		isflag=true;
	}
	keyboard();
	document.getElementById("chaXunID").value=mima;
}
function xunhuan(obj){
	mima+=obj.value;
	document.getElementById("chaXunID").value=mima;
}
function setchaxun(){
	if(mima.length==0){
		alert("密码不能为空！");
		guanbi();
	}else{
		var chaXunID = document.getElementById("chaXunID").value;
		var url= path+"/YwgkFront/getCardUser.json?1=1";
		$.ajax({
			dataType:"text",
            url:url,
            data: { idnumber:chaXunID},
            	cache: false,
            	type:"post",
            success: function (text) {
            	var t = text.toString();
            	if(t=="3"){
            		alert("查询码错误，请重新输入！");
            		qingkong();
            	}else{
            		alert("查询码登陆成功！");
            		location.reload();
            	}
            },
            error: function () {
            	alert("查询异常！");
            }
      	});
	}
}
