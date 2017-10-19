<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemResource"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
String path = request.getContextPath();
%>
<%
	List<SystemResource> topButtonList = new ArrayList<SystemResource>();
	List<SystemResource> inlineButtonList = new ArrayList<SystemResource>();
	SystemResource grid1 = null;
	List<SystemResource> grid1ColList = new ArrayList<SystemResource>();
	//
	Object topButtonList_attr = request.getAttribute("topButtonList");
	Object inlineButtonList_attr = request.getAttribute("inlineButtonList");
	Object grid1_attr = request.getAttribute("grid1");
	Object grid1ColList_attr = request.getAttribute("grid1ColList");
	//
	if(topButtonList_attr instanceof List<?>){
		topButtonList = (List<SystemResource>)topButtonList_attr;
	}
	if(inlineButtonList_attr instanceof List<?>){
		inlineButtonList = (List<SystemResource>)inlineButtonList_attr;
	}
	if(grid1_attr instanceof SystemResource){
		grid1 = (SystemResource)grid1_attr;
	}
	if(grid1ColList_attr instanceof List<?>){
		grid1ColList = (List<SystemResource>)grid1ColList_attr;
	}
	//
	String jsonParamObjectStr = "{}";
	Object jsonParamObject = request.getAttribute("jsonParamObject");
	if(null != jsonParamObject){
		jsonParamObjectStr = jsonParamObject.toString();
	}
	//
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>选择罪犯</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }  
    </style>
