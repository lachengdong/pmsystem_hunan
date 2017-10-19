<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>新增或修改</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
</head>
<body>
     <input type="hidden" id="hcrimid" value="${crimid}"/>
     <input type="hidden" id="hcriname" value="${criname}"/>
     <input type="hidden" id="state" value="${state }"/>
     <input type="hidden" id="category" value="${category }"/>
     <input type="hidden" id="courtsanction" value="${courtsanction }"/>
	 <div id="form1" >
	 	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
	        <table>
	            <tr>
	                <td style="width: 100%;"></td>
	                <td style="white-space: nowrap;">
	                    <a class="mini-button" onclick="saveChangeData()" iconCls="icon-save" plain="true" style="">存盘</a>       
            			<a class="mini-button" onclick="onCancel" plain="true" iconCls="icon-close" style="">关闭</a>
            			&nbsp;&nbsp;&nbsp;&nbsp;  
	                </td>
	            </tr>
	        </table>
	    </div>
	    
        <div style="padding-left:11px;padding-bottom:5px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:70px;">罪犯编号：</td>
                    <td style="width:150px;">    
                        <input name="crimid" id="crimid" enabled="false" class="mini-textbox" value=""/>
                    </td>
                    <td style="width:70px;">罪犯姓名：</td>
                    <td style="width:150px;">    
                        <input name="criname" id="criname"  enabled="false"  class="mini-textbox" value=""/>
                    </td>
                </tr>
                <tr style="height: 10px;"></tr>
                <tr>
                    <td >判决法院地区：</td>
                    <td >    
                        <input name="courtname" id="courtname" class="mini-textbox" required="true"/>
                    </td>
                    <td >法院名称：</td>
                    <td >    
                        <input name="courttitle" id="courttitle" class="mini-textbox"  required="true"/>
                    </td>
                    <td >判裁日期：</td>
                    <td >    
                        <input name="courtsanction" id="courtsanction" class="mini-datepicker"  required="true"/>
                    </td>
                </tr>
                <tr style="height: 10px;"></tr>
                <tr>
                    <td >判决年：</td>
                    <td >    
                        <input name="courtyear" id="courtyear" class="mini-textbox"  required="true"/>
                    </td>
                    <td >判决字：</td>
                    <td >    
                        <input name="courtshort" id="courtshort" class="mini-textbox"  required="true"/>
                    </td>
                    <td >判决号：</td>
                    <td>    
                        <input name="courtsn" id="courtsn" class="mini-textbox"  required="true"/>
                    </td>
                </tr>
                <tr><td style="height: 15px;"></td></tr>  
                <tr>
                    <td >判决幅度：</td>
                    <td >    
                        <input name="courtchange" id="courtchange" emptyText="12-01-00(12年1个月0天)" class="mini-textbox"  required="true"/>
                    </td>
                    
                    <td >初审(终审)：</td>
                    <td >    
                        <input name="category" id="category" textField="category"  required="true" valueField="id" class="mini-combobox" data="CateGorys"/>
                    </td>
                    <td >判裁地区：</td>
                    <td>
	                    <input id="courtarea" name="courtarea" class="mini-treeselect" url="getCodeNameByCodeType.action?1=1&state=shengfen&codetype=GB002" multiSelect="false"  valueFromSelect="false"
								        textField="name" valueField="codeid" parentField="pcodeid"  allowInput="false" 
								    	 showClose="true"  required="true"   
								    />
				    </td>
                </tr> 
                <tr style="height: 10px;"></tr>
                <tr>
                    
                    <td >刑期起日：</td>
                    <td >    
                        <input name="courtchangefrom" id="courtchangefrom" class="mini-datepicker"  required="true"/>
                    </td>
                    <td >刑期止日：</td>
                    <td >    
                        <input name="courtchangeto" id="courtchangeto" class="mini-datepicker"  required="true"/>
                    </td>
                    <td >执行日期：</td>
                    <td >    
                        <input name="exectime" id="exectime" class="mini-datepicker"  required="true"/>
                    </td>
                </tr>
            </table>
        </div>
    </div><br /><br />
    <span style="color: blue;">注:</span>
    <span style="color: red;">(1、)如果罪犯只有一审则一审设置为终审<br />&nbsp;&nbsp;&nbsp;&nbsp;
    (2、)如果罪犯有多审则把最后一次审判设置为终审。</span>
	<script type="text/javascript">
	    var CateGorys = [{id:255,category:'初审'},{id:1,category:'终审'}];
	    mini.parse(); 
	    queryChangeInfo();
	    var form = new mini.Form("#form1");
        function onCancel(){
        	var crimid=$("#hcrimid").val();
            var criname = $("#hcriname").val();
            var url = "<%=path%>/basicInfo/goDanGePanCaiInfoView.action?1=1&crimid="+crimid+"&name="+encodeURI(encodeURI(criname));
            self.location.href=url;
        }

        //获取修改的罪犯的信息 
	 	function queryChangeInfo(){
             var state=$("#state").val();
             var crimid=$("#hcrimid").val();
             var criname=$("#hcriname").val();
             var category=$("#category").val();
             var courtsanction = $("#courtsanction").val();
             if(state == 'new'){
                 mini.get("crimid").setValue(crimid);
                 mini.get("criname").setValue(criname);
             }else if(state == 'edit'){
                 $.ajax({
                     type:'post',
                     url:'queryChangeInfo.action?1=1&crimid='+crimid+'&category='+category+'&courtsanction='+courtsanction,
                     success:function (text){
	                     var o = mini.decode(text);
	                     $("#courtsanction").val(o.courtsanction);
	                     form.setData(o);
	                     mini.get("criname").setValue(criname);
                     }
                 });
             }
		}

		//保存 罪犯信息
		function saveChangeData(){
			var count = 0;
            //保存判断 两种情况
			if (form.isValid() == false) return;
			//如果 一审或者二审对应已经有数据 那么不能新增
			var crimid = mini.get("crimid").getValue();
			var category = mini.get("category").getValue();
			var catetext = mini.get("category").getText();
			var state = $("#state").val();
			var hcriname = $("#hcriname").val();
			var datetion = mini.get("courtsanction").getValue();
			//判断 此处两个为必选项 
			if(datetion == ""){
                 alert("判裁日期不能为空!");return;
		    }
		    if(category == ""){
                 alert("必须选择初审(终审)!");return;
			}
            //$.ajax({
            //    type:'post',
            //    url:'<%=path%>/basicInfo/judgeIsExistYiShenAndErShen.action?1=1&crimid='+crimid+'&category='+category,
            //    async:false,
            //    success:function (text){
            //        count = text;
            //    }
            //});
            //if(state == 'new'){
            //	if(count == 1){
            //        alert("罪犯:"+hcriname+""+catetext+"已经存在!");
            //        return;
            //    }
            //}
            var data = form.getData();
            var json = mini.encode([data]);
            var courtsanction=$("#courtsanction").val();
            $.ajax({
                type:'post',
                url:'<%=path%>/basicInfo/saveChangeData.action?1=1',
                data:{data:json,state:state,courtsanction:courtsanction},
                async:false,
                success:function(text){
                	if(text == '"1"'){
                        alert("保存成功!");
                        
                        var year = "";
                        var month = "";
                        var day = "";
                        year = datetion.getYear();
                        if((datetion.getMonth()+1) < 10){
                            month = "0"+(datetion.getMonth()+1);
                        }else{
                        	month = datetion.getMonth()+1;  
                        }
                        if((datetion.getDate()+1) < 10){
                        	day = "0"+datetion.getDate();
                        }else{
                        	day = datetion.getDate();
                        }
                        $("#courtsanction").val(year+month+day);
                        onCancel();
                    }
                    
                }
            });
	    }
	    // 根据 上级部门查询下级部门 
	    function onsdidchanged(state){
            var codeid = mini.get(state).getValue();
            if(state == 'shengfen'){
                var url = "<%=path%>/basicInfo/getCodeNameByCodeType.action?1=1&state=shiqu&codetype=GB002&codeid="+codeid; 
                var combox = mini.get("shiqu");
                combox.setUrl(url);
                //当选择 省份的时候 把县 进行置空 
                var data =[];
                var combox1 = mini.get("xianqu");
                combox1.setData(data);
            }else if(state == 'shiqu'){
            	var url = "<%=path%>/basicInfo/getCodeNameByCodeType.action?1=1&state=shiqu&codetype=GB002&codeid="+codeid; 
                var combox = mini.get("xianqu");
                combox.setUrl(url);
            }
		}
		//关闭 弹出窗口
	    function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
	</script>
</body>
</html>