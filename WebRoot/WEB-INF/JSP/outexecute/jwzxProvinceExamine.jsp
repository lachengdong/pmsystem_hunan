<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>处室审查</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
	</style>
</head>

<body >
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    <input id="tempid" name="tempid" class="mini-hidden" value="SJ_BWJY_ZYJWZXSPB"/>
         <table >
            <tr>
                <td style="width:100%;">
        			<a class="mini-button" iconCls="" plain="true" onclick="allqianzhang('ajaxAllQianZhang.action?signatureid=1&amp;tijiaotype=bwxb','900','550','10014','1')">批量盖章</a>
        			<a class="mini-button" iconCls="icon-undo" plain="true" onclick="allcaozuo('ajaxAllCaoZuo.action?tijiaotype=bwxb&amp;operate=BACK','900','550','10014','1')">批量退案</a>
        			<a class="mini-button" iconCls="icon-upgrade" plain="true" onclick="allcaozuo('ajaxAllCaoZuo.action?tijiaotype=bwxb&amp;operate=NO','900','550','10014','1')">不予办理</a>
        			<a class="mini-button" iconCls="icon-downgrade" plain="true" onclick="allcaozuo('ajaxAllCaoZuo.action?tijiaotype=bwxb&amp;operate=YES','900','550','10014','1')">批量提交</a>
        			<a class="mini-button" iconCls="icon-collapse" plain="true" onclick="add('toMSummaryPage.action?1=1&amp;rid=report@10404&amp;qtype=xb&amp;infotype=xbks','900','550','10014','1')">会议记录</a>
           			<!-- <a class="mini-button" plain="true" iconCls="icon-user" onclick="chakanshuoming('10014')">操作说明</a> -->
            	</td>
             	<td style="white-space:nowrap;">
             		<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
                	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="罪犯编号"  onenter="onKeyEnter"/>
                	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
					<!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10014')"></a>
             	</td>
            </tr>
		</table> 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	 url="jwzxProvinceExamineList.json?1=1" idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
        		 <div type="checkcolumn" width="10"></div> 
          		 <div field="opcnumber" width="40px" headerAlign="center" allowSort="true" align="center" >案件号</div>  
       			 <div field="crimid" width="40px" headerAlign="center" allowSort="true" align="center" >罪犯编号</div> 
          		 <div field="name" width="40px" headerAlign="center" allowSort="true" align="center" >罪犯姓名</div>  
           		 <div field="instanceid" visible="false"  headerAlign="center" allowSort="true" align="center" >流程实例ID</div>  
		         <div field="opcillinstance" width="40px" headerAlign="center" allowSort="true" align="center" >病残情况</div>  
		         <div field="opcillcheckresult" width="40px" headerAlign="center" allowSort="true" align="center" >病残鉴定结果</div>  
           		 <div field="instancestate" visible="false" headerAlign="center" allowSort="true" align="center" >流程状态</div>  
		         <div field="chujianzhuzhi" width="40px" headerAlign="center" allowSort="true" align="center" >出监住址（家庭住址）</div>  
               	 <div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
           </div>
	    </div>
    </div>
    <script type="text/javascript">
 		mini.parse(); 
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("crimid", "desc");
        
        function onActionRenderer(e) {
	        var s ="";
		    s=s+'<a class="Edit_Button" href="javascript:banan()">办案</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:jianyishu()">建议书</a>&nbsp;&nbsp;'
	        return s;
	    }
	    function banan(){
	    	var tempid = mini.get("tempid").getValue();
	    	var row = grid.getSelected();
            if (row) {
                var win = mini.open({
                    url: "tojwzxProvinceExamine.action?1=1&crimid="+row.crimid+"&tempid="+tempid,
                    showMaxButton: true,
             	    allowResize: false, 
                    title: "处室经办", width: 900, height: 500,
                    success: function (text) {
	                	alert("操作成功!");
	                  	back();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
            } 
	    }
	    function jianyishu(){
	    	var row = grid.getSelected();
	    	if(row){
	    		var win = mini.open({
	    			url: "tojwzxProvinceExaminejianyishu.action?1=1&crimid="+row.crimid,
	    			showMaxButton: true,
	    			allowResize: false,
	    			title: "建议书",width:900,height:500,
	    			onload: function () {
                       var iframe = this.getIFrameEl();
                       var data = { action: "jianyishu"};
                       iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (text) {
                	   grid.reload();
                    }
               });
	    	}
	    }
	</script>
</body>
</html>