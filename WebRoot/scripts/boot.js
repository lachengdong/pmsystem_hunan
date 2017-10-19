__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var bootPATH = __CreateJSPath("boot.js");

//debugger
mini_debugger = true;   
mini_useShims = true;

//miniui

document.write('<script src="' + bootPATH + 'jquery/jquery.min.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'jquery/jquery.cookie.js" type="text/javascript"></sc' + 'ript>');

document.write('<script src="' + bootPATH + 'miniui/miniui.js" type="text/javascript" ></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'miniui/locale/zh_CN.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'form/common_utils.js" type="text/javascript"></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'miniui/themes/icons.css" rel="stylesheet" type="text/css" />');

//skin
var skin = getCookie("miniuiSkin");
if (skin) {
    document.write('<link href="' + bootPATH + 'miniui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');
}


////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}
 function chakanshuoming(e){
        	var url="viewFunction.action?1=1&menuid="+e;
        	mini.open({
        		url:url,
        		type:"post",
        		title:"功能说明",width:900,height:550,
        		suceesss: function (text){
        		},
        		error: function (){
        			
        		}
        	});
        }
 