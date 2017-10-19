<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯收押审查表</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body>
	 <div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	 <div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	        <input id="operator" name="operator" class="mini-hidden" value=""/>
			<a class="mini-button"  id="12455" iconCls="icon-gk_page" tooltip="阅读" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
	    	<a class="mini-button"  id="12456" iconCls="icon-gk_fullscream" tooltip="全屏" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	${topsearch}
	    	${topstr}
	    	<!--  
	    	<a class="mini-button" style="display:none;" id="11622" iconCls="icon-save" plain="true" onclick="savedata()">存盘</a>
	    	<a class="mini-button" style="display:none;" id="11624" iconCls="icon-save" plain="true" onclick="savedata()">存盘</a>
	    	-->
			<a class="mini-button"  iconCls="icon-close" plain="true" onclick="onCancel()">关闭</a>
			<span class="separator"></span>
	    	<a class="mini-button"  id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
    	</td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  	</div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        
        //保存或更新
        function savedata(){
        	var introduction = null;
        	var docid = mini.get("docid").getValue();
			var crimid = mini.get("crimid").getValue();
        	var aipObj = document.getElementById("HWPostil1");
			var jianqu = aipObj.GetValueEx("jianqu",2,"",0,"");
			var danganhao = aipObj.GetValueEx("danganhao",2,"",0,"");
        	var checkboxval = aipObj.GetValueEx("Page1.CheckBox", 2, "", 0, "");
			var name = aipObj.GetValueEx("name",2,"",0,"").replace(/[ ]/g,"");
			var bianhao = aipObj.GetValueEx("bianhao",2,"",0,"").replace(/[ ]/g,"");
			
			if(bianhao && checkboxval=="符合要求;符合要求;齐全;齐全;齐全;未夹带危险物品;符合要求;"){
				introduction = "罪犯"+name+"符合收押条件，收押到"+jianqu+"，编号："+bianhao;
			}else{
				if(!crimid){
					alert("罪犯"+name+"不符合收押条件未被收押，收押审查表不能修改!");
					return;
				}
				aipObj.Setvalue("bianhao","");	//设置编号值为空
				aipObj.Setvalue("danganhao","");//设置档案号值为空
				introduction = "罪犯"+name+"不符合收押审查条件，未被收押";
			}
			if(!name) {
				 alert("罪犯姓名不能为空！");
				 return ;
			}
			if(!jianqu) {
				 alert("监区不能为空，请设置入监监区或选择监区！");
				 return ;
			}
			
       		var content = aipObj.GetCurrFileBase64();
       		var noteinfo = getNoteMap();
       		var tempid = mini.get("tempid").getValue();
       		$.ajax({
                url:encodeURI(encodeURI("checkCrimidAndFileno.action?1=1")),
                data: {crimid:bianhao,fileno:danganhao,flowdefid:"doc_zfrjdjsp"},
                type: "post",
                success: function (text) {
	                if(text=="save"){
             			alert("罪犯"+name+"的基本信息已保存，收押审查表不能修改!");
             			return;
                	}else if(text=="crimid" && !docid){
                	    alert("该罪犯编号已被使用，请更换罪犯编号！");
                	    return;
                    }else if(text=="fileno" && !docid){
                    	alert("该档案号已被使用，请更换档案号！");
                    	return;
                    }
	                
	                if(!docid){//新增
	            		$.ajax({
	    	                url:encodeURI(encodeURI("saveZFSYSCB.action?1=1")),
	    	                data: {content:content,tempid:tempid,noteinfo:noteinfo,introduction:introduction},
	    	                type: "post",
	    	                success: function (text) {
	    	                	alert("操作成功!");
	    	                  	onCancel();
	    	                },
	    	                error: function (jqXHR, textStatus, errorThrown) {
	    	                	alert("操作失败!");
	    	                }
	    	            });
	                }else{//更新
	    	            $.ajax({
	    	                url: encodeURI(encodeURI("saveZFSYSCB.action?1=1")),
	    	                data: {content:content,docid:docid,noteinfo:noteinfo,crimid:crimid,introduction:introduction},
	    	                type: "post",
	    	                success: function (text) {
	    	                	alert("操作成功!");
	                            onCancel();
	    	                },
	    	                error: function (jqXHR, textStatus, errorThrown) {
	    	                	alert("操作失败!");
	    	                }
	    	            });
	               }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
        }
		//标准方法接口定义
         function SetData(data) {
            data = mini.clone(data);
            mini.get("operator").setValue(data.action); 
          	mini.get("crimid").setValue(data.crimid); 
        }
        //关闭窗口
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
		function onCancel() {
            CloseWindow("cancel");
        }
    </script>
</body>
</html>
