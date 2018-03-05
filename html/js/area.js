var $accessToken=$.cookie(user_cookie_access_token);
var zhixiashi = ["北京市","天津市","上海市","重庆市"];
var cityTpl = $('#cityoption').html();
$(function(){
	 $("[sid=area]").on('change',function(e){
     	
     	var dom = $(e.target),
     		clevel = dom.attr("id"),
     		lable = dom.find("option:selected").text(),
     		code = dom.val();
     	
     	if(clevel=="sheng"){
     	    $("#shi").empty();
     		$("#qu").empty(); 
     		//剔除直辖市特殊情况
			var i = 0;
			var flag = 1;
			for(i = 0;i<zhixiashi.length;i++){
				if (zhixiashi[i]==lable){
					flag = 0;
				}
			}
     		if(!flag){
                var cityTpl = $('#cityoption').html();
     			var tpl_html = juicer(cityTpl, {data:[{code:code,name:lable}]});//模板赋值
                 $("#shi").empty();
                 $("#shi").html(tpl_html);
     		 	loadCitys(code,"qu");
     		}else{
     			//加载市一级，通过ajax回调加载区一级
     		    loadCitys(code,"shi",function(){
     		    	loadCitys($('#shi').val(),"qu");
     		    });
     		}
     	}
     	if(clevel=="shi"){
     		loadCitys(code,"qu");
     	}
     });
	 //初始化下拉数据
     loadCitys(0,"sheng",function(){
    	 var lable = $("#sheng").find("option:selected").text(),
    	     code  = $("#sheng").val();
		 var i = 0;
		 var flag = 1;
		 for(i = 0;i<zhixiashi.length;i++){
			 if (zhixiashi[i]==lable){
				 flag = 0;
			 }
		 }
    	 if(flag == 0){
    		$("#shi").empty().append("<option value="+code+">"+lable+"</option>"); 
    	  	loadCitys(code,"qu");
    	 }else{
    	    loadCitys(code,"shi",function(){
    	    	var code  = $("#sheng").val();
    	    	loadCitys(code,"qu");
    	    });
    	 }
     });
});

function loadCitys(id,level,callback){
	var obj = {};

	var url = server_url+"/api/v1/city/list/"+id;

	$.fn.mypost(url,obj,getUserToken(),function(response){
		//console.log(response);
		response = my_eval(response);
		var tpl = document.getElementById('cityoption').innerHTML;
		var html = juicer(tpl, response);
		$("#"+level).empty();
		$("#"+level).html(html);

		if(typeof callback === "function"){
			callback();
		}
	});

}