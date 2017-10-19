<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title></title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/form/formPopUpBox.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
	   <div id="toolbar1" class="mini-toolbar" >
	   	 <input id="outputparam" name="outputparam" class="mini-hidden" value=""/>
		 <input id="inputparam" name="inputparam" class="mini-hidden" value=""/>
		 
		 <input id="popup_zuzhang0" name="popup_zuzhang0" class="mini-hidden" value=""/>
		 <input id="popup_zuzhang1" name="popup_zuzhang1" class="mini-hidden" value=""/>
		 <input id="popup_zuzhang2" name="popup_zuzhang2" class="mini-hidden" value=""/>
		 <input id="popup_zuzhang3" name="popup_zuzhang3" class="mini-hidden" value=""/>
		 <input id="popup_zuzhang4" name="popup_zuzhang4" class="mini-hidden" value=""/>
		 <input id="popup_zuzhang5" name="popup_zuzhang5" class="mini-hidden" value=""/>
		 
		 <input id="xuhao0" name="xuhao0" class="mini-hidden" value=""/>
		 <input id="xuhao1" name="xuhao1" class="mini-hidden" value=""/>
		 <input id="xuhao2" name="xuhao2" class="mini-hidden" value=""/>
		 <input id="xuhao3" name="xuhao3" class="mini-hidden" value=""/>
		 <input id="xuhao4" name="xuhao4" class="mini-hidden" value=""/>
		 <input id="xuhao5" name="xuhao5" class="mini-hidden" value=""/>
		 
		 <input id="fajinjiaonaqingkuang" name="fajinjiaonaqingkuang" class="mini-hidden" value=""/>
		 <input id="paymentpc" name="paymentpc" class="mini-hidden" value=""/>
		 
		 <input id="members0" name="members0" class="mini-hidden" value=""/>
		 <input id="members1" name="members1" class="mini-hidden" value=""/>
		 <input id="members2" name="members2" class="mini-hidden" value=""/>
		 <input id="members3" name="members3" class="mini-hidden" value=""/>
		 <input id="members4" name="members4" class="mini-hidden" value=""/>
		 <input id="members5" name="members5" class="mini-hidden" value=""/>
		 
		 <input id="id0" name="id0" class="mini-hidden" value=""/>
		 <input id="id1" name="id1" class="mini-hidden" value=""/>
		 <input id="id2" name="id2" class="mini-hidden" value=""/>
		 <input id="id3" name="id3" class="mini-hidden" value=""/>
		 <input id="id4" name="id4" class="mini-hidden" value=""/>
		 <input id="id5" name="id5" class="mini-hidden" value=""/>
		 
		 <input id="leaderid0" name="leaderid0" class="mini-hidden" value=""/>
		 <input id="leaderid1" name="leaderid1" class="mini-hidden" value=""/>
		 <input id="leaderid2" name="leaderid2" class="mini-hidden" value=""/>
		 <input id="leaderid3" name="leaderid3" class="mini-hidden" value=""/>
		 <input id="leaderid4" name="leaderid4" class="mini-hidden" value=""/>
		 <input id="leaderid5" name="leaderid5" class="mini-hidden" value=""/>
		 
		 <input id="membersid0" name="membersid0" class="mini-hidden" value=""/>
		 <input id="membersid1" name="membersid1" class="mini-hidden" value=""/>
		 <input id="membersid2" name="membersid2" class="mini-hidden" value=""/>
		 <input id="membersid3" name="membersid3" class="mini-hidden" value=""/>
		 <input id="membersid4" name="membersid4" class="mini-hidden" value=""/>
		 <input id="membersid5" name="membersid5" class="mini-hidden" value=""/>
		 
	     <table style="width:100%;">
	        <tr>
		        <td style="width:100%;">
		        	<label style="font-family:Verdana;">互监编排情况</label>           
		            <span class="separator"></span>
		               互监类型
		            <input id="svtype" class="mini-combobox" style="width:80px;" textField="name" valueField="codeid"  onvaluechanged="search();"
		            	url="ajaxListByMap.action?codetype=GK199" showNullItem="true" /> 
		 				
					<input id="svduty" class="mini-combobox" style="width:80px;" textField="text" valueField="id" 
		 				data='[{ "id": "1", "text": "组长姓名" }, { "id": "0", "text": "组员姓名" }]' value="1"  required="true" /> 
		 				
		 		 	<input id="key" class="mini-textbox" emptyText="请输入姓名"  vtype="text" onenter="onKeyEnter"/>
		            <a class="mini-button" onclick="search();" style='width:60px'>搜索</a>
		        </td>
		        <td style="white-space:nowrap;"><!--label style="font-family:Verdana;">Filter by: </label>-->   
		        	<a class="mini-button" onclick="clear();" style='width:60px' >清空</a>
		        </td>
	        </tr>
	    </table>
	 </div>
	
	 <div class="mini-fit" id="div_two">  
	        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" idField="id" allowResize="false" shouSizeList="false"
	          sizeList="[10,20,50,100]" pageSize="10" showReloadButton="true"
	          url="<%=path%>/ajaxGetYXHJList.json" allowAlternating="true" showLoading="false">
	            <div property="columns">
	            	<div field="leaderid" visible="false" width="40" headerAlign="center"  allowSort="false" >组长狱号</div>
	            	<div field="membersid" visible="false" width="40" headerAlign="center"  allowSort="false" >组员狱号</div>
	                <div type="indexcolumn" headerAlign="center" >编号</div>
	                <div field="svtype" width="40" headerAlign="center" align="center" allowSort="false" renderer="onSvtypeRenderer" >互监类型</div>    
	                <div field="leader" width="40" headerAlign="center" align="center" allowSort="false">组长姓名</div>                                    
	                <div field="members" width="120" headerAlign="center"align="center"  allowSort="false">组员姓名</div>                                    
	                <div field="op" width="60" headerAlign="center" allowSort="false" renderer="onActionRenderer" >操作</div>
	            </div>
	        </div>        
	</div>

  </body>
     <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        function onActionRenderer(e) {
       		var s = "<a class='mini-button' style='width:100%' onclick='ensure();'> 确定 </a> ";	     
			return s;
       }
        
        function onSvtypeRenderer(e) {
        	if(e && e.value){
        		if(e.value=='1'){
        			return "生活互监";
        		}else if(e.value=='2'){
        			return "劳动互监";
        		}else if(e.value=='3'){
        			return "学习互监";
        		}
        	}
            return "";
       }
        
       //向表单返回值
     function ensure(){
    	   var xuhao = null;
    	   for(var i=0; i<6; i++){
    			xuhao = mini.get("xuhao" + i).getValue();
    			if (xuhao != null && xuhao != undefined & xuhao != ''){
    				break;
    			}
    	   }
    	   
    	   var row = grid.getSelected();
           var svtype = row.svtype;
           var leader = row.leader;
           var members = row.members;
           var leaderid = row.leaderid ;
           var membersid = row.membersid;
           var groupid = row.groupid;
           var groupnum = row.groupnum;
           
           var idValue = groupid;
           for(var i=0; i<6; i++){
        	   if(idValue == mini.get("id" + i).getValue()){
        		   alert("该组已被选过，不能再选！");
        		   return;
        	   }
	   	   }
           
           mini.get("id" + xuhao).setValue(idValue);
           mini.get("popup_zuzhang" + xuhao).setValue(leader);
           mini.get("members" + xuhao).setValue(members);
           mini.get("leaderid" + xuhao).setValue(leaderid);
           mini.get("membersid" + xuhao).setValue(membersid);
           
           countNum();
           
           CloseWindow("ok");
       }
       
       function clear(){
    	   var xuhao = null;
    	   for(var i=0; i<6; i++){
    			xuhao = mini.get("xuhao" + i).getValue();
    			if (xuhao != null && xuhao != undefined & xuhao != ''){
    				break;
    			}
    	   }
    	   
    	   mini.get("id" + xuhao).setValue(null);
    	   mini.get("popup_zuzhang" + xuhao).setValue(null);
    	   mini.get("members" + xuhao).setValue(null);
    	   mini.get("leaderid" + xuhao).setValue(null);
    	   mini.get("membersid" + xuhao).setValue(null);
    	   
    	   countNum();
    	   
    	   CloseWindow("ok");
       }
       
       function countNum(){
    	   
    	   var fajinjiaonaqingkuang = 0 //几组
           for(var i=0; i<6; i++){
        	    var leaderid = mini.get("leaderid" + i).getValue();
	   			if (leaderid != null && leaderid != undefined & leaderid != ''){
	   				fajinjiaonaqingkuang ++;
	   			}
	   	   }
           mini.get("fajinjiaonaqingkuang").setValue(fajinjiaonaqingkuang);
           
           var paymentpc = 0; //共多少人
           for(var i=0; i<6; i++){
       	    	var membersid = mini.get("membersid" + i).getValue();
	   			if (membersid != null && membersid != undefined & membersid != ''){
	   				paymentpc += membersid.split(",").length;
	   			}
	   	   }
           paymentpc += fajinjiaonaqingkuang;
           mini.get("paymentpc").setValue(paymentpc);
           
       }
       
       
       
	   function beforeOperate(){
		   
	 	}
     
     	function afterOperate(){
	    	//可为空
	    }
       
        // 搜索姓名
      function search(){
             var svtype = mini.get("svtype").getValue();
             var key = mini.get("key").getValue();
             var svduty = mini.get("svduty").getValue();
             grid.load({ svtype:svtype, svduty: svduty , key: key});
        }
        
        function onKeyEnter(){
        	search();
        }
        
    </script>
</html>
