$(function(){
	//在主页面上点击监狱简介显示监狱简介详细信息
$("#jyjj").click(function(){
	self.location.href="../ywgkIndexController/toDetailIndexPage.action?pagename=jyjj&moduleid=2&articleid=39";
      });	
$("#u4").click(function(){
	//self.location.href="../ywgkIndexController/toFrontIndexPage.action?pagename=jyjj";
	self.location.href="../ywgkIndexController/toDetailIndexPage.action?pagename=jyjj&moduleid=2&articleid=39";
      });	


//点击法律法规按钮显示法律法规信息列表
$("#flfg").click(function(){
	   //<a href="toListPage.page?1=1&moduleType=ygjy&modulename=flfg">
	self.location.href="../ywgkIndexController/toListPage.action?pagename=flfgList&modulename=flfg";
      });	


//点击投诉建议菜单显示监狱处理的罪犯的考核、刑期变动信息列表 
$("#tsjylist").click(function(){
	   //<a href="toListPage.page?1=1&moduleType=ygjy&modulename=flfg">
	self.location.href="../ywgk/toListPrisonOwnPage.action?page=toListPrisonOwnPage";
    });

//点击右上角的退出按钮，退出当前的session会话，界面返回到狱务公开首页
//点击法律法规显示法律法规列表
/*$("#u2").click(function(){
	alert("sdfsdfsfsfsdsfssdf");
	self.location.href="../ywgkIndexController/toFrontIndexPage.action?pagename=jyjj";
    });	*/


//在登录页面录入完数字之后，点击登录按钮
$("#qlingling").click(function(){
	var crimid=$("#crimid").val();
	var crimpassword=$("#crimpassword").val();
	var URL=$("#URL").attr("value");
	   //<a href="toListPage.page?1=1&moduleType=ygjy&modulename=flfg">
	self.location.href="../ywgkFrontLoginController/toYwgkLogin.action?crimid="+crimid+"&crimpassword="+crimpassword+"&URL="+URL;
  });


/**当鼠标在两个文本框中切换的时候分别获取对应的文本框的光标**/
var crimidtext="";
var crimidnumber="";
var crimpassword="";
$("#crimid").blur(function(){
	crimidtext="crimid";
	//alert("市区焦点！！");
});
$("#crimpassword").blur(function(){
	crimidtext="crimpassword";
	//alert("市区焦点！！");
});

/**当点击清零键时判断光标所在的文本框，并清空该文本框的数据 **/
$("#qlingling").click(function(){
	if(crimidtext=='crimid'){
		crimidnumber="";
		$("#crimid").attr("value","");
	}else if(crimidtext=='crimpassword'){
		crimpassword="";
		$("#crimpassword").attr("value","");
	}
	//alert("市区焦点！！");
});

$(".order").click(function(){
	if(crimidtext=='crimid'){
		crimidnumber =crimidnumber+$(this).text();
		$("#crimid").attr("value",crimidnumber);
	}else if(crimidtext=='crimpassword'){
		
		crimpassword =crimpassword+$(this).text();
		$("#crimpassword").attr("value",crimpassword);
	}
	
	var fillnunber="";
	fillnunber=$(this).text();
	
});



/*****  点击公开信息显示公开信息主页面   ***/
$("#gkxx").click(function(){
	self.location.href="../gkxx/toGkxxIndexPage.action?pagename=gkxxindex";
      });	

/***  点击刑罚执行下的 功能显示对应的信息列表**/

/***点击减刑假释显示减刑假释列表**/

$("#jxjs").click(function(){
	self.location.href="../gkxx/toGkxxIndexPage.action?pagename=gkxxindex";
      });


});