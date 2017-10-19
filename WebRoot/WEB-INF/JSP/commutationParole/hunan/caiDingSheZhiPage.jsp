<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>裁定信息设置</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"rel="stylesheet" type="text/css" />
	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	<style type="text/css">
		html,body {
			font-size: 12px;padding: 0;margin: 0;border: 0;height: 100%;overflow: auto;
		}
	</style>
  </head>
  
  <body >
   <input type="hidden" value="${sfaddress}" id="sfaddress"/>  
    <input type="hidden" value="${sqaddress}" id="sqaddress"/> 
    <input type="hidden" value="${dqxjaddress}" id="dqxjaddress"/> 
    <input type="hidden" value="${xxdzaddress}" id="xxdzaddress"/>
    <input type="hidden" value="${par5}" id="par5"/>
    <form id="form1" method="post">
	<div class="mini-toolbar mini-grid-headerCell" style="padding: 1px; border-bottom: 0px;">
	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
			<tr>
				<td style="width: 100%;">
				    <a  class="mini-button" onclick="onOk();" plain="true" iconCls="icon-save" style="width: 60px;">确定</a>
					<a  class="mini-button" onclick="onCancel();" plain="true" iconCls="icon-close" style="width: 60px;">关闭</a>
        		</td>
				<td style="white-space: nowrap;">
				</td>
			</tr>
		</table>
	</div>
	<div style="padding-left: 15px; padding-bottom: 5px;">
		<table style="table-layout: fixed;">
			<tr style="height: 10px;">
				
				<td style="width: 60px;">裁定时间：</td>
				<td >
					<input id="courtsanction" name="courtsanction"  allowInput="false" class="mini-datepicker" value="javascript:new Date()" dateFormat="yyyyMMdd" required="true"/>
				</td>
			</tr>
			<tr>
			<td style="width: 60px;">执行时间：</td>
				<td >
					<input id="exectime" name="exectime"  allowInput="false" class="mini-datepicker" value="javascript:new Date()" dateFormat="yyyyMMdd" required="true"/>
				</td>
			</tr>
			<tr>
                     <td width="90px;">判裁机关：</td>
                     <td width="120px;">    
                          <input id="select1" class="mini-combobox" textField="name" valueField="codeid"  allowInput="false" style="width:100%;" 
                          	   onvaluechanged="select1changed" url="getAllSFSelect.json?1=1" value="156" pinyinField="scearch" emptyValue="省份"
                          />
                     </td>
                </tr>
                <tr>
                <td width="90px;"></td>
                <td width="120px;">    
                          <input id="select2" class="mini-combobox" 
							     textField="name" valueField="codeid"  allowInput="false" style="width:100%;" pinyinField="scearch" 
							     onvaluechanged="select2changed"  emptyValue="市区"
							/>
                    </td>
                 </tr>
                 <tr>
                 <td width="90px;"></td>
                     <td width="120px;">    
                          <input id="select3" class="mini-combobox" 
							       textField="name" valueField="codeid"  allowInput="false" style="width:100%;"  pinyinField="scearch"  onvaluechanged="select4changed"
							        emptyValue="县或区"
							/>
                    </td>
                 </tr>
                 <tr>  
                           <td width="90px;">法院名称：</td>
                           <td width="245px;">
                                 <input id="xxaddress" name="xxaddress" class="mini-combobox" textField="text" valueField="id" style="width:100%;"
							        data="Comboxdata" value="0" allowInput="false"/>
                           </td>
                 </tr>
                 <tr>
                    <td style="width: 60px;">裁定年份：</td>
					<td >
						<input id="curyear" name="curyear"  class="mini-spinner" value="2017" minValue="2010" maxValue="2050"  width="100%" vtype="int"/>
					</td>
                 </tr>
                 <tr>
                 	<td style="width: 60px;">裁定字号：</td>
					<td >
						<input id="courtshort" name="courtshort" value="湘07刑更第" allowInput="true" class="mini-textbox"   width="100%"/>
					</td>
                 </tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	var Comboxdata = [{ id: 0, text: '中华人民共和国最高人民法院'},{ id: 1, text: '高级人民法院'},{ id: 2, text: '中级人民法院'},
          		{ id: 3, text: '人民法院'},{ id: 4, text: '铁路法院'},{ id: 5, text: '军事法院'}]; 
    
	mini.parse();
	var s1 = mini.get("select1");
	var s2 = mini.get("select2");
	var s3 = mini.get("select3");
  	var form = new mini.Form("form1");
    //当页面第一次打开的时候默认选择 已经存在省份和地区和详细地址
    defaultSelect();
    function onDateRenderer(e) {
     	if(e && e.value){
     		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
     	}
         return "";
	 }


      //标准方法接口定义
      function SetData(data) {
          //跨页面传递的数据对象，克隆后才可以安全使用
          data = mini.clone(data);
      }
      

      function defaultSelect(){
          //查询出所有省份 ，然后默认选择 一个省份
          var s1value = $("#sfaddress").val();
          s1.setValue(s1value);
          
          //查询出默认省份所对应的所有的市区 ，然后选择 一个市区
          
          var s2value = $("#sqaddress").val();
      	  var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s1value;
          s2.setUrl(url);
          s2.setValue(s2value);
          
          
          //然后根据默认市区 ，来确定选择本市区的所有的县或地区，然后默认选择一个地区或者县
          
          var s3value = $("#dqxjaddress").val();
      	  var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s2value;
          s3.setUrl(url);
          s3.setValue(s3value);
          
          //然后把具体地址  放到 对应的框中
          var s4value = $("#xxdzaddress").val();
          mini.get("xxaddress").setValue(s4value);
          
          //判断 par5 是否可以编辑具体地址

          var par5value = $("#par5").val();
          if(par5value == '1'){
               mini.get("xxaddress").setEnabled(false);
          }
          
          
          //判断市区和县打开就为空的时候 那么锁定市、县或区 
          if(s2value == ""){
               s2.setEnabled(false);
          }
          if(s3value == ""){
               s3.setEnabled(false);
          }
          
      }
      function select1changed() {
      	var s1v = s1.getValue();
          s2.setEnabled(true);
          s3.setEnabled(false);
    		var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s1v;
    		s2.setUrl(url);
    		s3.setValue("");
    		select4changed();
      }
      
      function select2changed() {
      	var s2v = s2.getValue();
      	if(s2v){
              s3.setEnabled(true);
      		var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s2v;
      		s3.setUrl(url);
      		select4changed();
      	}
      }
      
      mini.get("xxaddress").select(0);
      
      function select4changed() {
      	var s1v = s1.getText();
      	var s2v = s2.getText();
      	var s3v = s3.getText();
      	if(s1v){
      	    mini.get("xxaddress").select(1);
	        	if(s2v){
	        	    mini.get("xxaddress").select(2);
	        	}
	        	if(s3v){
	        		mini.get("xxaddress").select(3);
	        	}
	        	
      	}
      }
      
	function onOk(e) {
		CloseWindow("ok");
	}
      
	function GetData(){
		
		var result = "";
		var s1value = s1.getValue();//获取省份的codeid
		var s2value = s2.getValue();//获取所在市区的codeid
		var s3value = s3.getValue();//获取所在县或区的codeid
		var s1text = s1.text;//获取省份可视文本
		var s2text = s2.text;//获取省份可视文本
		var s3text = s3.text;//获取省份可视文本
		
		var courtsanction = mini.get("courtsanction").getFormValue();
		var exectime = mini.get("exectime").getFormValue();
		var curyear = mini.get("curyear").getValue();
		var courtshort = mini.get("courtshort").getValue();
		
		if (!curyear){
			alert("请输入年份！");
			return;
		}else if(!courtsanction){
			alert("请选择判裁日期！");
			return;
		}else if(!exectime){
			alert("请选择执行日期！");
			return;
		}else if(!courtshort){
			alert("请选择字号！");
			return;
		}
		
		var s5value = mini.get("xxaddress").getText();//获取附加法院名称
		//判断是否控制 详细地址为必填项
		var par5 = $("#par5").val();
		
		if(par5 == "0"){
			if(!s5value){
				alert("法院名称为必填项!");return;
			}
		}
		var data = new Array([7]);
		data[0] = '-1';
		if(s1value){//法院所在地区 就放在数组的第一位置，如果省份不为空那么就取省份的值
			data[0] = s1value;//省分代码 gb002
		}
		if(s2value){
			data[0] = s2value;//市代码
		}
		if(s3value){
			data[0] = s3value;//地区代码获取是县
		}
		
		
		//此处判断和其他地方 不一样，要判断 取最后一个选项的值而不是最后一个级别的值
		var url = "getJiGuanShuJuData.action?1=1&codeType=GB002&codeId="+data[0];
		$.ajax({
		    url: url,
		    data: {},
		    type: "post",
		    async: false,
		    success: function (text) {
		   		var o = mini.decode(text);
		   		if(data[0] != '-1'){
		   			if(o.remark){
		       			data[1] = o.remark;//法院所在地区的描述
		       		}else{
		       			data[1] = o.name;
		       		}
		       	}else{
		       		data[1]='';
		       	}
		   		data[2] = s5value;//法院名称
		        data[3] =courtsanction;
		      	data[4] =exectime;
		   		data[5] =curyear;
		   		data[6] =courtshort;
		    }
		});
		
		return data;
	}


	
       
      
      
	//关闭窗口
	function onCancel(e) {
		CloseWindow("cancel");
	}
	       
	function CloseWindow(action) {            
		if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
		else window.close();            
	}
</script>
</body>
</html>
