$(function(){


    //事件委托  后渲染的元素绑定[添加图片]
    $(document).on('change','input[type="file"]', function (e) {
        var $e = $( e.target );
            //正常图片上传 为这部分代码
            var $parent = $(this).parent();
            var img = $parent.next().find('img')[0];
            var File = this.files[0],
                reader = new FileReader();
            if (!this.files || !File) {return false;}
            if (!/^image/g.test(File.type)) {alert("请选择图片格式");return false;}
            reader.onload = function(n) {
                img.src = n.target.result;
                //$parent.find('.div-1').hide();
                //$parent.find('.div-2').show();
            };
            reader.readAsDataURL(File);

    });
});