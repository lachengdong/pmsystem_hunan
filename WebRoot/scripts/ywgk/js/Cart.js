//购物车
var Cart = function () {
    this.Count = 0;
    this.Total = 0;
    this.Items = new Array();
};
//购物车集合对象
var CartItem = function () {
    this.Id = 0;
    this.Name = "";
    this.Count = 0;
    this.Price = 0;
    this.Total = 0;
    this.Stock =0;
};
 
//购物车操作
var CartHelper = function () {
    this.cookieName = "gkzxCart";
    this.Clear = function () {
        var cart = new Cart();
        this.Save(cart);
        return cart;
    };
    //向购物车添加
    this.Add = function (id, name, count, price,stock) {
        var cart = this.Read();
        var index = this.Find(id);          
        //如果ID已存在，覆盖数量
        if (index > -1) { 
        	if(parseInt(stock)>cart.Items[index].Count){
        		cart.Items[index].Count = parseInt(cart.Items[index].Count)+count;
                cart.Total = toFixed2(cart.Total,toFixed(cart.Items[index].Price,count),"+");//商品总价
        		cart.Items[index].Total = toFixed(cart.Items[index].Price,cart.Items[index].Count);
        	}               
            /*
			alert("商品："+cart.Items[index].Name+" 数量："+(cart.Items[index].Count)+" 单价："+cart.Items[index].Price+
            " 总价："+cart.Items[index].Total+"合计："+cart.Total);
            */
        } else {        	
        	index = cart.Items.length;
            var item = new CartItem();
            item.Id = id;
            item.Name = name;
            item.Count = count;
            item.Price = price;
            item.Stock =stock;
			item.Total = toFixed(item.Price,item.Count);//商品合计
            cart.Items.push(item);
            cart.Count++;
            //cart.Total += toFixed(cart.Items[index].Price,cart.Items[index].Count,2);//商品总价
            cart.Total = toFixed2(cart.Total,toFixed(cart.Items[index].Price,cart.Items[index].Count),"+");//商品总价
        }
        this.Save(cart);
        return cart;
    };
    //改变数量
    this.Change = function (id, count) {
        var cart = this.Read();
        var index = this.Find(id);
        if(index >-1){
        	var _count = cart.Items[index].Count-1;
        	if(_count && parseInt(_count)<1)  _count = 0;
        	cart.Items[index].Count = parseInt(_count);
        	cart.Items[index].Total = toFixed(cart.Items[index].Price,parseInt(_count));//商品合计;
        	cart.Total = toFixed2(cart.Total,toFixed(cart.Items[index].Price,1),"-");
        }
        this.Save(cart);
        return cart;
    };
    //移出购物车
    this.Del = function (id) {
        var cart = this.Read();
        var index = this.Find(id);
        if (index > -1) {
            var item = cart.Items[index];
            cart.Count--;
            //cart.Total = cart.Total - (((item.Count * 100) * (item.Price * 100)) / 10000);
            cart.Total = toFixed(item.Price,item.Count);
            cart.Items.splice(index, 1);
            this.Save(cart);
        }
        return cart;
    };
    //根据ID查找
    this.Find = function (id) {
        var cart = this.Read();
        var index = -1;
        for (var i = 0; i < cart.Items.length; i++) {
            if (cart.Items[i].Id == id) {
                index = i;
            }
        }
        return index;
    };
    //COOKIE操作
    this.Save = function (cart) {
        var source = "";
        for (var i = 0; i < cart.Items.length; i++) {
            if (source != "") { source += "|$|"; }
            source += this.ItemToString(cart.Items[i]);
        }
        $.cookie(this.cookieName, source,{expires: 7, path: path});
    };
    this.Read = function () {
        //读取COOKIE中的集合
        var source = $.cookie(this.cookieName);
        var cart = new Cart();
        if (source == null || source == "") {
            return cart;
        }
        var arr = source.split("|$|");
        cart.Count = arr.length;
        for (var i = 0; i < arr.length; i++) {
            var item = this.ItemToObject(arr[i]);
            cart.Items.push(item);
            cart.Total += (((item.Count * 100) * (item.Price * 100)) / 10000);
        }
        return cart;
    };
    this.ItemToString = function (item) {
        return item.Id + "||" + escape(item.Name) + "||" + item.Count + "||" + item.Price+ "||" + item.Total+"||"+item.Stock;
    };
    this.ItemToObject = function (str) {
        var arr = str.split('||');
        var item = new CartItem();
        item.Id = arr[0];
        item.Name = unescape(arr[1]);
        item.Count = arr[2];
        item.Price = arr[3];
        item.Total = arr[4];
        item.Stock = arr[5];
        return item;
    };
}; 

