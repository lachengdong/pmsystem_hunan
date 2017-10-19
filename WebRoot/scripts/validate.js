
/* 是否英文 */
  function isEnglish(v) 
  {
       var re = new RegExp("^[a-zA-Z\_]+$");
       if (re.test(v)) return true;
       return false;
   }

   /* 是否英文+数字 */
   function isEnglishAndNumber(v) 
   {
       var re = new RegExp("^[0-9a-zA-Z\_]+$");
       if (re.test(v)) return true;
       return false;
   }

   /* 是否汉字 */
   function isChinese(v) 
   {
       var re = new RegExp("^[\u4e00-\u9fa5]+$");
       if (re.test(v)) return true;
       return false;
   }
   
   ////////////////////////////////////////
   function onEnglishValidation(e) 
   {
       if (e.isValid) {
           if (isEnglish(e.value) == false) {
               e.errorText = "必须输入英文";
               e.isValid = false;
           }
       }
   }
   function onEnglishAndNumberValidation(e) 
   {
       if (e.isValid) {
           if (isEnglishAndNumber(e.value) == false) {
               e.errorText = "必须输入英文+数字";
               e.isValid = false;
           }
       }
   }
   function onChineseValidation(e) 
   {
       if (e.isValid) {
           if (isChinese(e.value) == false) {
               e.errorText = "必须输入中文";
               e.isValid = false;
           }
       }
   }
   function onIDCardsValidation(e) 
   {
       if (e.isValid) {
           var pattern = /\d*/;
           if (e.value.length < 15 || e.value.length > 18 || pattern.test(e.value) == false) {
               e.errorText = "必须输入15~18位数字";
               e.isValid = false;
           }
       }
   }
 //是否手机号码
   function onMobileValidation(e) 
   {
	   if (e.isValid) {
		   var pattern = /^1[3458]\d{9}$/;
		   if (pattern.test(e.value) == false && e.value.length>0) {
			   e.errorText = "必须输入正确的手机号码";
			   e.isValid = false;
		   }
	   }
   }
   //是否家庭电话、传真号
   function onHomeTelePhoneValidation(e) 
   {
	   if (e.isValid) {
		   var pattern=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
		   if ((pattern.test(e.value) == false) && e.value.length>0) {
			   e.errorText = "必须输入正确的电话号码";
			   e.isValid = false;
		   }
	   }
   }
   //是否QQ号码
   function onQqValidation(e) 
   {
	   if (e.isValid) {
		   var pattern= /^[1-9]*[1-9][0-9]*$/;
		   if ((pattern.test(e.value) == false) && e.value.length>0) {
			   e.errorText = "必须输入正确的QQ号码";
			   e.isValid = false;
		   }
	   }
   }
   /*单位名称长度
   function islength(e){
	   if(e.isValid){
		   if(e!=null&&e.value.length>20){
			   e.errorText="长度不能超过20个字！";
			   e.isValid=false;
		   }
	   }
   }
   单位地址长度
   function islength2(e){
	   if(e.isValid){
		   if(e!=null&&e.value.length>40){
			   e.errorText="长度不能超过40个字！";
			   e.isValid=false;
		   }
	   }
   }*/
   //是否特殊符号
   function onSpecialChar(e){
 	   if (e.isValid) {
 		   var pattern= /^[A-Za-z0-9_]{1,16}/;
 		   if ((pattern.test(e.value) == false) && e.value.length>0&&isChinese(e.value) == false) {
 			   e.errorText = "不能输入特殊符号";
 			   e.isValid = false;
 		   }
 	   }
    } 
 