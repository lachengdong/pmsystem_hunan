/**
	*插入节点
	*strSignatureNoteInfo 一个签章节点的属性
	*sealcontent 默认签章意见
	*sealdate 签章页面日期
	*cppoopiniondate 签章方案里的日期
	*cppoopinion 从意见信息表里取的意见
	*/
	
    function InsertNote(strSignatureNoteInfo,aipObj,sealcontent,sealdate,cppoopiniondate,cppoopinion){//typeflag用来区分是什么节点
    	try
 	    {
 	    	//var aipObj = document.all.HWPostil1;
		    if(!aipObj.IsOpened()){
			    alert("必须打开文件，才可以操作");
			    return false;
		    }
	    //var sealcontent=document.getElementById("sealcontent").value;
	    var strSignatureNoteInfoArray = strSignatureNoteInfo.split(","); 
	    var theArray=new Array();// 将数组拆分成二维数组
	    var typeflag;
	    var yijian=0;
	    
	    for(var m=0;m<strSignatureNoteInfoArray.length;m++)
	    {
	    	theArray[m]=strSignatureNoteInfoArray[m].split(":");
	    }
	    for(var m=0;m<theArray.length;m++)
	    {
	    	if(theArray[m][0]=='节点类型')
	    	{
	    		typeflag=theArray[m][1];
	    	}
	    }
	    
	    if(typeflag==0){ //意见节点
	    	var vPageNum=1;//页码
	    	var vNoteValue="";
		    var vNoteTop = 42000;//上
		    var vNoteLeft = 34000;//左
		    var vNoteHeight = 3000;//高
		    var vNoteWidth = 3000;//宽
		    var vBORDER = ":PROP:BORDER:" + "0"; //边框格式
		    var vFontName = ":PROP:FACENAME:" + "仿宋_GB2312";//字体
		    var vFontSize = ":PROP:FONTSIZE:" + "12"; //字体大小
		    var vFontColor = ":PROP:FRONTCOLOR:" + "0";//字体颜色
		    var vFontBold = ":PROP:FONTBOLD:" + "0"; //字体粗体
	   	    var vFontItalic = ":PROP:FONTITALIC:" + "0"; //字体斜体
	   	    //var vBACKCOLOR =  ":PROP:BACKCOLOR:"+strSignatureNoteInfoArray[13];//背景框颜色
	   	    var tempvNoteName = "opinion22";
	    	for(var m=0;m<theArray.length;m++)
	    	{
	    		if(theArray[m][0]=='节点名称')
	    		{
	    			//tempvNoteName = "Page" + vPageNum +  "." + theArray[m][1];//节点名称
	    			tempvNoteName = theArray[m][1];//节点名称
	    		}
	    		if(theArray[m][0]=='页码')
	    		{
	    			vPageNum = parseInt(theArray[m][1]);//获取在那一页上添加区域
	    		}
	    		if(theArray[m][0]=='上')
	    		{
	    			vNoteTop = parseInt(theArray[m][1]);//上
	    		}
	    		if(theArray[m][0]=='左')
	    		{
	    			vNoteLeft = parseInt(theArray[m][1]);//左
	    		}
	    		if(theArray[m][0]=='高')
	    		{
	    			vNoteHeight = parseInt(theArray[m][1]);//高
	    		}
	    		if(theArray[m][0]=='宽')
	    		{
	    			vNoteWidth = parseInt(theArray[m][1]);//宽
	    		}
	    		if(theArray[m][0]=='边框类型')
	    		{
	    			vBORDER =":PROP:BORDER:" + parseInt(theArray[m][1]);//边框类型
	    		}
	    		if(theArray[m][0]=='字体')
	    		{
	    			vFontName = ":PROP:FACENAME:" + parseInt(theArray[m][1]);//字体
	    		}
	    		if(theArray[m][0]=='字体大小')
	    		{
	    			vFontSize = ":PROP:FONTSIZE:" + parseInt(theArray[m][1]);//字体大小
	    		}
	    		if(theArray[m][0]=='字体颜色')
	    		{
	    			vFontColor = ":PROP:FRONTCOLOR:" + parseInt(theArray[m][1]);//字体颜色
	    		}
	    		if(theArray[m][0]=='是否粗体')
	    		{
	    			vFontBold = ":PROP:FONTBOLD:" + parseInt(theArray[m][1]);//是否粗体
	    		}
	    		if(theArray[m][0]=='是否斜体')
	    		{
	    			vFontItalic = ":PROP:FONTITALIC:" + parseInt(theArray[m][1]);//是否斜体
	    		}
	    		if(theArray[m][0]=='意见来源')
	    		{
	    			yijian =parseInt(theArray[m][1]);//意见来源
	    		}
	    		if(theArray[m][0]=='意见内容')
	    		{
	    			sealcontent =theArray[m][1];//意见来源
	    		}
	    	}
	    	var vNoteName = "Page" + vPageNum +  "." +tempvNoteName;//节点名称
	    	
		    if(vPageNum < 1 || vPageNum > aipObj.PageCount){
			    return false;			
		    }
	   	    //var vBACKCOLOR =  ":PROP:BACKCOLOR:"+strSignatureNoteInfoArray[13];//背景框颜色
	   	    if(parseInt(yijian)==0)//从意见信息表取意见
	   	    {
	   	    	if(cppoopinion!=null)
	   	    	{
	   	    		vNoteValue=cppoopinion;
	   	    	}
	   	    }
	   	    if(parseInt(yijian)==1)//默认意见
	   	    {
	   	    	vNoteValue = sealcontent;//意见内容
	   	    }
		    var CurrPage = vPageNum-1;
		    
		    var abc = aipObj.DeleteNote(vNoteName);//删除节点   这个方法不起作作，（不影响现功能）后继再研究;
		    
		    aipObj.InsertNote(vNoteName,CurrPage,3,vNoteLeft,vNoteTop,vNoteWidth,vNoteHeight);//添加编辑域
		    aipObj.Setvalue(vNoteName,vBORDER);//设置边框格式
   		    aipObj.Setvalue(vNoteName,":PROP::LABEL:1"); //设置为不可编辑
   		    aipObj.Setvalue(vNoteName,":PROP:VALIGN:1");//设置竖直剧中
		    aipObj.Setvalue(vNoteName,vFontName);//设置字体
		    aipObj.Setvalue(vNoteName,vFontColor);//设置字体颜色
		    aipObj.Setvalue(vNoteName,vFontSize);//设置文字域字体大小
		    aipObj.Setvalue(vNoteName,vFontBold); //设置文字是否为BOLD	
		    aipObj.Setvalue(vNoteName,vFontItalic); //设置文字是否为斜体
		    //aipObj.Setvalue(vNoteName,vNoteBorder);//设置边框格式
		    aipObj.Setvalue(vNoteName,vFontBold);//设置粗体
		    //aipObj.Setvalue(vNoteName,vBACKCOLOR);//设置背景框颜色
		    aipObj.SetValue(vNoteName,"");
		    aipObj.Setvalue(vNoteName,vNoteValue);//设置值
		    aipObj.Getvalue(vNoteName);//通过此命令使签章信息生效
	    } else if(typeflag==1){ //盖章节点
		    	var pageSeal=1;//页码
		    	var sealTop = 42000;//上
			    var sealLeft = 34000;//左
		    	for(var m=0;m<theArray.length;m++)
	    		{
	    			if(theArray[m][0]=='页码')
		    		{
		    			pageSeal = parseInt(theArray[m][1]);//页码
		    		}
		    		if(theArray[m][0]=='上')
		    		{
		    			sealTop = parseInt(theArray[m][1]);//上
		    		}
		    		if(theArray[m][0]=='左')
		    		{
		    			sealLeft = parseInt(theArray[m][1]);//左
		    		}
		    	} 	
			    if(pageSeal < 1 || pageSeal > aipObj.PageCount){
				    return false;			
			    }
		    	 var param = (pageSeal -1) + "," + sealLeft + ",0," + sealTop;//页码，左，0，上
		    	 if(aipObj.IsLogin()){
                 	aipObj.AddQifengSeal(0,param,"","www");
		    	 }
                 aipObj.SetValue("www",":PROP:SHOWTIME:0");//:PROP:SHOWTIME:2   0不显示，  1 中间，2：底部 印章图片显示时间
	    }else if(typeflag == 2){ //日期节点
   		 	
		    var pageDate = 1;
			var dateTop = 42000;
		    var dateLeft = 34000;
		    var dateHight = 3000;
		    var dateWidth = 4000;
		    var dateValue="";
		    var dateFrom=0; //日期来源
		    var bigSmall=0; //日期大小写
		    var tempDateValue;//日期值
		    var vBORDER = ":PROP:BORDER:" + "0"; //边框格式
		    var vFontName = ":PROP:FACENAME:" + "仿宋_GB2312";//字体
		    var vFontSize = ":PROP:FONTSIZE:" + "12"; //字体大小
		    var vFontColor = ":PROP:FRONTCOLOR:" + "0";//字体颜色
		    var vFontBold = ":PROP:FONTBOLD:" + "0"; //字体粗体
	   	    var vFontItalic = ":PROP:FONTITALIC:" + "0"; //字体斜体
	   	    var tempPageDateName="date11";
		    for(var m=0;m<theArray.length;m++)
	    	{
	    		if(theArray[m][0]=='节点名称')
	    		{
	    			tempPageDateName=theArray[m][1];//节点名称
	    		}
	    		if(theArray[m][0]=='页码')
	    		{
	    			pageDate = parseInt(theArray[m][1]);//获取在那一页上添加区域
	    		}
	    		if(theArray[m][0]=='上')
	    		{
	    			dateTop = parseInt(theArray[m][1]);//上
	    		}
	    		if(theArray[m][0]=='左')
	    		{
	    			dateLeft = parseInt(theArray[m][1]);//左
	    		}
	    		if(theArray[m][0]=='高')
	    		{
	    			dateHight = parseInt(theArray[m][1]);//高
	    		}
	    		if(theArray[m][0]=='宽')
	    		{
	    			dateWidth = parseInt(theArray[m][1]);//宽
	    		}
	    		if(theArray[m][0]=='边框类型')
	    		{
	    			vBORDER =":PROP:BORDER:" + parseInt(theArray[m][1]);//边框类型
	    		}
	    		if(theArray[m][0]=='字体')
	    		{
	    			vFontName = ":PROP:FACENAME:" + parseInt(theArray[m][1]);//字体
	    		}
	    		if(theArray[m][0]=='字体大小')
	    		{
	    			vFontSize = ":PROP:FONTSIZE:" + parseInt(theArray[m][1]);//字体大小
	    		}
	    		if(theArray[m][0]=='字体颜色')
	    		{
	    			vFontColor = ":PROP:FRONTCOLOR:" + parseInt(theArray[m][1]);//字体颜色
	    		}
	    		if(theArray[m][0]=='是否粗体')
	    		{
	    			vFontBold = ":PROP:FONTBOLD:" + parseInt(theArray[m][1]);//是否粗体
	    		}
	    		if(theArray[m][0]=='是否斜体')
	    		{
	    			vFontItalic = ":PROP:FONTITALIC:" + parseInt(theArray[m][1]);//是否斜体
	    		}
	    		if(theArray[m][0]=='日期来源')
	    		{
	    			dateFrom=theArray[m][1];
	    		}
	    		if(theArray[m][0]=='日期大小写')
	    		{
	    			if(parseInt(theArray[m][1]==1))//日期大小写
	    			{
	    				bigSmall=1;
	    			}
	    		}
	    		if(theArray[m][0]=='日期值')
	    		{
	    			tempDateValue =  parseInt(theArray[m][1]);//日期值
	    		}
	    	}
	    	var dateName = "Page" + pageDate +  "." + tempPageDateName;
		    if(pageDate < 1 || pageDate > aipObj.PageCount){
				    return false;			
			    }
		    if(parseInt(dateFrom)==0)//从意见信息表取日期
		    {
		    	if(cppoopiniondate!=null&&cppoopiniondate!="")
		    	{
		    		dateValue = cppoopiniondate;
		    	}
		    }
		    if(parseInt(dateFrom)==1)//从签章方案取日期
		    {
		    	if(tempDateValue!=null&&tempDateValue!="")
		    	{
		    		dateValue = tempDateValue;
		    	}
		    }
		    if(parseInt(dateFrom)==2||dateFrom==-1)//从签章页面取日期
		    {
		    	if(sealdate!=null&&sealdate!="")
		    	{
		    		dateValue = sealdate;
		    	}
		    }
		    if(parseInt(dateFrom)==3)
		    {
		    	if(sealdate!=null&&sealdate!="")
		    	{
		    		dateValue = sealdate;
		    	}
		    }
		    if(parseInt(bigSmall)==0)//日期小写(默认)
		    {
				if(dateValue!=null&&dateValue!="")
				{
				}
		    }
		    if(parseInt(bigSmall)==1)//日期大写
		    {
		    	if(dateValue!=null&&dateValue!="")
				{
					dateValue=parBigDate(dateValue);
				}
		    }
		    if(parseInt(dateFrom)==3)
		    {
		    	aipObj.Setvalue(dateName,"");//设置值
		    	aipObj.Setvalue(dateName,dateValue);//设置值
		    	aipObj.Getvalue(dateValue);//通过此命令使签章信息生效 
		    }else{
		    	var CurrPage = pageDate-1; 
		    	var abc = aipObj.DeleteNote(dateName);//删除节点   这个方法不起作作，（不影响现功能）后继再研究;
		    	
			    aipObj.InsertNote(dateName,CurrPage,3,dateLeft,dateTop,dateWidth,dateHight);//添加编辑域
	   		    aipObj.Setvalue(dateName,":PROP::LABEL:0"); //设置为不可编辑
			    aipObj.Setvalue(dateName,vFontName);//设置字体
			    aipObj.Setvalue(dateName,vFontColor);//设置字体颜色
			    aipObj.Setvalue(dateName,vFontSize);//设置文字域字体大小
			    aipObj.Setvalue(dateName,vFontBold); //设置文字是否为BOLD	
			    aipObj.Setvalue(dateName,vFontItalic); //设置文字是否为斜体
			    aipObj.Setvalue(dateName,vBORDER);//设置边框格式
			    aipObj.Setvalue(dateName,"");//设置值
			    aipObj.Setvalue(dateName,dateValue);//设置值
		    	aipObj.Getvalue(dateValue);//通过此命令使签章信息生效
		    }
	    }
		else if(typeflag==3)//查找文字盖章
		{		
			    var textvalue="";
		    	for(var m=0;m<theArray.length;m++)
	    		{
	    			if(theArray[m][0]=='文字')
		    		{
	    				textvalue = theArray[m][1];//页码
		    		}
		    	} 	
                var sealResult = AutoSeal(aipObj,2,0,1,textvalue,'',sealdate);
                if(sealResult !='1'){
                	return false;
                }
	    }
		else if(typeflag==4)//右骑缝章
		{
            AutoSeal(aipObj,2,1,0,'','','');
	    }
		else if(typeflag==5)//对开骑缝章
		{
            AutoSeal(aipObj,2,2,0,'','','');
	    }
    	return true;
    }catch(e) {
     		return false;
     }
} 
    
    var checkUrl = null;
    function aipLogin(aipObj){
    	//var server1 = "http://localhost:9230/Seal/general/interface/";
    	//var server2 = "http://localhost:9230/Seal/general/interface/";
    	var server1 = "http://localhost:9230/Seal/general/interface/";
    	var server2 = "http://localhost:9230/Seal/general/interface/";
    	if (checkUrl == null) {
            var rtn = aipObj.Login("",3, 65535, "",server1);
            if (rtn == 0) {
            	checkUrl = server1;
            	return rtn;  
            } else {
            	rtn =  aipObj.Login("",3, 65535, "",server2);
            	checkUrl = server2;
            	return rtn;
            }    		
    	} else {
    		return aipObj.Login("",3, 65535, "",checkUrl);
    	}
		
    }     
    
    /****************************************************************************************************

    方法名：AutoSeal					自动盖章
    参  数：usertype					用户类型：0 测试用户，1 本地key用户，2 服务器key用户，3 服务器口令用户
    		doaction					操作类型：0 普通印章，1 右骑缝章，2对开骑缝
    		searchtype					定位盖章位置类型：只对普通印章doaction=0时有效，0 绝对坐标，1 文字定位
    		searchstring				定位信息：只对普通印章doaction=0时有效
    											searchtype为0时，searchstring为x:y:page格式，即200:500:0   x为横向坐标1-50000，y为纵向坐标1-50000，page为盖章页码从0开始
    											searchtype为1时，searchstring为要查找的文字字符串
    		other						预留参数：
    											当usertype为1,2时，值为用户真实姓名，可以为空获则取证书用户名。
    											当usertype为3时，值为口令内容。
    返回值：-200是登陆失败或者为插入Ukey，-100是签章失败，1是签章成功
    *****************************************************************************************************/
    function AutoSeal(AipObj,usertype,doaction,searchtype,searchstring,other,sealdate) {
    	if(usertype==0){
    		var islogin=AipObj.Login("HWSEALDEMO**",4,65535,"DEMO","");
    	}else if(usertype==1){
    		var islogin=AipObj.Login(other,1,65535,"","");
    	}else if(usertype==2){
    		//var islogin=AipObj.Login(other,3,65535,"","http://"+httpaddr+":8089/inc/seal_interface/");
    		var islogin=aipLogin(AipObj);
    	}else if(usertype==3){
    		var stri="Use-RemotePfx-Login:"+other;
    		var strj=stri.length+1;
    		var islogin=AipObj.LoginEx("http://"+httpaddr+":8089/inc/seal_interface/", stri, strj);
    	}else{
    		alert("用户类型参数错误，以测试用户身份登录");
    		var islogin=AipObj.Login("HWSEALDEMO**",4,65535,"DEMO","");
    	}
    	if (islogin != 0) {
    		return "-200";
    	} else {
    		if(doaction==0){
    			var num=AipObj.PageCount;
    			var str=searchstring.split(":");
    			var page="";
    			if(searchtype==0){
    				var isseal=AipObj.AddQifengSeal(0,0+","+str[0]+",0,"+str[1]+",50,"+str[2],"","AUTO_ADD_SEAL_FROM_PATH");
    				if(isseal!=1){
    					return "-100";
    				}else{
    					return "1";
    				}
    			}else if(searchtype==1){//服务器key用户查找文字盖普通章
    				var isseal = 0;
    				var searchstrings = searchstring.split("_",2);
    				if(searchstrings[1]!=null&&searchstrings[1]!=""){
    					isseal=AipObj.AddQifengSeal(0,"AUTO_ADD:0,"+num+",0,0,1,"+searchstrings[0]+")|(0,","","AUTO_ADD_SEAL_FROM_PATH");
    					/*
    					for(var j=1;j<=num;j++){
    						AipObj.setValue("Page"+j+"."+searchstrings[1],"");
    						AipObj.setValue("Page"+j+"."+searchstrings[1],sealdate);
    					}
    					*/
    				}else{
    					//AipObj.setvalue("ADD_FORCETYPE_VALUE","32768");签章下方显示时间
    					isseal=AipObj.AddQifengSeal(0,"AUTO_ADD:0,"+num+",0,0,133,"+searchstrings[0]+")|(0,","","AUTO_ADD_SEAL_FROM_PATH");
    				}
    				if(isseal!=1){
    					return "-100";
    				}else{
    					return "1";
    				}
    			}
    		}else if(doaction==1){
    			var num=AipObj.PageCount;
    			var page="";
    			for(i=1;i<num;i++){
    				page+=i+",";
    			}
    			var bl=100/(num-1);
    			var isseal=AipObj.AddQifengSeal(0,0+",25000,1,3,"+bl+","+page,"","AUTO_ADD_SEAL_FROM_PATH");
    			if(isseal!=1){
    				return "-100";
    			}else{
    				return "1";
    			}
    		}else if(doaction==2){
    			var num=AipObj.PageCount;
    			for(i=0;i<num-1;i++){
    				var isseal=AipObj.AddQifengSeal(0,i+",25000,2,3,50,1","","AUTO_ADD_SEAL_FROM_PATH");
    				if(isseal!=1){
    					return "-100";
    				}else{
    					return "1";
    				}
    			}
    		}
    	}
    }
    function DeleteSeal(obj){
	    if(!obj.IsOpened()){
	    	alert("必须打开文件，才可以操作");
			return;
		}
    	var SerialNumber = obj.GetSerialNumber();   //获取当前UK里证书序列号
    	var NoteInfo=obj.GetNextNote(SerialNumber,0,"");
    	var oldnote="";
    	while(NoteInfo != ""){
    		oldnote=NoteInfo;
    		NoteInfo=obj.GetNextNote(SerialNumber,0,"");
    		var delResult = obj.DeleteNote(oldnote);//删除节点
    	}
    	return true;
    }