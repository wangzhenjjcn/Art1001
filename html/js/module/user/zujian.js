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
                    "url" : components_tree_url+"?access_token="+getUserToken(),
                    "data" : function(node) {
                        if(node.id=="#"||!node.id){
                            return {
                                "id" : ""
                            };
                        }else{
                            return {
                                "id" : node.id
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
        function customMenu(node) {
            $("#change,#component_editor,#componentchild_editor,#changegroup,#subdel").css({display:"none"});
            var items;
                if (node.original.a_attr== "0") {
                    items = {
                        cItem: { // The "rename" menu item
                            label: "创建顶级群组",
                            action: function () {
                                createTopGroup(node);
                            }
                        },
                        c2Item: { // The "delete" menu item
                            label: "创建子群组",
                            action: function () {
                                createChildGroup(node);
                            }
                        }, eItem: { // The "delete" menu item
                            label: "修改群组",
                            action: function () {
                                changeGroup(node);
                            }
                        }, dItem: { // The "delete" menu item
                            label: "删除群组及其子项",
                            action: function () {
                                $("#subdel").css({display:"block"});
                                $(".confirm").data("id",node);
                            }
                        }, aItem: { // The "delete" menu item
                            label: "创建组件",
                            action: function () {
                                $("#change").css({display:"block"});
                                changeChildGroup(node);
                            }
                        }
                    };
                } else {
                    items = {
                        eItem: { // The "rename" menu item
                            label: "修改",
                            action: function () {
                                changeChildGroup(node);
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
        };

    $("#btn_ok").on('click',function(){
        var ids =  $('#mid').val();
        if(ids==""){
            alert("请选中组件");
        }else{
            var obj = {"id":ids};
            var url = components_get_url;
            $.fn.mypost(url,obj,getUserToken(),function(response){
                response = my_eval(response);
                skp_catalogs_tree(response.data);
            });

        }
    });

    $("#btn_cancel").on('click',function(){
        skp_window_close();
    });
        $(".close").bind("click",function(){
            $("#change").css({display:"none"});
            $("#component_editor").css({display:"none"});
            $("#componentchild_editor").css({display:"none"})
            $("#changegroup").css({display:"none"})
        });
        $("#createsave").bind("click",function(){
            var nameValue = $("#componentname2").val();
            var url = components_list_url_save;
            var obj = {name:nameValue}
            $.fn.mypost(url,obj,getUserToken(),function(response){
                $("#component_editor").css({display:"none"})
                location.reload();
            });
        });
        $("#createchildsave").bind("click",function(){
            var editName = $("#componentchild").val();
            var id = $("#createchildsave").data("id")
            var url = components_list_url_save;
            var obj = {"parent":id,"name":editName}
            $.fn.mypost(url,obj,getUserToken(),function(response){
                $("#component_editor").css({display:"none"});
                location.reload();
            });
        });
        $("#createsave3").bind("click",function(){
            var nameValue = $("#componentname3").val();
            var id = $("#createsave3").data("id");
            var url = components_list_url_update;
            var obj = {id:id,name:nameValue};
            $.fn.mypost(url,obj,getUserToken(),function(response){
                $("#changegroup").css({display:"none"});
                location.reload();
            });
        })
    });
    function closeDialog(){
        $("#subdel").css({display:"none"})
    }
    function confirmDelete(){
        var id = $(".confirm").data("id").id;
        var obj = {"id":id};
        var url = components_list_url_del;

        $.fn.mypost(url,obj,getUserToken(),function(response){

            location.reload();
        });
    }


    //创建顶级群组
    function createTopGroup(node){
        $("#component_editor").css({display:"block"});
    }
    //创建子群组
    function createChildGroup(node){
        var id = node.id;
        $("#componentchild_editor").css({display:"block"});
        $("#createchildsave").data("id",id);
    }
    function changeGroup(node){
        var id = node.id;
        $("#changegroup").css({display:"block"});
        $("#createsave3").data("id",id)
    }
    function changeChildGroup(node){
        var selectFlag = false;
        $("#change").css({display:"block"});
        var editId = node.id;
        var url = components_list_url_info;
        var obj = {"id":editId};
        $.fn.mypost(url,obj,getUserToken(),function(res){

            if(node.original.a_attr== "1"){
                $("#componentname").val(res.name);
                if(!res.extra){return;}
                var childInfo = $.parseJSON(res.extra);
                $.each(childInfo,function(index,item){
                    
                    var frage = $("<div class='listgood'><div class='proname listinfo '><input class='catalogId' type='hidden' value='"+item.catalogId+"' /><input class='inpname' type='text' value='"+item.catalogName+"' /></div><div class='pronum listinfo'><input class='inpnum' type='text' value='"+item.formula+"' /></div><div class='prounit listinfo'><span class='tunit'>"+item.unit+"</span><div class='unitlistcon'><a class='unitlist' href=''>个</a><a class='unitlist' href=''>支</a><a class='unitlist' href=''>台</a><a class='unitlist' href=''>块</a><a class='unitlist' href=''>㎡</a><a class='unitlist' href=''>根</a><a class='unitlist' href=''>片</a><a class='unitlist' href=''>把</a><a class='unitlist' href=''>套</a><a class='unitlist' href=''>快</a></div></div><div class='prodel listinfo'>删除</div></div>");
                    frage.appendTo( $("#prolist") )
                })
                $("#prolist").delegate(".prodel","click",function(event){
                    $(this).parent().remove();
                });
                $(".abs").bind("click",function(){
                    $("#prolist").empty();
                })
                $(".prounit").off("click");
                $("#prolist").delegate(".prounit","click",function(event){
                    if(selectFlag){
                        $(this).find(".unitlistcon").css({display:"none"})
                        selectFlag = false;
                    }else{
                        $(this).find(".unitlistcon").css({
                            display:"block"
                        })
                        selectFlag = true;
                    }
                })
                $("#prolist").delegate(".unitlist","click",function(event){
                    $(this).parent().parent().find(".tunit").html( $(this).html() );
                    event.preventDefault()
                })
                $("#addCatalogBtn").off("click");
                $("#addCatalogBtn").on("click",function(){
                    skp_show_catalogs_tree();

                    //var json_a = {"catalogs":[{"id":"4e75aa4ad74943fba2a06e350df8d0a3","name":"测试啊啊啊","owner":"13bd606fa632444a8c001d51ad03a184"}]};
                    //addCatalog(json_a);

                })
                $("#componentSaveBtn").off("click");
                $("#componentSaveBtn").bind("click",function(){
                    var pattern = /<\/div>/;
                    var regFlag = pattern.test($("#prolist").html());
                    if(!regFlag){alert("请选择产品");return;}
                    var arr = [],formula = [],unit = [],catalogId=[],catalogName = [];
                    $(".inpname").each(function(index,item){
                        catalogName.push($(item).val());
                    });
                    $(".tunit").each(function(index,item){
                        unit.push($(item).html());
                    });
                    $(".inpnum").each(function(index,item){
                        formula.push($(item).val());
                    })
                    $(".catalogId").each(function(index,item){
                        catalogId.push($(item).val());
                    })

                    var arrObj = {"guid":"","unit":"斤","catalogName":"修改后的型录名称 ","catalogId":"8591873dde2b420f94fe50e0871824d2","skp":"","type":"accessory","thumb":"","is_package":"","formula":"7"};
                    var strObj = JSON.stringify(arrObj);
                    for(var i=0,len=catalogName.length;i<len;i++){
                        arr[i] = $.parseJSON(strObj);
                        arr[i].catalogName = catalogName[i];
                        arr[i].unit = unit[i];
                        arr[i].formula = formula[i];
                        arr[i].catalogId = catalogId[i];
                    }
                    var strArr = JSON.stringify(arr);
                    var name = $("#componentname").val();
                    var url = components_list_url_update;
                    var obj = {"id":editId,"name":name,"extra":strArr};
                    $.fn.mypost(url,obj,getUserToken(),function(res){
                        if(obj.name==""){alert("请填写组件名称");return false};
                        $("#change").css({display:"none"});
                        location.reload();
                    });
                })
            }else{

                $("#componentname").val("");
                $("#addCatalogBtn").off("click");
                $("#addCatalogBtn").on("click",function(){
                    skp_show_catalogs_tree();
                    //                    var json_a = {"catalogs":[{"id":"4e75aa4ad74943fba2a06e350df8d0a3","name":"测试啊啊啊","owner":"13bd606fa632444a8c001d51ad03a184"}]};
                    //addCatalog(json_a);
                })
                $(".prounit").off("click");
                $("#prolist").delegate(".prounit","click",function(event){
                    if(selectFlag){
                        $(this).find(".unitlistcon").css({display:"none"})
                        selectFlag = false;
                    }else{
                        $(this).find(".unitlistcon").css({display:"block"})
                        selectFlag = true;
                    }
                })
                $("#prolist").delegate(".unitlist","click",function(event){
                    $(this).parent().parent().find(".tunit").html( $(this).html() );
                    event.preventDefault();
                })
                $("#prolist").delegate(".prodel","click",function(event){
                    $(this).parent().remove();
                });
                $(".abs").bind("click",function(){
                    $("#prolist").empty();
                })
                $("#componentSaveBtn").off("click");
                $("#componentSaveBtn").bind("click",function(){
                    var pattern = /<\/div>/;
                    var regFlag = pattern.test($("#prolist").html());
                    if(!regFlag){alert("请选择产品");return;}
                    var arr = [],formula = [],unit = [],catalogId=[],catalogName = [];
                    $(".inpname").each(function(index,item){
                        catalogName.push($(item).val());
                    });
                    $(".tunit").each(function(index,item){
                        unit.push($(item).html());
                    });
                    $(".inpnum").each(function(index,item){
                        formula.push($(item).val());
                    })
                    $(".catalogId").each(function(index,item){
                        catalogId.push($(item).val());
                    })
                    if(res.extra!=null&&res.extra!=""){
                        var arrObj = $.parseJSON(res.extra)[0];
                    }else{
                        var arrObj = {"guid":"","unit":"斤","catalogName":"修改后的型录名称 ","catalogId":"8591873dde2b420f94fe50e0871824d2","skp":"","type":"accessory","thumb":"","is_package":"","formula":"7"};
                    }
                    var strObj = JSON.stringify(arrObj);
                    for(var i=0,len=catalogName.length;i<len;i++){
                        arr[i] = $.parseJSON(strObj);
                        arr[i].catalogName = catalogName[i];
                        arr[i].unit = unit[i];
                        arr[i].formula = formula[i];
                        arr[i].catalogId = catalogId[i];
                    }

                    var strArr = JSON.stringify(arr);
                    var name = $("#componentname").val();
                    var url = components_list_url_save;
                    var obj = {"name":name,"extra":strArr,"parent":editId,"type":1};
                    $.fn.mypost(url,obj,getUserToken(),function(res){

                        $("#change").css({display:"none"});
                        location.reload();
                    });
                })
            }
        });
    }
    function addCatalog(arr){
        $.each(arr.catalogs,function(index,item){
            var frage = $("<div class='listgood'><div class='proname listinfo'><input class='catalogId' type='hidden' value='"+item.id+"' /><input class='inpname' type='text' value='"+item.name+"' /></div><div class='pronum listinfo'><input class='inpnum' type='text' value='' /></div><div class='prounit listinfo'><span class='tunit'></span><div class='unitlistcon'><a class='unitlist' href=''>个</a><a class='unitlist' href=''>支</a><a class='unitlist' href=''>台</a><a class='unitlist' href=''>块</a><a class='unitlist' href=''>㎡</a><a class='unitlist' href=''>根</a><a class='unitlist' href=''>片</a><a class='unitlist' href=''>把</a><a class='unitlist' href=''>套</a><a class='unitlist' href=''>快</a></div></div><div class='prodel listinfo'>删除</div></div>");
            $(".inpnum").val(slstr);
            frage.appendTo( $("#prolist") );
        })
    }

    //算量弹出框效果
    //
    //
    // //初始化弹出框内容函数
    function init(){
        slstr=""
        $(".slrst").val(""); 
    }

    //双击出现弹出框效果
    $(".f22").parent().append("<div class='markall'></div>");
     var markall=$(".markall");
    $("#prolist").delegate(".inpnum","dblclick",showHandler);
    var nowindex;
    function showHandler(event){ 
        if($(this).val()==""){
            init();
        }  
        $(".slrst").val($(this).val());
         slstr=$(this).val();        
        markall.css({
          "display":"block",
          "z-index":"998",
          "height":$("#change").height()+150+"px"
        })
        $(".mypop").css({
            "display":"block",
            "z-index":"999",
            "margin-top":-$(".mypop").height()/2,
            "margin-left":-$(".mypop").width()/2,
        });
        nowindex=$(this).parent().parent().index();
    }
   
    

    //算量表达式计算过程
    //添加变量  
    var slstr="";
    var newstr="";
    $(".blbox").on("click","a",slrstHandler);
    function slrstHandler(){
        var arr_hanzi=["灯带长度","面积","长","宽","高","数量","厚度","周长","窗帘盒长度"];
        var arr_sl=["lamp_length","area","length","width","height","depthnumber","depth","perimeter","curtain_box_length"];
        $(this).addClass("active").siblings().removeClass("active");
        for(var i=0;i<arr_hanzi.length;i++){
            if($(this).text()==arr_hanzi[i]){
                slstr=slstr+arr_sl[i];
                
            }
        }
        $(".slrst").val(slstr);        
    }
    //添加数值
     $(".calculator").on("click","span",slHandler);
     function slHandler(){
        var arr_fir=["(",")","=","<",">","÷","×","+","-","0","1","2","3","4","5","6","7","8","9",
                        ".",",","^","√￣","与","或","非","绝对","如果","最大","最小",
                            "平均","求余","取整"];
        var arr_sec=["(",")","=","<",">","/","*","+","-","0","1","2","3","4","5","6","7","8","9",
                        ".",",","POWER(","SQRT(","AND(","OR(","NOT(","ABS(","IF(","MAX(","MIN(",
                            "AVERAGE(","MOD(","ROUNDUP("];
        for(var i=0;i<arr_fir.length;i++){
            if($(this).text()==arr_fir[i]){
              slstr=slstr+arr_sec[i];  
            }
        }
         $(".slrst").val(slstr);   //最终的拼接字符串
     }
    
     //确认事件
     $(".submit").on("click",subHandler);
     function subHandler(){
        if($(".slrst").val()==""||$(".slrst").val()=="算量表达式"||$(".slrst").val()=="*请填写算量表达式*"){
            $(".slrst").val("*请填写算量表达式*");
            return false;
        }        
         $(".mypop").css({
            "display":"none",
        });
        markall.css({
            "display":"none",
        });
        $(".listgood").eq(nowindex).find(".inpnum").val(slstr);
     }

     //取消功能
    $(".cancle").on("click",hideHandler);
    function hideHandler(){
        markall.css({
            "display":"none"
        });
        $(".mypop").css({
            "display":"none"
        });
    }

     //clear键的删除功能
     $(".clear").on("click",function(){
        var i=slstr.length;
        slstr=slstr.slice(0,i-1);
         $(".slrst").val(slstr);
     })

     //clearall全部清除功能
    $(".clearall").on("click",function(){
         init();
    });
    //判断用户是否按下删除键
    document.onkeydown = function(){
        var oEvent = window.event; 
        if (oEvent.keyCode == 8) { 
            init();
    }}
