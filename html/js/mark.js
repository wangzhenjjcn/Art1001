
    //loading
    function loadShow( className ){
        $('.mark').show();
        setTimeout(function(){
            $('.mark').fadeOut();
            if(className){
                test(className);
            }else{
                test('.shopList_box');
            }

        },1000);
    }
    function test( className ){
        setTimeout(function() {
            $('.mark').stop().fadeOut();
            $(className).stop().fadeIn();
        },300)
    }
