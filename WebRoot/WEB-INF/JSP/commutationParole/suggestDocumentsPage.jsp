<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   		}    
    </style> 
</head>
<body onload="previewSuggestDoc('0');">
	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	<div>
	<input id="flag" name="flag" type="hidden" value="0"/>
	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	<input id="crimid" name="crimid" type="hidden" value="${crimid}"/>
	<input id="flowdraftid" name="flowdraftid"  type="hidden" value="${flowdraftid}"/>
	<input id="flowid" name="flowid"  type="hidden" value="${flowid}"/>
    <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;"> 
    	<table >
               <tr>
                <td style="width:100%;">
            建议书：<select onchange="previewSuggestDoc('1')" id="wenshu">
            				 <option value="" selected></option>
  							 <option value ="YQTQJXJYS">有期、无期提请减刑建议书</option>
  							 <option value="TQJSJYS">提请假释建议书</option> <!-- 测试报表 -->
  							 <option value="SHTQJXJYS">死缓提请减刑建议书</option>
						 </select>
			  </td>
			  <td style="white-space:nowrap;">
			  	<a class="mini-button"  id="12185" style="display:none;" plain="true" iconCls="icon-save"  onclick="saveBigData()">存盘</a>
			  </td>
		</tr>
		</table>
     </div>
     </div>
    	<div class="mini-fit" style="width:100%; height:100%;">
	     	<iframe id="subpage" width="100%" height="100%">
   			</iframe>
         </div>  
    <script type="text/javascript">
        mini.parse();
        
        function saveBigData() {
            //获取iframe的window对象
             var topWin = document.getElementById("subpage").contentWindow;
             //通过获取到的window对象操作HTML元素，这和普通页面一样
             var bigdata=topWin.saveFile();
             var tempid = document.getElementById("wenshu").value;
        	 var crimid = document.getElementById("crimid").value;
        	 var flowdraftid = document.getElementById("flowdraftid").value;
        	 var flowid = document.getElementById("flowid").value;
             $.ajax({
					type:"POST", 
					url:'<%=path%>/saveSuggestion.action?1=1', 
					data:{bigdata:bigdata,tempid:tempid, crimid:crimid, flowdraftid:flowdraftid, flowid:flowid}, 
					cache:false,
					success:function (text) {
							var obj = eval(text);
							if(obj=='1'){
								alert("操作成功！");
							}else{
								alert("操作失败！");
							}
					}
			});
        }
        function submitBigData(){
        	var aipDate = mini.formatDate ( new Date(), "yyyyMMdd" )
        	var topWin2 = document.getElementById("subpage").contentWindow;
        	topWin2.setAValue("shenheriqi", aipDate);
        	saveBigData();
        }
        
        function previewSuggestDoc(vtype){
        	var flag = document.getElementById("flag").value;
        	if(flag=='0'){
        		var menuid = document.getElementById("menuid").value;
        		init(menuid);
        		document.getElementById("flag").value = flag+'1';
        	}
        	var tempid = document.getElementById("wenshu").value;
        	var crimid = document.getElementById("crimid").value;
        	var flowdraftid = document.getElementById("flowdraftid").value;
        	if(crimid){
	           var url="toSingleSuggestDocumentPage.action?crimid="+crimid+"&tempid="+tempid+"&flowdraftid="+flowdraftid+"&vtype="+vtype;
	           document.getElementById("subpage").src=url;
            }else{
            	alert("请指定具体的罪犯！");
            }
        }
        
    </script>
 </body>
</html>