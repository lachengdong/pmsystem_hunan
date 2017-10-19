<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sinog2c.model.system.SystemResource"%>
<%@ page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();
	// 	
	String furl = request.getParameter("furl");
	if(null == furl){
		furl = "";
	}
	// 	
	String ids = request.getParameter("ids");
	if(null == ids){
		ids = "";
	}
	//
	String indexFlag = request.getParameter("indexFlag");
	if(null == indexFlag || indexFlag.trim().isEmpty()){
		indexFlag = "0";
	}
	
	//
	//组织列中按钮的修改和查看功能：前提是配资源时，资源的相关信息有：xiugai,修改,处理	chakan,查看
	Object middlemapObj = request.getAttribute("middlemap");
	String xiugaiButton = "";
	String chakanButton = "";
	String addButton = "";
	if(null != middlemapObj&&middlemapObj instanceof HashMap){
		Map middlemap = (HashMap)middlemapObj;
		Set<String> set = middlemap.keySet();
		for(String buttonkey : set){
			String buttonValue = middlemap.get(buttonkey).toString();
			if(buttonValue.indexOf("xiugai") != -1||buttonValue.indexOf("edit") != -1 || buttonValue.indexOf("修改") != -1 || buttonValue.indexOf("处理") != -1){
				xiugaiButton = buttonValue;
			}else if(buttonValue.indexOf("chakan") != -1 || buttonValue.indexOf("check") != -1 || buttonValue.indexOf("查看") != -1){
				chakanButton = buttonValue;
			}else if(buttonValue.indexOf("add") != -1 || buttonValue.indexOf("处理") != -1){
				addButton = buttonValue;
			}
		}
	}
	
	//
	SystemResource grid1 = null;
	List<SystemResource> grid1ColList = new ArrayList<SystemResource>();
	String flowdefid = "";
	String applyid = "";
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
	Object applyidObj = request.getAttribute("applyid");
	if(null != applyidObj){
		applyid = applyidObj.toString();
	}
	
%>

<html>
	<head>
		<title></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }     
    	</style>