////
//新增物品到购物车   
function addCart(id, name, count, price,stock){
	var Carts = new CartHelper().Add(id, name, count, price,stock);
    listCarts(Carts);
}
//改变购物数量
function changeCart(id, count){
	var flag = false;
	var Carts = new CartHelper().Change(id,count);
	for (var i = 0; i < Carts.Items.length; i++) {
		if(Carts.Items[i].Count == 0){
			Carts = new CartHelper().Del(id);
		}
		listCarts(Carts);
	}
}

//移除购物车
function delCart(id){
	var Carts = new CartHelper().Del(id);
	listCarts(Carts);
}
//列表显示遍历
function listCarts(Carts){
	var divcontent = divcontent = '<div  class="overfscr1"><table class="table1" width="100%" cellpadding="0" cellspacing="0" id="tab"><tr bgcolor="#ecf6fa"><td width="20%">商品名称</td>'+
	'<td width="12%">单价(元)</td><td width="25%">数量</td><td width="15%">金额(元)</td><td width="12%">操作</td></tr>';
	if(Carts.Items.length>0){
		for (var i = 0; i < Carts.Items.length; i++) {
		divcontent +='<tr  align="center"><td>'+Carts.Items[i].Name+'</td><td>'+Carts.Items[i].Price+
		'</td><td><a class="shuliang1" href="#" style="color:#000;" onclick="changeCart(\''+Carts.Items[i].Id+'\', 1);">-</a><span class="shuliang2">'+Carts.Items[i].Count+
		'</span><a class="shuliang1" href="#" style="color:#000;" onclick="addCart(\''+Carts.Items[i].Id+'\',\''+Carts.Items[i].Name+'\',1,\''+Carts.Items[i].Price+'\',\''+Carts.Items[i].Stock+'\');" >+</a></td><td>'+Carts.Items[i].Total+'</td><td width="10%" style="cursor:pointer;" onclick="delCart(\''+Carts.Items[i].Id+'\', 1);"><a style="color:#000;" class="removes" href="#">移除</a></td>';
        //alert(" 商品名称："+Carts.Items[i].Name+" 商品数量："+Carts.Items[i].Count+" 商品单价："+Carts.Items[i].Price+"商品合计："+Carts.Items[i].Total+"总计："+Carts.Total);
    }
	divcontent +='</tr>';
	divcontent =divcontent+"</table></div>"+'<table width="100%" cellpadding="0" cellspacing="0" border="0" ><tr height="45" align="right" style="font-size:25px;"><td width="85%" align="right">总计：</td><td align="left">'+Carts.Total.toString().substring(0,Carts.Total.toString().indexOf(".")+3)+'元</td></tr></table>'+
	'<button class="jiesuan" id="btnshow" onclick="validateUser(),showOrHide(true)">去结算</button>';
	$('div:#divcontent').html(divcontent);
	$('#goods').val(JSON.stringify(Carts.Items));
	}else{
		divcontent = '<div class="wygw_gwc"></div>';
		$('div:#divcontent').html(divcontent);
	}
	/*divcontent = '<div class="wygw_gwc"><img src="/pmsys/images/ywgk/images/gwcwk.jpg" width="632" height="185" /></div>';
	$('div:#divcontent').html(divcontent);*/

	/*json对象 Carts.Items 
               转换后的字符串  JSON.stringify(Carts.Items) 
               格式 [{"Id":"value1","Name":"value2","Count":"value3","Price":"value4","Total":"value5"},{......}]
    */
    //alert(JSON.stringify(Carts.Items));
}

function cookieCart(){
	var Carts = new CartHelper().Read();
	listCarts(Carts);
}
function toJieSuan(){
	//$('#crimid').val("6120003301");
	var queryForm = document.getElementById("queryForm");
	queryForm.submit();
}
//float 乘法
function toFixed(price,count){
	var exponent = 2;
	return count*parseInt(price * Math.pow(10,exponent) + 0.5 )/Math.pow(10,exponent);
}
//float 加减
function toFixed2(num1,num2,type){
	var val = 0;
	var exponent = 3;
	var num1 = parseInt(num1 * Math.pow(10,exponent) + 0.5);
	var num2 = parseInt(num2 * Math.pow(10,exponent) + 0.5);
	
	if(type.indexOf('+') > -1) val = parseInt(num1+num2)/Math.pow(10,exponent);
	if(type.indexOf('-') >-1) val = parseInt(num1-num2)/Math.pow(10,exponent);
	return val;
}

