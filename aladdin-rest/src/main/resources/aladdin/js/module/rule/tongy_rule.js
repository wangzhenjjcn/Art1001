/**
 * Created by shihan on 2016/10/11.
 */
function seriesReload(){
    location.reload();
}

$(function(){

    function initEvent(){
        $('.tongY_content table tr').on('click','.editor',function(){
            $(this).find('img').attr('src','images/tongy_rule/rule_editor_hover.png')
            var id = $(this).find('img').attr('alt');
            edit_series(id);
        })
        $('.tongY_content table tr').on('click','.rule',function(){
            $(this).find('img').attr('src','images/tongy_rule/rule_del_hover.png');
            var id = $(this).find('img').attr('alt');
            del_series(id)
        })
    }



    $('#addrule').on('click',function(){
        add_series();
    })




    var tpl = document.getElementById('tpl').innerHTML;

    function render(){

        var obj = {};
        var url = ty_ruleList_url;
        $.fn.mypost(url,obj,getUserToken(),function(response){
            //console.log(response);
            response = my_eval(response);
            var code = response.code;
            if(code==200){
                var tpl_html = juicer(tpl, response);
                $("#m_tpl").empty();
                $("#m_tpl").html(tpl_html);
                initEvent();
            }else{
                alert("服务器错误，请稍后重试");
            }
        });

    }

    function add_series(){
       skp_add_seriesr_html();
    }


    function edit_series(id){

        var obj = {"id":id};
        var url = ty_rule_get_url;
        $.fn.mypost(url,obj,getUserToken(),function(response){
            //console.log(response);
            response = my_eval(response);
            var code = response.code;
            if(code==200){
                skp_edit_seriesr_html(response.id,response.name,response.json)
            }else{
                alert("服务器错误，请稍后重试");
            }
        });

    }


    function del_series(id){

        var obj = {"id":id};
        var url = ty_rule_del_url;
        $.fn.mypost(url,obj,getUserToken(),function(response){
            //console.log(response);
            response = my_eval(response);
            var code = response.code;
            if(code==200){
                render();
            }else{
                alert("服务器错误，请稍后重试");
            }
        });

    }

    render();

})

