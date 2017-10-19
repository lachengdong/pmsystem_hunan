/*加载控件*/
function loadSdrdCard() {
	var s = "";
	s += '<OBJECT id=rd codeBase="./download/comRD800.cab" WIDTH="0" HEIGHT="0" classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>';
	document.getElementById("sdrdCardId").innerHTML = s;
}

/*初始化 寻卡*/
/*初始化 寻卡*/
function initCarid(){
	var st,lSnr;
	st = rd.dc_init(100, 115200);//初始化串口
	if(st <= 0){
		return;
	}
	rd.dc_config_card(65);
	st = rd.dc_card(0, lSnr);
	if(st != 0){
		rd.dc_exit();
		return;
	}
	return st
}

/**
 * _SecNr 扇区
 * _Nkey 密码
 * _Adr M1卡块地址
 * */
function m1CardTest(cardparam,st)
{
	var msg= "";
	var returnval = 0;
	var _SecNr= 0;
	var _Nkey= "FFFFFFFFFFFF";
	var _Adr = 2;
	if(cardparam && cardparam.length>2){
		var params = cardparam.split("@");
		_SecNr = params[0];
		_Nkey = params[1];
		_Adr = params[2];
	}
	rd.put_bstrSBuffer_asc = _Nkey; 
	st = rd.dc_load_key(0, _SecNr);
	if(st != 0) 
	{
	    msg+=("密码验证错误!");
	    rd.dc_exit();
	    showMsg(msg);
	    return;
	}
	
	st = rd.dc_authentication(0, _SecNr);
	if(st != 0) 
	{
	    msg+=("A套密码验证错误!");
	    rd.dc_exit();
	    showMsg(msg);
	    return;
	}

	st = rd.dc_read(_Adr);
	if(st != 0) 
	{
	    msg+=("卡信息读取失败!");
	    rd.dc_exit();
	    showMsg(msg);
	    return;
	}
	
	//关闭端口
	st = rd.dc_exit();
	if(st != 0) 
	{
	    msg+=("关闭端口失败!");
	    showMsg(msg);
	    return;
	}
	msg+=("关闭端口成功!</br>");
	showMsg(msg);
	//var cardId= rd.get_bstrRBuffer;
	var cardId;
	if(cardparam && cardparam.length>2){
		cardId= (rd.get_bstrRBuffer_asc).substr(2, 13);
	}
	if(cardId && cardId.length>0){
		returnval = cardId;
	}
	return returnval;
}
function showMsg(msg)
{
	//测试用 先注释掉
	//showMessage(msg);  
}