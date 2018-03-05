/**
 * Created by shihan on 2016/9/30.
 */



//userinfo
if(!$.cookie(user_cookie_access_token)){
    location.href = html_server_url+"/login.html";
}else{
    if(!$.cookie(user_info_cookie_name)){
        var obj = {};
        var url = userinfo_url;
        $.fn.mypost(url,obj,getUserToken(),function(response){
            response=my_eval(response);
            if(response.code==200){

                var membername=response.name;
                var avator=response.avater;
                membername=membername==""?"DIM设计师":membername;
                avator=avator==""?"images/touxiang.png":avator;

                $.cookie(member_name,membername);
                $.cookie(member_avater,avator);

                var user = $.cookie(user_cookie_name);
                user = my_eval(user);
                ART.Sketchup.callback("Window.ready",user);
                $.cookie(user_info_cookie_name,response);
            }else{
                //alert('�ϴ�ʧ��');
            }
        });
    }
}

function getUserToken(){
    return $.cookie(user_cookie_access_token);
}

