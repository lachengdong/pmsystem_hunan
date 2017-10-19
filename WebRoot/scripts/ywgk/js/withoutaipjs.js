	function doSubmitFlow(menuid,flowdefid,operateType,tempid)
	{
	     var commenttext = mini.get("commenttext").getValue();
	     if(!commenttext){
			 alert("请填写意见！");
	    	 return;
	     }{
	    	 $.ajax({
		      url:basePath+'/flow/flowapprove.action?1=1',
		      type:'post',
		      data:{flowdraftid:flowdraftid,operateType:operateType,commenttext:commenttext,resid:menuid},
		      success: function(text)
		      {  
		      	 toNext();	         	
		      },
		      error:function(){  	         	
		         goBack();
		      }
		     });   	   
	     }
	}
	 //isCommit,是否是提交操作, 默认值 0, 可指定 0,1
    function toNext(isCommit){
    	if(window.parent.next){
    		window.parent.next(isCommit);
    	}else{
    		next(isCommit);
    	}
    }
    // 到下一个; 父页面不负责业务逻辑(isCommit,是否是提交操作, 默认值 0, 可指定 0,1)
   function next(isCommit){
       	// 1. 获取参数
       	// 2. 截取index
       	// 3. 修改 index
       	// 4. 跳转
       	// 
       	// ids=1,2,3&indexFlag=0
       	var idArray = ids.split(",") || [];
       	var currentid = idArray[indexFlag];
       	//
       	if(indexFlag > idArray.length - 1){
       		return goBack();
       	}
       	//
       	var paramObject = parseURLParams() || {};
       	// isCommit
       	isCommit = isCommit || 0;
       	if(isCommit){
           	// 如果是提交, 则 indexFlag 不变,将当前ID摘除
           	// 
           	if(currentid){
           		// 当前ID存在
           		var newIDsArray = [];
           		for(var i = 0; i < idArray.length; i++){
           			var cid = idArray[i];
           			if(currentid === cid){
           				// 不执行
           			} else {
           				newIDsArray.push(cid);
           			}
           		}
           		//
           		ids = newIDsArray.join(",");
           		paramObject["ids"] = ids;
           		idArray = newIDsArray;
           	}
       	} else {
           	// 如果不是提交, 将 indexFlag +1;
           	indexFlag = indexFlag + 1;
           	paramObject["indexFlag"] = indexFlag;
       	}
       	
       	if(indexFlag > idArray.length - 1){
       		return goBack();
       	}
       	var url = processParam(getURLPathName(), paramObject);
       	window.location.href = url;
   };
     // 关闭,返回
   function goBack(){
      var r = new Date().getTime();
      url = parsefurl();
      if(url){
       	  window.location.href=url;
      }else{
          CloseWindow("close");
      }
   };