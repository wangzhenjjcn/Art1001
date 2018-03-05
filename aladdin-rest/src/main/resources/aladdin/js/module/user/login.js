/**
 * Created by shihan on 2016/9/30.
 */
$(document).ready(function(){
    var remPass = false;
    $(".p1").bind("click",function(){
        if($(this).find("span").hasClass('checked')){
            $(this).find("span").removeClass('checked');
            remPass = false;
        }else{
            $(this).find("span").addClass('checked');
              remPass = true;
        }
    })

    $(".p2").bind("click",function(){
        window.open("http://www.art1001.com/front/forget/index","_blank");
    })
    //focus username field
    $(" #username").focus();
    //checkbox
    // $('.login_box form h5 p').on('click','span',function(){
    //     console.log(this);
    // });
    $("#username").keydown(function(e){
        if(e.keyCode==13|| e.width==13){
            $("#password").focus();
        }
    });

    $("#password").keydown(function(e){
        if(e.keyCode==13|| e.which==13){
            sublogin();
        }
    });


    //login
    $('#submit_btn').on('click',function(){
        sublogin();
    });

    function sublogin(){
        var username = $('#username').val(), password = $('#password').val();
        if(!username) {
            alert('请输入邮箱/手机号');
            $('#username').focus();
            return false;
        }
        if(!password) {
            alert('请输入密码');
            $('#password').focus();
            return false;
        }
        var obj = {"client_id":client_id,
            "client_secret":client_secret,
            "username":username,
            "password":password,
            "grant_type":"password"
        };
        var url = login_url;
        $.fn.mypost(url,obj,"",function(response){
            var data = my_eval(response);
            var access_token = data.access_token;
            if(remPass){
                $.cookie(user_cookie_name, JSON.stringify(data), { expires: 7, path: '/' });
                $.cookie(user_cookie_access_token, access_token, { expires: 7, path: '/' });
            }else{
                $.cookie(user_cookie_name,JSON.stringify(data));
                $.cookie(user_cookie_access_token,access_token);
            }
            //getAvaAndName(access_token);
            skp_login_success(username,password);
            location.href="index.html";
        });
    }


});