</head>
<body>
	<div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
		<input id="applyid" name="applyid" class="mini-hidden" value="${applyid}"/>
		<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
		<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
        <table>
               <tr>
                <td style="width:100%;">
                	${topstr}
                </td>
                <td style="white-space:nowrap;">
                	${topsearch}
                	<c:if test="${topsearch} !=null" >
                   		<a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
					</c:if>
					<!-- 操作说明 -->
	                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
    	
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
			 Object paraMapObject = request.getAttribute("paraMap");
			 if(null != paraMapObject && paraMapObject instanceof HashMap){
				 Map paraMap = (HashMap)paraMapObject;
				 if(StringNumberUtil.notEmpty(tempid)&&paraMap.containsKey("tempid")){
				 	paraMap.remove("tempid");
				 }
				 if(StringNumberUtil.notEmpty(solutionid)&&paraMap.containsKey("solutionid")){
				 	paraMap.remove("solutionid");
				 }
				 if(StringNumberUtil.notEmpty(applyid)&&paraMap.containsKey("applyid")){
				 	paraMap.remove("applyid");
				 }
				 if(StringNumberUtil.notEmpty(flowdefid)&&paraMap.containsKey("flowdefid")){
				 	paraMap.remove("flowdefid");
				 }
				 if(urlSb.indexOf("?") == -1){
				 	urlSb.append("?1=1");
	        	 }
	        	 
				 Set<String> set = paraMap.keySet();
				 for(String key : set){
					 Object valueObj = paraMap.get(key);
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
        	 if(StringNumberUtil.notEmpty(applyid) && urlSb.indexOf("applyid") == -1){
        	 	urlSb.append("&applyid=").append(applyid);
        	 }
        	 url = urlSb.toString();
         %>
         
         <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[20,50,100]" pageSize="20"
        	 url="<%=url%>" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false"  showLoading="true">
          <div property="columns">
              <div type="checkcolumn" width="15px"></div>
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
              <div name="action" width="40" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>  
          </div> <!-- end of columns -->
        </div> <!-- end of mini-datagrid -->
         <%
         }else{
         %>
		    <div id="datagrid1" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" sizeList="[20,50,100]" pageSize="20"
		    url="getBaseDocByTempletID.action?1=1&applyid=${applyid}&tempid=${tempid}&flowdefid=${flowdefid}"  idField="" multiSelect="true" allowAlternating="true" showLoading="true">
		        <div property="columns">
		       		<div type="checkcolumn" width="15px"></div>
		<!--     	<div field="flowdraftid" headerAlign="center" align="left" allowSort="true" width="150px;"  visible="false">主键</div>  -->
		    		<div field="conent" headerAlign="center" align="center" allowSort="true" width="100px;">文书简介</div>
		    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="50px">操作人</div>
		    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="50px">操作时间</div>
		    		<div field="state" headerAlign="center"  align="center" allowSort="true"width="50px" renderer="onStatusRenderer">状态</div>
		   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="60px">操作</div> 
		        </div>
		    </div>
	     <%
         }
         %>
	    
    </div>
    
    <div style="height:0;">
		<form id="hidden_formid" method="post" url="">
			<input type="hidden" id="hidden_ids" name="ids" value=""/>
			<input type="hidden" id="hidden_furl" name="furl" value=""/>
		</form>
	</div>
	
    <script type="text/javascript"> 
    	
    	mini.parse();
    	var path="<%=path%>";
	   	var _furl = "<%=furl %>";
	   	var ids = "<%=ids%>";
	   	var total = ids.split(",").length;
	   	var indexFlag = <%=indexFlag%>; //数字,不加双引号
	   	
    	var grid = mini.get("datagrid1");
    	grid.load();
        //
        var xiugaiButtonObj = "<%=xiugaiButton%>";
        var chakanButtonObj = "<%=chakanButton%>";
        var addButtonObj = "<%=addButton%>";
        var xiugaiButtonArr = xiugaiButtonObj.split('@');
        var chakanButtonArr = chakanButtonObj.split('@');
        var addButtonArr = addButtonObj.split('@');
        function onActionRenderer(e){
        	var row = e.record;
        	var nodeid = row.nodeid;
        	var state = row.state;
         	var s = '';
         	if(nodeid == 0){
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+xiugaiButtonArr[1]+"\" >"+xiugaiButtonArr[0]+"</a>";
         	}else if(nodeid != 0 && nodeid != null){
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+chakanButtonArr[1]+"\" >"+chakanButtonArr[0]+"</a>";
         	}else if(nodeid == null){
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+addButtonArr[1]+"\" >"+addButtonArr[0]+"</a>";
         	}
    	    return s;
        }
        //
        
        function onStatusRenderer(e){
        	var s = "";
        	var row = e.record;
        	var state = row.state;
        	if(state=='-1'||state=='0'||state=='-2'){
        		s ='正在办理中&nbsp';
        	}else if(state=='1'){
        		s ='审批通过&nbsp';
        	}else if(state=='2'){
        		s ='审批退回&nbsp';
        	}else if(state=='3'){
        		s ='审批不通过&nbsp';
        	}else{
        		s = '';
        	}
            return s;
        }
	   	//
	   	function onKeyEnter(e) {
            search();
        };
        
        function onValueChanged(e){
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
        		data[key] =encodeURI( mini.get(key).getValue() );
        	}
        	
            grid.load(data);
        };
       
      //上一个
      function toPrevious(){
    	indexFlag--;
		if(indexFlag < 0){
     		return goBack();
      	}
		var url = location.href;
		toChooseOnePage(url,ids,_furl,indexFlag);
      }
      
      //下一个
      function toNext(){
    	indexFlag++;
  		if(indexFlag > total-1){
       		return goBack();
        }
  		var url = location.href;
  		toChooseOnePage(url,ids,_furl,indexFlag);
      }
        
	</script>
	<script src="<%=path %>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html>