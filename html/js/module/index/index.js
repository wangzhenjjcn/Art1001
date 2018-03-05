/**
 * Created by shihan on 2016/9/30.
 */
$(document).ready(function(){
    //focus username field
    $(" #username").focus();

    //findpwd
    $('#submit_btn').on('click',function(){
        var username = $('#username').val();
        if(!username) {
            alert('请输入邮箱/手机号');
            $('#username').focus();
            return false;
        }

        var settings = {
            "async": true,
            "crossDomain": true,
            "url": find_pwd_url+"?account="+username,
            "method": "PUT",
            "headers": {
                "cache-control": "no-cache",
                "content-type": "application/x-www-form-urlencoded"
            }
        }

        $.ajax(settings).done(function (response) {
            //console.log(response);
            var code = response.code;
            if(code==200){
                alert("密码重置成功");
                location.href="login.html";
            }else if(code==1001){
                alert("账号不存在");
            }else{
                alert("服务器错误，请稍后重试");
            }
        });

    });

});