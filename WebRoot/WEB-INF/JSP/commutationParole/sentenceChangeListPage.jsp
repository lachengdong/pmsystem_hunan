<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String closetype = request.getAttribute("closetype")==null ? "" : (String)request.getAttribute("closetype");
%>
<html>
	<head>
	<title>刑期变动</title>
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
                <td style="width:100%;">
               	 	<a class="mini-button" style="display:none;" iconCls="" plain="true"  id="900350" onclick="batchReview('900350');">批量重审</a>
               	 	<a class="mini-button" style="display:none;" iconCls="icon-add" plain="true"  id="12073" onclick="add('12073');">新增</a>
               	 	<a class="mini-button" style="display:black;" id="gb" iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
               	 	<span style="color:#ff3300;">当前罪犯：${crimid} ${name}</span>
                </td>
                <td style="white-space:nowrap;">
                 	 <a class="mini-button"   plain="true" onclick="search()"></a>
                </td>
              </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" 
	    	class="mini-datagrid"  allowResize="false" url="getSentenceChangeByCrimid.json?1=1&crimid=${crimid}&tempid=${tempid}"  
	    	idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="25"></div>
              	<div type="indexcolumn" width="25" headerAlign="center">序号</div>
	    		<div field="caseno" headerAlign="center"  align="center" allowSort="true" width="50px">案件号</div>
	    		<div field="category" headerAlign="center"  align="center" allowSort="true" renderer="onActionBianDongType" width="40px">变动类别</div>
	    		<div field="courtsanction" headerAlign="center"  align="center" allowSort="true" width="45px">判裁日期</div>
	    		<div field="exectime" headerAlign="center"  align="center" allowSort="true" width="45px">交付日期</div>
	   			<div field="pancaifayuan" headerAlign="center" align="center" allowSort="true" width="85px;">判裁法院</div>
	   			<div field="charge" headerAlign="center" align="center" allowSort="true" width="60px;">罪名</div>
	    		<div field="biandongfudu" headerAlign="center"  align="center" allowSort="true" width="60px">变动幅度</div>
	    		<div field="bianhouxingqi" headerAlign="center"  align="center" allowSort="true" width="40px">变后刑期</div>
	    		<div field="boquan" headerAlign="center"  align="center" allowSort="true" width="35px">剥政</div>
                <div field="courtchangeto" headerAlign="center"  align="center" allowSort="true" width="45px">变后止日</div>
                <div field="awardend" headerAlign="center"  align="center" allowSort="true" width="45px">考核止日</div>
	    		<div field="uuid" headerAlign="center"  align="center" allowSort="true" width="0px">变动幅度</div>
	    		<div field="isthrough" headerAlign="center"  color="red" align="center" allowSort="true" renderer="onActionExamineType" width="40px">审核状态</div>
	   			<div name="action" headerAlign="center" align="center"  width="60px" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
    </div>
   <script type="text/javascript">
   		mini.parse();
   		if('${toolbar}'=='0')$("#gb").hide();//经办人办案关闭按钮不显示
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
        function onActionBianDongType(e){
        	var record = e.record,
		    field = e.field,
		    value = e.value;
		    var s = "";
            if(value == 1){
                 s = "原判";
            }
            if(value == 7){
                s = "减刑";
            }
            if(value == 4){
                s = "加刑";
            }
            if(value == 6){
                s = "漏罪加刑";
            }
            if(value == 20){
                s = "刑期顺延";
            }
            return s;
        }
        function onActionExamineType(e){
        	var record = e.record,
		    field = e.field,
		    value = e.value;
		    var s = "";
            if(value == 0){
                 s = "未通过";
            }else{
            	e.cellStyle = "background:green;";
				s = "通过";
            }
            return s;
        }
        //批量重审
        function batchReview(){
        	var rows = datagrid.getSelecteds();
        	var crimid = '${crimid}';
			if(rows.length > 0){
           		var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    var isthrough = r.isthrough;
                    var courtsanction = r.courtsanction;
                    if(isthrough){
                         ids.push(courtsanction);
                    }
                }
                ids = ids.join(",");
                if(ids && ids.length >0){
            	   var url="<%=path%>/batchReview.json?1=1";
	                $.ajax({
	    		        url: url,
	    		        data: {ids:ids,crimid:crimid,type:0},
	    		        type: "post",
	    		        async: false,
	    		        success: function (text) {
	    		            var obj = text;
	    		            if(parseInt(obj)>0) alert("处理成功!");
	    		            else alert("操作失败!");
	    		            datagrid.reload();
	    		        }
	    	       });  
               }
			}
        }
        //新增
      function add(menuid){
      		var crimid = mini.get("crimid").getValue();
      		var tempid=mini.get("tempid").getValue();
      		var flag = getJxJsCase(crimid);
			if(!flag){alert("减刑案件已经开始办理、无法再新增!");return;}
	 		mini.open({
	 	        url: "sentenceChangeAdd.page?1=1&crimid=" + crimid+"&menuid="+menuid+"&tempid="+tempid+"&operator=new",
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
        
        
      function getJxJsCase(crimid){
			var nodeid = -1;
			$.ajax({
				url:'/pmsystem/flow/isDealJxjsCase.json',
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

        
        
        function xiugai(menuid){
        	var row = datagrid.getSelected();
      		var tempid=mini.get("tempid").getValue();
      		var uuid = row.uuid;
      		var url = "examineSentenceChange.page?1=1&crimid="+row.crimid+"&menuid="+menuid+"&tempid=XFZX_XQBD&operator=edit&courtsanction="+encodeURI(row.courtsanction)+"&uuid="+uuid;
	 		mini.open({
	 	        url: url,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "修改", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "edit" };
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	   });	
 	   }
 	   //查看
	   function chakan() {
			var row = datagrid.getSelected();//获取选中记录
			var crimid = row.crimid;
			var tempid=mini.get("tempid").getValue();
			var uuid = row.uuid;
			//var courtsanctionStr = mini.formatDate(new Date(row.courtsanction),'yyyy-MM-dd');
			var url = "examineSentenceChange.page?1=1&crimid="+crimid+"&menuid="+menuid+"&tempid=XFZX_XQBD&operator=edit&courtsanction="+encodeURI(row.courtsanction)+"&uuid="+uuid;
	    	mini.open({
                url: url,
                showMaxButton: true,
             	allowResize: false, 
                title: "刑期变动查看", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"show"};
                    iframe.contentWindow.SetData(data);
                },ondestroy: function (text){
                    datagrid.reload();
                }
            });
		}
		//删除
		function remove(menuid){
	    	var rows = datagrid.getSelecteds();
	    	if(rows.length > 0){
	    		if(confirm("确定操作选中的记录吗？")){
	    			var xfbduuidArr = [];
		            for (var i = 0, l = rows.length; i < l; i++){
		               var row = rows[i];
		               xfbduuidArr.push(''+row.uuid+''+';'+''+row.category+''+';'+''+row.crimid+''+';'+''+row.courtsanction+'');
		            }
					var xfbduuids = xfbduuidArr.join(',');
					 $.ajax({
	                   url: "removeSentenceChange.json?1=1&xfbduuids="+xfbduuids,
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
			   url = "sentenceChange.page?1=1&_criminalid="+_criminalid;
        	   self.location.href=url;
		   }
       }
	</script>  
</body>
</html>
