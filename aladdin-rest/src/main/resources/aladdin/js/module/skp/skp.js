/**
 * Created by shihan on 2016/10/11.
 */


function getWindow(width,height){
    return "?width="+width+"&height="+height+"&resizable=true";
}

/**
 * 登录成功
 * @param username
 * @param password
 */
function skp_login_success(username,password){
    var data = {"username":username,"password":password};
    ART.Sketchup.callback("Tools.login",data);
}

/**
 * 关闭窗口
 */
function skp_window_close(){
    ART.Sketchup.callback("Window.close");
}

/**
 * 户型管理
 */
function skp_my_house_html(){
    var data = {"url":html_house_url+getWindow(886,543)};
    ART.Sketchup.callback("Tools.my_floor_plans",data);
}

/**
 * 模型管理
 */
function skp_my_model_html(){
    var data = {"url":html_model_url+getWindow(886,608)};
    ART.Sketchup.callback("Tools.my_models",data);
}

/**
 * 创建套餐
 */
function skp_upload_package_html(){
    var data = {"url":html_upload_package_url};
    ART.Sketchup.callback("Tools.open_package_editor_window",data);
}

/**
 * 查看套餐
 */
function skp_apple_package_html(){
    ART.Sketchup.callback("Tools.open_apply_package_window");
}

/**
 * 云渲染
 */
function skp_render_html(){
    var data = {"url":rander_upload_url};
    ART.Sketchup.callback("Tools.render",data);
}

/**
 * 组件
 */
function skp_show_component_html(){
    var data = {"url":html_show_component_url};
    ART.Sketchup.callback("Tools.show_component_editor",data);
}

/**
 * 型录
 */
function skp_show_catalog_html(){
    var data = {"url":html_show_catalog_url};
    ART.Sketchup.callback("Tools.show_catalog_window",data);
}

/**
 * 通用规则
 */
function skp_show_seriesr_html(){
    var data = {"url":html_show_series_url};
    ART.Sketchup.callback("Tools.open_seriesr_rules_window",data);
}

/**
 * 新建通用规则
 */
function skp_add_seriesr_html(){
    ART.Sketchup.callback("SeriesrRules.add_rules");
}

/**
 * 修改通用规则
 */
function skp_edit_seriesr_html(id,name,json){
    var data = {"rules":json,"name":name,"id":id};
    ART.Sketchup.callback("SeriesrRules.edit_rules",data);
}

/**
 * 施工图
 */
function skp_show_structural_html(){
    ART.Sketchup.callback("Tools.create_structural");
}

function skp_projectreports(){
    var data = {"url":project_report_create_url};
    ART.Sketchup.callback("Tools.report",data);
}

/**
 * 暂时未开发
 */
function skp_html(){
    //var data = {"url":html_show_catalog_url};
    //ART.Sketchup.callback("Tools.open_seriesr_rules_window",data);
    alert("此页面正在开发中...");
}

/**
 * 平面图库下载
 * @param url
 */
function skp_down_parts(url){
    var data = {"url":url};
    var data = {"url":url};
    ART.Sketchup.callback("Tools.download_element",data);
}

function skp_down_my_house(url){
    var e=e||event;
    var target=e.currentTarget||e.srcElement;
    if(target.tagName!="DIV"){
         target=$(target).parents("div").eq(0);
     }
    var data = {"url":url};
    begin_loading(target);
    setTimeout(end_loading,4000);
    var data = {"url":url};
    ART.Sketchup.callback("MyFloorPlans.download_floor_plan",data);
}


function skp_down_home_house(url){
    var data = {"url":url};
    begin_loading();
    // setTimeout(end_loading,4000);
    ART.Sketchup.callback("Tools.download_floor_plan",data);
}

/******************************************************************
 * 下载套餐
 * @param responce
 */
function skp_down_home_package(responce){
    
    var data = {"url":url};
    var data = {
        "name" : responce.packages.name,
        "package_id" : responce.packages.id,
        "package_json" : responce.packages.json,
        "area" : responce.packages.area,
        "price" : responce.packages.unitprice,
        "style" : responce.packages.style,
        "seriesr_rule" : responce.packages.seriesJson,
        "upgrade_zone_infos":responce.upgradesJson
    };
    ART.Sketchup.callback("Tools.apply_package",data);
}




