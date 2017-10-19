<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>监外批量归档</title>
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
                    <a class="mini-button" id="14128" iconCls="" plain="true"  onclick="allMakeCover('14128');">制作封皮</a>
                	<a class="mini-button" id="14129" iconCls="icon-save" plain="true"  onclick="allcaozuoguidang('14129');">案件归档</a>
                	<!-- 陕西用 -->
                	<!-- <a class="mini-button" id="" iconCls="icon-tip" plain="true"  onclick="caseDataExchange();">报送省局</a> -->
                </td>
                <td style="white-space:nowrap;">
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('14127')"></a>	
                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid"  class="mini-datagrid"  style="width:100%;height:100%;"   url="<%=path %>/case/ajaxguidangcrimebaseinfo.json?1=1&modetype=jw"  idField="" multiSelect="true" allowResize="false" allowCellEdit="true"  showFilterRow="true" allowCellSelect="true" sizeList="[10,20,50,100]" pageSize="20" >
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
           			<input id="nameFilter" property="filter" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音"  onvaluechanged="onNameFilterChanged" style="width:100%;" />
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
        var state = record.state;
        var s ="";
        //state  案件未办理-1封皮已制作0
        if(state == 0){
              s = '<a class="New_Button" href="javascript:allcaozuoguidang(\'14132\');">案件归档</a>';
              e.rowStyle = "background:#ccffff;";
        }else{
             s='<a class="New_Button" href="javascript:allMakeCover(\'14131\');">制封皮</a>';
        }
        return s;
    }
	function onKeyEnter(e) {
	    search();
	}
    //批量制作封皮
     function allMakeCover(resid) {
     	var name = "";
     	var grid= mini.get("datagrid");
        var rows = grid.getSelecteds();
        var url = "<%=path%>/case/batchmakecover.action?1=1&modetype=jw";
         if (rows.length > 0) {
             if (confirm("确定批量操作选中记录？")) {
                 var ids = [];
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     if(r.state == 0){
                    	 name = name+r.cname+"、";
                         continue;
                     }
                     $.ajax({
                         url: url,
                         cache:false,
        	             async:false,
                         data:{tempid:'TJSJYGLJJW',personid:r.crimid},
                         type: 'POST',
                         success: function (text) {
                        	var o = mini.decode(text);
                        	var formcontent = "'"+o.formcontent+"'";
                        	var formDatajson = o.formDatajson;
                        	HWPostil1_modelReady(formcontent,1);
                        	HWPostil1_display(formDatajson,'');
                        	var type = 1;
                        	var MDCODE = saveFile();
                        	if(rows.length == 1) type = 0;
                        	ajaxmakecover(resid,type,r.crimid,r.cname,r.flowdraftid,r.state,MDCODE);
                         },
                         error: function () {
                         	alert("操作失败!");
                         }
                     });
                 }
                 if(name){
                	 name = name.substring(0, name.length-1);
                	 alert("罪犯"+name+"封皮已制作,不能重新制作!");                 
                 }
             }
         } else {
             alert("请选中一条记录");
         }
     }
     function ajaxmakecover(resid,type,personid,personname,flowdraftid,state,docconent){
		var url = "<%=path %>/case/ajaxmakecover.action?1=1&modetype=jw";
		var contempid = "JWJNML,ZFBWJYSPB,ZFBCJDBFY,ZFBWJYZHBG,ZFBWJYPSBFP,JBRZGSCB,ZFBWJY,ZFRJDJB,PJSCDS,ZFCJJDB,JWQT";
   	 	$.ajax({
            url: url,
            data:{resid:resid,personid:personid,personname:personname,tempid:'TJSJYGLJJW',
			 flowdraftid:flowdraftid,docconent:docconent,contempid:contempid},
            type: 'POST',
            cache:false,
         	async:false,
            success: function (text) {
            	datagrid.reload();
            },
            error: function () {
            	alert("操作失败!");
            }
        });
     }    
     
     function allcaozuoguidang(resid) {
     	var grid= mini.get("datagrid");
        var rows = grid.getSelecteds();
        var archtempid = "ZFBCJDB,JBRZGSCBNR,SX_SJBWJYSPB,ZFABWJYSPB,9706report";//归档表单模板，根据需要自己改
        var fengpi = "";
        var jianyishu = "";
        if (rows.length > 0) {
             if (confirm("确定批量操作选中记录？")) {
                 var ids = [];
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     if(r.state != 0){
                    	 fengpi = fengpi+r.cname+"、";
                         continue;
                     }
                     var url = "<%=path%>/case/ajaxmakearchive.action?1=1&modetype=jw";
			       	 $.ajax({
			                url: url,
			                type: 'POST',
			                cache:false,
			             	async:false,
			                data:{resid:resid,personid:r.crimid,personname:r.cname,flowdraftid:r.flowdraftid,archtempid:archtempid},
			                success: function (text) {
			                    if(text == '-1'){
			                    	jianyishu = jianyishu+r.cname+"、";
			                    }
			                    grid.reload();
			                },error: function () {
			                	alert("操作失败!");
			                }
			            });
                 }
             }
             if(fengpi){
            	 fengpi = fengpi.substring(0, fengpi.length-1);
            	 alert("罪犯"+fengpi+"封皮未制作,请先制作封皮!");                 
             }else if(jianyishu){
            	 jianyishu = jianyishu.substring(0, jianyishu.length-1);
            	 alert("罪犯"+jianyishu+"建议书未处理,请先处理建议书!");                 
             }
         } else {
             alert("请选中一条记录!");
         }
     }
     
     function caseDataExchange(){
		if(confirm("案件归档后执行此操作，确定归档了吗？")){
			var url = '<%=path%>'+'/caseDataExchange_sx.json?1=1';
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

