<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
	<title>财产刑补录2</title>
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
	<div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
     	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
     	<input id="closetype" name="closetype" class="mini-hidden" value="${closetype}"/>
        <table >
              <tr>
                <td style="width:40%;">
                <input id="removestatus" name="removestatus" class="mini-hidden" value="${removestatus}"/>
                
               	 	<a class="mini-button" iconCls="icon-add" id="18575" plain="true"  id="add" onclick="add('18575');">新增</a>
               	 	<a class="mini-button" style="display:black;" id="gb" iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
               	 	<span style="color:#ff3300;">当前罪犯：${crimid} 　${name}　</span>
                </td>
                <td style="white-space:nowrap;text-align: center;">
                		<table>
                			<tr>
                				<td width="110" align="left">
										原 判 罚 金：${forfeit}
				                </td>
                				<td width="150" align="center">
										已执行罚金：${payment}　
				                </td>
				                <td width="150" align="center">
										原没收财产：${forfeitureproperty}　
				                </td>
				                <td width="150" align="center">
										已没收财产：${expropriation}
				                </td>
                			</tr>
			              <tr>
								<td width="110" align="left">
				                	原 判 民 赔：${compensation}
				                </td>
								<td width="150" align="center">
				                	    已履行民赔：${fulfilcompensation}
				                </td>
								<td width="150" align="center">
				                	 赃 款：
				                </td>
								<td width="150" align="center">
				                	    已追缴赃款：
				                </td>	
			              </tr>			              
                		</table>
                </td>
              </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" 
	    	class="mini-datagrid"  allowResize="false" url="getCaiChanByCrimid.json?1=1&crimid=${crimid}"
	    	idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
              	<div type="indexcolumn" width="15" headerAlign="center">序号</div>
	    		<div field="caichantype" headerAlign="center"  align="center" allowSort="true" width="40px">类型</div>
	    		<div field="status" headerAlign="center"  align="center" allowSort="true" visible="false" width="40px" >状态</div>
	   			<div field="zhixingdate" headerAlign="center" align="center" dateFormat="yyyy-MM-dd" allowSort="true" width="40px;" renderer="onDateRenderer">执行时间</div>
	    		<div field="zhixingjine" headerAlign="center"  align="center" allowSort="true" width="40px">执行金额</div>
	    		<div field="kaipiaodate" headerAlign="center"  align="center" dateFormat="yyyy-MM-dd" allowSort="true" width="40px" renderer="onDateRenderer">开票时间</div>
	    		<div field="zhixingjiguan" headerAlign="center"  align="center" allowSort="true" width="40px">执行机关</div>
	    		<div field="danjuhao" headerAlign="center"  align="center" allowSort="true" width="40px">执行单据号</div>
	    		<div field="weizhixing" headerAlign="center"  align="center" allowSort="true" width="40px">未执行部分</div>
	   			<div name="action" headerAlign="center" align="center"  width="60px" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
    </div>
   <script type="text/javascript">
   		mini.parse();
   		var datagrid = mini.get("datagrid");
        datagrid.load();
        function onActionRenderer(e){
        	var removestatus = mini.get("removestatus").getValue();
        	var record = e.record;
        	var s;
            if (record.status =='1') {
            	 s= '<a class="Edit_Button" href="javascript:chakan(18580);">查看</a>'; 
			}else {
	        	s = '<a class="Edit_Button" href="javascript:xiugai(18576);">修改</a>';
			}
            if(removestatus =='1'){
            	s+='&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:shanchu(18577);">删除</a>';
            }
	     	return s;
        }
        //新增
        function add(resid){
      		var crimid = mini.get("crimid").getValue();
      		//alert(crimid);
      		var row = datagrid.getSelected();
      		var zhixingdate = mini.formatDate (new Date(new Date()),"yyyy-MM-dd HH:mm:ss");

			var flag = getJxJsCase(crimid);
			if(!flag){
				alert("减刑案件已经开始办理、无法再新增!");
				return;
			}
	 		mini.open({
	 	        url: "caiChanAdd.page?1=1&crimid=" + crimid+"&operator=new&menuid="+resid,
	 	        title: "新　增", width: 600, height: 350,
	 	      	onload: function () {
                	var iframe = this.getIFrameEl();
               		 var data = {action:"edit",zhixingdate:zhixingdate};
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (action){
	                datagrid.reload();
	            }
	 	   });
 	   }
       //
        function xiugai(resid){
        	var crimid = mini.get("crimid").getValue();
        	var row = datagrid.getSelected();
        	var zhixingdate = row.zhixingdate;
        	zhixingdate = mini.formatDate (new Date(zhixingdate),"yyyy-MM-dd HH:mm:ss");
        	var caichantype=row.caichantype;
        	var kaipiaodate = row.kaipiaodate;
        	var danjuhao = row.danjuhao;
        	var caichan;
        	if (caichantype == "罚金") {
        		caichan="fajin";
			}else if(caichantype == "没收财产"){
				caichan="moshou";
			}else if (caichantype == "民事赔偿") {
				caichan="minpei";
			}else if (caichantype == "追缴赃款") {
				caichan="zangkuan";
			}else if (caichantype == "责令退赔") {
				caichan="tuipei";
			}
	 		mini.open({
	 	        url: "caiChanAdd.page?1=1&crimid="+crimid +"&operator=edit&zhixingdate="+zhixingdate+"&menuid="+resid+"&danjuhao="+danjuhao,
	 	        title: "修　改", width: 600, height: 350,
	 	       onload: function () {
            		var iframe = this.getIFrameEl();
	           		var data = { action:"edit",zhixingdate:zhixingdate,caichantype:caichan,kaipiaodate:kaipiaodate};
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (action){
	                datagrid.reload();
           		 }
	 	   });	
 	   }

        function chakan(resid){
        	var crimid = mini.get("crimid").getValue();
        	var row = datagrid.getSelected();
        	var zhixingdate = row.zhixingdate;
        	zhixingdate = mini.formatDate (new Date(zhixingdate),"yyyy-MM-dd HH:mm:ss");
        	var caichantype=row.caichantype;
        	var kaipiaodate = row.kaipiaodate;
        	var danjuhao = row.danjuhao;
        	var caichan;
        	if (caichantype == "罚金") {
        		caichan="fajin";
			}else if(caichantype == "没收财产"){
				caichan="moshou";
			}else if (caichantype == "民事赔偿") {
				caichan="minpei";
			}else if (caichantype == "追缴赃款") {
				caichan="zangkuan";
			}else if (caichantype == "责令退赔") {
				caichan="tuipei";
			}
	 		mini.open({
	 	        url: "caiChanAdd.page?1=1&crimid="+crimid +"&operator=chakan&zhixingdate="+zhixingdate+"&menuid="+resid+"&danjuhao="+danjuhao,
	 	        title: "查　看", width: 600, height: 350,
	 	       onload: function () {
            		var iframe = this.getIFrameEl();
	           		var data = { action:"chakan",zhixingdate:zhixingdate,caichantype:caichan,kaipiaodate:kaipiaodate};
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (action){
	                datagrid.reload();
           		 }
	 	   });	
 	   }
        function shanchu(resid){
        	var crimid = mini.get("crimid").getValue();
        	var row = datagrid.getSelected();
        	var zhixingdate = row.zhixingdate;
        	zhixingdate = mini.formatDate (new Date(zhixingdate),"yyyy-MM-dd HH:mm:ss");
        	var caichantype=row.caichantype;
        	var danjuhao = encodeURI(encodeURI(row.danjuhao));
        	var caichan;
        	if (caichantype == "罚金") {
        		caichan="fajin";
			}else if(caichantype == "没收财产"){
				caichan="moshou";
			}else if (caichantype == "民事赔偿") {
				caichan="minpei";
			}else if (caichantype == "追缴赃款") {
				caichan="zangkuan";
			}else if (caichantype == "责令退赔") {
				caichan="tuipei";
			}
        	if (confirm("确定要删除吗？")) {
	        	$.ajax({
					url:"deleteByIdAndZhiDate.action?1=1&crimid="+crimid+"&zhixingdate="+zhixingdate+"&caichantype="+caichan+"&danjuhao="+danjuhao,
					success: function(text){
						alert("操作成功");
	        			datagrid.load();	
					},
					error: function (jqXHR, textStatus, errorThrown) {
						alert("操作失败"); 
						datagrid.load();	
		            }
	           });
			}
        }
       function onCanle(){
    	    var closetype = mini.get('closetype').getValue();
    	    if(closetype){
    	    	window.CloseOwnerWindow("cancel");
    	    }else{
    	    	url = "toCrimeCaiChanList.page?1=1";
            	self.location.href=url;
    	    }
       }
     
       //判断案件该罪犯是否已经正在进行案件办理 如果正在进行那么就不能操作
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
