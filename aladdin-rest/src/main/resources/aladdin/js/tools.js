window.clickEvent='click';
function plus(number,obj,method) {
    var $obj = $(obj),
        $field = $obj.parents('.field'),
        $input = $field.find('input'),
        value = parseFloat( parseFloat($input.val().replace(/~ /,"")).toFixed(2) );
    $input.val(value + parseFloat(number));
    WN.Sketchup.callback("Tools."+method,{value: $input.val()});
}
function minus(number,obj,method) {
    var $obj = $(obj),
        $field = $obj.parents('.field'),
        $input = $field.find('input'),
        value = parseFloat( parseFloat($input.val().replace(/~ /,"")).toFixed(2) ),
        result = value - parseFloat(number);
    result = result <= 0? 0: result;
    $input.val(result);
    WN.Sketchup.callback("Tools."+method,{value: $input.val()});
}
$(function(){
    $("a[class='move']").click(function(){
        WN.Sketchup.callback("Tools.move");
    }); 
    $("a[class='erase']").click(function(){
        WN.Sketchup.callback("Tools.erase");
    }); 
    $("a[class='copy']").click(function(){
        WN.Sketchup.callback("Tools.copy");
    });
    $("a[class='rotate']").click(function(){
        WN.Sketchup.callback("Tools.rotate");
    });
    $("a[class='arc']").click(function(){
        WN.Sketchup.callback("Tools.arc");
    });
    $("a[class='split']").click(function(){
        WN.Sketchup.callback("Tools.split");
    });
    $("a[class='place_text']").click(function(){
        WN.Sketchup.callback("Tools.place_text");
    });

    WN.bindEventListener(window.clickEvent, '.checkedBox', function(){
        var $obj = $(this), checked = $obj.hasClass('checked');
        if(checked) {
            $obj.removeClass('checked');
        } else {
            $obj.addClass('checked');
        }
    });
});
