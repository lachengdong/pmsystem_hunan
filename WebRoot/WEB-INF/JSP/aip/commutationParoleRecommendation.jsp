<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>减刑假释建议书生成模板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
    </style>
</head>
<body >
    <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
	    <div size="1px" showCollapseButton="false">
	        <input id="crimid" name="crimid" class="mini-hidden" value="${crimid}"/>
	         <div class="mini-toolbar" style="border:0;padding:0px;">
			      <table style="width:100%;">
			          <tr>
	                	 <td style="width:700px;">
	                		   模版：<input id="tempid" name="tempid" property="editor" class="mini-combobox" style="width:180px;"
	                		       onvaluechanged="tempidchanged" required="true" data="Tempids" /> 
	                	 </td>
	                     <td style="white-space:nowrap;">
	                         <a class="mini-button" iconCls="icon-save"  plain="true" onclick="savedata()">保存文书</a>
	                         <span class="separator"></span>
	                         <a class="mini-button" iconCls="icon-new"  plain="true" onclick="preview()">预览</a>
	                     </td>
			         </tr>
			     </table>           
		     </div>
	    </div>
	    <div showCollapseButton="false" id="editForm1" >
	       	<input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
	       	<input id="tmvorsionid" name="tmvorsionid" class="mini-hidden" />
	 	</div>
	</div>
    <script type="text/javascript">
    	var Tempids = [{id:9692,text:'减刑建议书'},{id:9719,text:'假释建议书'},{id:10020,text:'有期提请减刑建议书'},{id:10021,text:'有期提请假释建议书'},{id:10022,text:'死缓提请减刑建议书'}];
    	//var Tempids = [{ id: 10020, text: '有期提请减刑建议书'},{ id: 10021, text: '有期提请假释建议书'},{ id: 10022, text: '死缓提请减刑建议书' }];//天津
        mini.parse();
       	var form = new mini.Form("editForm1");
		var menuid = document.getElementById("menuid").value;
		var reporttype = document.getElementById("reporttype").value;
        
        //保存或更新
        function savedata() {
           	var data = form.getData();
           	if(!data.annexcontent){
           		return;
           	}
      		var criminalid = mini.get("criminalid").getValue();
            var json = mini.encode([data]);
            form.loading("保存中，请稍后......");
            $.ajax({
                //url: "saveJYSDoc.action?1=1&type=XFZX_JXJS_JYS&id="+criminalid,
                url: url,
                data: { data: json,menuid:menuid},
                type: "post",
                success: function (text) {
                  	form.unmask();
                	if(text == "success"){
                		alert("保存成功!");
                	}else{
                		alert("保存失败！");
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
        }
		//预览
		function preview(){
			var reportid = mini.get("reportid").getValue();
			var qtype = document.getElementById("qtype").value;//类型
			var qtype2 = document.getElementById("qtype2").value;
			var imprisontype = mini.get("imprisontype").getValue();
			if('1'==imprisontype){//有期的
				//reportid = mini.get("reportid2").getValue();
			}
			var url = "";
			var title = "";
        	if(qtype == '7'||qtype == '12'||qtype == '14'){//减刑假释_保外就医_监督意见
        		url = "toJdSuggestDocumentPage.action?1=1&qtype=write&reportid="+reportid+"&jdtype="+qtype;
        		title="意见书";
        	}else if(qtype == '15'){//开庭笔录
        		var criminalid = mini.get("criminalid").getValue();
        		url = "toReportPage.action?qtype=aip&rid=report@12377&menuid="+menuid+"&id="+criminalid+"&param1="+criminalid;   	
        	}else if(qtype == '33'){//合议笔录
        		var criminalid = mini.get("criminalid").getValue();
        		url = "toReportPage.action?qtype=aip&rid=report@12707&menuid="+menuid+"&id="+criminalid+"&param1="+criminalid;   	
        	}else if(qtype == '34'){//裁定书
        		var criminalid = mini.get("criminalid").getValue();
        		url = "toReportPage.action?qtype=aip&rid=report@12710&menuid="+menuid+"&id="+criminalid+"&param1="+criminalid;   	
        	}else{//减刑假释建议书
            	var jysreportid = mini.get("criminalid").getValue();
        		url = "toSuggestDocumentPage.action?1=1&qtype＝"+qtype+"&reportid="+reportid+"&menuid="+menuid+"&jysreportid="+jysreportid;
        		title = "建议书";
        	}
        	//alert(url);
			var id = mini.get("criminalid").getValue();
			 mini.open({
                    url: url,
                    title: title, width: 900, height: 550,
                    showMaxButton: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {id : id};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                    }
              });
		}
		//建议书下拉选择生成文本
		function tempidchanged(e) {
        	var tempid = mini.get("tempid").getValue();
			var crimid = mini.get("crimid").getValue();
               $.ajax({
                 url: "getRecommendationContent.action?1=1&tempid="+tempid+"&crimid="+crimid,
                 type: "post",
                 success: function (text) {
                     var o = mini.decode(text);
                     form.setData(o);
                 }
             });
		}     
    </script>
</body>
</html>