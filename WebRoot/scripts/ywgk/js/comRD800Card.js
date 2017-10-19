var maskmessage1 = "未读取到身份信息！";
var maskmessage3 = "此身份关联多个人，请联系相关人员进行核查！";
var maskmessage4 = "读取信息中，请等待...";
var maskmessage5 = "未找到卡片，请把卡片放入感应区...";
var intervalTime = 500;
var timer = setInterval("validateUser()",intervalTime);
function validateUser(){
	loadSdrdCard();
	var st;
	st = initCarid();
	if(st != 0){
		showMessage(maskmessage5);
		return;
	}else{
		var cardparam = $("#cardparam").val();
		var cardId = m1CardTest(cardparam,st);
		if(cardId && cardId.length>=10){
			getCard(cardId);
		}else{
			showMessage(maskmessage1);
		}
	}
}
function getCard(cardId){
	//根据类型判断 是犯人胸卡牌还是家属身份证 传递不同的参数
	if(cardId && cardId.length>=10){
		st = rd.dc_beep(5);
		$("#maskid").hide();
		$.ajax({
		   	url: path+"/YwgkFront/getCardUser.json?1=1&cardId="+cardId,
		   	type: "post",
		   	dataType:"json",
			async:false,
		   	success: function (text) {
			  var msg = text;
			  if(msg == 1){
					toSwingCard();
			  }else if(msg == 2){
				  	showMessage(maskmessage1);
				    alert(maskmessage3);
				  	clearInterval(timer);
           	  }else {
           		  	//showMessage(maskmessage1);
           		  	alert(maskmessage1);
           		    window.location.href= "toYwgkIndexPage.page";
			  }
		   }
		});
		//
	}else{
		showMessage(maskmessage4);
	}
	rd.dc_exit();//关闭端口
}
function toSwingCard(){
	var queryForm = document.getElementById("queryForm");
	queryForm.submit();
}
/*加载控件*/
function loadSdrdCard() {
	var s = "";
	s += '<OBJECT id=rd codeBase="./download/comRD800.cab" WIDTH="0" HEIGHT="0" classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>';
	document.getElementById("sdrdCardId").innerHTML = s;
}
//提示信息
function showMessage(maskmessage){
	$("#maskid").html(maskmessage);
	$('.mask_content').fadeIn(400);
}
