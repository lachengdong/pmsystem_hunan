 ///////////////////
    //获取选中目录下的所有文件
   	function GetFilesPath(sfolderpath)
   	{
   	 /*--------------- GetFilesPath(sfolderpath) -----------------
   	 * GetFilesPath(sfilepath) 
   	 * 功能:遍历sfolderpath目录下的所有文件.返回数组.存储文件路径.
   	 * 参数:sfolderpath,字符串,目录绝对路径.
   	 * 实例:Response.Write(GetFilesPath(Server.MapPath("xml")))
   	 * update:2004-5-12 8:33
   	 *--------------- GetFilesPath(sfolderpath) -----------------
   	 */
   	 	if(sfolderpath){
	   	 	arr1.length = 0;
	   	 	var divstrshow = "";
	   	 	var casestr =".jpg,.tif,.pdf";
	   	    var sFilePath = new Array();
	   	    var fso = new ActiveXObject("Scripting.FileSystemObject");
	   	    //获取目录对象
	   	    var oFolder = fso.GetFolder(sfolderpath);
	   	    var oSubFolders = oFolder.SubFolders;
	   	    var oFiles = oFolder.Files;
	   	    icount = oFiles.Count;
	   		var l = 0;
	   	    var enmFiles = new Enumerator(oFiles);
	   	    for(;!enmFiles.atEnd();enmFiles.moveNext())
	   	    {
	   			if(casestr.indexOf(enmFiles.item().Path.substring(enmFiles.item().Path.lastIndexOf("."),enmFiles.item().Path.length).toLowerCase())>-1){
	   				 sFilePath[sFilePath.length] = enmFiles.item().Path;
	   			};	       
	   	    }
	   	    var enmFolders = new Enumerator(oSubFolders);
	   	    for(;!enmFolders.atEnd();enmFolders.moveNext())
	   	    {
	   	        sFilePath=sFilePath.concat(GetFilesPath(enmFolders.item().Path));
	   	    }
	   	   // alert(sFilePath);
	   	   ///////
	   	 	var patharr = getAbsolutePath(oFolder).split(",");//返回拼接串
	   	 	foldermap.put(patharr[0],sFilePath);
	   	    return sFilePath;
   	   	}
   	}
   	//获取目录树
   	function GetFolderJson(folderpath,type)
   	{
   	   	var arry;
   	 	var patharr;
   	 	var folderarry = new Array();
   		var fso = new ActiveXObject("Scripting.FileSystemObject");
   		if(folderpath){
   		    var Folder = fso.GetFolder(folderpath);//获取目录对象
   			var oSubFolders = Folder.SubFolders;
   			var enmFolders = new Enumerator(oSubFolders);
   		    for (; !enmFolders.atEnd(); enmFolders.moveNext()) {
   		        //获取目录对象
   			    var oFolder = fso.GetFolder(enmFolders.item());
   			    patharr = getAbsolutePath(oFolder).split(",");//返回拼接串
   			    arry = "{id:'"+patharr[0]+"',text:'"+patharr[0]+"',pid:'"+patharr[1]+"'}";
   			    folderarry.push(arry);
   		        GetFolderJson(enmFolders.item(),'0');
   		   }
	   	   if(!type){
	 		   patharr = getAbsolutePath(Folder).split(",");//返回拼接串
	 		   arry = "{id:'"+patharr[0]+"',text:'"+patharr[0]+"',pid:'"+patharr[1]+"'}";
	 		   folderarry.push(arry);
	 	   }
	   	   //生成文件目录树json 串
		   var data =mini.decode('['+folderarry+']');
		   tree.loadList(data,"id","pid");	
   	   	}
   	}

    function getCheckedFolders(obj){
        //清空目录下的文件数组
    	filepathlist = [];
    	//拆分目录树
        var filefolders = obj.split(",");
        if(filefolders){
            for(var i=0;i<filefolders.length;i++){
            	var filelist = foldermap.get(filefolders[i]);//查找文件
            	if(filelist){//判断是否存在
            		for(var j=0;j<filelist.length;j++){
                		//添加到数组集合
                		filepathlist.push(filelist[j]);
                	}
                }
            }
        }
		var s = "<div>加载文件数量： "+ filepathlist.length +"个文件</div>";
		//for (var i = 0 ; i < filepathlist.length; i++){
	    //     s += filepathlist[i]+"<br/>";
  	  	//}	
		var textarea = document.getElementById("textarea");
		textarea.innerHTML = "<div style='font-size:8pt;font-family: Arial, Helvetica, sans-serif;'>"+s+"</div>";
	    }

	//文件选择框
   	function browseFolder() {
   		$("#tree1").show();
   		$("#describeid").hide();
   		folderarry = [];
   		folderpath = "";
   		foldermap = new Map();
   		var Message = "请选择档案存放目录"; //选择框提示信息
   		var Shell = new ActiveXObject("Shell.Application");
   		var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
   		if (Folder != null) {
   			Folder = Folder.items(); // 返回 FolderItems 对象
   			Folder = Folder.item(); // 返回 Folderitem 对象
   			Folder = Folder.Path; // 返回路径
   			if (Folder.charAt(Folder.length - 1) != "") {
   				Folder = Folder + "";
   			}
   			folderpath  = Folder;
   			if(null==folderpath){
   				alert("选择路径不正确！");
   			}else{
   				GetFolderJson(folderpath);
   				filepathlist = GetFilesPath(folderpath);//查找文件
   				var s = "<div>加载文件数量： "+ filepathlist.length +"个文件</div>";
   				//for (var i = 0 ; i < filepathlist.length; i++){
   			     //    s += filepathlist[i]+"<br/>";
   		  	  	//}	
   				var textarea = document.getElementById("textarea");
   				textarea.innerHTML = "<div style='font-size:8pt;font-family: Arial, Helvetica, sans-serif;'>"+s+"</div>";
   			}
   			return Folder;
   		}
   	}
   	//返回目录的绝对路径
   	function getAbsolutePath(oFolder){
   		var fso = new ActiveXObject("Scripting.FileSystemObject");
   		//获取当前目录名称
		var folderName = fso.GetFileName(oFolder);
	    //取得文件或目录的父目录名 
		var parentFolder = fso.GetParentFolderName(oFolder);
		//获取父目录名称
		var parentFolderName = fso.GetFileName(parentFolder);
		return folderName+","+parentFolderName;
   	}
   	
   	
