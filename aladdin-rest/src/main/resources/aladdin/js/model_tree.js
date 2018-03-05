$(function () {
        // 6 create an instance when the DOM is ready

        $('#jstree').data('jstree', false).empty();
        $('#jstree').jstree({
            "checkbox": {
                "keep_selected_style": true
            },
            'plugins': ["checkbox"],
            'core' : {
                'multiple' : true,
                'data' : {
                    "url" : models_tree_url+"?access_token="+getUserToken(),
                    "dataType" : "json",
                    "data" : function(node) {
                        if(node.id=="#"){
                            return {
                                "id" : "0"
                            };
                        }else{
                            var id = node.id;
                            return {
                                "id" : id
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
                if(type==="F"){
                    r.push(data.selected[i]);
                }
            }
            $('#mid').val(r.join(','));
        }).jstree();

        $("#btn_ok").on('click',function(){
            var ids =  $('#mid').val();
            if(ids==""){
                alert("请选中模型");
            }else{

                var obj = {"id":ids};

                var url = models_get_url;
                $.fn.mypost(url,obj,getUserToken(),function(response){
                    //console.log(response);
                    response = my_eval(response);
                    skp_down_yun_model_tree(response.data);
                });

            }
        });

        $("#btn_cancel").on('click',function(){
            skp_window_close();
        });


    });



