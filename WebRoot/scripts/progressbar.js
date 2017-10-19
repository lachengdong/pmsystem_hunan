function progressDesc(){
	var func = function myloading(){
		var percent = "";
		var myform = new mini.Form("datagrid1");
  		$.ajax({
      		url: path + "/commutationParole/getPercent.json?1=1",
      		dataType:"text",
      		async:false,
          	success: function (text){
				percent = text;
          	},
          	error: function(){
          		myform.unmask();
          		return;
          	}
      	});
  		//
  		myform.loading(percent);
		if(percent=="100%"){
			clearInterval(progressDesc);
			var timefunc = function() {progressunmask();};
			var timer_timeout = setTimeout(timefunc, 500);
			
      		$.ajax({
          		url: path + "/commutationParole/getPercent.json?1=1&type=remove",
          		dataType:"text",
          		async:false,
              	success: function (text) {
              	},
              	error: function () {
              	}
          	}); 
		}
  	}
	var progressDesc = window.setInterval(func,1000);
}

function progressunmask(){
	var myform = new mini.Form("datagrid1");
	myform.unmask();
}