<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();
	Object topsearchObj = request.getAttribute("topsearch");
	
	String topsearchkey = "";
	Object topsearchkeyObj = request.getAttribute("topsearchkey");
	if(null != topsearchkeyObj){
		topsearchkey = topsearchkeyObj.toString();
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
    </style>
</head>
<body onload="onLoad('edit');">
	
	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
	<input id="applyid" name="applyid" class="mini-hidden" value="${applyid}"/>
	<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
	<input id="systempid" name="systempid" class="mini-hidden" value="${systempid}"/>
	<input id="solutionid" name="solutionid" class="mini-hidden" value="${solutionid}"/>
	<input id="noOnLoad" name="noOnLoad" class="mini-hidden" value="${noOnLoad}"/>
	<input id="temp1" name="temp1" class="mini-hidden" /><!-- 会议记录暂存罪犯编号 -->
	<input id="temp2" name="temp2" class="mini-hidden" />
	
    <div id="form1" class="mini-splitter" vertical="true" style="width:100%;height:100%;" >
	     <div size="6%" showCollapseButton="false">
		       <div class="mini-toolbar" style="border:0;padding:0px;height:100%;">
		            <table style="width:100%;">
		                <tr>
		                	<td style="width:80%;">
		                		${topsearch}
		                		<%
		                			if(StringNumberUtil.notEmpty(topsearchObj)){
		                		%>
		                		<span class="separator"></span>
		                		<%	
		                			}
		                		 %>
		                		${topstr}
		                	</td>
		                    <td style="white-space:nowrap;">
			                    <input id="color"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:70px;"
									url="<%=path%>/txt/stype.txt" emptyText="选择" value="green" onvaluechanged="onselectChanged();"/>
						     	<input id="font"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:55px;" 
									url="<%=path%>/txt/font.txt" emptyText="字号"  value="zhong"  onvaluechanged="onselectChanged();"/>
								<a class="mini-button" iconCls="icon-reload" onclick="refreshPage1()"	plain="true">刷新</a>
		                    </td>
		                </tr>
		            </table>           
			     </div>
	    </div>
	    <div showCollapseButton="false"  >
	       	<input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
	 	</div>
	</div>

    <script type="text/javascript">
    
    	
        mini.parse();
        var path = '<%=path%>';
       	var form = new mini.Form("form1");
        
        function operateMasterData(operateType,bizName){
        	saveSysTemplate('master','save',bizName);
        }
        
        function operateSlaveData(operateType,bizName){
        	saveSysTemplate('slave','save',bizName);
        }
        
        function operateData(operateType,bizName){
        	operateMasterData(operateType,bizName);
        }
        
        function onLoad(optype){
        	var noOnLoad = mini.get("noOnLoad").getValue();
        	if(noOnLoad && noOnLoad=='1'){//如果为弹出框,如会议记录的弹出框,不执行跳出
        		return;
        	}
        	var flowdraftid = mini.get("flowdraftid").getValue();
        	var applyid = mini.get("applyid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	
        	var data = {};
        	data['flowdraftid'] = flowdraftid;
        	data['applyid'] = applyid;
        	data['flowdefid'] = flowdefid;
        	data['solutionid'] = solutionid;
        	data['tempid'] = tempid;
        	data['optype'] = optype;
        	
        	SetData(data);
        }
        
        var map = {};
        function SetData(data){
        	onselectChanged();
        	data = mini.clone(data);
        	//
        	var noOnLoad = mini.get("noOnLoad").getValue();
        	if(noOnLoad && noOnLoad=='1'){
        		if(mini.get("applyid").getValue()){
        			data['applyid'] = mini.get("applyid").getValue();
        		}
        		if(mini.get("flowdefid").getValue()){
        			data['flowdefid'] = mini.get("flowdefid").getValue();
        		}
        		if(mini.get("solutionid").getValue()){
        			data['solutionid'] = mini.get("solutionid").getValue();
        		}
        		if(mini.get("tempid").getValue()){
        			data['tempid'] = mini.get("tempid").getValue();
        		}
        		if(data['temp1']){
        			mini.get('temp1').setValue(data['temp1']);
        		}
        		if(data['flowdraftids']){
        			mini.get('temp2').setValue(data['flowdraftids']);
        		}
        		if(data['flowdraftid']){
        			mini.get('flowdraftid').setValue(data['flowdraftid']);
        		}
        	}
			//
        	var url = "<%=path%>/getSysTemplateData.json?1=1";
        	map = data;
       		$.ajax({
                 url: url,
                 type: "post",
                 data : data,
                 async: false,
                 success: function (text){
                 	 if(text){
                 		 var o = mini.decode(text);
                    	 form.setData(o);
                 	 }
                 }
	         });
        }
        
        function refreshPage1(){
        	var url = "<%=path%>/getSysTemplateData.json?1=1";
        	$.ajax({
                url: url,
                type: "post",
                data : map,
                async: false,
                success: function (text){
                	 if(text){
                		 var o = mini.decode(text);
                   	 form.setData(o);
                	 }
                }
	         });
        }
        
        //
        //针对会议记录的单独处理,将草稿id置空
        function setdata(data){
        	//data = mini.clone(data);
        	mini.get('flowdraftid').setValue('');
        }
        
        
        var topsearchkey = '<%=topsearchkey%>';
        function search(){
        	//var topsearchkey = '${topsearchkey}';
        	var topsearchkeyArr = [];
        	if(topsearchkey){
        		topsearchkeyArr = topsearchkey.split(',');
        	}
        	
        	var url = window.location.href;
        	var start = url.indexOf('?');
        	var param = url.substring(start+1);
        	url = url.substring(0,start);
        	var paramArr = param.split('&');
        	var paramMap = {};
        	var keyValue = '';
        	for(var i=0,l=paramArr.length; i<l ; i++){
        		keyValue = paramArr[i].split('=');
        		if(keyValue.length==2){
        			var key = keyValue[0];
        			var value = keyValue[1];
        			paramMap[key] = value;
        		} 
        	}
        	for(var i=0,l=topsearchkeyArr.length; i<l ; i++){
        		key = topsearchkeyArr[i];
        		value = mini.get(key).getValue();
        		if(value){
        			paramMap[key] = encodeURI( value );
            	}
        	}
        	
        	var paramstr = '';
        	for(var skey in paramMap){
        		paramstr += '&'+skey+'='+ paramMap[skey];
        	}
        	//判断是否以&开头
        	var andCharIndex = paramstr.indexOf('&');
        	if(andCharIndex == 0){
        		paramstr = paramstr.substring(1);
        	}
        	url += '?' + paramstr;
            window.location.href = url;
        };
        
        
		//预览
		function preview(menuid){
        	var flowdraftid = mini.get("flowdraftid").getValue();
        	var applyid = mini.get("applyid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var applyids = mini.get("temp1").getValue(); //针对会议记录，对多个罪犯的处理
        	//检查是否存了模板，没存模板无法预览
       		$.ajax({
                 url: "checkSingleSuggestDocumentData.action?1=1",
                 type: "post",
                 async: false,
                 data:{flowdraftid:flowdraftid, tempid:tempid},
                 success: function (text) {
                 	returnValue = eval(text);
                 }
	         });
	         if(returnValue =='-1'){
	         	 alert("请先保存文书！");
	         	 return;
	         }else if(returnValue != '1'){
	         	 alert("操作失败！");
	         	 return;
	         }
			 //menuid为预览按钮传的菜单id，该menuid与报表关联，同时该menuid对应的资源存了tempid和solutionid
			 var url = "<%=path%>/toPreviewReport.page?1=1&menuid="+menuid+"&flowdraftid="+flowdraftid+"&applyid="+applyid+"&flowdefid="+flowdefid+"&tempid="+tempid;
			 mini.open({
                   url: url,
                   width: 900, 
                   height: 600,
                   showMaxButton: true,
                   onload: function () {
                       var iframe = this.getIFrameEl();
                       var data = {menuid:menuid, flowdraftid:flowdraftid, applyid:applyid, flowdefid:flowdefid, applyids:applyids };
                       iframe.contentWindow.SetData(data);
                   },
                   ondestroy: function (action){
                   		
                   }
              });
		}
		
		
		function editSysTemplate(){
       		var tempid = mini.get("suggestmodel").getValue();
       		if(!tempid){
       			alert("请先选择模板！");
       			return ;
       		}
       		var win = mini.open({
                    url: "<%=path%>/toMeetingModel.action?1=1",
                    title: "模板编辑", width: 800, height: 500,
                    showMaxButton: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {action: "edit",tempid:tempid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                    }
             });
             win.max();
       	}
       	
       	
		//案件字号改变时自动查询,刑期改变时自动查询
		function onselectChanged(){
        	var color = mini.get("color").getValue();
        	var font = mini.get("font").getValue();
        	if(!font){
        		font = 'zhong';
        	}
        	document.getElementById("annexcontent").className="mini-textarea";
        	mini.get("annexcontent").setCls(color);
        	mini.get("annexcontent").setCls(font);
        }
        
        function reloadSuggestTemplate(){
			var suggesttemplateid = mini.get('suggesttemplateid').getValue();
			if(suggesttemplateid){
				mini.get('systempid').setValue(suggesttemplateid);
				
				var flowdraftid = mini.get("flowdraftid").getValue();
		        var applyid = mini.get("applyid").getValue();
		        var flowdefid = mini.get("flowdefid").getValue();
		        var solutionid = mini.get("solutionid").getValue();
		        var tempid = mini.get("tempid").getValue();
		        	
		        var data = {};
		    	data['flowdraftid'] = flowdraftid;
		    	data['applyid'] = applyid;
		    	data['flowdefid'] = flowdefid;
		    	data['solutionid'] = solutionid;
		    	data['tempid'] = tempid;
		    	data['optype'] = 'add';
		    	data['systempid'] = suggesttemplateid;
		        	
				SetData(data);
				var systempid = mini.get('systempid').getValue();
                if(systempid){
                 	mini.get('suggesttemplateid').setValue(systempid);
                }
			}
		}

    </script>
    <script src="<%=path %>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html>