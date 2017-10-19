<%@ page language="java" import="java.util.*,com.sinog2c.model.yzgl.*"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
	String path = request.getContextPath();
	int startyear = Integer.parseInt(request.getAttribute("startyear").toString());
	int endyear = Integer.parseInt(request.getAttribute("endyear").toString());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		mini.parse();
		function save(){
			var obj=document.getElementById('selectyear');   
			var index=obj.selectedIndex;   
			var khyear = obj.options[index].text;
			document.getElementById("khyear").innerText=khyear;
		    var textareaLength=document.getElementById("remark123").value.length; 
			if(textareaLength>500) {
				alert("备注内容不能超过500个字符！");
				return;
			}
			var crimid=document.getElementById('crimid').value;
 			$.ajax({
	                url:"judgehavekhyear.action?1=1&khyear="+khyear+"&crimid="+crimid,
	                type: "post",
	                dataType:"text",
	                success: function (text) {
	                	alert(text);
	                  	//var status= mini.decode(text);
	                  	if(status=='yes'){
	                  		if(valshuru()){
		                  		if (confirm("该年份成绩已存在，是否进行修改？")) {
									valsave(status);
					            }else{
					            	return;
					            }
	                  		}else{
					            return;
					        }
	                  	}else{
	                  		if(valshuru()){
		                  		valsave(status);
	                  		}else{
					            return;
					        }
	                  	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                }
	         });
		}
		function valsave(status){//验证各输入框中值是否为正数，并进行保存
			var operate='insert';
			if(status=='yes'){
				operate='update';
			}
			document.form1.action="buluchengjilist.action?operate="+operate;
			document.form1.submit();
		}
		function valshuru(){
			var id = "";
			var status = true;
			for(var a=1;a<4;a++){
			    if(a=1){ //a=1时，验证考核成绩输入值是否合格
		           for(var i=1;i<13;i++){ //i为1-12月份
		               if(i<10){
		                  yue = "0"+i;
		                  id = "khscore"+yue;
		               }else{
		                  id = "khscore"+i;
		               }
		               score = document.getElementById(id).value;
		               var isnum=isNumber(score);
		               if(!isnum&&""!=score){
		               		alert('对不起，'+i+'月份的考核成绩值无效，请重新输入正数！');
		               		status = false;
		               		return ;
		               }
		           } 
			    }
			    if(a=2){ //a=2时，验证奖分输入值是否合格
		           for(var i=1;i<13;i++){ //i为1-12月份
		               if(i<10){
		                  yue = "0"+i; 
		                  id = "jfscore"+yue;
		               }else{
		                  id = "jfscore"+i;
		               }
		               score = document.getElementById(id).value;
	               	   var isnum=isNumber(score);
		               if(!isnum&&""!=score){
		               		alert('对不起，'+i+'月份的奖分成绩值无效，请重新输入正数！');
		               		status = false;
		               		return ;
		               }
		           }
			    }
			    if(a=3){//a=3时，验证扣分输入值是否合格
		           for(var i=1;i<13;i++){//i为1-12月份
		               if(i<10){
		                  yue = "0"+i; 
		                  id = "kfscore"+yue;
		               }else{
		                  id = "kfscore"+i;
		               }
		               score = document.getElementById(id).value;
	               	   var isnum=isNumber(score);
		               if(!isnum&&""!=score){
		               		alert('对不起，'+i+'月份的扣分成绩值无效，请重新输入正数！');
		               		status = false;
		               		return ;
		               }
		           }
			    }
			}
			return status;
		}
		function edit(khyear){
			
			var obj=document.getElementById('selectyear'); 
			var index = 0;
			if(2008==khyear) index=1;
			if(2009==khyear) index=2;
			if(2010==khyear) index=3;
			if(2011==khyear) index=4;
			if(2012==khyear) index=5;
			if(2013==khyear) index=6;
			if(2014==khyear) index=7;
			if(2015==khyear) index=8;
			if(2016==khyear) index=9;
			if(2017==khyear) index=10;
			if(2018==khyear) index=11;
			if(2019==khyear) index=12;
			if(2020==khyear) index=13;
			obj.options[index].selected = 'selected';

		//	var criminalid=document.getElementById('crimid').value;
		//	var operate='edit';
		//	document.getElementById('khyear').value=khyear;
		//	document.form1.action="buluchengjilist.action?operate="+operate;
		//	document.form1.submit();
		}
		function del(khyear){
			if(window.confirm("删除后数据无法恢复，确认删除？")){
				document.getElementById('khyear').value=khyear;
				document.form1.action="buluchengjilist.action?operate=del";
				document.form1.submit();
			}
		}
		function fanhui() {
			var url = "<%=path%>/toPublicListPage.page?1=1&menuid=${menuid}";
	        self.location.href=url;
		}
		function isNumber(score){//判断是否为数字或带小数点的数字
			var isnum = true; 
            var strP=/^\d+$/;
            if(score.indexOf(".")>0){
           		var numInt=score.substring(0,score.indexOf("."));
           		var numFloat=score.substring(score.indexOf(".")+1);
           		if(!strP.test(numInt)||(""==numFloat)){
           			isnum = false;
           		}else if((""!=numFloat)&&(!strP.test(numFloat))){
           			isnum = false;
           		}
            }else if(!strP.test(score)&&""!=score) {//验证输入值是否为正数
		   	    isnum = false;
		    }
		    return isnum;
		}
	</script>
	<style>
		.TableBackGround tr td{
			font-size:12px;
		}
		.TableBackGround a{
			text-decoration:none;
		}
		.mini-toolbar{
			border:0;
		}
	</style>
<body >
<div style="width:100%;">
	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
		<form name="form1" action="" method="post">
		<input type="hidden" id="crimid" name="crimid" value="${crimid}"/>
		<input type="hidden" id="khyear" name="khyear" value="${khyear}"/>
			<TABLE class="TableBackGround" id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="background: #fff;">
				<TR>
					<TD class="TableHeader" width="100%" align="center" height="20">
						服刑人员&nbsp;【${name}】&nbsp;计分考核信息
					</TD>
					<TD class="TableHeader" width="20%" align="center" height="20">
						<a href="#" onclick="fanhui();">返回</a>
					</TD>
				</TR>
				<tr>
					<TD class="TableBody" width="100%" colspan="6">
						<table class="DataGrid" id="Table3" cellSpacing="1" border="1" cellPadding="4" width="100%">
							 <tr align="center">
					         	<td align="center">年份</td>
								<td colspan="13" align="center">
									<select id="selectyear" name="selectyear" >
                                        <%
                                        for (int i=startyear;i<=endyear;i++) {
                                        	if(i==endyear){
                                        		%><option value="<%=i%>" selected><%=i%></option><%
                                        	} else{
                                        		%><option value="<%=i%>" ><%=i%></option><%
                                        	} 
                                        }
                                        %>
                                    </select>年 
								</td>
					        	<td rowspan="5" width="6%">
						         	<a href="#" onclick="save()"> 保存</a>
						         </td>
					         </tr>
					         <tr align="center">
								<td align="center">月份</td>
								<td >1月</td>
								<td >2月</td>
								<td >3月</td>
								<td >4月</td>
								<td >5月</td>
								<td >6月</td>
								<td >7月</td>
								<td >8月</td>
								<td >9月</td>
								<td >10月</td>
								<td >11月</td>
								<td >12月</td>
								<td >备注</td>
							 </tr>
							 <tr align="center">
								<td align="center">考核成绩</td>
								<td ><input type="text" id="khscore01" name="khscore01" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore02" name="khscore02" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore03" name="khscore03" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore04" name="khscore04" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore05" name="khscore05" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore06" name="khscore06" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore07" name="khscore07" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore08" name="khscore08" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore09" name="khscore09" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore10" name="khscore10" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore11" name="khscore11" value="" style="width:30px"></td>
								<td ><input type="text" id="khscore12" name="khscore12" value="" style="width:30px"></td>
								<td rowspan="4" >
								<textarea name="remark" cols="28" rows="6" id="remark123"></textarea>
								</td>
							 </tr>
							 <tr align="center">
								<td align="center">奖分</td>
								<td ><input type="text" id="jfscore01" name="jfscore01" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore02" name="jfscore02" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore03" name="jfscore03" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore04" name="jfscore04" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore05" name="jfscore05" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore06" name="jfscore06" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore07" name="jfscore07" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore08" name="jfscore08" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore09" name="jfscore09" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore10" name="jfscore10" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore11" name="jfscore11" style="width:30px" value=""/></td>
								<td ><input type="text" id="jfscore12" name="jfscore12" style="width:30px" value=""/></td>
								 </tr>
								 <tr align="center">
								<td align="center">扣分</td>
								<td ><input type="text" id="kfscore01" name="kfscore01" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore02" name="kfscore02" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore03" name="kfscore03" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore04" name="kfscore04" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore05" name="kfscore05" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore06" name="kfscore06" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore07" name="kfscore07" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore08" name="kfscore08" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore09" name="kfscore09" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore10" name="kfscore10" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore11" name="kfscore11" style="width:30px" value=""></td>
								<td ><input type="text" id="kfscore12" name="kfscore12" style="width:30px" value=""></td>
								 </tr>
						</table>
					</TD>
				</tr>
				<TR>
					<TD class="TableHeader" width="100%" height="30">
						历年信息
					</TD>
				</TR>
		         <tr>
		           	<TD class="TableBody" width="100%" colspan="6">
						<table class="DataGrid" id="Table3" cellSpacing="0" border="1"
							cellPadding="4" width="100%">
								<c:forEach items="${list}" var="score" >
						         <tr align="center">
						         	<td align="center">年份</td>
									<td colspan="13" align="center">有效剩余<c:out value="${score.yufenscore}"/>分。（<c:out value="${score.khyear}"/>年,
									总分<c:out value="${score.yeartotal}"/>分。
									其中:考核总分<c:out value="${score.yearkaohetotal}"/>分,
									奖励总分<c:out value="${score.yearjianglitotal}"/>分,
									扣除总分<c:out value="${score.yearchengfatotal}"/>分） 
									</td>
						         	<td rowspan="5" width="6%">
						         		<a href="#" onclick="edit('${score.khyear}')">修改</a>
						         		<a href="#" onclick="del('${score.khyear}')"> 删除</a>
						         	</td>
						         </tr>
						         <tr align="center">
									<td align="center">月份</td>
									<td >1月</td>
									<td >2月</td>
									<td >3月</td>
									<td >4月</td>
									<td >5月</td>
									<td >6月</td>
									<td >7月</td>
									<td >8月</td>
									<td >9月</td>
									<td >10月</td>
									<td >11月</td>
									<td >12月</td>
									<td >备注</td>
								 </tr>
								 <tr align="center">
									<td align="center">考核成绩</td>
									<td ><c:out value="${score.khscore01}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore02}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore03}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore04}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore05}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore06}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore07}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore08}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore09}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore10}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore11}"/>&nbsp;</td>
									<td ><c:out value="${score.khscore12}"/>&nbsp;</td>
									<td rowspan="4" width="150px" valign=top >
									<textarea cols="30" rows="3"><c:out value="${score.remark}"/></textarea>
									</td>
								 </tr>
								 <tr align="center">
									<td align="center">奖分</td>
									<td ><c:out value="${score.jfscore01}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore02}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore03}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore04}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore05}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore06}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore07}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore08}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore09}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore10}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore11}"/>&nbsp;</td>
									<td ><c:out value="${score.jfscore12}"/>&nbsp;</td>
								 </tr>
								 <tr align="center">
									<td align="center">扣分</td>
									<td ><c:out value="${score.kfscore01}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore02}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore03}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore04}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore05}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore06}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore07}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore08}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore09}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore10}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore11}"/>&nbsp;</td>
									<td ><c:out value="${score.kfscore12}"/>&nbsp;</td>
								 </tr>
							</c:forEach>
					</table>
				</TD>
		       </tr>
			</TABLE>
		</form>
	  </div>
	</div>
</body>
</html>