var maskmessage1 = "未读取到身份信息！";
var maskmessage2 = "读取失败，请放入卡片重新结算！";
var maskmessage3 = "此身份关联多个人，请联系相关人员进行核查！";
var maskmessage4 = "读取信息中，请等待...";
var maskmessage5 = "未找到卡片，请把卡片放入感应区...";
var intervalTime = 500;

/*验证*/
function validateUser(){
	var Carts = new CartHelper().Read(); 
    var carts=encodeURI(JSON.stringify(Carts));
	$.ajax({
		url: path+"/YwgkGoods/cookieComp.action?1=1",	
	    data:"carts=" + carts,  
	    dataType:"json",
	    type:"post",
	    cache: false,
	    success:function(data){ 
	    	if(data.messg!=null)
	    	{
	    		alert(data.messg);
	    		var Carts=data.carts; 
	    		var carts = new CartHelper().Save(Carts);
	    		var Cartss = new CartHelper().Read(); 
	    		listCarts(Cartss);	
	    	}
	    	else
	    	{
	    		$("#sk").html('<div  class="popWarp_message1"></div>');
	    		loadSdrdCard();
				setTimeout("getCard()",intervalTime);
	    	}
		},
		
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("数据异常！！");
		}	
	});	
}	

function getCard(){
	var st;
	st = initCarid();
	if(st != 0){
		return;
	}else{
		var cardparam = $("#cardparam").val();
		var cardId = m1CardTest(cardparam,st);
		if(cardId && cardId.length>=10){
			$.ajax({
			   	url: path+"/YwgkFront/getCardUser.json?1=1&cardId="+cardId,
			   	type: "post",
			   	dataType:"json",
				async:false,
			   	success: function (text) {
				  var msg = text;
				  if(msg == 1){
						toJieSuan();
				  }else if(msg == 2){
					  	alert(maskmessage3);
	           	  }else {
	           		  	alert(maskmessage1);
				  }
			   }
			});
		}else{
			setTimeout("getCard()",intervalTime);
		}
	}
}

function showOrHide(trueOrFalse){
	if(trueOrFalse){
		$("#background").show();
		$("#swipingcard").show();
	}else{
		$("#background").hide();
		$("#swipingcard").hide();
	}
}
function zwsp_ts(){
	setTimeout('$("#zwsp_ts").html("暂无商品！")',300);
	setTimeout('$("#zwsp_ts").html("")',1000);
}
