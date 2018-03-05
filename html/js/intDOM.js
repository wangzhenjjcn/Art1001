function intHeight(height){
	$('.aladdin-content').height(height);
	$('.container-fluid').height(height-185);
	$('.aladdin-content ul:first').height(height);
	$('.aladdin-content .col-xs-10 #html').height(height-245);
	$('.taoxia #html').height(height-300);
}
function setSize(){
    if($('#html').outerWidth()<=0){
    	$('.pager').width("100%")	
    }else{
        $('.pager').width($('#html').outerWidth());
    }
}
$(window).resize(function () {          //当浏览器大小变化时
    //alert($(window).height());          //浏览器时下窗口可视区域高度
    //alert($(document).height());        //浏览器时下窗口文档的高度
    //alert($(document.body).height());   //浏览器时下窗口文档body的高度
    //alert($(document.body).outerHeight(true)); //浏览器时下窗口文档body的总高度 包括border padding margin
	intHeight($(window).height());
	//intHeight($(window).height());
	$('#html').scrollTop(0)
    if($('#html').outerWidth()<=0){
    	$('.pager').width("100%")	
    }else{
        $('.pager').width($('#html').outerWidth());
    }

});

function intContent(){

	var membername= $.cookie(member_name);
	var avator= $.cookie(member_avater);

	if(typeof membername == 'undefined' && typeof avator == 'undefined'){
		$.fn.mypost(userinfo_url,{},getUserToken(),function(response){

			if(response.code=='200'){
				membername=response.name;
				avator=response.avater;
				membername=membername==""?"DIM设计师":membername;
				//avator=avator==""?"images/touxiang.png":avator;
				avator="images/touxiang.png";
				$.cookie(member_name,membername);
				$.cookie(member_avater,avator);
				memberdomcon(avator,membername);
			}else{
				alert(response.msg);
			}
		});
	}else{
		memberdomcon(avator,membername);
	}

}

function memberdomcon(avator,membername){
	var DOMHtml_top =''+
		''+
		'<div class="header">'+
		'<div class="head-img">'+
		'<img style="display:inline-block" src="'+avator+'" alt="touxiang">'+
		'</div>'+
		'<div class="username">'+
		'<h5>'+membername+'</h5>'+
		'</div>'+
		'</div>'+
		'<div class="section">'+
		'<div class="tabs tabs-style-topline">'+
		'<nav>'+
		'<ul>'+
		'<li class="col-xs-4" rel="set"><a href="set_index.html" class="glyphicon glyphicon-home"><span>首页</span></a></li>'+
		'<li class="col-xs-4" rel="index"><a href="index.html" class="glyphicon glyphicon-th-large"><span>阿拉丁</span></a></li>'+
		'<li class="col-xs-4" rel="yun"><a href="pingmiantuku.html" class="glyphicon glyphicon-cloud-download"><span>云库</span></a></li>'+
		'</ul>'+
		'</nav>'+
		'</div>'
	'</div>';

	var DOMHtml_bottom =''+
		'<div class="footer">'+
		'<div class="logowrap">'+
		'<div class="logoimg"><img style="height:30px;" src="images/aladdin-logo.png" alt=""></div>'+
		'<div class="logotext"></div>'+
		'</div>'+
		'</div>';
	$('body').prepend(DOMHtml_top);
	$('body').append(DOMHtml_bottom);

	$(".m_avater1").attr('src',avator);
	$(".m_name1").text(membername);

	$(".m_avater2").attr('src',avator);
	$(".m_name2").text(membername);

	$(".m_avater3").attr('src',avator);
	$(".m_name3").text(membername);

	$(".m_avater4").attr('src',avator);
	$(".m_name4").text(membername);


}




function setDOM(){
	if(location.pathname.indexOf("/index")>=0){
		$('.tabs li[rel="index"]').addClass('tab-current')
	}else if(location.pathname.indexOf("pingmiantuku")>=0||location.pathname.indexOf("taocan.html")>=0||location.pathname.indexOf("moxingye.html")>=0||location.pathname.indexOf("huxingku.html")>=0){
		$('.tabs li[rel="yun"]').addClass('tab-current');
		//if($(window).height()<650){
			//intHeight($(window).height());
			//}else{
			//intHeight($(window).height());
			//}
	}else if(location.pathname.indexOf("set_index.html")>=0){
		$('.tabs li[rel="set"]').addClass('tab-current')
	}
    if($('#html').outerWidth()<=0){
    	$('.pager').width("100%")	
    }else{
        $('.pager').width($('#html').outerWidth());
    }
	$.mCustomScrollbar.defaults.scrollButtons.enable=true; //enable scrolling buttons by default
	$.mCustomScrollbar.defaults.axis="y"; //enable 2 axis scrollbars by default
	$(".aladdin-content .col-xs-10 #html").mCustomScrollbar({theme:"minimal-dark"});
	$(".taoxia #html").mCustomScrollbar({theme:"minimal-dark"});
	$(".container-fluid").mCustomScrollbar({theme:"minimal-dark"});
	IsmCustomScrollbar = mCustomScrollbar;
}
function intDOM(){
	setTimeout("intContent();",00);
	setTimeout("setDOM();",400);
	setTimeout("intHeight($(window).height());",500);
	setTimeout("setSize();",800);	
	setTimeout("$('.container-fluid').show();",200);
}
