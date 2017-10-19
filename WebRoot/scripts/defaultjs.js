function approvepersonDefault(){
	var approvepersonObj = mini.get('approveperson');
	if(approvepersonObj){
		approvepersonObj.select(0);
	}
}

approvepersonDefault();

function nextNodeidDefault(){
	var nextnodeidObj = mini.get('nextnodeid');
	if(nextnodeidObj){
		nextnodeidObj.select(0);
	}
}
nextNodeidDefault();
