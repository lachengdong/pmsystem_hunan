<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>省局批量归档</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body >
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
                <td style="width:100%;">
                	<a class="mini-button" id="16007" iconCls="icon-save" plain="true"  onclick="allcaozuoguidang('16104');">案件归档</a>
                	<!-- 陕西用 -->
                	<!-- <a class="mini-button" iconCls="icon-tip" plain="true"  onclick="caseDataExchange();">回复监狱</a> -->
                </td>
                <td style="white-space:nowrap;">
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('16102')"></a>	
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid"  class="mini-datagrid"  style="width:100%;height:100%;"   url="<%=path %>/case/ajaxguidangcrimebasesjinfo.json?1=1&modetype=jw"  idField="" multiSelect="true" allowResize="false" allowCellEdit="true"  showFilterRow="true" allowCellSelect="true" sizeList="[10,20,50,100]" pageSize="20" >
	        <div property="columns">
        		<div type="checkcolumn" width="10"></div> 
    			<div field="anjuanhao" width="20"  headerAlign="center"  	allowSort="true" align="center" >
           			案卷号
           		</div> 
    			<div field="crimid" width="20"  headerAlign="center"  	allowSort="true" align="center" >
           			罪犯编号
           		</div> 
       			<div field="cname" width="20"  headerAlign="center"  	allowSort="true" align="center" >
           			罪犯姓名
           			<input id="nameFilter" property="filter" class="mini-textbox" emptyText="罪犯编号、姓名、拼音首字母"  onvaluechanged="onNameFilterChanged" style="width:100%;" />
           		</div>  
       			<div field="jqu" width="20"  headerAlign="center"  	allowSort="true" align="center" >
           			所在监区
           		</div> 
       			<div field="jiany" width="20"  headerAlign="center"  	allowSort="true" align="center" >
           			监狱
           		</div>  
       			<div field="optime" width="20"  headerAlign="center"  dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
           			归档时间
           		</div>  
            	<div name="action" width="20" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    <div style="display: none;">
        <jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
    </div>
    
<script type="text/javascript"> 
	mini.parse();
	var datagrid = mini.get("datagrid");
	datagrid.load();
	mini.get("datagrid").sortBy("crimid", "desc");
	
    function plqz_loading(id) {
    	//alert(id);
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '正在对【'+id+'】的案件进行盖章，请稍候......'
        });
        setTimeout(function () {
        	id = "";
            mini.unmask(document.body);            
        }, 250);
    }
    function onNameFilterChanged(e) {
        var textbox = e.sender;
        var key = textbox.getValue();
        datagrid.load({key: key });
    }

    function search(){
    	 var key = mini.get("key").getValue();
    	 datagrid.load({ key: key });
    }
    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;
        var s ="";
        if(${provincecode}=='6100'){
	        s+='<a class="New_Button" href="javascript:chooseOne();">处理</a>&nbsp;&nbsp;&nbsp;&nbsp;';
        }
   		s += '<a class="New_Button" href="javascript:allcaozuoguidang(\'16103\');">案件归档</a>';
   		
        e.rowStyle = "background:#ccffff;";
        return s;
    }
	function onKeyEnter(e) {
	    search();
	}

    function allcaozuoguidang(resid) {
     	var grid= mini.get("datagrid");
        var rows = grid.getSelecteds();
        var archtempid = "";
        if (rows.length > 0) {
             if (confirm("确定批量操作选中记录？")) {
                 var ids = [];
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     var url = "<%=path%>/case/ajaxmakearchivesj.action?1=1&modetype=jw";
			       	 $.ajax({
			                url: url,
			                type: 'POST',
			                cache:false,
			             	async:false,
			                data:{resid:resid,personid:r.crimid,personname:r.cname,flowdraftid:r.flowdraftid,archtempid:archtempid},
			                success: function (text) {
			                    if(text == '-1'){
			                    	alert("罪犯"+r.cname+"建议书未处理,请先处理建议书!");  
			                    }
			                    grid.reload();
			                },error: function () {
			                	alert("操作失败!");
			                }
			            });
                 }
             }
         } else {
             alert("请选中一条记录!");
         }
     }   
    
    function chooseOne(){
           var row = datagrid.getSelected();
            if (row) {
            	var tempid ="";
            	var url="toSJcasepage.action?1=1&crimid="+row.crimid+"&flowid="+row.flowid+"&flowdraftid="+row.flowdraftid+"&flowdefid="
            		+row.flowdefid+"&tempid="+tempid+"&jdstempid=10428&menuid=1600700";
                mini.open({
                    url: url,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "", width: 900, height: 550,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
    }
    
    function caseDataExchange(){
				if(confirm("案件归档后执行此操作，确定归档了吗？")){
					var url = '<%=path%>'+'/caseDataExchange_sxsj.json?1=1';
		    		$.ajax({
		                url: url,
		                type: "post",
		                success: function (text){
		                	text = mini.decode(text);
			        	    if(text=='1'){
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
}
</script>
</body>
</html>