function skp_down_my_model(url,models){
    var e=e||event;
    var target=e.currentTarget||e.srcElement;
    var data = {"url":url};
    begin_loading(target);
    var data = {"url":url,"models":models};
    ART.Sketchup.callback("MyModels.download_element",data);
}

function skp_down_yun_model(url,models){     
    var data = {"url":url,"models":models};
    ART.Sketchup.callback("Tools.download_element",data);
    
}

function skp_down_yun_model_tree(models){
    var data = {"models":models};
    ART.Sketchup.callback("ModelsTree.get_products",data);
}

function skp_components_tree(catalogs){
    var data = {"catalogs":catalogs};
    ART.Sketchup.callback("Components.add_product",data);
}

function skp_catalogs_tree(components){
    ART.Sketchup.callback("Attributes.add_component",components);
}

/**
 * 组件里打开型录
 */
function skp_show_catalogs_tree(){
    var data = {"url":html_show_catalog_url};
    ART.Sketchup.callback("Components.show_catalog_window",data);
}




function skp_upload_houseType(){

    var data = {"url":html_upload_house_url+getWindow(378,582)};
    ART.Sketchup.callback("Tools.open_upload_floor_plan_window", data);
}

function skp_edit_upload_houseType(id){
    var data = {"url":html_edit_house_url+getWindow(378,582)+"&i="+id};
    ART.Sketchup.callback("Tools.open_upload_floor_plan_window", data);
}

function skp_upload_houseType_file(){
    var e=e||event;
    var target=e.currentTarget||e.srcElement;
    var data = {"url":url};
    begin_loading(target);
    ART.Sketchup.callback("Upload.upload_floor_plan", {
        url : upload_file_house_url
    });
}

function skp_upload_model_file(){
    var e=e||event;
    var target=e.currentTarget||e.srcElement;
    var data = {"url":url};
    begin_loading(target);
    ART.Sketchup.callback("Model.upload_model", {
        url : upload_file_house_url
    });
}

function skp_upload_my_model_file(){
    var e=e||event;
    var target=e.currentTarget||e.srcElement;
    var data = {"url":url};
    begin_loading(target);
    ART.Sketchup.callback("MyModel.upload_my_model", {
        url : upload_file_house_url
    });
}

function skp_upload_taocan_file(){
    ART.Sketchup.callback("Package.upload_package", {
        url : upload_file_house_url
    });
}

function skp_upload_my_model(){
    var data = {"url":html_upload_my_model_url};
    ART.Sketchup.callback("MyModels.open_upload_my_model_window", data);
}

function skp_upload_single_model(){
    var data = {"url":html_upload_single_model_url};
    ART.Sketchup.callback("MyModels.open_sku_window", data);
}

function skp_edit_upload_my_model(id){
    var data = {"url":html_upload_my_model_url+"/"+id};
    ART.Sketchup.callback("MyModels.open_upload_my_model_window", data);
}

function skp_edit_upload_single_model(id){
    var data = {"url":html_upload_single_model_url+"/"+id};
    ART.Sketchup.callback("MyModels.open_sku_window", data);
}

Element.prototype.index=function(){
    var arr=[];
    var pn=this.parentNode;
    for(var i=0;i<this.parentNode.childNodes.length;i++){
        if(pn.childNodes[i]==this){
            return i;
        }               
    }
}   


function begin_loading(){
    
    $("body").prepend("<div class='imgmarkall'></div>");
    $("body").append("<img src='images/loading.gif' class='download'/>");
    
    $(".imgmarkall").css({
         "position":"fixed",
         "width":"100%",
         "height":"100%",
          "z-index":"9999",
          "background":"rgba(0,0,0,.1)"
    });
    $(".download").css({
        "position":"absolute",
        "left":"50%",
        "top":"50%",
        "width":"60px",
        "marginLeft":"-30px",
        "marginTop":"-30px",
        "z-index":"999999999"
    });
}

function end_loading(){
     $(".imgmarkall").css({
         "display":"none",
     });
     $(".download").css({
         "display":"none",
     });  
}


function idplusDownloadEnd(){
    $(".download").css({
        "display":"none",
    });
}