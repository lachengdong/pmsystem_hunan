    

function fuyi(obj){
	
	alert(obj);
	 window.open('','blank','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no');
	
}


function tousu(obj){
	 window.open ('','self','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
	alert(obj);
	
}


$(function(){
     //当点击监狱办理的事件时，触发该事件
 
     $("#tousufuyi").click(function(){
    	 $("#contenttype").attr("value","tsfy");
    	 var result=$("#crimid").attr("value");
    		$.ajax({
    	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&thisPage=0",
    	        type: "post",
    	        async:false,
    	        dataType: "json", 
    	        success: function (data) {
    	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
        	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
           	     
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>"+
 	        		"<td style='border:1px solid blue;border-collapse:collapse;font-size:20px;'>操作</td></tr>";
    	        var tail="</table>";
    	        var content="";
    	        var td="";
    	        var BH="";
    	          var order=0;
    	            $.each(data, function(key, val) { 
        	            if(key!='pageopetion'){
                          order=parseInt(key);
                          order=order+1;
                          content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
            	            }
                	                $.each(val,function(ind,vald){
                                      if(ind=='thisPage'){
                                    	if(vald=='0'){
                                    	    $("#yeshu").text('1');
                                    	    $("#dbyeshu").text('1');
                                    	}else{
                                     		var currentpagenum=parseInt(vald);
                                      		 $("#yeshu").text(currentpagenum+1);
                                       	    $("#dbyeshu").text(currentpagenum+1);
                                        	}
                                          }else if(ind=='pageCount'){
                                        	  $("#pageCount").text(vald);
                                              }else if(ind=='allCount'){
                                            	  $("#allCount").text(vald);
                                            	  BH=ind;
                                              }else{
                                            	  //alert(ind);
                                           	   if(ind=='ID'){ BH=vald; }else{
                                            	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                           	        }  
                                           	   }
                	                });
            	                if(BH=='allCount'){
            	                	  content=content+td+"</tr>";
            		            	  	
            	                }else{
            	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
            	                	 "<input class='ts_fybut' type='button'  style='  cursor: pointer;cursor:hand;' value='复议' onClick='fuyi("+BH+");'>"+
                                      "<input class='ts_fybut' type='button'  value='投诉' style=' cursor: pointer; cursor:hand;' onClick='tousu("+BH+");'>"+"</td></tr>";
            	                }
    	            	td="";
        	                  });  
    	            head=head+ content+tail;
    	            	$("#tcontent1").html(head);	                   
    	        }
    	  });
 	 
         });


/***                                首页    **/

     $(".shouye").click(function(){
        	 var result=$("#crimid").attr("value");
        		$.ajax({
        	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&thisPage=0",
        	        type: "post",
        	        async:false,
        	        dataType: "json", 
        	        success: function (data) {
        	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
            	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
            	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
               	     
            	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
            	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
            	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
            	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
            	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>"  +
     	        		"<td style='border:1px solid blue;border-collapse:collapse;font-size:20px;'>操作</td></tr>";
        	        var tail="</table>";
        	        var content="";
        	        var td="";
        	        var BH="";
        	          var order=0;
        	            $.each(data, function(key, val) { 
            	            if(key!='pageopetion'){
                              order=parseInt(key);
                              order=order+1;
                              content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
                	            }
                    	                $.each(val,function(ind,vald){
                                          if(ind=='thisPage'){
                                        	if(vald=='0'){
                                        	    $("#yeshu").text('1');
                                        	    $("#dbyeshu").text('1');
                                        	}else{
                                         		var currentpagenum=parseInt(vald);
                                          		 $("#yeshu").text(currentpagenum+1);
                                           	    $("#dbyeshu").text(currentpagenum+1);
                                            	}
                                              }else if(ind=='pageCount'){
                                            	  $("#pageCount").text(vald);
                                                  }else if(ind=='allCount'){
                                                	  $("#allCount").text(vald);
                                                	  BH=ind;
                                                  }else{
                                                	  //alert(ind);
                                               	   if(ind=='ID'){ BH=vald; }else{
                                                	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                               	        }  
                                               	   }
                    	                });
                	                if(BH=='allCount'){
                	                	  content=content+td+"</tr>";
                		            	  	
                	                }else{
                	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
                	                	 "<input class='ts_fybut'  style='  cursor: pointer;cursor:hand;'  type='button'  value='复议' onClick='fuyi("+BH+");'>"+
                                         "<input class='ts_fybut'  style='  cursor: pointer;cursor:hand;' type='button' value='投诉' onClick='tousu("+BH+");'>"+"</td></tr>";
                	                }
        	            	td="";
            	                  });  
        	            head=head+ content+tail;
        	            	$("#tcontent1").html(head);	                   
        	        }
        	  });
     	 

         });
 /**                   末页                                        **/

     $(".weiye").click(function(){
		 var pageIndex=$("#pageCount").text();
		 var lastpagenum=parseInt(pageIndex)-1;
    	 var result=$("#crimid").attr("value");
    		$.ajax({
    	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&pageIndex="+lastpagenum,
    	        type: "post",
    	        async:false,
    	        dataType: "json", 
    	        success: function (data) {
    	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
        	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
           	     
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>" +
 	        		"<td style='border:1px solid blue;border-collapse:collapse;font-size:20px;'>操作</td></tr>";
    	        var tail="</table>";
    	        var content="";
    	        var td="";
    	          var order=0;
    	          var BH="";
    	            $.each(data, function(key, val) { 
        	            if(key!='pageopetion'){
                          order=parseInt(key);
                          order=order+1;
                          content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
            	            }
                	                $.each(val,function(ind,vald){
                                      if(ind=='thisPage'){
                                    	if(vald=='0'){
                                    	    $("#yeshu").text('1');
                                    	    $("#dbyeshu").text('1');
                                    	}else{

                                    		var currentpagenum=parseInt(vald);
                                   		 $("#yeshu").text(currentpagenum+1);
                                    	    $("#dbyeshu").text(currentpagenum+1);
                                    	
                                        	}
                                          }else if(ind=='pageCount'){
                                        	  $("#pageCount").text(vald);
                                              }else if(ind=='allCount'){
                                            	  $("#allCount").text(vald);
                                            	  BH=ind;
                                              }else{
                                            	  //alert(ind);
                                           	   if(ind=='ID'){ BH=vald; }else{
                                            	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                           	        }  
                                           	   }
                	                });
            	                if(BH=='allCount'){
            	                	  content=content+td+"</tr>";
            		            	  	
            	                }else{
            	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
            	                	 "<input class='ts_fybut'  style='  cursor: pointer;cursor:hand;' type='button'  value='复议' onClick='fuyi("+BH+");'>"+
                                     "<input class='ts_fybut'  style='  cursor: pointer;cursor:hand;' type='button' value='投诉' onClick='tousu("+BH+");'>"+"</td></tr>";
            	
            	                }
    	            	td="";
        	                  });  
    	            head=head+ content+tail;
    	            	$("#tcontent1").html(head);	                   
    	        }
    	  });
 	 

     });
         
     /**                   下一页                                        **/

     $(".xiayy").click(function(){
		 var pageIndex=$("#yeshu").text();
		 var lastpagenum=parseInt(pageIndex);
		 var pagetotal=$("#pageCount").text();
		 var pagetotalnum=parseInt(pagetotal);
			 if(lastpagenum == pagetotalnum){
				 alert("当前为最后一页！");
                     return ;
				 }
    	 var result=$("#crimid").attr("value");
    		$.ajax({
    	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&pageIndex="+lastpagenum,
    	        type: "post",
    	        async:false,
    	        dataType: "json", 
    	        success: function (data) {
    	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
        	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
           	     
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>" +
 	        		"<td style='border:1px solid blue;border-collapse:collapse;font-size:20px;' >操作</td></tr>";
    	        var tail="</table>";
    	        var content="";
    	        var td="";
    	        var BH="";
    	          var order=0;
    	            $.each(data, function(key, val) { 
        	            if(key!='pageopetion'){
                          order=parseInt(key);
                          order=order+1;
                          content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
            	            }
                	                $.each(val,function(ind,vald){
                                      if(ind=='thisPage'){
                                    	if(vald=='0'){
                                    	    $("#yeshu").text('1');
                                    	    $("#dbyeshu").text('1');
                                    	}else{
                                    		var currentpagenum=parseInt(vald);
                                    		 $("#yeshu").text(currentpagenum+1);
                                     	    $("#dbyeshu").text(currentpagenum+1);
                                        	}
                                          }else if(ind=='pageCount'){
                                        	  $("#pageCount").text(vald);
                                              }else if(ind=='allCount'){
                                            	  $("#allCount").text(vald);
                                            	  BH=ind;
                                              }else{
                                            	  //alert(ind);
                                           	   if(ind=='ID'){ BH=vald; }else{
                                            	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                           	        }  
                                           	   }
                	                });
            	                if(BH=='allCount'){
            	                	  content=content+td+"</tr>";
            		            	  	
            	                }else{
            	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
            	                	 "<input class='ts_fybut' type='button'  style='  cursor: pointer;cursor:hand;'  value='复议' onClick='fuyi("+BH+");'>"+
                                     "<input class='ts_fybut' type='button'  style='  cursor: pointer;cursor:hand;' value='投诉' onClick='tousu("+BH+");'>"+"</td></tr>";
            	                }
    	            	td="";
        	                  });  
    	            head=head+ content+tail;
    	            	$("#tcontent1").html(head);	                   
    	        }
    	  });
 	 

     });


/****     上一页      ****/

     $(".shangyy").click(function(){
		 var pageIndex=$("#yeshu").text();
		 var lastpagenum=parseInt(pageIndex);
		 var pagetotal=$("#pageCount").text();
		 var pagetotalnum=parseInt(pagetotal);
		 var  value=lastpagenum-1;
		 
			 if(value<=0){
				alert("当前为首页！");
				return 
			         }else{
							 lastpagenum=lastpagenum-1 ;
							 lastpagenum=lastpagenum-1 ;
							   }
    	 var result=$("#crimid").attr("value");
    		$.ajax({
    	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&pageIndex="+lastpagenum,
    	        type: "post",
    	        async:false,
    	        dataType: "json", 
    	        success: function (data) {
    	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
        	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
           	     
        	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
        	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>"  +
 	        		"<td style='border:1px solid blue;border-collapse:collapse;font-size:20px;' >操作</td></tr>";
    	        var tail="</table>";
    	        var content="";
    	        var td="";
    	        var BH="";
    	          var order=0;
    	            $.each(data, function(key, val) { 
        	            if(key!='pageopetion'){
                          order=parseInt(key);
                          order=order+1;
                          content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
            	            }
                	                $.each(val,function(ind,vald){
                                      if(ind=='thisPage'){
                                    	if(vald=='0'){
                                    	    $("#yeshu").text('1');
                                    	    $("#dbyeshu").text('1');
                                    	}else{
                                    		var currentpagenum=parseInt(vald);
                                    		 $("#yeshu").text(currentpagenum+1);
                                     	    $("#dbyeshu").text(currentpagenum+1);
                                        	}
                                          }else if(ind=='pageCount'){
                                        	  $("#pageCount").text(vald);
                                              }else if(ind=='allCount'){
                                            	  $("#allCount").text(vald);
                                            	  BH=ind;
                                              }else{
                                            	  //alert(ind);
                                           	   if(ind=='ID'){ BH=vald; }else{
                                            	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                           	        }  
                                           	   }
                	                });
            	                if(BH=='allCount'){
            	                	  content=content+td+"</tr>";
            		            	  	
            	                }else{
            	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
            	                	 "<input class='ts_fybut'  style='  cursor: pointer;cursor:hand;' type='button'  value='复议' onClick='fuyi("+BH+");'>"+
                                     "<input class='ts_fybut'  style='  cursor: pointer;cursor:hand;' type='button' value='投诉' onClick='tousu("+BH+");'>"+"</td></tr>";
            	                }
    	            	td="";
        	                  });  
    	            head=head+ content+tail;
    	            	$("#tcontent1").html(head);	                   
    	        }
    	  });
 	 

     });

     
     
 /**      默认显示的奖惩信息列表***/
     
     var result=$("#crimid").attr("value");
		$.ajax({
	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&thisPage=0",
	        type: "post",
	        async:false,
	        dataType: "json", 
	        success: function (data) {
	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
 	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
 	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
    	     
 	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
 	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
 	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
 	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
 	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>" +
 	        		"<td style='border:1px solid blue;border-collapse:collapse;font-size:20px;' >操作</td></tr>";
	        var tail="</table>";
	        var content="";
	        var td="";
	        var BH="";
	          var order=0;
	            $.each(data, function(key, val) { 
 	            if(key!='pageopetion'){
                   order=parseInt(key);
                   order=order+1;
                   content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
     	            }
         	                $.each(val,function(ind,vald){
                               if(ind=='thisPage'){
                             	if(vald=='0'){
                             	    $("#yeshu").text('1');
                             	    $("#dbyeshu").text('1');
                             	}else{
                              		var currentpagenum=parseInt(vald);
                               		 $("#yeshu").text(currentpagenum+1);
                                	    $("#dbyeshu").text(currentpagenum+1);
                                 	}
                                   }else if(ind=='pageCount'){
                                 	  $("#pageCount").text(vald);
                                       }else if(ind=='allCount'){
                                     	  $("#allCount").text(vald);
                                     	  BH=ind;
                                       }else{
                                     	  //alert(ind);
                                    	   if(ind=='ID'){ BH=vald; }else{
                                     	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                    	        }  
                                    	   }
         	                });
     	                if(BH=='allCount'){
     	                	  content=content+td+"</tr>";
     		            	  	
     	                }else{
     	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
    	                	 "<input class='ts_fybut' type='button'   style='  cursor: pointer;cursor:hand;' value='复议' onClick='fuyi("+BH+");'>"+
                             "<input class='ts_fybut' type='button'  style='  cursor: pointer;cursor:hand;' value='投诉' onClick='tousu("+BH+");'>"+"</td></tr>";
     	                }
         	               td="";
 	                  });  
	            head=head+ content+tail;
	            	$("#tcontent1").html(head);	                   
	        }
	  });
     
		
		/** 点击复议页签显示复议的内容**/
 $("#fuyiqingkuang").click(function(){
	 
	 $("#contenttype").attr("value","fuyiqingkuang");
	 
	 alert("复议");
	 
 });    
     
		
		
	/** 点击投诉页签显示投诉的内容**/
 $("#tousuqingkuang").click(function(){
	 
	 $("#contenttype").attr("value","tousuqingkuang");
	 
	 alert("投诉");
	 
 });  	
		
        });
    
    
    /**查询投诉复议、投诉、复议 的内容**/
    function  queryTSFY(){
    	
    	
   	 $("#contenttype").attr("value","tsfy");
   //如果contenttype的值为 tsfy 则点击查询操作按钮 就专门查询  投诉复议的内容  ，如果为fuyiqingkuang 则查询复议内容，如果为tousuqingkuang则查询投诉内容
   	 var contenttype= $("#contenttype").attr("value");
   	 var querycondition=$("#querycondition").attr("value");
   	 if(contenttype=="tsfy"){
	 var result=$("#crimid").attr("value");
		$.ajax({
	        url: "../ywgk/toListReconsiderationAndComplaintPage.action?criminalid="+result+"&thisPage=0&querycondition="+querycondition,
	        type: "post",
	        async:false,
	        dataType: "json", 
	        success: function (data) {
	        var head="<table  style='border:1px solid blue;width:800px;height:350px;border-collapse:collapse;' >"
    	        +"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>"
    	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>序号</td>"
    	        +"<td style='border-collapse:collapse;border:1px solid blue;text-align:center;font-size:20px;'>罪犯编号</td>"
    	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>录入日期</td>"
    	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>扣分</td>"
    	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;'>奖分</td>"
    	        +"<td style='border:1px solid blue;border-collapse:collapse;text-align:center;font-size:20px;width:300px;'>奖惩原因  </td>" +
    	        		"<td  style='font-size:20px;'>操作</td></tr>";
	        var tail="</table>";
	        var content="";
	        var td="";
	    	var BH="";
	          var order=0;
	            $.each(data, function(key, val) { 
    	            if(key!='pageopetion'){
                      order=parseInt(key);
                      order=order+1;
                      content=content+"<tr style='border:1px solid blue;border-collapse:collapse;text-align:center;width:5px;'><td>"+order+"</td>"
        	            }
            	                $.each(val,function(ind,vald){
            	            
                                  if(ind=='thisPage'){
                                	if(vald=='0'){
                                	    $("#yeshu").text('1');
                                	    $("#dbyeshu").text('1');
                                	}else{
                                 		var currentpagenum=parseInt(vald);
                                  		 $("#yeshu").text(currentpagenum+1);
                                   	    $("#dbyeshu").text(currentpagenum+1);
                                    	}
                                      }else if(ind=='pageCount'){
                                    	  $("#pageCount").text(vald);
                                          }else if(ind=='allCount'){
                                        	  $("#allCount").text(vald);
                                        	  BH=ind;
                                          }else{
                                        	  //alert(ind);
                                       	   if(ind=='ID'){ BH=vald; }else{
                                        	  td=td+"<td style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+vald+"</td>";   
                                       	        }  
                                       	   }
            	                });
        	                if(BH=='allCount'){
        	                	  content=content+td+"</tr>";
        		            	  	
        	                }else{
        	                	 content=content+td+"<td  style='border:1px solid blue;border-collapse:collapse;font-size:15px;'>"+
        	                	 "<input class='ts_fybut' type='button'  style='  cursor: pointer;cursor:hand;'  value='复议' onClick='fuyi("+BH+");'>"+
                                 "<input class='ts_fybut' type='button'  style='  cursor: pointer;cursor:hand;' value='投诉' onClick='tousu("+BH+");'>"+"</td></tr>";
        	                }
	            	td="";
    	                  });  
	            head=head+ content+tail;
	            	$("#tcontent1").html(head);	                   
	        }
	  });
    	
   	 }	
    	
    }

    