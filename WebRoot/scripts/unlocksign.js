    //点击批量退回的时候调用 openAip方法 做删除印章操作
	function batchSCSign(aipObj){
		var tempid = mini.get("tempid").getValue();
		alert(tempid);
		//openAip(tempid);//查询 本级别对应节点
		_DeleteSeal(aipObj);//删除表单上印章
	}
	
	
	
	//锁定节点其实和那个章没关系 和 权限有关系
	function lockSignNode(type,_protectnode){
		//alert(type+"||"+_protectnode);
    	var aipObj = document.getElementById("HWPostil1");
    	var signnode = aipObj.GetValueEx("signsuggest",2,"",0,"");
    	//if(!signnode){
    	//	return;
    	//}
		var signnodes = signnode.split("@");
    	//alert(signnodes);
		//判断 如果protectnode=1那么是单个签章，否者是批量签章操作
		if(_protectnode != "1")protectnode=_protectnode;
    	//锁定：lock，解锁：unlock
    	if (type == "lock") {
    		var signNum = 1;
        	//计算章的个数（每个级别都是从1开始）
        	if(signnode.indexOf(protectnode) != -1){
        		for (var m = 0; m < signnodes.length; m++) {
    				if(signnodes[m].indexOf(protectnode) != -1){
    					 var _nodes = signnodes[m].split("*");
    					 signNum = parseInt(_nodes[1])+1;
    				}
    			}
        	}
    		var datatype = protectnode+"*"+signNum;//组合内容
    		//alert(datatype+"走到这里了吗");
    		//判断当前节点是否已经存在，存在更新、否则新增
    		if(signnode.indexOf(protectnode) == -1){
    			datatype = datatype+"@";
    			aipObj.setValue("signsuggest",datatype);
    		}else{
    			for (var i = 0; i < signnodes.length; i++) {
    				if(signnodes[i].indexOf(protectnode) != -1){
    					//因为此处是替换，所以需要置空，否则将会累加
    					aipObj.setValue("signsuggest","");
    					//把整个节点以及对应的章的个数替换掉
    					aipObj.setValue("signsuggest",signnode.replace(signnodes[i],datatype));
    				}
    			}
    		}
            //签章 -->每签一次就需要锁定
    		locknode(protectnode,aipObj,type);
		}
    	if (type == "unlock") {
			//循环得到所有的控制节点
			for(var l = 0;l < signnodes.length; l++){
				var _node = signnodes[l];
				//alert(_node);
				//找到本级节点及对应章个数
				if(_node.indexOf(protectnode) != -1){
					var _nodes = _node.split("*");
					var c_node = parseInt(_nodes[1]);
					
					aipObj.setValue("signsuggest","");
					//等于1 说明删除的是最后一个章,把本级置空
					if(c_node == 1){
						aipObj.setValue("signsuggest",signnode.replace(_node+"@",""));
						//档删除只剩下一个章的时候 把节点置为可以编辑
						locknode(protectnode,aipObj,type);
					}else{
						aipObj.setValue("signsuggest",signnode.replace(_node,protectnode+"*"+(c_node-1)));
					}
				}
			}
		}
		//解除锁定方法
		//1、签章之前获取页面上一共有多少个章，然后放到一个系统变量中
		
	}
	
	//调用锁定印章的方法
	function locknode(datatype,aipObj,type){
		var nodes = datatype.split(",");
		//IF判断加在循环外面应该比较好
		for (var y = 0; y < nodes.length; y++) {
			//锁定
			if(type == "lock"){
				aipObj.SetValue(nodes[y],":PROP::LABEL:3");
				aipObj.SetValue(nodes[y],":PROP:READONLY:3");
			}
			//解锁
			if(type == "unlock"){
				aipObj.SetValue(nodes[y],":PROP::LABEL:0");
				aipObj.SetValue(nodes[y],":PROP:READONLY:0");
			}
		}
	}