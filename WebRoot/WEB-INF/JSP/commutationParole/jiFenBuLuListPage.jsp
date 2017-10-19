<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	String path = request.getContextPath();
	String closetype = request.getAttribute("closetype")==null ? "" : (String)request.getAttribute("closetype");
%>
<html>
	<head>
	<title>计分补录</title>
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
<body onload="init('${menuid}');">
	<div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
     	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
              <tr>
                <td style="width:50%;">
                    <span style="color:#ff3300;">当前罪犯：${crimid} ${name}</span>
               	 	<a class="mini-button" style="display:black;" iconCls="icon-add" plain="true"  id="12073" onclick="add('12073');">新增</a>
               	 	<a class="mini-button" style="display:black;" id="gb" iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
                </td>
                <td style="width:50%;" align="right">
                                                                      累计表扬：
               	 	<input class="mini-spinner" id="leijibiaoyang"  name="leijibiaoyang"  style="width:50px;" minValue="-1000" maxValue="20000" value="${leijibiaoyang}" />个　余
               	 	<input class="mini-spinner" id="yufen"  name="yufen"  style="width:50px;" minValue="-1000" maxValue="20000" value="${yufen}" />分
               	 	<a class="mini-button" style="display:black;" id="save"  plain="true" onclick="BiaoYangYuFen();">保存</a>               	 	
                </td>
                <td style="white-space:nowrap;">
                 	 <a class="mini-button"   plain="true" onclick="search()"></a>
                </td>
              </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" 
	    	class="mini-datagrid"  allowResize="false" url="getJiFenBuLuByCrimid.json?1=1&crimid=${crimid}&tempid=${tempid}"  
	    	idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="25"></div>
              	<div type="indexcolumn" width="25" headerAlign="center">序号</div>
              	<div field="id" headerAlign="center"  align="center" allowSort="true" width="40px">编号</div>
	    		<div field="awps_abstract" headerAlign="center"  align="center" allowSort="true" width="300px">奖惩描述</div>
	    		<div field="awps_score" headerAlign="center"  align="center" allowSort="true" width="40px">奖惩分数</div>
	    		<div field="awps_date" headerAlign="center"  align="center" allowSort="true" width="60px">奖惩时间</div>
	    		<div field="entry_name" headerAlign="center"  align="center" allowSort="true" width="45px">修改人</div>
	   			<div field="entry_date" headerAlign="center" align="center" allowSort="true" width="60px;">修改时间</div>
	   			<div name="action" headerAlign="center" align="center"  width="60px" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
    </div>
   <script type="text/javascript">
   
   		mini.parse();
   		if('${toolbar}'=='0'){
   			$("#gb").hide();//经办人办案关闭按钮不显示
   		}
   		
   		var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("caseno", "desc");
        
        var closetype = '<%=closetype%>';
	    
      	function onActionRenderer(e){
        	var record = e.record;
        	var isthrough = record.isthrough;
            var toolbar = '${toolbar}';  
            if(isthrough == 1) toolbar = '0';
        	var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
        	if(toolbar =='0') s = '<a class="Edit_Button" href="javascript:chakan(\'12618\');">查看</a>';//经办人办案查看按钮显示
	     	return s;
        }

        //新增
      	function add(menuid){
      		var crimid = mini.get("crimid").getValue();
      		var tempid=mini.get("tempid").getValue();
      		var flag = getJxJsCase(crimid);
			if(!flag){alert("减刑案件已经开始办理、无法再新增!");return;}
			
			
	 		mini.open({
	 	        url: "toJiFenBuLuAdd.page?1=1&crimid=" + crimid+"&menuid="+menuid+"&tempid=XFZX_JFBL&operator=new",
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "新增", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	   });	
 	   }
        
        
      function xiugai(menuid){
    	  
    	  	var crimid = mini.get("crimid").getValue();
	  		var tempid=mini.get("tempid").getValue();
	  		var menuid =  '12073';
	  		var flag = getJxJsCase(crimid);
			if(!flag){alert("减刑案件已经开始办理、无法修改!");return;}
			//
			var row = datagrid.getSelected();
	    	if(row){
				mini.open({
					url: "toJiFenBuLuAdd.page?1=1&id="+row.id+"&crimid=" + crimid+"&menuid="+menuid+"&tempid=XFZX_JFBL&operator=edit",
					showMaxButton: true,
					allowResize: false,
					title: "修改", width: 900, height: 500,
					onload: function () {
						var iframe = this.getIFrameEl();
						var data = { action: "new" };
						iframe.contentWindow.SetData(data);
					},
					ondestroy: function (action) {
						datagrid.reload();
					}
			   });	
	   		}
      }
      
      
		//删除
		function remove(menuid){
			var crimid = mini.get("crimid").getValue();
			var flag = getJxJsCase(crimid);
	    	if(!flag){alert("减刑案件已经开始办理、无法删除!");return;}
	    	//
	    	var row = datagrid.getSelected();
	    	
	    	var url = "removeJiFenBuLu.json?1=1&id="+row.id+"&crimid="+crimid;
	    	if(row){
	    		if(confirm("确定操作选中的记录吗？")){
					 $.ajax({
	                   url: url,
			           type: "post",
	                   success: function (text) {
	                   		if(text==1){
		        	    		alert("操作成功！");
		        	    	}else{
		        	    		alert("操作失败！");
		        	    	}
	                        datagrid.reload();
	                   },
	                   error: function (jqXHR, textStatus, errorThrown) {
			               alert("操作失败！");
			           }
	               });
	    		}
	    	}else{
	    		alert("请选择记录！");
	    		return false;
	    	}
	   }
	    
		//关闭
       function onCanle(){
		   var _criminalid =mini.get("crimid").getValue();
		   if(closetype){
			   window.CloseOwnerWindow("cancel");
		   }else{
			   url = "jiFenBuLu.page?1=1&_criminalid="+_criminalid;
        	   self.location.href=url;
		   }
       }
	
       //表扬余分
       function BiaoYangYuFen(){
    	   var crimid = mini.get("crimid").getValue();
    	   var kaohezongfen = "";
    	   var leijibiaoyang = mini.get("leijibiaoyang").getValue();//累计表扬
   	       var yufen = mini.get("yufen").getValue(); //余分
    	   var flag = getJxJsCase(crimid);
    	   if(!flag){alert("减刑案件已经开始办理、无法更新考核总分!");return;}
    	  
    	   $.ajax({
               url: "saveKaoHeZongFen.json?1=1&crimid="+crimid+"&kaohezongfen="+kaohezongfen+"&leijibiaoyang="+leijibiaoyang+"&yufen="+yufen,
	           type: "post",
               success: function (text) {
               		if(text==1){
        	    		alert("操作成功！");
        	    	}else{
        	    		alert("操作失败！");
        	    	}
               },
               error: function (jqXHR, textStatus, errorThrown) {
	               alert("操作失败！");
	           }
           });
       }
       
       
       ///flow/submitBaseInfo.action'
       function getJxJsCase(crimid){
			var nodeid = -1;
			$.ajax({
				url:'<%=path%>/flow/isDealJxjsCase.json',
				data:{crimid:crimid,flowdefid:'other_zfjyjxjssp'},
				type:'post',
				dataType:'json',
				async:false,
				success:function (text){
					nodeid = parseInt(text);
				}
			});
			if(nodeid == 0){
			    return true;
			}
			return false;
		}
	</script>  
</body>
</html>