</head>
<body onload="init('${menuid }');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
                	<%-- 此处显示 top 按钮 --%>
                	<%
                		for(SystemResource button : topButtonList){
                			//
                			String resid = button.getResid();
                			String showico = button.getShowico();
                			String srurl = button.getSrurl();
                			String name = button.getName();
                			//
                  			// 转义 
                  			if(null != srurl){
                  				srurl = srurl.replace("\"", "\\\"");
                  				//srurl = srurl.replace("\'", "\\\'");
                  			}
                			%>
                	<a class="mini-button" id="<%=resid %>" iconCls="<%=showico %>"
                		href="javascript:<%=srurl %>"
                		plain="true" onclick=""><%=name %></a>
                			<%
                		}
                	%>
                	<%--
                	<a class="mini-button" style="display:none;" id="13693" iconCls="" plain="true" onclick="chooseAll('13694')">批量处理</a>
                	 --%>
                	
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
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
        	 String gridId = "datagrid1";
        	 String flowdefid = grid1.getFormid(); //"doc_zfrjdjsp";
        	 String solutionid = grid1.getQuerysql(); //"doc_zfrjdjsp";
        	 String uuu = grid1.getSrurl();
        	 //
        	 String url = path + "/common/listprisoner.json?1=1";
        	 //
        	 url += "&flowdefid=" + flowdefid;
        	 url += "&solutionid=" + solutionid;
        	 url += "&flowdefid=" + flowdefid;
        	 
         %>
         
         <div id="<%=gridId %>" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="<%=url%>" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="true">
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
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
	    		 // 列
         %>
              <div field="<%=field %>" width="<%=width %>" headerAlign="center" align="center" allowSort="true" <%=rendererStr %> ><%=name %></div>    
         <%
         	}
         %>
              <div name="action" width="40" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>  
          </div> <!-- end of columns -->
        </div> <!-- end of mini-datagrid -->
         <%
         }
         %>
    </div> <!-- end of div_two -->
    
 	<script src="<%=path%>/common/customjs.json?menuid=${menuid}" type="text/javascript"></script>
    <script type="text/javascript">
	    //
	    var menuid = "${menuid}";
	    //
	    mini.parse();
        
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        //
        var jsonParamObject = <%=jsonParamObjectStr%>;
        //
        function loadGrid(){
        	var tempid = mini.get("tempid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	//
       		var url = "<%=path%>/common/listprisoner.json?1=1";
       		url += "&tempid="+tempid+"&solutionid="+solutionid+"&menuid="+menuid+"&fathermenuid=${menuid}";
       		if(row.idnumber && row.duty != '-1'){
       			url += "&idnumber="+row.idnumber;//如果罪犯审批未通过重新生成一条数据
       		}
        	
        };
        	
         // 如果idnumber存在表示已经处理,duty=-1表示审批未通过,显示处理。
         // 如果duty=0存在表示已经处理,accent=1表示未进入流程审批中,显示处理
         // 应该根据资源构建渲染信息
         function onActionRenderer(e) {
        	// 
	        var record = e.record;
        	// 这里要怎么处理?
        	// 显示条件 ?
        	//var menuid_process = "11090";
        	var menuid_process = "14287";
        	//
        	<%--
	        var s = '';
        	if(record.idnumber && record.duty != '-1' ){
        		if(record.duty=='0' || record.accent=='1'){
        			s += ' <a href="javascript:chooseOne(11090);" >修改</a>';
        		}else {
        			s += ' <a href="javascript:chooseOne(11106);" >查看</a>';
        		}
        	}else{
        		s += ' <a href="javascript:chooseOne('+ menuid_process +');" >处理</a>';
        	} 
     		--%>
          	<%-- 此处显示 inline 按钮 --%>
        	// 此处处理 inline 按钮
        	var s_process = "";
          	<%
          		for(SystemResource button : inlineButtonList){
          			//
          			String resid = button.getResid();
          			String showico = button.getShowico();
          			String srurl = button.getSrurl();
          			String name = button.getName();
          			// 转义 
          			if(null != srurl){
          				srurl = srurl.replace("\"", "\\\"");
          				srurl = srurl.replace("\'", "\\\'");
          			}
          			//
          			%>
          	s_process += ' <a href="javascript:<%=srurl %>" ><%=name %></a>';
          			<%
          		}
          	%>
        	
        	//
        	//
            return s_process;// || s;
        };
        
        //根据罪犯编号跳转到表单页面，如果寻找罪犯未处理或者审批未通过,没有流程idnumber,生成一条新数据。
        function chooseOne(menuid) {
        	
        	//
        	var row = grid.getSelected();
        	var tempid = mini.get("tempid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	var furl = getfurl();
        	if(row){
        		//
        		var jsonParam = mini.clone(window["jsonParamObject"] || {});
        		//
        		jsonParam["crimid"] = row.crimid;
        		jsonParam["applyid"] = row.crimid;
        		jsonParam["fathermenuid"] = "${menuid}";
        		jsonParam["menuid"] = menuid;
        		jsonParam["furl"] = furl;
        		
        		//
        		url = processParam("<%=path%>/common/showform.page", jsonParam);
        		
        		//
				self.location.href=url;
        	}else{
        		alert(confirmMessage);
        	}
        };
        //
        function processParam(urlBase, jsParam){
        	//
        	var url = ""+urlBase+"?";
        	// 遍历
        	for(var key in jsParam){
        		url += key+ "=" + jsParam[key] + "&";
        	}
        	url += "__=0";
        	//
        	return url;
        };
        
        
        //批量处理
  		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
        	var tempid = mini.get("tempid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
           	if(rows.length > 0){
           		var ids = [];
                   for (var i = 0, l = rows.length; i < l; i++) {
                       var r = rows[i];
                       if(r.accent=='2' && r.duty != '-1') continue;//审批中的罪犯不能进行批量处理
                       if(r.idnumber && r.duty != '-1'){//如果罪犯审批未通过重新生成一条数据
                       		ids.push(r.crimid+"@"+r.idnumber);
                       }else{
                       	    ids.push(r.crimid+"@");
                       }
                   }
				   if(ids.length == '0'){//所选中的数据都是审批中的
				   		alert(noProcessing);
				   		return ;
				   }else {
				   		if (confirm(allProcessing)) {
				   			var id = ids.join(',');
                   			var url = "<%=path%>/basicInfo/basicInformation.page?1=1&id="+id+"&menuid="+menuid+"&tempid="+tempid +"&solutionid="+solutionid+"&fathermenuid=${menuid}";
                   			self.location.href=url;
				   		}
				   }
           	}else {
               	alert(confirmMessages);
           	}   
		};
        //快速查询
        function onKeyEnter(e) {
            search();
        };
        function search() {
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid.sortBy("crimid","desc");
            grid.load({ key: key });
        };
    </script>
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
</body>  
</html>