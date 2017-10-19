<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemResource"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();	
	String basePathLessSlash = request.getScheme()+"://"+request.getServerName()
											+":"+request.getServerPort()+path ; // 少1个斜线
	String basePath = basePathLessSlash + "/";
	SystemResource grid1 = null;
	List<SystemResource> grid1ColList = new ArrayList<SystemResource>();
	String flowdefid = "";
	//
	Object grid1_attr = request.getAttribute("grid1");
	Object grid1ColList_attr = request.getAttribute("grid1ColList");
	//
	if(grid1_attr instanceof SystemResource){
		grid1 = (SystemResource)grid1_attr;
	}
	if(grid1ColList_attr instanceof List<?>){
		grid1ColList = (List<SystemResource>)grid1ColList_attr;
	}
	//
	Object flowdefidObj = request.getAttribute("flowdefid");
	if(null != flowdefidObj){
		flowdefid = flowdefidObj.toString();
	}
	//
	String topsearch = "";
	Object topsearchObj = request.getAttribute("topsearch");
	if(null != topsearchObj){
		topsearch = topsearchObj.toString();
	}
	
	String middlestr = "";
	Object middlestrObj = request.getAttribute("middlestr");
	if(null != middlestrObj){
		middlestr = middlestrObj.toString();
	}
	
	String selfAction = "";
	Object selfActionObj = request.getAttribute("selfAction");
	if(null != selfActionObj){
		selfAction = selfActionObj.toString().trim();
	}
	
	
	//
	//组织列中按钮的修改和查看功能：前提是配资源时，资源的相关信息有：xiugai,修改,处理	chakan,查看
	Object middlemapObj = request.getAttribute("middlemap");
	String xiugaiButton = "";
	String chakanButton = "";
	String freeResidButton = "";
	if(null != middlemapObj&&middlemapObj instanceof HashMap){
		Map middlemap = (HashMap)middlemapObj;
		Set<String> set = middlemap.keySet();
		for(String buttonkey : set){
			String buttonValue = middlemap.get(buttonkey).toString();
			if(buttonValue.indexOf("xiugai") != -1||buttonValue.indexOf("edit") != -1 || buttonValue.indexOf("修改") != -1 || buttonValue.indexOf("处理") != -1){
				xiugaiButton = buttonValue;
			}else if(buttonValue.indexOf("chakan") != -1 || buttonValue.indexOf("check") != -1 || buttonValue.indexOf("查看") != -1){
				chakanButton = buttonValue;
			}else{
				freeResidButton = buttonValue;
			}
		}
	}
	
	
	
	//
	String unlockbtn = ""; //解锁铵钮
	Object unlockbtnObj = request.getAttribute("unlockbtn");
	if(null != unlockbtnObj){
		unlockbtn = unlockbtnObj.toString().trim();
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>公共列表页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }  
    </style>
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
   </script>
   
    <script type="text/javascript">
    	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
    </script>
    
	<!-- 加入此处为了操作批量退回删除印章问题 -->
