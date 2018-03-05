/**
 * Created by haibo on 2016/10/25.
 */
$(function () {
    // 6 create an instance when the DOM is ready
    $.ajaxSetup({
        cache : false
    });
    $('#jstree').data('jstree', false).empty();
    $('#jstree').jstree({
        "checkbox": {
            "keep_selected_style": true
        },
        'plugins': ["checkbox", "contextmenu"],
        'contextmenu': {"items": customMenu},
        'core' : {
            'multiple' : true,
            'data' : {
                "url" : catalogs_tree_url+"?access_token="+getUserToken(),
                "data" : function(node) {
                    if(node.id=="#"){
                        return {
                            "pid" : ""
                        };
                    }else{
                        return {
                            "pid" : node.id
                        };
                    }
                }
            }
        }
    });

    $('#jstree').on('changed.jstree', function(e, data) {
        var i, j, r = [];
        for (i = 0, j = data.selected.length; i < j; i++) {
            var type = data.instance.get_node(data.selected[i]).original.a_attr;
            if(type==="1"){
                r.push(data.selected[i]);
            }
        }
        $('#mid').val(r.join(','));
    }).jstree();

    //根据id查看型录详情
    function getInfoById(id,fn){
        $.fn.mypost(category_info_url,{"id":id},getUserToken(),function(response){
            fn(response);
        })
    }

//        右键点击菜单事件
    function customMenu(node) {
            //alert(JSON.stringify(node));
            var items;

            if (node.original.a_attr== "0") {
                items = {
                    cItem: { // The "rename" menu item
                        label: "创建顶级型录",
                        action: function () {
                            $(".topxl").css({"display":"block"});
                            $("#baocun1").data("id",node);
                        }
                    },
                    c2Item: { // The "delete" menu item
                        label: "创建子项",
                        action: function () {
                            $(".creatSon").css({"display":"block"});
                            $("#baocun2").data("id",node.id);
                        }
                    }, eItem: { // The "delete" menu item
                        label: "修改",
                        action: function () {
                            getInfoById(node.id,function(res){
                                if(res.code=='200'){
                                    $("#xlname2").val(res.name);
                                    $(".revise").css({"display":"block"});
                                    $("#baocun3").data("id",node.id);
                                }else{
                                    alert('型录信息获取失败');
                                }
                            });
                        }
                    }, dItem: { // The "delete" menu item
                        label: "删除",
                        action: function () {
                            $("#subdel").css({"display":"block"});
                            $(".confirm").data("id",node);
                        }
                    }, aItem: { // The "delete" menu item
                        label: "添加产品",
                        action: function () {

                            $('.addproduct').find("input:text").val("");
                            $(".addproduct").find("select").val("");

                            $(".addproduct").css({"display":"block"});
                            $(".baocun4").data("id");
                            addCateLog(node);
                        }
                    }
                };
            } else {
                items = {
                    eItem: { // The "rename" menu item
                        label: "修改",
                        action: function () {
                            getInfoById(node.id,function(res){
                                if(res.code=='200'){
                                    $(".addproduct").css({"display":"block"});
                                    $(".baocun4").data("id",node.id);
                                    addCateLog(node);

                                    $('.addproduct').find("input:text").val("");
                                    $(".addproduct").find("select").val("");

                                    //修改型录信息 展示
                                    $(".catalogname").val(res.name);
                                    if(res.model!=null){
                                        var m=res.model;
                                        $("#pinpainame").val(m.brand==null?"": m.brand);
                                        $(".danjianame").val(m.unitprice==null?"": m.unitprice);
                                        $(".xinghaoname").val(m.modelnumber==null?"": m.modelnumber);
                                        if(m.unit!=null){
                                            $(".danweiname").val(m.unit);
                                        }

                                    }
                                    if(res.modelMetaMap!=null){
                                        var meta=res.modelMetaMap;
                                        if(typeof meta.specification !="undefined"){
                                            $(".guigename").val(meta.specification);
                                        }
                                        if(typeof meta.unitpriceMan !="undefined"){
                                            $(".rengongfeename").val(meta.unitpriceMan);
                                        }

                                        if(typeof meta.unitpriceMaterial !="undefined"){
                                            $(".cailiaofeename").val(meta.unitpriceMaterial);
                                        }
                                        if(typeof meta.transportExpenses !="undefined"){
                                            $(".banyunfeename").val(meta.transportExpenses);
                                        }
                                        if(typeof meta.installExpenses !="undefined"){
                                            $(".anzhuangfeename").val(meta.installExpenses);
                                        }
                                        if(typeof meta.wastageRate !="undefined"){
                                            $(".sunhaoratename").val(meta.wastageRate);
                                        }
                                        if(typeof meta.stage !="undefined"){
                                            $(".jieduanname").val(meta.stage);
                                        }
                                        if(typeof meta.exportType !="undefined"){
                                            $(".shuchutypename").val(meta.exportType);
                                        }
                                        if(typeof meta.projectCategory !="undefined"){
                                            $(".gongchengname").val(meta.projectCategory);
                                        }
                                    }
                                    if(res.modelFunctionList!=null){
                                        var funarr=new Array();
                                        funarr=res.modelFunctionList;
                                        for(var i=0;i<funarr.length;i++){
                                            var f=funarr[i].function;
                                            $("#box #list li").each(function(){
                                                var t=$(this).text();
                                                if(t==f){
                                                    $(this).addClass("active");
                                                }
                                            });
                                        }
                                    }
                                }else{
                                    alert('型录信息获取失败');
                                }
                            });
                        }
                    },
                    dItem: { // The "delete" menu item
                        label: "删除",
                        action: function () {
                            $("#subdel").css({display:"block"});
                            $(".confirm").data("id",node);
                        }
                    }
                };
            }

        return items;
    }


    $("#btn_ok").on('click',function(){
        var ids =  $('#mid').val();
        if(ids==""){
            alert("请选中型录");
        }else{
            var obj = {"ids":ids};
            var url = catalogs_get_url;
            $.fn.mypost(url,obj,getUserToken(),function(response){
                response = my_eval(response);
                skp_components_tree(response.data);
            });

        }
    });

    $("#btn_cancel").on('click',function(){
        skp_window_close();
    });
});
//    关闭界面
function closeDialog(){
    $("#subdel").css({display:"none"});
    $(".topxl").css({display:"none"});
    $(".creatSon").css({display:"none"});
    $(".revise").css({display:"none"});
    $(".addproduct").css({display:"none"});
}
//    删除 782dd7c206d54d51b67064fae7faf507
function confirmDelete(){
    var id = $(".confirm").data("id").id;
    var obj = {"id":id};
    var url = catalogs_del_url;
    $.fn.mypost(url,obj,getUserToken(),function(response){
        $(".subdel").css({display:"none"});
        location.reload();
    });
}
//    创建顶级型录
$("#baocun1").bind("click",function(){
    var nameValue = $("#xlname").val();
    var url = server_url+"/api/v1/catalogs/save";
    var obj = {name:nameValue};
    $.fn.mypost(url,obj,getUserToken(),function(response){
        $(".topxl").css({display:"none"});
        location.reload();
    });
})
//    创建子项
$("#baocun2").bind("click",function(){
    var editName = $("#xlname1").val();
    var id = $("#baocun2").data("id")
    var url = server_url+"/api/v1/catalogs/save";
    var obj = {"parent":id,"name":editName}
    $.fn.mypost(url,obj,getUserToken(),function(response){
        $(".creatSon").css({display:"none"})
        location.reload();
    });
})
//    修改
$("#baocun3").bind("click",function(){
    var nameValue = $("#xlname2").val();
    var id = $("#baocun3").data("id");
    var url = server_url+"/api/v1/catalogs/update";
    var obj = {"id":id,"name":nameValue};
    $.fn.mypost(url,obj,getUserToken(),function(response){
        $(".revise").css({display:"none"});
        location.reload();
    });
})
//选择区域部分
var List = document.getElementById("list");
var Li = List.getElementsByTagName("li");
//绑定事件
for (var i = 0; i < Li.length; i++) {
    Li[i].onclick = function(){
        if (this.className == "") {
            this.className = "active";
        }else{
            this.className = "";
        }
    }
}
function addCateLog(node){
    $(".baocun4").off("click");
    $(".baocun4").bind("click",function(){
        var id=$(".baocun4").data("id");

        var obj = {};
        var url = "";

        if(typeof id == "undefined"){//表示添加
            url = catalogs_add_url;
        }else{//表示修改
           obj.id=id;
            url=catalogs_edit_url;
        }

        obj.name = $(".catalogname").val();
        obj.parent = node.id;
        obj.type = 1;
        obj.exportType = $(".shuchutypename option:selected").val();
        obj.brand = $("#pinpainame option:selected").val();
        obj.modelnumber = $(".xinghaoname").val();
        obj.unit = $(".danweiname option:selected").html();
        obj.unitprice = $(".danjianame").val();
        obj.specification = $(".guigename").val();
        obj.unitpriceMan = $(".rengongfeename").val();
        obj.unitpriceMaterial = $(".cailiaofeename").val();
        obj.transportExpenses = $(".banyunfeename").val();
        obj.installExpenses = $(".anzhuangfeename").val();
        obj.wastageRate = $(".sunhaoratename").val();
        obj.stage = $(".jieduanname option:selected").html();
        obj.category = $(".gongchengname option:selected").html();
        var modelFunctions ="";
        $("#list li").each(function(index,item){
            if($(item).hasClass("active")){
                modelFunctions+=$(item).find("span").html()+",";
            }
        });
        obj.modelFunctions = modelFunctions.slice(0,-1);
        $.fn.mypost(url,obj,getUserToken(),function(res){
            if(res.code===200){
                $(".addproduct").css({display:"none"});
                location.reload();
            }
        })

  })
}

//加载品牌
function loadBrand() {
    var barnd_tpl = document.getElementById('brand_tpl').innerHTML;
    var obj = {};
    var url = brand_list_url;
    $.fn.mypost(url,obj,getUserToken(),function(response){
        //console.log(response);
        response = my_eval(response);
        var code = response.code;
        if (code == 200) {
            var tpl_html = juicer(barnd_tpl, response);//模板赋值
            $("#pinpainame").empty();
            $("#pinpainame").html(tpl_html);

        } else {
            alert("服务器错误，请稍后重试");
        }
    });

}
