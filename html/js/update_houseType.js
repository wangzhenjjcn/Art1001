
$(function(){
    //提交信息
    $('.finish_btn').on('click',function(){
        subinfo();
    });
    //绑定上传事件
    $('.upPic').on('change',function(){
        uploadpic();
    });
    //取消编辑事件
    $('.away_btn').on('click',function(){
        skp_window_close();
    });


});

//上传文件
function uploadpic(){

    //var form = new FormData();
    //form.append("files",$('#uploadfile')[0].files[0]);


    var url = upload_file_url;
    //$.fn.myupload(url,form,getUserToken(),function(response){
    //    //console.log(response);
    //    response = my_eval(response);
    //    if(response.code==200){
    //        var url=response.url;
    //        $('#uploadUri').val(url);
    //        $("#up_pic").attr('src',url);
    //    }else{
    //        alert('上传失败');
    //    }
    //});

    $.fn.singleupload(url,'uploadfile',getUserToken(),function(response){
        response = my_eval(response);
        if(response.code==200){
            var url=response.url;
            $('#uploadUri').val(url);
            $("#up_pic").attr('src',url);
        }else{
            alert('上传失败');
        }
    });


}

/*添加户型数据*/
function subinfo(){
    //验证
    if(verifyinfo()==false){
        return false;
    }
    skp_upload_houseType_file();
}

//scheth-up回调
function upload_callback(response){

    var attachmenturi = "";
    var attachmenturi_png = "";
    if(response){
        var data = eval('(' + response + ')');
        var code = data.code;
        if(code==200){
            if(data.skp!=""){
                attachmenturi = data.skp;
            }
            if(data.png!=""){
                attachmenturi_png = data.png;
            }

        }
    }

    var communityName=$('#housename').val();//楼盘名称
    var name=$('#name').val();//户型名称
    var area=$('#area').val();//面积
    var rooms=$('#rooms option:selected').val();//房型
    var uploadUri=$('#uploadUri').val();//用户上传的图片

    //区域
    var sheng=$('#sheng').find('option:selected').val();
    var shi=$('#shi').find('option:selected').val();
    var qu=$('#qu').find('option:selected').val();
    var shengtext=$('#sheng').find('option:selected').text();
    var shitext=$('#shi').find('option:selected').text();
    var qutext=$('#qu').find('option:selected').text();
    var locationId=sheng+','+shi+','+qu;
    var location=shengtext+shitext+qutext;


    var data={
        "communityName":communityName,
        "name":name,
        "houseTypeId":rooms,
        "attachmenturi":attachmenturi,
        "area":area,
//        "locationId":locationId,
        "sheng":sheng,
        "shi":shi,
        "qu":qu,
        "housePicUri":attachmenturi_png,
        "uploadUri":uploadUri,
        "location":location
    };

    var obj = data;
    var url = houseType_add_url;
    $.fn.mypost(url,obj,getUserToken(),function(response){
        //console.log(response);
        response = my_eval(response);
        if(response.code==200) {
            alert("上传成功!");
            setTimeout(function () {
                ART.Sketchup.callback("Window.close");
            }, 2000);
        }else
        {
            alert("服务器错误，请稍后重试");
        }
    });

}

function verifyinfo(){
    var communityName=$('#housename').val();//楼盘名称
    var name=$('#name').val();//户型名称
    var area=$('#area').val();//面积
    var rooms=$('#rooms').val();//房型
    //var uploadUri=$('#uploadUri').val();//用户上传的图片

    //区域
    var sheng=$('#sheng').val();
    var shi=$('#shi').val();
    var qu=$('#qu').val();

    if(communityName==''){
        alert('楼盘名称不能为空');
        return false;
    }
    if(name==''){
        alert('户型名称不能为空');
        return false;
    }
    if(area==''){
        alert('面积不能为空');
        return false;
    }
    var reg=/^([1-9]\d*|0)(.\d+)?$/;
    if(!(reg.test(area))){
        alert('面积只能是整形或浮点型');
        return false;
    }
    if(rooms==''){
        alert('请选择房型');
        return false;
    }
    if(sheng==''||shi==''|qu==''){
        alert('请选择户型地址');
        return false;
    }
    return true;

}