<!-- 	<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include> -->
</head>
<body>
	<div id="form1"  class="mini-splitter" vertical="true" style="display: none;" >
	</div>
	<div id="datagrid" style="width:100%;height:100%;">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
		<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
		<input id="currnodeid" name="currnodeid" class="mini-hidden" value="${currnodeid}"/>
		<input id="solutionid" name="solutionid" class="mini-hidden" value="${solutionid}"/>
		<input id="menuid" name="menuid" class="mini-hidden" value="${menuid}"/><!-- 跳转至此页面的menuid -->
		<input id="otherparam" name="otherparam" class="mini-hidden" value="${otherparam}"/>
		<input class="mini-hidden" id="sealOrRevoke" name="sealOrRevoke" value=""/> <!-- 签章或撤销签章 	临时存放 -->
		<input id="temp1" name="temp1" class="mini-hidden" value="${temp1}"/> <!-- 临时变量1，用完请清空 -->
        <table >
             <tr>
                <td style="width:100%;">
                 <!--  
                	<a class="mini-button"  iconCls="" plain="true" onclick="testFunction();">测试</a>
                -->	
                	${topstr}
                </td>
                <td style="white-space:nowrap;"> 
                	${topsearch}
                	<%
                		if(StringNumberUtil.notEmpty(topsearch)){
                	%>
                			<a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
                	<%
                		}
                	%>
                	<!-- 操作说明 -->
	                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
	                
	                <%
                		if(StringNumberUtil.notEmpty(unlockbtn)&& "1".equals(unlockbtn)){
                	%>
                		<span class="separator"></span>
	                	<a class="mini-button" iconCls="icon-unlock" plain="true" onclick="unlockBtn();">去锁</a>
                	<%
                		}
                	%>
                	
	                
                 </td>
             </tr>
        </table>
        
    </div>
    <div class="mini-fit" id="div_two">
         <%--
        	// 根据配置的Grid以及列进行拼接
         --%>
         <%
         
         if(null != grid1){
        	 //
        	 String tempid = grid1.getFormid(); //表单ID;
        	 String solutionid = grid1.getQuerysql(); //查询方案ID
        	 String url = "";
        	 //
			 StringBuffer urlSb = new StringBuffer();
        	 if(StringNumberUtil.isEmpty(grid1.getSrurl())){
        	 	urlSb.append(path).append("/getPublicListData.json?1=1");
        	 }else{
        	 	urlSb.append(path).append("/").append(grid1.getSrurl());
        	 }
        	 
        	 //从菜单的参数中获取参数，但是如果Grid中有相应参数，则以Grid为准
			 Object paramMapObject = request.getAttribute("paramMap");
			 if(null != paramMapObject && paramMapObject instanceof HashMap){
				 Map paramMap = (HashMap)paramMapObject;
				 if(StringNumberUtil.notEmpty(tempid)&&paramMap.containsKey("tempid")){
				 	paramMap.remove("tempid");
				 }
				 if(StringNumberUtil.notEmpty(solutionid)&&paramMap.containsKey("solutionid")){
				 	paramMap.remove("solutionid");
				 }
				 if(urlSb.indexOf("?") == -1){
				 	urlSb.append("?1=1");
	        	 }
	        	 
				 Set<String> set = paramMap.keySet();
				 for(String key : set){
					 Object valueObj = paramMap.get(key);
					 if(StringNumberUtil.notEmpty(valueObj)){
						 String value = valueObj.toString();
						 urlSb.append("&").append(key).append("=").append(value);
					 }
				 }
			 }
        	 
        	 //
        	 if(StringNumberUtil.notEmpty(tempid) && urlSb.indexOf("tempid")==-1){
        	 	urlSb.append("&tempid=").append(tempid);
        	 }
        	 if(StringNumberUtil.notEmpty(solutionid) && urlSb.indexOf("solutionid")==-1){
        	 	urlSb.append("&solutionid=").append(solutionid);
        	 }
        	 if(StringNumberUtil.notEmpty(flowdefid) && urlSb.indexOf("flowdefid") == -1){
        	 	urlSb.append("&flowdefid=").append(flowdefid);
        	 }
        	 url = urlSb.toString();
       	
         %>
         
         <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100,500,1000,1500,2000]" pageSize="${pageSize}"
        	 url="<%=url%>" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false"  showLoading="true">
        	 
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div type="indexcolumn" width="30" headerAlign="center" align="center" allowSort="true">序号</div>
              
         <%
	    	 //
	    	 Iterator<SystemResource> iteratorC = grid1ColList.iterator();
	    	 while(iteratorC.hasNext()){
	    		 SystemResource col = iteratorC.next();
	    		 //
	    		 String name = col.getName();
	    		 String field = col.getShowsite();
	    		 Integer width = col.getWindowwidth();
	    		 String renderer = col.getSrurl();
	    		 String additionInfo = col.getText1();
	    		 //
	    		 
	    		 //
	    		 if(StringNumberUtil.isEmpty(field)){
	    			 continue;
	    		 } else {
	    			 field = field.trim().toLowerCase();
	    		 }
	    		 if(StringNumberUtil.isEmpty(name)){
	    			 name = "";
	    		 }
	    		 if(null == width || width.intValue() < 1){
	    			 width = 30;
	    		 }

	    		 String rendererStr = "";
	    		 if(!StringNumberUtil.isEmpty(renderer)){
	    			 rendererStr = " renderer=\"" + renderer +"\" ";
	    		 }
	    		 
	    		 String allowSort = "";
	    		 if(StringNumberUtil.isEmpty(additionInfo)){
	    			 additionInfo = " allowSort=true ";
	    		 }else if(additionInfo.indexOf("allowSort")< 0){
	    			 additionInfo += " allowSort=true ";
	    		 }
	    		 
	    		 
	    		 // 列
         %>
              <div field="<%=field %>" width="<%=width %>" headerAlign="center" align="center" <%=additionInfo %> <%=rendererStr %> ><%=name %></div>    
         <%
         	}
         %>
         <%
         	if(StringNumberUtil.notEmpty(middlestr)){
         		if(StringNumberUtil.notEmpty(selfAction)){
         %>
         			<div name="action" width="40" headerAlign="center" align="center"  allowSort="false" renderer="<%=selfAction%>">操作</div>  
         <%
         		}else{
         %>	
         			<div name="action" width="40" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>
         <%
         		}
         	}
         %>
          </div> <!-- end of columns -->
        </div> <!-- end of mini-datagrid -->
         <%
         }
         %>
    </div> <!-- end of div_two -->
    
	<div style="height:0;"> <!-- display:none;  height:200px;-->
		<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
		<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
		<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
		
		<form id="hidden_formid" method="post" url="">
			<input type="hidden" id="hidden_ids" name="ids" value=""/>
			<input type="hidden" id="hidden_furl" name="furl" value=""/>
		</form>
	</div>
	
</div>

<!--电子会议嵌入所需iframe-->
<span id="con1_one1_1">		
</span>
<span id="con1_one1_2" style="display:none">		
	  <iframe id="iframeturbo2" src="" style="width:1888px;height:700px;"></iframe>
