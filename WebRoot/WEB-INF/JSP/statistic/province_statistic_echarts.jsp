<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
	<head>
		<title>全省案件统计</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    	<meta name="description" content="ECharts"/>
    	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<%=path%>/scripts/echarts3/js/echarts.js"></script>
    	<script type="text/javascript" src="<%=path%>/scripts/echarts3/js/${provincePinYin}.js"></script>
		<style type="text/css">
			.echartsDiv div{
				    			border:1px dashed #FFFFFF;
				    			background:#404a59; 
		    		}
		    		
		    #div3{
    			margin-top:1px;
    		}
    		
    		#div4{
    			margin-top:2px;
    		}
    		
    		
    		#div5{
    			margin-top:1px;
    		}
		</style>
	</head>
	<body >
			<input id="cityName" class="mini-hidden"/>
			<input id="orgid" class="mini-hidden"/>
			<div style="width:100%;" class="statisticDiv" >
				<div style="width: 100%;height: 25px; " class="searchDiv">
							<div class="mini-toolbar">
									起始时间：<input  id="startDate" class="mini-datepicker" value=""/>&nbsp;&nbsp;
									结束时间：<input  id="endDate" class="mini-datepicker" value=""/>&nbsp;&nbsp;
									羁押类型：<input  id="jylx" class="mini-combobox" data="[{id:'全部',text:'全部'},{id:'在押',text:'在押'},{id:'释放',text:'释放'}]" value="全部"/>&nbsp;&nbsp;
													<a class="mini-button" iconCls="icon-search" onclick="showEcharts">查询</a>&nbsp;
													<a class="mini-button" iconCls="icon-reload" onclick="reset">重置</a>
							</div>
				</div>
				<!-- <div style="height:2px;"></div> -->
				<hr />
				
				<div style="width:100%;" class="echartsDiv"  >
					<div style="width: 69%;height: 400px ;  float: left;"  id="div1" ></div>
					<div style="width: 30.5%;height: 200px ;  float: right;"  id="div2" ></div>
					<div style="width: 30.5%;height: 200px ;  float: right;"  id="div3"> </div>
					<div style="width: 69%;height: 200px ;  float: left;"  id="div4" ></div>
					<div style="width: 30.5%;height: 200px ;  float: right;"  id="div5" ></div>
				</div>
				

			</div>
			
			<div id="jail_window" class="mini-window"  title="监狱列表" style="width:400px;height:250px;" showMaxButton="true" showCollapseButton="true" 
				showShadow="true"   showModal="false" allowResize="true" allowDrag="true" visible="false">
					<div id="jail_window_toolbar" class="mini-toolbar"  style="padding:2px;border-bottom:0;">
						<table style="width:100%">
							<tr>
								<td  align="center">
									请输入监狱名称：<input class="mini-textbox" id="key" onenter="searchJail();"/>
									<a class="mini-button"  iconCls="icon-search" plain="true" onclick="searchJail();">查询</a>
								</td>
							</tr>
						</table>
					</div>
					<div id="jail_window_fit" class="mini-fit">
						<div id="jail_window_datagrid" class="mini-datagrid" style="width:100%;height:100%;"   url=""   sizeList="[5,10,20,50]" pageSize="10">
							<div property="columns">
								 <div type="indexcolumn" ></div>
				                <div field="orgid" width="40" headerAlign="center" align="center" allowSort="true">编号</div>    
				                <div field="name" width="60" headerAlign="center" align="center" allowSort="false">监狱名称</div>                            
				                <div field="city" width="50"  headerAlign="center" align="center" allowSort="false">所属城市</div>
				                <div field="action" width="100" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>
							</div>
						</div>
					</div>
				</div>

			<script type="text/javascript">
				mini.parse();
				 var myChart1 = echarts.init(document.getElementById('div1'));
				 var myChart2= echarts.init(document.getElementById('div2'));
				 var myChart3= echarts.init(document.getElementById('div3'));
				 var myChart4= echarts.init(document.getElementById('div4'));
				 var myChart5= echarts.init(document.getElementById('div5'));
				 var provinceName="${provinceName}";
					option1 = {
	    					series : [ {
	    						name:provinceName+'省',
						        type : 'map',
						        map : provinceName,
					            selectedMode : 'single',
					            layoutCenter: ['50%', '40%'],
					            layoutSize:380,
					            label: {
					                normal: {
					                    show: true
					                },
					                emphasis: {
					                    show: true
					                }
					            },
					            itemStyle: {
					                normal: {
					                	borderColor: '#111'
					                }
					            },
					            data:[]
						        } ],
		    	            tooltip : {
		    	                trigger: 'item'
		    	            }
		    	        };
				
					 option2 = {
							 title : {
							        text: '三类犯数量统计',
							        x:'center',
							        textStyle:{
							        	color:'#FFFFFF'
							        }
							        
							    },
							    tooltip : {
							        trigger: 'item',
							        formatter: "{a} <br/>{b} : {c} ({d}%)"
							    },
							    color:['#c23531','#61a0a8','#d48265','#ca8622','#bda29a'],
							    series : [
							        {
							            name: '三类犯',
							            type: 'pie',
							            radius : '55%',
							            center: ['50%', '60%'],
							            data:[],
							            itemStyle: {
							                emphasis: {
							                    shadowBlur: 10,
							                    shadowOffsetX: 0,
							                    shadowColor: 'rgba(0, 0, 0, 0.5)'
							                }
							            }
							        }
							    ]
						};
					 
					 option3 = {
							 title : {
							        text: '罪犯类型数量统计',
							        x:'center',
							        textStyle:{
							        	color:'#FFFFFF'
							        }
							        
							    },
							    tooltip : {
							        trigger: 'item',
							        formatter: "{a} <br/>{b} : {c} ({d}%)"
							    },
							    color:['#749f83','#c4ccd3'],
							    series : [
							        {
							            name: '罪犯类型',
							            type: 'pie',
							            radius : '55%',
							            center: ['50%', '60%'],
							            data:[],
							            itemStyle: {
							                emphasis: {
							                    shadowBlur: 10,
							                    shadowOffsetX: 0,
							                    shadowColor: 'rgba(0, 0, 0, 0.5)'
							                }
							            }
							        }
							    ]
						};
						
						var option4={
								title:{
									text:'案由',
									x:'left',
								     textStyle:{
								        	color:'#FFFFFF'
								        }
								},
								tooltip:{
									trigger:'axis'
								},
								color:['#E87C25'],
								toolbox:{
									show:true,
									y:'center',
									orient : 'vertical',
									feature: {
					                    //数据视图
					                    dataView: {
					                        show: true,
					                        readOnly: false
					                    },
					                    //图表类型切换
					                    magicType: {//动态类型切换
					                        type: ['bar', 'line']//bar:柱状图，line:线状图
					                    },
					                 	 //保存图片
					                    saveAsImage: {
					                        show: true
					                    },
					                    //刷新
					                    restore: {
					                        show: true
					                    }
					                }
								},
								xAxis:[{
									type:'category',
									data:['故意杀人罪','诈骗罪','盗窃罪','放火罪','抢劫罪','强奸罪','贩卖毒品罪','拐卖人口罪','非法买卖枪支罪','开设赌场罪','绑架罪','组织卖淫罪','受贿罪'],
									axisLabel: {
										   interval:0,  
										   rotate:30,
										   textStyle:{
											   color:'#ffa022'
										   }
										} 
								}],
								yAxis:[{
									type:'value',
								}],
								series:[
								        {
								        	name:'案件数量',
								        	type:'bar',
								        	tiled:'总量',
								        	data:[]
								        }
					
								        ]
						};
					
						 option5 = {
								    title : {
								        text: '减刑假释'
								    },
								    tooltip : {
								        trigger: 'axis'
								    },
					
								    toolbox: {
								        show : true,
								        feature : {
								            dataView : {show: true, readOnly: false},
								            magicType : {show: true, type: ['line', 'bar']},
								            restore : {show: true},
								            saveAsImage : {show: true}
								        }
								    },
								    calculable : true,
								    xAxis : [
								        {
								            type : 'category',
								            data : ['减刑','假释','退案']
								        }
								    ],
								    yAxis : [
								        {
								            type : 'value'
								        }
								    ],
								    series : [
								        {
								            name:'',
								            type:'bar',
								            data:[],
								            markPoint : {
								                data : [
								                    {type : 'max', name: '最大值'},
								                    {type : 'min', name: '最小值'}
								                ]
								            },
								            markLine : {
								                data : [
								                    {type : 'average', name: '平均值'}
								                ]
								            }
								        }
								    ]
								};
					
					 
					 myChart1.setOption(option1);
					 myChart2.setOption(option2);
					 myChart3.setOption(option3);
					 myChart4.setOption(option4);
					 myChart5.setOption(option5);
					
					$(document).ready(function(){
				    	var startDateObj=mini.get("startDate");
				    	var endDateObj=mini.get("endDate");
				    	var defaultStartDate=getYearFirstDay();//获取当前年的第一个月的第一天日期，日期格式：yyyy-MM-dd
				    	var defaultEndDate=getNowFormatDate();//获取当前时间，日期格式：yyyy-MM-dd
				    	startDateObj.setValue(defaultStartDate);
				    	endDateObj.setValue(defaultEndDate);
						showEcharts();
					});
					 
				
				function showEcharts(){
				 	var cityName=mini.get("cityName").getValue();
				 	var orgid=mini.get("orgid").getValue();
				 	var jylx=mini.get("jylx").getValue();
				   	var queryStartDate=mini.get("startDate").getValue();
			    	var queryEndDate=mini.get("endDate").getValue();
				 	queryStartDate=getFormatDate(queryStartDate);
				 	queryEndDate=getFormatDate(queryEndDate);
				 	
				 	myChart2.showLoading();
				 	myChart3.showLoading();
				 	myChart4.showLoading();
				 	myChart5.showLoading();
				 	if(cityName=="" || cityName==null){
				 		showMap(queryStartDate,queryEndDate,jylx);
				 	}
				 	showSanLeiCaseReport(queryStartDate,queryEndDate,cityName,orgid,jylx);
				 	showCaseTypeReport(queryStartDate,queryEndDate,cityName,orgid,jylx);
				 	showAnYouReport(queryStartDate,queryEndDate,cityName,orgid,jylx);
				 	showJXJSReport(queryStartDate,queryEndDate,cityName,orgid,jylx);
				}
				
				 /**
				 地图信息显示
				 */
				 
				function showMap(queryStartDate,queryEndDate,jylx){
					 var mapUrl="<%=path%>/statistic/getProvinceCaseCountInfo.json?";
					 if(queryStartDate){
						 mapUrl+="&queryStartDate="+queryStartDate;
					 }
					 if(queryEndDate){
						 mapUrl+="&queryEndDate="+queryEndDate;
					 }
					 if(jylx){
						 mapUrl+="&jylx="+encodeURIComponent(jylx);
					 }
					
					$.get(mapUrl).done(function(data){
						myChart1.hideLoading();
						var numList=[];
						for(var i=0;i<data.length;i++){
							numList.push(data[i].value);
						}
						var maxNum=Math.max.apply(null,numList);
						if(maxNum==0){maxNum=1;}
						myChart1.setOption({
							 visualMap: {
							        min: 0,
							        max:maxNum,
							        left: 'left',
							        top: 'bottom',
							        text: ['高','低'],           
							        calculable: true
							    },
							series:[{
								name:'在押犯数量',
								data:data
							}]
						});
					});
	
				} 
				 
				 /**
				 三类犯信息显示
				 */
				
				function showSanLeiCaseReport(queryStartDate,queryEndDate,cityName,orgid,jylx){
					var sanLeiUrl="<%=path%>/statistic/getSanLeiCaseCountInfo.json?";
					 if(queryStartDate){
						 sanLeiUrl+="&queryStartDate="+queryStartDate;
					 }
					 if(queryEndDate){
						 sanLeiUrl+="&queryEndDate="+queryEndDate;
					 }
					 if(cityName){
						 sanLeiUrl+="&cityName="+encodeURIComponent(cityName);
					}
					 if(jylx){
						 sanLeiUrl+="&jylx="+encodeURIComponent(jylx);
					 }
					 if(orgid){
						 sanLeiUrl+="&orgid="+encodeURIComponent(orgid);
					 }
					$.get(sanLeiUrl).done(function(data){
						myChart2.hideLoading();
						myChart2.setOption({
							series:[{
								name:'三类犯',
								data:data
							}]
						});
					});
				}
				 
				 /**
				案件类型信息显示
				 */
				
				function showCaseTypeReport(queryStartDate,queryEndDate,cityName,orgid,jylx){
					var caseTypeUrl="<%=path%>/statistic/getCaseTypeCountInfo.json?";
					 if(queryStartDate){
						 caseTypeUrl+="&queryStartDate="+queryStartDate;
					 }
					 if(queryEndDate){
						 caseTypeUrl+="&queryEndDate="+queryEndDate;
					 }
					 if(cityName){
						 caseTypeUrl+="&cityName="+encodeURIComponent(cityName);
					}
					 if(jylx){
						 caseTypeUrl+="&jylx="+encodeURIComponent(jylx);
					 }
					 if(orgid){
						 caseTypeUrl+="&orgid="+encodeURIComponent(orgid);
					 }
					$.get(caseTypeUrl).done(function(data){
						myChart3.hideLoading();
						myChart3.setOption({
							series:[{
								name:'罪犯类型',
								data:data
							}]
						});
					});
				}
				 /**
				案由信息显示
				 */
				
				function showAnYouReport(queryStartDate,queryEndDate,cityName,orgid,jylx){
					var anYouUrl="<%=path%>/statistic/getAnYouCaseCountInfo.json?";
					 if(queryStartDate){
						 anYouUrl+="&queryStartDate="+queryStartDate;
					 }
					 if(queryEndDate){
						anYouUrl+="&queryEndDate="+queryEndDate;
					 }
					 if(cityName){
						 anYouUrl+="&cityName="+encodeURIComponent(cityName);
					}
					 if(jylx){
						 anYouUrl+="&jylx="+encodeURIComponent(jylx);
					 }
					 if(orgid){
						 anYouUrl+="&orgid="+encodeURIComponent(orgid);
					 }
					$.get(anYouUrl).done(function(data){
						myChart4.hideLoading();
						myChart4.setOption({
							series:[{
								name:'案由统计',
								data:data
							}]
						});
					});
				}
				 
				 
				 /**
				减刑、假释信息显示
				 */
				
				function showJXJSReport(queryStartDate,queryEndDate,cityName,orgid,jylx){
					var JXJSUrl="<%=path%>/statistic/getJXJSCaseCountInfo.json?";
					 if(queryStartDate){
						 JXJSUrl+="&queryStartDate="+queryStartDate;
					 }
					 if(queryEndDate){
						 JXJSUrl+="&queryEndDate="+queryEndDate;
					 }
					 if(cityName){
						 JXJSUrl+="&cityName="+encodeURIComponent(cityName);
					}
					 if(jylx){
						 JXJSUrl+="&jylx="+encodeURIComponent(jylx);
					 }
					 if(orgid){
						 JXJSUrl+="&orgid="+encodeURIComponent(orgid);
					 }
					$.get(JXJSUrl).done(function(data){
						myChart5.hideLoading();
						myChart5.setOption({
							series:[{
								name:'在押、释放统计',
								data:data
							}]
						});
					});
				}

				
				
				function getFormatDate(originDate){
					var desDate=new Date(originDate);
					return mini.formatDate(desDate,'yyyy-MM-dd');
				}
				

				 
					function getCurrentYear(){
						var now=new Date();
						var year=now.getFullYear();
						return year;
					}
					
					function getYearFirstDay(){
						var currYear=getCurrentYear();
						return currYear+"-01-01"
					}
					
					//获取当前时间，格式YYYY-MM-DD
				    function getNowFormatDate(){
				        var dateObj = new Date();
				        var seperator = "-";
				        var year = dateObj.getFullYear();
				        var month = dateObj.getMonth() + 1;
				        var date = dateObj.getDate();
				        if (month >= 1 && month <= 9) {
				            month = "0" + month;
				        }
				        if (date >= 0 && date <= 9) {
				        	date = "0" + date;
				        }
				        var currentdate = year + seperator+ month + seperator + date;
				        return currentdate;
				    }
					
					function onActionRenderer(e){
						return "<a href='javascript:statisticByJail();'>查看监狱统计</a>";
					}
					
					function statisticByJail(){
						var grid=mini.get("jail_window_datagrid");
						var record=grid.getSelected();
						var orgid=record.orgid;
						var jailObj=mini.get("orgid");
						jailObj.setValue(orgid);
						showEcharts();
					}
					
					function reset(){
						mini.get('cityName').setValue("");
						mini.get('orgid').setValue("");
						mini.get('jail_window').hide();
						showEcharts();
					}
					
					myChart1.on('mapselectchanged',function(param){
						var name=param.batch[0].name;
						mini.get('cityName').setValue(name);
						mini.get('orgid').setValue("");
						var jailGrid=mini.get("jail_window_datagrid");
						var key=mini.get("key").getValue();
						jailGrid.setUrl("<%=path%>/statistic/getJailPageDataGrid.json?1=1");
						jailGrid.load({cityName:name,key:key});
						var win=mini.get("jail_window");
						win.show("right","middle");
						showEcharts();
					});
					
					myChart2.on('click',function(param){
						var name=param.name;
						var cityName=mini.get('cityName').getValue();
						var orgid=mini.get('orgid').getValue();
						var jylx=mini.get("jylx").getValue();
					   	var queryStartDate=mini.get("startDate").getValue();
				    	var queryEndDate=mini.get("endDate").getValue();
					 	if(queryStartDate){
					 		queryStartDate=getFormatDate(queryStartDate);
					 	}
					 	if(queryEndDate){
					 		queryEndDate=getFormatDate(queryEndDate);
					 	}
						popupDetailWindow('<%=path%>/statistic/toCaseDetailInfo.page','sanLei',name,null,cityName,orgid,queryStartDate,queryEndDate,jylx);
					});
					
					
					myChart3.on('click',function(param){
						var name=param.name;
						var cityName=mini.get('cityName').getValue();
						var orgid=mini.get('orgid').getValue();
						var jylx=mini.get("jylx").getValue();
					   	var queryStartDate=mini.get("startDate").getValue();
				    	var queryEndDate=mini.get("endDate").getValue();
					 	if(queryStartDate){
					 		queryStartDate=getFormatDate(queryStartDate);
					 	}
					 	if(queryEndDate){
					 		queryEndDate=getFormatDate(queryEndDate);
					 	}
						popupDetailWindow('<%=path%>/statistic/toCaseDetailInfo.page','caseType',name,null,cityName,orgid,queryStartDate,queryEndDate,jylx);
				});
					
					
					
					myChart4.on('click',function(param){
						var dataIndex=param.dataIndex;
						var cityName=mini.get('cityName').getValue();
						var orgid=mini.get('orgid').getValue();
						var jylx=mini.get("jylx").getValue();
					   	var queryStartDate=mini.get("startDate").getValue();
				    	var queryEndDate=mini.get("endDate").getValue();
					 	if(queryStartDate){
					 		queryStartDate=getFormatDate(queryStartDate);
					 	}
					 	if(queryEndDate){
					 		queryEndDate=getFormatDate(queryEndDate);
					 	}
						popupDetailWindow('<%=path%>/statistic/toCaseDetailInfo.page','anYou',null,dataIndex,cityName,orgid,queryStartDate,queryEndDate,jylx);
				});
					
					myChart5.on('click',function(param){
						var dataIndex=param.dataIndex;
						var cityName=mini.get('cityName').getValue();
						var orgid=mini.get('orgid').getValue();
						var jylx=mini.get("jylx").getValue();
					   	var queryStartDate=mini.get("startDate").getValue();
				    	var queryEndDate=mini.get("endDate").getValue();
					 	if(queryStartDate){
					 		queryStartDate=getFormatDate(queryStartDate);
					 	}
					 	if(queryEndDate){
					 		queryEndDate=getFormatDate(queryEndDate);
					 	}
						popupDetailWindow('<%=path%>/statistic/toCaseDetailInfo.page','jxjs',null,dataIndex,cityName,orgid,queryStartDate,queryEndDate,jylx);
				});
					
					
					 function popupDetailWindow(openPath,type,name,dataIndex,cityName,orgid,queryStartDate,queryEndDate,jylx){
						 var url=openPath+"?1=1";
						 if(type){
							 url+="&type="+encodeURIComponent(type);
						 }
						 if(name){
							 url+="&name="+encodeURIComponent(name);
						 }
						 if(dataIndex||dataIndex==0){
							 url+="&dataIndex="+encodeURIComponent(dataIndex);
						 }
						 if(cityName){
							 url+="&cityName="+encodeURIComponent(cityName);
						 }
						 if(orgid){
							 url+="&orgid="+encodeURIComponent(orgid);
						 }
						 if(jylx){
							 url+="&jylx="+encodeURIComponent(jylx);
						 }
						 if(queryStartDate){
							 url+="&queryStartDate="+encodeURIComponent(queryStartDate);
						 }
						 if(queryEndDate){
							 url+="&queryEndDate="+encodeURIComponent(queryEndDate);
						 }
						mini.open({
							url:url,
							title:'数据详情信息',
							width:'800px',
							height:'450px',
							onload:function(){
								
							},
							ondestroy:function(){
								
							}
						}); 
						 
					 }
					 
					function  searchJail(){
						var cityName=mini.get("cityName").getValue();
						var key=mini.get("key").getValue();
						var jailGrid=mini.get("jail_window_datagrid");
						jailGrid.setUrl("<%=path%>/statistic/getJailPageDataGrid.json?1=1");
						jailGrid.load({cityName:cityName,key:key});
					}
					 
					 
					
			 
			 
			</script>
		 	
	</body>
</html>