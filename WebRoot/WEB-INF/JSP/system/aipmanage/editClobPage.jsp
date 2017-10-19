<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>表单大字段修改</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path %>/css/CIC.css" rel="stylesheet" type="text/css"/>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
		    }   
		</style>
		<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
		<script language="JavaScript" for="HWPostil1" event="NotifyCtrlReady">
			// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
			document.all.HWPostil1.JSEnv = 1;
		</script>
	</head>

	<body>
		<div id="form1" class="mini-splitter" vertical="true" style="width: 100%; height: 100%; border: 0px;">
			<div size="180px;" showCollapseButton="false">
				<div class="mini-toolbar" style="height:180px;">
					<table>
						<tr>
							<td style="width:100%;">
						    	主键方案ID：<input id="keysolutionid" name="keysolutionid" value="1001445" class="mini-textbox" style="width:70px;"/>
						    	主键结果集：<input id="resultkeys" name="resultkeys" value="resultkeys" class="mini-textbox" style="width:90px;"/>
						    	主键结果：<input id="resultkey" name="resultkey" value="resultkey" class="mini-textbox" style="width:90px;"/>
						    	CLOB方案ID：<input id="clobsolutionid" name="clobsolutionid" value="1001446" class="mini-textbox" style="width:70px;"/>
						    	保存方案：<input id="savesolutionid" name="savesolutionid" value="1001531" class="mini-textbox" style="width:70px;"/>
						    	<a class="mini-button" iconCls="icon-ok" onclick="editClobJson()" plain="true">执行</a>
						    	<a class="mini-button" id="" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
						    </td>
					  	</tr>
					  	<tr>
							<td style="width:100%;">
						    	节点类型：<input id="nodetype" name="nodetype" value="0" class="mini-textbox" style="width:50px;"/>
						    	页        码：<input id="pagenum" name="pagenum" value="1" class="mini-textbox" style="width:50px;"/>
						    	坐  标  上：<input id="top" name="top" value="500" class="mini-textbox" style="width:80px;"/>
						    	坐  标  左：<input id="left" name="left" value="500" class="mini-textbox" style="width:80px;"/>
						    	高        度：<input id="height" name="height" value="5000" class="mini-textbox" style="width:80px;"/>
						    	宽        度：<input id="width" name="width" value="5000" class="mini-textbox" style="width:80px;"/>
						    	边框类型：<input id="bordertype" name="bordertype" value="0" class="mini-combobox" style="width:80px;" data="BorderType"/>
						    </td>
					  	</tr>
				  		<tr>
							<td style="width:100%;">
								字体主题：<input id="facename" name="facename" value="" class="mini-combobox" style="width:145px;" data="FaceName"/>
								字体大小：<input id="fontsize" name="fontsize" value="12.0" class="mini-combobox" style="width:75px;" data="FontSize"/>
								字体颜色：<input id="frontcolor" name="frontcolor" value="0" class="mini-combobox" style="width:75px;" data="FrontColor"/>
								是否粗体：<input id="fontbold" name="fontbold" value="0" class="mini-combobox" style="width:60px;" data="Comboboxdata"/>
								是否斜体：<input id="fontitalic" name="fontitalic" value="0" class="mini-combobox" style="width:60px;" data="Comboboxdata"/>
								是否缩放：<input id="zoomtext" name="zoomtext" value="1" class="mini-combobox" style="width:78px;" data="Comboboxdata"/>
						    </td>
					  	</tr>
					  	<tr>
							<td style="width:100%;">
								节点名称：<input id="nodename" name="nodename" value="firsttrialsum" class="mini-textbox" style="width:145px;"/>
								意见来源：<input id="nodefrom" name="nodefrom" value="" class="mini-textbox" style="width:600px;"/>
						    </td>
					  	</tr>
					  	<tr>
							<td style="width:100%;">
						    	意见内容：<input id="nodevalue" name="nodevalue" value="测试测试测试" class="mini-textarea" style="width:810px;height:60px;"/>
						    </td>
					  	</tr>
				  	</table>
		  		</div>
  			</div>
			<div showCollapseButton="false" >
				<div id="showContent" style="height: 100%; overflow: auto; display: none; background: #ababab; color: #000000; text-align: left;"></div>
				<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
				<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
				<script LANGUAGE="javascript" src="<%=path%>/scripts/unlocksign.js"></script>
				<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
			</div>
		</div>
		<script type="text/javascript">
			document.all("MyViewer").Init(window, document,600);
		</script>
		<script type="text/javascript">
			var Comboboxdata = [{id:'1', text:'是'},{id:'0', text:'否'}];
			var BorderType = [{id:'0', text:'无边框'},{id:'1', text:'3D'},{id:'2', text:'边框'},{id:'3', text:'下划线'}];
			var FontSize = [{id:'42.0', text:'初号'},{id:'36.0', text:'小初'},{id:'26.5', text:'一号'},{id:'24.0', text:'小一'},
							{id:'22.0', text:'二号'},{id:'18.0', text:'小二'},{id:'16.0', text:'三号'},{id:'15.0', text:'小三'},
							{id:'14.5', text:'四号'},{id:'12.0', text:'小四'},{id:'10.5', text:'五号'},{id:'9.0',  text:'小五'},
							{id:'7.5',  text:'六号'},{id:'7.0',  text:'小六'},{id:'6.5',  text:'七号'},{id:'5.5',  text:'八号'},
							{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'},{id:'11',text:'11'},{id:'12',text:'12'},
							{id:'14',text:'14'},{id:'16',text:'16'},{id:'18',text:'18'},{id:'20',text:'20'},{id:'22',text:'22'},
							{id:'24',text:'24'},{id:'26',text:'26'},{id:'28',text:'28'},{id:'36',text:'36'},{id:'48',text:'48'},{id:'72',text:'72'}];
			var FrontColor = [{id:'0',text:'黑色'},{id:'255',text:'红色'},{id:'65535',text:'黄色'},{id:'16711680',text:'蓝色'},{id:'16777215',text:'白色'}];				
			var FaceName = [{id:'fs',text:'仿宋'},{id:'ht',text:'黑体'},{id:'kt',text:'楷体'},{id:'st',text:'宋体'},{id:'xst',text:'新宋体'},{id:'wryh',text:'微软雅黑'}];				
			mini.parse();
			var form = new mini.Form("form1");	
					
			function editClobJson(){
				var currScheme = "";
				var data = new Object();
				var resultkeys = mini.get("resultkeys").getValue();//主键结果集
				var keysolutionid = mini.get("keysolutionid").getValue();//主键方案ID
				
				
				var nodetype = mini.get("nodetype").getValue();
	            var pagenum = mini.get("pagenum").getValue();
	            var top = mini.get("top").getValue();
	            var left = mini.get("left").getValue();
	            var height = mini.get("height").getValue();               
				var width = mini.get("width").getValue();  		    
				var bordertype = mini.get("bordertype").getValue();
				
	            if(nodetype) currScheme = currScheme+"节点类型:"+nodetype+",";
	            if(pagenum)  currScheme = currScheme+"页码:"+pagenum+",";
	            if(top) currScheme = currScheme+"上:"+top+",";
	            if(left) currScheme = currScheme+"左:"+left+",";
	            if(height) currScheme = currScheme+"高:"+height+",";
	            if(width) currScheme = currScheme+"宽:"+width+",";
	            if(bordertype) currScheme = currScheme+"边框类型:"+bordertype+",";
	            
	            
	            var facename = mini.get("facename").getText();
	            var fontsize = mini.get("fontsize").getValue();
	            var frontcolor = mini.get("frontcolor").getValue();
	            var fontbold = mini.get("fontbold").getValue();
	            var fontitalic = mini.get("fontitalic").getValue();
	            var zoomtext = mini.get("zoomtext").getValue();
	            
	            if(facename) currScheme = currScheme+"字体:"+facename+",";
	            if(fontsize) currScheme = currScheme+"字体大小:"+fontsize+",";
	            if(frontcolor) currScheme = currScheme+"字体颜色:"+frontcolor+",";
	            if(fontbold) currScheme = currScheme+"是否粗体:"+fontbold+",";
	            if(fontitalic) currScheme = currScheme+"是否斜体:"+fontitalic+",";
	            if(zoomtext) currScheme = currScheme+"是否缩放:"+zoomtext+",";
	            
	            
	            var nodename = mini.get("nodename").getValue();
				var nodefrom = mini.get("nodefrom").getValue();
				var nodevalue = mini.get("nodevalue").getValue();				
	            
	            if(nodename) currScheme = currScheme+"节点名称:"+nodename+",";
	            if(nodefrom) currScheme = currScheme+"意见来源:"+nodefrom+",";
	            if(nodevalue) currScheme = currScheme+"意见内容:"+nodevalue+",";
				currScheme = currScheme.substring(0, currScheme.length-1);
				
	            data["solutionid"] = keysolutionid;
	            form.validate();
	            if (form.isValid() == false) return;
	            var json = mini.encode([data]);
		        var	url = '<%=path%>/form/editClobJson.json?1=1';
		        //根据主键方案ID查询查询主键结果集
				$.ajax({   
			         url:url+"&type=list", 
			         data: {data:json},
			         type: "post",
			         cache:false,
			         async:false,
			         success: function (text){
			         	 var keyobj = mini.decode(text)[resultkeys];
			         	 //将主键结果用递归的取出大字段并保存
			         	 var index = -1;	
			         	 doSealData(keyobj,currScheme,index);
			         	 alert("操作成功!");
			         	 close();
			         }
				});
			}
			
			//获取签章数据  keyobj[i][resultkey]
		    function doSealData(keyobj,currScheme,index){
			    if(index == -99){
  					resultkey = null;
  					return null;
  				}
		        index ++;
		        if(index >= keyobj.length){
		        	index = -99;
		        	return null;
		        }else{
		        	getClobData(keyobj,currScheme,index);
		        }
			}
			
			function getClobData(keyobj,currScheme,index){
				var data = new Object();
				var nodetype = mini.get("nodetype").getValue();
				var nodename = mini.get("nodename").getValue();
				var nodevalue = mini.get("nodevalue").getValue();
				var resultkey = mini.get("resultkey").getValue();//主键结果
				var clobsolutionid = mini.get("clobsolutionid").getValue();//大字段方案ID
				var savesolutionid = mini.get("savesolutionid").getValue();//保存方案ID
         	 	data["solutionid"] = clobsolutionid;
         	 	data[resultkey] = keyobj[index][resultkey];
         	 	var json = mini.encode([data]);
         	 	//根据CLOB方案ID和主键结果查询查询大字段
				var	url = '<%=path%>/form/editClobJson.json?1=1';
				$.ajax({
			         url:url+"&type=root", 
			         data: {data:json},
			         type: "post",
			         cache:false,
			         async:false,
			         success: function (text){
			         	 var aipFileStr = mini.decode(text)["form"]["doc_content"];
			         	 if(aipFileStr){
			         	 	 var aipObj=document.getElementById("HWPostil1");
				         	 aipObj.LoadFileBase64(aipFileStr);
				         	 if(aipObj.IsOpened()){
				         	 	var sealStatus = false;
							 	aipObj.ForceSignType=7;
								aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
								if(nodetype==0){
									sealStatus = InsertNote(currScheme,aipObj,'','','',nodevalue);
								}else if(nodetype==2){
									sealStatus = InsertNote(currScheme,aipObj,'', date, nodevalue,'');
								}
								if(sealStatus == false){
									aipObj.CloseDoc(0);
									dealSingleFailSealMessage(caseno, map);
									doSealData(keyobj,currScheme,index);
								}else{
									aipFileStr = aipObj.GetCurrFileBase64();
									data["solutionid"] = savesolutionid;
			         	 	 		data[resultkey] = keyobj[index][resultkey];
			         	 	 		data["doc_content"] = aipFileStr;
			         	 	 		json = mini.encode([data]);
			         	 	 		$.ajax({   
								         url:url+"&type=save", 
								         data: {data:json},
								         type: "post",
								         cache:false,
								         async:false,
								         success: function (text){
								         	 aipObj.CloseDoc(0);
								         	 doSealData(keyobj,currScheme,index);
								         }
									});
								}
							 }
			         	 }else{
			         	 	doSealData(keyobj,currScheme,index);
			         	 }
					 }
				});
			}
			function close(){
				if (window.CloseOwnerWindow) return window.CloseOwnerWindow("cancel");
			}
		</script>
	</body>
</html>