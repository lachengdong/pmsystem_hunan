<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>释放告知书</title>
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
			<input id="indexFlag" name="indexFlag" class="mini-hidden" value="${indexFlag}"/>
			<table>
				<tr>
					<td style="width:100%;">
				    	<jsp:include page="/WEB-INF/JSP/form/formButtonBefore.jsp"></jsp:include>
				    	${topsearch}
						${topstr}
						<a class="mini-button" iconCls="icon-close"  plain="true" onclick="onCancel();" >关闭</a>
				    	<jsp:include page="/WEB-INF/JSP/form/formButtonAfter.jsp"></jsp:include>
				    </td>
				    <td style="white-space:nowrap;">
				    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
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
        
        //手动下一个
      function saveNext(){
        	
    	  	var id = mini.get("id").getValue();
	       	var indexFlag = mini.get("indexFlag").getValue();
	       	var menuid = mini.get("menuid").getValue();
	       	var tempid = mini.get("tempid").getValue();
	       	var idArr = id.split(",");
	       	indexFlag = parseInt(indexFlag);
	       	indexFlag ++;
	       	
	       	if(indexFlag < idArr.length){
	       		var url="<%=path%>/releaseInform.action?1=1&id="+id+"&indexFlag="+indexFlag+"&menuid="+menuid+"&tempid="+tempid;
	            self.location.href=url;
	       	}else{
            	//alert("批量处理已完成！");
            	onCancel();
            }
        }
        
    //上一个
     function toLast(){
	       	var id = mini.get("id").getValue();
	       	var indexFlag = mini.get("indexFlag").getValue();
	       	var menuid = mini.get("menuid").getValue();
	       	var tempid = mini.get("tempid").getValue();
	       	var idArr = id.split(",");
	       	indexFlag = parseInt(indexFlag);
	       	indexFlag --;
	       	
	       	if(indexFlag >=0){
	       		var url="<%=path%>/releaseInform.action?1=1&id="+id+"&indexFlag="+indexFlag+"&menuid="+menuid+"&tempid="+tempid;
	            self.location.href=url;
	       	}else{
	       		//alert("操作完毕！");
	           	onCancel();
	       	}
         
       }
        
        //保存或更新
      function savedata(){
       		var content = saveFile();
       		var tempid = mini.get("tempid").getValue();
       		var docid = mini.get("docid").getValue();
       		var crimid = mini.get("crimid").getValue();
       		var url="savereleaseInform.action?1=1";
        	if(!docid){//新增
	            $.ajax({
	                url:encodeURI(encodeURI(url)),
	                data: {content:content,tempid:tempid,crimid:crimid},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功!");
	                  	saveNext();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
            }else{//更新
	            $.ajax({
	                url: encodeURI(encodeURI(url)),
	                data: {content:content,docid:docid},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功!");
	                  	saveNext();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
           }
        }
        //返回到罪犯处理页面
	   function onCancel(){  
	       var url = "releaseInformchoose.action?1=1";
	       self.location.href=url;          
	    }
    </script>
</body>
</html>
