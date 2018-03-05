/**
 * Created by shihan on 2016/9/30.
 */
var server_url = "http://127.0.0.1";
var html_server_url = "http://127.0.0.1";
var goods_pic_url = "http://127.0.0.1/";

var html_house_url = html_server_url+"/housingType_manager.html";
var html_upload_house_url = html_server_url+"/update_houseType.html";
var html_edit_house_url = html_server_url+"/housingType_edit.html";
var html_model_url = html_server_url+"/modelType_manager.html";
var html_upload_my_model_url = html_server_url+"/update_model.html";
var html_upload_single_model_url = html_server_url+"/update_danModel.html";
var html_upload_package_url = html_server_url+"/chuanjiantaocan.html";
var html_show_component_url = html_server_url+"/zujian.html";
var html_show_catalog_url = html_server_url+"/xinglu.html";
var html_show_series_url = html_server_url+"/tongy_rule.html";

var client_id = "123456";
var client_secret = "abcdef";

var user_cookie_name = "user_cookie_name";
var user_cookie_access_token = "access_token_cookie_name";
var user_info_cookie_name = "user_info_cookie_name";

var user_update_pwd=server_url+"/api/v1/users/pwd";//修改密码

var login_url = server_url +"/api/v1/oauth/token";
var find_pwd_url = server_url + "/api/v1/users/findpwd";
var userinfo_url = server_url + "/api/v1/users/me";
var userinfo_submit_url = server_url + "/api/v1/users";
var houseType_manager_url=server_url + "/api/housetypes/search";
var houseType_getTotlePage_url = server_url + "/api/housetypes/getTotlePage";
var houseType_getCountInfo_url = server_url + "/api/housetypes/getCountInfo";
var houseType_del_url = server_url+"/api/housetypes/del";
var houseType_add_url = server_url +"/api/housetypes/addHouse";
var houseType_edit_url= server_url +"/api/housetypes/edit/";

var upload_file_url=server_url+"/api/v1/upload";

var upload_file_house_url = server_url + "/api/v1/upload_house";

var brand_list_url = server_url + "/api/v1/brands/list";
var category_list_url = server_url + "/api/v1/categorys/list";
var category_info_url=server_url+"/api/v1/catalogs/info";//型录详情

var goods_list_url = server_url + "/api/v1/goods/list";
var model_del_url=server_url + "/api/models/del";
var ty_ruleList_url=server_url + "/api/v1/series";
var ty_rule_del_url = server_url +"/api/v1/series/del";
var ty_rule_get_url = server_url +"/api/v1/series/info";
var parts_list_url = server_url + "/api/v1/parts/list";
var packages_my_url = server_url + "/api/v1/packages/my";
var packages_all_url = server_url + "/api/v1/packages";
var packages_get_url = server_url + "/api/v1/packages/info";
var models_search_url = server_url +"/api/models/search";
var models_get_url = server_url +"/api/models/get";
var models_totalPage_url = server_url + "/api/models/totalPage";
var models_upload_url  = server_url + "/api/models/single/upload";
var models_tree_url = server_url + "/api/models/tree";

var packages_save_url = server_url + "/api/v1/packages/save";

var catalogs_list_url = server_url + "/api/v1/catalogs";
var catalogs_tree_url = server_url + "/api/v1/catalogs/tree";
var catalogs_del_url = server_url + "/api/v1/catalogs/del";
var catalogs_get_url = server_url + "/api/v1/catalogs/datas";
var catalogs_add_url = server_url + "/api/v1/catalogs/save";//添加型录
var catalogs_edit_url=server_url + "/api/v1/catalogs/update";//修改型录

var components_list_url = server_url + "/api/v1/components";
var components_tree_url = server_url + "/api/v1/components/tree";
var components_list_url_del = server_url + "/api/v1/components/del";
var components_list_url_save = server_url +"/api/v1/components/save";
var components_list_url_update = server_url +"/api/v1/components/update";
var components_list_url_info = server_url + "/api/v1/components/info";
var components_get_url = server_url + "/api/v1/components/datas";


var project_report_create_url = server_url + "/api/vi/project_report/save";

var rander_upload_url = server_url + "/api/render/upload";

var member_name="member_name";//会员名称
var member_avater="member_avater";//会员头像


(function($){
    $.fn.extend({
        mypost:function(url,obj,access_token,_func){  //access_token 可以为空

            var encode_url ="";
            if(access_token!=null && access_token!=""){
                encode_url = url+"?access_token="+access_token;
            }else{
                encode_url = url;
            }
            var settings = {
                "url": encode_url,
                "method": "POST",
                "error": function(XMLHttpRequest, textStatus, errorThrown){
                    if(XMLHttpRequest.status==401){
                        alert("您的登陆信息已失效，请重新登陆");
                        $.removeCookie(user_cookie_access_token);
                        $.removeCookie(user_info_cookie_name);
                        $.removeCookie(user_cookie_name);
                        location.reload();
                    }else if(XMLHttpRequest.status==400){
                        alert("用户名密码错误");
                        
                    }
                },
                "data":obj
            }
            $.ajax(settings).done(function (response) {
                _func(response);
            });
        },
        mypostSync:function(url,obj,access_token,_func){  //access_token 可以为空

            var encode_url ="";
            if(access_token!=null && access_token!=""){
                encode_url = url+"?access_token="+access_token;
            }else{
                encode_url = url;
            }
            var settings = {
                "url": encode_url,
                "async":false,
                "method": "POST",
                "error": function(XMLHttpRequest, textStatus, errorThrown){
                    if(XMLHttpRequest.status==401){
                        alert("您的登陆信息已失效，请重新登陆");
                        $.removeCookie(user_cookie_access_token);
                        $.removeCookie(user_info_cookie_name);
                        $.removeCookie(user_cookie_name);
                        location.reload();
                    }
                },
                "data":obj
            }
            $.ajax(settings).done(function (response) {
                _func(response);
            });
        },
        singleupload:function(url,id,access_token,func){
            var encode_url ="";
            if(access_token!=null && access_token!=""){
                encode_url = url+"?access_token="+access_token;
            }else{
                encode_url = url;
            }
            $.ajaxFileUpload({
                url:encode_url,
                secureuri:false,                       //是否启用安全提交,默认为false
                fileElementId:id,           //文件选择框的id属性
                dataType : 'json',
                //fileSize:5120000,
                //allowType:'jpg,jpeg,png,JPG,JPEG,PNG,gif,GIF',                     //服务器返回的格式,可以是json或xml等
                success:function(data, status){        //服务器响应成功时的处理函数
                    func(data);
                }
            })
        },
        myupload:function(url,form,access_token,_func){  //access_token 可以为空

            var encode_url ="";
            if(access_token!=null && access_token!=""){
                encode_url = url+"?access_token="+access_token;
            }else{
                encode_url = url;
            }
            var settings = {
                "url": encode_url,
                "method": "POST",
                "error": function(XMLHttpRequest, textStatus, errorThrown){
                    if(XMLHttpRequest.status==401){
                        alert("您的登陆信息已失效，请重新登陆");
                        $.removeCookie(user_cookie_access_token);
                        $.removeCookie(user_info_cookie_name);
                        $.removeCookie(user_cookie_name);
                        location.reload();
                    }
                },
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form
            }
            $.ajax(settings).done(function (response) {
                _func(response);
            });
        }
    });
})(jQuery);


function my_eval(response){
    if(typeof(response) == "object" && Object.prototype.toString.call(response).toLowerCase() == "[object object]" && !response.length){
        return response;
    }else{
        return eval('(' + response + ')');
    }
}
