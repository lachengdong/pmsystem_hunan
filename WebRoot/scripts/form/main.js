//初始化map_，给map_对象增加方法，使map_像个Map   
function Map(){
    var map_=new Object();   
    //属性加个特殊字符，以区别方法名，统一加下划线_   
    map_.put=function(key,value){    map_[key]=value;}    
    map_.get=function(key){    return map_[key];}   
    map_.remove=function(key){    delete map_[key];}       
    map_.keyset=function(){   
        var ret="";   
        for(var p in map_){       
            if(typeof p =='string' && p.substring(p.length-1)=="_"){    
                ret+=",";   
                ret+=p;   
            }   
        }              
        if(ret==""){   
            return ret.split(","); //empty array   
        }else{   
            return ret.substring(1).split(",");    
        }   
    }      
    return map_;   
} 