////////////////////
   	function newRequest(resid){
   		var provincecode = mini.get("provincecode").getValue();
		var archiveseal = mini.get("archiveseal").getValue();
		var obj = document.getElementById("HWPostil1");	
		if(archiveseal=='1'){//需要签章
			var logiret=0;
			var flagNum =0;
			var acou =0;
			if(!obj.IsLogin()&& provincecode!='3100'){
				logiret=aipLogin(obj); 
			}
			if(logiret==-200 ){
				alert("未插入智能卡");
				return false;
			}else if(logiret!=0 ){
				alert("登录失败。错误ID:"+logiret);
				return false;
			}
			
			if(filepathlist.length == 0){
				 alert("请先选择文件!"); 
				 return;
			}
			obj.CloseDoc(0);
		   	obj.LoadFile(filepathlist[currentIndex]);
		   	obj.SilentMode = 1;
			if(obj.IsOpened()){
				var vNoteName ="Page1.PageText3";
				obj.InsertNote(vNoteName,0,3,"1900","46200","7000","1600");//添加编辑域
			    obj.Setvalue(vNoteName,":PROP:BORDER:0");//设置区域无边框
				obj.Setvalue(vNoteName,":PROP::LABEL:1"); //设置为不可编辑
				obj.Setvalue(vNoteName,":PROP::LABEL:2"); //可选,(对于编辑器不可点击)	
				obj.Setvalue(vNoteName,":PROP:VALIGN:1");//设置竖直剧中
			    obj.Setvalue(vNoteName,":PROP:FACENAME:黑体");//设置字体
			    obj.Setvalue(vNoteName,":PROP:FRONTCOLOR:0");//设置字体颜色
			    obj.Setvalue(vNoteName,":PROP:FONTSIZE:16" );//设置文字域字体大小
			    obj.Setvalue(vNoteName,":PROP:FONTBOLD:1"); //设置文字是否为BOLD	
			    obj.Setvalue(vNoteName,":PROP:FONTITALIC:0"); //设置文字是否为斜体0正
			    obj.Setvalue(vNoteName,"制作人：");//设置值
				var sealcontent="";
				var sealdate="";
				var cppoopiniondate="";
				var cppoopinion="";
				var strSignatureNoteInfo ="节点类型:1,页码:1,上:47000,左:10000,高:7500,宽:2000";
				InsertNote(strSignatureNoteInfo,obj,sealcontent,sealdate,cppoopiniondate,cppoopinion);
				
				var aipFileStr = obj.GetCurrFileBase64();
				
				var noteNum = obj.GetNoteNum(251);
				if(noteNum =='0'&& provincecode!='3100'){
					alert("档案签章失败！");
				}else{
					data = {count:currentIndex,filecount:filepathlist.length,filepathname:filepathlist[currentIndex],aipFileStr:aipFileStr,
								resid:resid,flowdefid:'arch_zfdajdsp'};
					$.ajax({
			                url:'importarchives.action?1=1&resid='+resid,         
			                data: data,
			                type: "post",
			                dataType:"text",
			                cache:false,
			                async:true,
			                success: function (text){
			                	var aar = text.split(",");
		                        currentIndex++;
		                        success = aar[0];
		                        var faild = aar[1];
		                     	if(faild < 0){
		                     		divstrshow =divstrshow+"<div style='color:red;'>导入第 "+currentIndex+"个文件:"+aar[2]+"......失败!</div>";
		                     		failnum++;
		   	                    }else{
		   	                  		divstrshow =divstrshow+"<div>导入第 "+currentIndex+"个文件:"+aar[2]+"......成功!</div>";
		   	   	                }
		                        if (currentIndex >= filepathlist.length) {
		   	                        var num = success - failnum ;
		                            divstrshow =divstrshow+sucessval+num+" 条<br/>";
						            if(faild < 0){
						               divstrshow =divstrshow+"<font color='#cc3300'>导入失败 "+ failnum +" 条<br/></font>";
						            }
		                        } else {
		                        	newRequest(resid);
		                        }
		                     	document.getElementById("divshowinfo").innerHTML=divstrshow;
		                     	var winflaginfo = document.getElementById("winflaginfo");
		                     	winflaginfo.scrollTop = winflaginfo.scrollHeight; 
		                        var percentage = parseInt((currentIndex/filepathlist.length)*100);
		                        var m_message  = '<div class="progressbar">'+ '<div class="progressbar-percent" style="width:'+percentage+'%;"></div>'
		          	        	+ '<div class="progressbar-label">'+percentage+'%</div>' +'</div>';
		                        var progressbarid = document.getElementById("progressbarid");
			                    progressbarid.innerHTML = m_message;
			                },
			                error: function (jqXHR, textStatus, errorThrown) {
			                }
			    	});
				}
			}
		}else{
			obj.Login("sys_admin", 5, 32767, "", "");
			obj.CloseDoc(0);
			obj.LoadFile(filepathlist[currentIndex]);
		   	obj.SilentMode = 1;
			if(obj.IsOpened()){
				var vNoteName ="Page1.PageText3";
				obj.InsertNote(vNoteName,0,3,"1900","46200","20000","1600");//添加编辑域
			    obj.Setvalue(vNoteName,":PROP:BORDER:0");//设置区域无边框
				obj.Setvalue(vNoteName,":PROP::LABEL:1"); //设置为不可编辑
				obj.Setvalue(vNoteName,":PROP::LABEL:2"); //可选,(对于编辑器不可点击)	
				obj.Setvalue(vNoteName,":PROP:VALIGN:1");//设置竖直剧中
			    obj.Setvalue(vNoteName,":PROP:FACENAME:黑体");//设置字体
			    obj.Setvalue(vNoteName,":PROP:FRONTCOLOR:0");//设置字体颜色
			    obj.Setvalue(vNoteName,":PROP:FONTSIZE:16" );//设置文字域字体大小
			    obj.Setvalue(vNoteName,":PROP:FONTBOLD:1"); //设置文字是否为BOLD	
			    obj.Setvalue(vNoteName,":PROP:FONTITALIC:0"); //设置文字是否为斜体0正
			    var username = mini.get("username").getValue();
			    var zhizuoren = "制作人："+username;
			    obj.Setvalue(vNoteName,zhizuoren);//设置值
				var sealcontent="";
				var sealdate="";
				var cppoopiniondate="";
				var cppoopinion="";
				var aipFileStr = obj.GetCurrFileBase64();
				data = {count:currentIndex,filecount:filepathlist.length,filepathname:filepathlist[currentIndex],aipFileStr:aipFileStr,
							resid:resid,flowdefid:'arch_zfdajdsp'};

               // dataType:"text",	
				$.ajax({
	                    type: "post",
		                url:'importarchives.action?1=1&resid='+resid,	                
		                data: data,
		                cache:false,
		                async:true,
		                contentType: "application/x-www-form-urlencoded; charset=utf-8",  
		                success: function (text){
		                	var aar = text.split(",");
	                        currentIndex++;
	                        success = aar[0];
	                        var faild = aar[1];
	                     	if(faild < 0){
	                     		divstrshow =divstrshow+"<div style='color:red;'>导入第 "+currentIndex+"个文件:"+aar[2]+"......失败!</div>";
	                     		failnum++;
	   	                    }else{
	   	                  		divstrshow =divstrshow+"<div>导入第 "+currentIndex+"个文件:"+aar[2]+"......成功!</div>";	
	   	   	                }
	                        if (currentIndex >= filepathlist.length) {
	   	                        var num = success - failnum ;
	                            divstrshow =divstrshow+sucessval+num+" 条<br/>";
					            if(faild < 0){
					               divstrshow =divstrshow+"<font color='#cc3300'>导入失败 "+ failnum +" 条<br/></font>";
					            }
	                        } else {
	                        	newRequest(resid);
	                        }
	                     	document.getElementById("divshowinfo").innerHTML=divstrshow;
	                     	var winflaginfo = document.getElementById("winflaginfo");
	                     	winflaginfo.scrollTop = winflaginfo.scrollHeight; 
	                        var percentage = parseInt((currentIndex/filepathlist.length)*100);
	                        var m_message  = '<div class="progressbar">'+ '<div class="progressbar-percent" style="width:'+percentage+'%;"></div>'
	          	        	+ '<div class="progressbar-label">'+percentage+'%</div>' +'</div>';
	                        var progressbarid = document.getElementById("progressbarid");
		                    progressbarid.innerHTML = m_message;
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                }
		    	});
			}
		}
	}
   	var flug=1;
   	var menuid = '';
   	var success = 0;
	var failnum = 0;
	var divstrshow ="";
	var folderpath = "";//用户选择的文件夹路径
	var filepath = "";//用户存放档案的临时目录
	var sucessval ="导入成功 ";
	var currentIndex = 0;//退出条件的递归调用
	var arr1 = new Array();
   	var arr2 = new Array();
	var aipstrlist = new Array();//大字段集合
   	var filepathlist = new Array();
    var foldermap = new Map();//文件选择后存放文件目录的map集合
    
   	//批量上传 resid 和流程相关按钮ID snodeid 源节点ID flowdefid 流程自定义ID
   	function piliangdaoru(resid){
   		failnum = 0;
   		success = 0;
   		divstrshow = "";
   		currentIndex = 0;
   		piliangdaoruvalidate();
   		if(flug==0){
   			newRequest(resid);
   		}
   	}	

   	function piliangdaoruvalidate(){	
   		var ff = new Array();
   		flug = 1;
   		var fileName= new Array();
   		var file = [];
   		var id="";
   		if(filepathlist && filepathlist.length>0){			
   			for(var i=0;i<filepathlist.length;i++){				
   				if(filepathlist[i]){
   					ff=filepathlist[i].split("\\");
   					if(ff && ff.length >= 2){
   						var cm=ff[ff.length-2];
   						
   						//\[罪犯编号][罪犯姓名]\[档案大类别编号]_[档案小类别编号]_[文件命名]_[排序号].JPG 
   						var str = cm+":"+ff[ff.length-1];
   						file.push(str);
   					}
   				}
   			}	
   			file = file.join(",");		
   			var json ="["+mini.encode(file)+"]";
   			$.ajax({
   	            url: 'importcheck.action?1=1', 
   	            data: {json:json},
   	            type: "post",
   	            dataType:"text",
   	            cache:false,
   	            async:false,
   	            success: function (text){
   	            	var obj = mini.decode(text);
   					if(obj && obj.length >0){
   						flug=1;
						var divstrshow = '';
						for(var i=0;i<obj.length;i++){
							divstrshow =divstrshow+"<div style='color:red;'> "+ obj[i] +"</div>";
						}
						document.getElementById("divshowinfo").innerHTML= divstrshow;
                     	var winflaginfo = document.getElementById("winflaginfo");
                     	winflaginfo.scrollTop = winflaginfo.scrollHeight; 
	   	            	return;
   					}else{
   						flug = 0;
   					}
   	            }
   			});
   		}
   	}
   	
	//文件选择框
   	function getFolder() {
   		
   		$("#tree1").show();
   		$("#describeid").hide();
   		folderarry = [];
   		folderpath = "";
   		foldermap = new Map();
   		var Message = "请选择档案存放目录"; //选择框提示信息
   		var Shell = new ActiveXObject("Shell.Application");
   		var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
   		if (Folder != null) {
   			Folder = Folder.items(); // 返回 FolderItems 对象
   			Folder = Folder.item(); // 返回 Folderitem 对象
   			Folder = Folder.Path; // 返回路径
   			
   			if (Folder.charAt(Folder.length - 1) != "") {
   				Folder = Folder + "";
   			}
   			folderpath  = Folder;
   			if(null==folderpath||""==folderpath){
   				alert("选择路径不正确！");
   			}
			GetFolderJson(folderpath);
			//filepathlist = GetFilesPath(folderpath);//查找文件 			
   			return Folder;
   		}
   		
   	}
   	
   	//批量解密
   	function piliangjiemi(applyids){
   		failnum = 0;
   		success = 0;
   		divstrshow = "";
   		currentIndex = 0;
		if(null!=folderpath&&""!=folderpath){
	   		newDecryptRequest(applyids,'');
		} else {
			alert("请选择档案存放目录！");
			return;
		}
   	}   	
   	
   	function newDecryptRequest(applyids, archiveid){

		var obj = document.getElementById("HWPostil1");	
		var logiret=0;
		var flagNum =0;
		var acou =0;
		
		obj.CloseDoc(0);
		$.ajax({
             url: 'getOneArchive.action?1=1',
             data: { applyids:applyids,archiveid:archiveid},
             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
             cache: false,
             type: "post",
             success: function (text) {
            	 var objs = eval(text);
            		 daochu(objs);
            		 archiveid = objs[0].archiveid;
                     var m_message  = "正在解密中...";
                     if (objs[0].theend == "1") {
                    	 alert(currentIndex);
                    	 m_message  = "解密结束，共解密" + currentIndex + "档案。";
                     } else {
                    	 currentIndex++;
                    	 divstrshow ="<div>解密第 "+currentIndex+"个文件......成功!</div>"+divstrshow;
                     	 newDecryptRequest(applyids, archiveid);
                     }
                  	document.getElementById("divshowinfo").innerHTML=divstrshow;
                    var progressbarid = document.getElementById("progressbarid");
                    progressbarid.innerHTML = m_message;            		 
             },
             error: function (jqXHR, textStatus, errorThrown) {
             }
         });
		
	}   	
   	
    function daochu(objs){//导出
		var aipObj=document.getElementById("HWPostil1");
		aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");

		for(var j=0;j<objs.length;j++){
			if (objs[j].theend == 1) {
				return;
			}
			aipObj.CloseDoc(0);
			var fso = new ActiveXObject("Scripting.FileSystemObject");
			if (!fso.FolderExists(folderpath + "\\" + objs[j].departid)){
				fso.CreateFolder(folderpath + "\\" + objs[j].departid);
			}
			if (!fso.FolderExists(folderpath + "\\" + objs[j].departid + "\\" + objs[j].personid)){
				fso.CreateFolder(folderpath + "\\" + objs[j].departid + "\\" + objs[j].personid);
			}			
			filepath = folderpath + "\\" + objs[j].departid + "\\" + objs[j].personid + "\\" + objs[j].archiveid + ".pdf";
			if (objs[j].docconent == null) {
				continue;
			}		
			aipObj.LoadFileBase64(objs[j].docconent);
			if(aipObj.IsOpened()){
				document.all.HWPostil1.SetValue("SET_TEMPFLAG_MODE", "2");//不加锁
    			document.all.HWPostil1.SaveTo(filepath,"pdf",0);
    		}
		}
	}
   	   	