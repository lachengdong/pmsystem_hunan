function callBack(){
	history.back(-1);
}
function callBack2(){
	window.location.href=path+"/YwgkFront/toListPage.page?1=1&modeltype=WoYaoGouWu&topId=WoYaoGouWu";
}
//xianshishijian
var dt = new Date();
function checkTime(i)
{
if (i<10) 
  {i="0" + i}
  return i
}
function month(i){
	i+=1;
	return i;
}
setInterval("$('#time').html(new Date().getFullYear() + '年' + month(new Date().getMonth()) + '月' + new Date().getDate() + '日 ' + checkTime(new Date().getHours()) + ':' + checkTime(new Date().getMinutes()) + ':' + checkTime(new Date().getSeconds())+' 星期'+'日一二三四五六'.charAt(new Date().getDay()));",1);

$(document).ready(function(){
  $(".ids").mousedown(function(){
    $(this).addClass('ids2');
  });  
  $(".ids").mouseout(function(){
    $(this).removeClass('ids2');
  }); 
});

//******ScreenSaver*******
function ScreenSaver(settings){  
    this.settings = settings;     
    
    this.nTimeout = this.settings.timeout;     
    document.body.screenSaver = this;     
    document.body.onmousemove = ScreenSaver.prototype.onevent;     
    document.body.onmousedown = ScreenSaver.prototype.onevent;     
    document.body.onkeydown = ScreenSaver.prototype.onevent;     
    document.body.onkeypress = ScreenSaver.prototype.onevent;     
         
    var pThis = this;     
    var f = function(){pThis.timeout();}     
    this.timerID = window.setTimeout(f, this.nTimeout);     
}     
ScreenSaver.prototype.timeout = function(){  
	var topId = $("#topId").val();
	var url =path+"/YwgkFront/toYwgkIndexPage.page"; 
	if(topId && topId.indexOf("family") != -1)
	url = path+"/YwgkFront/toYwgkIndexPage_family.page"; 
	
    if ( !this.saver ){     
        window.location = url;     
    }     
}     
ScreenSaver.prototype.signal = function(){     
    if ( this.saver ){     
        this.saver.stop();     
    }     
         
    window.clearTimeout(this.timerID);     
         
    var pThis = this;     
    var f = function(){pThis.timeout();}     
    this.timerID = window.setTimeout(f, this.nTimeout);     
}     
    
ScreenSaver.prototype.onevent = function(e){     
    this.screenSaver.signal();     
}     
    
    
var saver;     
function initScreenSaver(){ 
    saver = new ScreenSaver({timeout:600000});   //1000=1miao  
}     
 window.onload=function(){
	initScreenSaver();
}
//*********ScreenSaver***********

//*********pageDown/pageUp**************
function scrollTo(type,contentid){
	
	var scrollobj = document.getElementById(contentid);
	if(type == 'top'){
		scrollobj.scrollTop = scrollobj.scrollTop-scrollobj.offsetHeight;//scrollobj.scrollHeight
		if(scrollobj.scrollTop==0){
			setTimeout('$("#xianshi").html("已经到顶了")',300);
			setTimeout('$("#xianshi").html("")',1000);
		}
	}else{
		scrollobj.scrollTop = scrollobj.scrollTop+scrollobj.offsetHeight;
		if(scrollobj.scrollTop==document.getElementById(contentid).scrollHeight-scrollobj.offsetHeight){
			setTimeout('$("#xianshi").html("已经到底了")',300);
			setTimeout('$("#xianshi").html("")',1000);
		}
	}
}

//模块选中颜色控制和回显
var  arrayObj = new Array();
function change(id,name){
	$("#"+id).toggleClass('a3');
	aipObj.SetValue("modelname","");
	aipObj.SetValue("modelname",name);
    var docconent = aipObj.GetCurrFileBase64();
	$("#divid").html(aipObj);
	if($("#"+id).hasClass('a3')){
	    $(".a3").find("input[type='hidden']").each(function(){
	        var val = $(this).val();
	        if(val){
	        	arrayObj.push(val+"@"+docconent);
	        }
	    })
    }else{
    	var removeid = $("#"+id).find("input[type='hidden']").val();
	    returnFlag(arrayObj,removeid);
	}
    $("#moduleids").val(arrayObj.distinct());
}
//判断数组是否存在
 function returnFlag(arrayObj,id){
	var i = arrayObj.length; 
 	while( i-- ) {
 		var ind = arrayObj[i].lastIndexOf("@");
 		if(ind==-1){
 			if(arrayObj[i]== id){
 				arrayObj.splice(i,1);//删除数组的某项
 			}
 		}else {
 		var arr=arrayObj[i].substring(0,ind);			
		if((arr== id)){
			arrayObj.splice(i,1);//删除数组的某项
		}
 		}
	}  
 }
 //提交时去除form内重复元素
 Array.prototype.distinct = function(){
	 var arr = [], obj = {}, i = 0, len = this.length, result;
	 for( ; i < len; i++ ){
		    var ind = this[i].lastIndexOf("@");
	 		var ar=this[i].substring(0,ind);
	 		if(ind==-1){
	 			result = this[i];
				  if( obj[result] !== result ){
				   arr.push( result );
				   obj[result] = result;
				  }
	 		}else{
		  result = ar;
		  if( obj[result] !== result ){
		   arr.push( this[i] );
		   obj[result] = result;
		  }
	   }
	 }
	 return arr;
};
function nextStep(){
 	var moduleids = $("#moduleids").val();
   	if(moduleids){
		var queryForm = document.getElementById("queryForm");
		queryForm.submit();
	}else{
		alert("请选择模块！");
	 	return;
	}
}
//控制选中的部门单位 
$(document).ready(function(){
	$(".znhd_three_cons a").click( 
		function() {
        //$(this).addClass("background","#FF0000").siblings().css("background","#ffffff"); 
        $(this).addClass('jianqs2').siblings().removeClass('jianqs2'); // 设置被点击元素为红色  并去除所有同胞元素的红色样式
		//$(this).siblings().removeClass('jianqs2'); // 去除所有同胞元素的红色样式
	});
});