<%@ page language="java" language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page language="java" language="java" import="com.sinog2c.model.system.SystemUser" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>档案导入</title>
	<style type="text/css">
		html, body
		{
		    margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;font-size: 12px;
		}
		.progressbar
	    {
	        position:relative;background:#bbb;width:99%;height:16px;overflow:hidden;
	    }
	    .progressbar-percent
	    {
	        position:absolute;height:18px;background:blue;left:0;top:0px;overflow:hidden;
	        z-index:1;
	    }
	    .progressbar-label
	    {
	        position:absolute;left:0;top:0;width:100%;font-size:13px;color:White;
	        z-index:10;
	        text-align:center;
	        height:16px;line-height:16px;
	    }
	</style>
	<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
	<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
   	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   	<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
   	<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
   	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
</head>
<body  onload="init('1600_001_001_01');">
	<div class="mini-splitter"  vertical="true" style="width:100%;height:100%;border: 0px;">
		<div size="30px;" showCollapseButton="false" >
			<div class="mini-toolbar" style="padding: 2px;border-top:0; ">                
			   	<span style="vertical-align:middle;">
			   		<a class="mini-button" style="display:none;" id="10880" iconCls="icon-folderopen" plain="true" onclick="browseFolder();">文件选择</a>
			   		<a class="mini-button" style="display:none;" id="10881" iconCls="icon-addfolder" plain="true" onclick="piliangdaoru('10881');">批量导入</a>
			   		<a class="mini-button" iconCls="icon-node" plain="true" onclick="back();">查看明细</a>
			   		<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
			   </span>
			   <input id="provincecode" name="provincecode" class="mini-hidden" value="${provincecode}"/>
			   <input id="archiveseal" name="archiveseal" class="mini-hidden" value="${archiveseal}"/>
			   <input id="username" name="username" class="mini-hidden" value="${username}"/>
			   <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			</div>
		</div>
		<div showCollapseButton="false" style="border:0;">
			<div class="mini-splitter" style="width:100%;height:100%;border: 0;">
			    <div size="23%" showCollapseButton="true" >
			    <div id="describeid" style="margin-left: 20px;">
					此处为电子档案目录
				</div>
			    <div id="tree1" class="mini-tree" style="width:100%;padding:5px;height:100%;display: none;" showTreeIcon="true" expandOnLoad="true"
			        textField="text" idField="id" parentField="pid" resultAsTree="true"  showCheckBox="true" 
			        checkRecursive="true" onnodecheck="onNodeCheck" allowSelect="true" enableHotTrack="false">
			    </div>
			    </div>
			    <div showCollapseButton="false" style="border:0;"> 
			        <div class="mini-splitter" vertical="true" style="width:100%;height:100%;border: 0;">
					    <div  showCollapseButton="true" size="28%" style="width: 100%;">
					       	<div style="padding-left: 6px;">
					       		<div id = "textarea1" style="color:#cc3300">命名规则:..\[罪犯编号][罪犯姓名]\[档案大类别编号]_[档案小类别编号]_[文件命名]_[排序号].JPG
					     			<br/>例 如:..\4413012345_张三\012_001_2010@考核登记本_0003.JPG
					     			<br/>例 如:..\4413012345_张三\001_002_一审判决书_0001.JPG
					     			<br/>注：如果文件为多页的，请扫描成一份PDF文件，命名方式不变
					       		</div>
					       		<div id = "textarea"></div>
					       		<div id="progressbarid" style="text-align: center;"></div>
							</div>
					    </div>
					    <div showCollapseButton="false" style="border-bottom: 0;border-right: 0;">
					    	<!-- 提示信息 -->
					    	<div id="winflaginfo" style="height:100%;overflow-x:hidden;overflow-y:auto;margin-left: 6px;">
								<div id="divshowinfo" style="width: 100%;" ></div><!-- 正在导入文件列表 -->
								<div id="impsucflag" style="width: 100%;" ></div>
								<div id="imperrflag" style="width: 100%;" ></div>
								<div id="impsuccount" style="width: 100%;"></div>
					   		</div>
					    </div>        
					</div>
					<div style="height: 0px;border: 0px;">
						<table>
							<tr>
								<td height="100%">
								<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
								</td>
							</tr>
						</table>
					</div>
			    </div>        
			</div>
		</div>
	</div>
		
    <script type="text/javascript">
	mini.parse();
	var tree = mini.get("tree1");

 	function CloseWindow(action) {
       if (action == "close" && form.isChanged()) {
           if (confirm("数据被修改了，是否先保存？")) {
               return false;
           }
       }
       if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
       else window.close();      
    }
    function onCancel(e) {
        CloseWindow("cancel");
    }
    /////////
	function SetData(data){
		if (data.action == "new") {

		}
	}
	//显示目录树
    //--------------------------------
    function onNodeCheck(e) {
    	var node = e.node;
        var tree = e.sender;
        var filefolder = tree.getValue(false);
        //选中目录树后显示 文件
        getCheckedFolders(filefolder);
        if (tree.hasChildren(node)) {
            //e.cancel = true;
        }
    }
	function back(){
		var url = "<%=path%>/flow/archivescollection.action?1=1";
		self.location.href=url;
	}

	// 刷新本页面
	function refreshPage(){
		if(!window["____refreshPage"]){
			window["____refreshPage"] = true;
			//
			location.reload();
		} else {
			window.setTimeout(function(){
				window["____refreshPage"] = false;
				},1*1000);
		}
	}
</script> 
</body>
</html>