</span>
<div id="editWindow" class="" title="" style="width:0px;height:0px" showModal="true" allowResize="true" allowDrag="true">
	<div id="editform" class="form" style="min-height: 1px;" >
		<iframe id="iframeturbo" style="width:0px;height:0px;" src=""></iframe>
	</div> 
</div>
	<script type="text/javascript">
		document.all("MyViewer").Init(window, document,600);
   </script>
   
    <script type="text/javascript">
	    //
	    mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        //
      	var path = '<%=path%>';
      	var basePathLessSlash = '<%=basePathLessSlash%>';


        var xiugaiButtonObj = "<%=xiugaiButton%>";
        var chakanButtonObj = "<%=chakanButton%>";
        var freeResidButtonObj = "<%=freeResidButton%>";
        var xiugaiButtonArr = xiugaiButtonObj.split('@');
        var chakanButtonArr = chakanButtonObj.split('@');
        var freeResidButton = freeResidButtonObj.split('@');
       
        function onActionRenderer(e){
        	var row = e.record;

       	    //点击办理按钮、保存等操作，该案件会被加锁，加锁后颜色发生变化
       	    var islocked = row.islocked;
        	var state = row.state;
       	    if ( row.islocked && row.islocked==1 ){
       	    	if(state && state==-2){//保存
       	    		e.rowStyle = "background:#BBFFFF;";
       	    	}else{
       	    		e.rowStyle = "background:#EEEE80;";
       	    	}
	    	}else if(state && state==2){//上级退回
	    		e.rowStyle = "background:#CD9B9B;";
	    	}
       	    //判断减刑幅度超过政策的颜色变成 红色
	    	var jianxingfudustatus = row.jxfdstatus;
	    	if(jianxingfudustatus){
	    		if(jianxingfudustatus==1){
	    			e.rowStyle = "background:#FF0000;";
	    		}	
	    	}
       	    
        	var nodeid = row.nodeid;
        	var freeResid = row.freeresid;
        	var freeResid2="";
            var s = '';
         	if(nodeid == 0 && xiugaiButtonObj){
         		
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+xiugaiButtonArr[1]+"\" >"+xiugaiButtonArr[0]+"</a>";
         	}else if(chakanButtonObj){
         		
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+chakanButtonArr[1]+"\" >"+chakanButtonArr[0]+"</a>";
         	}else if(freeResid){//自由资源列中按钮
         		freeResid2 = freeResidButton[1].replace("resid",freeResid);
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+freeResid2+"\" >"+freeResidButton[0]+"</a>";  		
         	}else{
         		s += '${middlestr}';
            }
         	
    	    return s;
        }
        
        
        grid.on("drawcell",function(e){
      		 var record = e.record,
   		     column = e.column,
   		     field = e.field,
   		     value = e.value;
   		     var nodeid = record.nodeid;
   		     //alert(field);
   		     //alert(nodeid);
   		     //给帐号列，增加背景色
             if (field == "processstate" ) {
             	//alert(nodeid);
             	if(nodeid=='0'){
                	e.cellStyle = "background:#c4f1da";
                }else if(nodeid=='N0001'){
                	e.cellStyle = "background:#c4c9f1";
                }else if(nodeid=='N0002'){
                	e.cellStyle = "background:#a8d8ee";
                }else if(nodeid=='N0002_01'){
                	e.cellStyle = "background:#b6d7fb";
                }else if(nodeid=='N0003'){
                    e.cellStyle = "background:#adc4f8"; 
                }else if(nodeid=='N0003_1'){
                	e.cellStyle = "background:#DDA0DD";
                }else if(nodeid=='N0004'){
                	e.cellStyle = "background:#dfe4e0";
                }else if(nodeid=='N0005'){
                	e.cellStyle = "background:#f0f7cb";
                }else if(nodeid=='N0006'){
                	e.cellStyle = "background:#228B22";
                }else if(nodeid=='N0006_1'){
                	e.cellStyle = "background:#BC8F8F";
                }else if(nodeid=='N0007'){
                	e.cellStyle = "background:#808000";
                }else if(nodeid=='N0008'){
                	e.cellStyle = "background:#8FBC8F";
                }else if(nodeid=='N0009'){
                	e.cellStyle = "background:#90EE90";
                }else if(nodeid=='N0010'){
                	e.cellStyle = "background:#6495ED";
                }else if(nodeid=='1'){
                	e.cellStyle = "background:#f9d6cd";
                }else if(nodeid=='-1'){
                	e.cellStyle = "background:#fde8c7";
                }
             }
   		});

    	
        function onKeyEnter(e) {
            search();
        };
        
        function onValueChanged(e) {
            search();
        };
        
        function search(){
        	var topsearchkey = '${topsearchkey}';
        	if(topsearchkey){
        		var topsearchkeyArr = topsearchkey.split(',');
        	}
        	var data = {};
        	for(var i=0,l=topsearchkeyArr.length; i<l ; i++){
        		var key = topsearchkeyArr[i]
        		 var value = mini.get(key).getValue();
        		 if(value){
        			 data[key] =encodeURI( value );
            	 }
        	}
        	
            grid.load(data);
        };
    </script>
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
</body>  
</html>