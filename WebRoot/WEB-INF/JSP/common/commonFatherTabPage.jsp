<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	//
	String furl = request.getParameter("furl");
	if(null == furl){
		furl = "";
	}
	//
	String ids = "";
	Object idsObj = request.getAttribute("ids");
	if(null != idsObj){
		ids = (String)idsObj;
	}
	// 	
	//String indexFlag = request.getParameter("indexFlag");
	//if(null == indexFlag || indexFlag.trim().isEmpty()){
	//	indexFlag = "0";
	//}
	
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title></title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
	    </style>
</head>
<body>
	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
	<input id="applyid" name="applyid" class="mini-hidden" value="${applyid}"/>
	<input id="applyname" name="applyname" class="mini-hidden" value="${applyname}"/>
	<input id="flowMenuid" name="flowMenuid" class="mini-hidden" value="${flowMenuid}"/>
	<input id="indexFlag" name="indexFlag" class="mini-hidden" value=""/>
	<input id="operator" name="operator" class="mini-hidden" value=""/>
	
	<div id="tabs" class="mini-tabs" name="minitabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
	</div>
	
	<script type="text/javascript">
		var ftab = 1;
		
		mini.parse();
		var tabList = ${tabListJson};
		
	   	var path = '<%=path%>';
	   	var _furl = "<%=furl%>";
	   	var ids = "<%=ids%>";
	   	var idsArr = ids.split(",");
	   	var total = idsArr.length;
	   	var indexFlag = 0;
	        
	   	function generateTabs(index){
	   		var id = idsArr[index];
	   		var miniTab = mini.get("tabs");
	   		miniTab.removeAll();
	   		
	   		var tabListClone = mini.clone(tabList);
	   		for(var i=0,l = tabListClone.length;i<l;i++){
	   			var tabMap = tabListClone[i];
	   			tabMap.url += id;
	   		}
	   		mini.get("flowdraftid").setValue(null);
	   		mini.get("applyid").setValue(null);
	   		mini.get("applyname").setValue(null);
	   		
	   		mini.get("indexFlag").setValue(index);
	   		miniTab.setTabs(tabListClone);
	   	}
	    	
	    generateTabs(indexFlag);
	    	
	    //标准方法接口定义
	    function SetData(data){
	        data = mini.clone(data);
	        if(data.action){
	        	mini.get("operator").setValue(data.action); 
	        }
	    }
	    
	    function resetIdsArr(flowdraftid){
	    	idsArr[indexFlag] = "&flowdraftid="+flowdraftid;
	    }
	
	        
	    // 到上一个
		function previous(){
			indexFlag--;
			if(indexFlag < 0){
	     		return goBack();
	      	}
			generateTabs(indexFlag);
	    };
	    
	 	// 到下一个; 父页面不负责业务逻辑(isCommit,是否是提交操作, 默认值 0, 可指定 0,1)
	    function next(isCommit){
	 		if(isCommit){//isCommit 提交/退回/拒绝操作
	 			idsArr.splice(indexFlag,1);//移除当前数组中索引所对应的id
	 			total--;
	 		}else{
	 			indexFlag++;
	 		}
	 		//
	 		if(indexFlag > total-1){
	 			goBack();
	 		}else{
	 			generateTabs(indexFlag);
	 		}
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
	    
	    function CloseWindow(action){ 
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    };
	    
	    function setTabValue(value, node){
	    	mini.get(node).setValue(value);
	    	var tab2 = mini.get("tabs").getTabs();
	   		for(var i=0; i<tab2.length; i++){
	    		tab2[i].url += "&" + node + "=" + value;
	    	}
	    }

	    function getTabValue(node){
	    	return mini.get(node).getValue();
	    }
        
    </script>
</body>
</HTML>